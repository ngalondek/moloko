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

import java.util.HashMap;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.ViewGroup;
import dev.drsoran.moloko.IConfigurable;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.UIUtils;


public class LoaderFragmentImpl< D > extends LoaderFragmentImplBase< D >
{
   public static interface Support< D > extends
            LoaderFragmentImplBase.Support< D >
   {
      View getRootView();
      
      
      
      void initContent( ViewGroup content );
      
      
      
      ViewGroup getContentView();
      
      
      
      FragmentActivity getSherlockActivity();
   }
   
   private final Fragment fragment;
   
   private final Support< D > support;
   
   private final Handler handler = new Handler();
   
   
   
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
   
   
   
   public void onSettingsChanged( int which,
                                  HashMap< Integer, Object > oldValues )
   {
      if ( isLoaderDataFound() && support.getContentView() != null )
         support.initContent( support.getContentView() );
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
            support.initContent( content );
            content.setVisibility( View.VISIBLE );
         }
      }
   }
   
   
   
   private void showContentAsync()
   {
      handler.post( new Runnable()
      {
         @Override
         public void run()
         {
            if ( fragment.isAdded() )
               showContent();
         }
      } );
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
            
            UIUtils.inflateErrorWithIcon( support.getSherlockActivity(),
                                          content,
                                          R.string.err_entity_not_found,
                                          support.getLoaderDataName() );
            content.setVisibility( View.VISIBLE );
         }
      }
   }
}
