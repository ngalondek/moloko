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

import android.provider.BaseColumns;


final class Columns
{
   public final static int ID_IDX = 0;
   
   
   public final static class RtmListsColumns implements BaseColumns
   {
      /**
       * Foreign RTM ID
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String RTM_LIST_ID = "rtm_list_id";
      
      public final static int RTM_LIST_ID_IDX = 1;
      
      /**
       * The name of the list
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String LIST_NAME = "list_name";
      
      public final static int LIST_NAME_IDX = 2;
      
      /**
       * The created date of the list, 0 for server created lists
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String LIST_CREATED_DATE = "list_created";
      
      public final static int LIST_CREATED_DATE_IDX = 3;
      
      /**
       * The modified date of the list, 0 for server modified lists
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String LIST_MODIFIED_DATE = "list_modified";
      
      public final static int LIST_MODIFIED_DATE_IDX = 4;
      
      /**
       * Indicates if the list is deleted.
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - no</LI> <LI>!= 0 - yes</LI>
       */
      public final static String LIST_DELETED_DATE = "list_deleted";
      
      public final static int LIST_DELETED_DATE_IDX = 5;
      
      /**
       * Indicates if the list is locked.
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - no</LI> <LI>!= 0 - yes</LI>
       */
      public final static String LOCKED = "locked";
      
      public final static int LOCKED_IDX = 6;
      
      /**
       * Indicates if the list is archived.
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - no</LI> <LI>!= 0 - yes</LI>
       */
      public final static String ARCHIVED = "archived";
      
      public final static int ARCHIVED_IDX = 7;
      
      /**
       * Determines the list ordering
       * <P>
       * Type: INTEGER
       * </P>
       */
      public final static String POSITION = "position";
      
      public final static int POSITION_IDX = 8;
      
      /**
       * Indicates if the list is a smart list
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - no</LI> <LI>!= 0 - yes</LI>
       */
      public final static String IS_SMART_LIST = "smart";
      
      public final static int IS_SMART_LIST_IDX = 9;
      
      /**
       * The smart filer for this list
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String FILTER = "filter";
      
      public final static int FILTER_IDX = 10;
      
      /**
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = POSITION + ", "
         + LIST_NAME;
      
      public final static String[] PROJECTION =
      { _ID, RTM_LIST_ID, LIST_NAME, LIST_CREATED_DATE, LIST_MODIFIED_DATE,
       LIST_DELETED_DATE, LOCKED, ARCHIVED, POSITION, IS_SMART_LIST, FILTER };
   }
   
   
   public final static class RtmNotesColumns implements BaseColumns
   {
      /**
       * Foreign RTM ID
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String RTM_NOTE_ID = "rtm_note_id";
      
      public final static int RTM_NOTE_ID_IDX = 1;
      
      /**
       * The ID of the taskseries referenced.
       * <P>
       * Type: INTEGER (foreign key to table taskseries _ID field)
       * </P>
       */
      public final static String TASKSERIES_ID = "taskseries_id";
      
      public final static int TASKSERIES_ID_IDX = 2;
      
      /**
       * The created date of the note
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String NOTE_CREATED_DATE = "note_created";
      
      public final static int NOTE_CREATED_DATE_IDX = 3;
      
      /**
       * The modified date of the note
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String NOTE_MODIFIED_DATE = "note_modified";
      
      public final static int NOTE_MODIFIED_DATE_IDX = 4;
      
      /**
       * The deleted date of the note
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String NOTE_DELETED_DATE = "note_deleted";
      
      public final static int NOTE_DELETED_DATE_IDX = 5;
      
      /**
       * The title of the note
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String NOTE_TITLE = "note_title";
      
      public final static int NOTE_TITLE_IDX = 6;
      
      /**
       * The text of the note
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String NOTE_TEXT = "note_text";
      
      public final static int NOTE_TEXT_IDX = 7;
      
      /**
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = _ID + " ASC";
      
      public final static String[] PROJECTION =
      { _ID, RTM_NOTE_ID, TASKSERIES_ID, NOTE_CREATED_DATE, NOTE_MODIFIED_DATE,
       NOTE_DELETED_DATE, NOTE_TITLE, NOTE_TEXT };
   }
   
   
   public final static class RtmLocationsColumns implements BaseColumns
   {
      /**
       * Foreign RTM ID
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String RTM_LOCATION_ID = "rtm_location_id";
      
      public final static int RTM_LOCATION_ID_IDX = 1;
      
      /**
       * The name of the location
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String LOCATION_NAME = "location_name";
      
      public final static int LOCATION_NAME_IDX = 2;
      
      /**
       * The longitude of the location
       * <P>
       * Type: FLOAT
       * </P>
       */
      public final static String LONGITUDE = "longitude";
      
      public final static int LONGITUDE_IDX = 3;
      
      /**
       * The latitude of the location
       * <P>
       * Type: FLOAT
       * </P>
       */
      public final static String LATITUDE = "latitude";
      
      public final static int LATITUDE_IDX = 4;
      
      /**
       * The address of the location
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String ADDRESS = "address";
      
      public final static int ADDRESS_IDX = 5;
      
      /**
       * Is the location viewable
       * <P>
       * Type: INTEGER
       * </P>
       * 
       * <UL>
       * <LI>0 - no</LI>
       * <LI>!= 0 - yes</LI>
       * </UL>
       */
      public final static String VIEWABLE = "viewable";
      
      public final static int VIEWABLE_IDX = 6;
      
      /**
       * The zoom of the location
       * <P>
       * Type: INTEGER
       * </P>
       */
      public final static String ZOOM = "zoom";
      
      public final static int ZOOM_IDX = 7;
      
      /**
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = LOCATION_NAME;
      
      public final static String[] PROJECTION =
      { _ID, RTM_LOCATION_ID, LOCATION_NAME, LONGITUDE, LATITUDE, ADDRESS,
       VIEWABLE, ZOOM };
   }
   
   
   public final static class RtmTaskSeriesColumns implements BaseColumns
   {
      /**
       * Foreign RTM ID
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String RTM_TASKSERIES_ID = "rtm_taskseries_id";
      
      public final static int RTM_TASKSERIES_ID_IDX = 1;
      
      /**
       * The created date of the taskseries
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String TASKSERIES_CREATED_DATE = "taskseries_created";
      
      public final static int TASKSERIES_CREATED_DATE_IDX = 2;
      
      /**
       * The modified date of the taskseries
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String TASKSERIES_MODIFIED_DATE = "taskseries_modified";
      
      public final static int TASKSERIES_MODIFIED_DATE_IDX = 3;
      
      /**
       * The name of the taskseries
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String TASKSERIES_NAME = "taskseries_name";
      
      public final static int TASKSERIES_NAME_IDX = 4;
      
      /**
       * The source that entered the taskseries
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String SOURCE = "source";
      
      public final static int SOURCE_IDX = 5;
      
      /**
       * A URL attached to the taskseries
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String URL = "url";
      
      public final static int URL_IDX = 6;
      
      /**
       * A recurrence pattern for the taskseries.
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String RECURRENCE = "recurrence";
      
      public final static int RECURRENCE_IDX = 7;
      
      /**
       * Indicates the type of the recurrence pattern.
       * <P>
       * Type: INTEGER
       * </P>
       * <UI> <LI>0 - indicates an 'after' pattern</LI> <LI>1 - indicates an 'every' pattern</LI> </UI>
       */
      public final static String RECURRENCE_EVERY = "recurrence_every";
      
      public final static int RECURRENCE_EVERY_IDX = 8;
      
      /**
       * The ID of the location of this taskseries.
       * <P>
       * Type: INTEGER (foreign key to table locations _ID field)
       * </P>
       */
      public final static String LOCATION_ID = "location_id";
      
      public final static int LOCATION_ID_IDX = 9;
      
      /**
       * The ID of the list this taskseries is in.
       * <P>
       * Type: TEXT (foreign key to table lists _ID field)
       * </P>
       */
      public final static String LIST_ID = "list_id";
      
      public final static int LIST_ID_IDX = 10;
      
      /**
       * A {@link TAGS_SEPARATOR} separated list of tags.
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String TAGS = "tags";
      
      public final static int TAGS_IDX = 11;
      
      /**
       * The separator used to separate a the tags.
       */
      public final static String TAGS_SEPARATOR = ",";
      
      /**
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = _ID + " ASC";
      
      public final static String[] PROJECTION =
      { _ID, RTM_TASKSERIES_ID, TASKSERIES_CREATED_DATE,
       TASKSERIES_MODIFIED_DATE, TASKSERIES_NAME, SOURCE, URL, RECURRENCE,
       RECURRENCE_EVERY, LOCATION_ID, LIST_ID, TAGS };
   }
   
   
   public final static class RawTasksColumns implements BaseColumns
   {
      /**
       * Foreign RTM ID
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String RTM_RAWTASK_ID = "rtm_rawtask_id";
      
      public final static int RTM_RAWTASK_ID_IDX = 1;
      
      /**
       * The ID of the taskseries referenced.
       * <P>
       * Type: INTEGER (foreign key to table taskseries _ID field)
       * </P>
       */
      public final static String TASKSERIES_ID = "taskseries_id";
      
      public final static int TASKSERIES_ID_IDX = 2;
      
      /**
       * The due date of the task
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String DUE_DATE = "due";
      
      public final static int DUE_IDX = 3;
      
      /**
       * Indicates if the task has an explicit due time and not only a due date
       * <P>
       * Type: INTEGER
       * </P>
       */
      public final static String HAS_DUE_TIME = "has_due_time";
      
      public final static int HAS_DUE_TIME_IDX = 4;
      
      /**
       * The date when the task was added
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String ADDED_DATE = "added";
      
      public final static int ADDED_DATE_IDX = 5;
      
      /**
       * The date when the task has been completed
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String COMPLETED_DATE = "completed";
      
      public final static int COMPLETED_DATE_IDX = 6;
      
      /**
       * The date when the task was deleted
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String DELETED_DATE = "deleted";
      
      public final static int DELETED_DATE_IDX = 7;
      
      /**
       * The task's priority
       * <P>
       * Type: CHAR(1)
       * </P>
       * <LI>'n' - none</LI> <LI>'1' - high</LI> <LI>'2' - medium</LI> <LI>'3' - low</LI>
       */
      public final static String PRIORITY = "priority";
      
      public final static int PRIORITY_IDX = 8;
      
      /**
       * Indicates if the task is postponed
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - no</LI> <LI>!= 0 - yes</LI>
       */
      public final static String POSTPONED = "postponed";
      
      public final static int POSTPONED_IDX = 9;
      
      /**
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String ESTIMATE = "estimate";
      
      public final static int ESTIMATE_IDX = 10;
      
      /**
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String ESTIMATE_MILLIS = "estimateMillis";
      
      public final static int ESTIMATE_MILLIS_IDX = 11;
      
      /**
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = _ID + " ASC";
      
      public final static String[] PROJECTION =
      { _ID, RTM_RAWTASK_ID, TASKSERIES_ID, DUE_DATE, HAS_DUE_TIME, ADDED_DATE,
       COMPLETED_DATE, DELETED_DATE, PRIORITY, POSTPONED, ESTIMATE,
       ESTIMATE_MILLIS };
   }
   
   
   public final static class RtmSettingsColumns implements BaseColumns
   {
      /**
       * The time stamp when the settings were retrieved from the server
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String SYNC_TIMESTAMP = "sync_timestamp";
      
      public final static int SYNC_TIMESTAMP_IDX = 1;
      
      /**
       * The user's Olson timezone. Null if the user has not set a timezone
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String TIMEZONE = "timezone";
      
      public final static int TIMEZONE_IDX = 2;
      
      /**
       * The date format the user has set
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - indicates an European date format (e.g. 14/02/06)</LI> <LI>1 - indicates an American date format (e.g.
       * 02/14/06)</LI>
       */
      public final static String DATEFORMAT = "dateformat";
      
      public final static int DATEFORMAT_IDX = 3;
      
      /**
       * The time format the user has set
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - indicates 12 hour time with day period (e.g. 5pm)</LI> <LI>1 - indicates 24 hour time (e.g. 17:00)</LI>
       */
      public final static String TIMEFORMAT = "timeformat";
      
      public final static int TIMEFORMAT_IDX = 4;
      
      /**
       * The ID of the default list the user set.
       * <P>
       * Type: INTEGER (foreign key to table lists _ID field), NULL if not set.
       * </P>
       */
      public final static String DEFAULTLIST_ID = "defaultlist_id";
      
      public final static int DEFAULTLIST_ID_IDX = 5;
      
      /**
       * The user's language (ISO 639-1 code), NULL is not set
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String LANGUAGE = "language";
      
      public final static int LANGUAGE_IDX = 6;
      
      public final static String[] PROJECTION =
      { _ID, SYNC_TIMESTAMP, TIMEZONE, DATEFORMAT, TIMEFORMAT, DEFAULTLIST_ID,
       LANGUAGE };
   }
   
   
   public final static class SyncColumns implements BaseColumns
   {
      /**
       * The time stamp of the last synchronization RTM -> local
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String LAST_IN = "last_in";
      
      public final static int LAST_IN_IDX = 1;
      
      /**
       * The time stamp of the last synchronization local -> RTM
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String LAST_OUT = "last_out";
      
      public final static int LAST_OUT_IDX = 2;
      
      public final static String[] PROJECTION =
      { _ID, LAST_IN, LAST_OUT };
   }
   
   
   public final static class RtmContactsColumns implements BaseColumns
   {
      /**
       * Foreign RTM ID
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String RTM_CONTACT_ID = "rtm_contact_id";
      
      public final static int RTM_CONTACT_ID_IDX = 1;
      
      /**
       * The full name of the contact
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String FULLNAME = "fullname";
      
      public final static int FULLNAME_IDX = 2;
      
      /**
       * The RTM username of the contact
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String USERNAME = "username";
      
      public final static int USERNAME_IDX = 3;
      
      public final static String DEFAULT_SORT_ORDER = FULLNAME
         + " COLLATE NOCASE";
      
      public final static String[] PROJECTION =
      { _ID, RTM_CONTACT_ID, FULLNAME, USERNAME };
   }
   
   
   public final static class ParticipantsColumns implements BaseColumns
   {
      /**
       * The contact ID of the contact who participates
       * <P>
       * Type: INTEGER (long)
       * </P>
       */
      public final static String CONTACT_ID = "contact_id";
      
      public final static int CONTACT_ID_IDX = 1;
      
      /**
       * The taskseries ID the contact participates in.
       * <P>
       * Type: INTEGER (long)
       * </P>
       */
      public final static String TASKSERIES_ID = "taskseries_id";
      
      public final static int TASKSERIES_ID_IDX = 2;
      
      /**
       * The full name of the contact participates in.
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String FULLNAME = "fullname";
      
      public final static int FULLNAME_IDX = 3;
      
      /**
       * The user name the contact participates in.
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String USERNAME = "username";
      
      public final static int USERNAME_IDX = 4;
      
      public final static String DEFAULT_SORT_ORDER = FULLNAME
         + " COLLATE NOCASE";
      
      public final static String[] PROJECTION =
      { _ID, CONTACT_ID, TASKSERIES_ID, FULLNAME, USERNAME };
   }
   
   
   public final static class ModificationsColumns implements BaseColumns
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
       * The name of the modified column of the entity.
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String COL_NAME = "col_name";
      
      public final static int COL_NAME_IDX = 2;
      
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
       * The point of time when the modification was inserted.
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String TIMESTAMP = "timestamp";
      
      public final static int TIMESTAMP_IDX = 5;
      
      public final static String DEFAULT_SORT_ORDER = null;
      
      public final static String[] PROJECTION =
      { _ID, ENTITY_URI, COL_NAME, NEW_VALUE, SYNCED_VALUE, TIMESTAMP };
   }
   
   
   public final static class CreationsColumns implements BaseColumns
   {
      /**
       * Designates the entity that has been created by it's Content URI with ID.
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String ENTITY_URI = "entity_uri";
      
      public final static int ENTITY_URI_IDX = 1;
      
      /**
       * The point of time when the entity was created.
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String TIMESTAMP = "timestamp";
      
      public final static int TIMESTAMP_IDX = 2;
      
      public final static String DEFAULT_SORT_ORDER = null;
      
      public final static String[] PROJECTION =
      { _ID, ENTITY_URI, TIMESTAMP };
   }
}
