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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.junit.rules.ExternalResource;


public class ProcessedSQLiteScript extends ExternalResource
{
   private final SQLiteScript script;
   
   private final Map< Pattern, String > replacements = new HashMap< Pattern, String >();
   
   
   
   public ProcessedSQLiteScript( SQLiteScript script )
   {
      if ( script == null )
      {
         throw new IllegalArgumentException( "script" );
      }
      
      this.script = script;
   }
   
   
   
   @Override
   protected void before() throws Throwable
   {
      super.before();
      script.before();
   }
   
   
   
   public ProcessedSQLiteScript replaceScriptVariable( String variable,
                                                       long replacement )
   {
      return replaceScriptVariable( variable, Long.toString( replacement ) );
   }
   
   
   
   public ProcessedSQLiteScript replaceScriptVariable( String variable,
                                                       String replacement )
   {
      final Pattern pattern = Pattern.compile( Pattern.quote( variable ) );
      replacements.put( pattern, replacement );
      
      return this;
   }
   
   
   
   public Collection< String > getSqlStatements()
   {
      final Collection< String > baseStatements = script.getSqlStatements();
      final Collection< String > replacedStatements = new ArrayList< String >( baseStatements.size() );
      
      for ( String baseStatement : baseStatements )
      {
         for ( Pattern variablePattern : replacements.keySet() )
         {
            final String replacement = replacements.get( variablePattern );
            baseStatement = variablePattern.matcher( baseStatement )
                                           .replaceAll( replacement );
         }
         
         replacedStatements.add( baseStatement );
      }
      
      return replacedStatements;
   }
}
