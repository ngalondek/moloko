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

package dev.drsoran.test.unit.rtm.parsing.grammar.rtmsmart;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.rtm.model.Priority;
import dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterBuilder;


public class RtmSmartFilterBuilderFixture extends MolokoTestCase
{
   private RtmSmartFilterBuilder builder;
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      builder = new RtmSmartFilterBuilder();
   }
   
   
   
   @Test
   public void testFilter()
   {
      builder.filter( new RtmSmartFilter( "list:\"Test List\"" ) );
      assertThat( builder.toString(), is( "list:\"Test List\"" ) );
   }
   
   
   
   @Test
   public void testFilterString()
   {
      builder.filterString( "list:\"Test List\"" );
      assertThat( builder.toString(), is( "list:\"Test List\"" ) );
   }
   
   
   
   @Test
   public void testList()
   {
      builder.list( "List with spaces" );
      assertThat( builder.toString().trim(), is( "list:\"List with spaces\"" ) );
   }
   
   
   
   @Test
   public void testPriority()
   {
      builder.priority( Priority.High );
      assertThat( builder.toString().trim(), is( "priority:1" ) );
   }
   
   
   
   @Test
   public void testIsCompleted()
   {
      builder.isCompleted( true );
      assertThat( builder.toString().trim(), is( "status:completed" ) );
      
      builder = new RtmSmartFilterBuilder();
      
      builder.isCompleted( false );
      assertThat( builder.toString().trim(), is( "status:incomplete" ) );
   }
   
   
   
   @Test
   public void testTag()
   {
      builder.tag( "Tag w/ space" );
      assertThat( builder.toString().trim(), is( "tag:\"Tag w/ space\"" ) );
   }
   
   
   
   @Test
   public void testTagContains()
   {
      builder.tagContains( "Text w/ space" );
      assertThat( builder.toString().trim(),
                  is( "tagcontains:\"Text w/ space\"" ) );
   }
   
   
   
   @Test
   public void testIsTagged()
   {
      builder.isTagged( true );
      assertThat( builder.toString().trim(), is( "istagged:true" ) );
      
      builder = new RtmSmartFilterBuilder();
      
      builder.isTagged( false );
      assertThat( builder.toString().trim(), is( "istagged:false" ) );
   }
   
   
   
   @Test
   public void testLocation()
   {
      builder.location( "Loc w/ space" );
      assertThat( builder.toString().trim(), is( "location:\"Loc w/ space\"" ) );
   }
   
   
   
   @Test
   public void testIsLocated()
   {
      builder.isLocated( true );
      assertThat( builder.toString().trim(), is( "islocated:true" ) );
      
      builder = new RtmSmartFilterBuilder();
      
      builder.isLocated( false );
      assertThat( builder.toString().trim(), is( "islocated:false" ) );
   }
   
   
   
   @Test
   public void testIsRepeating()
   {
      builder.isRepeating( true );
      assertThat( builder.toString().trim(), is( "isrepeating:true" ) );
      
      builder = new RtmSmartFilterBuilder();
      
      builder.isRepeating( false );
      assertThat( builder.toString().trim(), is( "isrepeating:false" ) );
   }
   
   
   
   @Test
   public void testName()
   {
      builder.name( "Name w/ space" );
      assertThat( builder.toString().trim(), is( "name:\"Name w/ space\"" ) );
   }
   
   
   
   @Test
   public void testNoteContains()
   {
      builder.noteContains( "Text w/ space" );
      assertThat( builder.toString().trim(),
                  is( "notecontains:\"Text w/ space\"" ) );
   }
   
   
   
   @Test
   public void testHasNotes()
   {
      builder.hasNotes( true );
      assertThat( builder.toString().trim(), is( "hasnotes:true" ) );
      
      builder = new RtmSmartFilterBuilder();
      
      builder.hasNotes( false );
      assertThat( builder.toString().trim(), is( "hasnotes:false" ) );
   }
   
   
   
   @Test
   public void testStatusCompleted()
   {
      builder.statusCompleted();
      assertThat( builder.toString().trim(), is( "status:completed" ) );
   }
   
   
   
   @Test
   public void testStatusInccomplete()
   {
      builder.statusIncomplete();
      assertThat( builder.toString().trim(), is( "status:incomplete" ) );
   }
   
   
   
   @Test
   public void testDueString()
   {
      builder.due( "today" );
      assertThat( builder.toString().trim(), is( "due:\"today\"" ) );
   }
   
   
   
   @Test
   public void testDue()
   {
      builder.due();
      assertThat( builder.toString().trim(), is( "due:" ) );
   }
   
   
   
   @Test
   public void testDueAfterString()
   {
      builder.dueAfter( "today" );
      assertThat( builder.toString().trim(), is( "dueafter:\"today\"" ) );
   }
   
   
   
   @Test
   public void testDueAfter()
   {
      builder.dueAfter();
      assertThat( builder.toString().trim(), is( "dueafter:" ) );
   }
   
   
   
   @Test
   public void testDueBeforeString()
   {
      builder.dueBefore( "today" );
      assertThat( builder.toString().trim(), is( "duebefore:\"today\"" ) );
   }
   
   
   
   @Test
   public void testDueBefore()
   {
      builder.dueBefore();
      assertThat( builder.toString().trim(), is( "duebefore:" ) );
   }
   
   
   
   @Test
   public void testDueWithin()
   {
      builder.dueWithin( "2 days" );
      assertThat( builder.toString().trim(), is( "duewithin:\"2 days\"" ) );
   }
   
   
   
   @Test
   public void testCompletedString()
   {
      builder.completed( "today" );
      assertThat( builder.toString().trim(), is( "completed:\"today\"" ) );
   }
   
   
   
   @Test
   public void testCompleted()
   {
      builder.completed();
      assertThat( builder.toString().trim(), is( "completed:" ) );
   }
   
   
   
   @Test
   public void testCompletedAfterString()
   {
      builder.completedAfter( "today" );
      assertThat( builder.toString().trim(), is( "completedafter:\"today\"" ) );
   }
   
   
   
   @Test
   public void testCompletedAfter()
   {
      builder.completedAfter();
      assertThat( builder.toString().trim(), is( "completedafter:" ) );
   }
   
   
   
   @Test
   public void testCompletedBeforeString()
   {
      builder.completedBefore( "today" );
      assertThat( builder.toString().trim(), is( "completedbefore:\"today\"" ) );
   }
   
   
   
   @Test
   public void testCompletedBefore()
   {
      builder.completedBefore();
      assertThat( builder.toString().trim(), is( "completedbefore:" ) );
   }
   
   
   
   @Test
   public void testCompletedWithin()
   {
      builder.completedWithin( "today" );
      assertThat( builder.toString().trim(), is( "completedwithin:\"today\"" ) );
   }
   
   
   
   @Test
   public void testAddedString()
   {
      builder.added( "today" );
      assertThat( builder.toString().trim(), is( "added:\"today\"" ) );
   }
   
   
   
   @Test
   public void testAdded()
   {
      builder.added();
      assertThat( builder.toString().trim(), is( "added:" ) );
   }
   
   
   
   @Test
   public void testAddedAfterString()
   {
      builder.addedAfter( "today" );
      assertThat( builder.toString().trim(), is( "addedafter:\"today\"" ) );
   }
   
   
   
   @Test
   public void testAddedAfter()
   {
      builder.addedAfter();
      assertThat( builder.toString().trim(), is( "addedafter:" ) );
   }
   
   
   
   @Test
   public void testAddedBeforeString()
   {
      builder.addedBefore( "today" );
      assertThat( builder.toString().trim(), is( "addedbefore:\"today\"" ) );
   }
   
   
   
   @Test
   public void testAddedBefore()
   {
      builder.addedBefore();
      assertThat( builder.toString().trim(), is( "addedbefore:" ) );
   }
   
   
   
   @Test
   public void testAddedWithin()
   {
      builder.addedWithin( "today" );
      assertThat( builder.toString().trim(), is( "addedwithin:\"today\"" ) );
   }
   
   
   
   @Test
   public void testEstimated()
   {
      builder.estimated( ">2 hours" );
      assertThat( builder.toString().trim(), is( "timeestimate:\">2 hours\"" ) );
   }
   
   
   
   @Test
   public void testPostponed()
   {
      builder.postponed( 3 );
      assertThat( builder.toString().trim(), is( "postponed:3" ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testPostponedNegative()
   {
      builder.postponed( -1 );
   }
   
   
   
   @Test
   public void testIsShared()
   {
      builder.isShared( true );
      assertThat( builder.toString().trim(), is( "isshared:true" ) );
      
      builder = new RtmSmartFilterBuilder();
      
      builder.isShared( false );
      assertThat( builder.toString().trim(), is( "isshared:false" ) );
   }
   
   
   
   @Test
   public void testSharedWith()
   {
      builder.sharedWith( "me" );
      assertThat( builder.toString().trim(), is( "sharedwith:\"me\"" ) );
   }
   
   
   
   @Test
   public void testLParenth()
   {
      builder.lParenth();
      assertThat( builder.toString().trim(), is( "(" ) );
   }
   
   
   
   @Test
   public void testRParenth()
   {
      builder.rParenth();
      assertThat( builder.toString().trim(), is( ")" ) );
   }
   
   
   
   @Test
   public void testAnd()
   {
      builder.and();
      assertThat( builder.toString().trim(), is( "and" ) );
   }
   
   
   
   @Test
   public void testOr()
   {
      builder.or();
      assertThat( builder.toString().trim(), is( "or" ) );
   }
   
   
   
   @Test
   public void testNot()
   {
      builder.not();
      assertThat( builder.toString().trim(), is( "not" ) );
   }
   
   
   
   @Test
   public void testNow()
   {
      builder.now();
      assertThat( builder.toString().trim(), is( "now" ) );
   }
   
   
   
   @Test
   public void testToday()
   {
      builder.today();
      assertThat( builder.toString().trim(), is( "today" ) );
   }
   
   
   
   @Test
   public void testTomorrow()
   {
      builder.tomorrow();
      assertThat( builder.toString().trim(), is( "tom" ) );
   }
   
   
   
   @Test
   public void testLength()
   {
      builder.list( "List" );
      assertThat( builder.length(), is( builder.toString().length() ) );
   }
   
   
   
   @Test
   public void testToSmartFilter()
   {
      builder.list( "List" );
      final RtmSmartFilter filter = builder.toSmartFilter();
      
      assertThat( filter.getFilterString().trim(), is( "list:\"List\"" ) );
   }
   
}
