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

package dev.drsoran.moloko.test.comp.content.db.sync;

import static dev.drsoran.rtm.test.IterableAsserts.assertEqualSet;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import android.database.sqlite.SQLiteException;
import dev.drsoran.moloko.content.db.TableColumns.ModificationColumns;
import dev.drsoran.moloko.content.db.TableNames;
import dev.drsoran.moloko.content.db.sync.DbModificationsProvider;
import dev.drsoran.moloko.content.db.sync.SyncModification;
import dev.drsoran.moloko.test.MolokoSyncTestCase;
import dev.drsoran.moloko.test.SQLiteScript;
import dev.drsoran.rtm.content.ContentProperties;
import dev.drsoran.rtm.sync.IModification;


public class DbModificationsProviderTest extends MolokoSyncTestCase
{
   @Rule
   public SQLiteScript sqliteScriptTask = new SQLiteScript( DbModificationsProviderTest.class,
                                                            "DbModificationsProviderTest_Task.sql" );
   
   @Rule
   public SQLiteScript sqliteScriptLists = new SQLiteScript( DbModificationsProviderTest.class,
                                                             "DbModificationsProviderTest_List.sql" );
   
   private DbModificationsProvider provider;
   
   private final static Comparator< IModification > MOD_CMP = new Comparator< IModification >()
   {
      @Override
      public int compare( IModification o1, IModification o2 )
      {
         if ( o1 == o2 )
         {
            return 0;
         }
         
         int res = o1.getPropertyName().compareTo( o2.getPropertyName() );
         if ( res == 0 )
         {
            res = o1.getValue().compareTo( o2.getValue() );
         }
         
         if ( res == 0 )
         {
            res = o1.getSyncedValue().compareTo( o2.getSyncedValue() );
         }
         
         return res;
      }
   };
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      provider = new DbModificationsProvider( getDb() );
   }
   
   
   
   @Test
   public void testGetModificationsOfRtmTask()
   {
      prepareDatabase( sqliteScriptTask.getSqlStatements() );
      
      final List< IModification > modificationsOfRtmTask = provider.getModificationsOfRtmTask( "1" );
      
      assertEqualSet( Arrays.asList( new SyncModification( ContentProperties.RtmTaskProperties.NAME,
                                                           "Task1",
                                                           "Task" ) ),
                      modificationsOfRtmTask,
                      MOD_CMP );
   }
   
   
   
   @Test( expected = SQLiteException.class )
   public void testGetModificationsOfRtmTask_TaskIdNotFound()
   {
      prepareDatabase( sqliteScriptTask.getSqlStatements() );
      provider.getModificationsOfRtmTask( "10" );
   }
   
   
   
   @Test
   public void testGetModificationsOfRtmTasksList()
   {
      prepareDatabase( sqliteScriptLists.getSqlStatements() );
      
      final List< IModification > modificationsOfRtmTask = provider.getModificationsOfRtmTasksList( "1" );
      
      assertEqualSet( Arrays.asList( new SyncModification( ContentProperties.RtmTaskProperties.NAME,
                                                           "List1",
                                                           "List" ) ),
                      modificationsOfRtmTask,
                      MOD_CMP );
   }
   
   
   
   @Test( expected = SQLiteException.class )
   public void testGetModificationsOfRtmTasksList_ListIdNotFound()
   {
      prepareDatabase( sqliteScriptLists.getSqlStatements() );
      provider.getModificationsOfRtmTasksList( "10" );
   }
   
   
   
   @Test
   public void testClearModifications()
   {
      prepareDatabase( sqliteScriptLists.getSqlStatements() );
      
      c = getDb().getTable( TableNames.MODIFICATIONS_TABLE )
                 .query( ModificationColumns.PROJECTION, null, null, null );
      
      assertThat( c.getCount(), greaterThan( 0 ) );
      
      provider.clearModifications();
      
      c.close();
      c = getDb().getTable( TableNames.MODIFICATIONS_TABLE )
                 .query( ModificationColumns.PROJECTION, null, null, null );
      
      assertThat( c.getCount(), is( 0 ) );
   }
}
