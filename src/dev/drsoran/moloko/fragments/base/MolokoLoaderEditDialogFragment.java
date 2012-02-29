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
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.app.SupportActivity;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.ViewGroup;
import dev.drsoran.moloko.fragments.base.impl.LoaderFragmentImpl;


public abstract class MolokoLoaderEditDialogFragment< T extends Fragment, D >
         extends MolokoEditDialogFragment< T > implements LoaderCallbacks< D >,
         LoaderFragmentImpl.Support< D >
{
   private final LoaderFragmentImpl< D > loaderImpl;
   
   
   
   public MolokoLoaderEditDialogFragment()
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
   public void onAttach( SupportActivity activity )
   {
      super.onAttach( activity );
      loaderImpl.onAttach( activity );
   }
   
   
   
   @Override
   public void onDetach()
   {
      loaderImpl.onDetach();
      super.onDetach();
   }
   
   
   
   @Override
   public void onDialogViewCreated( ViewGroup dialogView )
   {
      super.onDialogViewCreated( dialogView );
      loaderImpl.onViewCreated( dialogView, null );
   }
   
   
   
   @Override
   public View getRootView()
   {
      return getDialogView();
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
   public abstract void initContent( ViewGroup content );
   
   
   
   @Override
   public abstract Loader< D > newLoaderInstance( int id, Bundle args );
   
   
   
   @Override
   public abstract String getLoaderDataName();
   
   
   
   @Override
   public abstract int getLoaderId();
}
