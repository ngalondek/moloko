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

package dev.drsoran.moloko.sync;

import static dev.drsoran.moloko.content.db.TableNames.RTM_CONTACTS_TABLE;
import static dev.drsoran.moloko.content.db.TableNames.RTM_LOCATIONS_TABLE;
import static dev.drsoran.moloko.content.db.TableNames.RTM_SETTINGS_TABLE;
import static dev.drsoran.moloko.content.db.TableNames.RTM_TASKS_LIST_TABLE;

import java.text.MessageFormat;
import java.util.List;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import dev.drsoran.db.ITable;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.db.RtmDatabase;
import dev.drsoran.moloko.content.db.TableColumns.RtmTasksListColumns;
import dev.drsoran.moloko.domain.content.IContentValuesFactory;
import dev.drsoran.moloko.domain.content.IModelElementFactory;
import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.model.RtmLocation;
import dev.drsoran.rtm.model.RtmNote;
import dev.drsoran.rtm.model.RtmSettings;
import dev.drsoran.rtm.model.RtmTask;
import dev.drsoran.rtm.model.RtmTasksList;
import dev.drsoran.rtm.sync.IModification;
import dev.drsoran.rtm.sync.IRtmSyncPartner;


public class DbRtmSyncPartner implements IRtmSyncPartner
{
   
   public DbRtmSyncPartner( RtmDatabase rtmDatabase,
      IModelElementFactory modelElementFactory,
      IContentValuesFactory contentValuesFactory )
   {
      if ( rtmDatabase == null )
      {
         throw new IllegalArgumentException( "rtmDatabase" );
      }
      
      if ( modelElementFactory == null )
      {
         throw new IllegalArgumentException( "modelElementFactory" );
      }
      
      if ( contentValuesFactory == null )
      {
         throw new IllegalArgumentException( "contentValuesFactory" );
      }
      
      this.rtmDatabase = rtmDatabase;
      this.modelElementFactory = modelElementFactory;
      this.contentValuesFactory = contentValuesFactory;
   }
   
   
   
   @Override
   public List< RtmTasksList > getTasksLists( long modifiedSinceMsUtc )
   {
      return getElementsModifiedSince( modifiedSinceMsUtc,
                                       RTM_TASKS_LIST_TABLE,
                                       SEL_RTM_TASKS_LIST_MODIFIED_SINCE,
                                       RtmTasksList.class );
   }
   
   
   
   @Override
   public List< IModification > getModificationsOfTasksList( String tasksListId )
   {
      // TODO Auto-generated method stub
      return null;
   }
   
   
   
   @Override
   public void insertTasksList( RtmTasksList tasksList )
   {
      final ContentValues contentValues = contentValuesFactory.createContentValues( tasksList );
      contentValues.put( RtmTasksListColumns.LIST_CREATED_DATE, timeOfSyncMsUtc );
      contentValues.put( RtmTasksListColumns.LIST_MODIFIED_DATE,
                         timeOfSyncMsUtc );
      
      if ( tasksList.isDeleted() )
      {
         contentValues.put( RtmTasksListColumns.LIST_DELETED_DATE,
                            timeOfSyncMsUtc );
      }
      
      final ITable tasksListTable = getTable( RTM_TASKS_LIST_TABLE );
      final long numInserted = tasksListTable.insert( contentValues );
      
      if ( numInserted < 1 )
      {
         throw new SQLiteException( MessageFormat.format( "RTM tasks list {0} not inserted.",
                                                          tasksList ) );
      }
   }
   
   
   
   @Override
   public void updateTasksList( RtmTasksList currentTasksList,
                                RtmTasksList updatedTasksList )
   {
      if ( currentTasksList.getId().equals( updatedTasksList.getId() ) )
      {
         throw new IllegalArgumentException( MessageFormat.format( "RTM tasks list IDs differ in update. {0} != {1}",
                                                                   currentTasksList,
                                                                   updatedTasksList ) );
      }
      
      final ITable tasksListTable = getTable( RTM_TASKS_LIST_TABLE );
      final ContentValues contentValues = contentValuesFactory.createContentValues( updatedTasksList );
      contentValues.put( RtmTasksListColumns.LIST_MODIFIED_DATE,
                         timeOfSyncMsUtc );
      
      if ( updatedTasksList.isDeleted() )
      {
         contentValues.put( RtmTasksListColumns.LIST_DELETED_DATE,
                            timeOfSyncMsUtc );
      }
      
      final int numUpdated = tasksListTable.update( Constants.NO_ID,
                                                    contentValues,
                                                    WHERE_RTM_TASKS_LIST_ID_EQ,
                                                    new String[]
                                                    { currentTasksList.getId() } );
      if ( numUpdated < 1 )
      {
         throw new SQLiteException( MessageFormat.format( "RTM tasks list {0} not updated.",
                                                          currentTasksList ) );
      }
   }
   
   
   
   @Override
   public void deleteTasksList( RtmTasksList tasksList )
   {
      final ITable tasksListTable = getTable( RTM_TASKS_LIST_TABLE );
      final int numDeleted = tasksListTable.delete( Constants.NO_ID,
                                                    WHERE_RTM_TASKS_LIST_ID_EQ,
                                                    new String[]
                                                    { tasksList.getId() } );
      if ( numDeleted < 1 )
      {
         throw new SQLiteException( MessageFormat.format( "RTM tasks list {0} not deleted.",
                                                          tasksList ) );
      }
   }
   
   
   
   @Override
   public List< RtmTask > getTasks( long modifiedSinceMsUtc )
   {
      return getElementsModifiedSince( modifiedSinceMsUtc,
                                       Rtm.TABLE_NAME,
                                       SEL_RTM_TASKS_LIST_MODIFIED_SINCE,
                                       RtmTask.class );
   }
   
   
   
   @Override
   public List< IModification > getModificationsOfTask( RtmTask task )
   {
      // TODO Auto-generated method stub
      return null;
   }
   
   
   
   @Override
   public void insertTask( RtmTask task )
   {
      // TODO Auto-generated method stub
      
   }
   
   
   
   @Override
   public void updateTask( RtmTask currentTask, RtmTask updatedTask )
   {
      // TODO Auto-generated method stub
      
   }
   
   
   
   @Override
   public void deleteTask( RtmTask task )
   {
      // TODO Auto-generated method stub
      
   }
   
   
   
   @Override
   public List< RtmNote > getDeletedNotes( RtmTask task, long deletedSinceMsUtc )
   {
      // TODO Auto-generated method stub
      return null;
   }
   
   
   
   @Override
   public List< RtmLocation > getLocations()
   {
      return getElementsModifiedSince( RtmConstants.NO_TIME,
                                       RTM_LOCATIONS_TABLE,
                                       null,
                                       RtmLocation.class );
   }
   
   
   
   @Override
   public void insertLocation( RtmLocation location )
   {
      // TODO Auto-generated method stub
      
   }
   
   
   
   @Override
   public void updateLocation( RtmLocation currentLocation,
                               RtmLocation updatedLocation )
   {
      // TODO Auto-generated method stub
      
   }
   
   
   
   @Override
   public void deleteLocation( RtmLocation location )
   {
      // TODO Auto-generated method stub
      
   }
   
   
   
   @Override
   public List< RtmContact > getContacts()
   {
      return getElementsModifiedSince( RtmConstants.NO_TIME,
                                       RTM_CONTACTS_TABLE,
                                       null,
                                       RtmContact.class );
   }
   
   
   
   @Override
   public void insertContact( RtmContact contact )
   {
      // TODO Auto-generated method stub
   }
   
   
   
   @Override
   public void updateContact( RtmContact currentContact,
                              RtmContact updatedContact )
   {
      // TODO Auto-generated method stub
   }
   
   
   
   @Override
   public void deleteContact( RtmContact contact )
   {
      // TODO Auto-generated method stub
   }
   
   
   
   @Override
   public RtmSettings getSettings()
   {
      return getElementsModifiedSince( RtmConstants.NO_TIME,
                                       RTM_SETTINGS_TABLE,
                                       null,
                                       RtmSettings.class ).get( 0 );
   }
   
   
   
   @Override
   public void onSyncStarted( long startMillisUtc )
   {
      timeOfSyncMsUtc = startMillisUtc;
      rtmDatabase.getWritable().beginTransaction();
   }
   
   
   
   @Override
   public void onSyncSuccessful()
   {
      final SQLiteDatabase db = rtmDatabase.getWritable();
      
      db.setTransactionSuccessful();
      db.endTransaction();
   }
   
   
   
   @Override
   public void onSyncFailed()
   {
      rtmDatabase.getWritable().endTransaction();
   }
   
}
