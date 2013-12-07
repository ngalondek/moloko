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

package dev.drsoran.moloko.content;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;


public class ReadOnlyContentUriHandler implements IContentUriHandler
{
   private final IContentUriHandler decorated;
   
   
   
   public ReadOnlyContentUriHandler( IContentUriHandler decorated )
   {
      if ( decorated == null )
      {
         throw new IllegalArgumentException( "decorated" );
      }
      
      this.decorated = decorated;
   }
   
   
   
   @Override
   public Cursor query( Uri contentUri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder )
   {
      return decorated.query( contentUri,
                              projection,
                              selection,
                              selectionArgs,
                              sortOrder );
   }
   
   
   
   @Override
   public long insert( Uri uri, ContentValues values )
   {
      throw new UnsupportedOperationException( "Insert operation not supported for content URI '"
         + uri + "'." );
   }
   
   
   
   @Override
   public int update( Uri uri,
                      ContentValues values,
                      String selection,
                      String[] selectionArgs )
   {
      throw new UnsupportedOperationException( "Update operation not supported for content URI '"
         + uri + "'." );
   }
   
   
   
   @Override
   public int delete( Uri uri, String selection, String[] selectionArgs )
   {
      throw new UnsupportedOperationException( "Delete operation not supported for content URI '"
         + uri + "'." );
   }
}
