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
   
   private static final Comparator< RtmTaskNote > LESS_ID = new Comparator< RtmTaskNote >()
   {
      public int compare( RtmTaskNote object1, RtmTaskNote object2 )
      {
         return object1.getId().compareTo( object2.getId() );
      }
   };
   
   private final List< RtmTaskNote > notes;
   
   

   public SyncRtmTaskNotesList()
   {
      notes = new ArrayList< RtmTaskNote >();
   }
   


   public SyncRtmTaskNotesList( List< RtmTaskNote > notes )
   {
      if ( notes == null )
         throw new NullPointerException( "notes is null" );
      
      this.notes = new ArrayList< RtmTaskNote >( notes );
      
      Collections.sort( this.notes, LESS_ID );
   }
   


   public List< SyncNote > getSyncNotes()
   {
      return getSyncNotes( SyncNote.LESS_ID );
   }
   


   public List< SyncNote > getSyncNotes( Comparator< ? super SyncNote > cmp )
   {
      final List< SyncNote > res = new LinkedList< SyncNote >();
      
      for ( RtmTaskNote note : notes )
         res.add( new SyncNote( note ) );
      
      if ( cmp != null )
         GetMode.SORTED.perform( res, cmp );
      
      return res;
   }
   


   public void add( RtmTaskNote note )
   {
      update( note );
   }
   


   public void remove( RtmTaskNote note )
   {
      final int pos = Collections.binarySearch( notes, note, LESS_ID );
      
      if ( pos >= 0 )
         notes.remove( pos );
   }
   


   public RtmTaskNote get( int location )
   {
      return notes.get( location );
   }
   


   public int size()
   {
      return notes.size();
   }
   


   public void update( RtmTaskNote note )
   {
      if ( note == null )
         throw new NullPointerException( "note is null" );
      
      // If the set already contains an element in respect to the Comparator,
      // then we update it by the new.
      final int pos = Collections.binarySearch( notes, note, LESS_ID );
      
      if ( pos >= 0 )
      {
         notes.remove( pos );
         notes.add( pos, note );
      }
      else
      {
         notes.add( ( -pos - 1 ), note );
      }
   }
   


   @Override
   public String toString()
   {
      return "<" + notes.size() + ">";
   }
}
