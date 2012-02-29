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
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.base.MolokoListFragment;


public class LoaderListFragmentImpl< D > extends LoaderFragmentImplBase< D >
{
   public static interface Support< D > extends
            LoaderFragmentImplBase.Support< D >
   {
      ListAdapter getListAdapter();
      
      
      
      ListAdapter createEmptyListAdapter();
      
      
      
      ListAdapter createListAdapterForResult( D result );
   }
   
   private final MolokoListFragment< D > fragment;
   
   private final Support< D > support;
   
   
   
   public LoaderListFragmentImpl( MolokoListFragment< D > fragment )
   {
      super( fragment, fragment, fragment );
      
      this.fragment = fragment;
      this.support = fragment;
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      if ( support.getListAdapter() == null && support.isReadyToStartLoader() )
      {
         startLoader();
      }
   }
   
   
   
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      if ( support.getListAdapter() == null )
         showLoadingSpinner( true );
   }
   
   
   
   public void onSettingsChanged( int which,
                                  HashMap< Integer, Object > oldValues )
   {
      notifyDataSetChanged();
   }
   
   
   
   @Override
   public Loader< D > onCreateLoader( int id, Bundle args )
   {
      showLoadingSpinner( true );
      return super.onCreateLoader( id, args );
   }
   
   
   
   @Override
   public void onLoadFinished( Loader< D > loader, D data )
   {
      if ( data != null )
         fragment.setListAdapter( support.createListAdapterForResult( data ) );
      else
         fragment.getLoaderManager().destroyLoader( support.getLoaderId() );
      
      super.onLoadFinished( loader, data );
   }
   
   
   
   public void showLoadingSpinner( boolean show )
   {
      fragment.showEmptyView( false );
      
      final View spinner = getLoadingSpinnerView();
      if ( spinner != null )
         spinner.setVisibility( show ? View.VISIBLE : View.GONE );
   }
   
   
   
   private void notifyDataSetChanged()
   {
      if ( fragment.getListAdapter() instanceof BaseAdapter )
         ( (BaseAdapter) fragment.getListAdapter() ).notifyDataSetChanged();
   }
   
   
   
   private View getLoadingSpinnerView()
   {
      View loadView = null;
      
      if ( fragment.getView() != null )
         loadView = fragment.getView().findViewById( R.id.loading_spinner );
      
      return loadView;
   }
}
