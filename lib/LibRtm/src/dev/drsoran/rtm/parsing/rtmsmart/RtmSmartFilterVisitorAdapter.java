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

package dev.drsoran.rtm.parsing.rtmsmart;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import dev.drsoran.rtm.Strings;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterBaseVisitor;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.AddedAfterContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.AddedBeforeContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.AddedContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.AddedWithinContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.CompletedAfterContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.CompletedBeforeContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.CompletedContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.CompletedWithinContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.DueAfterContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.DueBeforeContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.DueContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.DueWithinContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.EmptyExpressionContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.HasNotesContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.IsLocatedContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.IsRepeatingContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.IsSharedContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.IsTaggedContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.ListContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.LocationContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.LogicAndExpressionContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.LogicOrExpressionContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.NameContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.NegatedExpressionContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.NoteContainsContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.ParenthExpressionContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.PostponedContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.PriorityContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.SharedWithContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.StatusCompletedContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.StatusIncompleteContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.TagContainsContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.TagContext;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser.TimeEstimateContext;
import dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax;


public class RtmSmartFilterVisitorAdapter extends
         RtmSmartFilterBaseVisitor< Void >
{
   private final IRtmSmartFilterEvaluator evaluator;
   
   
   
   public RtmSmartFilterVisitorAdapter( IRtmSmartFilterEvaluator evaluator )
   {
      if ( evaluator == null )
      {
         throw new IllegalArgumentException( "evaluator" );
      }
      
      this.evaluator = evaluator;
   }
   
   
   
   public IRtmSmartFilterEvaluator getEvaluator()
   {
      return evaluator;
   }
   
   
   
   @Override
   public Void visitLogicOrExpression( LogicOrExpressionContext ctx )
   {
      visit( ctx.expression( 0 ) );
      
      throwIfFailed( evaluator.evalOr() );
      
      visit( ctx.expression( 1 ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitLogicAndExpression( LogicAndExpressionContext ctx )
   {
      visit( ctx.expression( 0 ) );
      
      throwIfFailed( evaluator.evalAnd() );
      
      visit( ctx.expression( 1 ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitNegatedExpression( NegatedExpressionContext ctx )
   {
      throwIfFailed( evaluator.evalNot() );
      
      visit( ctx.expression() );
      
      return null;
   }
   
   
   
   @Override
   public Void visitParenthExpression( ParenthExpressionContext ctx )
   {
      throwIfFailed( evaluator.evalLeftParenthesis() );
      
      visit( ctx.expression() );
      
      throwIfFailed( evaluator.evalRightParenthesis() );
      
      return null;
   }
   
   
   
   @Override
   public Void visitEmptyExpression( EmptyExpressionContext ctx )
   {
      throwIfFailed( evaluator.evalEmptyFilter() );
      return null;
   }
   
   
   
   @Override
   public Void visitList( ListContext ctx )
   {
      final String string = asString( ctx.s );
      throwIfFailed( evaluator.evalList( string ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitTag( TagContext ctx )
   {
      final String string = asString( ctx.s );
      throwIfFailed( evaluator.evalTag( string ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitTagContains( TagContainsContext ctx )
   {
      final String string = asString( ctx.s );
      throwIfFailed( evaluator.evalTagContains( string ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitDue( DueContext ctx )
   {
      final String string = asString( ctx.s );
      throwIfFailed( evaluator.evalDue( string ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitDueAfter( DueAfterContext ctx )
   {
      final String string = asString( ctx.s );
      throwIfFailed( evaluator.evalDueAfter( string ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitDueBefore( DueBeforeContext ctx )
   {
      final String string = asString( ctx.s );
      throwIfFailed( evaluator.evalDueBefore( string ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitDueWithin( DueWithinContext ctx )
   {
      final String string = asString( ctx.s );
      throwIfFailed( evaluator.evalDueWithIn( string ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitAdded( AddedContext ctx )
   {
      final String string = asString( ctx.s );
      throwIfFailed( evaluator.evalAdded( string ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitAddedAfter( AddedAfterContext ctx )
   {
      final String string = asString( ctx.s );
      throwIfFailed( evaluator.evalAddedAfter( string ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitAddedBefore( AddedBeforeContext ctx )
   {
      final String string = asString( ctx.s );
      throwIfFailed( evaluator.evalAddedBefore( string ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitAddedWithin( AddedWithinContext ctx )
   {
      final String string = asString( ctx.s );
      throwIfFailed( evaluator.evalAddedWithIn( string ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitCompleted( CompletedContext ctx )
   {
      final String string = asString( ctx.s );
      throwIfFailed( evaluator.evalCompleted( string ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitCompletedAfter( CompletedAfterContext ctx )
   {
      final String string = asString( ctx.s );
      throwIfFailed( evaluator.evalCompletedAfter( string ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitCompletedBefore( CompletedBeforeContext ctx )
   {
      final String string = asString( ctx.s );
      throwIfFailed( evaluator.evalCompletedBefore( string ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitCompletedWithin( CompletedWithinContext ctx )
   {
      final String string = asString( ctx.s );
      throwIfFailed( evaluator.evalCompletedWithIn( string ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitIsTagged( IsTaggedContext ctx )
   {
      final boolean isTagged = asBoolean( ctx.b );
      throwIfFailed( evaluator.evalIsTagged( isTagged ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitIsShared( IsSharedContext ctx )
   {
      final boolean bool = asBoolean( ctx.b );
      throwIfFailed( evaluator.evalIsShared( bool ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitIsRepeating( IsRepeatingContext ctx )
   {
      final boolean bool = asBoolean( ctx.b );
      throwIfFailed( evaluator.evalIsRepeating( bool ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitIsLocated( IsLocatedContext ctx )
   {
      final boolean bool = asBoolean( ctx.b );
      throwIfFailed( evaluator.evalIsLocated( bool ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitHasNotes( HasNotesContext ctx )
   {
      final boolean bool = asBoolean( ctx.b );
      throwIfFailed( evaluator.evalHasNotes( bool ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitSharedWith( SharedWithContext ctx )
   {
      final String string = asString( ctx.s );
      throwIfFailed( evaluator.evalSharedWith( string ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitNoteContains( NoteContainsContext ctx )
   {
      final String string = asString( ctx.s );
      throwIfFailed( evaluator.evalNoteContains( string ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitLocation( LocationContext ctx )
   {
      final String string = asString( ctx.s );
      throwIfFailed( evaluator.evalLocation( string ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitPriority( PriorityContext ctx )
   {
      final String string = asString( ctx.p );
      throwIfFailed( evaluator.evalPriority( string ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitTimeEstimate( TimeEstimateContext ctx )
   {
      final String param = asString( ctx.RELATION_PARAM().getSymbol() );
      final String relation = param.substring( 0, 1 );
      final String estimation = param.substring( 1 );
      
      throwIfFailed( evaluator.evalTimeEstimate( relation, estimation ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitName( NameContext ctx )
   {
      final String string = asString( ctx.s );
      throwIfFailed( evaluator.evalTaskName( string ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitStatusCompleted( StatusCompletedContext ctx )
   {
      throwIfFailed( evaluator.evalStatus( true ) );
      return null;
   }
   
   
   
   @Override
   public Void visitStatusIncomplete( StatusIncompleteContext ctx )
   {
      throwIfFailed( evaluator.evalStatus( false ) );
      return null;
   }
   
   
   
   @Override
   public Void visitPostponed( PostponedContext ctx )
   {
      String relation = Strings.EMPTY_STRING;
      final int postponedCount;
      
      if ( ctx.r != null )
      {
         final String param = asString( ctx.r );
         relation = param.substring( 0, 1 );
         
         if ( relation.equals( RtmSmartFilterSyntax.LESS )
            || relation.equals( RtmSmartFilterSyntax.GREATER )
            || relation.equals( RtmSmartFilterSyntax.EQUAL ) )
         {
            postponedCount = asInteger( param.substring( 1 ) );
         }
         else
         {
            relation = Strings.EMPTY_STRING;
            postponedCount = asInteger( param );
         }
      }
      else
      {
         postponedCount = asInteger( ctx.s.getText() );
      }
      
      throwIfFailed( evaluator.evalPostponed( relation, postponedCount ) );
      
      return null;
   }
   
   
   
   private String asString( Token token )
   {
      final String value = Strings.unquotify( token.getText() );
      return value;
   }
   
   
   
   private boolean asBoolean( Token token )
   {
      final boolean value = Boolean.parseBoolean( token.getText() );
      return value;
   }
   
   
   
   private int asInteger( String string )
   {
      try
      {
         return Integer.parseInt( string );
      }
      catch ( NumberFormatException e )
      {
         throw new ParseCancellationException( e );
      }
   }
   
   
   
   private void throwIfFailed( boolean success )
   {
      if ( !success )
      {
         throw new ParseCancellationException();
      }
   }
}
