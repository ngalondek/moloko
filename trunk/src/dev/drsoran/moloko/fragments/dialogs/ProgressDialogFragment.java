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

package dev.drsoran.moloko.fragments.dialogs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.fragments.base.MolokoDialogFragment;


public class ProgressDialogFragment extends MolokoDialogFragment
{
   public final static class Config
   {
      public final static String MESSAGE_ID = "message_id";
   }
   
   @InstanceState( key = Config.MESSAGE_ID, defaultValue = "-1" )
   private int messageId;
   
   
   
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
   
   
   
   public ProgressDialogFragment()
   {
      registerAnnotatedConfiguredInstance( this, ProgressDialogFragment.class );
   }
   
   
   
   @Override
   public Dialog onCreateDialog( Bundle savedInstanceState )
   {
      if ( savedInstanceState != null )
         configure( savedInstanceState );
      
      final ProgressDialog progressDialog = new ProgressDialog( getSherlockActivity() );
      
      progressDialog.setMessage( getSherlockActivity().getString( getProgressMessageId() ) );
      progressDialog.setCancelable( false );
      
      return progressDialog;
   }
   
   
   
   private int getProgressMessageId()
   {
      return messageId;
   }
}
