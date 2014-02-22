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

package dev.drsoran.moloko.domain.content;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import dev.drsoran.moloko.content.Columns;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.content.CursorUtils;
import dev.drsoran.moloko.domain.services.ContentException;
import dev.drsoran.moloko.sync.Compare;
import dev.drsoran.moloko.sync.Modification;
import dev.drsoran.moloko.sync.db.TableColumns.ModificationColumns;


public class MolokoModificationsApplier implements IModificationsApplier
{
   private final static String SEL_QUERY_MODIFICATION = new StringBuilder( 100 ).append( ModificationColumns.ENTITY_URI )
                                                                                .append( "=? AND " )
                                                                                .append( ModificationColumns.COL_NAME )
                                                                                .append( "=?" )
                                                                                .toString();
   
   private final ContentResolver contentResolver;
   
   private final IContentValuesFactory contentValuesFactory;
   
   
   
   public MolokoModificationsApplier( ContentResolver contentResolver,
      IContentValuesFactory contentValuesFactory )
   {
      this.contentResolver = contentResolver;
      this.contentValuesFactory = contentValuesFactory;
   }
   
   
   
   @Override
   public void applyPersistentModifications( Iterable< Modification > modifications ) throws ContentException
   {
      Cursor c = null;
      try
      {
         for ( Modification modification : modifications )
         {
            if ( modification.isPersistent() )
            {
               // Check if modification already exists
               c = getModification( modification.getEntityUri(),
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
   
   
   
   private Cursor getModification( String entityUri, String columnName )
   {
      return contentResolver.query( ContentUris.MODIFICATIONS_CONTENT_URI,
                                    ModificationColumns.PROJECTION,
                                    SEL_QUERY_MODIFICATION,
                                    new String[]
                                    { entityUri, columnName },
                                    null );
   }
   
   
   
   private void insertNewModification( Modification modification )
   {
      contentResolver.insert( ContentUris.MODIFICATIONS_CONTENT_URI,
                              contentValuesFactory.createContentValues( modification ) );
   }
   
   
   
   private void updateOrRevertExistingModification( Cursor existingModification,
                                                    Modification newModification )
   {
      // Check if the new value equals the synced value from the existing modification, if so the
      // user has reverted his change and we delete the modification.
      if ( Compare.isDifferent( CursorUtils.getOptString( existingModification,
                                                                 ModificationColumns.SYNCED_VALUE_IDX ),
                                       newModification.getNewValue() ) )
      {
         // Update the modification with the new value.
         contentResolver.update( ContentUris.bindElementId( ContentUris.MODIFICATIONS_CONTENT_URI_ID,
                                                            existingModification.getLong( Columns.ID_IDX ) ),
                                 createUpdateNewValueContentValues( newModification.getNewValue() ),
                                 null,
                                 null );
      }
      else
      {
         contentResolver.delete( ContentUris.bindElementId( ContentUris.MODIFICATIONS_CONTENT_URI_ID,
                                                            existingModification.getLong( Columns.ID_IDX ) ),
                                 null,
                                 null );
      }
   }
   
   
   
   private static ContentValues createUpdateNewValueContentValues( String newValue )
   {
      final ContentValues values = new ContentValues( 1 );
      values.put( ModificationColumns.NEW_VALUE, newValue );
      
      return values;
   }
}
