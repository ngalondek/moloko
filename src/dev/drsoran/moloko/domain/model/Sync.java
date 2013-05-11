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


public class Sync
{
   private final long lastSyncInMillis;
   
   private final long lastSyncOutMillis;
   
   
   
   public Sync( long lastSyncInMillis, long lastSyncOutMillis )
   {
      this.lastSyncInMillis = lastSyncInMillis;
      this.lastSyncOutMillis = lastSyncOutMillis;
   }
   
   
   
   public long getLastSyncInMillis()
   {
      return lastSyncInMillis;
   }
   
   
   
   public boolean hasEverSyncedIn()
   {
      return lastSyncInMillis != Constants.NO_TIME;
   }
   
   
   
   public long getLastSyncOutMillis()
   {
      return lastSyncOutMillis;
   }
   
   
   
   public boolean hasEverSyncedOut()
   {
      return lastSyncOutMillis != Constants.NO_TIME;
   }
   
   
   
   @Override
   public String toString()
   {
      return String.format( "Sync [lastSyncIn=%s, lastSyncOut=%s]",
                            new Date( lastSyncInMillis ),
                            new Date( lastSyncOutMillis ) );
   }
}
