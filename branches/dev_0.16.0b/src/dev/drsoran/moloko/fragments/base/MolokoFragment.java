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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import dev.drsoran.moloko.IConfigurable;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.MolokoApp;


public abstract class MolokoFragment extends Fragment implements IConfigurable
{
   private IOnSettingsChangedListener onSettingsChangedListener;
   
   protected Bundle configuration;
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      configure( getArguments() );
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
                  MolokoFragment.this.onSettingsChanged( which, oldValues );
            }
         };
         
         MolokoApp.get( activity )
                  .registerOnSettingsChangedListener( settingsMask,
                                                      onSettingsChangedListener );
      }
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
   


   public final ViewGroup getContentView()
   {
      final View root = getView();
      
      if ( root != null )
         return (ViewGroup) root.findViewById( android.R.id.content );
      else
         return null;
   }
   


   protected void onSettingsChanged( int which,
                                     HashMap< Integer, Object > oldValues )
   {
   }
   


   public int getSettingsMask()
   {
      return 0;
   }
}
