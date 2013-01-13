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

import java.util.List;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.text.Spanned;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import dev.drsoran.moloko.fragments.base.MolokoExpandableListFragment;


public class LoaderExpandableListFragmentImpl< D > extends
         LoaderFragmentImplBase< List< D > >
{
   public static interface Support< D > extends
            LoaderFragmentImplBase.Support< List< D > >
   {
      ExpandableListAdapter getExpandableListAdapter();
      
      
      
      ExpandableListAdapter createExpandableListAdapterForResult( List< D > result );
   }
   
   private final MolokoExpandableListFragment< D > fragment;
   
   private final LoaderListFragmentViewManager viewManager;
   
   private final Support< D > support;
   
   
   
   public LoaderExpandableListFragmentImpl(
      MolokoExpandableListFragment< D > fragment )
   {
      super( fragment, fragment, fragment );
      
      this.fragment = fragment;
      this.viewManager = new LoaderListFragmentViewManager( fragment );
      this.support = fragment;
   }
   
   
   
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      viewManager.onViewCreated( view, savedInstanceState );
      
      if ( support.getExpandableListAdapter() == null
         && support.isReadyToStartLoader() )
      {
         startLoader();
      }
   }
   
   
   
   public void onSettingsChanged( int which )
   {
      notifyDataSetChanged();
   }
   
   
   
   @Override
   public Loader< List< D > > onCreateLoader( int id, Bundle args )
   {
      viewManager.onCreateLoader();
      return super.onCreateLoader( id, args );
   }
   
   
   
   @Override
   public void onLoadFinished( Loader< List< D > > loader, List< D > data )
   {
      if ( data != null )
      {
         final ExpandableListAdapter adapter = support.createExpandableListAdapterForResult( data );
         
         fragment.setExpandableListAdapter( adapter );
         fragment.onExpandableListAdapterCreated( adapter, data );
      }
      else
      {
         fragment.setExpandableListAdapter( null );
         fragment.onListAdapterDestroyed();
         fragment.getLoaderManager().destroyLoader( support.getLoaderId() );
      }
      
      super.onLoadFinished( loader, data );
      
      viewManager.onLoadFinished( isLoaderDataFound() );
   }
   
   
   
   public int getNoElementsResourceId()
   {
      return viewManager.getNoElementsResourceId();
   }
   
   
   
   public void setNoElementsResourceId( int resId )
   {
      viewManager.setNoElementsResourceId( resId );
   }
   
   
   
   public void showError( int messageResId )
   {
      viewManager.showError( messageResId );
   }
   
   
   
   public void showError( CharSequence message )
   {
      viewManager.showError( message );
   }
   
   
   
   public void showError( Spanned message )
   {
      viewManager.showError( message );
   }
   
   
   
   private void notifyDataSetChanged()
   {
      if ( fragment.getExpandableListAdapter() instanceof BaseExpandableListAdapter )
         ( (BaseExpandableListAdapter) fragment.getExpandableListAdapter() ).notifyDataSetChanged();
   }
}
