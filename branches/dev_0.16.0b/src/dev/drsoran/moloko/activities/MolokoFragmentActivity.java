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

package dev.drsoran.moloko.activities;

import android.accounts.Account;
import android.accounts.OnAccountsUpdateListener;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItem;

import com.mdt.rtm.data.RtmAuth;

import dev.drsoran.moloko.IConfigurable;
import dev.drsoran.moloko.IRtmAccessLevelAware;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.listeners.IAlertDialogFragmentListener;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Intents;


public abstract class MolokoFragmentActivity extends FragmentActivity implements
         IConfigurable, IAlertDialogFragmentListener, OnAccountsUpdateListener
{
   public final static class StartActivityRequestCode
   {
      public final static int ADD_ACCOUNT = 1;
   }
   
   protected final Handler handler = new Handler();
   
   protected Bundle configuration;
   
   private boolean ignoreAccountListenerAfterRegister = true;
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      configureByIntent( getIntent() );
      
      if ( savedInstanceState != null )
         configureBySavedInstanceState( savedInstanceState );
      
      AccountUtils.registerAccountListener( this, handler, this );
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
      super.onDestroy();
      
      AccountUtils.unregisterAccountListener( this, this );
   }
   
   
   
   @Override
   protected void onSaveInstanceState( Bundle outState )
   {
      super.onSaveInstanceState( outState );
      outState.putAll( getConfiguration() );
   }
   
   
   
   @Override
   protected void onRestoreInstanceState( Bundle state )
   {
      super.onRestoreInstanceState( state );
      configure( state );
   }
   
   
   
   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      boolean handled = false;
      
      switch ( item.getItemId() )
      {
         case android.R.id.home:
            if ( IsShowHomeAsUp() )
            {
               if ( onFinishActivityByHome() )
                  finish();
            }
            else
            {
               startActivity( Intents.createOpenHomeIntent( this ) );
            }
            
            handled = true;
            
         default :
            handled = false;
      }
      
      return handled || super.onOptionsItemSelected( item );
   }
   
   
   
   protected boolean IsShowHomeAsUp()
   {
      return ( getSupportActionBar().getDisplayOptions() & ActionBar.DISPLAY_HOME_AS_UP ) == ActionBar.DISPLAY_HOME_AS_UP;
   }
   
   
   
   protected boolean onFinishActivityByHome()
   {
      return true;
   }
   
   
   
   @Override
   public Bundle getConfiguration()
   {
      return new Bundle( configuration );
   }
   
   
   
   @Override
   public void configure( Bundle config )
   {
      if ( configuration == null )
         configuration = createDefaultConfiguration();
      
      if ( config != null )
         takeConfigurationFrom( config );
   }
   
   
   
   @Override
   public void clearConfiguration()
   {
      if ( configuration != null )
         configuration.clear();
   }
   
   
   
   @Override
   public Bundle createDefaultConfiguration()
   {
      final Bundle bundle = new Bundle();
      
      putDefaultConfigurationTo( bundle );
      
      return bundle;
   }
   
   
   
   public Bundle getActivityAndFragmentsConfiguration( int... fragmentIds )
   {
      final Bundle config = new Bundle();
      
      config.putAll( configuration );
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
   
   
   
   protected void takeConfigurationFrom( Bundle config )
   {
   }
   
   
   
   protected void putDefaultConfigurationTo( Bundle bundle )
   {
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
   
   
   
   protected Fragment findAddedFragmentById( int fragmentId )
   {
      Fragment fragment = getSupportFragmentManager().findFragmentById( fragmentId );
      
      if ( fragment != null && ( !fragment.isAdded() || fragment.isDetached() ) )
         fragment = null;
      
      return fragment;
   }
   
   
   
   protected Fragment findAddedFragmentByTag( String fragmentTag )
   {
      Fragment fragment = getSupportFragmentManager().findFragmentByTag( fragmentTag );
      
      if ( fragment != null && !fragment.isAdded() )
         fragment = null;
      
      return fragment;
   }
   
   
   
   protected abstract int[] getFragmentIds();
}
