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
   


   public ParcelableDate( Parcel source )
   {
      date = new Date( source.readLong() );
   }
   


   public Date getDate()
   {
      return date;
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
