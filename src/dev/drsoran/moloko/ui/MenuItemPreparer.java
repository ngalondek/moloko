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

package dev.drsoran.moloko.ui;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;


public final class MenuItemPreparer
{
   private final Menu menu;
   
   
   
   public MenuItemPreparer( Menu menu )
   {
      this.menu = menu;
   }
   
   
   
   public MenuItemPreparer setVisible( int itemId, boolean visible )
   {
      final MenuItem item = menu.findItem( itemId );
      if ( item != null )
      {
         item.setVisible( visible );
      }
      
      return this;
   }
}
