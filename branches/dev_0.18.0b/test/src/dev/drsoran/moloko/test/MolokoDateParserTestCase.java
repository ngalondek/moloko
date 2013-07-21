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

package dev.drsoran.moloko.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.junit.Before;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.grammar.GrammarException;
import dev.drsoran.moloko.grammar.IDateFormatter;
import dev.drsoran.moloko.grammar.datetime.IDateParser;
import dev.drsoran.moloko.grammar.datetime.ParseReturn;


public abstract class MolokoDateParserTestCase extends MolokoTestCase
{
   private IDateParser dateParser;
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      dateParser = createDateParser();
   }
   
   
   
   public MolokoCalendar testParseDate( String dateToParse ) throws GrammarException
   {
      final MolokoCalendar cal = MolokoCalendar.getInstance();
      
      final IDateFormatter dateFormatter = createDateFormatter();
      final ParseReturn ret = testParseDate( cal,
                                             dateToParse,
                                             true,
                                             dateFormatter );
      
      verifyParseResult( dateToParse, cal, ret, false );
      
      return cal;
   }
   
   
   
   public ParseReturn testParseDate( MolokoCalendar cal,
                                     String dateToParse,
                                     boolean clearTime,
                                     IDateFormatter dateFormatter ) throws GrammarException
   {
      dateParser.setDateFormatter( dateFormatter );
      final ParseReturn ret = dateParser.parseDate( dateToParse, cal, clearTime );
      return ret;
   }
   
   
   
   public void verifyParseResult( String toParse,
                                  MolokoCalendar cal,
                                  ParseReturn ret,
                                  boolean expectHasTime )
   {
      assertThat( "Unexpected calendar hasTime for <" + toParse + ">",
                  cal.hasTime(),
                  is( expectHasTime ) );
      
      assertThat( "Not EOF for <" + toParse + ">", ret.isEof, is( true ) );
      assertThat( "Unexpected char count parsed for <" + toParse + ">",
                  ret.numParsedChars,
                  is( toParse.length() ) );
   }
   
   
   
   private static IDateFormatter createDateFormatter()
   {
      IDateFormatter dateFormatter = EasyMock.createNiceMock( IDateFormatter.class );
      EasyMock.expect( dateFormatter.getNumericDateFormatPattern( true ) )
              .andReturn( "dd.MM.yyyy" );
      EasyMock.expect( dateFormatter.getNumericDateFormatPattern( false ) )
              .andReturn( "dd.MM." );
      
      EasyMock.expect( dateFormatter.formatDateNumeric( EasyMock.anyObject( String.class ),
                                                        EasyMock.anyObject( String.class ) ) )
              .andAnswer( new IAnswer< String >()
              {
                 @Override
                 public String answer() throws Throwable
                 {
                    final Object[] args = EasyMock.getCurrentArguments();
                    return args[ 0 ] + "." + args[ 1 ] + ".";
                 }
              } );
      
      EasyMock.expect( dateFormatter.formatDateNumeric( EasyMock.anyObject( String.class ),
                                                        EasyMock.anyObject( String.class ),
                                                        EasyMock.anyObject( String.class ) ) )
              .andAnswer( new IAnswer< String >()
              {
                 @Override
                 public String answer() throws Throwable
                 {
                    final Object[] args = EasyMock.getCurrentArguments();
                    return args[ 0 ] + "." + args[ 1 ] + "." + args[ 2 ];
                 }
              } );
      
      EasyMock.replay( dateFormatter );
      
      return dateFormatter;
   }
   
   
   
   protected abstract IDateParser createDateParser();
}
