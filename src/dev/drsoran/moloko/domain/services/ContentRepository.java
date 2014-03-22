/*
 * Copyright (c) 2010 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.domain.services;

import static dev.drsoran.moloko.content.ContentSelections.SEL_NO_COMPLETED_AND_DELETED_TASKS;
import static dev.drsoran.moloko.content.ContentSelections.SEL_NO_DELETED_NOTES;
import static dev.drsoran.moloko.content.ContentSelections.SEL_NO_DELETED_NO_ARCHIVED_TASKS_LISTS;
import static dev.drsoran.moloko.content.ContentSelections.SEL_NO_DELETED_TASKS;
import static dev.drsoran.moloko.content.ContentSelections.SEL_PHYSICAL_NO_DELETED_NO_ARCHIVED_TASKS_LISTS;

import java.util.Iterator;
import java.util.NoSuchElementException;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import dev.drsoran.moloko.content.Columns.CloudEntryColumns;
import dev.drsoran.moloko.content.Columns.ContactColumns;
import dev.drsoran.moloko.content.Columns.LocationColumns;
import dev.drsoran.moloko.content.Columns.NoteColumns;
import dev.drsoran.moloko.content.Columns.ParticipantColumns;
import dev.drsoran.moloko.content.Columns.SettingsColumns;
import dev.drsoran.moloko.content.Columns.SyncTimesColumns;
import dev.drsoran.moloko.content.Columns.TagColumns;
import dev.drsoran.moloko.content.Columns.TaskColumns;
import dev.drsoran.moloko.content.Columns.TaskCountColumns;
import dev.drsoran.moloko.content.Columns.TasksListColumns;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.content.ContentQueryHandler;
import dev.drsoran.moloko.domain.content.IModelElementFactory;
import dev.drsoran.moloko.domain.model.CloudEntry;
import dev.drsoran.moloko.domain.model.Contact;
import dev.drsoran.moloko.domain.model.ExtendedTaskCount;
import dev.drsoran.moloko.domain.model.Location;
import dev.drsoran.moloko.domain.model.Note;
import dev.drsoran.moloko.domain.model.Participant;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.domain.model.Settings;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.rtm.Pair;
import dev.drsoran.rtm.parsing.GrammarException;
import dev.drsoran.rtm.parsing.IRtmDateTimeParsing;
import dev.drsoran.rtm.parsing.IRtmSmartFilterParsing;
import dev.drsoran.rtm.parsing.rtmsmart.IRtmSmartFilterEvaluator;
import dev.drsoran.rtm.parsing.rtmsmart.RtmSmartFilterParsingReturn;
import dev.drsoran.rtm.sync.SyncTime;


public class ContentRepository implements IContentRepository
{
   private final ContentResolver contentResolver;
   
   private final IRtmSmartFilterParsing smartFilterParsing;
   
   private final IRtmSmartFilterEvaluatorFactory smartFilterEvaluatorFactory;
   
   private final ContentQueryHandler< Task > taskQueryHandler;
   
   private final ContentQueryHandler< ExtendedTaskCount > taskCountQueryHandler;
   
   private final ContentQueryHandler< Note > taskNotesQueryHandler;
   
   private final ContentQueryHandler< Participant > taskParticipantsQueryHandler;
   
   private final ContentQueryHandler< TasksList > tasksListsQueryHandler;
   
   private final ContentQueryHandler< Contact > contactsQueryHandler;
   
   private final ContentQueryHandler< Location > locationsQueryHandler;
   
   private final ContentQueryHandler< String > tagsQueryHandler;
   
   private final ContentQueryHandler< Settings > settingsQueryHandler;
   
   private final ContentQueryHandler< SyncTime > syncTimesQueryHandler;
   
   private final ContentQueryHandler< CloudEntry > cloudEntriesQueryHandler;
   
   
   
   public ContentRepository( ContentResolver contentResolver,
      IModelElementFactory modelElementFactory,
      IRtmDateTimeParsing dateTimeParsing,
      IRtmSmartFilterParsing smartFilterParsing,
      IRtmSmartFilterEvaluatorFactory smartFilterEvaluatorFactory )
   {
      this.contentResolver = contentResolver;
      this.smartFilterParsing = smartFilterParsing;
      this.smartFilterEvaluatorFactory = smartFilterEvaluatorFactory;
      
      this.taskQueryHandler = new ContentQueryHandler< Task >( contentResolver,
                                                               TaskColumns.PROJECTION,
                                                               modelElementFactory,
                                                               Task.class );
      
      this.taskCountQueryHandler = new ContentQueryHandler< ExtendedTaskCount >( contentResolver,
                                                                                 TaskCountColumns.PROJECTION,
                                                                                 modelElementFactory,
                                                                                 ExtendedTaskCount.class );
      
      this.taskNotesQueryHandler = new ContentQueryHandler< Note >( contentResolver,
                                                                    NoteColumns.PROJECTION,
                                                                    modelElementFactory,
                                                                    Note.class );
      
      this.taskParticipantsQueryHandler = new ContentQueryHandler< Participant >( contentResolver,
                                                                                  ParticipantColumns.PROJECTION,
                                                                                  modelElementFactory,
                                                                                  Participant.class );
      
      this.tasksListsQueryHandler = new ContentQueryHandler< TasksList >( contentResolver,
                                                                          TasksListColumns.PROJECTION,
                                                                          modelElementFactory,
                                                                          TasksList.class );
      
      this.contactsQueryHandler = new ContentQueryHandler< Contact >( contentResolver,
                                                                      ContactColumns.PROJECTION,
                                                                      modelElementFactory,
                                                                      Contact.class );
      
      this.locationsQueryHandler = new ContentQueryHandler< Location >( contentResolver,
                                                                        LocationColumns.PROJECTION,
                                                                        modelElementFactory,
                                                                        Location.class );
      
      this.tagsQueryHandler = new ContentQueryHandler< String >( contentResolver,
                                                                 TagColumns.PROJECTION,
                                                                 modelElementFactory,
                                                                 String.class );
      
      this.settingsQueryHandler = new ContentQueryHandler< Settings >( contentResolver,
                                                                       SettingsColumns.PROJECTION,
                                                                       modelElementFactory,
                                                                       Settings.class );
      
      this.syncTimesQueryHandler = new ContentQueryHandler< SyncTime >( contentResolver,
                                                                        SyncTimesColumns.PROJECTION,
                                                                        modelElementFactory,
                                                                        SyncTime.class );
      
      this.cloudEntriesQueryHandler = new ContentQueryHandler< CloudEntry >( contentResolver,
                                                                             CloudEntryColumns.PROJECTION,
                                                                             modelElementFactory,
                                                                             CloudEntry.class );
   }
   
   
   
   @Override
   public Task getTask( long taskId, TaskContentOptions taskContentOptions ) throws NoSuchElementException,
                                                                            ContentException
   {
      final Task task = taskQueryHandler.getElement( ContentUris.TASKS_CONTENT_URI_ID,
                                                     taskId );
      
      setRequestedOptions( taskContentOptions, task );
      
      return task;
   }
   
   
   
   private void setRequestedOptions( TaskContentOptions taskContentOptions,
                                     final Task task )
   {
      if ( taskContentOptions.hasFlag( TaskContentOptions.WithNotes ) )
      {
         addNotesToTask( task );
      }
      
      if ( taskContentOptions.hasFlag( TaskContentOptions.WithParticipants ) )
      {
         addParticipantsToTask( task );
      }
      
      if ( taskContentOptions.hasFlag( TaskContentOptions.WithLocation ) )
      {
         setLocationToTask( task );
      }
   }
   
   
   
   @Override
   public Iterable< Task > getAllTasks( TaskContentOptions taskContentOptions ) throws ContentException
   {
      return getAllTasksImpl( SEL_NO_COMPLETED_AND_DELETED_TASKS,
                              taskContentOptions );
   }
   
   
   
   private Iterable< Task > getAllTasksImpl( String selection,
                                             TaskContentOptions taskContentOptions ) throws ContentException
   {
      final Iterable< Task > tasks = taskQueryHandler.getAll( ContentUris.TASKS_CONTENT_URI,
                                                              selection,
                                                              null );
      for ( Task task : tasks )
      {
         setRequestedOptions( taskContentOptions, task );
      }
      
      return tasks;
   }
   
   
   
   private void addNotesToTask( Task task )
   {
      final Iterable< Note > notes = taskNotesQueryHandler.getAllForAggregation( ContentUris.TASK_NOTES_CONTENT_URI,
                                                                                 task.getId(),
                                                                                 SEL_NO_DELETED_NOTES,
                                                                                 null );
      for ( Note note : notes )
      {
         task.addNote( note );
      }
   }
   
   
   
   private void addParticipantsToTask( Task task )
   {
      final Iterable< Participant > participants = taskParticipantsQueryHandler.getAllForAggregation( ContentUris.TASK_PARTICIPANTS_CONTENT_URI,
                                                                                                      task.getId(),
                                                                                                      null,
                                                                                                      null );
      for ( Participant participant : participants )
      {
         task.addParticipant( participant );
      }
   }
   
   
   
   private void setLocationToTask( Task task )
   {
      if ( task.isLocated() )
      {
         final Location location = locationsQueryHandler.getElement( ContentUris.LOCATIONS_CONTENT_URI_ID,
                                                                     task.getLocationId() );
         task.setLocation( location );
      }
      else
      {
         task.setLocation( null );
      }
   }
   
   
   
   @Override
   public Iterable< Task > getTasksInTasksList( TasksList tasksList,
                                                TaskContentOptions taskContentOptions ) throws ContentException,
                                                                                       GrammarException
   {
      if ( tasksList == null )
      {
         throw new IllegalArgumentException( "tasksList" );
      }
      
      if ( tasksList.isSmartList() )
      {
         return getTasksFromSmartFilter( tasksList.getSmartFilter(),
                                         taskContentOptions );
      }
      else
      {
         return getTasksInPhysicalList( tasksList.getId(), taskContentOptions );
      }
   }
   
   
   
   @Override
   public Iterable< Task > getTasksInPhysicalTasksList( long tasksListId,
                                                        TaskContentOptions taskContentOptions ) throws ContentException
   {
      return getTasksInPhysicalList( tasksListId, taskContentOptions );
   }
   
   
   
   private Iterable< Task > getTasksInPhysicalList( long tasksListId,
                                                    TaskContentOptions options ) throws ContentException
   {
      final String selection = getSelectionForTasksInPhysicalListBuilder( tasksListId ).toString();
      return getAllTasksImpl( selection, options );
   }
   
   
   
   private StringBuilder getSelectionForTasksInPhysicalListBuilder( long tasksListId )
   {
      final StringBuilder selection = getSelectionForTasksInTasksListBuilder( tasksListId ).append( " AND " )
                                                                                           .append( SEL_NO_COMPLETED_AND_DELETED_TASKS );
      return selection;
   }
   
   
   
   @Override
   public ExtendedTaskCount getTaskCountOfTasksList( TasksList tasksList ) throws ContentException,
                                                                          GrammarException
   {
      if ( tasksList == null )
      {
         throw new IllegalArgumentException( "tasksList" );
      }
      
      if ( tasksList.isSmartList() )
      {
         return getTasksCountFromSmartFilter( tasksList.getSmartFilter() );
      }
      else
      {
         return getTasksCountInPhysicalList( tasksList.getId() );
      }
   }
   
   
   
   private ExtendedTaskCount getTasksCountFromSmartFilter( RtmSmartFilter smartFilter ) throws ContentException,
                                                                                       GrammarException
   {
      final String selection = getSelectionTasksCountFromSmartFilter( smartFilter );
      return getExtendedTaskCountOrDefault( selection );
   }
   
   
   
   private ExtendedTaskCount getExtendedTaskCountOrDefault( String selection )
   {
      final Iterable< ExtendedTaskCount > taskCount = taskCountQueryHandler.getAll( ContentUris.TASKS_COUNT_CONTENT_URI,
                                                                                    selection,
                                                                                    null );
      final Iterator< ExtendedTaskCount > taskCountIterator = taskCount.iterator();
      
      if ( taskCountIterator.hasNext() )
      {
         return taskCountIterator.next();
      }
      else
      {
         return new ExtendedTaskCount( 0, 0, 0, 0, 0, 0 );
      }
   }
   
   
   
   private String getSelectionTasksCountFromSmartFilter( RtmSmartFilter smartFilter ) throws ContentException,
                                                                                     GrammarException
   {
      final Pair< StringBuilder, RtmSmartFilterParsingReturn > selectionBuilderWithResult = getSelectionFromSmartFilterBuilder( smartFilter );
      final StringBuilder selectionBuilder = selectionBuilderWithResult.first;
      
      // The predefined, static selections are added first to maintain speed gain from table indices.
      selectionBuilder.insert( 0, " AND " ).insert( 0, SEL_NO_DELETED_TASKS );
      
      return selectionBuilder.toString();
   }
   
   
   
   private ExtendedTaskCount getTasksCountInPhysicalList( long tasksListId ) throws ContentException
   {
      final String selection = getSelectionForTasksInTasksListBuilder( tasksListId ).append( " AND " )
                                                                                    .append( SEL_NO_DELETED_TASKS )
                                                                                    .toString();
      return getExtendedTaskCountOrDefault( selection );
   }
   
   
   
   private StringBuilder getSelectionForTasksInTasksListBuilder( long tasksListId )
   {
      final StringBuilder selection = new StringBuilder().append( TaskColumns.LIST_ID )
                                                         .append( "=" )
                                                         .append( tasksListId );
      return selection;
   }
   
   
   
   @Override
   public Iterable< Task > getTasksFromSmartFilter( RtmSmartFilter smartFilter,
                                                    TaskContentOptions taskContentOptions ) throws ContentException,
                                                                                           GrammarException
   {
      if ( smartFilter == null )
      {
         throw new IllegalArgumentException( "smartFilter" );
      }
      
      final String selection = getSelectionForTasksFromSmartFilter( smartFilter );
      return getAllTasksImpl( selection, taskContentOptions );
   }
   
   
   
   private String getSelectionForTasksFromSmartFilter( RtmSmartFilter smartFilter ) throws ContentException,
                                                                                   GrammarException
   {
      final Pair< StringBuilder, RtmSmartFilterParsingReturn > selectionBuilderWithResult = getSelectionFromSmartFilterBuilder( smartFilter );
      final StringBuilder selectionBuilder = selectionBuilderWithResult.first;
      final RtmSmartFilterParsingReturn evaluationResult = selectionBuilderWithResult.second;
      
      selectionBuilder.insert( 0, " AND " );
      
      // The predefined, static selections are added first to maintain speed gain from table indices.
      //
      // SPECIAL CASE: If the filter contains any operator 'completed or status:completed',
      // we include completed tasks. Otherwise we would never show such tasks.
      if ( !evaluationResult.hasCompletedOperator )
      {
         selectionBuilder.insert( 0, SEL_NO_COMPLETED_AND_DELETED_TASKS );
      }
      else
      {
         selectionBuilder.insert( 0, SEL_NO_DELETED_TASKS );
      }
      
      return selectionBuilder.toString();
   }
   
   
   
   private Pair< StringBuilder, RtmSmartFilterParsingReturn > getSelectionFromSmartFilterBuilder( RtmSmartFilter smartFilter ) throws GrammarException
   {
      final IRtmSmartFilterEvaluator evaluator = smartFilterEvaluatorFactory.createRtmSmartFilterEvaluator();
      final RtmSmartFilterParsingReturn evaluationResult = smartFilterParsing.evaluateRtmSmartFilter( smartFilter.getFilterString(),
                                                                                                      evaluator );
      try
      {
         return Pair.create( new StringBuilder( evaluator.getResult() ),
                             evaluationResult );
      }
      finally
      {
         evaluator.reset();
      }
   }
   
   
   
   @Override
   public TasksList getTasksList( long tasksListId ) throws NoSuchElementException,
                                                    ContentException
   {
      return tasksListsQueryHandler.getElement( ContentUris.TASKS_LISTS_CONTENT_URI_ID,
                                                tasksListId );
   }
   
   
   
   @Override
   public Iterable< TasksList > getAllTasksLists() throws ContentException
   {
      return tasksListsQueryHandler.getAll( ContentUris.TASKS_LISTS_CONTENT_URI,
                                            SEL_NO_DELETED_NO_ARCHIVED_TASKS_LISTS,
                                            TasksListColumns.DEFAULT_SORT_ORDER );
   }
   
   
   
   @Override
   public Iterable< TasksList > getPhysicalTasksLists() throws ContentException
   {
      return tasksListsQueryHandler.getAll( ContentUris.TASKS_LISTS_CONTENT_URI,
                                            SEL_PHYSICAL_NO_DELETED_NO_ARCHIVED_TASKS_LISTS,
                                            TasksListColumns.DEFAULT_SORT_ORDER );
   }
   
   
   
   @Override
   public Iterable< String > getAllTags() throws ContentException
   {
      return tagsQueryHandler.getAll( ContentUris.TAGS_CONTENT_URI,
                                      SEL_NO_COMPLETED_AND_DELETED_TASKS,
                                      null );
   }
   
   
   
   @Override
   public Contact getContact( long contactId ) throws NoSuchElementException,
                                              ContentException
   {
      return contactsQueryHandler.getElement( ContentUris.CONTACTS_CONTENT_URI_ID,
                                              contactId );
   }
   
   
   
   @Override
   public Iterable< Contact > getAllContacts() throws ContentException
   {
      return contactsQueryHandler.getAll( ContentUris.CONTACTS_CONTENT_URI,
                                          null,
                                          null );
   }
   
   
   
   @Override
   public Location getLocation( long locationId ) throws NoSuchElementException,
                                                 ContentException
   {
      return locationsQueryHandler.getElement( ContentUris.LOCATIONS_CONTENT_URI_ID,
                                               locationId );
   }
   
   
   
   @Override
   public Iterable< Location > getAllLocations() throws ContentException
   {
      return locationsQueryHandler.getAll( ContentUris.LOCATIONS_CONTENT_URI,
                                           null,
                                           null );
   }
   
   
   
   @Override
   public Settings getRtmSettings() throws ContentException
   {
      return settingsQueryHandler.getElement( ContentUris.RTM_SETTINGS_CONTENT_URI_ID,
                                              SettingsColumns.SINGLETON_ID );
   }
   
   
   
   @Override
   public SyncTime getSyncTimes() throws ContentException
   {
      return syncTimesQueryHandler.getElement( ContentUris.SYNC_CONTENT_URI_ID,
                                               SyncTimesColumns.SINGLETON_ID );
   }
   
   
   
   @Override
   public Iterable< CloudEntry > getCloudEntries() throws ContentException
   {
      return cloudEntriesQueryHandler.getAll( ContentUris.CLOUD_ENTRIES_CONTENT_URI,
                                              SEL_NO_COMPLETED_AND_DELETED_TASKS,
                                              null );
   }
   
   
   
   @Override
   public void registerContentObserver( ContentObserver observer, Uri contentUri )
   {
      if ( observer == null )
      {
         throw new IllegalArgumentException( "observer" );
      }
      
      if ( contentUri == null )
      {
         throw new IllegalArgumentException( "contentUri" );
      }
      
      contentResolver.registerContentObserver( contentUri, true, observer );
   }
   
   
   
   @Override
   public void unregisterContentObserver( ContentObserver observer )
   {
      contentResolver.unregisterContentObserver( observer );
   }
}
