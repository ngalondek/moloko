package dev.drsoran.moloko.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.RemoteException;
import dev.drsoran.provider.Rtm;
import dev.drsoran.provider.Rtm.TagRefs;
import dev.drsoran.provider.Rtm.Tags;
import dev.drsoran.provider.Rtm.TaskSeries;


public class TagRefsProviderPart extends AbstractRtmProviderPart
{
   @SuppressWarnings( "unused" )
   private static final String TAG = TagRefsProviderPart.class.getSimpleName();
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { TagRefs._ID, TagRefs.TASKSERIES_ID, TagRefs.TAG_ID };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      AbstractRtmProviderPart.initProjectionDependent( PROJECTION,
                                                       PROJECTION_MAP,
                                                       COL_INDICES );
   }
   
   

   public final static ContentProviderOperation addTagToTaskSeries( ContentProviderClient client,
                                                                    String taskSeriesId,
                                                                    String tagId )
   {
      ContentProviderOperation operation = null;
      
      // Check that booth exist
      try
      {
         if ( Queries.exists( client, TaskSeries.CONTENT_URI, taskSeriesId )
            && Queries.exists( client, Tags.CONTENT_URI, tagId )
            && taskSeriesId != null && tagId != null )
         {
            operation = ContentProviderOperation.newInsert( TagRefs.CONTENT_URI )
                                                .withValue( TagRefs.TASKSERIES_ID,
                                                            taskSeriesId )
                                                .withValue( TagRefs.TAG_ID,
                                                            tagId )
                                                .build();
         }
      }
      catch ( RemoteException e )
      {
         operation = null;
      }
      
      return operation;
   }
   


   public final static List< TagRef > getTagRefs( ContentProviderClient client,
                                                  String taskSeriesId )
   {
      ArrayList< TagRef > tagRefs = null;
      
      try
      {
         final Cursor c = client.query( Rtm.TagRefs.CONTENT_URI,
                                        PROJECTION,
                                        TagRefs.TASKSERIES_ID + "="
                                           + taskSeriesId,
                                        null,
                                        null );
         
         tagRefs = new ArrayList< TagRef >();
         
         boolean ok = true;
         
         if ( c.getCount() > 0 )
         {
            for ( ok = c.moveToFirst(); ok && !c.isAfterLast(); c.moveToNext() )
            {
               tagRefs.add( new TagRef( c.getString( COL_INDICES.get( TagRefs._ID ) ),
                                        c.getString( COL_INDICES.get( TagRefs.TASKSERIES_ID ) ),
                                        c.getString( COL_INDICES.get( TagRefs.TAG_ID ) ) ) );
            }
         }
         if ( !ok )
            tagRefs = null;
         
         c.close();
      }
      catch ( RemoteException e )
      {
         tagRefs = null;
      }
      
      return tagRefs;
   }
   


   public TagRefsProviderPart( SQLiteOpenHelper dbAccess )
   {
      super( dbAccess, "tagrefs" );
   }
   


   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE " + path + " ( " + TagRefs._ID
         + " INTEGER NOT NULL, " + TagRefs.TASKSERIES_ID
         + " INTEGER NOT NULL, " + TagRefs.TAG_ID + " INTEGER NOT NULL, "
         + "CONSTRAINT PK_TAGREFS PRIMARY KEY ( " + TagRefs.TASKSERIES_ID
         + ", " + TagRefs.TAG_ID + ", " + TagRefs._ID + "), "
         + "CONSTRAINT tag FOREIGN KEY ( " + TagRefs.TAG_ID
         + " ) REFERENCES tags ( " + Tags._ID + "), "
         + "CONSTRAINT taskseries_1 FOREIGN KEY ( " + TagRefs.TASKSERIES_ID
         + " ) REFERENCES taskseries ( " + TaskSeries._ID + " ) );" );
   }
   


   @Override
   protected String getContentItemType()
   {
      return TagRefs.CONTENT_ITEM_TYPE;
   }
   


   @Override
   protected String getContentType()
   {
      return TagRefs.CONTENT_TYPE;
   }
   


   @Override
   protected Uri getContentUri()
   {
      return TagRefs.CONTENT_URI;
   }
   


   @Override
   protected String getDefaultSortOrder()
   {
      return TagRefs.DEFAULT_SORT_ORDER;
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
