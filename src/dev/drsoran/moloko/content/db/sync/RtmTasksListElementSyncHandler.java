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

import static dev.drsoran.moloko.content.db.TableNames.RTM_TASKS_LIST_TABLE;
import android.content.ContentValues;
import dev.drsoran.moloko.content.db.RtmDatabase;
import dev.drsoran.moloko.content.db.TableColumns.RtmTasksListColumns;
import dev.drsoran.moloko.domain.content.IContentValuesFactory;
import dev.drsoran.moloko.domain.content.IModelElementFactory;
import dev.drsoran.rtm.model.RtmTasksList;


public class RtmTasksListElementSyncHandler extends
         AbstractElementSyncHandler< RtmTasksList >
{
   private final static String SEL_RTM_TASKS_LIST_MODIFIED_SINCE = RtmTasksListColumns.LIST_MODIFIED_DATE
      + " >= ?";
   
   private final long timeOfSyncMsUtc;
   
   
   
   public RtmTasksListElementSyncHandler( RtmDatabase rtmDatabase,
      IModelElementFactory modelElementFactory,
      IContentValuesFactory contentValuesFactory, long timeOfSyncMsUtc )
   {
      super( rtmDatabase, modelElementFactory, contentValuesFactory );
      this.timeOfSyncMsUtc = timeOfSyncMsUtc;
   }
   
   
   
   @Override
   protected String getTableName()
   {
      return RTM_TASKS_LIST_TABLE;
   }
   
   
   
   @Override
   protected String getId( RtmTasksList element )
   {
      return element.getId();
   }
   
   
   
   @Override
   protected String getModifiedSinceSelection()
   {
      return SEL_RTM_TASKS_LIST_MODIFIED_SINCE;
   }
   
   
   
   @Override
   protected String getEqualIdClause()
   {
      return RtmTasksListColumns.RTM_LIST_ID + " = '?'";
   }
   
   
   
   @Override
   protected Class< RtmTasksList > getElementClass()
   {
      return RtmTasksList.class;
   }
   
   
   
   @Override
   protected void adaptContentValuesForInsert( ContentValues contentValues,
                                               RtmTasksList elementToInsert )
   {
      contentValues.put( RtmTasksListColumns.LIST_CREATED_DATE, timeOfSyncMsUtc );
      contentValues.put( RtmTasksListColumns.LIST_MODIFIED_DATE,
                         timeOfSyncMsUtc );
      
      if ( elementToInsert.isDeleted() )
      {
         contentValues.put( RtmTasksListColumns.LIST_DELETED_DATE,
                            timeOfSyncMsUtc );
      }
   }
   
   
   
   @Override
   protected void adaptContentValuesForUpdate( ContentValues contentValues,
                                               RtmTasksList currentElement,
                                               RtmTasksList updatedElement )
   {
      contentValues.put( RtmTasksListColumns.LIST_MODIFIED_DATE,
                         timeOfSyncMsUtc );
      
      if ( updatedElement.isDeleted() )
      {
         contentValues.put( RtmTasksListColumns.LIST_DELETED_DATE,
                            timeOfSyncMsUtc );
      }
   }
}
