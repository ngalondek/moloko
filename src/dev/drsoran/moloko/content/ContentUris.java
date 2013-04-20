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
      uriMatcher.addURI( RTM, "lists", ContentUris.MATCH_TASKS_LISTS );
      uriMatcher.addURI( RTM, "lists/#", ContentUris.MATCH_TASKS_LISTS_ID );
      uriMatcher.addURI( RTM, "tasks", ContentUris.MATCH_TASKS );
      uriMatcher.addURI( RTM, "tasks/#", ContentUris.MATCH_TASKS_ID );
      uriMatcher.addURI( RTM, "tags", ContentUris.MATCH_TAGS );
      uriMatcher.addURI( RTM, "contacts", ContentUris.MATCH_CONTACTS );
      
      MATCHER = uriMatcher;
   }
   
   public final static UriMatcher MATCHER;
   
   public final static int MATCH_TASKS_LISTS = 1;
   
   public final static int MATCH_TASKS_LISTS_ID = 2;
   
   public final static int MATCH_TASKS = 3;
   
   public final static int MATCH_TASKS_ID = 4;
   
   public final static int MATCH_TAGS = 5;
   
   public final static int MATCH_CONTACTS = 6;
   
   public static final Uri TASKS_LISTS_CONTENT_URI = new Uri.Builder().scheme( SCHEME_CONTENT )
                                                                      .authority( RTM )
                                                                      .path( "lists" )
                                                                      .build();
   
   public static final Uri TASK_CONTENT_URI = new Uri.Builder().scheme( SCHEME_CONTENT )
                                                               .authority( RTM )
                                                               .path( "tasks" )
                                                               .build();
   
   public static final Uri TAGS_CONTENT_URI = new Uri.Builder().scheme( SCHEME_CONTENT )
                                                               .authority( RTM )
                                                               .path( "tags" )
                                                               .build();
   
   public static final Uri CONTACTS_CONTENT_URI = new Uri.Builder().scheme( SCHEME_CONTENT )
                                                                   .authority( RTM )
                                                                   .path( "contacts" )
                                                                   .build();
   
   
   
   public static Uri contentUriWithId( Uri contentUri, long id )
   {
      return android.content.ContentUris.withAppendedId( contentUri, id );
   }
}
