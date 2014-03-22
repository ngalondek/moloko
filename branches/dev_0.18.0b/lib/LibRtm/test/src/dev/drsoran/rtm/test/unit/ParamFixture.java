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

package dev.drsoran.rtm.test.unit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import dev.drsoran.rtm.Param;
import dev.drsoran.rtm.service.RtmDateFormatter;
import dev.drsoran.rtm.test.EqualsHashCodeTestCase;
import dev.drsoran.rtm.test.TestConstants;


public class ParamFixture extends EqualsHashCodeTestCase
{
   @Test
   public void testParamStringString()
   {
      new Param( "key", "value" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testParamStringString_Nullkey()
   {
      new Param( null, "value" );
   }
   
   
   
   @Test
   public void testParamStringLong()
   {
      new Param( "key", 1L );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testParamStringString_Nullkey1()
   {
      new Param( null, 1L );
   }
   
   
   
   @Test
   public void testGetName()
   {
      assertThat( new Param( "name", 1L ).getName(), is( "name" ) );
   }
   
   
   
   @Test
   public void testGetValue()
   {
      assertThat( new Param( "name", TestConstants.NOW ).getValue(),
                  is( RtmDateFormatter.formatDate( TestConstants.DATE_NOW.getTime() ) ) );
      assertThat( new Param( "name", "value" ).getValue(), is( "value" ) );
   }
   
   
   
   @Test
   public void testCompareTo()
   {
      assertThat( new Param( "name", 1L ).compareTo( new Param( "name", 1L ) ),
                  is( 0 ) );
      assertThat( new Param( "name", 1L ).compareTo( new Param( "name", 2L ) ),
                  is( 0 ) );
      assertThat( new Param( "name1", 1L ).compareTo( new Param( "name", 2L ) ),
                  is( 1 ) );
      assertThat( new Param( "name", 1L ).compareTo( new Param( "name1", 2L ) ),
                  is( -1 ) );
   }
   
   
   
   @Test
   public void testToString()
   {
      new Param( "name", TestConstants.NOW ).toString();
   }
   
   
   
   @Override
   protected Object createEqualEqualsHashTestInstance() throws Exception
   {
      return new Param( "1", "3" );
   }
   
   
   
   @Override
   protected Object createNotEqualEqualsHashTestInstance() throws Exception
   {
      return new Param( "2", "3" );
   }
   
}
