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
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.mdt.rtm.data.RtmTask;

import dev.drsoran.moloko.IEditableFragment;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.fragments.listeners.ITaskEditFragmentListener;
import dev.drsoran.moloko.fragments.listeners.NullTaskEditFragmentListener;
import dev.drsoran.moloko.util.ApplyModificationsTask;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.provider.Rtm.Tasks;
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
   }
   
   private ITaskEditFragmentListener listener;
   
   private Date created;
   
   

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
      final List< String > tags = new ArrayList< String >( Arrays.asList( TextUtils.split( emptyIfNull( intent.getStringExtra( Tasks.TAGS ) ),
                                                                                           Tasks.TAGS_SEPARATOR ) ) );
      
      // Try to determine the list ID
      final String listId = getConfiguredListId();
      
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
      
      final Bundle initialValues = new Bundle();
      
      initialValues.putString( Tasks.TASKSERIES_NAME, task.getName() );
      initialValues.putString( Tasks.LIST_ID, task.getListId() );
      initialValues.putString( Tasks.PRIORITY,
                               RtmTask.convertPriority( task.getPriority() ) );
      initialValues.putString( Tasks.TAGS,
                               TextUtils.join( Tasks.TAGS_SEPARATOR,
                                               task.getTags() ) );
      initialValues.putLong( Tasks.DUE_DATE,
                             MolokoDateUtils.getTime( task.getDue(),
                                                      Long.valueOf( -1 ) ) );
      initialValues.putBoolean( Tasks.HAS_DUE_TIME, task.hasDueTime() );
      initialValues.putString( Tasks.RECURRENCE, task.getRecurrence() );
      initialValues.putBoolean( Tasks.RECURRENCE_EVERY,
                                task.isEveryRecurrence() );
      initialValues.putString( Tasks.ESTIMATE, task.getEstimate() );
      initialValues.putLong( Tasks.ESTIMATE_MILLIS, task.getEstimateMillis() );
      initialValues.putString( Tasks.LOCATION_ID, task.getLocationId() );
      initialValues.putString( Tasks.URL, task.getUrl() );
      
      return initialValues;
   }
   


   @Override
   protected void initializeHeadSection()
   {
      final Task task = getConfiguredTaskAssertNotNull();
      
      defaultInitializeHeadSectionImpl( task );
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
         final ModificationSet modifications = createModificationSet( Collections.singletonList( getConfiguredTaskAssertNotNull() ) );
         
         if ( modifications != null && modifications.size() > 0 )
         {
            try
            {
               ok = new ApplyModificationsTask( getActivity(),
                                                R.string.toast_save_task ).execute( modifications )
                                                                          .get();
            }
            catch ( InterruptedException e )
            {
               ok = false;
            }
            catch ( ExecutionException e )
            {
               ok = false;
            }
            
            if ( !ok )
               Toast.makeText( getActivity(),
                               R.string.toast_delete_task_failed,
                               Toast.LENGTH_LONG ).show();
         }
      }
      
      return ok;
   }
   


   @Override
   public IEditableFragment< ? extends Fragment > createEditableFragmentInstance()
   {
      final Bundle config = new Bundle();
      
      config.putString( TaskFragment.Config.TASK_ID,
                        getConfiguredTaskAssertNotNull().getId() );
      
      final TaskFragment fragment = TaskFragment.newInstance( config );
      return fragment;
   }
}
