/*
 * Copyright (c) 2012 Ronny Röhricht
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

package dev.drsoran.moloko.app.home;

import java.util.Collection;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;


abstract class AbstractHomeNavAdapter extends BaseExpandableListAdapter
{
   private List< INavWidget > widgets;
   
   
   
   public final void setWidgets( List< INavWidget > widgets )
   {
      this.widgets = widgets;
      startWidgets( widgets );
   }
   
   
   
   @Override
   public int getGroupCount()
   {
      return widgets.size();
   }
   
   
   
   @Override
   public int getChildrenCount( int groupPosition )
   {
      return 0;
   }
   
   
   
   @Override
   public Object getGroup( int groupPosition )
   {
      return widgets.get( groupPosition );
   }
   
   
   
   @Override
   public Object getChild( int groupPosition, int childPosition )
   {
      return null;
   }
   
   
   
   @Override
   public long getGroupId( int groupPosition )
   {
      return groupPosition;
   }
   
   
   
   @Override
   public long getChildId( int groupPosition, int childPosition )
   {
      return childPosition + 1;
   }
   
   
   
   @Override
   public boolean hasStableIds()
   {
      return false;
   }
   
   
   
   @Override
   public View getGroupView( int groupPosition,
                             boolean isExpanded,
                             View convertView,
                             ViewGroup parent )
   {
      return getWidget( groupPosition ).getView( convertView );
   }
   
   
   
   @Override
   public View getChildView( int groupPosition,
                             int childPosition,
                             boolean isLastChild,
                             View convertView,
                             ViewGroup parent )
   {
      return null;
   }
   
   
   
   @Override
   public boolean isChildSelectable( int groupPosition, int childPosition )
   {
      return false;
   }
   
   
   
   @Override
   public boolean areAllItemsEnabled()
   {
      for ( INavWidget widget : widgets )
      {
         if ( widget.getIntent() == null )
         {
            return false;
         }
      }
      
      return true;
   }
   
   
   
   public INavWidget getWidget( int position )
   {
      return (INavWidget) getGroup( position );
   }
   
   
   
   public int getPositon( INavWidget widget )
   {
      for ( int i = 0; i < widgets.size(); i++ )
      {
         final INavWidget w = widgets.get( i );
         if ( w == widget )
         {
            return i;
         }
      }
      
      return -1;
   }
   
   
   
   public Intent getIntentForWidget( int position )
   {
      return getWidget( position ).getIntent();
   }
   
   
   
   public void updateWidgets()
   {
      for ( INavWidget widget : widgets )
      {
         widget.setDirty();
      }
   }
   
   
   
   public void insertWidgetsAfter( int position,
                                   Collection< INavWidget > widgets )
   {
      this.widgets.addAll( position + 1, widgets );
      startWidgets( widgets );
      
      notifyDataSetChanged();
   }
   
   
   
   public void replaceWidgets( int fromPosition,
                               int toPosition,
                               Collection< INavWidget > newWidgets )
   {
      int cnt = toPosition - fromPosition;
      
      while ( cnt-- > 0 )
      {
         final INavWidget widget = widgets.get( fromPosition );
         widget.stop();
         
         widgets.remove( fromPosition );
      }
      
      this.widgets.addAll( fromPosition, newWidgets );
      notifyDataSetChanged();
   }
   
   
   
   private void startWidgets( Iterable< INavWidget > widgets )
   {
      for ( INavWidget widget : widgets )
      {
         widget.start();
      }
   }
   
   
   
   public void stopWidgets()
   {
      for ( INavWidget widget : widgets )
      {
         widget.stop();
      }
   }
}
