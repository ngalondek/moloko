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
import java.util.List;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.text.Spanned;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import dev.drsoran.moloko.fragments.base.MolokoListFragment;


public class LoaderListFragmentImpl< D > extends
         LoaderFragmentImplBase< List< D > >
{
   public static interface Support< D > extends
            LoaderFragmentImplBase.Support< List< D > >
   {
      ListAdapter getListAdapter();
      
      
      
      ListAdapter createListAdapterForResult( List< D > result );
   }
   
   private final MolokoListFragment< D > fragment;
   
   private final LoaderListFragmentViewManager viewManager;
   
   private final Support< D > support;
   
   
   
   public LoaderListFragmentImpl( MolokoListFragment< D > fragment )
   {
      super( fragment, fragment, fragment );
      
      this.fragment = fragment;
      this.viewManager = new LoaderListFragmentViewManager( fragment );
      this.support = fragment;
   }
   
   
   
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      if ( support.getListAdapter() == null && support.isReadyToStartLoader() )
      {
         startLoader();
      }
   }
   
   
   
   public void onSettingsChanged( int which,
                                  HashMap< Integer, Object > oldValues )
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
         final ListAdapter adapter = support.createListAdapterForResult( data );
         
         fragment.setListAdapter( adapter );
         fragment.onListAdapterCreated( adapter, data );
      }
      else
      {
         fragment.setListAdapter( null );
         fragment.onListAdapterDestroyed();
         fragment.getLoaderManager().destroyLoader( support.getLoaderId() );
      }
      
      super.onLoadFinished( loader, data );
      
      viewManager.onLoadFinished( isLoaderDataFound() );
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
      if ( fragment.getListAdapter() instanceof BaseAdapter )
         ( (BaseAdapter) fragment.getListAdapter() ).notifyDataSetChanged();
   }
}
