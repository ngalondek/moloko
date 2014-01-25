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

package dev.drsoran.rtm.sync;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.SyncResult;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;


public final class MolokoSyncResult
{
   public final Context context;
   
   public final SyncResult androidSyncResult;
   
   public final List< IContentProviderSyncOperation > localOps = new LinkedList< IContentProviderSyncOperation >();
   
   

   public MolokoSyncResult( Context context, SyncResult result )
   {
      this.context = context;
      this.androidSyncResult = result;
   }
}
