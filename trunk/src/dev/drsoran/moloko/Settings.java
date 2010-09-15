package dev.drsoran.moloko;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.ContentObserver;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import dev.drsoran.moloko.content.RtmSettingsProviderPart;
import dev.drsoran.rtm.RtmSettings;


public class Settings extends ContentObserver implements
         OnSharedPreferenceChangeListener
{
   public int SRC_RTM = 0;
   
   public int SRC_LOCAL = 1;
   
   public int SRC_DEFAULT = 2;
   
   public int DATEFORMAT_EU = 0;
   
   public int DATEFORMAT_US = 1;
   
   public int TIMEFORMAT_12 = 0;
   
   public int TIMEFORMAT_24 = 1;
   
   private final Context context;
   
   private final SharedPreferences preferences;
   
   private RtmSettings rtmSettings;
   
   

   public Settings( Context context, Handler handler )
   {
      super( handler );
      
      this.context = context;
      
      preferences = PreferenceManager.getDefaultSharedPreferences( context );
      
      if ( preferences == null )
         throw new IllegalStateException( "SharedPreferences must not be null." );
      
      loadRtmSettings();
      
      // Register after loadRtmSettings() otherwise we would
      // see our own change.
      preferences.registerOnSharedPreferenceChangeListener( this );
      
      // Watch for settings changes from background sync
      context.getContentResolver()
             .registerContentObserver( dev.drsoran.provider.Rtm.Settings.CONTENT_URI,
                                       true,
                                       this );
   }
   


   synchronized public TimeZone getTimezone()
   {
      TimeZone timeZone = TimeZone.getTimeZone( preferences.getString( context.getString( R.string.key_timezone_local ),
                                                                       "" ) );
      
      if ( timeZone == null )
      {
         timeZone = Calendar.getInstance().getTimeZone();
      }
      
      return timeZone;
   }
   


   synchronized public int getDateformat()
   {
      return Integer.parseInt( preferences.getString( context.getString( R.string.key_dateformat_local ),
                                                      "0" ) );
   }
   


   synchronized public int getTimeformat()
   {
      return Integer.parseInt( preferences.getString( context.getString( R.string.key_timeformat_local ),
                                                      "0" ) );
   }
   


   synchronized public String getDefaultListId()
   {
      return preferences.getString( context.getString( R.string.key_def_list_local ),
                                    null );
   }
   


   synchronized public Locale getLanguage()
   {
      Locale language = null;
      
      if ( rtmSettings != null && rtmSettings.getLanguage() != null )
      {
         final String rtmLang = rtmSettings.getLanguage();
         final Locale[] locales = Locale.getAvailableLocales();
         
         for ( int i = 0; i < locales.length && language == null; i++ )
         {
            if ( locales[ i ].getLanguage().equals( rtmLang ) )
            {
               language = locales[ i ];
            }
         }
      }
      
      if ( language == null )
      {
         language = Locale.getDefault();
      }
      
      return language;
   }
   


   public RtmSettings getRtmSettings()
   {
      return rtmSettings;
   }
   


   @Override
   public void onChange( boolean selfChange )
   {
      if ( !selfChange )
      {
         final boolean newSettings = loadRtmSettings();
         
         // TODO: Notify settings changed
      }
      
      super.onChange( selfChange );
   }
   


   public void onSharedPreferenceChanged( SharedPreferences newValue,
                                          String key )
   {
      boolean notifyChanged = false;
      
      if ( key.equals( context.getString( R.string.key_timezone_local ) )
         || key.equals( context.getString( R.string.key_dateformat_local ) )
         || key.equals( context.getString( R.string.key_timeformat_local ) )
         || key.equals( context.getString( R.string.key_def_list_local ) ) )
      {
         notifyChanged = !newValue.getString( key, null )
                                           .equals( preferences.getString( key,
                                                                           null ) );
      }
      else if ( key.equals( context.getString( R.string.key_timezone_sync_with_rtm ) ) )
      {
         if ( newValue.getBoolean( key, true ) )
         {
            notifyChanged = setTimezone();
         }
      }
      else if ( key.equals( context.getString( R.string.key_dateformat_sync_with_rtm ) ) )
      {
         if ( newValue.getBoolean( key, true ) )
         {
            notifyChanged = setDateformat();
         }
      }
      else if ( key.equals( context.getString( R.string.key_timeformat_sync_with_rtm ) ) )
      {
         if ( newValue.getBoolean( key, true ) )
         {
            notifyChanged = setTimeformat();
         }
      }
      else if ( key.equals( context.getString( R.string.key_def_list_sync_with_rtm ) ) )
      {
         if ( newValue.getBoolean( key, true ) )
         {
            notifyChanged = setDefaultListId();
         }
      }
      
      if ( notifyChanged )
      {
         // TODO: Notify changed
      }
   }
   


   private boolean setTimezone()
   {
      final String keySync = context.getString( R.string.key_timezone_sync_with_rtm );
      final String key = context.getString( R.string.key_timezone_local );
      final boolean useRtm = useRtmSetting( keySync );
      
      if ( useRtm )
      {
         TimeZone timeZone = null;
         
         if ( rtmSettings != null && rtmSettings.getTimezone() != null )
         {
            timeZone = TimeZone.getTimeZone( rtmSettings.getTimezone() );
            return persistSetting( key, keySync, timeZone.getID(), true );
         }
         else
         {
            timeZone = Calendar.getInstance().getTimeZone();
            return persistSetting( key, keySync, timeZone.getID(), true );
         }
      }
      
      return false;
   }
   


   private boolean setDateformat()
   {
      final String keySync = context.getString( R.string.key_dateformat_sync_with_rtm );
      final String key = context.getString( R.string.key_dateformat_local );
      final boolean useRtm = useRtmSetting( keySync );
      
      if ( useRtm )
      {
         int dateformat;
         
         if ( rtmSettings != null )
         {
            dateformat = rtmSettings.getDateFormat();
            return persistSetting( key,
                                   keySync,
                                   String.valueOf( dateformat ),
                                   true );
         }
         else
         {
            final char[] order = DateFormat.getDateFormatOrder( context );
            
            dateformat = ( order.length > 0 && order[ 0 ] == DateFormat.DATE )
                                                                              ? DATEFORMAT_EU
                                                                              : DATEFORMAT_US;
            return persistSetting( key,
                                   keySync,
                                   String.valueOf( dateformat ),
                                   true );
         }
      }
      
      return false;
   }
   


   private boolean setTimeformat()
   {
      final String keySync = context.getString( R.string.key_timeformat_sync_with_rtm );
      final String key = context.getString( R.string.key_timeformat_local );
      final boolean useRtm = useRtmSetting( keySync );
      
      if ( useRtm )
      {
         int timeformat;
         
         if ( rtmSettings != null )
         {
            timeformat = rtmSettings.getTimeFormat();
            return persistSetting( key,
                                   keySync,
                                   String.valueOf( timeformat ),
                                   true );
         }
         else
         {
            timeformat = ( DateFormat.is24HourFormat( context ) )
                                                                 ? TIMEFORMAT_24
                                                                 : TIMEFORMAT_12;
            return persistSetting( key,
                                   keySync,
                                   String.valueOf( timeformat ),
                                   true );
         }
      }
      
      return false;
   }
   


   private boolean setDefaultListId()
   {
      final String keySync = context.getString( R.string.key_def_list_sync_with_rtm );
      final String key = context.getString( R.string.key_def_list_local );
      final boolean useRtm = useRtmSetting( keySync );
      
      if ( useRtm )
      {
         String id;
         
         if ( rtmSettings != null && rtmSettings.getDefaultListId() != null )
         {
            id = rtmSettings.getDefaultListId();
            return persistSetting( key, keySync, id, true );
         }
         else
         {
            return persistSetting( key, keySync, null, true );
         }
      }
      
      return false;
   }
   


   private boolean useRtmSetting( String key )
   {
      if ( !preferences.contains( key ) )
      {
         preferences.edit()
                    .putBoolean( context.getString( R.string.key_timezone_sync_with_rtm ),
                                 true )
                    .commit();
         return true;
      }
      else
      {
         return preferences.getBoolean( key, true );
      }
   }
   


   private boolean persistSetting( String key,
                                   String keySync,
                                   String value,
                                   boolean useRtm )
   {
      boolean changed = false;
      
      final Editor editor = preferences.edit();
      
      if ( !preferences.getString( key, "" ).equals( value ) )
      {
         editor.putString( key, value ).commit();
         changed = true;
      }
      
      if ( preferences.getBoolean( keySync, true ) != useRtm )
      {
         editor.putBoolean( keySync, useRtm ).commit();
         changed = true;
      }
      
      return changed;
   }
   


   synchronized private boolean loadRtmSettings()
   {
      final ContentProviderClient client = context.getContentResolver()
                                                  .acquireContentProviderClient( dev.drsoran.provider.Rtm.Settings.CONTENT_URI );
      
      if ( client != null )
      {
         rtmSettings = RtmSettingsProviderPart.getSettings( client );
         
         setTimezone();
         setDateformat();
         setTimeformat();
         setDefaultListId();
         
         return true;
      }
      
      return false;
   }
}
