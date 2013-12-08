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

package dev.drsoran.moloko.test;

import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.content.db.DbContentProvider;
import dev.drsoran.moloko.content.db.RtmDatabase;


@RunWith( SQLiteTestRunner.class )
public abstract class MolokoDbContentTestCase extends MolokoRoboTestCase
{
   private Context context;
   
   private RtmDatabase database;
   
   private DbContentProvider contentProvider;
   
   protected Cursor c;
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      
      context = Robolectric.buildActivity( Activity.class ).get();
      
      database = createDatabase( context );
      contentProvider = createContentProvider( database );
      
      assertTrue( contentProvider.onCreate() );
   }
   
   
   
   @Override
   @After
   public void tearDown() throws Exception
   {
      if ( c != null )
      {
         c.close();
         c = null;
      }
      
      if ( contentProvider != null )
      {
         contentProvider.shutdown();
      }
      
      super.tearDown();
   }
   
   
   
   public RtmDatabase getDb()
   {
      return database;
   }
   
   
   
   public DbContentProvider getContentProvider()
   {
      return contentProvider;
   }
   
   
   
   public void prepareDatabase( Iterable< String > sqlStatements ) throws SQLException
   {
      final SQLiteDatabase db = getDb().getWritable();
      
      for ( String statement : sqlStatements )
      {
         db.execSQL( statement );
      }
   }
   
   
   
   protected ILog createLog()
   {
      return EasyMock.createNiceMock( ILog.class );
   }
   
   
   
   protected RtmDatabase createDatabase( Context context )
   {
      return new RtmDatabase( context );
   }
   
   
   
   protected DbContentProvider createContentProvider( RtmDatabase database )
   {
      return new DbContentProvider( database );
   }
}
