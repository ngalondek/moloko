/* 
 *	Copyright (c) 2011 Ronny Röhricht
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

import android.content.ContentProvider;
import android.content.ContentProviderClient;
import android.content.Context;
import android.os.AsyncTask;
import dev.drsoran.moloko.app.MolokoApp;
import dev.drsoran.moloko.app.account.AccountUtils;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.provider.Rtm;


class ApplyContentProviderActionItemsTask extends
         AsyncTask< ContentProviderActionItemList, Void, Boolean >
{
   private final Context context;
   
   
   
   public ApplyContentProviderActionItemsTask( Context context )
   {
      this.context = context;
   }
   
   
   
   @Override
   protected Boolean doInBackground( ContentProviderActionItemList... params )
   {
      if ( params == null )
         return Boolean.FALSE;
      
      if ( params.length == 0 || params[ 0 ].size() == 0 )
         return Boolean.TRUE;
      
      if ( AccountUtils.isReadOnlyAccess( context ) )
      {
         throw new UnsupportedOperationException( LogUtils.DB_READ_ONLY_ERROR );
      }
      
      final ContentProviderClient client = context.getContentResolver()
                                                  .acquireContentProviderClient( Rtm.AUTHORITY );
      boolean ok = client != null;
      if ( ok )
      {
         final ContentProvider localProvider = client.getLocalContentProvider();
         ok = localProvider instanceof RtmProvider;
         
         if ( ok )
         {
            final ContentProviderActionItemList list = params[ 0 ];
            ok = list.applyTransactional( (RtmProvider) localProvider );
         }
         else
         {
            MolokoApp.Log.e( getClass(), "Required local content is of type "
               + RtmProvider.class.getSimpleName() );
         }
      }
      else
      {
         MolokoApp.Log.e( getClass(), LogUtils.GENERIC_DB_ERROR );
      }
      
      if ( client != null )
      {
         client.release();
      }
      
      return ok;
   }
}
