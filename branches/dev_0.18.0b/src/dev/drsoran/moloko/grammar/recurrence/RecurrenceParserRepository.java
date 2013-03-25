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

package dev.drsoran.moloko.grammar.recurrence;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.grammar.IDateFormatter;
import dev.drsoran.moloko.grammar.IDateTimeParsing;
import dev.drsoran.moloko.grammar.ILocalizedParser;
import dev.drsoran.moloko.grammar.LazyParserInstanceIterator;
import dev.drsoran.moloko.grammar.recurrence.de.RecurrenceParserFactoryDe;
import dev.drsoran.moloko.util.Reflection;


public class RecurrenceParserRepository implements IRecurrenceParserRepository
{
   private final static Method FACTORY_METHOD;
   
   private final List< IRecurrenceParserFactory > factories;
   
   private final List< IRecurrenceParser > recurrenceParserInstances = new ArrayList< IRecurrenceParser >();
   
   private final IRecurrencePatternParser recurrencePatternParser;
   
   static
   {
      FACTORY_METHOD = Reflection.findMethod( IRecurrenceParserFactory.class,
                                              "createRecurrenceParser" );
      
   }
   
   
   
   public RecurrenceParserRepository( ILog log, IDateFormatter dateFormatter,
      IRecurrenceSentenceLanguage recurrenceSentenceLanguage,
      IDateTimeParsing dateTimeParsing )
   {
      factories = new ArrayList< IRecurrenceParserFactory >( 2 );
      
      factories.add( new RecurrenceParserFactoryEn( dateTimeParsing ) );
      factories.add( new RecurrenceParserFactoryDe( dateTimeParsing ) );
      
      recurrencePatternParser = new RecurrencePatternParserImpl( log,
                                                                 dateFormatter,
                                                                 recurrenceSentenceLanguage );
   }
   
   
   
   @Override
   public Iterable< IRecurrenceParser > getRecurrenceParsers()
   {
      return new LazyParserInstanceIterator< IRecurrenceParser >( factories,
                                                                  FACTORY_METHOD,
                                                                  recurrenceParserInstances );
   }
   
   
   
   @Override
   public IRecurrenceParser getRecurrenceParser( Locale locale )
   {
      return getMatchingParser( locale, getRecurrenceParsers() );
   }
   
   
   
   @Override
   public IRecurrenceParser getDefaultRecurrenceParser()
   {
      return getRecurrenceParsers().iterator().next();
   }
   
   
   
   @Override
   public IRecurrencePatternParser getRecurrencePatternParser()
   {
      return recurrencePatternParser;
   }
   
   
   
   @Override
   public boolean existsRecurrenceParser( Locale locale )
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
      for ( IRecurrenceParserFactory factory : factories )
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
