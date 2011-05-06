/* 
 *	Copyright (c) 2010 Ronny Röhricht
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

package dev.drsoran.moloko.util.parsing;

import java.util.ArrayList;

import org.antlr.runtime.RecognitionException;

import android.util.Log;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.util.ANTLRNoCaseStringStream;
import dev.drsoran.rtm.RtmSmartFilter;


public final class RtmSmartFilterParsing
{
   private final static String TAG = "Moloko."
      + RtmSmartFilterParsing.class.getSimpleName();
   
   private final static RtmSmartFilterLexer rtmSmartFilterLexer = new RtmSmartFilterLexer();
   
   
   public final static class RtmSmartFilterReturn
   {
      public final String result;
      
      public final boolean hasCompletedOperator;
      
      

      public RtmSmartFilterReturn( String result, boolean hasCompletedOperator )
      {
         this.result = result;
         this.hasCompletedOperator = hasCompletedOperator;
      }
   }
   
   

   public synchronized final static RtmSmartFilterReturn evaluateRtmSmartFilter( RtmSmartFilter filter,
                                                                                 ArrayList< RtmSmartFilterToken > tokens )
   {
      return evaluateRtmSmartFilter( filter.getFilterString(), tokens );
   }
   


   public synchronized final static RtmSmartFilterReturn evaluateRtmSmartFilter( String filterString,
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
         Log.e( TAG, "Failed to lex " + filterString, e );
         return null;
      }
   }
   


   public final static boolean hasOperator( ArrayList< RtmSmartFilterToken > tokens,
                                            int operator,
                                            boolean negated )
   {
      for ( RtmSmartFilterToken token : tokens )
      {
         if ( token.operatorType == operator && token.isNegated == negated )
            return true;
      }
      
      return false;
   }
   


   public final static boolean hasOperatorAndValue( ArrayList< RtmSmartFilterToken > tokens,
                                                    int operator,
                                                    String value,
                                                    boolean negated )
   {
      for ( RtmSmartFilterToken token : tokens )
      {
         if ( token.operatorType == operator
            && token.value.equalsIgnoreCase( value )
            && negated == token.isNegated )
            return true;
      }
      
      return false;
   }
   


   public final static boolean hasCompletedOperator( ArrayList< RtmSmartFilterToken > tokens )
   {
      for ( RtmSmartFilterToken token : tokens )
      {
         final int tokenType = token.operatorType;
         if ( ( tokenType == RtmSmartFilterLexer.OP_STATUS && token.value.equalsIgnoreCase( RtmSmartFilterLexer.COMPLETED_LIT ) )
            || tokenType == RtmSmartFilterLexer.OP_COMPLETED
            || tokenType == RtmSmartFilterLexer.OP_COMPLETED_AFTER
            || tokenType == RtmSmartFilterLexer.OP_COMPLETED_BEFORE
            || tokenType == RtmSmartFilterLexer.OP_COMPLETED_WITHIN )
            return true;
      }
      
      return false;
   }
}
