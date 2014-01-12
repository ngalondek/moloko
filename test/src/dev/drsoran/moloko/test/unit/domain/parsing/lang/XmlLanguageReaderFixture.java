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

package dev.drsoran.moloko.test.unit.domain.parsing.lang;

import java.io.IOException;
import java.text.ParseException;

import org.codehaus.plexus.util.xml.pull.XmlPullParser;
import org.easymock.EasyMock;
import org.junit.Test;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;
import dev.drsoran.moloko.domain.parsing.lang.XmlLanguageReader;
import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.rtm.parsing.lang.DictionaryLanguage;


@SuppressWarnings( "unchecked" )
public class XmlLanguageReaderFixture extends MolokoTestCase
{
   @Test
   public void testXmlLanguageReader()
   {
      new XmlLanguageReader( EasyMock.createNiceMock( DictionaryLanguage.class ),
                             EasyMock.createNiceMock( XmlResourceParser.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testXmlLanguageReaderNullLanguage()
   {
      new XmlLanguageReader( null,
                             EasyMock.createNiceMock( XmlResourceParser.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testXmlLanguageReaderNullParser()
   {
      new XmlLanguageReader( EasyMock.createNiceMock( DictionaryLanguage.class ),
                             null );
   }
   
   
   
   @Test
   public void testReadEmptyDoc() throws Exception
   {
      final DictionaryLanguage< String > language = EasyMock.createStrictMock( DictionaryLanguage.class );
      EasyMock.replay( language );
      
      final XmlResourceParser xmlParser = EasyMock.createStrictMock( XmlResourceParser.class );
      EasyMock.expect( xmlParser.getEventType() )
              .andReturn( XmlPullParser.END_DOCUMENT );
      EasyMock.replay( xmlParser );
      
      final XmlLanguageReader reader = new XmlLanguageReader( language,
                                                              xmlParser );
      reader.read();
      
      EasyMock.verify( xmlParser );
      EasyMock.verify( language );
   }
   
   
   
   @Test( expected = ParseException.class )
   public void testReadXmlParserException() throws Exception
   {
      final DictionaryLanguage< String > language = EasyMock.createStrictMock( DictionaryLanguage.class );
      EasyMock.replay( language );
      
      final XmlResourceParser xmlParser = EasyMock.createStrictMock( XmlResourceParser.class );
      EasyMock.expect( xmlParser.getEventType() )
              .andThrow( newXmlPullParserExceptionMock() );
      EasyMock.replay( xmlParser );
      
      final XmlLanguageReader reader = new XmlLanguageReader( language,
                                                              xmlParser );
      reader.read();
      
      EasyMock.verify( xmlParser );
      EasyMock.verify( language );
   }
   
   
   
   @Test( expected = ParseException.class )
   public void testReadIoException() throws Exception
   {
      final DictionaryLanguage< String > language = EasyMock.createStrictMock( DictionaryLanguage.class );
      EasyMock.replay( language );
      
      final XmlResourceParser xmlParser = EasyMock.createStrictMock( XmlResourceParser.class );
      EasyMock.expect( xmlParser.getEventType() )
              .andReturn( XmlPullParser.START_DOCUMENT );
      EasyMock.expect( xmlParser.next() ).andThrow( new IOException() );
      EasyMock.expect( xmlParser.getLineNumber() ).andReturn( 1 );
      EasyMock.replay( xmlParser );
      
      final XmlLanguageReader reader = new XmlLanguageReader( language,
                                                              xmlParser );
      reader.read();
      
      EasyMock.verify( xmlParser );
      EasyMock.verify( language );
   }
   
   
   
   @Test
   public void testReadSimpleEntry() throws Exception
   {
      final DictionaryLanguage< String > language = EasyMock.createStrictMock( DictionaryLanguage.class );
      language.add( "testKey", "testValue" );
      EasyMock.replay( language );
      
      final XmlResourceParser xmlParser = EasyMock.createNiceMock( XmlResourceParser.class );
      EasyMock.expect( xmlParser.getEventType() )
              .andReturn( XmlPullParser.START_TAG );
      EasyMock.expect( xmlParser.getName() ).andReturn( "entry" );
      EasyMock.expect( xmlParser.getAttributeCount() ).andReturn( 2 );
      EasyMock.expect( xmlParser.getAttributeName( 0 ) ).andReturn( "key" );
      EasyMock.expect( xmlParser.getAttributeValue( 0 ) ).andReturn( "testKey" );
      EasyMock.expect( xmlParser.getAttributeName( 1 ) ).andReturn( "value" );
      EasyMock.expect( xmlParser.getAttributeValue( 1 ) )
              .andReturn( "testValue" );
      EasyMock.expect( xmlParser.next() )
              .andReturn( XmlPullParser.END_DOCUMENT );
      EasyMock.replay( xmlParser );
      
      final XmlLanguageReader reader = new XmlLanguageReader( language,
                                                              xmlParser );
      reader.read();
      
      EasyMock.verify( xmlParser );
      EasyMock.verify( language );
   }
   
   
   
   @Test( expected = ParseException.class )
   public void testReadSimpleEntryTooLessAttributes() throws Exception
   {
      testTooLessAttributes( "entry" );
   }
   
   
   
   @Test( expected = ParseException.class )
   public void testReadSimpleEntryUnknownAttribute() throws Exception
   {
      final DictionaryLanguage< String > language = EasyMock.createStrictMock( DictionaryLanguage.class );
      EasyMock.replay( language );
      
      final XmlResourceParser xmlParser = EasyMock.createNiceMock( XmlResourceParser.class );
      EasyMock.expect( xmlParser.getEventType() )
              .andReturn( XmlPullParser.START_TAG );
      EasyMock.expect( xmlParser.getName() ).andReturn( "entry" );
      EasyMock.expect( xmlParser.getAttributeCount() ).andReturn( 2 );
      EasyMock.expect( xmlParser.getAttributeName( 0 ) )
              .andReturn( "UnknownAttrib" );
      EasyMock.expect( xmlParser.getLineNumber() ).andReturn( 1 );
      EasyMock.replay( xmlParser );
      
      final XmlLanguageReader reader = new XmlLanguageReader( language,
                                                              xmlParser );
      reader.read();
      
      EasyMock.verify( xmlParser );
      EasyMock.verify( language );
   }
   
   
   
   @Test( expected = ParseException.class )
   public void testReadSimpleEntryAmbigiousKey() throws Exception
   {
      final DictionaryLanguage< String > language = EasyMock.createStrictMock( DictionaryLanguage.class );
      language.add( "ambKey", "testValue" );
      language.add( "ambKey", "testValue" );
      EasyMock.expectLastCall().andThrow( new IllegalArgumentException() );
      EasyMock.replay( language );
      
      testAmbigiousKey( "entry", language );
      
      EasyMock.verify( language );
   }
   
   
   
   @Test( expected = ParseException.class )
   public void testReadSimpleEntryNullKey() throws Exception
   {
      testNullKey( "entry" );
   }
   
   
   
   @Test( expected = ParseException.class )
   public void testReadSimpleEntryEmptyKey() throws Exception
   {
      testEmptyKey( "entry" );
   }
   
   
   
   @Test( expected = ParseException.class )
   public void testReadSimpleEntryNullValue() throws Exception
   {
      testNullValue( "entry" );
   }
   
   
   
   @Test
   public void testReadPluralEntry() throws Exception
   {
      final DictionaryLanguage< String > language = EasyMock.createNiceMock( DictionaryLanguage.class );
      language.add( "testKey_year_1", "1 year" );
      language.add( "testKey_year_2", "2 years" );
      language.add( "testKey_year_n", "%s years" );
      EasyMock.replay( language );
      
      final XmlResourceParser xmlParser = EasyMock.createNiceMock( XmlResourceParser.class );
      EasyMock.expect( xmlParser.getEventType() )
              .andReturn( XmlPullParser.START_TAG );
      EasyMock.expect( xmlParser.getName() ).andReturn( "entryPl" );
      EasyMock.expect( xmlParser.getAttributeCount() ).andReturn( 4 );
      EasyMock.expect( xmlParser.getAttributeName( 0 ) ).andReturn( "key" );
      EasyMock.expect( xmlParser.getAttributeValue( 0 ) ).andReturn( "testKey" );
      EasyMock.expect( xmlParser.getAttributeName( 1 ) ).andReturn( "year_1" );
      EasyMock.expect( xmlParser.getAttributeValue( 1 ) ).andReturn( "1 year" );
      EasyMock.expect( xmlParser.getAttributeName( 2 ) ).andReturn( "year_2" );
      EasyMock.expect( xmlParser.getAttributeValue( 2 ) ).andReturn( "2 years" );
      EasyMock.expect( xmlParser.getAttributeName( 3 ) ).andReturn( "year_n" );
      EasyMock.expect( xmlParser.getAttributeValue( 3 ) )
              .andReturn( "%s years" );
      EasyMock.expect( xmlParser.next() )
              .andReturn( XmlPullParser.END_DOCUMENT );
      EasyMock.replay( xmlParser );
      
      final XmlLanguageReader reader = new XmlLanguageReader( language,
                                                              xmlParser );
      reader.read();
      
      EasyMock.verify( xmlParser );
      EasyMock.verify( language );
   }
   
   
   
   @Test( expected = ParseException.class )
   public void testReadPluralEntryTooLessAttributes() throws Exception
   {
      testTooLessAttributes( "entryPl" );
   }
   
   
   
   @Test( expected = ParseException.class )
   public void testReadPluralEntryAmbigiousKey() throws Exception
   {
      final DictionaryLanguage< String > language = EasyMock.createStrictMock( DictionaryLanguage.class );
      language.add( "ambKey_value", "testValue" );
      language.add( "ambKey_value", "testValue" );
      EasyMock.expectLastCall().andThrow( new IllegalArgumentException() );
      EasyMock.replay( language );
      
      testAmbigiousKey( "entryPl", language );
      
      EasyMock.verify( language );
   }
   
   
   
   @Test( expected = ParseException.class )
   public void testReadPluralEntryNullKey() throws Exception
   {
      testNullKey( "entryPl" );
   }
   
   
   
   @Test( expected = ParseException.class )
   public void testReadPluralEntryEmptyKey() throws Exception
   {
      testEmptyKey( "entryPl" );
   }
   
   
   
   @Test( expected = ParseException.class )
   public void testReadPluralEntryNullValue() throws Exception
   {
      testNullValue( "entryPl" );
   }
   
   
   
   @Test
   public void testClose()
   {
      final XmlResourceParser xmlParser = EasyMock.createStrictMock( XmlResourceParser.class );
      xmlParser.close();
      EasyMock.replay( xmlParser );
      
      final XmlLanguageReader reader = new XmlLanguageReader( EasyMock.createNiceMock( DictionaryLanguage.class ),
                                                              xmlParser );
      reader.close();
      
      EasyMock.verify( xmlParser );
   }
   
   
   
   private void testTooLessAttributes( String elementName ) throws XmlPullParserException,
                                                           ParseException
   {
      final DictionaryLanguage< String > language = EasyMock.createStrictMock( DictionaryLanguage.class );
      EasyMock.replay( language );
      
      final XmlResourceParser xmlParser = EasyMock.createNiceMock( XmlResourceParser.class );
      EasyMock.expect( xmlParser.getEventType() )
              .andReturn( XmlPullParser.START_TAG );
      EasyMock.expect( xmlParser.getName() ).andReturn( elementName );
      EasyMock.expect( xmlParser.getAttributeCount() ).andReturn( 1 );
      EasyMock.expect( xmlParser.getLineNumber() ).andReturn( 1 );
      EasyMock.replay( xmlParser );
      
      final XmlLanguageReader reader = new XmlLanguageReader( language,
                                                              xmlParser );
      reader.read();
      
      EasyMock.verify( xmlParser );
      EasyMock.verify( language );
   }
   
   
   
   private void testAmbigiousKey( String element,
                                  DictionaryLanguage< String > language ) throws XmlPullParserException,
                                                                         ParseException,
                                                                         IOException
   {
      final XmlResourceParser xmlParser = EasyMock.createNiceMock( XmlResourceParser.class );
      EasyMock.expect( xmlParser.getEventType() )
              .andReturn( XmlPullParser.START_TAG );
      EasyMock.expect( xmlParser.getName() ).andReturn( element );
      EasyMock.expect( xmlParser.getAttributeCount() ).andReturn( 2 );
      EasyMock.expect( xmlParser.getAttributeName( 0 ) ).andReturn( "key" );
      EasyMock.expect( xmlParser.getAttributeValue( 0 ) ).andReturn( "ambKey" );
      EasyMock.expect( xmlParser.getAttributeName( 1 ) ).andReturn( "value" );
      EasyMock.expect( xmlParser.getAttributeValue( 1 ) )
              .andReturn( "testValue" );
      EasyMock.expect( xmlParser.next() ).andReturn( XmlPullParser.START_TAG );
      EasyMock.expect( xmlParser.getName() ).andReturn( element );
      EasyMock.expect( xmlParser.getAttributeCount() ).andReturn( 2 );
      EasyMock.expect( xmlParser.getAttributeName( 0 ) ).andReturn( "key" );
      EasyMock.expect( xmlParser.getAttributeValue( 0 ) ).andReturn( "ambKey" );
      EasyMock.expect( xmlParser.getAttributeName( 1 ) ).andReturn( "value" );
      EasyMock.expect( xmlParser.getAttributeValue( 1 ) )
              .andReturn( "testValue" );
      EasyMock.replay( xmlParser );
      
      final XmlLanguageReader reader = new XmlLanguageReader( language,
                                                              xmlParser );
      reader.read();
      
      EasyMock.verify( xmlParser );
   }
   
   
   
   private void testNullKey( String element ) throws XmlPullParserException,
                                             ParseException
   {
      final DictionaryLanguage< String > language = EasyMock.createStrictMock( DictionaryLanguage.class );
      EasyMock.replay( language );
      
      final XmlResourceParser xmlParser = EasyMock.createNiceMock( XmlResourceParser.class );
      EasyMock.expect( xmlParser.getEventType() )
              .andReturn( XmlPullParser.START_TAG );
      EasyMock.expect( xmlParser.getName() ).andReturn( element );
      EasyMock.expect( xmlParser.getAttributeCount() ).andReturn( 2 );
      EasyMock.expect( xmlParser.getAttributeName( 0 ) ).andReturn( "key" );
      EasyMock.expect( xmlParser.getAttributeValue( 0 ) ).andReturn( null );
      EasyMock.expect( xmlParser.getAttributeName( 1 ) ).andReturn( "value" );
      EasyMock.expect( xmlParser.getAttributeValue( 1 ) )
              .andReturn( "testValue" );
      EasyMock.replay( xmlParser );
      
      final XmlLanguageReader reader = new XmlLanguageReader( language,
                                                              xmlParser );
      reader.read();
      
      EasyMock.verify( xmlParser );
      EasyMock.verify( language );
   }
   
   
   
   private void testEmptyKey( String element ) throws XmlPullParserException,
                                              ParseException
   {
      final DictionaryLanguage< String > language = EasyMock.createStrictMock( DictionaryLanguage.class );
      EasyMock.replay( language );
      
      final XmlResourceParser xmlParser = EasyMock.createNiceMock( XmlResourceParser.class );
      EasyMock.expect( xmlParser.getEventType() )
              .andReturn( XmlPullParser.START_TAG );
      EasyMock.expect( xmlParser.getName() ).andReturn( element );
      EasyMock.expect( xmlParser.getAttributeCount() ).andReturn( 2 );
      EasyMock.expect( xmlParser.getAttributeName( 0 ) ).andReturn( "key" );
      EasyMock.expect( xmlParser.getAttributeValue( 0 ) ).andReturn( "" );
      EasyMock.expect( xmlParser.getAttributeName( 1 ) ).andReturn( "value" );
      EasyMock.expect( xmlParser.getAttributeValue( 1 ) )
              .andReturn( "testValue" );
      EasyMock.replay( xmlParser );
      
      final XmlLanguageReader reader = new XmlLanguageReader( language,
                                                              xmlParser );
      reader.read();
      
      EasyMock.verify( xmlParser );
      EasyMock.verify( language );
   }
   
   
   
   private void testNullValue( String element ) throws XmlPullParserException,
                                               ParseException
   {
      final DictionaryLanguage< String > language = EasyMock.createStrictMock( DictionaryLanguage.class );
      EasyMock.replay( language );
      
      final XmlResourceParser xmlParser = EasyMock.createNiceMock( XmlResourceParser.class );
      EasyMock.expect( xmlParser.getEventType() )
              .andReturn( XmlPullParser.START_TAG );
      EasyMock.expect( xmlParser.getName() ).andReturn( element );
      EasyMock.expect( xmlParser.getAttributeCount() ).andReturn( 2 );
      EasyMock.expect( xmlParser.getAttributeName( 0 ) ).andReturn( "key" );
      EasyMock.expect( xmlParser.getAttributeValue( 0 ) ).andReturn( "testKey" );
      EasyMock.expect( xmlParser.getAttributeName( 1 ) ).andReturn( "value" );
      EasyMock.expect( xmlParser.getAttributeValue( 1 ) ).andReturn( null );
      EasyMock.replay( xmlParser );
      
      final XmlLanguageReader reader = new XmlLanguageReader( language,
                                                              xmlParser );
      reader.read();
      
      EasyMock.verify( xmlParser );
      EasyMock.verify( language );
   }
   
   
   
   private static XmlPullParserException newXmlPullParserExceptionMock()
   {
      final XmlPullParserException exception = EasyMock.createNiceMock( XmlPullParserException.class );
      EasyMock.expect( exception.getMessage() ).andReturn( "" );
      EasyMock.expect( exception.getLineNumber() ).andReturn( 1 );
      EasyMock.replay( exception );
      
      return exception;
   }
}
