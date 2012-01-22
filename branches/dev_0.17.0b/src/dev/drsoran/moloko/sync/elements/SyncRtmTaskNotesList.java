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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.util.Pair;

import com.mdt.rtm.data.RtmTaskNote;


public class SyncRtmTaskNotesList
{
   private interface IGetModeAction
   {
      < T > void perform( List< T > list, Comparator< ? super T > cmp );
   }
   
   
   private static enum GetMode implements IGetModeAction
   {
      AS_IS
      {
         @Override
         public < T > void perform( List< T > list, Comparator< ? super T > cmp )
         {
            
         }
      },
      SORTED
      {
         @Override
         public < T > void perform( List< T > list, Comparator< ? super T > cmp )
         {
            Collections.sort( list, cmp );
         }
      }
      
   }
   
   private static final Comparator< SyncNote > LESS_ID = new Comparator< SyncNote >()
   {
      @Override
      public int compare( SyncNote object1, SyncNote object2 )
      {
         return object1.getId().compareTo( object2.getId() );
      }
   };
   
   private static final Comparator< RtmTaskNote > LESS_TASKSERIES_ID = new Comparator< RtmTaskNote >()
   {
      @Override
      public int compare( RtmTaskNote object1, RtmTaskNote object2 )
      {
         return object1.getTaskSeriesId().compareTo( object2.getTaskSeriesId() );
      }
   };
   
   // Sync notes grouped by their taskseries ID
   private Map< String, List< SyncNote > > notes;
   
   
   
   public SyncRtmTaskNotesList()
   {
      notes = new HashMap< String, List< SyncNote >>();
   }
   
   
   
   public SyncRtmTaskNotesList( List< RtmTaskNote > notes )
   {
      this();
      
      if ( notes == null )
         throw new NullPointerException( "notes are null" );
      
      for ( RtmTaskNote rtmTaskNote : notes )
      {
         if ( !this.notes.containsKey( rtmTaskNote.getTaskSeriesId() ) )
            this.notes.put( rtmTaskNote.getTaskSeriesId(),
                            new ArrayList< SyncNote >() );
         
         this.notes.get( rtmTaskNote.getTaskSeriesId() )
                   .add( new SyncNote( null, rtmTaskNote ) );
      }
      
      for ( List< SyncNote > syncNotes : this.notes.values() )
      {
         Collections.sort( syncNotes, LESS_ID );
      }
   }
   
   
   
   public List< SyncNote > getSyncNotes()
   {
      return getSyncNotes( LESS_ID );
   }
   
   
   
   public List< SyncNote > getSyncNotes( Comparator< ? super SyncNote > cmp )
   {
      List< SyncNote > res = new LinkedList< SyncNote >();
      
      for ( List< SyncNote > syncNotes : this.notes.values() )
      {
         res.addAll( syncNotes );
      }
      
      if ( cmp != null )
         GetMode.SORTED.perform( res, cmp );
      
      return res;
   }
   
   
   
   public void add( SyncNote note )
   {
      update( note );
   }
   
   
   
   public void remove( SyncNote note )
   {
      if ( note == null )
         throw new NullPointerException( "note is null" );
      
      if ( note.getTaskSeriesId() == null )
         throw new AssertionError( "note with taskseriesId expected" );
      
      final List< SyncNote > syncNotes = notes.get( note.getTaskSeriesId() );
      
      if ( syncNotes != null )
      {
         final int pos = Collections.binarySearch( syncNotes, note, LESS_ID );
         
         if ( pos >= 0 )
            notes.remove( pos );
      }
   }
   
   
   
   public void intersect( List< RtmTaskNote > notesToIntersectWith )
   {
      final Map< String, List< SyncNote >> intersectedMap = new HashMap< String, List< SyncNote > >();
      
      final List< RtmTaskNote > sortedRtmTaskNotes = new ArrayList< RtmTaskNote >( notesToIntersectWith );
      Collections.sort( sortedRtmTaskNotes, LESS_TASKSERIES_ID );
      
      Pair< Integer, Integer > slice = getNextSlice( sortedRtmTaskNotes, null );
      
      if ( slice.first.intValue() > -1 )
      {
         do
         {
            final int sliceStartIndex = slice.first.intValue();
            final int sliceEndIndex = slice.second.intValue();
            final String sliceStartTaskSeriesId = sortedRtmTaskNotes.get( sliceStartIndex )
                                                                    .getTaskSeriesId();
            
            final List< SyncNote > allSyncNotes = notes.get( sliceStartTaskSeriesId );
            
            if ( allSyncNotes != null )
            {
               final List< SyncNote > intersectedSyncNotes = new LinkedList< SyncNote >();
               
               for ( int i = sliceStartIndex; i < sliceEndIndex; ++i )
               {
                  final String noteId = sortedRtmTaskNotes.get( i ).getId();
                  
                  boolean found = false;
                  for ( Iterator< SyncNote > iterAllSyncNotes = allSyncNotes.iterator(); !found
                     && iterAllSyncNotes.hasNext(); )
                  {
                     final SyncNote syncNote = iterAllSyncNotes.next();
                     found = syncNote.getId().equals( noteId );
                     if ( found )
                     {
                        intersectedSyncNotes.add( syncNote );
                     }
                  }
               }
               
               intersectedMap.put( sliceStartTaskSeriesId, intersectedSyncNotes );
            }
            
            slice = getNextSlice( sortedRtmTaskNotes, slice.second );
         }
         while ( slice.first.intValue() > -1 );
      }
      
      notes = intersectedMap;
   }
   
   
   
   private Pair< Integer, Integer > getNextSlice( List< RtmTaskNote > allSortedNotes,
                                                  Integer formerSliceEndIndex )
   {
      final int notesCnt = allSortedNotes.size();
      
      int sliceBeginIndex = -1;
      int sliceEndIndex = -1;
      
      if ( formerSliceEndIndex != null )
      {
         if ( formerSliceEndIndex.intValue() < notesCnt )
         {
            sliceBeginIndex = formerSliceEndIndex.intValue();
         }
      }
      else if ( notesCnt > 0 )
      {
         sliceBeginIndex = 0;
      }
      
      if ( sliceBeginIndex != -1 )
      {
         final String sliceBeginNoteTaskseriesId = allSortedNotes.get( sliceBeginIndex )
                                                                 .getTaskSeriesId();
         
         for ( sliceEndIndex = sliceBeginIndex + 1; sliceEndIndex < notesCnt
            && sliceBeginNoteTaskseriesId.equals( allSortedNotes.get( sliceEndIndex )
                                                                .getTaskSeriesId() ); ++sliceEndIndex )
         {
         }
      }
      
      return Pair.create( Integer.valueOf( sliceBeginIndex ),
                          Integer.valueOf( sliceEndIndex ) );
   }
   
   
   
   public void filterByTaskSeriesIds( List< String > taskSeriesIds )
   {
      for ( Iterator< String > iterTaskSerieses = this.notes.keySet()
                                                            .iterator(); iterTaskSerieses.hasNext(); )
      {
         final String taskSeriesId = iterTaskSerieses.next();
         
         if ( !taskSeriesIds.contains( taskSeriesId ) )
            iterTaskSerieses.remove();
      }
   }
   
   
   
   public List< String > getTaskSeriesIds()
   {
      return new ArrayList< String >( notes.keySet() );
   }
   
   
   
   public List< SyncNote > get( int location )
   {
      return notes.get( location );
   }
   
   
   
   public int size()
   {
      return notes.size();
   }
   
   
   
   public void update( SyncNote note )
   {
      if ( note == null )
         throw new NullPointerException( "note is null" );
      
      if ( note.getTaskSeriesId() == null )
         throw new AssertionError( "note with taskseriesId expected" );
      
      List< SyncNote > syncNotes = notes.get( note.getTaskSeriesId() );
      
      if ( syncNotes == null )
      {
         syncNotes = new ArrayList< SyncNote >();
         notes.put( note.getTaskSeriesId(), syncNotes );
      }
      
      // If the set already contains an element in respect to the Comparator,
      // then we update it by the new.
      final int pos = Collections.binarySearch( syncNotes, note, LESS_ID );
      
      if ( pos >= 0 )
      {
         syncNotes.remove( pos );
         syncNotes.add( pos, note );
      }
      else
      {
         syncNotes.add( ( -pos - 1 ), note );
      }
   }
   
   
   
   @Override
   public String toString()
   {
      return "<" + notes.size() + ">";
   }
}
