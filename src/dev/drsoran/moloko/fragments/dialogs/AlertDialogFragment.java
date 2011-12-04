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

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import dev.drsoran.moloko.IConfigurable;
import dev.drsoran.moloko.fragments.base.MolokoDialogFragment;
import dev.drsoran.moloko.util.UIUtils;


public class AlertDialogFragment extends MolokoDialogFragment implements
         IConfigurable
{
   public final static class Config
   {
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
      
      private OnClickListener positiveListener;
      
      private OnClickListener negativeListener;
      
      private OnClickListener neutralListener;
      
      

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
      


      public Builder setPositiveButton( int textId, OnClickListener listener )
      {
         config.putInt( Config.POSITIVE_TEXT_ID, textId );
         positiveListener = listener;
         return this;
      }
      


      public Builder setNegativeButton( int textId, OnClickListener listener )
      {
         config.putInt( Config.NEGATIVE_TEXT_ID, textId );
         negativeListener = listener;
         return this;
      }
      


      public Builder setNeutralButton( int textId, OnClickListener listener )
      {
         config.putInt( Config.NEUTRAL_TEXT_ID, textId );
         neutralListener = listener;
         return this;
      }
      


      public AlertDialogFragment create()
      {
         AlertDialogFragment frag = AlertDialogFragment.newInstance( config );
         
         frag.setButtonClickListener( DialogInterface.BUTTON_POSITIVE,
                                      positiveListener );
         frag.setButtonClickListener( DialogInterface.BUTTON_NEGATIVE,
                                      negativeListener );
         frag.setButtonClickListener( DialogInterface.BUTTON_NEUTRAL,
                                      neutralListener );
         
         return frag;
      }
      


      public void show( FragmentActivity activity )
      {
         final AlertDialogFragment frag = create();
         UIUtils.showDialogFragment( activity,
                                     frag,
                                     AlertDialogFragment.class.getName() );
      }
   }
   
   

   public final static AlertDialogFragment newInstance( Bundle config )
   {
      final AlertDialogFragment fragment = new AlertDialogFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   private Bundle configuration;
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      configure( getArguments() );
      
      setRetainInstance( true );
   }
   


   @Override
   public Dialog onCreateDialog( Bundle savedInstanceState )
   {
      if ( savedInstanceState != null )
         configure( savedInstanceState );
      
      final Context context = getFragmentActivity();
      
      final AlertDialog.Builder builder = new AlertDialog.Builder( context );
      
      if ( configuration.containsKey( Config.ICON_ID ) )
         builder.setIcon( configuration.getInt( Config.ICON_ID ) );
      
      if ( configuration.containsKey( Config.TITLE_STRING ) )
         builder.setTitle( configuration.getString( Config.TITLE_STRING ) );
      
      if ( configuration.containsKey( Config.MESSAGE_STRING ) )
         builder.setMessage( configuration.getString( Config.MESSAGE_STRING ) );
      
      if ( configuration.containsKey( Config.POSITIVE_TEXT_ID ) )
         builder.setPositiveButton( configuration.getInt( Config.POSITIVE_TEXT_ID ),
                                    getGenericOnClickListener() );
      
      if ( configuration.containsKey( Config.NEGATIVE_TEXT_ID ) )
         builder.setNegativeButton( configuration.getInt( Config.NEGATIVE_TEXT_ID ),
                                    getGenericOnClickListener() );
      
      if ( configuration.containsKey( Config.NEUTRAL_TEXT_ID ) )
         builder.setNeutralButton( configuration.getInt( Config.NEUTRAL_TEXT_ID ),
                                   getGenericOnClickListener() );
      
      return builder.create();
   }
   


   @Override
   public void clearConfiguration()
   {
      configuration.clear();
   }
   


   @Override
   public void configure( Bundle config )
   {
      if ( configuration == null )
         configuration = createDefaultConfiguration();
      
      if ( config != null )
      {
         if ( config.containsKey( Config.ICON_ID ) )
            configuration.putInt( Config.ICON_ID,
                                  config.getInt( Config.ICON_ID ) );
         if ( config.containsKey( Config.TITLE_STRING ) )
            configuration.putString( Config.TITLE_STRING,
                                     config.getString( Config.TITLE_STRING ) );
         if ( config.containsKey( Config.MESSAGE_STRING ) )
            configuration.putString( Config.MESSAGE_STRING,
                                     config.getString( Config.MESSAGE_STRING ) );
         if ( config.containsKey( Config.POSITIVE_TEXT_ID ) )
            configuration.putInt( Config.POSITIVE_TEXT_ID,
                                  config.getInt( Config.POSITIVE_TEXT_ID ) );
         if ( config.containsKey( Config.NEGATIVE_TEXT_ID ) )
            configuration.putInt( Config.NEGATIVE_TEXT_ID,
                                  config.getInt( Config.NEGATIVE_TEXT_ID ) );
         if ( config.containsKey( Config.NEUTRAL_TEXT_ID ) )
            configuration.putInt( Config.NEUTRAL_TEXT_ID,
                                  config.getInt( Config.NEUTRAL_TEXT_ID ) );
      }
   }
   


   @Override
   public Bundle createDefaultConfiguration()
   {
      return new Bundle();
   }
   


   @Override
   public Bundle getConfiguration()
   {
      return new Bundle( configuration );
   }
}
