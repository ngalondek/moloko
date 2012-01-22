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

package dev.drsoran.moloko.layouts;

import android.content.Context;
import android.graphics.LightingColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.Menu;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.actionbarsherlock.internal.view.menu.MenuItemImpl;
import com.actionbarsherlock.internal.view.menu.MenuView;

import dev.drsoran.moloko.R;


public class ActionBarMenuItemView extends LinearLayout implements
         View.OnClickListener, View.OnLongClickListener, MenuView.ItemView
{
   private MenuItemImpl itemData;
   
   private ImageButton icon;
   
   private Menu invokeTarget;
   
   private int invokerId;
   
   
   
   public ActionBarMenuItemView( Context context )
   {
      super( context );
   }
   
   
   
   public ActionBarMenuItemView( Context context, AttributeSet attrs )
   {
      super( context, attrs );
   }
   
   
   
   public ActionBarMenuItemView( Context context, AttributeSet attrs,
      int defStyle )
   {
      super( context, attrs, defStyle );
   }
   
   
   
   @Override
   public void initialize( MenuItemImpl itemData, int menuType )
   {
      this.itemData = itemData;
      icon = (ImageButton) findViewById( android.R.id.icon );
      icon.setOnClickListener( this );
      icon.setOnLongClickListener( this );
      
      setOnClickListener( this );
      setOnLongClickListener( this );
      
      setIcon( itemData.getIcon() );
      setTitle( itemData.getTitle() );
      setId( itemData.getItemId() );
      setVisibility( itemData.isVisible() ? View.VISIBLE : View.GONE );
      setEnabled( itemData.isEnabled() );
   }
   
   
   
   @Override
   public void setIcon( Drawable icon )
   {
      this.icon.setImageDrawable( icon );
      this.icon.setColorFilter( new LightingColorFilter( 1,
                                                         getContext().getResources()
                                                                     .getColor( R.color.app_actionbar_action_button_tint ) ) );
   }
   
   
   
   public void setInvokeTarget( Menu menu, int menuItemId )
   {
      invokeTarget = menu;
      invokerId = menuItemId;
   }
   
   
   
   @Override
   public void onClick( View v )
   {
      if ( invokeTarget != null )
         invokeTarget.performIdentifierAction( invokerId, 0 );
   }
   
   
   
   @Override
   public MenuItemImpl getItemData()
   {
      return itemData;
   }
   
   
   
   @Override
   public void setTitle( CharSequence title )
   {
      setContentDescription( title );
   }
   
   
   
   @Override
   public void setCheckable( boolean checkable )
   {
   }
   
   
   
   @Override
   public void setChecked( boolean checked )
   {
   }
   
   
   
   @Override
   public void setShortcut( boolean showShortcut, char shortcutKey )
   {
   }
   
   
   
   @Override
   public boolean prefersCondensedTitle()
   {
      return true;
   }
   
   
   
   @Override
   public boolean showsIcon()
   {
      return true;
   }
   
   
   
   /**
    * Taken from com.actionbarsherlock.internal.view.menu.ActionMenuItemView.onLongClick(View v)
    */
   @Override
   public boolean onLongClick( View v )
   {
      final int[] screenPos = new int[ 2 ];
      final Rect displayFrame = new Rect();
      getLocationOnScreen( screenPos );
      getWindowVisibleDisplayFrame( displayFrame );
      
      final Context context = getContext();
      final int width = getWidth();
      final int height = getHeight();
      final int midy = screenPos[ 1 ] + height / 2;
      final int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
      
      Toast cheatSheet = Toast.makeText( context,
                                         itemData.getTitle(),
                                         Toast.LENGTH_SHORT );
      if ( midy < displayFrame.height() )
      {
         // Show along the top; follow action buttons
         cheatSheet.setGravity( Gravity.TOP | Gravity.RIGHT, screenWidth
            - screenPos[ 0 ] - width / 2, height );
      }
      else
      {
         // Show along the bottom center
         cheatSheet.setGravity( Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,
                                0,
                                height );
      }
      cheatSheet.show();
      return true;
   }
}
