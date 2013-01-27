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
import android.content.res.TypedArray;
import android.util.AttributeSet;
import dev.drsoran.moloko.R;


class AutoSummary< T >
{
   private final IAutoSummaryPreference< T > autoSummaryPreference;
   
   private final String summaryPattern;
   
   private IAutoSummaryFormatter summaryFormatter = new IAutoSummaryFormatter()
   {
      @Override
      public String format( String summaryPattern )
      {
         return String.format( summaryPattern,
                               autoSummaryPreference.getSummaryDisplay() );
      }
   };
   
   
   
   public AutoSummary( Context context, AttributeSet attrs,
      IAutoSummaryPreference< T > preference )
   {
      autoSummaryPreference = preference;
      
      TypedArray a = null;
      try
      {
         a = context.obtainStyledAttributes( attrs,
                                             R.styleable.AutoSummaryPreference,
                                             0,
                                             0 );
         summaryPattern = a.getString( R.styleable.AutoSummaryPreference_summaryPattern );
      }
      finally
      {
         if ( a != null )
         {
            a.recycle();
         }
      }
   }
   
   
   
   public String getSummary()
   {
      if ( summaryPattern == null )
      {
         return null;
      }
      else
      {
         return summaryFormatter.format( summaryPattern );
      }
   }
   
   
   
   public IAutoSummaryFormatter getAutoSummaryFormatter()
   {
      return summaryFormatter;
   }
   
   
   
   public void setAutoSummaryFormatter( IAutoSummaryFormatter formatter )
   {
      summaryFormatter = formatter;
   }
}
