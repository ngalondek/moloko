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

import android.text.TextUtils;
import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.db.TableColumns.RtmLocationColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmNoteColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmParticipantColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmRawTaskColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTaskSeriesColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTasksListColumns;
import dev.drsoran.moloko.grammar.GrammarException;
import dev.drsoran.moloko.grammar.IDateTimeParsing;
import dev.drsoran.moloko.grammar.datetime.ParseDateWithinReturn;
import dev.drsoran.moloko.grammar.rtmsmart.IRtmSmartFilterEvaluator;
import dev.drsoran.moloko.util.Strings;


public class DbRtmSmartFilterEvaluator implements IRtmSmartFilterEvaluator
{
   private final static String DEFAULT_OPERATOR = " AND ";
   
   private final StringBuilder result = new StringBuilder();
   
   private final IDateTimeParsing dateTimeParsing;
   
   // We do not insert the default operator for the first, lexed token.
   // So this starts as true.
   private boolean lexedOperator = true;
   
   
   
   public DbRtmSmartFilterEvaluator( IDateTimeParsing dateTimeParsing )
   {
      this.dateTimeParsing = dateTimeParsing;
   }
   
   
   
   @Override
   public String getResult()
   {
      result.insert( 0, "( " ).append( " )" );
      return result.toString();
   }
   
   
   
   @Override
   public void reset()
   {
      result.setLength( 0 );
   }
   
   
   
   @Override
   public boolean evalList( String listName )
   {
      ensureOperatorAndResetState();
      
      result.append( RtmTasksListColumns.LIST_NAME );
      likeStringParam( listName );
      
      return true;
   }
   
   
   
   @Override
   public boolean evalPriority( String priority )
   {
      ensureOperatorAndResetState();
      
      result.append( RtmRawTaskColumns.PRIORITY );
      likeStringParam( priority );
      
      return true;
   }
   
   
   
   @Override
   public boolean evalStatus( boolean completed )
   {
      ensureOperatorAndResetState();
      
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
      ensureOperatorAndResetState();
      
      final String unqString = Strings.unquotify( tag );
      
      result.append( "(" ).append( RtmTaskSeriesColumns.TAGS )
      // Exact match if only 1 tag
            .append( " = '" )
            .append( unqString )
            .append( "' OR " )
            // match for the case tag, (prefix)
            .append( RtmTaskSeriesColumns.TAGS )
            .append( " LIKE '" )
            .append( unqString )
            .append( RtmTaskSeriesColumns.TAGS_SEPARATOR )
            .append( "%' OR " )
            // match for the case ,tag, (infix)
            .append( RtmTaskSeriesColumns.TAGS )
            .append( " LIKE '%" )
            .append( RtmTaskSeriesColumns.TAGS_SEPARATOR )
            .append( unqString )
            .append( RtmTaskSeriesColumns.TAGS_SEPARATOR )
            .append( "%' OR " )
            // match for the case ,tag (suffix)
            .append( RtmTaskSeriesColumns.TAGS )
            .append( " LIKE '%" )
            .append( RtmTaskSeriesColumns.TAGS_SEPARATOR )
            .append( unqString )
            .append( "')" );
      
      return true;
   }
   
   
   
   @Override
   public boolean evalTagContains( String tagContains )
   {
      ensureOperatorAndResetState();
      
      result.append( RtmTaskSeriesColumns.TAGS );
      containsStringParam( tagContains );
      
      return true;
   }
   
   
   
   @Override
   public boolean evalIsTagged( boolean isTagged )
   {
      ensureOperatorAndResetState();
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
      ensureOperatorAndResetState();
      
      result.append( RtmLocationColumns.LOCATION_NAME );
      likeStringParam( locationName );
      
      return true;
   }
   
   
   
   @Override
   public boolean evalIsLocated( boolean isLocated )
   {
      ensureOperatorAndResetState();
      
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
      ensureOperatorAndResetState();
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
      ensureOperatorAndResetState();
      
      result.append( RtmTaskSeriesColumns.TASKSERIES_NAME );
      containsStringParam( taskName );
      
      return true;
   }
   
   
   
   @Override
   public boolean evalNoteContains( String titleOrText )
   {
      ensureOperatorAndResetState();
      
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
      ensureOperatorAndResetState();
      
      result.append( "(" )
            .append( RtmTaskSeriesTable.TABLE_NAME )
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
      ensureOperatorAndResetState();
      
      final boolean ok = equalsTimeParam( RtmRawTaskColumns.DUE_DATE, due );
      
      return ok;
   }
   
   
   
   @Override
   public boolean evalDueAfter( String dueAfter )
   {
      ensureOperatorAndResetState();
      
      final boolean ok = differsTimeParam( RtmRawTaskColumns.DUE_DATE,
                                           dueAfter,
                                           false );
      
      return ok;
   }
   
   
   
   @Override
   public boolean evalDueBefore( String dueBefore )
   {
      ensureOperatorAndResetState();
      
      final boolean ok = differsTimeParam( RtmRawTaskColumns.DUE_DATE,
                                           dueBefore,
                                           true );
      
      return ok;
   }
   
   
   
   @Override
   public boolean evalDueWithIn( String dueWithIn )
   {
      ensureOperatorAndResetState();
      
      final boolean ok = inTimeParamRange( RtmRawTaskColumns.DUE_DATE,
                                           dueWithIn,
                                           false );
      
      return ok;
   }
   
   
   
   @Override
   public boolean evalCompleted( String completed )
   {
      ensureOperatorAndResetState();
      
      final boolean ok = equalsTimeParam( RtmRawTaskColumns.COMPLETED_DATE,
                                          completed );
      
      return ok;
   }
   
   
   
   @Override
   public boolean evalCompletedAfter( String completedAfter )
   {
      ensureOperatorAndResetState();
      
      final boolean ok = differsTimeParam( RtmRawTaskColumns.COMPLETED_DATE,
                                           completedAfter,
                                           false );
      
      return ok;
   }
   
   
   
   @Override
   public boolean evalCompletedBefore( String completedBefore )
   {
      ensureOperatorAndResetState();
      
      final boolean ok = differsTimeParam( RtmRawTaskColumns.COMPLETED_DATE,
                                           completedBefore,
                                           true );
      
      return ok;
   }
   
   
   
   @Override
   public boolean evalCompletedWithIn( String completedWithIn )
   {
      ensureOperatorAndResetState();
      
      final boolean ok = inTimeParamRange( RtmRawTaskColumns.COMPLETED_DATE,
                                           completedWithIn,
                                           true );
      
      return ok;
   }
   
   
   
   @Override
   public boolean evalAdded( String added )
   {
      ensureOperatorAndResetState();
      
      final boolean ok = equalsTimeParam( RtmRawTaskColumns.ADDED_DATE, added );
      
      return ok;
   }
   
   
   
   @Override
   public boolean evalAddedAfter( String addedAfter )
   {
      ensureOperatorAndResetState();
      
      final boolean ok = differsTimeParam( RtmRawTaskColumns.ADDED_DATE,
                                           addedAfter,
                                           false );
      
      return ok;
   }
   
   
   
   @Override
   public boolean evalAddedBefore( String addedBefore )
   {
      ensureOperatorAndResetState();
      
      final boolean ok = differsTimeParam( RtmRawTaskColumns.ADDED_DATE,
                                           addedBefore,
                                           true );
      
      return ok;
   }
   
   
   
   @Override
   public boolean evalAddedWithIn( String addedWithIn )
   {
      ensureOperatorAndResetState();
      
      final boolean ok = inTimeParamRange( RtmRawTaskColumns.ADDED_DATE,
                                           addedWithIn,
                                           true );
      
      return ok;
   }
   
   
   
   @Override
   public boolean evalTimeEstimate( String estimation )
   {
      ensureOperatorAndResetState();
      
      try
      {
         result.append( "(" ).append( RtmRawTaskColumns.ESTIMATE_MILLIS );
         
         final String param = Strings.unquotify( estimation );
         
         long estimatedMillis = Constants.NO_TIME;
         final char chPos0 = param.charAt( 0 );
         
         if ( chPos0 == '<' || chPos0 == '>' )
         {
            result.append( " > " )
                  .append( Constants.NO_TIME )
                  .append( " AND " )
                  .append( RtmRawTaskColumns.ESTIMATE_MILLIS )
                  .append( chPos0 );
            
            estimatedMillis = dateTimeParsing.parseEstimated( param.substring( 1 ) );
         }
         else
         {
            result.append( "=" );
            estimatedMillis = dateTimeParsing.parseEstimated( param );
         }
         
         result.append( estimatedMillis ).append( ")" );
         
         return true;
      }
      catch ( GrammarException e )
      {
         return false;
      }
   }
   
   
   
   @Override
   public boolean evalPostponed( String postponed )
   {
      ensureOperatorAndResetState();
      result.append( RtmRawTaskColumns.POSTPONED );
      
      boolean ok = true;
      
      if ( !TextUtils.isEmpty( postponed ) )
      {
         if ( Strings.isQuotified( postponed ) )
         {
            result.append( Strings.unquotify( postponed ) );
         }
         else
         {
            ok = equalsIntParam( postponed );
         }
      }
      else
      {
         ok = false;
      }
      
      return ok;
   }
   
   
   
   @Override
   public boolean evalIsShared( boolean isShared )
   {
      ensureOperatorAndResetState();
      
      result.append( "(" )
            .append( RtmTaskSeriesTable.TABLE_NAME )
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
      ensureOperatorAndResetState();
      
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
         result.append( " " ).append( DEFAULT_OPERATOR ).append( " " );
         lexedOperator = true;
      }
   }
   
   
   
   private final void ensureOperatorAndResetState()
   {
      ensureOperator();
      lexedOperator = false;
   }
   
   
   
   private void likeStringParam( String param )
   {
      result.append( " LIKE '" )
            .append( Strings.unquotify( param ) )
            .append( "'" );
   }
   
   
   
   private void containsStringParam( String param )
   {
      result.append( " LIKE '%" )
            .append( Strings.unquotify( param ) )
            .append( "%'" );
   }
   
   
   
   private boolean equalsIntParam( String param )
   {
      try
      {
         final int val = Integer.parseInt( Strings.unquotify( param ) );
         result.append( "=" ).append( val );
      }
      catch ( NumberFormatException e )
      {
         return false;
      }
      
      return true;
   }
   
   
   
   private boolean equalsTimeParam( String column, String param )
   {
      
      final MolokoCalendar cal;
      try
      {
         cal = dateTimeParsing.parseDateTimeSpec( Strings.unquotify( param ) );
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
      final String unquotParam = Strings.unquotify( param );
      
      MolokoCalendar cal;
      try
      {
         cal = dateTimeParsing.parseDateTimeSpec( unquotParam );
      }
      // If simple time parsing failed, try parse date within
      catch ( GrammarException e )
      {
         try
         {
            cal = dateTimeParsing.parseDateWithin( unquotParam, before ).endEpoch;
         }
         catch ( GrammarException e1 )
         {
            return false;
         }
      }
      
      result.append( column );
      
      // Check if we have 'NEVER'
      if ( !cal.hasDate() )
      {
         result.append( " IS NOT NULL" );
      }
      else
      {
         result.append( ( before ) ? " < " : " > " )
               .append( cal.getTimeInMillis() );
      }
      
      return true;
   }
   
   
   
   private boolean inTimeParamRange( String column, String param, boolean past )
   {
      try
      {
         final ParseDateWithinReturn range = dateTimeParsing.parseDateWithin( Strings.unquotify( param ),
                                                                              past );
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
