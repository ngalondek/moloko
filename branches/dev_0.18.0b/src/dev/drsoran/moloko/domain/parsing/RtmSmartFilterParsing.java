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

package dev.drsoran.moloko.domain.parsing;

import java.text.MessageFormat;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;

import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.domain.parsing.rtmsmart.IRtmSmartFilterEvaluator;
import dev.drsoran.moloko.domain.parsing.rtmsmart.NullRtmSmartFilterEvaluator;
import dev.drsoran.moloko.domain.parsing.rtmsmart.RtmSmartFilterParsingReturn;
import dev.drsoran.moloko.domain.parsing.rtmsmart.RtmSmartFilterTokenCollection;
import dev.drsoran.moloko.domain.parsing.rtmsmart.RtmSmartFilterVisitorAdapter;
import dev.drsoran.moloko.domain.parsing.rtmsmart.TokenCollectingEvaluator;
import dev.drsoran.moloko.grammar.ANTLRNoCaseStringStream;
import dev.drsoran.moloko.grammar.antlr.rtmsmart.RtmSmartFilterBaseListener;
import dev.drsoran.moloko.grammar.antlr.rtmsmart.RtmSmartFilterLexer;
import dev.drsoran.moloko.grammar.antlr.rtmsmart.RtmSmartFilterParser;
import dev.drsoran.moloko.grammar.antlr.rtmsmart.RtmSmartFilterParser.CompletedAfterContext;
import dev.drsoran.moloko.grammar.antlr.rtmsmart.RtmSmartFilterParser.CompletedBeforeContext;
import dev.drsoran.moloko.grammar.antlr.rtmsmart.RtmSmartFilterParser.CompletedContext;
import dev.drsoran.moloko.grammar.antlr.rtmsmart.RtmSmartFilterParser.CompletedWithinContext;
import dev.drsoran.moloko.grammar.antlr.rtmsmart.RtmSmartFilterParser.StatusCompletedContext;
import dev.drsoran.moloko.grammar.antlr.rtmsmart.RtmSmartFilterVisitor;


public class RtmSmartFilterParsing implements IRtmSmartFilterParsing
{
   private final ILog log;
   
   
   
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
      return parseAndEvalRtmSmartFilter( filterString, evaluator );
   }
   
   
   
   @Override
   public RtmSmartFilterTokenCollection getSmartFilterTokens( String filterString ) throws GrammarException
   {
      final TokenCollectingEvaluator tokenCollector = new TokenCollectingEvaluator( new NullRtmSmartFilterEvaluator() );
      parseAndEvalRtmSmartFilter( filterString, tokenCollector );
      
      return new RtmSmartFilterTokenCollection( tokenCollector.getTokens() );
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
   
   
   
   private RtmSmartFilterParsingReturn parseAndEvalRtmSmartFilter( String smartFilter,
                                                                   IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      final RtmSmartFilterParser parser = createRtmSmartFilterParser( smartFilter );
      
      try
      {
         final HasCompletedOperatorListener hasCompletedOperatorListener = new HasCompletedOperatorListener();
         parser.addParseListener( hasCompletedOperatorListener );
         
         final ParseTree tree = parser.parseFilter();
         
         if ( evaluator != null )
         {
            final RtmSmartFilterVisitor< Void > visitor = new RtmSmartFilterVisitorAdapter( evaluator );
            visitor.visit( tree );
         }
         
         return new RtmSmartFilterParsingReturn( hasCompletedOperatorListener.hasCompletedOperator() );
      }
      catch ( ParseCancellationException e )
      {
         log.w( RtmSmartFilterParsing.class,
                MessageFormat.format( "Failed to parse smart filter ''{0}''",
                                      smartFilter ),
                e );
         
         throw e;
      }
   }
   
   
   
   private RtmSmartFilterParser createRtmSmartFilterParser( String smartFilter )
   {
      final ANTLRInputStream input = new ANTLRNoCaseStringStream( smartFilter );
      final Lexer lexer = new RtmSmartFilterLexer( input );
      
      final CommonTokenStream antlrTokens = new CommonTokenStream( lexer );
      final RtmSmartFilterParser parser = new RtmSmartFilterParser( antlrTokens );
      
      parser.setErrorHandler( new BailErrorStrategy() );
      
      return parser;
   }
   
   
   private final static class HasCompletedOperatorListener extends
            RtmSmartFilterBaseListener
   {
      private boolean hasCompletedOperator;
      
      
      
      public boolean hasCompletedOperator()
      {
         return hasCompletedOperator;
      }
      
      
      
      @Override
      public void enterCompletedBefore( @NotNull CompletedBeforeContext ctx )
      {
         hasCompletedOperator = true;
         super.enterCompletedBefore( ctx );
      }
      
      
      
      @Override
      public void enterCompleted( @NotNull CompletedContext ctx )
      {
         hasCompletedOperator = true;
         super.enterCompleted( ctx );
      }
      
      
      
      @Override
      public void enterCompletedWithin( @NotNull CompletedWithinContext ctx )
      {
         hasCompletedOperator = true;
         super.enterCompletedWithin( ctx );
      }
      
      
      
      @Override
      public void enterStatusCompleted( @NotNull StatusCompletedContext ctx )
      {
         hasCompletedOperator = true;
         super.enterStatusCompleted( ctx );
      }
      
      
      
      @Override
      public void enterCompletedAfter( @NotNull CompletedAfterContext ctx )
      {
         hasCompletedOperator = true;
         super.enterCompletedAfter( ctx );
      }
   }
}
