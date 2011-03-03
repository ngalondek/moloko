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

import org.w3c.dom.Element;

import com.mdt.rtm.Service;
import com.mdt.rtm.ServiceException;
import com.mdt.rtm.TimeLineMethod;
import com.mdt.rtm.TimeLineResult;
import com.mdt.rtm.Service.MethodCallType;
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
   


   public TimeLineMethod< RtmTaskSeries > tasks_complete( final String listId,
                                                          final String taskSeriesId,
                                                          final String taskId )
   {
      return new TimeLineMethod< RtmTaskSeries >()
      {
         @Override
         public TimeLineResult< RtmTaskSeries > call( MethodCallType callType ) throws ServiceException
         {
            return service.tasks_complete( id,
                                           listId,
                                           taskSeriesId,
                                           taskId,
                                           callType );
         }
      };
   }
   


   public TimeLineMethod< RtmTaskSeries > tasks_setName( final String listId,
                                                         final String taskSeriesId,
                                                         final String taskId,
                                                         final String newName )
   {
      return new TimeLineMethod< RtmTaskSeries >()
      {
         @Override
         public TimeLineResult< RtmTaskSeries > call( MethodCallType callType ) throws ServiceException
         {
            return service.tasks_setName( id,
                                          listId,
                                          taskSeriesId,
                                          taskId,
                                          newName,
                                          callType );
         }
      };
   }
   


   public TimeLineMethod< RtmTaskSeries > tasks_movePriority( final String listId,
                                                              final String taskSeriesId,
                                                              final String taskId,
                                                              final boolean up )
   {
      return new TimeLineMethod< RtmTaskSeries >()
      {
         @Override
         public TimeLineResult< RtmTaskSeries > call( MethodCallType callType ) throws ServiceException
         {
            return service.tasks_movePriority( id,
                                               listId,
                                               taskSeriesId,
                                               taskId,
                                               up,
                                               callType );
         }
      };
   }
   


   public TimeLineMethod< RtmTaskSeries > tasks_setPriority( final String listId,
                                                             final String taskSeriesId,
                                                             final String taskId,
                                                             final Priority priority )
   {
      return new TimeLineMethod< RtmTaskSeries >()
      {
         @Override
         public TimeLineResult< RtmTaskSeries > call( MethodCallType callType ) throws ServiceException
         {
            return service.tasks_setPriority( id,
                                              listId,
                                              taskSeriesId,
                                              taskId,
                                              priority,
                                              callType );
         }
      };
   }
   


   public TimeLineMethod< RtmTaskSeries > tasks_moveTo( final String fromListId,
                                                        final String toListId,
                                                        final String taskSeriesId,
                                                        final String taskId )
   {
      return new TimeLineMethod< RtmTaskSeries >()
      {
         @Override
         public TimeLineResult< RtmTaskSeries > call( MethodCallType callType ) throws ServiceException
         {
            return service.tasks_moveTo( id,
                                         fromListId,
                                         toListId,
                                         taskSeriesId,
                                         taskId,
                                         callType );
         }
      };
   }
   


   public TimeLineMethod< RtmTaskSeries > tasks_setLocation( final String listId,
                                                             final String taskSeriesId,
                                                             final String taskId,
                                                             final String locationId )
   {
      return new TimeLineMethod< RtmTaskSeries >()
      {
         @Override
         public TimeLineResult< RtmTaskSeries > call( MethodCallType callType ) throws ServiceException
         {
            return service.tasks_setLocation( id,
                                              listId,
                                              taskSeriesId,
                                              taskId,
                                              locationId,
                                              callType );
         }
      };
   }
   


   public TimeLineMethod< RtmTaskSeries > tasks_setURL( final String listId,
                                                        final String taskSeriesId,
                                                        final String taskId,
                                                        final String url )
   {
      return new TimeLineMethod< RtmTaskSeries >()
      {
         @Override
         public TimeLineResult< RtmTaskSeries > call( MethodCallType callType ) throws ServiceException
         {
            return service.tasks_setURL( id,
                                         listId,
                                         taskSeriesId,
                                         taskId,
                                         url,
                                         callType );
         }
      };
   }
}
