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

package dev.drsoran.moloko.app.taskedit;

import static dev.drsoran.moloko.content.Columns.TaskColumns.TASK_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;

import com.mdt.rtm.data.RtmTask;
import com.mdt.rtm.data.RtmTask.Priority;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.services.AppContentEditInfo;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.moloko.util.Strings;


class TaskAddFragment extends AbstractTaskEditFragment
{
   private final static String CREATED_DATE = "created_date";
   
   @InstanceState( key = Intents.Extras.KEY_TASK )
   private Task task;
   
   @InstanceState( key = CREATED_DATE, defaultValue = "-1" )
   private long created;
   
   
   
   public final static TaskAddFragment newInstance( Bundle config )
   {
      final TaskAddFragment fragment = new TaskAddFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   public TaskAddFragment()
   {
      registerAnnotatedConfiguredInstance( this, TaskAddFragment.class );
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      if ( task == null )
      {
         checkCreatedDate();
         createNewTaskFromArguments();
      }
   }
   
   
   
   private void createNewTaskFromArguments()
   {
      final Bundle args = getArguments();
      task = new Task( Constants.NO_ID,
                       created,
                       created,
                       args.getString( TASK_NAME, Strings.EMPTY_STRING ),
                       listId,
                       listName );
      
      final Bundle initialValues = new Bundle( 14 );
      initialValues.putString( Args.TASK_NAME, null );
      
      final String listId = determineListId( args );
      initialValues.putString( Args.LIST_ID, listId );
      
      final String locationId = determineLocationId( args );
      initialValues.putString( Args.LOCATION_ID, locationId );
      
      initialValues.putString( Args.PRIORITY,
                               RtmTask.convertPriority( Priority.None ) );
      initialValues.putString( Args.TAGS, Strings.EMPTY_STRING );
      initialValues.putLong( Args.DUE_DATE, -1 );
      initialValues.putBoolean( Args.HAS_DUE_TIME, false );
      initialValues.putString( Args.RECURRENCE, null );
      initialValues.putBoolean( Args.RECURRENCE_EVERY, false );
      initialValues.putString( Args.ESTIMATE, null );
      initialValues.putLong( Args.ESTIMATE_MILLIS, -1 );
      initialValues.putString( Args.URL, null );
      
      return initialValues;
   }
   
   
   
   @Override
   protected void determineInitialChanges()
   {
      super.determineInitialChanges();
      
      // If there are changes made by the user, we do not replace them
      // by the initial changes given by the arguments.
      if ( getChanges().isEmpty() )
      {
         final Bundle args = getArguments();
         
         putChange( Tasks.TASKSERIES_NAME,
                    args.getString( Args.TASK_NAME ),
                    String.class );
         
         String priority = args.getString( Args.PRIORITY );
         if ( TextUtils.isEmpty( priority ) )
         {
            priority = RtmTask.convertPriority( Priority.None );
         }
         
         putChange( Tasks.PRIORITY, priority, String.class );
         putChange( Tasks.TAGS,
                    TextUtils.join( Tasks.TAGS_SEPARATOR, getTagsListFromArgs() ),
                    String.class );
         putChange( Tasks.DUE_DATE,
                    args.getLong( Args.DUE_DATE, -1 ),
                    Long.class );
         putChange( Tasks.HAS_DUE_TIME,
                    args.getBoolean( Args.HAS_DUE_TIME, false ),
                    Boolean.class );
         putChange( Tasks.RECURRENCE,
                    args.getString( Args.RECURRENCE ),
                    String.class );
         putChange( Tasks.RECURRENCE_EVERY,
                    args.getBoolean( Args.RECURRENCE_EVERY, false ),
                    Boolean.class );
         putChange( Tasks.ESTIMATE,
                    args.getString( Args.ESTIMATE ),
                    String.class );
         putChange( Tasks.ESTIMATE_MILLIS,
                    args.getLong( Args.ESTIMATE_MILLIS, -1 ),
                    Long.class );
         putChange( Tasks.URL, args.getString( Args.URL ), String.class );
      }
   }
   
   
   
   @Override
   protected void initializeHeadSection()
   {
      addedDate.setText( getUiContext().getDateFormatter()
                                       .formatDateTime( created,
                                                        FULL_DATE_FLAGS ) );
      completedDate.setVisibility( View.GONE );
      postponed.setVisibility( View.GONE );
      
      source.setText( getString( R.string.task_source,
                                 getString( R.string.app_name ) ) );
   }
   
   
   
   private void checkCreatedDate()
   {
      if ( created == -1 )
      {
         created = System.currentTimeMillis();
      }
   }
   
   
   
   private String determineListId( Bundle args )
   {
      String listId = args.getString( Args.LIST_ID );
      
      if ( TextUtils.isEmpty( listId ) )
      {
         final String listName = args.getString( Args.LIST_NAME );
         
         if ( !TextUtils.isEmpty( listName ) )
         {
            listId = getIdByName( getLoaderDataAssertNotNull().getListIdsWithListNames(),
                                  listName );
         }
         
         if ( TextUtils.isEmpty( listId ) )
         {
            final List< String > tags = getTagsListFromArgs();
            // Check if a list name is part of the tags. This can happen
            // if a list name has been entered w/o taken the suggestion.
            // So it has been parsed as a tag since the operator (#) is the
            // same.
            final String lastRemovedListId = removeListsFromTags( tags );
            listId = lastRemovedListId;
         }
         
         if ( TextUtils.isEmpty( listId ) )
         {
            listId = getIdByName( getLoaderDataAssertNotNull().getListIdsWithListNames(),
                                  getString( R.string.app_list_name_inbox ) );
         }
      }
      
      return listId;
   }
   
   
   
   private String determineLocationId( Bundle args )
   {
      String locationId = args.getString( Args.LOCATION_ID );
      
      if ( TextUtils.isEmpty( locationId ) )
      {
         final String locationName = args.getString( Args.LOCATION_NAME );
         
         if ( !TextUtils.isEmpty( locationName ) )
         {
            locationId = getIdByName( getLoaderDataAssertNotNull().getLocationIdsWithLocationNames(),
                                      locationName );
         }
      }
      
      return locationId;
   }
   
   
   
   private List< String > getTagsListFromArgs()
   {
      return new ArrayList< String >( Arrays.asList( TextUtils.split( Strings.emptyIfNull( getArguments().getString( Args.TAGS ) ),
                                                                      Tasks.TAGS_SEPARATOR ) ) );
   }
   
   
   
   /**
    * @return the list ID of the last removed list name
    */
   private String removeListsFromTags( List< String > tags )
   {
      String listId = null;
      
      if ( tags.size() > 0 )
      {
         final List< Pair< String, String > > listIdsToName = getLoaderDataAssertNotNull().getListIdsWithListNames();
         
         for ( Iterator< String > i = tags.iterator(); i.hasNext(); )
         {
            final String tag = i.next();
            // Check if the tag is a list name
            listId = getIdByName( listIdsToName, tag );
            
            if ( listId != null )
            {
               i.remove();
            }
         }
      }
      
      return listId;
   }
   
   
   
   private String getIdByName( List< Pair< String, String >> idsToNames,
                               String name )
   {
      String res = null;
      
      for ( Iterator< Pair< String, String >> i = idsToNames.iterator(); res == null
         && i.hasNext(); )
      {
         final Pair< String, String > listIdToListName = i.next();
         if ( listIdToListName.second.equalsIgnoreCase( name ) )
         {
            res = listIdToListName.first;
         }
      }
      
      return res;
   }
   
   
   
   @Override
   protected AppContentEditInfo getApplyChangesInfo()
   {
      saveChanges();
      
      final Task newTask = newTask();
      final AppContentEditInfo modifications = TaskEditUtils.insertTask( getSherlockActivity(),
                                                                         newTask );
      return modifications;
   }
   
   
   
   @Override
   protected List< Task > getEditedTasks()
   {
      // this is not supported since we add a new task
      throw new UnsupportedOperationException();
   }
   
   
   
   private final Task newTask()
   {
      final Date createdDate = new Date( created );
      final long dueDate = getCurrentValue( Tasks.DUE_DATE, Long.class );
      
      return new Task( newTaskIds.rawTaskId,
                       newTaskIds.taskSeriesId,
                       (String) null,
                       false,
                       createdDate,
                       createdDate,
                       getCurrentValue( Tasks.TASKSERIES_NAME, String.class ),
                       TaskSeries.NEW_TASK_SOURCE,
                       getCurrentValue( Tasks.URL, String.class ),
                       getCurrentValue( Tasks.RECURRENCE, String.class ),
                       getCurrentValue( Tasks.RECURRENCE_EVERY, Boolean.class ),
                       getCurrentValue( Tasks.LOCATION_ID, String.class ),
                       getCurrentValue( Tasks.LIST_ID, String.class ),
                       dueDate == -1 ? null : new Date( dueDate ),
                       getCurrentValue( Tasks.HAS_DUE_TIME, Boolean.class ),
                       createdDate,
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
                       Strings.EMPTY_STRING );
   }
}
