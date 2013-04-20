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

import java.util.Collection;

import android.database.ContentObserver;
import android.net.Uri;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.content.UriLookup;


abstract class ContentObserverNotififer implements ITableChangedObserver
{
   private final ITable[] tables;
   
   private final UriLookup< Uri > tableUriToContentUri;
   
   private final UriLookup< ContentObserver > contentObservers;
   
   
   
   public ContentObserverNotififer( RtmDatabase database, ITable... tables )
   {
      this.tables = tables;
      this.tableUriToContentUri = createTableUriToContentUriLookUp( database );
      this.contentObservers = new UriLookup< ContentObserver >( ContentUris.MATCHER );
      
      registerTableListeners();
   }
   
   
   
   public void shutdown()
   {
      unregisterTableListeners();
   }
   
   
   
   @Override
   public void onTableChanged( Uri tableUri )
   {
      final Collection< Uri > matchingContentUris = tableUriToContentUri.get( tableUri );
      for ( Uri matchingContentUri : matchingContentUris )
      {
         for ( ContentObserver contentObserver : contentObservers.get( matchingContentUri ) )
         {
            contentObserver.dispatchChange( false, matchingContentUri );
         }
      }
   }
   
   
   
   private UriLookup< Uri > createTableUriToContentUriLookUp( RtmDatabase database )
   {
      final UriLookup< Uri > lookup = new UriLookup< Uri >( database.getTableUriMatcher() );
      
      for ( ITable table : tables )
      {
         mapTableUrisToContentUris( lookup, table );
      }
      
      return lookup;
   }
   
   
   
   private void registerTableListeners()
   {
      for ( ITable table : tables )
      {
         table.registerTableChangedObserver( this );
      }
   }
   
   
   
   private void unregisterTableListeners()
   {
      for ( ITable table : tables )
      {
         table.unregisterTableChangeObserver( this );
      }
   }
   
   
   
   public abstract void mapTableUrisToContentUris( UriLookup< Uri > tableUriToContentUriLookUp,
                                                   ITable table );
}
