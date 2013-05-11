/* 
 *	Copyright (c) 2013 Ronny Röhricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.content.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.ContentUris;


abstract class AbstractContentUriHandler implements IContentUriHandler
{
   private final ILog log;
   
   
   
   protected AbstractContentUriHandler( ILog log )
   {
      this.log = log;
   }
   
   
   
   public ILog Log()
   {
      return log;
   }
   
   
   
   @Override
   public final Cursor query( Uri contentUri,
                              String[] projection,
                              String selection,
                              String[] selectionArgs,
                              String sortOrder )
   {
      final long id = ContentUris.getLastPathIdFromUri( contentUri );
      try
      {
         if ( id == Constants.NO_ID )
         {
            return queryAll( contentUri,
                             projection,
                             selection,
                             selectionArgs,
                             sortOrder );
         }
         else
         {
            return queryElement( contentUri,
                                 id,
                                 projection,
                                 selection,
                                 selectionArgs,
                                 sortOrder );
         }
      }
      catch ( Exception e )
      {
         Log().e( getClass(),
                  "Failed to perform a query for content URI '" + contentUri
                     + "'",
                  e );
         return null;
      }
   }
   
   
   
   @Override
   public final long insert( Uri contentUri, ContentValues initialValues )
   {
      if ( initialValues == null )
      {
         throw new IllegalArgumentException( "initialValues" );
      }
      
      final long id = ContentUris.getLastPathIdFromUri( contentUri );
      if ( id != Constants.NO_ID )
      {
         throw new IllegalArgumentException( "Cannot insert into an element. The content URI '"
            + contentUri + "' must not contain an ID." );
      }
      
      try
      {
         return insertElement( contentUri, initialValues );
      }
      catch ( Exception e )
      {
         Log().e( getClass(),
                  "Failed to insert to content URI '" + contentUri + "'",
                  e );
         
         return Constants.NO_ID;
      }
   }
   
   
   
   @Override
   public final int update( Uri contentUri,
                            ContentValues values,
                            String where,
                            String[] whereArgs )
   {
      if ( values == null )
      {
         throw new IllegalArgumentException( "values" );
      }
      
      if ( !TextUtils.isEmpty( where ) )
      {
         throw new UnsupportedOperationException( "An update with a 'where clause' is not supported" );
      }
      
      final long id = ContentUris.getLastPathIdFromUri( contentUri );
      if ( id == Constants.NO_ID )
      {
         throw new IllegalArgumentException( "Cannot update a hole directory. The content URI '"
            + contentUri + "' must contain an element ID." );
      }
      
      try
      {
         return updateElement( contentUri, id, values );
      }
      catch ( Exception e )
      {
         Log().e( getClass(),
                  "Failed to update the content URI '" + contentUri + "'",
                  e );
         return 0;
      }
   }
   
   
   
   @Override
   public final int delete( Uri contentUri, String where, String[] whereArgs )
   {
      final long id = ContentUris.getLastPathIdFromUri( contentUri );
      
      try
      {
         if ( id == Constants.NO_ID )
         {
            return deleteAll( contentUri, where, whereArgs );
         }
         else
         {
            return deleteElement( contentUri, id, where, whereArgs );
         }
      }
      catch ( Exception e )
      {
         Log().e( getClass(),
                  "Failed to delete from content URI '" + contentUri + "'",
                  e );
         return 0;
      }
   }
   
   
   
   protected abstract Cursor queryElement( Uri contentUri,
                                           long id,
                                           String[] projection,
                                           String selection,
                                           String[] selectionArgs,
                                           String sortOrder );
   
   
   
   protected abstract Cursor queryAll( Uri contentUri,
                                       String[] projection,
                                       String selection,
                                       String[] selectionArgs,
                                       String sortOrder );
   
   
   
   protected abstract long insertElement( Uri contentUri,
                                          ContentValues initialValues );
   
   
   
   protected abstract int updateElement( Uri contentUri,
                                         long id,
                                         ContentValues values );
   
   
   
   protected abstract int deleteElement( Uri contentUri,
                                         long id,
                                         String where,
                                         String[] whereArgs );
   
   
   
   protected abstract int deleteAll( Uri contentUri,
                                     String where,
                                     String[] whereArgs );
}
