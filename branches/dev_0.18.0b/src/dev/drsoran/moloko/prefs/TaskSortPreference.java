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
import android.util.AttributeSet;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.Settings;


class TaskSortPreference extends AutoSummaryListPreference
{
   public final static String[] SORT_ORDER_VALUES =
   { String.valueOf( Settings.TASK_SORT_PRIORITY ),
    String.valueOf( Settings.TASK_SORT_DUE_DATE ),
    String.valueOf( Settings.TASK_SORT_NAME ) };
   
   
   
   public TaskSortPreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );      
      setEntryValues( SORT_ORDER_VALUES );
   }
   
   
   
   @Override
   protected void onAttachedToActivity()
   {
      super.onAttachedToActivity();
      ensureTaskSortSetting();
   }
   
   
   
   public final static int getIndexOfValue( int value )
   {
      for ( int i = 0; i < SORT_ORDER_VALUES.length; i++ )
      {
         if ( value == Integer.valueOf( SORT_ORDER_VALUES[ i ] ) )
            return i;
      }
      
      return -1;
   }
   
   
   
   public final static int getValueOfIndex( int idx )
   {
      if ( idx > -1 && idx < SORT_ORDER_VALUES.length )
         return Integer.valueOf( SORT_ORDER_VALUES[ idx ] );
      else
         return -1;
   }
   
   
   
   private void ensureTaskSortSetting()
   {
      MolokoApp.getSettings( getContext() ).getTaskSort();
   }
   
}
