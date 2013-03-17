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

package dev.drsoran.moloko.grammar.rtmsmart;

public interface IRtmSmartFilterEvaluator
{
   boolean evalList( String listName );
   
   
   
   boolean evalPriority( String priority );
   
   
   
   boolean evalStatus( boolean completed );
   
   
   
   boolean evalTag( String tag );
   
   
   
   boolean evalTagContains( String tagContains );
   
   
   
   boolean evalIsTagged( boolean isTagged );
   
   
   
   boolean evalLocation( String locationName );
   
   
   
   boolean evalIsLocated( boolean isLocated );
   
   
   
   boolean evalIsRepeating( boolean isRepeating );
   
   
   
   boolean evalTaskName( String taskName );
   
   
   
   boolean evalNoteContains( String titleOrText );
   
   
   
   boolean evalHasNotes( boolean hasNotes );
   
   
   
   boolean evalDue( String due );
   
   
   
   boolean evalDueAfter( String dueAfter );
   
   
   
   boolean evalDueBefore( String dueBefore );
   
   
   
   boolean evalDueWithIn( String dueWithIn );
   
   
   
   boolean evalCompleted( String completed );
   
   
   
   boolean evalCompletedAfter( String completedAfter );
   
   
   
   boolean evalCompletedBefore( String completedBefore );
   
   
   
   boolean evalCompletedWithIn( String completedWithIn );
   
   
   
   boolean evalAdded( String added );
   
   
   
   boolean evalAddedAfter( String addedAfter );
   
   
   
   boolean evalAddedBefore( String addedBefore );
   
   
   
   boolean evalAddedWithIn( String addedWithIn );
   
   
   
   boolean evalTimeEstimate( String estimation );
   
   
   
   boolean evalPostponed( String postponed );
   
   
   
   boolean evalIsShared( boolean isShared );
   
   
   
   boolean evalSharedWith( String sharedWith );
   
   
   
   boolean evalLeftParenthesis();
   
   
   
   boolean evalRightParenthesis();
   
   
   
   boolean evalAnd();
   
   
   
   boolean evalOr();
   
   
   
   boolean evalNot();
}
