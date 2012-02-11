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

package dev.drsoran.moloko.prefs;

import java.util.List;

import android.content.ContentProviderClient;
import android.content.Context;

import com.mdt.rtm.data.RtmList;
import com.mdt.rtm.data.RtmLists;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.Settings;
import dev.drsoran.moloko.content.RtmListsProviderPart;
import dev.drsoran.moloko.prefs.DefaultListPreference.EntriesAndValues;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.provider.Rtm.Lists;


class RtmListsEntriesAndValuesLoader
{
   private final Context context;
   
   
   
   public RtmListsEntriesAndValuesLoader( Context context )
   {
      this.context = context;
   }
   
   
   
   public EntriesAndValues createEntriesAndValuesSync()
   {
      EntriesAndValues entriesAndValues = null;
      
      // get all lists
      final ContentProviderClient client = context.getContentResolver()
                                                  .acquireContentProviderClient( Lists.CONTENT_URI );
      
      if ( client != null )
      {
         final RtmLists lists = RtmListsProviderPart.getAllLists( client,
                                                                  RtmListsProviderPart.SELECTION_EXCLUDE_DELETED_AND_ARCHIVED );
         client.release();
         
         if ( lists != null )
         {
            final List< RtmList > plainLists = lists.getListsPlain();
            
            final CharSequence[] entries = new CharSequence[ plainLists.size() + 1 ]; // +1
            // cause
            // of
            // "none"
            final CharSequence[] entryValues = new CharSequence[ plainLists.size() + 1 ]; // +1
            // cause
            // of
            // "none"
            
            entries[ EntriesAndValues.NONE_IDX ] = context.getResources()
                                                          .getString( R.string.phr_none_f );
            entryValues[ EntriesAndValues.NONE_IDX ] = Settings.NO_DEFAULT_LIST_ID;
            
            for ( int i = 0, cnt = plainLists.size(); i < cnt; ++i )
            {
               entryValues[ i + 1 ] = plainLists.get( i ).getId();
               entries[ i + 1 ] = plainLists.get( i ).getName();
            }
            
            entriesAndValues = new EntriesAndValues();
            entriesAndValues.entries = entries;
            entriesAndValues.values = entryValues;
         }
         else
         {
            LogUtils.logDBError( context,
                                 LogUtils.toTag( DefaultListPreference.class ),
                                 "Lists" );
         }
      }
      else
      {
         LogUtils.logDBError( context,
                              LogUtils.toTag( DefaultListPreference.class ),
                              "Lists" );
      }
      
      return entriesAndValues;
   }
}
