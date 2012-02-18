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

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.SupportActivity;


public class MolokoDialogFragmentImpl
{
   private final MolokoFragmentImpl impl;
   
   
   
   public MolokoDialogFragmentImpl( DialogFragment fragment, int settingsMask )
   {
      impl = new MolokoFragmentImpl( fragment, settingsMask );
   }
   
   
   
   public void onCreate( Bundle savedInstanceState )
   {
      impl.onCreate( savedInstanceState );
   }
   
   
   
   public void onAttach( SupportActivity activity )
   {
      impl.onAttach( activity );
   }
   
   
   
   public void onDetach()
   {
      impl.onDetach();
   }
   
   
   
   public void setArguments( Bundle args )
   {
      impl.setArguments( args );
   }
   
   
   
   public void onSaveInstanceState( Bundle outState )
   {
      impl.onSaveInstanceState( outState );
   }
   
   
   
   public void registerAnnotatedConfiguredInstance( Object instance,
                                                    Bundle initialState )
   {
      impl.registerAnnotatedConfiguredInstance( instance, initialState );
   }
   
   
   
   public Bundle getConfiguration()
   {
      return impl.getConfiguration();
   }
   
   
   
   public void configure( Bundle config )
   {
      impl.configure( config );
   }
   
   
   
   public void clearConfiguration()
   {
      impl.setDefaultConfiguration();
   }
}
