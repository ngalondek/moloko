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
   private static final String TASKS_LISTS_URI_PATH = "lists";
   
   private static final String TASKS_LISTS_ID_URI_PATH = "lists/#";
   
   private static final String TASKS_URI_PATH = "tasks";
   
   private static final String TASKS_ID_URI_PATH = "tasks/#";
   
   private static final String TASKS_COUNT_URI_PATH = "tasks/count";
   
   private static final String TASK_NOTES_URI_PATH = "tasks/#/notes";
   
   private static final String TASK_NOTES_ID_URI_PATH = "tasks/#/notes/#";
   
   private static final String TASK_PARTICIPANTS_URI_PATH = "tasks/#/participants";
   
   private static final String TASK_PARTICIPANTS_ID_URI_PATH = "tasks/#/participants/#";
   
   private static final String LOCATIONS_URI_PATH = "locations";
   
   private static final String LOCATIONS_ID_URI_PATH = "locations/#";
   
   private static final String CONTACTS_URI_PATH = "contacts";
   
   private static final String CONTACTS_ID_URI_PATH = "contacts/#";
   
   private static final String TAGS_URI_PATH = "tags";
   
   private static final String CLOUD_ENTRIES_URI_PATH = "cloud_entries";
   
   private static final String MODIFICATIONS_URI_PATH = "modifications";
   
   private static final String MODIFICATIONS_ID_URI_PATH = "modifications/#";
   
   private static final String RTM_SETTINGS_URI_PATH = "rtm_settings";
   
   private static final String RTM_SETTINGS_ID_URI_PATH = "rtm_settings/#";
   
   private static final String SYNC_URI_PATH = "sync";
   
   private static final String SYNC_ID_URI_PATH = "sync/#";
   
   
   
   private ContentUris()
   {
      throw new AssertionError();
   }
   
   static
   {
      TASKS_LISTS_CONTENT_URI = buildUri( TASKS_LISTS_URI_PATH );
      
      TASKS_LISTS_CONTENT_URI_ID = buildUri( TASKS_LISTS_ID_URI_PATH );
      
      TASKS_CONTENT_URI = buildUri( TASKS_URI_PATH );
      
      TASKS_CONTENT_URI_ID = buildUri( TASKS_ID_URI_PATH );
      
      TASKS_COUNT_CONTENT_URI = buildUri( TASKS_COUNT_URI_PATH );
      
      TASK_NOTES_CONTENT_URI = buildUri( TASK_NOTES_URI_PATH );
      
      TASK_NOTES_CONTENT_URI_ID = buildUri( TASK_NOTES_ID_URI_PATH );
      
      TASK_PARTICIPANTS_CONTENT_URI = buildUri( TASK_PARTICIPANTS_URI_PATH );
      
      TASK_PARTICIPANTS_CONTENT_URI_ID = buildUri( TASK_PARTICIPANTS_ID_URI_PATH );
      
      LOCATIONS_CONTENT_URI = buildUri( LOCATIONS_URI_PATH );
      
      LOCATIONS_CONTENT_URI_ID = buildUri( LOCATIONS_ID_URI_PATH );
      
      CONTACTS_CONTENT_URI = buildUri( CONTACTS_URI_PATH );
      
      CONTACTS_CONTENT_URI_ID = buildUri( CONTACTS_ID_URI_PATH );
      
      TAGS_CONTENT_URI = buildUri( TAGS_URI_PATH );
      
      CLOUD_ENTRIES_CONTENT_URI = buildUri( CLOUD_ENTRIES_URI_PATH );
      
      MODIFICATIONS_CONTENT_URI = buildUri( MODIFICATIONS_URI_PATH );
      
      MODIFICATIONS_CONTENT_URI_ID = buildUri( MODIFICATIONS_ID_URI_PATH );
      
      RTM_SETTINGS_CONTENT_URI = buildUri( RTM_SETTINGS_URI_PATH );
      
      RTM_SETTINGS_CONTENT_URI_ID = buildUri( RTM_SETTINGS_ID_URI_PATH );
      
      SYNC_CONTENT_URI = buildUri( SYNC_URI_PATH );
      
      SYNC_CONTENT_URI_ID = buildUri( SYNC_ID_URI_PATH );
      
      final UriMatcher uriMatcher = new UriMatcher( UriMatcher.NO_MATCH );
      uriMatcher.addURI( RTM,
                         TASKS_LISTS_URI_PATH,
                         ContentUris.MATCH_TASKS_LISTS );
      uriMatcher.addURI( RTM,
                         TASKS_LISTS_ID_URI_PATH,
                         ContentUris.MATCH_TASKS_LISTS_ID );
      uriMatcher.addURI( RTM, TASKS_URI_PATH, ContentUris.MATCH_TASKS );
      uriMatcher.addURI( RTM, TASKS_ID_URI_PATH, ContentUris.MATCH_TASKS_ID );
      uriMatcher.addURI( RTM,
                         TASKS_COUNT_URI_PATH,
                         ContentUris.MATCH_TASKS_COUNT );
      uriMatcher.addURI( RTM, TASK_NOTES_URI_PATH, ContentUris.MATCH_TASK_NOTES );
      uriMatcher.addURI( RTM,
                         TASK_NOTES_ID_URI_PATH,
                         ContentUris.MATCH_TASK_NOTES_ID );
      uriMatcher.addURI( RTM,
                         TASK_PARTICIPANTS_URI_PATH,
                         ContentUris.MATCH_TASK_PARTICIPANTS );
      uriMatcher.addURI( RTM,
                         TASK_PARTICIPANTS_ID_URI_PATH,
                         ContentUris.MATCH_TASK_PARTICIPANTS_ID );
      uriMatcher.addURI( RTM, LOCATIONS_URI_PATH, ContentUris.MATCH_LOCATIONS );
      uriMatcher.addURI( RTM,
                         LOCATIONS_ID_URI_PATH,
                         ContentUris.MATCH_LOCATIONS_ID );
      uriMatcher.addURI( RTM, CONTACTS_URI_PATH, ContentUris.MATCH_CONTACTS );
      uriMatcher.addURI( RTM,
                         CONTACTS_ID_URI_PATH,
                         ContentUris.MATCH_CONTACTS_ID );
      uriMatcher.addURI( RTM, TAGS_URI_PATH, ContentUris.MATCH_TAGS );
      uriMatcher.addURI( RTM,
                         CLOUD_ENTRIES_URI_PATH,
                         ContentUris.MATCH_CLOUD_ENTRIES );
      uriMatcher.addURI( RTM,
                         MODIFICATIONS_URI_PATH,
                         ContentUris.MATCH_MODIFICATIONS );
      uriMatcher.addURI( RTM,
                         MODIFICATIONS_ID_URI_PATH,
                         ContentUris.MATCH_MODIFICATIONS_ID );
      uriMatcher.addURI( RTM,
                         RTM_SETTINGS_URI_PATH,
                         ContentUris.MATCH_RTM_SETTINGS );
      uriMatcher.addURI( RTM,
                         RTM_SETTINGS_ID_URI_PATH,
                         ContentUris.MATCH_RTM_SETTINGS_ID );
      uriMatcher.addURI( RTM, SYNC_URI_PATH, ContentUris.MATCH_SYNC );
      uriMatcher.addURI( RTM, SYNC_ID_URI_PATH, ContentUris.MATCH_SYNC_ID );
      
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
   
   public final static int MATCH_MODIFICATIONS_ID = 16;
   
   public final static int MATCH_RTM_SETTINGS = 17;
   
   public final static int MATCH_RTM_SETTINGS_ID = 18;
   
   public final static int MATCH_SYNC = 19;
   
   public final static int MATCH_SYNC_ID = 20;
   
   public final static int MATCH_CLOUD_ENTRIES = 21;
   
   public static final Uri TASKS_LISTS_CONTENT_URI;
   
   public static final Uri TASKS_LISTS_CONTENT_URI_ID;
   
   public static final Uri TASKS_CONTENT_URI;
   
   public static final Uri TASKS_CONTENT_URI_ID;
   
   public static final Uri TASKS_COUNT_CONTENT_URI;
   
   public static final Uri TASK_NOTES_CONTENT_URI;
   
   public static final Uri TASK_NOTES_CONTENT_URI_ID;
   
   public static final Uri TASK_PARTICIPANTS_CONTENT_URI;
   
   public static final Uri TASK_PARTICIPANTS_CONTENT_URI_ID;
   
   public static final Uri LOCATIONS_CONTENT_URI;
   
   public static final Uri LOCATIONS_CONTENT_URI_ID;
   
   public static final Uri CONTACTS_CONTENT_URI;
   
   public static final Uri CONTACTS_CONTENT_URI_ID;
   
   public static final Uri TAGS_CONTENT_URI;
   
   public static final Uri CLOUD_ENTRIES_CONTENT_URI;
   
   public static final Uri MODIFICATIONS_CONTENT_URI;
   
   public static final Uri MODIFICATIONS_CONTENT_URI_ID;
   
   public static final Uri RTM_SETTINGS_CONTENT_URI;
   
   public static final Uri RTM_SETTINGS_CONTENT_URI_ID;
   
   public static final Uri SYNC_CONTENT_URI;
   
   public static final Uri SYNC_CONTENT_URI_ID;
   
   
   
   public static List< Long > getIdsFromUri( Uri uri )
   {
      if ( uri == null )
      {
         throw new IllegalArgumentException( "uri" );
      }
      
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
      final List< Long > ids = getIdsFromUri( uri );
      if ( ids.isEmpty() )
      {
         return Constants.NO_ID;
      }
      
      return ids.get( 0 ).longValue();
   }
   
   
   
   public static Uri bindAggregationIdToUri( Uri uri, long aggregationRootId )
   {
      if ( uri == null )
      {
         throw new IllegalArgumentException( "uri" );
      }
      
      final String boundUriString = uri.toString()
                                       .replaceFirst( "#",
                                                      String.valueOf( aggregationRootId ) );
      return Uri.parse( boundUriString );
   }
   
   
   
   public static Uri bindAggregatedElementIdToUri( Uri uri,
                                                   long aggregationRootId,
                                                   long elementId ) throws IllegalArgumentException
   {
      if ( uri == null )
      {
         throw new IllegalArgumentException( "uri" );
      }
      
      final StringBuilder stringBuilder = new StringBuilder( uri.toString() );
      
      final int posRootId = stringBuilder.indexOf( "#" );
      if ( posRootId == -1 )
      {
         throw new IllegalArgumentException( "The URI '"
            + uri
            + "' did not contains a place holder '#' for the aggregation root ID." );
      }
      
      stringBuilder.replace( posRootId,
                             posRootId + 1,
                             String.valueOf( aggregationRootId ) );
      
      final int posElementId = stringBuilder.indexOf( "#", posRootId + 1 );
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
   
   
   
   public static long getLastPathIdFromUri( Uri uri )
   {
      if ( uri == null )
      {
         throw new IllegalArgumentException( "uri" );
      }
      
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
      if ( uri == null )
      {
         throw new IllegalArgumentException( "uri" );
      }
      
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
   
   
   
   private static Uri buildUri( String path )
   {
      return new Uri.Builder().scheme( SCHEME_CONTENT )
                              .authority( RTM )
                              .appendEncodedPath( path )
                              .build();
   }
}
