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

import org.junit.After;
import org.junit.Before;

import dev.drsoran.moloko.content.db.DbContentProvider;
import dev.drsoran.moloko.content.db.RtmDatabase;


public abstract class MolokoDbContentTestCase extends MolokoDbTestCase
{
   private DbContentProvider contentProvider;
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      
      contentProvider = createContentProvider( getDb() );
      
      assertTrue( contentProvider.onCreate() );
      
      contentProvider.init( createLog(), TestCalendarProvider.get() );
   }
   
   
   
   @Override
   @After
   public void tearDown() throws Exception
   {
      if ( contentProvider != null )
      {
         contentProvider.shutdown();
      }
      
      super.tearDown();
   }
   
   
   
   public DbContentProvider getContentProvider()
   {
      return contentProvider;
   }
   
   
   
   protected DbContentProvider createContentProvider( RtmDatabase database )
   {
      return new DbContentProvider( database );
   }
}
