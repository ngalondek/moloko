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

package dev.drsoran.moloko.app.taskedit;

import java.text.MessageFormat;
import java.util.NoSuchElementException;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import dev.drsoran.moloko.util.Bundles;


public class ValuesContainer implements Parcelable
{
   public static final Creator< ValuesContainer > CREATOR = new Creator< ValuesContainer >()
   {
      @Override
      public ValuesContainer createFromParcel( Parcel source )
      {
         return new ValuesContainer( source );
      }
      
      
      
      @Override
      public ValuesContainer[] newArray( int size )
      {
         return new ValuesContainer[ size ];
      }
   };
   
   private Bundle container = new Bundle();
   
   
   
   public ValuesContainer()
   {
   }
   
   
   
   public ValuesContainer( Parcel in )
   {
      this.container = in.readBundle();
   }
   
   
   
   public void putValue( String key, Object value )
   {
      Bundles.put( container, key, value );
   }
   
   
   
   public < T > void putValue( String key, T value, Class< T > type )
   {
      Bundles.put( container, key, value, type );
   }
   
   
   
   public < T > T getValue( String key, Class< T > type ) throws NoSuchElementException
   {
      if ( !container.containsKey( key ) )
      {
         throw new NoSuchElementException( MessageFormat.format( "No value for key {0}",
                                                                 key ) );
      }
      
      return Bundles.get( container, key, type );
   }
   
   
   
   public boolean hasValue( String key )
   {
      return container.containsKey( key );
   }
   
   
   
   public void removeValue( String key )
   {
      container.remove( key );
   }
   
   
   
   public int size()
   {
      return container.size();
   }
   
   
   
   public boolean isEmpty()
   {
      return container.isEmpty();
   }
   
   
   
   @Override
   public int describeContents()
   {
      return 0;
   }
   
   
   
   @Override
   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeBundle( container );
   }
}
