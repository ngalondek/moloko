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

import java.util.Locale;


public final class RecurrenceParserFactory
{
   private final static String GRAMMAR_PACKAGE_NAME = "dev.drsoran.moloko.grammar";
   
   

   private RecurrenceParserFactory()
   {
      throw new AssertionError();
   }
   


   public final static IRecurrenceParser createRecurrenceParserForLocale( Locale locale )
   {
      IRecurrenceParser parser;
      
      final String langCodePlusVariant = getLangCodeAndVariant( locale );
      
      Class packageForLocale = Package.getPackage( GRAMMAR_PACKAGE_NAME + "."
         + langCodePlusVariant );
      
      if ( packageForLocale == null )
         packageForLocale = Package.getPackage( GRAMMAR_PACKAGE_NAME + "."
            + locale.getLanguage() );
      
      if ( packageForLocale == null )
         packageForLocale = getBestMatchingPackageForLangCode( locale.getLanguage() );
      
      if ( packageForLocale == null )
      {
         parser = new dev.drsoran.moloko.grammar.en.RecurrenceParserImpl();
      }
      else
      {
         
      }
      
      return parser;
   }
   


   private final static String getLangCodeAndVariant( Locale locale )
   {
      return ( locale.getLanguage() + "_" + locale.getVariant() ).toLowerCase();
   }
   


   private static Package getBestMatchingPackageForLangCode( String langCode )
   {
      Package bestMatchingPackage = null;
      final Package[] allPackages = Package.getPackages();
      
      if ( allPackages != null )
      {
         for ( int i = 0, cnt = allPackages.length; i < cnt
            && bestMatchingPackage == null; ++i )
         {
            final Package pack = allPackages[ i ];
            if ( pack.getName().startsWith( GRAMMAR_PACKAGE_NAME + "."
               + langCode ) )
            {
               bestMatchingPackage = pack;
            }
         }
      }
      
      return bestMatchingPackage;
   }
}
