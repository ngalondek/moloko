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

package dev.drsoran.moloko.content.db;

import java.util.Calendar;

import dev.drsoran.Strings;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.db.TableColumns.RtmLocationColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmNoteColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmParticipantColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmRawTaskColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTaskSeriesColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTasksListColumns;
import dev.drsoran.rtm.RtmCalendar;
import dev.drsoran.rtm.model.Priority;
import dev.drsoran.rtm.parsing.GrammarException;
import dev.drsoran.rtm.parsing.IRtmDateTimeParsing;
import dev.drsoran.rtm.parsing.datetime.ParseDateWithinReturn;
import dev.drsoran.rtm.parsing.rtmsmart.IRtmSmartFilterEvaluator;


public class DbRtmSmartFilterEvaluator implements IRtmSmartFilterEvaluator
{
   private final static String DEFAULT_OPERATOR = " AND ";
   
   private final StringBuilder result = new StringBuilder();
   
   private final IRtmDateTimeParsing dateTimeParsing;
   
   // We do not insert the default operator for the first, lexed token.
   // So this starts as true.
   private boolean lexedOperator = true;
   
   
   
   public DbRtmSmartFilterEvaluator( IRtmDateTimeParsing dateTimeParsing )
   {
      if ( dateTimeParsing == null )
      {
         throw new IllegalArgumentException( "dateTimeParsing" );
      }
      
      this.dateTimeParsing = dateTimeParsing;
   }
   
   
   
   @Override
   public String getResult()
   {
      if ( result.length() == 0 )
      {
         return null;
      }
      
      result.insert( 0, "( " ).append( " )" );
      return result.toString();
   }
   
   
   
   @Override
   public void reset()
   {
      result.setLength( 0 );
      
      // We do not insert the default operator for the first, lexed token.
      // So this starts as true.
      lexedOperator = true;
   }
   
   
   
   @Override
   public boolean evalList( String listName )
   {
      ensureOperatorAndResetLexedOperatorState();
      
      result.append( RtmTasksListColumns.LIST_NAME );
      likeStringParam( listName );
      
      return true;
   }
   
   
   
   @Override
   public boolean evalPriority( String priority )
   {
      final boolean ok = Priority.isValid( priority );
      
      if ( ok )
      {
         ensureOperatorAndResetLexedOperatorState();
         
         result.append( RtmRawTaskColumns.PRIORITY );
         likeStringParam( priority );
      }
      
      return ok;
   }
   
   
   
   @Override
   public boolean evalStatus( boolean completed )
   {
      ensureOperatorAndResetLexedOperatorState();
      
      result.append( RtmRawTaskColumns.COMPLETED_DATE );
      
      if ( completed )
      {
         result.append( " IS NOT NULL" );
      }
      else
      {
         result.append( " IS NULL" );
      }
      
      return true;
   }
   
   
   
   @Override
   public boolean evalTag( String tag )
   {
      ensureOperatorAndResetLexedOperatorState();
      
      result.append( "(" ).append( RtmTaskSeriesColumns.TAGS )
      // Exact match if only 1 tag
            .append( " = '" )
            .append( tag )
            .append( "' OR " )
            // match for the case tag, (prefix)
            .append( RtmTaskSeriesColumns.TAGS )
            .append( " LIKE '" )
            .append( tag )
            .append( RtmTaskSeriesColumns.TAGS_SEPARATOR )
            .append( "%' OR " )
            // match for the case ,tag, (infix)
            .append( RtmTaskSeriesColumns.TAGS )
            .append( " LIKE '%" )
            .append( RtmTaskSeriesColumns.TAGS_SEPARATOR )
            .append( tag )
            .append( RtmTaskSeriesColumns.TAGS_SEPARATOR )
            .append( "%' OR " )
            // match for the case ,tag (suffix)
            .append( RtmTaskSeriesColumns.TAGS )
            .append( " LIKE '%" )
            .append( RtmTaskSeriesColumns.TAGS_SEPARATOR )
            .append( tag )
            .append( "')" );
      
      return true;
   }
   
   
   
   @Override
   public boolean evalTagContains( String tagContains )
   {
      ensureOperatorAndResetLexedOperatorState();
      
      result.append( RtmTaskSeriesColumns.TAGS );
      containsStringParam( tagContains );
      
      return true;
   }
   
   
   
   @Override
   public boolean evalIsTagged( boolean isTagged )
   {
      ensureOperatorAndResetLexedOperatorState();
      result.append( RtmTaskSeriesColumns.TAGS );
      
      if ( isTagged )
      {
         result.append( " IS NOT NULL" );
      }
      else
      {
         result.append( " IS NULL" );
      }
      
      return true;
   }
   
   
   
   @Override
   public boolean evalLocation( String locationName )
   {
      ensureOperatorAndResetLexedOperatorState();
      
      result.append( RtmLocationColumns.LOCATION_NAME );
      likeStringParam( locationName );
      
      return true;
   }
   
   
   
   @Override
   public boolean evalIsLocated( boolean isLocated )
   {
      ensureOperatorAndResetLexedOperatorState();
      
      if ( isLocated )
      {
         result.append( "(" ).append( RtmTaskSeriesColumns.LOCATION_ID )
         // Handle the case that shared tasks have a location
         // ID but not from our DB. So a simple NOT NULL check is not
         // enough. We have to look up the location ID.
               .append( " IS NOT NULL AND " )
               .append( RtmTaskSeriesColumns.LOCATION_ID )
               .append( " IN (SELECT " )
               .append( RtmLocationColumns._ID )
               .append( " FROM " )
               .append( RtmLocationsTable.TABLE_NAME )
               .append( "))" );
      }
      else
      {
         result.append( RtmTaskSeriesColumns.LOCATION_ID ).append( " IS NULL" );
      }
      
      return true;
   }
   
   
   
   @Override
   public boolean evalIsRepeating( boolean isRepeating )
   {
      ensureOperatorAndResetLexedOperatorState();
      result.append( RtmTaskSeriesColumns.RECURRENCE );
      
      if ( isRepeating )
      {
         result.append( " IS NOT NULL" );
      }
      else
      {
         result.append( " IS NULL" );
      }
      
      return true;
   }
   
   
   
   @Override
   public boolean evalTaskName( String taskName )
   {
      ensureOperatorAndResetLexedOperatorState();
      
      result.append( RtmTaskSeriesColumns.TASKSERIES_NAME );
      containsStringParam( taskName );
      
      return true;
   }
   
   
   
   @Override
   public boolean evalNoteContains( String titleOrText )
   {
      ensureOperatorAndResetLexedOperatorState();
      
      result.append( "(SELECT " )
            .append( RtmNoteColumns.TASKSERIES_ID )
            .append( " FROM " )
            .append( RtmNotesTable.TABLE_NAME )
            .append( " WHERE " )
            .append( RtmNoteColumns.TASKSERIES_ID )
            .append( "=" )
            .append( RtmTaskSeriesTable.TABLE_NAME )
            .append( "." )
            .append( RtmTaskSeriesColumns._ID )
            .append( " AND " )
            .append( RtmNoteColumns.NOTE_TITLE );
      
      containsStringParam( titleOrText );
      
      result.append( " OR " ).append( RtmNoteColumns.NOTE_TEXT );
      
      containsStringParam( titleOrText );
      
      result.append( ")" );
      
      return true;
   }
   
   
   
   @Override
   public boolean evalHasNotes( boolean hasNotes )
   {
      ensureOperatorAndResetLexedOperatorState();
      
      result.append( "(" )
            .append( RtmTaskSeriesTable.TABLE_NAME )
            .append( "." )
            .append( RtmTaskSeriesColumns._ID );
      
      if ( hasNotes )
      {
         result.append( " IN " );
      }
      else
      {
         result.append( " NOT IN " );
      }
      
      result.append( "(SELECT " )
            .append( RtmNoteColumns.TASKSERIES_ID )
            .append( " FROM " )
            .append( RtmNotesTable.TABLE_NAME )
            .append( "))" );
      
      return true;
   }
   
   
   
   @Override
   public boolean evalDue( String due )
   {
      ensureOperatorAndResetLexedOperatorState();
      
      final boolean ok = equalsTimeParam( RtmRawTaskColumns.DUE_DATE, due );
      
      return ok;
   }
   
   
   
   @Override
   public boolean evalDueAfter( String dueAfter )
   {
      ensureOperatorAndResetLexedOperatorState();
      
      final boolean ok = differsTimeParam( RtmRawTaskColumns.DUE_DATE,
                                           dueAfter,
                                           false );
      
      return ok;
   }
   
   
   
   @Override
   public boolean evalDueBefore( String dueBefore )
   {
      ensureOperatorAndResetLexedOperatorState();
      
      final boolean ok = differsTimeParam( RtmRawTaskColumns.DUE_DATE,
                                           dueBefore,
                                           true );
      
      return ok;
   }
   
   
   
   @Override
   public boolean evalDueWithIn( String dueWithIn )
   {
      ensureOperatorAndResetLexedOperatorState();
      
      final boolean ok = inTimeParamRange( RtmRawTaskColumns.DUE_DATE,
                                           dueWithIn,
                                           false );
      
      return ok;
   }
   
   
   
   @Override
   public boolean evalCompleted( String completed )
   {
      ensureOperatorAndResetLexedOperatorState();
      
      final boolean ok = equalsTimeParam( RtmRawTaskColumns.COMPLETED_DATE,
                                          completed );
      
      return ok;
   }
   
   
   
   @Override
   public boolean evalCompletedAfter( String completedAfter )
   {
      ensureOperatorAndResetLexedOperatorState();
      
      final boolean ok = differsTimeParam( RtmRawTaskColumns.COMPLETED_DATE,
                                           completedAfter,
                                           false );
      
      return ok;
   }
   
   
   
   @Override
   public boolean evalCompletedBefore( String completedBefore )
   {
      ensureOperatorAndResetLexedOperatorState();
      
      final boolean ok = differsTimeParam( RtmRawTaskColumns.COMPLETED_DATE,
                                           completedBefore,
                                           true );
      
      return ok;
   }
   
   
   
   @Override
   public boolean evalCompletedWithIn( String completedWithIn )
   {
      ensureOperatorAndResetLexedOperatorState();
      
      final boolean ok = inTimeParamRange( RtmRawTaskColumns.COMPLETED_DATE,
                                           completedWithIn,
                                           true );
      
      return ok;
   }
   
   
   
   @Override
   public boolean evalAdded( String added )
   {
      ensureOperatorAndResetLexedOperatorState();
      
      final boolean ok = equalsTimeParam( RtmRawTaskColumns.ADDED_DATE, added );
      
      return ok;
   }
   
   
   
   @Override
   public boolean evalAddedAfter( String addedAfter )
   {
      ensureOperatorAndResetLexedOperatorState();
      
      final boolean ok = differsTimeParam( RtmRawTaskColumns.ADDED_DATE,
                                           addedAfter,
                                           false );
      
      return ok;
   }
   
   
   
   @Override
   public boolean evalAddedBefore( String addedBefore )
   {
      ensureOperatorAndResetLexedOperatorState();
      
      final boolean ok = differsTimeParam( RtmRawTaskColumns.ADDED_DATE,
                                           addedBefore,
                                           true );
      
      return ok;
   }
   
   
   
   @Override
   public boolean evalAddedWithIn( String addedWithIn )
   {
      ensureOperatorAndResetLexedOperatorState();
      
      final boolean ok = inTimeParamRange( RtmRawTaskColumns.ADDED_DATE,
                                           addedWithIn,
                                           true );
      
      return ok;
   }
   
   
   
   @Override
   public boolean evalTimeEstimate( String relation, String estimation )
   {
      ensureOperatorAndResetLexedOperatorState();
      
      try
      {
         result.append( "(" ).append( RtmRawTaskColumns.ESTIMATE_MILLIS );
         
         long estimatedMillis = Constants.NO_TIME;
         if ( !Strings.isNullOrEmpty( relation ) )
         {
            result.append( " > " )
                  .append( Constants.NO_TIME )
                  .append( " AND " )
                  .append( RtmRawTaskColumns.ESTIMATE_MILLIS )
                  .append( relation );
         }
         else
         {
            result.append( "=" );
         }
         
         estimatedMillis = dateTimeParsing.parseEstimated( estimation );
         
         result.append( estimatedMillis ).append( ")" );
         
         return true;
      }
      catch ( GrammarException e )
      {
         return false;
      }
   }
   
   
   
   @Override
   public boolean evalPostponed( String relation, int postponedCount )
   {
      boolean ok = postponedCount > -1;
      
      if ( ok )
      {
         ensureOperatorAndResetLexedOperatorState();
         result.append( RtmRawTaskColumns.POSTPONED );
         
         if ( !Strings.isNullOrEmpty( relation ) )
         {
            result.append( relation );
         }
         else
         {
            result.append( "=" );
         }
         
         result.append( postponedCount );
      }
      
      return ok;
   }
   
   
   
   @Override
   public boolean evalIsShared( boolean isShared )
   {
      ensureOperatorAndResetLexedOperatorState();
      
      result.append( "(" )
            .append( RtmTaskSeriesTable.TABLE_NAME )
            .append( "." )
            .append( RtmTaskSeriesColumns._ID );
      
      if ( isShared )
      {
         result.append( " IN " );
      }
      else
      {
         result.append( " NOT IN " );
      }
      
      result.append( "(SELECT " )
            .append( RtmParticipantColumns.TASKSERIES_ID )
            .append( " FROM " )
            .append( RtmParticipantsTable.TABLE_NAME )
            .append( "))" );
      
      return true;
   }
   
   
   
   @Override
   public boolean evalSharedWith( String sharedWith )
   {
      ensureOperatorAndResetLexedOperatorState();
      
      result.append( "(SELECT " )
            .append( RtmParticipantColumns.TASKSERIES_ID )
            .append( " FROM " )
            .append( RtmParticipantsTable.TABLE_NAME )
            .append( " WHERE " )
            .append( RtmParticipantColumns.TASKSERIES_ID )
            .append( "=" )
            .append( RtmTaskSeriesTable.TABLE_NAME )
            .append( "." )
            .append( RtmTaskSeriesColumns._ID )
            .append( " AND " )
            .append( RtmParticipantColumns.FULLNAME );
      
      containsStringParam( sharedWith );
      
      result.append( " OR " ).append( RtmParticipantColumns.USERNAME );
      
      containsStringParam( sharedWith );
      
      result.append( ")" );
      
      return true;
   }
   
   
   
   @Override
   public boolean evalLeftParenthesis()
   {
      ensureOperator();
      result.append( "( " );
      
      return true;
   }
   
   
   
   @Override
   public boolean evalRightParenthesis()
   {
      result.append( " )" );
      lexedOperator = false;
      
      return true;
   }
   
   
   
   @Override
   public boolean evalAnd()
   {
      result.append( " AND " );
      lexedOperator = true;
      
      return true;
   }
   
   
   
   @Override
   public boolean evalOr()
   {
      result.append( " OR " );
      lexedOperator = true;
      
      return true;
   }
   
   
   
   @Override
   public boolean evalNot()
   {
      ensureOperator();
      result.append( " NOT " );
      
      return true;
   }
   
   
   
   private final void ensureOperator()
   {
      if ( !lexedOperator )
      {
         result.append( DEFAULT_OPERATOR );
         lexedOperator = true;
      }
   }
   
   
   
   private final void ensureOperatorAndResetLexedOperatorState()
   {
      ensureOperator();
      lexedOperator = false;
   }
   
   
   
   private void likeStringParam( String param )
   {
      result.append( " LIKE '" ).append( param ).append( "'" );
   }
   
   
   
   private void containsStringParam( String param )
   {
      result.append( " LIKE '%" ).append( param ).append( "%'" );
   }
   
   
   
   private boolean equalsTimeParam( String column, String param )
   {
      final RtmCalendar cal;
      try
      {
         cal = dateTimeParsing.parseDateTime( param );
      }
      catch ( GrammarException e )
      {
         return false;
      }
      
      result.append( "(" ).append( column );
      
      // Check if we have 'NEVER'
      if ( !cal.hasDate() )
      {
         result.append( " IS NULL" );
      }
      
      // Check if we have an explicit time
      // given.
      else if ( cal.hasTime() )
      {
         result.append( " == " ).append( cal.getTimeInMillis() );
      }
      else
      {
         result.append( " >= " )
               .append( cal.getTimeInMillis() )
               .append( " AND " );
         
         cal.add( Calendar.DAY_OF_YEAR, 1 );
         
         result.append( column ).append( " < " ).append( cal.getTimeInMillis() );
      }
      
      result.append( ")" );
      
      return true;
   }
   
   
   
   private boolean differsTimeParam( String column, String param, boolean before )
   {
      RtmCalendar cal;
      try
      {
         cal = dateTimeParsing.parseDateTime( param );
      }
      // If simple time parsing failed, try parse date within
      catch ( GrammarException e )
      {
         try
         {
            cal = dateTimeParsing.parseDateWithin( param ).endEpoch;
         }
         catch ( GrammarException e1 )
         {
            return false;
         }
      }
      
      result.append( "(" ).append( column );
      
      // Check if we have 'NEVER'
      if ( !cal.hasDate() )
      {
         result.append( " IS NULL" );
      }
      else
      {
         result.append( before ? " < " : " > " ).append( cal.getTimeInMillis() );
      }
      
      result.append( ")" );
      
      return true;
   }
   
   
   
   private boolean inTimeParamRange( String column, String param, boolean past )
   {
      try
      {
         final ParseDateWithinReturn range = dateTimeParsing.parseDateWithin( param );
         result.append( "(" )
               .append( column )
               .append( " >= " )
               .append( !past ? range.startEpoch.getTimeInMillis()
                             : range.endEpoch.getTimeInMillis() )
               .append( " AND " )
               .append( column )
               .append( " < " )
               .append( !past ? range.endEpoch.getTimeInMillis()
                             : range.startEpoch.getTimeInMillis() )
               .append( ")" );
         
         return true;
      }
      catch ( GrammarException e )
      {
         return false;
      }
   }
}
