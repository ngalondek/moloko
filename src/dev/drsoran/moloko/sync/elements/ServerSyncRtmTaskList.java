/* 
 *	Copyright (c) 2011 Ronny Röhricht
 *
 *	This file is part of Moloko_trunk.
 *
 *	Moloko_trunk is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko_trunk is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko_trunk.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.sync.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.mdt.rtm.data.RtmTaskList;
import com.mdt.rtm.data.RtmTaskSeries;
import com.mdt.rtm.data.RtmTasks;

import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.NoopContentProviderSyncOperation;


public class ServerSyncRtmTaskList extends SyncRtmTaskList
{
   private final List< RtmTaskSeries > seriesWithDeletedTasks;
   
   

   public ServerSyncRtmTaskList()
   {
      super();
      seriesWithDeletedTasks = Collections.emptyList();
   }
   


   public ServerSyncRtmTaskList( List< RtmTaskSeries > taskSeries )
   {
      super( taskSeries );
      seriesWithDeletedTasks = Collections.emptyList();
   }
   


   public ServerSyncRtmTaskList( RtmTaskList taskList )
   {
      super( taskList );
      seriesWithDeletedTasks = new ArrayList< RtmTaskSeries >();
      
      fillSeriesesWithDeletedTasks( taskList );
   }
   


   public ServerSyncRtmTaskList( RtmTasks tasks )
   {
      super( tasks );
      seriesWithDeletedTasks = new ArrayList< RtmTaskSeries >();
      
      for ( RtmTaskList taskList : tasks.getLists() )
      {
         fillSeriesesWithDeletedTasks( taskList );
      }
   }
   


   @Override
   public void update( RtmTaskList taskList )
   {
      super.update( taskList );
      
      for ( RtmTaskSeries taskSeries : taskList.getSeriesWithDeletedTasks() )
      {
         // If the set already contains an element in respect to the Comparator,
         // then we update it by the new.
         final int pos = Collections.binarySearch( seriesWithDeletedTasks,
                                                   taskSeries,
                                                   LESS_ID );
         if ( pos >= 0 )
         {
            seriesWithDeletedTasks.remove( pos );
            seriesWithDeletedTasks.add( pos, taskSeries );
         }
         else
         {
            seriesWithDeletedTasks.add( ( -pos - 1 ), taskSeries );
         }
      }
   }
   


   public List< IContentProviderSyncOperation > removeDeletedTasks()
   {
      final List< IContentProviderSyncOperation > ops;
      
      if ( seriesWithDeletedTasks.size() > 0 )
      {
         ops = new LinkedList< IContentProviderSyncOperation >();
         
         final List< InSyncTask > inSynTasks = collectInSyncTasks( seriesWithDeletedTasks,
                                                                   null );
         for ( InSyncTask inSyncTask : inSynTasks )
         {
            IContentProviderSyncOperation op = inSyncTask.computeContentProviderDeleteOperation();
            
            if ( op != null
               && !( op instanceof NoopContentProviderSyncOperation ) )
            {
               ops.add( op );
            }
         }
      }
      else
      {
         ops = Collections.emptyList();
      }
      
      return ops;
   }
   


   private void fillSeriesesWithDeletedTasks( RtmTaskList taskList )
   {
      seriesWithDeletedTasks.addAll( taskList.getSeriesWithDeletedTasks() );
      Collections.sort( seriesWithDeletedTasks, LESS_ID );
   }
}
