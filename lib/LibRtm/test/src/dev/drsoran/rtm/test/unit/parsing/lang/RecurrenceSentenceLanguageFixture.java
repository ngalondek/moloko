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

package dev.drsoran.rtm.test.unit.parsing.lang;

import static dev.drsoran.rtm.test.IterableAsserts.assertEmpty;
import static dev.drsoran.rtm.test.IterableAsserts.assertEqualSet;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Locale;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import dev.drsoran.rtm.ILog;
import dev.drsoran.rtm.parsing.lang.RecurrenceSentenceLanguage;


public class RecurrenceSentenceLanguageFixture
{
   private ILog log;
   
   private RecurrenceSentenceLanguage lang;
   
   
   
   @Before
   public void setUp() throws Exception
   {
      log = EasyMock.createNiceMock( ILog.class );
      lang = new RecurrenceSentenceLanguage( Locale.ENGLISH, log );
   }
   
   
   
   @Test
   public void testRecurrenceSentenceLanguage()
   {
      new RecurrenceSentenceLanguage( Locale.GERMAN, log );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRecurrenceSentenceLanguageNullLocale()
   {
      new RecurrenceSentenceLanguage( null, log );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRecurrenceSentenceLanguageNullLog()
   {
      new RecurrenceSentenceLanguage( Locale.GERMAN, null );
   }
   
   
   
   @Test
   public void testAppend()
   {
      StringBuilder sb = new StringBuilder();
      lang.add( "Mo", "Monday" );
      lang.append( sb, "Mo" );
      assertThat( sb.toString(), is( "Monday" ) );
      
      sb = new StringBuilder();
      lang.append( sb, "Tu" );
      assertThat( sb.toString(), is( "" ) );
   }
   
   
   
   @Test
   public void testAppendEvery()
   {
      lang.add( "every_year_1", "every year" );
      lang.add( "every_year_n", "every %s years" );
      
      StringBuilder sb = new StringBuilder();
      lang.appendEvery( sb, "year", "1" );
      assertThat( sb.toString(), is( "every year" ) );
      
      sb = new StringBuilder();
      lang.appendEvery( sb, "year", "2" );
      assertThat( sb.toString(), is( "every 2 years" ) );
      
      sb = new StringBuilder();
      lang.appendEvery( sb, "year", "3" );
      assertThat( sb.toString(), is( "every 3 years" ) );
      
      sb = new StringBuilder();
      lang.appendEvery( sb, "day", "3" );
      assertThat( sb.toString(), is( "" ) );
   }
   
   
   
   @Test
   public void testAppendAfter()
   {
      lang.add( "after_year_1", "after 1 year" );
      lang.add( "after_year_n", "after %s years" );
      
      StringBuilder sb = new StringBuilder();
      lang.appendAfter( sb, "year", "1" );
      assertThat( sb.toString(), is( "after 1 year" ) );
      
      sb = new StringBuilder();
      lang.appendAfter( sb, "year", "2" );
      assertThat( sb.toString(), is( "after 2 years" ) );
      
      sb = new StringBuilder();
      lang.appendAfter( sb, "year", "3" );
      assertThat( sb.toString(), is( "after 3 years" ) );
      
      sb = new StringBuilder();
      lang.appendEvery( sb, "day", "3" );
      assertThat( sb.toString(), is( "" ) );
   }
   
   
   
   @Test
   public void testAppendStToXFromLanguage()
   {
      lang.add( "xst", "." );
      
      for ( int i = 1; i < 30; ++i )
      {
         final StringBuilder sb = new StringBuilder();
         lang.appendStToX( sb, i );
         
         assertThat( sb.toString(), is( i + "." ) );
      }
   }
   
   
   
   @Test
   public void testAppendStToXNotFromLanguage()
   {
      for ( int i = 4; i < 21; ++i )
      {
         final StringBuilder sb = new StringBuilder();
         lang.appendStToX( sb, i );
         
         assertThat( sb.toString(), is( i + "th" ) );
      }
      
      StringBuilder sb = new StringBuilder();
      lang.appendStToX( sb, 1 );
      assertThat( sb.toString(), is( "1st" ) );
      
      sb = new StringBuilder();
      lang.appendStToX( sb, 2 );
      assertThat( sb.toString(), is( "2nd" ) );
      
      sb = new StringBuilder();
      lang.appendStToX( sb, 3 );
      assertThat( sb.toString(), is( "3rd" ) );
      
      for ( int i = 21; i < 40; ++i )
      {
         sb = new StringBuilder();
         lang.appendStToX( sb, i );
         
         final String xst = String.valueOf( i );
         final char lastNum = xst.charAt( xst.length() - 1 );
         
         if ( lastNum == '1' )
         {
            assertThat( sb.toString(), is( xst + "st" ) );
         }
         else if ( lastNum == '2' )
         {
            assertThat( sb.toString(), is( xst + "nd" ) );
         }
         else if ( lastNum == '3' )
         {
            assertThat( sb.toString(), is( xst + "rd" ) );
         }
         else
         {
            assertThat( sb.toString(), is( xst + "th" ) );
         }
      }
   }
   
   
   
   @Test
   public void testGetLocale()
   {
      assertThat( lang.getLocale(), is( Locale.ENGLISH ) );
   }
   
   
   
   @Test
   public void testGetString()
   {
      lang.add( "key", "val" );
      assertThat( lang.getString( "key" ), is( "val" ) );
      
      assertThat( lang.getString( "key1" ), nullValue() );
   }
   
   
   
   @Test
   public void testGetPluralStringStringStringInt()
   {
      lang.add( "key_year_1", "1 year" );
      lang.add( "key_year_2", "2 years" );
      lang.add( "key_year_n", "%s years" );
      
      assertThat( lang.getPluralString( "key", "year", 1 ), is( "1 year" ) );
      assertThat( lang.getPluralString( "key", "year", 2 ), is( "2 years" ) );
      assertThat( lang.getPluralString( "key", "year", 3 ), is( "%s years" ) );
      assertThat( lang.getPluralString( "key", "day", 1 ), nullValue() );
   }
   
   
   
   @Test
   public void testGetPluralStringStringStringString()
   {
      lang.add( "key_year_1", "1 year" );
      lang.add( "key_year_2", "2 years" );
      lang.add( "key_year_n", "%s years" );
      
      assertThat( lang.getPluralString( "key", "year", "1" ), is( "1 year" ) );
      assertThat( lang.getPluralString( "key", "year", "2" ), is( "2 years" ) );
      assertThat( lang.getPluralString( "key", "year", "3" ), is( "%s years" ) );
      assertThat( lang.getPluralString( "key", "day", "1" ), nullValue() );
   }
   
   
   
   @Test
   public void testGetStrings()
   {
      lang.add( "key", "val1,val2,val3" );
      assertEqualSet( lang.getStrings( "key" ), "val1", "val2", "val3" );
      
      assertEmpty( lang.getStrings( "key1" ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testAddNullKey()
   {
      lang.add( null, "val" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testAddEmptyKey()
   {
      lang.add( "", "val" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testAddNullValue()
   {
      lang.add( "key", null );
   }
   
   
   
   public void testAddEmptyValue()
   {
      lang.add( "key", "" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testAddAmbigiousKey()
   {
      lang.add( "key", "val" );
      lang.add( "key", "val1" );
   }
   
   
   
   public void testAddAmbigiousValue()
   {
      lang.add( "key", "val" );
      lang.add( "key1", "val" );
      
      assertThat( lang.getString( "key" ), is( "val" ) );
      assertThat( lang.getString( "key1" ), is( "val" ) );
   }
}
