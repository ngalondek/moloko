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

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import dev.drsoran.rtm.test.FileResource;


public class SQLiteScript extends FileResource
{
   private final static String SQL_STATEMENT_SEPARATOR = ";";
   
   private Collection< String > sqlStatements;
   
   
   
   public SQLiteScript( Class< ? > clazz, String filename )
   {
      super( clazz, filename );
   }
   
   
   
   public Collection< String > getSqlStatements()
   {
      try
      {
         sqlStatements = readScriptFromFile();
      }
      catch ( IOException e )
      {
         fail( e.getLocalizedMessage() );
      }
      finally
      {
         close();
      }
      
      return sqlStatements;
   }
   
   
   
   private Collection< String > readScriptFromFile() throws IOException
   {
      final BufferedReader reader = getReader();
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
