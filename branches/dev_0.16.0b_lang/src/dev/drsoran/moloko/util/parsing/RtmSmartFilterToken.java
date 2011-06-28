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

package dev.drsoran.moloko.util.parsing;

import android.os.Parcel;
import android.os.Parcelable;


public class RtmSmartFilterToken implements Parcelable
{
   public final int operatorType;
   
   public final String value;
   
   public final boolean isNegated;
   
   public static final Parcelable.Creator< RtmSmartFilterToken > CREATOR = new Parcelable.Creator< RtmSmartFilterToken >()
   {
      
      public RtmSmartFilterToken createFromParcel( Parcel source )
      {
         return new RtmSmartFilterToken( source );
      }
      


      public RtmSmartFilterToken[] newArray( int size )
      {
         return new RtmSmartFilterToken[ size ];
      }
      
   };
   
   

   public RtmSmartFilterToken( int operatorType, String value, boolean negated )
   {
      this.operatorType = operatorType;
      this.value = value;
      this.isNegated = negated;
   }
   


   public RtmSmartFilterToken( Parcel source )
   {
      this.operatorType = source.readInt();
      this.value = source.readString();
      this.isNegated = source.readInt() != 0;
   }
   


   public int describeContents()
   {
      return 0;
   }
   


   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeInt( operatorType );
      dest.writeString( value );
      dest.writeInt( isNegated ? 1 : 0 );
   }
}
