/*
 * Copyright (c) 2012 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.prefs;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.TextView;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;


abstract class SyncableListPreference extends AutoSummaryListPreference
         implements OnClickListener
{
   protected final String syncWithRtmKey;
   
   private final ISyncStateViewManager viewManager;
   
   private CheckBox checkBox;
   
   
   
   public SyncableListPreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      
      if ( MolokoApp.isApiLevelSupported( Build.VERSION_CODES.HONEYCOMB ) )
      {
         viewManager = new HoneycombViewManager();
      }
      else
      {
         viewManager = new PreHoneycombViewManager();
      }
      
      setDialogLayoutResource( R.layout.moloko_prefs_take_from_rtm_checkbox );
      
      final TypedArray array = context.obtainStyledAttributes( attrs,
                                                               R.styleable.SyncableListPreference,
                                                               0,
                                                               0 );
      
      syncWithRtmKey = array.getString( R.styleable.SyncableListPreference_syncWithRtmKey );
      
      array.recycle();
      
      if ( syncWithRtmKey == null )
      {
         throw new IllegalStateException( "SyncableListPreference requires a syncWithRtmKey attribute." );
      }
   }
   
   
   
   @Override
   protected void onBindView( View view )
   {
      viewManager.onBindView( view );
      viewManager.updateUi();
      
      super.onBindView( view );
   }
   
   
   
   @Override
   protected void onBindDialogView( View view )
   {
      super.onBindDialogView( view );
      
      checkBox = (CheckBox) view.findViewById( R.id.sync_check );
      
      if ( checkBox != null )
      {
         checkBox.setOnClickListener( this );
      }
   }
   
   
   
   @Override
   public void onClick( View v )
   {
      final boolean isChecked = checkBox.isChecked();
      
      setSyncWithRtm( isChecked );
      
      /*
       * Clicking on the check box simulates the negative button click, and dismisses the dialog. This is needed cause
       * we write the value from RTM and a positive result would write the current list value.
       */
      onClick( getDialog(), DialogInterface.BUTTON_NEGATIVE );
      getDialog().dismiss();
   }
   
   
   
   @Override
   public void onSharedPreferenceChanged( SharedPreferences sharedPreferences,
                                          String key )
   {
      if ( key != null )
      {
         if ( key.equals( getKey() ) )
         {
            final String value = sharedPreferences.getString( getKey(),
                                                              getValue() );
            
            setValue( value );
            notifyChanged();
         }
         else if ( key.equals( syncWithRtmKey ) )
         {
            notifyChanged();
         }
      }
      else
      {
         super.onSharedPreferenceChanged( sharedPreferences, key );
      }
   }
   
   
   
   @Override
   public boolean onPreferenceChange( Preference preference, Object newValue )
   {
      viewManager.updateUi();
      notifyChanged();
      
      return true;
   }
   
   
   
   protected boolean hasRtmSettings()
   {
      return MolokoApp.getSettings( getContext() ).getRtmSettings() != null;
   }
   
   
   
   @Override
   protected void onPrepareDialogBuilder( Builder builder )
   {
      super.onPrepareDialogBuilder( builder );
      
      if ( checkBox != null )
      {
         checkBox.setChecked( isSyncWithRtm() );
      }
   }
   
   
   
   @Override
   protected void onDialogClosed( boolean positiveResult )
   {
      // in case of a positive result a list item has been
      // clicked and we switch to user setting
      if ( positiveResult )
      {
         setSyncWithRtm( false );
      }
      
      super.onDialogClosed( positiveResult );
   }
   
   
   
   abstract protected boolean isSyncWithRtm();
   
   
   
   abstract protected void setSyncWithRtm( boolean value );
   
   
   private interface ISyncStateViewManager
   {
      
      void onBindView( View view );
      
      
      
      void updateUi();
      
   }
   
   
   private final class PreHoneycombViewManager implements ISyncStateViewManager
   {
      private TextView settingSourceText;
      
      
      
      public PreHoneycombViewManager()
      {
         setLayoutResource( R.layout.moloko_prefs_rtm_list_preference );
      }
      
      
      
      @Override
      public void onBindView( View view )
      {
         settingSourceText = (TextView) view.findViewById( R.id.sync_list_setting_source_text );
      }
      
      
      
      @Override
      public void updateUi()
      {
         if ( settingSourceText != null )
         {
            if ( isSyncWithRtm() && hasRtmSettings() )
            {
               settingSourceText.setText( R.string.g_settings_src_rtm );
               settingSourceText.setCompoundDrawables( createLeftDrawable( R.drawable.ic_small_black_refresh ),
                                                       null,
                                                       null,
                                                       null );
            }
            else
            {
               settingSourceText.setText( R.string.g_settings_src_local );
               settingSourceText.setCompoundDrawables( createLeftDrawable( R.drawable.ic_small_black_user ),
                                                       null,
                                                       null,
                                                       null );
            }
         }
      }
      
      
      
      private Drawable createLeftDrawable( int resId )
      {
         final Drawable[] drawables = settingSourceText.getCompoundDrawables();
         
         BitmapDrawable drawable = null;
         
         if ( drawables[ 0 ] != null )
         {
            final Resources resources = getContext().getResources();
            drawable = new BitmapDrawable( resources,
                                           resources.openRawResource( resId ) );
            
            if ( drawable != null )
            {
               drawable.setBounds( drawables[ 0 ].getBounds() );
            }
         }
         
         return drawable;
      }
   }
   
   
   private final class HoneycombViewManager implements ISyncStateViewManager
   {
      
      @Override
      public void onBindView( View view )
      {
      }
      
      
      
      @Override
      public void updateUi()
      {
         if ( isSyncWithRtm() && hasRtmSettings() )
         {
            setIcon( R.drawable.ic_rtm_outline );
         }
         else
         {
            setIcon( R.drawable.ic_prefs_user );
         }
      }
   }
}
