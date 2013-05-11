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


public class DbContentProvider extends ContentProvider
{
   private ILog log;
   
   private UriLookup< IContentUriHandler > contentUriToHandlerLookup;
   
   private RtmDatabase database;
   
   
   
   @Override
   public boolean onCreate()
   {
      final Context context = getContext();
      
      log = SystemContext.get( context ).Log();
      contentUriToHandlerLookup = createContentUriToHandlerLookup();
      database = new RtmDatabase( context, log );
      
      return true;
   }
   
   
   
   @Override
   public Cursor query( Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder )
   {
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
      
      IContentUriHandler handler = new ContentUriHandlerTableAdapter( log,
                                                                      database.getTable( RtmTasksListsTable.TABLE_NAME ) );
      handlerLookup.put( handler, ContentUris.TASKS_LISTS_CONTENT_URI );
      handlerLookup.put( handler, ContentUris.TASKS_LISTS_CONTENT_URI_ID );
      
      final TasksContentUriHandler tasksContentUriHandler = new TasksContentUriHandler( log,
                                                                                        database.getWritable(),
                                                                                        database.getTable( RtmTaskSeriesTable.TABLE_NAME ),
                                                                                        database.getTable( RtmRawTasksTable.TABLE_NAME ) );
      handlerLookup.put( tasksContentUriHandler, ContentUris.TASKS_CONTENT_URI );
      handlerLookup.put( tasksContentUriHandler,
                         ContentUris.TASKS_CONTENT_URI_ID );
      
      handler = new TaskCountContentUriHandler( log, tasksContentUriHandler );
      handlerLookup.put( handler, ContentUris.TASKS_COUNT_CONTENT_URI );
      
      handler = new TaskNotesContentUriHandler( log,
                                                database.getTable( RtmNotesTable.TABLE_NAME ),
                                                tasksContentUriHandler );
      handlerLookup.put( handler, ContentUris.TASK_NOTES_CONTENT_URI );
      handlerLookup.put( handler, ContentUris.TASK_NOTES_CONTENT_URI_ID );
      
      handler = new TaskParticipantsContentUriHandler( log,
                                                       database.getTable( RtmParticipantsTable.TABLE_NAME ),
                                                       tasksContentUriHandler );
      handlerLookup.put( handler, ContentUris.TASK_PARTICIPANTS_CONTENT_URI );
      handlerLookup.put( handler, ContentUris.TASK_PARTICIPANTS_CONTENT_URI_ID );
      
      handler = new ContentUriHandlerTableAdapter( log,
                                                   database.getTable( RtmLocationsTable.TABLE_NAME ) );
      handlerLookup.put( handler, ContentUris.LOCATIONS_CONTENT_URI );
      handlerLookup.put( handler, ContentUris.LOCATIONS_CONTENT_URI_ID );
      
      handler = new TagsContentUriHandler( log, database.getReadable() );
      handlerLookup.put( handler, ContentUris.TAGS_CONTENT_URI );
      
      handler = new ContentUriHandlerTableAdapter( log,
                                                   database.getTable( RtmContactsTable.TABLE_NAME ) );
      handlerLookup.put( handler, ContentUris.CONTACTS_CONTENT_URI );
      handlerLookup.put( handler, ContentUris.CONTACTS_CONTENT_URI_ID );
      
      handler = new ContentUriHandlerTableAdapter( log,
                                                   database.getTable( ModificationsTable.TABLE_NAME ) );
      handlerLookup.put( handler, ContentUris.MODIFICATIONS_CONTENT_URI );
      
      handler = new ContentUriHandlerTableAdapter( log,
                                                   database.getTable( RtmSettingsTable.TABLE_NAME ) );
      handlerLookup.put( handler, ContentUris.RTM_SETTINGS_CONTENT_URI );
      
      handler = new ContentUriHandlerTableAdapter( log,
                                                   database.getTable( SyncTable.TABLE_NAME ) );
      handlerLookup.put( handler, ContentUris.SYNC_CONTENT_URI );
      
      return handlerLookup;
   }
   
   
   
   private void logOperationFailed( String operation, Exception e )
   {
      log.e( DbContentProvider.class,
             "Operation '" + operation + "' failed.",
             e );
   }
}
