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

package dev.drsoran.moloko.fragments.dialogs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import dev.drsoran.moloko.fragments.base.MolokoDialogFragment;


public class ProgressDialogFragment extends MolokoDialogFragment
{
   public final static class Config
   {
      public final static String MESSAGE_ID = "message_id";
   }
   
   

   public final static ProgressDialogFragment newInstance( int messageResId )
   {
      final Bundle config = new Bundle( 1 );
      config.putInt( Config.MESSAGE_ID, messageResId );
      
      return newInstance( config );
   }
   


   public final static ProgressDialogFragment newInstance( Bundle config )
   {
      final ProgressDialogFragment fragment = new ProgressDialogFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   


   @Override
   public Dialog onCreateDialog( Bundle savedInstanceState )
   {
      if ( savedInstanceState != null )
         configure( savedInstanceState );
      
      ProgressDialog progressDialog = new ProgressDialog( getFragmentActivity() );
      
      progressDialog.setMessage( getFragmentActivity().getString( getProgressMessageId() ) );
      progressDialog.setCancelable( false );
      
      return progressDialog;
   }
   


   @Override
   protected void takeConfigurationFrom( Bundle config )
   {
      if ( config.containsKey( Config.MESSAGE_ID ) )
         configuration.putInt( Config.MESSAGE_ID,
                               config.getInt( Config.MESSAGE_ID ) );
   }
   


   private int getProgressMessageId()
   {
      return configuration.getInt( Config.MESSAGE_ID, View.NO_ID );
   }
}
