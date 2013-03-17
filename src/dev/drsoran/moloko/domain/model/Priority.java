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

package dev.drsoran.moloko.domain.model;

import android.text.TextUtils;


public enum Priority
{
   High( '1' ), Medium( '2' ), Low( '3' ), None( 'n' );
   
   private final char value;
   
   
   
   private Priority( char value )
   {
      this.value = value;
   }
   
   
   
   @Override
   public String toString()
   {
      return String.valueOf( value );
   }
   
   
   
   public final static Priority fromString( String priorityString )
   {
      if ( TextUtils.isEmpty( priorityString ) )
      {
         return Priority.None;
      }
      
      switch ( priorityString.charAt( 0 ) )
      {
         case 'n':
         case 'N':
            return Priority.None;
            
         case '3':
            return Priority.Low;
            
         case '2':
            return Priority.Medium;
            
         case '1':
            return Priority.High;
            
         default :
            throw new IllegalArgumentException( priorityString + "is no valid "
               + Priority.class.getName() );
      }
   }
}
