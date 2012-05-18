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

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.actionmodes.listener.IBaseSelectableActionModeListener;
import dev.drsoran.moloko.adapters.ISelectableAdapter;
import dev.drsoran.moloko.util.Strings;


public class BaseSelectableActionModeCallback< T > implements
         ActionMode.Callback
{
   private final Context context;
   
   private ISelectableAdapter< T > adapter;
   
   private IBaseSelectableActionModeListener listener;
   
   
   
   public BaseSelectableActionModeCallback( Context context,
      ISelectableAdapter< T > adapter )
   {
      this.context = context;
      this.adapter = adapter;
   }
   
   
   
   public Context getContext()
   {
      return context;
   }
   
   
   
   public void attachAdapter( ISelectableAdapter< T > adapter )
   {
      this.adapter = adapter;
   }
   
   
   
   public ISelectableAdapter< T > getAdapter()
   {
      return adapter;
   }
   
   
   
   public void setActionModeListener( IBaseSelectableActionModeListener listener )
   {
      this.listener = listener;
   }
   
   
   
   @Override
   public boolean onCreateActionMode( ActionMode mode, Menu menu )
   {
      mode.getMenuInflater().inflate( R.menu.selection_mode_female, menu );
      return true;
   }
   
   
   
   @Override
   public boolean onPrepareActionMode( ActionMode mode, Menu menu )
   {
      final int selectedCnt = adapter.getSelectedCount();
      
      setTitle( mode, selectedCnt );
      setMenuItemsVisibility( menu, selectedCnt );
      
      return true;
   }
   
   
   
   private void setMenuItemsVisibility( Menu menu, int selectedCnt )
   {
      menu.findItem( R.id.menu_delete_selected ).setVisible( selectedCnt > 0 );
   }
   
   
   
   @Override
   public boolean onActionItemClicked( ActionMode mode, MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case R.id.menu_select_all:
            adapter.selectAll();
            return true;
            
         case R.id.menu_deselect_all:
            adapter.deselectAll();
            return true;
            
         case R.id.menu_invert_selection:
            adapter.invertSelection();
            return true;
            
         default :
            return false;
      }
   }
   
   
   
   @Override
   public void onDestroyActionMode( ActionMode mode )
   {
      if ( listener != null )
      {
         listener.onFinishActionMode();
         listener = null;
      }
   }
   
   
   
   private void setTitle( ActionMode mode, int selectedCnt )
   {
      final CharSequence title;
      if ( selectedCnt > 0 )
      {
         title = context.getResources().getString( R.string.app_selected_count,
                                                   selectedCnt );
      }
      else
      {
         title = Strings.EMPTY_STRING;
      }
      
      mode.setTitle( title );
   }
}
