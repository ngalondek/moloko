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

package dev.drsoran.provider;

import android.net.Uri;
import android.provider.BaseColumns;


@Deprecated
class Rtm
{
   public static final String AUTHORITY = "dev.drsoran.provider.Rtm";
   
   
   protected static interface TasksListColumns
   {
      /**
       * The name of the list
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String LIST_NAME = "list_name";
      
      /**
       * The created date of the list, 0 for server created lists
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String CREATED_DATE = "list_created";
      
      /**
       * The modified date of the list, 0 for server modified lists
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String LIST_MODIFIED_DATE = "list_modified";
      
      /**
       * Indicates if the list is deleted.
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - no</LI> <LI>!= 0 - yes</LI>
       */
      public final static String LIST_DELETED = "list_deleted";
      
      /**
       * Indicates if the list is locked.
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - no</LI> <LI>!= 0 - yes</LI>
       */
      public final static String LOCKED = "locked";
      
      /**
       * Indicates if the list is archived.
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - no</LI> <LI>!= 0 - yes</LI>
       */
      public final static String ARCHIVED = "archived";
      
      /**
       * Determines the list ordering
       * <P>
       * Type: INTEGER
       * </P>
       */
      public final static String POSITION = "position";
      
      /**
       * Indicates if the list is a smart list
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - no</LI> <LI>!= 0 - yes</LI>
       */
      public final static String IS_SMART_LIST = "smart";
      
      /**
       * The smart filer for this list
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String FILTER = "filter";
   }
   
   
   public final static class TasksLists implements BaseColumns,
            TasksListColumns
   {
      public final static String PATH = "lists";
      
      /**
       * The content:// style URL for this table
       */
      public final static Uri CONTENT_URI = Uri.parse( "content://" + AUTHORITY
         + "/" + PATH );
      
      /**
       * The MIME type of {@link #CONTENT_URI} providing a directory of lists.
       */
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.rtm.list";
      
      /**
       * The MIME type of a {@link #CONTENT_URI} sub-directory of a single list.
       */
      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rtm.list";
      
      /**
       * The number of tasks in the list
       * <P>
       * Type: INTEGER
       * </P>
       */
      public final static String TASKS_COUNT = "tasks_count";
      
      /**
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = POSITION + ", "
         + LIST_NAME;
   }
   
   
   protected static interface NoteBaseColumns
   {
      /**
       * The created date of the note
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String NOTE_CREATED_DATE = "note_created";
      
      /**
       * The modified date of the note
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String NOTE_MODIFIED_DATE = "note_modified";
      
      /**
       * Indicates if the note is deleted.
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - no</LI> <LI>!= 0 - yes</LI>
       */
      public final static String NOTE_DELETED = "note_deleted";
      
      /**
       * The title of the note
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String NOTE_TITLE = "note_title";
      
      /**
       * The text of the note
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String NOTE_TEXT = "note_text";
   }
   
   
   protected static interface NoteColumns extends NoteBaseColumns
   {
      /**
       * The ID of the taskseries referenced.
       * <P>
       * Type: INTEGER (foreign key to table taskseries _ID field)
       * </P>
       */
      public final static String TASKSERIES_ID = "taskseries_id";
   }
   
   
   public static final class Notes implements BaseColumns, NoteColumns
   {
      public final static String PATH = "notes";
      
      /**
       * The content:// style URL for this table
       */
      public final static Uri CONTENT_URI = Uri.parse( "content://" + AUTHORITY
         + "/" + PATH );
      
      /**
       * The MIME type of {@link #CONTENT_URI} providing a directory of notes.
       */
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.rtm.note";
      
      /**
       * The MIME type of a {@link #CONTENT_URI} sub-directory of a single note.
       */
      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rtm.note";
      
      /**
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = _ID + " ASC";
   }
   
   
   protected static interface LocationColumns
   {
      /**
       * The name of the location
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String LOCATION_NAME = "location_name";
      
      /**
       * The longitude of the location
       * <P>
       * Type: FLOAT
       * </P>
       */
      public final static String LONGITUDE = "longitude";
      
      /**
       * The latitude of the location
       * <P>
       * Type: FLOAT
       * </P>
       */
      public final static String LATITUDE = "latitude";
      
      /**
       * The address of the location
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String ADDRESS = "address";
      
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
      
      /**
       * The zoom of the location
       * <P>
       * Type: INTEGER
       * </P>
       */
      public final static String ZOOM = "zoom";
   }
   
   
   public static final class Locations implements BaseColumns, LocationColumns
   {
      public final static String PATH = "locations";
      
      /**
       * The content:// style URL for this table
       */
      public final static Uri CONTENT_URI = Uri.parse( "content://" + AUTHORITY
         + "/" + PATH );
      
      /**
       * The MIME type of {@link #CONTENT_URI} providing a directory of locations.
       */
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.rtm.location";
      
      /**
       * The MIME type of a {@link #CONTENT_URI} sub-directory of a single location.
       */
      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rtm.location";
      
      /**
       * The number of tasks having this location set
       * <P>
       * Type: INTEGER
       * </P>
       */
      public final static String TASKS_COUNT = "tasks_count";
      
      /**
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = LOCATION_NAME;
   }
   
   
   protected static interface TagColumns
   {
      /**
       * A {@link TAGS_SEPARATOR} separated list of tags.
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String TAGS = "tags";
      
      /**
       * The separator used to separate a the tags.
       */
      public final static String TAGS_SEPARATOR = ",";
   }
   
   
   public static final class Tags implements BaseColumns, TagColumns
   {
      public final static String PATH = "tags";
      
      /**
       * The content:// style URL for this table
       */
      public final static Uri CONTENT_URI = Uri.parse( "content://" + AUTHORITY
         + "/" + PATH );
      
      /**
       * The MIME type of {@link #CONTENT_URI} providing a directory of tags.
       */
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.rtm.tags";
      
      /**
       * The MIME type of a {@link #CONTENT_URI} sub-directory of {@link TAGS_SEPARATOR} separated tags.
       */
      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rtm.tags";
      
      /**
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = _ID + " ASC";
   }
   
   
   protected static interface TaskSeriesColumns
   {
      /**
       * The created date of the taskseries
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String TASKSERIES_CREATED_DATE = "taskseries_created";
      
      /**
       * The modified date of the taskseries
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String TASKSERIES_MODIFIED_DATE = "taskseries_modified";
      
      /**
       * The name of the taskseries
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String TASKSERIES_NAME = "taskseries_name";
      
      /**
       * The source that entered the taskseries
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String SOURCE = "source";
      
      /**
       * A URL attached to the taskseries
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String URL = "url";
      
      /**
       * A recurrence pattern for the taskseries.
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String RECURRENCE = "recurrence";
      
      /**
       * Indicates the type of the recurrence pattern.
       * <P>
       * Type: INTEGER
       * </P>
       * <UI> <LI>0 - indicates an 'after' pattern</LI> <LI>1 - indicates an 'every' pattern</LI> </UI>
       */
      public final static String RECURRENCE_EVERY = "recurrence_every";
      
      /**
       * The ID of the location of this taskseries.
       * <P>
       * Type: INTEGER (foreign key to table locations _ID field)
       * </P>
       */
      public final static String LOCATION_ID = "location_id";
      
      /**
       * The ID of the list this taskseries is in.
       * <P>
       * Type: TEXT (foreign key to table lists _ID field)
       * </P>
       */
      public final static String LIST_ID = "list_id";
   }
   
   
   protected static interface RawTaskColumns
   {
      /**
       * The ID of the taskseries referenced.
       * <P>
       * Type: INTEGER (foreign key to table taskseries _ID field)
       * </P>
       */
      public final static String TASKSERIES_ID = "taskseries_id";
      
      /**
       * The due date of the task
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String DUE_DATE = "due";
      
      /**
       * Indicates if the task has an explicit due time and not only a due date
       * <P>
       * Type: INTEGER
       * </P>
       */
      public final static String HAS_DUE_TIME = "has_due_time";
      
      /**
       * The date when the task was added
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String ADDED_DATE = "added";
      
      /**
       * The date when the task has been completed
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String COMPLETED_DATE = "completed";
      
      /**
       * The date when the task was deleted
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String DELETED_DATE = "deleted";
      
      /**
       * The task's priority
       * <P>
       * Type: CHAR(1)
       * </P>
       * <LI>'n' - none</LI> <LI>'1' - high</LI> <LI>'2' - medium</LI> <LI>'3' - low</LI>
       */
      public final static String PRIORITY = "priority";
      
      /**
       * Indicates if the task is postponed
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - no</LI> <LI>!= 0 - yes</LI>
       */
      public final static String POSTPONED = "postponed";
      
      /**
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String ESTIMATE = "estimate";
      
      /**
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String ESTIMATE_MILLIS = "estimateMillis";
   }
   
   
   public static final class Tasks implements BaseColumns, TasksListColumns,
            TaskSeriesColumns, RawTaskColumns, TagColumns, LocationColumns
   {
      public final static String PATH = "tasks";
      
      /**
       * The content:// style URL for this table
       */
      public final static Uri CONTENT_URI = Uri.parse( "content://" + AUTHORITY
         + "/" + PATH );
      
      /**
       * The MIME type of {@link #CONTENT_URI} providing a directory of tasks.
       */
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.rtm.task";
      
      /**
       * The MIME type of a {@link #CONTENT_URI} sub-directory of a single task.
       */
      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rtm.task";
      
      /**
       * A {@link NOTE_IDS_DELIMITER} separated list of all note IDs the task has attached
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String NOTE_IDS = "note_ids";
      
      /**
       * The delimiter used to separate a list of note IDs.
       */
      public final static String NOTE_IDS_DELIMITER = TagColumns.TAGS_SEPARATOR;
      
      /**
       * A {@link PARTICIPANTS_DELIMITER} separated list of all {@link ContactColumns} contact IDs
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String PARTICIPANT_IDS = "participant_ids";
      
      /**
       * A {@link PARTICIPANTS_DELIMITER} separated list of all {@link ContactColumns} contact full names
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String PARTICIPANT_FULLNAMES = "participant_fullnames";
      
      /**
       * A {@link PARTICIPANTS_DELIMITER} separated list of all {@link ContactColumns} contact user names
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String PARTICIPANT_USERNAMES = "participant_usernames";
      
      /**
       * The delimiter used to separate a list of participant attributes.
       */
      public final static String PARTICIPANTS_DELIMITER = TagColumns.TAGS_SEPARATOR;
      
      /**
       * Sorts result tasks by their priority
       */
      public final static String SORT_PRIORITY = PRIORITY + ", COALESCE("
         + DUE_DATE + ",'-1')";
      
      /**
       * Sorts result tasks by their due date
       */
      public final static String SORT_DUE_DATE = "COALESCE(" + DUE_DATE
         + ",'-1'), " + PRIORITY;
      
      /**
       * Sorts result tasks by their name
       */
      public final static String SORT_TASK_NAME = TASKSERIES_NAME
         + " COLLATE NOCASE";
      
      /**
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = SORT_PRIORITY;
   }
   
   
   protected static interface SettingsColumns
   {
      /**
       * The time stamp when the settings were retrieved from the server
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String SYNC_TIMESTAMP = "sync_timestamp";
      
      /**
       * The user's Olson timezone. Null if the user has not set a timezone
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String TIMEZONE = "timezone";
      
      /**
       * The date format the user has set
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - indicates an European date format (e.g. 14/02/06)</LI> <LI>1 - indicates an American date format (e.g.
       * 02/14/06)</LI>
       */
      public final static String DATEFORMAT = "dateformat";
      
      /**
       * The time format the user has set
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - indicates 12 hour time with day period (e.g. 5pm)</LI> <LI>1 - indicates 24 hour time (e.g. 17:00)</LI>
       */
      public final static String TIMEFORMAT = "timeformat";
      
      /**
       * The ID of the default list the user set.
       * <P>
       * Type: INTEGER (foreign key to table lists _ID field), NULL if not set.
       * </P>
       */
      public final static String DEFAULTLIST_ID = "defaultlist_id";
      
      /**
       * The user's language (ISO 639-1 code), NULL is not set
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String LANGUAGE = "language";
   }
   
   
   public static final class Settings implements BaseColumns, SettingsColumns
   {
      public final static String PATH = "settings";
      
      /**
       * The content:// style URL for this table
       */
      public final static Uri CONTENT_URI = Uri.parse( "content://" + AUTHORITY
         + "/" + PATH );
      
      /**
       * The MIME type of {@link #CONTENT_URI} providing the settings.
       */
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.rtm.settings";
      
      /**
       * The MIME type of a {@link #CONTENT_URI} sub-directory of settings.
       */
      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rtm.settings";
   }
   
   
   protected static interface ContactColumns
   {
      /**
       * The full name of the contact
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String FULLNAME = "fullname";
      
      /**
       * The RTM username of the contact
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String USERNAME = "username";
   }
   
   
   public static final class Contacts implements BaseColumns, ContactColumns
   {
      public final static String PATH = "contacts";
      
      /**
       * The content:// style URL for this table
       */
      public final static Uri CONTENT_URI = Uri.parse( "content://" + AUTHORITY
         + "/" + PATH );
      
      /**
       * The MIME type of {@link #CONTENT_URI} providing the settings.
       */
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.rtm.contact";
      
      /**
       * The MIME type of a {@link #CONTENT_URI} sub-directory of settings.
       */
      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rtm.contact";
      
      /**
       * The number of tasks in the list
       * <P>
       * Type: INTEGER
       * </P>
       */
      public final static String TASKS_COUNT = "tasks_count";
      
      public static final String DEFAULT_SORT_ORDER = FULLNAME
         + " COLLATE NOCASE";
   }
}
