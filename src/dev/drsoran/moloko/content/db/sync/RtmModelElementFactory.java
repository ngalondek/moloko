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
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import android.database.Cursor;
import dev.drsoran.moloko.content.CursorUtils;
import dev.drsoran.moloko.content.db.TableColumns.RtmContactColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmLocationColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmNoteColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmRawTaskColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmSettingsColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTaskSeriesColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTasksListColumns;
import dev.drsoran.moloko.domain.content.IModelElementFactory;
import dev.drsoran.rtm.Strings;
import dev.drsoran.rtm.model.Priority;
import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.model.RtmLocation;
import dev.drsoran.rtm.model.RtmNote;
import dev.drsoran.rtm.model.RtmRawTask;
import dev.drsoran.rtm.model.RtmSettings;
import dev.drsoran.rtm.model.RtmTaskSeries;
import dev.drsoran.rtm.model.RtmTasksList;


public class RtmModelElementFactory implements IModelElementFactory
{
   private final Map< Class< ? >, IFactoryMethod< ? > > factoryMethodLookUp = new HashMap< Class< ? >, IFactoryMethod< ? > >();
   
   
   
   public RtmModelElementFactory()
   {
      factoryMethodLookUp.put( RtmTasksList.class,
                               new RtmTasksListFactoryMethod() );
      factoryMethodLookUp.put( RtmTaskSeries.class,
                               new RtmTaskSeriesFactoryMethod() );
      factoryMethodLookUp.put( RtmRawTask.class, new RtmRawTaskFactoryMethod() );
      factoryMethodLookUp.put( RtmNote.class, new RtmNoteFactoryMethod() );
      factoryMethodLookUp.put( RtmContact.class, new RtmContactFactoryMethod() );
      factoryMethodLookUp.put( RtmLocation.class,
                               new RtmLocationFactoryMethod() );
      factoryMethodLookUp.put( RtmSettings.class,
                               new RtmSettingsFactoryMethod() );
   }
   
   
   
   @Override
   public < T > T createElementFromCursor( Cursor c, Class< T > elementType ) throws IllegalArgumentException
   {
      @SuppressWarnings( "unchecked" )
      final IFactoryMethod< T > responsibleMethod = (IFactoryMethod< T >) factoryMethodLookUp.get( elementType );
      
      if ( responsibleMethod == null )
      {
         throw new IllegalArgumentException( MessageFormat.format( "The element type ''{0}'' is not supported",
                                                                   elementType.getName() ) );
      }
      
      final T element = responsibleMethod.create( c );
      return element;
   }
   
   
   private interface IFactoryMethod< T >
   {
      T create( Cursor c );
   }
   
   
   private final class RtmTasksListFactoryMethod implements
            IFactoryMethod< RtmTasksList >
   {
      @Override
      public RtmTasksList create( Cursor c )
      {
         final RtmTasksList tasksList = new RtmTasksList( c.getString( RtmTasksListColumns.RTM_LIST_ID_IDX ),
                                                          c.getInt( RtmTasksListColumns.POSITION_IDX ),
                                                          CursorUtils.getOptLong( c,
                                                                                  RtmTasksListColumns.LIST_DELETED_DATE_IDX,
                                                                                  RtmConstants.NO_TIME ) != RtmConstants.NO_TIME,
                                                          c.getInt( RtmTasksListColumns.LOCKED_IDX ) != 0,
                                                          c.getInt( RtmTasksListColumns.ARCHIVED_IDX ) != 0,
                                                          c.getString( RtmTasksListColumns.LIST_NAME_IDX ),
                                                          CursorUtils.getOptString( c,
                                                                                    RtmTasksListColumns.FILTER_IDX ) );
         return tasksList;
      }
   }
   
   
   private final class RtmTaskSeriesFactoryMethod implements
            IFactoryMethod< RtmTaskSeries >
   {
      @Override
      public RtmTaskSeries create( Cursor c )
      {
         final Collection< String > tags = getTags( c );
         
         final RtmTaskSeries taskSeries = new RtmTaskSeries( c.getString( RtmTaskSeriesColumns.RTM_TASKSERIES_ID_IDX ),
                                                             c.getLong( RtmTaskSeriesColumns.TASKSERIES_CREATED_DATE_IDX ),
                                                             c.getLong( RtmTaskSeriesColumns.TASKSERIES_MODIFIED_DATE_IDX ),
                                                             c.getString( RtmTaskSeriesColumns.RTM_LIST_ID_IDX ),
                                                             CursorUtils.getOptString( c,
                                                                                       RtmTaskSeriesColumns.RTM_LOCATION_ID_IDX ),
                                                             c.getString( RtmTaskSeriesColumns.TASKSERIES_NAME_IDX ),
                                                             c.getString( RtmTaskSeriesColumns.SOURCE_IDX ),
                                                             CursorUtils.getOptString( c,
                                                                                       RtmTaskSeriesColumns.URL_IDX ),
                                                             CursorUtils.getOptString( c,
                                                                                       RtmTaskSeriesColumns.RECURRENCE_IDX ),
                                                             CursorUtils.getOptBool( c,
                                                                                     RtmTaskSeriesColumns.RECURRENCE_EVERY_IDX,
                                                                                     false ),
                                                             tags,
                                                             null,
                                                             null );
         return taskSeries;
      }
   }
   
   
   private final class RtmRawTaskFactoryMethod implements
            IFactoryMethod< RtmRawTask >
   {
      @Override
      public RtmRawTask create( Cursor c )
      {
         final RtmRawTask task = new RtmRawTask( c.getString( RtmRawTaskColumns.RTM_RAWTASK_ID_IDX ),
                                                 c.getLong( RtmRawTaskColumns.ADDED_DATE_IDX ),
                                                 CursorUtils.getOptLong( c,
                                                                         RtmRawTaskColumns.DELETED_DATE_IDX,
                                                                         RtmConstants.NO_TIME ),
                                                 CursorUtils.getOptLong( c,
                                                                         RtmRawTaskColumns.COMPLETED_DATE_IDX,
                                                                         RtmConstants.NO_TIME ),
                                                 Priority.fromString( c.getString( RtmRawTaskColumns.PRIORITY_IDX ) ),
                                                 c.getInt( RtmRawTaskColumns.POSTPONED_IDX ),
                                                 CursorUtils.getOptLong( c,
                                                                         RtmRawTaskColumns.DUE_IDX,
                                                                         RtmConstants.NO_TIME ),
                                                 CursorUtils.getOptBool( c,
                                                                         RtmRawTaskColumns.HAS_DUE_TIME_IDX,
                                                                         false ),
                                                 CursorUtils.getOptString( c,
                                                                           RtmRawTaskColumns.ESTIMATE_IDX ) );
         return task;
      }
   }
   
   
   
   private final Collection< String > getTags( Cursor c )
   {
      final String tagsJoined = CursorUtils.getOptString( c,
                                                          RtmTaskSeriesColumns.TAGS_IDX );
      
      if ( tagsJoined != null )
      {
         return Arrays.asList( tagsJoined.split( RtmTaskSeriesColumns.TAGS_SEPARATOR ) );
      }
      
      return null;
   }
   
   
   private final class RtmNoteFactoryMethod implements IFactoryMethod< RtmNote >
   {
      @Override
      public RtmNote create( Cursor c )
      {
         final RtmNote note = new RtmNote( c.getString( RtmNoteColumns.RTM_NOTE_ID_IDX ),
                                           c.getLong( RtmNoteColumns.NOTE_CREATED_DATE_IDX ),
                                           c.getLong( RtmNoteColumns.NOTE_MODIFIED_DATE_IDX ),
                                           Strings.emptyIfNull( CursorUtils.getOptString( c,
                                                                                          RtmNoteColumns.NOTE_TITLE_IDX ) ),
                                           Strings.emptyIfNull( CursorUtils.getOptString( c,
                                                                                          RtmNoteColumns.NOTE_TEXT_IDX ) ) );
         return note;
      }
   }
   
   
   private final class RtmContactFactoryMethod implements
            IFactoryMethod< RtmContact >
   {
      @Override
      public RtmContact create( Cursor c )
      {
         return new RtmContact( c.getString( RtmContactColumns.RTM_CONTACT_ID_IDX ),
                                c.getString( RtmContactColumns.USERNAME_IDX ),
                                c.getString( RtmContactColumns.FULLNAME_IDX ) );
      }
   }
   
   
   private final class RtmLocationFactoryMethod implements
            IFactoryMethod< RtmLocation >
   {
      @Override
      public RtmLocation create( Cursor c )
      {
         return new RtmLocation( c.getString( RtmLocationColumns.RTM_LOCATION_ID_IDX ),
                                 c.getString( RtmLocationColumns.LOCATION_NAME_IDX ),
                                 c.getFloat( RtmLocationColumns.LONGITUDE_IDX ),
                                 c.getFloat( RtmLocationColumns.LATITUDE_IDX ),
                                 CursorUtils.getOptString( c,
                                                           RtmLocationColumns.ADDRESS_IDX ),
                                 c.getInt( RtmLocationColumns.VIEWABLE_IDX ) != 0,
                                 c.getInt( RtmLocationColumns.ZOOM_IDX ) );
      }
   }
   
   
   private final class RtmSettingsFactoryMethod implements
            IFactoryMethod< RtmSettings >
   {
      @Override
      public RtmSettings create( Cursor c )
      {
         return new RtmSettings( c.getLong( RtmSettingsColumns.SYNC_TIMESTAMP_IDX ),
                                 c.getString( RtmSettingsColumns.TIMEZONE_IDX ),
                                 c.getInt( RtmSettingsColumns.DATEFORMAT_IDX ),
                                 c.getInt( RtmSettingsColumns.TIMEFORMAT_IDX ),
                                 CursorUtils.getOptString( c,
                                                           RtmSettingsColumns.RTM_DEFAULTLIST_ID_IDX,
                                                           RtmConstants.NO_ID ),
                                 c.getString( RtmSettingsColumns.LANGUAGE_IDX ) );
      }
   }
}
