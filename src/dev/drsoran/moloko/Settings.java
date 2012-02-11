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

package dev.drsoran.moloko;

import java.util.Locale;

import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import dev.drsoran.moloko.content.RtmSettingsProviderPart;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.rtm.RtmSettings;


public class Settings
{
   public final static String NO_DEFAULT_LIST_ID = Strings.EMPTY_STRING;
   
   public final static int STARTUP_VIEW_DEFAULT_LIST = 1 << 0;
   
   public final static int STARTUP_VIEW_LISTS = 1 << 1;
   
   public final static int STARTUP_VIEW_HOME = 1 << 2;
   
   public final static int STARTUP_VIEW_DEFAULT = STARTUP_VIEW_HOME;
   
   public final static int TASK_SORT_PRIORITY = 1 << 0;
   
   public final static int TASK_SORT_DUE_DATE = 1 << 1;
   
   public final static int TASK_SORT_NAME = 1 << 2;
   
   public final static int TASK_SORT_DEFAULT = TASK_SORT_PRIORITY;
   
   private final Context context;
   
   private final SharedPreferences preferences;
   
   private RtmSettings rtmSettings;
   
   
   
   public Settings( Context context )
   {
      this.context = context;
      
      preferences = PreferenceManager.getDefaultSharedPreferences( context );
      if ( preferences == null )
      {
         throw new IllegalStateException( "SharedPreferences must not be null." );
      }
      
      loadRtmSettings();
   }
   
   
   
   public String getDateformat()
   {
      return android.provider.Settings.System.getString( context.getContentResolver(),
                                                         android.provider.Settings.System.DATE_FORMAT );
   }
   
   
   
   public boolean Is24hTimeformat()
   {
      return android.text.format.DateFormat.is24HourFormat( context );
   }
   
   
   
   public String getDefaultListId()
   {
      return loadString( context.getString( R.string.key_def_list_local ),
                         context.getString( R.string.key_def_list_sync_with_rtm ),
                         NO_DEFAULT_LIST_ID );
   }
   
   
   
   public void setDefaultListId( String id )
   {
      if ( TextUtils.isEmpty( id ) )
      {
         id = NO_DEFAULT_LIST_ID;
      }
      
      storeString( context.getString( R.string.key_def_list_local ),
                   context.getString( R.string.key_def_list_sync_with_rtm ),
                   id,
                   false );
   }
   
   
   
   public Locale getLocale()
   {
      return Locale.getDefault();
   }
   
   
   
   public RtmSettings getRtmSettings()
   {
      return rtmSettings;
   }
   
   
   
   public int getStartupView()
   {
      return loadInt( context.getString( R.string.key_startup_view ),
                      null,
                      STARTUP_VIEW_DEFAULT );
   }
   
   
   
   public void setStartupView( int value )
   {
      storeInt( context.getString( R.string.key_startup_view ), value );
   }
   
   
   
   public int getTaskSort()
   {
      return loadInt( context.getString( R.string.key_task_sort ),
                      null,
                      TASK_SORT_DEFAULT );
   }
   
   
   
   public void setTaskSort( int taskSort )
   {
      storeInt( context.getString( R.string.key_task_sort ), taskSort );
   }
   
   
   
   @Override
   public void onSharedPreferenceChanged( SharedPreferences newValue, String key )
   {
      if ( key != null && newValue != null )
      {
         if ( key.equals( context.getString( R.string.key_def_list_local ) )
            || key.equals( context.getString( R.string.key_def_list_sync_with_rtm ) ) )
         {
            setDefaultListId();
         }
      }
   }
   
   
   
   private void setDefaultListId()
   {
      String newListId;
      
      final String keySync = context.getString( R.string.key_def_list_sync_with_rtm );
      final String key = context.getString( R.string.key_def_list_local );
      final boolean useRtm = useRtmSetting( keySync );
      
      if ( useRtm )
      {
         if ( rtmSettings != null && rtmSettings.getDefaultListId() != null )
         {
            newListId = rtmSettings.getDefaultListId();
         }
         else
         {
            newListId = NO_DEFAULT_LIST_ID;
         }
      }
      else
      {
         newListId = preferences.getString( key, defaultListId );
      }
      
      if ( !defaultListId.equals( newListId ) )
      {
         final String oldDefListId = defaultListId;
         
         defaultListId = newListId;
         storeString( key, keySync, defaultListId, useRtm );
         
         return oldDefListId;
      }
   }
   
   
   
   private boolean useRtmSetting( String key )
   {
      if ( !preferences.contains( key ) )
      {
         preferences.edit().putBoolean( key, true ).commit();
         return true;
      }
      else
      {
         return preferences.getBoolean( key, true );
      }
   }
   
   
   
   private String loadString( String key, String keySync, String defValue )
   {
      String value;
      
      final Editor editor = preferences.edit();
      
      if ( !preferences.contains( key ) )
      {
         editor.putString( key, defValue ).commit();
         value = defValue;
      }
      else
      {
         value = preferences.getString( key, defValue );
      }
      
      if ( keySync != null && !preferences.contains( keySync ) )
      {
         editor.putBoolean( keySync, true ).commit();
      }
      
      return value;
   }
   
   
   
   private void storeString( String key,
                             String keySync,
                             String value,
                             boolean useRtm )
   {
      final Editor editor = preferences.edit();
      
      if ( !preferences.getString( key, Strings.EMPTY_STRING ).equals( value ) )
      {
         editor.putString( key, value ).commit();
      }
      
      if ( preferences.getBoolean( keySync, true ) != useRtm )
      {
         editor.putBoolean( keySync, useRtm ).commit();
      }
   }
   
   
   
   private int loadInt( String key, String keySync, int defValue )
   {
      return Integer.parseInt( loadString( key,
                                           keySync,
                                           String.valueOf( defValue ) ) );
   }
   
   
   
   public void storeInt( String key, int value )
   {
      storeString( key, null, String.valueOf( value ), false );
   }
   
   
   
   private void loadRtmSettings()
   {
      // TODO: Load the settings form DB in a background task
      final ContentProviderClient client = context.getContentResolver()
                                                  .acquireContentProviderClient( dev.drsoran.provider.Rtm.Settings.CONTENT_URI );
      
      if ( client != null )
      {
         rtmSettings = RtmSettingsProviderPart.getSettings( client );
         client.release();
      }
   }
}
