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

package dev.drsoran.moloko.content.db;

import java.util.NoSuchElementException;

import android.database.ContentObserver;
import android.net.Uri;
import android.util.Pair;
import dev.drsoran.moloko.content.db.Columns.RawTasksColumns;
import dev.drsoran.moloko.content.db.Columns.RtmListsColumns;
import dev.drsoran.moloko.content.db.Columns.RtmTaskSeriesColumns;
import dev.drsoran.moloko.domain.model.ExtendedTaskCount;
import dev.drsoran.moloko.domain.model.IContact;
import dev.drsoran.moloko.domain.model.ITask;
import dev.drsoran.moloko.domain.model.ITasksList;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.domain.services.ContentException;
import dev.drsoran.moloko.domain.services.IContentRepository;
import dev.drsoran.moloko.grammar.GrammarException;
import dev.drsoran.moloko.grammar.IDateTimeParsing;
import dev.drsoran.moloko.grammar.IRtmSmartFilterParsing;
import dev.drsoran.moloko.grammar.rtmsmart.RtmSmartFilterParsingReturn;


public class DbContentRepository implements IContentRepository
{
   private final static String SEL_NO_DELETED_TASKS;
   
   private final static String SEL_NO_COMPLETED_AND_DELETED_TASKS;
   
   private final DbTasksContentRepositoryPart tasksRepositoryPart;
   
   private final DbTasksListsContentRepositoryPart tasksListsRepositoryPart;
   
   private final DbContactsContentRepositoryPart contactsRepositoryPart;
   
   private final DbRtmSmartFilterEvaluator dbSmartFilterEvaluator;
   
   private final IRtmSmartFilterParsing smartFilterParsing;
   
   static
   {
      SEL_NO_DELETED_TASKS = new StringBuilder( RawTasksTable.TABLE_NAME ).append( "." )
                                                                          .append( RawTasksColumns.DELETED_DATE )
                                                                          .append( " IS NULL" )
                                                                          .toString();
      
      SEL_NO_COMPLETED_AND_DELETED_TASKS = new StringBuilder( RawTasksTable.TABLE_NAME ).append( "." )
                                                                                        .append( RawTasksColumns.COMPLETED_DATE )
                                                                                        .append( " IS NULL AND " )
                                                                                        .append( SEL_NO_DELETED_TASKS )
                                                                                        .toString();
   }
   
   
   
   public DbContentRepository( RtmDatabase database,
      IDateTimeParsing dateTimeParsing,
      IRtmSmartFilterParsing smartFilterParsing )
   {
      this.tasksRepositoryPart = new DbTasksContentRepositoryPart( database );
      this.tasksListsRepositoryPart = new DbTasksListsContentRepositoryPart( database );
      this.contactsRepositoryPart = new DbContactsContentRepositoryPart( database );
      this.dbSmartFilterEvaluator = new DbRtmSmartFilterEvaluator( dateTimeParsing );
      this.smartFilterParsing = smartFilterParsing;
   }
   
   
   
   @Override
   public void registerContentObserver( ContentObserver observer, Uri contentUri )
   {
   }
   
   
   
   @Override
   public void unregisterContentObserver( ContentObserver observer )
   {
   }
   
   
   
   @Override
   public ITask getTask( long taskId, int taskContentOptions ) throws NoSuchElementException,
                                                              ContentException
   {
      return tasksRepositoryPart.getById( taskId, taskContentOptions );
   }
   
   
   
   @Override
   public Iterable< ITask > getTasks( int taskContentOptions ) throws ContentException
   {
      return tasksRepositoryPart.getAll( null, taskContentOptions );
   }
   
   
   
   @Override
   public Iterable< ITask > getTasksInTasksList( ITasksList tasksList,
                                                 int taskContentOptions ) throws ContentException,
                                                                         GrammarException
   {
      if ( tasksList.isSmartList() )
      {
         return getTasksFromSmartFilter( tasksList.getSmartFilter(),
                                         taskContentOptions );
      }
      else
      {
         return getTasksInPhysicalList( tasksList, taskContentOptions );
      }
   }
   
   
   
   @Override
   public ExtendedTaskCount getTaskCountOfTasksList( ITasksList tasksList ) throws ContentException,
                                                                           GrammarException
   {
      if ( tasksList.isSmartList() )
      {
         return getTasksCountFromSmartFilter( tasksList.getSmartFilter() );
      }
      else
      {
         return getTasksCountInPhysicalList( tasksList );
      }
   }
   
   
   
   @Override
   public Iterable< ITask > getTasksFromSmartFilter( RtmSmartFilter smartFilter,
                                                     int taskContentOptions ) throws ContentException,
                                                                             GrammarException
   {
      return evaluateSmartFilterAndQueryTasks( smartFilter, taskContentOptions );
   }
   
   
   
   @Override
   public ITasksList getTasksList( long tasksListId ) throws NoSuchElementException,
                                                     ContentException
   {
      return tasksListsRepositoryPart.getById( tasksListId );
   }
   
   
   
   @Override
   public Iterable< ITasksList > getAllTasksLists() throws ContentException
   {
      return tasksListsRepositoryPart.getAll( null );
   }
   
   
   
   @Override
   public Iterable< ITasksList > getPhysicalTasksLists() throws ContentException
   {
      return tasksListsRepositoryPart.getAll( RtmListsTable.TABLE_NAME + "."
         + RtmListsColumns.IS_SMART_LIST + "=0" );
   }
   
   
   
   @Override
   public IContact getContact( long contactId ) throws NoSuchElementException,
                                               ContentException
   {
      return contactsRepositoryPart.getById( contactId );
   }
   
   
   
   @Override
   public Iterable< IContact > getContacts() throws ContentException
   {
      return contactsRepositoryPart.getAll();
   }
   
   
   
   @Override
   public int getNumTasksContactIsParticipating( long contactId ) throws ContentException
   {
      return contactsRepositoryPart.getNumTasksContactIsParticipating( contactId,
                                                                       SEL_NO_COMPLETED_AND_DELETED_TASKS );
   }
   
   
   
   @Override
   public Iterable< String > getTags() throws ContentException
   {
      return tasksRepositoryPart.getTags( SEL_NO_COMPLETED_AND_DELETED_TASKS );
   }
   
   
   
   private Iterable< ITask > getTasksInPhysicalList( ITasksList tasksList,
                                                     int options ) throws ContentException
   {
      final String selection = getSelectionForTasksInPhysicalListBuilder( tasksList ).toString();
      return tasksRepositoryPart.getAll( selection, options );
   }
   
   
   
   private ExtendedTaskCount getTasksCountInPhysicalList( ITasksList tasksList ) throws ContentException
   {
      final String selection = getSelectionForTasksInListBuilder( tasksList ).append( " AND " )
                                                                             .append( SEL_NO_DELETED_TASKS )
                                                                             .toString();
      return tasksRepositoryPart.getCount( selection );
   }
   
   
   
   private ExtendedTaskCount getTasksCountFromSmartFilter( RtmSmartFilter smartFilter ) throws ContentException,
                                                                                       GrammarException
   {
      final String selection = getSelectionTasksCountFromSmartFilter( smartFilter );
      return tasksRepositoryPart.getCount( selection );
   }
   
   
   
   private Iterable< ITask > evaluateSmartFilterAndQueryTasks( RtmSmartFilter smartFilter,
                                                               int options ) throws ContentException,
                                                                            GrammarException
   {
      final String selection = getSelectionForTasksFromSmartFilter( smartFilter );
      return tasksRepositoryPart.getAll( selection, options );
   }
   
   
   
   private String getSelectionForTasksFromSmartFilter( RtmSmartFilter smartFilter ) throws ContentException,
                                                                                   GrammarException
   {
      final Pair< StringBuilder, RtmSmartFilterParsingReturn > selectionBuilderWithResult = getSelectionFromSmartFilterBuilder( smartFilter );
      final StringBuilder selectionBuilder = selectionBuilderWithResult.first;
      final RtmSmartFilterParsingReturn evaluationResult = selectionBuilderWithResult.second;
      
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
   
   
   
   private String getSelectionTasksCountFromSmartFilter( RtmSmartFilter smartFilter ) throws ContentException,
                                                                                     GrammarException
   {
      final Pair< StringBuilder, RtmSmartFilterParsingReturn > selectionBuilderWithResult = getSelectionFromSmartFilterBuilder( smartFilter );
      final StringBuilder selectionBuilder = selectionBuilderWithResult.first;
      
      // The predefined, static selections are added first to maintain speed gain from table indices.
      selectionBuilder.insert( 0, SEL_NO_DELETED_TASKS );
      
      return selectionBuilder.toString();
   }
   
   
   
   private StringBuilder getSelectionForTasksInPhysicalListBuilder( ITasksList tasksList )
   {
      final StringBuilder selection = getSelectionForTasksInListBuilder( tasksList ).append( " AND " )
                                                                                    .append( SEL_NO_COMPLETED_AND_DELETED_TASKS );
      return selection;
   }
   
   
   
   private StringBuilder getSelectionForTasksInListBuilder( ITasksList tasksList )
   {
      final StringBuilder selection = new StringBuilder().append( RtmTaskSeriesTable.TABLE_NAME )
                                                         .append( "." )
                                                         .append( RtmTaskSeriesColumns.LIST_ID )
                                                         .append( "=" )
                                                         .append( tasksList.getId() );
      return selection;
   }
   
   
   
   private Pair< StringBuilder, RtmSmartFilterParsingReturn > getSelectionFromSmartFilterBuilder( RtmSmartFilter smartFilter ) throws GrammarException
   {
      final RtmSmartFilterParsingReturn evaluationResult = smartFilterParsing.evaluateRtmSmartFilter( smartFilter.getFilterString(),
                                                                                                      dbSmartFilterEvaluator );
      try
      {
         return Pair.create( new StringBuilder( dbSmartFilterEvaluator.getQuery() ),
                             evaluationResult );
      }
      finally
      {
         dbSmartFilterEvaluator.reset();
      }
   }
}
