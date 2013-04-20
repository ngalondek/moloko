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
                         ContentUris.TASKS_MINIMAL_CONTENT_URI.getPath(),
                         ContentUris.MATCH_TASKS_MINIMAL );
      uriMatcher.addURI( RTM,
                         ContentUris.TASKS_MINIMAL_CONTENT_URI_ID.getPath(),
                         ContentUris.MATCH_TASKS_MINIMAL_ID );
      uriMatcher.addURI( RTM,
                         ContentUris.TAGS_CONTENT_URI.getPath(),
                         ContentUris.MATCH_TAGS );
      uriMatcher.addURI( RTM,
                         ContentUris.CONTACTS_CONTENT_URI.getPath(),
                         ContentUris.MATCH_CONTACTS );
      
      MATCHER = uriMatcher;
   }
   
   public final static UriMatcher MATCHER;
   
   public final static int MATCH_TASKS_LISTS = 1;
   
   public final static int MATCH_TASKS_LISTS_ID = 2;
   
   public final static int MATCH_TASKS = 3;
   
   public final static int MATCH_TASKS_ID = 4;
   
   public final static int MATCH_TASKS_MINIMAL = 5;
   
   public final static int MATCH_TASKS_MINIMAL_ID = 6;
   
   public final static int MATCH_TAGS = 7;
   
   public final static int MATCH_CONTACTS = 8;
   
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
   
   public static final Uri TASKS_MINIMAL_CONTENT_URI = new Uri.Builder().scheme( SCHEME_CONTENT )
                                                                        .authority( RTM )
                                                                        .path( "tasks_minimal" )
                                                                        .build();
   
   public static final Uri TASKS_MINIMAL_CONTENT_URI_ID = ContentUris.TASKS_MINIMAL_CONTENT_URI.buildUpon()
                                                                                               .appendEncodedPath( "#" )
                                                                                               .build();
   
   public static final Uri TAGS_CONTENT_URI = new Uri.Builder().scheme( SCHEME_CONTENT )
                                                               .authority( RTM )
                                                               .path( "tags" )
                                                               .build();
   
   public static final Uri CONTACTS_CONTENT_URI = new Uri.Builder().scheme( SCHEME_CONTENT )
                                                                   .authority( RTM )
                                                                   .path( "contacts" )
                                                                   .build();
}
