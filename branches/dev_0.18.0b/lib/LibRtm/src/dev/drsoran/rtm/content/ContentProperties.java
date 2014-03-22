/*
 * Copyright (c) 2010 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.rtm.content;

public final class ContentProperties
{
   public static class BaseProperties
   {
      public final static String ID = "id";
   }
   
   
   public static class RtmTasksListProperties extends BaseProperties
   {
      /**
       * The name of the list
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String NAME = "name";
      
      /**
       * Indicates if the list is deleted.
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - no</LI> <LI>!= 0 - yes</LI>
       */
      public final static String DELETED = "deleted";
      
      /**
       * Indicates if the list is locked.
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - no</LI> <LI>!= 0 - yes</LI>
       */
      public final static String LOCKED = "locked";
      
      /**
       * Indicates if the list is archived.
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - no</LI> <LI>!= 0 - yes</LI>
       */
      public final static String ARCHIVED = "archived";
      
      /**
       * Determines the list ordering
       * <P>
       * Type: INTEGER
       * </P>
       */
      public final static String POSITION = "position";
      
      /**
       * Indicates if the list is a smart list
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - no</LI> <LI>!= 0 - yes</LI>
       */
      public final static String IS_SMART_LIST = "smart";
      
      /**
       * The smart filer for this list
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String FILTER = "filter";
   }
   
   
   public static class RtmTaskProperties extends BaseProperties
   {
      /**
       * The created date of the task
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String CREATED_DATE = "created";
      
      /**
       * The modified date of the task
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String MODIFIED_DATE = "modified";
      
      /**
       * The name of the task
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String NAME = "name";
      
      /**
       * The source that entered the task
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String SOURCE = "source";
      
      /**
       * A URL attached to the task
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String URL = "url";
      
      /**
       * A recurrence pattern for the task.
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String RECURRENCE = "recurrence";
      
      /**
       * Indicates the type of the recurrence pattern.
       * <P>
       * Type: INTEGER
       * </P>
       * <UI> <LI>0 - indicates an 'after' pattern</LI> <LI>1 - indicates an 'every' pattern</LI> </UI>
       */
      public final static String RECURRENCE_EVERY = "every";
      
      /**
       * The ID of the location of this task.
       * <P>
       * Type: INTEGER
       * </P>
       */
      public final static String LOCATION_ID = "location_id";
      
      /**
       * The ID of the list this task is in.
       * <P>
       * Type: INTEGER (long)
       * </P>
       */
      public final static String LIST_ID = "list_id";
      
      /**
       * A {@link TAGS_SEPARATOR} separated list of tags.
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String TAGS = "tags";
      
      /**
       * The separator used to separate a the tags.
       */
      public final static String TAGS_SEPARATOR = ",";
      
      /**
       * The due date of the task
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String DUE_DATE = "due";
      
      /**
       * Indicates if the task has an explicit due time and not only a due date
       * <P>
       * Type: INTEGER
       * </P>
       */
      public final static String HAS_DUE_TIME = "has_due_time";
      
      /**
       * The date when the task was added
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String ADDED_DATE = "added";
      
      /**
       * The date when the task has been completed
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String COMPLETED_DATE = "completed";
      
      /**
       * The date when the task was deleted
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String DELETED_DATE = "deleted";
      
      /**
       * The task's priority
       * <P>
       * Type: CHAR(1)
       * </P>
       * <LI>'n' - none</LI> <LI>'1' - high</LI> <LI>'2' - medium</LI> <LI>'3' - low</LI>
       */
      public final static String PRIORITY = "priority";
      
      /**
       * Indicates if the task is postponed
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - no</LI> <LI>!= 0 - yes</LI>
       */
      public final static String POSTPONED = "postponed";
      
      /**
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String ESTIMATE = "estimate";
   }
   
   
   public static class RtmNoteProperties extends BaseProperties
   {
      /**
       * The created date of the note
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String CREATED_DATE = "created";
      
      /**
       * The modified date of the note
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String MODIFIED_DATE = "modified";
      
      /**
       * The title of the note
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String TITLE = "title";
      
      /**
       * The text of the note
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String TEXT = "text";
   }
   
   
   public static class RtmContactProperties extends BaseProperties
   {
      /**
       * The full name of the contact
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String FULLNAME = "fullname";
      
      /**
       * The RTM username of the contact
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String USERNAME = "username";
   }
   
   
   public static class RtmLocationProperties extends BaseProperties
   {
      /**
       * The name of the location
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String NAME = "name";
      
      /**
       * The longitude of the location
       * <P>
       * Type: FLOAT
       * </P>
       */
      public final static String LONGITUDE = "longitude";
      
      /**
       * The latitude of the location
       * <P>
       * Type: FLOAT
       * </P>
       */
      public final static String LATITUDE = "latitude";
      
      /**
       * The address of the location
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String ADDRESS = "address";
      
      /**
       * Is the location viewable
       * <P>
       * Type: INTEGER
       * </P>
       * 
       * <UL>
       * <LI>0 - no</LI>
       * <LI>!= 0 - yes</LI>
       * </UL>
       */
      public final static String VIEWABLE = "viewable";
      
      /**
       * The zoom of the location
       * <P>
       * Type: INTEGER
       * </P>
       */
      public final static String ZOOM = "zoom";
   }
   
   
   public static class RtmSettingsProperties
   {
      /**
       * The user's timezone. Null if the user has not set a timezone
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String TIMEZONE = "timezone";
      
      /**
       * The date format the user has set
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - indicates an European date format (e.g. 14/02/06)</LI> <LI>1 - indicates an American date format (e.g.
       * 02/14/06)</LI>
       */
      public final static String DATEFORMAT = "dateformat";
      
      /**
       * The time format the user has set
       * <P>
       * Type: INTEGER
       * </P>
       * <LI>0 - indicates 12 hour time with day period (e.g. 5pm)</LI> <LI>1 - indicates 24 hour time (e.g. 17:00)</LI>
       */
      public final static String TIMEFORMAT = "timeformat";
      
      /**
       * The ID of the default list the user set.
       * <P>
       * Type: INTEGER (long), NULL if not set.
       * </P>
       */
      public final static String DEFAULTLIST = "defaultlist";
      
      /**
       * The user's language (ISO 639-1 code), NULL is not set
       * <P>
       * Type: STRING
       * </P>
       */
      public final static String LANGUAGE = "language";
   }
}
