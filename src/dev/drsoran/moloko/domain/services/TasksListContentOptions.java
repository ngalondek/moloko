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

package dev.drsoran.moloko.domain.services;

public enum TasksListContentOptions
{
   None( 0 ), WithTaskCount( 1 << 0 ), Minimal( None.getValue() ), Complete(
      WithTaskCount.getValue() );
   
   private int value;
   
   
   
   TasksListContentOptions( int value )
   {
      this.value = value;
   }
   
   
   
   public int getValue()
   {
      return value;
   }
   
   
   
   public boolean hasFlag( TasksListContentOptions flag )
   {
      return ( value & flag.value ) == flag.value;
   }
   
   
   
   public TasksListContentOptions or( TasksListContentOptions flag )
   {
      value |= flag.value;
      return this;
   }
}
