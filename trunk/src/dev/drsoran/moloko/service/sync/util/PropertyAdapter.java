/*
Copyright (c) 2010 Ronny Röhricht   

This file is part of Moloko.

Moloko is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Moloko is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Moloko.  If not, see <http://www.gnu.org/licenses/>.

Contributors:
	Ronny Röhricht - implementation
*/

package dev.drsoran.moloko.service.sync.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class PropertyAdapter< T1, T2 >
{
   private final Method t1PropertyGetter;
   
   private final Method t2PropertyGetter;
   
   

   public PropertyAdapter( Method t1Get, Method t2Get )
   {
      this.t1PropertyGetter = t1Get;
      this.t2PropertyGetter = t2Get;
   }
   


   public boolean equals( T1 instance1, T2 instance2 ) throws InvocationTargetException
   {
      try
      {
         return t1PropertyGetter.invoke( instance1, (Object[]) null )
                                .equals( t2PropertyGetter.invoke( instance2,
                                                                  (Object[]) null ) );
      }
      catch ( IllegalArgumentException e )
      {
         throw new InvocationTargetException( e );
      }
      catch ( IllegalAccessException e )
      {
         throw new InvocationTargetException( e );
      }
   }
}
