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

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.os.AsyncTask;
import dev.drsoran.moloko.content.RtmProvider;
import dev.drsoran.moloko.content.TransactionalAccess;
import dev.drsoran.provider.Rtm;


public class ApplyContentProviderOperationsTask extends
         AsyncTask< ArrayList< ContentProviderOperation >, Void, Boolean >
{
   private final Activity activity;
   
   private final int progressTitle;
   
   private final boolean transactional;
   
   private ProgressDialog dialog;
   
   
   
   public ApplyContentProviderOperationsTask( Activity activity,
      int progressTitle, boolean transactional )
   {
      if ( activity == null )
         throw new NullPointerException( "activity is null" );
      
      this.activity = activity;
      this.progressTitle = progressTitle;
      this.transactional = transactional;
   }
   
   
   
   @Override
   protected void onPreExecute()
   {
      dialog = new ProgressDialog( activity );
      
      if ( progressTitle != -1 )
         dialog.setMessage( activity.getString( progressTitle ) );
      
      dialog.setOwnerActivity( activity );
      dialog.setCancelable( false );
      dialog.show();
   }
   
   
   
   @Override
   protected Boolean doInBackground( ArrayList< ContentProviderOperation >... params )
   {
      if ( params == null )
         return Boolean.FALSE;
      
      if ( params.length == 0 || params[ 0 ].size() == 0 )
         return Boolean.TRUE;
      
      boolean ok = true;
      
      try
      {
         if ( transactional )
         {
            final ContentProviderClient client = activity.getContentResolver()
                                                         .acquireContentProviderClient( Rtm.AUTHORITY );
            if ( client != null )
            {
               if ( !( client.getLocalContentProvider() instanceof RtmProvider ) )
                  ok = false;
               
               if ( ok )
               {
                  final RtmProvider rtmProvider = (RtmProvider) client.getLocalContentProvider();
                  final TransactionalAccess transactionalAccess = rtmProvider.newTransactionalAccess();
                  
                  try
                  {
                     transactionalAccess.beginTransaction();
                     
                     rtmProvider.applyBatch( params[ 0 ] );
                     
                     transactionalAccess.setTransactionSuccessful();
                  }
                  catch ( Throwable e )
                  {
                     ok = false;
                  }
                  finally
                  {
                     transactionalAccess.endTransaction();
                  }
               }
               
               client.release();
            }
            else
               ok = false;
         }
         else
            activity.getContentResolver()
                    .applyBatch( Rtm.AUTHORITY, params[ 0 ] );
      }
      catch ( Throwable e )
      {
         ok = false;
      }
      
      return Boolean.valueOf( ok );
   }
   
   
   
   @Override
   protected void onPostExecute( Boolean result )
   {
      if ( dialog != null )
         dialog.dismiss();
   }
}
