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

package dev.drsoran;

public final class Compare
{
   private Compare()
   {
      throw new AssertionError();
   }
   
   
   
   public static < V > boolean isDifferent( V oldVal, V newVal )
   {
      return ( oldVal == null && newVal != null )
         || ( oldVal != null && ( newVal == null || !oldVal.equals( newVal ) ) );
   }
   
   
   
   public static boolean isDifferent( int oldVal, int newVal )
   {
      return oldVal != newVal;
   }
   
   
   
   public static boolean isDifferent( long oldVal, long newVal )
   {
      return oldVal != newVal;
   }
   
   
   
   public static boolean isDifferent( double oldVal, double newVal )
   {
      return oldVal != newVal;
   }
   
   
   
   public static boolean isDifferent( float oldVal, float newVal )
   {
      return oldVal != newVal;
   }
   
   
   
   public static boolean isDifferent( boolean oldVal, boolean newVal )
   {
      return oldVal != newVal;
   }
}
