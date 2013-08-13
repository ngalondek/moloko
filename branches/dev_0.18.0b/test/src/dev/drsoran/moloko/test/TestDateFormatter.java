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

import java.text.SimpleDateFormat;
import java.util.Date;

import org.easymock.EasyMock;
import org.easymock.IAnswer;

import dev.drsoran.moloko.domain.parsing.IDateFormatter;


public class TestDateFormatter
{
   private final static String NUM_DATE_PATTERN_FULL = "dd.MM.yyyy";
   
   private final static String NUM_DATE_PATTERN_SHORT = "dd.MM.";
   
   private final static String NUM_DATE_TIME_PATTERN = NUM_DATE_PATTERN_FULL
      + ", " + "H:mm:ss";
   
   
   
   public static IDateFormatter get()
   {
      IDateFormatter dateFormatter = EasyMock.createNiceMock( IDateFormatter.class );
      EasyMock.expect( dateFormatter.getNumericDateFormatPattern( true ) )
              .andReturn( NUM_DATE_PATTERN_FULL );
      EasyMock.expect( dateFormatter.getNumericDateFormatPattern( false ) )
              .andReturn( NUM_DATE_PATTERN_SHORT );
      
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
              } )
              .anyTimes();
      
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
              } )
              .anyTimes();
      
      EasyMock.expect( dateFormatter.formatDateNumeric( EasyMock.anyLong() ) )
              .andAnswer( new IAnswer< String >()
              {
                 @Override
                 public String answer() throws Throwable
                 {
                    final long millis = (Long) EasyMock.getCurrentArguments()[ 0 ];
                    return new SimpleDateFormat( NUM_DATE_TIME_PATTERN ).format( new Date( millis ) );
                 }
              } )
              .anyTimes();
      
      EasyMock.replay( dateFormatter );
      
      return dateFormatter;
   }
}
