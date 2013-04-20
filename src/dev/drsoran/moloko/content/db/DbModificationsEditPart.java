/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.moloko.content.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import dev.drsoran.moloko.content.ContentCompare;
import dev.drsoran.moloko.content.db.Columns.ModificationsColumns;
import dev.drsoran.moloko.domain.services.ContentException;


class DbModificationsEditPart
{
   private final RtmDatabase database;
   
   private final ITable modificationsTable;
   
   private final ModificationsQuery modificationsQuery;
   
   
   
   public DbModificationsEditPart( RtmDatabase database )
   {
      this.database = database;
      this.modificationsTable = database.getTable( ModificationsTable.TABLE_NAME );
      this.modificationsQuery = database.getQuery( ModificationsQuery.class );
   }
   
   
   
   public void applyModificationsInTransaction( Iterable< Modification > modifications ) throws ContentException
   {
      Cursor c = null;
      try
      {
         for ( Modification modification : modifications )
         {
            // Check if the modification should be stored or simply modify
            // the entity.
            if ( modification.isPersistent() )
            {
               // Check if modification already exists
               c = modificationsQuery.getModification( modification.getEntityUri(),
                                                       modification.getColName() );
               
               if ( c.moveToNext() )
               {
                  updateOrRevertExistingModification( c, modification );
               }
               else
               {
                  insertNewModification( modification );
               }
               
               c.close();
               c = null;
            }
            
            // Update the entity itself
            updateEntity( modification );
         }
      }
      catch ( Throwable e )
      {
         throw new ContentException( "Failed to apply modifications", e );
      }
      finally
      {
         if ( c != null )
         {
            c.close();
         }
      }
   }
   
   
   
   private void updateEntity( Modification modification )
   {
      final Uri entityUri = modification.getEntityUri();
      final String tableName = entityUri.getPath();
      final long entityId = Long.parseLong( entityUri.getLastPathSegment() );
      
      final ContentValues updateValues = new ContentValues( 1 );
      updateValues.put( modification.getColName(), modification.getNewValue() );
      
      final ITable table = database.getTable( tableName );
      table.update( entityId, updateValues, null, null );
   }
   
   
   
   private void insertNewModification( Modification modification )
   {
      modificationsTable.insert( createInsertContentValues( modification ) );
   }
   
   
   
   private void updateOrRevertExistingModification( Cursor existingModification,
                                                    Modification newModification )
   {
      // Check if the new value equals the synced value from the existing modification, if so the
      // user has reverted his change and we delete the modification.
      if ( ContentCompare.isDifferent( existingModification.getString( ModificationsColumns.SYNCED_VALUE_IDX ),
                                       newModification.getNewValue() ) )
      {
         // Update the modification with the new value.
         modificationsTable.update( existingModification.getLong( Columns.ID_IDX ),
                                    createUpdateNewValue( newModification.getNewValue() ),
                                    null,
                                    null );
      }
      else
      {
         modificationsTable.delete( existingModification.getLong( Columns.ID_IDX ),
                                    null,
                                    null );
      }
   }
   
   
   
   private static ContentValues createInsertContentValues( Modification modification )
   {
      final ContentValues values = new ContentValues();
      
      values.put( ModificationsColumns.ENTITY_URI, modification.getEntityUri()
                                                               .toString() );
      values.put( ModificationsColumns.COL_NAME, modification.getColName() );
      values.put( ModificationsColumns.TIMESTAMP, modification.getTimestamp() );
      
      if ( modification.getNewValue() != null )
      {
         values.put( ModificationsColumns.NEW_VALUE, modification.getNewValue() );
      }
      else
      {
         values.putNull( ModificationsColumns.NEW_VALUE );
      }
      
      String syncedValue = null;
      if ( modification.isSetSyncedValue() )
      {
         syncedValue = modification.getSyncedValue();
      }
      
      if ( modification.getSyncedValue() != null )
      {
         values.put( ModificationsColumns.SYNCED_VALUE, syncedValue );
      }
      else
      {
         values.putNull( ModificationsColumns.SYNCED_VALUE );
      }
      
      return values;
   }
   
   
   
   private static ContentValues createUpdateNewValue( String newValue )
   {
      final ContentValues values = new ContentValues( 1 );
      values.put( ModificationsColumns.NEW_VALUE, newValue );
      
      return values;
   }
}
