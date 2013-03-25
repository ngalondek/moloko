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

import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.grammar.rtmsmart.IRtmSmartFilterEvaluator;
import dev.drsoran.moloko.grammar.rtmsmart.NullRtmSmartFilterEvaluator;
import dev.drsoran.moloko.grammar.rtmsmart.RtmSmartFilterLexer;
import dev.drsoran.moloko.grammar.rtmsmart.RtmSmartFilterParsingReturn;
import dev.drsoran.moloko.grammar.rtmsmart.RtmSmartFilterTokenCollection;
import dev.drsoran.moloko.grammar.rtmsmart.TokenCollectingEvaluator;


public class RtmSmartFilterParsing implements IRtmSmartFilterParsing
{
   private final ILog log;
   
   private final RtmSmartFilterLexer rtmSmartFilterLexer = new RtmSmartFilterLexer();
   
   
   
   public RtmSmartFilterParsing( ILog log )
   {
      this.log = log;
   }
   
   
   
   @Override
   public RtmSmartFilterParsingReturn evaluateRtmSmartFilter( String filterString ) throws GrammarException
   {
      return evaluateRtmSmartFilter( filterString, null );
   }
   
   
   
   @Override
   public RtmSmartFilterParsingReturn evaluateRtmSmartFilter( String filterString,
                                                              IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      try
      {
         final ANTLRNoCaseStringStream input = new ANTLRNoCaseStringStream( filterString );
         
         rtmSmartFilterLexer.setCharStream( input );
         rtmSmartFilterLexer.setEvaluator( evaluator );
         rtmSmartFilterLexer.startEvaluation();
         
         return new RtmSmartFilterParsingReturn( rtmSmartFilterLexer.hasStatusCompletedOperator() );
      }
      catch ( GrammarException e )
      {
         log.w( RtmSmartFilterParsing.class, "Failed to lex smart filter '"
            + filterString + "'", e );
         
         throw e;
      }
      finally
      {
         rtmSmartFilterLexer.reset();
         rtmSmartFilterLexer.setEvaluator( null );
      }
   }
   
   
   
   @Override
   public RtmSmartFilterTokenCollection getSmartFilterTokens( String filterString ) throws GrammarException
   {
      try
      {
         final TokenCollectingEvaluator tokenCollector = new TokenCollectingEvaluator( new NullRtmSmartFilterEvaluator() );
         final ANTLRNoCaseStringStream input = new ANTLRNoCaseStringStream( filterString );
         
         rtmSmartFilterLexer.setCharStream( input );
         rtmSmartFilterLexer.setEvaluator( tokenCollector );
         rtmSmartFilterLexer.startEvaluation();
         
         return new RtmSmartFilterTokenCollection( tokenCollector.getTokens() );
      }
      catch ( GrammarException e )
      {
         log.w( RtmSmartFilterParsing.class, "Failed to lex smart filter '"
            + filterString + "'", e );
         
         throw e;
      }
      finally
      {
         rtmSmartFilterLexer.reset();
         rtmSmartFilterLexer.setEvaluator( null );
      }
   }
   
   
   
   @Override
   public boolean isParsableSmartFilter( String filterString )
   {
      try
      {
         evaluateRtmSmartFilter( filterString );
         return true;
      }
      catch ( GrammarException e )
      {
         return false;
      }
   }
}
