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

package dev.drsoran.moloko.fragments.base;

import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.SupportActivity;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class MolokoLoaderFragment< D > extends MolokoFragment
         implements LoaderFragmentImpl.Support< D >

{
   private final LoaderFragmentImpl< D > impl;
   
   
   
   protected MolokoLoaderFragment()
   {
      impl = new LoaderFragmentImpl< D >( this );
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      impl.onCreate( savedInstanceState );
   }
   
   
   
   @Override
   public void onAttach( SupportActivity activity )
   {
      super.onAttach( activity );
      impl.onAttach( getFragmentActivity() );
   }
   
   
   
   @Override
   public void onDetach()
   {
      impl.onDetach();
      super.onDetach();
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
      impl.onViewCreated( view, savedInstanceState );
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
      return impl.getLoaderData();
   }
   
   
   
   public D getLoaderDataAssertNotNull()
   {
      return impl.getLoaderDataAssertNotNull();
   }
   
   
   
   public boolean isLoaderDataFound()
   {
      return impl.isLoaderDataFound();
   }
   
   
   
   @Override
   public void onSettingsChanged( int which,
                                  HashMap< Integer, Object > oldValues )
   {
      impl.onSettingsChanged( which, oldValues );
   }
   
   
   
   public final void setRespectContentChanges( boolean respect )
   {
      impl.setRespectContentChanges( respect );
   }
   
   
   
   public final boolean isRespectingContentChanges()
   {
      return impl.isRespectingContentChanges();
   }
   
   
   
   abstract protected View createFragmentView( LayoutInflater inflater,
                                               ViewGroup container,
                                               Bundle savedInstanceState );
   
   
   
   @Override
   abstract public void initContent( ViewGroup content );
   
   
   
   @Override
   abstract public Loader< D > newLoaderInstance( int id, Bundle config );
   
   
   
   @Override
   abstract public String getLoaderDataName();
   
   
   
   @Override
   abstract public int getLoaderId();
}
