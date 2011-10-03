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

import java.util.Collections;
import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
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


public class TaskEditFragment extends
         AbstractTaskEditFragment< TaskEditFragment >
{
   private final static IntentFilter INTENT_FILTER;
   
   static
   {
      try
      {
         INTENT_FILTER = new IntentFilter( Intent.ACTION_EDIT,
                                           "vnd.android.cursor.item/vnd.rtm.task" );
         INTENT_FILTER.addCategory( Intent.CATEGORY_DEFAULT );
      }
      catch ( MalformedMimeTypeException e )
      {
         throw new RuntimeException( e );
      }
   }
   
   
   public static class Config
   {
      public final static String TASK = "task";
   }
   
   private ITaskEditFragmentListener listener;
   
   
   
   public final static TaskEditFragment newInstance( Bundle config )
   {
      final TaskEditFragment fragment = new TaskEditFragment();
      
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
      final Task task = getConfiguredTaskAssertNotNull();
      
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
      
      if ( config.containsKey( Config.TASK ) )
         configuration.putParcelable( Config.TASK,
                                      config.getParcelable( Config.TASK ) );
   }
   
   
   
   public Task getConfiguredTaskAssertNotNull()
   {
      final Task task = configuration.getParcelable( Config.TASK );
      
      if ( task == null )
         throw new AssertionError( "expected task to be not null" );
      
      return task;
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
               ok = new ApplyModificationsTask( getFragmentActivity(),
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
               Toast.makeText( getFragmentActivity(),
                               R.string.toast_save_task_failed,
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
