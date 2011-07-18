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
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;

import com.mdt.rtm.data.RtmAuth;

import dev.drsoran.moloko.IConfigurable;
import dev.drsoran.moloko.IRtmAccessLevelAware;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.UIUtils;


public abstract class MolokoFragmentActivity extends FragmentActivity implements
         IConfigurable, OnAccountsUpdateListener
{
   protected final Handler handler = new Handler();
   
   protected Bundle configuration;
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      final Bundle intentExtras = getIntent().getExtras();
      final Bundle intentConfig;
      
      if ( intentExtras != null )
         intentConfig = new Bundle( intentExtras );
      else
         intentConfig = new Bundle();
      
      if ( savedInstanceState != null )
         intentConfig.putAll( savedInstanceState );
      
      configure( intentConfig );
      
      // TODO: ActionBarSherlock workaround
      // https://github.com/JakeWharton/ActionBarSherlock/issues/35
      super.onCreate( savedInstanceState );
      
      AccountUtils.registerAccountListener( this, handler, this );
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
   


   public void notifyOptionsMenuChanged()
   {
      if ( !isFinishing() )
      {
         handler.post( new Runnable()
         {
            @Override
            public void run()
            {
               invalidateOptionsMenu();
            }
         } );
      }
   }
   


   protected abstract void takeConfigurationFrom( Bundle config );
   


   protected abstract void putDefaultConfigurationTo( Bundle bundle );
   


   protected abstract int[] getFragmentIds();
   


   @Override
   public void onAccountsUpdated( Account[] accounts )
   {
      onReEvaluateRtmAccessLevel( AccountUtils.getAccessLevel( this ) );
   }
   


   protected void onReEvaluateRtmAccessLevel( RtmAuth.Perms currentAccessLevel )
   {
      notifyOptionsMenuChanged();
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
            
            if ( fragment instanceof IRtmAccessLevelAware )
               ( (IRtmAccessLevelAware) fragment ).reEvaluateRtmAccessLevel( currentAccessLevel );
         }
      }
   }
   


   protected final void showLoadingSpinner()
   {
      handler.post( new Runnable()
      {
         @Override
         public void run()
         {
            final View content = findViewById( android.R.id.content );
            if ( content != null )
               content.setVisibility( View.GONE );
            
            final View spinner = findViewById( R.id.loading_spinner );
            if ( spinner != null )
               spinner.setVisibility( View.VISIBLE );
         }
      } );
   }
   


   protected final void showContent()
   {
      handler.post( new Runnable()
      {
         @Override
         public void run()
         {
            final View content = findViewById( android.R.id.content );
            if ( content != null )
            {
               content.setVisibility( View.VISIBLE );
               updateContent( (ViewGroup) content );
            }
            
            final View spinner = findViewById( R.id.loading_spinner );
            if ( spinner != null )
               spinner.setVisibility( View.GONE );
         }
      } );
   }
   


   protected void updateContent( ViewGroup container )
   {
   }
   


   protected final void showElementNotFoundError( final CharSequence elementType )
   {
      handler.post( new Runnable()
      {
         @Override
         public void run()
         {
            final View spinner = findViewById( R.id.loading_spinner );
            if ( spinner != null )
               spinner.setVisibility( View.GONE );
            
            final ViewGroup content = (ViewGroup) findViewById( android.R.id.content );
            if ( content != null )
            {
               content.setVisibility( View.VISIBLE );
               content.removeAllViews();
               
               UIUtils.initializeErrorWithIcon( MolokoFragmentActivity.this,
                                                content,
                                                R.string.err_entity_not_found,
                                                elementType );
            }
         }
      } );
   }
}
