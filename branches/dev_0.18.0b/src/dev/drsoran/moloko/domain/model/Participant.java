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

import dev.drsoran.Strings;


public class Participant
{
   private final long id;
   
   private final String fullname;
   
   private final String username;
   
   
   
   public Participant( long id, String fullname, String username )
   {
      if ( fullname == null )
      {
         throw new IllegalArgumentException( "fullname" );
      }
      if ( Strings.isNullOrEmpty( username ) )
      {
         throw new IllegalArgumentException( "username" );
      }
      
      this.id = id;
      this.fullname = fullname;
      this.username = username;
   }
   
   
   
   public long getId()
   {
      return id;
   }
   
   
   
   public String getFullname()
   {
      return fullname;
   }
   
   
   
   public String getUsername()
   {
      return username;
   }
   
   
   
   @Override
   public String toString()
   {
      return String.format( "Participant [id=%s, fullname=%s, username=%s]",
                            id,
                            fullname,
                            username );
   }
}
