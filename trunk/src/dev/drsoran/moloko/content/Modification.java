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

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;


public class Modification< T > implements Comparable< Modification< ? > >
{
   private final String id;
   
   private final Uri entityUri;
   
   private final String colName;
   
   private final T newValue;
   
   private final Class< T > valueClass;
   
   

   private Modification( String id, Uri entityUri, String colName,
      Class< T > valueClass, T newValue )
   {
      this.id = id;
      this.entityUri = entityUri;
      this.colName = colName;
      this.newValue = newValue;
      this.valueClass = valueClass;
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
   


   public T getNewValue()
   {
      return newValue;
   }
   


   public Class< T > getValueClass()
   {
      return valueClass;
   }
   


   @SuppressWarnings( "unchecked" )
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
      equal = equal && valueClass.getName().equals( other.valueClass.getName() );
      equal = equal && colName == other.colName;
      
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
   


   public int compareTo( Modification< ? > another )
   {
      int cmp = entityUri.compareTo( another.entityUri );
      if ( cmp != 0 )
         return cmp;
      
      cmp = colName.compareTo( another.colName );
      if ( cmp != 0 )
         return cmp;
      
      return 0;
   }
   


   public final static < T > Modification< T > newModification( String id,
                                                                Uri entityUri,
                                                                String colName,
                                                                Class< T > valueClass,
                                                                T newValue )
   {
      return new Modification< T >( id,
                                    entityUri,
                                    colName,
                                    valueClass,
                                    newValue );
   }
   


   public final static < T > Modification< T > newModification( Uri entityUri,
                                                                String colName,
                                                                Class< T > valueClass,
                                                                T newValue )
   {
      return new Modification< T >( null,
                                    entityUri,
                                    colName,
                                    valueClass,
                                    newValue );
   }
   


   @SuppressWarnings( "unchecked" )
   public final static < T > T get( Cursor c, int column, Class< T > valueClass )
   {
      if ( c == null )
         throw new NullPointerException( "key is null" );
      if ( valueClass == null )
         throw new NullPointerException( "valueType is null" );
      
      if ( c.isNull( column ) )
         return null;
      else if ( valueClass.equals( String.class ) )
         return (T) c.getString( column );
      else if ( valueClass.equals( Integer.class ) )
         return (T) Integer.valueOf( c.getInt( column ) );
      else if ( valueClass.equals( Long.class ) )
         return (T) Long.valueOf( c.getLong( column ) );
      else if ( valueClass.equals( Double.class ) )
         return (T) Double.valueOf( c.getDouble( column ) );
      else if ( valueClass.equals( Float.class ) )
         return (T) Float.valueOf( c.getFloat( column ) );
      else if ( valueClass.equals( Short.class ) )
         return (T) Short.valueOf( c.getShort( column ) );
      else if ( valueClass.equals( Boolean.class ) )
         return (T) Boolean.valueOf( c.getInt( column ) != 0 );
      else
         throw new IllegalArgumentException( "Unsupported data type of valueType "
            + valueClass.getName() );
   }
   


   public final static < T > void put( ContentValues contentValues,
                                       String key,
                                       T value )
   {
      if ( key == null )
         throw new NullPointerException( "key is null" );
      
      if ( value == null )
         contentValues.putNull( key );
      else if ( value instanceof String )
         contentValues.put( key, (String) value );
      else if ( value instanceof Integer )
         contentValues.put( key, (Integer) value );
      else if ( value instanceof Long )
         contentValues.put( key, (Long) value );
      else if ( value instanceof Double )
         contentValues.put( key, (Double) value );
      else if ( value instanceof Float )
         contentValues.put( key, (Double) value );
      else if ( value instanceof Short )
         contentValues.put( key, (Short) value );
      else if ( value instanceof Boolean )
         contentValues.put( key, (Boolean) value );
      else
         throw new IllegalArgumentException( "Unsupported data type of value "
            + value );
   }
   
}
