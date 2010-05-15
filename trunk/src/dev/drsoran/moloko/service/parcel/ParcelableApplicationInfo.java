package dev.drsoran.moloko.service.parcel;

import android.os.Parcel;
import android.os.Parcelable;

import com.mdt.rtm.ApplicationInfo;
import com.mdt.rtm.data.RtmAuth;


public class ParcelableApplicationInfo implements Parcelable
{
   private ApplicationInfo applicationInfo = null;
   
   private RtmAuth.Perms permission;
   
   public static final Parcelable.Creator< ParcelableApplicationInfo > CREATOR =
      new Parcelable.Creator< ParcelableApplicationInfo >()
      {
         
         public ParcelableApplicationInfo createFromParcel( Parcel source )
         {
            return new ParcelableApplicationInfo( source );
         }
         


         public ParcelableApplicationInfo[] newArray( int size )
         {
            return new ParcelableApplicationInfo[ size ];
         }
         
      };
   
   

   public ParcelableApplicationInfo( ApplicationInfo applicationInfo,
                                     RtmAuth.Perms permission )
   {
      this.applicationInfo = applicationInfo;
      this.permission = permission;
   }
   


   public ParcelableApplicationInfo( Parcel source )
   {
      readFromParcel( source );
   }
   


   public ApplicationInfo getApplicationInfo()
   {
      return applicationInfo;
   }
   


   public RtmAuth.Perms getPermission()
   {
      return permission;
   }
   


   public void writeToParcel( Parcel out, int flags )
   {
      if ( applicationInfo != null )
      {
         out.writeString( applicationInfo.getApiKey() );
         out.writeString( applicationInfo.getSharedSecret() );
         out.writeString( applicationInfo.getName() );
         out.writeString( applicationInfo.getAuthToken() );
         out.writeString( permission.toString() );
      }
   }
   


   public void readFromParcel( Parcel in )
   {
      applicationInfo =
         new ApplicationInfo( in.readString(),
                              in.readString(),
                              in.readString(),
                              in.readString() );
      permission = RtmAuth.Perms.valueOf( in.readString() );
   }
   


   public int describeContents()
   {
      return 0;
   }
}
