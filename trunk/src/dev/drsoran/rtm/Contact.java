/* 
 *	Copyright (c) 2010 Ronny Röhricht
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

package dev.drsoran.rtm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;


public class Contact extends RtmContact
{
   private String lookUpKey;
   
   private Bitmap photo;
   
   public static final Parcelable.Creator< Contact > CREATOR = new Parcelable.Creator< Contact >()
   {
      public Contact createFromParcel( Parcel source )
      {
         return new Contact( source );
      }
      


      public Contact[] newArray( int size )
      {
         return new Contact[ size ];
      }
   };
   
   

   public Contact( String id, String fullname, String username )
   {
      super( id, fullname, username );
   }
   


   public Contact( Parcel source )
   {
      super( source );
      this.lookUpKey = source.readString();
      this.photo = source.readParcelable( null );
   }
   


   public String getPhonebookId()
   {
      return lookUpKey;
   }
   


   public void setLookUpKey( String lookUpKey )
   {
      this.lookUpKey = lookUpKey;
   }
   


   public Bitmap getPhoto()
   {
      return photo;
   }
   


   public void setPhoto( Bitmap photo )
   {
      this.photo = photo;
   }
   


   public void setPhoto( byte[] data )
   {
      try
      {
         this.photo = BitmapFactory.decodeByteArray( data, 0, data.length, null );
      }
      catch ( OutOfMemoryError e )
      {
         // Do nothing - the photo will appear to be missing
      }
   }
   


   @Override
   public void writeToParcel( Parcel dest, int flags )
   {
      super.writeToParcel( dest, flags );
      dest.writeString( lookUpKey );
      dest.writeParcelable( photo, flags );
   }
   
}
