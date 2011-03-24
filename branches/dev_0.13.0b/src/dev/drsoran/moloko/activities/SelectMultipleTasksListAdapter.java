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

package dev.drsoran.moloko.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import dev.drsoran.moloko.R;
import dev.drsoran.rtm.ListTask;
import dev.drsoran.rtm.RtmSmartFilter;


public class SelectMultipleTasksListAdapter extends TasksListAdapter
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + SelectMultipleTasksListAdapter.class.getName();
   
   private final List< String > selectionState;
   
   

   public SelectMultipleTasksListAdapter( Context context, int resourceId,
      List< ListTask > tasks, RtmSmartFilter filter )
   {
      super( context, resourceId, tasks, filter );
      selectionState = new ArrayList< String >( Collections.< String > nCopies( tasks.size(),
                                                                                null ) );
   }
   


   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      final View v = super.getView( position, convertView, parent );
      
      if ( selectionState.get( position ) == null )
         v.setBackgroundColor( R.color.select_multiple_listitem_unselected_bgnd );
      else
         v.setBackgroundColor( R.color.select_multiple_listitem_selected_bgnd );
      
      return v;
   }
   


   public void toggleSelection( int pos )
   {
      if ( selectionState.get( pos ) == null )
         selectionState.set( pos, getItem( pos ).getId() );
      else
         selectionState.set( pos, null );
   }
}
