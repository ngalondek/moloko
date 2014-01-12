/* 
 *	Copyright (c) 2014 Ronny Röhricht
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

package dev.drsoran.rtm.service;

import java.util.Collection;

import dev.drsoran.Strings;
import dev.drsoran.rtm.IRtmConnection;
import dev.drsoran.rtm.IRtmConnectionFactory;
import dev.drsoran.rtm.Param;
import dev.drsoran.rtm.RtmResponse;
import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.model.Priority;
import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.model.RtmNote;
import dev.drsoran.rtm.model.RtmTask;
import dev.drsoran.rtm.model.RtmTasksList;
import dev.drsoran.rtm.model.RtmTimeline;


public class RtmContentEditService implements IRtmContentEditService
{
   private final IRtmConnectionFactory connectionFactory;
   
   
   
   public RtmContentEditService( IRtmConnectionFactory connectionFactory )
   {
      this.connectionFactory = connectionFactory;
   }
   
   
   
   @Override
   public RtmResponse< RtmTasksList > lists_add( String timelineId,
                                                 String listName,
                                                 String smartFilter ) throws RtmServiceException
   {
      checkNotNullOrEmpty( listName );
      
      final Param timelineParam = checkAndCreateTimelineParam( timelineId );
      final Param nameParam = new Param( "name", listName );
      
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      
      final RtmResponse< RtmTasksList > response;
      if ( !Strings.isNullOrEmpty( smartFilter ) )
      {
         response = rtmConnection.executeMethod( RtmTasksList.class,
                                                 "rtm.lists.add",
                                                 timelineParam,
                                                 nameParam,
                                                 new Param( "filter",
                                                            smartFilter ) );
      }
      else
      {
         response = rtmConnection.executeMethod( RtmTasksList.class,
                                                 "rtm.lists.add",
                                                 timelineParam,
                                                 nameParam );
      }
      
      return response;
   }
   
   
   
   @Override
   public RtmResponse< RtmTasksList > lists_delete( String timelineId,
                                                    String listId ) throws RtmServiceException
   {
      checkNotNullOrEmpty( listId );
      
      final Param timelineParam = checkAndCreateTimelineParam( timelineId );
      
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      final RtmResponse< RtmTasksList > response = rtmConnection.executeMethod( RtmTasksList.class,
                                                                                "rtm.lists.delete",
                                                                                timelineParam,
                                                                                new Param( "list_id",
                                                                                           listId ) );
      return response;
   }
   
   
   
   @Override
   public RtmResponse< RtmTasksList > lists_setName( String timelineId,
                                                     String listId,
                                                     String newName ) throws RtmServiceException
   {
      checkNotNullOrEmpty( listId );
      checkNotNullOrEmpty( newName );
      
      final Param timelineParam = checkAndCreateTimelineParam( timelineId );
      
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      final RtmResponse< RtmTasksList > response = rtmConnection.executeMethod( RtmTasksList.class,
                                                                                "rtm.lists.setName",
                                                                                timelineParam,
                                                                                new Param( "list_id",
                                                                                           listId ),
                                                                                new Param( "name",
                                                                                           newName ) );
      return response;
   }
   
   
   
   @Override
   public RtmResponse< RtmTask[] > tasks_add( String timelineId,
                                              String listId,
                                              String name ) throws RtmServiceException
   {
      checkNotNullOrEmpty( listId );
      checkNotNullOrEmpty( name );
      
      final Param timelineParam = checkAndCreateTimelineParam( timelineId );
      
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      final RtmResponse< RtmTask[] > response = rtmConnection.executeMethod( RtmTask[].class,
                                                                             "rtm.tasks.add",
                                                                             timelineParam,
                                                                             new Param( "list_id",
                                                                                        listId ),
                                                                             new Param( "name",
                                                                                        name ) );
      return response;
   }
   
   
   
   @Override
   public RtmResponse< RtmTask[] > tasks_complete( String timelineId,
                                                   String listId,
                                                   String taskSeriesId,
                                                   String taskId ) throws RtmServiceException
   {
      checkNotNullOrEmpty( listId );
      checkNotNullOrEmpty( taskSeriesId );
      checkNotNullOrEmpty( taskId );
      
      final Param timelineParam = checkAndCreateTimelineParam( timelineId );
      
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      final RtmResponse< RtmTask[] > response = rtmConnection.executeMethod( RtmTask[].class,
                                                                             "rtm.tasks.complete",
                                                                             timelineParam,
                                                                             new Param( "list_id",
                                                                                        listId ),
                                                                             new Param( "taskseries_id",
                                                                                        taskSeriesId ),
                                                                             new Param( "task_id",
                                                                                        taskId ) );
      return response;
   }
   
   
   
   @Override
   public RtmResponse< RtmTask[] > tasks_uncomplete( String timelineId,
                                                     String listId,
                                                     String taskSeriesId,
                                                     String taskId ) throws RtmServiceException
   {
      checkNotNullOrEmpty( listId );
      checkNotNullOrEmpty( taskSeriesId );
      checkNotNullOrEmpty( taskId );
      
      final Param timelineParam = checkAndCreateTimelineParam( timelineId );
      
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      final RtmResponse< RtmTask[] > response = rtmConnection.executeMethod( RtmTask[].class,
                                                                             "rtm.tasks.uncomplete",
                                                                             timelineParam,
                                                                             new Param( "list_id",
                                                                                        listId ),
                                                                             new Param( "taskseries_id",
                                                                                        taskSeriesId ),
                                                                             new Param( "task_id",
                                                                                        taskId ) );
      return response;
   }
   
   
   
   @Override
   public RtmResponse< RtmTask[] > tasks_delete( String timelineId,
                                                 String listId,
                                                 String taskSeriesId,
                                                 String taskId ) throws RtmServiceException
   {
      checkNotNullOrEmpty( listId );
      checkNotNullOrEmpty( taskSeriesId );
      checkNotNullOrEmpty( taskId );
      
      final Param timelineParam = checkAndCreateTimelineParam( timelineId );
      
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      final RtmResponse< RtmTask[] > response = rtmConnection.executeMethod( RtmTask[].class,
                                                                             "rtm.tasks.delete",
                                                                             timelineParam,
                                                                             new Param( "list_id",
                                                                                        listId ),
                                                                             new Param( "taskseries_id",
                                                                                        taskSeriesId ),
                                                                             new Param( "task_id",
                                                                                        taskId ) );
      return response;
   }
   
   
   
   @Override
   public RtmResponse< RtmTask[] > tasks_movePriority( String timelineId,
                                                       String listId,
                                                       String taskSeriesId,
                                                       String taskId,
                                                       boolean up ) throws RtmServiceException
   {
      checkNotNullOrEmpty( listId );
      checkNotNullOrEmpty( taskSeriesId );
      checkNotNullOrEmpty( taskId );
      
      final Param timelineParam = checkAndCreateTimelineParam( timelineId );
      
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      final RtmResponse< RtmTask[] > response = rtmConnection.executeMethod( RtmTask[].class,
                                                                             "rtm.tasks.movePriority",
                                                                             timelineParam,
                                                                             new Param( "list_id",
                                                                                        listId ),
                                                                             new Param( "taskseries_id",
                                                                                        taskSeriesId ),
                                                                             new Param( "task_id",
                                                                                        taskId ),
                                                                             new Param( "direction",
                                                                                        up
                                                                                          ? "up"
                                                                                          : "down" ) );
      return response;
   }
   
   
   
   @Override
   public RtmResponse< RtmTask[] > tasks_moveTo( String timelineId,
                                                 String fromListId,
                                                 String toListId,
                                                 String taskSeriesId,
                                                 String taskId ) throws RtmServiceException
   {
      checkNotNullOrEmpty( fromListId );
      checkNotNullOrEmpty( toListId );
      checkNotNullOrEmpty( taskSeriesId );
      checkNotNullOrEmpty( taskId );
      
      final Param timelineParam = checkAndCreateTimelineParam( timelineId );
      
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      final RtmResponse< RtmTask[] > response = rtmConnection.executeMethod( RtmTask[].class,
                                                                             "rtm.tasks.moveTo",
                                                                             timelineParam,
                                                                             new Param( "from_list_id",
                                                                                        fromListId ),
                                                                             new Param( "to_list_id",
                                                                                        toListId ),
                                                                             new Param( "taskseries_id",
                                                                                        taskSeriesId ),
                                                                             new Param( "task_id",
                                                                                        taskId ) );
      return response;
   }
   
   
   
   @Override
   public RtmResponse< RtmTask[] > tasks_postpone( String timelineId,
                                                   String listId,
                                                   String taskSeriesId,
                                                   String taskId ) throws RtmServiceException
   {
      checkNotNullOrEmpty( listId );
      checkNotNullOrEmpty( taskSeriesId );
      checkNotNullOrEmpty( taskId );
      
      final Param timelineParam = checkAndCreateTimelineParam( timelineId );
      
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      final RtmResponse< RtmTask[] > response = rtmConnection.executeMethod( RtmTask[].class,
                                                                             "rtm.tasks.postpone",
                                                                             timelineParam,
                                                                             new Param( "list_id",
                                                                                        listId ),
                                                                             new Param( "taskseries_id",
                                                                                        taskSeriesId ),
                                                                             new Param( "task_id",
                                                                                        taskId ) );
      return response;
   }
   
   
   
   @Override
   public RtmResponse< RtmTask[] > tasks_setDueDate( String timelineId,
                                                     String listId,
                                                     String taskSeriesId,
                                                     String taskId,
                                                     long dueMillisUtc,
                                                     boolean hasTime ) throws RtmServiceException
   {
      checkNotNullOrEmpty( listId );
      checkNotNullOrEmpty( taskSeriesId );
      checkNotNullOrEmpty( taskId );
      
      final Param timelineParam = checkAndCreateTimelineParam( timelineId );
      
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      
      final RtmResponse< RtmTask[] > response;
      if ( dueMillisUtc == RtmConstants.NO_TIME )
      {
         response = rtmConnection.executeMethod( RtmTask[].class,
                                                 "rtm.tasks.setDueDate",
                                                 timelineParam,
                                                 new Param( "list_id", listId ),
                                                 new Param( "taskseries_id",
                                                            taskSeriesId ),
                                                 new Param( "task_id", taskId ) );
      }
      else
      {
         response = rtmConnection.executeMethod( RtmTask[].class,
                                                 "rtm.tasks.setDueDate",
                                                 timelineParam,
                                                 new Param( "list_id", listId ),
                                                 new Param( "taskseries_id",
                                                            taskSeriesId ),
                                                 new Param( "task_id", taskId ),
                                                 new Param( "due", dueMillisUtc ),
                                                 new Param( "has_due_time",
                                                            hasTime ? "1" : "0" ) );
      }
      
      return response;
   }
   
   
   
   @Override
   public RtmResponse< RtmTask[] > tasks_setEstimate( String timelineId,
                                                      String listId,
                                                      String taskSeriesId,
                                                      String taskId,
                                                      String estimate ) throws RtmServiceException
   {
      checkNotNullOrEmpty( listId );
      checkNotNullOrEmpty( taskSeriesId );
      checkNotNullOrEmpty( taskId );
      
      final Param timelineParam = checkAndCreateTimelineParam( timelineId );
      
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      
      final RtmResponse< RtmTask[] > response;
      if ( Strings.isNullOrEmpty( estimate ) )
      {
         response = rtmConnection.executeMethod( RtmTask[].class,
                                                 "rtm.tasks.setEstimate",
                                                 timelineParam,
                                                 new Param( "list_id", listId ),
                                                 new Param( "taskseries_id",
                                                            taskSeriesId ),
                                                 new Param( "task_id", taskId ) );
      }
      else
      {
         response = rtmConnection.executeMethod( RtmTask[].class,
                                                 "rtm.tasks.setEstimate",
                                                 timelineParam,
                                                 new Param( "list_id", listId ),
                                                 new Param( "taskseries_id",
                                                            taskSeriesId ),
                                                 new Param( "task_id", taskId ),
                                                 new Param( "estimate",
                                                            estimate ) );
      }
      
      return response;
   }
   
   
   
   @Override
   public RtmResponse< RtmTask[] > tasks_setName( String timelineId,
                                                  String listId,
                                                  String taskSeriesId,
                                                  String taskId,
                                                  String newName ) throws RtmServiceException
   {
      checkNotNullOrEmpty( listId );
      checkNotNullOrEmpty( taskSeriesId );
      checkNotNullOrEmpty( taskId );
      checkNotNullOrEmpty( newName );
      
      final Param timelineParam = checkAndCreateTimelineParam( timelineId );
      
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      final RtmResponse< RtmTask[] > response = rtmConnection.executeMethod( RtmTask[].class,
                                                                             "rtm.tasks.setName",
                                                                             timelineParam,
                                                                             new Param( "list_id",
                                                                                        listId ),
                                                                             new Param( "taskseries_id",
                                                                                        taskSeriesId ),
                                                                             new Param( "task_id",
                                                                                        taskId ),
                                                                             new Param( "name",
                                                                                        taskId ) );
      return response;
   }
   
   
   
   @Override
   public RtmResponse< RtmTask[] > tasks_setPriority( String timelineId,
                                                      String listId,
                                                      String taskSeriesId,
                                                      String taskId,
                                                      Priority priority ) throws RtmServiceException
   {
      checkNotNullOrEmpty( listId );
      checkNotNullOrEmpty( taskSeriesId );
      checkNotNullOrEmpty( taskId );
      
      final Param timelineParam = checkAndCreateTimelineParam( timelineId );
      
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      final RtmResponse< RtmTask[] > response = rtmConnection.executeMethod( RtmTask[].class,
                                                                             "rtm.tasks.setName",
                                                                             timelineParam,
                                                                             new Param( "list_id",
                                                                                        listId ),
                                                                             new Param( "taskseries_id",
                                                                                        taskSeriesId ),
                                                                             new Param( "task_id",
                                                                                        taskId ),
                                                                             new Param( "priority",
                                                                                        priority.toString() ) );
      return response;
   }
   
   
   
   @Override
   public RtmResponse< RtmTask[] > tasks_setRecurrence( String timelineId,
                                                        String listId,
                                                        String taskSeriesId,
                                                        String taskId,
                                                        String repeat ) throws RtmServiceException
   {
      checkNotNullOrEmpty( listId );
      checkNotNullOrEmpty( taskSeriesId );
      checkNotNullOrEmpty( taskId );
      
      final Param timelineParam = checkAndCreateTimelineParam( timelineId );
      
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      
      final RtmResponse< RtmTask[] > response;
      if ( Strings.isNullOrEmpty( repeat ) )
      {
         response = rtmConnection.executeMethod( RtmTask[].class,
                                                 "rtm.tasks.setRecurrence",
                                                 timelineParam,
                                                 new Param( "list_id", listId ),
                                                 new Param( "taskseries_id",
                                                            taskSeriesId ),
                                                 new Param( "task_id", taskId ) );
      }
      else
      {
         response = rtmConnection.executeMethod( RtmTask[].class,
                                                 "rtm.tasks.setRecurrence",
                                                 timelineParam,
                                                 new Param( "list_id", listId ),
                                                 new Param( "taskseries_id",
                                                            taskSeriesId ),
                                                 new Param( "task_id", taskId ),
                                                 new Param( "repeat", repeat ) );
      }
      
      return response;
   }
   
   
   
   @Override
   public RtmResponse< RtmTask[] > tasks_setTags( String timelineId,
                                                  String listId,
                                                  String taskSeriesId,
                                                  String taskId,
                                                  Collection< String > tags ) throws RtmServiceException
   {
      checkNotNullOrEmpty( listId );
      checkNotNullOrEmpty( taskSeriesId );
      checkNotNullOrEmpty( taskId );
      
      final String joinedTags;
      if ( tags == null || tags.isEmpty() )
      {
         joinedTags = Strings.EMPTY_STRING;
      }
      else
      {
         joinedTags = Strings.join( ",", tags );
      }
      
      final Param timelineParam = checkAndCreateTimelineParam( timelineId );
      
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      final RtmResponse< RtmTask[] > response = rtmConnection.executeMethod( RtmTask[].class,
                                                                             "rtm.tasks.setTags",
                                                                             timelineParam,
                                                                             new Param( "list_id",
                                                                                        listId ),
                                                                             new Param( "taskseries_id",
                                                                                        taskSeriesId ),
                                                                             new Param( "task_id",
                                                                                        taskId ),
                                                                             new Param( "tags",
                                                                                        joinedTags ) );
      return response;
   }
   
   
   
   @Override
   public RtmResponse< RtmTask[] > tasks_setLocation( String timelineId,
                                                      String listId,
                                                      String taskSeriesId,
                                                      String taskId,
                                                      String locationId ) throws RtmServiceException
   {
      checkNotNullOrEmpty( listId );
      checkNotNullOrEmpty( taskSeriesId );
      checkNotNullOrEmpty( taskId );
      
      final Param timelineParam = checkAndCreateTimelineParam( timelineId );
      
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      
      final RtmResponse< RtmTask[] > response;
      if ( Strings.isNullOrEmpty( locationId ) )
      {
         response = rtmConnection.executeMethod( RtmTask[].class,
                                                 "rtm.tasks.setLocation",
                                                 timelineParam,
                                                 new Param( "list_id", listId ),
                                                 new Param( "taskseries_id",
                                                            taskSeriesId ),
                                                 new Param( "task_id", taskId ) );
      }
      else
      {
         response = rtmConnection.executeMethod( RtmTask[].class,
                                                 "rtm.tasks.setLocation",
                                                 timelineParam,
                                                 new Param( "list_id", listId ),
                                                 new Param( "taskseries_id",
                                                            taskSeriesId ),
                                                 new Param( "task_id", taskId ),
                                                 new Param( "location_id",
                                                            locationId ) );
      }
      
      return response;
   }
   
   
   
   @Override
   public RtmResponse< RtmTask[] > tasks_setURL( String timelineId,
                                                 String listId,
                                                 String taskSeriesId,
                                                 String taskId,
                                                 String url ) throws RtmServiceException
   {
      checkNotNullOrEmpty( listId );
      checkNotNullOrEmpty( taskSeriesId );
      checkNotNullOrEmpty( taskId );
      
      final Param timelineParam = checkAndCreateTimelineParam( timelineId );
      
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      
      final RtmResponse< RtmTask[] > response;
      if ( Strings.isNullOrEmpty( url ) )
      {
         response = rtmConnection.executeMethod( RtmTask[].class,
                                                 "rtm.tasks.setURL",
                                                 timelineParam,
                                                 new Param( "list_id", listId ),
                                                 new Param( "taskseries_id",
                                                            taskSeriesId ),
                                                 new Param( "task_id", taskId ) );
      }
      else
      {
         response = rtmConnection.executeMethod( RtmTask[].class,
                                                 "rtm.tasks.setURL",
                                                 timelineParam,
                                                 new Param( "list_id", listId ),
                                                 new Param( "taskseries_id",
                                                            taskSeriesId ),
                                                 new Param( "task_id", taskId ),
                                                 new Param( "url", url ) );
      }
      
      return response;
   }
   
   
   
   @Override
   public RtmResponse< RtmNote > tasks_notes_add( String timelineId,
                                                  String listId,
                                                  String taskSeriesId,
                                                  String taskId,
                                                  String title,
                                                  String text ) throws RtmServiceException
   {
      checkNotNullOrEmpty( listId );
      checkNotNullOrEmpty( taskSeriesId );
      checkNotNullOrEmpty( taskId );
      
      title = Strings.emptyIfNull( title );
      text = Strings.emptyIfNull( text );
      
      final Param timelineParam = checkAndCreateTimelineParam( timelineId );
      
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      final RtmResponse< RtmNote > response = rtmConnection.executeMethod( RtmNote.class,
                                                                           "rtm.tasks.notes.add",
                                                                           timelineParam,
                                                                           new Param( "list_id",
                                                                                      listId ),
                                                                           new Param( "taskseries_id",
                                                                                      taskSeriesId ),
                                                                           new Param( "task_id",
                                                                                      taskId ),
                                                                           new Param( "note_title",
                                                                                      title ),
                                                                           new Param( "note_text",
                                                                                      text ) );
      return response;
   }
   
   
   
   @Override
   public RtmResponse< Void > tasks_notes_delete( String timelineId,
                                                  String taskSeriesId,
                                                  String noteId ) throws RtmServiceException
   {
      checkNotNullOrEmpty( taskSeriesId );
      checkNotNullOrEmpty( noteId );
      
      final Param timelineParam = checkAndCreateTimelineParam( timelineId );
      
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      final RtmResponse< Void > response = rtmConnection.executeMethod( Void.class,
                                                                        "rtm.tasks.notes.delete",
                                                                        timelineParam,
                                                                        new Param( "taskseries_id",
                                                                                   taskSeriesId ),
                                                                        new Param( "note_id",
                                                                                   noteId ) );
      return response;
   }
   
   
   
   @Override
   public RtmResponse< RtmNote > tasks_notes_edit( String timelineId,
                                                   String taskSeriesId,
                                                   String noteId,
                                                   String title,
                                                   String text ) throws RtmServiceException
   {
      checkNotNullOrEmpty( taskSeriesId );
      checkNotNullOrEmpty( noteId );
      
      title = Strings.emptyIfNull( title );
      text = Strings.emptyIfNull( text );
      
      final Param timelineParam = checkAndCreateTimelineParam( timelineId );
      
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      final RtmResponse< RtmNote > response = rtmConnection.executeMethod( RtmNote.class,
                                                                           "rtm.tasks.notes.edit",
                                                                           timelineParam,
                                                                           new Param( "taskseries_id",
                                                                                      taskSeriesId ),
                                                                           new Param( "note_id",
                                                                                      noteId ),
                                                                           new Param( "note_title",
                                                                                      title ),
                                                                           new Param( "note_text",
                                                                                      text ) );
      return response;
   }
   
   
   
   @Override
   public RtmTimeline timelines_create() throws RtmServiceException
   {
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      final RtmResponse< RtmTimeline > response = rtmConnection.executeMethod( RtmTimeline.class,
                                                                               "rtm.timelines.create" );
      return response.getElement();
   }
   
   
   
   @Override
   public void transactions_undo( String timelineId, String transactionId ) throws RtmServiceException
   {
      checkNotNullOrEmpty( transactionId );
      
      final Param timelineParam = checkAndCreateTimelineParam( timelineId );
      
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      rtmConnection.executeMethod( Void.class,
                                   "rtm.transactions.undo",
                                   timelineParam,
                                   new Param( "transaction_id", transactionId ) );
   }
   
   
   
   private static Param checkAndCreateTimelineParam( String timelineId )
   {
      checkNotNullOrEmpty( timelineId );
      return new Param( "timeline", timelineId );
   }
   
   
   
   private static void checkNotNullOrEmpty( String value )
   {
      if ( Strings.isNullOrEmpty( value ) )
      {
         throw new IllegalArgumentException( "Expected not null and not empty value" );
      }
   }
}
