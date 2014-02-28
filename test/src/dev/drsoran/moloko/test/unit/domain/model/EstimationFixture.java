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
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import dev.drsoran.moloko.domain.model.Estimation;
import dev.drsoran.moloko.test.EqualsHashCodeTestCase;


public class EstimationFixture extends EqualsHashCodeTestCase
{
   
   @Test
   public void testEstimation()
   {
      new Estimation( "sentence", NOW );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testEstimationNullSentence()
   {
      new Estimation( null, NOW );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testEstimationEmptySentence()
   {
      new Estimation( "", NOW );
   }
   
   
   
   @Test
   public void testEstimationNoTime()
   {
      new Estimation( "sentence", NEVER );
   }
   
   
   
   @Test
   public void testGetSentence()
   {
      assertThat( new Estimation( "sentence", NOW ).getSentence(),
                  is( "sentence" ) );
   }
   
   
   
   @Test
   public void testGetMillisUtc()
   {
      assertThat( new Estimation( "sentence", NOW ).getMillis(), is( NOW ) );
   }
   
   
   
   @Test
   public void testToString()
   {
      new Estimation( "sentence", NOW ).toString();
   }
   
   
   
   @Override
   protected Object createEqualEqualsHashTestInstance() throws Exception
   {
      return new Estimation( "sentence", NOW );
   }
   
   
   
   @Override
   protected Object createNotEqualEqualsHashTestInstance() throws Exception
   {
      return new Estimation( "otherSentence", LATER );
   }
}
