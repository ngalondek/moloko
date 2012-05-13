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

package dev.drsoran.moloko.fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;

import com.mdt.rtm.data.RtmTask;

import dev.drsoran.moloko.ApplyChangesInfo;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.content.TasksProviderPart.NewTaskIds;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.TaskEditUtils;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.ParticipantList;
import dev.drsoran.rtm.Task;


public class TaskAddFragment extends AbstractTaskEditFragment
{
   private final static class Config
   {
      public final static String TASK_NAME = Tasks.TASKSERIES_NAME;
      
      public final static String LIST_ID = Tasks.LIST_ID;
      
      public final static String LIST_NAME = Tasks.LIST_NAME;
      
      public final static String LOCATION_ID = Tasks.LOCATION_ID;
      
      public final static String LOCATION_NAME = Tasks.LOCATION_NAME;
      
      public final static String PRIORITY = Tasks.PRIORITY;
      
      public final static String TAGS = Tasks.TAGS;
      
      public final static String DUE_DATE = Tasks.DUE_DATE;
      
      public final static String HAS_DUE_TIME = Tasks.HAS_DUE_TIME;
      
      public final static String RECURRENCE = Tasks.RECURRENCE;
      
      public final static String RECURRENCE_EVERY = Tasks.RECURRENCE_EVERY;
      
      public final static String ESTIMATE = Tasks.ESTIMATE;
      
      public final static String ESTIMATE_MILLIS = Tasks.ESTIMATE_MILLIS;
   }
   
   private final static String CREATED_DATE = "created_date";
   
   @InstanceState( key = Config.TASK_NAME )
   private String taskName;
   
   @InstanceState( key = Config.LIST_ID )
   private String listId;
   
   @InstanceState( key = Config.LIST_NAME )
   private String listName;
   
   @InstanceState( key = Config.LOCATION_ID )
   private String locationId;
   
   @InstanceState( key = Config.LOCATION_NAME )
   private String locationName;
   
   @InstanceState( key = Config.PRIORITY, defaultValue = "n" )
   private String priority;
   
   @InstanceState( key = Config.TAGS )
   private String tags;
   
   @InstanceState( key = Config.DUE_DATE, defaultValue = "-1" )
   private long dueDate;
   
   @InstanceState( key = Config.HAS_DUE_TIME, defaultValue = "false" )
   private boolean hasDueTime;
   
   @InstanceState( key = Config.RECURRENCE )
   private String recurrence;
   
   @InstanceState( key = Config.RECURRENCE_EVERY, defaultValue = "false" )
   private boolean recurrenceEvery;
   
   @InstanceState( key = Config.ESTIMATE )
   private String estimate;
   
   @InstanceState( key = Config.ESTIMATE_MILLIS, defaultValue = "-1" )
   private long estimateMillis;
   
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
      checkCreatedDate();
   }
   
   
   
   @Override
   protected Bundle getInitialValues()
   {
      determineListId();
      determineLocationId();
      
      final List< String > tags = getTagsList();
      // Check if a list name is part of the tags. This can happen
      // if a list name has been entered w/o taken the suggestion.
      // So it has been parsed as a tag since the operator (#) is the
      // same.
      {
         final String lastRemovedListId = removeListsFromTags( tags );
         if ( listId == null )
            listId = lastRemovedListId;
      }
      
      final Bundle initialValues = new Bundle();
      
      initialValues.putString( Tasks.TASKSERIES_NAME, taskName );
      initialValues.putString( Tasks.LIST_ID, listId );
      initialValues.putString( Tasks.PRIORITY, priority );
      initialValues.putString( Tasks.TAGS,
                               TextUtils.join( Tasks.TAGS_SEPARATOR,
                                               getTagsList() ) );
      initialValues.putLong( Tasks.DUE_DATE, dueDate );
      initialValues.putBoolean( Tasks.HAS_DUE_TIME, hasDueTime );
      initialValues.putString( Tasks.RECURRENCE, recurrence );
      initialValues.putBoolean( Tasks.RECURRENCE_EVERY, recurrenceEvery );
      initialValues.putString( Tasks.ESTIMATE, estimate );
      initialValues.putLong( Tasks.ESTIMATE_MILLIS, estimateMillis );
      initialValues.putString( Tasks.LOCATION_ID, locationId );
      initialValues.putString( Tasks.URL, null );
      
      return initialValues;
   }
   
   
   
   @Override
   protected void initializeHeadSection()
   {
      addedDate.setText( MolokoDateUtils.formatDateTime( getSherlockActivity(),
                                                         created,
                                                         FULL_DATE_FLAGS ) );
      completedDate.setVisibility( View.GONE );
      postponed.setVisibility( View.GONE );
      
      source.setText( getString( R.string.task_source,
                                 getString( R.string.app_name ) ) );
   }
   
   
   
   @Override
   protected void registerInputListeners()
   {
      super.registerInputListeners();
      
      getContentView().findViewById( R.id.task_edit_tags_btn_change )
                      .setOnClickListener( new OnClickListener()
                      {
                         @Override
                         public void onClick( View v )
                         {
                            listener.onChangeTags( getTags() );
                         }
                      } );
   }
   
   
   
   private void checkCreatedDate()
   {
      if ( created == -1 )
         created = System.currentTimeMillis();
   }
   
   
   
   private void determineListId()
   {
      if ( TextUtils.isEmpty( listId ) )
      {
         if ( !TextUtils.isEmpty( listName ) )
         {
            listId = getIdByName( getLoaderDataAssertNotNull().getListIdsToListNames(),
                                  listName );
         }
      }
   }
   
   
   
   private void determineLocationId()
   {
      if ( TextUtils.isEmpty( locationId ) )
      {
         if ( !TextUtils.isEmpty( locationName ) )
         {
            locationId = getIdByName( getLoaderDataAssertNotNull().getLocationIdsToLocationNames(),
                                      locationName );
         }
      }
   }
   
   
   
   private List< String > getTagsList()
   {
      return new ArrayList< String >( Arrays.asList( TextUtils.split( Strings.emptyIfNull( tags ),
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
         final List< Pair< String, String > > listIdsToName = getLoaderDataAssertNotNull().getListIdsToListNames();
         
         for ( Iterator< String > i = tags.iterator(); i.hasNext(); )
         {
            final String tag = i.next();
            // Check if the tag is a list name
            listId = getIdByName( listIdsToName, tag );
            
            if ( listId != null )
               i.remove();
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
         Pair< String, String > listIdToListName = i.next();
         if ( listIdToListName.second.equalsIgnoreCase( name ) )
            res = listIdToListName.first;
      }
      
      return res;
   }
   
   
   
   @Override
   protected ApplyChangesInfo getChanges()
   {
      final Task newTask = newTask();
      final ApplyChangesInfo modifications = TaskEditUtils.insertTask( getSherlockActivity(),
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
      final NewTaskIds newTaskIds = createNewTaskIds();
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
   
   
   
   private NewTaskIds createNewTaskIds()
   {
      return TasksProviderPart.createNewTaskIds( getSherlockActivity().getContentResolver()
                                                                      .acquireContentProviderClient( Tasks.CONTENT_URI ) );
   }
}
