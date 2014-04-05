/* 
 *	Copyright (c) 2013 Ronny R�hricht
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
 * Ronny R�hricht - implementation
 */

package dev.drsoran.moloko.domain.services;

import java.util.NoSuchElementException;

import android.database.ContentObserver;
import android.net.Uri;
import dev.drsoran.moloko.domain.model.CloudEntry;
import dev.drsoran.moloko.domain.model.Contact;
import dev.drsoran.moloko.domain.model.ExtendedTaskCount;
import dev.drsoran.moloko.domain.model.Location;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.domain.model.Settings;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.rtm.sync.SyncTime;


public interface IContentRepository
{
   Task getTask( long taskId, TaskContentOptions taskContentOptions ) throws NoSuchElementException,
                                                                     ContentException;
   
   
   
   Iterable< Task > getAllTasks( TaskContentOptions taskContentOptions ) throws ContentException;
   
   
   
   Iterable< Task > getTasksInTasksList( TasksList tasksList,
                                         TaskContentOptions taskContentOptions ) throws ContentException;
   
   
   
   Iterable< Task > getTasksInPhysicalTasksList( long tasksListId,
                                                 TaskContentOptions taskContentOptions ) throws ContentException;
   
   
   
   ExtendedTaskCount getTaskCountOfTasksList( TasksList tasksList ) throws ContentException;
   
   
   
   Iterable< Task > getTasksFromSmartFilter( RtmSmartFilter smartFilter,
                                             TaskContentOptions taskContentOptions ) throws ContentException;
   
   
   
   TasksList getTasksList( long tasksListId,
                           TasksListContentOptions tasksListContentOptions ) throws NoSuchElementException,
                                                                            ContentException;
   
   
   
   Iterable< TasksList > getAllTasksLists( TasksListContentOptions tasksListContentOptions ) throws ContentException;
   
   
   
   Iterable< TasksList > getPhysicalTasksLists( TasksListContentOptions tasksListContentOptions ) throws ContentException;
   
   
   
   Iterable< String > getAllTags() throws ContentException;
   
   
   
   Contact getContact( long contactId ) throws NoSuchElementException,
                                       ContentException;
   
   
   
   Iterable< Contact > getAllContacts() throws ContentException;
   
   
   
   Location getLocation( long locationId ) throws NoSuchElementException,
                                          ContentException;
   
   
   
   Iterable< Location > getAllLocations() throws ContentException;
   
   
   
   SyncTime getSyncTimes() throws ContentException;
   
   
   
   Settings getRtmSettings() throws ContentException;
   
   
   
   Iterable< CloudEntry > getCloudEntries() throws ContentException;
   
   
   
   void registerContentObserver( ContentObserver observer, Uri contentUri );
   
   
   
   void unregisterContentObserver( ContentObserver observer );
}
