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

import java.util.NoSuchElementException;

import android.content.UriMatcher;
import android.net.Uri;
import android.util.SparseArray;


public final class UriLookup< T >
{
   private final SparseArray< T > lookUp = new SparseArray< T >();
   
   private final UriMatcher uriMatcher;
   
   
   
   public UriLookup( UriMatcher uriMatcher )
   {
      this.uriMatcher = uriMatcher;
   }
   
   
   
   public void put( T object, Uri uri, Uri... otherUris ) throws IllegalArgumentException
   {
      if ( !contains( object ) )
      {
         putUri( uri, object );
         
         for ( Uri otherUri : otherUris )
         {
            putUri( otherUri, object );
         }
      }
   }
   
   
   
   public T get( Uri uri ) throws NoSuchElementException
   {
      final int matchType = getMatchTypeForUri( uri );
      return lookUp.get( matchType );
   }
   
   
   
   public boolean canLookUp( Uri uri )
   {
      return tryGetMatchTypeForUri( uri ) != UriMatcher.NO_MATCH;
   }
   
   
   
   public boolean contains( T object )
   {
      return lookUp.indexOfValue( object ) > -1;
   }
   
   
   
   public void remove( Uri uri ) throws NoSuchElementException
   {
      final int matchType = getMatchTypeForUri( uri );
      lookUp.remove( matchType );
   }
   
   
   
   public void removeAll()
   {
      lookUp.clear();
   }
   
   
   
   private void putUri( Uri uri, T object ) throws IllegalArgumentException
   {
      final int matchType = tryGetMatchTypeForUri( uri );
      if ( matchType == UriMatcher.NO_MATCH )
      {
         throw new IllegalArgumentException( "The URI '" + uri
            + "' is not valid." );
      }
      
      lookUp.put( matchType, object );
   }
   
   
   
   private int getMatchTypeForUri( Uri uri ) throws NoSuchElementException
   {
      final int matchType = tryGetMatchTypeForUri( uri );
      if ( matchType == UriMatcher.NO_MATCH )
      {
         throw new NoSuchElementException( "The URI '" + uri
            + "' cannot be matched to an existing URI" );
      }
      
      return matchType;
   }
   
   
   
   private int tryGetMatchTypeForUri( Uri uri )
   {
      final int matchType = uriMatcher.match( uri );
      return matchType;
   }
}
