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

package dev.drsoran.moloko.ui.actionmodes;

import android.view.View;

import com.actionbarsherlock.internal.view.menu.MenuWrapper;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.MenuInflater;


/**
 * @author ActionBarSherlock
 */
public class ActionModeWrapper extends ActionMode
{
   private final MenuInflater menuInflater;
   
   private final android.view.ActionMode mActionMode;
   
   private MenuWrapper mMenu = null;
   
   
   
   public ActionModeWrapper( MenuInflater menuInflater,
      android.view.ActionMode actionMode )
   {
      mActionMode = actionMode;
      this.menuInflater = menuInflater;
   }
   
   
   
   public android.view.ActionMode getWrapped()
   {
      return mActionMode;
   }
   
   
   
   @Override
   public void setTitle( CharSequence title )
   {
      mActionMode.setTitle( title );
   }
   
   
   
   @Override
   public void setTitle( int resId )
   {
      mActionMode.setTitle( resId );
   }
   
   
   
   @Override
   public void setSubtitle( CharSequence subtitle )
   {
      mActionMode.setSubtitle( subtitle );
   }
   
   
   
   @Override
   public void setSubtitle( int resId )
   {
      mActionMode.setSubtitle( resId );
   }
   
   
   
   @Override
   public void setCustomView( View view )
   {
      mActionMode.setCustomView( view );
   }
   
   
   
   @Override
   public void invalidate()
   {
      mActionMode.invalidate();
   }
   
   
   
   @Override
   public void finish()
   {
      mActionMode.finish();
   }
   
   
   
   @Override
   public MenuWrapper getMenu()
   {
      if ( mMenu == null )
      {
         mMenu = new MenuWrapper( mActionMode.getMenu() );
      }
      return mMenu;
   }
   
   
   
   @Override
   public CharSequence getTitle()
   {
      return mActionMode.getTitle();
   }
   
   
   
   @Override
   public CharSequence getSubtitle()
   {
      return mActionMode.getSubtitle();
   }
   
   
   
   @Override
   public View getCustomView()
   {
      return mActionMode.getCustomView();
   }
   
   
   
   @Override
   public MenuInflater getMenuInflater()
   {
      return menuInflater;
   }
}
