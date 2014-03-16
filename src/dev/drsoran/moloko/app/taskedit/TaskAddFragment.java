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

import static dev.drsoran.moloko.content.Columns.TaskColumns.DUE_DATE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.ESTIMATE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.LIST_ID;
import static dev.drsoran.moloko.content.Columns.TaskColumns.LIST_NAME;
import static dev.drsoran.moloko.content.Columns.TaskColumns.LOCATION_ID;
import static dev.drsoran.moloko.content.Columns.TaskColumns.LOCATION_NAME;
import static dev.drsoran.moloko.content.Columns.TaskColumns.PRIORITY;
import static dev.drsoran.moloko.content.Columns.TaskColumns.RECURRENCE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.TAGS;
import static dev.drsoran.moloko.content.Columns.TaskColumns.TASK_NAME;
import static dev.drsoran.moloko.content.Columns.TaskColumns.URL;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.domain.model.Due;
import dev.drsoran.moloko.domain.model.Estimation;
import dev.drsoran.moloko.domain.model.Recurrence;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.moloko.ui.services.IDateFormatterService;
import dev.drsoran.rtm.model.Priority;


public class TaskAddFragment extends AbstractTaskEditFragment
{
   @InstanceState( key = "created_date", defaultValue = "-1" )
   private long createdDate;
   
   private TextView addedDate;
   
   
   
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
   public View createFragmentView( LayoutInflater inflater,
                                   ViewGroup container,
                                   Bundle savedInstanceState )
   {
      final View fragmentView = inflater.inflate( R.layout.task_add_fragment,
                                                  container,
                                                  false );
      
      addedDate = (TextView) fragmentView.findViewById( R.id.task_added_date );
      
      initializeHeadSection();
      
      setFragmentView( fragmentView );
      
      return fragmentView;
   }
   
   
   
   private void initializeHeadSection()
   {
      addedDate.setText( getUiContext().getDateFormatter()
                                       .formatDateTime( createdDate,
                                                        IDateFormatterService.FORMAT_WITH_YEAR ) );
   }
   
   
   
   @Override
   public void onFinishEditing()
   {
      final ITaskEditFragmentListener editFragmentListener = getListener();
      if ( editFragmentListener != null )
      {
         final Task task = createNewTask();
         editFragmentListener.onAddTask( task );
      }
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   private Task createNewTask()
   {
      final Task task = new Task( Constants.NO_ID,
                                  createdDate,
                                  createdDate,
                                  getChange( TASK_NAME, String.class ),
                                  getChange( LIST_ID, Long.class ),
                                  getChange( LIST_NAME, String.class ) );
      
      task.setPriority( getChange( PRIORITY, Priority.class ) );
      task.setLocationStub( getChange( LOCATION_ID, Long.class ),
                            getChange( LOCATION_NAME, String.class ) );
      task.setTags( getChange( TAGS, ArrayList.class ) );
      task.setDue( getChange( DUE_DATE, Due.class ) );
      task.setRecurrence( getChange( RECURRENCE, Recurrence.class ) );
      task.setEstimation( getChange( ESTIMATE, Estimation.class ) );
      task.setUrl( getChange( URL, String.class ) );
      
      return task;
   }
   
   
   
   @Override
   protected void putInitialValues( ValuesContainer valuesContainer,
                                    TaskEditData taskEditData )
   {
      final Bundle args = getArguments();
      
      valuesContainer.putValue( TASK_NAME,
                                args.getString( TASK_NAME ),
                                String.class );
      
      final Pair< Long, String > listIdWithName = determineListIdAndListName( args,
                                                                              taskEditData );
      valuesContainer.putValue( LIST_ID,
                                listIdWithName.first.longValue(),
                                Long.class );
      valuesContainer.putValue( LIST_NAME, listIdWithName.second, String.class );
      
      final Priority priority = args.containsKey( PRIORITY )
                                                            ? (Priority) args.get( PRIORITY )
                                                            : Priority.None;
      valuesContainer.putValue( PRIORITY, priority, Priority.class );
      valuesContainer.putValue( TAGS,
                                args.getStringArrayList( TAGS ),
                                ArrayList.class );
      
      valuesContainer.putValue( DUE_DATE, (Due) args.get( DUE_DATE ), Due.class );
      valuesContainer.putValue( RECURRENCE,
                                (Recurrence) args.get( RECURRENCE ),
                                Recurrence.class );
      valuesContainer.putValue( ESTIMATE,
                                (Estimation) args.get( ESTIMATE ),
                                Estimation.class );
      
      final Pair< Long, String > locationIdWithName = determineLocationIdAndLocationName( args,
                                                                                          taskEditData );
      if ( locationIdWithName != null )
      {
         valuesContainer.putValue( LOCATION_ID,
                                   locationIdWithName.first,
                                   Long.class );
         valuesContainer.putValue( LOCATION_NAME,
                                   locationIdWithName.second,
                                   String.class );
      }
      else
      {
         valuesContainer.putValue( LOCATION_ID, Constants.NO_ID, Long.class );
         valuesContainer.putValue( LOCATION_NAME, null, String.class );
      }
      
      valuesContainer.putValue( URL, args.getString( URL ), String.class );
   }
   
   
   
   private void checkCreatedDate()
   {
      if ( createdDate == Constants.NO_TIME )
      {
         createdDate = getUiContext().getCalendarProvider().getNowMillisUtc();
      }
   }
   
   
   
   private Pair< Long, String > determineListIdAndListName( Bundle args,
                                                            TaskEditData taskEditData )
   {
      Pair< Long, String > listIdWithName = getListNameByListId( args,
                                                                 taskEditData );
      
      if ( listIdWithName == null )
      {
         final String listName = args.getString( LIST_NAME );
         if ( !TextUtils.isEmpty( listName ) )
         {
            listIdWithName = getListIdByListName( listName, taskEditData );
         }
      }
      
      if ( listIdWithName == null )
      {
         // Check if a list name is part of the tags. This can happen
         // if a list name has been entered w/o taken the suggestion.
         // So it has been parsed as a tag since the operator (#) is the
         // same.
         listIdWithName = getListIdAndListNameFromTags( args, taskEditData );
      }
      
      if ( listIdWithName == null )
      {
         listIdWithName = getListIdByListName( getString( R.string.app_list_name_inbox ),
                                               taskEditData );
      }
      
      return listIdWithName;
   }
   
   
   
   private Pair< Long, String > getListNameByListId( Bundle args,
                                                     TaskEditData taskEditData )
   {
      long listId = args.getLong( LIST_ID, Constants.NO_ID );
      if ( listId != Constants.NO_ID )
      {
         final String listName = taskEditData.getListIdsWithListNames()
                                             .get( Long.valueOf( listId ) );
         if ( !TextUtils.isEmpty( listName ) )
         {
            return Pair.create( Long.valueOf( listId ), listName );
         }
      }
      
      return null;
   }
   
   
   
   private Pair< Long, String > getListIdByListName( String listName,
                                                     TaskEditData taskEditData )
   {
      final Map< Long, String > idsWithListNames = taskEditData.getListIdsWithListNames();
      for ( Long listId : idsWithListNames.keySet() )
      {
         final String name = idsWithListNames.get( listId );
         if ( name.equalsIgnoreCase( listName ) )
         {
            return Pair.create( listId, listName );
         }
      }
      
      return null;
   }
   
   
   
   private Pair< Long, String > getListIdAndListNameFromTags( Bundle args,
                                                              TaskEditData taskEditData )
   {
      final List< String > tags = args.getStringArrayList( TAGS );
      if ( tags != null )
      {
         final Pair< Long, String > lastRemovedListId = removeListsFromTags( tags,
                                                                             taskEditData );
         return lastRemovedListId;
      }
      
      return null;
   }
   
   
   
   /**
    * @return the list ID, list name of the last removed list name
    */
   private Pair< Long, String > removeListsFromTags( List< String > tags,
                                                     TaskEditData taskEditData )
   {
      Pair< Long, String > listIdWithName = null;
      
      if ( tags.size() > 0 )
      {
         for ( Iterator< String > i = tags.iterator(); i.hasNext(); )
         {
            final String tag = i.next();
            
            // Check if the tag is a list name
            listIdWithName = getListIdByListName( tag, taskEditData );
            if ( listIdWithName != null )
            {
               i.remove();
            }
         }
      }
      
      return listIdWithName;
   }
   
   
   
   private Pair< Long, String > determineLocationIdAndLocationName( Bundle args,
                                                                    TaskEditData taskEditData )
   {
      Pair< Long, String > locationIdWithName = getLocationNameByLocationId( args,
                                                                             taskEditData );
      
      if ( locationIdWithName == null )
      {
         locationIdWithName = getLocationIdByLocationName( args, taskEditData );
      }
      
      return locationIdWithName;
   }
   
   
   
   private Pair< Long, String > getLocationNameByLocationId( Bundle args,
                                                             TaskEditData taskEditData )
   {
      long locationId = args.getLong( LOCATION_ID, Constants.NO_ID );
      if ( locationId != Constants.NO_ID )
      {
         final String locationName = taskEditData.getLocationIdsWithLocationNames()
                                                 .get( Long.valueOf( locationId ) );
         if ( !TextUtils.isEmpty( locationName ) )
         {
            return Pair.create( Long.valueOf( locationId ), locationName );
         }
      }
      
      return null;
   }
   
   
   
   private Pair< Long, String > getLocationIdByLocationName( Bundle args,
                                                             TaskEditData taskEditData )
   {
      String locationName = args.getString( LOCATION_NAME );
      if ( !TextUtils.isEmpty( locationName ) )
      {
         final Map< Long, String > idsWithLocationNames = taskEditData.getListIdsWithListNames();
         for ( Long locationId : idsWithLocationNames.keySet() )
         {
            final String name = idsWithLocationNames.get( locationId );
            if ( name.equalsIgnoreCase( locationName ) )
            {
               return Pair.create( locationId, locationName );
            }
         }
      }
      
      return null;
   }
}
