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

package dev.drsoran.moloko.app.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import dev.drsoran.Compare;


class AutoSummaryEditTextPreference extends EditTextPreference implements
         IMolokoPreference, IAutoSummaryPreference< String >,
         OnSharedPreferenceChangeListener, OnPreferenceChangeListener
{
   private final AutoSummary< String > autoSummaryImpl;
   
   
   
   public AutoSummaryEditTextPreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      autoSummaryImpl = new AutoSummary< String >( context, attrs, this );
   }
   
   
   
   @Override
   protected void onAttachedToHierarchy( PreferenceManager preferenceManager )
   {
      super.onAttachedToHierarchy( preferenceManager );
      getSharedPreferences().registerOnSharedPreferenceChangeListener( this );
      setOnPreferenceChangeListener( this );
   }
   
   
   
   @Override
   public void checkEnabled()
   {
   }
   
   
   
   @Override
   public void cleanUp()
   {
      setOnPreferenceChangeListener( null );
      getSharedPreferences().unregisterOnSharedPreferenceChangeListener( this );
   }
   
   
   
   @Override
   public CharSequence getSummary()
   {
      return autoSummaryImpl.getSummary();
   }
   
   
   
   @Override
   public String getSummaryDisplay()
   {
      return getText();
   }
   
   
   
   public IAutoSummaryFormatter getAutoSummaryFormatter()
   {
      return autoSummaryImpl.getAutoSummaryFormatter();
   }
   
   
   
   public void setAutoSummaryFormatter( IAutoSummaryFormatter formatter )
   {
      autoSummaryImpl.setAutoSummaryFormatter( formatter );
   }
   
   
   
   @Override
   public void onSharedPreferenceChanged( SharedPreferences sharedPreferences,
                                          String key )
   {
      if ( key != null && key.equals( getKey() ) )
      {
         final String currentValue = getText();
         final String persistedValue = getPersistedString( currentValue );
         
         if ( Compare.isDifferent( currentValue, persistedValue ) )
         {
            setText( persistedValue );
            notifyChanged();
         }
      }
   }
   
   
   
   @Override
   public boolean onPreferenceChange( Preference preference, Object newValue )
   {
      if ( newValue != null && newValue.equals( getText() ) )
      {
         notifyChanged();
      }
      
      return true;
   }
}
