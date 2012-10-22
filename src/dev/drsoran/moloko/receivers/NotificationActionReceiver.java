/* 
 *	Copyright (c) 2012 Ronny Röhricht
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

package dev.drsoran.moloko.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import dev.drsoran.moloko.ApplyChangesInfo;
import dev.drsoran.moloko.content.ActionItemListApplier;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.TaskEditUtils;
import dev.drsoran.rtm.Task;


public class NotificationActionReceiver extends BroadcastReceiver
{
   @Override
   public void onReceive( Context context, Intent intent )
   {
      ApplyChangesInfo applyChangesInfo = null;
      if ( Intents.Action.TASK_COMPLETED_FROM_NOTIFICATION.equals( intent.getAction() ) )
      {
         applyChangesInfo = TaskEditUtils.setTaskCompletion( context,
                                                             getTaskFromIntent( intent ),
                                                             true );
      }
      else if ( Intents.Action.TASK_POSTPONED_FROM_NOTIFICATION.equals( intent.getAction() ) )
      {
         applyChangesInfo = TaskEditUtils.postponeTask( context,
                                                        getTaskFromIntent( intent ) );
      }
      
      if ( applyChangesInfo != null )
      {
         new ActionItemListApplier( context ).applyInBackground( applyChangesInfo.getActionItems() );
      }
   }
   
   
   
   private Task getTaskFromIntent( Intent intent )
   {
      return intent.getParcelableExtra( Intents.Extras.KEY_TASK );
   }
}
