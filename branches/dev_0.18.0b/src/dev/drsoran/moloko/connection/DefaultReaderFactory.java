/* 
 *	Copyright (c) 2014 Ronny Röhricht
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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.rtm.ILog;


public class DefaultReaderFactory implements IReaderFactory
{
   private final ILog log;
   
   
   
   public DefaultReaderFactory( ILog log )
   {
      this.log = log;
   }
   
   
   
   @SuppressWarnings( "resource" )
   @Override
   public Reader createReader( InputStream inputStream )
   {
      Reader reader = new InputStreamReader( inputStream );
      
      if ( MolokoApp.DEBUG() )
      {
         reader = new LoggingReader( log, reader, HttpUrlConnection.class );
      }
      
      return reader;
   }
}
