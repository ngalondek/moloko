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

package dev.drsoran.moloko.grammar.recurrence;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public final class RecurrenceParserFactory
{
   private final static String GRAMMAR_RECURRENCE_PACKAGE_NAME = "dev.drsoran.moloko.grammar.recurrence";
   
   private final static Class< ? extends IRecurrenceParser >[] availableParsers =
   { dev.drsoran.moloko.grammar.recurrence.RecurrenceParserImpl.class,
    dev.drsoran.moloko.grammar.recurrence.de.RecurrenceParserImpl.class };
   
   

   private RecurrenceParserFactory()
   {
      throw new AssertionError();
   }
   


   public final static IRecurrenceParser createRecurrenceParserForLocale( Locale locale )
   {
      List< IRecurrenceParser > parsers = getAvailableRecurrenceParsers();
      
      for ( IRecurrenceParser iRecurrenceParser : parsers )
      {
         if ( iRecurrenceParser.getLocale().hashCode() == locale.hashCode()
            || iRecurrenceParser.getLocale()
                                .getLanguage()
                                .equalsIgnoreCase( locale.getLanguage() ) )
            return iRecurrenceParser;
      }
      
      return createDefaultRecurrenceParser();
   }
   


   public static IRecurrenceParser createDefaultRecurrenceParser()
   {
      return getParserInstanceForPackage( GRAMMAR_RECURRENCE_PACKAGE_NAME );
   }
   


   public final static List< IRecurrenceParser > getAvailableRecurrenceParsers()
   {
      if ( availableParsers == null )
         enumerateAvailableParsers();
      
      return availableParsers;
   }
   


   private final static void enumerateAvailableParsers()
   {
      availableParsers = new ArrayList< IRecurrenceParser >();
      
      final Package[] allPackages = Package.getPackages();
      
      if ( allPackages != null )
      {
         for ( int i = 0, cnt = allPackages.length; i < cnt; ++i )
         {
            final Package pack = allPackages[ i ];
            if ( pack.getName().startsWith( GRAMMAR_RECURRENCE_PACKAGE_NAME ) )
            {
               final IRecurrenceParser parser = getParserInstanceForPackage( pack.getName() );
               if ( parser != null )
                  availableParsers.add( parser );
            }
         }
      }
   }
   


   private final static IRecurrenceParser getParserInstanceForPackage( String packageName )
   {
      IRecurrenceParser instance = null;
      
      try
      {
         final Class< ? > classForPackage = Class.forName( packageName
            + ".RecurrenceParserImpl" );
         
         final Object o = classForPackage.newInstance();
         
         if ( o instanceof IRecurrenceParser )
            instance = (IRecurrenceParser) o;
      }
      catch ( Throwable e )
      {
      }
      
      return instance;
   }
}
