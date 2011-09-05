/*
 * Copyright (c) 2011 Ronny Röhricht
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

package dev.drsoran.moloko.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.adapters.HomeAdapter;
import dev.drsoran.moloko.fragments.dialogs.NoAccountDialogFragment;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.UIUtils;


public class HomeActivity extends MolokoFragmentActivity implements
         OnItemClickListener
{
   private static class OptionsMenu
   {
      public final static int ADD_TASK = R.id.menu_quick_add_task;
   }
   
   private final Handler handler = new Handler();
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.home_activity );
      
      checkRtmAccountExists();
      
      final GridView gridview = (GridView) findViewById( R.id.home_gridview );
      gridview.setOnItemClickListener( this );
      
      fillGrid();
   }
   


   @Override
   protected void onResume()
   {
      super.onResume();
      
      final GridView gridview = (GridView) findViewById( R.id.home_gridview );
      final HomeAdapter homeAdapter = (HomeAdapter) gridview.getAdapter();
      
      if ( homeAdapter != null )
      {
         homeAdapter.startWidgets();
         onContentChanged();
      }
   }
   


   @Override
   protected void onStop()
   {
      super.onStop();
      
      final GridView gridview = (GridView) findViewById( R.id.home_gridview );
      final HomeAdapter homeAdapter = (HomeAdapter) gridview.getAdapter();
      
      if ( homeAdapter != null )
      {
         homeAdapter.stopWidgets();
      }
   }
   


   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      super.onCreateOptionsMenu( menu );
      
      UIUtils.addOptionalMenuItem( this,
                                   menu,
                                   OptionsMenu.ADD_TASK,
                                   getString( R.string.app_task_add ),
                                   Menu.CATEGORY_CONTAINER,
                                   Menu.NONE,
                                   R.drawable.ic_menu_add_task,
                                   MenuItem.SHOW_AS_ACTION_ALWAYS,
                                   AccountUtils.isWriteableAccess( this ) );
      
      UIUtils.addSearchMenuItem( this,
                                 menu,
                                 Menu.CATEGORY_ALTERNATIVE,
                                 MenuItem.SHOW_AS_ACTION_ALWAYS );
      
      UIUtils.addSyncMenuItem( this,
                               menu,
                               Menu.CATEGORY_ALTERNATIVE,
                               MenuItem.SHOW_AS_ACTION_ALWAYS );
      
      return true;
   }
   


   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case android.R.id.home:
            UIUtils.newAboutMolokoDialog( this );
            return true;
            
         case OptionsMenu.ADD_TASK:
            startActivity( Intents.createAddTaskIntent( this, null ) );
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
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
         startActivity( intent );
      else
      {
         final Runnable runnable = adapter.getRunnableForWidget( pos );
         
         if ( runnable != null )
            handler.post( runnable );
      }
   }
   


   @Override
   protected int[] getFragmentIds()
   {
      return null;
   }
   


   private void checkRtmAccountExists()
   {
      if ( AccountUtils.getRtmAccount( this ) == null )
      {
         final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( this );
         if ( prefs != null )
         {
            final String prefKey = getString( R.string.key_notified_account_creation );
            
            if ( !prefs.getBoolean( prefKey, false ) )
            {
               if ( !isDialogFragmentAdded( NoAccountDialogFragment.class ) )
                  showDialogFragment( NoAccountDialogFragment.newInstance( Bundle.EMPTY ) );
               
               prefs.edit().putBoolean( prefKey, true ).commit();
            }
         }
      }
   }
   


   private boolean isDialogFragmentAdded( Class< ? extends DialogFragment > clazz )
   {
      return getSupportFragmentManager().findFragmentByTag( clazz.getName() ) != null;
   }
   


   private void showDialogFragment( DialogFragment newInstance )
   {
      newInstance.show( getSupportFragmentManager(), newInstance.getClass()
                                                                .getName() );
   }
}
