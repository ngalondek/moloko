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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm;
import dev.drsoran.provider.Rtm.Modifications;


public class ModificationsProviderPart extends AbstractRtmProviderPart
{
   private static final String TAG = "Moloko."
      + ModificationsProviderPart.class.getSimpleName();
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { Modifications._ID, Modifications.ENTITY_URI, Modifications.COL_NAME,
    Modifications.NEW_VALUE, Modifications.SYNCED_VALUE };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   private final static String SEL_QUERY_MODIFICATION = new StringBuilder( 100 ).append( Modifications.ENTITY_URI )
                                                                                .append( "=? AND " )
                                                                                .append( Modifications.COL_NAME )
                                                                                .append( "=?" )
                                                                                .toString();
   
   private final static String SEL_QUERY_MODIFICATIONS = new StringBuilder( Modifications.ENTITY_URI ).append( "=?" )
                                                                                                      .toString();
   
   static
   {
      AbstractRtmProviderPart.initProjectionDependent( PROJECTION,
                                                       PROJECTION_MAP,
                                                       COL_INDICES );
   }
   
   

   public final static ContentValues getContentValues( ContentResolver contentResolver,
                                                       Modification modification,
                                                       boolean withId )
   {
      ContentValues values = new ContentValues();
      
      if ( withId )
         if ( !TextUtils.isEmpty( modification.getId() ) )
            values.put( Modifications._ID, modification.getId() );
         else
            values.putNull( Modifications._ID );
      
      values.put( Modifications.ENTITY_URI, modification.getEntityUri()
                                                        .toString() );
      values.put( Modifications.COL_NAME, modification.getColName() );
      
      Modification.put( values,
                        Modifications.NEW_VALUE,
                        modification.getNewValue() );
      
      String syncedValue = null;
      
      if ( !modification.isSyncedValueSet() )
      {
         final ContentProviderClient entityClient = contentResolver.acquireContentProviderClient( modification.getEntityUri() );
         
         if ( entityClient == null )
            throw new IllegalArgumentException( "Illegal content URI "
               + modification.getEntityUri() );
         
         Cursor c = null;
         
         try
         {
            c = entityClient.query( modification.getEntityUri(), new String[]
            { modification.getColName() }, null, null, null );
            
            if ( c == null || c.getCount() == 0 )
               throw new IllegalArgumentException( "Illegal entity URI "
                  + modification.getEntityUri() );
            
            if ( !c.moveToFirst() )
               throw new SQLException( "Unable to move to 1st element" );
            
            syncedValue = c.getString( 0 );
         }
         catch ( RemoteException e )
         {
            Log.e( TAG, LogUtils.GENERIC_DB_ERROR, e );
            values = null;
         }
         finally
         {
            if ( c != null )
               c.close();
         }
         
         entityClient.release();
      }
      else
      {
         syncedValue = modification.getSyncedValue();
      }
      
      Modification.put( values, Modifications.SYNCED_VALUE, syncedValue );
      
      return values;
   }
   


   public final static Modification getModification( ContentProviderClient client,
                                                     Uri entityUri,
                                                     String colName )
   {
      Modification modification = null;
      Cursor c = null;
      
      try
      {
         c = client.query( Modifications.CONTENT_URI,
                           PROJECTION,
                           SEL_QUERY_MODIFICATION,
                           new String[]
                           { entityUri.toString(), colName },
                           null );
         
         if ( c != null && c.getCount() > 0 )
         {
            if ( !c.moveToFirst() )
               throw new SQLException( "Unable to move to 1st element" );
            
            modification = createModification( c );
         }
      }
      catch ( RemoteException e )
      {
         Log.e( TAG, LogUtils.GENERIC_DB_ERROR, e );
         modification = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return modification;
   }
   


   public final static List< Modification > getModifications( ContentProviderClient client,
                                                              Uri entityUri )
   {
      List< Modification > modifications = null;
      Cursor c = null;
      
      try
      {
         if ( entityUri == null )
            c = client.query( Modifications.CONTENT_URI,
                              PROJECTION,
                              null,
                              null,
                              null );
         else
            c = client.query( Modifications.CONTENT_URI,
                              PROJECTION,
                              SEL_QUERY_MODIFICATIONS,
                              new String[]
                              { entityUri.toString() },
                              null );
         
         boolean ok = c != null;
         if ( ok )
         {
            modifications = new ArrayList< Modification >( c.getCount() );
            
            if ( c.getCount() > 0 )
            {
               for ( ok = c.moveToFirst(); ok && !c.isAfterLast(); c.moveToNext() )
               {
                  final Modification modification = createModification( c );
                  ok = modification != null;
                  
                  if ( ok )
                     modifications.add( modification );
               }
            }
         }
      }
      catch ( RemoteException e )
      {
         Log.e( TAG, LogUtils.GENERIC_DB_ERROR, e );
         modifications = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return modifications;
   }
   


   public final static boolean isModified( ContentProviderClient client,
                                           Uri entityUri ) throws RemoteException
   {
      Cursor c = null;
      
      try
      {
         c = client.query( Modifications.CONTENT_URI,
                           PROJECTION,
                           SEL_QUERY_MODIFICATIONS,
                           new String[]
                           { entityUri.toString() },
                           null );
         
         if ( c == null )
            throw new RemoteException();
         
         return c.getCount() > 0;
      }
      catch ( RemoteException e )
      {
         Log.e( TAG, LogUtils.GENERIC_DB_ERROR, e );
         throw e;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
   }
   


   public final static boolean applyModifications( ContentResolver contentResolver,
                                                   Collection< Modification > modifications )
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
               final ArrayList< ContentProviderOperation > operations = new ArrayList< ContentProviderOperation >();
               
               try
               {
                  transactionalAccess.beginTransaction();
                  
                  for ( Modification modification : modifications )
                  {
                     final Uri entityUri = modification.getEntityUri();
                     
                     // Check id the modification should be stored or simply modify
                     // the entity.
                     if ( modification.isPersistent() )
                     {
                        // Check if modification already exists
                        final Modification existingMod = getModification( client,
                                                                          entityUri,
                                                                          modification.getColName() );
                        if ( existingMod != null )
                        {
                           // Update the modification with the new value.
                           operations.add( ContentProviderOperation.newUpdate( Modifications.CONTENT_URI )
                                                                   .withValue( Modifications.NEW_VALUE,
                                                                               modification.getNewValue() )
                                                                   .build() );
                        }
                        else
                        {
                           // Insert a new modification.
                           operations.add( ContentProviderOperation.newInsert( Modifications.CONTENT_URI )
                                                                   .withValues( getContentValues( contentResolver,
                                                                                                  modification,
                                                                                                  true ) )
                                                                   .build() );
                        }
                     }
                     
                     // Update the entity itself
                     operations.add( ContentProviderOperation.newUpdate( modification.getEntityUri() )
                                                             .withValue( modification.getColName(),
                                                                         modification.getNewValue() )
                                                             .build() );
                  }
                  
                  // Apply the collected operations in a bulk.
                  if ( operations.size() > 0 )
                     contentResolver.applyBatch( Rtm.AUTHORITY, operations );
                  
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
   


   public static ContentProviderOperation getRemoveModificationOps( Uri contentUri,
                                                                    String entityId )
   {
      return ContentProviderOperation.newDelete( Modifications.CONTENT_URI )
                                     .withSelection( new StringBuilder( Modifications.ENTITY_URI ).append( " = '" )
                                                                                                  .append( Queries.contentUriWithId( contentUri,
                                                                                                                                     entityId ) )
                                                                                                  .append( "'" )
                                                                                                  .toString(),
                                                     null )
                                     .build();
   }
   


   private static Modification createModification( Cursor c )
   {
      return Modification.newModification( c.getString( COL_INDICES.get( Modifications._ID ) ),
                                           Uri.parse( c.getString( COL_INDICES.get( Modifications.ENTITY_URI ) ) ),
                                           c.getString( COL_INDICES.get( Modifications.COL_NAME ) ),
                                           Queries.getOptString( c,
                                                                 COL_INDICES.get( Modifications.NEW_VALUE ) ),
                                           Queries.getOptString( c,
                                                                 COL_INDICES.get( Modifications.SYNCED_VALUE ) ) );
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
         + Modifications.ENTITY_URI + " TEXT NOT NULL, "
         + Modifications.COL_NAME + " TEXT NOT NULL, "
         + Modifications.NEW_VALUE + " TEXT, " + Modifications.SYNCED_VALUE
         + " TEXT );" );
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
