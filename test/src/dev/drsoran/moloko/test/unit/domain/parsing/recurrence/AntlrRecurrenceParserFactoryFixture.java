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

package dev.drsoran.moloko.test.unit.domain.parsing.recurrence;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Locale;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import dev.drsoran.Strings;
import dev.drsoran.moloko.domain.parsing.recurrence.AntlrRecurrenceParserFactory;
import dev.drsoran.moloko.domain.parsing.recurrence.IRecurrenceParserFactory;
import dev.drsoran.moloko.grammar.antlr.recurrence.RecurrenceParser;
import dev.drsoran.moloko.test.MolokoTestCase;


public class AntlrRecurrenceParserFactoryFixture extends MolokoTestCase
{
   private IRecurrenceParserFactory fact;
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      fact = new AntlrRecurrenceParserFactory();
   }
   
   
   
   @Test
   public void testCreateRecurrenceParserGerman()
   {
      final RecurrenceParser parser = fact.createRecurrenceParser( Locale.GERMAN,
                                                                   Strings.EMPTY_STRING );
      assertThat( parser, notNullValue() );
   }
   
   
   
   @Test
   public void testCreateRecurrenceParserEnglish()
   {
      final RecurrenceParser parser = fact.createRecurrenceParser( Locale.ENGLISH,
                                                                   Strings.EMPTY_STRING );
      assertThat( parser, notNullValue() );
   }
   
   
   
   @Test( expected = NoSuchElementException.class )
   public void testCreateRecurrenceParserUnknown()
   {
      fact.createRecurrenceParser( Locale.JAPAN, Strings.EMPTY_STRING );
   }
   
   
   
   @Test
   public void testCreateRecurrencePatternParser()
   {
      assertThat( fact.createRecurrencePatternParser( Strings.EMPTY_STRING ),
                  notNullValue() );
   }
   
   
   
   @Test
   public void testGetAvailableParserLocales()
   {
      assertThat( fact.getAvailableParserLocales(),
                  hasItems( Locale.ENGLISH, Locale.GERMAN ) );
   }
}
