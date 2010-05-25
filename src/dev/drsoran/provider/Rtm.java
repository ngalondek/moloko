package dev.drsoran.provider;

import android.net.Uri;
import android.provider.BaseColumns;


public class Rtm
{
   public static final String AUTHORITY = "dev.drsoran.provider.Rtm";
   
   
   public static final class Lists implements BaseColumns
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
       * The name of the list
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String NAME = "name";
      
      /**
       * The ID of the taskseries contained in this list.
       * <P>
       * Type: INTEGER (foreign key to table taskseries _ID field)
       * </P>
       */
      public final static String TASKSERIES_ID = "taskseries";
      
      /**
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = NAME + " DESC";
   }
   

   public static final class Notes implements BaseColumns
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
       * The created date of the note
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String CREATED_DATE = "created";
      
      /**
       * The modified date of the note
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String MODIFIED_DATE = "modified";
      
      /**
       * The title of the note
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String TITLE = "title";
      
      /**
       * The text of the note
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String TEXT = "text";
      
      /**
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = CREATED_DATE + " DESC";
   }
   

   public static final class Locations implements BaseColumns
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
       * The name of the location
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String NAME = "name";
      
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
      
      /**
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = NAME + " DESC";
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
   

   public static final class TaskSeries implements BaseColumns
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
       * The created date of the taskseries
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String CREATED_DATE = "created";
      
      /**
       * The modified date of the taskseries
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String MODIFIED_DATE = "modified";
      
      /**
       * The name of the taskseries
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String NAME = "name";
      
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
       * The ID of the note of this taskseries.
       * <P>
       * Type: INTEGER (foreign key to table notes _ID field)
       * </P>
       */
      public final static String NOTES_ID = "notes";
      
      /**
       * The ID of the tag of this taskseries.
       * <P>
       * Type: INTEGER (foreign key to table tags _ID field)
       * </P>
       */
      public final static String TAGS_ID = "tags";
      
      /**
       * The ID of the task of this taskseries.
       * <P>
       * Type: INTEGER (foreign key to table tasks _ID field)
       * </P>
       */
      public final static String TASKS_ID = "tasks";
      
      /**
       * The ID of the location of this taskseries.
       * <P>
       * Type: INTEGER (foreign key to table locations _ID field)
       * </P>
       */
      public final static String LOCATIONS_ID = "locations";
      
      /**
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = CREATED_DATE + " DESC";
   }
   

   public static final class Tasks implements BaseColumns
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
       * Type: INTEGER
       * </P>
       * <LI>0 - none</LI> <LI>1 - high</LI> <LI>2 - medium</LI> <LI>3 - low</LI>
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
       * The default sort order for this table
       */
      public final static String DEFAULT_SORT_ORDER = DUE_DATE + " DESC";
   }
   
}
