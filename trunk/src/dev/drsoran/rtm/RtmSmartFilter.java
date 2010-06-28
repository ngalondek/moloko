package dev.drsoran.rtm;

import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.mdt.rtm.data.RtmData;

import android.os.Parcel;
import android.os.Parcelable;


public class RtmSmartFilter extends RtmData
{
   @SuppressWarnings( "unused" )
   private final static String TAG = RtmSmartFilter.class.getSimpleName();
   
   public static final Parcelable.Creator< RtmSmartFilter > CREATOR = new Parcelable.Creator< RtmSmartFilter >()
   {
      
      public RtmSmartFilter createFromParcel( Parcel source )
      {
         return new RtmSmartFilter( source );
      }
      


      public RtmSmartFilter[] newArray( int size )
      {
         return new RtmSmartFilter[ size ];
      }
      
   };
   
   private final String filter;
   
   

   public RtmSmartFilter( String filter )
   {
      this.filter = filter;
   }
   


   public RtmSmartFilter( Element elt )
   {
      if ( elt.getChildNodes().getLength() > 0 )
      {
         final Text innerText = (Text) elt.getChildNodes().item( 0 );
         filter = innerText.getData();
      }
      else
      {
         filter = "";
      }
   }
   


   public RtmSmartFilter( Parcel source )
   {
      filter = source.readString();
   }
   


   public String getFilterString()
   {
      return filter;
   }
   


   public int describeContents()
   {
      return 0;
   }
   


   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeString( filter );
   }
   
}
