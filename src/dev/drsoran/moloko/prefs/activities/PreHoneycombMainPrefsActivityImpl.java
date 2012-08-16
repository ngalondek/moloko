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

package dev.drsoran.moloko.prefs.activities;

import java.util.List;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceActivity.Header;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.prefs.PreferenceLifecycle;


class PreHoneycombMainPrefsActivityImpl implements
         IMainPreferencesActivityImplementor
{
   @SuppressWarnings( "deprecation" )
   @Override
   public void onCreate( Bundle savedInstanceState, PreferenceActivity activity )
   {
      activity.addPreferencesFromResource( R.xml.main_preferences );
      PreferenceLifecycle.enablePreferences( activity.getPreferenceScreen() );
   }
   
   
   
   @SuppressWarnings( "deprecation" )
   @Override
   public void onDestroy( PreferenceActivity activity )
   {
      PreferenceLifecycle.cleanUpPreferences( activity.getPreferenceScreen() );
   }
   
   
   
   @Override
   public void onBuildHeaders( List< Header > target,
                               PreferenceActivity activity )
   {
      throw new UnsupportedOperationException( "This is not supported prior to API level 11." );
   }
}
