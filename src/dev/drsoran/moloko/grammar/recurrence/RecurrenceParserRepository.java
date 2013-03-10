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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.grammar.IDateFormatter;
import dev.drsoran.moloko.grammar.IDateTimeParsing;
import dev.drsoran.moloko.grammar.ILocalizedParser;
import dev.drsoran.moloko.grammar.LazyParserInstanceIterator;


public class RecurrenceParserRepository implements IRecurrenceParserRepository
{
   private final static List< Class< ? extends IRecurrenceParser > > RecurrenceParserClasses = new ArrayList< Class< ? extends IRecurrenceParser >>( 2 );
   
   private final List< IRecurrenceParser > recurrenceParserInstances = new ArrayList< IRecurrenceParser >();
   
   private final IRecurrencePatternParser recurrencePatternParser;
   
   private final IDateTimeParsing dateTimeParsing;
   
   static
   {
      RecurrenceParserClasses.add( dev.drsoran.moloko.grammar.recurrence.RecurrenceParserImpl.class );
      RecurrenceParserClasses.add( dev.drsoran.moloko.grammar.recurrence.de.RecurrenceParserImpl.class );
   }
   
   
   
   public RecurrenceParserRepository( ILog log, IDateFormatter dateFormatter,
      IRecurrenceSentenceLanguage recurrenceSentenceLanguage,
      IDateTimeParsing dateTimeParsing )
   {
      this.dateTimeParsing = dateTimeParsing;
      this.recurrencePatternParser = new RecurrencePatternParserImpl( log,
                                                                      dateFormatter,
                                                                      recurrenceSentenceLanguage );
   }
   
   
   
   @Override
   public Iterable< IRecurrenceParser > getRecurrenceParsers()
   {
      return new LazyParserInstanceIterator< IRecurrenceParser >( RecurrenceParserClasses,
                                                                  recurrenceParserInstances,
                                                                  dateTimeParsing );
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
      return hasParserClassWithLocale( RecurrenceParserClasses, locale );
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
   
   
   
   private final static boolean equalLocales( Locale locale1, Locale locale2 )
   {
      return locale1.hashCode() == locale2.hashCode()
         || locale1.getLanguage().equalsIgnoreCase( locale2.getLanguage() );
   }
   
   
   
   private final < T > boolean hasParserClassWithLocale( Iterable< Class< ? extends T > > classes,
                                                         Locale locale )
   {
      for ( Class< ? extends T > clazz : classes )
      {
         if ( equalLocales( locale, getLocale( clazz ) ) )
         {
            return true;
         }
      }
      
      return false;
   }
   
   
   
   private final < T > Locale getLocale( Class< T > clazz )
   {
      try
      {
         return (Locale) clazz.getField( "LOCALE" ).get( null );
      }
      catch ( IllegalArgumentException e )
      {
         throw new RuntimeException( e );
      }
      catch ( IllegalAccessException e )
      {
         throw new RuntimeException( e );
      }
      catch ( NoSuchFieldException e )
      {
         throw new RuntimeException( e );
      }
   }
}
