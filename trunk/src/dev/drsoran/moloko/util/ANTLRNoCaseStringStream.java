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

package dev.drsoran.moloko.util;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;


public class ANTLRNoCaseStringStream extends ANTLRStringStream
{
   public ANTLRNoCaseStringStream( String string )
   {
      super( string );
   }
   


   @Override
   public int LA( int i )
   {
      if ( i == 0 )
      {
         return 0; // undefined
      }
      
      else if ( i < 0 )
      {
         i++; // e.g., translate LA(-1) to use offset 0
      }
      
      if ( ( p + i - 1 ) >= n )
      {         
         return CharStream.EOF;
      }
      
      return Character.toLowerCase( data[ p + i - 1 ] );
   }
}
