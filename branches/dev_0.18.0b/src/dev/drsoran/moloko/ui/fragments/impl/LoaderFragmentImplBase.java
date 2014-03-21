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

package dev.drsoran.moloko.ui.fragments.impl;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.os.Bundle;
import dev.drsoran.moloko.IConfigurable;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.ui.fragments.listeners.ILoaderFragmentListener;
import dev.drsoran.moloko.ui.fragments.listeners.NullLoaderFragmentListener;


abstract class LoaderFragmentImplBase< D >
{
   protected static interface Support< D >
   {
      boolean isReadyToStartLoader();
      
      
      
      int getLoaderId();
      
      
      
      String getLoaderDataName();
      
      
      
      Loader< D > newLoaderInstance( int id, Bundle config );
   }
   
   private final Fragment fragment;
   
   private final LoaderCallbacks< D > loaderCallbacks;
   
   private final IConfigurable configurable;
   
   private final Support< D > support;
   
   private ILoaderFragmentListener loaderListener;
   
   private D loaderData;
   
   private boolean loaderNotDataFound;
   
   
   
   @SuppressWarnings( "unchecked" )
   protected LoaderFragmentImplBase( Fragment fragment,
      LoaderCallbacks< D > loaderCallbacks, IConfigurable configurable )
   {
      this.fragment = fragment;
      this.loaderCallbacks = loaderCallbacks;
      this.configurable = configurable;
      this.support = (Support< D >) fragment;
   }
   
   
   
   public void onAttach( Activity activity )
   {
      configurable.registerAnnotatedConfiguredInstance( this,
                                                        LoaderFragmentImplBase.class );
      
      if ( activity instanceof ILoaderFragmentListener )
         loaderListener = (ILoaderFragmentListener) activity;
      else
         loaderListener = new NullLoaderFragmentListener();
   }
   
   
   
   public void onDetach()
   {
      loaderListener = null;
   }
   
   
   
   public D getLoaderData()
   {
      return loaderData;
   }
   
   
   
   public void preSetLoaderData( D loaderData )
   {
      this.loaderData = loaderData;
   }
   
   
   
   public D getLoaderDataAssertNotNull()
   {
      if ( getLoaderData() == null )
      {
         throw new IllegalStateException( "loader data must not be null" );
      }
      
      return getLoaderData();
   }
   
   
   
   public boolean isLoaderDataFound()
   {
      return !loaderNotDataFound;
   }
   
   
   
   public Loader< D > onCreateLoader( int id, Bundle args )
   {
      final Bundle loaderArgs = new Bundle( configurable.getConfiguration() );
      
      if ( args != null )
      {
         loaderArgs.putAll( args );
      }
      
      final Loader< D > loader = support.newLoaderInstance( id, loaderArgs );
      
      if ( loader instanceof AsyncTaskLoader< ? > )
      {
         final int loaderUpdateThrottleMs = fragment.getResources()
                                                    .getInteger( R.integer.env_loader_update_throttle_ms );
         
         ( (AsyncTaskLoader< ? >) loader ).setUpdateThrottle( loaderUpdateThrottleMs );
      }
      
      loaderListener.onFragmentLoadStarted( fragment.getId(), fragment.getTag() );
      
      return loader;
   }
   
   
   
   public void onLoadFinished( Loader< D > loader, D data )
   {
      loaderData = data;
      loaderNotDataFound = loaderData == null;
      
      loaderListener.onFragmentLoadFinished( fragment.getId(),
                                             fragment.getTag(),
                                             isLoaderDataFound() );
   }
   
   
   
   public void onLoaderReset( Loader< D > loader )
   {
      loaderData = null;
      loaderNotDataFound = false;
   }
   
   
   
   public void startLoader()
   {
      startLoaderWithConfiguration( Bundle.EMPTY );
   }
   
   
   
   public void startLoaderWithConfiguration( Bundle config )
   {
      fragment.getLoaderManager().initLoader( support.getLoaderId(),
                                              config,
                                              loaderCallbacks );
   }
}
