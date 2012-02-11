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

package dev.drsoran.moloko.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import dev.drsoran.moloko.sync.util.SyncUtils;


public class AutoSummaryListPreference extends ListPreference implements
         IMolokoPreference, OnSharedPreferenceChangeListener,
         OnPreferenceChangeListener
{
   private int clickedDialogIndex;
   
   
   
   public AutoSummaryListPreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );
   }
   
   
   
   @Override
   protected void onAttachedToHierarchy( PreferenceManager preferenceManager )
   {
      super.onAttachedToHierarchy( preferenceManager );
      
      final SharedPreferences prefs = getSharedPreferences();
      
      if ( prefs != null )
      {
         prefs.registerOnSharedPreferenceChangeListener( this );
         setOnPreferenceChangeListener( this );
      }
   }
   
   
   
   @Override
   public void checkEnabled()
   {
   }
   
   
   
   @Override
   public void cleanUp()
   {
      setOnPreferenceChangeListener( null );
      
      final SharedPreferences prefs = getSharedPreferences();
      
      if ( prefs != null )
      {
         prefs.unregisterOnSharedPreferenceChangeListener( this );
      }
   }
   
   
   
   @Override
   public CharSequence getSummary()
   {
      final CharSequence summary = super.getSummary();
      final CharSequence entry = getEntry();
      
      if ( summary == null || entry == null )
      {
         return summary;
      }
      else
      {
         return String.format( summary.toString(), entry.toString() );
      }
   }
   
   
   
   @Override
   public void setSummary( CharSequence summary )
   {
      super.setSummary( summary );
      
      final CharSequence lSummary = getSummary();
      
      if ( summary == null && lSummary != null )
      {
         setSummary( null );
      }
      else if ( summary != null && !summary.equals( lSummary ) )
      {
         setSummary( summary.toString() );
      }
   }
   
   
   
   public String getClickedDialogValue()
   {
      if ( clickedDialogIndex > -1
         && clickedDialogIndex < getEntryValues().length )
         return getEntryValues()[ clickedDialogIndex ].toString();
      else
         return null;
   }
   
   
   
   @Override
   public void onSharedPreferenceChanged( SharedPreferences sharedPreferences,
                                          String key )
   {
      if ( key != null && key.equals( getKey() ) )
      {
         final String currentValue = getValue();
         final String persistedValue = sharedPreferences.getString( key,
                                                                    currentValue );
         
         if ( SyncUtils.hasChanged( currentValue, persistedValue ) )
         {
            setValue( persistedValue );
            notifyChanged();
         }
      }
   }
   
   
   
   @Override
   public boolean onPreferenceChange( Preference preference, Object newValue )
   {
      if ( newValue != null && newValue instanceof String
         && !( (String) newValue ).equals( getValue() ) )
      {
         notifyChanged();
      }
      
      return true;
   }
}
