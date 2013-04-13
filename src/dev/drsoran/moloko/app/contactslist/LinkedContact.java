/* 
 *	Copyright (c) 2010 Ronny R�hricht
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
 * Ronny R�hricht - implementation
 */

package dev.drsoran.moloko.app.contactslist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import dev.drsoran.moloko.domain.model.IContact;


class LinkedContact
{
   private final IContact contact;
   
   private final int numTasksParticipating;
   
   private String lookUpKey;
   
   private Bitmap photo;
   
   private boolean isLinkedByNotes;
   
   
   
   public LinkedContact( IContact contact, int numTasksParticipating )
   {
      this.contact = contact;
      this.numTasksParticipating = numTasksParticipating;
   }
   
   
   
   public long getId()
   {
      return contact.getId();
   }
   
   
   
   public String getFullName()
   {
      return contact.getFullName();
   }
   
   
   
   public String getUserName()
   {
      return contact.getUserName();
   }
   
   
   
   public int getNumTasksParticipating()
   {
      return numTasksParticipating;
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