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

package dev.drsoran.moloko.test.grammar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Pattern;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mdt.rtm.data.RtmTask;
import com.mdt.rtm.data.RtmTask.Priority;

import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.grammar.IDateTimeParsing;
import dev.drsoran.moloko.grammar.RtmSmartFilterParsing;
import dev.drsoran.moloko.grammar.rtmsmart.RtmSmartFilterLexer;
import dev.drsoran.moloko.grammar.rtmsmart.RtmSmartFilterReturn;
import dev.drsoran.moloko.grammar.rtmsmart.RtmSmartFilterToken;
import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.provider.Rtm.Locations;
import dev.drsoran.provider.Rtm.Notes;
import dev.drsoran.provider.Rtm.Tags;
import dev.drsoran.provider.Rtm.Tasks;


public class RtmSmartFilerParsingTest extends MolokoTestCase
{
   private final DateFormat dateFormat = new SimpleDateFormat( "MM/dd/yyyy" );
   
   private final DateFormat timeFormat = new SimpleDateFormat( "hh:mma" );
   
   private final DateFormat dateTimeFormat = new SimpleDateFormat( "MM/dd/yyyy, hh:mma" );
   
   private ILog log;
   
   private IDateTimeParsing dateTimeParsing;
   
   private RtmSmartFilterParsing smartFilterParsing;
   
   
   
   @Override
   @Before
   public void setUp()
   {
      super.setUp();
      
      log = EasyMock.createNiceMock( ILog.class );
      dateTimeParsing = EasyMock.createNiceMock( IDateTimeParsing.class );
      
      smartFilterParsing = new RtmSmartFilterParsing( log, dateTimeParsing );
   }
   
   
   
   @Override
   @After
   public void tearDown()
   {
      smartFilterParsing = null;
      super.tearDown();
   }
   
   
   
   @Test
   public void evaluate_op_name()
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
   public void evaluate_op_list()
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
   public void evaluate_op_priority()
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
   public void evaluate_op_status()
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
   public void evaluate_op_tag()
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
   public void evaluate_op_tagcontains()
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
   public void evaluate_op_istagged()
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
   public void evaluate_op_location()
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
   public void evaluate_op_islocated()
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
   public void evaluate_op_isrepeating()
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
   public void evaluate_op_notecontains()
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
   public void evaluate_op_hasnotes()
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
   public void evaluate_op_due() throws ParseException
   {
      testTimeOperator( RtmSmartFilterLexer.OP_DUE_LIT,
                        RtmSmartFilterLexer.OP_DUE,
                        Tasks.DUE_DATE );
   }
   
   
   
   @Test
   public void evaluate_op_dueafter() throws ParseException
   {
      testTimeOperatorAfter( RtmSmartFilterLexer.OP_DUE_AFTER_LIT,
                             RtmSmartFilterLexer.OP_DUE_AFTER,
                             Tasks.DUE_DATE );
   }
   
   
   
   @Test
   public void evaluate_op_duebefore() throws ParseException
   {
      testTimeOperatorBefore( RtmSmartFilterLexer.OP_DUE_BEFORE_LIT,
                              RtmSmartFilterLexer.OP_DUE_BEFORE,
                              Tasks.DUE_DATE );
   }
   
   
   
   @Test
   public void evaluate_op_duewithin() throws ParseException
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
   public void evaluate_op_completed() throws ParseException
   {
      testTimeOperator( RtmSmartFilterLexer.OP_COMPLETED_LIT,
                        RtmSmartFilterLexer.OP_COMPLETED,
                        Tasks.COMPLETED_DATE,
                        true );
   }
   
   
   
   @Test
   public void evaluate_op_completedafter() throws ParseException
   {
      testTimeOperatorAfter( RtmSmartFilterLexer.OP_COMPLETED_AFTER_LIT,
                             RtmSmartFilterLexer.OP_COMPLETED_AFTER,
                             Tasks.COMPLETED_DATE,
                             true );
   }
   
   
   
   @Test
   public void evaluate_op_completedbefore() throws ParseException
   {
      testTimeOperatorBefore( RtmSmartFilterLexer.OP_COMPLETED_BEFORE_LIT,
                              RtmSmartFilterLexer.OP_COMPLETED_BEFORE,
                              Tasks.COMPLETED_DATE,
                              true );
   }
   
   
   
   @Test
   public void evaluate_op_completedwithin() throws ParseException
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
   public void evaluate_op_added() throws ParseException
   {
      testTimeOperator( RtmSmartFilterLexer.OP_ADDED_LIT,
                        RtmSmartFilterLexer.OP_ADDED,
                        Tasks.ADDED_DATE );
   }
   
   
   
   @Test
   public void evaluate_op_addeddafter() throws ParseException
   {
      testTimeOperatorAfter( RtmSmartFilterLexer.OP_ADDED_AFTER_LIT,
                             RtmSmartFilterLexer.OP_ADDED_AFTER,
                             Tasks.ADDED_DATE );
   }
   
   
   
   @Test
   public void evaluate_op_addedbefore() throws ParseException
   {
      testTimeOperatorBefore( RtmSmartFilterLexer.OP_ADDED_BEFORE_LIT,
                              RtmSmartFilterLexer.OP_ADDED_BEFORE,
                              Tasks.ADDED_DATE );
   }
   
   
   
   @Test
   public void evaluate_op_addedwithin() throws ParseException
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
   
   
   
   /**
    * https://code.google.com/p/moloko/issues/detail?id=80
    */
   @Test
   public void evaluate_sleeperTags() throws ParseException
   {
      evaluateFilter( "(tag:zzz AND dueAfter:now)"
                         + " OR (tag:z1d AND dueAfter:\"1 day of now\")"
                         + " OR (tag:z2d AND dueAfter:\"2 days of now\")"
                         + " OR (tag:z1w AND dueAfter:\"1 week of now\")"
                         + " OR (tag:z1m AND dueAfter:\"1 month of now\")"
                         + " OR (tag:z2w AND dueAfter:\"2 weeks of now\")",
                      new RtmSmartFilterReturn( "\\( \\( \\(tags = 'zzz' OR tags like 'zzz,%' OR tags like '%,zzz,%' OR tags like '%,zzz'\\) AND due > \\d+ \\)"
                                                   + " OR \\( \\(tags = 'z1d' OR tags like 'z1d,%' OR tags like '%,z1d,%' OR tags like '%,z1d'\\) AND due > \\d+ \\)"
                                                   + " OR \\( \\(tags = 'z2d' OR tags like 'z2d,%' OR tags like '%,z2d,%' OR tags like '%,z2d'\\) AND due > \\d+ \\)"
                                                   + " OR \\( \\(tags = 'z1w' OR tags like 'z1w,%' OR tags like '%,z1w,%' OR tags like '%,z1w'\\) AND due > \\d+ \\)"
                                                   + " OR \\( \\(tags = 'z1m' OR tags like 'z1m,%' OR tags like '%,z1m,%' OR tags like '%,z1m'\\) AND due > \\d+ \\)"
                                                   + " OR \\( \\(tags = 'z2w' OR tags like 'z2w,%' OR tags like '%,z2w,%' OR tags like '%,z2w'\\) AND due > \\d+ \\) \\)",
                                                false,
                                                true ) );
   }
   
   
   
   @Test
   public void hasCompletedOperator_op_status()
   {
      Collection< RtmSmartFilterToken > tokens = Arrays.asList( new RtmSmartFilterToken[]
      { new RtmSmartFilterToken( RtmSmartFilterLexer.OP_STATUS,
                                 RtmSmartFilterLexer.COMPLETED_LIT,
                                 false ) } );
      
      Assert.assertFalse( smartFilterParsing.hasCompletedOperator( tokens ) );
      
      tokens = Arrays.asList( new RtmSmartFilterToken[]
      { new RtmSmartFilterToken( RtmSmartFilterLexer.OP_STATUS,
                                 RtmSmartFilterLexer.INCOMPLETE_LIT,
                                 false ) } );
   }
   
   
   
   @Test
   public void hasCompletedOperator_op_completed()
   {
      Collection< RtmSmartFilterToken > tokens = Arrays.asList( new RtmSmartFilterToken[]
      { new RtmSmartFilterToken( RtmSmartFilterLexer.OP_COMPLETED,
                                 RtmSmartFilterLexer.TRUE_LIT,
                                 false ) } );
      
      Assert.assertTrue( smartFilterParsing.hasCompletedOperator( tokens ) );
      
      tokens = Arrays.asList( new RtmSmartFilterToken[]
      { new RtmSmartFilterToken( RtmSmartFilterLexer.OP_COMPLETED,
                                 RtmSmartFilterLexer.FALSE_LIT,
                                 false ) } );
      
      Assert.assertTrue( smartFilterParsing.hasCompletedOperator( tokens ) );
   }
   
   
   
   @Test
   public void hasCompletedOperator_op_completedafter()
   {
      Collection< RtmSmartFilterToken > tokens = Arrays.asList( new RtmSmartFilterToken[]
      { new RtmSmartFilterToken( RtmSmartFilterLexer.OP_COMPLETED_AFTER,
                                 "",
                                 false ) } );
      
      Assert.assertTrue( smartFilterParsing.hasCompletedOperator( tokens ) );
   }
   
   
   
   @Test
   public void hasCompletedOperator_op_completebefore()
   {
      Collection< RtmSmartFilterToken > tokens = Arrays.asList( new RtmSmartFilterToken[]
      { new RtmSmartFilterToken( RtmSmartFilterLexer.OP_COMPLETED_BEFORE,
                                 "",
                                 false ) } );
      
      Assert.assertTrue( smartFilterParsing.hasCompletedOperator( tokens ) );
   }
   
   
   
   @Test
   public void hasCompletedOperator_op_completedwithin()
   {
      Collection< RtmSmartFilterToken > tokens = Arrays.asList( new RtmSmartFilterToken[]
      { new RtmSmartFilterToken( RtmSmartFilterLexer.OP_COMPLETED_WITHIN,
                                 "",
                                 false ) } );
      
      Assert.assertTrue( smartFilterParsing.hasCompletedOperator( tokens ) );
   }
   
   
   
   @Test
   public void hasCompletedOperator_op_tag()
   {
      Collection< RtmSmartFilterToken > tokens = Arrays.asList( new RtmSmartFilterToken[]
      { new RtmSmartFilterToken( RtmSmartFilterLexer.OP_TAG, "", false ) } );
      
      Assert.assertFalse( smartFilterParsing.hasCompletedOperator( tokens ) );
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
   
   
   
   private void evaluateFilter( String filterString,
                                RtmSmartFilterReturn expectedResult,
                                RtmSmartFilterToken... expectedTokens )
   {
      final ArrayList< RtmSmartFilterToken > lexedTokens = new ArrayList< RtmSmartFilterToken >();
      final RtmSmartFilterReturn result = smartFilterParsing.evaluateRtmSmartFilter( filterString,
                                                                                     lexedTokens );
      
      if ( expectedResult == null )
      {
         Assert.assertNull( "Expected the smart filter parsing to return null",
                            result );
      }
      else
      {
         // TODO: matchQuery must not be at the domain class
         if ( expectedResult.matchQuery )
         {
            
            final boolean match = Pattern.matches( expectedResult.queryString,
                                                   result.queryString );
            
            Assert.assertTrue( "Query string did not match result.", match );
            
            Assert.assertEquals( "Completed operator differs",
                                 expectedResult.hasCompletedOperator,
                                 result.hasCompletedOperator );
         }
         else
         {
            Assert.assertEquals( "Wrong smart filter parsing result",
                                 expectedResult,
                                 result );
         }
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
