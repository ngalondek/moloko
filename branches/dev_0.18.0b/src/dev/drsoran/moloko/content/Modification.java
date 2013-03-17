/* 
 *	Copyright (c) 2011 Ronny Röhricht
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

import java.util.Comparator;
import java.util.Date;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import dev.drsoran.provider.Rtm.Notes;


public class Modification implements Comparable< Modification >
{
   private final String id;
   
   private final Uri entityUri;
   
   private final String colName;
   
   private final String newValue;
   
   private final String syncedValue;
   
   private final boolean synedValueSet;
   
   private final boolean persistent;
   
   private final long timestamp;
   
   
   private final static class SortColumnName implements
            Comparator< Modification >
   {
      @Override
      public int compare( Modification object1, Modification object2 )
      {
         return object1.colName.compareTo( object2.colName );
      }
   }
   
   public final static SortColumnName SORT_COLUMN_NAME = new SortColumnName();
   
   
   
   private Modification( String id, Uri entityUri, String colName,
      String newValue, String syncedValue, boolean syncedValueSet,
      boolean persistent, long timestamp )
   {
      this.id = id;
      this.entityUri = entityUri;
      this.colName = colName;
      this.newValue = newValue;
      this.syncedValue = syncedValue;
      this.synedValueSet = syncedValueSet;
      this.persistent = persistent;
      this.timestamp = timestamp;
   }
   
   
   
   public String getId()
   {
      return id;
   }
   
   
   
   public Uri getEntityUri()
   {
      return entityUri;
   }
   
   
   
   public String getColName()
   {
      return colName;
   }
   
   
   
   public String getNewValue()
   {
      return newValue;
   }
   
   
   
   public < T > T getNewValue( Class< T > valueClass )
   {
      return fromString( newValue, valueClass );
   }
   
   
   
   public String getSyncedValue()
   {
      return syncedValue;
   }
   
   
   
   public < T > T getSyncedValue( Class< T > valueClass )
   {
      return fromString( syncedValue, valueClass );
   }
   
   
   
   public boolean isSyncedValueSet()
   {
      return synedValueSet;
   }
   
   
   
   public boolean isPersistent()
   {
      return persistent;
   }
   
   
   
   public long getTimestamp()
   {
      return timestamp;
   }
   
   
   
   @Override
   public boolean equals( Object o )
   {
      if ( this == o )
         return true;
      
      if ( !( o instanceof Modification ) )
         return false;
      
      final Modification other = (Modification) o;
      
      boolean equal = ( newValue == null ? other.newValue == null
                                        : newValue.equals( other.newValue ) );
      equal = equal && entityUri.equals( other.entityUri );
      equal = equal && colName.equals( other.colName );
      
      return equal;
   }
   
   
   
   @Override
   public int hashCode()
   {
      int result = 17;
      
      result = 31 * result + entityUri.hashCode();
      result = 31 * result + colName.hashCode();
      result = 31 * result + ( newValue != null ? newValue.hashCode() : 0 );
      
      return result;
   }
   
   
   
   @Override
   public int compareTo( Modification another )
   {
      int cmp = entityUri.compareTo( another.entityUri );
      if ( cmp != 0 )
         return cmp;
      
      cmp = colName.compareTo( another.colName );
      if ( cmp != 0 )
         return cmp;
      
      return 0;
   }
   
   
   
   @Override
   public String toString()
   {
      return "<Mod, " + id + ", " + entityUri + ", " + colName + ", "
         + newValue + ", " + syncedValue + ", " + new Date( timestamp ) + ">";
   }
   
   
   
   public final static < T > Modification newModification( Uri contentUri,
                                                           String id,
                                                           String colName,
                                                           T newValue )
   {
      return newModification( DbUtils.contentUriWithId( contentUri, id ),
                              colName,
                              newValue );
   }
   
   
   
   public final static < T > Modification newModification( Uri entityUri,
                                                           String colName,
                                                           T newValue )
   {
      return new Modification( null,
                               entityUri,
                               colName,
                               toString( newValue ),
                               null,
                               false,
                               true,
                               System.currentTimeMillis() );
   }
   
   
   
   public final static < T > Modification newModification( Uri entityUri,
                                                           String colName,
                                                           T newValue,
                                                           T synedValue )
   {
      return new Modification( null,
                               entityUri,
                               colName,
                               toString( newValue ),
                               toString( synedValue ),
                               true,
                               true,
                               System.currentTimeMillis() );
   }
   
   
   
   public final static < T > ContentProviderOperation newModificationOperation( Uri entityUri,
                                                                                String colName,
                                                                                T newValue,
                                                                                T syncedValue )
   {
      return ContentProviderOperation.newInsert( Modifications.CONTENT_URI )
                                     .withValues( ModificationsProviderPart.getContentValues( null,
                                                                                              newModification( entityUri,
                                                                                                               colName,
                                                                                                               newValue,
                                                                                                               syncedValue ),
                                                                                              true ) )
                                     .build();
   }
   
   
   
   public final static < T > Modification newNonPersistentModification( Uri contentUri,
                                                                        String id,
                                                                        String colName,
                                                                        T newValue )
   {
      return newNonPersistentModification( DbUtils.contentUriWithId( contentUri,
                                                                      id ),
                                           colName,
                                           newValue );
   }
   
   
   
   public final static < T > Modification newNonPersistentModification( Uri entityUri,
                                                                        String colName,
                                                                        T newValue )
   {
      return new Modification( null,
                               entityUri,
                               colName,
                               toString( newValue ),
                               null,
                               false,
                               false,
                               System.currentTimeMillis() );
   }
   
   
   
   public final static Modification newTaskModified( String taskSeriesId )
   {
      return newNonPersistentModification( TaskSeries.CONTENT_URI,
                                           taskSeriesId,
                                           TaskSeries.MODIFIED_DATE,
                                           System.currentTimeMillis() );
   }
   
   
   
   public final static Modification newNoteModified( String noteId )
   {
      return newNonPersistentModification( Notes.CONTENT_URI,
                                           noteId,
                                           Notes.NOTE_MODIFIED_DATE,
                                           System.currentTimeMillis() );
   }
   
   
   
   public final static Modification newListModified( String listId )
   {
      return newNonPersistentModification( Lists.CONTENT_URI,
                                           listId,
                                           Lists.MODIFIED_DATE,
                                           System.currentTimeMillis() );
   }
   
   
   
   public final static < T > T get( Cursor c, int column, Class< T > valueClass )
   {
      if ( c == null )
         throw new NullPointerException( "key is null" );
      
      if ( c.isNull( column ) )
         return null;
      
      return fromString( c.getString( column ), valueClass );
   }
   
   
   
   public final static < T > void put( ContentValues contentValues,
                                       String key,
                                       T value )
   {
      if ( key == null )
         throw new NullPointerException( "key is null" );
      
      if ( value == null )
         contentValues.putNull( key );
      else
         contentValues.put( key, toString( value ) );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   private final static < T > T fromString( String value, Class< T > valueClass )
   {
      if ( valueClass == null )
         throw new NullPointerException( "valueClass is null" );
      
      if ( value == null )
         return null;
      else if ( valueClass.equals( String.class ) )
         return (T) value;
      else if ( valueClass.equals( Integer.class ) )
         return (T) Integer.valueOf( value );
      else if ( valueClass.equals( Long.class ) )
         return (T) Long.valueOf( value );
      else if ( valueClass.equals( Double.class ) )
         return (T) Double.valueOf( value );
      else if ( valueClass.equals( Float.class ) )
         return (T) Float.valueOf( value );
      else if ( valueClass.equals( Short.class ) )
         return (T) Short.valueOf( value );
      else if ( valueClass.equals( Boolean.class ) )
         return (T) Boolean.valueOf( ( Integer.valueOf( value ) != 0 ) );
      else
         throw new IllegalArgumentException( "Unsupported data type of valueType "
            + valueClass.getName() );
   }
   
   
   
   private final static < T > String toString( T value )
   {
      if ( value == null )
         return null;
      else if ( value instanceof String )
         return (String) value;
      else if ( value instanceof Integer )
         return Integer.toString( (Integer) value );
      else if ( value instanceof Long )
         return Long.toString( (Long) value );
      else if ( value instanceof Double )
         return Double.toString( (Double) value );
      else if ( value instanceof Float )
         return Float.toString( (Float) value );
      else if ( value instanceof Short )
         return Short.toString( (Short) value );
      else if ( value instanceof Boolean )
         return ( (Boolean) value ) ? "1" : "0";
      else
         throw new IllegalArgumentException( "Unsupported data type of value "
            + value.getClass().getName() );
   }
}
