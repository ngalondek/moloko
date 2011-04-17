/* 
 *	Copyright (c) 2011 Ronny Röhricht
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

package dev.drsoran.moloko.sync.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.mdt.rtm.data.RtmTask;
import com.mdt.rtm.data.RtmTaskList;
import com.mdt.rtm.data.RtmTaskSeries;
import com.mdt.rtm.data.RtmTasks;


public class SyncRtmTaskList
{
   private interface IGetModeAction
   {
      < T > void perform( List< T > list, Comparator< ? super T > cmp );
   }
   

   private static enum GetMode implements IGetModeAction
   {
      AS_IS
      {
         public < T > void perform( List< T > list, Comparator< ? super T > cmp )
         {
            
         }
      },
      SORTED
      {
         public < T > void perform( List< T > list, Comparator< ? super T > cmp )
         {
            Collections.sort( list, cmp );
         }
      }
      
   }
   
   private static final Comparator< RtmTaskSeries > LESS_ID = new Comparator< RtmTaskSeries >()

   {
      public int compare( RtmTaskSeries object1, RtmTaskSeries object2 )
      {
         return object1.getId().compareTo( object2.getId() );
      }
   };
   
   private final List< RtmTaskSeries > series;
   
   

   public SyncRtmTaskList()
   {
      series = new ArrayList< RtmTaskSeries >();
   }
   


   public SyncRtmTaskList( List< RtmTaskSeries > taskSeries )
   {
      if ( taskSeries == null )
         throw new NullPointerException( "taskSeries is null" );
      
      series = new ArrayList< RtmTaskSeries >( taskSeries );
      
      Collections.sort( series, LESS_ID );
   }
   


   public SyncRtmTaskList( RtmTaskList taskList )
   {
      this( taskList.getSeries() );
   }
   


   public SyncRtmTaskList( RtmTasks tasks )
   {
      if ( tasks == null )
         throw new NullPointerException( "tasks is null" );
      
      series = new LinkedList< RtmTaskSeries >();
      
      for ( RtmTaskList taskList : tasks.getLists() )
         for ( RtmTaskSeries series : taskList.getSeries() )
            this.series.add( series );
   }
   


   public List< InSyncRtmTaskSeries > getInSyncTasksSeries()
   {
      final List< InSyncRtmTaskSeries > res = new ArrayList< InSyncRtmTaskSeries >( series.size() );
      
      for ( RtmTaskSeries taskSeries : series )
         res.add( new InSyncRtmTaskSeries( taskSeries ) );
      
      GetMode.SORTED.perform( res, InSyncRtmTaskSeries.LESS_ID );
      
      return res;
   }
   


   public List< OutSyncTask > getOutSyncTasks()
   {
      return getOutSyncTasks( OutSyncTask.LESS_ID );
   }
   


   public List< OutSyncTask > getOutSyncTasks( Comparator< ? super OutSyncTask > cmp )
   {
      final List< OutSyncTask > res = new LinkedList< OutSyncTask >();
      
      for ( RtmTaskSeries taskSeries : series )
         for ( RtmTask task : taskSeries.getTasks() )
            res.add( new OutSyncTask( taskSeries, task ) );
      
      if ( cmp != null )
         GetMode.SORTED.perform( res, cmp );
      
      return res;
   }
   


   public void add( RtmTaskSeries taskSeries )
   {
      series.add( taskSeries );
   }
   


   public void remove( RtmTaskSeries taskSeries )
   {
      final int pos = Collections.binarySearch( series, taskSeries, LESS_ID );
      
      if ( pos >= 0 )
         series.remove( pos );
   }
   


   public RtmTaskSeries get( int location )
   {
      return series.get( location );
   }
   


   public int size()
   {
      return series.size();
   }
   


   public void update( RtmTaskList taskList )
   {
      if ( taskList == null )
         throw new NullPointerException( "taskList is null" );
      
      for ( RtmTaskSeries taskSeries : taskList.getSeries() )
      {
         // If the set already contains an element in respect to the Comparator,
         // then we update it by the new.
         final int pos = Collections.binarySearch( series, taskSeries, LESS_ID );
         
         if ( pos >= 0 )
         {
            series.remove( pos );
            series.add( pos, taskSeries );
         }
         else
         {
            series.add( ( -pos - 1 ), taskSeries );
         }
      }
   }
   


   public RtmTaskList toRtmTaskList()
   {
      return new RtmTaskList( null, series, null );
   }
   


   @Override
   public String toString()
   {
      return "<" + series.size() + ">";
   }
}
