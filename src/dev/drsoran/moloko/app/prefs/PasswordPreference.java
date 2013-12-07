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
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import dev.drsoran.Strings;
import dev.drsoran.moloko.R;


class PasswordPreference extends AutoSummaryEditTextPreference
{
   
   public PasswordPreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      getEditText().setTransformationMethod( new PasswordTransformationMethod() );
   }
   
   
   
   @Override
   protected void onAttachedToHierarchy( PreferenceManager preferenceManager )
   {
      super.onAttachedToHierarchy( preferenceManager );
      
      setAutoSummaryFormatter( new IAutoSummaryFormatter()
      {
         @Override
         public String format( String summaryPattern )
         {
            if ( getSharedPreferences().contains( getKey() ) )
            {
               return getContext().getString( R.string.phr_password_set );
            }
            else
            {
               return Strings.EMPTY_STRING;
            }
         }
      } );
   }
   
   
   
   @Override
   public void onSharedPreferenceChanged( SharedPreferences sharedPreferences,
                                          String key )
   {
      if ( key != null && key.equals( getKey() ) )
      {
         // If the password is empty, clear the setting
         final String storedPw = sharedPreferences.getString( key, null );
         
         if ( storedPw != null && TextUtils.isEmpty( storedPw ) )
         {
            sharedPreferences.edit().remove( key ).commit();
         }
         
         notifyChanged();
      }
   }
}
