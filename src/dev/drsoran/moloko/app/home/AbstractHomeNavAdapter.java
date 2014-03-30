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

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


abstract class AbstractHomeNavAdapter extends BaseAdapter
{
   private List< INavWidget > widgets;
   
   
   
   protected AbstractHomeNavAdapter( Context context )
   {
      registerDataSetObserver( new DataSetObserver()
      {
         @Override
         public void onChanged()
         {
            updateWidgets();
         }
      } );
   }
   
   
   
   public final void setWidgets( List< INavWidget > widgets )
   {
      this.widgets = widgets;
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
   
   
   
   public INavWidget getWidget( int position )
   {
      return (INavWidget) getItem( position );
   }
   
   
   
   @Override
   public long getItemId( int position )
   {
      return 0;
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
   
   
   
   @Override
   public boolean isEnabled( int position )
   {
      return getWidget( position ).getIntent() != null;
   }
   
   
   
   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      return getWidget( position ).getView( convertView );
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
   
   
   
   public void addWidget( INavWidget widget )
   {
      widgets.add( widget );
      notifyDataSetChanged();
   }
   
   
   
   public void removeWidget( INavWidget widget )
   {
      widget.stop();
      widgets.remove( widget );
      
      notifyDataSetChanged();
   }
   
   
   
   public void stopWidgets()
   {
      for ( INavWidget widget : widgets )
      {
         widget.stop();
      }
   }
}
