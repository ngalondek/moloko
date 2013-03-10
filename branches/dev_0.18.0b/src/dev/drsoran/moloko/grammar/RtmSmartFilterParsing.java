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

package dev.drsoran.moloko.grammar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.RecognitionException;

import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.grammar.rtmsmart.RtmSmartFilterLexer;
import dev.drsoran.moloko.grammar.rtmsmart.RtmSmartFilterReturn;
import dev.drsoran.moloko.grammar.rtmsmart.RtmSmartFilterToken;
import dev.drsoran.rtm.RtmSmartFilter;


public class RtmSmartFilterParsing implements IRtmSmartFilterParsing
{
   private final ILog log;
   
   private final RtmSmartFilterLexer rtmSmartFilterLexer = new RtmSmartFilterLexer();
   
   
   
   public RtmSmartFilterParsing( ILog log, IDateTimeParsing dateTimeParsing )
   {
      this.log = log;
      rtmSmartFilterLexer.setDateTimeParsing( dateTimeParsing );
   }
   
   
   
   @Override
   public RtmSmartFilterReturn evaluateRtmSmartFilter( RtmSmartFilter filter,
                                                       ArrayList< RtmSmartFilterToken > tokens )
   {
      return evaluateRtmSmartFilter( filter.getFilterString(), tokens );
   }
   
   
   
   @Override
   public RtmSmartFilterReturn evaluateRtmSmartFilter( String filterString,
                                                       ArrayList< RtmSmartFilterToken > tokens )
   {
      final ANTLRNoCaseStringStream input = new ANTLRNoCaseStringStream( filterString );
      
      rtmSmartFilterLexer.setCharStream( input );
      
      try
      {
         final String res = rtmSmartFilterLexer.getResult( tokens );
         return new RtmSmartFilterReturn( res,
                                          rtmSmartFilterLexer.hasStatusCompletedOperator() );
      }
      catch ( RecognitionException e )
      {
         log.w( RtmSmartFilterParsing.class, "Failed to lex " + filterString, e );
         return null;
      }
   }
   
   
   
   @Override
   public boolean hasOperator( Collection< RtmSmartFilterToken > tokens,
                               int operator,
                               boolean negated )
   {
      for ( RtmSmartFilterToken token : removeAmbiguousTokens( tokens ) )
      {
         if ( token.operatorType == operator && token.isNegated == negated )
         {
            return true;
         }
      }
      
      return false;
   }
   
   
   
   @Override
   public boolean hasOperatorAndValue( Collection< RtmSmartFilterToken > tokens,
                                       int operator,
                                       String value,
                                       boolean negated )
   {
      for ( RtmSmartFilterToken token : removeAmbiguousTokens( tokens ) )
      {
         if ( token.operatorType == operator
            && token.value.equalsIgnoreCase( value )
            && negated == token.isNegated )
         {
            return true;
         }
      }
      
      return false;
   }
   
   
   
   @Override
   public boolean hasCompletedOperator( Collection< RtmSmartFilterToken > tokens )
   {
      for ( RtmSmartFilterToken token : tokens )
      {
         final int tokenType = token.operatorType;
         if ( ( tokenType == RtmSmartFilterLexer.OP_STATUS && token.value.equalsIgnoreCase( RtmSmartFilterLexer.COMPLETED_LIT ) )
            || tokenType == RtmSmartFilterLexer.OP_COMPLETED
            || tokenType == RtmSmartFilterLexer.OP_COMPLETED_AFTER
            || tokenType == RtmSmartFilterLexer.OP_COMPLETED_BEFORE
            || tokenType == RtmSmartFilterLexer.OP_COMPLETED_WITHIN )
         {
            return true;
         }
      }
      
      return false;
   }
   
   
   
   /**
    * Ambiguous is determined by the number a token exists in the list of given tokens. This is the simplest approach to
    * avoid situation with complex logical expressions where a token is entered negated and not negated.
    * 
    * TODO: Use more sophisticated approach like BDDs
    */
   @Override
   public List< RtmSmartFilterToken > removeAmbiguousTokens( Collection< RtmSmartFilterToken > tokens )
   {
      final List< RtmSmartFilterToken > filterTokens = new LinkedList< RtmSmartFilterToken >( tokens );
      
      Collections.sort( filterTokens, new Comparator< RtmSmartFilterToken >()
      {
         @Override
         public int compare( RtmSmartFilterToken object1,
                             RtmSmartFilterToken object2 )
         {
            return object1.operatorType - object2.operatorType;
         }
      } );
      
      for ( int i = 0, cnt = filterTokens.size(); i < cnt; )
      {
         boolean ambiguous = false;
         
         RtmSmartFilterToken filterToken = filterTokens.get( i );
         
         // next token
         int startI = i++;
         
         // Look ahead if we have a row of equal operators
         for ( ; i < cnt
            && filterTokens.get( i ).operatorType == filterToken.operatorType; ++i )
         {
            ambiguous = true;
            
            // remove equal token in row
            filterTokens.set( i, null );
         }
         
         if ( ambiguous )
            // Remove first token of the row
            filterTokens.set( startI, null );
      }
      
      for ( Iterator< RtmSmartFilterToken > i = filterTokens.iterator(); i.hasNext(); )
      {
         if ( i.next() == null )
            i.remove();
      }
      
      return filterTokens;
   }
}
