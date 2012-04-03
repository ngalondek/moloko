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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.fragments.base.MolokoDialogFragment;
import dev.drsoran.moloko.fragments.listeners.IAlertDialogFragmentListener;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.UIUtils;


public class AlertDialogFragment extends MolokoDialogFragment
{
   public final static class Config
   {
      private final static String DIALOG_ID = "dialog_id";
      
      public final static String TAG = "tag";
      
      public final static String ICON_ID = "icon_id";
      
      public final static String TITLE_STRING = "title_string";
      
      public final static String MESSAGE_STRING = "message_string";
      
      public final static String POSITIVE_TEXT_ID = "positive_text_id";
      
      public final static String NEGATIVE_TEXT_ID = "negative_text_id";
      
      public final static String NEUTRAL_TEXT_ID = "neutral_text_id";
   }
   
   
   public final static class Builder
   {
      private final Bundle config = new Bundle();
      
      
      
      public Builder( int dialogId )
      {
         config.putInt( Config.DIALOG_ID, dialogId );
      }
      
      
      
      public Builder setTag( String tag )
      {
         config.putString( Config.TAG, tag );
         return this;
      }
      
      
      
      public Builder setTitle( String title )
      {
         config.putString( Config.TITLE_STRING, title );
         return this;
      }
      
      
      
      public Builder setIcon( int iconResId )
      {
         config.putInt( Config.ICON_ID, iconResId );
         return this;
      }
      
      
      
      public Builder setMessage( String message )
      {
         config.putString( Config.MESSAGE_STRING, message );
         return this;
      }
      
      
      
      public Builder setPositiveButton( int textId )
      {
         config.putInt( Config.POSITIVE_TEXT_ID, textId );
         return this;
      }
      
      
      
      public Builder setNegativeButton( int textId )
      {
         config.putInt( Config.NEGATIVE_TEXT_ID, textId );
         return this;
      }
      
      
      
      public Builder setNeutralButton( int textId )
      {
         config.putInt( Config.NEUTRAL_TEXT_ID, textId );
         return this;
      }
      
      
      
      public AlertDialogFragment create()
      {
         AlertDialogFragment frag = AlertDialogFragment.newInstance( config );
         return frag;
      }
      
      
      
      public void show( FragmentActivity activity )
      {
         final AlertDialogFragment frag = create();
         UIUtils.showDialogFragment( activity,
                                     frag,
                                     String.format( "%s:%d",
                                                    AlertDialogFragment.class.getName(),
                                                    config.getInt( Config.DIALOG_ID ) ) );
      }
   } // Builder
   
   private IAlertDialogFragmentListener listener;
   
   @InstanceState( key = Config.DIALOG_ID, defaultValue = "0" )
   private int dialogId;
   
   @InstanceState( key = Config.TAG, defaultValue = Strings.EMPTY_STRING )
   private String tag;
   
   @InstanceState( key = Config.TITLE_STRING,
                   defaultValue = Strings.EMPTY_STRING )
   private String title;
   
   @InstanceState( key = Config.MESSAGE_STRING,
                   defaultValue = Strings.EMPTY_STRING )
   private String message;
   
   @InstanceState( key = Config.ICON_ID, defaultValue = "-1" )
   private int iconId;
   
   @InstanceState( key = Config.POSITIVE_TEXT_ID, defaultValue = "-1" )
   private int positiveButtonTextId;
   
   @InstanceState( key = Config.NEGATIVE_TEXT_ID, defaultValue = "-1" )
   private int negativeButtonTextId;
   
   @InstanceState( key = Config.NEUTRAL_TEXT_ID, defaultValue = "-1" )
   private int neutralButtonTextId;
   
   
   
   public final static AlertDialogFragment newInstance( Bundle config )
   {
      final AlertDialogFragment fragment = new AlertDialogFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   public AlertDialogFragment()
   {
      registerAnnotatedConfiguredInstance( this, AlertDialogFragment.class );
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      if ( activity instanceof IAlertDialogFragmentListener )
         listener = (IAlertDialogFragmentListener) activity;
      else
         listener = null;
   }
   
   
   
   @Override
   public void onDetach()
   {
      listener = null;
      super.onDetach();
   }
   
   
   
   @Override
   public Dialog onCreateDialog( Bundle savedInstanceState )
   {
      if ( savedInstanceState != null )
         configure( savedInstanceState );
      
      final Context context = getSherlockActivity();
      
      final AlertDialog.Builder builder = new AlertDialog.Builder( context );
      
      if ( iconId != -1 )
         builder.setIcon( iconId );
      
      if ( !TextUtils.isEmpty( title ) )
         builder.setTitle( title );
      
      if ( !TextUtils.isEmpty( message ) )
         builder.setMessage( message );
      
      if ( positiveButtonTextId != -1 )
         builder.setPositiveButton( positiveButtonTextId,
                                    getGenericOnClickListener() );
      
      if ( negativeButtonTextId != -1 )
         builder.setNegativeButton( negativeButtonTextId,
                                    getGenericOnClickListener() );
      
      if ( neutralButtonTextId != -1 )
         builder.setNeutralButton( negativeButtonTextId,
                                   getGenericOnClickListener() );
      
      return builder.create();
   }
   
   
   
   @Override
   protected void onButtonClicked( int which )
   {
      if ( listener != null )
         listener.onAlertDialogFragmentClick( dialogId, tag, which );
   }
}
