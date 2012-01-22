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

package dev.drsoran.moloko.util;

import android.content.ContentProvider;
import android.content.ContentProviderClient;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import dev.drsoran.moloko.content.ContentProviderActionItemList;
import dev.drsoran.moloko.content.RtmProvider;
import dev.drsoran.provider.Rtm;


public class ApplyContentProviderActionItemsTask extends
         AsyncTask< ContentProviderActionItemList, Void, Boolean >
{
   private final static String TAG = "Moloko."
      + ApplyContentProviderActionItemsTask.class.getSimpleName();
   
   private final FragmentActivity activity;
   
   @SuppressWarnings( "unused" )
   // Can be null
   private final String progressMsg;
   
   
   
   public ApplyContentProviderActionItemsTask( FragmentActivity activity,
      String progressText )
   {
      if ( activity == null )
         throw new NullPointerException( "activity is null" );
      
      this.activity = activity;
      this.progressMsg = progressText;
   }
   
   
   
   @Override
   protected Boolean doInBackground( ContentProviderActionItemList... params )
   {
      if ( params == null )
         return Boolean.FALSE;
      
      if ( params.length == 0 || params[ 0 ].size() == 0 )
         return Boolean.TRUE;
      
      if ( AccountUtils.isReadOnlyAccess( activity ) )
         throw new SecurityException( LogUtils.DB_READ_ONLY_ERROR );
      
      final ContentProviderClient client = activity.getContentResolver()
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
            Log.e( TAG, "Required local content is of type "
               + RtmProvider.class.getSimpleName() );
         }
      }
      else
      {
         Log.e( TAG, LogUtils.GENERIC_DB_ERROR );
      }
      
      if ( client != null )
         client.release();
      
      return ok;
   }
}
