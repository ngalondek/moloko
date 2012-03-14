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

package dev.drsoran.moloko.notification;

import java.util.Calendar;

import android.content.ContentProviderClient;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.RemoteException;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.util.MolokoCalendar;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.Tasks;


class LoadHoleDayDueTasksAsyncTask extends AbstractNotificationTasksLoader
{
   private final static String DUE_TASKS_QUERY_WITH_REMIND_BEFORE = Tasks.HAS_DUE_TIME
      + " != 0 AND "
      + Tasks.DUE_DATE
      + " >= ? AND "
      + Tasks.DUE_DATE
      + " < ? AND "
      + Tasks.COMPLETED_DATE
      + " IS NULL AND "
      + Tasks.DELETED_DATE + " IS NULL";
   
   private final long remindBeforeMillis;
   
   
   
   public LoadHoleDayDueTasksAsyncTask( Context context, Handler handler,
      long remindBeforeMillis )
   {
      super( context, handler );
      
      this.remindBeforeMillis = remindBeforeMillis;
   }
   
   
   
   @Override
   protected Cursor doInBackground( Void... params )
   {
      Cursor result = null;
      
      final ContentProviderClient client = context.getContentResolver()
                                                  .acquireContentProviderClient( Tasks.CONTENT_URI );
      
      if ( client != null )
      {
         final String selection = getDueTasksSelection( remindBeforeMillis );
         
         try
         {
            result = client.query( Tasks.CONTENT_URI,
                                   TasksProviderPart.PROJECTION,
                                   selection,
                                   null,
                                   Tasks.DUE_DATE );
         }
         catch ( RemoteException e )
         {
            result = null;
         }
         
         client.release();
      }
      
      return result;
   }
   
   
   
   private String getDueTasksSelection( long remindBeforeMillis )
   {
      final MolokoCalendar cal = getDateOnlyCalendar();
      final long today = cal.getTimeInMillis();
      
      cal.roll( Calendar.DAY_OF_YEAR, true );
      
      final long tomorrowPlusReminder = cal.getTimeInMillis()
         + remindBeforeMillis;
      
      final String result = Queries.bindAll( DUE_TASKS_QUERY_WITH_REMIND_BEFORE,
                                             new String[]
                                             {
                                              String.valueOf( today ),
                                              String.valueOf( tomorrowPlusReminder ) } );
      
      return result;
   }
   
   
   
   private static MolokoCalendar getDateOnlyCalendar()
   {
      final MolokoCalendar cal = MolokoCalendar.getInstance();
      cal.setHasTime( false );
      
      return cal;
   }
}
