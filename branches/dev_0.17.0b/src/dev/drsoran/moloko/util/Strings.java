/*
 * Copyright (c) 2010 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.util;

import android.text.Editable;
import android.text.TextUtils;


public final class Strings
{
   public final static String EMPTY_STRING = "";
   
   public final static String NULL = "null";
   
   
   
   private Strings()
   {
      throw new AssertionError( "This class should not be instantiated." );
   }
   
   
   
   public final static String getTrimmed( Editable editable )
   {
      return editable.toString().trim();
   }
   
   
   
   public final static String emptyIfNull( String string )
   {
      return ( string == null ) ? EMPTY_STRING : string;
   }
   
   
   
   public final static CharSequence emptyIfNull( CharSequence string )
   {
      return ( string == null ) ? EMPTY_STRING : string;
   }
   
   
   
   public final static String nullIfEmpty( String string )
   {
      return ( TextUtils.isEmpty( string ) ) ? null : string;
   }
   
   
   
   public final static CharSequence nullIfEmpty( CharSequence string )
   {
      return ( string == null || string.length() == 0 ) ? null : string;
   }
   
   
   
   public final static Object convertTo( String value, Class< ? > valueClass )
   {
      if ( value.equalsIgnoreCase( NULL ) )
      {
         return null;
      }
      
      else if ( valueClass == String.class )
      {
         return value;
      }
      
      else if ( valueClass == Long.class )
      {
         return Long.parseLong( value );
      }
      
      else if ( valueClass == Integer.class )
      {
         return Integer.parseInt( value );
      }
      
      else if ( valueClass == Boolean.class )
      {
         return Boolean.parseBoolean( value );
      }
      
      else if ( valueClass == Float.class )
      {
         return Float.parseFloat( value );
      }
      
      else if ( valueClass == Double.class )
      {
         return Double.parseDouble( value );
      }
      
      throw new IllegalArgumentException( "The type " + valueClass.getName()
         + " is not supported" );
   }
}
