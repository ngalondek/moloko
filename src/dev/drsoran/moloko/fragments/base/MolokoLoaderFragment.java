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

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dev.drsoran.moloko.fragments.base.impl.LoaderFragmentImpl;


public abstract class MolokoLoaderFragment< D > extends MolokoFragment
         implements LoaderCallbacks< D >, LoaderFragmentImpl.Support< D >

{
   private final LoaderFragmentImpl< D > loaderImpl;
   
   
   
   protected MolokoLoaderFragment()
   {
      loaderImpl = new LoaderFragmentImpl< D >( this, this, this );
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      loaderImpl.onCreate( savedInstanceState );
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      loaderImpl.onAttach( getSherlockActivity() );
   }
   
   
   
   @Override
   public void onDetach()
   {
      loaderImpl.onDetach();
      super.onDetach();
   }
   
   
   
   @Override
   public final View onCreateView( LayoutInflater inflater,
                                   ViewGroup container,
                                   Bundle savedInstanceState )
   {
      final View fragmentView = createFragmentView( inflater,
                                                    container,
                                                    savedInstanceState );
      return fragmentView;
   }
   
   
   
   @Override
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      super.onViewCreated( view, savedInstanceState );
      loaderImpl.onViewCreated( view, savedInstanceState );
   }
   
   
   
   @Override
   public View getRootView()
   {
      return getView();
   }
   
   
   
   public D getLoaderData()
   {
      return loaderImpl.getLoaderData();
   }
   
   
   
   public D getLoaderDataAssertNotNull()
   {
      return loaderImpl.getLoaderDataAssertNotNull();
   }
   
   
   
   public boolean isLoaderDataFound()
   {
      return loaderImpl.isLoaderDataFound();
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
   
   
   
   @Override
   public void onSettingsChanged( int which,
                                  HashMap< Integer, Object > oldValues )
   {
      loaderImpl.onSettingsChanged( which, oldValues );
   }
   
   
   
   public final void setRespectContentChanges( boolean respect )
   {
      loaderImpl.setRespectContentChanges( respect );
   }
   
   
   
   public final boolean isRespectingContentChanges()
   {
      return loaderImpl.isRespectingContentChanges();
   }
   
   
   
   abstract protected View createFragmentView( LayoutInflater inflater,
                                               ViewGroup container,
                                               Bundle savedInstanceState );
   
   
   
   @Override
   abstract public void initContent( ViewGroup content );
   
   
   
   @Override
   abstract public Loader< D > newLoaderInstance( int id, Bundle config );
   
   
   
   @Override
   abstract public String getLoaderDataName();
   
   
   
   @Override
   abstract public int getLoaderId();
}
