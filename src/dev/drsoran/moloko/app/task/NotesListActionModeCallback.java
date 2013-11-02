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

package dev.drsoran.moloko.app.task;

import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.domain.model.Note;
import dev.drsoran.moloko.ui.actionmodes.BaseMultiChoiceModeListener;
import dev.drsoran.moloko.ui.widgets.MolokoListView;


class NotesListActionModeCallback extends BaseMultiChoiceModeListener< Note >
{
   INotesListActionModeListener listener;
   
   
   
   public NotesListActionModeCallback( MolokoListView listView )
   {
      super( listView );
   }
   
   
   
   public void setNotesListActionModeListener( INotesListActionModeListener listener )
   {
      this.listener = listener;
   }
   
   
   
   @Override
   public boolean onCreateActionMode( ActionMode mode, Menu menu )
   {
      super.onCreateActionMode( mode, menu );
      mode.getMenuInflater().inflate( R.menu.noteslist_actionmode_rwd, menu );
      
      return true;
   }
   
   
   
   @Override
   public boolean onPrepareActionMode( ActionMode mode, Menu menu )
   {
      super.onPrepareActionMode( mode, menu );
      menu.findItem( R.id.menu_selection ).setIcon( null );
      return true;
   }
   
   
   
   @Override
   public boolean onActionItemClicked( ActionMode mode, MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case R.id.menu_delete_selected:
            if ( listener != null )
            {
               listener.onDeleteNotes( getSelectedItems() );
               return true;
            }
            return false;
            
         default :
            return super.onActionItemClicked( mode, item );
      }
   }
}
