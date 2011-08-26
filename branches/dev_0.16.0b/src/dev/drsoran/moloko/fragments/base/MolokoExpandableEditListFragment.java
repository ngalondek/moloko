/* 
 *	Copyright (c) 2011 Ronny Röhricht
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

package dev.drsoran.moloko.fragments.base;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;


public abstract class MolokoExpandableEditListFragment< D > extends
         MolokoEditListFragment< D > implements
         ExpandableListView.OnGroupClickListener,
         ExpandableListView.OnChildClickListener,
         ExpandableListView.OnGroupCollapseListener,
         ExpandableListView.OnGroupExpandListener
{
   @Override
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      super.onViewCreated( view, savedInstanceState );
      
      final ExpandableListView expandableListView = getExpandableListView();
      
      expandableListView.setOnGroupClickListener( this );
      expandableListView.setOnChildClickListener( this );
      expandableListView.setOnGroupCollapseListener( this );
      expandableListView.setOnGroupExpandListener( this );
   }
   


   @Override
   public View getEmptyView()
   {
      View emptyView = null;
      
      if ( getExpandableListView() != null )
         emptyView = getExpandableListView().getEmptyView();
      
      return emptyView;
   }
   


   @Override
   public boolean onGroupClick( ExpandableListView parent,
                                View v,
                                int groupPosition,
                                long id )
   {
      return false;
   }
   


   @Override
   public boolean onChildClick( ExpandableListView parent,
                                View v,
                                int groupPosition,
                                int childPosition,
                                long id )
   {
      return false;
   }
   


   @Override
   public void onGroupExpand( int groupPosition )
   {
   }
   


   @Override
   public void onGroupCollapse( int groupPosition )
   {
   }
   


   public ExpandableListView getExpandableListView()
   {
      return (ExpandableListView) super.getListView();
   }
   


   public void setExpandableListAdapter( ExpandableListAdapter adapter )
   {
      getExpandableListView().setAdapter( adapter );
   }
   


   public ExpandableListAdapter getExpandableListAdapter()
   {
      return getExpandableListView().getExpandableListAdapter();
   }
   


   @Override
   public boolean hasListAdapter()
   {
      return getExpandableListAdapter() != null;
   }
   


   @Override
   public final void setListAdapter( ListAdapter adapter )
   {
      throw new UnsupportedOperationException( "use setExpandableListAdapter" );
   }
   


   @Override
   public final ListAdapter getListAdapter()
   {
      throw new UnsupportedOperationException( "use getExpandableListAdapter" );
   }
   


   @Override
   public void onLoadFinished( Loader< D > loader, D data )
   {
      if ( data != null )
         setExpandableListAdapter( createExpandableListAdapterForResult( data ) );
      else
         getLoaderManager().destroyLoader( getLoaderId() );
   }
   


   @Override
   public void onLoaderReset( Loader< D > loader )
   {
      setExpandableListAdapter( createEmptyExpandableListAdapter() );
   }
   


   @Override
   protected void invalidateOptionsMenu()
   {
      if ( getActivity() != null )
         getActivity().invalidateOptionsMenu();
   }
   


   @Override
   protected void notifyDataSetChanged()
   {
      if ( getExpandableListAdapter() instanceof BaseExpandableListAdapter )
         ( (BaseExpandableListAdapter) getExpandableListAdapter() ).notifyDataSetChanged();
   }
   


   @Override
   protected final ListAdapter createEmptyListAdapter()
   {
      throw new UnsupportedOperationException( "use createEmptyExpandableListAdapter" );
   }
   


   @Override
   protected final ListAdapter createListAdapterForResult( D result )
   {
      throw new UnsupportedOperationException( "use createExpandableListAdapterForResult" );
   }
   


   abstract protected ExpandableListAdapter createEmptyExpandableListAdapter();
   


   abstract protected ExpandableListAdapter createExpandableListAdapterForResult( D result );
}
