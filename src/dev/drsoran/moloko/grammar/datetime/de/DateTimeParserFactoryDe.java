/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.moloko.grammar.datetime.de;

import java.util.Locale;

import dev.drsoran.moloko.grammar.datetime.DateParserImpl;
import dev.drsoran.moloko.grammar.datetime.IDateParser;
import dev.drsoran.moloko.grammar.datetime.IDateTimeParserFactory;
import dev.drsoran.moloko.grammar.datetime.ITimeParser;
import dev.drsoran.moloko.grammar.datetime.TimeParserImpl;


public class DateTimeParserFactoryDe implements IDateTimeParserFactory
{
   @Override
   public IDateParser createDateParser()
   {
      return new DateParserImpl( Locale.GERMAN,
                                 new DateParser(),
                                 new DateLexer() );
   }
   
   
   
   @Override
   public ITimeParser createTimeParser()
   {
      return new TimeParserImpl( Locale.GERMAN,
                                 new TimeParser(),
                                 new TimeLexer() );
   }
   
   
   
   @Override
   public Locale getParserLocale()
   {
      return Locale.GERMAN;
   }
}
