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

package dev.drsoran.rtm.parsing.lang;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class DateLanguage implements ILanguage
{
   @Override
   public Locale getLocale()
   {
      return Locale.ENGLISH;
   }
   
   
   
   @Override
   public int getInteger( String key )
   {
      return keyToNumber( key );
   }
   
   
   
   @Override
   public String getString( String key )
   {
      return String.valueOf( getInteger( key ) );
   }
   
   
   
   @Override
   public String getPluralString( String key, String unit, int qty )
   {
      return getString( key );
   }
   
   
   
   @Override
   public String getPluralString( String key, String unit, String qty )
   {
      return getString( key );
   }
   
   
   
   @Override
   public List< String > getStrings( String key )
   {
      return Collections.singletonList( getString( key ) );
   }
   
   
   
   private int keyToNumber( String key )
   {
      final String string = key.toLowerCase( getLocale() );
      
      switch ( string.charAt( 0 ) )
      {
         case 'o':
         {
            switch ( string.charAt( 1 ) )
            {
               case 'n':
                  return 1;
               case 'c':
                  return Calendar.OCTOBER;
            }
         }
         case 't':
         {
            switch ( string.charAt( 1 ) )
            {
               case 'e':
                  return 10;
               case 'u':
                  return Calendar.TUESDAY;
               case 'w':
                  return 2;
               case 'h':
                  switch ( string.charAt( 2 ) )
                  {
                     case 'u':
                        return Calendar.THURSDAY;
                     case 'r':
                        return 3;
                  }
            }
         }
         case 'f':
         {
            switch ( string.charAt( 1 ) )
            {
               case 'o':
                  return 4;
               case 'i':
                  return 5;
               case 'e':
                  return Calendar.FEBRUARY;
               case 'r':
                  return Calendar.FRIDAY;
            }
         }
         case 's':
         {
            switch ( string.charAt( 1 ) )
            {
               case 'i':
                  return 6;
               case 'e':
                  switch ( string.charAt( 2 ) )
                  {
                     case 'v':
                        return 7;
                     case 'p':
                        return Calendar.SEPTEMBER;
                  }
               case 'a':
                  return Calendar.SATURDAY;
               case 'u':
                  return Calendar.SUNDAY;
            }
         }
         case 'e':
            return 8;
         case 'n':
            switch ( string.charAt( 1 ) )
            {
               case 'i':
                  return 9;
               case 'o':
                  return Calendar.NOVEMBER;
            }
         case 'm':
            switch ( string.charAt( 1 ) )
            {
               case 'o':
                  return Calendar.MONDAY;
               case 'a':
                  switch ( string.charAt( 2 ) )
                  {
                     case 'r':
                        return Calendar.MARCH;
                     case 'y':
                        return Calendar.MAY;
                  }
            }
         case 'j':
            switch ( string.charAt( 1 ) )
            {
               case 'a':
                  return Calendar.JANUARY;
               default :
                  switch ( string.charAt( 2 ) )
                  {
                     case 'n':
                        return Calendar.JUNE;
                     case 'l':
                        return Calendar.JULY;
                  }
            }
         case 'a':
            switch ( string.charAt( 1 ) )
            {
               case 'p':
                  return Calendar.APRIL;
               case 'u':
                  return Calendar.AUGUST;
            }
         case 'w':
            return Calendar.WEDNESDAY;
         case 'd':
            return Calendar.DECEMBER;
            
         default :
            break;
      }
      
      return -1;
   }
}
