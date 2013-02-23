/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.moloko.model;

import java.util.ArrayList;

import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.parsing.RtmSmartFilterParsing;
import dev.drsoran.moloko.util.parsing.RtmSmartFilterParsing.RtmSmartFilterReturn;
import dev.drsoran.moloko.util.parsing.RtmSmartFilterToken;
import dev.drsoran.provider.Rtm.RawTasks;


public class RtmSmartFilter
{
   private final String filter;
   
   private String evalFilter;
   
   private ArrayList< RtmSmartFilterToken > tokens;
   
   
   
   public RtmSmartFilter()
   {
      this( Strings.EMPTY_STRING );
   }
   
   
   
   public RtmSmartFilter( String filter )
   {
      this.filter = transformFilter( filter );
      this.evalFilter = null;
      this.tokens = null;
   }
   
   
   
   public String getFilterString()
   {
      return filter;
   }
   
   
   
   public String getEvaluatedFilterString( boolean collectTokens )
   {
      if ( !isEvaluated() && filter != null || collectTokens && tokens == null )
      {
         if ( collectTokens )
         {
            tokens = new ArrayList< RtmSmartFilterToken >();
         }
         
         evalFilter = evaluate( filter, tokens, true );
      }
      
      return evalFilter;
   }
   
   
   
   public boolean isEvaluated()
   {
      return evalFilter != null;
   }
   
   
   
   public ArrayList< RtmSmartFilterToken > getTokens()
   {
      if ( tokens == null )
      {
         tokens = new ArrayList< RtmSmartFilterToken >();
         
         if ( filter != null && !isEvaluated() )
         {
            evaluate( filter, tokens, true );
         }
      }
      
      return tokens;
   }
   
   
   
   @Override
   public boolean equals( Object o )
   {
      if ( o == null )
      {
         return false;
      }
      
      if ( o == this )
      {
         return true;
      }
      
      if ( o.getClass() != getClass() )
      {
         return false;
      }
      
      final RtmSmartFilter other = (RtmSmartFilter) o;
      
      boolean equal = Strings.equals( filter, other.filter );
      equal = equal && Strings.equals( evalFilter, other.evalFilter );
      
      return equal;
   }
   
   
   
   public static final String evaluate( String filter, boolean excludeCompleted )
   {
      return evaluate( transformFilter( filter ), null, excludeCompleted );
   }
   
   
   
   // TODO: Use an injected evaluator to not depend on SQL
   public static final String evaluate( String filter,
                                        ArrayList< RtmSmartFilterToken > tokens,
                                        boolean excludeCompleted )
   {
      final StringBuilder evalFilter = new StringBuilder();
      
      // a 0-length filter == "true"
      if ( filter != null )
      {
         if ( filter.length() == 0 )
         {
            if ( excludeCompleted )
            {
               evalFilter.append( RawTasks.COMPLETED_DATE )
                         .append( " IS NULL AND " )
                         .append( RawTasks.DELETED_DATE )
                         .append( " IS NULL" );
            }
            else
            {
               evalFilter.append( RawTasks.DELETED_DATE ).append( " IS NULL" );
            }
         }
         
         // Check if there was no operator used. If so it has the
         // same meaning as operator name:
         else
         {
            final RtmSmartFilterReturn parserRes = RtmSmartFilterParsing.evaluateRtmSmartFilter( filter,
                                                                                                 tokens );
            
            if ( parserRes != null )
            {
               evalFilter.append( "( " );
               evalFilter.append( parserRes.queryString );
               
               // SPECIAL CASE: If the filter contains any operator 'completed or status',
               // we include completed tasks. Otherwise we would never show tasks in
               // such lists. In all other cases we exclude completed tasks.
               if ( !parserRes.hasCompletedOperator && excludeCompleted )
               {
                  evalFilter.append( " AND " )
                            .append( RawTasks.COMPLETED_DATE )
                            .append( " IS NULL" );
               }
               
               // We always exclude deleted tasks
               evalFilter.append( " AND " )
                         .append( RawTasks.DELETED_DATE )
                         .append( " IS NULL" );
               
               evalFilter.append( " )" );
            }
         }
      }
      
      // An empty string is an evaluation error.
      if ( evalFilter.length() == 0 )
      {
         return null;
      }
      
      return evalFilter.toString();
   }
   
   
   
   @Override
   public String toString()
   {
      return new StringBuilder( "<" ).append( RtmSmartFilter.class.getSimpleName() )
                                     .append( ", " )
                                     .append( filter )
                                     .append( ", " )
                                     .append( evalFilter )
                                     .append( ">" )
                                     .toString();
   }
   
   
   
   private final static String transformFilter( String filter )
   {
      if ( filter.length() > 0 && !filter.contains( ":" ) )
      {
         filter = RtmSmartFilterLexer.OP_NAME_LIT
            + RtmSmartFilterLexer.quotify( filter );
      }
      
      return filter;
   }
   
}
