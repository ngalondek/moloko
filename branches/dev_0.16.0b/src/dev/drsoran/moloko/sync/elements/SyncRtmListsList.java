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
import java.util.Iterator;
import java.util.List;

import com.mdt.rtm.data.RtmList;


public class SyncRtmListsList
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
   
   private static final Comparator< SyncRtmList > LESS_ID = new Comparator< SyncRtmList >()
   {
      public int compare( SyncRtmList object1, SyncRtmList object2 )
      {
         return object1.getId().compareTo( object2.getId() );
      }
   };
   
   private final List< SyncRtmList > lists;
   
   

   public SyncRtmListsList()
   {
      lists = new ArrayList< SyncRtmList >();
   }
   


   public SyncRtmListsList( List< RtmList > lists )
   {
      if ( lists == null )
         throw new NullPointerException( "lists is null" );
      
      this.lists = new ArrayList< SyncRtmList >( lists.size() );
      
      for ( RtmList rtmList : lists )
         this.lists.add( new SyncRtmList( rtmList ) );
      
      Collections.sort( this.lists, LESS_ID );
   }
   


   public List< SyncRtmList > getSyncLists()
   {
      return getSyncLists( SyncRtmList.LESS_ID );
   }
   


   public List< SyncRtmList > getSyncLists( Comparator< ? super SyncRtmList > cmp )
   {
      final List< SyncRtmList > res = new ArrayList< SyncRtmList >( lists );
      
      if ( cmp != null )
         GetMode.SORTED.perform( res, cmp );
      
      return res;
   }
   


   public void add( SyncRtmList list )
   {
      update( list );
   }
   


   public void remove( SyncRtmList list )
   {
      final int pos = Collections.binarySearch( lists, list, LESS_ID );
      
      if ( pos >= 0 )
         lists.remove( pos );
   }
   


   public void intersect( List< RtmList > lists )
   {
      for ( Iterator< SyncRtmList > i = this.lists.iterator(); i.hasNext(); )
      {
         SyncRtmList syncList = i.next();
         
         boolean found = false;
         
         for ( RtmList rtmList : lists )
         {
            if ( rtmList.getId().equals( syncList.getId() ) )
            {
               found = true;
               break;
            }
         }
         
         if ( !found )
            i.remove();
      }
   }
   


   public SyncRtmList get( int location )
   {
      return lists.get( location );
   }
   


   public int size()
   {
      return lists.size();
   }
   


   public void update( SyncRtmList list )
   {
      if ( list == null )
         throw new NullPointerException( "list is null" );
      
      // If the set already contains an element in respect to the Comparator,
      // then we update it by the new.
      final int pos = Collections.binarySearch( lists, list, LESS_ID );
      
      if ( pos >= 0 )
      {
         lists.remove( pos );
         lists.add( pos, list );
      }
      else
      {
         lists.add( ( -pos - 1 ), list );
      }
   }
   


   @Override
   public String toString()
   {
      return "<" + lists.size() + ">";
   }
}
