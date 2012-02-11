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

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.SupportActivity;
import dev.drsoran.moloko.IConfigurable;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.MolokoApp;


public abstract class MolokoDialogFragment extends DialogFragment implements
         IConfigurable
{
   
   private final DialogInterface.OnClickListener genericListener = new OnClickListener()
   {
      @Override
      public void onClick( DialogInterface dialog, int which )
      {
         onButtonClicked( which );
      }
   };
   
   private IOnSettingsChangedListener onSettingsChangedListener;
   
   protected Bundle configuration;
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      if ( savedInstanceState == null )
         configure( getArguments() );
      else
         configure( savedInstanceState );
   }
   
   
   
   @Override
   public final void onAttach( SupportActivity activity )
   {
      super.onAttach( activity );
      
      onAttach( (FragmentActivity) activity );
   }
   
   
   
   public void onAttach( FragmentActivity activity )
   {
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
                  MolokoDialogFragment.this.onSettingsChanged( which, oldValues );
            }
         };
         
         MolokoApp.getNotifierContext( activity )
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
         MolokoApp.getNotifierContext( getFragmentActivity() )
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
   
   
   
   @Override
   public Bundle getConfiguration()
   {
      return new Bundle( configuration );
   }
   
   
   
   protected void takeConfigurationFrom( Bundle config )
   {
   }
   
   
   
   protected void putDefaultConfigurationTo( Bundle bundle )
   {
   }
   
   
   
   public void onSettingsChanged( int which,
                                  HashMap< Integer, Object > oldValues )
   {
   }
   
   
   
   public int getSettingsMask()
   {
      return 0;
   }
   
   
   
   public FragmentActivity getFragmentActivity()
   {
      return (FragmentActivity) getSupportActivity();
   }
   
   
   
   protected void onButtonClicked( int which )
   {
   }
   
   
   
   protected OnClickListener getGenericOnClickListener()
   {
      return genericListener;
   }
}
