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
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;
import dev.drsoran.moloko.app.MolokoApp;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.Creations;


public class CreationsProviderPart extends AbstractRtmProviderPart
{
   private static final Class< CreationsProviderPart > TAG = CreationsProviderPart.class;
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { Creations._ID, Creations.ENTITY_URI, Creations.TIMESTAMP };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      AbstractRtmProviderPart.initProjectionDependent( PROJECTION,
                                                       PROJECTION_MAP,
                                                       COL_INDICES );
   }
   
   
   
   public final static ContentValues getContentValues( Creation creation,
                                                       boolean withId )
   {
      ContentValues values = new ContentValues();
      
      if ( withId )
         if ( !TextUtils.isEmpty( creation.getId() ) )
            values.put( Creations._ID, creation.getId() );
         else
            values.putNull( Creations._ID );
      
      values.put( Creations.ENTITY_URI, creation.getEntityUri().toString() );
      values.put( Creations.TIMESTAMP, Long.toString( creation.getTimeStamp() ) );
      
      return values;
   }
   
   
   
   public final static Creation getCreation( ContentProviderClient client,
                                             String id )
   {
      Creation creation = null;
      Cursor c = null;
      
      try
      {
         c = client.query( Creations.CONTENT_URI, PROJECTION, Creations._ID
            + "=" + id, null, null );
         
         if ( c != null && c.getCount() > 0 )
         {
            if ( !c.moveToFirst() )
               throw new SQLException( "Unable to move to 1st element" );
            
            creation = createCreation( c );
         }
      }
      catch ( RemoteException e )
      {
         MolokoApp.Log.e( TAG, LogUtils.GENERIC_DB_ERROR, e );
         creation = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return creation;
   }
   
   
   
   public final static List< Creation > getCreations( ContentProviderClient client,
                                                      Uri contentUri )
   {
      List< Creation > creations = null;
      Cursor c = null;
      
      try
      {
         c = client.query( Creations.CONTENT_URI,
                           PROJECTION,
                           contentUri != null ? Creations.ENTITY_URI
                              + " like '" + contentUri.toString() + "%'" : null,
                           null,
                           null );
         
         boolean ok = c != null;
         if ( ok )
         {
            creations = new ArrayList< Creation >( c.getCount() );
            
            if ( c.getCount() > 0 )
            {
               for ( ok = c.moveToFirst(); ok && !c.isAfterLast(); c.moveToNext() )
               {
                  final Creation creation = createCreation( c );
                  ok = creation != null;
                  
                  if ( ok )
                     creations.add( creation );
               }
            }
         }
      }
      catch ( RemoteException e )
      {
         MolokoApp.Log.e( TAG, LogUtils.GENERIC_DB_ERROR, e );
         creations = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return creations;
   }
   
   
   
   public final static ContentProviderOperation newCreation( Uri entityUri,
                                                             long timestamp )
   {
      final Creation creation = timestamp != -1
                                               ? Creation.newCreation( entityUri,
                                                                       timestamp )
                                               : Creation.newCreation( entityUri );
      
      return ContentProviderOperation.newInsert( Creations.CONTENT_URI )
                                     .withValues( getContentValues( creation,
                                                                    true ) )
                                     .build();
   }
   
   
   
   public final static ContentProviderOperation deleteCreation( Uri entityUri )
   {
      return ContentProviderOperation.newDelete( Creations.CONTENT_URI )
                                     .withSelection( new StringBuilder( Creations.ENTITY_URI ).append( " = '" )
                                                                                              .append( entityUri )
                                                                                              .append( "'" )
                                                                                              .toString(),
                                                     null )
                                     .build();
   }
   
   
   
   public final static ContentProviderOperation deleteCreation( Uri contentUri,
                                                                String entityId )
   {
      return deleteCreation( Queries.contentUriWithId( contentUri, entityId ) );
   }
   
   
   
   private static Creation createCreation( Cursor c )
   {
      return Creation.newCreation( c.getString( COL_INDICES.get( Creations._ID ) ),
                                   Uri.parse( c.getString( COL_INDICES.get( Creations.ENTITY_URI ) ) ),
                                   c.getLong( COL_INDICES.get( Creations.TIMESTAMP ) ) );
   }
   
   
   
   public CreationsProviderPart( Context context, SQLiteOpenHelper dbAccess )
   {
      super( context, dbAccess, Creations.PATH );
   }
   
   
   
   @Override
   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE " + path + " ( " + Creations._ID
         + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
         + Creations.ENTITY_URI + " TEXT NOT NULL, " + Creations.TIMESTAMP
         + " INTEGER NOT NULL" + ");" );
   }
   
   
   
   @Override
   protected String getContentItemType()
   {
      return Creations.CONTENT_ITEM_TYPE;
   }
   
   
   
   @Override
   protected String getContentType()
   {
      return Creations.CONTENT_TYPE;
   }
   
   
   
   @Override
   public Uri getContentUri()
   {
      return Creations.CONTENT_URI;
   }
   
   
   
   @Override
   protected String getDefaultSortOrder()
   {
      return Creations.DEFAULT_SORT_ORDER;
   }
   
   
   
   @Override
   public HashMap< String, String > getProjectionMap()
   {
      return PROJECTION_MAP;
   }
   
   
   
   @Override
   public HashMap< String, Integer > getColumnIndices()
   {
      return COL_INDICES;
   }
   
   
   
   @Override
   public String[] getProjection()
   {
      return PROJECTION;
   }
}
