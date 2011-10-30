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

package dev.drsoran.rtm;

import java.io.Serializable;
import java.util.Comparator;

import android.os.Parcel;
import android.os.Parcelable;


public class Tag implements Parcelable
{
   
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko." + Tag.class.getSimpleName();
   
   private final String taskSeriesId;
   
   private final String tag;
   
   public static final Parcelable.Creator< Tag > CREATOR = new Parcelable.Creator< Tag >()
   {
      @Override
      public Tag createFromParcel( Parcel source )
      {
         return new Tag( source );
      }
      


      @Override
      public Tag[] newArray( int size )
      {
         return new Tag[ size ];
      }
   };
   
   
   public final static class ASC_ALPHA implements Comparator< Tag >,
            Serializable
   {
      
      private static final long serialVersionUID = -8573387797926369240L;
      
      

      @Override
      public int compare( Tag object1, Tag object2 )
      {
         return object1.tag.compareToIgnoreCase( object2.tag );
      }
   }
   
   

   public Tag( String taskSeriesId, String tag )
   {
      this.taskSeriesId = taskSeriesId;
      this.tag = tag;
   }
   


   public Tag( Parcel source )
   {
      taskSeriesId = source.readString();
      tag = source.readString();
   }
   


   public String getTaskSeriesId()
   {
      return taskSeriesId;
   }
   


   public String getTag()
   {
      return tag;
   }
   


   @Override
   public int describeContents()
   {
      return 0;
   }
   


   @Override
   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeString( taskSeriesId );
      dest.writeString( tag );
   }
   


   @Override
   public boolean equals( Object o )
   {
      if ( !( o instanceof Tag ) )
         return false;
      
      final Tag other = (Tag) o;
      
      boolean equal = taskSeriesId == null
                                          ? other.taskSeriesId == null
                                          : taskSeriesId.equals( other.taskSeriesId );
      
      return equal && tag.equals( other.tag );
   }
   


   @Override
   public int hashCode()
   {
      int result = 17;
      
      result = 31 * result
         + ( taskSeriesId != null ? taskSeriesId.hashCode() : 0 );
      result = 31 * result + tag.hashCode();
      
      return result;
   }
   


   @Override
   public String toString()
   {
      return tag;
   }
}
