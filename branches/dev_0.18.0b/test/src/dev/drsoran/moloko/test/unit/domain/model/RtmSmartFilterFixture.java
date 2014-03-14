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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;

import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.test.EqualsHashCodeTestCase;


public class RtmSmartFilterFixture extends EqualsHashCodeTestCase
{
   
   @Test
   public void testRtmSmartFilter()
   {
      new RtmSmartFilter( "filter" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmSmartFilterNullFilter()
   {
      new RtmSmartFilter( null );
   }
   
   
   
   @Test
   public void testRtmSmartFilterEmptyFilter()
   {
      new RtmSmartFilter( "" );
   }
   
   
   
   @Test
   public void testGetFilterStringNoOperator()
   {
      assertThat( new RtmSmartFilter( "filter" ).getFilterString(),
                  is( "name:\"filter\"" ) );
   }
   
   
   
   @Test
   public void testGetFilterStringNoOperator_EmptyFilter()
   {
      assertThat( new RtmSmartFilter( "" ).getFilterString(), is( "" ) );
   }
   
   
   
   @Test
   public void testGetFilterStringOperator()
   {
      assertThat( new RtmSmartFilter( "list:filter" ).getFilterString(),
                  is( "list:filter" ) );
   }
   
   
   
   @Test
   public void testToString()
   {
      new RtmSmartFilter( "filter" ).toString();
   }
   
   
   
   @Test
   public void testSerializeDeserialize() throws IOException,
                                         ClassNotFoundException
   {
      final ByteArrayOutputStream memStream = new ByteArrayOutputStream();
      final ObjectOutputStream oos = new ObjectOutputStream( memStream );
      
      final RtmSmartFilter filter = new RtmSmartFilter( "name:Test" );
      
      oos.writeObject( filter );
      oos.close();
      
      byte[] serialized = memStream.toByteArray();
      memStream.close();
      
      final ByteArrayInputStream memIStream = new ByteArrayInputStream( serialized );
      final ObjectInputStream ois = new ObjectInputStream( memIStream );
      
      final RtmSmartFilter deserialized = (RtmSmartFilter) ois.readObject();
      
      ois.close();
      memIStream.close();
      
      assertThat( deserialized, is( notNullValue() ) );
      assertThat( deserialized.getFilterString(), is( "name:Test" ) );
   }
   
   
   
   @Override
   protected Object createEqualEqualsHashTestInstance() throws Exception
   {
      return new RtmSmartFilter( "filter" );
   }
   
   
   
   @Override
   protected Object createNotEqualEqualsHashTestInstance() throws Exception
   {
      return new RtmSmartFilter( "filter1" );
   }
}
