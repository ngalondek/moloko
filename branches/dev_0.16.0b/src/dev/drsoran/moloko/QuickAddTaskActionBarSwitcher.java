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


public class QuickAddTaskActionBarSwitcher
{
   private final static String IS_SWITCHED_KEY = "quick_add_task_is_switched";
   
   private final static String FRAGMENT_CONFIG = "quick_add_task_frag_config";
   
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
   


   public void showInLastState()
   {
      if ( isSwitched )
         hideActionBarAndShowFragments();
      else
         showActionBarAndHideFragments();
   }
   


   public boolean isSwitched()
   {
      return isSwitched;
   }
   


   public void showUnswitched()
   {
      if ( isSwitched )
      {
         showActionBarAndHideFragments();
         isSwitched = false;
      }
   }
   


   public void showSwitched( Bundle fragmentConfig )
   {
      if ( !isSwitched )
      {
         this.fragmentConfig = fragmentConfig;
         hideActionBarAndShowFragments();
         isSwitched = true;
      }
   }
   


   private void hideActionBarAndShowFragments()
   {
      handler.post( new Runnable()
      {
         @Override
         public void run()
         {
            actionBar.hide();
         }
      } );
      
      Fragment fragment = activity.getSupportFragmentManager()
                                  .findFragmentById( R.id.frag_quick_add_task );
      if ( fragment == null )
      {
         fragment = QuickAddTaskActionBarFragment.newInstance( fragmentConfig );
         
         final FragmentTransaction transaction = activity.getSupportFragmentManager()
                                                         .beginTransaction();
         transaction.setTransitionStyle( FragmentTransaction.TRANSIT_FRAGMENT_FADE );
         transaction.add( R.id.frag_quick_add_task, fragment );
         transaction.commit();
      }
   }
   


   private void showActionBarAndHideFragments()
   {
      final Fragment fragment = activity.getSupportFragmentManager()
                                        .findFragmentById( R.id.frag_quick_add_task );
      if ( fragment != null )
      {
         final FragmentTransaction transaction = activity.getSupportFragmentManager()
                                                         .beginTransaction();
         transaction.setTransitionStyle( FragmentTransaction.TRANSIT_FRAGMENT_FADE );
         transaction.remove( fragment );
         transaction.commit();
      }
      
      handler.post( new Runnable()
      {
         @Override
         public void run()
         {
            actionBar.show();
         }
      } );
   }
}
