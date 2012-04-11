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

package dev.drsoran.moloko.util;

import android.content.Intent;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;


public final class MolokoMenuItemBuilder
{
   private int itemId;
   
   private CharSequence title;
   
   private int order = MenuCategory.NONE;
   
   private int groupId = Menu.NONE;
   
   private int iconId = -1;
   
   private int showAsActionFlags = MenuItem.SHOW_AS_ACTION_NEVER;
   
   private Intent intent;
   
   private boolean show;
   
   
   
   public MolokoMenuItemBuilder setItemId( int itemId )
   {
      this.itemId = itemId;
      return this;
   }
   
   
   
   public MolokoMenuItemBuilder setTitle( CharSequence title )
   {
      this.title = title;
      return this;
   }
   
   
   
   public MolokoMenuItemBuilder setOrder( int order )
   {
      this.order = order;
      return this;
   }
   
   
   
   public MolokoMenuItemBuilder setGroupId( int groupId )
   {
      this.groupId = groupId;
      return this;
   }
   
   
   
   public MolokoMenuItemBuilder setIconId( int iconId )
   {
      this.iconId = iconId;
      return this;
   }
   
   
   
   public MolokoMenuItemBuilder setShowAsActionFlags( int showAsActionFlags )
   {
      this.showAsActionFlags = showAsActionFlags;
      return this;
   }
   
   
   
   public MolokoMenuItemBuilder setIntent( Intent intent )
   {
      this.intent = intent;
      return this;
   }
   
   
   
   public MolokoMenuItemBuilder setShow( boolean show )
   {
      this.show = show;
      return this;
   }
   
   
   
   public void build( Menu menu )
   {
      if ( show )
      {
         MenuItem item = menu.findItem( itemId );
         
         if ( item == null )
         {
            item = menu.add( groupId, itemId, order, title );
            
            if ( iconId != -1 )
               item.setIcon( iconId );
         }
         
         item.setTitle( title );
         
         if ( intent != null )
         {
            item.setIntent( intent );
         }
         
         item.setShowAsAction( showAsActionFlags );
      }
      else
      {
         menu.removeItem( itemId );
      }
   }
}
