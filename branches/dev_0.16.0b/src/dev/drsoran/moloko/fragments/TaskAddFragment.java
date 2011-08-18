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

package dev.drsoran.moloko.fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.mdt.rtm.data.RtmTask;

import dev.drsoran.moloko.IEditableFragment;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.CreationsProviderPart;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.content.TasksProviderPart.NewTaskIds;
import dev.drsoran.moloko.fragments.listeners.ITaskEditFragmentListener;
import dev.drsoran.moloko.fragments.listeners.NullTaskEditFragmentListener;
import dev.drsoran.moloko.util.AsyncInsertEntity;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.ParcelableDate;
import dev.drsoran.rtm.ParticipantList;
import dev.drsoran.rtm.Task;


public class TaskAddFragment extends AbstractTaskEditFragment< TaskAddFragment >
{
   private final static IntentFilter INTENT_FILTER;
   
   static
   {
      try
      {
         INTENT_FILTER = new IntentFilter( Intent.ACTION_INSERT,
                                           "vnd.android.cursor.item/vnd.rtm.task" );
         INTENT_FILTER.addCategory( Intent.CATEGORY_DEFAULT );
      }
      catch ( MalformedMimeTypeException e )
      {
         throw new RuntimeException( e );
      }
   }
   
   
   public final static class Config
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
      
      private final static String NEW_TASK_URI = "new_task_uri";
      
      private final static String CREATED_DATE = "created_date";
   }
   
   private ITaskEditFragmentListener listener;
   
   

   public final static TaskAddFragment newInstance( Bundle config )
   {
      final TaskAddFragment fragment = new TaskAddFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   


   public static IntentFilter getIntentFilter()
   {
      return INTENT_FILTER;
   }
   


   @Override
   public void onAttach( FragmentActivity activity )
   {
      super.onAttach( activity );
      
      if ( activity instanceof ITaskEditFragmentListener )
         listener = (ITaskEditFragmentListener) activity;
      else
         listener = new NullTaskEditFragmentListener();
   }
   


   @Override
   public void onDetach()
   {
      super.onDetach();
      
      listener = null;
   }
   


   @Override
   protected Bundle getInitialValues()
   {
      final List< String > tags = getConfiguredTags();
      final String locationId = getConfiguredLocationId();
      
      String listId = getConfiguredListId();
      
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
      
      initialValues.putString( Tasks.TASKSERIES_NAME,
                               configuration.getString( Config.TASK_NAME ) );
      initialValues.putString( Tasks.LIST_ID, listId );
      initialValues.putString( Tasks.PRIORITY,
                               configuration.getString( Config.PRIORITY ) );
      initialValues.putString( Tasks.TAGS,
                               TextUtils.join( Tasks.TAGS_SEPARATOR, tags ) );
      initialValues.putLong( Tasks.DUE_DATE,
                             configuration.getLong( Config.DUE_DATE,
                                                    Long.valueOf( -1 ) ) );
      initialValues.putBoolean( Tasks.HAS_DUE_TIME,
                                configuration.getBoolean( Config.HAS_DUE_TIME ) );
      initialValues.putString( Tasks.RECURRENCE,
                               configuration.getString( Config.RECURRENCE ) );
      initialValues.putBoolean( Tasks.RECURRENCE_EVERY,
                                configuration.getBoolean( Config.RECURRENCE_EVERY ) );
      initialValues.putString( Tasks.ESTIMATE,
                               configuration.getString( Config.ESTIMATE ) );
      initialValues.putLong( Tasks.ESTIMATE_MILLIS,
                             configuration.getLong( Config.ESTIMATE_MILLIS,
                                                    Long.valueOf( -1 ) ) );
      initialValues.putString( Tasks.LOCATION_ID, locationId );
      initialValues.putString( Tasks.URL, null );
      
      return initialValues;
   }
   


   @Override
   protected void initializeHeadSection()
   {
      final ParcelableDate created = getConfiguredCreatedDateAssertNotNull();
      
      addedDate.setText( MolokoDateUtils.formatDateTime( created.getTime(),
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
   


   @Override
   public void takeConfigurationFrom( Bundle config )
   {
      super.takeConfigurationFrom( config );
      
      if ( config.containsKey( Config.TASK_NAME ) )
         configuration.putString( Config.TASK_NAME,
                                  config.getString( Config.TASK_NAME ) );
      if ( config.containsKey( Config.LIST_ID ) )
         configuration.putString( Config.LIST_ID,
                                  config.getString( Config.LIST_ID ) );
      if ( config.containsKey( Config.LIST_NAME ) )
         configuration.putString( Config.LIST_NAME,
                                  config.getString( Config.LIST_NAME ) );
      if ( config.containsKey( Config.LOCATION_ID ) )
         configuration.putString( Config.LOCATION_ID,
                                  config.getString( Config.LOCATION_ID ) );
      if ( config.containsKey( Config.LOCATION_NAME ) )
         configuration.putString( Config.LOCATION_NAME,
                                  config.getString( Config.LOCATION_NAME ) );
      if ( config.containsKey( Config.PRIORITY ) )
         configuration.putString( Config.PRIORITY,
                                  config.getString( Config.PRIORITY ) );
      if ( config.containsKey( Config.TAGS ) )
         configuration.putString( Config.TAGS, config.getString( Config.TAGS ) );
      if ( config.containsKey( Config.DUE_DATE ) )
         configuration.putLong( Config.DUE_DATE,
                                config.getLong( Config.DUE_DATE ) );
      if ( config.containsKey( Config.HAS_DUE_TIME ) )
         configuration.putBoolean( Config.HAS_DUE_TIME,
                                   config.getBoolean( Config.HAS_DUE_TIME ) );
      if ( config.containsKey( Config.RECURRENCE ) )
         configuration.putString( Config.RECURRENCE,
                                  config.getString( Config.RECURRENCE ) );
      if ( config.containsKey( Config.RECURRENCE_EVERY ) )
         configuration.putBoolean( Config.RECURRENCE_EVERY,
                                   config.getBoolean( Config.RECURRENCE_EVERY ) );
      if ( config.containsKey( Config.ESTIMATE ) )
         configuration.putString( Config.ESTIMATE,
                                  config.getString( Config.ESTIMATE ) );
      if ( config.containsKey( Config.ESTIMATE_MILLIS ) )
         configuration.putLong( Config.ESTIMATE_MILLIS,
                                config.getLong( Config.ESTIMATE_MILLIS ) );
      if ( config.containsKey( Config.CREATED_DATE ) )
         configuration.putParcelable( Config.CREATED_DATE,
                                      config.getParcelable( Config.CREATED_DATE ) );
      if ( config.containsKey( Config.NEW_TASK_URI ) )
         configuration.putString( Config.NEW_TASK_URI,
                                  config.getString( Config.NEW_TASK_URI ) );
   }
   


   @Override
   protected void putDefaultConfigurationTo( Bundle bundle )
   {
      super.putDefaultConfigurationTo( bundle );
      
      bundle.putParcelable( Config.CREATED_DATE,
                            ParcelableDate.newInstanceIfNotNull( new Date() ) );
   }
   


   private ParcelableDate getConfiguredCreatedDateAssertNotNull()
   {
      final ParcelableDate date = configuration.getParcelable( Config.CREATED_DATE );
      
      if ( date != null )
         throw new AssertionError( "expected date to be not null" );
      
      return date;
   }
   


   private Uri getConfiguredNewTaskUri()
   {
      return configuration.getParcelable( Config.NEW_TASK_URI );
   }
   


   private void configuredNewTaskUri( Uri newTaskUri )
   {
      configuration.putParcelable( Config.NEW_TASK_URI, newTaskUri );
   }
   


   private String getConfiguredListId()
   {
      if ( configuration.containsKey( Tasks.LIST_ID ) )
         return configuration.getString( Tasks.LIST_ID );
      else if ( configuration.containsKey( Tasks.LIST_NAME ) )
         return getIdByName( getLoaderDataAssertNotNull().getListIdsToListNames(),
                             configuration.getString( Tasks.LIST_NAME ) );
      else
         return null;
   }
   


   private String getConfiguredLocationId()
   {
      if ( configuration.containsKey( Tasks.LOCATION_ID ) )
         return configuration.getString( Tasks.LOCATION_ID );
      else if ( configuration.containsKey( Tasks.LOCATION_NAME ) )
         return getIdByName( getLoaderDataAssertNotNull().getLocationIdsToLocationNames(),
                             configuration.getString( Tasks.LOCATION_NAME ) );
      else
         return null;
   }
   


   private List< String > getConfiguredTags()
   {
      return new ArrayList< String >( Arrays.asList( TextUtils.split( Strings.emptyIfNull( configuration.getString( Tasks.TAGS ) ),
                                                                      Tasks.TAGS_SEPARATOR ) ) );
   }
   


   /**
    * @return the list ID of the last removed list name
    */
   private String removeListsFromTags( List< String > tags )
   {
      String listId = null;
      
      final List< Pair< String, String > > listIdsToName = getLoaderDataAssertNotNull().getListIdsToListNames();
      
      for ( Iterator< String > i = tags.iterator(); i.hasNext(); )
      {
         final String tag = i.next();
         // Check if the tag is a list name
         listId = getIdByName( listIdsToName, tag );
         
         if ( listId != null )
            i.remove();
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
   public boolean saveChanges()
   {
      boolean ok = super.saveChanges();
      
      if ( ok )
      {
         // Apply the modifications to the DB.
         // set the taskseries modification time to now
         Uri newTaskUri = null;
         
         try
         {
            final Task newTask = newTask();
            newTaskUri = new AsyncInsertEntity< Task >( getActivity() )
            {
               @Override
               protected int getProgressMessageId()
               {
                  return R.string.toast_insert_task;
               }
               


               @Override
               protected List< ContentProviderOperation > getInsertOperations( ContentResolver contentResolver,
                                                                               Task entity )
               {
                  final NewTaskIds newIds = new NewTaskIds( null, null );
                  final List< ContentProviderOperation > operations = TasksProviderPart.insertLocalCreatedTask( contentResolver,
                                                                                                                entity,
                                                                                                                newIds );
                  
                  operations.add( CreationsProviderPart.newCreation( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                               newIds.taskSeriesId ),
                                                                     entity.getCreated()
                                                                           .getTime() ) );
                  operations.add( CreationsProviderPart.newCreation( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                               newIds.rawTaskId ),
                                                                     entity.getCreated()
                                                                           .getTime() ) );
                  return operations;
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
            Log.e( LogUtils.toTag( TaskAddFragment.class ),
                   "Failed to insert new task",
                   e );
            ok = false;
         }
         catch ( ExecutionException e )
         {
            Log.e( LogUtils.toTag( TaskAddFragment.class ),
                   "Failed to insert new task",
                   e );
            ok = false;
         }
         
         if ( ok )
            configuredNewTaskUri( newTaskUri );
         else
            Toast.makeText( getActivity(),
                            R.string.toast_insert_task_fail,
                            Toast.LENGTH_LONG ).show();
      }
      
      return ok;
   }
   


   private final Task newTask()
   {
      final Date created = getConfiguredCreatedDateAssertNotNull().getDate();
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
                       Strings.EMPTY_STRING );
   }
   


   @Override
   public IEditableFragment< ? extends Fragment > createEditableFragmentInstance()
   {
      final Uri newTaskUri = getConfiguredNewTaskUri();
      
      if ( newTaskUri != null )
      {
         final Bundle config = new Bundle();
         
         config.putString( TaskFragment.Config.TASK_ID,
                           newTaskUri.getLastPathSegment() );
         
         final TaskFragment fragment = TaskFragment.newInstance( config );
         return fragment;
      }
      else
      {
         return null;
      }
   }
}
