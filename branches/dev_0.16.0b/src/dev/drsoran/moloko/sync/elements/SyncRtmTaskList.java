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
import com.mdt.rtm.data.RtmTaskNote;
import com.mdt.rtm.data.RtmTaskSeries;
import com.mdt.rtm.data.RtmTasks;


public class SyncRtmTaskList
{
   private interface IGetModeAction
   {
      < T > void perform( List< T > list, Comparator< ? super T > cmp );
   }
   

   protected static enum GetMode implements IGetModeAction
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
   
   protected static final Comparator< RtmTaskSeries > LESS_ID = new Comparator< RtmTaskSeries >()
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
   


   public List< OutSyncTask > getOutSyncTasks()
   {
      return getOutSyncTasks( OutSyncTask.LESS_ID );
   }
   


   public List< OutSyncTask > getOutSyncTasks( Comparator< ? super OutSyncTask > cmp )
   {
      return collectOutSyncTasks( series, cmp );
   }
   


   protected List< OutSyncTask > collectOutSyncTasks( List< ? extends RtmTaskSeries > seriesList,
                                                      Comparator< ? super OutSyncTask > cmp )
   {
      final List< OutSyncTask > res = new LinkedList< OutSyncTask >();
      
      for ( RtmTaskSeries taskSeries : seriesList )
         for ( RtmTask task : taskSeries.getTasks() )
            res.add( new OutSyncTask( taskSeries, task ) );
      
      if ( cmp != null )
         GetMode.SORTED.perform( res, cmp );
      
      return res;
   }
   


   public List< InSyncTask > getInSyncTasks()
   {
      return getInSyncTasks( InSyncTask.LESS_ID );
   }
   


   public List< InSyncTask > getInSyncTasks( Comparator< ? super InSyncTask > cmp )
   {
      return collectInSyncTasks( series, cmp );
   }
   


   protected List< InSyncTask > collectInSyncTasks( List< ? extends RtmTaskSeries > seriesList,
                                                    Comparator< ? super InSyncTask > cmp )
   {
      final List< InSyncTask > res = new LinkedList< InSyncTask >();
      
      for ( RtmTaskSeries taskSeries : seriesList )
         for ( RtmTask task : taskSeries.getTasks() )
            res.add( new InSyncTask( taskSeries, task ) );
      
      if ( cmp != null )
         GetMode.SORTED.perform( res, cmp );
      
      return res;
   }
   


   public SyncRtmTaskNotesList getSyncNotesList()
   {
      return collectSyncNotes( series );
   }
   


   protected SyncRtmTaskNotesList collectSyncNotes( List< ? extends RtmTaskSeries > seriesList )
   {
      final SyncRtmTaskNotesList res = new SyncRtmTaskNotesList();
      
      for ( RtmTaskSeries taskSeries : seriesList )
         for ( RtmTaskNote note : taskSeries.getNotes().getNotes() )
            res.add( new SyncNote( taskSeries, note ) );
      
      return res;
   }
   


   public List< String > getTaskSeriesIds()
   {
      final List< String > res = new ArrayList< String >( series.size() );
      
      for ( RtmTaskSeries taskSeries : series )
         res.add( taskSeries.getId() );
      
      return res;
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
