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

package dev.drsoran.moloko.fragments.base.impl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import dev.drsoran.moloko.IConfigurable;
import dev.drsoran.moloko.IHandlerToken;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;


public class LoaderFragmentImpl< D > extends LoaderFragmentImplBase< D >
{
   public static interface Support< D > extends
            LoaderFragmentImplBase.Support< D >
   {
      View getRootView();
      
      
      
      void initContentAfterDataLoaded( ViewGroup content );
      
      
      
      ViewGroup getContentView();
      
      
      
      FragmentActivity getSherlockActivity();
   }
   
   private final Fragment fragment;
   
   private final Support< D > support;
   
   private final IHandlerToken handler = MolokoApp.acquireHandlerToken();
   
   private final Runnable showContentAsyncRunnable = new Runnable()
   {
      @Override
      public void run()
      {
         if ( fragment.isAdded() )
         {
            showContent();
         }
      }
   };
   
   
   
   @SuppressWarnings( "unchecked" )
   public LoaderFragmentImpl( Fragment fragment,
      LoaderCallbacks< D > loaderCallbacks, IConfigurable configurable )
   {
      super( fragment, loaderCallbacks, configurable );
      
      this.fragment = fragment;
      this.support = (Support< D >) fragment;
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      if ( getLoaderData() == null && support.isReadyToStartLoader() )
      {
         startLoader();
      }
   }
   
   
   
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      if ( !isLoaderDataFound() )
         showElementNotFoundError();
      else if ( getLoaderData() != null )
         showContent();
      else
         showLoadingSpinner();
   }
   
   
   
   public void onDestroy()
   {
      handler.release();
   }
   
   
   
   @Override
   public Loader< D > onCreateLoader( int id, Bundle args )
   {
      showLoadingSpinner();
      return super.onCreateLoader( id, args );
   }
   
   
   
   @Override
   public void onLoadFinished( Loader< D > loader, D data )
   {
      super.onLoadFinished( loader, data );
      
      if ( !isLoaderDataFound() )
         showElementNotFoundError();
      else
         showContentAsync();
   }
   
   
   
   private void showLoadingSpinner()
   {
      if ( support.getRootView() != null )
      {
         final View spinner = getLoadingSpinnerView();
         if ( spinner != null )
            spinner.setVisibility( View.VISIBLE );
         
         final View contentView = support.getContentView();
         if ( contentView != null )
            contentView.setVisibility( View.GONE );
      }
   }
   
   
   
   public View getLoadingSpinnerView()
   {
      View loadView = null;
      
      if ( support.getRootView() != null )
         loadView = support.getRootView().findViewById( R.id.loading_spinner );
      
      return loadView;
   }
   
   
   
   private void showContent()
   {
      if ( support.getRootView() != null )
      {
         final View spinner = getLoadingSpinnerView();
         if ( spinner != null )
            spinner.setVisibility( View.GONE );
         
         final ViewGroup content = support.getContentView();
         if ( content != null )
         {
            support.initContentAfterDataLoaded( content );
            content.setVisibility( View.VISIBLE );
         }
      }
   }
   
   
   
   private void showContentAsync()
   {
      handler.removeRunnablesAndMessages();
      handler.post( showContentAsyncRunnable );
   }
   
   
   
   private void showElementNotFoundError()
   {
      if ( support.getRootView() != null )
      {
         final View spinner = getLoadingSpinnerView();
         if ( spinner != null )
            spinner.setVisibility( View.GONE );
         
         final ViewGroup content = support.getContentView();
         if ( content != null )
         {
            content.removeAllViews();
            
            inflateErrorWithIcon( content,
                                  R.string.err_entity_not_found,
                                  support.getLoaderDataName() );
            content.setVisibility( View.VISIBLE );
         }
      }
   }
   
   
   
   private void inflateErrorWithIcon( ViewGroup container,
                                      int errorMsgResId,
                                      Object... params )
   {
      final View view = LayoutInflater.from( support.getSherlockActivity() )
                                      .inflate( R.layout.error_with_icon,
                                                container,
                                                true );
      final TextView text = (TextView) view.findViewById( R.id.title_with_text_text );
      final String msg = support.getSherlockActivity()
                                .getResources()
                                .getString( errorMsgResId, params );
      text.setText( msg );
      
      MolokoApp.Log.e( MolokoApp.class, msg );
   }
   
}
