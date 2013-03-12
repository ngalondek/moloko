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

package dev.drsoran.moloko.loaders;

import java.util.List;

import android.content.ContentProviderClient;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.ListOverviewsProviderPart;
import dev.drsoran.provider.Rtm.ListOverviews;
import dev.drsoran.rtm.RtmListWithTaskCount;


public class RtmListWithTaskCountLoader extends
         AbstractLoader< List< RtmListWithTaskCount > >
{
   public final static int ID = R.id.loader_lists_with_task_count;
   
   private final String slelection;
   
   private boolean expandLists;
   
   
   
   public RtmListWithTaskCountLoader( Context context )
   {
      this( context, RtmListsTable.SELECTION_EXCLUDE_DELETED_AND_ARCHIVED );
   }
   
   
   
   public RtmListWithTaskCountLoader( Context context, String selection )
   {
      super( context );
      this.slelection = selection;
   }
   
   
   
   public void setPreExpandExtendedListInfoAfterLoad( boolean expandLists )
   {
      this.expandLists = expandLists;
   }
   
   
   
   @Override
   protected List< RtmListWithTaskCount > queryResultInBackground( ContentProviderClient client )
   {
      final List< RtmListWithTaskCount > lists = ListOverviewsProviderPart.getListsOverview( client,
                                                                                             slelection );
      if ( lists != null && expandLists )
      {
         for ( RtmListWithTaskCount rtmListWithTaskCount : lists )
         {
            rtmListWithTaskCount.getExtendedListInfo( getContext() );
         }
      }
      
      return lists;
   }
   
   
   
   @Override
   protected Uri getContentUri()
   {
      return ListOverviews.CONTENT_URI;
   }
   
   
   
   @Override
   protected void registerContentObserver( ContentObserver observer )
   {
      ListOverviewsProviderPart.registerContentObserver( getContext(), observer );
   }
   
   
   
   @Override
   protected void unregisterContentObserver( ContentObserver observer )
   {
      ListOverviewsProviderPart.unregisterContentObserver( getContext(),
                                                           observer );
   }
}
