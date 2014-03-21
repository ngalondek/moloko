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

package dev.drsoran.moloko.app.baseactivities;

import android.accounts.Account;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import dev.drsoran.moloko.IConfigurable;
import dev.drsoran.moloko.IHandlerToken;
import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.Intents.HomeAction;
import dev.drsoran.moloko.app.event.IAccountUpdatedListener;
import dev.drsoran.moloko.app.event.ISyncStatusListener;
import dev.drsoran.moloko.app.services.IAccountService;
import dev.drsoran.moloko.app.services.SyncStartResult;
import dev.drsoran.moloko.app.sync.ISyncActionProviderHost;
import dev.drsoran.moloko.app.sync.SyncConstants;
import dev.drsoran.moloko.state.AnnotatedConfigurationSupport;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.moloko.ui.fragments.dialogs.AlertDialogFragment;
import dev.drsoran.moloko.ui.fragments.listeners.IAlertDialogFragmentListener;
import dev.drsoran.rtm.service.RtmServicePermission;


public abstract class MolokoActivity extends Activity implements IConfigurable,
         IAlertDialogFragmentListener, ISyncStatusListener,
         ISyncActionProviderHost, IAccountUpdatedListener
{
   public final static class StartActivityRequestCode
   {
      public final static int ADD_ACCOUNT = 1;
   }
   
   private final AnnotatedConfigurationSupport annotatedConfigSupport = new AnnotatedConfigurationSupport();
   
   private AppContext appContext;
   
   private IHandlerToken handlerToken;
   
   @InstanceState( key = Intents.Extras.HOME_ACTION,
                   defaultValue = Intents.HomeAction.BACK )
   private String homeAction;
   
   @InstanceState( key = Intents.Extras.HOME_AS_UP_ACTIVITY,
                   defaultValue = InstanceState.NULL )
   private String homeAsUpTargetActivity;
   
   private boolean disableSearch;
   
   private boolean showSyncProgress;
   
   private boolean showSyncProgressByActionProvider;
   
   private boolean listenersRegistered;
   
   
   
   protected MolokoActivity()
   {
      annotatedConfigSupport.registerInstance( this, MolokoActivity.class );
   }
   
   
   
   public AppContext getAppContext()
   {
      return appContext;
   }
   
   
   
   public ILog Log()
   {
      return appContext.Log();
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      appContext = AppContext.get( this );
      annotatedConfigSupport.onAttach( appContext.asSystemContext(),
                                       savedInstanceState );
      handlerToken = getAppContext().acquireHandlerToken();
      
      setupActionBar();
      
      super.onCreate( savedInstanceState );
      
      configureByIntent( getIntent() );
      if ( savedInstanceState != null )
      {
         configureBySavedInstanceState( savedInstanceState );
      }
   }
   
   
   
   @Override
   protected void onStart()
   {
      super.onStart();
      if ( !listenersRegistered )
      {
         onRegisterListeners();
         listenersRegistered = true;
      }
   }
   
   
   
   @Override
   protected void onResume()
   {
      super.onResume();
      initializeSyncingProgressIndicator();
   }
   
   
   
   @Override
   protected void onStop()
   {
      onUnregisterListeners();
      listenersRegistered = false;
      
      super.onStop();
   }
   
   
   
   @Override
   protected void onNewIntent( Intent intent )
   {
      super.onNewIntent( intent );
      
      clearConfiguration();
      
      configureByIntent( intent );
      setIntent( intent );
   }
   
   
   
   protected void onRegisterListeners()
   {
      getAppContext().getAppEvents().registerAccountUpdatedListener( this );
      getAppContext().getAppEvents().registerSyncStatusChangedListener( this );
   }
   
   
   
   protected void onUnregisterListeners()
   {
      getAppContext().getAppEvents().unregisterAccountUpdatedListener( this );
      getAppContext().getAppEvents().unregisterSyncStatusChangedListener( this );
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
   public final boolean onCreateOptionsMenu( Menu menu )
   {
      onActivityCreateOptionsMenu( menu );
      
      // If the sync icon is displayed as action item, the
      // flag will be set correctly. This may happen if the
      // icon is first shown and then pushed into overflow
      // if a fragment adds new items.
      if ( menu.findItem( R.id.menu_sync ) != null )
      {
         setShowSyncProgress( true );
         showSyncProgressByActionProvider = false;
      }
      
      return true;
   }
   
   
   
   public boolean onActivityCreateOptionsMenu( Menu menu )
   {
      return super.onCreateOptionsMenu( menu );
   }
   
   
   
   @Override
   protected void onDestroy()
   {
      annotatedConfigSupport.onDetach();
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
   public void onActionModeStarted( ActionMode mode )
   {
      disableSearch = true;
      super.onActionModeStarted( mode );
   }
   
   
   
   @Override
   public void onActionModeFinished( ActionMode mode )
   {
      disableSearch = false;
      super.onActionModeFinished( mode );
   }
   
   
   
   public void startActivityWithHomeAction( Intent intent, String homeAction )
   {
      startActivity( intent.putExtra( Intents.Extras.HOME_ACTION, homeAction ) );
   }
   
   
   
   public void startActivityWithHomeAction( Intent intent,
                                            Class< ? > homeAsUpActivity )
   {
      startActivity( intent.putExtra( Intents.Extras.HOME_ACTION,
                                      HomeAction.ACTIVITY )
                           .putExtra( Intents.Extras.HOME_AS_UP_ACTIVITY,
                                      homeAsUpActivity.getName() ) );
   }
   
   
   
   public void startActivityPreserveHomeAction( Intent intent )
   {
      if ( homeAction != null )
      {
         intent.putExtra( Intents.Extras.HOME_ACTION, homeAction );
      }
      
      if ( homeAsUpTargetActivity != null )
      {
         intent.putExtra( Intents.Extras.HOME_AS_UP_ACTIVITY,
                          homeAsUpTargetActivity );
      }
      
      startActivity( intent );
   }
   
   
   
   public String getHomeAction()
   {
      return homeAction;
   }
   
   
   
   public String getHomeAsUpTargetActivity()
   {
      return homeAsUpTargetActivity;
   }
   
   
   
   public boolean isReadOnlyAccess()
   {
      final IAccountService accountService = appContext.getAccountService();
      return accountService.isReadOnlyAccess( accountService.getRtmAccount() );
   }
   
   
   
   public boolean isWritableAccess()
   {
      final IAccountService accountService = appContext.getAccountService();
      return accountService.isWriteableAccess( accountService.getRtmAccount() );
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
   
   
   
   protected boolean isShowHomeAsUp()
   {
      final ActionBar actionBar = getActionBar();
      return actionBar != null
         && ( actionBar.getDisplayOptions() & ActionBar.DISPLAY_HOME_AS_UP ) == ActionBar.DISPLAY_HOME_AS_UP;
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
         final Fragment fragment = getFragmentManager().findFragmentById( fragId );
         
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
         
         case R.id.dlg_not_connected:
            if ( which == Dialog.BUTTON_NEUTRAL )
            {
               startActivity( new Intent( android.provider.Settings.ACTION_WIRELESS_SETTINGS ) );
            }
            break;
         
         default :
            break;
      }
   }
   
   
   
   public void execute( Runnable runnable )
   {
      handlerToken.post( runnable );
   }
   
   
   
   public void executeDelayed( Runnable runnable, int delayMillis )
   {
      handlerToken.postDelayed( runnable, delayMillis );
   }
   
   
   
   public IHandlerToken getHandlerToken()
   {
      return handlerToken;
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
   public void onAccountUpdated( int what, Account account )
   {
      final IAccountService accountService = appContext.getAccountService();
      final RtmServicePermission accessLevel = accountService.getAccessLevel( accountService.getRtmAccount() );
      
      onReEvaluateRtmAccessLevel( accessLevel );
   }
   
   
   
   public void setShowSyncProgress( boolean show )
   {
      if ( showSyncProgress != show )
      {
         showSyncProgress = show;
         initializeSyncingProgressIndicator();
      }
   }
   
   
   
   public boolean getShowSyncProgress()
   {
      return showSyncProgress;
   }
   
   
   
   @Override
   public void onSyncStatusChanged( int status )
   {
      if ( showSyncProgressByActionProvider )
      {
         invalidateOptionsMenu();
      }
      
      if ( showSyncProgress )
      {
         switch ( status )
         {
            case SyncConstants.SYNC_STATUS_STARTED:
               setProgressBarIndeterminateVisibility( true );
               break;
            
            case SyncConstants.SYNC_STATUS_FINISHED:
               setProgressBarIndeterminateVisibility( false );
               break;
            
            default :
               break;
         }
      }
   }
   
   
   
   @Override
   public void onSyncActionProviderViewCreated()
   {
      showSyncProgressByActionProvider = true;
      setShowSyncProgress( false );
   }
   
   
   
   @Override
   public void showNoAccountDialog()
   {
      new AlertDialogFragment.Builder( R.id.dlg_no_account ).setTitle( getString( R.string.dlg_no_account_title ) )
                                                            .setIcon( R.drawable.ic_rtm )
                                                            .setMessage( getString( R.string.dlg_no_account_text ) )
                                                            .setPositiveButton( R.string.btn_new_account )
                                                            .setNegativeButton( R.string.btn_cancel )
                                                            .show( this );
   }
   
   
   
   @Override
   public void showNotConnectedDialog()
   {
      new AlertDialogFragment.Builder( R.id.dlg_not_connected ).setTitle( getString( R.string.err_not_connected ) )
                                                               .setIcon( android.R.drawable.ic_dialog_alert )
                                                               .setMessage( getString( R.string.phr_establish_connection ) )
                                                               .setPositiveButton( R.string.btn_ok )
                                                               .setNeutralButton( R.string.phr_settings )
                                                               .show( this );
   }
   
   
   
   protected boolean removeFragmentByTag( String fragmentTag, int transit )
   {
      boolean removed = false;
      
      final Fragment fragment = findAddedFragmentByTag( fragmentTag );
      
      if ( fragment != null )
      {
         final FragmentTransaction transaction = getFragmentManager().beginTransaction();
         
         transaction.remove( fragment );
         transaction.setTransition( transit );
         
         transaction.commit();
         
         removed = true;
      }
      
      return removed;
   }
   
   
   
   public Fragment findAddedFragmentById( int fragmentId )
   {
      Fragment fragment = getFragmentManager().findFragmentById( fragmentId );
      
      if ( fragment != null && ( !fragment.isAdded() || fragment.isDetached() ) )
      {
         fragment = null;
      }
      
      return fragment;
   }
   
   
   
   public Fragment findAddedFragmentByTag( String fragmentTag )
   {
      Fragment fragment = getFragmentManager().findFragmentByTag( fragmentTag );
      
      if ( fragment != null && !fragment.isAdded() )
      {
         fragment = null;
      }
      
      return fragment;
   }
   
   
   
   protected void setupActionBar()
   {
      requestWindowFeature( Window.FEATURE_INDETERMINATE_PROGRESS );
      
      final ActionBar actionBar = getActionBar();
      if ( actionBar != null )
      {
         actionBar.setHomeButtonEnabled( true );
      }
   }
   
   
   
   private void initializeSyncingProgressIndicator()
   {
      if ( getActionBar() != null )
      {
         boolean show = showSyncProgress;
         if ( show )
         {
            final Account account = appContext.getAccountService()
                                              .getRtmAccount();
            show = account != null
               && appContext.getSyncService().isSyncing( account );
         }
         
         setProgressBarIndeterminateVisibility( show );
      }
   }
   
   
   
   private void onActionBarHome()
   {
      if ( Intents.HomeAction.HOME.equals( homeAction ) )
      {
         if ( onFinishActivityByHome() )
         {
            startActivity( Intents.createHomeIntent( this ) );
         }
      }
      else if ( Intents.HomeAction.BACK.equals( homeAction ) )
      {
         if ( onFinishActivityByHome() )
         {
            finish();
         }
      }
      else if ( Intents.HomeAction.ACTIVITY.equals( homeAction ) )
      {
         if ( onFinishActivityByHome() )
         {
            startActivity( Intents.createHomeAsUpIntent( this,
                                                         getHomeAsUpActivityClass() ) );
         }
      }
   }
   
   
   
   private Class< ? > getHomeAsUpActivityClass()
   {
      try
      {
         return getClassLoader().loadClass( homeAsUpTargetActivity );
      }
      catch ( ClassNotFoundException e )
      {
         throw new RuntimeException( e );
      }
   }
   
   
   
   private void onMenuSync()
   {
      final Account account = appContext.getAccountService().getRtmAccount();
      if ( account != null )
      {
         if ( appContext.getSyncService().requestManualSync( account ) == SyncStartResult.NotConnected )
         {
            showNotConnectedDialog();
         }
      }
      else
      {
         showNoAccountDialog();
      }
   }
   
   
   
   private void onMenuSearch()
   {
      onSearchRequested();
   }
   
   
   
   private void onReEvaluateRtmAccessLevel( RtmServicePermission currentAccessLevel )
   {
      invalidateOptionsMenu();
   }
   
   
   
   @Override
   public boolean onSearchRequested()
   {
      if ( !disableSearch )
      {
         return super.onSearchRequested();
      }
      
      return false;
   }
   
   
   
   private void onMenuSettings()
   {
      startActivity( Intents.createOpenPreferencesIntent( this ) );
   }
   
   
   
   protected abstract int[] getFragmentIds();
}
