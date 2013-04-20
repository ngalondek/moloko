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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import android.content.UriMatcher;
import android.net.Uri;


public final class UriLookup< T >
{
   private final List< UriLookUpEntry > lookUp = new ArrayList< UriLookUpEntry >();
   
   private final UriMatcher uriMatcher;
   
   
   
   public UriLookup( UriMatcher uriMatcher )
   {
      this.uriMatcher = uriMatcher;
   }
   
   
   
   public void put( T object, Uri uri, Uri... otherUris ) throws IllegalArgumentException
   {
      if ( !contains( object ) )
      {
         int matchType = getMatchTypeForUri( uri );
         lookUp.add( new UriLookUpEntry( matchType, object ) );
         
         for ( Uri otherUri : otherUris )
         {
            matchType = getMatchTypeForUri( otherUri );
            lookUp.add( new UriLookUpEntry( matchType, object ) );
         }
      }
   }
   
   
   
   public Collection< T > get( Uri uri ) throws IllegalArgumentException
   {
      final int matchType = getMatchTypeForUri( uri );
      final Collection< T > matches = new ArrayList< T >();
      
      for ( UriLookUpEntry entry : lookUp )
      {
         if ( entry.UriMatchType == matchType )
         {
            matches.add( entry.Object );
         }
      }
      
      return matches;
   }
   
   
   
   public boolean canLookUp( Uri uri )
   {
      return tryGetMatchTypeForUri( uri ) != UriMatcher.NO_MATCH;
   }
   
   
   
   public boolean contains( T object )
   {
      for ( UriLookUpEntry entry : lookUp )
      {
         if ( entry.Object == object )
         {
            return true;
         }
      }
      
      return false;
   }
   
   
   
   public void remove( T object, Uri uri ) throws IllegalArgumentException
   {
      final int matchType = getMatchTypeForUri( uri );
      for ( Iterator< UriLookUpEntry > i = lookUp.iterator(); i.hasNext(); )
      {
         final UriLookUpEntry entry = i.next();
         if ( entry.Object == object && entry.UriMatchType == matchType )
         {
            i.remove();
         }
      }
   }
   
   
   
   public void removeAll( T object )
   {
      for ( Iterator< UriLookUpEntry > i = lookUp.iterator(); i.hasNext(); )
      {
         final UriLookUpEntry entry = i.next();
         if ( entry.Object == object )
         {
            i.remove();
         }
      }
   }
   
   
   
   private int getMatchTypeForUri( Uri uri ) throws IllegalArgumentException
   {
      final int matchType = tryGetMatchTypeForUri( uri );
      if ( matchType == UriMatcher.NO_MATCH )
      {
         throw new IllegalArgumentException( "The URI '" + uri
            + "' cannot be matched to a valid URI" );
      }
      
      return matchType;
   }
   
   
   
   private int tryGetMatchTypeForUri( Uri uri )
   {
      final int matchType = uriMatcher.match( uri );
      return matchType;
   }
   
   
   private final class UriLookUpEntry
   {
      public final int UriMatchType;
      
      public final T Object;
      
      
      
      public UriLookUpEntry( int uriMatchType, T object )
      {
         UriMatchType = uriMatchType;
         Object = object;
      }
   }
}
