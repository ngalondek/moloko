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
import android.widget.BaseAdapter;


abstract class AbstractHomeNavAdapter extends BaseAdapter
{
   private List< INavWidget > widgets;
   
   
   
   public final void setWidgets( List< INavWidget > widgets )
   {
      this.widgets = widgets;
      startWidgets( widgets );
   }
   
   
   
   @Override
   public int getCount()
   {
      return widgets.size();
   }
   
   
   
   @Override
   public Object getItem( int position )
   {
      return widgets.get( position );
   }
   
   
   
   @Override
   public long getItemId( int position )
   {
      return position;
   }
   
   
   
   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      return getWidget( position ).getView( convertView );
   }
   
   
   
   @Override
   public boolean hasStableIds()
   {
      return false;
   }
   
   
   
   @Override
   public boolean isEnabled( int position )
   {
      return getWidget( position ).getIntent() != null;
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
      return (INavWidget) getItem( position );
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
