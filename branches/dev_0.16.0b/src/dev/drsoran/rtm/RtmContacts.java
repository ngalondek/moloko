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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.Element;

import android.os.Parcel;

import com.mdt.rtm.data.RtmData;


public class RtmContacts extends RtmData
{
   @SuppressWarnings( "unused" )
   private static final String TAG = "Moloko."
      + RtmContacts.class.getSimpleName();
   
   private ArrayList< RtmContact > contacts;
   
   

   public RtmContacts()
   {
      this.contacts = new ArrayList< RtmContact >( 0 );
   }
   


   public RtmContacts( List< RtmContact > contacts )
   {
      this.contacts = new ArrayList< RtmContact >( contacts );
   }
   


   public RtmContacts( Parcel source )
   {
      this.contacts = source.createTypedArrayList( RtmContact.CREATOR );
   }
   


   public RtmContacts( Element elt )
   {
      final List< Element > children = children( elt, "contact" );
      this.contacts = new ArrayList< RtmContact >( children.size() );
      
      for ( Element element : children )
      {
         contacts.add( new RtmContact( element ) );
      }
   }
   


   public List< RtmContact > getContacts()
   {
      return Collections.unmodifiableList( contacts );
   }
   


   public boolean addContact( RtmContact contact )
   {
      return contacts.add( contact );
   }
   


   public void setContacts( ArrayList< RtmContact > contacts )
   {
      this.contacts = contacts;
   }
   


   @Override
   public String toString()
   {
      return "RtmContacts<#" + contacts.size() + ">";
   }
   


   public int describeContents()
   {
      return 0;
   }
   


   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeTypedList( contacts );
   }
}
