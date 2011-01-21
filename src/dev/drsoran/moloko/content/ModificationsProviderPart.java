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

import java.util.Collection;
import java.util.HashMap;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.provider.Rtm.Modifications;


public class ModificationsProviderPart extends AbstractRtmProviderPart
{
   private static final String TAG = "Moloko."
      + ModificationsProviderPart.class.getSimpleName();
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { Modifications._ID, Modifications.ENTITY_URI, Modifications.COL_ID,
    Modifications.NEW_VALUE, Modifications.SYNCED_VALUE };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      AbstractRtmProviderPart.initProjectionDependent( PROJECTION,
                                                       PROJECTION_MAP,
                                                       COL_INDICES );
   }
   
   

   public final static < T > ContentValues getContentValues( Modification< T > modification,
                                                             boolean withId )
   {
      final ContentValues values = new ContentValues();
      
      if ( withId )
         if ( !TextUtils.isEmpty( modification.getId() ) )
            values.put( Modifications._ID, modification.getId() );
         else
            values.putNull( Modifications._ID );
      
      values.put( Modifications.ENTITY_URI, modification.getEntityUri()
                                                        .toString() );
      values.put( Modifications.COL_ID, modification.getColId() );
      
      Modification.put( values,
                        Modifications.NEW_VALUE,
                        modification.getNewValue() );
      
      Modification.put( values,
                        Modifications.SYNCED_VALUE,
                        modification.getOldValue() );
      
      return values;
   }
   


   public final static boolean applyModifications( ContentResolver contentResolver,
                                                   Collection< Modification< ? > > modifications )
   {
      boolean ok = true;
      
      if ( ok )
      {
         final ContentProviderClient client = contentResolver.acquireContentProviderClient( Modifications.CONTENT_URI );
         ok = client != null;
         
         if ( ok )
         {
            RtmProvider rtmProvider = null;
            TransactionalAccess transactionalAccess = null;
            
            if ( client.getLocalContentProvider() instanceof RtmProvider )
            {
               rtmProvider = (RtmProvider) client.getLocalContentProvider();
               transactionalAccess = rtmProvider.newTransactionalAccess();
            }
            else
            {
               Log.e( TAG, "This method needs a RtmProvider instance to work." );
               ok = false;
            }
            
            if ( ok && transactionalAccess == null )
            {
               Log.e( TAG, "Failed to create transactions." );
               ok = false;
            }
            
            if ( ok )
            {
               try
               {
                  transactionalAccess.beginTransaction();
                  
                  for ( Modification< ? > modification : modifications )
                  {
                     
                  }
                  
                  transactionalAccess.setTransactionSuccessful();
               }
               catch ( Exception e )
               {
                  Log.e( TAG, LogUtils.GENERIC_DB_ERROR, e );
                  ok = false;
               }
               finally
               {
                  transactionalAccess.endTransaction();
               }
            }
         }
         else
         {
            Log.e( TAG, LogUtils.GENERIC_DB_ERROR );
         }
         
         if ( client != null )
            client.release();
      }
      
      return ok;
   }
   


   public ModificationsProviderPart( SQLiteOpenHelper dbAccess )
   {
      super( dbAccess, Modifications.PATH );
   }
   


   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE "
         + path
         + " ( "
         + Modifications._ID
         + " INTEGER NOT NULL CONSTRAINT PK_MODIFICATIONS PRIMARY KEY AUTOINCREMENT, "
         + Modifications.ENTITY_URI + " TEXT NOT NULL, " + Modifications.COL_ID
         + " INTEGER NOT NULL, " + Modifications.NEW_VALUE + " TEXT, "
         + Modifications.SYNCED_VALUE + " TEXT );" );
   }
   


   @Override
   protected String getContentItemType()
   {
      return Modifications.CONTENT_ITEM_TYPE;
   }
   


   @Override
   protected String getContentType()
   {
      return Modifications.CONTENT_TYPE;
   }
   


   @Override
   protected Uri getContentUri()
   {
      return Modifications.CONTENT_URI;
   }
   


   @Override
   protected String getDefaultSortOrder()
   {
      return Modifications.DEFAULT_SORT_ORDER;
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
