/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.moloko.domain.model;

import java.io.Serializable;


public final class ExtendedTaskCount implements Serializable
{
   private static final long serialVersionUID = -8223110030488788833L;
   
   private final int incompleteTaskCount;
   
   private final int completedTaskCount;
   
   private final int dueTodayTaskCount;
   
   private final int dueTomorrowTaskCount;
   
   private final int overDueTaskCount;
   
   private final long sumEstimated;
   
   
   
   public ExtendedTaskCount( int incompleteTaskCount, int completedTaskCount,
      int dueTodayTaskCount, int dueTomorrowTaskCount, int overDueTaskCount,
      long sumEstimated )
   {
      if ( incompleteTaskCount < 0 )
      {
         throw new IllegalArgumentException( "incompleteTaskCount" );
      }
      if ( completedTaskCount < 0 )
      {
         throw new IllegalArgumentException( "completedTaskCount" );
      }
      if ( dueTodayTaskCount < 0 )
      {
         throw new IllegalArgumentException( "dueTodayTaskCount" );
      }
      if ( dueTomorrowTaskCount < 0 )
      {
         throw new IllegalArgumentException( "dueTomorrowTaskCount" );
      }
      if ( overDueTaskCount < 0 )
      {
         throw new IllegalArgumentException( "overDueTaskCount" );
      }
      if ( sumEstimated < 0 )
      {
         throw new IllegalArgumentException( "sumEstimated" );
      }
      
      this.incompleteTaskCount = incompleteTaskCount;
      this.completedTaskCount = completedTaskCount;
      this.dueTodayTaskCount = dueTodayTaskCount;
      this.dueTomorrowTaskCount = dueTomorrowTaskCount;
      this.overDueTaskCount = overDueTaskCount;
      this.sumEstimated = sumEstimated;
   }
   
   
   
   public int getIncompleteTaskCount()
   {
      return incompleteTaskCount;
   }
   
   
   
   public int getCompletedTaskCount()
   {
      return completedTaskCount;
   }
   
   
   
   public int getDueTodayTaskCount()
   {
      return dueTodayTaskCount;
   }
   
   
   
   public int getDueTomorrowTaskCount()
   {
      return dueTomorrowTaskCount;
   }
   
   
   
   public int getOverDueTaskCount()
   {
      return overDueTaskCount;
   }
   
   
   
   public long getSumEstimated()
   {
      return sumEstimated;
   }
   
   
   
   @Override
   public String toString()
   {
      return String.format( "ExtendedTaskCount [incompleteTaskCount=%s, completedTaskCount=%s, dueTodayTaskCount=%s, dueTomorrowTaskCount=%s, overDueTaskCount=%s, sumEstimated=%s]",
                            getIncompleteTaskCount(),
                            getCompletedTaskCount(),
                            getDueTodayTaskCount(),
                            getDueTomorrowTaskCount(),
                            getOverDueTaskCount(),
                            getSumEstimated() );
   }
}
