/* 
 *	Copyright (c) 2011 Ronny Röhricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.grammar.datetime;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dev.drsoran.moloko.grammar.AbstractParserFactory;


public final class DateParserFactory extends AbstractParserFactory
{
   private final static List< Class< ? extends IDateParser > > availableParserClasses = new ArrayList< Class< ? extends IDateParser >>();
   
   static
   {
      availableParserClasses.add( dev.drsoran.moloko.grammar.datetime.DateParserImpl.class );
      availableParserClasses.add( dev.drsoran.moloko.grammar.datetime.de.DateParserImpl.class );
   }
   
   
   
   public final static IDateParser createDateParserForLocale( Locale locale )
   {
      return createParserForLocale( locale, availableParserClasses );
   }
   
   
   
   public static IDateParser createDefaultDateParser()
   {
      return createDefaultParser( availableParserClasses );
   }
   
   
   
   public final static List< IDateParser > getAvailableDateParsers()
   {
      return getAvailableParsers( availableParserClasses );
   }
}
