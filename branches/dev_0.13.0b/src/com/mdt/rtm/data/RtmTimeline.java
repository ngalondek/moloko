/*
 * Copyright 2007, MetaDimensional Technologies Inc.
 * 
 * 
 * This file is part of the RememberTheMilk Java API.
 * 
 * The RememberTheMilk Java API is free software; you can redistribute it
 * and/or modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 * 
 * The RememberTheMilk Java API is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.mdt.rtm.data;

import java.util.Date;
import java.util.List;

import org.w3c.dom.Element;

import com.mdt.rtm.Service;
import com.mdt.rtm.ServiceException;
import com.mdt.rtm.TimeLineMethod;
import com.mdt.rtm.TimeLineResult;
import com.mdt.rtm.data.RtmTask.Priority;


public class RtmTimeline
{
   
   private final String id;
   
   private final Service service;
   
   

   public RtmTimeline( String id, Service service )
   {
      this.id = id;
      this.service = service;
   }
   


   public RtmTimeline( Element elt, Service service )
   {
      id = RtmData.text( elt );
      this.service = service;
   }
   


   public String getId()
   {
      return id;
   }
   


   @Override
   public String toString()
   {
      return "<id=" + id + ">";
   }
   


   public TimeLineMethod< RtmTaskList > tasks_complete( final String listId,
                                                        final String taskSeriesId,
                                                        final String taskId )
   {
      return new TimeLineMethod< RtmTaskList >()
      {
         @Override
         public TimeLineResult< RtmTaskList > call() throws ServiceException
         {
            return service.tasks_complete( id, listId, taskSeriesId, taskId );
         }
      };
   }
   


   public TimeLineMethod< RtmTaskList > tasks_uncomplete( final String listId,
                                                          final String taskSeriesId,
                                                          final String taskId )
   {
      return new TimeLineMethod< RtmTaskList >()
      {
         @Override
         public TimeLineResult< RtmTaskList > call() throws ServiceException
         {
            return service.tasks_uncomplete( id, listId, taskSeriesId, taskId );
         }
      };
   }
   


   public TimeLineMethod< RtmTaskList > tasks_setEstimate( final String listId,
                                                           final String taskSeriesId,
                                                           final String taskId,
                                                           final String estimate )
   {
      return new TimeLineMethod< RtmTaskList >()
      {
         @Override
         public TimeLineResult< RtmTaskList > call() throws ServiceException
         {
            return service.tasks_setName( id,
                                          listId,
                                          taskSeriesId,
                                          taskId,
                                          estimate );
         }
      };
   }
   


   public TimeLineMethod< RtmTaskList > tasks_setName( final String listId,
                                                       final String taskSeriesId,
                                                       final String taskId,
                                                       final String newName )
   {
      return new TimeLineMethod< RtmTaskList >()
      {
         @Override
         public TimeLineResult< RtmTaskList > call() throws ServiceException
         {
            return service.tasks_setName( id,
                                          listId,
                                          taskSeriesId,
                                          taskId,
                                          newName );
         }
      };
   }
   


   public TimeLineMethod< RtmTaskList > tasks_movePriority( final String listId,
                                                            final String taskSeriesId,
                                                            final String taskId,
                                                            final boolean up )
   {
      return new TimeLineMethod< RtmTaskList >()
      {
         @Override
         public TimeLineResult< RtmTaskList > call() throws ServiceException
         {
            return service.tasks_movePriority( id,
                                               listId,
                                               taskSeriesId,
                                               taskId,
                                               up );
         }
      };
   }
   


   public TimeLineMethod< RtmTaskList > tasks_setPriority( final String listId,
                                                           final String taskSeriesId,
                                                           final String taskId,
                                                           final Priority priority )
   {
      return new TimeLineMethod< RtmTaskList >()
      {
         @Override
         public TimeLineResult< RtmTaskList > call() throws ServiceException
         {
            return service.tasks_setPriority( id,
                                              listId,
                                              taskSeriesId,
                                              taskId,
                                              priority );
         }
      };
   }
   


   public TimeLineMethod< RtmTaskList > tasks_setRecurrence( final String listId,
                                                             final String taskSeriesId,
                                                             final String taskId,
                                                             final String repeat )
   {
      return new TimeLineMethod< RtmTaskList >()
      {
         @Override
         public TimeLineResult< RtmTaskList > call() throws ServiceException
         {
            return service.tasks_setRecurrence( id,
                                                listId,
                                                taskSeriesId,
                                                taskId,
                                                repeat );
         }
      };
   }
   


   public TimeLineMethod< RtmTaskList > tasks_setTags( final String listId,
                                                       final String taskSeriesId,
                                                       final String taskId,
                                                       final List< String > tags )
   {
      return new TimeLineMethod< RtmTaskList >()
      {
         @Override
         public TimeLineResult< RtmTaskList > call() throws ServiceException
         {
            return service.tasks_setTags( id,
                                          listId,
                                          taskSeriesId,
                                          taskId,
                                          tags );
         }
      };
   }
   


   public TimeLineMethod< RtmTaskList > tasks_moveTo( final String fromListId,
                                                      final String toListId,
                                                      final String taskSeriesId,
                                                      final String taskId )
   {
      return new TimeLineMethod< RtmTaskList >()
      {
         @Override
         public TimeLineResult< RtmTaskList > call() throws ServiceException
         {
            return service.tasks_moveTo( id,
                                         fromListId,
                                         toListId,
                                         taskSeriesId,
                                         taskId );
         }
      };
   }
   


   public TimeLineMethod< RtmTaskList > tasks_postpone( final String listId,
                                                        final String taskSeriesId,
                                                        final String taskId )
   {
      return new TimeLineMethod< RtmTaskList >()
      {
         @Override
         public TimeLineResult< RtmTaskList > call() throws ServiceException
         {
            return service.tasks_postpone( id, listId, taskSeriesId, taskId );
         }
      };
   }
   


   public TimeLineMethod< RtmTaskList > tasks_setDueDate( final String listId,
                                                          final String taskSeriesId,
                                                          final String taskId,
                                                          final Date dueUtc,
                                                          final boolean hasTime )
   {
      return new TimeLineMethod< RtmTaskList >()
      {
         @Override
         public TimeLineResult< RtmTaskList > call() throws ServiceException
         {
            return service.tasks_setDueDate( id,
                                             listId,
                                             taskSeriesId,
                                             taskId,
                                             dueUtc,
                                             hasTime );
         }
      };
   }
   


   public TimeLineMethod< RtmTaskList > tasks_setLocation( final String listId,
                                                           final String taskSeriesId,
                                                           final String taskId,
                                                           final String locationId )
   {
      return new TimeLineMethod< RtmTaskList >()
      {
         @Override
         public TimeLineResult< RtmTaskList > call() throws ServiceException
         {
            return service.tasks_setLocation( id,
                                              listId,
                                              taskSeriesId,
                                              taskId,
                                              locationId );
         }
      };
   }
   


   public TimeLineMethod< RtmTaskList > tasks_setURL( final String listId,
                                                      final String taskSeriesId,
                                                      final String taskId,
                                                      final String url )
   {
      return new TimeLineMethod< RtmTaskList >()
      {
         @Override
         public TimeLineResult< RtmTaskList > call() throws ServiceException
         {
            return service.tasks_setURL( id, listId, taskSeriesId, taskId, url );
         }
      };
   }
}
