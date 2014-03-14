/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.moloko.content.db;

import java.util.List;
import java.util.NoSuchElementException;

import dev.drsoran.db.ITable;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.content.IContentUriHandler;
import dev.drsoran.moloko.content.MolokoContentProvider;
import dev.drsoran.moloko.content.ReadOnlyContentUriHandler;
import dev.drsoran.moloko.content.UriLookup;


public class DbContentProvider extends MolokoContentProvider
{
   private RtmDatabase database;
   
   
   
   public DbContentProvider()
   {
   }
   
   
   
   public DbContentProvider( RtmDatabase database )
   {
      this.database = database;
   }
   
   
   
   @Override
   public boolean onCreate()
   {
      if ( database == null )
      {
         database = new RtmDatabase( getContext() );
      }
      
      boolean ok = super.onCreate();
      return ok;
   }
   
   
   
   public RtmDatabase getDatabase()
   {
      return database;
   }
   
   
   
   public List< ? extends ITable > getTables()
   {
      return database.getAllTables();
   }
   
   
   
   public void clearAllTables()
   {
      database.clearAllTables();
   }
   
   
   
   public void clearTable( String tableName ) throws NoSuchElementException
   {
      database.clearTable( tableName );
   }
   
   
   
   @Override
   public void shutdown()
   {
      database.close();
      super.shutdown();
   }
   
   
   
   @Override
   protected UriLookup< IContentUriHandler > createContentUriToHandlerLookup()
   {
      final UriLookup< IContentUriHandler > handlerLookup = new UriLookup< IContentUriHandler >( ContentUris.MATCHER );
      
      final IContentUriHandler tasksListContentUriHandler = new ContentUriHandlerTableAdapter( database.getTable( RtmTasksListsTable.TABLE_NAME ) );
      handlerLookup.put( tasksListContentUriHandler,
                         ContentUris.MATCH_TASKS_LISTS );
      handlerLookup.put( tasksListContentUriHandler,
                         ContentUris.MATCH_TASKS_LISTS_ID );
      
      final TasksContentUriHandler tasksContentUriHandler = new TasksContentUriHandler( database.getWritable(),
                                                                                        database.getTable( RtmTaskSeriesTable.TABLE_NAME ),
                                                                                        database.getTable( RtmRawTasksTable.TABLE_NAME ) );
      handlerLookup.put( tasksContentUriHandler, ContentUris.MATCH_TASKS );
      handlerLookup.put( tasksContentUriHandler, ContentUris.MATCH_TASKS_ID );
      
      final IContentUriHandler locationsContentUriHandler = new ReadOnlyContentUriHandler( new ContentUriHandlerTableAdapter( database.getTable( RtmLocationsTable.TABLE_NAME ) ) );
      handlerLookup.put( locationsContentUriHandler,
                         ContentUris.MATCH_LOCATIONS );
      handlerLookup.put( locationsContentUriHandler,
                         ContentUris.MATCH_LOCATIONS_ID );
      
      final IContentUriHandler tagsContentUriHandler = new ReadOnlyContentUriHandler( new TagsContentUriHandler( database.getReadable() ) );
      handlerLookup.put( tagsContentUriHandler, ContentUris.MATCH_TAGS );
      
      IContentUriHandler handler = new ReadOnlyContentUriHandler( new TaskCountContentUriHandler( tasksContentUriHandler,
                                                                                                  getCalendarProvider() ) );
      handlerLookup.put( handler, ContentUris.MATCH_TASKS_COUNT );
      
      handler = new TaskNotesContentUriHandler( database.getTable( RtmNotesTable.TABLE_NAME ),
                                                tasksContentUriHandler );
      handlerLookup.put( handler, ContentUris.MATCH_TASK_NOTES );
      handlerLookup.put( handler, ContentUris.MATCH_TASK_NOTES_ID );
      
      handler = new ReadOnlyContentUriHandler( new TaskParticipantsContentUriHandler( database.getTable( RtmParticipantsTable.TABLE_NAME ),
                                                                                      tasksContentUriHandler ) );
      handlerLookup.put( handler, ContentUris.MATCH_TASK_PARTICIPANTS );
      handlerLookup.put( handler, ContentUris.MATCH_TASK_PARTICIPANTS_ID );
      
      handler = new ReadOnlyContentUriHandler( new CloudEntriesUriHandler( tasksContentUriHandler ) );
      handlerLookup.put( handler, ContentUris.MATCH_CLOUD_ENTRIES );
      
      handler = new ReadOnlyContentUriHandler( new ContentUriHandlerTableAdapter( database.getTable( RtmContactsTable.TABLE_NAME ) ) );
      handlerLookup.put( handler, ContentUris.MATCH_CONTACTS );
      handlerLookup.put( handler, ContentUris.MATCH_CONTACTS_ID );
      
      handler = new ContentUriHandlerTableAdapter( database.getTable( ModificationsTable.TABLE_NAME ) );
      handlerLookup.put( handler, ContentUris.MATCH_MODIFICATIONS );
      handlerLookup.put( handler, ContentUris.MATCH_MODIFICATIONS_ID );
      
      handler = new RtmSettingsContentUriHandler( database.getTable( RtmSettingsTable.TABLE_NAME ) );
      handlerLookup.put( handler, ContentUris.MATCH_RTM_SETTINGS );
      handlerLookup.put( handler, ContentUris.MATCH_RTM_SETTINGS_ID );
      
      handler = new SyncTimesContentUriHandler( database.getTable( SyncTimesTable.TABLE_NAME ) );
      handlerLookup.put( handler, ContentUris.MATCH_SYNC );
      handlerLookup.put( handler, ContentUris.MATCH_SYNC_ID );
      
      return handlerLookup;
   }
}
