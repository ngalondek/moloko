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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.SupportActivity;
import android.view.View;
import android.view.ViewGroup;


class MolokoFragmentImpl
{
   private final Fragment fragment;
   
   private final FragmentConfigurableImpl configuration;
   
   private FragmentActivity activity;
   
   
   
   public MolokoFragmentImpl( Fragment fragment, int settingsMask )
   {
      this.fragment = fragment;
      configuration = new FragmentConfigurableImpl( fragment, settingsMask );
   }
   
   
   
   public void onCreate( Bundle savedInstanceState )
   {
      if ( savedInstanceState == null )
         configure( getArguments() );
      else
         configure( savedInstanceState );
   }
   
   
   
   public void onAttach( SupportActivity activity )
   {
      onAttach( (FragmentActivity) activity );
   }
   
   
   
   public void onDetach()
   {
      configuration.onDetach();
      activity = null;
   }
   
   
   
   public void setArguments( Bundle args )
   {
      configure( args );
   }
   
   
   
   public void onSaveInstanceState( Bundle outState )
   {
      outState.putAll( getConfiguration() );
   }
   
   
   
   public Bundle getConfiguration()
   {
      return new Bundle( configuration.getConfiguration() );
   }
   
   
   
   public void configure( Bundle config )
   {
      configuration.configure( config );
   }
   
   
   
   public void clearConfiguration()
   {
      configuration.clearConfiguration();
   }
   
   
   
   public Bundle createDefaultConfiguration()
   {
      return configuration.createDefaultConfiguration();
   }
   
   
   
   public ViewGroup getContentView()
   {
      final View root = fragment.getView();
      
      if ( root != null )
         return (ViewGroup) root.findViewById( android.R.id.content );
      else
         return null;
   }
   
   
   
   private void onAttach( FragmentActivity activity )
   {
      this.activity = activity;
      configuration.onAttach( activity, getArguments() );
   }
}
