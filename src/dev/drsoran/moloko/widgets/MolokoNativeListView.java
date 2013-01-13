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

package dev.drsoran.moloko.widgets;

import android.content.Context;
import android.util.AttributeSet;

import com.actionbarsherlock.internal.view.menu.MenuItemWrapper;
import com.actionbarsherlock.internal.view.menu.MenuWrapper;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import dev.drsoran.moloko.ActionModeWrapper;


public class MolokoNativeListView extends MolokoListView
{
   private class MolokoMultiChoiceModeWrapper implements
            MultiChoiceModeListener
   {
      private final IMolokoMultiChoiceModeListener wrapped;
      
      
      
      public MolokoMultiChoiceModeWrapper(
         IMolokoMultiChoiceModeListener wrapped )
      {
         this.wrapped = wrapped;
      }
      
      
      
      @Override
      public boolean onActionItemClicked( android.view.ActionMode mode,
                                          android.view.MenuItem item )
      {
         return wrapped.onActionItemClicked( wrap( mode ), wrap( item ) );
      }
      
      
      
      @Override
      public boolean onCreateActionMode( android.view.ActionMode mode,
                                         android.view.Menu menu )
      {
         return wrapped.onCreateActionMode( wrap( mode ), wrap( menu ) );
      }
      
      
      
      @Override
      public void onDestroyActionMode( android.view.ActionMode mode )
      {
         wrapped.onDestroyActionMode( wrap( mode ) );
      }
      
      
      
      @Override
      public boolean onPrepareActionMode( android.view.ActionMode mode,
                                          android.view.Menu menu )
      {
         return wrapped.onPrepareActionMode( wrap( mode ), wrap( menu ) );
      }
      
      
      
      @Override
      public void onItemCheckedStateChanged( android.view.ActionMode mode,
                                             int position,
                                             long id,
                                             boolean checked )
      {
         wrapped.onItemCheckedStateChanged( wrap( mode ), position, id, checked );
      }
      
      
      
      private ActionMode wrap( android.view.ActionMode mode )
      {
         return new ActionModeWrapper( actionModeSupport.getSupportMenuInflater(),
                                       mode );
      }
      
      
      
      private Menu wrap( android.view.Menu menu )
      {
         return new MenuWrapper( menu );
      }
      
      
      
      private MenuItem wrap( android.view.MenuItem menuItem )
      {
         return new MenuItemWrapper( menuItem );
      }
   }
   
   
   
   public MolokoNativeListView( Context context )
   {
      super( context );
   }
   
   
   
   public MolokoNativeListView( Context context, AttributeSet attrs )
   {
      super( context, attrs );
   }
   
   
   
   public MolokoNativeListView( Context context, AttributeSet attrs,
      int defStyle )
   {
      super( context, attrs, defStyle );
   }
   
   
   
   @Override
   public void setMolokoMultiChoiceModeListener( IMolokoMultiChoiceModeListener listener )
   {
      super.setMultiChoiceModeListener( new MolokoMultiChoiceModeWrapper( listener ) );
   }
}
