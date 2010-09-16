package dev.drsoran.moloko;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
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


public class Settings implements OnSharedPreferenceChangeListener
{
   private final static class ListenerEntry
   {
      public final int mask;
      
      public final WeakReference< IOnSettingsChangedListener > listener;
      
      

      public ListenerEntry( int setting, IOnSettingsChangedListener listener )
      {
         this.mask = setting;
         this.listener = new WeakReference< IOnSettingsChangedListener >( listener );
      }
      


      boolean notifyIfMatches( int setting )
      {
         boolean ok = listener.get() != null;
         
         if ( ok && ( ( mask & setting ) != 0 ) )
            listener.get().onSettingsChanged( setting );
         
         return ok;
      }
   }
   
   public final static int DATEFORMAT_EU = 0;
   
   public final static int DATEFORMAT_US = 1;
   
   public final static int TIMEFORMAT_12 = 0;
   
   public final static int TIMEFORMAT_24 = 1;
   
   public final static int SETTINGS_RTM_TIMEZONE = 1 << 0;
   
   public final static int SETTINGS_RTM_DATEFORMAT = 1 << 1;
   
   public final static int SETTINGS_RTM_TIMEFORMAT = 1 << 2;
   
   public final static int SETTINGS_RTM_DEFAULTLIST = 1 << 3;
   
   public final static int SETTINGS_ALL = Integer.MAX_VALUE;
   
   private final Context context;
   
   private final SharedPreferences preferences;
   
   // TODO: No check for double registration, no check for registration of same listener
   // with different mask. In these cases the listener gets notified multiple times.
   private final ArrayList< ListenerEntry > listeners = new ArrayList< ListenerEntry >();
   
   private RtmSettings rtmSettings;
   
   

   public Settings( Context context, Handler handler )
   {
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
                                       new ContentObserver( handler )
                                       {
                                          // This should be called from the main thread,
                                          // see handler parameter.
                                          @Override
                                          public void onChange( boolean selfChange )
                                          {
                                             loadRtmSettings();
                                          }
                                       } );
   }
   


   public void registerOnSettingsChangedListener( int which,
                                                  IOnSettingsChangedListener listener )
   {
      if ( listener != null )
      {
         listeners.add( new ListenerEntry( which, listener ) );
      }
   }
   


   public void unregisterOnSettingsChangedListener( IOnSettingsChangedListener listener )
   {
      if ( listener != null )
      {
         removeListener( listener );
      }
   }
   


   public TimeZone getTimezone()
   {
      return TimeZone.getTimeZone( preferences.getString( context.getString( R.string.key_timezone_local ),
                                                          TimeZone.getDefault().getID() ) );
   }
   


   public int getDateformat()
   {
      return Integer.parseInt( preferences.getString( context.getString( R.string.key_dateformat_local ),
                                                      "0" ) );
   }
   


   public int getTimeformat()
   {
      return Integer.parseInt( preferences.getString( context.getString( R.string.key_timeformat_local ),
                                                      "0" ) );
   }
   


   public String getDefaultListId()
   {
      return preferences.getString( context.getString( R.string.key_def_list_local ),
                                    null );
   }
   


   public Locale getLanguage()
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
   


   public void onSharedPreferenceChanged( SharedPreferences newValue, String key )
   {
      int settingsChanged = 0;
      
      if ( key.equals( context.getString( R.string.key_timezone_local ) ) )
      {
         settingsChanged = SETTINGS_RTM_TIMEZONE;
      }
      else if ( key.equals( context.getString( R.string.key_dateformat_local ) ) )
      {
         settingsChanged = SETTINGS_RTM_DATEFORMAT;
      }
      else if ( key.equals( context.getString( R.string.key_timeformat_local ) ) )
      {
         settingsChanged = SETTINGS_RTM_TIMEFORMAT;
      }
      else if ( key.equals( context.getString( R.string.key_def_list_local ) ) )
      {
         settingsChanged = SETTINGS_RTM_DEFAULTLIST;
      }
      else if ( key.equals( context.getString( R.string.key_timezone_sync_with_rtm ) ) )
      {
         if ( newValue.getBoolean( key, true ) )
         {
            setTimezone();
         }
      }
      else if ( key.equals( context.getString( R.string.key_dateformat_sync_with_rtm ) ) )
      {
         if ( newValue.getBoolean( key, true ) )
         {
            setDateformat();
         }
      }
      else if ( key.equals( context.getString( R.string.key_timeformat_sync_with_rtm ) ) )
      {
         if ( newValue.getBoolean( key, true ) )
         {
            setTimeformat();
         }
      }
      else if ( key.equals( context.getString( R.string.key_def_list_sync_with_rtm ) ) )
      {
         if ( newValue.getBoolean( key, true ) )
         {
            setDefaultListId();
         }
      }
      
      notifyListeners( settingsChanged );
   }
   


   private void setTimezone()
   {
      TimeZone timeZone = null;
      
      final String keySync = context.getString( R.string.key_timezone_sync_with_rtm );
      final String key = context.getString( R.string.key_timezone_local );
      final boolean useRtm = useRtmSetting( keySync );
      
      if ( useRtm )
      {
         timeZone = null;
         
         if ( rtmSettings != null && rtmSettings.getTimezone() != null )
         {
            timeZone = TimeZone.getTimeZone( rtmSettings.getTimezone() );
            persistSetting( key, keySync, timeZone.getID(), true );
         }
         else
         {
            timeZone = TimeZone.getDefault();
            persistSetting( key, keySync, timeZone.getID(), true );
         }
      }
      else
      {
         timeZone = getTimezone();
      }
      
      TimeZone.setDefault( timeZone );
   }
   


   private void setDateformat()
   {
      final String keySync = context.getString( R.string.key_dateformat_sync_with_rtm );
      final String key = context.getString( R.string.key_dateformat_local );
      final boolean useRtm = useRtmSetting( keySync );
    
      int dateformat = DATEFORMAT_EU;
      
      if ( useRtm )
      {
         if ( rtmSettings != null )
         {
            dateformat = rtmSettings.getDateFormat();
            persistSetting( key, keySync, String.valueOf( dateformat ), true );
         }
         else
         {
            final char[] order = DateFormat.getDateFormatOrder( context );
            
            dateformat = ( order.length > 0 && order[ 0 ] == DateFormat.DATE )
                                                                              ? DATEFORMAT_EU
                                                                              : DATEFORMAT_US;
            persistSetting( key, keySync, String.valueOf( dateformat ), true );
         }
      }
   }
   


   private void setTimeformat()
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
            persistSetting( key, keySync, String.valueOf( timeformat ), true );
         }
         else
         {
            timeformat = ( DateFormat.is24HourFormat( context ) )
                                                                 ? TIMEFORMAT_24
                                                                 : TIMEFORMAT_12;
            persistSetting( key, keySync, String.valueOf( timeformat ), true );
         }
      }
   }
   


   private void setDefaultListId()
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
            persistSetting( key, keySync, id, true );
         }
         else
         {
            persistSetting( key, keySync, null, true );
         }
      }
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
   


   private void persistSetting( String key,
                                String keySync,
                                String value,
                                boolean useRtm )
   {
      final Editor editor = preferences.edit();
      
      if ( !preferences.getString( key, "" ).equals( value ) )
      {
         editor.putString( key, value ).commit();
      }
      
      if ( preferences.getBoolean( keySync, true ) != useRtm )
      {
         editor.putBoolean( keySync, useRtm ).commit();
      }
   }
   


   private void loadRtmSettings()
   {
      final ContentProviderClient client = context.getContentResolver()
                                                  .acquireContentProviderClient( dev.drsoran.provider.Rtm.Settings.CONTENT_URI );
      
      if ( client != null )
      {
         rtmSettings = RtmSettingsProviderPart.getSettings( client );
         
         client.release();
         
         setTimezone();
         setDateformat();
         setTimeformat();
         setDefaultListId();
      }
   }
   


   private void notifyListeners( int mask )
   {
      if ( mask > 0 )
      {
         for ( Iterator< ListenerEntry > i = listeners.iterator(); i.hasNext(); )
         {
            ListenerEntry entry = i.next();
            
            // Check if we have a dead entry
            if ( !entry.notifyIfMatches( mask ) )
            {
               i.remove();
            }
         }
      }
   }
   


   private boolean removeListener( IOnSettingsChangedListener listener )
   {
      final int size = listeners.size();
      
      for ( int i = 0; i < size; i++ )
      {
         if ( listener == listeners.get( i ).listener )
         {
            listeners.remove( i );
            return true;
         }
      }
      
      return false;
   }
}
