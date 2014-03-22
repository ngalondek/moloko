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

package dev.drsoran.rtm.test.unit.parsing.util;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.junit.Test;

import dev.drsoran.rtm.Lambda.Func1;
import dev.drsoran.rtm.parsing.util.ParserLanguageDetector;


public class ParserLanguageDetectorFixture
{
   @Test
   public void testParserLanguageDetector()
   {
      new ParserLanguageDetector< Integer >( Collections.< Locale > emptyList() );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testParserLanguageDetectorNullLocales()
   {
      new ParserLanguageDetector< Integer >( null );
   }
   
   
   
   @Test
   public void testDetectLanguageAndParse_NoParserSucceeds()
   {
      final ParserLanguageDetector< Integer > detector = new ParserLanguageDetector< Integer >( Arrays.asList( Locale.GERMAN,
                                                                                                               Locale.ENGLISH ) );
      
      final Integer result = detector.detectLanguageAndParse( new Func1< Locale, Integer >()
      {
         @Override
         public Integer call( Locale param )
         {
            assertThat( param, notNullValue() );
            throw new ParseCancellationException();
         }
      } );
      
      assertThat( result, nullValue() );
   }
   
   
   
   @Test
   public void testDetectLanguageAndParse_FirstParserSucceeds()
   {
      final ParserLanguageDetector< Integer > detector = new ParserLanguageDetector< Integer >( Arrays.asList( Locale.GERMAN,
                                                                                                               Locale.ENGLISH ) );
      
      final Integer result = detector.detectLanguageAndParse( new Func1< Locale, Integer >()
      {
         @Override
         public Integer call( Locale param )
         {
            assertThat( param, notNullValue() );
            
            if ( Locale.GERMAN == param )
            {
               return 1;
            }
            
            throw new ParseCancellationException();
         }
      } );
      
      assertThat( result, is( 1 ) );
   }
   
   
   
   @Test
   public void testDetectLanguageAndParse_FirstParserSucceeds2Times()
   {
      final ParserLanguageDetector< Integer > detector = new ParserLanguageDetector< Integer >( Arrays.asList( Locale.GERMAN,
                                                                                                               Locale.ENGLISH ) );
      
      Func1< Locale, Integer > parseFunc = new Func1< Locale, Integer >()
      {
         @Override
         public Integer call( Locale param )
         {
            assertThat( param, notNullValue() );
            
            if ( Locale.GERMAN == param )
            {
               return 1;
            }
            
            throw new ParseCancellationException();
         }
      };
      
      assertThat( detector.detectLanguageAndParse( parseFunc ), is( 1 ) );
      assertThat( detector.detectLanguageAndParse( parseFunc ), is( 1 ) );
   }
   
   
   
   @Test
   public void testDetectLanguageAndParse_FirstParserThenSecondParser()
   {
      final ParserLanguageDetector< Integer > detector = new ParserLanguageDetector< Integer >( Arrays.asList( Locale.GERMAN,
                                                                                                               Locale.ENGLISH ) );
      
      final Func1< Locale, Integer > parseFunc1 = new Func1< Locale, Integer >()
      {
         @Override
         public Integer call( Locale param )
         {
            assertThat( param, notNullValue() );
            
            if ( Locale.GERMAN == param )
            {
               return 1;
            }
            
            throw new ParseCancellationException();
         }
      };
      
      assertThat( detector.detectLanguageAndParse( parseFunc1 ), is( 1 ) );
      assertThat( detector.detectLanguageAndParse( parseFunc1 ), is( 1 ) );
      
      assertThat( detector.detectLanguageAndParse( new Func1< Locale, Integer >()
                  {
                     @Override
                     public Integer call( Locale param )
                     {
                        assertThat( param, notNullValue() );
                        
                        if ( Locale.ENGLISH == param )
                        {
                           return 2;
                        }
                        
                        throw new ParseCancellationException();
                     }
                  } ),
                  is( 2 ) );
   }
   
   
   
   @Test
   public void testDetectLanguageAndParse_SecondParserSucceeds()
   {
      final ParserLanguageDetector< Integer > detector = new ParserLanguageDetector< Integer >( Arrays.asList( Locale.GERMAN,
                                                                                                               Locale.ENGLISH ) );
      
      final Integer result = detector.detectLanguageAndParse( new Func1< Locale, Integer >()
      {
         @Override
         public Integer call( Locale param )
         {
            assertThat( param, notNullValue() );
            
            if ( Locale.ENGLISH == param )
            {
               return 2;
            }
            
            throw new ParseCancellationException();
         }
      } );
      
      assertThat( result, is( 2 ) );
   }
}
