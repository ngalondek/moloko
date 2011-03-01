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

package dev.drsoran.moloko.sync.util;

import java.util.Date;

import android.net.Uri;
import dev.drsoran.moloko.content.ModificationSet;


public class SyncProperties
{
   public final Date serverModDate;
   
   public final Date localModDate;
   
   public final Uri uri;
   
   public final ModificationSet modifications;
   
   

   private SyncProperties( Date serverModDate, Date localModDate, Uri uri,
      ModificationSet modifications )
   {
      this.serverModDate = serverModDate;
      this.localModDate = localModDate;
      this.uri = uri;
      this.modifications = modifications;
   }
   


   public final static SyncProperties newInstance( Date serverModDate,
                                                   Date localModDate,
                                                   Uri uri,
                                                   ModificationSet modifications )
   {
      return new SyncProperties( serverModDate,
                                 localModDate,
                                 uri,
                                 modifications );
   }
   


   public final static SyncProperties newLocalOnlyInstance( Date serverModDate,
                                                            Date localModDate,
                                                            Uri uri )
   {
      return new SyncProperties( serverModDate, localModDate, uri, null );
   }
}
