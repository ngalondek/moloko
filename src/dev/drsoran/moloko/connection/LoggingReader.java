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

import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

import android.util.Log;


class LoggingReader extends Reader
{
   private final Reader decoratedReader;
   
   private final String logTarget;
   
   
   
   public LoggingReader( Reader decorated, String logTarget )
   {
      this.decoratedReader = decorated;
      this.logTarget = logTarget;
   }
   
   
   
   @Override
   public int read( char[] buf ) throws IOException
   {
      final int numRead = decoratedReader.read( buf );
      
      logRead( buf, 0, numRead );
      
      return numRead;
   }
   
   
   
   @Override
   public int read( CharBuffer target ) throws IOException
   {
      final int lengthBefore = target.length();
      final int numRead = decoratedReader.read( target );
      
      logRead( target.array(), lengthBefore - 1, numRead );
      
      return numRead;
   }
   
   
   
   @Override
   public int read( char[] buf, int offset, int count ) throws IOException
   {
      final int numRead = decoratedReader.read( buf, offset, count );
      
      logRead( buf, offset, numRead );
      
      return numRead;
   }
   
   
   
   @Override
   public boolean equals( Object o )
   {
      return decoratedReader.equals( o );
   }
   
   
   
   @Override
   public int hashCode()
   {
      return decoratedReader.hashCode();
   }
   
   
   
   @Override
   public void mark( int readLimit ) throws IOException
   {
      decoratedReader.mark( readLimit );
   }
   
   
   
   @Override
   public boolean markSupported()
   {
      return decoratedReader.markSupported();
   }
   
   
   
   @Override
   public int read() throws IOException
   {
      return decoratedReader.read();
   }
   
   
   
   @Override
   public boolean ready() throws IOException
   {
      return decoratedReader.ready();
   }
   
   
   
   @Override
   public void reset() throws IOException
   {
      decoratedReader.reset();
   }
   
   
   
   @Override
   public long skip( long charCount ) throws IOException
   {
      return decoratedReader.skip( charCount );
   }
   
   
   
   @Override
   public String toString()
   {
      return decoratedReader.toString();
   }
   
   
   
   @Override
   public void close() throws IOException
   {
      decoratedReader.close();
      finishLogging();
   }
   
   
   
   private void logRead( char[] buf, int offset, int numRead )
   {
      if ( numRead > 0 )
         Log.i( logTarget, String.valueOf( buf, offset, numRead ) );
   }
   
   
   
   private void finishLogging()
   {
      Log.i( logTarget, "\n" );
   }
}
