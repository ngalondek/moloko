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
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import dev.drsoran.moloko.IHandlerToken;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.ui.UiUtils;


public class EditFragmentImpl
{
   private final Fragment fragment;
   
   private IHandlerToken handler;
   
   private IBinder windowToken;
   
   private AppContext context;
   
   
   
   public EditFragmentImpl( Fragment fragment )
   {
      this.fragment = fragment;
   }
   
   
   
   public void onAttach( Activity activity )
   {
      this.context = AppContext.get( activity );
      this.handler = context.acquireHandlerToken();
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
      if ( handler != null )
      {
         handler.release();
         handler = null;
      }
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
            {
               UiUtils.showSoftInput( view );
            }
         }
      } );
   }
   
   
   
   public void hideSoftInput()
   {
      if ( windowToken != null )
      {
         final InputMethodManager imm = (InputMethodManager) context.getSystemService( Context.INPUT_METHOD_SERVICE );
         if ( imm != null )
         {
            imm.hideSoftInputFromWindow( windowToken,
                                         InputMethodManager.HIDE_NOT_ALWAYS );
         }
      }
   }
   
   
   
   public void setWindowToken( IBinder windowToken )
   {
      this.windowToken = windowToken;
   }
}
