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

import dev.drsoran.moloko.content.db.Columns.RawTasksColumns;
import dev.drsoran.moloko.content.db.Columns.RtmTaskSeriesColumns;
import dev.drsoran.moloko.domain.model.IContact;
import dev.drsoran.moloko.domain.model.ITask;
import dev.drsoran.moloko.domain.model.ITasksList;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.domain.services.ContentException;
import dev.drsoran.moloko.domain.services.IContentRepository;
import dev.drsoran.moloko.grammar.IDateTimeParsing;
import dev.drsoran.moloko.grammar.IRtmSmartFilterParsing;
import dev.drsoran.moloko.grammar.rtmsmart.RtmSmartFilterReturn;


public class DbContentRepository implements IContentRepository
{
   private final static String SEL_NO_DELETED_TASKS;
   
   private final static String SEL_NO_COMPLETED_AND_DELETED_TASKS;
   
   private final DbTasksContentRepositoryPart tasksRepositoryPart;
   
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
      this.dbSmartFilterEvaluator = new DbRtmSmartFilterEvaluator( dateTimeParsing );
      this.smartFilterParsing = smartFilterParsing;
   }
   
   
   
   @Override
   public ITask getTask( long taskId, int taskContentOptions ) throws ContentException
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
                                                 int taskContentOptions ) throws ContentException
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
   public int getNumberOfTasksInTasksList( ITasksList tasksList ) throws ContentException
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
                                                     int taskContentOptions ) throws ContentException
   {
      return evaluateSmartFilterAndQueryTasks( smartFilter, taskContentOptions );
   }
   
   
   
   @Override
   public ITasksList getTasksList( long tasksListId, int taskListContentOptions ) throws ContentException
   {
      return null;
   }
   
   
   
   @Override
   public Iterable< ITasksList > getTaskLists( int taskListContentOptions ) throws ContentException
   {
      return null;
   }
   
   
   
   @Override
   public IContact getContact( long contactId ) throws ContentException
   {
      return null;
   }
   
   
   
   @Override
   public Iterable< IContact > getContacts() throws ContentException
   {
      return null;
   }
   
   
   
   private Iterable< ITask > getTasksInPhysicalList( ITasksList tasksList,
                                                     int options ) throws ContentException
   {
      final String selection = getSelectionForPhysicalList( tasksList );
      return tasksRepositoryPart.getAll( selection, options );
   }
   
   
   
   private int getTasksCountInPhysicalList( ITasksList tasksList ) throws ContentException
   {
      final String selection = getSelectionForPhysicalList( tasksList );
      return tasksRepositoryPart.getCount( selection );
   }
   
   
   
   private int getTasksCountFromSmartFilter( RtmSmartFilter smartFilter ) throws ContentException
   {
      final String selection = getSelectionFromSmartFilter( smartFilter );
      return tasksRepositoryPart.getCount( selection );
   }
   
   
   
   private Iterable< ITask > evaluateSmartFilterAndQueryTasks( RtmSmartFilter smartFilter,
                                                               int options ) throws ContentException
   {
      final String selection = getSelectionFromSmartFilter( smartFilter );
      return tasksRepositoryPart.getAll( selection, options );
   }
   
   
   
   private String getSelectionForPhysicalList( ITasksList tasksList )
   {
      final StringBuilder selection = new StringBuilder().append( RtmTaskSeriesTable.TABLE_NAME )
                                                         .append( "." )
                                                         .append( RtmTaskSeriesColumns.LIST_ID )
                                                         .append( "=" )
                                                         .append( tasksList.getId() )
                                                         .append( " AND " )
                                                         .append( SEL_NO_COMPLETED_AND_DELETED_TASKS );
      return selection.toString();
   }
   
   
   
   private String getSelectionFromSmartFilter( RtmSmartFilter smartFilter ) throws ContentException
   {
      final RtmSmartFilterReturn evaluationResult = smartFilterParsing.evaluateRtmSmartFilter( smartFilter.getFilterString(),
                                                                                               dbSmartFilterEvaluator );
      try
      {
         if ( evaluationResult.success )
         {
            final StringBuilder queryBuilder;
            
            // The predefined, static selections are added first to maintain speed gain from table indices.
            //
            // SPECIAL CASE: If the filter contains any operator 'completed or status:completed',
            // we include completed tasks. Otherwise we would never show tasks in
            // such lists. In all other cases we exclude completed tasks.
            if ( !evaluationResult.hasCompletedOperator )
            {
               queryBuilder = new StringBuilder( SEL_NO_COMPLETED_AND_DELETED_TASKS );
            }
            else
            {
               queryBuilder = new StringBuilder( SEL_NO_DELETED_TASKS );
            }
            
            final String evaluatedQuery = dbSmartFilterEvaluator.getQuery();
            
            // If the filter is empty, it is semantical equal to evaluating to "TRUE", so we
            // leave the filter away.
            if ( evaluatedQuery.length() > 0 )
            {
               queryBuilder.append( " AND " ).append( evaluatedQuery );
            }
            
            return queryBuilder.toString();
         }
         else
         {
            throw new ContentException( "Unable to evaluate smart filter '"
               + smartFilter.getFilterString() + "'" );
         }
      }
      finally
      {
         dbSmartFilterEvaluator.reset();
      }
   }
}
