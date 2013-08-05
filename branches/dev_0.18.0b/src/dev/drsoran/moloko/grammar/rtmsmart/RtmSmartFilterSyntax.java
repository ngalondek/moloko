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

public final class RtmSmartFilterSyntax
{
   private RtmSmartFilterSyntax()
   {
      throw new AssertionError();
   }
   
   public final static String OP_LIST = "list:";
   
   public final static String OP_PRIORITY = "priority:";
   
   public final static String OP_STATUS = "status:";
   
   public final static String OP_TAG = "tag:";
   
   public final static String OP_TAG_CONTAINS = "tagcontains:";
   
   public final static String OP_IS_TAGGED = "istagged:";
   
   public final static String OP_LOCATION = "location:";
   
   public final static String OP_LOCATION_WITHIN = "locationwithin:";
   
   public final static String OP_IS_LOCATED = "islocated:";
   
   public final static String OP_IS_REPEATING = "isrepeating:";
   
   public final static String OP_NAME = "name:";
   
   public final static String OP_NOTE_CONTAINS = "notecontains:";
   
   public final static String OP_HAS_NOTES = "hasnotes:";
   
   public final static String OP_DUE = "due:";
   
   public final static String OP_DUE_AFTER = "dueafter:";
   
   public final static String OP_DUE_BEFORE = "duebefore:";
   
   public final static String OP_DUE_WITHIN = "duewithin:";
   
   public final static String OP_COMPLETED = "completed:";
   
   public final static String OP_COMPLETED_BEFORE = "completedbefore:";
   
   public final static String OP_COMPLETED_AFTER = "completedafter:";
   
   public final static String OP_COMPLETED_WITHIN = "completedwithin:";
   
   public final static String OP_ADDED = "added:";
   
   public final static String OP_ADDED_BEFORE = "addedbefore:";
   
   public final static String OP_ADDED_AFTER = "addedafter:";
   
   public final static String OP_ADDED_WITHIN = "addedwithin:";
   
   public final static String OP_TIME_ESTIMATE = "timeestimate:";
   
   public final static String OP_POSTPONED = "postponed:";
   
   public final static String OP_IS_SHARED = "isshared:";
   
   public final static String OP_SHARED_WITH = "sharedwith:";
   
   public final static String COMPLETED = "completed";
   
   public final static String INCOMPLETE = "incomplete";
   
   public final static String TRUE = "true";
   
   public final static String FALSE = "false";
   
   public final static String PRIO_HIGH = "1";
   
   public final static String PRIO_MED = "2";
   
   public final static String PRIO_LOW = "3";
   
   public final static String PRIO_NONE = "n";
   
   public final static String L_PARENTH = "(";
   
   public final static String R_PARENTH = ")";
   
   public final static String AND = "and";
   
   public final static String OR = "or";
   
   public final static String NOT = "not";
   
   public final static String LESS = "<";
   
   public final static String GREATER = ">";
   
   public final static String EQUAL = "=";
}
