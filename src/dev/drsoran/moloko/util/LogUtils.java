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
import android.widget.Toast;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;


public final class LogUtils
{
   // TODO: Check where this can be used more often.
   public final static String GENERIC_DB_ERROR = "Error during DB access";
   
   public final static String DB_READ_ONLY_ERROR = "Cannot modify the RTM database with read only permission level";
   
   
   
   private LogUtils()
   {
      throw new AssertionError( "This class should not be instantiated." );
   }
   
   
   
   public final static void logDBError( Context context,
                                        Class< ? > tag,
                                        String entity )
   {
      logDBError( context, tag, entity, null );
   }
   
   
   
   public final static void logDBError( Context context,
                                        Class< ? > tag,
                                        String entity,
                                        Throwable e )
   {
      final String message = context.getString( R.string.err_db_typed, entity );
      
      if ( e != null )
      {
         MolokoApp.Log.e( tag, message, e );
      }
      else
      {
         MolokoApp.Log.e( tag, message );
      }
      
      Toast.makeText( context, message, Toast.LENGTH_SHORT ).show();
   }
}
