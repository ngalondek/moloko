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

package dev.drsoran.moloko.content;

import android.provider.BaseColumns;


public final class Columns
{
   public final static int ID_IDX = 0;
   
   
   public static class TasksListColumns implements BaseColumns
   {
      /**
       * The name of the list
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String LIST_NAME = "list_name";
      
      public final static int LIST_NAME_IDX = 1;
      
      /**
       * The created date of the list, 0 for server created lists
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String LIST_CREATED_DATE = "list_created";
      
      public final static int LIST_CREATED_DATE_IDX = 2;
      
      /**
       * The modified date of the list, 0 for server modified lists
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String LIST_MODIFIED_DATE = "list_modified";
      
      public final static int LIST_MODIFIED_DATE_IDX = 3;
      
      /**
       * Indicates if the list is deleted.
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - no</LI> <LI>!= 0 - yes</LI>
       */
      public final static String LIST_DELETED_DATE = "list_deleted";
      
      public final static int LIST_DELETED_DATE_IDX = 4;
      
      /**
       * Indicates if the list is locked.
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - no</LI> <LI>!= 0 - yes</LI>
       */
      public final static String LOCKED = "locked";
      
      public final static int LOCKED_IDX = 5;
      
      /**
       * Indicates if the list is archived.
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - no</LI> <LI>!= 0 - yes</LI>
       */
      public final static String ARCHIVED = "archived";
      
      public final static int ARCHIVED_IDX = 6;
      
      /**
       * Determines the list ordering
       * <P>
       * Type: INTEGER
       * </P>
       */
      public final static String POSITION = "position";
      
      public final static int POSITION_IDX = 7;
      
      /**
       * Indicates if the list is a smart list
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - no</LI> <LI>!= 0 - yes</LI>
       */
      public final static String IS_SMART_LIST = "smart";
      
      public final static int IS_SMART_LIST_IDX = 8;
      
      /**
       * The smart filer for this list
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String FILTER = "filter";
      
      public final static int FILTER_IDX = 9;
      
      /**
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = POSITION + ", "
         + LIST_NAME;
      
      public final static String[] PROJECTION =
      { _ID, LIST_NAME, LIST_CREATED_DATE, LIST_MODIFIED_DATE,
       LIST_DELETED_DATE, LOCKED, ARCHIVED, POSITION, IS_SMART_LIST, FILTER };
   }
   
   
   public static class TaskColumns implements BaseColumns
   {
      /**
       * The created date of the task
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String TASK_CREATED_DATE = "task_created";
      
      public final static int TASK_CREATED_DATE_IDX = 1;
      
      /**
       * The modified date of the task
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String TASK_MODIFIED_DATE = "task_modified";
      
      public final static int TASK_MODIFIED_DATE_IDX = 2;
      
      /**
       * The name of the task
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String TASK_NAME = "task_name";
      
      public final static int TASK_NAME_IDX = 3;
      
      /**
       * The source that entered the task
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String SOURCE = "source";
      
      public final static int SOURCE_IDX = 4;
      
      /**
       * A URL attached to the task
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String URL = "url";
      
      public final static int URL_IDX = 5;
      
      /**
       * A recurrence pattern for the task.
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String RECURRENCE = "recurrence";
      
      public final static int RECURRENCE_IDX = 6;
      
      /**
       * Indicates the type of the recurrence pattern.
       * <P>
       * Type: INTEGER
       * </P>
       * <UI> <LI>0 - indicates an 'after' pattern</LI> <LI>1 - indicates an 'every' pattern</LI> </UI>
       */
      public final static String RECURRENCE_EVERY = "recurrence_every";
      
      public final static int RECURRENCE_EVERY_IDX = 7;
      
      /**
       * The ID of the location of this task.
       * <P>
       * Type: INTEGER
       * </P>
       */
      public final static String LOCATION_ID = "location_id";
      
      public final static int LOCATION_ID_IDX = 8;
      
      /**
       * The name of the location of this task.
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String LOCATION_NAME = "location_name";
      
      public final static int LOCATION_NAME_IDX = 9;
      
      /**
       * The ID of the list this task is in.
       * <P>
       * Type: INTEGER (long)
       * </P>
       */
      public final static String LIST_ID = "list_id";
      
      public final static int LIST_ID_IDX = 10;
      
      /**
       * The name of the list this task is in.
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String LIST_NAME = "list_name";
      
      public final static int LIST_NAME_IDX = 11;
      
      /**
       * A {@link TAGS_SEPARATOR} separated list of tags.
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String TAGS = "tags";
      
      public final static int TAGS_IDX = 12;
      
      /**
       * The separator used to separate a the tags.
       */
      public final static String TAGS_SEPARATOR = ",";
      
      /**
       * The due date of the task
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String DUE_DATE = "due";
      
      public final static int DUE_IDX = 13;
      
      /**
       * Indicates if the task has an explicit due time and not only a due date
       * <P>
       * Type: INTEGER
       * </P>
       */
      public final static String HAS_DUE_TIME = "has_due_time";
      
      public final static int HAS_DUE_TIME_IDX = 14;
      
      /**
       * The date when the task was added
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String ADDED_DATE = "added";
      
      public final static int ADDED_DATE_IDX = 15;
      
      /**
       * The date when the task has been completed
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String COMPLETED_DATE = "completed";
      
      public final static int COMPLETED_DATE_IDX = 16;
      
      /**
       * The date when the task was deleted
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String DELETED_DATE = "deleted";
      
      public final static int DELETED_DATE_IDX = 17;
      
      /**
       * The task's priority
       * <P>
       * Type: CHAR(1)
       * </P>
       * <LI>'n' - none</LI> <LI>'1' - high</LI> <LI>'2' - medium</LI> <LI>'3' - low</LI>
       */
      public final static String PRIORITY = "priority";
      
      public final static int PRIORITY_IDX = 18;
      
      /**
       * Indicates if the task is postponed
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - no</LI> <LI>!= 0 - yes</LI>
       */
      public final static String POSTPONED = "postponed";
      
      public final static int POSTPONED_IDX = 19;
      
      /**
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String ESTIMATE = "estimate";
      
      public final static int ESTIMATE_IDX = 20;
      
      /**
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String ESTIMATE_MILLIS = "estimateMillis";
      
      public final static int ESTIMATE_MILLIS_IDX = 21;
      
      /**
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = _ID + " ASC";
      
      public final static String[] PROJECTION =
      { _ID, TASK_CREATED_DATE, TASK_MODIFIED_DATE, TASK_NAME, SOURCE, URL,
       RECURRENCE, RECURRENCE_EVERY, LOCATION_ID, LOCATION_NAME, LIST_ID,
       LIST_NAME, TAGS, DUE_DATE, HAS_DUE_TIME, ADDED_DATE, COMPLETED_DATE,
       DELETED_DATE, PRIORITY, POSTPONED, ESTIMATE, ESTIMATE_MILLIS };
   }
   
   
   public static class NoteColumns implements BaseColumns
   {
      /**
       * The created date of the note
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String NOTE_CREATED_DATE = "note_created";
      
      public final static int NOTE_CREATED_DATE_IDX = 1;
      
      /**
       * The modified date of the note
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String NOTE_MODIFIED_DATE = "note_modified";
      
      public final static int NOTE_MODIFIED_DATE_IDX = 2;
      
      /**
       * The deleted date of the note
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String NOTE_DELETED_DATE = "note_deleted";
      
      public final static int NOTE_DELETED_DATE_IDX = 3;
      
      /**
       * The title of the note
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String NOTE_TITLE = "note_title";
      
      public final static int NOTE_TITLE_IDX = 4;
      
      /**
       * The text of the note
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String NOTE_TEXT = "note_text";
      
      public final static int NOTE_TEXT_IDX = 5;
      
      /**
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = _ID + " ASC";
      
      public final static String[] PROJECTION =
      { _ID, NOTE_CREATED_DATE, NOTE_MODIFIED_DATE, NOTE_DELETED_DATE,
       NOTE_TITLE, NOTE_TEXT };
   }
   
   
   public static class ContactColumns implements BaseColumns
   {
      /**
       * The full name of the contact
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String FULLNAME = "fullname";
      
      public final static int FULLNAME_IDX = 1;
      
      /**
       * The RTM username of the contact
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String USERNAME = "username";
      
      public final static int USERNAME_IDX = 2;
      
      /**
       * The number of tasks the Contact participates
       * <P>
       * Type: INTEGER
       * </P>
       */
      public final static String NUM_TASKS_PARTICIPATING = "num_tasks_participates";
      
      public final static int NUM_TASKS_PARTICIPATING_IDX = 3;
      
      public final static String DEFAULT_SORT_ORDER = FULLNAME
         + " COLLATE NOCASE";
      
      public final static String[] PROJECTION =
      { _ID, FULLNAME, USERNAME, NUM_TASKS_PARTICIPATING };
   }
   
   
   public static class ParticipantColumns implements BaseColumns
   {
      /**
       * The full name of the contact participates in.
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String FULLNAME = "fullname";
      
      public final static int FULLNAME_IDX = 1;
      
      /**
       * The user name the contact participates in.
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String USERNAME = "username";
      
      public final static int USERNAME_IDX = 2;
      
      public final static String DEFAULT_SORT_ORDER = FULLNAME
         + " COLLATE NOCASE";
      
      public final static String[] PROJECTION =
      { _ID, FULLNAME, USERNAME };
   }
   
   
   public static class LocationColumns implements BaseColumns
   {
      /**
       * The name of the location
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String LOCATION_NAME = "location_name";
      
      public final static int LOCATION_NAME_IDX = 1;
      
      /**
       * The longitude of the location
       * <P>
       * Type: FLOAT
       * </P>
       */
      public final static String LONGITUDE = "longitude";
      
      public final static int LONGITUDE_IDX = 2;
      
      /**
       * The latitude of the location
       * <P>
       * Type: FLOAT
       * </P>
       */
      public final static String LATITUDE = "latitude";
      
      public final static int LATITUDE_IDX = 3;
      
      /**
       * The address of the location
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String ADDRESS = "address";
      
      public final static int ADDRESS_IDX = 4;
      
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
      
      public final static int VIEWABLE_IDX = 5;
      
      /**
       * The zoom of the location
       * <P>
       * Type: INTEGER
       * </P>
       */
      public final static String ZOOM = "zoom";
      
      public final static int ZOOM_IDX = 6;
      
      /**
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = LOCATION_NAME;
      
      public final static String[] PROJECTION =
      { _ID, LOCATION_NAME, LONGITUDE, LATITUDE, ADDRESS, VIEWABLE, ZOOM };
   }
   
   
   public static class TagColumns
   {
      /**
       * The tag itself
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String TAG = "tag";
      
      public final static int TAG_IDX = 0;
      
      public final static String[] PROJECTION =
      { TAG };
   }
   
   
   public static class TaskCountColumns
   {
      /**
       * The count of incomplete tasks
       * <P>
       * Type: INTEGER
       * </P>
       */
      public final static String INCOMPLETE = "incomplete";
      
      public final static int INCOMPLETE_IDX = 0;
      
      /**
       * The count of completed tasks
       * <P>
       * Type: INTEGER
       * </P>
       */
      public final static String COMPLETED = "completed";
      
      public final static int COMPLETED_IDX = 1;
      
      /**
       * The count of tasks that due today
       * <P>
       * Type: INTEGER
       * </P>
       */
      public final static String DUE_TODAY = "due_today";
      
      public final static int DUE_TODAY_IDX = 2;
      
      /**
       * The count of tasks that due tomorrow
       * <P>
       * Type: INTEGER
       * </P>
       */
      public final static String DUE_TOMORROW = "due_tomorrow";
      
      public final static int DUE_TOMORROW_IDX = 3;
      
      /**
       * The count of tasks overdue
       * <P>
       * Type: INTEGER
       * </P>
       */
      public final static String OVERDUE = "overdue";
      
      public final static int OVERDUE_IDX = 4;
      
      /**
       * The sum of estimation milliseconds
       * <P>
       * Type: INTEGER (long)
       * </P>
       */
      public final static String SUM_ESTIMATED = "sum_estimated";
      
      public final static int SUM_ESTIMATED_IDX = 5;
      
      public final static String[] PROJECTION =
      { INCOMPLETE, COMPLETED, DUE_TODAY, DUE_TOMORROW, OVERDUE, SUM_ESTIMATED };
   }
   
   
   public static class CloudEntryColumns
   {
      /**
       * The type of the cloud entry
       * <P>
       * Type: INTEGER
       * </P>
       */
      public final static String ENTRY_TYPE = "entry_type";
      
      public final static int ENTRY_TYPE_IDX = 0;
      
      /**
       * The display text of the element
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String DISPLAY = "display";
      
      public final static int DISPLAY_IDX = 1;
      
      /**
       * The count how often this element exists
       * <P>
       * Type: INTEGER
       * </P>
       */
      public final static String COUNT = "count";
      
      public final static int COUNT_IDX = 2;
      
      /**
       * The optional ID of the element this cloud entry stands for.
       * <P>
       * Type: INTEGER (long)
       * </P>
       */
      public final static String ELEMENT_ID = "element_id";
      
      public final static int ELEMENT_ID_IDX = 3;
      
      public final static String[] PROJECTION =
      { ENTRY_TYPE, DISPLAY, COUNT, ELEMENT_ID };
   }
   
   
   public static class SettingsColumns implements BaseColumns
   {
      public final static long SINGLETON_ID = 1L;
      
      /**
       * The time stamp when the settings were retrieved from the server
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String SYNC_TIMESTAMP = "sync_timestamp";
      
      public final static int SYNC_TIMESTAMP_IDX = 1;
      
      /**
       * The user's timezone. Null if the user has not set a timezone
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
       * Type: INTEGER (long), NULL if not set.
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
   
   
   public static class SyncTimesColumns implements BaseColumns
   {
      public final static long SINGLETON_ID = 1L;
      
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
}
