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

package dev.drsoran.moloko.content.db.sync;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import dev.drsoran.db.ITable;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.db.RtmDatabase;
import dev.drsoran.moloko.domain.content.IContentValuesFactory;
import dev.drsoran.moloko.domain.content.IModelElementFactory;
import dev.drsoran.rtm.model.RtmConstants;


abstract class AbstractElementSyncHandler< T > implements
         IDbElementSyncHandler< T >
{
   private final RtmDatabase rtmDatabase;
   
   private final IModelElementFactory modelElementFactory;
   
   private final IContentValuesFactory contentValuesFactory;
   
   
   
   protected AbstractElementSyncHandler( RtmDatabase rtmDatabase,
      IModelElementFactory modelElementFactory,
      IContentValuesFactory contentValuesFactory )
   {
      if ( rtmDatabase == null )
      {
         throw new IllegalArgumentException( "rtmDatabase" );
      }
      
      if ( modelElementFactory == null )
      {
         throw new IllegalArgumentException( "modelElementFactory" );
      }
      
      if ( contentValuesFactory == null )
      {
         throw new IllegalArgumentException( "contentValuesFactory" );
      }
      
      this.rtmDatabase = rtmDatabase;
      this.modelElementFactory = modelElementFactory;
      this.contentValuesFactory = contentValuesFactory;
   }
   
   
   
   @Override
   public List< T > getElementsModifiedSince( long modifiedSinceMsUtc )
   {
      return getElementsModifiedSinceImpl( modifiedSinceMsUtc,
                                           getTableName(),
                                           getModifiedSinceSelection(),
                                           getElementClass() );
   }
   
   
   
   @Override
   public void insert( T element )
   {
      final ContentValues contentValues = contentValuesFactory.createContentValues( element );
      adaptContentValuesForInsert( contentValues, element );
      
      final ITable table = getTable( getTableName() );
      table.insert( contentValues );
   }
   
   
   
   @Override
   public void update( T currentElement, T updatedElement )
   {
      if ( getId( currentElement ).equals( getId( updatedElement ) ) )
      {
         throw new IllegalArgumentException( MessageFormat.format( "IDs differ in update. {0} != {1}",
                                                                   currentElement,
                                                                   updatedElement ) );
      }
      
      final ITable table = getTable( getTableName() );
      final ContentValues contentValues = contentValuesFactory.createContentValues( updatedElement );
      
      adaptContentValuesForUpdate( contentValues,
                                   currentElement,
                                   updatedElement );
      
      final String whereClause = getEqualIdClause();
      
      final int numUpdated = table.update( Constants.NO_ID,
                                           contentValues,
                                           whereClause,
                                           whereClause != null ? new String[]
                                           { getId( currentElement ) } : null );
      if ( numUpdated < 1 )
      {
         throw new SQLiteException( MessageFormat.format( " {0} not updated.",
                                                          currentElement ) );
      }
   }
   
   
   
   @Override
   public void delete( T element )
   {
      final ITable tasksListTable = getTable( getTableName() );
      
      final String whereClause = getEqualIdClause();
      
      final int numDeleted = tasksListTable.delete( Constants.NO_ID,
                                                    whereClause,
                                                    whereClause != null
                                                                       ? new String[]
                                                                       { getId( element ) }
                                                                       : null );
      if ( numDeleted < 1 )
      {
         throw new SQLiteException( MessageFormat.format( "RTM tasks list {0} not deleted.",
                                                          element ) );
      }
   }
   
   
   
   protected abstract String getTableName();
   
   
   
   protected abstract String getId( T element );
   
   
   
   protected abstract String getModifiedSinceSelection();
   
   
   
   protected abstract String getEqualIdClause();
   
   
   
   protected abstract Class< T > getElementClass();
   
   
   
   protected void adaptContentValuesForInsert( ContentValues contentValues,
                                               T elementToInsert )
   {
   }
   
   
   
   protected void adaptContentValuesForUpdate( ContentValues contentValues,
                                               T currentElement,
                                               T updatedElement )
   {
   }
   
   
   
   private List< T > getElementsModifiedSinceImpl( long modifiedSinceMsUtc,
                                                   String tableName,
                                                   String selection,
                                                   Class< T > elementType )
   {
      final ITable table = getTable( tableName );
      
      Cursor c = null;
      try
      {
         final String[] selectionArgs = selection != null
                                                         ? new String[]
                                                         { String.valueOf( modifiedSinceMsUtc == RtmConstants.NO_TIME
                                                                                                                     ? 0L
                                                                                                                     : modifiedSinceMsUtc ) }
                                                         : null;
         
         c = table.query( table.getProjection(), selection, selectionArgs, null );
         
         final List< T > result = new ArrayList< T >( c.getCount() );
         while ( c.moveToNext() )
         {
            final T element = modelElementFactory.createElementFromCursor( c,
                                                                           elementType );
            result.add( element );
         }
         
         return result;
      }
      finally
      {
         if ( c != null )
         {
            c.close();
         }
      }
   }
   
   
   
   private ITable getTable( String tableName )
   {
      return rtmDatabase.getTable( tableName );
   }
}
