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

package dev.drsoran.moloko.app.prefs.activities;

import java.util.List;

import android.os.Build;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockPreferenceActivity;

import dev.drsoran.moloko.app.MolokoApp;


public class MainPreferencesActivity extends SherlockPreferenceActivity
{
   private IMainPreferencesActivityImplementor impl;
   
   
   
   public MainPreferencesActivity()
   {
      if ( MolokoApp.isApiLevelSupported( Build.VERSION_CODES.HONEYCOMB ) )
      {
         impl = new HoneycombMainPrefsActivityImpl();
      }
      else
      {
         impl = new PreHoneycombMainPrefsActivityImpl();
      }
   }
   
   
   
   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      impl.onCreate( savedInstanceState, this );
   }
   
   
   
   @Override
   protected void onStart()
   {
      super.onStart();
      impl.onStart( this );
   }
   
   
   
   @Override
   protected void onDestroy()
   {
      super.onDestroy();
      impl.onDestroy( this );
   }
   
   
   
   @Override
   public void onBuildHeaders( List< Header > target )
   {
      impl.onBuildHeaders( target, this );
   }
}
