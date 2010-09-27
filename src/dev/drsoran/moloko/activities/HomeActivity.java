/*
	Copyright (c) 2010 Ronny Röhricht

	This file is part of Moloko.

	Moloko is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	Moloko is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.

	Contributors:
     Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.GridView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.util.DelayedRun;
import dev.drsoran.moloko.util.UIUtils;


public class HomeActivity extends Activity
{
   protected static class OptionsMenu
   {
      protected final static int START_IDX = 0;
      
      private final static int MENU_ORDER_STATIC = 10000;
      
      public final static int MENU_ORDER = MENU_ORDER_STATIC - 1;
      
      public final static int SETTINGS = START_IDX + 1;
   }
   
   protected final Handler handler = new Handler();
   
   protected final ContentObserver dbObserver = new ContentObserver( handler )
   {
      @Override
      public void onChange( boolean selfChange )
      {
         // Aggregate several calls to a single update.
         DelayedRun.run( handler, new Runnable()
         {
            public void run()
            {
               fillGrid();
               onContentChanged();
            }
         }, 1000 );
      }
   };
   
   

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.home_activity );
      
      UIUtils.setTitle( this,
                        getString( R.string.app_home ),
                        R.drawable.icon_home_white );
      
      TasksProviderPart.registerContentObserver( this, dbObserver );
      
      fillGrid();
   }
   


   @Override
   protected void onDestroy()
   {
      super.onDestroy();
      
      TasksProviderPart.unregisterContentObserver( this, dbObserver );
   }
   


   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      menu.add( Menu.NONE,
                OptionsMenu.SETTINGS,
                OptionsMenu.MENU_ORDER_STATIC,
                R.string.phr_settings )
          .setIcon( R.drawable.icon_settings_black )
          .setIntent( new Intent( this, MolokoPreferencesActivity.class ) );
      
      return true;
   }
   


   public void onTitleBarSearchClicked( View v )
   {
      onSearchRequested();
   }
   


   private void fillGrid()
   {
      final GridView gridview = (GridView) findViewById( R.id.home_gridview );
      gridview.setAdapter( new HomeAdapter( this ) );
   }
}
