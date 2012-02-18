/* 
 *	Copyright (c) 2012 Ronny R�hricht
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
 * Ronny R�hricht - implementation
 */

package dev.drsoran.moloko.fragments.base.impl;

import java.util.HashMap;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.ViewGroup;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.base.MolokoFragment;
import dev.drsoran.moloko.util.UIUtils;


public class LoaderFragmentImpl< D > extends LoaderFragmentImplBase< D >
{
   public static interface Support< D > extends
            LoaderFragmentImplBase.Support< D >
   {
      void initContent( ViewGroup content );
   }
   
   private final MolokoFragment fragment;
   
   private final Support< D > support;
   
   private final Handler handler = new Handler();
   
   private D loaderData;
   
   private boolean loaderNotDataFound;
   
   
   
   @SuppressWarnings( "unchecked" )
   public LoaderFragmentImpl( MolokoFragment fragment )
   {
      super( fragment, fragment );
      
      this.fragment = fragment;
      this.support = (Support< D >) fragment;
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      if ( getLoaderData() == null )
         startLoader();
   }
   
   
   
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      if ( loaderNotDataFound )
         showElementNotFoundError();
      else if ( getLoaderData() != null )
         showContent();
      else
         showLoadingSpinner();
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
   
   
   
   public boolean isLoaderDataFound()
   {
      return !loaderNotDataFound;
   }
   
   
   
   public void onSettingsChanged( int which,
                                  HashMap< Integer, Object > oldValues )
   {
      if ( !loaderNotDataFound && fragment.getContentView() != null )
         support.initContent( fragment.getContentView() );
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
      loaderData = data;
      loaderNotDataFound = loaderData == null;
      
      if ( loaderNotDataFound )
         showElementNotFoundError();
      else
         showContentAsync();
      
      super.onLoadFinished( loader, data );
   }
   
   
   
   @Override
   public void onLoaderReset( Loader< D > loader )
   {
      loaderData = null;
      loaderNotDataFound = false;
      
      super.onLoaderReset( loader );
   }
   
   
   
   private void showLoadingSpinner()
   {
      if ( fragment.getView() != null )
      {
         final View spinner = getLoadingSpinnerView();
         if ( spinner != null )
            spinner.setVisibility( View.VISIBLE );
         
         final View contentView = fragment.getContentView();
         if ( contentView != null )
            contentView.setVisibility( View.GONE );
      }
   }
   
   
   
   public View getLoadingSpinnerView()
   {
      View loadView = null;
      
      if ( fragment.getView() != null )
         loadView = fragment.getView().findViewById( R.id.loading_spinner );
      
      return loadView;
   }
   
   
   
   private void showContent()
   {
      if ( fragment.getView() != null )
      {
         final View spinner = getLoadingSpinnerView();
         if ( spinner != null )
            spinner.setVisibility( View.GONE );
         
         final ViewGroup content = fragment.getContentView();
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
      if ( fragment.getView() != null )
      {
         final View spinner = fragment.getView()
                                      .findViewById( R.id.loading_spinner );
         if ( spinner != null )
            spinner.setVisibility( View.GONE );
         
         final ViewGroup content = fragment.getContentView();
         if ( content != null )
         {
            content.removeAllViews();
            
            UIUtils.inflateErrorWithIcon( fragment.getFragmentActivity(),
                                          content,
                                          R.string.err_entity_not_found,
                                          support.getLoaderDataName() );
            content.setVisibility( View.VISIBLE );
         }
      }
   }
}