/* 
 *	Copyright (c) 2011 Ronny Röhricht
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

package dev.drsoran.moloko.content;

import android.net.Uri;


@Deprecated
public final class Creation
{
   private final String id;
   
   private final Uri entityUri;
   
   private final long timeStamp;
   
   
   
   private Creation( String id, Uri entityUri, long timeStamp )
   {
      this.id = id;
      this.entityUri = entityUri;
      this.timeStamp = timeStamp;
   }
   
   
   
   public String getId()
   {
      return id;
   }
   
   
   
   public Uri getEntityUri()
   {
      return entityUri;
   }
   
   
   
   public long getTimeStamp()
   {
      return timeStamp;
   }
   
   
   
   @Override
   public String toString()
   {
      // Only return the entity ID cause toString() is used
      // in Queries.toColumnList()
      return entityUri.getLastPathSegment();
   }
   
   
   
   public final static Creation newCreation( Uri entityUri )
   {
      return newCreation( null, entityUri, System.currentTimeMillis() );
   }
   
   
   
   public final static Creation newCreation( Uri entityUri, long creationDate )
   {
      return newCreation( null, entityUri, creationDate );
   }
   
   
   
   public final static Creation newCreation( String id,
                                             Uri entityUri,
                                             long creationDate )
   {
      return new Creation( id, entityUri, creationDate );
   }
}
