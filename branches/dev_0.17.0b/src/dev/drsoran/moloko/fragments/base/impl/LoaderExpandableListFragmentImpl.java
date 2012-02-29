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
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.base.MolokoExpandableListFragment;


public class LoaderExpandableListFragmentImpl< D > extends
         LoaderFragmentImplBase< D >
{
   public static interface Support< D > extends
            LoaderFragmentImplBase.Support< D >
   {
      ExpandableListAdapter getExpandableListAdapter();
      
      
      
      ExpandableListAdapter createEmptyExpandableListAdapter();
      
      
      
      ExpandableListAdapter createExpandableListAdapterForResult( D result );
   }
   
   private final MolokoExpandableListFragment< D > fragment;
   
   private final Support< D > support;
   
   
   
   public LoaderExpandableListFragmentImpl(
      MolokoExpandableListFragment< D > fragment )
   {
      super( fragment, fragment, fragment );
      
      this.fragment = fragment;
      this.support = fragment;
   }
   
   
   
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      if ( support.getExpandableListAdapter() == null
         && support.isReadyToStartLoader() )
      {
         startLoader();
         showLoadingSpinner( true );
      }
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
         fragment.setExpandableListAdapter( support.createExpandableListAdapterForResult( data ) );
      else
         fragment.getLoaderManager().destroyLoader( support.getLoaderId() );
      
      super.onLoadFinished( loader, data );
   }
   
   
   
   private void notifyDataSetChanged()
   {
      if ( fragment.getExpandableListAdapter() instanceof BaseExpandableListAdapter )
         ( (BaseExpandableListAdapter) fragment.getExpandableListAdapter() ).notifyDataSetChanged();
   }
   
   
   
   private void showLoadingSpinner( boolean show )
   {
      fragment.showEmptyView( false );
      
      final View spinner = getLoadingSpinnerView();
      if ( spinner != null )
         spinner.setVisibility( show ? View.VISIBLE : View.GONE );
   }
   
   
   
   private View getLoadingSpinnerView()
   {
      View loadView = null;
      
      if ( fragment.getView() != null )
         loadView = fragment.getView().findViewById( R.id.loading_spinner );
      
      return loadView;
   }
}
