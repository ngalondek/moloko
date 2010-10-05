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

package dev.drsoran.moloko.service.sync.util;

import android.util.Log;


public final class ParamChecker
{
   public final static boolean checkParams( String tag,
                                            String prequel,
                                            Class< ? >[] types,
                                            Object... params )
   {
      boolean ok = types != null;
      
      if ( !ok )
         Log.e( tag, "Cannot check null parametes." );
      
      if ( ok && types.length != params.length )
      {
         ok = false;
         Log.e( tag,
                new StringBuilder( prequel ).append( "Invalid number of parameters supplied. Expected " )
                                            .append( types.length )
                                            .append( " found " )
                                            .append( params.length )
                                            .toString() );
      }
      
      for ( int i = 0; ok && i < types.length; i++ )
      {
         final Class< ? > type = types[ i ];
         final Object object = params[ i ];
         
         if ( !type.isInstance( object ) )
         {
            ok = false;
            Log.e( tag,
                   new StringBuilder( prequel ).append( "Invalid parameter type supplied for parameter " )
                                               .append( i )
                                               .append( ". Expected " )
                                               .append( type.getName() )
                                               .append( " found " )
                                               .append( object.getClass()
                                                              .getName() )
                                               .toString() );
         }
      }
      
      return ok;
   }
}
