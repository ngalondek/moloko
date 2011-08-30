/*
 * Copyright (c) 2010 Ronny Röhricht
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

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.view.Window;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.prefs.IMolokoPreference;


public class MolokoPreferencesActivity extends PreferenceActivity
{
   
   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      // This must be called before super.onCreate
      requestWindowFeature( Window.FEATURE_LEFT_ICON );
      
      super.onCreate( savedInstanceState );
      
      addPreferencesFromResource( R.xml.moloko_preferences_activity );
      
      // getWindow().setFeatureDrawableResource( Window.FEATURE_LEFT_ICON,
      // R.drawable.ic_title_settings );
   }
   


   @Override
   protected void onDestroy()
   {
      super.onDestroy();
      
      cleanUpPreferences( getPreferenceScreen() );
   }
   


   private void cleanUpPreferences( PreferenceGroup group )
   {
      final int count = group.getPreferenceCount();
      
      for ( int i = 0; i < count; i++ )
      {
         final Preference pref = group.getPreference( i );
         
         if ( pref instanceof PreferenceGroup )
            cleanUpPreferences( (PreferenceGroup) pref );
         else if ( pref instanceof IMolokoPreference )
            ( (IMolokoPreference) pref ).cleanUp();
      }
   }
}
