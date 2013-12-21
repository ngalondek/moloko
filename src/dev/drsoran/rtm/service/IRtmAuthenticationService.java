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
package dev.drsoran.rtm.service;

import java.util.Date;
import java.util.List;

import dev.drsoran.moloko.domain.model.Settings;
import dev.drsoran.rtm.RtmResponse;
import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.model.old.RtmList;
import dev.drsoran.rtm.model.old.RtmLists;
import dev.drsoran.rtm.model.old.RtmLocation;
import dev.drsoran.rtm.model.old.RtmTask.Priority;
import dev.drsoran.rtm.model.old.RtmTaskList;
import dev.drsoran.rtm.model.old.RtmTaskNote;
import dev.drsoran.rtm.model.old.RtmTaskSeries;
import dev.drsoran.rtm.model.old.RtmTasks;
import dev.drsoran.rtm.model.old.RtmTimeline;


/**
 * Represents the Remember the Milk service API.
 * 
 * @author Will Ross Jun 21, 2007
 */
public interface IRtmAuthenticationService
{
   // ////// AUTHORIZATION /////////////////////////////
   
   /**
    * Checks whether the service is authorized to communicate with the RTM server. Depends on the user's login info, and
    * whether or not that user has authorized the service wrapper to communicate with RTM.
    * 
    * @return true if the service API has permission to interact with full permissions (including delete) with RTM
    * @throws RtmServiceException
    *            if there is a problem checking for authorization
    */
   boolean isServiceAuthorized() throws RtmServiceException;
   
   
   
   /**
    * Begins the process of obtaining authorization for the service API to communicate with RTM on behalf of a
    * particular user.
    * 
    * @return the URL that the user should be prompted to log in to to complete authorization
    * @throws RtmServiceException
    *            if the authorization process cannot be started
    */
   String beginAuthorization( RtmServicePermission permissions ) throws RtmServiceException;
   
   
   
   /**
    * The same method as the previous {@link #beginAuthorization(dev.drsoran.rtm.model.RtmAuth.RtmServicePermission)},
    * except that you need to invoke yourself the {@link #auth_getFrob()} beforehand.
    * 
    * This has been introduced, in order to provide better control over the API.
    */
   String beginAuthorization( RtmFrob frob, RtmServicePermission permissions ) throws RtmServiceException;
   
   
   
   /**
    * Completes the process of obtaining authorization for the service API to communicate with RTM on behalf of a
    * particular user.
    * 
    * Once this is called successfully, <code>isServiceAuthorized()</code> should return true until the user goes to RTM
    * and explicitly denies the service access. It also might be possible for authorization to time out, in which case
    * this process would need to be started again.
    * 
    * @return the newly created authentication token
    * @throws RtmServiceException
    *            if the authorization process cannot be completed
    */
   String completeAuthorization() throws RtmServiceException;
   
   
   
   /**
    * Same as the previous {@link #completeAuthorization()} method, except that the frob taken is implicitly given. Very
    * useful when you need to handle multiple authentication tokens.
    */
   String completeAuthorization( RtmFrob frob ) throws RtmServiceException;
   
   
   
   RtmAuth auth_checkToken( String authToken ) throws RtmServiceException;
   
   
   
   RtmFrob auth_getFrob() throws RtmServiceException;
   
   
   
   String auth_getToken( String frob ) throws RtmServiceException;
   
   
   
   // ////// CONTACTS /////////////////////////////
   
   void contacts_add() throws RtmServiceException;
   
   
   
   void contacts_delete() throws RtmServiceException;
   
   
   
   RtmContacts contacts_getList() throws RtmServiceException;
   
   
   
   void groups_add() throws RtmServiceException;
   
   
   
   void groups_addContact() throws RtmServiceException;
   
   
   
   void groups_delete() throws RtmServiceException;
   
   
   
   void groups_getList() throws RtmServiceException;
   
   
   
   void groups_removeContact() throws RtmServiceException;
   
   
   
   // ////// LISTS /////////////////////////////
   
   RtmResponse< RtmList > lists_add( String timelineId,
                                     String listName,
                                     String smartFilter ) throws RtmServiceException;
   
   
   
   void lists_archive() throws RtmServiceException;
   
   
   
   RtmResponse< RtmList > lists_delete( String timelineId, String listId ) throws RtmServiceException;
   
   
   
   RtmLists lists_getList() throws RtmServiceException;
   
   
   
   RtmList lists_getList( String listName ) throws RtmServiceException;
   
   
   
   void lists_setDefaultList() throws RtmServiceException;
   
   
   
   RtmResponse< RtmList > lists_setName( String timelineId,
                                         String listId,
                                         String newName ) throws RtmServiceException;
   
   
   
   void lists_unarchive() throws RtmServiceException;
   
   
   
   // ////// REFLECTION /////////////////////////////
   
   void reflection_getMethodInfo() throws RtmServiceException;
   
   
   
   void reflection_getMethods() throws RtmServiceException;
   
   
   
   // ////// SETTINGS /////////////////////////////
   
   Settings settings_getList() throws RtmServiceException;
   
   
   
   // ////// TASKS /////////////////////////////
   
   RtmResponse< RtmTaskList > tasks_add( String timelineId,
                                         String listId,
                                         String name ) throws RtmServiceException;
   
   
   
   void tasks_addTags() throws RtmServiceException;
   
   
   
   RtmResponse< RtmTaskList > tasks_complete( String timelineId,
                                              String listId,
                                              String taskSeriesId,
                                              String taskId ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTaskList > tasks_uncomplete( String timelineId,
                                                String listId,
                                                String taskSeriesId,
                                                String taskId ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTaskList > tasks_delete( String timelineId,
                                            String listId,
                                            String taskSeriesId,
                                            String taskId ) throws RtmServiceException;
   
   
   
   RtmTasks tasks_getList( String listId, String filter, Date lastSync ) throws RtmServiceException;
   
   
   
   RtmTaskSeries tasks_getTask( String taskSeriesId,
                                String taskName,
                                String listId ) throws RtmServiceException;
   
   
   
   /**
    * @return Warning: the very first task with the given name is returned!
    */
   RtmTaskSeries tasks_getTask( String taskName ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTaskList > tasks_movePriority( String timelineId,
                                                  String listId,
                                                  String taskSeriesId,
                                                  String taskId,
                                                  boolean up ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTaskList > tasks_moveTo( String timelineId,
                                            String fromListId,
                                            String toListId,
                                            String taskSeriesId,
                                            String taskId ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTaskList > tasks_postpone( String timelineId,
                                              String listId,
                                              String taskSeriesId,
                                              String taskId ) throws RtmServiceException;
   
   
   
   void tasks_removeTags() throws RtmServiceException;
   
   
   
   RtmResponse< RtmTaskList > tasks_setDueDate( String timelineId,
                                                String listId,
                                                String taskSeriesId,
                                                String taskId,
                                                Date due,
                                                boolean hasTime ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTaskList > tasks_setEstimate( String timelineId,
                                                 String listId,
                                                 String taskSeriesId,
                                                 String taskId,
                                                 String estimate ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTaskList > tasks_setName( String timelineId,
                                             String listId,
                                             String taskSeriesId,
                                             String taskId,
                                             String newName ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTaskList > tasks_setPriority( String timelineId,
                                                 String listId,
                                                 String taskSeriesId,
                                                 String taskId,
                                                 Priority priority ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTaskList > tasks_setRecurrence( String timelineId,
                                                   String listId,
                                                   String taskSeriesId,
                                                   String taskId,
                                                   String repeat ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTaskList > tasks_setTags( String timelineId,
                                             String listId,
                                             String taskSeriesId,
                                             String taskId,
                                             List< String > tags ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTaskList > tasks_setLocation( String timelineId,
                                                 String listId,
                                                 String taskSeriesId,
                                                 String taskId,
                                                 String locationId ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTaskList > tasks_setURL( String timelineId,
                                            String listId,
                                            String taskSeriesId,
                                            String taskId,
                                            String url ) throws RtmServiceException;
   
   
   
   // ////// NOTES /////////////////////////////
   
   RtmResponse< RtmTaskNote > tasks_notes_add( String timelineId,
                                               String listId,
                                               String taskSeriesId,
                                               String taskId,
                                               String title,
                                               String text ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTaskNote > tasks_notes_delete( String timelineId,
                                                  String taskSeriesId,
                                                  String noteId ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTaskNote > tasks_notes_edit( String timelineId,
                                                String taskSeriesId,
                                                String noteId,
                                                String title,
                                                String text ) throws RtmServiceException;
   
   
   
   // ////// OTHER STUFF /////////////////////////////
   
   void test_echo() throws RtmServiceException;
   
   
   
   void test_login() throws RtmServiceException;
   
   
   
   void time_convert() throws RtmServiceException;
   
   
   
   void time_parse() throws RtmServiceException;
   
   
   
   RtmTimeline timelines_create() throws RtmServiceException;
   
   
   
   void timezones_getList() throws RtmServiceException;
   
   
   
   void transactions_undo( String timeline, String transactionId ) throws RtmServiceException;
   
   
   
   // ////// LOCATIONS /////////////////////////////
   
   List< RtmLocation > locations_getList() throws RtmServiceException;
}
