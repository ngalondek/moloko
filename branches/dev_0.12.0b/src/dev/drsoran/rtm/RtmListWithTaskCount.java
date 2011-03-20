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

package dev.drsoran.rtm;

import android.content.Context;
import android.util.Log;

import com.mdt.rtm.data.RtmList;

import dev.drsoran.moloko.content.ListOverviewsProviderPart;


public class RtmListWithTaskCount
{
   private final static String TAG = "Moloko."
      + RtmListWithTaskCount.class.getSimpleName();
   
   
   public final static class ExtendedListInfo
   {
      public int dueTodayTaskCount;
      
      public int dueTomorrowTaskCount;
      
      public int overDueTaskCount;
      
      public long sumEstimated;
      
      public int completedTaskCount;
      
      

      public ExtendedListInfo()
      {
         this.dueTodayTaskCount = 0;
         this.dueTomorrowTaskCount = 0;
         this.overDueTaskCount = 0;
         this.sumEstimated = -1;
         this.completedTaskCount = 0;
      }
      


      public ExtendedListInfo( int dueTodayTaskCount, int dueTomorrowTaskCount,
         int overDueTaskCount, long sumEstimated, int completedTaskCount )
      {
         this.dueTodayTaskCount = dueTodayTaskCount;
         this.dueTomorrowTaskCount = dueTomorrowTaskCount;
         this.overDueTaskCount = overDueTaskCount;
         this.sumEstimated = sumEstimated;
         this.completedTaskCount = completedTaskCount;
      }
   }
   
   private final RtmList impl;
   
   private final int incompletedTaskCount;
   
   private ExtendedListInfo extendedListInfo = null;
   
   

   public RtmListWithTaskCount( RtmList impl, int taskCount )
      throws NullPointerException
   {
      if ( impl == null )
         throw new NullPointerException();
      
      this.impl = impl;
      this.incompletedTaskCount = taskCount;
   }
   


   public RtmListWithTaskCount( String id, String name, int locked,
      int archived, int position, RtmSmartFilter smartFilter,
      int incompletedTaskCount )
   {
      this.impl = new RtmList( id,
                               name,
                               null,
                               null,
                               null,
                               locked,
                               archived,
                               position,
                               smartFilter );
      this.incompletedTaskCount = incompletedTaskCount;
   }
   


   public String getId()
   {
      return impl.getId();
   }
   


   public String getName()
   {
      return impl.getName();
   }
   


   public int getLocked()
   {
      return impl.getLocked();
   }
   


   public int getArchived()
   {
      return impl.getArchived();
   }
   


   public int getPosition()
   {
      return impl.getPosition();
   }
   


   public RtmSmartFilter getSmartFilter()
   {
      return impl.getSmartFilter();
   }
   


   public boolean hasSmartFilter()
   {
      return impl.getSmartFilter() != null;
   }
   


   public boolean isSmartFilterValid()
   {
      return !hasSmartFilter() || incompletedTaskCount > -1;
   }
   


   public int getIncompleteTaskCount()
   {
      return incompletedTaskCount;
   }
   


   public ExtendedListInfo getExtendedListInfo( Context context )
   {
      if ( extendedListInfo == null )
      {
         if ( isSmartFilterValid() )
         {
            extendedListInfo = ListOverviewsProviderPart.getExtendedOverview( context.getContentResolver(),
                                                                              getId(),
                                                                              hasSmartFilter()
                                                                                              ? getSmartFilter().getFilterString()
                                                                                              : null );
         }
         
         if ( extendedListInfo == null )
         {
            Log.e( TAG, "Unable to create ExtendedListInfo." );
            
            // RETURN: Create temporary empty instance
            return new ExtendedListInfo();
         }
      }
      
      return extendedListInfo;
   }
   


   @Override
   public String toString()
   {
      return "<" + impl.toString() + "," + incompletedTaskCount + ">";
   }
}
