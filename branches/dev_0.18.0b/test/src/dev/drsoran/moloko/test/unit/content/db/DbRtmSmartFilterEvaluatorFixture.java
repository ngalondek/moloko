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
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Calendar;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import dev.drsoran.moloko.content.db.DbRtmSmartFilterEvaluator;
import dev.drsoran.moloko.content.db.TableColumns.RtmLocationColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmRawTaskColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTaskSeriesColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTasksListColumns;
import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.moloko.test.TestConstants;
import dev.drsoran.rtm.RtmCalendar;
import dev.drsoran.rtm.parsing.GrammarException;
import dev.drsoran.rtm.parsing.IRtmDateTimeParsing;
import dev.drsoran.rtm.parsing.datetime.ParseDateWithinReturn;


public class DbRtmSmartFilterEvaluatorFixture extends MolokoTestCase
{
   private IRtmDateTimeParsing dateTimeParsing;
   
   private DbRtmSmartFilterEvaluator evaluator;
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      
      dateTimeParsing = EasyMock.createNiceMock( IRtmDateTimeParsing.class );
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
      assertThat( evaluator.getResult(), nullValue() );
   }
   
   
   
   @Test
   public void testReset()
   {
      evaluator.reset();
      assertThat( evaluator.getResult(), nullValue() );
      
      assertTrue( evaluator.evalAnd() );
      assertThat( evaluator.getResult(), notNullValue() );
      
      evaluator.reset();
      assertThat( evaluator.getResult(), nullValue() );
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
      
      testEnsureOperator( "evalList", "listName", RtmTasksListColumns.LIST_NAME
         + " LIKE 'listName'", "List with Space", RtmTasksListColumns.LIST_NAME
         + " LIKE 'List with Space'", String.class );
   }
   
   
   
   @Test
   public void testEvalPriority() throws Exception
   {
      assertTrue( evaluator.evalPriority( "n" ) );
      assertQueryAndReset( RtmRawTaskColumns.PRIORITY + " LIKE 'n'" );
      
      assertFalse( evaluator.evalPriority( "-1" ) );
      assertFalse( evaluator.evalPriority( "4" ) );
      
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
         + " LIKE '%tag%'", "tag1", RtmTaskSeriesColumns.TAGS
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
      
      assertTrue( evaluator.evalTimeEstimate( "<", "now" ) );
      assertQueryAndReset( String.format( lessQuery, TestConstants.NOW ) );
      
      assertTrue( evaluator.evalTimeEstimate( ">", "now" ) );
      assertQueryAndReset( String.format( greaterQuery, TestConstants.NOW ) );
      
      assertTrue( evaluator.evalTimeEstimate( null, "now" ) );
      assertQueryAndReset( String.format( equalQuery, TestConstants.NOW ) );
      
      assertTrue( evaluator.evalTimeEstimate( "", "now" ) );
      assertQueryAndReset( String.format( equalQuery, TestConstants.NOW ) );
      
      assertFalse( evaluator.evalTimeEstimate( ">", "???" ) );
      assertQueryAndReset( null );
   }
   
   
   
   @Test
   public void testEvalPostponed() throws Exception
   {
      assertThat( evaluator.evalPostponed( "", 0 ), is( true ) );
      assertQueryAndReset( RtmRawTaskColumns.POSTPONED + "=0" );
      
      assertThat( evaluator.evalPostponed( null, 1 ), is( true ) );
      assertQueryAndReset( RtmRawTaskColumns.POSTPONED + "=1" );
      
      assertTrue( evaluator.evalPostponed( "<", 100 ) );
      assertQueryAndReset( RtmRawTaskColumns.POSTPONED + "<100" );
      
      assertTrue( evaluator.evalPostponed( ">", 3 ) );
      assertQueryAndReset( RtmRawTaskColumns.POSTPONED + ">3" );
      
      assertFalse( evaluator.evalPostponed( ">", -1 ) );
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
   
   
   
   /**
    * https://code.google.com/p/moloko/issues/detail?id=80
    * 
    * @throws GrammarException
    */
   @Test
   public void testSleeperTags() throws GrammarException
   {
      final RtmCalendar now = RtmCalendar.getInstance();
      now.setTimeInMillis( TestConstants.NOW );
      
      final RtmCalendar oneDayOfNow = now.clone();
      oneDayOfNow.add( Calendar.DAY_OF_YEAR, 1 );
      
      final RtmCalendar twoDayOfNow = now.clone();
      oneDayOfNow.add( Calendar.DAY_OF_YEAR, 2 );
      
      final RtmCalendar oneWeekOfNow = now.clone();
      oneDayOfNow.add( Calendar.WEEK_OF_YEAR, 1 );
      
      final RtmCalendar twoWeeksOfNow = now.clone();
      oneDayOfNow.add( Calendar.WEEK_OF_YEAR, 2 );
      
      final RtmCalendar oneMonthOfNow = now.clone();
      oneDayOfNow.add( Calendar.MONTH, 1 );
      
      EasyMock.reset( dateTimeParsing );
      EasyMock.expect( dateTimeParsing.parseDateTime( "now" ) )
              .andReturn( now )
              .anyTimes();
      EasyMock.expect( dateTimeParsing.parseDateTime( "1 day of now" ) )
              .andReturn( oneDayOfNow )
              .anyTimes();
      EasyMock.expect( dateTimeParsing.parseDateTime( "2 days of now" ) )
              .andReturn( twoDayOfNow )
              .anyTimes();
      EasyMock.expect( dateTimeParsing.parseDateTime( "1 week of now" ) )
              .andReturn( oneWeekOfNow )
              .anyTimes();
      EasyMock.expect( dateTimeParsing.parseDateTime( "2 weeks of now" ) )
              .andReturn( twoWeeksOfNow )
              .anyTimes();
      EasyMock.expect( dateTimeParsing.parseDateTime( "1 month of now" ) )
              .andReturn( oneMonthOfNow )
              .anyTimes();
      EasyMock.replay( dateTimeParsing );
      
      // (tag:zzz AND dueAfter:now)
      assertTrue( evaluator.evalLeftParenthesis() );
      assertTrue( evaluator.evalTag( "zzz" ) );
      assertTrue( evaluator.evalAnd() );
      assertTrue( evaluator.evalDueAfter( "now" ) );
      assertTrue( evaluator.evalRightParenthesis() );
      
      // OR (tag:z1d AND dueAfter:"1 day of now")
      assertTrue( evaluator.evalOr() );
      assertTrue( evaluator.evalLeftParenthesis() );
      assertTrue( evaluator.evalTag( "z1d" ) );
      assertTrue( evaluator.evalAnd() );
      assertTrue( evaluator.evalDueAfter( "1 day of now" ) );
      assertTrue( evaluator.evalRightParenthesis() );
      
      // OR (tag:z2d AND dueAfter:"2 days of now")
      assertTrue( evaluator.evalOr() );
      assertTrue( evaluator.evalLeftParenthesis() );
      assertTrue( evaluator.evalTag( "z2d" ) );
      assertTrue( evaluator.evalAnd() );
      assertTrue( evaluator.evalDueAfter( "2 days of now" ) );
      assertTrue( evaluator.evalRightParenthesis() );
      
      // OR (tag:z1w AND dueAfter:"1 week of now")
      assertTrue( evaluator.evalOr() );
      assertTrue( evaluator.evalLeftParenthesis() );
      assertTrue( evaluator.evalTag( "z1w" ) );
      assertTrue( evaluator.evalAnd() );
      assertTrue( evaluator.evalDueAfter( "1 week of now" ) );
      assertTrue( evaluator.evalRightParenthesis() );
      
      // OR (tag:z1m AND dueAfter:"1 month of now")
      assertTrue( evaluator.evalOr() );
      assertTrue( evaluator.evalLeftParenthesis() );
      assertTrue( evaluator.evalTag( "z1m" ) );
      assertTrue( evaluator.evalAnd() );
      assertTrue( evaluator.evalDueAfter( "1 month of now" ) );
      assertTrue( evaluator.evalRightParenthesis() );
      
      // OR (tag:z2w AND dueAfter:"2 weeks of now")
      assertTrue( evaluator.evalOr() );
      assertTrue( evaluator.evalLeftParenthesis() );
      assertTrue( evaluator.evalTag( "z2w" ) );
      assertTrue( evaluator.evalAnd() );
      assertTrue( evaluator.evalDueAfter( "2 weeks of now" ) );
      assertTrue( evaluator.evalRightParenthesis() );
      
      assertQueryAndReset( MessageFormat.format( "( (tags = ''zzz'' OR tags LIKE ''zzz,%'' OR tags LIKE ''%,zzz,%'' OR tags LIKE ''%,zzz'') AND (due > {0,number,#}) )"
                                                    + " OR ( (tags = ''z1d'' OR tags LIKE ''z1d,%'' OR tags LIKE ''%,z1d,%'' OR tags LIKE ''%,z1d'') AND (due > {1,number,#}) )"
                                                    + " OR ( (tags = ''z2d'' OR tags LIKE ''z2d,%'' OR tags LIKE ''%,z2d,%'' OR tags LIKE ''%,z2d'') AND (due > {2,number,#}) )"
                                                    + " OR ( (tags = ''z1w'' OR tags LIKE ''z1w,%'' OR tags LIKE ''%,z1w,%'' OR tags LIKE ''%,z1w'') AND (due > {3,number,#}) )"
                                                    + " OR ( (tags = ''z1m'' OR tags LIKE ''z1m,%'' OR tags LIKE ''%,z1m,%'' OR tags LIKE ''%,z1m'') AND (due > {4,number,#}) )"
                                                    + " OR ( (tags = ''z2w'' OR tags LIKE ''z2w,%'' OR tags LIKE ''%,z2w,%'' OR tags LIKE ''%,z2w'') AND (due > {5,number,#}) )",
                                                 now.getTimeInMillis(),
                                                 oneDayOfNow.getTimeInMillis(),
                                                 twoDayOfNow.getTimeInMillis(),
                                                 oneWeekOfNow.getTimeInMillis(),
                                                 oneMonthOfNow.getTimeInMillis(),
                                                 twoWeeksOfNow.getTimeInMillis() ) );
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
         assertThat( "( " + query + " )", is( result ) );
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
      EasyMock.expect( dateTimeParsing.parseDateTime( "never" ) )
              .andReturn( RtmCalendar.getDatelessAndTimelessInstance() )
              .anyTimes();
      EasyMock.replay( dateTimeParsing );
      
      assertTrue( (Boolean) evalMethod.invoke( evaluator, "never" ) );
      assertQueryAndReset( "(" + column + " IS NULL)" );
      
      // Only date
      final RtmCalendar calToday = RtmCalendar.getInstance();
      calToday.setHasTime( false );
      
      final long todMillis = calToday.getTimeInMillis();
      calToday.add( Calendar.DAY_OF_YEAR, 1 );
      final long tomMillis = calToday.getTimeInMillis();
      calToday.add( Calendar.DAY_OF_YEAR, -1 );
      
      EasyMock.reset( dateTimeParsing );
      EasyMock.expect( dateTimeParsing.parseDateTime( "today" ) )
              .andReturn( calToday )
              .anyTimes();
      EasyMock.replay( dateTimeParsing );
      
      assertTrue( (Boolean) evalMethod.invoke( evaluator, "today" ) );
      assertQueryAndReset( "(" + column + " >= " + todMillis + " AND " + column
         + " < " + tomMillis + ")" );
      
      // Only time or date with time
      final RtmCalendar calTodayWithTime = RtmCalendar.getInstance();
      calTodayWithTime.set( Calendar.HOUR, 10 );
      calTodayWithTime.set( Calendar.MINUTE, 0 );
      calTodayWithTime.set( Calendar.SECOND, 0 );
      calTodayWithTime.set( Calendar.MILLISECOND, 0 );
      
      final long todWithTimeMillis = calTodayWithTime.getTimeInMillis();
      
      EasyMock.reset( dateTimeParsing );
      EasyMock.expect( dateTimeParsing.parseDateTime( "today@10" ) )
              .andReturn( calTodayWithTime )
              .anyTimes();
      EasyMock.replay( dateTimeParsing );
      
      assertTrue( (Boolean) evalMethod.invoke( evaluator, "today@10" ) );
      assertQueryAndReset( "(" + column + " == " + todWithTimeMillis + ")" );
      
      // Parsing failed
      EasyMock.reset( dateTimeParsing );
      EasyMock.expect( dateTimeParsing.parseDateTime( "???" ) )
              .andStubThrow( new GrammarException() );
      EasyMock.replay( dateTimeParsing );
      
      assertFalse( (Boolean) evalMethod.invoke( evaluator, "???" ) );
      assertQueryAndReset( null );
      
      // Ensure operator
      final RtmCalendar calTom = RtmCalendar.getInstance();
      calTom.add( Calendar.DAY_OF_YEAR, 1 );
      
      EasyMock.reset( dateTimeParsing );
      EasyMock.expect( dateTimeParsing.parseDateTime( "never" ) )
              .andReturn( RtmCalendar.getDatelessAndTimelessInstance() )
              .anyTimes();
      EasyMock.expect( dateTimeParsing.parseDateTime( "tom" ) )
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
      EasyMock.expect( dateTimeParsing.parseDateTime( "never" ) )
              .andReturn( RtmCalendar.getDatelessAndTimelessInstance() )
              .anyTimes();
      EasyMock.replay( dateTimeParsing );
      
      assertTrue( (Boolean) evalMethod.invoke( evaluator, "never" ) );
      assertQueryAndReset( "(" + column + " IS NULL)" );
      
      // Date
      final RtmCalendar calToday = RtmCalendar.getInstance();
      calToday.setHasTime( false );
      
      final long todMillis = calToday.getTimeInMillis();
      
      EasyMock.reset( dateTimeParsing );
      EasyMock.expect( dateTimeParsing.parseDateTime( "today" ) )
              .andReturn( calToday )
              .anyTimes();
      EasyMock.replay( dateTimeParsing );
      
      assertTrue( (Boolean) evalMethod.invoke( evaluator, "today" ) );
      assertQueryAndReset( "(" + column + ( ( before ) ? " < " : " > " )
         + todMillis + ")" );
      
      // Parsing DateTimeSpec failed
      final RtmCalendar calTom = RtmCalendar.getInstance();
      calTom.add( Calendar.DAY_OF_YEAR, 1 );
      
      EasyMock.reset( dateTimeParsing );
      EasyMock.expect( dateTimeParsing.parseDateTime( "???" ) )
              .andStubThrow( new GrammarException() );
      EasyMock.expect( dateTimeParsing.parseDateWithin( "???" ) )
              .andReturn( new ParseDateWithinReturn( calToday, calTom ) );
      EasyMock.replay( dateTimeParsing );
      
      assertTrue( (Boolean) evalMethod.invoke( evaluator, "???" ) );
      assertQueryAndReset( "(" + column + ( ( before ) ? " < " : " > " )
         + calTom.getTimeInMillis() + ")" );
      
      // Parsing failed
      EasyMock.reset( dateTimeParsing );
      EasyMock.expect( dateTimeParsing.parseDateTime( "???" ) )
              .andStubThrow( new GrammarException() );
      EasyMock.expect( dateTimeParsing.parseDateWithin( "???" ) )
              .andStubThrow( new GrammarException() );
      EasyMock.replay( dateTimeParsing );
      
      assertFalse( (Boolean) evalMethod.invoke( evaluator, "???" ) );
      assertQueryAndReset( null );
      
      // Ensure operator
      EasyMock.reset( dateTimeParsing );
      EasyMock.expect( dateTimeParsing.parseDateTime( "today" ) )
              .andReturn( calToday )
              .anyTimes();
      EasyMock.expect( dateTimeParsing.parseDateTime( "tom" ) )
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
      
      final RtmCalendar calTod = RtmCalendar.getInstance();
      final RtmCalendar calMinus2 = RtmCalendar.getInstance();
      calMinus2.add( Calendar.DAY_OF_YEAR, -2 );
      
      final String query = "(" + column + " >= "
         + ( !past ? calTod.getTimeInMillis() : calMinus2.getTimeInMillis() )
         + " AND " + column + " < "
         + ( !past ? calMinus2.getTimeInMillis() : calTod.getTimeInMillis() )
         + ")";
      
      EasyMock.expect( dateTimeParsing.parseDateWithin( EasyMock.eq( "before 2 days" ) ) )
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
      EasyMock.expect( dateTimeParsing.parseDateWithin( "???" ) )
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
