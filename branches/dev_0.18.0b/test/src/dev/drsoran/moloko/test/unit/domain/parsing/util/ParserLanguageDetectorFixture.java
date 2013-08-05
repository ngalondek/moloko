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

package dev.drsoran.moloko.test.unit.domain.parsing.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.junit.Test;

import dev.drsoran.moloko.domain.parsing.util.ParserLanguageDetector;
import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.moloko.util.Lambda.Func1;


public class ParserLanguageDetectorFixture extends MolokoTestCase
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
            throw new ParseCancellationException();
         }
      } );
      
      assertThat( result, sameInstance( null ) );
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
