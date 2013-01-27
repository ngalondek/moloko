/* 
 *	Copyright (c) 2012 Ronny Röhricht
 *
 *	This file is part of MolokoTest.
 *
 *	MolokoTest is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	MolokoTest is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with MolokoTest.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.test.util.parsing;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.mdt.rtm.data.RtmTask;
import com.mdt.rtm.data.RtmTask.Priority;

import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.moloko.util.MolokoCalendar;
import dev.drsoran.moloko.util.parsing.RtmSmartFilterParsing;
import dev.drsoran.moloko.util.parsing.RtmSmartFilterParsing.RtmSmartFilterReturn;
import dev.drsoran.moloko.util.parsing.RtmSmartFilterToken;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.provider.Rtm.Locations;
import dev.drsoran.provider.Rtm.Notes;
import dev.drsoran.provider.Rtm.Tags;
import dev.drsoran.provider.Rtm.Tasks;


public class RtmSmartFilerParsingTest extends MolokoTestCase
{
   private final DateFormat dateFormat = new SimpleDateFormat( "MM/dd/yyyy" );
   
   private final DateFormat timeFormat = new SimpleDateFormat( "hh:mma" );
   
   private final DateFormat dateTimeFormat = new SimpleDateFormat( "MM/dd/yyyy, hh:mma" );
   
   
   
   @Test
   public void op_name()
   {
      evaluateFilter( RtmSmartFilterLexer.OP_NAME_LIT + "Test",
                      query( Tasks.TASKSERIES_NAME + " like '%Test%'" ),
                      new RtmSmartFilterToken( RtmSmartFilterLexer.OP_NAME,
                                               "Test",
                                               false ) );
      evaluateFilter( RtmSmartFilterLexer.OP_NAME_LIT + "\"Test Name\"",
                      query( Tasks.TASKSERIES_NAME + " like '%Test Name%'" ),
                      new RtmSmartFilterToken( RtmSmartFilterLexer.OP_NAME,
                                               "Test Name",
                                               false ) );
      evaluateFilter( RtmSmartFilterLexer.OP_NAME_LIT, null );
   }
   
   
   
   @Test
   public void op_list()
   {
      evaluateFilter( RtmSmartFilterLexer.OP_LIST_LIT + "TestList",
                      query( Lists.LIST_NAME + " like 'TestList'" ),
                      new RtmSmartFilterToken( RtmSmartFilterLexer.OP_LIST,
                                               "TestList",
                                               false ) );
      evaluateFilter( RtmSmartFilterLexer.OP_LIST_LIT + "\"Test List\"",
                      query( Lists.LIST_NAME + " like 'Test List'" ),
                      new RtmSmartFilterToken( RtmSmartFilterLexer.OP_LIST,
                                               "Test List",
                                               false ) );
      evaluateFilter( RtmSmartFilterLexer.OP_LIST_LIT, null );
   }
   
   
   
   @Test
   public void op_priority()
   {
      evaluateFilter( RtmSmartFilterLexer.OP_PRIORITY_LIT
                         + RtmTask.convertPriority( Priority.None ),
                      query( Tasks.PRIORITY + " like 'n'" ),
                      new RtmSmartFilterToken( RtmSmartFilterLexer.OP_PRIORITY,
                                               "n",
                                               false ) );
      evaluateFilter( RtmSmartFilterLexer.OP_PRIORITY_LIT
                         + RtmTask.convertPriority( Priority.High ),
                      query( Tasks.PRIORITY + " like '1'" ),
                      new RtmSmartFilterToken( RtmSmartFilterLexer.OP_PRIORITY,
                                               "1",
                                               false ) );
      evaluateFilter( RtmSmartFilterLexer.OP_PRIORITY_LIT
                         + RtmTask.convertPriority( Priority.Medium ),
                      query( Tasks.PRIORITY + " like '2'" ),
                      new RtmSmartFilterToken( RtmSmartFilterLexer.OP_PRIORITY,
                                               "2",
                                               false ) );
      evaluateFilter( RtmSmartFilterLexer.OP_PRIORITY_LIT
                         + RtmTask.convertPriority( Priority.Low ),
                      query( Tasks.PRIORITY + " like '3'" ),
                      new RtmSmartFilterToken( RtmSmartFilterLexer.OP_PRIORITY,
                                               "3",
                                               false ) );
      evaluateFilter( RtmSmartFilterLexer.OP_PRIORITY_LIT + "Invalid", null );
      evaluateFilter( RtmSmartFilterLexer.OP_PRIORITY_LIT, null );
   }
   
   
   
   @Test
   public void op_status()
   {
      evaluateFilter( RtmSmartFilterLexer.OP_STATUS_LIT
                         + RtmSmartFilterLexer.COMPLETED_LIT,
                      query( Tasks.COMPLETED_DATE + " IS NOT NULL", true ),
                      new RtmSmartFilterToken( RtmSmartFilterLexer.OP_STATUS,
                                               RtmSmartFilterLexer.COMPLETED_LIT,
                                               false ) );
      evaluateFilter( RtmSmartFilterLexer.OP_STATUS_LIT
                         + RtmSmartFilterLexer.INCOMPLETE_LIT,
                      query( Tasks.COMPLETED_DATE + " IS NULL" ),
                      new RtmSmartFilterToken( RtmSmartFilterLexer.OP_STATUS,
                                               RtmSmartFilterLexer.INCOMPLETE_LIT,
                                               false ) );
      evaluateFilter( RtmSmartFilterLexer.OP_STATUS_LIT + "Invalid", null );
      evaluateFilter( RtmSmartFilterLexer.OP_STATUS_LIT, null );
   }
   
   
   
   @Test
   public void op_tag()
   {
      final String testTag = "TestTag";
      final String queryString = new StringBuilder().append( "(" )
                                                    .append( Tasks.TAGS )
                                                    // Exact match if only 1 tag
                                                    .append( " = '" )
                                                    .append( testTag )
                                                    .append( "' OR " )
                                                    // match for the case tag, (prefix)
                                                    .append( Tasks.TAGS )
                                                    .append( " like '" )
                                                    .append( testTag )
                                                    .append( Tags.TAGS_SEPARATOR )
                                                    .append( "%' OR " )
                                                    // match for the case ,tag, (infix)
                                                    .append( Tasks.TAGS )
                                                    .append( " like '%" )
                                                    .append( Tags.TAGS_SEPARATOR )
                                                    .append( testTag )
                                                    .append( Tags.TAGS_SEPARATOR )
                                                    .append( "%' OR " )
                                                    // match for the case ,tag (suffix)
                                                    .append( Tasks.TAGS )
                                                    .append( " like '%" )
                                                    .append( Tags.TAGS_SEPARATOR )
                                                    .append( testTag )
                                                    .append( "')" )
                                                    .toString();
      
      evaluateFilter( RtmSmartFilterLexer.OP_TAG_LIT + testTag,
                      query( queryString ),
                      new RtmSmartFilterToken( RtmSmartFilterLexer.OP_TAG,
                                               testTag,
                                               false ) );
      evaluateFilter( RtmSmartFilterLexer.OP_TAG_LIT, null );
   }
   
   
   
   @Test
   public void op_tagcontains()
   {
      evaluateFilter( RtmSmartFilterLexer.OP_TAG_CONTAINS_LIT + "TestTag",
                      query( Tasks.TAGS + " like '%TestTag%'" ),
                      new RtmSmartFilterToken( RtmSmartFilterLexer.OP_TAG_CONTAINS,
                                               "TestTag",
                                               false ) );
      evaluateFilter( RtmSmartFilterLexer.OP_TAG_CONTAINS_LIT + "\"Test Tag\"",
                      query( Tasks.TAGS + " like '%Test Tag%'" ),
                      new RtmSmartFilterToken( RtmSmartFilterLexer.OP_TAG_CONTAINS,
                                               "Test Tag",
                                               false ) );
      evaluateFilter( RtmSmartFilterLexer.OP_TAG_CONTAINS_LIT, null );
   }
   
   
   
   @Test
   public void op_istagged()
   {
      evaluateFilter( RtmSmartFilterLexer.OP_IS_TAGGED_LIT
                         + RtmSmartFilterLexer.TRUE_LIT,
                      query( Tasks.TAGS + " IS NOT NULL" ),
                      new RtmSmartFilterToken( RtmSmartFilterLexer.OP_IS_TAGGED,
                                               RtmSmartFilterLexer.TRUE_LIT,
                                               false ) );
      evaluateFilter( RtmSmartFilterLexer.OP_IS_TAGGED_LIT
                         + RtmSmartFilterLexer.FALSE_LIT,
                      query( Tasks.TAGS + " IS NULL" ),
                      new RtmSmartFilterToken( RtmSmartFilterLexer.OP_IS_TAGGED,
                                               RtmSmartFilterLexer.FALSE_LIT,
                                               false ) );
      evaluateFilter( RtmSmartFilterLexer.OP_IS_TAGGED_LIT + "Invalid", null );
      evaluateFilter( RtmSmartFilterLexer.OP_IS_TAGGED_LIT, null );
   }
   
   
   
   @Test
   public void op_location()
   {
      evaluateFilter( RtmSmartFilterLexer.OP_LOCATION_LIT + "TestLocation",
                      query( Tasks.LOCATION_NAME + " like 'TestLocation'" ),
                      new RtmSmartFilterToken( RtmSmartFilterLexer.OP_LOCATION,
                                               "TestLocation",
                                               false ) );
      evaluateFilter( RtmSmartFilterLexer.OP_LOCATION_LIT + "\"Test Location\"",
                      query( Tasks.LOCATION_NAME + " like 'Test Location'" ),
                      new RtmSmartFilterToken( RtmSmartFilterLexer.OP_LOCATION,
                                               "Test Location",
                                               false ) );
      evaluateFilter( RtmSmartFilterLexer.OP_LOCATION_LIT, null );
   }
   
   
   
   @Test
   public void op_islocated()
   {
      final String queryString = new StringBuilder().append( "(" )
                                                    .append( Tasks.LOCATION_ID )
                                                    // Handle the case that shared tasks have a location
                                                    // ID but not from our DB.
                                                    .append( " IS NOT NULL AND " )
                                                    .append( Tasks.LOCATION_ID )
                                                    .append( " IN ( SELECT " )
                                                    .append( Locations._ID )
                                                    .append( " FROM " )
                                                    .append( Locations.PATH )
                                                    .append( " ))" )
                                                    .toString();
      
      evaluateFilter( RtmSmartFilterLexer.OP_IS_LOCATED_LIT
                         + RtmSmartFilterLexer.TRUE_LIT,
                      query( queryString ),
                      new RtmSmartFilterToken( RtmSmartFilterLexer.OP_ISLOCATED,
                                               RtmSmartFilterLexer.TRUE_LIT,
                                               false ) );
      evaluateFilter( RtmSmartFilterLexer.OP_IS_LOCATED_LIT
                         + RtmSmartFilterLexer.FALSE_LIT,
                      query( Tasks.LOCATION_ID + " IS NULL" ),
                      new RtmSmartFilterToken( RtmSmartFilterLexer.OP_ISLOCATED,
                                               RtmSmartFilterLexer.FALSE_LIT,
                                               false ) );
      evaluateFilter( RtmSmartFilterLexer.OP_IS_LOCATED_LIT + "Invalid", null );
      evaluateFilter( RtmSmartFilterLexer.OP_IS_LOCATED_LIT, null );
   }
   
   
   
   @Test
   public void op_isrepeating()
   {
      evaluateFilter( RtmSmartFilterLexer.OP_IS_REPEATING_LIT
                         + RtmSmartFilterLexer.TRUE_LIT,
                      query( Tasks.RECURRENCE + " IS NOT NULL" ),
                      new RtmSmartFilterToken( RtmSmartFilterLexer.OP_IS_REPEATING,
                                               RtmSmartFilterLexer.TRUE_LIT,
                                               false ) );
      evaluateFilter( RtmSmartFilterLexer.OP_IS_REPEATING_LIT
                         + RtmSmartFilterLexer.FALSE_LIT,
                      query( Tasks.RECURRENCE + " IS NULL" ),
                      new RtmSmartFilterToken( RtmSmartFilterLexer.OP_IS_REPEATING,
                                               RtmSmartFilterLexer.FALSE_LIT,
                                               false ) );
      evaluateFilter( RtmSmartFilterLexer.OP_IS_REPEATING_LIT + "Invalid", null );
      evaluateFilter( RtmSmartFilterLexer.OP_IS_REPEATING_LIT, null );
   }
   
   
   
   @Test
   public void op_notecontains()
   {
      final String noteText = "NoteText";
      final String queryString = new StringBuilder().append( "( (SELECT " )
                                                    .append( Notes.TASKSERIES_ID )
                                                    .append( " FROM " )
                                                    .append( Notes.PATH )
                                                    .append( " WHERE " )
                                                    .append( Notes.TASKSERIES_ID )
                                                    .append( " = subQuery." )
                                                    .append( Tasks.TASKSERIES_ID )
                                                    .append( " AND " )
                                                    .append( Notes.NOTE_TITLE )
                                                    .append( " like '%" )
                                                    .append( noteText )
                                                    .append( "%' OR " )
                                                    .append( Notes.NOTE_TEXT )
                                                    .append( " like '%" )
                                                    .append( noteText )
                                                    .append( "%'))" )
                                                    .toString();
      
      evaluateFilter( RtmSmartFilterLexer.OP_NOTE_CONTAINS_LIT + noteText,
                      query( queryString ),
                      new RtmSmartFilterToken( RtmSmartFilterLexer.OP_NOTE_CONTAINS,
                                               noteText,
                                               false ) );
      evaluateFilter( RtmSmartFilterLexer.OP_NOTE_CONTAINS_LIT, null );
   }
   
   
   
   @Test
   public void op_hasnotes()
   {
      evaluateFilter( RtmSmartFilterLexer.OP_HAS_NOTES_LIT
                         + RtmSmartFilterLexer.TRUE_LIT,
                      query( Tasks.NOTE_IDS + " NOT NULL" ),
                      new RtmSmartFilterToken( RtmSmartFilterLexer.OP_HAS_NOTES,
                                               RtmSmartFilterLexer.TRUE_LIT,
                                               false ) );
      evaluateFilter( RtmSmartFilterLexer.OP_HAS_NOTES_LIT
                         + RtmSmartFilterLexer.FALSE_LIT,
                      query( Tasks.NOTE_IDS + " IS NULL" ),
                      new RtmSmartFilterToken( RtmSmartFilterLexer.OP_HAS_NOTES,
                                               RtmSmartFilterLexer.FALSE_LIT,
                                               false ) );
      evaluateFilter( RtmSmartFilterLexer.OP_HAS_NOTES_LIT + "Invalid", null );
      evaluateFilter( RtmSmartFilterLexer.OP_HAS_NOTES_LIT, null );
   }
   
   
   
   @Test
   public void op_due() throws ParseException
   {
      testTimeOperator( RtmSmartFilterLexer.OP_DUE_LIT,
                        RtmSmartFilterLexer.OP_DUE,
                        Tasks.DUE_DATE );
   }
   
   
   
   @Test
   public void op_dueafter() throws ParseException
   {
      testTimeOperatorAfter( RtmSmartFilterLexer.OP_DUE_AFTER_LIT,
                             RtmSmartFilterLexer.OP_DUE_AFTER,
                             Tasks.DUE_DATE );
   }
   
   
   
   @Test
   public void op_duebefore() throws ParseException
   {
      testTimeOperatorBefore( RtmSmartFilterLexer.OP_DUE_BEFORE_LIT,
                              RtmSmartFilterLexer.OP_DUE_BEFORE,
                              Tasks.DUE_DATE );
   }
   
   
   
   @Test
   public void op_duewithin() throws ParseException
   {
      final MolokoCalendar calStart = MolokoCalendar.getInstance();
      calStart.setHasTime( false );
      
      final MolokoCalendar calEnd = MolokoCalendar.getInstance();
      calEnd.setHasTime( false );
      calEnd.add( Calendar.DAY_OF_YEAR, 2 );
      
      evaluateFilter( RtmSmartFilterLexer.OP_DUE_WITHIN_LIT + "\"2 days\"",
                      query( inTimeParamRangeQuery( Tasks.DUE_DATE,
                                                    calStart.getTime(),
                                                    calEnd.getTime() ),
                             false ),
                      new RtmSmartFilterToken( RtmSmartFilterLexer.OP_DUE_WITHIN,
                                               "2 days",
                                               false ) );
      
      evaluateFilter( RtmSmartFilterLexer.OP_DUE_WITHIN_LIT + "\"never\"", null );
      evaluateFilter( RtmSmartFilterLexer.OP_DUE_WITHIN_LIT + "\"Invalid\"",
                      null );
      evaluateFilter( RtmSmartFilterLexer.OP_DUE_WITHIN_LIT, null );
   }
   
   
   
   @Test
   public void op_completed() throws ParseException
   {
      testTimeOperator( RtmSmartFilterLexer.OP_COMPLETED_LIT,
                        RtmSmartFilterLexer.OP_COMPLETED,
                        Tasks.COMPLETED_DATE,
                        true );
   }
   
   
   
   @Test
   public void op_completedafter() throws ParseException
   {
      testTimeOperatorAfter( RtmSmartFilterLexer.OP_COMPLETED_AFTER_LIT,
                             RtmSmartFilterLexer.OP_COMPLETED_AFTER,
                             Tasks.COMPLETED_DATE,
                             true );
   }
   
   
   
   @Test
   public void op_completedbefore() throws ParseException
   {
      testTimeOperatorBefore( RtmSmartFilterLexer.OP_COMPLETED_BEFORE_LIT,
                              RtmSmartFilterLexer.OP_COMPLETED_BEFORE,
                              Tasks.COMPLETED_DATE,
                              true );
   }
   
   
   
   @Test
   public void op_completedwithin() throws ParseException
   {
      final MolokoCalendar calStart = MolokoCalendar.getInstance();
      calStart.setHasTime( false );
      
      final MolokoCalendar calEnd = MolokoCalendar.getInstance();
      calEnd.setHasTime( false );
      calEnd.add( Calendar.DAY_OF_YEAR, -2 );
      
      evaluateFilter( RtmSmartFilterLexer.OP_COMPLETED_WITHIN_LIT
                         + "\"2 days\"",
                      query( inTimeParamRangeQuery( Tasks.COMPLETED_DATE,
                                                    calEnd.getTime(),
                                                    calStart.getTime() ),
                             true ),
                      new RtmSmartFilterToken( RtmSmartFilterLexer.OP_COMPLETED_WITHIN,
                                               "2 days",
                                               false ) );
      
      evaluateFilter( RtmSmartFilterLexer.OP_COMPLETED_WITHIN_LIT + "\"never\"",
                      null );
      evaluateFilter( RtmSmartFilterLexer.OP_COMPLETED_WITHIN_LIT
         + "\"Invalid\"", null );
      evaluateFilter( RtmSmartFilterLexer.OP_COMPLETED_WITHIN_LIT, null );
   }
   
   
   
   @Test
   public void op_added() throws ParseException
   {
      testTimeOperator( RtmSmartFilterLexer.OP_ADDED_LIT,
                        RtmSmartFilterLexer.OP_ADDED,
                        Tasks.ADDED_DATE );
   }
   
   
   
   @Test
   public void op_addeddafter() throws ParseException
   {
      testTimeOperatorAfter( RtmSmartFilterLexer.OP_ADDED_AFTER_LIT,
                             RtmSmartFilterLexer.OP_ADDED_AFTER,
                             Tasks.ADDED_DATE );
   }
   
   
   
   @Test
   public void op_addedbefore() throws ParseException
   {
      testTimeOperatorBefore( RtmSmartFilterLexer.OP_ADDED_BEFORE_LIT,
                              RtmSmartFilterLexer.OP_ADDED_BEFORE,
                              Tasks.ADDED_DATE );
   }
   
   
   
   @Test
   public void op_addedwithin() throws ParseException
   {
      final MolokoCalendar calStart = MolokoCalendar.getInstance();
      calStart.setHasTime( false );
      
      final MolokoCalendar calEnd = MolokoCalendar.getInstance();
      calEnd.add( Calendar.DAY_OF_YEAR, -2 );
      calEnd.setHasTime( false );
      
      evaluateFilter( RtmSmartFilterLexer.OP_ADDED_WITHIN_LIT + "\"2 days\"",
                      query( inTimeParamRangeQuery( Tasks.ADDED_DATE,
                                                    calEnd.getTime(),
                                                    calStart.getTime() ),
                             false ),
                      new RtmSmartFilterToken( RtmSmartFilterLexer.OP_ADDED_WITHIN,
                                               "2 days",
                                               false ) );
      
      evaluateFilter( RtmSmartFilterLexer.OP_ADDED_WITHIN_LIT + "\"never\"",
                      null );
      evaluateFilter( RtmSmartFilterLexer.OP_ADDED_WITHIN_LIT + "\"Invalid\"",
                      null );
      evaluateFilter( RtmSmartFilterLexer.OP_ADDED_WITHIN_LIT, null );
   }
   
   
   
   private RtmSmartFilterReturn query( String queryString )
   {
      return query( queryString, false );
   }
   
   
   
   private RtmSmartFilterReturn query( String queryString,
                                       boolean hasCompletedOp )
   {
      return new RtmSmartFilterReturn( "( " + queryString + " )",
                                       hasCompletedOp );
   }
   
   
   
   private RtmSmartFilterReturn and( RtmSmartFilterReturn lhs,
                                     RtmSmartFilterReturn rhs )
   {
      return new RtmSmartFilterReturn( "( " + lhs.queryString + " AND "
         + rhs.queryString + " )", lhs.hasCompletedOperator
         | rhs.hasCompletedOperator );
   }
   
   
   
   private RtmSmartFilterReturn or( RtmSmartFilterReturn lhs,
                                    RtmSmartFilterReturn rhs )
   {
      return new RtmSmartFilterReturn( "( " + lhs.queryString + " OR "
         + rhs.queryString + " )", lhs.hasCompletedOperator
         | rhs.hasCompletedOperator );
   }
   
   
   
   private RtmSmartFilterReturn not( RtmSmartFilterReturn query )
   {
      return new RtmSmartFilterReturn( "NOT " + query.queryString,
                                       query.hasCompletedOperator );
   }
   
   
   
   private void evaluateFilter( String filterString,
                                RtmSmartFilterReturn expectedResult,
                                RtmSmartFilterToken... expectedTokens )
   {
      final ArrayList< RtmSmartFilterToken > lexedTokens = new ArrayList< RtmSmartFilterToken >();
      final RtmSmartFilterReturn result = RtmSmartFilterParsing.evaluateRtmSmartFilter( filterString,
                                                                                        lexedTokens );
      
      if ( expectedResult == null )
      {
         Assert.assertNull( "Expected the smart filter parsing to return null",
                            result );
      }
      else
      {
         Assert.assertEquals( "Wrong smart filter parsing result",
                              expectedResult,
                              result );
      }
      
      if ( expectedTokens.length > 0 )
      {
         Assert.assertEquals( "Lexed token count is wrong",
                              expectedTokens.length,
                              lexedTokens.size() );
         
         for ( int i = 0; i < expectedTokens.length; i++ )
         {
            final RtmSmartFilterToken expectedToken = expectedTokens[ i ];
            final RtmSmartFilterToken lexedToken = lexedTokens.get( i );
            
            Assert.assertEquals( "Lexed token is wrong",
                                 expectedToken,
                                 lexedToken );
         }
      }
   }
   
   
   
   private String equalsNeverTimeParamQuery( String column )
   {
      return "(" + column + " IS NULL)";
   }
   
   
   
   private String differsNeverTimeParamQuery( String column )
   {
      return column + " IS NOT NULL";
   }
   
   
   
   private String equalsTimeOnlyQuery( String column, Date expectedTime )
   {
      return "(" + column + " == " + expectedTime.getTime() + ")";
   }
   
   
   
   private String equalsDateQuery( String column, Date expectedDate )
   {
      final Calendar cal = Calendar.getInstance();
      cal.setTime( expectedDate );
      cal.add( Calendar.DAY_OF_YEAR, 1 );
      
      return new StringBuilder( "(" ).append( column )
                                     .append( " >= " )
                                     .append( expectedDate.getTime() )
                                     .append( " AND " )
                                     .append( column )
                                     .append( " < " )
                                     .append( cal.getTimeInMillis() )
                                     .append( ")" )
                                     .toString();
   }
   
   
   
   private String differsBeforeTimeParamQuery( String column, Date expectedDate )
   {
      return column + " < " + expectedDate.getTime();
   }
   
   
   
   private String differsAfterTimeParamQuery( String column, Date expectedDate )
   {
      return column + " > " + expectedDate.getTime();
   }
   
   
   
   private String inTimeParamRangeQuery( String column,
                                         Date expectedStartDate,
                                         Date expectedEndDate )
   {
      return new StringBuilder( "(" ).append( column )
                                     .append( " >= " )
                                     .append( expectedStartDate.getTime() )
                                     .append( " AND " )
                                     .append( column )
                                     .append( " < " )
                                     .append( expectedEndDate.getTime() )
                                     .append( ")" )
                                     .toString();
   }
   
   
   
   private void testTimeOperator( String operatorLit,
                                  int operatorToken,
                                  String column ) throws ParseException
   {
      testTimeOperator( operatorLit, operatorToken, column, false );
   }
   
   
   
   private void testTimeOperator( String operatorLit,
                                  int operatorToken,
                                  String column,
                                  boolean completedOperator ) throws ParseException
   {
      evaluateFilter( operatorLit + "1/10/2012",
                      query( equalsDateQuery( column,
                                              dateFormat.parse( "1/10/2012" ) ),
                             completedOperator ),
                      new RtmSmartFilterToken( operatorToken,
                                               "1/10/2012",
                                               false ) );
      
      evaluateFilter( operatorLit + "1/10/2012@10am",
                      query( equalsTimeOnlyQuery( column,
                                                  dateTimeFormat.parse( "1/10/2012, 10:00am" ) ),
                             completedOperator ),
                      new RtmSmartFilterToken( operatorToken,
                                               "1/10/2012@10am",
                                               false ) );
      
      evaluateFilter( operatorLit + "never",
                      query( equalsNeverTimeParamQuery( column ),
                             completedOperator ),
                      new RtmSmartFilterToken( operatorToken, "never", false ) );
      
      evaluateFilter( operatorLit + "Invalid", null );
      evaluateFilter( operatorLit, null );
   }
   
   
   
   private void testTimeOperatorAfter( String operatorLit,
                                       int operatorToken,
                                       String column ) throws ParseException
   {
      testTimeOperatorAfter( operatorLit, operatorToken, column, false );
   }
   
   
   
   private void testTimeOperatorAfter( String operatorLit,
                                       int operatorToken,
                                       String column,
                                       boolean completedOperator ) throws ParseException
   {
      evaluateFilter( operatorLit + "1/10/2012",
                      query( differsAfterTimeParamQuery( column,
                                                         dateFormat.parse( "1/10/2012" ) ),
                             completedOperator ),
                      new RtmSmartFilterToken( operatorToken,
                                               "1/10/2012",
                                               false ) );
      
      evaluateFilter( operatorLit + "1/10/2012@10am",
                      query( differsAfterTimeParamQuery( column,
                                                         dateTimeFormat.parse( "1/10/2012, 10:00am" ) ),
                             completedOperator ),
                      new RtmSmartFilterToken( operatorToken,
                                               "1/10/2012@10am",
                                               false ) );
      
      evaluateFilter( operatorLit + "never",
                      query( differsNeverTimeParamQuery( column ),
                             completedOperator ),
                      new RtmSmartFilterToken( operatorToken, "never", false ) );
      
      evaluateFilter( operatorLit + "Invalid", null );
      evaluateFilter( operatorLit, null );
   }
   
   
   
   private void testTimeOperatorBefore( String operatorLit,
                                        int operatorToken,
                                        String column ) throws ParseException
   {
      testTimeOperatorBefore( operatorLit, operatorToken, column, false );
   }
   
   
   
   private void testTimeOperatorBefore( String operatorLit,
                                        int operatorToken,
                                        String column,
                                        boolean completedOperator ) throws ParseException
   {
      evaluateFilter( operatorLit + "1/10/2012",
                      query( differsBeforeTimeParamQuery( column,
                                                          dateFormat.parse( "1/10/2012" ) ),
                             completedOperator ),
                      new RtmSmartFilterToken( operatorToken,
                                               "1/10/2012",
                                               false ) );
      
      evaluateFilter( operatorLit + "1/10/2012@10am",
                      query( differsBeforeTimeParamQuery( column,
                                                          dateTimeFormat.parse( "1/10/2012, 10:00am" ) ),
                             completedOperator ),
                      new RtmSmartFilterToken( operatorToken,
                                               "1/10/2012@10am",
                                               false ) );
      
      evaluateFilter( operatorLit + "never",
                      query( differsNeverTimeParamQuery( column ),
                             completedOperator ),
                      new RtmSmartFilterToken( operatorToken, "never", false ) );
      
      evaluateFilter( operatorLit + "Invalid", null );
      evaluateFilter( operatorLit, null );
   }
}
