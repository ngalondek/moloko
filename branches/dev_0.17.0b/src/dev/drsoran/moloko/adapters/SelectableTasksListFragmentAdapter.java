/*
 * Copyright (c) 2010 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import dev.drsoran.moloko.R;
import dev.drsoran.rtm.SelectableTask;
import dev.drsoran.rtm.Task;


public class SelectableTasksListFragmentAdapter extends
         MinDetailedTasksListFragmentAdapter
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + SelectableTasksListFragmentAdapter.class.getName();
   
   
   public static interface ISelectionChangedListener
   {
      void onSelectionChanged();
   }
   
   
   private final static class CmpSelection implements Comparator< Task >
   {
      @Override
      public int compare( Task object1, Task object2 )
      {
         if ( object1 == object2 )
            return 0;
         
         final boolean isSel1 = ( (SelectableTask) object1 ).isSelected();
         final boolean isSel2 = ( (SelectableTask) object2 ).isSelected();
         
         if ( isSel1 == isSel2 )
            return 0;
         
         if ( isSel1 && !isSel2 )
            return -1;
         else
            return 1;
      }
   }
   
   public final static int SEL_MODE_ALL = 1;
   
   public final static int SEL_MODE_NONE = 2;
   
   public final static int SEL_MODE_INVERT = 3;
   
   private ISelectionChangedListener listener;
   
   
   
   public SelectableTasksListFragmentAdapter( Context context, int resourceId )
   {
      this( context, resourceId, Collections.< SelectableTask > emptyList() );
   }
   
   
   
   public SelectableTasksListFragmentAdapter( Context context, int resourceId,
      List< SelectableTask > tasks )
   {
      super( context, resourceId, SelectableTask.asTaskList( tasks ) );
   }
   
   
   
   public void setSelectionChangedListener( ISelectionChangedListener listener )
   {
      this.listener = listener;
   }
   
   
   
   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      final View v = super.getView( position, convertView, parent );
      
      final boolean isSelected = getItem( position ).isSelected();
      
      ( (CheckBox) v.findViewById( R.id.selectmultipletasks_listitem_selected ) ).setChecked( isSelected );
      
      return v;
   }
   
   
   
   public void sortBySelection()
   {
      sort( new CmpSelection() );
      notifyDataSetChanged();
   }
   
   
   
   public void changeSelection( int mode )
   {
      switch ( mode )
      {
         case SEL_MODE_ALL:
            for ( int i = 0, cnt = getCount(); i < cnt; ++i )
               setSelection( i, true );
            break;
         
         case SEL_MODE_NONE:
            for ( int i = 0, cnt = getCount(); i < cnt; ++i )
               setSelection( i, false );
            break;
         
         case SEL_MODE_INVERT:
            for ( int i = 0, cnt = getCount(); i < cnt; ++i )
               toggleSelection( i );
            break;
         
         default :
            break;
      }
   }
   
   
   
   public void setSelection( int pos, boolean select )
   {
      setSelectionWithoutNotify( pos, select );
      notifyChanged();
   }
   
   
   
   private void setSelectionWithoutNotify( int pos, boolean select )
   {
      getItem( pos ).setSelected( select );
   }
   
   
   
   public void toggleSelection( int pos )
   {
      setSelection( pos, !getItem( pos ).isSelected() );
   }
   
   
   
   public boolean areAllSelected()
   {
      for ( int i = 0, cnt = getCount(); i < cnt; ++i )
         if ( !getItem( i ).isSelected() )
            return false;
      
      return true;
   }
   
   
   
   public boolean areSomeSelected()
   {
      for ( int i = 0, cnt = getCount(); i < cnt; ++i )
         if ( getItem( i ).isSelected() )
            return true;
      
      return false;
   }
   
   
   
   public int getSelectedCount()
   {
      int sc = 0;
      
      for ( int i = 0, cnt = getCount(); i < cnt; ++i )
         if ( getItem( i ).isSelected() )
            ++sc;
      
      return sc;
   }
   
   
   
   public ArrayList< String > getSelectedTaskIds()
   {
      final ArrayList< String > selected = new ArrayList< String >();
      
      for ( int i = 0, cnt = getCount(); i < cnt; ++i )
         if ( getItem( i ).isSelected() )
            selected.add( getItem( i ).getId() );
      
      return selected;
   }
   
   
   
   public List< SelectableTask > getSelectedTasks()
   {
      final List< SelectableTask > selected = new LinkedList< SelectableTask >();
      
      for ( int i = 0, cnt = getCount(); i < cnt; ++i )
         if ( getItem( i ).isSelected() )
            selected.add( getItem( i ) );
      
      return selected;
   }
   
   
   
   public void setSelectedTaskIds( List< String > taskIds )
   {
      for ( int i = 0, cnt = getCount(); i < cnt; ++i )
         setSelectionWithoutNotify( i, false );
      
      for ( String s : taskIds )
         for ( int i = 0, cnt = getCount(); i < cnt; ++i )
            if ( getItem( i ).getId().equals( s ) )
               setSelectionWithoutNotify( i, true );
   }
   
   
   
   public void setSelectedTaskIdsAndNotifyDataChanged( List< String > taskIds )
   {
      setSelectedTaskIds( taskIds );
      notifyChanged();
   }
   
   
   
   @Override
   public SelectableTask getItem( int position )
   {
      return (SelectableTask) super.getItem( position );
   }
   
   
   
   private void notifySelectionChanged()
   {
      if ( listener != null )
         listener.onSelectionChanged();
   }
   
   
   
   private void notifyChanged()
   {
      notifyDataSetChanged();
      notifySelectionChanged();
   }
}
