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

package dev.drsoran.rtm.parsing.lang.de;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import dev.drsoran.rtm.parsing.lang.ILanguage;


public class DateLanguage implements ILanguage
{
   @Override
   public Locale getLocale()
   {
      return Locale.GERMAN;
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
         case 'a':
         {
            switch ( string.charAt( 1 ) )
            {
               case 'c':
                  return 8;
               case 'p':
                  return Calendar.APRIL;
               case 'u':
                  return Calendar.AUGUST;
            }
         }
         
         case 'd':
         {
            switch ( string.charAt( 1 ) )
            {
               case 'r':
                  return 3;
               case 'e':
                  return Calendar.DECEMBER;
               case 'i':
                  return Calendar.TUESDAY;
               case 'o':
                  return Calendar.THURSDAY;
            }
         }
         
         case 'e':
            return 1;
            
         case 'f':
         {
            switch ( string.charAt( 1 ) )
            {
               case 'u':
               case 'ü':
                  return 5;
               case 'e':
                  return Calendar.FEBRUARY;
               case 'r':
                  return Calendar.FRIDAY;
            }
         }
         
         case 'j':
         {
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
         }
         
         case 'm':
         {
            switch ( string.charAt( 1 ) )
            {
               case 'ä':
                  return Calendar.MARCH;
               case 'o':
                  return Calendar.MONDAY;
               case 'i':
                  return Calendar.WEDNESDAY;
               case 'a':
                  switch ( string.charAt( 2 ) )
                  {
                     case 'e':
                     case 'r':
                        return Calendar.MARCH;
                     case 'i':
                        return Calendar.MAY;
                  }
            }
         }
         
         case 'n':
            switch ( string.charAt( 1 ) )
            {
               case 'e':
                  return 9;
                  
               case 'o':
                  return Calendar.NOVEMBER;
            }
            
         case 'o':
            return Calendar.OCTOBER;
            
         case 's':
         {
            switch ( string.charAt( 1 ) )
            {
               case 'e':
                  switch ( string.charAt( 2 ) )
                  {
                     case 'c':
                        return 6;
                     case 'p':
                        return Calendar.SEPTEMBER;
                  }
               case 'i':
                  return 7;
               case 'a':
                  return Calendar.SATURDAY;
               case 'o':
                  return Calendar.SUNDAY;
            }
         }
         
         case 'v':
            return 4;
            
         case 'z':
         {
            switch ( string.charAt( 1 ) )
            {
               case 'w':
                  return 2;
               case 'e':
                  return 10;
            }
         }
         
         default :
            break;
      }
      
      return -1;
   }
}
