package dev.drsoran.rtm;

import org.antlr.runtime.RecognitionException;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import android.os.Parcel;
import android.os.Parcelable;

import com.mdt.rtm.data.RtmData;

import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.util.ANTLRNoCaseStringStream;


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
   
   private String evalFilter;
   
   

   public RtmSmartFilter( String filter )
   {
      this.filter = filter.replaceAll( "\\(|\\)", "" );
      this.evalFilter = null;
   }
   


   public RtmSmartFilter( Element elt )
   {
      if ( elt.getChildNodes().getLength() > 0 )
      {
         final Text innerText = (Text) elt.getChildNodes().item( 0 );
         filter = innerText.getData().replaceAll( "\\(|\\)", "" );
      }
      else
      {
         filter = "";
      }
      
      this.evalFilter = null;
   }
   


   public RtmSmartFilter( Parcel source )
   {
      this.filter = source.readString();
      this.evalFilter = source.readString();
   }
   


   public String getFilterString()
   {
      return filter;
   }
   


   public String getEvaluatedFilterString()
   {
      if ( evalFilter == null )
      {
         if ( filter != null )
         {
            evalFilter = evaluate( filter );
         }
         else
         {
            evalFilter = "true";
         }
      }
      
      return evalFilter;
   }
   


   public static final String evaluate( String filter )
   {
      String evalFilter = null;
      
      final ANTLRNoCaseStringStream input = new ANTLRNoCaseStringStream( filter );
      final RtmSmartFilterLexer lexer = new RtmSmartFilterLexer( input );
      
      try
      {
         evalFilter = lexer.getResult();
      }
      catch ( RecognitionException e )
      {
      }
      
      return evalFilter;
   }
   


   public int describeContents()
   {
      return 0;
   }
   


   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeString( filter );
      dest.writeString( evalFilter );
   }
   
}
