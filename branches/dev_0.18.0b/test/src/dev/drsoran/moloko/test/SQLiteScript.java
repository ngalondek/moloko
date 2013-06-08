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

package dev.drsoran.moloko.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.rules.ExternalResource;


public class SQLiteScript extends ExternalResource
{
   private final static String SQL_STATEMENT_SEPARATOR = ";";
   
   private final Class< ? > clazz;
   
   private final String filename;
   
   private Collection< String > sqlStatements;
   
   
   
   public SQLiteScript( Class< ? > clazz, String filename )
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
   
   
   
   @Override
   protected void before() throws Throwable
   {
      super.before();
      final InputStream scriptStream = clazz.getResourceAsStream( filename );
      if ( scriptStream == null )
      {
         throw new FileNotFoundException( MessageFormat.format( "No file ''{0}''",
                                                                filename ) );
      }
      
      sqlStatements = readScriptFromFile( scriptStream );
      scriptStream.close();
   }
   
   
   
   public Collection< String > getSqlStatements()
   {
      return sqlStatements;
   }
   
   
   
   private Collection< String > readScriptFromFile( InputStream scriptStream ) throws IOException
   {
      final BufferedReader reader = new BufferedReader( new InputStreamReader( scriptStream ) );
      final StringBuilder stringBuffer = new StringBuilder();
      
      String line;
      while ( ( line = reader.readLine() ) != null )
      {
         stringBuffer.append( line );
      }
      
      final Collection< String > statements = new ArrayList< String >();
      
      int newIndex;
      int lastIndex = 0;
      while ( ( newIndex = stringBuffer.indexOf( SQL_STATEMENT_SEPARATOR,
                                                 lastIndex ) ) != -1 )
      {
         String statement = stringBuffer.substring( lastIndex, newIndex );
         statement = statement.replaceAll( "\n", "" );
         statement = statement.replaceAll( "\\s{2,}", " " );
         
         statements.add( statement );
         lastIndex = newIndex + 1;
      }
      
      if ( statements.isEmpty() && stringBuffer.length() > 0 )
      {
         statements.add( stringBuffer.toString() );
      }
      
      return statements;
   }
}
