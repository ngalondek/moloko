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
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.Intents.HomeAction;
import dev.drsoran.moloko.app.baseactivities.MolokoActivity;
import dev.drsoran.moloko.app.loaders.SyncTimeLoader;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.event.IOnTimeChangedListener;
import dev.drsoran.rtm.sync.SyncTime;


public class HomeActivity extends MolokoActivity implements
         IOnTimeChangedListener, OnItemClickListener,
         LoaderCallbacks< SyncTime >
{
   private ActionBarDrawerToggle drawerToggle;
   
   private DrawerLayout drawerLayout;
   
   private ListView drawerList;
   
   private long lastSyncMillis = Constants.NO_TIME;
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.home_activity );
      initializeDrawer();
      
      getLoaderManager().initLoader( R.id.loader_sync_time, Bundle.EMPTY, this );
   }
   
   
   
   @Override
   protected void onPostCreate( Bundle savedInstanceState )
   {
      super.onPostCreate( savedInstanceState );
      drawerToggle.syncState();
   }
   
   
   
   @Override
   public void onConfigurationChanged( Configuration newConfig )
   {
      super.onConfigurationChanged( newConfig );
      drawerToggle.onConfigurationChanged( newConfig );
   }
   
   
   
   private void initializeDrawer()
   {
      drawerLayout = (DrawerLayout) findViewById( R.id.drawer_layout );
      drawerLayout.setDrawerShadow( R.drawable.drawer_shadow,
                                    GravityCompat.START );
      
      drawerList = (ListView) drawerLayout.findViewById( android.R.id.list );
      drawerList.setAdapter( new HomeAdapter( this ) );
      
      drawerToggle = new ActionBarDrawerToggle( this,
                                                drawerLayout,
                                                R.drawable.ic_drawer,
                                                R.string.app_name,
                                                R.string.app_name )
      {
         @Override
         public void onDrawerClosed( View view )
         {
            setAccountNameAsSubTitle();
            invalidateOptionsMenu();
         }
         
         
         
         @Override
         public void onDrawerOpened( View drawerView )
         {
            handleDrawerMoves();
         }
         
         
         
         @Override
         public void onDrawerStateChanged( int newState )
         {
            switch ( newState )
            {
               case DrawerLayout.STATE_DRAGGING:
                  handleDrawerMoves();
                  break;
               
               default :
                  break;
            }
            
            super.onDrawerStateChanged( newState );
         }
         
         
         
         private void handleDrawerMoves()
         {
            invalidateOptionsMenu();
            getActionBar().setSubtitle( null );
         }
      };
      
      drawerLayout.setDrawerListener( drawerToggle );
   }
   
   
   
   @Override
   protected void onStart()
   {
      super.onStart();
      registerListeners();
   }
   
   
   
   private void registerListeners()
   {
      getAppContext().getSystemEvents()
                     .registerOnTimeChangedListener( IOnTimeChangedListener.SYSTEM_TIME
                                                        | IOnTimeChangedListener.MIDNIGHT,
                                                     this );
   }
   
   
   
   @Override
   protected void onStop()
   {
      unregisterListeners();
      super.onStop();
   }
   
   
   
   private void unregisterListeners()
   {
      getAppContext().getSystemEvents().unregisterOnTimeChangedListener( this );
   }
   
   
   
   @Override
   protected void onResume()
   {
      super.onResume();
      
      final Account account = getAccount();
      
      setAccountNameAsSubTitle( account );
      setDrawerUsernameAndLastSync( account );
   }
   
   
   
   private HomeAdapter getAdapter()
   {
      return (HomeAdapter) drawerList.getAdapter();
   }
   
   
   
   @Override
   public boolean onActivityCreateOptionsMenu( Menu menu )
   {
      if ( getAccount() == null )
      {
         getMenuInflater().inflate( R.menu.home_activity_no_account, menu );
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
      if ( drawerToggle.onOptionsItemSelected( item ) )
      {
         return true;
      }
      
      switch ( item.getItemId() )
      {
         case R.id.menu_add_account:
            startActivity( Intents.createNewAccountIntent() );
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   
   
   
   @Override
   public Loader< SyncTime > onCreateLoader( int id, Bundle args )
   {
      return new SyncTimeLoader( getAppContext().asDomainContext() );
   }
   
   
   
   @Override
   public void onLoadFinished( Loader< SyncTime > loader, SyncTime data )
   {
      if ( data != null )
      {
         lastSyncMillis = Math.max( data.getLastSyncInMillis(),
                                    data.getLastSyncOutMillis() );
      }
      else
      {
         lastSyncMillis = Constants.NO_TIME;
      }
      
      setLastSyncTime();
   }
   
   
   
   @Override
   public void onLoaderReset( Loader< SyncTime > loader )
   {
      lastSyncMillis = Constants.NO_TIME;
      setLastSyncTime();
   }
   
   
   
   @Override
   public void onSyncStatusChanged( int status )
   {
      super.onSyncStatusChanged( status );
      setLastSyncTime();
   }
   
   
   
   @Override
   public void onTimeChanged( int which )
   {
      switch ( which )
      {
         case IOnTimeChangedListener.MIDNIGHT:
            onMidnight();
            break;
         
         case IOnTimeChangedListener.SYSTEM_TIME:
            onSystemTimeChanged();
            break;
         
         default :
            break;
      }
      
   }
   
   
   
   private void onMidnight()
   {
      final HomeAdapter adapter = getAdapter();
      if ( adapter != null )
      {
         adapter.updateWidgets();
      }
   }
   
   
   
   private void onSystemTimeChanged()
   {
      final HomeAdapter adapter = getAdapter();
      if ( adapter != null )
      {
         adapter.updateWidgets();
      }
   }
   
   
   
   @Override
   public void onAccountUpdated( int what, Account account )
   {
      super.onAccountUpdated( what, account );
      
      setDrawerUsernameAndLastSync( account );
      invalidateOptionsMenu();
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
   
   
   
   private void setAccountNameAsSubTitle()
   {
      setAccountNameAsSubTitle( getAccount() );
   }
   
   
   
   private void setAccountNameAsSubTitle( Account account )
   {
      if ( account != null )
      {
         getActionBar().setSubtitle( account.name );
      }
      else
      {
         getActionBar().setSubtitle( null );
      }
   }
   
   
   
   private void setDrawerUsernameAndLastSync( Account account )
   {
      final TextView userName = (TextView) drawerLayout.findViewById( R.id.user_name );
      
      if ( account != null )
      {
         userName.setText( account.name );
         setLastSyncTime();
      }
      else
      {
         userName.setText( R.string.home_no_account );
         drawerLayout.findViewById( R.id.last_sync_time )
                     .setVisibility( View.GONE );
      }
   }
   
   
   
   private void setLastSyncTime()
   {
      final TextView lastSyncTimeView = (TextView) drawerLayout.findViewById( R.id.last_sync_time );
      final String lastSyncTimeString;
      if ( lastSyncMillis != Constants.NO_TIME )
      {
         final long now = getAppContext().getCalendarProvider()
                                         .getNowMillisUtc();
         
         lastSyncTimeString = DateUtils.getRelativeTimeSpanString( lastSyncMillis,
                                                                   now,
                                                                   DateUtils.MINUTE_IN_MILLIS )
                                       .toString();
      }
      else
      {
         lastSyncTimeString = getString( R.string.phr_unknown );
      }
      
      lastSyncTimeView.setText( getString( R.string.home_last_sync,
                                           lastSyncTimeString ) );
   }
   
   
   
   @Override
   protected int[] getFragmentIds()
   {
      return null;
   }
   
   
   
   private Account getAccount()
   {
      final Account account = getAppContext().getAccountService()
                                             .getRtmAccount();
      return account;
   }
}
