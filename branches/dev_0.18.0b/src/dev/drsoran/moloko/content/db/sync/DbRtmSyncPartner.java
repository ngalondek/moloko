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

package dev.drsoran.moloko.content.db.sync;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import dev.drsoran.moloko.content.db.ITable;
import dev.drsoran.moloko.content.db.RtmDatabase;
import dev.drsoran.moloko.content.db.TableColumns.RtmNoteColumns;
import dev.drsoran.moloko.content.db.TableNames;
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
   private final static String SEL_DELETED_NOTES_OF_TASK = RtmNoteColumns.TASKSERIES_ID
      + "=? AND "
      + RtmNoteColumns.NOTE_DELETED_DATE
      + "!="
      + RtmConstants.NO_TIME;
   
   private final RtmDatabase database;
   
   private final IDbElementSyncHandler< RtmTasksList > tasksListSyncHandler;
   
   private final IDbElementSyncHandler< RtmTask > taskSyncHandler;
   
   private final IDbElementSyncHandler< RtmLocation > locationSyncHandler;
   
   private final IDbElementSyncHandler< RtmContact > contactSyncHandler;
   
   private final IDbElementSyncHandler< RtmSettings > settingsSyncHandler;
   
   private final IModificationsProvider modificationsProvider;
   
   private final IModelElementFactory rtmModelElementFactory;
   
   private final ITaskSeriesIdProvider rtmTaskSeriesIdProvider;
   
   
   
   public DbRtmSyncPartner( RtmDatabase database,
      IDbElementSyncHandler< RtmTasksList > tasksListSyncHandler,
      IDbElementSyncHandler< RtmTask > taskSyncHandler,
      IDbElementSyncHandler< RtmLocation > locationSyncHandler,
      IDbElementSyncHandler< RtmContact > contactSyncHandler,
      IDbElementSyncHandler< RtmSettings > settingsSyncHandler,
      IModificationsProvider modificationsProvider,
      IModelElementFactory rtmModelElementFactory,
      ITaskSeriesIdProvider rtmTaskSeriesIdProvider )
   {
      if ( database == null )
      {
         throw new IllegalArgumentException( "database" );
      }
      if ( tasksListSyncHandler == null )
      {
         throw new IllegalArgumentException( "tasksListsSyncHandler" );
      }
      if ( taskSyncHandler == null )
      {
         throw new IllegalArgumentException( "taskSyncHandler" );
      }
      if ( locationSyncHandler == null )
      {
         throw new IllegalArgumentException( "locationSyncHandler" );
      }
      if ( contactSyncHandler == null )
      {
         throw new IllegalArgumentException( "contactSyncHandler" );
      }
      if ( modificationsProvider == null )
      {
         throw new IllegalArgumentException( "modificationsProvider" );
      }
      if ( settingsSyncHandler == null )
      {
         throw new IllegalArgumentException( "settingsSyncHandler" );
      }
      if ( rtmModelElementFactory == null )
      {
         throw new IllegalArgumentException( "rtmModelElementFactory" );
      }
      if ( rtmTaskSeriesIdProvider == null )
      {
         throw new IllegalArgumentException( "rtmTaskSeriesIdProvider" );
      }
      
      this.database = database;
      this.tasksListSyncHandler = tasksListSyncHandler;
      this.taskSyncHandler = taskSyncHandler;
      this.locationSyncHandler = locationSyncHandler;
      this.contactSyncHandler = contactSyncHandler;
      this.settingsSyncHandler = settingsSyncHandler;
      this.modificationsProvider = modificationsProvider;
      this.rtmModelElementFactory = rtmModelElementFactory;
      this.rtmTaskSeriesIdProvider = rtmTaskSeriesIdProvider;
   }
   
   
   
   @Override
   public List< RtmTasksList > getTasksLists( long modifiedSinceMsUtc )
   {
      return tasksListSyncHandler.getElementsModifiedSince( modifiedSinceMsUtc );
   }
   
   
   
   @Override
   public List< IModification > getModificationsOfTasksList( String tasksListId )
   {
      return modificationsProvider.getModificationsOfRtmTasksList( tasksListId );
   }
   
   
   
   @Override
   public void insertTasksList( RtmTasksList tasksList )
   {
      tasksListSyncHandler.insert( tasksList );
   }
   
   
   
   @Override
   public void updateTasksList( RtmTasksList currentTasksList,
                                RtmTasksList updatedTasksList )
   {
      tasksListSyncHandler.update( currentTasksList, updatedTasksList );
   }
   
   
   
   @Override
   public void deleteTasksList( RtmTasksList tasksList )
   {
      tasksListSyncHandler.delete( tasksList );
   }
   
   
   
   @Override
   public List< RtmTask > getTasks( long modifiedSinceMsUtc )
   {
      return taskSyncHandler.getElementsModifiedSince( modifiedSinceMsUtc );
   }
   
   
   
   @Override
   public List< IModification > getModificationsOfTask( RtmTask task )
   {
      return modificationsProvider.getModificationsOfRtmTask( task.getId() );
   }
   
   
   
   @Override
   public void insertTask( RtmTask task )
   {
      taskSyncHandler.insert( task );
   }
   
   
   
   @Override
   public void updateTask( RtmTask currentTask, RtmTask updatedTask )
   {
      taskSyncHandler.update( currentTask, updatedTask );
   }
   
   
   
   @Override
   public void deleteTask( RtmTask task )
   {
      taskSyncHandler.delete( task );
   }
   
   
   
   @Override
   public List< RtmNote > getDeletedNotes( RtmTask task, long deletedSinceMsUtc )
   {
      final ITable notesTable = database.getTable( TableNames.RTM_NOTES_TABLE );
      
      Cursor c = null;
      try
      {
         c = notesTable.query( RtmNoteColumns.TABLE_PROJECTION,
                               SEL_DELETED_NOTES_OF_TASK,
                               new String[]
                               { Long.toString( rtmTaskSeriesIdProvider.getTaskSeriesIdOfRtmTaskSeriesId( task.getTaskSeriesId() ) ) },
                               null );
         
         final List< RtmNote > notes = new ArrayList< RtmNote >( c.getCount() );
         while ( c.moveToNext() )
         {
            notes.add( rtmModelElementFactory.createElementFromCursor( c,
                                                                       RtmNote.class ) );
         }
         
         return notes;
      }
      finally
      {
         if ( c != null )
         {
            c.close();
         }
      }
   }
   
   
   
   @Override
   public List< RtmLocation > getLocations()
   {
      return locationSyncHandler.getElementsModifiedSince( RtmConstants.NO_TIME );
   }
   
   
   
   @Override
   public void insertLocation( RtmLocation location )
   {
      locationSyncHandler.insert( location );
   }
   
   
   
   @Override
   public void updateLocation( RtmLocation currentLocation,
                               RtmLocation updatedLocation )
   {
      locationSyncHandler.update( currentLocation, updatedLocation );
   }
   
   
   
   @Override
   public void deleteLocation( RtmLocation location )
   {
      locationSyncHandler.delete( location );
   }
   
   
   
   @Override
   public List< RtmContact > getContacts()
   {
      return contactSyncHandler.getElementsModifiedSince( RtmConstants.NO_TIME );
   }
   
   
   
   @Override
   public void insertContact( RtmContact contact )
   {
      contactSyncHandler.insert( contact );
   }
   
   
   
   @Override
   public void updateContact( RtmContact currentContact,
                              RtmContact updatedContact )
   {
      contactSyncHandler.update( currentContact, updatedContact );
   }
   
   
   
   @Override
   public void deleteContact( RtmContact contact )
   {
      contactSyncHandler.delete( contact );
   }
   
   
   
   @Override
   public RtmSettings getSettings()
   {
      return settingsSyncHandler.getElementsModifiedSince( RtmConstants.NO_TIME )
                                .get( 0 );
   }
   
   
   
   @Override
   public void updateSettings( RtmSettings currentSettings,
                               RtmSettings updatedSettings )
   {
      settingsSyncHandler.update( currentSettings, updatedSettings );
   }
   
   
   
   @Override
   public void onSyncStarted()
   {
      database.beginTransaction();
   }
   
   
   
   @Override
   public void onSyncSuccessful()
   {
      modificationsProvider.clearModifications();
      
      database.setTransactionSuccessful();
      database.endTransaction();
   }
   
   
   
   @Override
   public void onSyncFailed()
   {
      database.endTransaction();
   }
}
