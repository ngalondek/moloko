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
   
   /**
    * The MIME type of providing a directory of lists.
    */
   public static final String TASKS_LISTS_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.rtm.list";
   
   /**
    * The MIME type of a sub-directory of a single list.
    */
   public static final String TASKS_LIST_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rtm.list";
   
   /**
    * The MIME type of providing a directory of tasks.
    */
   public static final String TASK_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.rtm.task";
   
   /**
    * The MIME type of a sub-directory of a single task.
    */
   public static final String TASKS_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rtm.task";
   
   /**
    * The MIME type of providing a directory of tags.
    */
   public static final String TAGS_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.rtm.tag";
   
   /**
    * The MIME type of providing the contacts.
    */
   public static final String CONTACTS_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.rtm.contact";
   
   /**
    * The MIME type of a sub-directory of a single contact.
    */
   public static final String CONTACT_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rtm.contact";
}
