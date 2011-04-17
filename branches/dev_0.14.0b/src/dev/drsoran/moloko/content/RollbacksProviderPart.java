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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.mdt.rtm.TimeLineResult;

import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.provider.Rtm.Modifications;
import dev.drsoran.provider.Rtm.Rollbacks;


public class RollbacksProviderPart extends AbstractRtmProviderPart
{
   private static final String TAG = "Moloko."
      + RollbacksProviderPart.class.getSimpleName();
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { Rollbacks._ID, Rollbacks.TIMELINE_ID, Rollbacks.TRANSACTION_ID,
    Rollbacks.TIMESTAMP };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      AbstractRtmProviderPart.initProjectionDependent( PROJECTION,
                                                       PROJECTION_MAP,
                                                       COL_INDICES );
   }
   
   

   public final static ContentValues getContentValues( Rollback rollback,
                                                       boolean withId )
   {
      ContentValues values = new ContentValues();
      
      if ( withId )
         if ( !TextUtils.isEmpty( rollback.getId() ) )
            values.put( Rollbacks._ID, rollback.getId() );
         else
            values.putNull( Rollbacks._ID );
      
      values.put( Rollbacks.TIMELINE_ID, rollback.getTimeLineId() );
      values.put( Rollbacks.TRANSACTION_ID, rollback.getTransactionId() );
      values.put( Rollbacks.TIMESTAMP, Long.toString( rollback.getTimeStamp() ) );
      
      return values;
   }
   


   public final static ContentValues getContentValues( TimeLineResult.Transaction transaction )
   {
      ContentValues values = new ContentValues();
      
      values.putNull( Rollbacks._ID );
      values.put( Rollbacks.TIMELINE_ID, transaction.timelineId );
      values.put( Rollbacks.TRANSACTION_ID, transaction.transactionId );
      values.put( Rollbacks.TIMESTAMP,
                  Long.toString( System.currentTimeMillis() ) );
      
      return values;
   }
   


   public final static Rollback getRollback( ContentProviderClient client,
                                             String id )
   {
      Rollback rollback = null;
      Cursor c = null;
      
      try
      {
         c = client.query( Rollbacks.CONTENT_URI, PROJECTION, Rollbacks._ID
            + "=" + id, null, null );
         
         if ( c != null && c.getCount() > 0 )
         {
            if ( !c.moveToFirst() )
               throw new SQLException( "Unable to move to 1st element" );
            
            rollback = createRollback( c );
         }
      }
      catch ( RemoteException e )
      {
         Log.e( TAG, LogUtils.GENERIC_DB_ERROR, e );
         rollback = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return rollback;
   }
   


   public final static List< Rollback > getRollbacks( ContentProviderClient client )
   {
      List< Rollback > rollbacks = null;
      Cursor c = null;
      
      try
      {
         c = client.query( Rollbacks.CONTENT_URI, PROJECTION, null, null, null );
         
         boolean ok = c != null;
         if ( ok )
         {
            rollbacks = new ArrayList< Rollback >( c.getCount() );
            
            if ( c.getCount() > 0 )
            {
               for ( ok = c.moveToFirst(); ok && !c.isAfterLast(); c.moveToNext() )
               {
                  final Rollback rollback = createRollback( c );
                  ok = rollback != null;
                  
                  if ( ok )
                     rollbacks.add( rollback );
               }
            }
         }
      }
      catch ( RemoteException e )
      {
         Log.e( TAG, LogUtils.GENERIC_DB_ERROR, e );
         rollbacks = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return rollbacks;
   }
   


   private static Rollback createRollback( Cursor c )
   {
      return Rollback.newRollback( c.getString( COL_INDICES.get( Rollbacks._ID ) ),
                                   c.getString( COL_INDICES.get( Rollbacks.TIMELINE_ID ) ),
                                   c.getString( COL_INDICES.get( Rollbacks.TRANSACTION_ID ) ),
                                   c.getLong( COL_INDICES.get( Rollbacks.TIMESTAMP ) ) );
   }
   


   public RollbacksProviderPart( Context context, SQLiteOpenHelper dbAccess )
   {
      super( context, dbAccess, Rollbacks.PATH );
   }
   


   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE "
         + path
         + " ( "
         + Rollbacks._ID
         + " INTEGER NOT NULL CONSTRAINT PK_MODIFICATIONS PRIMARY KEY AUTOINCREMENT, "
         + Rollbacks.TIMELINE_ID + " TEXT NOT NULL, "
         + Rollbacks.TRANSACTION_ID + " TEXT NOT NULL, " + Rollbacks.TIMESTAMP
         + " INTEGER NOT NULL " + ");" );
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
   public Uri getContentUri()
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
