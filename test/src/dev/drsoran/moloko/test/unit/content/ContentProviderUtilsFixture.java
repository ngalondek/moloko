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

package dev.drsoran.moloko.test.unit.content;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Test;

import dev.drsoran.moloko.content.ContentProviderUtils;
import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.moloko.test.PrivateCtorCaller;


public class ContentProviderUtilsFixture extends MolokoTestCase
{
   @Test( expected = AssertionError.class )
   public void testPrivateCtor() throws Throwable
   {
      PrivateCtorCaller.callPrivateCtor( ContentProviderUtils.class );
   }
   
   
   
   @Test
   public void testBindAll()
   {
      assertThat( ContentProviderUtils.bindAll( "WHERE _id=?", "12345" ),
                  is( "WHERE _id=12345" ) );
      
      assertThat( ContentProviderUtils.bindAll( "WHERE _id=? AND name='?'",
                                                "12345",
                                                "name" ),
                  is( "WHERE _id=12345 AND name='name'" ) );
      
      assertThat( ContentProviderUtils.bindAll( "WHERE _id=? AND name='?' AND date=?",
                                                "12345",
                                                "name" ),
                  is( "WHERE _id=12345 AND name='name' AND date=?" ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testBindAllNullSelection()
   {
      ContentProviderUtils.bindAll( null, "abc" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testBindAllNullArg1()
   {
      ContentProviderUtils.bindAll( "selection", (String) null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testBindAllNullArg2()
   {
      ContentProviderUtils.bindAll( "selection", "arg1", (String) null );
   }
}
