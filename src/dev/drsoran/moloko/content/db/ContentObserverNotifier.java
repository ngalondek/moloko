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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.UriMatcher;
import android.database.ContentObserver;
import android.net.Uri;
import dev.drsoran.moloko.content.ContentUris;


class ContentObserverNotifier
{
   private final List< ContentObserverEntry > registeredContentObservers = new ArrayList< ContentObserverEntry >();
   
   
   
   public void registerContentObserver( ContentObserver contentObserver,
                                        Uri contentUri ) throws IllegalArgumentException
   {
      final int matchType = getMatchTypeForContentUri( contentUri );
      if ( !isContentObserverRegistered( contentObserver ) )
      {
         registeredContentObservers.add( new ContentObserverEntry( matchType,
                                                                   contentObserver ) );
      }
   }
   
   
   
   public void unregisterContentObserver( ContentObserver contentObserver )
   {
      for ( Iterator< ContentObserverEntry > i = registeredContentObservers.iterator(); i.hasNext(); )
      {
         final ContentObserverEntry entry = i.next();
         if ( entry.ContentObserver == contentObserver )
         {
            i.remove();
         }
      }
   }
   
   
   
   public void notifyContentChanged( Uri contentUri ) throws IllegalArgumentException
   {
      final int matchType = getMatchTypeForContentUri( contentUri );
      
      for ( ContentObserverEntry entry : registeredContentObservers )
      {
         if ( entry.UriMatchType == matchType )
         {
            entry.ContentObserver.dispatchChange( false, contentUri );
         }
      }
   }
   
   
   
   private int getMatchTypeForContentUri( Uri contentUri ) throws IllegalArgumentException
   {
      final int matchType = ContentUris.MATCHER.match( contentUri );
      if ( matchType == UriMatcher.NO_MATCH )
      {
         throw new IllegalArgumentException( "The URI '" + contentUri
            + "' cannot be matched to a valid URI" );
      }
      
      return matchType;
   }
   
   
   
   private boolean isContentObserverRegistered( ContentObserver contentObserver )
   {
      for ( ContentObserverEntry entry : registeredContentObservers )
      {
         if ( entry.ContentObserver == contentObserver )
         {
            return true;
         }
      }
      
      return false;
   }
   
   
   private final class ContentObserverEntry
   {
      public final int UriMatchType;
      
      public final ContentObserver ContentObserver;
      
      
      
      public ContentObserverEntry( int uriMatchType,
         ContentObserver contentObserver )
      {
         UriMatchType = uriMatchType;
         ContentObserver = contentObserver;
      }
   }
}
