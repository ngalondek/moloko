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

public class Contact
{
   private final long id;
   
   private final String userName;
   
   private final String fullName;
   
   
   
   public Contact( long id, String userName, String fullName )
   {
      this.id = id;
      this.userName = userName;
      this.fullName = fullName;
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
   
   
   
   @Override
   public String toString()
   {
      return String.format( "Contact [id=%s, userName=%s, fullName=%s]",
                            id,
                            userName,
                            fullName );
   }
   
}
