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

public enum CloudEntryType
{
   Tag( 0 ), TasksList( 1 ), Location( 2 );
   
   private final int value;
   
   
   
   private CloudEntryType( int value )
   {
      this.value = value;
   }
   
   
   
   public int getValue()
   {
      return value;
   }
   
   
   
   public static CloudEntryType fromValue( int value )
   {
      switch ( value )
      {
         case 0:
            return Tag;
         case 1:
            return TasksList;
         case 2:
            return Location;
         default :
            throw new IllegalArgumentException( "value" );
      }
   }
}
