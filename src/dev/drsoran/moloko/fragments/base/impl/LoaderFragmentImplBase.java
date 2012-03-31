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

package dev.drsoran.moloko.fragments.base.impl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.app.SupportActivity;
import android.support.v4.content.Loader;
import dev.drsoran.moloko.IConfigurable;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.fragments.listeners.ILoaderFragmentListener;
import dev.drsoran.moloko.fragments.listeners.NullLoaderFragmentListener;
import dev.drsoran.moloko.loaders.AbstractLoader;


abstract class LoaderFragmentImplBase< D >
{
   protected static interface Support< D >
   {
      boolean isReadyToStartLoader();
      
      
      
      Bundle getLoaderConfig();
      
      
      
      int getLoaderId();
      
      
      
      String getLoaderDataName();
      
      
      
      Loader< D > newLoaderInstance( int id, Bundle config );
   }
   
   
   public static class Config
   {
      public final static String RESPECT_CONTENT_CHANGES = "loader_respect_content_changes";
   }
   
   private final Fragment fragment;
   
   private final LoaderCallbacks< D > loaderCallbacks;
   
   private final IConfigurable config;
   
   private final Support< D > support;
   
   private ILoaderFragmentListener loaderListener;
   
   @InstanceState( key = Config.RESPECT_CONTENT_CHANGES, defaultValue = "true" )
   private boolean respectContentChanges = true;
   
   
   
   @SuppressWarnings( "unchecked" )
   protected LoaderFragmentImplBase( Fragment fragment,
      LoaderCallbacks< D > loaderCallbacks, IConfigurable config )
   {
      this.fragment = fragment;
      this.loaderCallbacks = loaderCallbacks;
      this.config = config;
      this.support = (Support< D >) fragment;
   }
   
   
   
   public void onAttach( SupportActivity activity )
   {
      config.registerAnnotatedConfiguredInstance( this,
                                                  LoaderFragmentImplBase.class );
      
      if ( activity instanceof ILoaderFragmentListener )
         loaderListener = (ILoaderFragmentListener) activity;
      else
         loaderListener = new NullLoaderFragmentListener();
   }
   
   
   
   public void onCreate( Bundle savedInstanceState )
   {
      if ( savedInstanceState != null )
      {
         config.configure( savedInstanceState );
      }
      else
      {
         final Bundle bundle = new Bundle();
         bundle.putBoolean( Config.RESPECT_CONTENT_CHANGES,
                            isRespectingContentChanges() );
         
         config.configure( bundle );
      }
   }
   
   
   
   public void onDetach()
   {
      loaderListener = null;
   }
   
   
   
   public void setRespectContentChanges( boolean respect )
   {
      respectContentChanges = respect;
      
      if ( fragment.isAdded() )
      {
         final Loader< D > loader = fragment.getLoaderManager()
                                            .getLoader( support.getLoaderId() );
         
         if ( loader instanceof AbstractLoader< ? > )
            ( (AbstractLoader< ? >) loader ).setRespectContentChanges( respect );
      }
   }
   
   
   
   public boolean isRespectingContentChanges()
   {
      return respectContentChanges;
   }
   
   
   
   public Loader< D > onCreateLoader( int id, Bundle args )
   {
      final Loader< D > loader = support.newLoaderInstance( id, args );
      
      if ( loader instanceof AbstractLoader< ? > )
         ( (AbstractLoader< ? >) loader ).setRespectContentChanges( isRespectingContentChanges() );
      
      loaderListener.onFragmentLoadStarted( fragment.getId(), fragment.getTag() );
      
      return loader;
   }
   
   
   
   public void onLoadFinished( Loader< D > loader, D data )
   {
      loaderListener.onFragmentLoadFinished( fragment.getId(),
                                             fragment.getTag(),
                                             data != null );
   }
   
   
   
   public void onLoaderReset( Loader< D > loader )
   {
   }
   
   
   
   public void startLoader()
   {
      startLoaderWithConfiguration( support.getLoaderConfig() );
   }
   
   
   
   public void startLoaderWithConfiguration( Bundle config )
   {
      fragment.getLoaderManager().initLoader( support.getLoaderId(),
                                              config,
                                              loaderCallbacks );
   }
}
