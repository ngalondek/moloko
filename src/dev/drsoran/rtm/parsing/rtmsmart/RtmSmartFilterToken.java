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

package dev.drsoran.rtm.parsing.rtmsmart;

import java.util.Locale;

import android.os.Parcel;
import android.os.Parcelable;


// TODO: Remove the parceling
public class RtmSmartFilterToken implements Parcelable
{
   public final int operatorType;
   
   public final String value;
   
   public final boolean isNegated;
   
   public static final Parcelable.Creator< RtmSmartFilterToken > CREATOR = new Parcelable.Creator< RtmSmartFilterToken >()
   {
      
      @Override
      public RtmSmartFilterToken createFromParcel( Parcel source )
      {
         return new RtmSmartFilterToken( source );
      }
      
      
      
      @Override
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
   
   
   
   @Override
   public int describeContents()
   {
      return 0;
   }
   
   
   
   @Override
   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeInt( operatorType );
      dest.writeString( value );
      dest.writeInt( isNegated ? 1 : 0 );
   }
   
   
   
   @Override
   public boolean equals( Object o )
   {
      if ( o == this )
      {
         return true;
      }
      
      if ( o == null )
      {
         return false;
      }
      
      if ( o.getClass() != getClass() )
      {
         return false;
      }
      
      final RtmSmartFilterToken other = (RtmSmartFilterToken) o;
      
      return other.operatorType == operatorType && other.value.equals( value )
         && other.isNegated == isNegated;
   }
   
   
   
   @Override
   public int hashCode()
   {
      int hashCode = operatorType;
      hashCode = 31 * hashCode ^ value.hashCode();
      hashCode = 31 * hashCode ^ ( isNegated ? 0 : 1 );
      
      return hashCode;
   }
   
   
   
   @Override
   public String toString()
   {
      return String.format( Locale.ENGLISH,
                            "Op: %d, %s, neg: %b",
                            operatorType,
                            value,
                            isNegated );
   }
}
