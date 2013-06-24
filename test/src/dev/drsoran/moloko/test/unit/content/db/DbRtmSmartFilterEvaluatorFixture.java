/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.moloko.test.unit.content.db;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Calendar;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.content.db.DbRtmSmartFilterEvaluator;
import dev.drsoran.moloko.content.db.TableColumns.RtmLocationColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmRawTaskColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTaskSeriesColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTasksListColumns;
import dev.drsoran.moloko.grammar.GrammarException;
import dev.drsoran.moloko.grammar.IDateTimeParsing;
import dev.drsoran.moloko.grammar.datetime.ParseDateWithinReturn;
import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.moloko.test.TestConstants;


public class DbRtmSmartFilterEvaluatorFixture extends MolokoTestCase
{
   private IDateTimeParsing dateTimeParsing;
   
   private DbRtmSmartFilterEvaluator evaluator;
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      
      dateTimeParsing = EasyMock.createNiceMock( IDateTimeParsing.class );
      evaluator = new DbRtmSmartFilterEvaluator( dateTimeParsing );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testDbRtmSmartFilterEvaluatorNullDateTimmeParsing()
   {
      new DbRtmSmartFilterEvaluator( null );
   }
   
   
   
   @Test
   public void testGetResult()
   {
      assertThat( evaluator.getResult(), is( (String) null ) );
   }
   
   
   
   @Test
   public void testReset()
   {
      evaluator.reset();
      assertThat( evaluator.getResult(), is( (String) null ) );
      
      assertTrue( evaluator.evalAnd() );
      assertThat( evaluator.getResult(), not( is( (String) null ) ) );
      
      evaluator.reset();
      assertThat( evaluator.getResult(), is( (String) null ) );
   }
   
   
   
   @Test
   public void testMultipleWithReset()
   {
      assertTrue( evaluator.evalAnd() );
      assertQuery( "AND" );
      
      evaluator.reset();
      
      assertTrue( evaluator.evalList( "listName" ) );
      assertQuery( RtmTasksListColumns.LIST_NAME + " LIKE 'listName'" );
   }
   
   
   
   @Test
   public void testEvalList() throws Exception
   {
      assertTrue( evaluator.evalList( "listName" ) );
      assertQueryAndReset( RtmTasksListColumns.LIST_NAME + " LIKE 'listName'" );
      
      assertTrue( evaluator.evalList( "List with Space" ) );
      assertQueryAndReset( RtmTasksListColumns.LIST_NAME
         + " LIKE 'List with Space'" );
      
      assertTrue( evaluator.evalList( "\"List with Space\"" ) );
      assertQueryAndReset( RtmTasksListColumns.LIST_NAME
         + " LIKE 'List with Space'" );
      
      testEnsureOperator( "evalList",
                          "listName",
                          RtmTasksListColumns.LIST_NAME + " LIKE 'listName'",
                          "\"List with Space\"",
                          RtmTasksListColumns.LIST_NAME
                             + " LIKE 'List with Space'",
                          String.class );
   }
   
   
   
   @Test
   public void testEvalPriority() throws Exception
   {
      assertTrue( evaluator.evalPriority( "n" ) );
      assertQueryAndReset( RtmRawTaskColumns.PRIORITY + " LIKE 'n'" );
      
      testEnsureOperator( "evalPriority",
                          "1",
                          RtmRawTaskColumns.PRIORITY + " LIKE '1'",
                          "2",
                          RtmRawTaskColumns.PRIORITY + " LIKE '2'",
                          String.class );
   }
   
   
   
   @Test
   public void testEvalStatus() throws Exception
   {
      assertTrue( evaluator.evalStatus( true ) );
      assertQueryAndReset( RtmRawTaskColumns.COMPLETED_DATE + " IS NOT NULL" );
      
      assertTrue( evaluator.evalStatus( false ) );
      assertQueryAndReset( RtmRawTaskColumns.COMPLETED_DATE + " IS NULL" );
      
      testEnsureOperator( "evalStatus",
                          true,
                          RtmRawTaskColumns.COMPLETED_DATE + " IS NOT NULL",
                          false,
                          RtmRawTaskColumns.COMPLETED_DATE + " IS NULL",
                          boolean.class );
   }
   
   
   
   @Test
   public void testEvalTag() throws Exception
   {
      final String tagsQueryFmt = "(tags = ''{0}'' OR tags LIKE ''{0},%'' OR tags LIKE ''%,{0},%'' OR tags LIKE ''%,{0}'')";
      
      assertTrue( evaluator.evalTag( "tag" ) );
      assertQueryAndReset( MessageFormat.format( tagsQueryFmt, "tag" ) );
      
      testEnsureOperator( "evalTag",
                          "tag",
                          MessageFormat.format( tagsQueryFmt, "tag" ),
                          "tag1",
                          MessageFormat.format( tagsQueryFmt, "tag1" ),
                          String.class );
   }
   
   
   
   @Test
   public void testEvalTagContains() throws Exception
   {
      assertTrue( evaluator.evalTagContains( "tag" ) );
      assertQueryAndReset( RtmTaskSeriesColumns.TAGS + " LIKE '%tag%'" );
      
      testEnsureOperator( "evalTagContains", "tag", RtmTaskSeriesColumns.TAGS
         + " LIKE '%tag%'", "\"tag1\"", RtmTaskSeriesColumns.TAGS
         + " LIKE '%tag1%'", String.class );
   }
   
   
   
   @Test
   public void testEvalIsTagged() throws Exception
   {
      assertTrue( evaluator.evalIsTagged( true ) );
      assertQueryAndReset( RtmTaskSeriesColumns.TAGS + " IS NOT NULL" );
      
      assertTrue( evaluator.evalIsTagged( false ) );
      assertQueryAndReset( RtmTaskSeriesColumns.TAGS + " IS NULL" );
      
      testEnsureOperator( "evalIsTagged",
                          true,
                          RtmTaskSeriesColumns.TAGS + " IS NOT NULL",
                          false,
                          RtmTaskSeriesColumns.TAGS + " IS NULL",
                          boolean.class );
   }
   
   
   
   @Test
   public void testEvalLocation() throws Exception
   {
      assertTrue( evaluator.evalLocation( "loc1" ) );
      assertQueryAndReset( RtmLocationColumns.LOCATION_NAME + " LIKE 'loc1'" );
      
      assertTrue( evaluator.evalLocation( "loc with space" ) );
      assertQueryAndReset( RtmLocationColumns.LOCATION_NAME
         + " LIKE 'loc with space'" );
      
      testEnsureOperator( "evalLocation",
                          "loc1",
                          RtmLocationColumns.LOCATION_NAME + " LIKE 'loc1'",
                          "loc with space",
                          RtmLocationColumns.LOCATION_NAME
                             + " LIKE 'loc with space'",
                          String.class );
   }
   
   
   
   @Test
   public void testEvalIsLocated() throws Exception
   {
      final String isLocatedQuery = "(location_id IS NOT NULL AND "
         + "location_id IN (SELECT _id FROM locations))";
      
      assertTrue( evaluator.evalIsLocated( true ) );
      assertQueryAndReset( isLocatedQuery );
      
      assertTrue( evaluator.evalIsLocated( false ) );
      assertQueryAndReset( "location_id IS NULL" );
      
      testEnsureOperator( "evalIsLocated",
                          true,
                          isLocatedQuery,
                          false,
                          "location_id IS NULL",
                          boolean.class );
   }
   
   
   
   @Test
   public void testEvalIsRepeating() throws Exception
   {
      assertTrue( evaluator.evalIsRepeating( true ) );
      assertQueryAndReset( RtmTaskSeriesColumns.RECURRENCE + " IS NOT NULL" );
      
      assertTrue( evaluator.evalIsRepeating( false ) );
      assertQueryAndReset( RtmTaskSeriesColumns.RECURRENCE + " IS NULL" );
      
      testEnsureOperator( "evalIsRepeating",
                          true,
                          RtmTaskSeriesColumns.RECURRENCE + " IS NOT NULL",
                          false,
                          RtmTaskSeriesColumns.RECURRENCE + " IS NULL",
                          boolean.class );
   }
   
   
   
   @Test
   public void testEvalTaskName() throws Exception
   {
      assertTrue( evaluator.evalTaskName( "taskName" ) );
      assertQueryAndReset( RtmTaskSeriesColumns.TASKSERIES_NAME
         + " LIKE '%taskName%'" );
      
      assertTrue( evaluator.evalTaskName( "Task with Space" ) );
      assertQueryAndReset( RtmTaskSeriesColumns.TASKSERIES_NAME
         + " LIKE '%Task with Space%'" );
      
      assertTrue( evaluator.evalTaskName( "\"Task with Space\"" ) );
      assertQueryAndReset( RtmTaskSeriesColumns.TASKSERIES_NAME
         + " LIKE '%Task with Space%'" );
      
      testEnsureOperator( "evalTaskName",
                          "taskName",
                          RtmTaskSeriesColumns.TASKSERIES_NAME
                             + " LIKE '%taskName%'",
                          "Task with Space",
                          RtmTaskSeriesColumns.TASKSERIES_NAME
                             + " LIKE '%Task with Space%'",
                          String.class );
   }
   
   
   
   @Test
   public void testEvalNoteContains() throws Exception
   {
      final String noteQuery = "(SELECT taskseries_id FROM notes WHERE taskseries_id=taskseries._id "
         + "AND note_title LIKE ''%{0}%'' OR note_text LIKE ''%{0}%'')";
      
      assertTrue( evaluator.evalNoteContains( "noteContent" ) );
      assertQueryAndReset( MessageFormat.format( noteQuery, "noteContent" ) );
      
      testEnsureOperator( "evalNoteContains",
                          "noteContent",
                          MessageFormat.format( noteQuery, "noteContent" ),
                          "otherNoteContent",
                          MessageFormat.format( noteQuery, "otherNoteContent" ),
                          String.class );
   }
   
   
   
   @Test
   public void testEvalHasNotes() throws Exception
   {
      final String hasQuery = "(taskseries._id IN (SELECT taskseries_id FROM notes))";
      final String hasNotQuery = "(taskseries._id NOT IN (SELECT taskseries_id FROM notes))";
      
      assertTrue( evaluator.evalHasNotes( true ) );
      assertQueryAndReset( hasQuery );
      
      assertTrue( evaluator.evalHasNotes( false ) );
      assertQueryAndReset( hasNotQuery );
      
      testEnsureOperator( "evalHasNotes",
                          true,
                          hasQuery,
                          false,
                          hasNotQuery,
                          boolean.class );
   }
   
   
   
   @Test
   public void testEvalDue() throws Exception
   {
      testDateAndTime( "evalDue", RtmRawTaskColumns.DUE_DATE );
   }
   
   
   
   @Test
   public void testEvalDueAfter() throws Exception
   {
      testDateAndTimeBeforeAfter( "evalDueAfter",
                                  RtmRawTaskColumns.DUE_DATE,
                                  false );
   }
   
   
   
   @Test
   public void testEvalDueBefore() throws Exception
   {
      testDateAndTimeBeforeAfter( "evalDueBefore",
                                  RtmRawTaskColumns.DUE_DATE,
                                  true );
   }
   
   
   
   @Test
   public void testEvalDueWithIn() throws Exception
   {
      testDateAndTimeInRange( "evalDueWithIn",
                              RtmRawTaskColumns.DUE_DATE,
                              false );
   }
   
   
   
   @Test
   public void testEvalCompleted() throws Exception
   {
      testDateAndTime( "evalCompleted", RtmRawTaskColumns.COMPLETED_DATE );
   }
   
   
   
   @Test
   public void testEvalCompletedAfter() throws Exception
   {
      testDateAndTimeBeforeAfter( "evalCompletedAfter",
                                  RtmRawTaskColumns.COMPLETED_DATE,
                                  false );
   }
   
   
   
   @Test
   public void testEvalCompletedBefore() throws Exception
   {
      testDateAndTimeBeforeAfter( "evalCompletedBefore",
                                  RtmRawTaskColumns.COMPLETED_DATE,
                                  true );
   }
   
   
   
   @Test
   public void testEvalCompletedWithIn() throws Exception
   {
      testDateAndTimeInRange( "evalCompletedWithIn",
                              RtmRawTaskColumns.COMPLETED_DATE,
                              true );
   }
   
   
   
   @Test
   public void testEvalAdded() throws Exception
   {
      testDateAndTime( "evalAdded", RtmRawTaskColumns.ADDED_DATE );
   }
   
   
   
   @Test
   public void testEvalAddedAfter() throws Exception
   {
      testDateAndTimeBeforeAfter( "evalAddedAfter",
                                  RtmRawTaskColumns.ADDED_DATE,
                                  false );
   }
   
   
   
   @Test
   public void testEvalAddedBefore() throws Exception
   {
      testDateAndTimeBeforeAfter( "evalAddedBefore",
                                  RtmRawTaskColumns.ADDED_DATE,
                                  true );
   }
   
   
   
   @Test
   public void testEvalAddedWithIn() throws Exception
   {
      testDateAndTimeInRange( "evalAddedWithIn",
                              RtmRawTaskColumns.ADDED_DATE,
                              true );
   }
   
   
   
   @Test
   public void testEvalTimeEstimate() throws Exception
   {
      final String lessQuery = "(estimateMillis > -1 AND estimateMillis<%d)";
      final String greaterQuery = "(estimateMillis > -1 AND estimateMillis>%d)";
      final String equalQuery = "(estimateMillis=%d)";
      
      EasyMock.expect( dateTimeParsing.parseEstimated( "now" ) )
              .andReturn( TestConstants.NOW )
              .anyTimes();
      EasyMock.expect( dateTimeParsing.parseEstimated( "???" ) )
              .andThrow( new GrammarException() )
              .anyTimes();
      EasyMock.replay( dateTimeParsing );
      
      assertTrue( evaluator.evalTimeEstimate( "<now" ) );
      assertQueryAndReset( String.format( lessQuery, TestConstants.NOW ) );
      
      assertTrue( evaluator.evalTimeEstimate( ">now" ) );
      assertQueryAndReset( String.format( greaterQuery, TestConstants.NOW ) );
      
      assertTrue( evaluator.evalTimeEstimate( "now" ) );
      assertQueryAndReset( String.format( equalQuery, TestConstants.NOW ) );
      
      assertFalse( evaluator.evalTimeEstimate( "???" ) );
      assertQueryAndReset( null );
      
      testEnsureOperator( "evalTimeEstimate",
                          "<now",
                          String.format( lessQuery, TestConstants.NOW ),
                          "now",
                          String.format( equalQuery, TestConstants.NOW ),
                          String.class );
   }
   
   
   
   @Test
   public void testEvalPostponed() throws Exception
   {
      assertThat( evaluator.evalPostponed( "" ), is( false ) );
      assertQueryAndReset( null );
      
      assertThat( evaluator.evalPostponed( null ), is( false ) );
      assertQueryAndReset( null );
      
      assertThat( evaluator.evalPostponed( "NAN" ), is( false ) );
      assertQueryAndReset( null );
      
      assertTrue( evaluator.evalPostponed( "1" ) );
      assertQueryAndReset( RtmRawTaskColumns.POSTPONED + "=1" );
      
      assertTrue( evaluator.evalPostponed( "\">3\"" ) );
      assertQueryAndReset( RtmRawTaskColumns.POSTPONED + ">3" );
      
      testEnsureOperator( "evalPostponed", "1", RtmRawTaskColumns.POSTPONED
         + "=1", "\"<4\"", RtmRawTaskColumns.POSTPONED + "<4", String.class );
   }
   
   
   
   @Test
   public void testEvalIsShared() throws Exception
   {
      final String isSharedQuery = "(taskseries._id IN (SELECT taskseries_id FROM participants))";
      final String notSharedQuery = "(taskseries._id NOT IN (SELECT taskseries_id FROM participants))";
      
      assertTrue( evaluator.evalIsShared( true ) );
      assertQueryAndReset( isSharedQuery );
      
      assertTrue( evaluator.evalIsShared( false ) );
      assertQueryAndReset( notSharedQuery );
      
      testEnsureOperator( "evalIsShared",
                          true,
                          isSharedQuery,
                          false,
                          notSharedQuery,
                          boolean.class );
   }
   
   
   
   @Test
   public void testEvalSharedWith() throws Exception
   {
      final String sharedWithQuery = "(SELECT taskseries_id FROM participants WHERE taskseries_id=taskseries._id "
         + "AND fullname LIKE ''%{0}%'' OR username LIKE ''%{0}%'')";
      
      assertTrue( evaluator.evalSharedWith( "Tom" ) );
      assertQueryAndReset( MessageFormat.format( sharedWithQuery, "Tom" ) );
      
      testEnsureOperator( "evalSharedWith",
                          "Tom",
                          MessageFormat.format( sharedWithQuery, "Tom" ),
                          "User",
                          MessageFormat.format( sharedWithQuery, "User" ),
                          String.class );
   }
   
   
   
   @Test
   public void testEvalLeftParenthesis() throws Exception
   {
      assertTrue( evaluator.evalLeftParenthesis() );
      assertQueryAndReset( "(" );
      
      assertTrue( evaluator.evalLeftParenthesis() );
      assertTrue( evaluator.evalLeftParenthesis() );
      assertQueryAndReset( "( (" );
      
      assertTrue( evaluator.evalLeftParenthesis() );
      assertTrue( evaluator.evalRightParenthesis() );
      assertTrue( evaluator.evalLeftParenthesis() );
      assertTrue( evaluator.evalRightParenthesis() );
      assertQueryAndReset( "( ) AND ( )" );
      
      assertTrue( evaluator.evalLeftParenthesis() );
      assertTrue( evaluator.evalRightParenthesis() );
      assertTrue( evaluator.evalNot() );
      assertTrue( evaluator.evalLeftParenthesis() );
      assertTrue( evaluator.evalRightParenthesis() );
      assertQueryAndReset( "( ) AND NOT ( )" );
      
      assertTrue( evaluator.evalLeftParenthesis() );
      assertTrue( evaluator.evalList( "list" ) );
      assertTrue( evaluator.evalLeftParenthesis() );
      assertQueryAndReset( "( list_name LIKE 'list' AND (" );
      
      assertTrue( evaluator.evalLeftParenthesis() );
      assertTrue( evaluator.evalList( "list" ) );
      assertTrue( evaluator.evalAnd() );
      assertTrue( evaluator.evalLeftParenthesis() );
      assertQueryAndReset( "( list_name LIKE 'list' AND (" );
   }
   
   
   
   @Test
   public void testEvalRightParenthesis()
   {
      assertTrue( evaluator.evalRightParenthesis() );
      assertQueryAndReset( ")" );
      
      assertTrue( evaluator.evalRightParenthesis() );
      assertTrue( evaluator.evalRightParenthesis() );
      assertQueryAndReset( ") )" );
      
      assertTrue( evaluator.evalRightParenthesis() );
      assertTrue( evaluator.evalList( "list" ) );
      assertQueryAndReset( ") AND list_name LIKE 'list'" );
      
      assertTrue( evaluator.evalRightParenthesis() );
      assertTrue( evaluator.evalOr() );
      assertTrue( evaluator.evalList( "list" ) );
      assertQueryAndReset( ") OR list_name LIKE 'list'" );
   }
   
   
   
   @Test
   public void testEvalAnd()
   {
      assertTrue( evaluator.evalAnd() );
      assertQueryAndReset( "AND" );
   }
   
   
   
   @Test
   public void testEvalOr()
   {
      assertTrue( evaluator.evalOr() );
      assertQueryAndReset( "OR" );
   }
   
   
   
   @Test
   public void testEvalNot()
   {
      assertTrue( evaluator.evalNot() );
      assertQueryAndReset( "NOT" );
      
      assertTrue( evaluator.evalRightParenthesis() );
      assertTrue( evaluator.evalNot() );
      assertQueryAndReset( ") AND NOT" );
   }
   
   
   
   private void assertQuery( String query )
   {
      String result = evaluator.getResult();
      if ( query == null )
      {
         assertThat( query, is( (String) null ) );
      }
      else
      {
         result = result.replaceAll( "\\s{2,}", " " );
         assertThat( result, is( "( " + query + " )" ) );
      }
   }
   
   
   
   private void assertQueryAndReset( String query )
   {
      assertQuery( query );
      evaluator.reset();
   }
   
   
   
   private void testDateAndTime( String method, String column ) throws Exception
   {
      final Method evalMethod = DbRtmSmartFilterEvaluator.class.getMethod( method,
                                                                           String.class );
      
      // No date and time
      EasyMock.reset( dateTimeParsing );
      EasyMock.expect( dateTimeParsing.parseDateTimeSpec( "never" ) )
              .andReturn( MolokoCalendar.getDatelessAndTimelessInstance() )
              .anyTimes();
      EasyMock.replay( dateTimeParsing );
      
      assertTrue( (Boolean) evalMethod.invoke( evaluator, "never" ) );
      assertQueryAndReset( "(" + column + " IS NULL)" );
      
      // Only date
      final MolokoCalendar calToday = MolokoCalendar.getInstance();
      calToday.setHasTime( false );
      
      final long todMillis = calToday.getTimeInMillis();
      calToday.add( Calendar.DAY_OF_YEAR, 1 );
      final long tomMillis = calToday.getTimeInMillis();
      calToday.add( Calendar.DAY_OF_YEAR, -1 );
      
      EasyMock.reset( dateTimeParsing );
      EasyMock.expect( dateTimeParsing.parseDateTimeSpec( "today" ) )
              .andReturn( calToday )
              .anyTimes();
      EasyMock.replay( dateTimeParsing );
      
      assertTrue( (Boolean) evalMethod.invoke( evaluator, "today" ) );
      assertQueryAndReset( "(" + column + " >= " + todMillis + " AND " + column
         + " < " + tomMillis + ")" );
      
      // Only time or date with time
      final MolokoCalendar calTodayWithTime = MolokoCalendar.getInstance();
      calTodayWithTime.set( Calendar.HOUR, 10 );
      calTodayWithTime.set( Calendar.MINUTE, 0 );
      calTodayWithTime.set( Calendar.SECOND, 0 );
      calTodayWithTime.set( Calendar.MILLISECOND, 0 );
      
      final long todWithTimeMillis = calTodayWithTime.getTimeInMillis();
      
      EasyMock.reset( dateTimeParsing );
      EasyMock.expect( dateTimeParsing.parseDateTimeSpec( "today@10" ) )
              .andReturn( calTodayWithTime )
              .anyTimes();
      EasyMock.replay( dateTimeParsing );
      
      assertTrue( (Boolean) evalMethod.invoke( evaluator, "today@10" ) );
      assertQueryAndReset( "(" + column + " == " + todWithTimeMillis + ")" );
      
      // Parsing failed
      EasyMock.reset( dateTimeParsing );
      EasyMock.expect( dateTimeParsing.parseDateTimeSpec( "???" ) )
              .andStubThrow( new GrammarException() );
      EasyMock.replay( dateTimeParsing );
      
      assertFalse( (Boolean) evalMethod.invoke( evaluator, "???" ) );
      assertQueryAndReset( null );
      
      // Ensure operator
      final MolokoCalendar calTom = MolokoCalendar.getInstance();
      calTom.add( Calendar.DAY_OF_YEAR, 1 );
      
      EasyMock.reset( dateTimeParsing );
      EasyMock.expect( dateTimeParsing.parseDateTimeSpec( "never" ) )
              .andReturn( MolokoCalendar.getDatelessAndTimelessInstance() )
              .anyTimes();
      EasyMock.expect( dateTimeParsing.parseDateTimeSpec( "tom" ) )
              .andReturn( calTom )
              .anyTimes();
      EasyMock.replay( dateTimeParsing );
      
      testEnsureOperator( method,
                          "never",
                          "(" + column + " IS NULL)",
                          "tom",
                          "(" + column + " == " + calTom.getTimeInMillis()
                             + ")",
                          String.class );
   }
   
   
   
   private void testDateAndTimeBeforeAfter( String method,
                                            String column,
                                            boolean before ) throws Exception
   {
      final Method evalMethod = DbRtmSmartFilterEvaluator.class.getMethod( method,
                                                                           String.class );
      
      // No date and time
      EasyMock.reset( dateTimeParsing );
      EasyMock.expect( dateTimeParsing.parseDateTimeSpec( "never" ) )
              .andReturn( MolokoCalendar.getDatelessAndTimelessInstance() )
              .anyTimes();
      EasyMock.replay( dateTimeParsing );
      
      assertTrue( (Boolean) evalMethod.invoke( evaluator, "never" ) );
      assertQueryAndReset( "(" + column + " IS NULL)" );
      
      // Date
      final MolokoCalendar calToday = MolokoCalendar.getInstance();
      calToday.setHasTime( false );
      
      final long todMillis = calToday.getTimeInMillis();
      
      EasyMock.reset( dateTimeParsing );
      EasyMock.expect( dateTimeParsing.parseDateTimeSpec( "today" ) )
              .andReturn( calToday )
              .anyTimes();
      EasyMock.replay( dateTimeParsing );
      
      assertTrue( (Boolean) evalMethod.invoke( evaluator, "today" ) );
      assertQueryAndReset( "(" + column + ( ( before ) ? " < " : " > " )
         + todMillis + ")" );
      
      // Parsing DateTimeSpec failed
      final MolokoCalendar calTom = MolokoCalendar.getInstance();
      calTom.add( Calendar.DAY_OF_YEAR, 1 );
      
      EasyMock.reset( dateTimeParsing );
      EasyMock.expect( dateTimeParsing.parseDateTimeSpec( "???" ) )
              .andStubThrow( new GrammarException() );
      EasyMock.expect( dateTimeParsing.parseDateWithin( "???", before ) )
              .andReturn( new ParseDateWithinReturn( calToday, calTom ) );
      EasyMock.replay( dateTimeParsing );
      
      assertTrue( (Boolean) evalMethod.invoke( evaluator, "???" ) );
      assertQueryAndReset( "(" + column + ( ( before ) ? " < " : " > " )
         + calTom.getTimeInMillis() + ")" );
      
      // Parsing failed
      EasyMock.reset( dateTimeParsing );
      EasyMock.expect( dateTimeParsing.parseDateTimeSpec( "???" ) )
              .andStubThrow( new GrammarException() );
      EasyMock.expect( dateTimeParsing.parseDateWithin( "???", before ) )
              .andStubThrow( new GrammarException() );
      EasyMock.replay( dateTimeParsing );
      
      assertFalse( (Boolean) evalMethod.invoke( evaluator, "???" ) );
      assertQueryAndReset( null );
      
      // Ensure operator
      EasyMock.reset( dateTimeParsing );
      EasyMock.expect( dateTimeParsing.parseDateTimeSpec( "today" ) )
              .andReturn( calToday )
              .anyTimes();
      EasyMock.expect( dateTimeParsing.parseDateTimeSpec( "tom" ) )
              .andReturn( calTom )
              .anyTimes();
      EasyMock.replay( dateTimeParsing );
      
      testEnsureOperator( method,
                          "today",
                          "(" + column + ( ( before ) ? " < " : " > " )
                             + calToday.getTimeInMillis() + ")",
                          "tom",
                          "(" + column + ( ( before ) ? " < " : " > " )
                             + calTom.getTimeInMillis() + ")",
                          String.class );
   }
   
   
   
   private void testDateAndTimeInRange( String method,
                                        String column,
                                        boolean past ) throws Exception
   {
      final Method evalMethod = DbRtmSmartFilterEvaluator.class.getMethod( method,
                                                                           String.class );
      
      final MolokoCalendar calTod = MolokoCalendar.getInstance();
      final MolokoCalendar calMinus2 = MolokoCalendar.getInstance();
      calMinus2.add( Calendar.DAY_OF_YEAR, -2 );
      
      final String query = "(" + column + " >= "
         + ( !past ? calTod.getTimeInMillis() : calMinus2.getTimeInMillis() )
         + " AND " + column + " < "
         + ( !past ? calMinus2.getTimeInMillis() : calTod.getTimeInMillis() )
         + ")";
      
      EasyMock.expect( dateTimeParsing.parseDateWithin( EasyMock.eq( "before 2 days" ),
                                                        EasyMock.anyBoolean() ) )
              .andReturn( new ParseDateWithinReturn( calTod, calMinus2 ) )
              .anyTimes();
      EasyMock.replay( dateTimeParsing );
      
      assertTrue( (Boolean) evalMethod.invoke( evaluator, "before 2 days" ) );
      assertQueryAndReset( query );
      
      // Ensure operator
      testEnsureOperator( method,
                          "before 2 days",
                          query,
                          "before 2 days",
                          query,
                          String.class );
      
      // Parsing failed
      EasyMock.reset( dateTimeParsing );
      EasyMock.expect( dateTimeParsing.parseDateWithin( "???", past ) )
              .andStubThrow( new GrammarException() );
      EasyMock.replay( dateTimeParsing );
      
      assertFalse( (Boolean) evalMethod.invoke( evaluator, "???" ) );
      assertQueryAndReset( null );
   }
   
   
   
   private < T > void testEnsureOperator( String method,
                                          T param1,
                                          String evalResult1,
                                          T param2,
                                          String evalResult2,
                                          Class< T > clazz ) throws Exception
   {
      final Method evalMethod = DbRtmSmartFilterEvaluator.class.getMethod( method,
                                                                           clazz );
      
      assertTrue( (Boolean) evalMethod.invoke( evaluator, param1 ) );
      assertTrue( (Boolean) evalMethod.invoke( evaluator, param2 ) );
      assertQueryAndReset( evalResult1 + " AND " + evalResult2 );
      
      assertTrue( (Boolean) evalMethod.invoke( evaluator, param1 ) );
      assertTrue( evaluator.evalAnd() );
      assertTrue( (Boolean) evalMethod.invoke( evaluator, param2 ) );
      assertQueryAndReset( evalResult1 + " AND " + evalResult2 );
      
      assertTrue( (Boolean) evalMethod.invoke( evaluator, param1 ) );
      assertTrue( evaluator.evalOr() );
      assertTrue( (Boolean) evalMethod.invoke( evaluator, param2 ) );
      assertQueryAndReset( evalResult1 + " OR " + evalResult2 );
      
      assertTrue( (Boolean) evalMethod.invoke( evaluator, param1 ) );
      assertTrue( evaluator.evalNot() );
      assertTrue( (Boolean) evalMethod.invoke( evaluator, param2 ) );
      assertQueryAndReset( evalResult1 + " AND NOT " + evalResult2 );
      
      assertTrue( evaluator.evalNot() );
      assertTrue( (Boolean) evalMethod.invoke( evaluator, param1 ) );
      assertTrue( (Boolean) evalMethod.invoke( evaluator, param2 ) );
      assertQueryAndReset( "NOT " + evalResult1 + " AND " + evalResult2 );
   }
}
