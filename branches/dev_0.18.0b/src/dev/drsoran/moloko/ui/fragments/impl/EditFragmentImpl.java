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

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.View;
import dev.drsoran.moloko.IHandlerToken;
import dev.drsoran.moloko.app.MolokoApp;
import dev.drsoran.moloko.ui.UiUtils;


public class EditFragmentImpl
{
   private final Fragment fragment;
   
   private final IHandlerToken handler = MolokoApp.acquireHandlerToken();
   
   private IBinder windowToken;
   
   private Context context;
   
   
   
   public EditFragmentImpl( Fragment fragment )
   {
      this.fragment = fragment;
   }
   
   
   
   public void onAttach( Context context )
   {
      this.context = context;
   }
   
   
   
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      windowToken = view.getWindowToken();
   }
   
   
   
   public void onDestroyView()
   {
      hideSoftInput();
   }
   
   
   
   public void onDestroy()
   {
      handler.release();
   }
   
   
   
   public void onDetach()
   {
      context = null;
   }
   
   
   
   public void showShoftInput( final View view )
   {
      handler.post( new Runnable()
      {
         @Override
         public void run()
         {
            if ( fragment.isAdded() )
               UiUtils.showSoftInput( view );
         }
      } );
   }
   
   
   
   public void hideSoftInput()
   {
      if ( windowToken != null )
      {
         UiUtils.hideSoftInput( context, windowToken );
      }
   }
   
   
   
   public void setWindowToken( IBinder windowToken )
   {
      this.windowToken = windowToken;
   }
   
   
   
   public Context getContext()
   {
      return context;
   }
}
