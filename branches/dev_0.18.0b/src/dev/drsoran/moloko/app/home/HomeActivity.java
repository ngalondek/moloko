/*
 * Copyright (c) 2012 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.app.home;

import android.accounts.Account;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.Intents.HomeAction;
import dev.drsoran.moloko.app.baseactivities.MolokoActivity;
import dev.drsoran.moloko.app.event.IAccountUpdatedListener;
import dev.drsoran.moloko.ui.fragments.dialogs.AboutMolokoDialogFragment;
import dev.drsoran.moloko.ui.layouts.SimpleHomeWidgetLayout;
import dev.drsoran.moloko.ui.widgets.IMolokoHomeWidget;


public class HomeActivity extends MolokoActivity implements OnItemClickListener
{
   private IMolokoHomeWidget addAccountWidget;
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.home_activity );
      
      final GridView gridview = (GridView) findViewById( R.id.home_gridview );
      gridview.setOnItemClickListener( this );
      
      fillGrid();
   }
   
   
   
   @Override
   protected void onResume()
   {
      super.onResume();
      
      setAccountNameAsSubTitle();
      
      final HomeAdapter homeAdapter = getHomeAdapter();
      
      if ( homeAdapter != null )
      {
         homeAdapter.startWidgets();
         onContentChanged();
         showAddAccountWidget( getAppContext().getAccountService()
                                              .getRtmAccount() == null );
      }
   }
   
   
   
   @Override
   protected void onStop()
   {
      final HomeAdapter homeAdapter = getHomeAdapter();
      if ( homeAdapter != null )
      {
         homeAdapter.stopWidgets();
      }
      
      super.onStop();
   }
   
   
   
   @Override
   public boolean onActivityCreateOptionsMenu( Menu menu )
   {
      if ( isWritableAccess() )
      {
         getMenuInflater().inflate( R.menu.home_activity_rwd, menu );
      }
      else
      {
         getMenuInflater().inflate( R.menu.home_activity, menu );
      }
      
      super.onActivityCreateOptionsMenu( menu );
      
      return true;
   }
   
   
   
   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case android.R.id.home:
            showAboutMolokoDialog();
            return true;
            
         case R.id.menu_quick_add_task:
            onAddTask();
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   
   
   
   private void onAddTask()
   {
      startActivity( Intents.createAddTaskIntent( this, null ) );
   }
   
   
   
   @Override
   public void onAccountUpdated( int what, Account account )
   {
      super.onAccountUpdated( what, account );
      showAddAccountWidget( what == IAccountUpdatedListener.ACCOUNT_REMOVED );
   }
   
   
   
   private HomeAdapter getHomeAdapter()
   {
      final GridView gridview = (GridView) findViewById( R.id.home_gridview );
      final HomeAdapter homeAdapter = (HomeAdapter) gridview.getAdapter();
      
      return homeAdapter;
   }
   
   
   
   private void setAccountNameAsSubTitle()
   {
      final Account account = getAppContext().getAccountService()
                                             .getRtmAccount();
      if ( account != null )
      {
         getActionBar().setSubtitle( account.name );
      }
      else
      {
         getActionBar().setSubtitle( null );
      }
   }
   
   
   
   private void fillGrid()
   {
      final GridView gridview = (GridView) findViewById( R.id.home_gridview );
      final HomeAdapter adapter = new HomeAdapter( this );
      
      gridview.setAdapter( adapter );
   }
   
   
   
   @Override
   public void onItemClick( AdapterView< ? > adapterView,
                            View view,
                            int pos,
                            long id )
   {
      final HomeAdapter adapter = (HomeAdapter) ( (GridView) adapterView ).getAdapter();
      
      final Intent intent = adapter.getIntentForWidget( pos );
      if ( intent != null )
      {
         startActivityWithHomeAction( intent, HomeAction.HOME );
      }
   }
   
   
   
   @Override
   protected int[] getFragmentIds()
   {
      return null;
   }
   
   
   
   private void showAddAccountWidget( boolean show )
   {
      if ( show )
      {
         if ( addAccountWidget == null )
         {
            addAccountWidget = new SimpleHomeWidgetLayout( this,
                                                           null,
                                                           R.string.btn_new_account,
                                                           R.drawable.ic_home_add,
                                                           Intents.createNewAccountIntent() );
            getHomeAdapter().addWidget( addAccountWidget );
         }
      }
      else
      {
         if ( addAccountWidget != null )
         {
            getHomeAdapter().removeWidget( addAccountWidget );
            addAccountWidget = null;
         }
      }
   }
   
   
   
   private void showAboutMolokoDialog()
   {
      final DialogFragment dialog = AboutMolokoDialogFragment.newInstance( Bundle.EMPTY );
      dialog.show( getFragmentManager(),
                   String.valueOf( R.id.frag_about_moloko ) );
   }
}
