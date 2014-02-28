/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

import dev.drsoran.Strings;
import dev.drsoran.moloko.content.Constants;


public class Contact
{
   private final long id;
   
   private final String userName;
   
   private final String fullName;
   
   private final int numTasksParticipating;
   
   
   
   public Contact( long id, String userName, String fullName,
      int numTasksParticipating )
   {
      if ( id == Constants.NO_ID )
      {
         throw new IllegalArgumentException( "id" );
      }
      
      if ( Strings.isNullOrEmpty( userName ) )
      {
         throw new IllegalArgumentException( "userName" );
      }
      
      if ( Strings.isNullOrEmpty( fullName ) )
      {
         throw new IllegalArgumentException( "fullname" );
      }
      
      if ( numTasksParticipating < 0 )
      {
         throw new IllegalArgumentException( "numTasksParticipating" );
      }
      
      this.id = id;
      this.userName = userName;
      this.fullName = fullName;
      this.numTasksParticipating = numTasksParticipating;
   }
   
   
   
   public long getId()
   {
      return id;
   }
   
   
   
   public String getUsername()
   {
      return userName;
   }
   
   
   
   public String getFullname()
   {
      return fullName;
   }
   
   
   
   public int getNumTasksParticipating()
   {
      return numTasksParticipating;
   }
   
   
   
   @Override
   public String toString()
   {
      return String.format( "Contact [id=%s, userName=%s, fullName=%s, part=%d]",
                            id,
                            userName,
                            fullName,
                            numTasksParticipating );
   }
   
}
