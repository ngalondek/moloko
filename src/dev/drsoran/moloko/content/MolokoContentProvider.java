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

package dev.drsoran.moloko.content;

import java.text.MessageFormat;
import java.util.NoSuchElementException;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import dev.drsoran.Strings;
import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.content.db.DbContentProvider;


public abstract class MolokoContentProvider extends ContentProvider
{
   private ILog log;
   
   private UriLookup< IContentUriHandler > contentUriToHandlerLookup;
   
   
   
   /**
    * Note: This is called by the OS before any Application onCreate(). So referring to App resources is not valid here.
    */
   @Override
   public boolean onCreate()
   {
      contentUriToHandlerLookup = createContentUriToHandlerLookup();
      return true;
   }
   
   
   
   public void init( ILog log )
   {
      this.log = log;
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
   
   
   
   public ILog Log()
   {
      return log;
   }
   
   
   
   private void logOperationFailed( String operation, Exception e )
   {
      Log().e( DbContentProvider.class,
               MessageFormat.format( "Operation ''{0}'' failed.", operation ),
               e );
   }
   
   
   
   protected abstract UriLookup< IContentUriHandler > createContentUriToHandlerLookup();
}
