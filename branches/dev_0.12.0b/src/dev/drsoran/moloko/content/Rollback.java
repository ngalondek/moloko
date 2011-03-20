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

package dev.drsoran.moloko.content;

public final class Rollback
{
   private final String id;
   
   private final String timeLineId;
   
   private final String transactionId;
   
   private final long timeStamp;
   
   

   private Rollback( String id, String timeLineId, String transactionId,
      long timeStamp )
   {
      this.id = id;
      this.timeLineId = timeLineId;
      this.transactionId = transactionId;
      this.timeStamp = timeStamp;
   }
   


   public String getId()
   {
      return id;
   }
   


   public String getTimeLineId()
   {
      return timeLineId;
   }
   


   public String getTransactionId()
   {
      return transactionId;
   }
   


   public long getTimeStamp()
   {
      return timeStamp;
   }
   


   @Override
   public String toString()
   {
      return "<timeline=" + timeLineId + ", transaction=" + transactionId + ">";
   }
   


   public final static Rollback newRollback( String id,
                                             String timeLineId,
                                             String transactionId,
                                             long timeStamp )
   {
      return new Rollback( id, timeLineId, transactionId, timeStamp );
   }
   


   public final static Rollback newRollback( String timeLineId,
                                             String transactionId,
                                             long timeStamp )
   {
      return new Rollback( null, timeLineId, transactionId, timeStamp );
   }
   


   public final static Rollback newRollback( String timeLineId,
                                             String transactionId )
   {
      return new Rollback( null,
                           timeLineId,
                           transactionId,
                           System.currentTimeMillis() );
   }
}
