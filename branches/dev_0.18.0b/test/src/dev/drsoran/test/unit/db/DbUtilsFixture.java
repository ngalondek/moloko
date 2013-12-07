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

package dev.drsoran.test.unit.db;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import android.database.sqlite.SQLiteDatabase;
import dev.drsoran.db.DbUtils;
import dev.drsoran.moloko.test.MolokoDbContentTestCase;
import dev.drsoran.moloko.test.PrivateCtorCaller;
import dev.drsoran.moloko.test.shadows.SQLiteDatabaseTransactionShadow;


@RunWith( DbUtilsFixtureRunner.class )
public class DbUtilsFixture extends MolokoDbContentTestCase
{
   @Test( expected = AssertionError.class )
   public void testPrivatenCtor() throws Throwable
   {
      PrivateCtorCaller.callPrivateCtor( DbUtils.class );
   }
   
   
   
   @Test
   public void testDoTransactional()
   {
      final Runnable runnableMock = EasyMock.createStrictMock( Runnable.class );
      runnableMock.run();
      
      EasyMock.replay( runnableMock );
      
      DbUtils.doTransactional( getDb().getWritable(), runnableMock );
      
      SQLiteDatabaseTransactionShadow shadow = Robolectric.shadowOf_( getDb().getWritable() );
      assertThat( shadow.beginCalledCount, is( 1 ) );
      assertThat( shadow.successfullCalledCount, is( 1 ) );
      assertThat( shadow.endCalledCount, is( 1 ) );
      
      EasyMock.verify( runnableMock );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testDoTransactionalNullSql()
   {
      DbUtils.doTransactional( null, EasyMock.createNiceMock( Runnable.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testDoTransactionalNullRunnable()
   {
      DbUtils.doTransactional( EasyMock.createNiceMock( SQLiteDatabase.class ),
                               null );
   }
   
   
   
   @Test( expected = UnsupportedOperationException.class )
   public void testDoTransactionalWithException()
   {
      DbUtils.doTransactional( getDb().getWritable(), new Runnable()
      {
         @Override
         public void run()
         {
            throw new UnsupportedOperationException();
         }
      } );
      
      SQLiteDatabaseTransactionShadow shadow = Robolectric.shadowOf_( getDb().getWritable() );
      assertThat( shadow.beginCalledCount, is( 1 ) );
      assertThat( shadow.successfullCalledCount, is( 0 ) );
      assertThat( shadow.endCalledCount, is( 1 ) );
   }
}
