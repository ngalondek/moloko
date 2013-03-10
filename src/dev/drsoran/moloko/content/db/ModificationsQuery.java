/*
 * Copyright (c) 2010 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.content.db;

import android.database.Cursor;
import android.net.Uri;
import dev.drsoran.provider.Rtm.Modifications;


public class ModificationsQuery
{
   private final static String SEL_QUERY_MODIFICATION = new StringBuilder( 100 ).append( Modifications.ENTITY_URI )
                                                                                .append( "=? AND " )
                                                                                .append( Modifications.COL_NAME )
                                                                                .append( "=?" )
                                                                                .toString();
   
   private final static String SEL_QUERY_MODIFICATIONS = new StringBuilder( Modifications.ENTITY_URI ).append( "=?" )
                                                                                                      .toString();
   
   private final RtmDatabase database;
   
   private final ModificationsTable modificationsTable;
   
   
   
   public ModificationsQuery( RtmDatabase database,
      ModificationsTable modificationsTable )
   {
      this.database = database;
      this.modificationsTable = modificationsTable;
   }
   
   
   
   public Cursor getModification( Uri entityUri, String colName )
   {
      final Cursor c = database.getReadable()
                               .query( modificationsTable.getTableName(),
                                       modificationsTable.getProjection(),
                                       SEL_QUERY_MODIFICATION,
                                       new String[]
                                       { entityUri.toString(), colName },
                                       null,
                                       null,
                                       null );
      return c;
   }
   
   
   
   public Cursor getModifications( String tableName, String... otherTableNames )
   {
      final StringBuilder selectionBuilder = new StringBuilder();
      selectionBuilder.append( Modifications.ENTITY_URI )
                      .append( " like '" )
                      .append( tableName )
                      .append( "%'" );
      
      if ( otherTableNames.length > 0 )
      {
         selectionBuilder.append( " OR " );
         
         for ( int i = 0; i < otherTableNames.length; ++i )
         {
            selectionBuilder.append( Modifications.ENTITY_URI )
                            .append( " like '" )
                            .append( otherTableNames[ i ] )
                            .append( "%'" );
            
            if ( i + 1 < otherTableNames.length )
            {
               selectionBuilder.append( " OR " );
            }
         }
      }
      
      final Cursor c = database.getReadable()
                               .query( modificationsTable.getTableName(),
                                       modificationsTable.getProjection(),
                                       selectionBuilder.toString(),
                                       null,
                                       null,
                                       null,
                                       null );
      return c;
   }
   
   
   
   public boolean isModified( Uri entityUri )
   {
      Cursor c = null;
      
      try
      {
         c = database.getReadable().query( modificationsTable.getTableName(),
                                           modificationsTable.getProjection(),
                                           SEL_QUERY_MODIFICATIONS,
                                           new String[]
                                           { entityUri.toString() },
                                           null,
                                           null,
                                           null );
         
         return c.getCount() > 0;
      }
      finally
      {
         if ( c != null )
         {
            c.close();
         }
      }
   }
}
