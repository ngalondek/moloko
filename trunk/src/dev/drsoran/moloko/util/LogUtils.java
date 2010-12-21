/* 
 *	Copyright (c) 2010 Ronny Röhricht
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

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import dev.drsoran.moloko.R;


public final class LogUtils
{
   private LogUtils()
   {
      throw new AssertionError( "This class should not be instantiated." );
   }
   


   public final static < T > String toTag( Class< T > c )
   {
      return "Moloko." + c.getSimpleName();
   }
   


   public final static void logDBError( Context context,
                                        String tag,
                                        String entity )
   {
      logDBError( context, tag, entity, null );
   }
   


   public final static void logDBError( Context context,
                                        String tag,
                                        String entity,
                                        Exception e )
   {
      final String message = context.getString( R.string.err_db_typed, entity );
      
      if ( e != null )
         Log.e( tag, message, e );
      else
         Log.e( tag, message );
      
      Toast.makeText( context, message, Toast.LENGTH_SHORT );
   }
}
