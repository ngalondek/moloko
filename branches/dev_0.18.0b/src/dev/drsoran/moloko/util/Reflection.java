/* 
 *	Copyright (c) 2012 Ronny Röhricht
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

package dev.drsoran.moloko.util;

import java.lang.reflect.Method;


public final class Reflection
{
   public final static < T > Method findMethod( Class< T > clazz, String name )
   {
      Method method = null;
      
      final Method[] methods = clazz.getMethods();
      
      for ( int i = 0; i < methods.length && method == null; i++ )
      {
         if ( methods[ i ].getName().equals( name ) )
            method = methods[ i ];
      }
      
      if ( method == null )
      {
         throw new RuntimeException( "The class " + clazz.getName()
            + " does not has a method " + name );
      }
      
      return method;
   }
   
}
