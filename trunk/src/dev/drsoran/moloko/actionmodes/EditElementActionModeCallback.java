/* 
 *	Copyright (c) 2012 Ronny Röhricht
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

package dev.drsoran.moloko.actionmodes;

import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import dev.drsoran.moloko.IEditFragment;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.activities.base.MolokoEditFragmentActivity;


public class EditElementActionModeCallback implements ActionMode.Callback
{
   private final MolokoEditFragmentActivity activity;
   
   private final IEditFragment editFragment;
   
   
   
   public EditElementActionModeCallback( MolokoEditFragmentActivity activity,
      IEditFragment editFragment )
   {
      this.activity = activity;
      this.editFragment = editFragment;
   }
   
   
   
   @Override
   public boolean onCreateActionMode( ActionMode mode, Menu menu )
   {
      mode.getMenuInflater().inflate( R.menu.edit_mode, menu );
      return true;
   }
   
   
   
   @Override
   public boolean onPrepareActionMode( ActionMode mode, Menu menu )
   {
      return false;
   }
   
   
   
   @Override
   public boolean onActionItemClicked( ActionMode mode, MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case R.id.menu_save:
            if ( saveChanges() )
            {
               mode.finish();
            }
            return true;
            
         default :
            return false;
      }
   }
   
   
   
   @Override
   public void onDestroyActionMode( ActionMode mode )
   {
   }
   
   
   
   private boolean saveChanges()
   {
      return activity.finishFragmentEditing( editFragment );
   }
}
