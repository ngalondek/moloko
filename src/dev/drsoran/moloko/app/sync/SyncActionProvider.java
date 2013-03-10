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

package dev.drsoran.moloko.app.sync;

import android.accounts.Account;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

import com.actionbarsherlock.view.ActionProvider;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.app.services.ISyncService;
import dev.drsoran.moloko.app.services.SyncStartResult;
import dev.drsoran.moloko.util.ShowButtonTextAsToast;


public class SyncActionProvider extends ActionProvider
{
   private final AppContext context;
   
   private final ISyncActionProviderHost host;
   
   private final ISyncService syncService;
   
   
   
   public SyncActionProvider( AppContext context )
   {
      super( context );
      this.context = context;
      
      final Context hostContext = context instanceof ContextWrapper
                                                                   ? ( (ContextWrapper) context ).getBaseContext()
                                                                   : context;
      if ( hostContext instanceof ISyncActionProviderHost )
      {
         host = (ISyncActionProviderHost) hostContext;
      }
      else
      {
         host = null;
      }
      
      this.syncService = context.getSyncService();
   }
   
   
   
   @Override
   public View onCreateActionView()
   {
      if ( host != null )
      {
         host.onSyncActionProviderViewCreated();
      }
      
      final LayoutInflater inflater = LayoutInflater.from( context );
      final Account account = context.getAccountService().getRtmAccount();
      final int layoutRes = account != null && syncService.isSyncing( account )
                                                                               ? R.layout.app_spinner
                                                                               : R.layout.action_provider_sync;
      
      final View view = inflater.inflate( layoutRes, null, false );
      
      final View button = view.findViewById( android.R.id.button1 );
      if ( button != null )
      {
         button.setOnClickListener( new OnClickListener()
         {
            @Override
            public void onClick( View v )
            {
               startSync();
            }
         } );
         
         button.setLongClickable( true );
         button.setOnLongClickListener( new OnLongClickListener()
         {
            @Override
            public boolean onLongClick( View v )
            {
               new ShowButtonTextAsToast( context ).show( view,
                                                          context.getString( R.string.phr_do_sync ) );
               return true;
            }
         } );
      }
      
      return view;
   }
   
   
   
   @Override
   public boolean onPerformDefaultAction()
   {
      return startSync();
   }
   
   
   
   private boolean startSync()
   {
      final Account rtmAccount = context.getAccountService().getRtmAccount();
      
      boolean ok = rtmAccount != null;
      if ( ok )
      {
         final SyncStartResult res = syncService.requestManualSync( rtmAccount );
         if ( res == SyncStartResult.NotConnected )
         {
            host.showNotConnectedDialog();
            ok = false;
         }
      }
      else if ( host != null )
      {
         host.showNoAccountDialog();
      }
      
      return ok;
   }
}
