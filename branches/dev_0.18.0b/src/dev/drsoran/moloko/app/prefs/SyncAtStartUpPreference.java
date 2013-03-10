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

package dev.drsoran.moloko.app.prefs;

import android.content.Context;
import android.preference.CheckBoxPreference;
import android.util.AttributeSet;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.AppContext;


class SyncAtStartUpPreference extends CheckBoxPreference implements
         IMolokoPreference
{
   public SyncAtStartUpPreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );
   }
   
   
   
   @Override
   public void checkEnabled()
   {
      setEnabled( !AppContext.get( getContext() )
                             .getSettings()
                             .isManualSyncOnly() );
      
      if ( !isEnabled() )
      {
         setSummaryOn( R.string.moloko_prefs_sync_at_startup_summary_disabled );
         setSummaryOff( R.string.moloko_prefs_sync_at_startup_summary_disabled );
      }
      else
      {
         setSummaryOn( R.string.phr_yes );
         setSummaryOff( R.string.phr_no );
      }
   }
   
   
   
   @Override
   public CharSequence getSummaryOn()
   {
      if ( !isEnabled() )
      {
         return getSummaryDisabled();
      }
      else
      {
         return getContext().getString( R.string.phr_yes );
      }
   }
   
   
   
   @Override
   public CharSequence getSummaryOff()
   {
      if ( !isEnabled() )
      {
         return getSummaryDisabled();
      }
      else
      {
         return getContext().getString( R.string.phr_no );
      }
   }
   
   
   
   @Override
   public void cleanUp()
   {
   }
   
   
   
   private CharSequence getSummaryDisabled()
   {
      return getContext().getString( R.string.moloko_prefs_sync_at_startup_summary_disabled );
   }
}
