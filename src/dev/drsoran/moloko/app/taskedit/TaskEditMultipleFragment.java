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
import static dev.drsoran.moloko.content.Columns.TaskColumns.LOCATION_ID;
import static dev.drsoran.moloko.content.Columns.TaskColumns.PRIORITY;
import static dev.drsoran.moloko.content.Columns.TaskColumns.TASK_NAME;
import static dev.drsoran.moloko.content.Columns.TaskColumns.URL;

import java.util.ArrayList;
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
import dev.drsoran.Iterables;
import dev.drsoran.Strings;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.moloko.ui.IValueChangedListener;
import dev.drsoran.moloko.ui.IndexedValueChangedListener;
import dev.drsoran.moloko.ui.ValidationResult;
import dev.drsoran.moloko.ui.ValueChangedListener;
import dev.drsoran.moloko.util.Lambda.Func1;
import dev.drsoran.moloko.util.Lambda.Func2;
import dev.drsoran.rtm.model.Priority;


public class TaskEditMultipleFragment extends AbstractTaskEditFragment
{
   private final static String STRING_MULTI_VALUE = Strings.EMPTY_STRING;
   
   private final static String URL_MULTI_VALUE = null;
   
   private final static String TAGS_MULTI_VALUE = "multi_tag";
   
   private final static long LONG_MULTI_VALUE = Long.valueOf( -1L );
   
   /**
    * Map< Task attribute, Map< attribute value, number of tasks with attribute > >
    * 
    * e.g. Map< TaskColumns.TASK_NAME, Map< "Task Name", 2 > >
    */
   private final Map< String, Map< Object, Integer > > attributeCount = new HashMap< String, Map< Object, Integer > >();
   
   @InstanceState( key = Intents.Extras.KEY_TASKS )
   private final ArrayList< Task > initialTasks = new ArrayList< Task >();
   
   
   
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
   public void onStart()
   {
      super.onStart();
      joinInitialTaskAttributes();
   }
   
   
   
   @Override
   public void initContentAfterDataLoaded( ViewGroup content )
   {
      hideUnsupportedControls();
      
      initializeNameEdit();
      initializeUrlEdit();
      initializePrioritySpinner();
      initializeListSpinner();
      initializeLocationSpinner();
   }
   
   
   
   @Override
   protected IValueChangedListener getValueChangedListener( String key )
   {
      if ( TASK_NAME.equals( key ) )
      {
         return new ValueChangedListener()
         {
            @Override
            public < T > void onValueChanged( T value, Class< T > type )
            {
               final String newName = Strings.convertFrom( value );
               if ( Strings.isNullOrEmpty( newName ) )
               {
                  revertNames();
               }
               else
               {
                  setNames( newName );
               }
            }
         };
      }
      else if ( URL.equals( key ) )
      {
         return new ValueChangedListener()
         {
            @Override
            public < T > void onValueChanged( T value, Class< T > type )
            {
               final String newUrl = Strings.convertFrom( value );
               if ( Strings.isNullOrEmpty( newUrl ) )
               {
                  revertUrls();
               }
               else
               {
                  setUrl( newUrl );
               }
            }
         };
      }
      else if ( PRIORITY.equals( key ) )
      {
         return new IndexedValueChangedListener()
         {
            @Override
            public < T > void onValueChanged( int index,
                                              T value,
                                              Class< T > type )
            {
               if ( index == 0 )
               {
                  revertPriorites();
               }
               else
               {
                  final String newPriority = Strings.convertFrom( value );
                  setPriority( Priority.fromString( newPriority ) );
               }
            }
         };
      }
      else if ( LIST_ID.equals( key ) )
      {
         return new IndexedValueChangedListener()
         {
            @Override
            public < T > void onValueChanged( int index,
                                              T value,
                                              Class< T > type )
            {
               if ( index == 0 )
               {
                  revertListIds();
               }
               else
               {
                  final String newListId = Strings.convertFrom( value );
                  setListId( newListId );
               }
            }
         };
      }
      else if ( LOCATION_ID.equals( key ) )
      {
         return new IndexedValueChangedListener()
         {
            @Override
            public < T > void onValueChanged( int index,
                                              T value,
                                              Class< T > type )
            {
               if ( index == 0 )
               {
                  revertLocationIds();
               }
               else
               {
                  final String newLocationId = Strings.convertFrom( value );
                  setLocationId( newLocationId );
               }
            }
         };
      }
      
      return null;
   }
   
   
   
   private void setLocationId( final String newLocationId )
   {
      foreachEditTaskDo( new Func1< Task, Void >()
      {
         @Override
         public Void call( Task editedTask )
         {
            editedTask.setLocationStub( Long.valueOf( newLocationId ),
                                        getLocationNameById( newLocationId ) );
            return null;
         }
      } );
   }
   
   
   
   private void revertLocationIds()
   {
      foreachTaskPairDo( new Func2< Task, Task, Void >()
      {
         @Override
         public Void call( Task initialTask, Task editedTask )
         {
            editedTask.setLocationStub( initialTask.getLocationId(),
                                        initialTask.getLocationName() );
            return null;
         }
      } );
   }
   
   
   
   private void setListId( final String newListId )
   {
      foreachEditTaskDo( new Func1< Task, Void >()
      {
         @Override
         public Void call( Task editedTask )
         {
            editedTask.setList( Long.valueOf( newListId ),
                                getListNameById( newListId ) );
            return null;
         }
      } );
   }
   
   
   
   private void revertListIds()
   {
      foreachTaskPairDo( new Func2< Task, Task, Void >()
      {
         @Override
         public Void call( Task initialTask, Task editedTask )
         {
            editedTask.setList( initialTask.getListId(),
                                initialTask.getListName() );
            return null;
         }
      } );
   }
   
   
   
   private void setPriority( final Priority priority )
   {
      foreachEditTaskDo( new Func1< Task, Void >()
      {
         @Override
         public Void call( Task editedTask )
         {
            editedTask.setPriority( priority );
            return null;
         }
      } );
   }
   
   
   
   private void revertPriorites()
   {
      foreachTaskPairDo( new Func2< Task, Task, Void >()
      {
         @Override
         public Void call( Task initialTask, Task editedTask )
         {
            editedTask.setPriority( initialTask.getPriority() );
            return null;
         }
      } );
   }
   
   
   
   private void setUrl( final String newUrl )
   {
      foreachEditTaskDo( new Func1< Task, Void >()
      {
         @Override
         public Void call( Task editedTask )
         {
            editedTask.setUrl( Strings.emptyIfNull( newUrl ) );
            return null;
         }
      } );
   }
   
   
   
   private void revertUrls()
   {
      foreachTaskPairDo( new Func2< Task, Task, Void >()
      {
         @Override
         public Void call( Task initialTask, Task editedTask )
         {
            editedTask.setUrl( initialTask.getUrl() );
            return null;
         }
      } );
   }
   
   
   
   private void setNames( final String newName )
   {
      foreachEditTaskDo( new Func1< Task, Void >()
      {
         @Override
         public Void call( Task editedTask )
         {
            editedTask.setName( newName );
            return null;
         }
      } );
   }
   
   
   
   private void revertNames()
   {
      foreachTaskPairDo( new Func2< Task, Task, Void >()
      {
         @Override
         public Void call( Task initialTask, Task editedTask )
         {
            editedTask.setName( initialTask.getName() );
            return null;
         }
      } );
   }
   
   
   
   private void foreachEditTaskDo( Func1< Task, Void > action )
   {
      final List< Task > editedTasks = getEditedTasksAssertNotNull();
      
      for ( int i = 0; i < editedTasks.size(); i++ )
      {
         final Task editedTask = editedTasks.get( i );
         action.call( editedTask );
      }
   }
   
   
   
   private void foreachTaskPairDo( Func2< Task, Task, Void > action )
   {
      final List< Task > initialTasks = getInitialTasksAssertNotNull();
      final List< Task > editedTasks = getEditedTasksAssertNotNull();
      
      for ( int i = 0; i < initialTasks.size(); i++ )
      {
         final Task initialTask = initialTasks.get( i );
         final Task editedTask = editedTasks.get( i );
         action.call( initialTask, editedTask );
      }
   }
   
   
   
   private String getListNameById( String listId )
   {
      final TaskEditData taskEditData = getLoaderDataAssertNotNull();
      return taskEditData.getListIdsWithListNames().get( listId );
   }
   
   
   
   private String getLocationNameById( String locationId )
   {
      final TaskEditData taskEditData = getLoaderDataAssertNotNull();
      return taskEditData.getLocationIdsWithLocationNames().get( locationId );
   }
   
   
   
   private void joinInitialTaskAttributes()
   {
      final List< Task > tasks = getInitialTasksAssertNotNull();
      
      attributeCount.put( TASK_NAME, new HashMap< Object, Integer >() );
      attributeCount.put( LIST_ID, new HashMap< Object, Integer >() );
      attributeCount.put( PRIORITY, new HashMap< Object, Integer >() );
      attributeCount.put( LOCATION_ID, new HashMap< Object, Integer >() );
      attributeCount.put( URL, new HashMap< Object, Integer >() );
      
      for ( Task task : tasks )
      {
         inc( attributeCount.get( TASK_NAME ), task.getName() );
         inc( attributeCount.get( LIST_ID ), task.getListId() );
         inc( attributeCount.get( PRIORITY ), task.getPriority().toString() );
         inc( attributeCount.get( LOCATION_ID ), task.getLocationId() );
         inc( attributeCount.get( URL ), task.getUrl() );
      }
   }
   
   
   
   private void initializeNameEdit()
   {
      final List< Task > tasks = getInitialTasksAssertNotNull();
      
      if ( !isCommonAttrib( TASK_NAME ) )
      {
         nameEditText.setText( Strings.EMPTY_STRING );
         nameEditText.setHint( R.string.edit_multiple_tasks_different_task_names );
         
         if ( nameEditText instanceof AutoCompleteTextView )
         {
            final Set< String > names = new HashSet< String >( tasks.size() );
            
            for ( Task task : tasks )
            {
               names.add( task.getName() );
            }
            
            final List< String > uniqueTaskNames = new ArrayList< String >( names );
            
            ( (AutoCompleteTextView) nameEditText ).setAdapter( new ArrayAdapter< String >( getSherlockActivity(),
                                                                                            android.R.layout.simple_dropdown_item_1line,
                                                                                            android.R.id.text1,
                                                                                            uniqueTaskNames ) );
         }
      }
      else if ( tasks.size() > 0 )
      {
         nameEditText.setText( Iterables.first( tasks ).getName() );
      }
   }
   
   
   
   private void initializeUrlEdit()
   {
      if ( !isCommonAttrib( URL ) )
      {
         urlEditText.setText( Strings.EMPTY_STRING );
         urlEditText.setHint( R.string.edit_multiple_tasks_different_urls );
      }
      else if ( getNumberOfTasks() > 0 )
      {
         urlEditText.setText( Strings.emptyIfNull( Iterables.first( getInitialTasksAssertNotNull() )
                                                            .getUrl() ) );
      }
   }
   
   
   
   private void initializeListSpinner()
   {
      if ( isCommonAttrib( LIST_ID ) )
      {
         super.defaultInitializeListSpinner( getCommonStringAttrib( LIST_ID ) );
      }
      else
      {
         final ArrayAdapter< String > adapter = defaultInitializeListSpinner( null );
         
         if ( adapter != null )
         {
            adapter.insert( getString( R.string.edit_multiple_tasks_different_lists ),
                            0 );
         }
      }
   }
   
   
   
   private void initializeLocationSpinner()
   {
      if ( isCommonAttrib( LOCATION_ID ) )
      {
         super.defaultInitializeLocationSpinner( getCommonStringAttrib( LOCATION_ID ) );
      }
      else
      {
         final ArrayAdapter< String > adapter = defaultInitializeLocationSpinner( null );
         
         if ( adapter != null )
         {
            adapter.insert( getString( R.string.edit_multiple_tasks_different_locations ),
                            0 );
         }
      }
   }
   
   
   
   private void initializePrioritySpinner()
   {
      if ( isCommonAttrib( PRIORITY ) )
      {
         super.defaultInitializePrioritySpinner( getCommonStringAttrib( PRIORITY ) );
      }
      else
      {
         final ArrayAdapter< String > adapter = defaultInitializePrioritySpinner( null );
         
         if ( adapter != null )
         {
            adapter.insert( getString( R.string.edit_multiple_tasks_different_priorities ),
                            0 );
         }
      }
   }
   
   
   
   private void hideUnsupportedControls()
   {
      addedDate.setVisibility( View.GONE );
      completedDate.setVisibility( View.GONE );
      postponed.setVisibility( View.GONE );
      source.setVisibility( View.GONE );
      tagsContainer.setVisibility( View.GONE );
      dueContainer.setVisibility( View.GONE );
      estimateContainer.setVisibility( View.GONE );
      recurrContainer.setVisibility( View.GONE );
   }
   
   
   
   @Override
   protected ValidationResult validateName()
   {
      if ( hasChange( TASK_NAME ) )
      {
         return super.validateName();
      }
      else
      {
         return ValidationResult.OK;
      }
   }
   
   
   
   private List< Task > getInitialTasksAssertNotNull()
   {
      if ( initialTasks == null )
      {
         throw new AssertionError( "expected initial tasks to be not null" );
      }
      
      return initialTasks;
   }
   
   
   
   private int getNumberOfTasks()
   {
      return getInitialTasksAssertNotNull().size();
   }
   
   
   
   private List< Task > getEditedTasksAssertNotNull()
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
   
   
   
   private final boolean isCommonAttrib( String key )
   {
      return attributeCount.get( key ).size() == 1;
   }
   
   
   
   private final String getCommonStringAttrib( String key )
   {
      return attributeCount.get( key ).toString();
   }
}
