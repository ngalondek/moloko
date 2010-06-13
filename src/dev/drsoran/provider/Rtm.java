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
      public final static String LIST_NAME = "list_name";
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
   

   protected static interface NoteColumns
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
   

   public static final class NoteRefs implements BaseColumns
   {
      public final static String PATH = "noterefs";
      
      /**
       * The content:// style URL for this table
       */
      public final static Uri CONTENT_URI = Uri.parse( "content://" + AUTHORITY
         + "/" + PATH );
      
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
   

   public static final class Tasks implements BaseColumns, ListColumns,
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
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = _ID + " ASC";
   }
}
