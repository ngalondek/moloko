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

package dev.drsoran.rtm.test.unit.rest;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.TimeZone;

import org.easymock.EasyMock;
import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import dev.drsoran.rtm.Strings;
import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.rest.XmlAttr;
import dev.drsoran.rtm.test.PrivateCtorCaller;


public class XmlAttrFixture
{
   @Test( expected = AssertionError.class )
   public void privateCtor() throws Throwable
   {
      PrivateCtorCaller.callPrivateCtor( XmlAttr.class );
   }
   
   
   
   @Test
   public void testGetOptMillisUtc() throws SAXException
   {
      final Calendar cal = Calendar.getInstance( TimeZone.getTimeZone( "GMT+0" ) );
      cal.set( Calendar.YEAR, 2006 );
      cal.set( Calendar.MONTH, Calendar.MAY );
      cal.set( Calendar.DATE, 7 );
      cal.set( Calendar.HOUR_OF_DAY, 10 );
      cal.set( Calendar.MINUTE, 19 );
      cal.set( Calendar.SECOND, 54 );
      cal.set( Calendar.MILLISECOND, 0 );
      
      assertThat( XmlAttr.getOptMillisUtc( getAttrs( "millis",
                                                     "2006-05-07T10:19:54Z" ),
                                           "millis" ),
                  is( cal.getTimeInMillis() ) );
      
      assertThat( XmlAttr.getOptMillisUtc( getAttrs( "millis", null ), "millis" ),
                  is( RtmConstants.NO_TIME ) );
   }
   
   
   
   @Test( expected = SAXException.class )
   public void testGetOptMillisUtcException() throws SAXException
   {
      XmlAttr.getOptMillisUtc( getAttrs( "millis", "2006-05-07" ), "millis" );
   }
   
   
   
   @Test
   public void testGetStringNotNull() throws SAXException
   {
      assertThat( XmlAttr.getStringNotNull( getAttrs( "string", "str" ),
                                            "string" ), is( "str" ) );
   }
   
   
   
   @Test( expected = SAXException.class )
   public void testGetStringNotNull_Null() throws SAXException
   {
      XmlAttr.getStringNotNull( getAttrs( "string", null ), "string" );
   }
   
   
   
   @Test
   public void testGetOptString() throws SAXException
   {
      assertThat( XmlAttr.getOptString( getAttrs( "string", "str" ),
                                        "string",
                                        null ), is( "str" ) );
      assertThat( XmlAttr.getOptString( getAttrs( "string", null ),
                                        "string",
                                        "???" ), is( "???" ) );
      assertThat( XmlAttr.getOptString( getAttrs( "string",
                                                  Strings.EMPTY_STRING ),
                                        "string",
                                        "???" ), is( "???" ) );
   }
   
   
   
   @Test
   public void testGetInt() throws SAXException
   {
      assertThat( XmlAttr.getInt( getAttrs( "int", "1" ), "int" ), is( 1 ) );
   }
   
   
   
   @Test( expected = SAXException.class )
   public void testGetIntException() throws SAXException
   {
      XmlAttr.getInt( getAttrs( "int", "w" ), "int" );
   }
   
   
   
   @Test
   public void testGetFloat() throws SAXException
   {
      assertThat( XmlAttr.getFloat( getAttrs( "float", "1.0" ), "float" ),
                  is( 1.0f ) );
   }
   
   
   
   @Test( expected = SAXException.class )
   public void testGetFloatException() throws SAXException
   {
      XmlAttr.getInt( getAttrs( "float", "w" ), "float" );
   }
   
   
   
   @Test
   public void testGetBoolean() throws SAXException
   {
      assertThat( XmlAttr.getBoolean( getAttrs( "bool", "0" ), "bool" ),
                  is( false ) );
      assertThat( XmlAttr.getBoolean( getAttrs( "bool", "1" ), "bool" ),
                  is( true ) );
   }
   
   
   
   @Test( expected = SAXException.class )
   public void testGetBooleanException() throws SAXException
   {
      XmlAttr.getBoolean( getAttrs( "bool", "w" ), "bool" );
   }
   
   
   
   private Attributes getAttrs( String key, String value )
   {
      final Attributes attrs = EasyMock.createNiceMock( Attributes.class );
      EasyMock.expect( attrs.getValue( key ) ).andReturn( value ).anyTimes();
      EasyMock.replay( attrs );
      
      return attrs;
   }
}
