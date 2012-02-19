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

package dev.drsoran.moloko.fragments.base;

import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.app.SupportActivity;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import dev.drsoran.moloko.IConfigurable;
import dev.drsoran.moloko.fragments.base.impl.LoaderExpandableListFragmentImpl;
import dev.drsoran.moloko.fragments.base.impl.MolokoListFragmentImpl;


public abstract class MolokoExpandableListFragment< D > extends ListFragment
         implements IConfigurable, LoaderCallbacks< D >,
         LoaderExpandableListFragmentImpl.Support< D >,
         ExpandableListView.OnGroupClickListener,
         ExpandableListView.OnChildClickListener,
         ExpandableListView.OnGroupCollapseListener,
         ExpandableListView.OnGroupExpandListener
{
   private final MolokoListFragmentImpl baseImpl;
   
   private final LoaderExpandableListFragmentImpl< D > loaderImpl;
   
   private final EditFragmentImpl editImpl;
   
   
   
   protected MolokoExpandableListFragment()
   {
      baseImpl = new MolokoListFragmentImpl( this, getSettingsMask() );
      loaderImpl = new LoaderExpandableListFragmentImpl< D >( this );
      editImpl = new EditFragmentImpl( this );
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      baseImpl.onCreate( savedInstanceState );
      loaderImpl.onCreate( savedInstanceState );
   }
   
   
   
   @Override
   public void onAttach( SupportActivity activity )
   {
      super.onAttach( activity );
      baseImpl.onAttach( activity );
      loaderImpl.onAttach( activity );
      editImpl.onAttach( activity.asActivity() );
   }
   
   
   
   @Override
   public void onDetach()
   {
      baseImpl.onDetach();
      loaderImpl.onDetach();
      editImpl.onDetach();
      super.onDetach();
   }
   
   
   
   @Override
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      super.onViewCreated( view, savedInstanceState );
      loaderImpl.onViewCreated( view, savedInstanceState );
      
      final ExpandableListView expandableListView = getExpandableListView();
      
      expandableListView.setOnGroupClickListener( this );
      expandableListView.setOnChildClickListener( this );
      expandableListView.setOnGroupCollapseListener( this );
      expandableListView.setOnGroupExpandListener( this );
   }
   
   
   
   public View getEmptyView()
   {
      return baseImpl.getEmptyView();
   }
   
   
   
   public void showEmptyView( boolean show )
   {
      baseImpl.showEmptyView( show );
   }
   
   
   
   public void showListView( boolean show )
   {
      baseImpl.showListView( show );
   }
   
   
   
   public FragmentActivity getFragmentActivity()
   {
      return (FragmentActivity) getSupportActivity();
   }
   
   
   
   @Override
   public void setArguments( Bundle args )
   {
      super.setArguments( args );
      baseImpl.setArguments( args );
   }
   
   
   
   @Override
   public void onSaveInstanceState( Bundle outState )
   {
      super.onSaveInstanceState( outState );
      baseImpl.onSaveInstanceState( outState );
   }
   
   
   
   @Override
   public final void registerAnnotatedConfiguredInstance( Object instance,
                                                          Bundle initialState )
   {
      baseImpl.registerAnnotatedConfiguredInstance( instance, initialState );
   }
   
   
   
   @Override
   public final Bundle getConfiguration()
   {
      return baseImpl.getConfiguration();
   }
   
   
   
   @Override
   public final void configure( Bundle config )
   {
      baseImpl.configure( config );
   }
   
   
   
   @Override
   public void clearConfiguration()
   {
      baseImpl.clearConfiguration();
   }
   
   
   
   public void onSettingsChanged( int which,
                                  HashMap< Integer, Object > oldValues )
   {
      loaderImpl.onSettingsChanged( which, oldValues );
   }
   
   
   
   protected int getSettingsMask()
   {
      return 0;
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
   
   
   
   @Override
   public ExpandableListAdapter getExpandableListAdapter()
   {
      return getExpandableListView().getExpandableListAdapter();
   }
   
   
   
   public boolean hasListAdapter()
   {
      return getExpandableListAdapter() != null;
   }
   
   
   
   @Override
   public Loader< D > onCreateLoader( int id, Bundle args )
   {
      return loaderImpl.onCreateLoader( id, args );
   }
   
   
   
   @Override
   public void onLoadFinished( Loader< D > loader, D data )
   {
      loaderImpl.onLoadFinished( loader, data );
   }
   
   
   
   @Override
   public void onLoaderReset( Loader< D > loader )
   {
      loaderImpl.onLoaderReset( loader );
   }
   
   
   
   protected void invalidateOptionsMenu()
   {
      if ( getFragmentActivity() != null )
         getFragmentActivity().invalidateOptionsMenu();
   }
   
   
   
   @Override
   public abstract ExpandableListAdapter createEmptyExpandableListAdapter();
   
   
   
   @Override
   public abstract ExpandableListAdapter createExpandableListAdapterForResult( D result );
}
