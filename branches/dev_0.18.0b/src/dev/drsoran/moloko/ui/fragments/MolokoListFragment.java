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
import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.text.Spanned;
import android.view.View;
import dev.drsoran.moloko.IConfigurable;
import dev.drsoran.moloko.ui.UiContext;
import dev.drsoran.moloko.ui.fragments.impl.ConfigurableFragmentImpl;
import dev.drsoran.moloko.ui.fragments.impl.LoaderListFragmentImpl;
import dev.drsoran.moloko.ui.fragments.impl.RtmAccessLevelFragmentImpl;
import dev.drsoran.rtm.ILog;


public abstract class MolokoListFragment< D > extends ListFragment implements
         IConfigurable, LoaderCallbacks< List< D > >,
         LoaderListFragmentImpl.Support< D >
{
   private final ConfigurableFragmentImpl baseImpl;
   
   private final LoaderListFragmentImpl< D > loaderImpl;
   
   private final RtmAccessLevelFragmentImpl accessImpl;
   
   
   
   protected MolokoListFragment()
   {
      baseImpl = new ConfigurableFragmentImpl( this );
      loaderImpl = new LoaderListFragmentImpl< D >( this );
      accessImpl = new RtmAccessLevelFragmentImpl();
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      baseImpl.onAttach( activity );
      loaderImpl.onAttach( activity );
      accessImpl.onAttach( activity );
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      baseImpl.onCreate( savedInstanceState );
   }
   
   
   
   @Override
   public void onDetach()
   {
      accessImpl.onDetach();
      baseImpl.onDetach();
      loaderImpl.onDetach();
      
      super.onDetach();
   }
   
   
   
   public UiContext getUiContext()
   {
      return baseImpl.getUiContext();
   }
   
   
   
   public ILog Log()
   {
      return baseImpl.getUiContext().Log();
   }
   
   
   
   public boolean isWritableAccess()
   {
      return accessImpl.hasWritableAccess();
   }
   
   
   
   @Override
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      super.onViewCreated( view, savedInstanceState );
      loaderImpl.onViewCreated( view, savedInstanceState );
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
   
   
   
   public boolean hasListAdapter()
   {
      return getListAdapter() != null;
   }
   
   
   
   protected void invalidateOptionsMenu()
   {
      if ( getActivity() != null )
         getActivity().invalidateOptionsMenu();
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
   
   
   
   public List< D > getLoaderData()
   {
      return loaderImpl.getLoaderData();
   }
   
   
   
   public List< D > getLoaderDataAssertNotNull()
   {
      return loaderImpl.getLoaderDataAssertNotNull();
   }
   
   
   
   public void preSetLoaderData( List< D > loaderData )
   {
      loaderImpl.preSetLoaderData( loaderData );
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
   
   
   
   public abstract int getChoiceMode();
}
