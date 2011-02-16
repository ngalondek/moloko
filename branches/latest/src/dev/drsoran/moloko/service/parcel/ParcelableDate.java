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

package dev.drsoran.moloko.service.parcel;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;


public class ParcelableDate implements Parcelable
{
   
   public static final Parcelable.Creator< ParcelableDate > CREATOR = new Parcelable.Creator< ParcelableDate >()
   {
      
      public ParcelableDate createFromParcel( Parcel source )
      {
         return new ParcelableDate( source );
      }
      


      public ParcelableDate[] newArray( int size )
      {
         return new ParcelableDate[ size ];
      }
      
   };
   
   private final Date date;
   
   

   public ParcelableDate( final Date date )
   {
      this.date = date;
   }
   


   public ParcelableDate( long millis )
   {
      this.date = new Date( millis );
   }
   


   public ParcelableDate( Parcel source )
   {
      date = new Date( source.readLong() );
   }
   


   public Date getDate()
   {
      return date;
   }
   


   @Override
   public String toString()
   {
      return date.toString();
   }
   


   public int describeContents()
   {
      return 0;
   }
   


   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeLong( date.getTime() );
   }
   
}
