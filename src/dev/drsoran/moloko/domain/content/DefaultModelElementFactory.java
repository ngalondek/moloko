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

package dev.drsoran.moloko.domain.content;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import android.database.Cursor;
import dev.drsoran.moloko.content.Columns;
import dev.drsoran.moloko.content.Columns.ContactColumns;
import dev.drsoran.moloko.content.Columns.LocationColumns;
import dev.drsoran.moloko.content.Columns.ModificationColumns;
import dev.drsoran.moloko.content.Columns.NoteColumns;
import dev.drsoran.moloko.content.Columns.ParticipantColumns;
import dev.drsoran.moloko.content.Columns.RtmSettingsColumns;
import dev.drsoran.moloko.content.Columns.SyncColumns;
import dev.drsoran.moloko.content.Columns.TagColumns;
import dev.drsoran.moloko.content.Columns.TaskColumns;
import dev.drsoran.moloko.content.Columns.TaskCountColumns;
import dev.drsoran.moloko.content.Columns.TasksListColumns;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.CursorUtils;
import dev.drsoran.moloko.domain.model.Contact;
import dev.drsoran.moloko.domain.model.Due;
import dev.drsoran.moloko.domain.model.Estimation;
import dev.drsoran.moloko.domain.model.ExtendedTaskCount;
import dev.drsoran.moloko.domain.model.Location;
import dev.drsoran.moloko.domain.model.Modification;
import dev.drsoran.moloko.domain.model.Note;
import dev.drsoran.moloko.domain.model.Participant;
import dev.drsoran.moloko.domain.model.Priority;
import dev.drsoran.moloko.domain.model.Recurrence;
import dev.drsoran.moloko.domain.model.RtmSettings;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.domain.model.Sync;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.moloko.util.Strings;


public class DefaultModelElementFactory implements IModelElementFactory
{
   private final Map< Class< ? >, IFactoryMethod< ? > > factoryMethodLookUp = new HashMap< Class< ? >, IFactoryMethod< ? > >();
   
   
   
   public DefaultModelElementFactory()
   {
      factoryMethodLookUp.put( TasksList.class, new TasksListFactoryMethod() );
      factoryMethodLookUp.put( Task.class, new TaskFactoryMethod() );
      factoryMethodLookUp.put( Note.class, new NoteFactoryMethod() );
      factoryMethodLookUp.put( Participant.class,
                               new ParticipantFactoryMethod() );
      factoryMethodLookUp.put( Contact.class, new ContactFactoryMethod() );
      factoryMethodLookUp.put( Location.class, new LocationFactoryMethod() );
      factoryMethodLookUp.put( ExtendedTaskCount.class,
                               new ExtendedTaskCountFactoryMethod() );
      factoryMethodLookUp.put( String.class, new TagFactoryMethod() );
      factoryMethodLookUp.put( RtmSettings.class,
                               new RtmSettingsFactoryMethod() );
      factoryMethodLookUp.put( Modification.class,
                               new ModificationFactoryMethod() );
      factoryMethodLookUp.put( Sync.class, new SyncFactoryMethod() );
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
   
   
   private final class TasksListFactoryMethod implements
            IFactoryMethod< TasksList >
   {
      @Override
      public TasksList create( Cursor c )
      {
         final TasksList tasksList = new TasksList( c.getLong( Columns.ID_IDX ),
                                                    c.getLong( TasksListColumns.LIST_CREATED_DATE_IDX ),
                                                    c.getString( TasksListColumns.LIST_NAME_IDX ),
                                                    c.getInt( TasksListColumns.POSITION_IDX ),
                                                    c.getInt( TasksListColumns.LOCKED_IDX ) != 0,
                                                    c.getInt( TasksListColumns.ARCHIVED_IDX ) != 0 );
         
         tasksList.setModifiedMillisUtc( c.getLong( TasksListColumns.LIST_MODIFIED_DATE_IDX ) );
         tasksList.setDeletedMillisUtc( CursorUtils.getOptLong( c,
                                                                TasksListColumns.LIST_DELETED_DATE_IDX,
                                                                Constants.NO_TIME ) );
         
         if ( c.getInt( TasksListColumns.IS_SMART_LIST_IDX ) != 0 )
         {
            tasksList.setSmartFilter( new RtmSmartFilter( c.getString( TasksListColumns.FILTER_IDX ) ) );
         }
         
         return tasksList;
      }
   }
   
   
   private final class TaskFactoryMethod implements IFactoryMethod< Task >
   {
      @Override
      public Task create( Cursor c )
      {
         final Task task = new Task( c.getLong( Columns.ID_IDX ),
                                     c.getLong( TaskColumns.TASK_CREATED_DATE_IDX ),
                                     c.getLong( TaskColumns.ADDED_DATE_IDX ),
                                     c.getString( TaskColumns.TASK_NAME_IDX ),
                                     c.getLong( TaskColumns.LIST_ID_IDX ),
                                     c.getString( TaskColumns.LIST_NAME_IDX ) );
         
         task.setLocation( CursorUtils.getOptLong( c,
                                                   TaskColumns.LOCATION_ID_IDX,
                                                   Constants.NO_ID ),
                           CursorUtils.getOptString( c,
                                                     TaskColumns.LOCATION_NAME_IDX ) );
         task.setModifiedMillisUtc( c.getLong( TaskColumns.TASK_MODIFIED_DATE_IDX ) );
         task.setSource( Strings.emptyIfNull( CursorUtils.getOptString( c,
                                                                        TaskColumns.SOURCE_IDX ) ) );
         task.setUrl( Strings.emptyIfNull( CursorUtils.getOptString( c,
                                                                     TaskColumns.URL_IDX ) ) );
         task.setDeletedMillisUtc( CursorUtils.getOptLong( c,
                                                           TaskColumns.DELETED_DATE_IDX,
                                                           Constants.NO_TIME ) );
         task.setCompletedMillisUtc( CursorUtils.getOptLong( c,
                                                             TaskColumns.COMPLETED_DATE_IDX,
                                                             Constants.NO_TIME ) );
         task.setPriority( Priority.fromString( c.getString( TaskColumns.PRIORITY_IDX ) ) );
         task.setPostponedCount( c.getInt( TaskColumns.POSTPONED_IDX ) );
         
         if ( !c.isNull( TaskColumns.TAGS_IDX ) )
         {
            final String[] tags = c.getString( TaskColumns.TAGS_IDX )
                                   .split( TaskColumns.TAGS_SEPARATOR, -1 );
            task.setTags( Arrays.asList( tags ) );
         }
         
         if ( !c.isNull( TaskColumns.RECURRENCE_IDX ) )
         {
            task.setRecurrence( new Recurrence( c.getString( TaskColumns.RECURRENCE_IDX ),
                                                c.getInt( TaskColumns.RECURRENCE_EVERY_IDX ) != 0 ) );
         }
         
         if ( !c.isNull( TaskColumns.DUE_IDX ) )
         {
            task.setDue( new Due( c.getLong( TaskColumns.DUE_IDX ),
                                  c.getInt( TaskColumns.HAS_DUE_TIME_IDX ) != 0 ) );
         }
         
         if ( !c.isNull( TaskColumns.ESTIMATE_IDX ) )
         {
            task.setEstimation( new Estimation( c.getString( TaskColumns.ESTIMATE_IDX ),
                                                c.getLong( TaskColumns.ESTIMATE_MILLIS_IDX ) ) );
         }
         
         return task;
      }
   }
   
   
   private final class NoteFactoryMethod implements IFactoryMethod< Note >
   {
      @Override
      public Note create( Cursor c )
      {
         final Note note = new Note( c.getLong( Columns.ID_IDX ),
                                     c.getLong( NoteColumns.NOTE_CREATED_DATE_IDX ) );
         
         note.setModifiedMillisUtc( c.getLong( NoteColumns.NOTE_MODIFIED_DATE_IDX ) );
         note.setDeletedMillisUtc( CursorUtils.getOptLong( c,
                                                           NoteColumns.NOTE_DELETED_DATE_IDX,
                                                           Constants.NO_TIME ) );
         note.setTitle( Strings.emptyIfNull( CursorUtils.getOptString( c,
                                                                       NoteColumns.NOTE_TITLE_IDX ) ) );
         note.setText( c.getString( NoteColumns.NOTE_TEXT_IDX ) );
         
         return note;
      }
   }
   
   
   private final class ParticipantFactoryMethod implements
            IFactoryMethod< Participant >
   {
      @Override
      public Participant create( Cursor c )
      {
         final Participant participant = new Participant( c.getLong( Columns.ID_IDX ),
                                                          c.getLong( ParticipantColumns.CONTACT_ID_IDX ),
                                                          c.getString( ParticipantColumns.FULLNAME_IDX ),
                                                          c.getString( ParticipantColumns.USERNAME_IDX ) );
         return participant;
      }
   }
   
   
   private final class ContactFactoryMethod implements IFactoryMethod< Contact >
   {
      @Override
      public Contact create( Cursor c )
      {
         return new Contact( c.getLong( Columns.ID_IDX ),
                             c.getString( ContactColumns.USERNAME_IDX ),
                             c.getString( ContactColumns.FULLNAME_IDX ) );
      }
   }
   
   
   private final class LocationFactoryMethod implements
            IFactoryMethod< Location >
   {
      @Override
      public Location create( Cursor c )
      {
         return new Location( c.getLong( Columns.ID_IDX ),
                              c.getString( LocationColumns.LOCATION_NAME_IDX ),
                              c.getFloat( LocationColumns.LONGITUDE_IDX ),
                              c.getFloat( LocationColumns.LATITUDE_IDX ),
                              CursorUtils.getOptString( c,
                                                        LocationColumns.ADDRESS_IDX ),
                              c.getInt( LocationColumns.VIEWABLE_IDX ) != 0,
                              c.getInt( LocationColumns.ZOOM_IDX ) );
      }
   }
   
   
   private final class TagFactoryMethod implements IFactoryMethod< String >
   {
      @Override
      public String create( Cursor c )
      {
         return c.getString( TagColumns.TAG_IDX );
      }
   }
   
   
   private final class ExtendedTaskCountFactoryMethod implements
            IFactoryMethod< ExtendedTaskCount >
   {
      @Override
      public ExtendedTaskCount create( Cursor c )
      {
         return new ExtendedTaskCount( c.getInt( TaskCountColumns.INCOMPLETE_IDX ),
                                       c.getInt( TaskCountColumns.COMPLETED_IDX ),
                                       c.getInt( TaskCountColumns.DUE_TODAY_IDX ),
                                       c.getInt( TaskCountColumns.DUE_TOMORROW_IDX ),
                                       c.getInt( TaskCountColumns.OVERDUE_IDX ),
                                       c.getLong( TaskCountColumns.SUM_ESTIMATED_IDX ) );
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
                                 CursorUtils.getOptLong( c,
                                                         RtmSettingsColumns.DEFAULTLIST_ID_IDX,
                                                         Constants.NO_ID ),
                                 c.getString( RtmSettingsColumns.LANGUAGE_IDX ) );
      }
   }
   
   
   private final class ModificationFactoryMethod implements
            IFactoryMethod< Modification >
   {
      @Override
      public Modification create( Cursor c )
      {
         return Modification.newModification( c.getString( ModificationColumns.ENTITY_URI_IDX ),
                                              c.getString( ModificationColumns.COL_NAME_IDX ),
                                              c.getString( ModificationColumns.NEW_VALUE_IDX ),
                                              CursorUtils.getOptString( c,
                                                                        ModificationColumns.SYNCED_VALUE_IDX ),
                                              c.getLong( ModificationColumns.TIMESTAMP_IDX ) );
      }
   }
   
   
   private final class SyncFactoryMethod implements IFactoryMethod< Sync >
   {
      @Override
      public Sync create( Cursor c )
      {
         return new Sync( CursorUtils.getOptLong( c,
                                                  SyncColumns.LAST_IN_IDX,
                                                  Constants.NO_TIME ),
                          CursorUtils.getOptLong( c,
                                                  SyncColumns.LAST_OUT_IDX,
                                                  Constants.NO_TIME ) );
      }
   }
}
