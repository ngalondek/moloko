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
import android.widget.Checkable;
import dev.drsoran.moloko.R;
import dev.drsoran.rtm.ListTask;
import dev.drsoran.rtm.SelectableListTask;


public class SelectableTasksListFragmentAdapter extends
         MinDetailedTasksListFragmentAdapter
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + SelectableTasksListFragmentAdapter.class.getName();
   
   
   private final static class CmpSelection implements Comparator< ListTask >
   {
      public int compare( ListTask object1, ListTask object2 )
      {
         if ( object1 == object2 )
            return 0;
         
         final boolean isSel1 = ( (SelectableListTask) object1 ).isSelected();
         final boolean isSel2 = ( (SelectableListTask) object2 ).isSelected();
         
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
   
   

   public SelectableTasksListFragmentAdapter( Context context, int resourceId )
   {
      this( context, resourceId, Collections.< SelectableListTask > emptyList() );
   }
   


   public SelectableTasksListFragmentAdapter( Context context, int resourceId,
      List< SelectableListTask > tasks )
   {
      super( context, resourceId, SelectableListTask.asListTaskList( tasks ) );
   }
   


   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      final View v = super.getView( position, convertView, parent );
      
      ( (Checkable) v.findViewById( R.id.taskslist_listitem_priority ) ).setChecked( getItem( position ).isSelected() );
      
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
      
      notifyDataSetChanged();
   }
   


   public void setSelection( int pos, boolean select )
   {
      getItem( pos ).setSelected( select );
      notifyDataSetChanged();
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
   


   public List< SelectableListTask > getSelectedTasks()
   {
      final List< SelectableListTask > selected = new LinkedList< SelectableListTask >();
      
      for ( int i = 0, cnt = getCount(); i < cnt; ++i )
         if ( getItem( i ).isSelected() )
            selected.add( getItem( i ) );
      
      return selected;
   }
   


   public void setSelectedTaskIds( List< String > taskIds )
   {
      for ( int i = 0, cnt = getCount(); i < cnt; ++i )
         getItem( i ).setSelected( false );
      
      for ( String s : taskIds )
         for ( int i = 0, cnt = getCount(); i < cnt; ++i )
            if ( getItem( i ).getId().equals( s ) )
               getItem( i ).setSelected( true );
      
      notifyDataSetChanged();
   }
   


   @Override
   public SelectableListTask getItem( int position )
   {
      return (SelectableListTask) super.getItem( position );
   }
}
