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
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockDialogFragment;

import dev.drsoran.moloko.app.settings.IOnSettingsChangedListener;
import dev.drsoran.moloko.ui.IConfigurable;
import dev.drsoran.moloko.ui.fragments.impl.MolokoDialogFragmentImpl;


public abstract class MolokoDialogFragment extends SherlockDialogFragment
         implements IConfigurable, IOnSettingsChangedListener
{
   
   private final DialogInterface.OnClickListener genericListener = new OnClickListener()
   {
      @Override
      public void onClick( DialogInterface dialog, int which )
      {
         onButtonClicked( which );
      }
   };
   
   private final MolokoDialogFragmentImpl impl;
   
   
   
   protected MolokoDialogFragment()
   {
      impl = new MolokoDialogFragmentImpl( this, getSettingsMask() );
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      impl.onCreate( savedInstanceState );
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      impl.onAttach( activity );
   }
   
   
   
   @Override
   public void onDetach()
   {
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
   
   
   
   @Override
   public final < T > void registerAnnotatedConfiguredInstance( T instance,
                                                                Class< T > clazz )
   {
      impl.registerAnnotatedConfiguredInstance( instance, clazz );
   }
   
   
   
   @Override
   public Bundle getConfiguration()
   {
      return impl.getConfiguration();
   }
   
   
   
   @Override
   public final void configure( Bundle config )
   {
      impl.configure( config );
   }
   
   
   
   @Override
   public void clearConfiguration()
   {
      impl.setDefaultConfiguration();
   }
   
   
   
   public Bundle getDefaultConfiguration()
   {
      return impl.getDefaultConfiguration();
   }
   
   
   
   @Override
   public void onSettingsChanged( int which )
   {
   }
   
   
   
   protected int getSettingsMask()
   {
      return 0;
   }
   
   
   
   protected void onButtonClicked( int which )
   {
   }
   
   
   
   protected OnClickListener getGenericOnClickListener()
   {
      return genericListener;
   }
}
