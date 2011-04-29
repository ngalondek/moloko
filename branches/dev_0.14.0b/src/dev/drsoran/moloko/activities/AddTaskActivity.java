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

package dev.drsoran.moloko.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.AlertDialog;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mdt.rtm.data.RtmTask;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.util.AsyncInsertEntity;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.ParticipantList;
import dev.drsoran.rtm.Task;


public class AddTaskActivity extends AbstractTaskEditActivity
{
   public final static int RESULT_INSERT_TASK_OK = 1 << 0;
   
   public final static int RESULT_INSERT_TASK_FAILED = 1 << 1;
   
   private Date created;
   
   

   @Override
   protected InitialValues onCreateImpl( Intent intent )
   {
      created = new Date();
      
      final List< String > tags = new ArrayList< String >( Arrays.asList( TextUtils.split( emptyIfNull( intent.getStringExtra( Tasks.TAGS ) ),
                                                                                           Tasks.TAGS_SEPARATOR ) ) );
      
      // Try to determine the list ID
      String listId = null;
      
      if ( intent.hasExtra( Tasks.LIST_ID ) )
         listId = intent.getStringExtra( Tasks.LIST_ID );
      else if ( intent.hasExtra( Tasks.LIST_NAME ) )
         listId = getListIdByName( intent.getStringExtra( Tasks.LIST_NAME ) );
      
      // Check if the list name is part of the tags. This can happen
      // if the list name has been entered w/o taken the suggestion.
      // So it has been parsed as a tag since the operator (#) is the
      // same.
      else
      {
         for ( Iterator< String > i = tags.iterator(); i.hasNext()
            && listId == null; )
         {
            final String tag = i.next();
            // Check if the tag is a list name
            listId = getListIdByName( tag );
            
            if ( listId != null )
               i.remove();
         }
      }
      
      // Try to determine the location ID
      String locationId = null;
      
      if ( intent.hasExtra( Tasks.LOCATION_ID ) )
         locationId = intent.getStringExtra( Tasks.LOCATION_ID );
      else if ( intent.hasExtra( Tasks.LOCATION_NAME ) )
         locationId = getLocationIdByName( intent.getStringExtra( Tasks.LOCATION_NAME ) );
      
      return new InitialValues( intent.getStringExtra( Tasks.TASKSERIES_NAME ),
                                listId,
                                RtmTask.convertPriority( RtmTask.convertPriority( emptyIfNull( intent.getStringExtra( Tasks.PRIORITY ) ) ) ),
                                TextUtils.join( Tasks.TAGS_SEPARATOR, tags ),
                                intent.getLongExtra( Tasks.DUE_DATE, -1 ),
                                intent.getBooleanExtra( Tasks.HAS_DUE_TIME,
                                                        false ),
                                intent.getStringExtra( Tasks.RECURRENCE ),
                                intent.getBooleanExtra( Tasks.RECURRENCE_EVERY,
                                                        true ),
                                intent.getStringExtra( Tasks.ESTIMATE ),
                                intent.getLongExtra( Tasks.ESTIMATE_MILLIS, -1 ),
                                locationId,
                                (String) null );
   }
   


   @Override
   protected boolean shouldHandleIntentAction( String action )
   {
      return action.equals( Intent.ACTION_INSERT );
   }
   


   @Override
   protected void refreshHeadSection( TextView addedDate,
                                      TextView completedDate,
                                      TextView postponed,
                                      TextView source )
   {
      addedDate.setText( MolokoDateUtils.formatDateTime( created.getTime(),
                                                         FULL_DATE_FLAGS ) );
      completedDate.setVisibility( View.GONE );
      postponed.setVisibility( View.GONE );
      
      source.setText( getString( R.string.task_source,
                                 getString( R.string.app_name ) ) );
   }
   


   @Override
   public void onDone( View v )
   {
      if ( validateInput() )
      {
         Uri newTaskUri = null;
         
         // Apply the modifications to the DB.
         // set the taskseries modification time to now
         try
         {
            final Task newTask = newTask();
            
            newTaskUri = new AsyncInsertEntity< Task >( this )
            {
               @Override
               protected int getProgressMessageId()
               {
                  return R.string.dlg_insert_task;
               }
               


               @Override
               protected List< ContentProviderOperation > getInsertOperations( ContentResolver contentResolver,
                                                                               Task entity )
               {
                  return TasksProviderPart.insertTask( contentResolver, entity );
               }
               


               @Override
               protected Uri getContentUri()
               {
                  return Tasks.CONTENT_URI;
               }
               


               @Override
               protected String getPath()
               {
                  return RawTasks.PATH;
               }
            }.execute( newTask ).get();
         }
         catch ( InterruptedException e )
         {
            Log.e( LogUtils.toTag( AddTaskActivity.class ),
                   "Failed to insert new task",
                   e );
         }
         catch ( ExecutionException e )
         {
            Log.e( LogUtils.toTag( AddTaskActivity.class ),
                   "Failed to insert new task",
                   e );
         }
         
         if ( newTaskUri != null )
            setResult( RESULT_INSERT_TASK_OK, getIntent().setData( newTaskUri ) );
         else
            setResult( RESULT_INSERT_TASK_FAILED );
         
         finish();
      }
   }
   


   @Override
   public void onCancel( View v )
   {
      new AlertDialog.Builder( this ).setMessage( R.string.add_task_edit_dlg_cancel )
                                     .setPositiveButton( android.R.string.yes,
                                                         new OnClickListener()
                                                         {
                                                            public void onClick( DialogInterface dialog,
                                                                                 int which )
                                                            {
                                                               setResult( RESULT_CANCELED );
                                                               finish();
                                                            }
                                                         } )
                                     .setNegativeButton( android.R.string.no,
                                                         null )
                                     .show();
   }
   


   @Override
   protected ModificationSet getModifications()
   {
      return ModificationSet.EMPTY_MODIFICATION_SET;
   }
   


   private String getListIdByName( String listName )
   {
      String res = null;
      
      if ( listNames_2_listId != null )
         res = listNames_2_listId.get( listName.toLowerCase() );
      
      return res;
   }
   


   private String getLocationIdByName( String locationName )
   {
      String res = null;
      
      if ( locationNames_2_locationIds != null )
         res = locationNames_2_locationIds.get( locationName.toLowerCase() );
      
      return res;
   }
   


   private final Task newTask()
   {
      final long dueDate = getCurrentValue( Tasks.DUE_DATE, Long.class );
      
      return new Task( (String) null,
                       (String) null,
                       (String) null,
                       false,
                       created,
                       created,
                       getCurrentValue( Tasks.TASKSERIES_NAME, String.class ),
                       TaskSeries.NEW_TASK_SOURCE,
                       getCurrentValue( Tasks.URL, String.class ),
                       getCurrentValue( Tasks.RECURRENCE, String.class ),
                       getCurrentValue( Tasks.RECURRENCE_EVERY, Boolean.class ),
                       getCurrentValue( Tasks.LOCATION_ID, String.class ),
                       getCurrentValue( Tasks.LIST_ID, String.class ),
                       dueDate == -1 ? null : new Date( dueDate ),
                       getCurrentValue( Tasks.HAS_DUE_TIME, Boolean.class ),
                       created,
                       (Date) null,
                       (Date) null,
                       RtmTask.convertPriority( getCurrentValue( Tasks.PRIORITY,
                                                                 String.class ) ),
                       0,
                       getCurrentValue( Tasks.ESTIMATE, String.class ),
                       getCurrentValue( Tasks.ESTIMATE_MILLIS, Long.class ),
                       (String) null,
                       0.0f,
                       0.0f,
                       (String) null,
                       false,
                       0,
                       getCurrentValue( Tasks.TAGS, String.class ),
                       (ParticipantList) null,
                       0 );
   }
   


   private final static String emptyIfNull( String string )
   {
      return string == null ? Strings.EMPTY_STRING : string;
   }
}
