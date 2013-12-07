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

package com.mdt.rtm;

import org.w3c.dom.Element;


import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.model.RtmData;


public class TimeLineResult< V >
{
   public final static class Transaction
   {
      public final String timelineId;
      
      public final String transactionId;
      
      public final boolean undoable;
      
      

      Transaction( Element elt, String timelineId ) throws RtmServiceException
      {
         this.timelineId = timelineId;
         
         if ( !elt.getNodeName().equalsIgnoreCase( "transaction" ) )
            throw new ServiceInternalException( "transaction element expected. Found "
               + elt.getNodeName() );
         
         this.transactionId = RtmData.textNullIfEmpty( elt, "id" );
         
         if ( transactionId == null )
            throw new ServiceInternalException( "No id element found" );
         
         try
         {
            this.undoable = Integer.valueOf( RtmData.textNullIfEmpty( elt,
                                                                      "undoable" ) ) != 0;
         }
         catch ( NumberFormatException e )
         {
            throw new ServiceInternalException( "Invalid value for undoable", e );
         }
      }
   }
   
   public final Transaction transaction;
   
   public final V element;
   
   

   TimeLineResult( Element elt, String timelineId, V element )
      throws RtmServiceException
   {
      this.transaction = new Transaction( elt, timelineId );
      this.element = element;
   }
   


   public final static < V > TimeLineResult< V > newResult( Element elt,
                                                            String timelineId ) throws RtmServiceException
   {
      return newResult( elt, timelineId, null );
   }
   


   public final static < V > TimeLineResult< V > newResult( Element elt,
                                                            String timelineId,
                                                            V element ) throws RtmServiceException
   {
      return new TimeLineResult< V >( elt, timelineId, element );
   }
}
