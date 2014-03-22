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

import dev.drsoran.rtm.Strings;



public class NullRtmSmartFilterEvaluator implements IRtmSmartFilterEvaluator
{
   @Override
   public String getResult()
   {
      return Strings.EMPTY_STRING;
   }
   
   
   
   @Override
   public void reset()
   {
   }
   
   
   
   @Override
   public boolean evalEmptyFilter()
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalList( String listName )
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalPriority( String priority )
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalStatus( boolean completed )
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalTag( String tag )
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalTagContains( String tagContains )
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalIsTagged( boolean isTagged )
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalLocation( String locationName )
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalIsLocated( boolean isLocated )
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalIsRepeating( boolean isRepeating )
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalTaskName( String taskName )
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalNoteContains( String titleOrText )
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalHasNotes( boolean hasNotes )
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalDue( String due )
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalDueAfter( String dueAfter )
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalDueBefore( String dueBefore )
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalDueWithIn( String dueWithIn )
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalCompleted( String completed )
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalCompletedAfter( String completedAfter )
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalCompletedBefore( String completedBefore )
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalCompletedWithIn( String completedWithIn )
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalAdded( String added )
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalAddedAfter( String addedAfter )
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalAddedBefore( String addedBefore )
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalAddedWithIn( String addedWithIn )
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalTimeEstimate( String relation, String estimation )
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalPostponed( String relation, int postponedCount )
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalIsShared( boolean isShared )
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalSharedWith( String sharedWith )
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalLeftParenthesis()
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalRightParenthesis()
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalAnd()
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalOr()
   {
      return true;
   }
   
   
   
   @Override
   public boolean evalNot()
   {
      return true;
   }
}
