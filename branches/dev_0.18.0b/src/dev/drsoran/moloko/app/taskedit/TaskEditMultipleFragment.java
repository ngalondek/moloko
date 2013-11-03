/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.content.Columns.TaskColumns;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.moloko.ui.ValidationResult;
import dev.drsoran.moloko.util.Strings;


class TaskEditMultipleFragment extends AbstractTaskEditFragment
{
   private final static String STRING_MULTI_VALUE = Strings.EMPTY_STRING;
   
   private final static String URL_MULTI_VALUE = null;
   
   private final static String TAGS_MULTI_VALUE = "multi_tag";
   
   private final static long LONG_MULTI_VALUE = Long.valueOf( -1L );
   
   /**
    * Map< Task attribute, Map< attribute value, number of tasks with attribute > >
    * 
    * e.g. Map< TaskColumns.TASKSERIES_NAME, Map< "Task Name", 2 > >
    */
   private final Map< String, Map< Object, Integer > > attributeCount = new HashMap< String, Map< Object, Integer > >();
   
   @InstanceState( key = Intents.Extras.KEY_TASKS )
   private final ArrayList< Task > initialTasks = new ArrayList< Task >();
   
   @InstanceState( key = "edited_tasks", defaultValue = InstanceState.NULL )
   private final ArrayList< Task > editedTasks = new ArrayList< Task >();
   
   
   
   public final static TaskEditMultipleFragment newInstance( Bundle config )
   {
      final TaskEditMultipleFragment fragment = new TaskEditMultipleFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   public TaskEditMultipleFragment()
   {
      registerAnnotatedConfiguredInstance( this, TaskEditMultipleFragment.class );
   }
   
   
   
   @Override
   public Bundle determineInitialValues()
   {
      final List< Task > tasks = getInitialTasksAssertNotNull();
      
      joinAttributes( tasks );
      
      final Bundle initialValues = new Bundle();
      
      initialValues.putString( TaskColumns.TASKSERIES_NAME,
                               getInitialValue( TaskColumns.TASKSERIES_NAME,
                                                STRING_MULTI_VALUE,
                                                String.class ) );
      initialValues.putString( TaskColumns.LIST_ID,
                               getInitialValue( TaskColumns.LIST_ID,
                                                STRING_MULTI_VALUE,
                                                String.class ) );
      initialValues.putString( TaskColumns.PRIORITY,
                               getInitialValue( TaskColumns.PRIORITY,
                                                STRING_MULTI_VALUE,
                                                String.class ) );
      initialValues.putString( TaskColumns.TAGS,
                               getInitialValue( TaskColumns.TAGS,
                                                TAGS_MULTI_VALUE,
                                                String.class ) );
      initialValues.putLong( TaskColumns.DUE_DATE,
                             getInitialValue( TaskColumns.DUE_DATE,
                                              LONG_MULTI_VALUE,
                                              Long.class ) );
      initialValues.putBoolean( TaskColumns.HAS_DUE_TIME,
                                getInitialValue( TaskColumns.HAS_DUE_TIME,
                                                 Boolean.FALSE,
                                                 Boolean.class ) );
      initialValues.putString( TaskColumns.RECURRENCE,
                               getInitialValue( TaskColumns.RECURRENCE,
                                                STRING_MULTI_VALUE,
                                                String.class ) );
      initialValues.putBoolean( TaskColumns.RECURRENCE_EVERY,
                                getInitialValue( TaskColumns.RECURRENCE_EVERY,
                                                 Boolean.FALSE,
                                                 Boolean.class ) );
      initialValues.putString( TaskColumns.ESTIMATE,
                               getInitialValue( TaskColumns.ESTIMATE,
                                                STRING_MULTI_VALUE,
                                                String.class ) );
      initialValues.putLong( TaskColumns.ESTIMATE_MILLIS,
                             getInitialValue( TaskColumns.ESTIMATE_MILLIS,
                                              LONG_MULTI_VALUE,
                                              Long.class ) );
      initialValues.putString( TaskColumns.LOCATION_ID,
                               getInitialValue( TaskColumns.LOCATION_ID,
                                                STRING_MULTI_VALUE,
                                                String.class ) );
      initialValues.putString( TaskColumns.URL,
                               getInitialValue( TaskColumns.URL,
                                                URL_MULTI_VALUE,
                                                String.class ) );
      return initialValues;
   }
   
   
   
   private void joinAttributes( List< Task > tasks )
   {
      attributeCount.put( TaskColumns.TASK_NAME,
                          new HashMap< Object, Integer >() );
      attributeCount.put( TaskColumns.LIST_ID, new HashMap< Object, Integer >() );
      attributeCount.put( TaskColumns.PRIORITY,
                          new HashMap< Object, Integer >() );
      attributeCount.put( TaskColumns.LOCATION_ID,
                          new HashMap< Object, Integer >() );
      attributeCount.put( TaskColumns.URL, new HashMap< Object, Integer >() );
      
      for ( Task task : tasks )
      {
         inc( attributeCount.get( TaskColumns.TASK_NAME ), task.getName() );
         inc( attributeCount.get( TaskColumns.LIST_ID ), task.getListId() );
         inc( attributeCount.get( TaskColumns.PRIORITY ), task.getPriority()
                                                              .toString() );
         inc( attributeCount.get( TaskColumns.LOCATION_ID ),
              task.getLocationId() );
         inc( attributeCount.get( TaskColumns.URL ), task.getUrl() );
      }
   }
   
   
   
   @Override
   public void initContentAfterDataLoaded( ViewGroup content )
   {
      super.initContentAfterDataLoaded( content );
      
      // Setup tasks name edit
      if ( !isCommonAttrib( TaskColumns.TASKSERIES_NAME ) )
      {
         nameEditText.setHint( R.string.edit_multiple_tasks_different_task_names );
         
         if ( nameEditText instanceof AutoCompleteTextView )
         {
            final List< Task > tasks = getInitialTasksAssertNotNull();
            final Set< String > names = new HashSet< String >( tasks.size() );
            
            for ( Task task : tasks )
               names.add( task.getDisplay() );
            
            final List< String > uniqueTaskNames = new ArrayList< String >( names );
            
            ( (AutoCompleteTextView) nameEditText ).setAdapter( new ArrayAdapter< String >( getSherlockActivity(),
                                                                                            android.R.layout.simple_dropdown_item_1line,
                                                                                            android.R.id.text1,
                                                                                            uniqueTaskNames ) );
         }
      }
      
      // Setup URL edit
      if ( !isCommonAttrib( TaskColumns.URL ) )
      {
         urlEditText.setHint( R.string.edit_multiple_tasks_different_urls );
      }
      
      // These controls are not visible in multi edit task mode
      tagsContainer.setVisibility( View.GONE );
      dueContainer.setVisibility( View.GONE );
      estimateContainer.setVisibility( View.GONE );
      recurrContainer.setVisibility( View.GONE );
   }
   
   
   
   @Override
   protected void initializeHeadSection()
   {
      addedDate.setVisibility( View.GONE );
      completedDate.setVisibility( View.GONE );
      postponed.setVisibility( View.GONE );
      source.setVisibility( View.GONE );
   }
   
   
   
   @Override
   protected void initializeListSpinner()
   {
      if ( isCommonAttrib( TaskColumns.LIST_ID ) )
      {
         super.initializeListSpinner();
      }
      else
      {
         final TaskEditData loaderData = getLoaderData();
         
         if ( loaderData != null )
         {
            final List< String > listIds = loaderData.getListIds();
            final List< String > listNames = loaderData.getListNames();
            
            appendQuantifierToEntries( TaskColumns.LIST_ID, listNames, listIds );
            
            listIds.add( 0, STRING_MULTI_VALUE );
            listNames.add( 0,
                           getString( R.string.edit_multiple_tasks_different_lists ) );
            
            createListSpinnerAdapterForValues( listIds, listNames );
         }
      }
   }
   
   
   
   @Override
   protected void initializeLocationSpinner()
   {
      if ( isCommonAttrib( TaskColumns.LOCATION_ID ) )
      {
         super.initializeLocationSpinner();
      }
      else
      {
         final TaskEditData loaderData = getLoaderData();
         
         if ( loaderData != null )
         {
            final List< String > locationIds = loaderData.getLocationIds();
            final List< String > locationNames = loaderData.getLocationNames();
            
            insertNowhereLocationEntry( locationIds, locationNames );
            
            appendQuantifierToEntries( TaskColumns.LOCATION_ID,
                                       locationNames,
                                       locationIds );
            
            locationIds.add( 0, STRING_MULTI_VALUE );
            locationNames.add( 0,
                               getString( R.string.edit_multiple_tasks_different_locations ) );
            
            createLocationSpinnerAdapterForValues( locationIds, locationNames );
         }
      }
   }
   
   
   
   @Override
   protected void initializePrioritySpinner()
   {
      if ( isCommonAttrib( TaskColumns.PRIORITY ) )
      {
         super.initializePrioritySpinner();
      }
      else
      {
         final List< String > priorityTexts = new ArrayList< String >( Arrays.asList( getResources().getStringArray( R.array.rtm_priorities ) ) );
         final List< String > priorityValues = new ArrayList< String >( Arrays.asList( getResources().getStringArray( R.array.rtm_priority_values ) ) );
         
         appendQuantifierToEntries( TaskColumns.PRIORITY,
                                    priorityTexts,
                                    priorityValues );
         
         priorityTexts.add( 0,
                            getString( R.string.edit_multiple_tasks_different_priorities ) );
         priorityValues.add( 0, STRING_MULTI_VALUE );
         
         createPrioritySpinnerAdapterForValues( priorityTexts, priorityValues );
      }
   }
   
   
   
   @Override
   protected ValidationResult validateName()
   {
      if ( hasChange( TaskColumns.TASKSERIES_NAME ) )
         return super.validateName();
      else
         return ValidationResult.OK;
   }
   
   
   
   public List< Task > getInitialTasksAssertNotNull()
   {
      if ( initialTasks == null )
         throw new AssertionError( "expected initial tasks to be not null" );
      
      return initialTasks;
   }
   
   
   
   public List< Task > getEditedTasksAssertNotNull()
   {
      if ( editedTasks == null )
         throw new AssertionError( "expected edited tasks to be not null" );
      
      return editedTasks;
   }
   
   
   
   private final static void inc( Map< Object, Integer > map, Object value )
   {
      final Integer cnt = map.get( value );
      
      if ( cnt != null )
         map.put( value, cnt + 1 );
      else
         map.put( value, Integer.valueOf( 1 ) );
   }
   
   
   
   private final < V > V getInitialValue( String key,
                                          V multiVal,
                                          Class< V > type )
   {
      final Map< Object, Integer > values = attributeCount.get( key );
      
      if ( values.size() == 1 )
         return type.cast( values.keySet().iterator().next() );
      else
         return multiVal;
   }
   
   
   
   private final void appendQuantifierToEntries( String attributeKey,
                                                 List< String > entries,
                                                 List< String > values )
   {
      if ( entries.size() != values.size() )
         throw new AssertionError( "expected entries and values have the same size" );
      
      for ( int i = 0, cnt = entries.size(); i < cnt; i++ )
      {
         entries.set( i,
                      getEntryWithCountString( attributeKey,
                                               values.get( i ),
                                               entries.get( i ) ) );
      }
   }
   
   
   
   private final String getEntryWithCountString( String key,
                                                 Object value,
                                                 String entry )
   {
      return getResources().getString( R.string.edit_multiple_tasks_entry_with_count,
                                       entry,
                                       getAttribValueCnt( key, value ) );
   }
   
   
   
   private final int getAttribValueCnt( String key, Object value )
   {
      final Integer cnt = attributeCount.get( key ).get( value );
      return cnt == null ? 0 : cnt.intValue();
   }
   
   
   
   private final boolean isCommonAttrib( String key )
   {
      return attributeCount.get( key ).size() == 1;
   }
}
