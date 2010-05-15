package dev.drsoran.moloko.service.parcel;

import com.mdt.rtm.data.RtmAuth;
import com.mdt.rtm.data.RtmUser;

import android.os.Parcel;
import android.os.Parcelable;


public class ParcelableRtmAuth implements Parcelable
{
   private RtmAuth rtmAuth;
   
   public static final Parcelable.Creator< ParcelableRtmAuth > CREATOR =
      new Parcelable.Creator< ParcelableRtmAuth >()
      {
         
         public ParcelableRtmAuth createFromParcel( Parcel source )
         {
            return new ParcelableRtmAuth( source );
         }
         


         public ParcelableRtmAuth[] newArray( int size )
         {
            return new ParcelableRtmAuth[ size ];
         }
         
      };
   
   

   public ParcelableRtmAuth( Parcel source )
   {
      readFromParcel( source );
   }
   


   public ParcelableRtmAuth( final RtmAuth rtmAuth )
   {
      this.rtmAuth = rtmAuth;
   }
   


   public RtmAuth getRtmAuth()
   {
      return rtmAuth;
   }
   


   public int describeContents()
   {
      return 0;
   }
   


   public void readFromParcel( Parcel source )
   {
      rtmAuth =
         new RtmAuth( source.readString(),
                      RtmAuth.Perms.valueOf( source.readString() ),
                      new RtmUser( source.readString(),
                                   source.readString(),
                                   source.readString() ) );
      
   }
   


   public void writeToParcel( Parcel out, int flags )
   {
      if ( rtmAuth != null )
      {
         out.writeString( rtmAuth.getToken() );
         out.writeString( rtmAuth.getPerms().toString() );
         
         final RtmUser user = rtmAuth.getUser();
         
         if ( user != null )
         {
            out.writeSerializable( user.getId() );
            out.writeSerializable( user.getUsername() );
            out.writeSerializable( user.getFullname() );
         }
         else
         {
            out.writeString( "not set" );
            out.writeString( "not set" );
            out.writeString( "not set" );
         }
      }
   }
   
}
