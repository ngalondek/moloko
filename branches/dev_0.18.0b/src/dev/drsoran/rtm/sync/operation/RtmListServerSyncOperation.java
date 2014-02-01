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

package dev.drsoran.moloko.sync.operation;

import java.util.Collections;


public class RtmListServerSyncOperation extends ServerSyncOperation< RtmList >
{
   private SyncRtmListsList resultList;
   
   

   public RtmListServerSyncOperation( Builder< RtmList > builder )
   {
      super( builder );
   }
   


   @Override
   protected RtmList handleResultElement( RtmList resultElement )
   {
      final RtmList result = super.handleResultElement( resultElement );
      
      if ( result != null )
      {
         if ( resultList == null )
         {
            resultList = new SyncRtmListsList( Collections.singletonList( result ) );
         }
         else
         {
            // Finally we want to have one list with RtmLists containing
            // all changes from all server operations. So we merge the current
            // change to the final resultList.
            resultList.update( new SyncRtmList( result ) );
         }
      }
      
      return result;
   }
}
