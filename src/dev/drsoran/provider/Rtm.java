package dev.drsoran.provider;

import android.net.Uri;
import android.provider.BaseColumns;


public class Rtm
{
   public static final String AUTHORITY = "dev.drsoran.provider.Rtm";
   
   
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
