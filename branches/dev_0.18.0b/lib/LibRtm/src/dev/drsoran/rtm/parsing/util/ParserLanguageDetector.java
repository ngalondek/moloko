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

package dev.drsoran.rtm.parsing.util;

import java.util.Locale;

import org.antlr.v4.runtime.misc.ParseCancellationException;

import dev.drsoran.rtm.Lambda.Func1;


public class ParserLanguageDetector< TParser >
{
   private final Iterable< Locale > availableLocales;
   
   private Locale lastUsedLocale;
   
   
   
   public ParserLanguageDetector( Iterable< Locale > availableLocales )
   {
      if ( availableLocales == null )
      {
         throw new IllegalArgumentException( "availableLocales" );
      }
      
      this.availableLocales = availableLocales;
   }
   
   
   
   public < TResult > TResult detectLanguageAndParse( Func1< Locale, TResult > parserFunc )
   {
      TResult result = null;
      
      if ( lastUsedLocale != null )
      {
         result = TryParse( parserFunc, lastUsedLocale );
      }
      
      if ( result != null )
      {
         return result;
      }
      else
      {
         // If parsing with the current parser fails, we don't need to try it again.
         final Locale dontTryLocale = lastUsedLocale;
         
         for ( Locale locale : availableLocales )
         {
            if ( locale != dontTryLocale )
            {
               result = TryParse( parserFunc, locale );
               if ( result != null )
               {
                  lastUsedLocale = locale;
                  return result;
               }
            }
         }
      }
      
      return null;
   }
   
   
   
   private static < TParser, TResult > TResult TryParse( Func1< Locale, TResult > parserFunc,
                                                         Locale locale )
   {
      TResult result = null;
      
      try
      {
         result = parserFunc.call( locale );
      }
      catch ( ParseCancellationException e )
      {
      }
      
      return result;
   }
}
