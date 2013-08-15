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

package dev.drsoran.moloko.test.unit.domain.parsing.datetime;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import java.util.Locale;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import dev.drsoran.moloko.domain.parsing.datetime.AntlrDateTimeParserFactory;
import dev.drsoran.moloko.domain.parsing.datetime.IDateTimeParserFactory;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser;
import dev.drsoran.moloko.grammar.antlr.datetime.TimeParser;
import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.moloko.util.Strings;


public class AntlrDateTimeParserFactoryFixture extends MolokoTestCase
{
   private IDateTimeParserFactory fact;
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      fact = new AntlrDateTimeParserFactory();
   }
   
   
   
   @Test
   public void testCreateDateParserGerman()
   {
      final DateParser parser = fact.createDateParser( Locale.GERMAN,
                                                       Strings.EMPTY_STRING );
      assertThat( parser, notNullValue() );
   }
   
   
   
   @Test
   public void testCreateDateParserEnglish()
   {
      final DateParser parser = fact.createDateParser( Locale.ENGLISH,
                                                       Strings.EMPTY_STRING );
      assertThat( parser, notNullValue() );
   }
   
   
   
   @Test( expected = NoSuchElementException.class )
   public void testCreateDateParserUnknown()
   {
      fact.createDateParser( Locale.JAPAN, Strings.EMPTY_STRING );
   }
   
   
   
   @Test
   public void testCreateTimeParserGerman()
   {
      final TimeParser parser = fact.createTimeParser( Locale.GERMAN,
                                                       Strings.EMPTY_STRING );
      assertThat( parser, notNullValue() );
   }
   
   
   
   @Test
   public void testCreateTimeParserEnglish()
   {
      final TimeParser parser = fact.createTimeParser( Locale.ENGLISH,
                                                       Strings.EMPTY_STRING );
      assertThat( parser, notNullValue() );
   }
   
   
   
   @Test( expected = NoSuchElementException.class )
   public void testCreateTimeParserUnknown()
   {
      fact.createTimeParser( Locale.JAPAN, Strings.EMPTY_STRING );
   }
   
   
   
   @Test
   public void testGetAvailableParserLocales()
   {
      assertThat( fact.getAvailableParserLocales(),
                  hasItems( Locale.ENGLISH, Locale.GERMAN ) );
   }
}
