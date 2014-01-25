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

package dev.drsoran.rtm.sync.db;

import android.provider.BaseColumns;


public final class TableColumns
{
   public static class TimesColumns implements BaseColumns
   {
      /**
       * The time stamp of the last synchronization RTM -> local
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String LAST_IN = "last_in";
      
      public final static int LAST_IN_IDX = 1;
      
      /**
       * The time stamp of the last synchronization local -> RTM
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String LAST_OUT = "last_out";
      
      public final static int LAST_OUT_IDX = 2;
      
      public final static String[] PROJECTION =
      { _ID, LAST_IN, LAST_OUT };
   }
   
   
   public static class ModificationColumns implements BaseColumns
   {
      /**
       * Designates the entity that has been modified by it's Content URI with ID.
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String SRC_ENTITY_URI = "src_entity_uri";
      
      public final static int SRC_ENTITY_URI_IDX = 1;
      
      /**
       * Designates the entity that should be modified by it's Content URI with ID.
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String DST_ENTITY_URI = "dst_entity_uri";
      
      public final static int DST_ENTITY_URI_IDX = 2;
      
      /**
       * The name of the modified attribute of the entity.
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String ATTRIBUTE = "attribute";
      
      public final static int ATTRIBUTE_IDX = 3;
      
      /**
       * The new value.
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String NEW_VALUE = "new_value";
      
      public final static int NEW_VALUE_IDX = 3;
      
      /**
       * The last synchronized value with the RTM server.
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String SYNCED_VALUE = "synced_value";
      
      public final static int SYNCED_VALUE_IDX = 4;
      
      /**
       * The point in time when the modification was inserted.
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String TIMESTAMP = "timestamp";
      
      public final static int TIMESTAMP_IDX = 5;
      
      public final static String DEFAULT_SORT_ORDER = null;
      
      public final static String[] PROJECTION =
      { _ID, SRC_ENTITY_URI, DST_ENTITY_URI, ATTRIBUTE, NEW_VALUE,
       SYNCED_VALUE, TIMESTAMP };
   }
   
   
   public static class CreationsColumns implements BaseColumns
   {
      /**
       * Designates the entity that has been created by it's Content URI with ID.
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String SRC_ENTITY_URI = "src_entity_uri";
      
      public final static int SRC_ENTITY_URI_IDX = 1;
      
      /**
       * Designates the entity that should be created by it's Content URI with ID.
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String DST_ENTITY_URI = "dst_entity_uri";
      
      public final static int DST_ENTITY_URI_IDX = 2;
      
      /**
       * The point of time when the entity was created.
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String TIMESTAMP = "timestamp";
      
      public final static int TIMESTAMP_IDX = 3;
      
      public final static String DEFAULT_SORT_ORDER = null;
      
      public final static String[] PROJECTION =
      { _ID, SRC_ENTITY_URI, DST_ENTITY_URI, TIMESTAMP };
   }
   
   
   public static class DeletionsColumns implements BaseColumns
   {
      /**
       * Designates the entity that has been deleted by it's Content URI with ID.
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String SRC_ENTITY_URI = "src_entity_uri";
      
      public final static int SRC_ENTITY_URI_IDX = 1;
      
      /**
       * Designates the entity that should be deleted by it's Content URI with ID.
       * <P>
       * Type: TEXT
       * </P>
       */
      public final static String DST_ENTITY_URI = "dst_entity_uri";
      
      public final static int DST_ENTITY_URI_IDX = 2;
      
      /**
       * The point of time when the entity was created.
       * <P>
       * Type: INTEGER(long)
       * </P>
       */
      public final static String TIMESTAMP = "timestamp";
      
      public final static int TIMESTAMP_IDX = 3;
      
      public final static String DEFAULT_SORT_ORDER = null;
      
      public final static String[] PROJECTION =
      { _ID, SRC_ENTITY_URI, DST_ENTITY_URI, TIMESTAMP };
   }
}
