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

package dev.drsoran.moloko.test.unit.grammar;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

import org.easymock.EasyMock;
import org.junit.Test;
import org.robolectric.annotation.Config;

import android.util.Pair;
import dev.drsoran.moloko.grammar.GrammarException;
import dev.drsoran.moloko.grammar.ILocalizedParser;
import dev.drsoran.moloko.grammar.IParserFunc;
import dev.drsoran.moloko.grammar.ParserLanguageDetector;
import dev.drsoran.moloko.test.MolokoRoboTestCase;
import dev.drsoran.moloko.test.PrivateCtorCaller;


@Config( manifest = Config.NONE )
public class ParserLanguageDetectorFixture extends MolokoRoboTestCase
{
   @Test( expected = AssertionError.class )
   public void testPrivateCtor() throws Throwable
   {
      PrivateCtorCaller.callPrivateCtor( ParserLanguageDetector.class );
   }
   
   
   
   @Test
   public void testDetectLanguageAndParse_NoExistingParser() throws GrammarException
   {
      final ILocalizedParser deParser = getParser( Locale.GERMAN );
      
      final Pair< ILocalizedParser, Object > result = ParserLanguageDetector.detectLanguageAndParse( null,
                                                                                                     Collections.singletonList( deParser ),
                                                                                                     parseSucceedsForParser( deParser ) );
      
      assertThat( result.first, sameInstance( deParser ) );
      assertThat( (Locale) result.second, is( deParser.getLocale() ) );
   }
   
   
   
   @Test
   public void testDetectLanguageAndParse_ExistingParserSucceeds() throws GrammarException
   {
      final ILocalizedParser deParser = getParser( Locale.GERMAN );
      final ILocalizedParser enParser = getParser( Locale.ENGLISH );
      
      final Pair< ILocalizedParser, Object > result = ParserLanguageDetector.detectLanguageAndParse( enParser,
                                                                                                     Arrays.asList( deParser,
                                                                                                                    enParser ),
                                                                                                     parseSucceedsForParser( enParser ) );
      
      assertThat( result.first, sameInstance( enParser ) );
      assertThat( (Locale) result.second, is( enParser.getLocale() ) );
   }
   
   
   
   @Test
   public void testDetectLanguageAndParse_ExistingParserNotSucceeds() throws GrammarException
   {
      final ILocalizedParser deParser = getParser( Locale.GERMAN );
      final ILocalizedParser enParser = getParser( Locale.ENGLISH );
      
      final Pair< ILocalizedParser, Object > result = ParserLanguageDetector.detectLanguageAndParse( enParser,
                                                                                                     Arrays.asList( deParser,
                                                                                                                    enParser ),
                                                                                                     parseSucceedsForParser( deParser ) );
      
      assertThat( result.first, sameInstance( deParser ) );
      assertThat( (Locale) result.second, is( deParser.getLocale() ) );
   }
   
   
   
   @Test
   public void testDetectLanguageAndParse_NoParserSucceeds() throws GrammarException
   {
      final ILocalizedParser deParser = getParser( Locale.GERMAN );
      final ILocalizedParser enParser = getParser( Locale.ENGLISH );
      
      final Pair< ILocalizedParser, Object > result = ParserLanguageDetector.detectLanguageAndParse( enParser,
                                                                                                     Arrays.asList( deParser,
                                                                                                                    enParser ),
                                                                                                     parseSucceedsForParser( null ) );
      
      assertThat( result.first, is( (ILocalizedParser) null ) );
      assertThat( result.second, is( (Object) null ) );
   }
   
   
   
   private IParserFunc< ILocalizedParser, Object > parseSucceedsForParser( ILocalizedParser parser ) throws GrammarException
   {
      @SuppressWarnings( "unchecked" )
      final IParserFunc< ILocalizedParser, Object > parserFunc = EasyMock.createNiceMock( IParserFunc.class );
      
      if ( parser != null )
      {
         EasyMock.expect( parserFunc.call( parser ) )
                 .andReturn( parser.getLocale() )
                 .anyTimes();
      }
      
      EasyMock.expect( parserFunc.call( EasyMock.not( EasyMock.same( parser ) ) ) )
              .andThrow( new GrammarException() )
              .anyTimes();
      
      EasyMock.replay( parserFunc );
      
      return parserFunc;
   }
   
   
   
   private ILocalizedParser getParser( Locale locale )
   {
      final ILocalizedParser parser = EasyMock.createNiceMock( ILocalizedParser.class );
      EasyMock.expect( parser.getLocale() ).andReturn( locale ).anyTimes();
      EasyMock.replay( parser );
      
      return parser;
   }
}
