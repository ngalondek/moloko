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

package dev.drsoran.moloko.grammar.datetime;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dev.drsoran.moloko.grammar.ILocalizedParser;
import dev.drsoran.moloko.grammar.LazyParserInstanceIterator;
import dev.drsoran.moloko.grammar.datetime.de.DateTimeParserFactoryDe;
import dev.drsoran.moloko.util.Reflection;


public class DateTimeParserRepository implements IDateTimeParserRepository
{
   private final static List< IDateTimeParserFactory > FACTORIES;
   
   private final static Method FACTORY_METHOD_DATE_PARSER;
   
   private final static Method FACTORY_METHOD_TIME_PARSER;
   
   private final List< IDateParser > dateParserInstances = new ArrayList< IDateParser >();
   
   private final List< ITimeParser > timeParserInstances = new ArrayList< ITimeParser >();
   
   static
   {
      FACTORIES = new ArrayList< IDateTimeParserFactory >( 2 );
      
      FACTORIES.add( new DateTimeParserFactoryEn() );
      FACTORIES.add( new DateTimeParserFactoryDe() );
      
      FACTORY_METHOD_DATE_PARSER = Reflection.findMethod( IDateTimeParserFactory.class,
                                                          "createDateParser" );
      
      FACTORY_METHOD_TIME_PARSER = Reflection.findMethod( IDateTimeParserFactory.class,
                                                          "createTimeParser" );
   }
   
   
   
   @Override
   public Iterable< IDateParser > getDateParsers()
   {
      return new LazyParserInstanceIterator< IDateParser >( FACTORIES,
                                                            FACTORY_METHOD_DATE_PARSER,
                                                            dateParserInstances );
   }
   
   
   
   @Override
   public Iterable< ITimeParser > getTimeParsers()
   {
      return new LazyParserInstanceIterator< ITimeParser >( FACTORIES,
                                                            FACTORY_METHOD_TIME_PARSER,
                                                            timeParserInstances );
   }
   
   
   
   @Override
   public IDateParser getDateParser( Locale locale )
   {
      return getMatchingParser( locale, getDateParsers() );
   }
   
   
   
   @Override
   public ITimeParser getTimeParser( Locale locale )
   {
      return getMatchingParser( locale, getTimeParsers() );
   }
   
   
   
   @Override
   public IDateParser getDefaultDateParser()
   {
      return getDateParsers().iterator().next();
   }
   
   
   
   @Override
   public ITimeParser getDefaultTimeParser()
   {
      return getTimeParsers().iterator().next();
   }
   
   
   
   @Override
   public boolean existsDateParserForLocale( Locale locale )
   {
      return hasParserClassWithLocale( locale );
   }
   
   
   
   @Override
   public boolean existsTimeParserForLocale( Locale locale )
   {
      return hasParserClassWithLocale( locale );
   }
   
   
   
   private < T extends ILocalizedParser > T getMatchingParser( Locale locale,
                                                               Iterable< T > parsers )
   {
      for ( T parser : parsers )
      {
         if ( equalLocales( parser.getLocale(), locale ) )
         {
            return parser;
         }
      }
      
      return null;
   }
   
   
   
   private < T > boolean hasParserClassWithLocale( Locale locale )
   {
      for ( IDateTimeParserFactory factory : FACTORIES )
      {
         if ( equalLocales( locale, factory.getParserLocale() ) )
         {
            return true;
         }
      }
      
      return false;
   }
   
   
   
   private static boolean equalLocales( Locale locale1, Locale locale2 )
   {
      return locale1.hashCode() == locale2.hashCode()
         || locale1.getLanguage().equalsIgnoreCase( locale2.getLanguage() );
   }
}
