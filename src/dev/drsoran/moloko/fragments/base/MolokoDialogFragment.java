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
import dev.drsoran.moloko.fragments.base.impl.MolokoDialogFragmentImpl;


public abstract class MolokoDialogFragment extends DialogFragment implements
         IConfigurable, IOnSettingsChangedListener
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
   public void onAttach( SupportActivity activity )
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
   
   
   
   public FragmentActivity getFragmentActivity()
   {
      return (FragmentActivity) getSupportActivity();
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
      impl.clearConfiguration();
   }
   
   
   
   @Override
   public void onSettingsChanged( int which,
                                  HashMap< Integer, Object > oldValues )
   {
   }
   
   
   
   public int getSettingsMask()
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
