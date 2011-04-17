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

package dev.drsoran.moloko.util;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.Modification;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.rtm.Task;


public final class TaskEditUtils
{
   private final static String TAG = "Moloko."
      + TaskEditUtils.class.getSimpleName();
   
   

   private TaskEditUtils()
   {
      throw new AssertionError();
   }
   


   public final static boolean setTaskCompletion( Activity activity,
                                                  Task task,
                                                  boolean complete )
   {
      return setTasksCompletion( activity,
                                 Collections.singletonList( task ),
                                 complete );
   }
   


   public final static boolean setTasksCompletion( Context context,
                                                   List< ? extends Task > tasks,
                                                   boolean complete )
   {
      boolean ok = true;
      
      if ( !tasks.isEmpty() )
      {
         final ModificationSet modifications = new ModificationSet();
         
         for ( Task task : tasks )
         {
            final Date complDate = task.getCompleted();
            
            if ( complDate == null && complete || complDate != null
               && !complete )
            {
               modifications.add( Modification.newModification( RawTasks.CONTENT_URI,
                                                                task.getId(),
                                                                RawTasks.COMPLETED_DATE,
                                                                complete
                                                                        ? System.currentTimeMillis()
                                                                        : null ) );
               
               modifications.add( Modification.newTaskModified( task.getTaskSeriesId() ) );
            }
         }
         
         ok = applyModifications( context, modifications );
         
         reportStatus( context, ok );
      }
      
      return ok;
   }
   


   public final static boolean postponeTask( Context context, Task task )
   {
      return postponeTasks( context, Collections.singletonList( task ) );
   }
   


   public final static boolean postponeTasks( Context context,
                                              List< ? extends Task > tasks )
   {
      /**
       * NOTE: RTM has no API to set the postponed count. One can only postpone a task and the count is the number of
       * calls, which affects also the due date of the task. Since we want offline postponing a task, we must alter the
       * due date only locally, but not sync it out. Finally the date becomes in sync with RTM if we call the postpone
       * API method and using the returned result task.
       * 
       * All following due date modifications are non-persistent. Means they only appear locally but are not going to be
       * synced out.
       **/
      
      boolean ok = true;
      
      if ( !tasks.isEmpty() )
      {
         final ModificationSet modifications = new ModificationSet();
         final Calendar cal = MolokoDateUtils.newCalendarUTC();
         
         for ( Task task : tasks )
         {
            final Date due = task.getDue();
            cal.setTimeInMillis( System.currentTimeMillis() );
            
            // If the task has no due date or is overdue, its due date is set to today.
            if ( due == null )
            {
               modifications.add( Modification.newNonPersistentModification( RawTasks.CONTENT_URI,
                                                                             task.getId(),
                                                                             RawTasks.DUE_DATE,
                                                                             cal.getTimeInMillis() ) );
            }
            else if ( MolokoDateUtils.isBefore( due.getTime(),
                                                cal.getTimeInMillis() ) )
            {
               final Calendar tmp = MolokoDateUtils.newCalendarUTC( due.getTime() );
               
               // Preserve the original time when setting to today
               cal.set( Calendar.HOUR_OF_DAY, tmp.get( Calendar.HOUR_OF_DAY ) );
               cal.set( Calendar.HOUR, tmp.get( Calendar.HOUR ) );
               cal.set( Calendar.MINUTE, tmp.get( Calendar.MINUTE ) );
               cal.set( Calendar.SECOND, tmp.get( Calendar.SECOND ) );
               cal.set( Calendar.MILLISECOND, 0 );
               
               modifications.add( Modification.newNonPersistentModification( RawTasks.CONTENT_URI,
                                                                             task.getId(),
                                                                             RawTasks.DUE_DATE,
                                                                             cal.getTimeInMillis() ) );
            }
            
            // Otherwise, the task due date is advanced a day.
            else
            {
               cal.setTime( due );
               cal.roll( Calendar.DAY_OF_YEAR, true );
               
               modifications.add( Modification.newNonPersistentModification( RawTasks.CONTENT_URI,
                                                                             task.getId(),
                                                                             RawTasks.DUE_DATE,
                                                                             cal.getTimeInMillis() ) );
            }
            
            modifications.add( Modification.newModification( RawTasks.CONTENT_URI,
                                                             task.getId(),
                                                             RawTasks.POSTPONED,
                                                             task.getPosponed() + 1 ) );
            
            modifications.add( Modification.newTaskModified( task.getTaskSeriesId() ) );
         }
         
         ok = applyModifications( context, modifications );
         
         reportStatus( context, ok );
      }
      
      return ok;
   }
   


   public final static boolean deleteTask( Activity activity, Task task )
   {
      return deleteTasks( activity, Collections.singletonList( task ) );
   }
   


   public final static boolean deleteTasks( Context context,
                                            List< ? extends Task > tasks )
   {
      boolean ok = true;
      
      if ( !tasks.isEmpty() )
      {
         final ModificationSet modifications = new ModificationSet();
         
         for ( Task task : tasks )
         {
            modifications.add( Modification.newNonPersistentModification( RawTasks.CONTENT_URI,
                                                                          task.getId(),
                                                                          RawTasks.DELETED_DATE,
                                                                          System.currentTimeMillis() ) );
            
            modifications.add( Modification.newTaskModified( task.getTaskSeriesId() ) );
         }
         
         ok = applyModifications( context, modifications );
         
         reportStatus( context, ok );
      }
      
      return ok;
   }
   


   private final static void reportStatus( Context context, boolean ok )
   {
      Toast.makeText( context,
                      ok ? R.string.task_save_ok : R.string.task_save_error,
                      Toast.LENGTH_SHORT ).show();
   }
   


   private final static boolean applyModifications( Context context,
                                                    ModificationSet modifications )
   {
      boolean ok = true;
      
      if ( modifications.size() > 0 )
      {
         try
         {
            ok = new ApplyModificationsTask( context ).execute( modifications )
                                                      .get();
         }
         catch ( InterruptedException e )
         {
            Log.e( TAG, "Applying task changes failed", e );
            ok = false;
         }
         catch ( ExecutionException e )
         {
            Log.e( TAG, "Applying task changes failed", e );
            ok = false;
         }
      }
      
      return ok;
   }
}
