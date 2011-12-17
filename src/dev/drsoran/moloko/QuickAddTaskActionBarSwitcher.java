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

package dev.drsoran.moloko;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import dev.drsoran.moloko.fragments.QuickAddTaskActionBarFragment;
import dev.drsoran.moloko.fragments.QuickAddTaskButtonBarFragment;


public class QuickAddTaskActionBarSwitcher
{
   private final static String IS_SWITCHED_KEY = "quick_add_task_is_switched";
   
   private final static String FRAGMENT_CONFIG = "quick_add_task_frag_config";
   
   private final boolean isSwitchingSupported;
   
   private final boolean showButtonBarFragment;
   
   private final FragmentActivity activity;
   
   private final ActionBar actionBar;
   
   private final Handler handler = new Handler();
   
   private boolean isSwitched;
   
   private Bundle fragmentConfig;
   
   
   
   public QuickAddTaskActionBarSwitcher( FragmentActivity activity )
   {
      this( activity, null );
   }
   
   
   
   public QuickAddTaskActionBarSwitcher( FragmentActivity activity,
      Bundle savedInstanceState )
   {
      this.activity = activity;
      actionBar = activity.getSupportActionBar();
      
      isSwitchingSupported = hasActionBarFragmentContainer();
      showButtonBarFragment = hasButtonBarFragmentContainer();
      
      restoreInstanceState( savedInstanceState );
   }
   
   
   
   public void saveInstanceState( Bundle bundle )
   {
      if ( bundle != null )
      {
         bundle.putBoolean( IS_SWITCHED_KEY, isSwitched );
         bundle.putBundle( FRAGMENT_CONFIG, fragmentConfig );
      }
   }
   
   
   
   public void restoreInstanceState( Bundle bundle )
   {
      if ( bundle != null )
      {
         isSwitched = bundle.getBoolean( IS_SWITCHED_KEY, false );
         fragmentConfig = bundle.getBundle( FRAGMENT_CONFIG );
      }
   }
   
   
   
   public void insertOperator( char operator )
   {
      final QuickAddTaskActionBarFragment fragment = (QuickAddTaskActionBarFragment) getFragment( R.id.frag_quick_add_task_action_bar );
      if ( fragment != null )
      {
         fragment.insertOperatorAtCursor( operator );
      }
   }
   
   
   
   public void showInLastState()
   {
      if ( isSwitchingSupported )
      {
         if ( isSwitched )
            hideActionBarAndAddFragments();
         else
            showActionBarAndRemoveFragments();
      }
   }
   
   
   
   public boolean isSwitched()
   {
      return isSwitched;
   }
   
   
   
   public void showUnswitched()
   {
      if ( isSwitched )
      {
         showActionBarAndRemoveFragments();
         isSwitched = false;
      }
   }
   
   
   
   public void showSwitched( Bundle fragmentConfig )
   {
      if ( isSwitchingSupported && !isSwitched )
      {
         this.fragmentConfig = fragmentConfig;
         hideActionBarAndAddFragments();
         isSwitched = true;
      }
   }
   
   
   
   private void hideActionBarAndAddFragments()
   {
      hideActionBar();
      
      final FragmentTransaction transaction = activity.getSupportFragmentManager()
                                                      .beginTransaction();
      
      addActionBarFragment( transaction );
      
      if ( showButtonBarFragment )
         addButtonBarFragment( transaction );
      
      transaction.commit();
   }
   
   
   
   private void showActionBarAndRemoveFragments()
   {
      final FragmentTransaction transaction = activity.getSupportFragmentManager()
                                                      .beginTransaction();
      
      removeActionBarFragment( transaction );
      removeButtonBarFragment( transaction );
      
      transaction.commit();
      
      showActionBar();
   }
   
   
   
   private void addActionBarFragment( FragmentTransaction transaction )
   {
      final int fragId = R.id.frag_quick_add_task_action_bar;
      Fragment fragment = getFragment( fragId );
      
      if ( fragment == null )
      {
         fragment = QuickAddTaskActionBarFragment.newInstance( fragmentConfig );
         addFragment( transaction, fragment, fragId );
      }
   }
   
   
   
   private void removeActionBarFragment( FragmentTransaction transaction )
   {
      final Fragment fragment = getFragment( R.id.frag_quick_add_task_action_bar );
      
      if ( fragment != null )
      {
         removeFragment( transaction, fragment );
      }
   }
   
   
   
   private void addButtonBarFragment( FragmentTransaction transaction )
   {
      final int fragId = R.id.frag_quick_add_task_button_bar;
      
      Fragment fragment = getFragment( fragId );
      
      if ( fragment == null )
      {
         fragment = QuickAddTaskButtonBarFragment.newInstance( fragmentConfig );
         addFragment( transaction, fragment, fragId );
      }
   }
   
   
   
   private void removeButtonBarFragment( FragmentTransaction transaction )
   {
      final Fragment fragment = getFragment( R.id.frag_quick_add_task_button_bar );
      if ( fragment != null )
      {
         removeFragment( transaction, fragment );
      }
   }
   
   
   
   private Fragment getFragment( int id )
   {
      final Fragment fragment = activity.getSupportFragmentManager()
                                        .findFragmentById( id );
      return fragment;
   }
   
   
   
   private void addFragment( FragmentTransaction transaction,
                             Fragment fragment,
                             int id )
   {
      transaction.setTransitionStyle( FragmentTransaction.TRANSIT_FRAGMENT_FADE );
      transaction.add( id, fragment );
   }
   
   
   
   private void removeFragment( FragmentTransaction transaction,
                                Fragment fragment )
   {
      transaction.setTransitionStyle( FragmentTransaction.TRANSIT_FRAGMENT_FADE );
      transaction.remove( fragment );
   }
   
   
   
   private void hideActionBar()
   {
      handler.post( new Runnable()
      {
         @Override
         public void run()
         {
            actionBar.hide();
         }
      } );
   }
   
   
   
   private void showActionBar()
   {
      handler.post( new Runnable()
      {
         @Override
         public void run()
         {
            actionBar.show();
         }
      } );
   }
   
   
   
   private boolean hasActionBarFragmentContainer()
   {
      return activity.findViewById( R.id.frag_quick_add_task_action_bar ) != null;
   }
   
   
   
   private boolean hasButtonBarFragmentContainer()
   {
      return activity.findViewById( R.id.frag_quick_add_task_button_bar ) != null;
   }
}
