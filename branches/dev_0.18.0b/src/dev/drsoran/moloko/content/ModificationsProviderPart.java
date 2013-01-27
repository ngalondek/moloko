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
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.Modifications;


public class ModificationsProviderPart extends AbstractRtmProviderPart
{
   private static final Class< ModificationsProviderPart > TAG = ModificationsProviderPart.class;
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { Modifications._ID, Modifications.ENTITY_URI, Modifications.COL_NAME,
    Modifications.NEW_VALUE, Modifications.SYNCED_VALUE,
    Modifications.TIMESTAMP };
   
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
      values.put( Modifications.TIMESTAMP, modification.getTimestamp() );
      
      Modification.put( values,
                        Modifications.NEW_VALUE,
                        modification.getNewValue() );
      
      final String syncedValue;
      if ( !modification.isSyncedValueSet() && contentResolver != null )
      {
         try
         {
            syncedValue = getSyncedValue( contentResolver,
                                          modification.getEntityUri(),
                                          modification.getColName() );
         }
         catch ( RemoteException e )
         {
            return null;
         }
      }
      else
         syncedValue = modification.getSyncedValue();
      
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
         MolokoApp.Log.e( TAG, LogUtils.GENERIC_DB_ERROR, e );
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
                                                              Uri[] entityUris )
   {
      List< Modification > modifications = null;
      Cursor c = null;
      
      try
      {
         if ( entityUris == null || entityUris.length == 0 )
            c = client.query( Modifications.CONTENT_URI,
                              PROJECTION,
                              null,
                              null,
                              null );
         else
         {
            final StringBuilder selection = new StringBuilder( Modifications.ENTITY_URI ).append( " like '" );
            
            for ( int i = 0; i < entityUris.length; ++i )
            {
               selection.append( entityUris[ i ].toString() ).append( "%'" );
               
               if ( i + 1 < entityUris.length )
                  selection.append( " OR " )
                           .append( Modifications.ENTITY_URI )
                           .append( " like '" );
            }
            
            c = client.query( Modifications.CONTENT_URI,
                              PROJECTION,
                              selection.toString(),
                              null,
                              null );
         }
         
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
         MolokoApp.Log.e( TAG, LogUtils.GENERIC_DB_ERROR, e );
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
         MolokoApp.Log.e( TAG, LogUtils.GENERIC_DB_ERROR, e );
         throw e;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
   }
   
   
   
   public final static boolean applyModificationsTransactional( RtmProvider rtmProvider,
                                                                Collection< Modification > modifications )
   {
      boolean ok = true;
      
      final TransactionalAccess transactionalAccess = rtmProvider.newTransactionalAccess();
      
      if ( transactionalAccess == null )
      {
         MolokoApp.Log.e( TAG, "Failed to create transactions." );
         ok = false;
      }
      
      if ( ok )
      {
         try
         {
            transactionalAccess.beginTransaction();
            
            ok = applyModifications( rtmProvider.getContext()
                                                .getContentResolver(),
                                     modifications );
            if ( ok )
               transactionalAccess.setTransactionSuccessful();
         }
         catch ( Throwable e )
         {
            MolokoApp.Log.e( TAG, LogUtils.GENERIC_DB_ERROR, e );
            ok = false;
         }
         finally
         {
            transactionalAccess.endTransaction();
         }
      }
      
      return ok;
   }
   
   
   
   public final static boolean applyModifications( ContentResolver contentResolver,
                                                   Collection< Modification > modifications )
   {
      boolean ok = true;
      
      final ContentProviderClient client = contentResolver.acquireContentProviderClient( Modifications.CONTENT_URI );
      ok = client != null;
      
      if ( ok )
      {
         final ArrayList< ContentProviderOperation > operations = new ArrayList< ContentProviderOperation >();
         
         for ( Modification modification : modifications )
         {
            // Check id the modification should be stored or simply modify
            // the entity.
            if ( modification.isPersistent() )
            {
               // Check if modification already exists
               final Modification existingMod = getModification( client,
                                                                 modification.getEntityUri(),
                                                                 modification.getColName() );
               if ( existingMod != null )
               {
                  // Check if the new value equals the synced value from the existing modification, if so the
                  // user has reverted his change and we delete the modification.
                  if ( !SyncUtils.hasChanged( existingMod.getSyncedValue(),
                                              modification.getNewValue() ) )
                     operations.add( ContentProviderOperation.newDelete( Queries.contentUriWithId( Modifications.CONTENT_URI,
                                                                                                   existingMod.getId() ) )
                                                             .build() );
                  else
                     // Update the modification with the new value.
                     operations.add( ContentProviderOperation.newUpdate( Queries.contentUriWithId( Modifications.CONTENT_URI,
                                                                                                   existingMod.getId() ) )
                                                             .withValue( Modifications.NEW_VALUE,
                                                                         modification.getNewValue() )
                                                             .build() );
               }
               else
               {
                  // Query the synced value and check if the new value is really different.
                  try
                  {
                     final String syncedValue = getSyncedValue( contentResolver,
                                                                modification.getEntityUri(),
                                                                modification.getColName() );
                     
                     if ( SyncUtils.hasChanged( syncedValue,
                                                modification.getNewValue() ) )
                        // Insert a new modification.
                        operations.add( ContentProviderOperation.newInsert( Modifications.CONTENT_URI )
                                                                .withValues( getContentValues( contentResolver,
                                                                                               modification,
                                                                                               true ) )
                                                                .build() );
                  }
                  catch ( RemoteException e )
                  {
                     ok = false;
                  }
               }
            }
            
            if ( ok )
               // Update the entity itself
               operations.add( ContentProviderOperation.newUpdate( modification.getEntityUri() )
                                                       .withValue( modification.getColName(),
                                                                   modification.getNewValue() )
                                                       .build() );
         }
         
         // Apply the collected operations in a bulk.
         if ( ok && operations.size() > 0 )
         {
            try
            {
               client.applyBatch( operations );
            }
            catch ( RemoteException e )
            {
               MolokoApp.Log.e( TAG, LogUtils.GENERIC_DB_ERROR, e );
               ok = false;
            }
            catch ( OperationApplicationException e )
            {
               MolokoApp.Log.e( TAG, LogUtils.GENERIC_DB_ERROR, e );
               ok = false;
            }
         }
      }
      else
      {
         MolokoApp.Log.e( TAG, LogUtils.GENERIC_DB_ERROR );
      }
      
      if ( client != null )
         client.release();
      
      return ok;
   }
   
   
   
   public static String getSyncedValue( ContentResolver contentResolver,
                                        Uri entityUri,
                                        String colName ) throws RemoteException
   {
      final ContentProviderClient entityClient = contentResolver.acquireContentProviderClient( entityUri );
      
      if ( entityClient == null )
         throw new IllegalArgumentException( "Illegal content URI " + entityUri );
      
      Cursor c = null;
      
      try
      {
         c = entityClient.query( entityUri, new String[]
         { colName }, null, null, null );
         
         entityClient.release();
         
         if ( c == null || c.getCount() == 0 )
            throw new IllegalArgumentException( "Illegal entity URI "
               + entityUri );
         
         if ( !c.moveToFirst() )
            throw new SQLException( "Unable to move to 1st element" );
         
         return c.getString( 0 );
      }
      catch ( RemoteException e )
      {
         MolokoApp.Log.e( TAG, LogUtils.GENERIC_DB_ERROR, e );
         throw e;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
   }
   
   
   
   @Override
   public Uri insert( ContentValues initialValues )
   {
      final Uri insertUri = super.insert( initialValues );
      
      if ( insertUri != null )
      {
         MolokoApp.Log.d( TAG,
                          String.format( "INSERT id=%s, %s",
                                         insertUri.getLastPathSegment(),
                                         initialValues.toString() ) );
      }
      else
      {
         MolokoApp.Log.e( TAG,
                          String.format( "INSERT of %s failed",
                                         initialValues.toString() ) );
      }
      
      return insertUri;
   }
   
   
   
   @Override
   public int update( String id,
                      ContentValues values,
                      String where,
                      String[] whereArgs )
   {
      MolokoApp.Log.d( TAG, String.format( "UPDATE %s, %s",
                                           ( id != null ? id : where ),
                                           values.toString() ) );
      
      return super.update( id, values, where, whereArgs );
   }
   
   
   
   @Override
   public int delete( String id, String where, String[] whereArgs )
   {
      MolokoApp.Log.d( TAG,
                       String.format( "DELETE %s", ( id != null ? id : where ) ) );
      return super.delete( id, where, whereArgs );
   }
   
   
   
   public static ContentProviderOperation getRemoveModificationOps( Uri contentUri,
                                                                    String entityId )
   {
      return getRemoveModificationOps( Queries.contentUriWithId( contentUri,
                                                                 entityId ) );
   }
   
   
   
   public static ContentProviderOperation getRemoveModificationOps( Uri entityUri )
   {
      return ContentProviderOperation.newDelete( Modifications.CONTENT_URI )
                                     .withSelection( new StringBuilder( Modifications.ENTITY_URI ).append( " = '" )
                                                                                                  .append( entityUri )
                                                                                                  .append( "'" )
                                                                                                  .toString(),
                                                     null )
                                     .build();
   }
   
   
   
   private static Modification createModification( Cursor c )
   {
      return new Modification( c.getString( COL_INDICES.get( Modifications._ID ) ),
                               Uri.parse( c.getString( COL_INDICES.get( Modifications.ENTITY_URI ) ) ),
                               c.getString( COL_INDICES.get( Modifications.COL_NAME ) ),
                               Queries.getOptString( c,
                                                     COL_INDICES.get( Modifications.NEW_VALUE ) ),
                               Queries.getOptString( c,
                                                     COL_INDICES.get( Modifications.SYNCED_VALUE ) ),
                               true,
                               true,
                               c.getLong( COL_INDICES.get( Modifications.TIMESTAMP ) ) );
   }
   
   
   
   public ModificationsProviderPart( Context context, SQLiteOpenHelper dbAccess )
   {
      super( context, dbAccess, Modifications.PATH );
   }
   
   
   
   @Override
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
         + " TEXT, " + Modifications.TIMESTAMP + " INTEGER NOT NULL " + ");" );
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
