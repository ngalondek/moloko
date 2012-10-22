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

import java.util.Collections;
import java.util.List;

import android.database.ContentObserver;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import com.mdt.rtm.data.RtmTask;

import dev.drsoran.moloko.ApplyChangesInfo;
import dev.drsoran.moloko.IHandlerToken;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.loaders.TaskLoader;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.Task;


public class TaskEditFragment extends AbstractTaskEditFragment
{
   private final IHandlerToken handler = MolokoApp.acquireHandlerToken();
   
   private final TaskLoaderHandler taskLoaderHandler = new TaskLoaderHandler();
   
   @InstanceState( key = Intents.Extras.KEY_TASK )
   private Task task;
   
   private ContentObserver taskChangesObserver;
   
   
   
   public final static TaskEditFragment newInstance( Bundle config )
   {
      final TaskEditFragment fragment = new TaskEditFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   public TaskEditFragment()
   {
      registerAnnotatedConfiguredInstance( this, TaskEditFragment.class );
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      registerForTaskDeletedByBackgroundSync();
   }
   
   
   
   @Override
   public void onDestroy()
   {
      unregisterForTaskDeletedByBackgroundSync();
      handler.release();
      
      super.onDestroy();
   }
   
   
   
   @Override
   public Bundle determineInitialValues()
   {
      final Task task = getTaskAssertNotNull();
      
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
      final Task task = getTaskAssertNotNull();
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
   
   
   
   public Task getTaskAssertNotNull()
   {
      if ( task == null )
         throw new AssertionError( "expected task to be not null" );
      
      return task;
   }
   
   
   
   @Override
   protected List< Task > getEditedTasks()
   {
      return Collections.singletonList( getTaskAssertNotNull() );
   }
   
   
   
   @Override
   protected ApplyChangesInfo getApplyChangesInfo()
   {
      saveChanges();
      
      final ModificationSet modificationSet = createModificationSet( getEditedTasks() );
      final ApplyChangesInfo applyChangesInfo = new ApplyChangesInfo( modificationSet.toContentProviderActionItemList(),
                                                                      getString( R.string.toast_save_task ),
                                                                      getString( R.string.toast_save_task_ok ),
                                                                      getString( R.string.toast_save_task_failed ) );
      
      return applyChangesInfo;
   }
   
   
   
   private void registerForTaskDeletedByBackgroundSync()
   {
      taskChangesObserver = new ContentObserver( MolokoApp.getHandler() )
      {
         @Override
         public void onChange( boolean selfChange )
         {
            getSherlockActivity().getSupportLoaderManager()
                                 .initLoader( TaskLoader.ID,
                                              Bundle.EMPTY,
                                              TaskEditFragment.this.taskLoaderHandler );
         }
      };
      
      getSherlockActivity().getContentResolver()
                           .registerContentObserver( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                               getTaskAssertNotNull().getId() ),
                                                     false,
                                                     taskChangesObserver );
   }
   
   
   
   private void unregisterForTaskDeletedByBackgroundSync()
   {
      getSherlockActivity().getContentResolver()
                           .unregisterContentObserver( taskChangesObserver );
      taskChangesObserver = null;
   }
   
   
   private class TaskLoaderHandler implements LoaderCallbacks< Task >
   {
      @Override
      public Loader< Task > onCreateLoader( int id, Bundle args )
      {
         return new TaskLoader( getSherlockActivity(),
                                getTaskAssertNotNull().getId() );
      }
      
      
      
      @Override
      public void onLoadFinished( Loader< Task > loader, Task data )
      {
         // If the database changed by a background sync and the assigned note
         // has been deleted, then we ask the user if he wants to keep his changes
         // or drop the note.
         if ( data == null )
         {
            handler.post( new Runnable()
            {
               @Override
               public void run()
               {
                  if ( listener != null )
                  {
                     listener.onBackgroundDeletion( getTaskAssertNotNull() );
                  }
               }
            } );
         }
      }
      
      
      
      @Override
      public void onLoaderReset( Loader< Task > loader )
      {
      }
   }
}
