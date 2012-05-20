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

import android.content.Context;

import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.mdt.rtm.data.RtmTaskNote;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.actionmodes.listener.INotesListActionModeListener;
import dev.drsoran.moloko.adapters.ISelectableAdapter;


public class NotesListActionModeCallback extends
         BaseSelectableActionModeCallback< RtmTaskNote >
{
   INotesListActionModeListener listener;
   
   
   
   public NotesListActionModeCallback( Context context,
      ISelectableAdapter< RtmTaskNote > adapter )
   {
      super( context, adapter );
   }
   
   
   
   public void setNotesListActionModeListener( INotesListActionModeListener listener )
   {
      this.listener = listener;
   }
   
   
   
   @Override
   public boolean onCreateActionMode( ActionMode mode, Menu menu )
   {
      super.onCreateActionMode( mode, menu );
      mode.getMenuInflater().inflate( R.menu.noteslist_actionmode, menu );
      
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
               listener.onDeleteNotes( getAdapter().getSelectedItems() );
               return true;
            }
            return false;
            
         default :
            return super.onActionItemClicked( mode, item );
      }
   }
}
