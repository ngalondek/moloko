/* 
 *	Copyright (c) 2012 Ronny Röhricht
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

package dev.drsoran.moloko.app.prefs;

import java.util.List;

import android.content.ContentProviderClient;
import android.content.Context;

import com.mdt.rtm.data.RtmList;
import com.mdt.rtm.data.RtmLists;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.settings.Settings;
import dev.drsoran.moloko.content.db.RtmListsTable;
import dev.drsoran.moloko.util.ListEntriesAndValues;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.provider.Rtm.Lists;


class RtmListsEntriesAndValuesLoader
{
   public final static int FLAG_INCLUDE_NONE = 1 << 0;
   
   public final static int FLAG_INCLUDE_SMART_LISTS = 1 << 1;
   
   private final Context context;
   
   
   
   public RtmListsEntriesAndValuesLoader( Context context )
   {
      this.context = context;
   }
   
   
   
   public ListEntriesAndValues createEntriesAndValuesSync( int flags )
   {
      ListEntriesAndValues entriesAndValues = null;
      
      // get all lists
      final ContentProviderClient client = context.getContentResolver()
                                                  .acquireContentProviderClient( Lists.CONTENT_URI );
      
      if ( client != null )
      {
         final String selection = getSelectionString( flags );
         
         final RtmLists lists = RtmListsTable.getAllLists( client,
                                                                  selection );
         client.release();
         
         if ( lists != null )
         {
            final List< RtmList > plainLists = lists.getListsPlain();
            final boolean includeNoneElement = ( flags & FLAG_INCLUDE_NONE ) != 0;
            
            entriesAndValues = createEntriesAndValues( plainLists.size(),
                                                       includeNoneElement );
            
            final int startInsertIndex = includeNoneElement
                                                           ? ListEntriesAndValues.NONE_IDX + 1
                                                           : 0;
            for ( int i = 0, cnt = plainLists.size(); i < cnt; ++i )
            {
               entriesAndValues.entries[ i + startInsertIndex ] = plainLists.get( i )
                                                                            .getName();
               entriesAndValues.values[ i + startInsertIndex ] = plainLists.get( i )
                                                                           .getId();
            }
         }
         else
         {
            LogUtils.logDBError( context, getClass(), "Lists" );
         }
      }
      else
      {
         LogUtils.logDBError( context, getClass(), "Lists" );
      }
      
      return entriesAndValues;
   }
   
   
   
   private String getSelectionString( int flags )
   {
      final StringBuilder selectionStringBuilder = new StringBuilder( RtmListsTable.SELECTION_EXCLUDE_DELETED_AND_ARCHIVED );
      
      if ( ( flags & FLAG_INCLUDE_SMART_LISTS ) == 0 )
      {
         selectionStringBuilder.append( " AND " )
                               .append( Lists.IS_SMART_LIST )
                               .append( "=0" );
      }
      
      return selectionStringBuilder.toString();
   }
   
   
   
   private ListEntriesAndValues createEntriesAndValues( int elementCount,
                                                    boolean includeNoneElement )
   {
      int finalElementCount = includeNoneElement ? elementCount + 1
                                                : elementCount; // +1 cause of "none"
      
      final CharSequence[] entries = new CharSequence[ finalElementCount ];
      final CharSequence[] entryValues = new CharSequence[ finalElementCount ];
      
      if ( includeNoneElement )
      {
         entries[ ListEntriesAndValues.NONE_IDX ] = context.getResources()
                                                       .getString( R.string.phr_none_f );
         entryValues[ ListEntriesAndValues.NONE_IDX ] = Settings.NO_DEFAULT_LIST_ID;
      }
      
      final ListEntriesAndValues entriesAndValues = new ListEntriesAndValues();
      entriesAndValues.entries = entries;
      entriesAndValues.values = entryValues;
      
      return entriesAndValues;
   }
}
