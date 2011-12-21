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

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dev.drsoran.moloko.ApplyChangesInfo;
import dev.drsoran.moloko.IEditFragment;
import dev.drsoran.moloko.content.ContentProviderActionItemList;
import dev.drsoran.moloko.fragments.listeners.IEditFragmentListener;
import dev.drsoran.moloko.util.UIUtils;


public abstract class MolokoEditDialogFragment< T extends Fragment > extends
         MolokoDialogFragment implements IEditFragment< T >
{
   private final Handler handler = new Handler();
   
   private IEditFragmentListener listener;
   
   private ViewGroup dialogView;
   
   private IBinder windowToken;
   
   
   
   @Override
   public void onAttach( FragmentActivity activity )
   {
      super.onAttach( activity );
      
      if ( activity instanceof IEditFragmentListener )
         listener = (IEditFragmentListener) activity;
      else
         listener = null;
   }
   
   
   
   @Override
   public void onDetach()
   {
      super.onDetach();
      listener = null;
   }
   
   
   
   @Override
   public void onDestroyView()
   {
      if ( windowToken != null )
      {
         handler.post( new Runnable()
         {
            @Override
            public void run()
            {
               final Context context = getFragmentActivity();
               if ( context != null )
                  UIUtils.hideSoftInput( context, windowToken );
            }
         } );
      }
      
      super.onDestroyView();
   }
   
   
   
   @Override
   public final Dialog onCreateDialog( Bundle savedInstanceState )
   {
      if ( savedInstanceState != null )
         configure( savedInstanceState );
      
      dialogView = createContent( LayoutInflater.from( getFragmentActivity() ) );
      windowToken = dialogView.getWindowToken();
      
      final Dialog dialog = createDialog( dialogView );
      
      return dialog;
   }
   
   
   
   public View getDialogView()
   {
      return dialogView;
   }
   
   
   
   @Override
   public final boolean onFinishEditing()
   {
      boolean ok = validateInput();
      
      if ( ok && hasChanges() )
      {
         ok = saveChanges();
      }
      
      if ( ok )
         getDialog().dismiss();
      
      return ok;
   }
   
   
   
   @Override
   public final void onCancelEditing()
   {
      if ( hasChanges() )
      {
         requestCancelEditing();
      }
      else
      {
         getDialog().cancel();
      }
   }
   
   
   
   public void showShoftInput( final View view )
   {
      handler.post( new Runnable()
      {
         @Override
         public void run()
         {
            UIUtils.showSoftInput( view );
         }
      } );
   }
   
   
   
   protected boolean validateInput()
   {
      return true;
   }
   
   
   
   protected boolean applyModifications( ContentProviderActionItemList actionItemList,
                                         ApplyChangesInfo applyChangesInfo )
   {
      boolean ok = listener != null;
      return ok
         && listener.applyModifications( actionItemList, applyChangesInfo );
   }
   
   
   
   protected void requestCancelEditing()
   {
      if ( listener != null )
         listener.requestCancelEditing( getTag() );
   }
   
   
   
   protected boolean applyModifications( Pair< ContentProviderActionItemList, ApplyChangesInfo > modifications )
   {
      return applyModifications( modifications.first, modifications.second );
   }
   
   
   
   abstract protected ViewGroup createContent( LayoutInflater inflater );
   
   
   
   abstract protected Dialog createDialog( View fragmentView );
   
   
   
   protected abstract boolean saveChanges();
}
