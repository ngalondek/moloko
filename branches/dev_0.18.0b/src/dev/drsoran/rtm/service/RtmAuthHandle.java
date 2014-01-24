/* 
 *	Copyright (c) 2014 Ronny Röhricht
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

package dev.drsoran.rtm.service;

public class RtmAuthHandle
{
   private final String authUri;
   
   private final RtmFrob frob;
   
   
   
   public RtmAuthHandle( String authUri, RtmFrob frob )
   {
      if ( authUri == null )
      {
         throw new IllegalArgumentException( "authUri" );
      }
      if ( frob == null )
      {
         throw new IllegalArgumentException( "frob" );
      }
      
      this.authUri = authUri;
      this.frob = frob;
   }
   
   
   
   public String getAuthUri()
   {
      return authUri;
   }
   
   
   
   public RtmFrob getFrob()
   {
      return frob;
   }
   
   
   
   @Override
   public String toString()
   {
      return "RtmAuthHandle [authUri=" + authUri + ", frob=" + frob + "]";
   }
}
