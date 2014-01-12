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

package dev.drsoran.test.unit.rtm.parsing.lang;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Locale;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.rtm.parsing.lang.DateLanguageRepository;
import dev.drsoran.rtm.parsing.lang.IDateLanguageRepository;


public class DateLanguageRepositoryFixture extends MolokoTestCase
{
   private IDateLanguageRepository repo;
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      repo = new DateLanguageRepository();
   }
   
   
   
   @Test
   public void testGetLanguageGerman()
   {
      assertThat( repo.getLanguage( Locale.GERMAN ).getLocale(),
                  is( Locale.GERMAN ) );
   }
   
   
   
   @Test
   public void testGetLanguageEnglish()
   {
      assertThat( repo.getLanguage( Locale.ENGLISH ).getLocale(),
                  is( Locale.ENGLISH ) );
   }
   
   
   
   @Test( expected = NoSuchElementException.class )
   public void testGetLanguageUnknown()
   {
      repo.getLanguage( Locale.JAPANESE );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testGetLanguageNull()
   {
      repo.getLanguage( null );
   }
   
}
