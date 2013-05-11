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

import static android.content.ContentResolver.SCHEME_CONTENT;
import static dev.drsoran.moloko.content.ContentAuthority.RTM;

import java.util.ArrayList;
import java.util.List;

import android.content.UriMatcher;
import android.net.Uri;


public final class ContentUris
{
   private ContentUris()
   {
      throw new AssertionError();
   }
   
   static
   {
      UriMatcher uriMatcher = new UriMatcher( UriMatcher.NO_MATCH );
      uriMatcher.addURI( RTM,
                         ContentUris.TASKS_LISTS_CONTENT_URI.getPath(),
                         ContentUris.MATCH_TASKS_LISTS );
      uriMatcher.addURI( RTM,
                         ContentUris.TASKS_LISTS_CONTENT_URI_ID.getPath(),
                         ContentUris.MATCH_TASKS_LISTS_ID );
      uriMatcher.addURI( RTM,
                         ContentUris.TASKS_CONTENT_URI.getPath(),
                         ContentUris.MATCH_TASKS );
      uriMatcher.addURI( RTM,
                         ContentUris.TASKS_CONTENT_URI_ID.getPath(),
                         ContentUris.MATCH_TASKS_ID );
      uriMatcher.addURI( RTM,
                         ContentUris.TASKS_COUNT_CONTENT_URI.getPath(),
                         ContentUris.MATCH_TASKS_COUNT );
      uriMatcher.addURI( RTM,
                         ContentUris.TASK_NOTES_CONTENT_URI.getPath(),
                         ContentUris.MATCH_TASK_NOTES );
      uriMatcher.addURI( RTM,
                         ContentUris.TASK_NOTES_CONTENT_URI_ID.getPath(),
                         ContentUris.MATCH_TASK_NOTES_ID );
      uriMatcher.addURI( RTM,
                         ContentUris.TASK_PARTICIPANTS_CONTENT_URI.getPath(),
                         ContentUris.MATCH_TASK_PARTICIPANTS );
      uriMatcher.addURI( RTM,
                         ContentUris.TASK_PARTICIPANTS_CONTENT_URI_ID.getPath(),
                         ContentUris.MATCH_TASK_PARTICIPANTS_ID );
      uriMatcher.addURI( RTM,
                         ContentUris.LOCATIONS_CONTENT_URI.getPath(),
                         ContentUris.MATCH_LOCATIONS );
      uriMatcher.addURI( RTM,
                         ContentUris.LOCATIONS_CONTENT_URI_ID.getPath(),
                         ContentUris.MATCH_LOCATIONS_ID );
      uriMatcher.addURI( RTM,
                         ContentUris.CONTACTS_CONTENT_URI.getPath(),
                         ContentUris.MATCH_CONTACTS );
      uriMatcher.addURI( RTM,
                         ContentUris.CONTACTS_CONTENT_URI_ID.getPath(),
                         ContentUris.MATCH_CONTACTS_ID );
      uriMatcher.addURI( RTM,
                         ContentUris.TAGS_CONTENT_URI.getPath(),
                         ContentUris.MATCH_TAGS );
      uriMatcher.addURI( RTM,
                         ContentUris.MODIFICATIONS_CONTENT_URI.getPath(),
                         ContentUris.MATCH_MODIFICATIONS );
      
      MATCHER = uriMatcher;
   }
   
   public final static UriMatcher MATCHER;
   
   public final static int MATCH_TASKS_LISTS = 1;
   
   public final static int MATCH_TASKS_LISTS_ID = 2;
   
   public final static int MATCH_TASKS = 3;
   
   public final static int MATCH_TASKS_ID = 4;
   
   public final static int MATCH_TASKS_COUNT = 5;
   
   public final static int MATCH_TASK_NOTES = 6;
   
   public final static int MATCH_TASK_NOTES_ID = 7;
   
   public final static int MATCH_TASK_PARTICIPANTS = 8;
   
   public final static int MATCH_TASK_PARTICIPANTS_ID = 9;
   
   public final static int MATCH_LOCATIONS = 10;
   
   public final static int MATCH_LOCATIONS_ID = 11;
   
   public final static int MATCH_CONTACTS = 12;
   
   public final static int MATCH_CONTACTS_ID = 13;
   
   public final static int MATCH_TAGS = 14;
   
   public final static int MATCH_MODIFICATIONS = 15;
   
   public final static int MATCH_RTM_SETTINGS = 16;
   
   public final static int MATCH_SYNC = 17;
   
   public static final Uri TASKS_LISTS_CONTENT_URI = new Uri.Builder().scheme( SCHEME_CONTENT )
                                                                      .authority( RTM )
                                                                      .path( "lists" )
                                                                      .build();
   
   public static final Uri TASKS_LISTS_CONTENT_URI_ID = ContentUris.TASKS_LISTS_CONTENT_URI.buildUpon()
                                                                                           .appendEncodedPath( "#" )
                                                                                           .build();
   
   public static final Uri TASKS_CONTENT_URI = new Uri.Builder().scheme( SCHEME_CONTENT )
                                                                .authority( RTM )
                                                                .path( "tasks" )
                                                                .build();
   
   public static final Uri TASKS_CONTENT_URI_ID = ContentUris.TASKS_CONTENT_URI.buildUpon()
                                                                               .appendEncodedPath( "#" )
                                                                               .build();
   
   public static final Uri TASKS_COUNT_CONTENT_URI = ContentUris.TASKS_CONTENT_URI.buildUpon()
                                                                                  .appendEncodedPath( "count" )
                                                                                  .build();
   
   public static final Uri TASK_NOTES_CONTENT_URI = ContentUris.TASKS_CONTENT_URI_ID.buildUpon()
                                                                                    .appendEncodedPath( "notes" )
                                                                                    .build();
   
   public static final Uri TASK_NOTES_CONTENT_URI_ID = ContentUris.TASK_NOTES_CONTENT_URI.buildUpon()
                                                                                         .appendEncodedPath( "#" )
                                                                                         .build();
   
   public static final Uri TASK_PARTICIPANTS_CONTENT_URI = ContentUris.TASKS_CONTENT_URI_ID.buildUpon()
                                                                                           .appendEncodedPath( "participants" )
                                                                                           .build();
   
   public static final Uri TASK_PARTICIPANTS_CONTENT_URI_ID = ContentUris.TASK_PARTICIPANTS_CONTENT_URI.buildUpon()
                                                                                                       .appendEncodedPath( "#" )
                                                                                                       .build();
   
   public static final Uri LOCATIONS_CONTENT_URI = new Uri.Builder().scheme( SCHEME_CONTENT )
                                                                    .authority( RTM )
                                                                    .path( "locations" )
                                                                    .build();
   
   public static final Uri LOCATIONS_CONTENT_URI_ID = ContentUris.LOCATIONS_CONTENT_URI.buildUpon()
                                                                                       .appendEncodedPath( "#" )
                                                                                       .build();
   
   public static final Uri CONTACTS_CONTENT_URI = new Uri.Builder().scheme( SCHEME_CONTENT )
                                                                   .authority( RTM )
                                                                   .path( "contacts" )
                                                                   .build();
   
   public static final Uri CONTACTS_CONTENT_URI_ID = ContentUris.CONTACTS_CONTENT_URI.buildUpon()
                                                                                     .appendEncodedPath( "#" )
                                                                                     .build();
   
   public static final Uri TAGS_CONTENT_URI = new Uri.Builder().scheme( SCHEME_CONTENT )
                                                               .authority( RTM )
                                                               .path( "tags" )
                                                               .build();
   
   public static final Uri MODIFICATIONS_CONTENT_URI = new Uri.Builder().scheme( SCHEME_CONTENT )
                                                                        .authority( RTM )
                                                                        .path( "modifications" )
                                                                        .build();
   
   public static final Uri RTM_SETTINGS_CONTENT_URI = new Uri.Builder().scheme( SCHEME_CONTENT )
                                                                       .authority( RTM )
                                                                       .path( "rtm_settings" )
                                                                       .build();
   
   public static final Uri SYNC_CONTENT_URI = new Uri.Builder().scheme( SCHEME_CONTENT )
                                                               .authority( RTM )
                                                               .path( "sync" )
                                                               .build();
   
   
   
   public static List< Long > getIdsFromUri( Uri uri )
   {
      final List< Long > ids = new ArrayList< Long >();
      
      for ( String part : uri.getPathSegments() )
      {
         try
         {
            ids.add( Long.parseLong( part ) );
         }
         catch ( NumberFormatException e )
         {
         }
      }
      
      return ids;
   }
   
   
   
   public static long getTaskIdFromUri( Uri uri )
   {
      return getIdsFromUri( uri ).get( 0 ).longValue();
   }
   
   
   
   public static Uri bindAggregationIdToUri( Uri uri, long aggregationRootId )
   {
      final String boundUriString = uri.toString()
                                       .replaceFirst( "#",
                                                      String.valueOf( aggregationRootId ) );
      return Uri.parse( boundUriString );
   }
   
   
   
   public static Uri bindAggregatedElementIdToUri( Uri uri,
                                                   long aggregationRootId,
                                                   long elementId ) throws IllegalArgumentException
   {
      final StringBuilder stringBuilder = new StringBuilder( uri.toString() );
      
      final int posRootId = stringBuilder.indexOf( "#" );
      if ( posRootId == -1 )
      {
         throw new IllegalArgumentException( "The URI '"
            + uri
            + "' did not contains a place holder '#' for the aggregation root ID." );
      }
      
      final int posElementId = stringBuilder.indexOf( "#", posRootId + 1 );
      if ( posElementId == -1 )
      {
         throw new IllegalArgumentException( "The URI '" + uri
            + "' did not contains a place holder '#' for the element ID." );
      }
      
      stringBuilder.replace( posRootId,
                             posRootId + 1,
                             String.valueOf( aggregationRootId ) );
      stringBuilder.replace( posElementId,
                             posElementId + 1,
                             String.valueOf( elementId ) );
      
      return Uri.parse( stringBuilder.toString() );
   }
   
   
   
   public static long getLastPathIdFromUri( Uri uri )
   {
      final String idString = uri.getLastPathSegment();
      try
      {
         return idString == null ? Constants.NO_ID : Long.parseLong( idString );
      }
      catch ( NumberFormatException e )
      {
         return Constants.NO_ID;
      }
   }
   
   
   
   public static Uri bindElementId( Uri uri, long elementId )
   {
      final StringBuilder stringBuilder = new StringBuilder( uri.toString() );
      final int posElementId = stringBuilder.lastIndexOf( "#" );
      if ( posElementId == -1 )
      {
         throw new IllegalArgumentException( "The URI '" + uri
            + "' did not contains a place holder '#' for the element ID." );
      }
      
      stringBuilder.replace( posElementId,
                             posElementId + 1,
                             String.valueOf( elementId ) );
      
      return Uri.parse( stringBuilder.toString() );
   }
}
