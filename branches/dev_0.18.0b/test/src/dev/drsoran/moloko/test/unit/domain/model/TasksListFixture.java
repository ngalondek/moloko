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

package dev.drsoran.moloko.test.unit.domain.model;

import static dev.drsoran.moloko.test.TestConstants.LATER;
import static dev.drsoran.moloko.test.TestConstants.NEVER;
import static dev.drsoran.moloko.test.TestConstants.NOW;
import static dev.drsoran.moloko.test.TestConstants.NO_ID;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dev.drsoran.moloko.domain.model.ExtendedTaskCount;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.moloko.test.MolokoTestCase;


public class TasksListFixture extends MolokoTestCase
{
   @Test
   public void testTasksListNoId()
   {
      new TasksList( NO_ID, NOW, "list", 0, false, false );
   }
   
   
   
   @Test
   public void testTasksList()
   {
      new TasksList( 1, NOW, "list", 0, false, false );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasksListNoCreatedMillis()
   {
      new TasksList( 1, NEVER, "list", 0, false, false );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasksListNullName()
   {
      new TasksList( 1, NEVER, null, 0, false, false );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasksListEmptyName()
   {
      new TasksList( 1, NEVER, "", 0, false, false );
   }
   
   
   
   @Test
   public void testGetId()
   {
      assertThat( new TasksList( 1, NOW, "list", 0, false, false ).getId(),
                  is( 1L ) );
   }
   
   
   
   @Test
   public void testGetName()
   {
      assertThat( new TasksList( NO_ID, NOW, "list", 0, false, false ).getName(),
                  is( "list" ) );
   }
   
   
   
   @Test
   public void testSetName()
   {
      final TasksList list = new TasksList( NO_ID, NOW, "list", 0, false, false );
      list.setName( "otherList" );
      assertThat( list.getName(), is( "otherList" ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testSetNameNull()
   {
      new TasksList( NO_ID, NOW, "list", 0, false, false ).setName( null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testSetNameEmpty()
   {
      new TasksList( NO_ID, NOW, "list", 0, false, false ).setName( "" );
   }
   
   
   
   @Test
   public void testGetPosition()
   {
      assertThat( new TasksList( NO_ID, NOW, "list", 0, false, false ).getPosition(),
                  is( 0 ) );
      assertThat( new TasksList( NO_ID, NOW, "list", -1, false, false ).getPosition(),
                  is( -1 ) );
      assertThat( new TasksList( NO_ID, NOW, "list", 1, false, false ).getPosition(),
                  is( 1 ) );
   }
   
   
   
   @Test
   public void testIsLocked()
   {
      assertFalse( new TasksList( NO_ID, NOW, "list", 0, false, false ).isLocked() );
      assertTrue( new TasksList( NO_ID, NOW, "list", 0, true, false ).isLocked() );
   }
   
   
   
   @Test
   public void testIsArchived()
   {
      assertFalse( new TasksList( NO_ID, NOW, "list", 0, true, false ).isArchived() );
      assertTrue( new TasksList( NO_ID, NOW, "list", 0, true, true ).isArchived() );
   }
   
   
   
   @Test
   public void testHasTaskCount()
   {
      final TasksList list = new TasksList( NO_ID, NOW, "list", 0, false, false );
      assertFalse( list.hasTaskCount() );
      
      list.setTasksCount( new ExtendedTaskCount( 0, 0, 0, 0, 0, 0 ) );
      assertTrue( list.hasTaskCount() );
   }
   
   
   
   @Test
   public void testGetTasksCount()
   {
      final TasksList list = new TasksList( NO_ID, NOW, "list", 0, false, false );
      assertNull( list.getTasksCount() );
      
      list.setTasksCount( new ExtendedTaskCount( 0, 0, 0, 0, 0, 0 ) );
      assertNotNull( list.getTasksCount() );
   }
   
   
   
   @Test
   public void testSetTasksCount()
   {
      final TasksList list = new TasksList( NO_ID, NOW, "list", 0, false, false );
      list.setTasksCount( null );
      assertNull( list.getTasksCount() );
      
      list.setTasksCount( new ExtendedTaskCount( 0, 0, 0, 0, 0, 0 ) );
      assertNotNull( list.getTasksCount() );
      
      list.setTasksCount( null );
      assertNull( list.getTasksCount() );
   }
   
   
   
   @Test
   public void testIsSmartList()
   {
      final TasksList list = new TasksList( NO_ID, NOW, "list", 0, false, false );
      assertFalse( list.isSmartList() );
      
      list.setSmartFilter( new RtmSmartFilter( "name:list" ) );
      assertTrue( list.isSmartList() );
   }
   
   
   
   @Test
   public void testGetSmartFilter()
   {
      final TasksList list = new TasksList( NO_ID, NOW, "list", 0, false, false );
      assertNull( list.getSmartFilter() );
   }
   
   
   
   @Test
   public void testSetSmartFilter()
   {
      final TasksList list = new TasksList( NO_ID, NOW, "list", 0, false, false );
      list.setSmartFilter( new RtmSmartFilter( "name:list" ) );
      assertNotNull( list.getSmartFilter() );
   }
   
   
   
   @Test
   public void testGetCreatedMillisUtc()
   {
      assertThat( new TasksList( NO_ID, NOW, "list", 0, false, false ).getCreatedMillisUtc(),
                  is( NOW ) );
   }
   
   
   
   @Test
   public void testGetModifiedMillisUtc()
   {
      assertThat( new TasksList( NO_ID, NOW, "list", 0, false, false ).getModifiedMillisUtc(),
                  is( NEVER ) );
   }
   
   
   
   @Test
   public void testSetModifiedMillisUtc()
   {
      final TasksList list = new TasksList( NO_ID, NOW, "list", 0, false, false );
      list.setModifiedMillisUtc( LATER );
      
      assertThat( list.getModifiedMillisUtc(), is( LATER ) );
   }
   
   
   
   @Test
   public void testIsModified()
   {
      final TasksList list = new TasksList( NO_ID, NOW, "list", 0, false, false );
      assertFalse( list.isModified() );
      
      list.setModifiedMillisUtc( LATER );
      assertTrue( list.isModified() );
   }
   
   
   
   @Test
   public void testGetDeletedMillisUtc()
   {
      assertThat( new TasksList( NO_ID, NOW, "list", 0, false, false ).getDeletedMillisUtc(),
                  is( NEVER ) );
   }
   
   
   
   @Test
   public void testSetDeletedMillisUtc()
   {
      final TasksList list = new TasksList( NO_ID, NOW, "list", 0, false, false );
      list.setModifiedMillisUtc( LATER );
      
      assertThat( list.getModifiedMillisUtc(), is( LATER ) );
   }
   
   
   
   @Test
   public void testIsDeleted()
   {
      final TasksList list = new TasksList( NO_ID, NOW, "list", 0, false, false );
      assertFalse( list.isDeleted() );
      
      list.setDeletedMillisUtc( LATER );
      assertTrue( list.isDeleted() );
   }
   
   
   
   @Test
   public void testToString()
   {
      new TasksList( NO_ID, NOW, "list", 0, false, false ).toString();
   }
}
