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

import java.util.HashMap;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dev.drsoran.moloko.IConfigurable;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.listeners.ILoaderFragmentListener;
import dev.drsoran.moloko.fragments.listeners.NullLoaderFragmentListener;
import dev.drsoran.moloko.loaders.AbstractLoader;
import dev.drsoran.moloko.util.UIUtils;


public abstract class MolokoLoaderEditDialogFragment< T extends Fragment, D >
         extends MolokoEditDialogFragment< T > implements IConfigurable,
         LoaderCallbacks< D >
{
   private final static class Config
   {
      public final static String LOADER_RESPECT_CONTENT_CHANGES = "loader_respect_content_changes";
   }
   
   private final Handler handler = new Handler();
   
   private ILoaderFragmentListener loaderListener;
   
   private D loaderData;
   
   private boolean loaderNotDataFound;
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      if ( getLoaderData() == null )
         startLoader();
   }
   
   
   
   @Override
   public void onAttach( FragmentActivity activity )
   {
      super.onAttach( activity );
      
      if ( activity instanceof ILoaderFragmentListener )
         loaderListener = (ILoaderFragmentListener) activity;
      else
         loaderListener = new NullLoaderFragmentListener();
   }
   
   
   
   @Override
   public void onDetach()
   {
      super.onDetach();
      loaderListener = null;
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
      
      if ( loaderNotDataFound )
         showElementNotFoundError();
      else if ( getLoaderData() != null )
         showContent();
      else
         showLoadingSpinner();
   }
   
   
   
   @Override
   protected void takeConfigurationFrom( Bundle config )
   {
      super.takeConfigurationFrom( config );
      
      if ( config.containsKey( Config.LOADER_RESPECT_CONTENT_CHANGES ) )
         configuration.putBoolean( Config.LOADER_RESPECT_CONTENT_CHANGES,
                                   config.getBoolean( Config.LOADER_RESPECT_CONTENT_CHANGES ) );
   }
   
   
   
   @Override
   protected void putDefaultConfigurationTo( Bundle bundle )
   {
      super.putDefaultConfigurationTo( bundle );
      
      bundle.putBoolean( Config.LOADER_RESPECT_CONTENT_CHANGES, true );
   }
   
   
   
   public D getLoaderData()
   {
      return loaderData;
   }
   
   
   
   public D getLoaderDataAssertNotNull()
   {
      if ( getLoaderData() == null )
         throw new IllegalStateException( "loader data must not be null" );
      
      return getLoaderData();
   }
   
   
   
   public boolean hasLoaderDataFound()
   {
      return !loaderNotDataFound;
   }
   
   
   
   protected final void showLoadingSpinner()
   {
      if ( getView() != null )
      {
         final View content = getContentView();
         if ( content != null )
            content.setVisibility( View.GONE );
         
         final View spinner = getView().findViewById( R.id.loading_spinner );
         if ( spinner != null )
            spinner.setVisibility( View.VISIBLE );
      }
   }
   
   
   
   protected final void showContent()
   {
      if ( getView() != null )
      {
         final View spinner = getView().findViewById( R.id.loading_spinner );
         if ( spinner != null )
            spinner.setVisibility( View.GONE );
         
         final ViewGroup content = getContentView();
         if ( content != null )
         {
            initContent( content );
            content.setVisibility( View.VISIBLE );
         }
      }
   }
   
   
   
   protected final void showContentAsync()
   {
      handler.post( new Runnable()
      {
         @Override
         public void run()
         {
            showContent();
         }
      } );
   }
   
   
   
   protected final void showElementNotFoundError()
   {
      if ( getView() != null )
      {
         final View spinner = getView().findViewById( R.id.loading_spinner );
         if ( spinner != null )
            spinner.setVisibility( View.GONE );
         
         final ViewGroup content = getContentView();
         if ( content != null )
         {
            content.removeAllViews();
            
            UIUtils.inflateErrorWithIcon( getFragmentActivity(),
                                          content,
                                          R.string.err_entity_not_found,
                                          getLoaderDataName() );
            content.setVisibility( View.VISIBLE );
         }
      }
   }
   
   
   
   @Override
   public void onSettingsChanged( int which,
                                  HashMap< Integer, Object > oldValues )
   {
      if ( !loaderNotDataFound && getContentView() != null )
         initContent( getContentView() );
   }
   
   
   
   public final void startLoader()
   {
      getLoaderManager().initLoader( getLoaderId(), getConfiguration(), this );
   }
   
   
   
   public final void setRespectContentChanges( boolean respect )
   {
      configuration.putBoolean( Config.LOADER_RESPECT_CONTENT_CHANGES, respect );
      
      if ( isAdded() )
      {
         final Loader< D > loader = getLoaderManager().getLoader( getLoaderId() );
         
         if ( loader instanceof AbstractLoader< ? > )
            ( (AbstractLoader< ? >) loader ).setRespectContentChanges( respect );
      }
   }
   
   
   
   public final boolean isRespectingContentChanges()
   {
      return configuration.getBoolean( Config.LOADER_RESPECT_CONTENT_CHANGES,
                                       false );
   }
   
   
   
   @Override
   public final Loader< D > onCreateLoader( int id, Bundle args )
   {
      showLoadingSpinner();
      
      final Loader< D > loader = newLoaderInstance( id, args );
      
      if ( loader instanceof AbstractLoader< ? > )
         ( (AbstractLoader< ? >) loader ).setRespectContentChanges( isRespectingContentChanges() );
      
      loaderListener.onFragmentLoadStarted( getId(), getTag() );
      
      return loader;
   }
   
   
   
   @Override
   public void onLoadFinished( Loader< D > loader, D data )
   {
      loaderData = data;
      loaderNotDataFound = loaderData == null;
      
      if ( loaderNotDataFound )
         showElementNotFoundError();
      else
         showContentAsync();
      
      loaderListener.onFragmentLoadFinished( getId(),
                                             getTag(),
                                             !loaderNotDataFound );
   }
   
   
   
   @Override
   public void onLoaderReset( Loader< D > loader )
   {
      loaderData = null;
      loaderNotDataFound = false;
   }
   
   
   
   @Override
   public int getSettingsMask()
   {
      return 0;
   }
   
   
   
   abstract protected View createFragmentView( LayoutInflater inflater,
                                               ViewGroup container,
                                               Bundle savedInstanceState );
   
   
   
   abstract protected void initContent( ViewGroup content );
   
   
   
   abstract protected Loader< D > newLoaderInstance( int id, Bundle args );
   
   
   
   abstract protected String getLoaderDataName();
   
   
   
   abstract protected int getLoaderId();
}
