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

import static dev.drsoran.moloko.content.Columns.TaskColumns.LIST_ID;
import static dev.drsoran.moloko.content.Columns.TaskColumns.LIST_NAME;
import static dev.drsoran.moloko.content.Columns.TaskColumns.LOCATION_ID;
import static dev.drsoran.moloko.content.Columns.TaskColumns.LOCATION_NAME;
import static dev.drsoran.moloko.content.Columns.TaskColumns.PRIORITY;
import static dev.drsoran.moloko.content.Columns.TaskColumns.TASK_NAME;
import static dev.drsoran.moloko.content.Columns.TaskColumns.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import android.app.Activity;
import android.content.Loader;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.domain.DomainContext;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.moloko.ui.AfterTextChangedWatcher;
import dev.drsoran.moloko.ui.ValidationResult;
import dev.drsoran.moloko.ui.fragments.MolokoLoaderEditFragment;
import dev.drsoran.moloko.ui.layouts.TitleWithEditTextLayout;
import dev.drsoran.moloko.ui.layouts.TitleWithSpinnerLayout;
import dev.drsoran.rtm.Iterables;
import dev.drsoran.rtm.Strings;
import dev.drsoran.rtm.model.Priority;


public class TaskEditMultipleFragment extends
         MolokoLoaderEditFragment< TaskEditData >
{
   /**
    * Map< Task attribute, Map< attribute value, number of tasks with attribute > >
    * 
    * e.g. Map< TaskColumns.TASK_NAME, Map< "Task Name", 2 > >
    */
   private final Map< String, Map< Object, Integer > > attributeCount = new HashMap< String, Map< Object, Integer > >();
   
   @InstanceState( key = Intents.Extras.KEY_TASKS )
   private final ArrayList< Task > tasks = new ArrayList< Task >();
   
   @InstanceState( key = "changes", defaultValue = InstanceState.NEW )
   private ValuesContainer changes;
   
   @InstanceState( key = "isFirstInit", defaultValue = "true" )
   private boolean isFirstInitialization;
   
   private EditText nameEditText;
   
   private TitleWithSpinnerLayout listsSpinner;
   
   private TitleWithSpinnerLayout prioritySpinner;
   
   private TitleWithSpinnerLayout locationSpinner;
   
   private TitleWithEditTextLayout urlEditText;
   
   private ITaskEditFragmentListener listener;
   
   
   
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
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      if ( activity instanceof ITaskEditFragmentListener )
         listener = (ITaskEditFragmentListener) activity;
      else
         listener = null;
   }
   
   
   
   @Override
   public void onStart()
   {
      super.onStart();
      joinInitialTaskAttributes();
   }
   
   
   
   private < T > void putChange( String key, T value, Class< T > type )
   {
      changes.putValue( key, value, type );
   }
   
   
   
   private < T > T getChange( String key, Class< T > type ) throws NoSuchElementException
   {
      return changes.getValue( key, type );
   }
   
   
   
   private < T > T tryGetChange( String key, Class< T > type, T defaultValue )
   {
      if ( changes.hasValue( key ) )
      {
         return changes.getValue( key, type );
      }
      
      return defaultValue;
   }
   
   
   
   private boolean hasChange( String key )
   {
      return changes.hasValue( key );
   }
   
   
   
   private void removeChange( String key )
   {
      changes.removeValue( key );
   }
   
   
   
   private void joinInitialTaskAttributes()
   {
      final List< Task > tasks = getInitialTasksAssertNotNull();
      
      attributeCount.put( TASK_NAME, new HashMap< Object, Integer >() );
      attributeCount.put( LIST_ID, new HashMap< Object, Integer >() );
      attributeCount.put( LIST_NAME, new HashMap< Object, Integer >() );
      attributeCount.put( PRIORITY, new HashMap< Object, Integer >() );
      attributeCount.put( LOCATION_ID, new HashMap< Object, Integer >() );
      attributeCount.put( LOCATION_NAME, new HashMap< Object, Integer >() );
      attributeCount.put( URL, new HashMap< Object, Integer >() );
      
      for ( Task task : tasks )
      {
         inc( attributeCount.get( TASK_NAME ), task.getName() );
         inc( attributeCount.get( LIST_ID ), task.getListId() );
         inc( attributeCount.get( LIST_NAME ), task.getListName() );
         inc( attributeCount.get( PRIORITY ), task.getPriority().toString() );
         inc( attributeCount.get( LOCATION_ID ), task.getLocationId() );
         inc( attributeCount.get( LOCATION_NAME ),
              Strings.emptyIfNull( task.getLocationName() ) );
         inc( attributeCount.get( URL ), task.getUrl() );
      }
   }
   
   
   
   @Override
   public View createFragmentView( LayoutInflater inflater,
                                   ViewGroup container,
                                   Bundle savedInstanceState )
   {
      final View fragmentView = inflater.inflate( R.layout.task_edit_multiple_fragment,
                                                  container,
                                                  false );
      
      final View content = fragmentView.findViewById( android.R.id.content );
      
      nameEditText = (EditText) content.findViewById( R.id.task_edit_desc );
      listsSpinner = (TitleWithSpinnerLayout) content.findViewById( R.id.task_edit_list );
      prioritySpinner = (TitleWithSpinnerLayout) content.findViewById( R.id.task_edit_priority );
      locationSpinner = (TitleWithSpinnerLayout) content.findViewById( R.id.task_edit_location );
      urlEditText = (TitleWithEditTextLayout) content.findViewById( R.id.task_edit_url );
      
      return fragmentView;
   }
   
   
   
   @Override
   public void initContentAfterDataLoaded( ViewGroup content )
   {
      if ( isFirstInitialization )
      {
         initializeNameEdit();
         nameEditText.requestFocus();
         
         initializeUrlEdit();
         
         isFirstInitialization = false;
      }
      
      setNameEditHint();
      setUrlEditHint();
      
      initializePrioritySpinner();
      initializeListSpinner();
      initializeLocationSpinner();
      
      registerInputListeners();
   }
   
   
   
   private void initializeNameEdit()
   {
      final List< Task > tasks = getInitialTasksAssertNotNull();
      
      if ( !isCommonAttrib( TASK_NAME ) )
      {
         nameEditText.setText( Strings.EMPTY_STRING );
         
         if ( nameEditText instanceof AutoCompleteTextView )
         {
            addTaskNameSuggestions( tasks );
         }
      }
      else if ( !tasks.isEmpty() )
      {
         nameEditText.setText( Iterables.first( tasks ).getName() );
      }
   }
   
   
   
   private void addTaskNameSuggestions( List< Task > tasks )
   {
      final Set< String > names = new HashSet< String >( tasks.size() );
      
      for ( Task task : tasks )
      {
         names.add( task.getName() );
      }
      
      final List< String > uniqueTaskNames = new ArrayList< String >( names );
      
      ( (AutoCompleteTextView) nameEditText ).setAdapter( new ArrayAdapter< String >( getActivity(),
                                                                                      android.R.layout.simple_dropdown_item_1line,
                                                                                      android.R.id.text1,
                                                                                      uniqueTaskNames ) );
   }
   
   
   
   private void setNameEditHint()
   {
      if ( !isCommonAttrib( TASK_NAME ) )
      {
         nameEditText.setHint( R.string.edit_multiple_tasks_different_task_names );
      }
   }
   
   
   
   private void initializeUrlEdit()
   {
      if ( !isCommonAttrib( URL ) )
      {
         urlEditText.setText( Strings.EMPTY_STRING );
      }
      else if ( getNumberOfTasks() > 0 )
      {
         urlEditText.setText( Strings.emptyIfNull( Iterables.first( getInitialTasksAssertNotNull() )
                                                            .getUrl() ) );
      }
   }
   
   
   
   private void setUrlEditHint()
   {
      if ( !isCommonAttrib( URL ) )
      {
         urlEditText.setHint( R.string.edit_multiple_tasks_different_urls );
      }
   }
   
   
   
   private void initializePrioritySpinner()
   {
      List< String > priorities = Arrays.asList( getResources().getStringArray( R.array.rtm_priorities ) );
      List< String > priorityValues = Arrays.asList( getResources().getStringArray( R.array.rtm_priority_values ) );
      
      final String priority;
      if ( isCommonAttrib( PRIORITY ) )
      {
         priority = tryGetChange( PRIORITY,
                                  String.class,
                                  getCommonStringAttrib( PRIORITY ) );
      }
      else
      {
         priorities = getEntriesWithCount( PRIORITY, priorities );
         
         priorities.add( 0,
                         getString( R.string.edit_multiple_tasks_different_priorities ) );
         
         priorityValues = new ArrayList< String >( priorityValues );
         priorityValues.add( 0, Strings.EMPTY_STRING );
         
         priority = tryGetChange( PRIORITY, String.class, null );
      }
      
      setSpinnerAdapterForValues( prioritySpinner,
                                  priorityValues,
                                  priorities,
                                  priority );
   }
   
   
   
   private void initializeListSpinner()
   {
      final TaskEditData taskEditData = getLoaderDataAssertNotNull();
      
      final String listId;
      if ( isCommonAttrib( LIST_ID ) )
      {
         listId = tryGetChange( LIST_ID,
                                String.class,
                                getCommonStringAttrib( LIST_ID ) );
      }
      else
      {
         final List< String > listIds = new ArrayList< String >( taskEditData.getListIds() );
         listIds.add( 0, Strings.EMPTY_STRING );
         
         final List< String > listNames = getEntriesWithCount( LIST_NAME,
                                                               taskEditData.getListNames() );
         listNames.add( 0,
                        getString( R.string.edit_multiple_tasks_different_lists ) );
         
         listId = tryGetChange( LIST_ID, String.class, null );
      }
      
      setSpinnerAdapterForValues( listsSpinner,
                                  taskEditData.getListIds(),
                                  taskEditData.getListNames(),
                                  listId );
   }
   
   
   
   private void initializeLocationSpinner()
   {
      final TaskEditData taskEditData = getLoaderDataAssertNotNull();
      
      final List< String > locationIds = taskEditData.getLocationIds();
      final List< String > locationNames = getEntriesWithCount( LOCATION_NAME,
                                                                taskEditData.getLocationNames() );
      
      insertNowhereLocationEntry( locationIds, locationNames );
      
      final String locationId;
      if ( isCommonAttrib( LOCATION_ID ) )
      {
         locationId = tryGetChange( LOCATION_ID,
                                    String.class,
                                    getCommonStringAttrib( LOCATION_ID ) );
      }
      else
      {
         locationIds.add( 0, Strings.EMPTY_STRING );
         locationNames.add( 0,
                            getString( R.string.edit_multiple_tasks_different_locations ) );
         
         locationId = tryGetChange( LOCATION_ID, String.class, null );
      }
      
      setSpinnerAdapterForValues( locationSpinner,
                                  locationIds,
                                  locationNames,
                                  locationId );
   }
   
   
   
   private void insertNowhereLocationEntry( List< String > locationIds,
                                            List< String > locationNames )
   {
      locationIds.add( 0, Long.toString( Constants.NO_ID ) );
      locationNames.add( 0,
                         getEntryDisplayWithCount( getString( R.string.task_edit_location_no ),
                                                   attributeCount.get( LOCATION_NAME )
                                                                 .get( Strings.EMPTY_STRING ) ) );
   }
   
   
   
   private void registerInputListeners()
   {
      nameEditText.addTextChangedListener( new AfterTextChangedWatcher()
      {
         @Override
         public void afterTextChanged( Editable s )
         {
            final String name = getTrimmed( s );
            if ( Strings.isEmptyOrWhitespace( name ) )
            {
               removeChange( TASK_NAME );
            }
            else
            {
               putChange( TASK_NAME, name, String.class );
            }
         }
      } );
      
      urlEditText.addTextChangedListener( new AfterTextChangedWatcher()
      {
         @Override
         public void afterTextChanged( Editable s )
         {
            final String url = getTrimmed( s );
            if ( Strings.isEmptyOrWhitespace( url ) )
            {
               removeChange( URL );
            }
            else
            {
               putChange( URL, url, String.class );
            }
         }
      } );
      
      listsSpinner.setOnItemSelectedListener( new OnItemSelectedListener()
      {
         @Override
         public void onItemSelected( AdapterView< ? > adapter,
                                     View arg1,
                                     int pos,
                                     long id )
         {
            final String listIdString = listsSpinner.getValueAtPos( pos );
            if ( TextUtils.isEmpty( listIdString ) )
            {
               removeChange( LIST_ID );
               removeChange( LIST_NAME );
            }
            else
            {
               putChange( LIST_ID, listIdString, String.class );
               putChange( LIST_NAME,
                          listsSpinner.getSelectedValue(),
                          String.class );
            }
         }
         
         
         
         @Override
         public void onNothingSelected( AdapterView< ? > arg0 )
         {
         }
      } );
      
      locationSpinner.setOnItemSelectedListener( new OnItemSelectedListener()
      {
         @Override
         public void onItemSelected( AdapterView< ? > arg0,
                                     View arg1,
                                     int pos,
                                     long id )
         {
            final String locationIdString = locationSpinner.getValueAtPos( pos );
            if ( TextUtils.isEmpty( locationIdString ) )
            {
               removeChange( LOCATION_ID );
               removeChange( LOCATION_NAME );
            }
            else if ( locationIdString.equals( Long.toString( Constants.NO_ID ) ) )
            {
               putChange( LOCATION_ID,
                          String.valueOf( Constants.NO_ID ),
                          String.class );
               putChange( LOCATION_NAME, null, String.class );
            }
            else
            {
               putChange( LOCATION_ID, locationIdString, String.class );
               putChange( LOCATION_NAME,
                          locationSpinner.getSelectedValue(),
                          String.class );
            }
         }
         
         
         
         @Override
         public void onNothingSelected( AdapterView< ? > arg0 )
         {
         }
      } );
      
      prioritySpinner.setOnItemSelectedListener( new OnItemSelectedListener()
      {
         @Override
         public void onItemSelected( AdapterView< ? > arg0,
                                     View arg1,
                                     int pos,
                                     long arg3 )
         {
            final String priorityValue = prioritySpinner.getValueAtPos( pos );
            if ( TextUtils.isEmpty( priorityValue ) )
            {
               removeChange( PRIORITY );
            }
            else
            {
               putChange( PRIORITY, priorityValue, String.class );
            }
         }
         
         
         
         @Override
         public void onNothingSelected( AdapterView< ? > arg0 )
         {
         }
      } );
   }
   
   
   
   private void setSpinnerAdapterForValues( TitleWithSpinnerLayout spinner,
                                            List< String > values,
                                            List< String > displayStrings,
                                            String initialValue )
   {
      final ArrayAdapter< String > adapter = new ArrayAdapter< String >( getActivity(),
                                                                         android.R.layout.simple_spinner_item,
                                                                         android.R.id.text1,
                                                                         displayStrings );
      
      adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
      spinner.setAdapter( adapter );
      spinner.setValues( values );
      spinner.setSelectionByValue( initialValue, 0 );
   }
   
   
   
   private List< String > getEntriesWithCount( String property,
                                               List< String > entries )
   {
      final Map< Object, Integer > enties2Count = attributeCount.get( property );
      final List< String > entriesWithCount = new ArrayList< String >( entries );
      
      for ( String entry : entries )
      {
         entriesWithCount.add( getEntryDisplayWithCount( entry,
                                                         enties2Count.get( entry ) ) );
      }
      
      return entriesWithCount;
   }
   
   
   
   private String getEntryDisplayWithCount( String entry, int count )
   {
      return getString( R.string.edit_multiple_tasks_entry_with_count,
                        entry,
                        count );
   }
   
   
   
   @Override
   public ValidationResult validate()
   {
      if ( hasChange( TASK_NAME ) )
      {
         return validateName();
      }
      
      return ValidationResult.OK;
   }
   
   
   
   private ValidationResult validateName()
   {
      final boolean ok = !Strings.isEmptyOrWhitespace( getChange( TASK_NAME,
                                                                  String.class ) );
      if ( !ok )
      {
         return new ValidationResult( getString( R.string.task_edit_validate_empty_name ) ).setSourceView( nameEditText )
                                                                                           .setFocusOnErrorView( nameEditText );
      }
      
      return ValidationResult.OK;
   }
   
   
   
   @Override
   public void onFinishEditing()
   {
      applyChanges();
      
      if ( listener != null )
      {
         listener.onUpdateTasks( tasks );
      }
   }
   
   
   
   private void applyChanges()
   {
      for ( Task task : tasks )
      {
         task.setName( tryGetChange( TASK_NAME, String.class, task.getName() ) );
         task.setPriority( Priority.fromString( tryGetChange( PRIORITY,
                                                              String.class,
                                                              task.getPriority()
                                                                  .toString() ) ) );
         task.setUrl( tryGetChange( URL, String.class, task.getUrl() ) );
         
         if ( hasChange( LIST_ID ) )
         {
            task.setList( Long.valueOf( getChange( LIST_ID, String.class ) ),
                          getChange( LIST_NAME, String.class ) );
         }
         
         if ( hasChange( LOCATION_ID ) )
         {
            task.setLocationStub( Long.valueOf( getChange( LOCATION_ID,
                                                           String.class ) ),
                                  getChange( LOCATION_NAME, String.class ) );
         }
      }
   }
   
   
   
   private List< Task > getInitialTasksAssertNotNull()
   {
      if ( tasks == null )
      {
         throw new AssertionError( "expected initial tasks to be not null" );
      }
      
      return tasks;
   }
   
   
   
   private int getNumberOfTasks()
   {
      return getInitialTasksAssertNotNull().size();
   }
   
   
   
   @Override
   public String getLoaderDataName()
   {
      return TaskEditData.class.getSimpleName();
   }
   
   
   
   @Override
   public int getLoaderId()
   {
      return TaskListsAndLocationsLoader.ID;
   }
   
   
   
   @Override
   public Loader< TaskEditData > newLoaderInstance( int id, Bundle args )
   {
      final TaskListsAndLocationsLoader loader = new TaskListsAndLocationsLoader( DomainContext.get( getActivity() ) );
      loader.setRespectContentChanges( false );
      
      return loader;
   }
   
   
   
   private final static void inc( Map< Object, Integer > map, Object value )
   {
      final Integer cnt = map.get( value );
      
      if ( cnt != null )
      {
         map.put( value, cnt + 1 );
      }
      else
      {
         map.put( value, Integer.valueOf( 1 ) );
      }
   }
   
   
   
   private final boolean isCommonAttrib( String key )
   {
      return attributeCount.get( key ).size() == 1;
   }
   
   
   
   private final String getCommonStringAttrib( String key )
   {
      return Iterables.first( attributeCount.get( key ).keySet() ).toString();
   }
   
   
   
   private static String getTrimmed( Editable editable )
   {
      return editable.toString().trim();
   }
}
