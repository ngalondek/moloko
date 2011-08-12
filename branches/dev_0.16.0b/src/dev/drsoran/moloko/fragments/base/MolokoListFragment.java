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
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import dev.drsoran.moloko.IConfigurable;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.listeners.ILoaderFragmentListener;
import dev.drsoran.moloko.fragments.listeners.NullLoaderFragmentListener;
import dev.drsoran.moloko.util.AccountUtils;


public abstract class MolokoListFragment< D > extends ListFragment implements
         IConfigurable, LoaderCallbacks< D >
{
   private IOnSettingsChangedListener onSettingsChangedListener;
   
   private ILoaderFragmentListener loaderListener;
   
   protected Bundle configuration;
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      if ( savedInstanceState == null )
         configure( getArguments() );
      else
         configure( savedInstanceState );
      
      startLoader();
   }
   


   @Override
   public void onAttach( FragmentActivity activity )
   {
      super.onAttach( activity );
      
      final int settingsMask = getSettingsMask();
      
      if ( settingsMask != 0 )
      {
         onSettingsChangedListener = new IOnSettingsChangedListener()
         {
            @Override
            public void onSettingsChanged( int which,
                                           HashMap< Integer, Object > oldValues )
            {
               if ( isAdded() && !isDetached() )
                  MolokoListFragment.this.onSettingsChanged( which, oldValues );
            }
         };
         
         MolokoApp.get( activity )
                  .registerOnSettingsChangedListener( settingsMask,
                                                      onSettingsChangedListener );
      }
      
      if ( activity instanceof ILoaderFragmentListener )
         loaderListener = (ILoaderFragmentListener) activity;
      else
         loaderListener = new NullLoaderFragmentListener();
   }
   


   @Override
   public void onDetach()
   {
      super.onDetach();
      
      if ( onSettingsChangedListener != null )
      {
         MolokoApp.get( getActivity() )
                  .unregisterOnSettingsChangedListener( onSettingsChangedListener );
         
         onSettingsChangedListener = null;
      }
      
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
      
      if ( getListAdapter() == null )
         showLoadingSpinner();
   }
   


   @Override
   public void setArguments( Bundle args )
   {
      super.setArguments( args );
      
      configure( args );
   }
   


   @Override
   public void onSaveInstanceState( Bundle outState )
   {
      super.onSaveInstanceState( outState );
      
      outState.putAll( getConfiguration() );
   }
   


   @Override
   public final Bundle getConfiguration()
   {
      return new Bundle( configuration );
   }
   


   @Override
   public final void configure( Bundle config )
   {
      if ( configuration == null )
         configuration = createDefaultConfiguration();
      
      if ( config != null )
         takeConfigurationFrom( config );
   }
   


   @Override
   public void clearConfiguration()
   {
      if ( configuration != null )
         configuration.clear();
   }
   


   @Override
   public final Bundle createDefaultConfiguration()
   {
      final Bundle bundle = new Bundle();
      
      putDefaultConfigurationTo( bundle );
      
      return bundle;
   }
   


   protected void takeConfigurationFrom( Bundle config )
   {
   }
   


   protected void putDefaultConfigurationTo( Bundle bundle )
   {
   }
   


   protected final void showLoadingSpinner()
   {
      if ( getView() != null )
      {
         final View emptyView = getListView().getEmptyView();
         if ( emptyView != null )
            emptyView.setVisibility( View.GONE );
         
         final View spinner = getView().findViewById( R.id.loading_spinner );
         if ( spinner != null )
            spinner.setVisibility( View.VISIBLE );
      }
   }
   


   public void onSettingsChanged( int which,
                                  HashMap< Integer, Object > oldValues )
   {
      notifyDataSetChanged();
   }
   


   public int getSettingsMask()
   {
      return 0;
   }
   


   public final void startLoader()
   {
      getLoaderManager().initLoader( getLoaderId(), getConfiguration(), this );
   }
   


   @Override
   public Loader< D > onCreateLoader( int id, Bundle args )
   {
      showLoadingSpinner();
      
      loaderListener.onFragmentLoadStarted( getId(), getTag() );
      
      return null;
   }
   


   @Override
   public void onLoadFinished( Loader< D > loader, D data )
   {
      if ( data != null )
         setListAdapter( createListAdapterForResult( data ) );
      else
         getLoaderManager().destroyLoader( getLoaderId() );
   }
   


   @Override
   public void onLoaderReset( Loader< D > loader )
   {
      setListAdapter( createEmptyListAdapter() );
   }
   


   protected void invalidateOptionsMenu()
   {
      if ( getActivity() != null )
         getActivity().invalidateOptionsMenu();
   }
   


   protected void notifyDataSetChanged()
   {
      if ( getListAdapter() instanceof BaseAdapter )
         ( (BaseAdapter) getListAdapter() ).notifyDataSetChanged();
   }
   


   public boolean hasRtmWriteAccess()
   {
      return AccountUtils.isWriteableAccess( getActivity() );
   }
   


   abstract protected View createFragmentView( LayoutInflater inflater,
                                               ViewGroup container,
                                               Bundle savedInstanceState );
   


   abstract protected String getLoaderDataName();
   


   abstract protected int getLoaderId();
   


   protected abstract ListAdapter createEmptyListAdapter();
   


   protected abstract ListAdapter createListAdapterForResult( D result );
}
