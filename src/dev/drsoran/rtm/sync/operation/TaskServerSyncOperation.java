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



public class TaskServerSyncOperation extends ServerSyncOperation< RtmTaskList >
{
   private SyncRtmTaskList resultList;
   
   

   public TaskServerSyncOperation( Builder< RtmTaskList > builder )
   {
      super( builder );
   }
   


   @Override
   protected RtmTaskList handleResultElement( RtmTaskList resultElement )
   {
      final RtmTaskList result = super.handleResultElement( resultElement );
      
      if ( result != null )
      {
         if ( resultList == null )
         {
            resultList = new SyncRtmTaskList( result );
         }
         else
         {
            // Finally we want to have one RtmTaskList with TaskSeries containing
            // all changes from all server operations. So we merge the current
            // change to the final resultList.
            resultList.update( result );
         }
      }
      
      return resultList.toRtmTaskList();
   }
}
