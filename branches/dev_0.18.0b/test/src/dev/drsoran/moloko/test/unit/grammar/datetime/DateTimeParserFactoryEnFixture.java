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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import dev.drsoran.moloko.grammar.datetime.DateTimeParserFactoryEn;
import dev.drsoran.moloko.grammar.datetime.IDateParser;
import dev.drsoran.moloko.grammar.datetime.ITimeParser;
import dev.drsoran.moloko.test.MolokoTestCase;


public class DateTimeParserFactoryEnFixture extends MolokoTestCase
{
   private DateTimeParserFactoryEn fact;
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      fact = new DateTimeParserFactoryEn();
   }
   
   
   
   @Test
   public void testCreateDateParser()
   {
      final IDateParser dateParser = fact.createDateParser();
      assertThat( dateParser, notNullValue() );
      assertThat( dateParser.getLocale(), is( Locale.ENGLISH ) );
   }
   
   
   
   @Test
   public void testCreateTimeParser()
   {
      final ITimeParser timeParser = fact.createTimeParser();
      assertThat( timeParser, notNullValue() );
      assertThat( timeParser.getLocale(), is( Locale.ENGLISH ) );
   }
   
   
   
   @Test
   public void testGetParserLocale()
   {
      assertThat( fact.getParserLocale(), is( Locale.ENGLISH ) );
   }
}
