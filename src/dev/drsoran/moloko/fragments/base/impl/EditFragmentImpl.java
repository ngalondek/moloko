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

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.View;
import dev.drsoran.moloko.ApplyChangesInfo;
import dev.drsoran.moloko.content.ContentProviderActionItemList;
import dev.drsoran.moloko.fragments.listeners.IEditFragmentListener;
import dev.drsoran.moloko.util.UIUtils;


public class EditFragmentImpl
{
   private final Fragment fragment;
   
   private final Handler handler = new Handler();
   
   private IBinder windowToken;
   
   private IEditFragmentListener listener;
   
   private Context context;
   
   
   
   public EditFragmentImpl( Fragment fragment )
   {
      this.fragment = fragment;
   }
   
   
   
   public void onAttach( Context context )
   {
      if ( context instanceof IEditFragmentListener )
         listener = (IEditFragmentListener) context;
      else
         listener = null;
      
      this.context = context;
   }
   
   
   
   public void onDetach()
   {
      listener = null;
      context = null;
   }
   
   
   
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      windowToken = view.getWindowToken();
   }
   
   
   
   public void onDestroyView()
   {
      hideSoftInput();
   }
   
   
   
   public void showShoftInput( final View view )
   {
      handler.post( new Runnable()
      {
         @Override
         public void run()
         {
            if ( fragment.isAdded() )
               UIUtils.showSoftInput( view );
         }
      } );
   }
   
   
   
   public void hideSoftInput()
   {
      if ( windowToken != null )
      {
         handler.post( new Runnable()
         {
            @Override
            public void run()
            {
               if ( context != null )
                  UIUtils.hideSoftInput( context, windowToken );
            }
         } );
      }
   }
   
   
   
   public void requestCancelEditing( String fragmentTag )
   {
      if ( listener != null )
         listener.requestCancelEditing( fragmentTag );
   }
   
   
   
   public boolean applyModifications( ContentProviderActionItemList actionItemList,
                                      ApplyChangesInfo applyChangesInfo )
   {
      boolean ok = listener != null;
      return ok
         && listener.applyModifications( actionItemList, applyChangesInfo );
   }
   
   
   
   public boolean applyModifications( Pair< ContentProviderActionItemList, ApplyChangesInfo > modifications )
   {
      return applyModifications( modifications.first, modifications.second );
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
