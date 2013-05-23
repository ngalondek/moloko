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

import java.util.Date;

import dev.drsoran.moloko.content.Constants;


abstract class LifeTimeManaged
{
   private final long createdMillisUtc;
   
   private long modifiedMillisUtc = Constants.NO_TIME;
   
   private long deletedMillisUtc = Constants.NO_TIME;
   
   
   
   protected LifeTimeManaged( long createdMillisUtc )
   {
      if ( createdMillisUtc == Constants.NO_TIME )
      {
         throw new IllegalArgumentException( "createdMillisUtc" );
      }
      
      this.createdMillisUtc = createdMillisUtc;
   }
   
   
   
   public long getCreatedMillisUtc()
   {
      return createdMillisUtc;
   }
   
   
   
   public long getModifiedMillisUtc()
   {
      return modifiedMillisUtc;
   }
   
   
   
   public void setModifiedMillisUtc( long modifiedMillisUtc )
   {
      this.modifiedMillisUtc = modifiedMillisUtc;
   }
   
   
   
   public boolean isModified()
   {
      return modifiedMillisUtc != Constants.NO_TIME;
   }
   
   
   
   public long getDeletedMillisUtc()
   {
      return deletedMillisUtc;
   }
   
   
   
   public void setDeletedMillisUtc( long deletedMillisUtc )
   {
      this.deletedMillisUtc = deletedMillisUtc;
   }
   
   
   
   public boolean isDeleted()
   {
      return deletedMillisUtc != Constants.NO_TIME;
   }
   
   
   
   @Override
   public String toString()
   {
      return String.format( "created: %s, modified: %s, deleted: %s",
                            new Date( createdMillisUtc ),
                            modifiedMillisUtc != Constants.NO_TIME
                                                                  ? new Date( modifiedMillisUtc )
                                                                  : "never",
                            deletedMillisUtc != Constants.NO_TIME
                                                                 ? new Date( deletedMillisUtc )
                                                                 : "never" );
   }
}
