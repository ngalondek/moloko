/*
Copyright (c) 2010 Ronny Röhricht   

This file is part of Moloko.

Moloko is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Moloko is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Moloko.  If not, see <http://www.gnu.org/licenses/>.

Contributors:
	Ronny Röhricht - implementation
 */

package dev.drsoran.rtm;

import org.antlr.runtime.RecognitionException;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.mdt.rtm.data.RtmData;

import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.util.ANTLRNoCaseStringStream;
import dev.drsoran.moloko.util.Strings;


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
      this.filter = filter.replaceAll( "\\(|\\)", Strings.EMPTY_STRING );
      this.evalFilter = null;
   }
   


   public RtmSmartFilter( Element elt )
   {
      if ( elt.getChildNodes().getLength() > 0 )
      {
         final Text innerText = (Text) elt.getChildNodes().item( 0 );
         filter = innerText.getData().replaceAll( "\\(|\\)",
                                                  Strings.EMPTY_STRING );
      }
      else
      {
         filter = Strings.EMPTY_STRING;
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
      if ( !isEvaluated() )
      {
         if ( filter != null && filter.length() > 0 )
         {
            evalFilter = evaluate( filter );
         }
         else
         {
            evalFilter = "1";
         }
      }
      
      return evalFilter;
   }
   


   public boolean isEvaluated()
   {
      return evalFilter != null;
   }
   


   public static final String evaluate( String filter )
   {
      String evalFilter = null;
      
      // a 0-length filter == "true"
      if ( filter != null && filter.length() == 0 )
      {
         evalFilter = "1";
      }
      
      // Check if there was no operator used. If so it has the
      // same meaning as operator name:
      else
      {
         if ( !filter.contains( ":" ) )
            filter = RtmSmartFilterLexer.OP_NAME_LIT + filter;
         
         final ANTLRNoCaseStringStream input = new ANTLRNoCaseStringStream( filter );
         final RtmSmartFilterLexer lexer = new RtmSmartFilterLexer( input );
         
         try
         {
            evalFilter = lexer.getResult();
         }
         catch ( RecognitionException e )
         {
         }
      }
      
      // An empty string is an evaluation error.
      if ( TextUtils.isEmpty( evalFilter ) )
         evalFilter = null;
      
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
