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

package dev.drsoran.moloko.app.prefs;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.app.services.ISettingsService;


public class InfoTextPreference extends Preference implements IMolokoPreference
{
   private View preferenceView;
   
   private final AppContext appContext;
   
   
   
   public InfoTextPreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      
      appContext = AppContext.get( context );
      
      final TypedArray array = context.obtainStyledAttributes( attrs,
                                                               R.styleable.InfoTextPreference,
                                                               0,
                                                               0 );
      
      if ( MolokoApp.isApiLevelSupported( Build.VERSION_CODES.HONEYCOMB ) )
      {
         final int iconResId = array.getResourceId( R.styleable.InfoTextPreference_infoIcon,
                                                    -1 );
         if ( iconResId != -1 )
         {
            setIcon( iconResId );
         }
      }
      else
      {
         final int widgetLayoutResId = array.getResourceId( R.styleable.InfoTextPreference_infoWidgetLayout,
                                                            -1 );
         if ( widgetLayoutResId != -1 )
         {
            setWidgetLayoutResource( widgetLayoutResId );
         }
      }
      
      array.recycle();
   }
   
   
   
   @Override
   protected final void onBindView( View view )
   {
      preferenceView = view;
      setupPreference( view );
      
      // This has to be called as last step, otherwise the summary
      // can not be set by derived classes.
      super.onBindView( view );
   }
   
   
   
   public AppContext getAppContext()
   {
      return appContext;
   }
   
   
   
   public ILog Log()
   {
      return appContext.Log();
   }
   
   
   
   public ISettingsService getSettings()
   {
      return appContext.getSettings();
   }
   
   
   
   @Override
   public void checkEnabled()
   {
   }
   
   
   
   @Override
   public void cleanUp()
   {
   }
   
   
   
   @Override
   public void setIcon( int iconResId )
   {
      if ( MolokoApp.isApiLevelSupported( Build.VERSION_CODES.HONEYCOMB ) )
      {
         super.setIcon( iconResId );
      }
      else
      {
         final ImageView widget = (ImageView) preferenceView.findViewById( R.id.moloko_prefs_widget_sync );
         widget.setImageResource( R.drawable.ic_prefs_refresh );
      }
   }
   
   
   
   protected void setupPreference( View view )
   {
   }
}
