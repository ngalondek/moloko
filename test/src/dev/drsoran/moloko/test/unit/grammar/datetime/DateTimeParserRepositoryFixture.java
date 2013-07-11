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

package dev.drsoran.moloko.test.unit.grammar.datetime;

import static dev.drsoran.moloko.test.IterableAsserts.assertNotEmpty;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import dev.drsoran.moloko.grammar.datetime.DateTimeParserRepository;
import dev.drsoran.moloko.grammar.datetime.IDateParser;
import dev.drsoran.moloko.grammar.datetime.ITimeParser;
import dev.drsoran.moloko.test.MolokoTestCase;


public class DateTimeParserRepositoryFixture extends MolokoTestCase
{
   private DateTimeParserRepository repo;
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      repo = new DateTimeParserRepository();
   }
   
   
   
   @Test
   public void testGetDateParsers()
   {
      assertThat( repo.getDateParsers(), notNullValue() );
      assertNotEmpty( repo.getDateParsers() );
      
      final Iterable< IDateParser > dateParsers = repo.getDateParsers();
      for ( IDateParser dateParser : dateParsers )
      {
         assertThat( dateParser, notNullValue() );
      }
   }
   
   
   
   @Test
   public void testGetTimeParsers()
   {
      assertThat( repo.getTimeParsers(), notNullValue() );
      assertNotEmpty( repo.getTimeParsers() );
      
      final Iterable< ITimeParser > parsers = repo.getTimeParsers();
      for ( ITimeParser parser : parsers )
      {
         assertThat( parser, notNullValue() );
      }
   }
   
   
   
   @Test
   public void testGetDateParser()
   {
      IDateParser parser = repo.getDateParser( Locale.ENGLISH );
      assertThat( parser, notNullValue() );
      assertThat( parser.getLocale(), is( Locale.ENGLISH ) );
      
      parser = repo.getDateParser( Locale.GERMAN );
      assertThat( parser, notNullValue() );
      assertThat( parser.getLocale(), is( Locale.GERMAN ) );
      
      assertThat( repo.getDateParser( Locale.ITALIAN ), nullValue() );
   }
   
   
   
   @Test
   public void testGetTimeParser()
   {
      ITimeParser parser = repo.getTimeParser( Locale.ENGLISH );
      assertThat( parser, notNullValue() );
      assertThat( parser.getLocale(), is( Locale.ENGLISH ) );
      
      parser = repo.getTimeParser( Locale.GERMAN );
      assertThat( parser, notNullValue() );
      assertThat( parser.getLocale(), is( Locale.GERMAN ) );
      
      assertThat( repo.getTimeParser( Locale.ITALIAN ), nullValue() );
   }
   
   
   
   @Test
   public void testGetDefaultDateParser()
   {
      final IDateParser parser = repo.getDefaultDateParser();
      assertThat( parser, notNullValue() );
      assertThat( parser.getLocale(), is( Locale.ENGLISH ) );
   }
   
   
   
   @Test
   public void testGetDefaultTimeParser()
   {
      final ITimeParser parser = repo.getDefaultTimeParser();
      assertThat( parser, notNullValue() );
      assertThat( parser.getLocale(), is( Locale.ENGLISH ) );
   }
   
   
   
   @Test
   public void testExistsDateParserForLocale()
   {
      assertThat( repo.existsDateParserForLocale( Locale.ENGLISH ), is( true ) );
      assertThat( repo.existsDateParserForLocale( Locale.GERMAN ), is( true ) );
      assertThat( repo.existsDateParserForLocale( Locale.ITALIAN ), is( false ) );
   }
   
   
   
   @Test
   public void testExistsTimeParserForLocale()
   {
      assertThat( repo.existsTimeParserForLocale( Locale.ENGLISH ), is( true ) );
      assertThat( repo.existsTimeParserForLocale( Locale.GERMAN ), is( true ) );
      assertThat( repo.existsTimeParserForLocale( Locale.ITALIAN ), is( false ) );
   }
}
