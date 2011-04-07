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

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.RtmBaseColumns;


public abstract class AbstractRtmProviderPart extends AbstractProviderPart
         implements IRtmProviderPart
{
   
   @SuppressWarnings( "unused" )
   private static final String TAG = "Moloko."
      + RtmTasksProviderPart.class.getSimpleName();
   
   

   public AbstractRtmProviderPart( Context context, SQLiteOpenHelper dbAccess,
      String tableName )
   {
      super( context, dbAccess, tableName );
   }
   


   public void create( SQLiteDatabase db ) throws SQLException
   {
      // Trigger: If a RTM entity gets inserted, check for the RTM_ID so
      // it will not be inserted more than once.
      db.execSQL( "CREATE TRIGGER "
         + path
         + "_unique_rtm_id BEFORE INSERT ON "
         + path
         + " SELECT RAISE ( ABORT, 'RTM entity already exists with same rtm_id' ) WHERE EXISTS ( SELECT "
         + RtmBaseColumns.RTM_ID + " FROM " + path + " WHERE new."
         + RtmBaseColumns.RTM_ID + " = " + RtmBaseColumns.RTM_ID + "; END;" );
   }
   


   public void upgrade( SQLiteDatabase db, int oldVersion, int newVersion ) throws SQLException
   {
      Log.w( path, "Upgrading database from version " + oldVersion + " to "
         + newVersion + ", which will destroy all old data" );
      
      drop( db );
      create( db );
   }
   


   public void drop( SQLiteDatabase db )
   {
      db.execSQL( "DROP TABLE IF EXISTS " + path );
   }
   


   public Uri insert( ContentValues initialValues )
   {
      Uri uri = null;
      
      if ( initialValues != null && initialValues.containsKey( BaseColumns._ID ) )
      {
         initialValues = getInitialValues( initialValues );
         
         final SQLiteDatabase db = dbAccess.getWritableDatabase();
         final long rowId = db.insert( path, BaseColumns._ID, initialValues );
         
         if ( rowId > 0 )
         {
            uri = Queries.contentUriWithId( getContentUri(),
                                            initialValues.getAsLong( BaseColumns._ID ) );
         }
      }
      
      return uri;
   }
   


   public int update( String id,
                      ContentValues values,
                      String where,
                      String[] whereArgs )
   {
      SQLiteDatabase db = dbAccess.getWritableDatabase();
      
      int count = 0;
      
      if ( id == null )
      {
         count = db.update( path, values, where, whereArgs );
      }
      else
      {
         StringBuilder sb = new StringBuilder( ITEM_ID_EQUALS ).append( id );
         
         if ( !TextUtils.isEmpty( where ) )
         {
            sb.append( " AND (" ).append( where ).append( ')' );
         }
         
         count = db.update( path, values, sb.toString(), whereArgs );
      }
      
      return count;
   }
   


   public int delete( String id, String where, String[] whereArgs )
   {
      SQLiteDatabase db = dbAccess.getWritableDatabase();
      
      int count = 0;
      
      if ( id == null )
      {
         count = db.delete( path, where, whereArgs );
      }
      else
      {
         StringBuilder sb = new StringBuilder( ITEM_ID_EQUALS ).append( id );
         
         if ( !TextUtils.isEmpty( where ) )
         {
            sb.append( " AND (" ).append( where ).append( ')' );
         }
         
         count = db.delete( path, sb.toString(), whereArgs );
      }
      
      return count;
   }
   


   public String getTableName()
   {
      return path;
   }
   


   protected ContentValues getInitialValues( ContentValues initialValues )
   {
      return initialValues;
   }
}
