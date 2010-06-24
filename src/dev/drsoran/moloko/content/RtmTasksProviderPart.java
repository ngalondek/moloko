package dev.drsoran.moloko.content;

import java.util.Date;
import java.util.HashMap;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.RemoteException;

import com.mdt.rtm.data.RtmTask;

import dev.drsoran.provider.Rtm;
import dev.drsoran.provider.Rtm.RawTasks;


public class RtmTasksProviderPart extends AbstractRtmProviderPart
{
   @SuppressWarnings( "unused" )
   private static final String TAG = RtmTasksProviderPart.class.getSimpleName();
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { RawTasks._ID, RawTasks.DUE_DATE, RawTasks.HAS_DUE_TIME,
    RawTasks.ADDED_DATE, RawTasks.COMPLETED_DATE, RawTasks.DELETED_DATE,
    RawTasks.PRIORITY, RawTasks.POSTPONED, RawTasks.ESTIMATE };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      AbstractRtmProviderPart.initProjectionDependent( PROJECTION,
                                                       PROJECTION_MAP,
                                                       COL_INDICES );
   }
   
   

   public final static ContentValues getContentValues( RtmTask task,
                                                       boolean withId )
   {
      final ContentValues values = new ContentValues();
      
      if ( withId )
         values.put( RawTasks._ID, task.getId() );
      
      if ( task.getDue() != null )
         values.put( RawTasks.DUE_DATE, task.getDue().getTime() );
      else
         values.putNull( RawTasks.DUE_DATE );
      
      values.put( RawTasks.HAS_DUE_TIME, task.getHasDueTime() );
      
      if ( task.getAdded() != null )
         values.put( RawTasks.ADDED_DATE, task.getAdded().getTime() );
      else
         values.putNull( RawTasks.ADDED_DATE );
      
      if ( task.getCompleted() != null )
         values.put( RawTasks.COMPLETED_DATE, task.getCompleted().getTime() );
      else
         values.putNull( RawTasks.COMPLETED_DATE );
      
      if ( task.getDeleted() != null )
         values.put( RawTasks.DELETED_DATE, task.getDeleted().getTime() );
      else
         values.putNull( RawTasks.DELETED_DATE );
      
      if ( task.getDeleted() != null )
         values.put( RawTasks.DELETED_DATE, task.getDeleted().getTime() );
      else
         values.putNull( RawTasks.DELETED_DATE );
      
      values.put( RawTasks.PRIORITY,
                  RtmTask.convertPriority( task.getPriority() ) );
      values.put( RawTasks.POSTPONED, task.getPostponed() );
      
      if ( task.getEstimate() != null )
         values.put( RawTasks.ESTIMATE, task.getEstimate() );
      else
         values.putNull( RawTasks.ESTIMATE );
      
      return values;
   }
   


   public final static ContentProviderOperation insertTask( ContentProviderClient client,
                                                            RtmTask task ) throws RemoteException
   {
      ContentProviderOperation operation = null;
      
      // Only insert if not exists
      boolean ok = !Queries.exists( client, RawTasks.CONTENT_URI, task.getId() );
      
      // Check mandatory attributes
      ok = ok && task.getId() != null;
      
      if ( ok )
      {
         operation = ContentProviderOperation.newInsert( RawTasks.CONTENT_URI )
                                             .withValues( getContentValues( task,
                                                                            true ) )
                                             .build();
      }
      
      return operation;
   }
   


   public final static RtmTask getTask( ContentProviderClient client, String id ) throws RemoteException
   {
      RtmTask task = null;
      
      // We query all TaskSeries rows and sort them by their list ID.
      // So we have all lists with their tasks together.
      final Cursor c = Queries.getItem( client,
                                        PROJECTION,
                                        Rtm.RawTasks.CONTENT_URI,
                                        id );
      
      if ( c.moveToFirst() )
      {
         Date due = null;
         Date completed = null;
         Date deleted = null;
         
         if ( !c.isNull( COL_INDICES.get( RawTasks.DUE_DATE ) ) )
            due = new Date( c.getLong( COL_INDICES.get( RawTasks.DUE_DATE ) ) );
         if ( !c.isNull( COL_INDICES.get( RawTasks.COMPLETED_DATE ) ) )
            completed = new Date( c.getLong( COL_INDICES.get( RawTasks.COMPLETED_DATE ) ) );
         if ( !c.isNull( COL_INDICES.get( RawTasks.DELETED_DATE ) ) )
            deleted = new Date( c.getLong( COL_INDICES.get( RawTasks.DELETED_DATE ) ) );
         
         task = new RtmTask( c.getString( COL_INDICES.get( RawTasks._ID ) ),
                             due,
                             c.getInt( COL_INDICES.get( RawTasks.HAS_DUE_TIME ) ),
                             new Date( c.getLong( COL_INDICES.get( RawTasks.ADDED_DATE ) ) ),
                             completed,
                             deleted,
                             RtmTask.convertPriority( c.getString( COL_INDICES.get( RawTasks.PRIORITY ) ) ),
                             c.getInt( COL_INDICES.get( RawTasks.POSTPONED ) ),
                             Queries.getOptString( c,
                                                   COL_INDICES.get( RawTasks.ESTIMATE ) ) );
      }
      
      c.close();
      
      return task;
   }
   


   public RtmTasksProviderPart( SQLiteOpenHelper dbAccess )
   {
      super( dbAccess, RawTasks.PATH );
   }
   


   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE " + path + " ( " + RawTasks._ID
         + " INTEGER NOT NULL, " + RawTasks.DUE_DATE + " INTEGER, "
         + RawTasks.HAS_DUE_TIME + " INTEGER NOT NULL DEFAULT 0, "
         + RawTasks.ADDED_DATE + " INTEGER NOT NULL, "
         + RawTasks.COMPLETED_DATE + " INTEGER, " + RawTasks.DELETED_DATE
         + " INTEGER, " + RawTasks.PRIORITY + " CHAR(1) NOT NULL DEFAULT 'n', "
         + RawTasks.POSTPONED + " INTEGER DEFAULT 0, " + RawTasks.ESTIMATE
         + " NOTE_TEXT, " + "CONSTRAINT PK_TASKS PRIMARY KEY ( \""
         + RawTasks._ID + "\" )" + " );" );
   }
   


   @Override
   protected ContentValues getInitialValues( ContentValues initialValues )
   {
      // Make sure that the fields are all set
      if ( initialValues.containsKey( RawTasks.ADDED_DATE ) == false )
      {
         final Long now = Long.valueOf( System.currentTimeMillis() );
         
         initialValues.put( RawTasks.ADDED_DATE, now );
      }
      
      return initialValues;
   }
   


   @Override
   protected String getContentItemType()
   {
      return RawTasks.CONTENT_ITEM_TYPE;
   }
   


   @Override
   protected String getContentType()
   {
      return RawTasks.CONTENT_TYPE;
   }
   


   @Override
   protected Uri getContentUri()
   {
      return RawTasks.CONTENT_URI;
   }
   


   @Override
   protected String getDefaultSortOrder()
   {
      return RawTasks.DEFAULT_SORT_ORDER;
   }
   


   public HashMap< String, String > getProjectionMap()
   {
      return PROJECTION_MAP;
   }
   


   public HashMap< String, Integer > getColumnIndices()
   {
      return COL_INDICES;
   }
   


   public String[] getProjection()
   {
      return PROJECTION;
   }
}
