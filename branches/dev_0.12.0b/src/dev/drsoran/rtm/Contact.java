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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class Contact
{
   RtmContactWithTaskCount rtmContact;
   
   private String lookUpKey;
   
   private Bitmap photo;
   
   private boolean isLinkedByNotes;
   
   

   public Contact( RtmContactWithTaskCount rtmContact )
   {
      this.rtmContact = rtmContact;
      this.lookUpKey = null;
      this.photo = null;
      this.isLinkedByNotes = false;
   }
   


   public String getFullname()
   {
      return rtmContact.getFullname();
   }
   


   public String getId()
   {
      return rtmContact.getId();
   }
   


   public int getTaskCount()
   {
      return rtmContact.getTaskCount();
   }
   


   public String getUsername()
   {
      return rtmContact.getUsername();
   }
   


   public String getLookUpKey()
   {
      return lookUpKey;
   }
   


   public void setLookUpKey( String lookUpKey, boolean linkedByNotes )
   {
      this.lookUpKey = lookUpKey;
      this.isLinkedByNotes = linkedByNotes;
   }
   


   public Bitmap getPhoto()
   {
      return photo;
   }
   


   public void setPhoto( Bitmap photo )
   {
      this.photo = photo;
   }
   


   public boolean isLinkedByNotes()
   {
      return isLinkedByNotes;
   }
   


   public void setLinkedByNotes( boolean isLinkedByNotes )
   {
      this.isLinkedByNotes = isLinkedByNotes;
   }
   


   public void setPhoto( byte[] data )
   {
      try
      {
         this.photo = BitmapFactory.decodeByteArray( data, 0, data.length, null );
      }
      catch ( OutOfMemoryError e )
      {
         // Do nothing - the photo will appear to be missing
      }
   }
}
