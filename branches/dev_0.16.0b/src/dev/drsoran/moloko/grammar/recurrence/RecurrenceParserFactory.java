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
   private final static List< Class< ? extends IRecurrenceParser > > availableParserClasses;
   
   static
   {
      availableParserClasses = new ArrayList< Class< ? extends IRecurrenceParser >>();
      
      try
      {
         availableParserClasses.add( dev.drsoran.moloko.grammar.recurrence.RecurrenceParserImpl.class );
         availableParserClasses.add( dev.drsoran.moloko.grammar.recurrence.de.RecurrenceParserImpl.class );
      }
      catch ( Throwable e )
      {
         
      }
   }
   
   

   private RecurrenceParserFactory()
   {
      throw new AssertionError();
   }
   


   public final static IRecurrenceParser createRecurrenceParserForLocale( Locale locale )
   {
      for ( Class< ? > parserClass : availableParserClasses )
      {
         try
         {
            final Locale parserLocale = (Locale) parserClass.getField( "LOCALE" )
                                                            .get( null );
            
            if ( parserLocale.hashCode() == locale.hashCode()
               || parserLocale.getLanguage()
                              .equalsIgnoreCase( locale.getLanguage() ) )
               return createParser( parserClass );
         }
         catch ( Throwable e )
         {
         }
      }
      
      return createDefaultRecurrenceParser();
   }
   


   public static IRecurrenceParser createDefaultRecurrenceParser()
   {
      return createParser( availableParserClasses.get( 0 ) );
   }
   


   public final static List< IRecurrenceParser > getAvailableRecurrenceParsers()
   {
      final List< IRecurrenceParser > availableParsers = new ArrayList< IRecurrenceParser >( availableParserClasses.size() );
      
      for ( Class< ? > parserClass : availableParserClasses )
      {
         final IRecurrenceParser parserInstance = createParser( parserClass );
         if ( parserInstance != null )
            availableParsers.add( parserInstance );
      }
      
      return availableParsers;
   }
   


   private final static IRecurrenceParser createParser( Class< ? > parserClass )
   {
      try
      {
         return (IRecurrenceParser) parserClass.newInstance();
      }
      catch ( Throwable e )
      {
         return null;
      }
   }
}
