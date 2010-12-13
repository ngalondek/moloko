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

import org.antlr.runtime.RecognitionException;

import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.util.ANTLRNoCaseStringStream;
import dev.drsoran.rtm.RtmSmartFilter;


public final class RtmSmartFilterParsing
{
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
   
   

   public synchronized final static RtmSmartFilterReturn evaluateRtmSmartFilter( RtmSmartFilter filter )
   {
      return evaluateRtmSmartFilter( filter.getFilterString() );
   }
   


   public synchronized final static RtmSmartFilterReturn evaluateRtmSmartFilter( String filterString )
   {
      final ANTLRNoCaseStringStream input = new ANTLRNoCaseStringStream( filterString );
      
      rtmSmartFilterLexer.setCharStream( input );
      
      try
      {
         final String res = rtmSmartFilterLexer.getResult();
         return new RtmSmartFilterReturn( res,
                                          rtmSmartFilterLexer.hasStatusCompletedOperator() );
      }
      catch ( RecognitionException e )
      {
         return null;
      }
   }
}
