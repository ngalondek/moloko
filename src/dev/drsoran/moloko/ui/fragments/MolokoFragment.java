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

package dev.drsoran.moloko.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dev.drsoran.moloko.IConfigurable;
import dev.drsoran.moloko.ui.UiContext;
import dev.drsoran.moloko.ui.fragments.impl.ConfigurableFragmentImpl;
import dev.drsoran.moloko.ui.fragments.impl.RtmAccessLevelFragmentImpl;
import dev.drsoran.rtm.ILog;


public abstract class MolokoFragment extends Fragment implements IConfigurable
{
   private final ConfigurableFragmentImpl impl;
   
   private final RtmAccessLevelFragmentImpl accessImpl;
   
   
   
   protected MolokoFragment()
   {
      impl = new ConfigurableFragmentImpl( this );
      accessImpl = new RtmAccessLevelFragmentImpl();
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      impl.onAttach( activity );
      accessImpl.onAttach( activity );
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      impl.onCreate( savedInstanceState );
   }
   
   
   
   @Override
   public void onDetach()
   {
      accessImpl.onDetach();
      impl.onDetach();
      
      super.onDetach();
   }
   
   
   
   @Override
   public void setArguments( Bundle args )
   {
      super.setArguments( args );
      impl.setArguments( args );
   }
   
   
   
   @Override
   public void onSaveInstanceState( Bundle outState )
   {
      super.onSaveInstanceState( outState );
      impl.onSaveInstanceState( outState );
   }
   
   
   
   public UiContext getUiContext()
   {
      return impl.getUiContext();
   }
   
   
   
   public ILog Log()
   {
      return impl.getUiContext().Log();
   }
   
   
   
   @Override
   public final < T > void registerAnnotatedConfiguredInstance( T instance,
                                                                Class< T > clazz )
   {
      impl.registerAnnotatedConfiguredInstance( instance, clazz );
   }
   
   
   
   @Override
   public final Bundle getConfiguration()
   {
      return impl.getConfiguration();
   }
   
   
   
   @Override
   public final void configure( Bundle config )
   {
      impl.configure( config );
   }
   
   
   
   @Override
   public final void clearConfiguration()
   {
      impl.setDefaultConfiguration();
   }
   
   
   
   public Bundle getDefaultConfiguration()
   {
      return impl.getDefaultConfiguration();
   }
   
   
   
   public final ViewGroup getContentView()
   {
      return impl.getContentView();
   }
   
   
   
   public boolean isWritableAccess()
   {
      return accessImpl.hasWritableAccess();
   }
   
   
   
   @Override
   public abstract View onCreateView( LayoutInflater inflater,
                                      ViewGroup container,
                                      Bundle savedInstanceState );
}
