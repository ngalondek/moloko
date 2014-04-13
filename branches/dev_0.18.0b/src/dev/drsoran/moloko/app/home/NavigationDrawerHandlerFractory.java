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

package dev.drsoran.moloko.app.home;

import android.net.Uri;
import dev.drsoran.moloko.app.baseactivities.MolokoActivity;
import dev.drsoran.moloko.app.contactslist.ContactsListNavigationDrawerHandler;
import dev.drsoran.moloko.app.tagcloud.TagCloudNavigationDrawerHandler;
import dev.drsoran.moloko.app.taskslist.TasksListNavigationDrawerHandler;
import dev.drsoran.moloko.content.ContentUris;


class NavigationDrawerHandlerFractory
{
   public static INavigationDrawerHandler create( MolokoActivity activity,
                                                  Uri intentUri )
   {
      switch ( ContentUris.MATCHER.match( intentUri ) )
      {
         case ContentUris.MATCH_TASKS:
         case ContentUris.MATCH_TASKS_LISTS_ID:
            return new TasksListNavigationDrawerHandler( activity,
                                                         android.R.id.widget_frame );
            
         case ContentUris.MATCH_TAGS:
            return new TagCloudNavigationDrawerHandler( activity,
                                                        android.R.id.widget_frame );
            
         case ContentUris.MATCH_CONTACTS:
            return new ContactsListNavigationDrawerHandler( activity,
                                                            android.R.id.widget_frame );
            
         default :
            return null;
      }
   }
}
