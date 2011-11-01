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

package dev.drsoran.moloko.grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public abstract class AbstractParserFactory
{
   
   protected AbstractParserFactory()
   {
      throw new AssertionError();
   }
   
   
   
   protected final static < T > T createParserForLocale( Locale locale,
                                                         List< Class< ? extends T > > availableParserClasses )
   {
      for ( Class< ? extends T > parserClass : availableParserClasses )
      {
         final Locale parserLocale = getParserLocale( parserClass );
         
         if ( parserLocale != null && equalLocales( parserLocale, locale ) )
         {
            return createParser( parserClass );
         }
      }
      
      return createDefaultParser( availableParserClasses );
   }
   
   
   
   protected static < T > T createDefaultParser( List< Class< ? extends T > > availableParserClasses )
   {
      if ( availableParserClasses.size() > 0 )
         return createParser( availableParserClasses.get( 0 ) );
      else
         return null;
   }
   
   
   
   protected final static < T > List< T > getAvailableParsers( List< Class< ? extends T > > availableParserClasses )
   {
      final List< T > availableParsers = new ArrayList< T >( availableParserClasses.size() );
      
      for ( Class< ? extends T > parserClass : availableParserClasses )
      {
         final T parserInstance = createParser( parserClass );
         if ( parserInstance != null )
            availableParsers.add( parserInstance );
      }
      
      return availableParsers;
   }
   
   
   
   protected final static < T > Locale getDefaultParserLocale( List< Class< ? extends T > > availableParserClasses )
   {
      if ( availableParserClasses.size() > 0 )
      {
         return getParserLocale( availableParserClasses.get( 0 ) );
      }
      
      return null;
   }
   
   
   
   protected final static < T > Locale getNearestParserLocale( Locale refLocale,
                                                               List< Class< ? extends T > > availableParserClasses )
   {
      Locale nearestLocale = null;
      
      if ( availableParserClasses.size() > 0 )
      {
         for ( Class< ? extends T > parserClass : availableParserClasses )
         {
            final Locale parserLocale = getParserLocale( parserClass );
            
            if ( parserLocale != null && equalLocales( parserLocale, refLocale ) )
            {
               nearestLocale = parserLocale;
            }
         }
         
         if ( nearestLocale == null )
            nearestLocale = getDefaultParserLocale( availableParserClasses );
      }
      
      return nearestLocale;
   }
   
   
   
   protected final static < T > List< Locale > getAvailableParserLocales( List< Class< ? extends T > > availableParserClasses )
   {
      List< Locale > locales = new ArrayList< Locale >( availableParserClasses.size() );
      
      for ( Class< ? extends T > parserClass : availableParserClasses )
      {
         final Locale parserLocale = getParserLocale( parserClass );
         
         if ( parserLocale != null )
            locales.add( parserLocale );
      }
      
      return locales;
   }
   
   
   
   private final static boolean equalLocales( Locale locale1, Locale locale2 )
   {
      return locale1.hashCode() == locale2.hashCode()
         || locale1.getLanguage().equalsIgnoreCase( locale2.getLanguage() );
   }
   
   
   
   private final static < T > Locale getParserLocale( Class< ? extends T > parserClass )
   {
      try
      {
         return (Locale) parserClass.getField( "LOCALE" ).get( null );
      }
      catch ( Throwable e )
      {
         return null;
      }
   }
   
   
   
   private final static < T > T createParser( Class< ? extends T > parserClass )
   {
      try
      {
         return parserClass.newInstance();
      }
      catch ( Throwable e )
      {
         return null;
      }
   }
}
