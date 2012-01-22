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

package dev.drsoran.moloko.prefs;

import android.content.Context;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import dev.drsoran.moloko.util.Strings;


public class AutoSummaryEditTextPreference extends EditTextPreference implements
         IMolokoPreference, OnPreferenceChangeListener
{
   
   public AutoSummaryEditTextPreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );
   }
   
   
   
   @Override
   protected void onAttachedToHierarchy( PreferenceManager preferenceManager )
   {
      super.onAttachedToHierarchy( preferenceManager );
      
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
   }
   
   
   
   @Override
   public CharSequence getSummary()
   {
      final CharSequence summary = super.getSummary();
      final CharSequence entry = super.getText();
      
      if ( summary == null || entry == null )
      {
         return String.format( summary.toString(), Strings.EMPTY_STRING );
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
   
   
   
   @Override
   public boolean onPreferenceChange( Preference preference, Object newValue )
   {
      if ( newValue != null && newValue instanceof String
         && !( (String) newValue ).equals( getText() ) )
      {
         notifyChanged();
      }
      
      return true;
   }
}
