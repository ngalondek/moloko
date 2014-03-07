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

package dev.drsoran.moloko.domain.content;

import java.util.ArrayList;
import java.util.Collection;

import android.content.ContentResolver;
import dev.drsoran.moloko.content.Columns.SyncTimesColumns;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.rtm.sync.SyncTime;


public class SyncTimesContentEditHandler extends
         AbstractContentEditHandler< SyncTime >
{
   public SyncTimesContentEditHandler( ContentResolver contentResolver,
      IContentValuesFactory contentValuesFactory,
      IModificationsApplier modificationsApplier )
   {
      super( contentResolver, contentValuesFactory, modificationsApplier );
   }
   
   
   
   @Override
   protected Collection< Modification > collectUpdateModifications( SyncTime existingElement,
                                                                    SyncTime updatedElement )
   {
      final Collection< Modification > modifications = new ArrayList< Modification >();
      
      final String entityUri = ContentUris.bindElementId( ContentUris.SYNC_CONTENT_URI_ID,
                                                          SyncTimesColumns.SINGLETON_ID )
                                          .toString();
      
      Modification.newNonPersistentModification( entityUri,
                                                 SyncTimesColumns.LAST_IN,
                                                 updatedElement.getLastSyncInMillis() );
      
      Modification.newNonPersistentModification( entityUri,
                                                 SyncTimesColumns.LAST_OUT,
                                                 updatedElement.getLastSyncOutMillis() );
      
      return modifications;
   }
}
