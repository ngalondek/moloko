/* 
 *	Copyright (c) 2014 Ronny Röhricht
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

package dev.drsoran.moloko.test.unit.content.db.sync;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;
import org.robolectric.annotation.Config;

import android.database.Cursor;
import dev.drsoran.db.ITable;
import dev.drsoran.moloko.content.Columns.TaskColumns;
import dev.drsoran.moloko.content.Columns.TasksListColumns;
import dev.drsoran.moloko.content.db.RtmDatabase;
import dev.drsoran.moloko.content.db.TableColumns.ModificationColumns;
import dev.drsoran.moloko.content.db.TableNames;
import dev.drsoran.moloko.content.db.sync.DbModificationsProvider;
import dev.drsoran.moloko.test.MolokoRoboTestCase;
import dev.drsoran.rtm.Pair;
import dev.drsoran.rtm.content.ContentProperties;
import dev.drsoran.rtm.sync.IModification;


@SuppressWarnings( "rawtypes" )
@Config( manifest = Config.NONE )
public class DbModificationsProviderFixture extends MolokoRoboTestCase
{
   public final static Pair[] taskMappingTestData = new Pair[]
   {
    Pair.create( TaskColumns.TASK_CREATED_DATE,
                 ContentProperties.RtmTaskProperties.CREATED_DATE ),
    Pair.create( TaskColumns.TASK_MODIFIED_DATE,
                 ContentProperties.RtmTaskProperties.MODIFIED_DATE ),
    Pair.create( TaskColumns.TASK_NAME,
                 ContentProperties.RtmTaskProperties.NAME ),
    Pair.create( TaskColumns.RECURRENCE_EVERY,
                 ContentProperties.RtmTaskProperties.RECURRENCE_EVERY ) };
   
   public final static Pair[] tasksListMappingTestData = new Pair[]
   {
    Pair.create( TasksListColumns.LIST_NAME,
                 ContentProperties.RtmTasksListProperties.NAME ),
    Pair.create( TasksListColumns.LIST_DELETED_DATE,
                 ContentProperties.RtmTasksListProperties.DELETED ) };
   
   
   
   @Test
   public void testTaskMappings()
   {
      for ( Pair fromTo : taskMappingTestData )
      {
         final String from = (String) fromTo.first;
         final String to = (String) fromTo.second;
         
         final RtmDatabase database = getDatabase( from );
         
         final DbModificationsProvider provider = new DbModificationsProvider( database );
         final List< IModification > modificationsOfRtmTask = provider.getModificationsOfRtmTask( "1" );
         
         assertThat( modificationsOfRtmTask.get( 0 ).getPropertyName(), is( to ) );
      }
   }
   
   
   
   @Test
   public void testTasksListMappings()
   {
      for ( Pair fromTo : taskMappingTestData )
      {
         final String from = (String) fromTo.first;
         final String to = (String) fromTo.second;
         
         final RtmDatabase database = getDatabase( from );
         
         final DbModificationsProvider provider = new DbModificationsProvider( database );
         final List< IModification > modificationsOfRtmTask = provider.getModificationsOfRtmTask( "1" );
         
         assertThat( modificationsOfRtmTask.get( 0 ).getPropertyName(), is( to ) );
      }
   }
   
   
   
   private RtmDatabase getDatabase( final String from )
   {
      final Cursor c = EasyMock.createNiceMock( Cursor.class );
      EasyMock.expect( c.moveToNext() ).andReturn( true ).once();
      EasyMock.expect( c.getString( ModificationColumns.PROPERTY_IDX ) )
              .andReturn( from );
      EasyMock.replay( c );
      
      final ITable modificationsTable = EasyMock.createNiceMock( ITable.class );
      EasyMock.expect( modificationsTable.query( EasyMock.anyObject( String[].class ),
                                                 EasyMock.anyObject( String.class ),
                                                 EasyMock.anyObject( String[].class ),
                                                 EasyMock.anyObject( String.class ) ) )
              .andReturn( c );
      EasyMock.replay( modificationsTable );
      
      final Cursor idCursor = EasyMock.createNiceMock( Cursor.class );
      EasyMock.expect( idCursor.moveToNext() ).andReturn( true );
      EasyMock.expect( idCursor.getLong( EasyMock.anyInt() ) ).andReturn( 1L );
      EasyMock.replay( idCursor );
      
      final ITable idTable = EasyMock.createNiceMock( ITable.class );
      EasyMock.expect( idTable.query( EasyMock.anyObject( String[].class ),
                                      EasyMock.anyObject( String.class ),
                                      EasyMock.anyObject( String[].class ),
                                      EasyMock.anyObject( String.class ) ) )
              .andReturn( idCursor );
      EasyMock.replay( idTable );
      
      final RtmDatabase database = EasyMock.createNiceMock( RtmDatabase.class );
      EasyMock.expect( database.getTable( TableNames.MODIFICATIONS_TABLE ) )
              .andReturn( modificationsTable );
      EasyMock.expect( database.getTable( TableNames.RTM_RAW_TASKS_TABLE ) )
              .andReturn( idTable );
      EasyMock.expect( database.getTable( TableNames.RTM_TASKS_LIST_TABLE ) )
              .andReturn( idTable );
      EasyMock.replay( database );
      return database;
   }
}
