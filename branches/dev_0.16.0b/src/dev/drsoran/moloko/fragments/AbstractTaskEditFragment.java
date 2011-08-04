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

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mdt.rtm.data.RtmLists;
import com.mdt.rtm.data.RtmLocation;
import com.mdt.rtm.data.RtmTask;

import dev.drsoran.moloko.IEditFragment;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.Modification;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.dialogs.AbstractPickerDialog;
import dev.drsoran.moloko.fragments.base.MolokoLoaderFragment;
import dev.drsoran.moloko.layouts.TitleWithEditTextLayout;
import dev.drsoran.moloko.layouts.TitleWithSpinnerLayout;
import dev.drsoran.moloko.layouts.WrappingLayout;
import dev.drsoran.moloko.loaders.TaskEditDatabaseDataLoader;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.Bundles;
import dev.drsoran.moloko.util.MolokoCalendar;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.parsing.RecurrenceParsing;
import dev.drsoran.moloko.util.parsing.RtmDateTimeParsing;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.Tags;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.Task;


public abstract class AbstractTaskEditFragment< T extends Fragment > extends
         MolokoLoaderFragment< AbstractTaskEditFragment.TaskEditDatabaseData >
         implements IEditFragment< T >
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + AbstractTaskEditFragment.class.getSimpleName();
   
   
   public static class Config
   {
      public final static String TASK = "task";
   }
   

   public final static class TaskEditDatabaseData
   {
      public final RtmLists lists;
      
      public final List< RtmLocation > locations;
      
      

      public TaskEditDatabaseData( RtmLists lists, List< RtmLocation > locations )
      {
         this.lists = lists;
         this.locations = locations;
      }
   }
   
   public final int FULL_DATE_FLAGS = MolokoDateUtils.FORMAT_WITH_YEAR;
   
   private final static int TASK_EDIT_LOADER = 1;
   
   protected TextView addedDate;
   
   protected TextView completedDate;
   
   protected TextView source;
   
   protected TextView postponed;
   
   protected EditText nameEdit;
   
   protected TitleWithSpinnerLayout list;
   
   protected TitleWithSpinnerLayout priority;
   
   protected ViewGroup tagsContainer;
   
   protected WrappingLayout tagsLayout;
   
   protected ViewGroup dueContainer;
   
   protected EditText dueEdit;
   
   protected ViewGroup recurrContainer;
   
   protected EditText recurrEdit;
   
   protected ViewGroup estimateContainer;
   
   protected EditText estimateEdit;
   
   protected TitleWithSpinnerLayout location;
   
   protected TitleWithEditTextLayout url;
   
   protected AbstractPickerDialog pickerDlg;
   
   protected Bundle initialValues;
   
   protected Bundle changes;
   
   

   @Override
   public View createFragmentView( LayoutInflater inflater,
                                   ViewGroup container,
                                   Bundle savedInstanceState )
   {
      final View fragmentView = inflater.inflate( R.layout.task_edit_fragment,
                                                  container,
                                                  false );
      
      final View content = fragmentView.findViewById( android.R.id.content );
      addedDate = (TextView) content.findViewById( R.id.task_edit_added_date );
      completedDate = (TextView) content.findViewById( R.id.task_edit_completed_date );
      source = (TextView) content.findViewById( R.id.task_edit_src );
      postponed = (TextView) content.findViewById( R.id.task_edit_postponed );
      
      // Editables
      nameEdit = (EditText) content.findViewById( R.id.task_edit_desc );
      list = (TitleWithSpinnerLayout) content.findViewById( R.id.task_edit_list );
      priority = (TitleWithSpinnerLayout) content.findViewById( R.id.task_edit_priority );
      tagsContainer = (ViewGroup) content.findViewById( R.id.task_edit_tags_layout );
      tagsLayout = (WrappingLayout) content.findViewById( R.id.task_edit_tags_container );
      dueContainer = (ViewGroup) content.findViewById( R.id.task_edit_due_layout );
      dueEdit = (EditText) dueContainer.findViewById( R.id.task_edit_due_text );
      recurrContainer = (ViewGroup) content.findViewById( R.id.task_edit_recurrence_layout );
      recurrEdit = (EditText) recurrContainer.findViewById( R.id.task_edit_recurrence_text );
      estimateContainer = (ViewGroup) content.findViewById( R.id.task_edit_estimate_layout );
      estimateEdit = (EditText) estimateContainer.findViewById( R.id.task_edit_estim_text );
      location = (TitleWithSpinnerLayout) content.findViewById( R.id.task_edit_location );
      url = (TitleWithEditTextLayout) content.findViewById( R.id.task_edit_url );
      
      return fragmentView;
   }
   


   @Override
   public void takeConfigurationFrom( Bundle config )
   {
      super.takeConfigurationFrom( config );
      
      if ( config.containsKey( Config.TASK ) )
         configuration.putParcelable( Config.TASK,
                                      config.getParcelable( Config.TASK ) );
   }
   


   public String getConfiguredTaskId()
   {
      return configuration.getString( Config.TASK );
   }
   


   @Override
   public int getSettingsMask()
   {
      return IOnSettingsChangedListener.DATE_TIME_RELATED;
   }
   


   protected boolean validateInput()
   {
      // Task name
      boolean ok = validateName( nameEdit );
      
      // Due
      ok = ok && validateDue( dueEdit ) != null;
      
      // Recurrence
      ok = ok && validateRecurrence( recurrEdit ) != null;
      
      // Estimate
      ok = ok && validateEstimate( estimateEdit ) != -1;
      
      return ok;
   }
   


   protected boolean validateName( TextView name )
   {
      final boolean ok = !TextUtils.isEmpty( getCurrentValue( TaskSeries.TASKSERIES_NAME,
                                                              String.class ) );
      if ( !ok )
      {
         Toast.makeText( getActivity(),
                         R.string.task_edit_validate_empty_name,
                         Toast.LENGTH_LONG ).show();
         nameEdit.requestFocus();
      }
      
      return ok;
   }
   


   protected MolokoCalendar validateDue( TextView dueEdit )
   {
      MolokoCalendar cal = null;
      
      final String dueStr = dueEdit.getText().toString();
      
      if ( !TextUtils.isEmpty( dueStr ) )
      {
         cal = RtmDateTimeParsing.parseDateTimeSpec( dueStr );
         
         if ( cal == null )
         {
            Toast.makeText( getActivity(),
                            getString( R.string.task_edit_validate_due, dueStr ),
                            Toast.LENGTH_LONG )
                 .show();
            dueEdit.requestFocus();
         }
      }
      
      return cal;
   }
   


   protected Pair< String, Boolean > validateRecurrence( TextView recurrenceEdit )
   {
      Pair< String, Boolean > res = new Pair< String, Boolean >( Strings.EMPTY_STRING,
                                                                 Boolean.FALSE );
      final String recurrStr = recurrEdit.getText().toString();
      
      if ( !TextUtils.isEmpty( recurrStr ) )
      {
         res = RecurrenceParsing.parseRecurrence( recurrStr );
         
         if ( res == null )
         {
            Toast.makeText( getActivity(),
                            getString( R.string.task_edit_validate_recurrence,
                                       recurrStr ),
                            Toast.LENGTH_LONG ).show();
            recurrenceEdit.requestFocus();
         }
      }
      
      return res;
   }
   


   protected long validateEstimate( TextView estimateEdit )
   {
      long millis = 0;
      
      String estStr = estimateEdit.getText().toString();
      
      if ( !TextUtils.isEmpty( estStr ) )
      {
         millis = RtmDateTimeParsing.parseEstimated( estStr );
         
         if ( millis == -1 )
         {
            Toast.makeText( getActivity(),
                            getString( R.string.task_edit_validate_estimate,
                                       estStr ),
                            Toast.LENGTH_LONG ).show();
            estimateEdit.requestFocus();
         }
      }
      
      return millis;
   }
   


   protected final < V > V getCurrentValue( String key, Class< V > type )
   {
      V res = getChange( key, type );
      
      if ( res == null )
      {
         final Object o = initialValues.get( key );
         
         if ( o == null || o.getClass() == type )
            res = type.cast( o );
         else
            throw new IllegalArgumentException( "Expected type " + o.getClass()
               + " for " + key );
      }
      
      return res;
   }
   


   protected final boolean hasChange( String key )
   {
      if ( changes == null )
         return false;
      
      return changes.containsKey( key );
   }
   


   protected final < V > V getChange( String key, Class< V > type )
   {
      if ( changes == null )
         return null;
      
      final Object o = changes.get( key );
      
      if ( o == null )
         return null;
      
      if ( o.getClass() == type )
         return type.cast( o );
      else
         throw new IllegalArgumentException( "Expected type " + o.getClass()
            + " for " + key );
   }
   


   private final < V > void putChange( String key, V value, Class< V > type )
   {
      // Check if it has reverted to the initial value
      if ( SyncUtils.hasChanged( value, initialValues.get( key ) ) )
      {
         if ( changes == null )
            changes = new Bundle();
         
         Bundles.put( changes, key, value, type );
      }
      else
      {
         if ( changes != null )
            changes.remove( key );
      }
   }
   


   protected ModificationSet createModificationSet( List< Task > tasks )
   {
      final ModificationSet modifications = new ModificationSet();
      
      for ( Task task : tasks )
      {
         boolean anyChanges = false;
         
         // Task name
         if ( hasChange( Tasks.TASKSERIES_NAME ) )
         {
            final String taskName = getCurrentValue( Tasks.TASKSERIES_NAME,
                                                     String.class );
            
            if ( SyncUtils.hasChanged( task.getName(), taskName ) )
            {
               modifications.add( Modification.newModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.TASKSERIES_NAME,
                                                                taskName ) );
               anyChanges = true;
            }
         }
         
         // List
         if ( hasChange( Tasks.LIST_ID ) )
         {
            final String selectedListId = getCurrentValue( Tasks.LIST_ID,
                                                           String.class );
            
            if ( SyncUtils.hasChanged( task.getListId(), selectedListId ) )
            {
               modifications.add( Modification.newModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.LIST_ID,
                                                                selectedListId ) );
               anyChanges = true;
            }
         }
         
         // Priority
         if ( hasChange( Tasks.PRIORITY ) )
         {
            final String selectedPriority = getCurrentValue( Tasks.PRIORITY,
                                                             String.class );
            
            if ( SyncUtils.hasChanged( RtmTask.convertPriority( task.getPriority() ),
                                       selectedPriority ) )
            {
               modifications.add( Modification.newModification( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                          task.getId() ),
                                                                RawTasks.PRIORITY,
                                                                selectedPriority ) );
               anyChanges = true;
            }
         }
         
         // Tags
         if ( hasChange( Tasks.TAGS ) )
         {
            final String tags = getCurrentValue( Tasks.TAGS, String.class );
            
            if ( SyncUtils.hasChanged( tags,
                                       TextUtils.join( Tags.TAGS_SEPARATOR,
                                                       task.getTags() ) ) )
            {
               modifications.add( Modification.newModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.TAGS,
                                                                tags ) );
               anyChanges = true;
            }
         }
         
         // Due
         if ( hasChange( Tasks.DUE_DATE ) )
         {
            Long newDue = getCurrentValue( Tasks.DUE_DATE, Long.class );
            
            if ( newDue == -1 )
               newDue = null;
            
            if ( SyncUtils.hasChanged( MolokoDateUtils.getTime( task.getDue() ),
                                       newDue ) )
            {
               modifications.add( Modification.newModification( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                          task.getId() ),
                                                                RawTasks.DUE_DATE,
                                                                newDue ) );
               anyChanges = true;
            }
         }
         
         if ( hasChange( Tasks.HAS_DUE_TIME ) )
         {
            final boolean newHasDueTime = getCurrentValue( Tasks.HAS_DUE_TIME,
                                                           Boolean.class );
            
            if ( SyncUtils.hasChanged( task.hasDueTime(), newHasDueTime ) )
            {
               modifications.add( Modification.newModification( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                          task.getId() ),
                                                                RawTasks.HAS_DUE_TIME,
                                                                newHasDueTime
                                                                             ? 1
                                                                             : 0 ) );
               anyChanges = true;
            }
         }
         
         // Recurrence
         if ( hasChange( Tasks.RECURRENCE ) )
         {
            final String recurrence = getCurrentValue( Tasks.RECURRENCE,
                                                       String.class );
            
            if ( SyncUtils.hasChanged( task.getRecurrence(), recurrence ) )
            {
               modifications.add( Modification.newModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.RECURRENCE,
                                                                recurrence ) );
               
               final boolean isEveryRecurrence = getCurrentValue( Tasks.RECURRENCE_EVERY,
                                                                  Boolean.class );
               
               if ( SyncUtils.hasChanged( task.isEveryRecurrence(),
                                          isEveryRecurrence ) )
               {
                  modifications.add( Modification.newModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                             task.getTaskSeriesId() ),
                                                                   TaskSeries.RECURRENCE_EVERY,
                                                                   isEveryRecurrence ) );
               }
               
               anyChanges = true;
            }
         }
         
         // Estimate
         if ( hasChange( Tasks.ESTIMATE_MILLIS ) )
         {
            final long estimateMillis = getCurrentValue( Tasks.ESTIMATE_MILLIS,
                                                         Long.class );
            
            if ( SyncUtils.hasChanged( task.getEstimateMillis(), estimateMillis ) )
            {
               modifications.add( Modification.newModification( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                          task.getId() ),
                                                                RawTasks.ESTIMATE,
                                                                getCurrentValue( RawTasks.ESTIMATE,
                                                                                 String.class ) ) );
               
               modifications.add( Modification.newModification( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                          task.getId() ),
                                                                RawTasks.ESTIMATE_MILLIS,
                                                                estimateMillis ) );
               anyChanges = true;
            }
         }
         
         // Location
         if ( hasChange( Tasks.LOCATION_ID ) )
         {
            final String selectedLocation = getCurrentValue( Tasks.LOCATION_ID,
                                                             String.class );
            
            if ( SyncUtils.hasChanged( task.getLocationId(), selectedLocation ) )
            {
               modifications.add( Modification.newModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.LOCATION_ID,
                                                                selectedLocation ) );
               anyChanges = true;
            }
         }
         
         // URL
         if ( hasChange( Tasks.URL ) )
         {
            final String newUrl = Strings.nullIfEmpty( getCurrentValue( Tasks.URL,
                                                                        String.class ) );
            
            if ( SyncUtils.hasChanged( task.getUrl(), newUrl ) )
            {
               modifications.add( Modification.newModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.URL,
                                                                newUrl ) );
               anyChanges = true;
            }
         }
         
         // set the taskseries modification time to now
         if ( anyChanges )
            modifications.add( Modification.newTaskModified( task.getTaskSeriesId() ) );
      }
      
      return modifications;
   }
   


   private boolean hasInputCommitted( int actionId )
   {
      return actionId == EditorInfo.IME_ACTION_DONE
         | actionId == EditorInfo.IME_ACTION_NEXT
         | actionId == EditorInfo.IME_NULL;
   }
   


   @Override
   protected String getLoaderDataName()
   {
      return TaskEditDatabaseData.class.getSimpleName();
   }
   


   @Override
   protected int getLoaderId()
   {
      return TASK_EDIT_LOADER;
   }
   


   @Override
   protected Loader< TaskEditDatabaseData > newLoaderInstance( int id,
                                                               Bundle args )
   {
      return new TaskEditDatabaseDataLoader( getActivity() );
   }
   


   abstract protected Bundle getInitialValues();
}
