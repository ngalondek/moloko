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

package dev.drsoran.moloko.ui.fragments;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.mdt.rtm.data.RtmAuth;

import dev.drsoran.moloko.app.settings.IOnSettingsChangedListener;
import dev.drsoran.moloko.ui.IConfigurable;
import dev.drsoran.moloko.ui.IRtmAccessLevelAware;
import dev.drsoran.moloko.ui.fragments.impl.ConfigurableFragmentImpl;
import dev.drsoran.moloko.ui.fragments.impl.EditFragmentImpl;
import dev.drsoran.moloko.ui.fragments.impl.LoaderExpandableListFragmentImpl;
import dev.drsoran.moloko.ui.fragments.impl.RtmAccessLevelAwareFragmentImpl;


public abstract class MolokoExpandableListFragment< D > extends
         SherlockListFragment implements IConfigurable,
         IOnSettingsChangedListener, LoaderCallbacks< List< D > >,
         LoaderExpandableListFragmentImpl.Support< D >, IRtmAccessLevelAware,
         ExpandableListView.OnGroupClickListener,
         ExpandableListView.OnChildClickListener,
         ExpandableListView.OnGroupCollapseListener,
         ExpandableListView.OnGroupExpandListener
{
   private final ConfigurableFragmentImpl baseImpl;
   
   private final LoaderExpandableListFragmentImpl< D > loaderImpl;
   
   private final EditFragmentImpl editImpl;
   
   private final RtmAccessLevelAwareFragmentImpl accessLevelAwareImpl;
   
   
   
   protected MolokoExpandableListFragment()
   {
      baseImpl = new ConfigurableFragmentImpl( this, getSettingsMask() );
      loaderImpl = new LoaderExpandableListFragmentImpl< D >( this );
      editImpl = new EditFragmentImpl( this );
      accessLevelAwareImpl = new RtmAccessLevelAwareFragmentImpl();
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      baseImpl.onCreate( savedInstanceState );
      loaderImpl.onCreate( savedInstanceState );
   }
   
   
   
   @Override
   public void onStart()
   {
      super.onStart();
      baseImpl.onStart();
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      baseImpl.onAttach( activity );
      loaderImpl.onAttach( activity );
      editImpl.onAttach( activity );
      accessLevelAwareImpl.onAttach( getSherlockActivity() );
   }
   
   
   
   @Override
   public void onDetach()
   {
      baseImpl.onDetach();
      loaderImpl.onDetach();
      editImpl.onDetach();
      accessLevelAwareImpl.onDetach();
      super.onDetach();
   }
   
   
   
   @Override
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      super.onViewCreated( view, savedInstanceState );
      loaderImpl.onViewCreated( view, savedInstanceState );
      editImpl.onViewCreated( view, savedInstanceState );
      
      final ExpandableListView expandableListView = getExpandableListView();
      
      expandableListView.setOnGroupClickListener( this );
      expandableListView.setOnChildClickListener( this );
      expandableListView.setOnGroupCollapseListener( this );
      expandableListView.setOnGroupExpandListener( this );
   }
   
   
   
   @Override
   public void onDestroyView()
   {
      editImpl.onDestroyView();
      super.onDestroyView();
   }
   
   
   
   @Override
   public void onDestroy()
   {
      editImpl.onDestroy();
      super.onDestroy();
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
   
   
   
   public int getNoElementsResourceId()
   {
      return loaderImpl.getNoElementsResourceId();
   }
   
   
   
   public void setNoElementsResourceId( int resId )
   {
      loaderImpl.setNoElementsResourceId( resId );
   }
   
   
   
   @Override
   public void reEvaluateRtmAccessLevel( RtmAuth.Perms currentAccessLevel )
   {
      accessLevelAwareImpl.reEvaluateRtmAccessLevel( currentAccessLevel );
   }
   
   
   
   public boolean isReadOnlyAccess()
   {
      return accessLevelAwareImpl.isReadOnlyAccess();
   }
   
   
   
   public boolean isWritableAccess()
   {
      return accessLevelAwareImpl.isWritableAccess();
   }
   
   
   
   @Override
   public final < T > void registerAnnotatedConfiguredInstance( T instance,
                                                                Class< T > clazz )
   {
      baseImpl.registerAnnotatedConfiguredInstance( instance, clazz );
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
      baseImpl.setDefaultConfiguration();
   }
   
   
   
   public Bundle getDefaultConfiguration()
   {
      return baseImpl.getDefaultConfiguration();
   }
   
   
   
   @Override
   public void onSettingsChanged( int which )
   {
      loaderImpl.onSettingsChanged( which );
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
   public Loader< List< D > > onCreateLoader( int id, Bundle args )
   {
      return loaderImpl.onCreateLoader( id, args );
   }
   
   
   
   @Override
   public void onLoadFinished( Loader< List< D > > loader, List< D > data )
   {
      loaderImpl.onLoadFinished( loader, data );
   }
   
   
   
   @Override
   public void onLoaderReset( Loader< List< D > > loader )
   {
      loaderImpl.onLoaderReset( loader );
   }
   
   
   
   @Override
   public boolean isReadyToStartLoader()
   {
      return true;
   }
   
   
   
   @Override
   public Bundle getLoaderConfig()
   {
      return getConfiguration();
   }
   
   
   
   public List< D > getLoaderData()
   {
      return loaderImpl.getLoaderData();
   }
   
   
   
   public List< D > getLoaderDataAssertNotNull()
   {
      return loaderImpl.getLoaderDataAssertNotNull();
   }
   
   
   
   public boolean isLoaderDataFound()
   {
      return loaderImpl.isLoaderDataFound();
   }
   
   
   
   public final void setRespectContentChanges( boolean respect )
   {
      loaderImpl.setRespectContentChanges( respect );
   }
   
   
   
   public final boolean isRespectingContentChanges()
   {
      return loaderImpl.isRespectingContentChanges();
   }
   
   
   
   protected void invalidateOptionsMenu()
   {
      if ( getSherlockActivity() != null )
      {
         getSherlockActivity().supportInvalidateOptionsMenu();
      }
   }
   
   
   
   public void showError( int messageResId )
   {
      loaderImpl.showError( messageResId );
   }
   
   
   
   public void showError( CharSequence message )
   {
      loaderImpl.showError( message );
   }
   
   
   
   public void showError( Spanned message )
   {
      loaderImpl.showError( message );
   }
   
   
   
   public void onExpandableListAdapterCreated( ExpandableListAdapter listAdapter,
                                               List< D > result )
   {
   }
   
   
   
   public void onListAdapterDestroyed()
   {
   }
   
   
   
   @Override
   public abstract View onCreateView( LayoutInflater inflater,
                                      ViewGroup container,
                                      Bundle savedInstanceState );
   
   
   
   @Override
   public abstract ExpandableListAdapter createExpandableListAdapterForResult( List< D > result );
}
