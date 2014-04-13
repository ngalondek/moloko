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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.CharBuffer;
import java.text.MessageFormat;
import java.util.Date;

import android.content.Context;
import dev.drsoran.rtm.ILog;


class FileWritingReader extends Reader
{
   private final static String NEW_LINE = System.getProperty( "line.separator" );
   
   private final String filename;
   
   private final ILog log;
   
   private final Context context;
   
   private final Reader decoratedReader;
   
   private OutputStreamWriter streamWriter;
   
   
   
   public FileWritingReader( String filename, ILog log, Context context,
      Reader decorated )
   {
      this.filename = filename;
      this.log = log;
      this.context = context;
      this.decoratedReader = decorated;
   }
   
   
   
   @Override
   public int read( char[] buf ) throws IOException
   {
      final int numRead = decoratedReader.read( buf );
      
      appendBuffer( buf, 0, numRead );
      
      return numRead;
   }
   
   
   
   @Override
   public int read( CharBuffer target ) throws IOException
   {
      final int lengthBefore = target.length();
      final int numRead = decoratedReader.read( target );
      
      appendBuffer( target.array(), lengthBefore - 1, numRead );
      
      return numRead;
   }
   
   
   
   @Override
   public int read( char[] buf, int offset, int count ) throws IOException
   {
      final int numRead = decoratedReader.read( buf, offset, count );
      
      appendBuffer( buf, offset, numRead );
      
      return numRead;
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
      closeStream();
   }
   
   
   
   private void appendBuffer( char[] buf, int offset, int numRead )
   {
      if ( numRead > 0 )
      {
         try
         {
            if ( streamWriter == null )
            {
               createStreamWriter();
               writeHeader();
            }
            
            streamWriter.write( buf, offset, numRead );
         }
         catch ( FileNotFoundException e )
         {
            log.e( getClass(),
                   MessageFormat.format( "Error creating file {0}", filename ),
                   e );
         }
         catch ( IOException e )
         {
            log.e( getClass(),
                   MessageFormat.format( "Error writing to file {0}", filename ),
                   e );
         }
      }
   }
   
   
   
   private void writeHeader() throws IOException
   {
      streamWriter.write( MessageFormat.format( "<!-- {0} -->",
                                                new Date().toString() ) );
      streamWriter.write( NEW_LINE );
   }
   
   
   
   private void createStreamWriter() throws FileNotFoundException
   {
      final FileOutputStream outputStream = context.openFileOutput( filename,
                                                                    Context.MODE_PRIVATE
                                                                       | Context.MODE_APPEND );
      streamWriter = new OutputStreamWriter( outputStream );
   }
   
   
   
   private void closeStream()
   {
      if ( streamWriter != null )
      {
         try
         {
            streamWriter.write( NEW_LINE );
            streamWriter.close();
         }
         catch ( IOException e )
         {
            log.e( getClass(), "Failed to close file stream", e );
         }
         
         streamWriter = null;
      }
   }
}
