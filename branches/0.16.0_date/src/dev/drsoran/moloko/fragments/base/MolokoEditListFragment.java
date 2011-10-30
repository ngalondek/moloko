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

package dev.drsoran.moloko.fragments.base;

import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.UIUtils;


public abstract class MolokoEditListFragment< D > extends
         MolokoListFragment< D >
{
   public final void performDatabaseModification( Runnable action )
   {
      if ( action != null )
      {
         if ( !hasWritableDatabaseAccess() )
         {
            showOnlyReadableDatabaseAccessDialog();
         }
         else
         {
            action.run();
         }
      }
   }
   
   
   
   private boolean hasWritableDatabaseAccess()
   {
      return AccountUtils.isWriteableAccess( getFragmentActivity() );
   }
   
   
   
   protected void showOnlyReadableDatabaseAccessDialog()
   {
      UIUtils.newReadOnlyAccessDialog( getFragmentActivity(), null ).show();
   }
}
