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

package dev.drsoran.moloko.domain.model;

import java.util.Comparator;


public class Participant
{
   public final static LessContactIdComperator LESS_CONTACT_ID = new LessContactIdComperator();
   
   private final long id;
   
   private final long contactId;
   
   private final String fullname;
   
   private final String username;
   
   
   
   public Participant( long id, long contactId, String fullname, String username )
   {
      this.id = id;
      this.contactId = contactId;
      this.fullname = fullname;
      this.username = username;
   }
   
   
   
   public long getId()
   {
      return id;
   }
   
   
   
   public long getContactId()
   {
      return contactId;
   }
   
   
   
   public String getFullname()
   {
      return fullname;
   }
   
   
   
   public String getUsername()
   {
      return username;
   }
   
   
   private static final class LessContactIdComperator implements
            Comparator< Participant >
   {
      @Override
      public int compare( Participant object1, Participant object2 )
      {
         final long contactId1 = object1.contactId;
         final long contactId2 = object2.contactId;
         
         if ( contactId1 > contactId2 )
         {
            return 1;
         }
         if ( contactId1 < contactId2 )
         {
            return -1;
         }
         
         return 0;
      }
   }
   
   
   
   @Override
   public String toString()
   {
      return String.format( "Participant [id=%s, contactId=%s, fullname=%s, username=%s]",
                            id,
                            contactId,
                            fullname,
                            username );
   }
}
