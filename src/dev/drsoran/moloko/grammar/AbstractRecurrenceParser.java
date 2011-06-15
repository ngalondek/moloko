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

package dev.drsoran.moloko.grammar;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.TokenStream;


public abstract class AbstractRecurrenceParser extends Parser
{
   protected final static CmpWeekday CMP_WEEKDAY = new CmpWeekday();
   
   protected Map< String, Object > res;
   
   protected Boolean isEvery;
   
   protected TreeSet< String > weekdays;
   
   protected TreeSet< Integer > ints;
   
   protected int interval;
   
   protected String freq;
   
   protected String resolution;
   
   protected String resolutionVal;
   
   

   protected AbstractRecurrenceParser( TokenStream input )
   {
      super( input );
   }
   


   protected AbstractRecurrenceParser( TokenStream input,
      RecognizerSharedState state )
   {
      super( input, state );
   }
   


   protected void startParseRecurrence()
   {
      clearState();
      res = new TreeMap< String, Object >( RecurrencePatternParser.CMP_OPERATORS );
      weekdays = new TreeSet< String >( CMP_WEEKDAY );
      ints = new TreeSet< Integer >();
   }
   


   protected Map< String, Object > finishedParseRecurrence()
   {
      res.put( RecurrencePatternParser.OP_FREQ_LIT, freq );
      res.put( RecurrencePatternParser.OP_INTERVAL_LIT, new Integer( interval ) );
      
      if ( resolution != null && resolutionVal != null )
         res.put( resolution, resolutionVal );
      
      res.put( RecurrencePatternParser.IS_EVERY, isEvery );
      
      final Map< String, Object > result = res;
      clearState();
      
      return result;
   }
   


   private void clearState()
   {
      res = null;
      isEvery = Boolean.FALSE;
      weekdays = null;
      ints = null;
      interval = 1;
      freq = null;
      resolution = null;
      resolutionVal = null;
   }
   
   
   protected final static class CmpWeekday implements Comparator< String >
   {
      private final static int weekdayToInt( String wd )
      {
         // only take the last 2 chars, the leading chars can be
         // Xst values.
         final String weekday = wd.substring( wd.length() - 2 );
         
         if ( weekday.equals( RecurrencePatternParser.BYDAY_MON ) )
            return 1;
         else if ( weekday.equals( RecurrencePatternParser.BYDAY_TUE ) )
            return 2;
         else if ( weekday.equals( RecurrencePatternParser.BYDAY_WED ) )
            return 3;
         else if ( weekday.equals( RecurrencePatternParser.BYDAY_THU ) )
            return 4;
         else if ( weekday.equals( RecurrencePatternParser.BYDAY_FRI ) )
            return 5;
         else if ( weekday.equals( RecurrencePatternParser.BYDAY_SAT ) )
            return 6;
         else if ( weekday.equals( RecurrencePatternParser.BYDAY_SUN ) )
            return 7;
         else
            return 1;
      }
      


      public int compare( String wd1, String wd2 )
      {
         return weekdayToInt( wd1 ) - weekdayToInt( wd2 );
      }
   }
   
   

   protected final static < E > String join( String delim, Iterable< E > values )
   {
      final StringBuilder result = new StringBuilder();
      final Iterator< E > i = values.iterator();
      
      for ( boolean hasNext = i.hasNext(); hasNext; )
      {
         result.append( i.next() );
         hasNext = i.hasNext();
         
         if ( hasNext )
            result.append( delim );
      }
      
      return result.toString();
   }
}
