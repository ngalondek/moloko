/*
 * Copyright (c) 2010 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.content.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.provider.BaseColumns;
import dev.drsoran.moloko.content.Columns.LocationColumns;
import dev.drsoran.moloko.content.Columns.NoteColumns;
import dev.drsoran.moloko.content.Columns.ParticipantColumns;
import dev.drsoran.moloko.content.Columns.SettingsColumns;
import dev.drsoran.moloko.content.Columns.TaskColumns;
import dev.drsoran.moloko.content.Columns.TasksListColumns;
import dev.drsoran.rtm.content.ContentProperties;


public final class TableColumns
{
   public final static class RtmTasksListColumns extends TasksListColumns
   {
      public final static String RTM_LIST_ID = "rtm_list_id";
      
      public final static int RTM_LIST_ID_IDX = TasksListColumns.PROJECTION.length;
      
      static
      {
         final List< String > baseProjection = new ArrayList< String >( Arrays.asList( TasksListColumns.PROJECTION ) );
         baseProjection.add( RTM_LIST_ID );
         
         RtmTasksListColumns.TABLE_PROJECTION = baseProjection.toArray( new String[ baseProjection.size() ] );
      }
      
      public static String[] TABLE_PROJECTION;
   }
   
   
   public final static class RtmTaskSeriesColumns implements BaseColumns
   {
      public final static String TASKSERIES_CREATED_DATE = TaskColumns.TASK_CREATED_DATE;
      
      public final static int TASKSERIES_CREATED_DATE_IDX = 1;
      
      public final static String TASKSERIES_MODIFIED_DATE = TaskColumns.TASK_MODIFIED_DATE;
      
      public final static int TASKSERIES_MODIFIED_DATE_IDX = 2;
      
      public final static String TASKSERIES_NAME = TaskColumns.TASK_NAME;
      
      public final static int TASKSERIES_NAME_IDX = 3;
      
      public final static String SOURCE = TaskColumns.SOURCE;
      
      public final static int SOURCE_IDX = 4;
      
      public final static String URL = TaskColumns.URL;
      
      public final static int URL_IDX = 5;
      
      public final static String RECURRENCE = TaskColumns.RECURRENCE;
      
      public final static int RECURRENCE_IDX = 6;
      
      public final static String RECURRENCE_EVERY = TaskColumns.RECURRENCE_EVERY;
      
      public final static int RECURRENCE_EVERY_IDX = 7;
      
      public final static String LOCATION_ID = TaskColumns.LOCATION_ID;
      
      public final static int LOCATION_ID_IDX = 8;
      
      public final static String LIST_ID = TaskColumns.LIST_ID;
      
      public final static int LIST_ID_IDX = 9;
      
      public final static String TAGS = TaskColumns.TAGS;
      
      public final static int TAGS_IDX = 10;
      
      public final static String RTM_TASKSERIES_ID = "rtm_taskseries_id";
      
      public final static int RTM_TASKSERIES_ID_IDX = 11;
      
      public final static String RTM_LIST_ID = "rtm_list_id";
      
      public final static int RTM_LIST_ID_IDX = 12;
      
      public final static String RTM_LOCATION_ID = "rtm_location_id";
      
      public final static int RTM_LOCATION_ID_IDX = 13;
      
      public final static String TAGS_SEPARATOR = TaskColumns.TAGS_SEPARATOR;
      
      public final static String DEFAULT_SORT_ORDER = TaskColumns.DEFAULT_SORT_ORDER;
      
      public final static String[] TABLE_PROJECTION =
      { _ID, TASKSERIES_CREATED_DATE, TASKSERIES_MODIFIED_DATE,
       TASKSERIES_NAME, SOURCE, URL, RECURRENCE, RECURRENCE_EVERY, LOCATION_ID,
       LIST_ID, TAGS, RTM_TASKSERIES_ID, RTM_LIST_ID, RTM_LOCATION_ID };
   }
   
   
   public final static class RtmRawTaskColumns implements BaseColumns
   {
      public final static String DUE_DATE = TaskColumns.DUE_DATE;
      
      public final static int DUE_IDX = 1;
      
      public final static String HAS_DUE_TIME = TaskColumns.HAS_DUE_TIME;
      
      public final static int HAS_DUE_TIME_IDX = 2;
      
      public final static String ADDED_DATE = TaskColumns.ADDED_DATE;
      
      public final static int ADDED_DATE_IDX = 3;
      
      public final static String COMPLETED_DATE = TaskColumns.COMPLETED_DATE;
      
      public final static int COMPLETED_DATE_IDX = 4;
      
      public final static String DELETED_DATE = TaskColumns.DELETED_DATE;
      
      public final static int DELETED_DATE_IDX = 5;
      
      public final static String PRIORITY = TaskColumns.PRIORITY;
      
      public final static int PRIORITY_IDX = 6;
      
      public final static String POSTPONED = TaskColumns.POSTPONED;
      
      public final static int POSTPONED_IDX = 7;
      
      public final static String ESTIMATE = TaskColumns.ESTIMATE;
      
      public final static int ESTIMATE_IDX = 8;
      
      public final static String ESTIMATE_MILLIS = TaskColumns.ESTIMATE_MILLIS;
      
      public final static int ESTIMATE_MILLIS_IDX = 9;
      
      public final static String RTM_RAWTASK_ID = "rtm_rawtask_id";
      
      public final static int RTM_RAWTASK_ID_IDX = 10;
      
      public final static String TASKSERIES_ID = "taskseries_id";
      
      public final static int TASKSERIES_ID_IDX = 11;
      
      public final static String DEFAULT_SORT_ORDER = TaskColumns.DEFAULT_SORT_ORDER;
      
      public final static String[] TABLE_PROJECTION =
      { _ID, DUE_DATE, HAS_DUE_TIME, ADDED_DATE, COMPLETED_DATE, DELETED_DATE,
       PRIORITY, POSTPONED, ESTIMATE, ESTIMATE_MILLIS, RTM_RAWTASK_ID,
       TASKSERIES_ID };
   }
   
   
   public final static class RtmNoteColumns extends NoteColumns
   {
      public final static String TASKSERIES_ID = "taskseries_id";
      
      public final static int TASKSERIES_ID_IDX = NoteColumns.PROJECTION.length;
      
      public final static String RTM_NOTE_ID = "rtm_note_id";
      
      public final static int RTM_NOTE_ID_IDX = NoteColumns.PROJECTION.length + 1;
      
      static
      {
         final List< String > baseProjection = new ArrayList< String >( Arrays.asList( NoteColumns.PROJECTION ) );
         baseProjection.add( TASKSERIES_ID );
         baseProjection.add( RTM_NOTE_ID );
         
         RtmNoteColumns.TABLE_PROJECTION = baseProjection.toArray( new String[ baseProjection.size() ] );
      }
      
      public static String[] TABLE_PROJECTION;
   }
   
   
   public final static class RtmContactColumns implements BaseColumns
   {
      public final static String FULLNAME = "fullname";
      
      public final static int FULLNAME_IDX = 1;
      
      public final static String USERNAME = "username";
      
      public final static int USERNAME_IDX = 2;
      
      public final static String RTM_CONTACT_ID = "rtm_contact_id";
      
      public final static int RTM_CONTACT_ID_IDX = 3;
      
      public final static String DEFAULT_SORT_ORDER = FULLNAME
         + " COLLATE NOCASE";
      
      public static String[] TABLE_PROJECTION = new String[]
      { _ID, FULLNAME, USERNAME, RTM_CONTACT_ID };
   }
   
   
   public final static class RtmParticipantColumns extends ParticipantColumns
   {
      public final static String TASKSERIES_ID = "taskseries_id";
      
      public final static int TASKSERIES_ID_IDX = ParticipantColumns.PROJECTION.length;
      
      /**
       * The contact ID of the contact who participates
       * <P>
       * Type: INTEGER (long)
       * </P>
       */
      public final static String RTM_CONTACT_ID = "rtm_contact_id";
      
      public final static int RTM_CONTACT_ID_IDX = TASKSERIES_ID_IDX + 1;
      
      static
      {
         final List< String > baseProjection = new ArrayList< String >( Arrays.asList( ParticipantColumns.PROJECTION ) );
         baseProjection.add( TASKSERIES_ID );
         baseProjection.add( RTM_CONTACT_ID );
         
         RtmParticipantColumns.TABLE_PROJECTION = baseProjection.toArray( new String[ baseProjection.size() ] );
      }
      
      public static String[] TABLE_PROJECTION;
   }
   
   
   public final static class RtmLocationColumns extends LocationColumns
   {
      public final static String RTM_LOCATION_ID = "rtm_location_id";
      
      public final static int RTM_LOCATION_ID_IDX = LocationColumns.PROJECTION.length;
      
      static
      {
         final List< String > baseProjection = new ArrayList< String >( Arrays.asList( LocationColumns.PROJECTION ) );
         baseProjection.add( RTM_LOCATION_ID );
         
         RtmLocationColumns.TABLE_PROJECTION = baseProjection.toArray( new String[ baseProjection.size() ] );
      }
      
      public static String[] TABLE_PROJECTION;
   }
   
   
   public final static class RtmSettingsColumns extends SettingsColumns
   {
      public final static String RTM_DEFAULTLIST_ID = "rtm_defaultlist_id";
      
      public final static int RTM_DEFAULTLIST_ID_IDX = SettingsColumns.PROJECTION.length;
      
      static
      {
         final List< String > baseProjection = new ArrayList< String >( Arrays.asList( SettingsColumns.PROJECTION ) );
         baseProjection.add( RTM_DEFAULTLIST_ID );
         
         RtmSettingsColumns.TABLE_PROJECTION = baseProjection.toArray( new String[ baseProjection.size() ] );
      }
      
      public static String[] TABLE_PROJECTION;
   }
   
   
   public static class ModificationColumns implements BaseColumns
   {
      /**
       * Designates the entity that has been modified by it's Content URI with ID.
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String ENTITY_URI = "entity_uri";
      
      public final static int ENTITY_URI_IDX = 1;
      
      /**
       * The name of the modified property of the entity.
       * <P>
       * Type: TEXT
       * </P>
       * 
       * @see ContentProperties
       */
      public final static String PROPERTY = "property";
      
      public final static int PROPERTY_IDX = 2;
      
      /**
       * The new value.
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String NEW_VALUE = "new_value";
      
      public final static int NEW_VALUE_IDX = 3;
      
      /**
       * The last synchronized value with the RTM server.
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String SYNCED_VALUE = "synced_value";
      
      public final static int SYNCED_VALUE_IDX = 4;
      
      /**
       * The point in time when the modification was inserted.
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String TIMESTAMP = "timestamp";
      
      public final static int TIMESTAMP_IDX = 5;
      
      public final static String DEFAULT_SORT_ORDER = null;
      
      public final static String[] PROJECTION =
      { _ID, ENTITY_URI, PROPERTY, NEW_VALUE, SYNCED_VALUE, TIMESTAMP };
   }
}
