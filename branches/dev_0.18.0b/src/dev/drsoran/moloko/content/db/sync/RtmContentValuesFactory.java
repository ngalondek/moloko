/* 
 *	Copyright (c) 2013 Ronny Röhricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.content.db.sync;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import dev.drsoran.moloko.content.db.TableColumns.RtmContactColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmLocationColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmNoteColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmRawTaskColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmSettingsColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTaskSeriesColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTasksListColumns;
import dev.drsoran.moloko.domain.content.IContentValuesFactory;
import dev.drsoran.rtm.Strings;
import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.model.RtmLocation;
import dev.drsoran.rtm.model.RtmNote;
import dev.drsoran.rtm.model.RtmRawTask;
import dev.drsoran.rtm.model.RtmSettings;
import dev.drsoran.rtm.model.RtmTaskSeries;
import dev.drsoran.rtm.model.RtmTasksList;


public class RtmContentValuesFactory implements IContentValuesFactory
{
   private final Map< Class< ? >, IFactoryMethod< ? > > factoryMethodLookUp = new HashMap< Class< ? >, IFactoryMethod< ? > >();
   
   
   
   public RtmContentValuesFactory()
   {
      factoryMethodLookUp.put( RtmTasksList.class,
                               new RtmTasksListContentValuesFactoryMethod() );
      factoryMethodLookUp.put( RtmTaskSeries.class,
                               new RtmTaskSeriesContentValuesFactoryMethod() );
      factoryMethodLookUp.put( RtmRawTask.class,
                               new RtmRawTaskContentValuesFactoryMethod() );
      factoryMethodLookUp.put( RtmNote.class,
                               new RtmNoteContentValuesFactoryMethod() );
      factoryMethodLookUp.put( RtmContact.class,
                               new RtmContactContentValuesFactoryMethod() );
      factoryMethodLookUp.put( RtmLocation.class,
                               new RtmLocationContentValuesFactoryMethod() );
      factoryMethodLookUp.put( RtmSettings.class,
                               new RtmSettingsContentValuesFactoryMethod() );
   }
   
   
   
   @Override
   public < T > ContentValues createContentValues( T modelElement ) throws IllegalArgumentException
   {
      @SuppressWarnings( "unchecked" )
      final IFactoryMethod< T > responsibleMethod = (IFactoryMethod< T >) factoryMethodLookUp.get( modelElement.getClass() );
      
      if ( responsibleMethod == null )
      {
         throw new IllegalArgumentException( MessageFormat.format( "The element type ''{0}'' is not supported.",
                                                                   modelElement.getClass()
                                                                               .getName() ) );
      }
      
      final ContentValues contentValues = responsibleMethod.create( modelElement );
      return contentValues;
   }
   
   
   private interface IFactoryMethod< T >
   {
      ContentValues create( T modelElement );
   }
   
   
   private final class RtmTasksListContentValuesFactoryMethod implements
            IFactoryMethod< RtmTasksList >
   {
      @Override
      public ContentValues create( RtmTasksList tasksList )
      {
         final ContentValues values = new ContentValues();
         
         values.put( RtmTasksListColumns.RTM_LIST_ID, tasksList.getId() );
         values.put( RtmTasksListColumns.LIST_NAME, tasksList.getName() );
         values.put( RtmTasksListColumns.LOCKED, tasksList.isLocked() ? 1 : 0 );
         values.put( RtmTasksListColumns.ARCHIVED, tasksList.isArchived() ? 1
                                                                         : 0 );
         values.put( RtmTasksListColumns.POSITION, tasksList.getPosition() );
         
         if ( tasksList.isSmartList() )
         {
            final String filter = tasksList.getSmartFilter();
            values.put( RtmTasksListColumns.IS_SMART_LIST, 1 );
            values.put( RtmTasksListColumns.FILTER, filter );
         }
         else
         {
            values.put( RtmTasksListColumns.IS_SMART_LIST, 0 );
            values.putNull( RtmTasksListColumns.FILTER );
         }
         
         return values;
      }
   }
   
   
   private final class RtmTaskSeriesContentValuesFactoryMethod implements
            IFactoryMethod< RtmTaskSeries >
   {
      @Override
      public ContentValues create( RtmTaskSeries taskSeries )
      {
         final ContentValues values = new ContentValues();
         
         values.put( RtmTaskSeriesColumns.RTM_TASKSERIES_ID, taskSeries.getId() );
         values.put( RtmTaskSeriesColumns.TASKSERIES_CREATED_DATE,
                     taskSeries.getCreatedMillisUtc() );
         values.put( RtmTaskSeriesColumns.TASKSERIES_MODIFIED_DATE,
                     taskSeries.getModifiedMillisUtc() );
         values.put( RtmTaskSeriesColumns.TASKSERIES_NAME, taskSeries.getName() );
         values.put( RtmTaskSeriesColumns.RTM_LIST_ID, taskSeries.getListId() );
         
         if ( !Strings.isNullOrEmpty( taskSeries.getSource() ) )
         {
            values.put( RtmTaskSeriesColumns.SOURCE, taskSeries.getSource() );
         }
         else
         {
            values.putNull( RtmTaskSeriesColumns.SOURCE );
         }
         
         if ( !Strings.isNullOrEmpty( taskSeries.getUrl() ) )
         {
            values.put( RtmTaskSeriesColumns.URL, taskSeries.getUrl() );
         }
         else
         {
            values.putNull( RtmTaskSeriesColumns.URL );
         }
         
         final String recurrencePattern = taskSeries.getRecurrencePattern();
         if ( recurrencePattern != null )
         {
            values.put( RtmTaskSeriesColumns.RECURRENCE, recurrencePattern );
            values.put( RtmTaskSeriesColumns.RECURRENCE_EVERY,
                        taskSeries.isEveryRecurrence() ? 1 : 0 );
         }
         else
         {
            values.putNull( RtmTaskSeriesColumns.RECURRENCE );
            values.putNull( RtmTaskSeriesColumns.RECURRENCE_EVERY );
         }
         
         if ( taskSeries.getLocationId() != RtmConstants.NO_ID )
         {
            values.put( RtmTaskSeriesColumns.RTM_LOCATION_ID,
                        taskSeries.getLocationId() );
         }
         else
         {
            values.putNull( RtmTaskSeriesColumns.RTM_LOCATION_ID );
         }
         
         final Iterable< String > tags = taskSeries.getTags();
         final String tagsJoined = Strings.join( RtmTaskSeriesColumns.TAGS_SEPARATOR,
                                                 tags );
         
         if ( !Strings.isNullOrEmpty( tagsJoined ) )
         {
            values.put( RtmTaskSeriesColumns.TAGS, tagsJoined );
         }
         else
         {
            values.putNull( RtmTaskSeriesColumns.TAGS );
         }
         
         return values;
      }
   }
   
   
   private final class RtmRawTaskContentValuesFactoryMethod implements
            IFactoryMethod< RtmRawTask >
   {
      @Override
      public ContentValues create( RtmRawTask rawTask )
      {
         final ContentValues values = new ContentValues();
         
         values.put( RtmRawTaskColumns.RTM_RAWTASK_ID, rawTask.getId() );
         values.put( RtmRawTaskColumns.ADDED_DATE, rawTask.getAddedMillisUtc() );
         values.put( RtmRawTaskColumns.PRIORITY, rawTask.getPriority()
                                                        .toString() );
         values.put( RtmRawTaskColumns.POSTPONED, rawTask.getPostponedCount() );
         
         final long due = rawTask.getDueMillisUtc();
         if ( due != RtmConstants.NO_TIME )
         {
            values.put( RtmRawTaskColumns.DUE_DATE, due );
            values.put( RtmRawTaskColumns.HAS_DUE_TIME, rawTask.hasDueTime()
                                                                            ? 1
                                                                            : 0 );
         }
         else
         {
            values.putNull( RtmRawTaskColumns.DUE_DATE );
         }
         
         if ( rawTask.getCompletedMillisUtc() != RtmConstants.NO_TIME )
         {
            values.put( RtmRawTaskColumns.COMPLETED_DATE,
                        rawTask.getCompletedMillisUtc() );
         }
         else
         {
            values.putNull( RtmRawTaskColumns.COMPLETED_DATE );
         }
         
         if ( rawTask.getDeletedMillisUtc() != RtmConstants.NO_TIME )
         {
            values.put( RtmRawTaskColumns.DELETED_DATE,
                        rawTask.getDeletedMillisUtc() );
         }
         else
         {
            values.putNull( RtmRawTaskColumns.DELETED_DATE );
         }
         
         final String estimation = rawTask.getEstimationSentence();
         if ( estimation != null )
         {
            values.put( RtmRawTaskColumns.ESTIMATE, estimation );
         }
         else
         {
            values.putNull( RtmRawTaskColumns.ESTIMATE );
         }
         
         return values;
      }
   }
   
   
   private final class RtmNoteContentValuesFactoryMethod implements
            IFactoryMethod< RtmNote >
   {
      @Override
      public ContentValues create( RtmNote note )
      {
         final ContentValues values = new ContentValues();
         
         values.put( RtmNoteColumns.RTM_NOTE_ID, note.getId() );
         values.put( RtmNoteColumns.NOTE_CREATED_DATE,
                     note.getCreatedMillisUtc() );
         values.put( RtmNoteColumns.NOTE_MODIFIED_DATE,
                     note.getModifiedMillisUtc() );
         values.put( RtmNoteColumns.NOTE_TITLE, note.getTitle() );
         values.put( RtmNoteColumns.NOTE_TEXT, note.getText() );
         
         return values;
      }
   }
   
   
   private final class RtmContactContentValuesFactoryMethod implements
            IFactoryMethod< RtmContact >
   {
      @Override
      public ContentValues create( RtmContact contact )
      {
         final ContentValues values = new ContentValues();
         
         values.put( RtmContactColumns.RTM_CONTACT_ID, contact.getId() );
         values.put( RtmContactColumns.FULLNAME, contact.getFullname() );
         values.put( RtmContactColumns.USERNAME, contact.getUsername() );
         
         return values;
      }
   }
   
   
   private final class RtmLocationContentValuesFactoryMethod implements
            IFactoryMethod< RtmLocation >
   {
      @Override
      public ContentValues create( RtmLocation location )
      {
         final ContentValues values = new ContentValues();
         
         values.put( RtmLocationColumns.RTM_LOCATION_ID, location.getId() );
         values.put( RtmLocationColumns.LOCATION_NAME, location.getName() );
         values.put( RtmLocationColumns.LONGITUDE, location.getLongitude() );
         values.put( RtmLocationColumns.LATITUDE, location.getLatitude() );
         values.put( RtmLocationColumns.VIEWABLE, location.isViewable() ? 1 : 0 );
         values.put( RtmLocationColumns.ZOOM, location.getZoom() );
         
         if ( !Strings.isNullOrEmpty( location.getAddress() ) )
         {
            values.put( RtmLocationColumns.ADDRESS, location.getAddress() );
         }
         else
         {
            values.putNull( RtmLocationColumns.ADDRESS );
         }
         
         return values;
      }
   }
   
   
   private final class RtmSettingsContentValuesFactoryMethod implements
            IFactoryMethod< RtmSettings >
   {
      @Override
      public ContentValues create( RtmSettings settings )
      {
         final ContentValues values = new ContentValues();
         
         values.put( RtmSettingsColumns.SYNC_TIMESTAMP,
                     settings.getSyncTimeStampMillis() );
         values.put( RtmSettingsColumns.TIMEZONE, settings.getTimezone() );
         values.put( RtmSettingsColumns.DATEFORMAT, settings.getDateFormat() );
         values.put( RtmSettingsColumns.TIMEFORMAT, settings.getTimeFormat() );
         
         if ( settings.getDefaultListId() != RtmConstants.NO_ID )
         {
            values.put( RtmSettingsColumns.RTM_DEFAULTLIST_ID,
                        settings.getDefaultListId() );
         }
         else
         {
            values.putNull( RtmSettingsColumns.RTM_DEFAULTLIST_ID );
         }
         
         values.put( RtmSettingsColumns.LANGUAGE, settings.getLanguage() );
         
         return values;
      }
   }
}
