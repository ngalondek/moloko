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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.SupportActivity;
import android.view.View;
import android.view.ViewGroup;
import dev.drsoran.moloko.AnnotatedConfigurationSupport;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.MolokoApp;


public class MolokoFragmentImpl implements IOnSettingsChangedListener
{
   private final AnnotatedConfigurationSupport annotatedConfigSupport = new AnnotatedConfigurationSupport();
   
   private final Fragment fragment;
   
   private FragmentActivity activity;
   
   private int settingsMask;
   
   
   
   public MolokoFragmentImpl( Fragment fragment, int settingsMask )
   {
      this.fragment = fragment;
      this.settingsMask = settingsMask;
   }
   
   
   
   public void onCreate( Bundle savedInstanceState )
   {
      if ( savedInstanceState == null )
         configure( fragment.getArguments() );
      else
         configure( savedInstanceState );
   }
   
   
   
   public void onAttach( SupportActivity activity )
   {
      this.activity = (FragmentActivity) activity;
      if ( settingsMask != 0 && activity instanceof IOnSettingsChangedListener )
      {
         MolokoApp.getNotifierContext( this.activity )
                  .registerOnSettingsChangedListener( settingsMask, this );
      }
   }
   
   
   
   public void onDetach()
   {
      if ( activity instanceof IOnSettingsChangedListener )
      {
         MolokoApp.getNotifierContext( activity )
                  .unregisterOnSettingsChangedListener( this );
      }
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
   
   
   
   public < T > void registerAnnotatedConfiguredInstance( T instance,
                                                          Class< T > clazz,
                                                          Bundle initialConfig )
   {
      annotatedConfigSupport.registerInstance( instance, clazz, initialConfig );
   }
   
   
   
   public void onSaveInstanceState( Bundle outState )
   {
      annotatedConfigSupport.onSaveInstanceStates( outState );
   }
   
   
   
   @Override
   public void onSettingsChanged( int which,
                                  HashMap< Integer, Object > oldValues )
   {
      if ( fragment.isAdded() && !fragment.isDetached() )
      {
         ( (IOnSettingsChangedListener) fragment ).onSettingsChanged( which,
                                                                      oldValues );
      }
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
