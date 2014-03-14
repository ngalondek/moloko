/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.ViewGroup;
import dev.drsoran.moloko.ui.fragments.impl.LoaderFragmentImpl;


public abstract class MolokoLoaderDialogFragment< D > extends
         MolokoContentDialogFragment implements LoaderCallbacks< D >,
         LoaderFragmentImpl.Support< D >
{
   private final LoaderFragmentImpl< D > loaderImpl;
   
   
   
   public MolokoLoaderDialogFragment()
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
      loaderImpl.onAttach( activity );
   }
   
   
   
   @Override
   public void onDestroy()
   {
      loaderImpl.onDestroy();
      super.onDestroy();
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
   
   
   
   public D getLoaderData()
   {
      return loaderImpl.getLoaderData();
   }
   
   
   
   public D getLoaderDataAssertNotNull()
   {
      return loaderImpl.getLoaderDataAssertNotNull();
   }
   
   
   
   public void preSetLoaderData( D loaderData )
   {
      loaderImpl.preSetLoaderData( loaderData );
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
   public abstract void initContentAfterDataLoaded( ViewGroup content );
   
   
   
   @Override
   public abstract Loader< D > newLoaderInstance( int id, Bundle args );
   
   
   
   @Override
   public abstract String getLoaderDataName();
   
   
   
   @Override
   public abstract int getLoaderId();
}
