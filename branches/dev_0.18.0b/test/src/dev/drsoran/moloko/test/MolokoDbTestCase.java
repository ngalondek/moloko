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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import dev.drsoran.moloko.content.db.RtmDatabase;
import dev.drsoran.rtm.ILog;


@RunWith( SQLiteTestRunner.class )
@Config( manifest = Config.NONE )
public abstract class MolokoDbTestCase extends MolokoRoboTestCase
{
   private Context context;
   
   private RtmDatabase database;
   
   protected Cursor c;
   
   
   
   @Before
   public void setUp() throws Exception
   {
      context = Robolectric.buildActivity( Activity.class ).get();
      database = createDatabase( context );
   }
   
   
   
   @After
   public void tearDown() throws Exception
   {
      if ( c != null )
      {
         c.close();
         c = null;
      }
   }
   
   
   
   public RtmDatabase getDb()
   {
      return database;
   }
   
   
   
   public void openDb()
   {
      database.open();
   }
   
   
   
   public void prepareDatabase( Iterable< String > sqlStatements ) throws SQLException
   {
      final SQLiteDatabase db = getDb().getWritable();
      
      for ( String statement : sqlStatements )
      {
         db.execSQL( statement );
      }
   }
   
   
   
   public void saveDatabase( String path ) throws IOException
   {
      final String dbPath = database.getWritable().getPath();
      
      FileInputStream is = null;
      FileOutputStream os = null;
      try
      {
         is = new FileInputStream( dbPath );
         os = new FileOutputStream( path + "/" + RtmDatabase.DATABASE_NAME );
         
         final byte[] buffer = new byte[ is.available() ];
         is.read( buffer );
         os.write( buffer );
      }
      finally
      {
         if ( is != null )
         {
            is.close();
         }
         if ( os != null )
         {
            os.close();
         }
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
}
