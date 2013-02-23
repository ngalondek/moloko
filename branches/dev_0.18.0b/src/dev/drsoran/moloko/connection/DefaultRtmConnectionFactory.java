/* 
 *	Copyright (c) 2011 Ronny Röhricht
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

package dev.drsoran.moloko.connection;

import android.os.Build;
import dev.drsoran.moloko.app.MolokoApp;


public class DefaultRtmConnectionFactory implements IRtmConnectionFactory
{
   @Override
   public IRtmConnection createRtmConnection( String scheme,
                                              String hostname,
                                              int port )
   {
      if ( MolokoApp.isApiLevelSupported( Build.VERSION_CODES.GINGERBREAD ) )
         return new HttpUrlRtmConnection( scheme, hostname, port );
      else
         return new ApacheHttpClientRtmConnection( scheme, hostname, port );
   }
}
