/* 
 *	Copyright (c) 2012 Ronny Röhricht
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

package dev.drsoran.moloko.app.taskedit;

import java.util.List;

import android.content.ContentProviderClient;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;

import com.mdt.rtm.data.RtmLists;
import com.mdt.rtm.data.RtmLocation;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.loaders.AbstractLoader;
import dev.drsoran.moloko.app.taskedit.AbstractTaskEditFragment.TaskEditDatabaseData;
import dev.drsoran.moloko.content.db.TableColumns;
import dev.drsoran.moloko.content.db.RtmTasksListsTable;
import dev.drsoran.moloko.content.db.RtmLocationsTable;
import dev.drsoran.moloko.content.db.TableColumns.Lists;
import dev.drsoran.moloko.content.db.TableColumns.Locations;


class TaskEditDatabaseDataLoader extends AbstractLoader< TaskEditDatabaseData >
{
   public final static int ID = R.id.loader_taskedit_data;
   
   private final static String LISTS_SELECTION = Lists.IS_SMART_LIST
      + "=0 AND " + Lists.LIST_DELETED + " IS NULL";
   
   
   
   public TaskEditDatabaseDataLoader( Context context )
   {
      super( context );
   }
   
   
   
   @Override
   protected TaskEditDatabaseData queryResultInBackground( ContentProviderClient client )
   {
      TaskEditDatabaseData data = null;
      
      final RtmLists lists = RtmTasksListsTable.getAllLists( client,
                                                               LISTS_SELECTION );
      if ( lists != null )
      {
         final List< RtmLocation > locations = RtmLocationsTable.getAllLocations( client );
         
         if ( locations != null )
         {
            data = new TaskEditDatabaseData( lists, locations );
         }
      }
      
      return data;
   }
   
   
   
   @Override
   protected ContentProviderClient getContentProviderClient()
   {
      return getContext().getContentResolver()
                         .acquireContentProviderClient( TableColumns.AUTHORITY );
   }
   
   
   
   @Override
   protected Uri getContentUri()
   {
      return Uri.EMPTY;
   }
   
   
   
   @Override
   protected void registerContentObserver( ContentObserver observer )
   {
      getContext().getContentResolver()
                  .registerContentObserver( Lists.CONTENT_URI, true, observer );
      getContext().getContentResolver()
                  .registerContentObserver( Locations.CONTENT_URI,
                                            true,
                                            observer );
   }
   
   
   
   @Override
   protected void unregisterContentObserver( ContentObserver observer )
   {
      getContext().getContentResolver().unregisterContentObserver( observer );
   }
}
