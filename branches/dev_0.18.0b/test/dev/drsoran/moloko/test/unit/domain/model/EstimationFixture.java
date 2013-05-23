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

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Test;

import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.domain.model.Estimation;
import dev.drsoran.moloko.test.unit.EqualsHashCodeTestCase;


public class EstimationFixture extends EqualsHashCodeTestCase
{
   private final static long NOW = System.currentTimeMillis();
   
   private final static long NEVER = Constants.NO_TIME;
   
   private final static long LATER = NOW + 3600 * 1000;
   
   
   
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
   
   
   
   @Test( expected = IllegalArgumentException.class )
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
      assertThat( new Estimation( "sentence", NOW ).getMillisUtc(), is( NOW ) );
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
