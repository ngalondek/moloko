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
package com.mdt.rtm;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.text.TextUtils;
import android.util.Pair;

import com.mdt.rtm.data.RtmAuth;
import com.mdt.rtm.data.RtmData;
import com.mdt.rtm.data.RtmFrob;
import com.mdt.rtm.data.RtmList;
import com.mdt.rtm.data.RtmLists;
import com.mdt.rtm.data.RtmLocation;
import com.mdt.rtm.data.RtmTask;
import com.mdt.rtm.data.RtmTask.Priority;
import com.mdt.rtm.data.RtmTaskList;
import com.mdt.rtm.data.RtmTaskNote;
import com.mdt.rtm.data.RtmTaskSeries;
import com.mdt.rtm.data.RtmTasks;
import com.mdt.rtm.data.RtmTimeline;

import dev.drsoran.moloko.connection.DefaultRtmConnectionFactory;
import dev.drsoran.moloko.connection.IRtmConnection;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.rtm.RtmContacts;
import dev.drsoran.rtm.RtmSettings;


/**
 * A major part of the RTM API implementation is here.
 * 
 * @author Will Ross Jun 21, 2007
 * @author Edouard Mercier, since 2008.04.15
 */
public class ServiceImpl implements Service
{
   public final static String SERVER_HOST_NAME = "api.rememberthemilk.com";
   
   public final static int SERVER_PORT_NUMBER_HTTP = 80;
   
   public final static int SERVER_PORT_NUMBER_HTTPS = 443;
   
   public final static String REST_SERVICE_URL_POSTFIX = "/services/rest/";
   
   private final ApplicationInfo applicationInfo;
   
   private final Invoker invoker;
   
   private final Prefs prefs;
   
   private String currentAuthToken;
   
   RtmFrob tempFrob;
   
   
   
   public static ServiceImpl getInstance( boolean useHttps,
                                          ApplicationInfo applicationInfo ) throws ServiceInternalException
   {
      final ServiceImpl serviceImpl = new ServiceImpl( applicationInfo,
                                                       !useHttps );
      
      return serviceImpl;
   }
   
   
   
   protected ServiceImpl( ApplicationInfo applicationInfo, boolean useHttp )
      throws ServiceInternalException
   {
      final String scheme = useHttp ? "http" : "https";
      final int port = useHttp ? SERVER_PORT_NUMBER_HTTP
                              : SERVER_PORT_NUMBER_HTTPS;
      
      final IRtmConnection connection = new DefaultRtmConnectionFactory().createRtmConnection( scheme,
                                                                                               SERVER_HOST_NAME,
                                                                                               port );
      
      invoker = new Invoker( connection,
                             REST_SERVICE_URL_POSTFIX,
                             applicationInfo );
      
      this.applicationInfo = applicationInfo;
      prefs = new Prefs();
      
      if ( applicationInfo.getAuthToken() != null )
      {
         currentAuthToken = applicationInfo.getAuthToken();
      }
      else
      {
         currentAuthToken = prefs.getAuthToken();
      }
   }
   
   
   
   @Override
   public void shutdown()
   {
      if ( invoker != null )
         invoker.shutdown();
   }
   
   
   
   @Override
   public boolean isServiceAuthorized() throws ServiceException
   {
      if ( currentAuthToken == null )
         return false;
      
      try
      {
         /* RtmAuth auth = */auth_checkToken( currentAuthToken );
         return true;
      }
      catch ( ServiceException e )
      {
         if ( e.getResponseCode() != 98 )
         {
            throw e;
         }
         else
         {
            // Bad token.
            currentAuthToken = null;
            return false;
         }
      }
   }
   
   
   
   @Override
   public String beginAuthorization( RtmAuth.Perms permissions ) throws ServiceException
   {
      // Instructions from the "User authentication for desktop applications"
      // section at
      // http://www.rememberthemilk.com/services/api/authentication.rtm
      tempFrob = auth_getFrob();
      return beginAuthorization( tempFrob, permissions );
   }
   
   
   
   @Override
   public String beginAuthorization( RtmFrob frob, RtmAuth.Perms permissions ) throws ServiceException
   {
      String authBaseUrl = "http://" + SERVER_HOST_NAME + "/services/auth/";
      Param[] params = new Param[]
      { new Param( "api_key", applicationInfo.getApiKey() ),
       new Param( "perms", permissions.toString() ),
       new Param( "frob", frob.getValue() ) };
      
      Param sig = new Param( "api_sig", invoker.calcApiSig( params ) );
      StringBuilder authUrl = new StringBuilder( authBaseUrl );
      authUrl.append( "?" );
      for ( Param param : params )
      {
         authUrl.append( param.getName() )
                .append( "=" )
                .append( param.getValue() )
                .append( "&" );
      }
      authUrl.append( sig.getName() ).append( "=" ).append( sig.getValue() );
      return authUrl.toString();
   }
   
   
   
   @Override
   public String completeAuthorization() throws ServiceException
   {
      return completeAuthorization( tempFrob );
   }
   
   
   
   @Override
   public String completeAuthorization( RtmFrob frob ) throws ServiceException
   {
      currentAuthToken = auth_getToken( frob.getValue() );
      prefs.setAuthToken( currentAuthToken );
      return currentAuthToken;
   }
   
   
   
   @Override
   public RtmAuth auth_checkToken( String authToken ) throws ServiceException
   {
      Element response = invoker.invoke( new Param( "method",
                                                    "rtm.auth.checkToken" ),
                                         new Param( "auth_token", authToken ),
                                         new Param( "api_key",
                                                    applicationInfo.getApiKey() ) );
      return new RtmAuth( response );
   }
   
   
   
   @Override
   public RtmFrob auth_getFrob() throws ServiceException
   {
      return new RtmFrob( invoker.invoke( new Param( "method",
                                                     "rtm.auth.getFrob" ),
                                          new Param( "api_key",
                                                     applicationInfo.getApiKey() ) ) );
   }
   
   
   
   @Override
   public String auth_getToken( String frob ) throws ServiceException
   {
      Element response = invoker.invoke( new Param( "method",
                                                    "rtm.auth.getToken" ),
                                         new Param( "frob", frob ),
                                         new Param( "api_key",
                                                    applicationInfo.getApiKey() ) );
      return new RtmAuth( response ).getToken();
   }
   
   
   
   @Override
   public void contacts_add()
   {
      throw new UnsupportedOperationException( "Not supported yet." );
   }
   
   
   
   @Override
   public void contacts_delete()
   {
      throw new UnsupportedOperationException( "Not supported yet." );
   }
   
   
   
   @Override
   public RtmContacts contacts_getList() throws ServiceException
   {
      final Element response = invoker.invoke( new Param( "method",
                                                          "rtm.contacts.getList" ),
                                               new Param( "auth_token",
                                                          currentAuthToken ),
                                               new Param( "api_key",
                                                          applicationInfo.getApiKey() ) );
      return new RtmContacts( response );
   }
   
   
   
   @Override
   public void groups_add()
   {
      throw new UnsupportedOperationException( "Not supported yet." );
   }
   
   
   
   @Override
   public void groups_addContact()
   {
      throw new UnsupportedOperationException( "Not supported yet." );
   }
   
   
   
   @Override
   public void groups_delete()
   {
      throw new UnsupportedOperationException( "Not supported yet." );
   }
   
   
   
   @Override
   public void groups_getList()
   {
      throw new UnsupportedOperationException( "Not supported yet." );
   }
   
   
   
   @Override
   public void groups_removeContact()
   {
      throw new UnsupportedOperationException( "Not supported yet." );
   }
   
   
   
   @Override
   public TimeLineResult< RtmList > lists_add( String timelineId,
                                               String listName,
                                               String smartFilter ) throws ServiceException
   {
      final Element elt;
      
      if ( TextUtils.isEmpty( smartFilter ) )
         elt = invoker.invoke( new Param( "method", "rtm.lists.add" ),
                               new Param( "auth_token", currentAuthToken ),
                               new Param( "api_key",
                                          applicationInfo.getApiKey() ),
                               new Param( "timeline", timelineId ),
                               new Param( "name", listName ) );
      else
         elt = invoker.invoke( new Param( "method", "rtm.lists.add" ),
                               new Param( "auth_token", currentAuthToken ),
                               new Param( "api_key",
                                          applicationInfo.getApiKey() ),
                               new Param( "timeline", timelineId ),
                               new Param( "name", listName ),
                               new Param( "filter", smartFilter ) );
      
      return newListResult( timelineId, elt );
   }
   
   
   
   @Override
   public void lists_archive()
   {
      throw new UnsupportedOperationException( "Not supported yet." );
   }
   
   
   
   @Override
   public TimeLineResult< RtmList > lists_delete( String timelineId,
                                                  String listId ) throws ServiceException
   {
      final Element elt = invoker.invoke( new Param( "method",
                                                     "rtm.lists.delete" ),
                                          new Param( "auth_token",
                                                     currentAuthToken ),
                                          new Param( "api_key",
                                                     applicationInfo.getApiKey() ),
                                          new Param( "timeline", timelineId ),
                                          new Param( "list_id", listId ) );
      return newListResult( timelineId, elt );
   }
   
   
   
   @Override
   public RtmLists lists_getList() throws ServiceException
   {
      Element response = invoker.invoke( new Param( "method",
                                                    "rtm.lists.getList" ),
                                         new Param( "auth_token",
                                                    currentAuthToken ),
                                         new Param( "api_key",
                                                    applicationInfo.getApiKey() ) );
      return new RtmLists( response );
   }
   
   
   
   @Override
   public RtmList lists_getList( String listName ) throws ServiceException
   {
      RtmLists fullList = lists_getList();
      for ( Pair< String, RtmList > entry : fullList.getLists() )
      {
         if ( entry.second.getName().equals( listName ) )
         {
            return entry.second;
         }
      }
      return null;
   }
   
   
   
   @Override
   public void lists_setDefaultList()
   {
      throw new UnsupportedOperationException( "Not supported yet." );
   }
   
   
   
   @Override
   public TimeLineResult< RtmList > lists_setName( String timelineId,
                                                   String listId,
                                                   String newName ) throws ServiceException
   {
      final Element elt = invoker.invoke( new Param( "method",
                                                     "rtm.lists.setName" ),
                                          new Param( "timeline", timelineId ),
                                          new Param( "list_id", listId ),
                                          new Param( "name", newName ),
                                          new Param( "auth_token",
                                                     currentAuthToken ),
                                          new Param( "api_key",
                                                     applicationInfo.getApiKey() ) );
      return newListResult( timelineId, elt );
   }
   
   
   
   @Override
   public void lists_unarchive()
   {
      throw new UnsupportedOperationException( "Not supported yet." );
   }
   
   
   
   @Override
   public void reflection_getMethodInfo()
   {
      throw new UnsupportedOperationException( "Not supported yet." );
   }
   
   
   
   @Override
   public void reflection_getMethods()
   {
      throw new UnsupportedOperationException( "Not supported yet." );
   }
   
   
   
   @Override
   public RtmSettings settings_getList() throws ServiceException
   {
      Element response = invoker.invoke( new Param( "method",
                                                    "rtm.settings.getList" ),
                                         new Param( "auth_token",
                                                    currentAuthToken ),
                                         new Param( "api_key",
                                                    applicationInfo.getApiKey() ) );
      return new RtmSettings( response );
   }
   
   
   
   @Override
   public TimeLineResult< RtmTaskList > tasks_add( String timelineId,
                                                   String listId,
                                                   String name ) throws ServiceException
   {
      final Element elt;
      
      if ( listId != null )
         elt = invoker.invoke( new Param( "method", "rtm.tasks.add" ),
                               new Param( "timeline", timelineId ),
                               new Param( "list_id", listId ),
                               new Param( "name", name ),
                               new Param( "auth_token", currentAuthToken ),
                               new Param( "api_key",
                                          applicationInfo.getApiKey() ) );
      else
         elt = invoker.invoke( new Param( "method", "rtm.tasks.add" ),
                               new Param( "timeline", timelineId ),
                               new Param( "name", name ),
                               new Param( "auth_token", currentAuthToken ),
                               new Param( "api_key",
                                          applicationInfo.getApiKey() ) );
      
      return newTaskResult( timelineId, elt );
   }
   
   
   
   @Override
   public void tasks_addTags()
   {
      throw new UnsupportedOperationException( "Not supported yet." );
   }
   
   
   
   @Override
   public TimeLineResult< RtmTaskList > tasks_complete( String timelineId,
                                                        String listId,
                                                        String taskSeriesId,
                                                        String taskId ) throws ServiceException
   {
      final Element elt = invoker.invoke( new Param( "method",
                                                     "rtm.tasks.complete" ),
                                          new Param( "timeline", timelineId ),
                                          new Param( "list_id", listId ),
                                          new Param( "taskseries_id",
                                                     taskSeriesId ),
                                          new Param( "task_id", taskId ),
                                          new Param( "auth_token",
                                                     currentAuthToken ),
                                          new Param( "api_key",
                                                     applicationInfo.getApiKey() ) );
      
      return newTaskResult( timelineId, elt );
   }
   
   
   
   @Override
   public TimeLineResult< RtmTaskList > tasks_uncomplete( String timelineId,
                                                          String listId,
                                                          String taskSeriesId,
                                                          String taskId ) throws ServiceException
   {
      final Element elt = invoker.invoke( new Param( "method",
                                                     "rtm.tasks.uncomplete" ),
                                          new Param( "timeline", timelineId ),
                                          new Param( "list_id", listId ),
                                          new Param( "taskseries_id",
                                                     taskSeriesId ),
                                          new Param( "task_id", taskId ),
                                          new Param( "auth_token",
                                                     currentAuthToken ),
                                          new Param( "api_key",
                                                     applicationInfo.getApiKey() ) );
      return newTaskResult( timelineId, elt );
   }
   
   
   
   @Override
   public TimeLineResult< RtmTaskList > tasks_delete( String timelineId,
                                                      String listId,
                                                      String taskSeriesId,
                                                      String taskId ) throws ServiceException
   {
      final Element elt = invoker.invoke( new Param( "method",
                                                     "rtm.tasks.delete" ),
                                          new Param( "timeline", timelineId ),
                                          new Param( "list_id", listId ),
                                          new Param( "taskseries_id",
                                                     taskSeriesId ),
                                          new Param( "task_id", taskId ),
                                          new Param( "auth_token",
                                                     currentAuthToken ),
                                          new Param( "api_key",
                                                     applicationInfo.getApiKey() ) );
      return newTaskResult( timelineId, elt );
   }
   
   
   
   @Override
   public RtmTasks tasks_getList( String listId, String filter, Date lastSync ) throws ServiceException
   {
      Set< Param > params = new HashSet< Param >();
      params.add( new Param( "method", "rtm.tasks.getList" ) );
      if ( listId != null )
      {
         params.add( new Param( "list_id", listId ) );
      }
      if ( filter != null )
      {
         params.add( new Param( "filter", filter ) );
      }
      if ( lastSync != null )
      {
         params.add( new Param( "last_sync", lastSync ) );
      }
      params.add( new Param( "auth_token", currentAuthToken ) );
      params.add( new Param( "api_key", applicationInfo.getApiKey() ) );
      return new RtmTasks( invoker.invoke( params.toArray( new Param[ params.size() ] ) ) );
   }
   
   
   
   @Override
   public RtmTaskSeries tasks_getTask( String taskSeriesId,
                                       String taskName,
                                       String listId ) throws ServiceException
   {
      final Set< Param > params = new HashSet< Param >();
      params.add( new Param( "method", "rtm.tasks.getList" ) );
      params.add( new Param( "auth_token", currentAuthToken ) );
      params.add( new Param( "api_key", applicationInfo.getApiKey() ) );
      if ( taskName != null )
      {
         params.add( new Param( "filter", "name:\"" + taskName + "\"" ) );
      }
      if ( listId != null )
      {
         params.add( new Param( "list_id", listId ) );
      }
      final RtmTasks rtmTasks = new RtmTasks( invoker.invoke( params.toArray( new Param[ params.size() ] ) ) );
      return RtmTaskSeries.findTask( taskSeriesId, rtmTasks );
   }
   
   
   
   @Override
   public RtmTaskSeries tasks_getTask( String taskName ) throws ServiceException
   {
      return tasks_getTask( null, taskName, null );
   }
   
   
   
   @Override
   public TimeLineResult< RtmTaskList > tasks_movePriority( String timelineId,
                                                            String listId,
                                                            String taskSeriesId,
                                                            String taskId,
                                                            boolean up ) throws ServiceException
   {
      final Element elt = invoker.invoke( new Param( "method",
                                                     "rtm.tasks.movePriority" ),
                                          new Param( "timeline", timelineId ),
                                          new Param( "list_id", listId ),
                                          new Param( "taskseries_id",
                                                     taskSeriesId ),
                                          new Param( "task_id", taskId ),
                                          new Param( "direction", up ? "up"
                                                                    : "down" ),
                                          new Param( "auth_token",
                                                     currentAuthToken ),
                                          new Param( "api_key",
                                                     applicationInfo.getApiKey() ) );
      return newTaskResult( timelineId, elt );
   }
   
   
   
   @Override
   public TimeLineResult< RtmTaskList > tasks_moveTo( String timelineId,
                                                      String fromListId,
                                                      String toListId,
                                                      String taskSeriesId,
                                                      String taskId ) throws ServiceException
   {
      final Element elt = invoker.invoke( new Param( "method",
                                                     "rtm.tasks.moveTo" ),
                                          new Param( "timeline", timelineId ),
                                          new Param( "from_list_id", fromListId ),
                                          new Param( "to_list_id", toListId ),
                                          new Param( "taskseries_id",
                                                     taskSeriesId ),
                                          new Param( "task_id", taskId ),
                                          new Param( "auth_token",
                                                     currentAuthToken ),
                                          new Param( "api_key",
                                                     applicationInfo.getApiKey() ) );
      return newTaskResult( timelineId, elt );
   }
   
   
   
   @Override
   public TimeLineResult< RtmTaskList > tasks_postpone( String timelineId,
                                                        String listId,
                                                        String taskSeriesId,
                                                        String taskId ) throws ServiceException
   {
      final Element elt = invoker.invoke( new Param( "method",
                                                     "rtm.tasks.postpone" ),
                                          new Param( "timeline", timelineId ),
                                          new Param( "list_id", listId ),
                                          new Param( "taskseries_id",
                                                     taskSeriesId ),
                                          new Param( "task_id", taskId ),
                                          new Param( "auth_token",
                                                     currentAuthToken ),
                                          new Param( "api_key",
                                                     applicationInfo.getApiKey() ) );
      return newTaskResult( timelineId, elt );
   }
   
   
   
   @Override
   public void tasks_removeTags()
   {
      throw new UnsupportedOperationException( "Not supported yet." );
   }
   
   
   
   @Override
   public TimeLineResult< RtmTaskList > tasks_setDueDate( String timelineId,
                                                          String listId,
                                                          String taskSeriesId,
                                                          String taskId,
                                                          Date due,
                                                          boolean hasTime ) throws ServiceException
   {
      final boolean setDueDate = ( due != null );
      final Element elt;
      
      if ( setDueDate == true )
      {
         elt = invoker.invoke( new Param( "method", "rtm.tasks.setDueDate" ),
                               new Param( "timeline", timelineId ),
                               new Param( "list_id", listId ),
                               new Param( "taskseries_id", taskSeriesId ),
                               new Param( "task_id", taskId ),
                               new Param( "due", RtmData.formatDate( due ) ),
                               new Param( "has_due_time", hasTime ? "1" : "0" ),
                               new Param( "auth_token", currentAuthToken ),
                               new Param( "api_key",
                                          applicationInfo.getApiKey() ) );
      }
      else
      {
         elt = invoker.invoke( new Param( "method", "rtm.tasks.setDueDate" ),
                               new Param( "timeline", timelineId ),
                               new Param( "list_id", listId ),
                               new Param( "taskseries_id", taskSeriesId ),
                               new Param( "task_id", taskId ),
                               new Param( "auth_token", currentAuthToken ),
                               new Param( "api_key",
                                          applicationInfo.getApiKey() ) );
      }
      
      return newTaskResult( timelineId, elt );
   }
   
   
   
   @Override
   public TimeLineResult< RtmTaskList > tasks_setEstimate( String timelineId,
                                                           String listId,
                                                           String taskSeriesId,
                                                           String taskId,
                                                           String estimate ) throws ServiceException
   {
      final Element elt = invoker.invoke( new Param( "method",
                                                     "rtm.tasks.setEstimate" ),
                                          new Param( "timeline", timelineId ),
                                          new Param( "list_id", listId ),
                                          new Param( "taskseries_id",
                                                     taskSeriesId ),
                                          new Param( "task_id", taskId ),
                                          new Param( "estimate", estimate ),
                                          new Param( "auth_token",
                                                     currentAuthToken ),
                                          new Param( "api_key",
                                                     applicationInfo.getApiKey() ) );
      return newTaskResult( timelineId, elt );
   }
   
   
   
   @Override
   public TimeLineResult< RtmTaskList > tasks_setName( String timelineId,
                                                       String listId,
                                                       String taskSeriesId,
                                                       String taskId,
                                                       String newName ) throws ServiceException
   {
      final Element elt = invoker.invoke( new Param( "method",
                                                     "rtm.tasks.setName" ),
                                          new Param( "timeline", timelineId ),
                                          new Param( "list_id", listId ),
                                          new Param( "taskseries_id",
                                                     taskSeriesId ),
                                          new Param( "task_id", taskId ),
                                          new Param( "name", newName ),
                                          new Param( "auth_token",
                                                     currentAuthToken ),
                                          new Param( "api_key",
                                                     applicationInfo.getApiKey() ) );
      return newTaskResult( timelineId, elt );
   }
   
   
   
   @Override
   public TimeLineResult< RtmTaskList > tasks_setPriority( String timelineId,
                                                           String listId,
                                                           String taskSeriesId,
                                                           String taskId,
                                                           Priority priority ) throws ServiceException
   {
      final Element elt = invoker.invoke( new Param( "method",
                                                     "rtm.tasks.setPriority" ),
                                          new Param( "timeline", timelineId ),
                                          new Param( "list_id", listId ),
                                          new Param( "taskseries_id",
                                                     taskSeriesId ),
                                          new Param( "task_id", taskId ),
                                          new Param( "priority",
                                                     RtmTask.convertPriority( priority ) ),
                                          new Param( "auth_token",
                                                     currentAuthToken ),
                                          new Param( "api_key",
                                                     applicationInfo.getApiKey() ) );
      return newTaskResult( timelineId, elt );
   }
   
   
   
   @Override
   public TimeLineResult< RtmTaskList > tasks_setRecurrence( String timelineId,
                                                             String listId,
                                                             String taskSeriesId,
                                                             String taskId,
                                                             String repeat ) throws ServiceException
   {
      final Element elt;
      
      if ( !TextUtils.isEmpty( repeat ) )
         elt = invoker.invoke( new Param( "method", "rtm.tasks.setRecurrence" ),
                               new Param( "timeline", timelineId ),
                               new Param( "list_id", listId ),
                               new Param( "taskseries_id", taskSeriesId ),
                               new Param( "task_id", taskId ),
                               new Param( "repeat", repeat ),
                               new Param( "auth_token", currentAuthToken ),
                               new Param( "api_key",
                                          applicationInfo.getApiKey() ) );
      else
         elt = invoker.invoke( new Param( "method", "rtm.tasks.setRecurrence" ),
                               new Param( "timeline", timelineId ),
                               new Param( "list_id", listId ),
                               new Param( "taskseries_id", taskSeriesId ),
                               new Param( "task_id", taskId ),
                               new Param( "auth_token", currentAuthToken ),
                               new Param( "api_key",
                                          applicationInfo.getApiKey() ) );
      
      return newTaskResult( timelineId, elt );
   }
   
   
   
   @Override
   public TimeLineResult< RtmTaskList > tasks_setTags( String timelineId,
                                                       String listId,
                                                       String taskSeriesId,
                                                       String taskId,
                                                       List< String > tags ) throws ServiceException
   {
      final String joinedTags = TextUtils.join( ",", tags );
      
      final Element elt = invoker.invoke( new Param( "method",
                                                     "rtm.tasks.setTags" ),
                                          new Param( "timeline", timelineId ),
                                          new Param( "list_id", listId ),
                                          new Param( "taskseries_id",
                                                     taskSeriesId ),
                                          new Param( "task_id", taskId ),
                                          new Param( "tags", joinedTags ),
                                          new Param( "auth_token",
                                                     currentAuthToken ),
                                          new Param( "api_key",
                                                     applicationInfo.getApiKey() ) );
      return newTaskResult( timelineId, elt );
   }
   
   
   
   @Override
   public TimeLineResult< RtmTaskList > tasks_setLocation( String timelineId,
                                                           String listId,
                                                           String taskSeriesId,
                                                           String taskId,
                                                           String locationId ) throws ServiceException
   {
      
      final Element elt;
      
      if ( locationId != null )
         elt = invoker.invoke( new Param( "method", "rtm.tasks.setLocation" ),
                               new Param( "timeline", timelineId ),
                               new Param( "list_id", listId ),
                               new Param( "taskseries_id", taskSeriesId ),
                               new Param( "task_id", taskId ),
                               new Param( "location_id", locationId ),
                               new Param( "auth_token", currentAuthToken ),
                               new Param( "api_key",
                                          applicationInfo.getApiKey() ) );
      else
         elt = invoker.invoke( new Param( "method", "rtm.tasks.setLocation" ),
                               new Param( "timeline", timelineId ),
                               new Param( "list_id", listId ),
                               new Param( "taskseries_id", taskSeriesId ),
                               new Param( "task_id", taskId ),
                               new Param( "auth_token", currentAuthToken ),
                               new Param( "api_key",
                                          applicationInfo.getApiKey() ) );
      
      return newTaskResult( timelineId, elt );
   }
   
   
   
   @Override
   public TimeLineResult< RtmTaskList > tasks_setURL( String timelineId,
                                                      String listId,
                                                      String taskSeriesId,
                                                      String taskId,
                                                      String url ) throws ServiceException
   {
      final Element elt = invoker.invoke( new Param( "method",
                                                     "rtm.tasks.setURL" ),
                                          new Param( "timeline", timelineId ),
                                          new Param( "list_id", listId ),
                                          new Param( "taskseries_id",
                                                     taskSeriesId ),
                                          new Param( "task_id", taskId ),
                                          new Param( "url", url ),
                                          new Param( "auth_token",
                                                     currentAuthToken ),
                                          new Param( "api_key",
                                                     applicationInfo.getApiKey() ) );
      return newTaskResult( timelineId, elt );
   }
   
   
   
   @Override
   public TimeLineResult< RtmTaskNote > tasks_notes_add( String timelineId,
                                                         String listId,
                                                         String taskSeriesId,
                                                         String taskId,
                                                         String title,
                                                         String text ) throws ServiceException
   {
      final Element elt = invoker.invoke( new Param( "method",
                                                     "rtm.tasks.notes.add" ),
                                          new Param( "timeline", timelineId ),
                                          new Param( "list_id", listId ),
                                          new Param( "taskseries_id",
                                                     taskSeriesId ),
                                          new Param( "task_id", taskId ),
                                          new Param( "note_title",
                                                     Strings.emptyIfNull( title ) ),
                                          new Param( "note_text",
                                                     Strings.emptyIfNull( text ) ),
                                          new Param( "auth_token",
                                                     currentAuthToken ),
                                          new Param( "api_key",
                                                     applicationInfo.getApiKey() ) );
      return newNoteResult( timelineId, elt, null, taskSeriesId );
   }
   
   
   
   @Override
   public TimeLineResult< RtmTaskNote > tasks_notes_delete( String timelineId,
                                                            String taskSeriesId,
                                                            String noteId ) throws ServiceException
   {
      final Element elt = invoker.invoke( new Param( "method",
                                                     "rtm.tasks.notes.delete" ),
                                          new Param( "timeline", timelineId ),
                                          new Param( "note_id", noteId ),
                                          new Param( "auth_token",
                                                     currentAuthToken ),
                                          new Param( "api_key",
                                                     applicationInfo.getApiKey() ) );
      return newNoteResult( timelineId, elt, noteId, taskSeriesId );
   }
   
   
   
   @Override
   public TimeLineResult< RtmTaskNote > tasks_notes_edit( String timelineId,
                                                          String taskSeriesId,
                                                          String noteId,
                                                          String title,
                                                          String text ) throws ServiceException
   {
      final Element elt = invoker.invoke( new Param( "method",
                                                     "rtm.tasks.notes.edit" ),
                                          new Param( "timeline", timelineId ),
                                          new Param( "note_id", noteId ),
                                          new Param( "note_title", title ),
                                          new Param( "note_text", text ),
                                          new Param( "auth_token",
                                                     currentAuthToken ),
                                          new Param( "api_key",
                                                     applicationInfo.getApiKey() ) );
      return newNoteResult( timelineId, elt, noteId, taskSeriesId );
   }
   
   
   
   @Override
   public void test_echo()
   {
      throw new UnsupportedOperationException( "Not supported yet." );
   }
   
   
   
   @Override
   public void test_login()
   {
      throw new UnsupportedOperationException( "Not supported yet." );
   }
   
   
   
   @Override
   public void time_convert()
   {
      throw new UnsupportedOperationException( "Not supported yet." );
   }
   
   
   
   @Override
   public void time_parse()
   {
      throw new UnsupportedOperationException( "Not supported yet." );
   }
   
   
   
   @Override
   public RtmTimeline timelines_create() throws ServiceException
   {
      return new RtmTimeline( invoker.invoke( new Param( "method",
                                                         "rtm.timelines.create" ),
                                              new Param( "auth_token",
                                                         currentAuthToken ),
                                              new Param( "api_key",
                                                         applicationInfo.getApiKey() ) ),
                              this );
   }
   
   
   
   @Override
   public void timezones_getList()
   {
      throw new UnsupportedOperationException( "Not supported yet." );
   }
   
   
   
   @Override
   public void transactions_undo( String timeline, String transactionId ) throws ServiceException
   {
      invoker.invoke( new Param( "method", "rtm.transactions.undo" ),
                      new Param( "auth_token", currentAuthToken ),
                      new Param( "api_key", applicationInfo.getApiKey() ),
                      new Param( "timeline", timeline ),
                      new Param( "transaction_id ", transactionId ) );
   }
   
   
   
   @Override
   public List< RtmLocation > locations_getList() throws ServiceException
   {
      Element result = invoker.invoke( new Param( "method",
                                                  "rtm.locations.getList" ),
                                       new Param( "auth_token",
                                                  currentAuthToken ),
                                       new Param( "api_key",
                                                  applicationInfo.getApiKey() ) );
      
      List< RtmLocation > locations = new ArrayList< RtmLocation >();
      for ( Element child : RtmData.children( result, "location" ) )
      {
         locations.add( new RtmLocation( child ) );
      }
      return locations;
   }
   
   
   
   private final static TimeLineResult< RtmTaskList > newTaskResult( String timelineId,
                                                                     Element elt ) throws ServiceException
   {
      final NodeList nodes = elt.getParentNode().getChildNodes(); // <rsp>
      
      if ( nodes.getLength() < 2 )
         throw new ServiceInternalException( "Expected at least 2 nodes in response" );
      
      final Node transactionNode = nodes.item( 0 );
      
      if ( transactionNode != null
         && transactionNode.getNodeName().equalsIgnoreCase( "transaction" )
         && transactionNode.getNodeType() == Node.ELEMENT_NODE )
      {
         final Node taskListNode = nodes.item( 1 );
         
         if ( taskListNode != null
            && taskListNode.getNodeName().equalsIgnoreCase( "list" )
            && taskListNode.getNodeType() == Node.ELEMENT_NODE )
         {
            final RtmTaskList taskList = new RtmTaskList( (Element) taskListNode );
            
            if ( nodes.getLength() > 2 )
            {
               final Node generatedNode = taskListNode.getNextSibling();
               
               if ( generatedNode != null
                  && generatedNode.getNodeName().equalsIgnoreCase( "generated" )
                  && generatedNode.getNodeType() == Node.ELEMENT_NODE )
               {
                  taskList.addGeneratedSeries( (Element) generatedNode );
               }
               else
               {
                  throw new ServiceInternalException( "Expected <generated> node in response" );
               }
            }
            
            return TimeLineResult.newResult( elt, timelineId, taskList );
         }
         else
         {
            throw new ServiceInternalException( "Expected <list> node in response" );
         }
      }
      else
      {
         throw new ServiceInternalException( "Expected <transaction> node in response" );
      }
   }
   
   
   
   private final static TimeLineResult< RtmTaskNote > newNoteResult( String timelineId,
                                                                     Element elt,
                                                                     String noteId,
                                                                     String taskSeriesId ) throws ServiceException
   {
      final NodeList nodes = elt.getParentNode().getChildNodes(); // <rsp>
      
      if ( nodes.getLength() < 1 )
         throw new ServiceInternalException( "Expected at least 1 node in response" );
      
      final Node transactionNode = nodes.item( 0 );
      
      if ( transactionNode != null
         && transactionNode.getNodeName().equalsIgnoreCase( "transaction" )
         && transactionNode.getNodeType() == Node.ELEMENT_NODE )
      {
         // The <note> element is not present in case of deleting a note.
         if ( nodes.getLength() > 1 )
         {
            final Node noteNode = nodes.item( 1 );
            
            if ( noteNode != null
               && noteNode.getNodeName().equalsIgnoreCase( "note" )
               && noteNode.getNodeType() == Node.ELEMENT_NODE )
            {
               final RtmTaskNote note = new RtmTaskNote( (Element) noteNode,
                                                         taskSeriesId );
               
               return TimeLineResult.newResult( elt, timelineId, note );
            }
            else
            {
               throw new ServiceInternalException( "Expected <note> node in response" );
            }
         }
         else
         {
            // Create a temporary note that is deleted.
            return TimeLineResult.newResult( elt,
                                             timelineId,
                                             new RtmTaskNote( noteId,
                                                              taskSeriesId,
                                                              null,
                                                              null,
                                                              new Date(),
                                                              null,
                                                              null ) );
         }
      }
      else
      {
         throw new ServiceInternalException( "Expected <transaction> node in response" );
      }
   }
   
   
   
   private final static TimeLineResult< RtmList > newListResult( String timelineId,
                                                                 Element elt ) throws ServiceException
   {
      final NodeList nodes = elt.getParentNode().getChildNodes(); // <rsp>
      
      if ( nodes.getLength() < 1 )
         throw new ServiceInternalException( "Expected at least 1 node in response" );
      
      final Node transactionNode = nodes.item( 0 );
      
      if ( transactionNode != null
         && transactionNode.getNodeName().equalsIgnoreCase( "transaction" )
         && transactionNode.getNodeType() == Node.ELEMENT_NODE )
      {
         final Node listNode = nodes.item( 1 );
         
         if ( listNode != null
            && listNode.getNodeName().equalsIgnoreCase( "list" )
            && listNode.getNodeType() == Node.ELEMENT_NODE )
         {
            final RtmList list = new RtmList( (Element) listNode );
            
            return TimeLineResult.newResult( elt, timelineId, list );
         }
         else
         {
            throw new ServiceInternalException( "Expected <list> node in response" );
         }
      }
      else
      {
         throw new ServiceInternalException( "Expected <transaction> node in response" );
      }
   }
}
