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

import org.easymock.EasyMock;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.domain.parsing.GrammarException;
import dev.drsoran.moloko.domain.parsing.IDateTimeParsing;


public class TestDateTimeParsing
{
   private TestDateTimeParsing()
   {
   }
   
   
   
   public static IDateTimeParsing get( MolokoCalendar parsedDateTime )
   {
      final IDateTimeParsing dateTimeParsing = EasyMock.createNiceMock( IDateTimeParsing.class );
      try
      {
         EasyMock.expect( dateTimeParsing.parseDateTime( EasyMock.anyObject( String.class ) ) )
                 .andReturn( parsedDateTime )
                 .anyTimes();
      }
      catch ( GrammarException e )
      {
      }
      
      EasyMock.replay( dateTimeParsing );
      
      return dateTimeParsing;
   }
}
