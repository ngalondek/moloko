package dev.drsoran.moloko.content;

import java.util.HashMap;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.RemoteException;
import dev.drsoran.provider.Rtm.Tags;


public class RtmTagsProviderPart extends AbstractRtmProviderPart
{
   @SuppressWarnings( "unused" )
   private static final String TAG = RtmTagsProviderPart.class.getSimpleName();
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      COL_INDICES.put( Tags._ID, 0 );
      COL_INDICES.put( Tags.TAG, 1 );
      
      AbstractRtmProviderPart.fillProjectionMap( PROJECTION_MAP, COL_INDICES );
   }
   
   

   public final static ContentProviderOperation insertTag( ContentProviderClient client,
                                                           String tag ) throws RemoteException
   {
      ContentProviderOperation operation = null;
      
      if ( tag != null )
      {
         operation = ContentProviderOperation.newInsert( Tags.CONTENT_URI )
                                             .withValue( Tags.TAG, tag )
                                             .build();
      }
      
      return operation;
   }
   


   public final static String getMatchingTagId( ContentProviderClient client,
                                                String tag ) throws RemoteException
   {
      String id = null;
      
      final Cursor c = client.query( Tags.CONTENT_URI, null, Tags.TAG
         + " like " + tag, null, null );
      
      if ( c != null )
      {
         id = c.getString( COL_INDICES.get( Tags._ID ) );
         c.close();
      }
      
      return id;
   }
   


   public RtmTagsProviderPart( SQLiteOpenHelper dbAccess )
   {
      super( dbAccess, "tags" );
   }
   


   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE " + tableName + " ( " + Tags._ID
         + " INTEGER NOT NULL CONSTRAINT PK_TAGS PRIMARY KEY AUTOINCREMENT, "
         + Tags.TAG + " TEXT NOT NULL );" );
   }
   


   @Override
   protected String getContentItemType()
   {
      return Tags.CONTENT_ITEM_TYPE;
   }
   


   @Override
   protected String getContentType()
   {
      return Tags.CONTENT_TYPE;
   }
   


   @Override
   protected Uri getContentUri()
   {
      return Tags.CONTENT_URI;
   }
   


   @Override
   protected String getDefaultSortOrder()
   {
      return Tags.DEFAULT_SORT_ORDER;
   }
   


   public HashMap< String, String > getProjectionMap()
   {
      return PROJECTION_MAP;
   }
   


   public HashMap< String, Integer > getColumnIndices()
   {
      return COL_INDICES;
   }
}
