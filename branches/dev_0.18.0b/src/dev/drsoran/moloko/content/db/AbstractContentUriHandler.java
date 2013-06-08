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
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.ContentUris;


abstract class AbstractContentUriHandler implements IContentUriHandler
{
   @Override
   public final Cursor query( Uri contentUri,
                              String[] projection,
                              String selection,
                              String[] selectionArgs,
                              String sortOrder )
   {
      final long id = ContentUris.getLastPathIdFromUri( contentUri );
      
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
   
   
   
   @Override
   public final long insert( Uri contentUri, ContentValues initialValues )
   {
      final long id = ContentUris.getLastPathIdFromUri( contentUri );
      if ( id != Constants.NO_ID )
      {
         throw new IllegalArgumentException( "Cannot insert into an element. The content URI '"
            + contentUri + "' must not contain an ID." );
      }
      
      return insertElement( contentUri, initialValues );
   }
   
   
   
   @Override
   public final int update( Uri contentUri,
                            ContentValues values,
                            String where,
                            String[] whereArgs )
   {
      
      final long id = ContentUris.getLastPathIdFromUri( contentUri );
      if ( id == Constants.NO_ID )
      {
         throw new IllegalArgumentException( "Cannot update a hole directory. The content URI '"
            + contentUri + "' must contain an element ID." );
      }
      
      return updateElement( contentUri, id, values );
   }
   
   
   
   @Override
   public final int delete( Uri contentUri, String where, String[] whereArgs )
   {
      final long id = ContentUris.getLastPathIdFromUri( contentUri );
      
      if ( id == Constants.NO_ID )
      {
         return deleteAll( contentUri, where, whereArgs );
      }
      else
      {
         return deleteElement( contentUri, id, where, whereArgs );
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
