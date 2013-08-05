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

package dev.drsoran.moloko.domain.parsing.rtmsmart;

import java.util.Collection;
import java.util.LinkedList;

import dev.drsoran.moloko.grammar.antlr.rtmsmart.RtmSmartFilterLexer;
import dev.drsoran.moloko.grammar.rtmsmart.RtmSmartFilterSyntax;
import dev.drsoran.moloko.util.Strings;


public class TokenCollectingEvaluator implements IRtmSmartFilterEvaluator
{
   private final Collection< RtmSmartFilterToken > tokens = new LinkedList< RtmSmartFilterToken >();
   
   private final IRtmSmartFilterEvaluator decorated;
   
   private boolean opNot;
   
   
   
   public TokenCollectingEvaluator( IRtmSmartFilterEvaluator decorated )
   {
      if ( decorated == null )
      {
         throw new IllegalArgumentException( "decorated" );
      }
      
      this.decorated = decorated;
   }
   
   
   
   @Override
   public String getResult()
   {
      return decorated.getResult();
   }
   
   
   
   @Override
   public void reset()
   {
      tokens.clear();
      opNot = false;
      
      decorated.reset();
   }
   
   
   
   public Collection< RtmSmartFilterToken > getTokens()
   {
      return tokens;
   }
   
   
   
   @Override
   public boolean evalList( String listName )
   {
      addRtmToken( RtmSmartFilterLexer.OP_LIST, listName );
      return decorated.evalList( listName );
   }
   
   
   
   @Override
   public boolean evalPriority( String priority )
   {
      addRtmToken( RtmSmartFilterLexer.OP_PRIORITY, priority );
      return decorated.evalPriority( priority );
   }
   
   
   
   @Override
   public boolean evalStatus( boolean completed )
   {
      addRtmToken( RtmSmartFilterLexer.OP_STATUS,
                   completed ? RtmSmartFilterSyntax.COMPLETED
                            : RtmSmartFilterSyntax.INCOMPLETE );
      return decorated.evalStatus( completed );
   }
   
   
   
   @Override
   public boolean evalTag( String tag )
   {
      addRtmToken( RtmSmartFilterLexer.OP_TAG, tag );
      return decorated.evalTag( tag );
   }
   
   
   
   @Override
   public boolean evalTagContains( String tagContains )
   {
      addRtmToken( RtmSmartFilterLexer.OP_TAG_CONTAINS, tagContains );
      return decorated.evalTagContains( tagContains );
   }
   
   
   
   @Override
   public boolean evalIsTagged( boolean isTagged )
   {
      addRtmToken( RtmSmartFilterLexer.OP_IS_TAGGED,
                   isTagged ? RtmSmartFilterSyntax.TRUE
                           : RtmSmartFilterSyntax.FALSE );
      return decorated.evalIsTagged( isTagged );
   }
   
   
   
   @Override
   public boolean evalLocation( String locationName )
   {
      addRtmToken( RtmSmartFilterLexer.OP_LOCATION, locationName );
      return decorated.evalLocation( locationName );
   }
   
   
   
   @Override
   public boolean evalIsLocated( boolean isLocated )
   {
      addRtmToken( RtmSmartFilterLexer.OP_IS_LOCATED,
                   isLocated ? RtmSmartFilterSyntax.TRUE
                            : RtmSmartFilterSyntax.FALSE );
      return decorated.evalIsLocated( isLocated );
   }
   
   
   
   @Override
   public boolean evalIsRepeating( boolean isRepeating )
   {
      addRtmToken( RtmSmartFilterLexer.OP_IS_REPEATING,
                   isRepeating ? RtmSmartFilterSyntax.TRUE
                              : RtmSmartFilterSyntax.FALSE );
      return decorated.evalIsRepeating( isRepeating );
   }
   
   
   
   @Override
   public boolean evalTaskName( String taskName )
   {
      addRtmToken( RtmSmartFilterLexer.OP_NAME, taskName );
      return decorated.evalTaskName( taskName );
   }
   
   
   
   @Override
   public boolean evalNoteContains( String titleOrText )
   {
      addRtmToken( RtmSmartFilterLexer.OP_NOTE_CONTAINS, titleOrText );
      return decorated.evalNoteContains( titleOrText );
   }
   
   
   
   @Override
   public boolean evalHasNotes( boolean hasNotes )
   {
      addRtmToken( RtmSmartFilterLexer.OP_HAS_NOTES,
                   hasNotes ? RtmSmartFilterSyntax.TRUE
                           : RtmSmartFilterSyntax.FALSE );
      return decorated.evalHasNotes( hasNotes );
   }
   
   
   
   @Override
   public boolean evalDue( String due )
   {
      addRtmToken( RtmSmartFilterLexer.OP_DUE, due );
      return decorated.evalDue( due );
   }
   
   
   
   @Override
   public boolean evalDueAfter( String dueAfter )
   {
      addRtmToken( RtmSmartFilterLexer.OP_DUE_AFTER, dueAfter );
      return decorated.evalDueAfter( dueAfter );
   }
   
   
   
   @Override
   public boolean evalDueBefore( String dueBefore )
   {
      addRtmToken( RtmSmartFilterLexer.OP_DUE_BEFORE, dueBefore );
      return decorated.evalDueBefore( dueBefore );
   }
   
   
   
   @Override
   public boolean evalDueWithIn( String dueWithIn )
   {
      addRtmToken( RtmSmartFilterLexer.OP_DUE_WITHIN, dueWithIn );
      return decorated.evalDueWithIn( dueWithIn );
   }
   
   
   
   @Override
   public boolean evalCompleted( String completed )
   {
      addRtmToken( RtmSmartFilterLexer.OP_COMPLETED, completed );
      return decorated.evalCompleted( completed );
   }
   
   
   
   @Override
   public boolean evalCompletedAfter( String completedAfter )
   {
      addRtmToken( RtmSmartFilterLexer.OP_COMPLETED_AFTER, completedAfter );
      return decorated.evalCompletedAfter( completedAfter );
   }
   
   
   
   @Override
   public boolean evalCompletedBefore( String completedBefore )
   {
      addRtmToken( RtmSmartFilterLexer.OP_COMPLETED_BEFORE, completedBefore );
      return decorated.evalCompletedBefore( completedBefore );
   }
   
   
   
   @Override
   public boolean evalCompletedWithIn( String completedWithIn )
   {
      addRtmToken( RtmSmartFilterLexer.OP_COMPLETED_WITHIN, completedWithIn );
      return decorated.evalCompletedWithIn( completedWithIn );
   }
   
   
   
   @Override
   public boolean evalAdded( String added )
   {
      addRtmToken( RtmSmartFilterLexer.OP_ADDED, added );
      return decorated.evalAdded( added );
   }
   
   
   
   @Override
   public boolean evalAddedAfter( String addedAfter )
   {
      addRtmToken( RtmSmartFilterLexer.OP_ADDED_AFTER, addedAfter );
      return decorated.evalAddedAfter( addedAfter );
   }
   
   
   
   @Override
   public boolean evalAddedBefore( String addedBefore )
   {
      addRtmToken( RtmSmartFilterLexer.OP_ADDED_BEFORE, addedBefore );
      return decorated.evalAddedBefore( addedBefore );
   }
   
   
   
   @Override
   public boolean evalAddedWithIn( String addedWithIn )
   {
      addRtmToken( RtmSmartFilterLexer.OP_ADDED_WITHIN, addedWithIn );
      return decorated.evalAddedWithIn( addedWithIn );
   }
   
   
   
   @Override
   public boolean evalTimeEstimate( String relation, String estimation )
   {
      addRtmToken( RtmSmartFilterLexer.OP_TIME_ESTIMATE, relation + estimation );
      return decorated.evalTimeEstimate( relation, estimation );
   }
   
   
   
   @Override
   public boolean evalPostponed( String relation, int postponedCount )
   {
      addRtmToken( RtmSmartFilterLexer.OP_POSTPONED,
                   Strings.emptyIfNull( relation )
                      + String.valueOf( postponedCount ) );
      return decorated.evalPostponed( relation, postponedCount );
   }
   
   
   
   @Override
   public boolean evalIsShared( boolean isShared )
   {
      addRtmToken( RtmSmartFilterLexer.OP_IS_SHARED,
                   isShared ? RtmSmartFilterSyntax.TRUE
                           : RtmSmartFilterSyntax.FALSE );
      return decorated.evalIsShared( isShared );
   }
   
   
   
   @Override
   public boolean evalSharedWith( String sharedWith )
   {
      addRtmToken( RtmSmartFilterLexer.OP_SHARED_WITH, sharedWith );
      return decorated.evalSharedWith( sharedWith );
   }
   
   
   
   @Override
   public boolean evalLeftParenthesis()
   {
      return decorated.evalLeftParenthesis();
   }
   
   
   
   @Override
   public boolean evalRightParenthesis()
   {
      return decorated.evalRightParenthesis();
   }
   
   
   
   @Override
   public boolean evalAnd()
   {
      opNot = false;
      return decorated.evalAnd();
   }
   
   
   
   @Override
   public boolean evalOr()
   {
      opNot = false;
      return decorated.evalOr();
   }
   
   
   
   @Override
   public boolean evalNot()
   {
      opNot = true;
      return decorated.evalNot();
   }
   
   
   
   private void addRtmToken( int type, String param )
   {
      tokens.add( new RtmSmartFilterToken( type, param, opNot ) );
   }
}
