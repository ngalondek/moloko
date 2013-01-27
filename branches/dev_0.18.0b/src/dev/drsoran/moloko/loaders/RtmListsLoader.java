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

import android.content.ContentProviderClient;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;

import com.mdt.rtm.data.RtmLists;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.RtmListsProviderPart;
import dev.drsoran.provider.Rtm.Lists;


public class RtmListsLoader extends AbstractLoader< RtmLists >
{
   public final static int ID = R.id.loader_lists;
   
   private final String slelection;
   
   
   
   public RtmListsLoader( Context context )
   {
      this( context, Lists.LIST_DELETED + " IS NULL" );
   }
   
   
   
   public RtmListsLoader( Context context, String selection )
   {
      super( context );
      this.slelection = selection;
   }
   
   
   
   @Override
   protected RtmLists queryResultInBackground( ContentProviderClient client )
   {
      return RtmListsProviderPart.getAllLists( client, slelection );
   }
   
   
   
   @Override
   protected Uri getContentUri()
   {
      return Lists.CONTENT_URI;
   }
   
   
   
   @Override
   protected void registerContentObserver( ContentObserver observer )
   {
      getContext().getContentResolver()
                  .registerContentObserver( getContentUri(), true, observer );
   }
   
   
   
   @Override
   protected void unregisterContentObserver( ContentObserver observer )
   {
      getContext().getContentResolver().unregisterContentObserver( observer );
   }
}
