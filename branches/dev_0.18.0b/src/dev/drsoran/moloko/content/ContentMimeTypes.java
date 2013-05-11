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

public final class ContentMimeTypes
{
   private ContentMimeTypes()
   {
      throw new AssertionError();
   }
   
   static
   {
      final UriLookup< String > lookup = new UriLookup< String >( ContentUris.MATCHER );
      
      lookup.put( ContentMimeTypes.TASKS_LIST_CONTENT_TYPE,
                  ContentUris.TASKS_LISTS_CONTENT_URI );
      lookup.put( ContentMimeTypes.TASKS_LIST_CONTENT_ITEM_TYPE,
                  ContentUris.TASKS_LISTS_CONTENT_URI_ID );
      lookup.put( ContentMimeTypes.TASK_CONTENT_TYPE,
                  ContentUris.TASKS_CONTENT_URI );
      lookup.put( ContentMimeTypes.TASK_CONTENT_ITEM_TYPE,
                  ContentUris.TASKS_CONTENT_URI_ID );
      lookup.put( ContentMimeTypes.TASK_NOTE_CONTENT_TYPE,
                  ContentUris.TASK_NOTES_CONTENT_URI );
      lookup.put( ContentMimeTypes.TASK_NOTES_CONTENT_ITEM_TYPE,
                  ContentUris.TASK_NOTES_CONTENT_URI_ID );
      lookup.put( ContentMimeTypes.TASK_PARTICIPANT_CONTENT_TYPE,
                  ContentUris.TASK_PARTICIPANTS_CONTENT_URI );
      lookup.put( ContentMimeTypes.TASK_PARTICIPANT_CONTENT_ITEM_TYPE,
                  ContentUris.TASK_PARTICIPANTS_CONTENT_URI_ID );
      lookup.put( ContentMimeTypes.LOCATION_CONTENT_TYPE,
                  ContentUris.LOCATIONS_CONTENT_URI );
      lookup.put( ContentMimeTypes.LOCATION_CONTENT_ITEM_TYPE,
                  ContentUris.LOCATIONS_CONTENT_URI_ID );
      lookup.put( ContentMimeTypes.CONTACT_CONTENT_TYPE,
                  ContentUris.CONTACTS_CONTENT_URI );
      lookup.put( ContentMimeTypes.CONTACT_CONTENT_ITEM_TYPE,
                  ContentUris.CONTACTS_CONTENT_URI_ID );
      lookup.put( ContentMimeTypes.TAGS_CONTENT_TYPE,
                  ContentUris.TAGS_CONTENT_URI );
      
      CONTENT_URI_MIME_TYPE_LOOKUP = lookup;
   }
   
   public static final UriLookup< String > CONTENT_URI_MIME_TYPE_LOOKUP;
   
   /**
    * The MIME type of providing a directory of lists.
    */
   public static final String TASKS_LIST_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.moloko.content.tasklist";
   
   /**
    * The MIME type of a sub-directory of a single list.
    */
   public static final String TASKS_LIST_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.moloko.content.taskslist";
   
   /**
    * The MIME type of providing a directory of tasks.
    */
   public static final String TASK_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.moloko.content.task";
   
   /**
    * The MIME type of a sub-directory of a single task.
    */
   public static final String TASK_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.moloko.content.task";
   
   /**
    * The MIME type of providing a directory of notes for a task.
    */
   public static final String TASK_NOTE_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.moloko.content.task.note";
   
   /**
    * The MIME type of a sub-directory of a single note for a task.
    */
   public static final String TASK_NOTES_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.moloko.content.task.note";
   
   /**
    * The MIME type of providing a directory of participants of a task.
    */
   public static final String TASK_PARTICIPANT_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.moloko.content.task.participant";
   
   /**
    * The MIME type of a sub-directory of a single participants of a task.
    */
   public static final String TASK_PARTICIPANT_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.moloko.content.task.participant";
   
   /**
    * The MIME type of providing the locations.
    */
   public static final String LOCATION_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.moloko.content.location";
   
   /**
    * The MIME type of a sub-directory of a single location.
    */
   public static final String LOCATION_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.moloko.content.location";
   
   /**
    * The MIME type of providing the contacts.
    */
   public static final String CONTACT_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.moloko.content.contact";
   
   /**
    * The MIME type of a sub-directory of a single contact.
    */
   public static final String CONTACT_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.moloko.content.contact";
   
   /**
    * The MIME type of a sub-directory of tags.
    */
   public static final String TAGS_CONTENT_TYPE = "vnd.android.cursor.item/vnd.moloko.content.tag";
}
