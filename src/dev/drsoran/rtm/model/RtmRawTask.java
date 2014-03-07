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

package dev.drsoran.rtm.model;

public class RtmRawTask
{
   private final String id;
   
   private final long addedMillisUtc;
   
   private final long deletedMillisUtc;
   
   private final long completedMillisUtc;
   
   private final Priority priority;
   
   private final int numPostponed;
   
   private final long dueMillisUtc;
   
   private final boolean hasDueTime;
   
   private final String estimationSentence;
   
   
   
   public RtmRawTask( String id, long addedMillisUtc, long deletedMillisUtc,
      long completedMillisUtc, Priority priority, int numPostponed,
      long dueMillisUtc, boolean hasDueTime, String estimationSentence )
   {
      if ( addedMillisUtc == RtmConstants.NO_TIME )
      {
         throw new IllegalArgumentException( "addedMillisUtc" );
      }
      
      if ( numPostponed < 0 )
      {
         throw new IllegalArgumentException( "numPostponed" );
      }
      
      this.id = id;
      this.addedMillisUtc = addedMillisUtc;
      this.deletedMillisUtc = deletedMillisUtc;
      this.completedMillisUtc = completedMillisUtc;
      this.priority = priority;
      this.numPostponed = numPostponed;
      this.dueMillisUtc = dueMillisUtc;
      this.hasDueTime = hasDueTime;
      this.estimationSentence = estimationSentence;
   }
   
   
   
   public String getId()
   {
      return id;
   }
   
   
   
   public long getAddedMillisUtc()
   {
      return addedMillisUtc;
   }
   
   
   
   public long getDeletedMillisUtc()
   {
      return deletedMillisUtc;
   }
   
   
   
   public long getCompletedMillisUtc()
   {
      return completedMillisUtc;
   }
   
   
   
   public Priority getPriority()
   {
      return priority;
   }
   
   
   
   public int getPostponedCount()
   {
      return numPostponed;
   }
   
   
   
   public long getDueMillisUtc()
   {
      return dueMillisUtc;
   }
   
   
   
   public boolean hasDueTime()
   {
      return hasDueTime;
   }
   
   
   
   public String getEstimationSentence()
   {
      return estimationSentence;
   }
   
   
   
   @Override
   public String toString()
   {
      return String.format( "RtmRawTask [id=%s, added=%s, completed=%s deleted=%s]",
                            id,
                            addedMillisUtc,
                            completedMillisUtc,
                            deletedMillisUtc );
   }
}
