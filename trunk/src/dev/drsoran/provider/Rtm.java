package dev.drsoran.provider;

import android.net.Uri;
import android.provider.BaseColumns;


public class Rtm
{
   public static final String AUTHORITY = "dev.drsoran.provider.Rtm";
   
   
   protected static interface ListBaseColumns
   {
      /**
       * The name of the list
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String LIST_NAME = "list_name";
      
      /**
       * Indicates if the list is a smart list
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - no</LI> <LI>!= 0 - yes</LI>
       */
      public final static String IS_SMART_LIST = "smart";
   }
   

   protected static interface ListColumns extends ListBaseColumns
   {
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
       * The smart filer for this list
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String FILTER = "filter";
   }
   

   public static final class Lists implements BaseColumns, ListColumns
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
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = _ID + " ASC";
   }
   

   public static final class ListOverviews implements BaseColumns, ListColumns
   {
      public final static String PATH = "list_overviews";
      
      /**
       * The content:// style URL for this table
       */
      public final static Uri CONTENT_URI = Uri.parse( "content://" + AUTHORITY
         + "/" + PATH );
      
      /**
       * The MIME type of {@link #CONTENT_URI} providing a directory of list overviews.
       */
      public final static String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.rtm.list_overview";
      
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
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = _ID + " ASC";
   }
   

   protected static interface TagColumns
   {
      /**
       * The ID of the taskseries referenced.
       * <P>
       * Type: INTEGER (foreign key to table taskseries _ID field)
       * </P>
       */
      public final static String TASKSERIES_ID = "taskseries_id";
      
      /**
       * The tag itself
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String TAG = "tag";
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
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.rtm.tag";
      
      /**
       * The MIME type of a {@link #CONTENT_URI} sub-directory of a single tag.
       */
      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rtm.tag";
      
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
      public final static String MODIFIED_DATE = "taskseries_modified";
      
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
       * The ID of the task of this taskseries.
       * <P>
       * Type: INTEGER (foreign key to table tasks _ID field)
       * </P>
       */
      public final static String RAW_TASK_ID = "raw_task_id";
      
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
       * Type: INTEGER (foreign key to table lists _ID field)
       * </P>
       */
      public final static String LIST_ID = "list_id";
   }
   

   public static final class TaskSeries implements BaseColumns,
            TaskSeriesColumns
   {
      public final static String PATH = "taskseries";
      
      /**
       * The content:// style URL for this table
       */
      public final static Uri CONTENT_URI = Uri.parse( "content://" + AUTHORITY
         + "/" + PATH );
      
      /**
       * The MIME type of {@link #CONTENT_URI} providing a directory of taskseries.
       */
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.rtm.taskseries";
      
      /**
       * The MIME type of a {@link #CONTENT_URI} sub-directory of a single taskseries.
       */
      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rtm.taskseries";
      
      /**
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = _ID + " ASC";
   }
   

   protected static interface RawTaskColumns
   {
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
   }
   

   public static final class RawTasks implements BaseColumns, RawTaskColumns
   {
      public final static String PATH = "rawtasks";
      
      /**
       * The content:// style URL for this table
       */
      public final static Uri CONTENT_URI = Uri.parse( "content://" + AUTHORITY
         + "/" + PATH );
      
      /**
       * The MIME type of {@link #CONTENT_URI} providing a directory of raw tasks.
       */
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.rtm.rawtask";
      
      /**
       * The MIME type of a {@link #CONTENT_URI} sub-directory of a single raw task.
       */
      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rtm.rawtask";
      
      /**
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = _ID + " ASC";
   }
   

   public static final class Tasks implements BaseColumns, ListBaseColumns,
            TaskSeriesColumns, RawTaskColumns, LocationColumns
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
       * A {@link TAGS_DELIMITER} separated list of all tags
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String TAGS = "tags";
      
      /**
       * The number of notes the tag has attached
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String NUM_NOTES = "num_notes";
      
      /**
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = _ID + " ASC";
      
      /**
       * The delimiter used to separate a list of tags.
       */
      public final static String TAGS_DELIMITER = ",";
   }
}
