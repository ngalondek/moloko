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

package dev.drsoran.moloko.content.db;

import java.util.Collection;
import java.util.Comparator;
import java.util.Date;

import android.net.Uri;
import dev.drsoran.moloko.content.ContentCompare;


public class Modification implements Comparable< Modification >
{
   private final long id;
   
   private final Uri entityUri;
   
   private final String colName;
   
   private final String newValue;
   
   private final String syncedValue;
   
   private final boolean isSetSyncedValue;
   
   private final boolean persistent;
   
   private final long timestampMillisUtc;
   
   
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
   
   
   
   private Modification( long id, Uri entityUri, String colName,
      String newValue, String syncedValue, boolean isSetSyncedValue,
      boolean persistent, long timestamp )
   {
      this.id = id;
      this.entityUri = entityUri;
      this.colName = colName;
      this.newValue = newValue;
      this.syncedValue = syncedValue;
      this.isSetSyncedValue = isSetSyncedValue;
      this.persistent = persistent;
      this.timestampMillisUtc = timestamp;
   }
   
   
   
   public long getId()
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
   
   
   
   public boolean isSetSyncedValue()
   {
      return isSetSyncedValue;
   }
   
   
   
   public boolean isPersistent()
   {
      return persistent;
   }
   
   
   
   public long getTimestamp()
   {
      return timestampMillisUtc;
   }
   
   
   
   @Override
   public boolean equals( Object o )
   {
      if ( this == o )
      {
         return true;
      }
      
      if ( getClass() != o.getClass() )
      {
         return false;
      }
      
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
         + newValue + ", " + syncedValue + ", " + new Date( timestampMillisUtc )
         + ">";
   }
   
   
   
   public static < T > Modification newModification( Uri entityUri,
                                                     String colName,
                                                     T newValue )
   {
      return new Modification( -1L,
                               entityUri,
                               colName,
                               toString( newValue ),
                               null,
                               false,
                               true,
                               System.currentTimeMillis() );
   }
   
   
   
   public static < T > Modification newModification( Uri entityUri,
                                                     String colName,
                                                     T newValue,
                                                     T synedValue )
   {
      return new Modification( -1L,
                               entityUri,
                               colName,
                               toString( newValue ),
                               toString( synedValue ),
                               true,
                               true,
                               System.currentTimeMillis() );
   }
   
   
   
   public static < T > Modification newNonPersistentModification( Uri entityUri,
                                                                  String colName,
                                                                  T newValue )
   {
      return new Modification( -1L,
                               entityUri,
                               colName,
                               toString( newValue ),
                               null,
                               false,
                               false,
                               System.currentTimeMillis() );
   }
   
   
   
   public static < V > void addIfDifferent( Collection< Modification > modifications,
                                            Uri entityUri,
                                            String colName,
                                            V existingValue,
                                            V updatedValue )
   {
      if ( ContentCompare.isDifferent( existingValue, updatedValue ) )
      {
         modifications.add( Modification.newModification( entityUri,
                                                          colName,
                                                          updatedValue,
                                                          existingValue ) );
      }
   }
   
   
   
   public static < V > void addIfDifferentNonPersistent( Collection< Modification > modifications,
                                                         Uri entityUri,
                                                         String colName,
                                                         V existingValue,
                                                         V updatedValue )
   {
      if ( ContentCompare.isDifferent( existingValue, updatedValue ) )
      {
         modifications.add( Modification.newNonPersistentModification( entityUri,
                                                                       colName,
                                                                       updatedValue ) );
      }
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   private static < T > T fromString( String value, Class< T > valueClass )
   {
      if ( valueClass == null )
         throw new IllegalArgumentException( "valueClass" );
      
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
