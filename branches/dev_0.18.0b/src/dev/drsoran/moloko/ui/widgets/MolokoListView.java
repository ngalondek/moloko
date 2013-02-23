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

package dev.drsoran.moloko.ui.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.MenuInflater;


public abstract class MolokoListView extends ListView
{
   public final static int CHOICE_MODE_MULTIPLE_MODAL = 3;
   
   
   public interface IActionModeSupport
   {
      ActionMode startActionMode( IMolokoMultiChoiceModeListener callback );
      
      
      
      MenuInflater getSupportMenuInflater();
   }
   
   
   public interface IMolokoMultiChoiceModeListener extends ActionMode.Callback
   {
      void onItemCheckedStateChanged( ActionMode mode,
                                      int position,
                                      long id,
                                      boolean checked );
   }
   
   protected IActionModeSupport actionModeSupport;
   
   
   
   protected MolokoListView( Context context )
   {
      super( context );
   }
   
   
   
   protected MolokoListView( Context context, AttributeSet attrs )
   {
      super( context, attrs );
   }
   
   
   
   protected MolokoListView( Context context, AttributeSet attrs, int defStyle )
   {
      super( context, attrs, defStyle );
   }
   
   
   
   public void setActionModeSupport( IActionModeSupport support )
   {
      this.actionModeSupport = support;
   }
   
   
   
   public boolean isInMultiChoiceModalActionMode()
   {
      return getChoiceMode() == CHOICE_MODE_MULTIPLE_MODAL
         && getCheckedItemCountSupport() > 0;
   }
   
   
   
   @Override
   public void handleDataChanged()
   {
      super.handleDataChanged();
   }
   
   
   
   /**
    * Use getCheckedItemCountSupport() since this works with all Android versions.
    */
   @Deprecated
   @Override
   public int getCheckedItemCount()
   {
      return super.getCheckedItemCount();
   }
   
   
   
   public < T > List< T > getCheckedItems()
   {
      final List< T > checkedItems;
      final ListAdapter adapter = getAdapter();
      final int numItems = adapter.getCount();
      
      if ( numItems > 0 )
      {
         final SparseBooleanArray checkedPositions = getCheckedItemPositions();
         final int numSelected = checkedPositions.size();
         
         checkedItems = new ArrayList< T >( numSelected );
         
         for ( int i = 0; i < numSelected; ++i )
         {
            final int position = checkedPositions.keyAt( i );
            final boolean isChecked = checkedPositions.get( position );
            
            // Note: We have to check the position against the number of items
            // to circumvent an issue in Android where the registration order
            // of a DataSetObserver may cause the checked state of the list
            // items to be stale.
            if ( isChecked && position < numItems )
            {
               @SuppressWarnings( "unchecked" )
               final T selectedItem = (T) adapter.getItem( position );
               
               checkedItems.add( selectedItem );
            }
         }
      }
      else
      {
         checkedItems = Collections.emptyList();
      }
      
      return checkedItems;
   }
   
   
   
   public int getCheckedItemCountSupport()
   {
      int selectedItemCount = 0;
      
      final SparseBooleanArray checkedPositions = getCheckedItemPositions();
      final int numSelected = checkedPositions.size();
      final int numItems = getAdapter().getCount();
      
      for ( int i = 0; i < numSelected; ++i )
      {
         final int position = checkedPositions.keyAt( i );
         final boolean isChecked = checkedPositions.get( position );
         
         // Note: We have to check the position against the number of items
         // to circumvent an issue in Android where the registration order
         // of a DataSetObserver may cause the checked state of the list
         // items to be stale.
         if ( isChecked && position < numItems )
         {
            ++selectedItemCount;
         }
      }
      
      return selectedItemCount;
   }
   
   
   
   public abstract void setMolokoMultiChoiceModeListener( IMolokoMultiChoiceModeListener listener );
}
