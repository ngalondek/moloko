package dev.drsoran.provider;

import android.net.Uri;
import android.provider.BaseColumns;


public class Rtm
{
   public static final String AUTHORITY = "dev.drsoran.provider.Rtm";
   
   
   protected static interface ListColumns
   {
      /**
       * The name of the list
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String NAME = "list_name";
   }
   

   public static final class Lists implements BaseColumns, ListColumns
   {
      /**
       * The content:// style URL for this table
       */
      public final static Uri CONTENT_URI = Uri.parse( "content://" + AUTHORITY
         + "/lists" );
      
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
   

   protected static interface NoteColumns
   {
      /**
       * The created date of the note
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String CREATED_DATE = "note_created";
      
      /**
       * The modified date of the note
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String MODIFIED_DATE = "note_modified";
      
      /**
       * The title of the note
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String TITLE = "note_title";
      
      /**
       * The text of the note
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String TEXT = "note_text";
   }
   

   public static final class Notes implements BaseColumns, NoteColumns
   {
      /**
       * The content:// style URL for this table
       */
      public final static Uri CONTENT_URI = Uri.parse( "content://" + AUTHORITY
         + "/notes" );
      
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
   

   public static final class NoteRefs implements BaseColumns
   {
      /**
       * The content:// style URL for this table
       */
      public final static Uri CONTENT_URI = Uri.parse( "content://" + AUTHORITY
         + "/noterefs" );
      
      /**
       * The MIME type of {@link #CONTENT_URI} providing a directory of note references.
       */
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.rtm.noteref";
      
      /**
       * The MIME type of a {@link #CONTENT_URI} sub-directory of a single note reference.
       */
      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rtm.noteref";
      
      /**
       * The ID of the taskseries referenced.
       * <P>
       * Type: INTEGER (foreign key to table taskseries _ID field)
       * </P>
       */
      public final static String TASKSERIES_ID = "taskseries_id";
      
      /**
       * The ID of the note referenced.
       * <P>
       * Type: INTEGER (foreign key to table tag _ID field)
       * </P>
       */
      public final static String NOTE_ID = "note_id";
      
      /**
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = null;
   }
   

   protected static interface LocationColumns
   {
      /**
       * The name of the location
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String NAME = "location_name";
      
      /**
       * The longitude of the location
       * <P>
       * Type: FLOAT
       * </P>
       */
      public final static String LONGITUDE = "location_longitude";
      
      /**
       * The latitude of the location
       * <P>
       * Type: FLOAT
       * </P>
       */
      public final static String LATITUDE = "location_latitude";
      
      /**
       * The address of the location
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String ADDRESS = "location_address";
      
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
      public final static String VIEWABLE = "location_viewable";
      
      /**
       * The zoom of the location
       * <P>
       * Type: INTEGER
       * </P>
       */
      public final static String ZOOM = "location_zoom";
   }
   

   public static final class Locations implements BaseColumns, LocationColumns
   {
      /**
       * The content:// style URL for this table
       */
      public final static Uri CONTENT_URI = Uri.parse( "content://" + AUTHORITY
         + "/locations" );
      
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
   

   public static final class Tags implements BaseColumns
   {
      /**
       * The content:// style URL for this table
       */
      public final static Uri CONTENT_URI = Uri.parse( "content://" + AUTHORITY
         + "/tags" );
      
      /**
       * The MIME type of {@link #CONTENT_URI} providing a directory of tags.
       */
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.rtm.tag";
      
      /**
       * The MIME type of a {@link #CONTENT_URI} sub-directory of a single tag.
       */
      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rtm.tag";
      
      /**
       * The tag itself
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String TAG = "tag";
      
      /**
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = TAG + " DESC";
   }
   

   public static final class TagRefs implements BaseColumns
   {
      /**
       * The content:// style URL for this table
       */
      public final static Uri CONTENT_URI = Uri.parse( "content://" + AUTHORITY
         + "/tagrefs" );
      
      /**
       * The MIME type of {@link #CONTENT_URI} providing a directory of tag references.
       */
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.rtm.tagref";
      
      /**
       * The MIME type of a {@link #CONTENT_URI} sub-directory of a single tag reference.
       */
      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rtm.tagref";
      
      /**
       * The ID of the taskseries referenced.
       * <P>
       * Type: INTEGER (foreign key to table taskseries _ID field)
       * </P>
       */
      public final static String TASKSERIES_ID = "taskseries_id";
      
      /**
       * The ID of the tag referenced.
       * <P>
       * Type: INTEGER (foreign key to table tag _ID field)
       * </P>
       */
      public final static String TAG_ID = "tag_id";
      
      /**
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = null;
   }
   

   protected static interface TaskSeriesColumns
   {
      /**
       * The created date of the taskseries
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String CREATED_DATE = "taskseries_created";
      
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
      public final static String NAME = "taskseries_name";
      
      /**
       * The source that entered the taskseries
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String SOURCE = "taskseries_source";
      
      /**
       * A URL attached to the taskseries
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String URL = "taskseries_url";
      
      /**
       * The ID of the task of this taskseries.
       * <P>
       * Type: INTEGER (foreign key to table tasks _ID field)
       * </P>
       */
      public final static String TASK_ID = "taskseries_task_id";
      
      /**
       * The ID of the location of this taskseries.
       * <P>
       * Type: INTEGER (foreign key to table locations _ID field)
       * </P>
       */
      public final static String LOCATION_ID = "taskseries_location_id";
      
      /**
       * The ID of the list this taskseries is in.
       * <P>
       * Type: INTEGER (foreign key to table lists _ID field)
       * </P>
       */
      public final static String LIST_ID = "taskseries_list_id";
   }
   

   public static final class TaskSeries implements BaseColumns,
            TaskSeriesColumns
   {
      /**
       * The content:// style URL for this table
       */
      public final static Uri CONTENT_URI = Uri.parse( "content://" + AUTHORITY
         + "/taskseries" );
      
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
      public final static String DUE_DATE = "task_due";
      
      /**
       * The date when the task was added
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String ADDED_DATE = "task_added";
      
      /**
       * The date when the task has been completed
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String COMPLETED_DATE = "task_completed";
      
      /**
       * The date when the task was deleted
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String DELETED_DATE = "task_deleted";
      
      /**
       * The task's priority
       * <P>
       * Type: CHAR(1)
       * </P>
       * <LI>'n' - none</LI> <LI>'1' - high</LI> <LI>'2' - medium</LI> <LI>'3' - low</LI>
       */
      public final static String PRIORITY = "task_priority";
      
      /**
       * Indicates if the task is postponed
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - no</LI> <LI>!= 0 - yes</LI>
       */
      public final static String POSTPONED = "task_postponed";
      
      /**
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String ESTIMATE = "task_estimate";
   }
   

   public static final class RawTasks implements BaseColumns, RawTaskColumns
   {
      /**
       * The content:// style URL for this table
       */
      public final static Uri CONTENT_URI = Uri.parse( "content://" + AUTHORITY
         + "/raw_tasks" );
      
      /**
       * The MIME type of {@link #CONTENT_URI} providing a directory of raw tasks.
       */
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.rtm.raw_task";
      
      /**
       * The MIME type of a {@link #CONTENT_URI} sub-directory of a single raw task.
       */
      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rtm.raw_task";
      
      /**
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = _ID + " ASC";
   }
   

   public static final class Tasks implements BaseColumns, ListColumns,
            TaskSeriesColumns, RawTaskColumns
   {
      /**
       * The content:// style URL for this table
       */
      public final static Uri CONTENT_URI = Uri.parse( "content://" + AUTHORITY
         + "/tasks" );
      
      /**
       * The MIME type of {@link #CONTENT_URI} providing a directory of tasks.
       */
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.rtm.task";
      
      /**
       * The MIME type of a {@link #CONTENT_URI} sub-directory of a single task.
       */
      public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rtm.task";
      
      /**
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = _ID + " ASC";
   }
}
