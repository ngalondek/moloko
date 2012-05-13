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

package dev.drsoran.moloko.activities;

import android.accounts.Account;
import android.accounts.OnAccountsUpdateListener;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.mdt.rtm.data.RtmAuth;
import com.mdt.rtm.data.RtmAuth.Perms;

import dev.drsoran.moloko.AnnotatedConfigurationSupport;
import dev.drsoran.moloko.IConfigurable;
import dev.drsoran.moloko.IRtmAccessLevelAware;
import dev.drsoran.moloko.ISyncStatusListener;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.listeners.IAlertDialogFragmentListener;
import dev.drsoran.moloko.sync.Constants;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Intents;


public abstract class MolokoFragmentActivity extends SherlockFragmentActivity
         implements IConfigurable, IAlertDialogFragmentListener,
         ISyncStatusListener, OnAccountsUpdateListener, IRtmAccessLevelAware
{
   public final static class StartActivityRequestCode
   {
      public final static int ADD_ACCOUNT = 1;
   }
   
   private final AnnotatedConfigurationSupport annotatedConfigSupport = new AnnotatedConfigurationSupport();
   
   private final Handler handler = new Handler();
   
   private boolean ignoreAccountListenerAfterRegister = true;
   
   private Class< ? extends Activity > homeAsUpTargetActivity = HomeActivity.class;
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      annotatedConfigSupport.onAttach( this, savedInstanceState );
      
      super.onCreate( savedInstanceState );
      
      setupActionBar();
      
      configureByIntent( getIntent() );
      
      if ( savedInstanceState != null )
         configureBySavedInstanceState( savedInstanceState );
      
      AccountUtils.registerAccountListener( this, handler, this );
      
      MolokoApp.getNotifierContext( this )
               .registerSyncStatusChangedListener( this );
   }
   
   
   
   @Override
   protected void onResume()
   {
      super.onResume();
      initializeSyncingProgressIndicator();
   }
   
   
   
   @Override
   protected void onNewIntent( Intent intent )
   {
      super.onNewIntent( intent );
      
      clearConfiguration();
      
      configureByIntent( intent );
      setIntent( intent );
   }
   
   
   
   protected void configureByIntent( Intent intent )
   {
      final Bundle intentExtras = intent.getExtras();
      final Bundle intentConfig;
      
      if ( intentExtras != null )
         intentConfig = new Bundle( intentExtras );
      else
         intentConfig = new Bundle();
      
      configure( intentConfig );
   }
   
   
   
   protected void configureBySavedInstanceState( Bundle savedInstanceState )
   {
      configure( savedInstanceState );
   }
   
   
   
   @Override
   protected void onDestroy()
   {
      annotatedConfigSupport.onDetach();
      
      AccountUtils.unregisterAccountListener( this, this );
      
      MolokoApp.getNotifierContext( this )
               .unregisterSyncStatusChangedListener( this );
      
      super.onDestroy();
   }
   
   
   
   @Override
   protected void onSaveInstanceState( Bundle outState )
   {
      super.onSaveInstanceState( outState );
      annotatedConfigSupport.onSaveInstanceStates( outState );
   }
   
   
   
   @Override
   protected void onRestoreInstanceState( Bundle state )
   {
      annotatedConfigSupport.onRestoreInstanceStates( state );
      super.onRestoreInstanceState( state );
   }
   
   
   
   @Override
   public void reEvaluateRtmAccessLevel( Perms currentAccessLevel )
   {
      invalidateOptionsMenu();
   }
   
   
   
   public boolean isReadOnlyAccess()
   {
      return AccountUtils.isReadOnlyAccess( this );
   }
   
   
   
   public boolean isWritableAccess()
   {
      return AccountUtils.isWriteableAccess( this );
   }
   
   
   
   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      boolean handled = true;
      
      switch ( item.getItemId() )
      {
         case android.R.id.home:
            onActionBarHome();
            break;
         
         case R.id.menu_sync:
            onMenuSync();
            break;
         
         case R.id.menu_search_tasks:
            onMenuSearch();
            break;
         
         case R.id.menu_settings:
            onMenuSettings();
            break;
         
         default :
            handled = false;
      }
      
      return handled || super.onOptionsItemSelected( item );
   }
   
   
   
   public void setHomeAsUpTargetActivity( Class< ? extends Activity > target )
   {
      homeAsUpTargetActivity = target;
   }
   
   
   
   protected boolean isShowHomeAsUp()
   {
      return ( getSupportActionBar().getDisplayOptions() & ActionBar.DISPLAY_HOME_AS_UP ) == ActionBar.DISPLAY_HOME_AS_UP;
   }
   
   
   
   protected boolean onFinishActivityByHome()
   {
      return true;
   }
   
   
   
   @Override
   public < T > void registerAnnotatedConfiguredInstance( T instance,
                                                          Class< T > clazz )
   {
      annotatedConfigSupport.registerInstance( instance, clazz );
   }
   
   
   
   @Override
   public Bundle getConfiguration()
   {
      return annotatedConfigSupport.getInstanceStates();
   }
   
   
   
   @Override
   public void configure( Bundle config )
   {
      annotatedConfigSupport.setInstanceStates( config );
   }
   
   
   
   @Override
   public void clearConfiguration()
   {
      annotatedConfigSupport.setDefaultInstanceStates();
   }
   
   
   
   public Bundle getActivityAndFragmentsConfiguration( int... fragmentIds )
   {
      final Bundle config = new Bundle();
      
      config.putAll( getConfiguration() );
      config.putAll( getFragmentConfigurations( fragmentIds ) );
      
      return config;
   }
   
   
   
   public Bundle getFragmentConfigurations( int... fragmentIds )
   {
      final Bundle config = new Bundle();
      
      for ( int i = 0; i < fragmentIds.length; i++ )
      {
         final int fragId = fragmentIds[ i ];
         final Fragment fragment = getSupportFragmentManager().findFragmentById( fragId );
         
         if ( fragment instanceof IConfigurable )
            config.putAll( ( (IConfigurable) fragment ).getConfiguration() );
      }
      
      return config;
   }
   
   
   
   @Override
   public void onAlertDialogFragmentClick( int dialogId, String tag, int which )
   {
      switch ( dialogId )
      {
         case R.id.dlg_no_account:
            handleNoAccountDialogClick( which );
            break;
         
         default :
            break;
      }
   }
   
   
   
   public void execute( Runnable runnable )
   {
      getHandler().post( runnable );
   }
   
   
   
   protected Handler getHandler()
   {
      return handler;
   }
   
   
   
   protected void handleNoAccountDialogClick( int which )
   {
      if ( which == Dialog.BUTTON_POSITIVE )
      {
         startActivityForResult( Intents.createNewAccountIntent(),
                                 StartActivityRequestCode.ADD_ACCOUNT );
      }
   }
   
   
   
   @Override
   public void onAccountsUpdated( Account[] accounts )
   {
      if ( !ignoreAccountListenerAfterRegister )
      {
         onReEvaluateRtmAccessLevel( AccountUtils.getAccessLevel( this ) );
      }
      else
      {
         ignoreAccountListenerAfterRegister = false;
      }
   }
   
   
   
   @Override
   public void onSyncStatusChanged( int status )
   {
      switch ( status )
      {
         case Constants.SYNC_STATUS_STARTED:
            setSupportProgressBarIndeterminateVisibility( true );
            break;
         
         case Constants.SYNC_STATUS_FINISHED:
            setSupportProgressBarIndeterminateVisibility( false );
            break;
         
         default :
            break;
      }
   }
   
   
   
   protected void onReEvaluateRtmAccessLevel( RtmAuth.Perms currentAccessLevel )
   {
      invalidateOptionsMenu();
      notifyFragmentsAboutRtmAccessLevelChange( currentAccessLevel );
   }
   
   
   
   protected void notifyFragmentsAboutRtmAccessLevelChange( RtmAuth.Perms currentAccessLevel )
   {
      final int[] fragIds = getFragmentIds();
      
      if ( fragIds != null )
      {
         for ( int i = 0; i < fragIds.length; i++ )
         {
            final int fragId = fragIds[ i ];
            final Fragment fragment = getSupportFragmentManager().findFragmentById( fragId );
            
            if ( ( fragment instanceof IRtmAccessLevelAware )
               && fragment.isAdded() )
               ( (IRtmAccessLevelAware) fragment ).reEvaluateRtmAccessLevel( currentAccessLevel );
         }
      }
   }
   
   
   
   protected boolean removeFragmentByTag( String fragmentTag, int transit )
   {
      boolean removed = false;
      
      final Fragment fragment = findAddedFragmentByTag( fragmentTag );
      
      if ( fragment != null )
      {
         final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
         
         transaction.remove( fragment );
         transaction.setTransition( transit );
         
         transaction.commit();
         
         removed = true;
      }
      
      return removed;
   }
   
   
   
   public Fragment findAddedFragmentById( int fragmentId )
   {
      Fragment fragment = getSupportFragmentManager().findFragmentById( fragmentId );
      
      if ( fragment != null && ( !fragment.isAdded() || fragment.isDetached() ) )
         fragment = null;
      
      return fragment;
   }
   
   
   
   public Fragment findAddedFragmentByTag( String fragmentTag )
   {
      Fragment fragment = getSupportFragmentManager().findFragmentByTag( fragmentTag );
      
      if ( fragment != null && !fragment.isAdded() )
         fragment = null;
      
      return fragment;
   }
   
   
   
   protected void setupActionBar()
   {
      requestWindowFeature( Window.FEATURE_INDETERMINATE_PROGRESS );
      getSupportActionBar().setHomeButtonEnabled( true );
   }
   
   
   
   private void initializeSyncingProgressIndicator()
   {
      setSupportProgressBarIndeterminateVisibility( SyncUtils.isSyncing( this ) );
   }
   
   
   
   private void onActionBarHome()
   {
      if ( isShowHomeAsUp() )
      {
         if ( onFinishActivityByHome() )
         {
            startActivity( Intents.createHomeAsUpIntent( this,
                                                         homeAsUpTargetActivity ) );
         }
      }
      else
      {
         startActivity( Intents.createHomeAsUpIntent( this, HomeActivity.class ) );
      }
   }
   
   
   
   private void onMenuSync()
   {
      SyncUtils.requestManualSync( this );
   }
   
   
   
   private void onMenuSearch()
   {
      onSearchRequested();
   }
   
   
   
   private void onMenuSettings()
   {
      startActivity( Intents.createOpenPreferencesIntent( this ) );
   }
   
   
   
   protected abstract int[] getFragmentIds();
}
