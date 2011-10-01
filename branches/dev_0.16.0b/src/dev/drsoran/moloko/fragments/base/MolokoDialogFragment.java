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

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;


public abstract class MolokoDialogFragment extends DialogFragment
{
   
   private OnClickListener listenerPositive;
   
   private OnClickListener listenerNegative;
   
   private OnClickListener listenerNeutral;
   
   private final DialogInterface.OnClickListener genericListener = new OnClickListener()
   {
      @Override
      public void onClick( DialogInterface dialog, int which )
      {
         switch ( which )
         {
            case DialogInterface.BUTTON_POSITIVE:
               if ( listenerPositive != null )
                  listenerPositive.onClick( getDialog(), which );
               break;
            case DialogInterface.BUTTON_NEGATIVE:
               if ( listenerNegative != null )
                  listenerNegative.onClick( getDialog(), which );
               break;
            case DialogInterface.BUTTON_NEUTRAL:
               if ( listenerNeutral != null )
                  listenerNeutral.onClick( getDialog(), which );
               break;
            default :
               break;
         }
      }
   };
   
   
   
   public FragmentActivity getFragmentActivity()
   {
      return (FragmentActivity) getSupportActivity();
   }
   
   
   
   public void setButtonClickListener( int buttonType,
                                       DialogInterface.OnClickListener listener )
   {
      switch ( buttonType )
      {
         case DialogInterface.BUTTON_POSITIVE:
            listenerPositive = listener;
            break;
         case DialogInterface.BUTTON_NEGATIVE:
            listenerNegative = listener;
            break;
         case DialogInterface.BUTTON_NEUTRAL:
            listenerNeutral = listener;
            break;
         default :
            break;
      }
   }
   
   
   
   protected OnClickListener getGenericOnClickListener()
   {
      return genericListener;
   }
}
