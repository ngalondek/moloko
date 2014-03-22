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

package dev.drsoran.rtm.sync;

import java.util.Comparator;

import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.model.RtmLocation;
import dev.drsoran.rtm.model.RtmTask;
import dev.drsoran.rtm.model.RtmTasksList;


public final class RtmContentSort
{
   private RtmContentSort()
   {
      throw new AssertionError();
   }
   
   
   
   public static Comparator< RtmTask > getRtmTaskIdSort()
   {
      return new Comparator< RtmTask >()
      {
         @Override
         public int compare( RtmTask lhs, RtmTask rhs )
         {
            return RtmContentSort.compareIds( lhs.getId(), rhs.getId() );
         }
      };
   }
   
   
   
   public static Comparator< RtmTasksList > getRtmTasksListIdSort()
   {
      return new Comparator< RtmTasksList >()
      {
         @Override
         public int compare( RtmTasksList lhs, RtmTasksList rhs )
         {
            return RtmContentSort.compareIds( lhs.getId(), rhs.getId() );
         }
      };
   }
   
   
   
   public static Comparator< RtmLocation > getRtmLocationIdSort()
   {
      return new Comparator< RtmLocation >()
      {
         @Override
         public int compare( RtmLocation lhs, RtmLocation rhs )
         {
            return RtmContentSort.compareIds( lhs.getId(), rhs.getId() );
         }
      };
   }
   
   
   
   public static Comparator< RtmContact > getRtmContactIdSort()
   {
      return new Comparator< RtmContact >()
      {
         @Override
         public int compare( RtmContact lhs, RtmContact rhs )
         {
            return RtmContentSort.compareIds( lhs.getId(), rhs.getId() );
         }
      };
   }
   
   
   
   private static int compareIds( String lhs, String rhs )
   {
      if ( lhs == rhs )
      {
         return 0;
      }
      
      if ( lhs == null && rhs != null )
      {
         return -1;
      }
      
      if ( lhs != null && rhs == null )
      {
         return 1;
      }
      
      return lhs.compareTo( rhs );
   }
}
