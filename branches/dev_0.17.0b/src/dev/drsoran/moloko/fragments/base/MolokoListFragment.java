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
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.actionbarsherlock.app.SherlockListFragment;
import com.mdt.rtm.data.RtmAuth;

import dev.drsoran.moloko.IConfigurable;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.IRtmAccessLevelAware;
import dev.drsoran.moloko.fragments.base.impl.ConfigurableFragmentImpl;
import dev.drsoran.moloko.fragments.base.impl.EditFragmentImpl;
import dev.drsoran.moloko.fragments.base.impl.LoaderListFragmentImpl;
import dev.drsoran.moloko.fragments.base.impl.RtmAccessLevelAwareFragmentImpl;


public abstract class MolokoListFragment< D > extends SherlockListFragment
         implements IConfigurable, IOnSettingsChangedListener,
         LoaderCallbacks< List< D > >, LoaderListFragmentImpl.Support< D >,
         IRtmAccessLevelAware
{
   private final ConfigurableFragmentImpl baseImpl;
   
   private final LoaderListFragmentImpl< D > loaderImpl;
   
   private final EditFragmentImpl editImpl;
   
   private final RtmAccessLevelAwareFragmentImpl accessLevelAwareImpl;
   
   
   
   protected MolokoListFragment()
   {
      baseImpl = new ConfigurableFragmentImpl( this, getSettingsMask() );
      loaderImpl = new LoaderListFragmentImpl< D >( this );
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
   }
   
   
   
   @Override
   public void onDestroyView()
   {
      super.onDestroyView();
      editImpl.onDestroyView();
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
   public void onSettingsChanged( int which,
                                  HashMap< Integer, Object > oldValues )
   {
      loaderImpl.onSettingsChanged( which, oldValues );
   }
   
   
   
   protected int getSettingsMask()
   {
      return 0;
   }
   
   
   
   public boolean hasListAdapter()
   {
      return getListAdapter() != null;
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
         getSherlockActivity().invalidateOptionsMenu();
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
   
   
   
   public void onListAdapterCreated( ListAdapter listAdapter, List< D > result )
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
   public abstract Loader< List< D > > newLoaderInstance( int id, Bundle config );
   
   
   
   @Override
   public abstract String getLoaderDataName();
   
   
   
   @Override
   public abstract int getLoaderId();
   
   
   
   @Override
   public abstract ListAdapter createListAdapterForResult( List< D > result );
}
