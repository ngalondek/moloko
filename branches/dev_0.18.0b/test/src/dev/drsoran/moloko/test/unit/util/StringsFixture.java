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

package dev.drsoran.moloko.test.unit.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.moloko.test.unit.PrivateCtorCaller;
import dev.drsoran.moloko.util.Strings;


public class StringsFixture extends MolokoTestCase
{
   @Test( expected = AssertionError.class )
   public void testPrivateCtor() throws Throwable
   {
      PrivateCtorCaller.callPrivateCtor( Strings.class );
   }
   
   
   
   @Test
   public void testEmptyString()
   {
      assertThat( Strings.EMPTY_STRING, is( "" ) );
   }
   
   
   
   @Test
   public void testIsQuotified()
   {
      assertFalse( Strings.isQuotified( "test" ) );
      assertTrue( Strings.isQuotified( "\"test\"" ) );
      assertFalse( Strings.isQuotified( "\"test" ) );
      assertFalse( Strings.isQuotified( "test\"" ) );
      assertFalse( Strings.isQuotified( "te\"st" ) );
      assertFalse( Strings.isQuotified( "" ) );
      assertTrue( Strings.isQuotified( "\"\"" ) );
      assertTrue( Strings.isQuotified( "\"longer test\"" ) );
      assertFalse( Strings.isQuotified( "\"longer \"test\"\"" ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testIsQuotifiedNull()
   {
      Strings.isQuotified( null );
   }
   
   
   
   @Test
   public void testUnquotify()
   {
      assertThat( Strings.unquotify( "test" ), is( "test" ) );
      assertThat( Strings.unquotify( "\"test\"" ), is( "test" ) );
      assertThat( Strings.unquotify( "\"test" ), is( "test" ) );
      assertThat( Strings.unquotify( "test\"" ), is( "test" ) );
      assertThat( Strings.unquotify( "te\"st" ), is( "test" ) );
      assertThat( Strings.unquotify( "" ), is( "" ) );
      assertThat( Strings.unquotify( "\"\"" ), is( "" ) );
      assertThat( Strings.unquotify( "\"longer test\"" ), is( "longer test" ) );
      assertThat( Strings.unquotify( "\"longer \"test\"\"" ),
                  is( "longer test" ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testUnquotifyNull()
   {
      Strings.unquotify( null );
   }
   
   
   
   @Test
   public void testQuotify()
   {
      assertThat( Strings.quotify( "test" ), is( "\"test\"" ) );
      assertThat( Strings.quotify( "\"test\"" ), is( "\"\"test\"\"" ) );
      assertThat( Strings.quotify( "" ), is( "\"\"" ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testQuotifyNull()
   {
      Strings.quotify( null );
   }
   
   
   
   @Test
   public void testEmptyIfNullString()
   {
      assertThat( Strings.emptyIfNull( null ), is( "" ) );
      assertThat( Strings.emptyIfNull( "test" ), is( "test" ) );
   }
   
   
   
   @Test
   public void testEmptyIfNullCharSequence()
   {
      assertThat( Strings.emptyIfNull( (CharSequence) null ),
                  is( (CharSequence) "" ) );
      assertThat( Strings.emptyIfNull( (CharSequence) "test" ),
                  is( (CharSequence) "test" ) );
   }
   
   
   
   @Test
   public void testNullIfEmptyString()
   {
      assertThat( Strings.nullIfEmpty( "" ), is( (String) null ) );
      assertThat( Strings.nullIfEmpty( "test" ), is( "test" ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNullIfEmptyStringNull()
   {
      Strings.nullIfEmpty( (String) null );
   }
   
   
   
   @Test
   public void testNullIfEmptyCharSequence()
   {
      assertThat( Strings.nullIfEmpty( (CharSequence) "" ),
                  is( (CharSequence) null ) );
      assertThat( Strings.nullIfEmpty( (CharSequence) "test" ),
                  is( (CharSequence) "test" ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNullIfEmptyCharSequenceNull()
   {
      Strings.nullIfEmpty( (CharSequence) null );
   }
   
   
   
   @Test
   public void testEqualsNullAwareStringString()
   {
      final String t1 = new String( "test" );
      final String t2 = new String( "test" );
      
      assertTrue( Strings.equalsNullAware( t1, t2 ) );
      assertFalse( Strings.equalsNullAware( "test", "test1" ) );
      assertFalse( Strings.equalsNullAware( null, "test" ) );
      assertFalse( Strings.equalsNullAware( "test", null ) );
      assertTrue( Strings.equalsNullAware( null, null ) );
   }
   
   
   
   @Test
   public void testIsNullOrEmpty()
   {
      assertFalse( Strings.isNullOrEmpty( "test" ) );
      assertTrue( Strings.isNullOrEmpty( null ) );
      assertTrue( Strings.isNullOrEmpty( "" ) );
   }
   
   
   
   @Test
   public void testConvertToNull()
   {
      assertNull( Strings.convertTo( null, String.class ) );
      
      assertNull( Strings.convertTo( null, Long.class ) );
      assertNull( Strings.convertTo( null, Integer.class ) );
      assertNull( Strings.convertTo( null, Boolean.class ) );
      assertNull( Strings.convertTo( null, Float.class ) );
      assertNull( Strings.convertTo( null, Double.class ) );
      
      assertNull( Strings.convertTo( null, long.class ) );
      assertNull( Strings.convertTo( null, int.class ) );
      assertNull( Strings.convertTo( null, boolean.class ) );
      assertNull( Strings.convertTo( null, float.class ) );
      assertNull( Strings.convertTo( null, double.class ) );
   }
   
   
   
   @Test
   public void testConvertTo()
   {
      assertThat( Strings.convertTo( "abc", String.class ), is( "abc" ) );
      
      assertThat( Strings.convertTo( "1", Long.class ), is( Long.valueOf( 1 ) ) );
      assertThat( Strings.convertTo( "1", long.class ), is( 1L ) );
      
      assertThat( Strings.convertTo( "2", Integer.class ),
                  is( Integer.valueOf( 2 ) ) );
      assertThat( Strings.convertTo( "2", int.class ), is( 2 ) );
      
      assertThat( Strings.convertTo( "true", Boolean.class ), is( Boolean.TRUE ) );
      assertTrue( Strings.convertTo( "true", boolean.class ) );
      assertThat( Strings.convertTo( "false", Boolean.class ),
                  is( Boolean.FALSE ) );
      assertFalse( Strings.convertTo( "false", boolean.class ) );
      
      assertThat( Strings.convertTo( "3.14f", Float.class ),
                  is( Float.valueOf( 3.14f ) ) );
      assertThat( Strings.convertTo( "3.14f", float.class ), is( 3.14f ) );
      assertThat( Strings.convertTo( "3.14", Float.class ),
                  is( Float.valueOf( 3.14f ) ) );
      assertThat( Strings.convertTo( "3.14", float.class ), is( 3.14f ) );
      
      assertThat( Strings.convertTo( "4.15", Double.class ),
                  is( Double.valueOf( 4.15 ) ) );
      assertThat( Strings.convertTo( "4.15", double.class ), is( 4.15 ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testConvertToUnsupüported()
   {
      Strings.convertTo( "0", BigDecimal.class );
   }
   
   
   
   @Test
   public void testConvertFromNull()
   {
      assertNull( Strings.convertFrom( (String) null ) );
      assertNull( Strings.convertFrom( (Long) null ) );
      assertNull( Strings.convertFrom( (Integer) null ) );
      assertNull( Strings.convertFrom( (Boolean) null ) );
      assertNull( Strings.convertFrom( (Float) null ) );
      assertNull( Strings.convertFrom( (Double) null ) );
   }
   
   
   
   @Test
   public void testConvertFrom()
   {
      assertThat( Strings.convertFrom( "abc" ), is( "abc" ) );
      assertThat( Strings.convertFrom( 1L ), is( Long.toString( 1 ) ) );
      assertThat( Strings.convertFrom( 2 ), is( Integer.toString( 2 ) ) );
      assertThat( Strings.convertFrom( true ), is( Boolean.toString( true ) ) );
      assertThat( Strings.convertFrom( false ), is( Boolean.toString( false ) ) );
      assertThat( Strings.convertFrom( 3.14f ), is( Float.toString( 3.14f ) ) );
      assertThat( Strings.convertFrom( 4.15 ), is( Double.toString( 4.15 ) ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testConvertFromUnsupported()
   {
      Strings.convertFrom( new BigDecimal( 0 ) );
   }
}
