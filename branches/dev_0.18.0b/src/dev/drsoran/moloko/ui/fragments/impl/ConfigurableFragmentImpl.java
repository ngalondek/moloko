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

package dev.drsoran.moloko.ui.fragments.impl;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import dev.drsoran.moloko.state.AnnotatedConfigurationSupport;
import dev.drsoran.moloko.ui.UiContext;


public class ConfigurableFragmentImpl
{
   private final AnnotatedConfigurationSupport annotatedConfigSupport = new AnnotatedConfigurationSupport();
   
   private final Fragment fragment;
   
   private UiContext uiContext;
   
   
   
   public ConfigurableFragmentImpl( Fragment fragment )
   {
      this.fragment = fragment;
   }
   
   
   
   public Fragment getFragment()
   {
      return fragment;
   }
   
   
   
   public void onAttach( Activity activity )
   {
      this.uiContext = UiContext.get( activity );
      this.annotatedConfigSupport.onAttach( uiContext.asSystemContext() );
   }
   
   
   
   public void onCreate( Bundle savedInstanceState )
   {
      if ( savedInstanceState == null )
         configure( fragment.getArguments() );
      else
         configure( savedInstanceState );
   }
   
   
   
   public void onDetach()
   {
      annotatedConfigSupport.onDetach();
      uiContext = null;
   }
   
   
   
   public UiContext getUiContext()
   {
      return uiContext;
   }
   
   
   
   public void setArguments( Bundle args )
   {
      configure( args );
   }
   
   
   
   public Bundle getConfiguration()
   {
      return annotatedConfigSupport.getInstanceStates();
   }
   
   
   
   public void configure( Bundle config )
   {
      annotatedConfigSupport.setInstanceStates( config );
   }
   
   
   
   public void setDefaultConfiguration()
   {
      annotatedConfigSupport.setDefaultInstanceStates();
   }
   
   
   
   public Bundle getDefaultConfiguration()
   {
      return annotatedConfigSupport.getDefaultInstanceStates();
   }
   
   
   
   public < T > Bundle getDefaultConfiguration( T instance, Class< T > clazz )
   {
      return annotatedConfigSupport.getDefaultInstanceState( instance );
   }
   
   
   
   public < T > void registerAnnotatedConfiguredInstance( T instance,
                                                          Class< T > clazz )
   {
      annotatedConfigSupport.registerInstance( instance, clazz );
   }
   
   
   
   public void onSaveInstanceState( Bundle outState )
   {
      annotatedConfigSupport.onSaveInstanceStates( outState );
   }
   
   
   
   public ViewGroup getContentView()
   {
      final View root = fragment.getView();
      
      if ( root != null )
         return (ViewGroup) root.findViewById( android.R.id.content );
      else
         return null;
   }
}
