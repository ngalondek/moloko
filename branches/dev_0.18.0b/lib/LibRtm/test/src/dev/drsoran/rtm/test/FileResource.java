/* 
 *	Copyright (c) 2013 Ronny Röhricht
 *
 *	This file is part of MolokoTest.
 *
 *	MolokoTest is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	MolokoTest is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with MolokoTest.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.rtm.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;

import org.junit.rules.ExternalResource;


public class FileResource extends ExternalResource
{
   private final Class< ? > clazz;
   
   private final String filename;
   
   private InputStream inputStream;
   
   private BufferedReader reader;
   
   
   
   public FileResource( Class< ? > clazz, String filename )
   {
      if ( clazz == null )
      {
         throw new IllegalArgumentException( "clazz" );
      }
      
      if ( filename == null )
      {
         throw new IllegalArgumentException( "filename" );
      }
      
      this.clazz = clazz;
      this.filename = filename;
   }
   
   
   
   public InputStream getInputStream()
   {
      return inputStream;
   }
   
   
   
   public BufferedReader getReader()
   {
      return reader;
   }
   
   
   
   public void close()
   {
      if ( reader != null )
      {
         try
         {
            reader.close();
            reader = null;
         }
         catch ( IOException e )
         {
            throw new RuntimeException( e );
         }
      }
   }
   
   
   
   @Override
   public void before() throws Throwable
   {
      super.before();
      
      final InputStream fileStream = clazz.getResourceAsStream( filename );
      if ( fileStream == null )
      {
         throw new FileNotFoundException( MessageFormat.format( "No file ''{0}''",
                                                                filename ) );
      }
      
      reader = new BufferedReader( new InputStreamReader( fileStream ) );
   }
   
   
   
   @Override
   protected void after()
   {
      close();
      super.after();
   }
}
