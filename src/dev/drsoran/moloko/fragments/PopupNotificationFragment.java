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

package dev.drsoran.moloko.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.fragments.base.MolokoFragment;
import dev.drsoran.moloko.fragments.listeners.IPopupNotificationFragmentListener;
import dev.drsoran.moloko.layouts.TitleWithTextLayout;
import dev.drsoran.moloko.util.Strings;


public class PopupNotificationFragment extends MolokoFragment
{
   public final static class Config
   {
      public final static String NOTIFICATION_TITLE = "notif_title";
      
      public final static String NOTIFICATION_MESSAGE = "notif_message";
      
      public final static String NOTIFICATION_AUTO_CLOSE_MS = "notif_auto_close_ms";
   }
   
   private final Handler handler = new Handler();
   
   private final Runnable closePopupRunnable = new Runnable()
   {
      @Override
      public void run()
      {
         notifyClosePopup();
      }
   };
   
   @InstanceState( key = Config.NOTIFICATION_TITLE,
                   defaultValue = Strings.EMPTY_STRING )
   private String title;
   
   @InstanceState( key = Config.NOTIFICATION_MESSAGE,
                   defaultValue = Strings.EMPTY_STRING )
   private String message;
   
   @InstanceState( key = Config.NOTIFICATION_AUTO_CLOSE_MS, defaultValue = "-1" )
   private long autoCloseMs;
   
   private TitleWithTextLayout content;
   
   private IPopupNotificationFragmentListener listener;
   
   
   
   public static PopupNotificationFragment newInstance( Bundle configuration )
   {
      final PopupNotificationFragment fragment = new PopupNotificationFragment();
      
      fragment.setArguments( configuration );
      
      return fragment;
   }
   
   
   
   public PopupNotificationFragment()
   {
      registerAnnotatedConfiguredInstance( this,
                                           PopupNotificationFragment.class );
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      if ( activity instanceof IPopupNotificationFragmentListener )
      {
         listener = (IPopupNotificationFragmentListener) activity;
      }
      else
      {
         listener = null;
      }
   }
   
   
   
   @Override
   public View onCreateView( LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState )
   {
      final ViewGroup view = (ViewGroup) inflater.inflate( R.layout.popup_notificaton_fragment,
                                                           container,
                                                           false );
      
      content = (TitleWithTextLayout) view.findViewById( R.id.notification );
      
      initTitle();
      initMessage();
      setButtonListener( view );
      startAutoCloseTimer();
      
      return view;
   }
   
   
   
   @Override
   public void onDestroyView()
   {
      handler.removeCallbacks( closePopupRunnable );
      super.onDestroyView();
   }
   
   
   
   @Override
   public void onDetach()
   {
      listener = null;
      super.onDetach();
   }
   
   
   
   private void initTitle()
   {
      content.setTitle( title );
      content.showTopLine( !TextUtils.isEmpty( title ) );
   }
   
   
   
   private void initMessage()
   {
      content.setText( message );
   }
   
   
   
   private void setButtonListener( ViewGroup fragmentView )
   {
      ( (ImageButton) fragmentView.findViewById( android.R.id.closeButton ) ).setOnClickListener( new OnClickListener()
      {
         @Override
         public void onClick( View v )
         {
            notifyClosePopup();
         }
      } );
   }
   
   
   
   private void startAutoCloseTimer()
   {
      if ( autoCloseMs > 0 && listener != null )
      {
         handler.removeCallbacks( closePopupRunnable );
         handler.postDelayed( closePopupRunnable, autoCloseMs );
      }
   }
   
   
   
   private void notifyClosePopup()
   {
      if ( listener != null )
      {
         listener.onClosePopup();
      }
   }
}
