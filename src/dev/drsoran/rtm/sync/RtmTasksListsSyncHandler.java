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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dev.drsoran.rtm.model.RtmTasksList;


public class RtmTasksListsSyncHandler implements IRtmSyncHandler< RtmTasksList >
{
   private final Comparator< RtmTasksList > comparator;
   
   
   
   public RtmTasksListsSyncHandler( Comparator< RtmTasksList > comparator )
   {
      if ( comparator == null )
      {
         throw new IllegalArgumentException( "comparator" );
      }
      
      this.comparator = comparator;
   }
   
   
   
   @Override
   public void handleIncomingSync( List< RtmTasksList > syncPartnerElements,
                                   List< RtmTasksList > rtmElements )
   {
      final Collection< Integer > untouchedElements = new ArrayList< Integer >();
      
      for ( RtmTasksList rtmTasksList : rtmElements )
      {
         final long pos = Collections.binarySearch( syncPartnerElements,
                                                    rtmTasksList,
                                                    comparator );
         
         // INSERT: The server element is not contained in the partner list.
         if ( pos < 0 )
         {
            // Check if the RTM element is not deleted
            if ( !rtmTasksList.isDeleted() )
            {
               
            }
            
            // We never had this RTM element locally, just skip it.
         }
         
         // UPDATE: The RTM element is contained in the partner list.
         else if ( !rtmTasksList.isDeleted() )
         {
            
         }
         
         // DELETE: The RTM element is contained in the partner list and is deleted.
         else
         {
            
         }
      }
   }
   
   
   
   @Override
   public void handleOutgoingSync( List< RtmTasksList > syncPartnerElements,
                                   List< RtmTasksList > rtmElements )
   {
   }
}
