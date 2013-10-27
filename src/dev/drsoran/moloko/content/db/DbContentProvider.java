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

import java.util.NoSuchElementException;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.SystemContext;
import dev.drsoran.moloko.content.ContentMimeTypes;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.content.UriLookup;
import dev.drsoran.moloko.util.Strings;


public class DbContentProvider extends ContentProvider
{
   private ILog log;
   
   private UriLookup< IContentUriHandler > contentUriToHandlerLookup;
   
   private RtmDatabase database;
   
   
   
   public DbContentProvider()
   {
   }
   
   
   
   public DbContentProvider( RtmDatabase database, ILog log )
   {
      this.database = database;
      this.log = log;
   }
   
   
   
   @Override
   public boolean onCreate()
   {
      final Context context = getContext();
      
      if ( log == null )
      {
         log = SystemContext.get( context ).Log();
      }
      
      if ( database == null )
      {
         database = new RtmDatabase( context, log );
      }
      
      contentUriToHandlerLookup = createContentUriToHandlerLookup();
      return true;
   }
   
   
   
   @Override
   public void shutdown()
   {
      database.close();
      super.shutdown();
   }
   
   
   
   @Override
   public Cursor query( Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder )
   {
      if ( uri == null )
      {
         throw new IllegalArgumentException( "uri" );
      }
      
      if ( projection == null )
      {
         throw new IllegalArgumentException( "projection" );
      }
      
      final IContentUriHandler handlerToQuery;
      try
      {
         handlerToQuery = contentUriToHandlerLookup.get( uri );
         return handlerToQuery.query( uri,
                                      projection,
                                      selection,
                                      selectionArgs,
                                      sortOrder );
      }
      catch ( NoSuchElementException e )
      {
         logOperationFailed( "Query", e );
         return null;
      }
   }
   
   
   
   @Override
   public Uri insert( Uri uri, ContentValues values )
   {
      if ( uri == null )
      {
         throw new IllegalArgumentException( "uri" );
      }
      
      if ( values == null )
      {
         throw new IllegalArgumentException( "values" );
      }
      
      long insertedElementId;
      final IContentUriHandler handlerToInsertInto;
      try
      {
         handlerToInsertInto = contentUriToHandlerLookup.get( uri );
         insertedElementId = handlerToInsertInto.insert( uri, values );
         
         getContext().getContentResolver().notifyChange( uri, null );
         
         return android.content.ContentUris.withAppendedId( uri,
                                                            insertedElementId );
      }
      catch ( NoSuchElementException e )
      {
         logOperationFailed( "Insert", e );
         return null;
      }
   }
   
   
   
   @Override
   public int delete( Uri uri, String selection, String[] selectionArgs )
   {
      if ( uri == null )
      {
         throw new IllegalArgumentException( "uri" );
      }
      
      int numDeleted;
      final IContentUriHandler handlerToDeleteFrom;
      try
      {
         handlerToDeleteFrom = contentUriToHandlerLookup.get( uri );
         numDeleted = handlerToDeleteFrom.delete( uri, selection, selectionArgs );
      }
      catch ( NoSuchElementException e )
      {
         logOperationFailed( "Delete", e );
         numDeleted = 0;
      }
      
      if ( numDeleted > 0 )
      {
         getContext().getContentResolver().notifyChange( uri, null );
      }
      
      return numDeleted;
   }
   
   
   
   @Override
   public int update( Uri uri,
                      ContentValues values,
                      String selection,
                      String[] selectionArgs )
   {
      if ( uri == null )
      {
         throw new IllegalArgumentException( "uri" );
      }
      
      if ( values == null )
      {
         throw new IllegalArgumentException( "values" );
      }
      
      if ( !Strings.isNullOrEmpty( selection ) )
      {
         throw new IllegalArgumentException( "An update with a 'where clause' is not supported" );
      }
      
      int numUpdated;
      final IContentUriHandler handlerToUpdate;
      try
      {
         handlerToUpdate = contentUriToHandlerLookup.get( uri );
         numUpdated = handlerToUpdate.update( uri,
                                              values,
                                              selection,
                                              selectionArgs );
      }
      catch ( NoSuchElementException e )
      {
         logOperationFailed( "Update", e );
         numUpdated = 0;
      }
      
      if ( numUpdated > 0 )
      {
         getContext().getContentResolver().notifyChange( uri, null );
      }
      
      return numUpdated;
   }
   
   
   
   @Override
   public String getType( Uri uri )
   {
      if ( uri == null )
      {
         throw new IllegalArgumentException( "uri" );
      }
      
      String mimeType;
      
      try
      {
         mimeType = ContentMimeTypes.CONTENT_URI_MIME_TYPE_LOOKUP.get( uri );
      }
      catch ( NoSuchElementException e )
      {
         // If no MIME type could be determined for the URI, the ContentProvider
         // interface contract requires us to return null.
         mimeType = null;
      }
      
      return mimeType;
   }
   
   
   
   private UriLookup< IContentUriHandler > createContentUriToHandlerLookup()
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
      
      IContentUriHandler handler = new ReadOnlyContentUriHandler( new TaskCountContentUriHandler( tasksContentUriHandler ) );
      handlerLookup.put( handler, ContentUris.MATCH_TASKS_COUNT );
      
      handler = new TaskNotesContentUriHandler( database.getTable( RtmNotesTable.TABLE_NAME ),
                                                tasksContentUriHandler );
      handlerLookup.put( handler, ContentUris.MATCH_TASK_NOTES );
      handlerLookup.put( handler, ContentUris.MATCH_TASK_NOTES_ID );
      
      handler = new ReadOnlyContentUriHandler( new TaskParticipantsContentUriHandler( database.getTable( RtmParticipantsTable.TABLE_NAME ),
                                                                                      tasksContentUriHandler ) );
      handlerLookup.put( handler, ContentUris.MATCH_TASK_PARTICIPANTS );
      handlerLookup.put( handler, ContentUris.MATCH_TASK_PARTICIPANTS_ID );
      
      handler = new ReadOnlyContentUriHandler( new CloudEntriesUriHandler( tagsContentUriHandler,
                                                                           tasksListContentUriHandler,
                                                                           locationsContentUriHandler ) );
      handlerLookup.put( handler, ContentUris.MATCH_CLOUD_ENTRIES );
      
      handler = new ReadOnlyContentUriHandler( new ContentUriHandlerTableAdapter( database.getTable( RtmContactsTable.TABLE_NAME ) ) );
      handlerLookup.put( handler, ContentUris.MATCH_CONTACTS );
      handlerLookup.put( handler, ContentUris.MATCH_CONTACTS_ID );
      
      handler = new ContentUriHandlerTableAdapter( database.getTable( ModificationsTable.TABLE_NAME ) );
      handlerLookup.put( handler, ContentUris.MATCH_MODIFICATIONS );
      handlerLookup.put( handler, ContentUris.MATCH_MODIFICATIONS_ID );
      
      handler = new ContentUriHandlerTableAdapter( database.getTable( RtmSettingsTable.TABLE_NAME ) );
      handlerLookup.put( handler, ContentUris.MATCH_RTM_SETTINGS );
      handlerLookup.put( handler, ContentUris.MATCH_RTM_SETTINGS_ID );
      
      handler = new ContentUriHandlerTableAdapter( database.getTable( SyncTable.TABLE_NAME ) );
      handlerLookup.put( handler, ContentUris.MATCH_SYNC );
      handlerLookup.put( handler, ContentUris.MATCH_SYNC_ID );
      
      return handlerLookup;
   }
   
   
   
   private void logOperationFailed( String operation, Exception e )
   {
      log.e( DbContentProvider.class,
             "Operation '" + operation + "' failed.",
             e );
   }
}
