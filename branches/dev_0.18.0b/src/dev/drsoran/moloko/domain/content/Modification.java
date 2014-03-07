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

package dev.drsoran.moloko.domain.content;

import java.util.Collection;
import java.util.Date;

import dev.drsoran.Compare;
import dev.drsoran.Strings;
import dev.drsoran.moloko.content.Constants;


public class Modification
{
   private final String entityUri;
   
   private final String property;
   
   private final String newValue;
   
   private final String syncedValue;
   
   private final boolean isSetSyncedValue;
   
   private final boolean persistent;
   
   private final long timestampMillisUtc;
   
   
   
   private Modification( String entityUri, String property, String newValue,
      String syncedValue, boolean isSetSyncedValue, boolean persistent,
      long timestamp )
   {
      if ( entityUri == null )
      {
         throw new IllegalArgumentException( "entityUri" );
      }
      
      if ( Strings.isNullOrEmpty( property ) )
      {
         throw new IllegalArgumentException( "property" );
      }
      
      if ( timestamp == Constants.NO_TIME )
      {
         throw new IllegalArgumentException( "timestamp" );
      }
      
      this.entityUri = entityUri;
      this.property = property;
      this.newValue = newValue;
      this.syncedValue = syncedValue;
      this.isSetSyncedValue = isSetSyncedValue;
      this.persistent = persistent;
      this.timestampMillisUtc = timestamp;
   }
   
   
   
   public String getEntityUri()
   {
      return entityUri;
   }
   
   
   
   public String getPropertyName()
   {
      return property;
   }
   
   
   
   public String getValue()
   {
      return newValue;
   }
   
   
   
   public < T > T getValue( Class< T > valueClass )
   {
      return Strings.convertTo( newValue, valueClass );
   }
   
   
   
   public String getSyncedValue()
   {
      return syncedValue;
   }
   
   
   
   public < T > T getSyncedValue( Class< T > valueClass )
   {
      return Strings.convertTo( syncedValue, valueClass );
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
      if ( o == null )
      {
         return false;
      }
      
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
      equal = equal && property.equals( other.property );
      
      return equal;
   }
   
   
   
   @Override
   public int hashCode()
   {
      int result = 17;
      
      result = 31 * result + entityUri.hashCode();
      result = 31 * result + property.hashCode();
      result = 31 * result + ( newValue != null ? newValue.hashCode() : 0 );
      
      return result;
   }
   
   
   
   @Override
   public String toString()
   {
      return String.format( "Modification [entityUri=%s, prop=%s, new=%s, synced=%s, pers=%s, time=%s]",
                            entityUri,
                            property,
                            newValue,
                            syncedValue,
                            Boolean.toString( persistent ),
                            new Date( timestampMillisUtc ) );
   }
   
   
   
   public static < T > Modification newModification( String entityUri,
                                                     String property,
                                                     T newValue )
   {
      return new Modification( entityUri,
                               property,
                               Strings.convertFrom( newValue ),
                               null,
                               false,
                               true,
                               System.currentTimeMillis() );
   }
   
   
   
   public static < T > Modification newModification( String entityUri,
                                                     String property,
                                                     T newValue,
                                                     T synedValue )
   {
      return new Modification( entityUri,
                               property,
                               Strings.convertFrom( newValue ),
                               Strings.convertFrom( synedValue ),
                               true,
                               true,
                               System.currentTimeMillis() );
   }
   
   
   
   public static < T > Modification newModification( String entityUri,
                                                     String property,
                                                     T newValue,
                                                     T synedValue,
                                                     long timeStamp )
   {
      return new Modification( entityUri,
                               property,
                               Strings.convertFrom( newValue ),
                               Strings.convertFrom( synedValue ),
                               true,
                               true,
                               timeStamp );
   }
   
   
   
   public static < T > Modification newNonPersistentModification( String entityUri,
                                                                  String property,
                                                                  T newValue )
   {
      return new Modification( entityUri,
                               property,
                               Strings.convertFrom( newValue ),
                               null,
                               false,
                               false,
                               System.currentTimeMillis() );
   }
   
   
   
   public static < V > void addIfDifferent( Collection< Modification > modifications,
                                            String entityUri,
                                            String property,
                                            V existingValue,
                                            V updatedValue )
   {
      if ( modifications == null )
      {
         throw new IllegalArgumentException( "modifications" );
      }
      
      if ( Compare.isDifferent( existingValue, updatedValue ) )
      {
         modifications.add( Modification.newModification( entityUri,
                                                          property,
                                                          updatedValue,
                                                          existingValue ) );
      }
   }
   
   
   
   public static < V > void addIfDifferentNonPersistent( Collection< Modification > modifications,
                                                         String entityUri,
                                                         String property,
                                                         V existingValue,
                                                         V updatedValue )
   {
      if ( modifications == null )
      {
         throw new IllegalArgumentException( "modifications" );
      }
      
      if ( Compare.isDifferent( existingValue, updatedValue ) )
      {
         modifications.add( Modification.newNonPersistentModification( entityUri,
                                                                       property,
                                                                       updatedValue ) );
      }
   }
}
