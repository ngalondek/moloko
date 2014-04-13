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

import java.text.MessageFormat;

import android.accounts.Account;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.Intents.HomeAction;
import dev.drsoran.moloko.app.baseactivities.MolokoActivity;
import dev.drsoran.moloko.event.IOnTimeChangedListener;


public class HomeActivity extends MolokoActivity implements
         IOnTimeChangedListener, OnItemClickListener
{
   private ActionBarDrawerToggle drawerToggle;
   
   private DrawerLayout drawerLayout;
   
   private ListView drawerList;
   
   private INavigationDrawerHandler activeNavigationHandler;
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.home_activity );
      initializeDrawer();
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
   
   
   
   @Override
   protected void onNewIntent( Intent intent )
   {
      super.onNewIntent( intent );
      
      closeDrawer();
      selectNavigationHandler( intent );
      navigate( intent );
   }
   
   
   
   private void selectNavigationHandler( Intent intent )
   {
      if ( intent.getData() != null )
      {
         activeNavigationHandler = NavigationDrawerHandlerFractory.create( this,
                                                                           intent.getData() );
      }
      else
      {
         activeNavigationHandler = null;
      }
   }
   
   
   
   private void navigate( Intent intent )
   {
      if ( activeNavigationHandler != null )
      {
         activeNavigationHandler.handleIntent( intent );
      }
      else
      {
         Log().e( getClass(),
                  MessageFormat.format( "Unhandled Intent {0}", intent ) );
      }
   }
   
   
   
   private void initializeDrawer()
   {
      drawerLayout = (DrawerLayout) findViewById( R.id.drawer_layout );
      drawerLayout.setDrawerShadow( R.drawable.drawer_shadow,
                                    GravityCompat.START );
      drawerList = (ListView) drawerLayout.findViewById( R.id.left_drawer );
      
      drawerList.setOnItemClickListener( this );
      
      drawerToggle = new ActionBarDrawerToggle( this,
                                                drawerLayout,
                                                R.drawable.ic_drawer,
                                                R.string.app_name,
                                                R.string.app_name )
      {
         @Override
         public void onDrawerClosed( View view )
         {
            setDrawerClosedTitle();
            invalidateOptionsMenu();
         }
         
         
         
         @Override
         public void onDrawerOpened( View drawerView )
         {
            setDrawerOpenedTitle();
         }
      };
      
      drawerLayout.setDrawerListener( drawerToggle );
   }
   
   
   
   @Override
   protected void onStart()
   {
      super.onStart();
      
      selectNavigationHandler( getIntent() );
      navigate( getIntent() );
      
      registerListeners();
      setHomeAdapter();
   }
   
   
   
   @Override
   protected void onResume()
   {
      super.onResume();
      setInitialTitle();
   }
   
   
   
   @Override
   protected void onStop()
   {
      unregisterListeners();
      super.onStop();
   }
   
   
   
   private AbstractHomeNavAdapter getAdapter()
   {
      return (AbstractHomeNavAdapter) drawerList.getAdapter();
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
   public boolean onPrepareOptionsMenu( Menu menu )
   {
      final MenuItem addAccountItem = menu.findItem( R.id.menu_add_account );
      
      if ( addAccountItem != null )
      {
         addAccountItem.setVisible( !drawerLayout.isDrawerOpen( drawerList ) );
      }
      
      return super.onPrepareOptionsMenu( menu );
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
   public void onSyncStatusChanged( int status )
   {
      super.onSyncStatusChanged( status );
      updateWidgets();
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
      updateWidgets();
   }
   
   
   
   private void onSystemTimeChanged()
   {
      updateWidgets();
   }
   
   
   
   @Override
   public void onAccountUpdated( int what, Account account )
   {
      super.onAccountUpdated( what, account );
      
      setHomeAdapter();
      invalidateOptionsMenu();
   }
   
   
   
   @Override
   public void onItemClick( AdapterView< ? > parent,
                            View view,
                            int position,
                            long id )
   {
      final AbstractHomeNavAdapter adapter = getAdapter();
      
      final Intent intent = adapter.getIntentForWidget( position );
      if ( intent != null )
      {
         startActivityWithHomeAction( intent, HomeAction.HOME );
         closeDrawer();
         
         drawerList.setItemChecked( position, true );
      }
   }
   
   
   
   private void closeDrawer()
   {
      drawerLayout.closeDrawer( drawerList );
   }
   
   
   
   private void registerListeners()
   {
      getAppContext().getSystemEvents()
                     .registerOnTimeChangedListener( IOnTimeChangedListener.SYSTEM_TIME
                                                        | IOnTimeChangedListener.MIDNIGHT,
                                                     this );
   }
   
   
   
   private void unregisterListeners()
   {
      getAppContext().getSystemEvents().unregisterOnTimeChangedListener( this );
   }
   
   
   
   private void setInitialTitle()
   {
      if ( drawerLayout.isDrawerVisible( drawerList ) )
      {
         setDrawerOpenedTitle();
      }
      else
      {
         setDrawerClosedTitle();
      }
   }
   
   
   
   private void setDrawerClosedTitle()
   {
      if ( activeNavigationHandler != null )
      {
         activeNavigationHandler.onNavigationDrawerClosed();
      }
      else
      {
         setTitle( R.string.app_name );
         setAccountNameAsSubTitle();
      }
   }
   
   
   
   private void setDrawerOpenedTitle()
   {
      if ( activeNavigationHandler != null )
      {
         activeNavigationHandler.onNavigationDrawerOpened();
      }
      
      setTitle( R.string.app_name );
      getActionBar().setSubtitle( null );
   }
   
   
   
   private void setAccountNameAsSubTitle()
   {
      if ( getAccount() != null )
      {
         getActionBar().setSubtitle( getAccount().name );
      }
      else
      {
         getActionBar().setSubtitle( null );
      }
   }
   
   
   
   private void updateWidgets()
   {
      final AbstractHomeNavAdapter adapter = getAdapter();
      if ( adapter != null )
      {
         adapter.updateWidgets();
      }
   }
   
   
   
   private Account getAccount()
   {
      final Account account = getAppContext().getAccountService()
                                             .getRtmAccount();
      return account;
   }
   
   
   
   private void setHomeAdapter()
   {
      if ( getAccount() != null )
      {
         drawerList.setAdapter( createAdapter() );
      }
      else
      {
         drawerList.setAdapter( createNoAccountAdapter() );
      }
   }
   
   
   
   private AbstractHomeNavAdapter createNoAccountAdapter()
   {
      return new NoAccountHomeNavAdapter( getActionBar().getThemedContext() );
   }
   
   
   
   private AbstractHomeNavAdapter createAdapter()
   {
      return new DefaultHomeNavAdapter( getActionBar().getThemedContext() );
   }
}
