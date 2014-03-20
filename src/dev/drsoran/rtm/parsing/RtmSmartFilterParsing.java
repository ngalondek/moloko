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

package dev.drsoran.rtm.parsing;

import java.text.MessageFormat;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

import dev.drsoran.Strings;
import dev.drsoran.moloko.ILog;
import dev.drsoran.rtm.parsing.grammar.ANTLRBailOutErrorListener;
import dev.drsoran.rtm.parsing.grammar.ANTLRNoCaseStringStream;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterVisitor;
import dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax;
import dev.drsoran.rtm.parsing.rtmsmart.IRtmSmartFilterEvaluator;
import dev.drsoran.rtm.parsing.rtmsmart.NullRtmSmartFilterEvaluator;
import dev.drsoran.rtm.parsing.rtmsmart.RtmSmartFilterParsingReturn;
import dev.drsoran.rtm.parsing.rtmsmart.RtmSmartFilterTokenCollection;
import dev.drsoran.rtm.parsing.rtmsmart.RtmSmartFilterVisitorAdapter;
import dev.drsoran.rtm.parsing.rtmsmart.TokenCollectingEvaluator;


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
      smartFilter = transformFilter( smartFilter );
      
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
         log.e( RtmSmartFilterParsing.class,
                MessageFormat.format( "Failed to parse smart filter ''{0}''",
                                      smartFilter ),
                e );
         
         throw new GrammarException( e );
      }
   }
   
   
   
   private static String transformFilter( String filter )
   {
      if ( Strings.isEmptyOrWhitespace( filter ) )
      {
         filter = Strings.EMPTY_STRING;
      }
      
      // Check if there was no operator used. If so it has the
      // same meaning as operator name:
      if ( filter.length() > 0 && !filter.contains( ":" ) )
      {
         filter = RtmSmartFilterSyntax.OP_NAME + Strings.quotify( filter );
      }
      
      return filter;
   }
   
   
   
   private RtmSmartFilterParser createRtmSmartFilterParser( String smartFilter )
   {
      final ANTLRInputStream input = new ANTLRNoCaseStringStream( smartFilter );
      final Lexer lexer = new RtmSmartFilterLexer( input );
      lexer.addErrorListener( new ANTLRBailOutErrorListener() );
      
      final CommonTokenStream antlrTokens = new CommonTokenStream( lexer );
      
      final RtmSmartFilterParser parser = new RtmSmartFilterParser( antlrTokens );
      parser.getInterpreter().setPredictionMode( PredictionMode.SLL );
      parser.setErrorHandler( new BailErrorStrategy() );
      
      return parser;
   }
   
   
   private final static class HasCompletedOperatorListener implements
            ParseTreeListener
   {
      private boolean hasCompletedOperator;
      
      
      
      public boolean hasCompletedOperator()
      {
         return hasCompletedOperator;
      }
      
      
      
      @Override
      public void enterEveryRule( @NotNull ParserRuleContext arg0 )
      {
      }
      
      
      
      @Override
      public void exitEveryRule( @NotNull ParserRuleContext arg0 )
      {
      }
      
      
      
      @Override
      public void visitErrorNode( @NotNull ErrorNode arg0 )
      {
      }
      
      
      
      @Override
      public void visitTerminal( @NotNull TerminalNode node )
      {
         switch ( node.getSymbol().getType() )
         {
            case RtmSmartFilterLexer.OP_COMPLETED:
            case RtmSmartFilterLexer.OP_COMPLETED_AFTER:
            case RtmSmartFilterLexer.OP_COMPLETED_BEFORE:
            case RtmSmartFilterLexer.OP_COMPLETED_WITHIN:
            case RtmSmartFilterLexer.COMPLETED:
               hasCompletedOperator = true;
               break;
            
            default :
               break;
         }
      }
   }
}
