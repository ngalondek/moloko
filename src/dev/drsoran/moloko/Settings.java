package dev.drsoran.moloko;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
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
import android.text.TextUtils;
import android.text.format.DateFormat;
import dev.drsoran.moloko.content.RtmSettingsProviderPart;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.provider.Rtm.Tasks;
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
      


      boolean isDead()
      {
         return listener.get() == null;
      }
      


      boolean matches( int setting )
      {
         return ( ( mask & setting ) != 0 );
      }
      


      void notify( int setting, HashMap< Integer, Object > oldValues )
      {
         if ( listener.get() != null )
            listener.get().onSettingsChanged( setting, oldValues );
      }
      


      boolean notifyIfMatches( int setting, HashMap< Integer, Object > oldValues )
      {
         boolean ok = !isDead();
         
         if ( ok && matches( setting ) )
            listener.get().onSettingsChanged( setting, oldValues );
         
         return ok;
      }
   }
   
   public final static int DATEFORMAT_EU = 0;
   
   public final static int DATEFORMAT_US = 1;
   
   public final static int TIMEFORMAT_12 = 0;
   
   public final static int TIMEFORMAT_24 = 1;
   
   public final static String NO_DEFAULT_LIST_ID = Strings.EMPTY_STRING;
   
   public final static int STARTUP_VIEW_DEFAULT_LIST = 1 << 0;
   
   public final static int STARTUP_VIEW_LISTS = 1 << 1;
   
   public final static int STARTUP_VIEW_DEFAULT = STARTUP_VIEW_LISTS;
   
   public final static int TASK_SORT_PRIORITY = 1 << 0;
   
   public final static int TASK_SORT_DUE_DATE = 1 << 1;
   
   public final static int TASK_SORT_NAME = 1 << 2;
   
   public final static int TASK_SORT_DEFAULT = TASK_SORT_PRIORITY;
   
   // Values used for registerOnSettingsChangedListener
   
   public final static int SETTINGS_RTM_TIMEZONE = 1 << 0;
   
   public final static int SETTINGS_RTM_DATEFORMAT = 1 << 1;
   
   public final static int SETTINGS_RTM_TIMEFORMAT = 1 << 2;
   
   public final static int SETTINGS_RTM_DEFAULTLIST = 1 << 3;
   
   public final static int SETTINGS_RTM_LANGUAGE = 1 << 4;
   
   public final static int SETTINGS_TASK_SORT = 1 << 5;
   
   public final static int SETTINGS_ALL = Integer.MAX_VALUE;
   
   private final Context context;
   
   private final SharedPreferences preferences;
   
   // TODO: No check for double registration, no check for registration of same listener
   // with different mask. In these cases the listener gets notified multiple times.
   private final ArrayList< ListenerEntry > listeners = new ArrayList< ListenerEntry >();
   
   private RtmSettings rtmSettings;
   
   private TimeZone timeZone = TimeZone.getDefault();
   
   private int dateformat = DATEFORMAT_EU;
   
   private int timeformat = TIMEFORMAT_12;
   
   private String defaultListId = NO_DEFAULT_LIST_ID;
   
   private Locale locale = Locale.getDefault();
   
   private int startupView = STARTUP_VIEW_DEFAULT;
   
   private int taskSort = TASK_SORT_DEFAULT;
   
   

   public Settings( Context context, Handler handler )
   {
      this.context = context;
      
      preferences = PreferenceManager.getDefaultSharedPreferences( context );
      
      if ( preferences == null )
         throw new IllegalStateException( "SharedPreferences must not be null." );
      
      loadLocalSettings();
      
      loadRtmSettings( null );
      
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
                                             final HashMap< Integer, Object > oldValues = new HashMap< Integer, Object >();
                                             notifyListeners( loadRtmSettings( oldValues ),
                                                              oldValues );
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
      return timeZone;
   }
   


   public int getDateformat()
   {
      return dateformat;
   }
   


   public int getTimeformat()
   {
      return timeformat;
   }
   


   public String getDefaultListId()
   {
      return defaultListId;
   }
   


   public void setDefaultListId( String id )
   {
      if ( TextUtils.isEmpty( id ) )
         id = NO_DEFAULT_LIST_ID;
      
      defaultListId = persistSetting( context.getString( R.string.key_def_list_local ),
                                      context.getString( R.string.key_def_list_sync_with_rtm ),
                                      id,
                                      false );
   }
   


   public Locale getLocale()
   {
      return locale;
   }
   


   public RtmSettings getRtmSettings()
   {
      return rtmSettings;
   }
   


   public int getStartupView()
   {
      return startupView;
   }
   


   public void setStartupView( int value )
   {
      startupView = Integer.valueOf( persistSetting( context.getString( R.string.key_startup_view ),
                                                     null,
                                                     String.valueOf( value ),
                                                     false ) );
   }
   


   public int getTaskSort()
   {
      return taskSort;
   }
   


   public final static String resolveTaskSortToSqlite( int sortValue )
   {
      switch ( sortValue )
      {
         case TASK_SORT_PRIORITY:
            return Tasks.SORT_PRIORITY;
         case TASK_SORT_DUE_DATE:
            return Tasks.SORT_DUE_DATE;
         case TASK_SORT_NAME:
            return Tasks.SORT_TASK_NAME;
         default :
            return null;
      }
   }
   


   public void onSharedPreferenceChanged( SharedPreferences newValue, String key )
   {
      if ( key != null && newValue != null )
      {
         if ( key.equals( context.getString( R.string.key_timezone_local ) )
            || key.equals( context.getString( R.string.key_timezone_sync_with_rtm ) ) )
         {
            notifyListeners( SETTINGS_RTM_TIMEZONE, setTimezone() );
         }
         else if ( key.equals( context.getString( R.string.key_dateformat_local ) )
            || key.equals( context.getString( R.string.key_dateformat_sync_with_rtm ) ) )
         {
            notifyListeners( SETTINGS_RTM_DATEFORMAT, setDateformat() );
         }
         else if ( key.equals( context.getString( R.string.key_timeformat_local ) )
            || key.equals( context.getString( R.string.key_timeformat_sync_with_rtm ) ) )
         {
            notifyListeners( SETTINGS_RTM_TIMEFORMAT, setTimeformat() );
         }
         else if ( key.equals( context.getString( R.string.key_def_list_local ) )
            || key.equals( context.getString( R.string.key_def_list_sync_with_rtm ) ) )
         {
            notifyListeners( SETTINGS_RTM_DEFAULTLIST, setDefaultListId() );
         }
         else if ( key.equals( context.getString( R.string.key_lang_local ) )
            || key.equals( context.getString( R.string.key_lang_sync_with_rtm ) ) )
         {
            notifyListeners( SETTINGS_RTM_LANGUAGE, setLocale() );
         }
         else if ( key.equals( context.getString( R.string.key_task_sort ) ) )
         {
            notifyListeners( SETTINGS_TASK_SORT, setTaskSort() );
         }
      }
   }
   


   private TimeZone setTimezone()
   {
      TimeZone newTimeZone;
      
      final String keySync = context.getString( R.string.key_timezone_sync_with_rtm );
      final String key = context.getString( R.string.key_timezone_local );
      final boolean useRtm = useRtmSetting( keySync );
      
      if ( useRtm )
      {
         newTimeZone = null;
         
         if ( rtmSettings != null && rtmSettings.getTimezone() != null )
         {
            newTimeZone = TimeZone.getTimeZone( rtmSettings.getTimezone() );
         }
         else
         {
            newTimeZone = timeZone;
         }
      }
      else
      {
         newTimeZone = TimeZone.getTimeZone( preferences.getString( key,
                                                                    timeZone.getID() ) );
      }
      
      if ( !newTimeZone.getID().equals( timeZone ) )
      {
         final TimeZone oldTimeZone = timeZone;
         
         timeZone = newTimeZone;
         persistSetting( key, keySync, timeZone.getID(), useRtm );
         
         return oldTimeZone;
      }
      
      return null;
   }
   


   private Integer setDateformat()
   {
      int newDateformat;
      
      final String keySync = context.getString( R.string.key_dateformat_sync_with_rtm );
      final String key = context.getString( R.string.key_dateformat_local );
      final boolean useRtm = useRtmSetting( keySync );
      
      if ( useRtm )
      {
         if ( rtmSettings != null )
         {
            newDateformat = rtmSettings.getDateFormat();
         }
         else
         {
            final char[] order = DateFormat.getDateFormatOrder( context );
            
            newDateformat = ( order.length > 0 && order[ 0 ] == DateFormat.DATE )
                                                                                 ? DATEFORMAT_EU
                                                                                 : DATEFORMAT_US;
         }
      }
      else
      {
         newDateformat = Integer.valueOf( preferences.getString( key, "0" ) );
      }
      
      if ( newDateformat != dateformat )
      {
         final Integer oldDateFormat = dateformat;
         
         dateformat = newDateformat;
         persistSetting( key, keySync, String.valueOf( dateformat ), useRtm );
         
         return oldDateFormat;
      }
      
      return null;
   }
   


   private Integer setTimeformat()
   {
      int newTimeformat;
      
      final String keySync = context.getString( R.string.key_timeformat_sync_with_rtm );
      final String key = context.getString( R.string.key_timeformat_local );
      final boolean useRtm = useRtmSetting( keySync );
      
      if ( useRtm )
      {
         if ( rtmSettings != null )
         {
            newTimeformat = rtmSettings.getTimeFormat();
         }
         else
         {
            newTimeformat = ( DateFormat.is24HourFormat( context ) )
                                                                    ? TIMEFORMAT_24
                                                                    : TIMEFORMAT_12;
         }
      }
      else
      {
         newTimeformat = Integer.valueOf( preferences.getString( key, "0" ) );
      }
      
      if ( newTimeformat != timeformat )
      {
         final Integer oldTimeFormat = timeformat;
         
         timeformat = newTimeformat;
         persistSetting( key, keySync, String.valueOf( timeformat ), useRtm );
         
         return oldTimeFormat;
      }
      
      return null;
   }
   


   private String setDefaultListId()
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
         persistSetting( key, keySync, defaultListId, useRtm );
         
         return oldDefListId;
      }
      
      return null;
   }
   


   private Locale setLocale()
   {
      Locale newLocale;
      
      final String keySync = context.getString( R.string.key_lang_sync_with_rtm );
      final String key = context.getString( R.string.key_lang_local );
      final boolean useRtm = useRtmSetting( keySync );
      
      if ( useRtm )
      {
         newLocale = null;
         
         if ( rtmSettings != null && rtmSettings.getLanguage() != null )
         {
            newLocale = getLocaleFromLanguage( rtmSettings.getLanguage(),
                                               locale );
         }
         else
         {
            newLocale = locale;
         }
      }
      else
      {
         newLocale = getLocaleFromLanguage( preferences.getString( key,
                                                                   locale.getLanguage() ),
                                            locale );
      }
      
      if ( !newLocale.equals( locale ) )
      {
         final Locale oldLocale = locale;
         
         locale = newLocale;
         persistSetting( key, keySync, newLocale.getLanguage(), useRtm );
         
         return oldLocale;
      }
      
      return null;
   }
   


   private Integer setTaskSort()
   {
      final int newTaskSort = Integer.valueOf( preferences.getString( context.getString( R.string.key_task_sort ),
                                                                      String.valueOf( taskSort ) ) );
      
      if ( newTaskSort != taskSort )
      {
         final Integer oldTaskSort = taskSort;
         taskSort = newTaskSort;
         
         return oldTaskSort;
      }
      
      return null;
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
   


   private String persistSetting( String key,
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
      
      return value;
   }
   


   private String loadLocalValue( String key, String keySync, String defValue )
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
   


   private int loadLocalValue( String key, String keySync, int defValue )
   {
      return Integer.parseInt( loadLocalValue( key,
                                               keySync,
                                               String.valueOf( defValue ) ) );
   }
   


   private void loadLocalSettings()
   {
      timeZone = TimeZone.getTimeZone( loadLocalValue( context.getString( R.string.key_timezone_local ),
                                                       context.getString( R.string.key_timezone_sync_with_rtm ),
                                                       timeZone.getID() ) );
      dateformat = loadLocalValue( context.getString( R.string.key_dateformat_local ),
                                   context.getString( R.string.key_dateformat_sync_with_rtm ),
                                   dateformat );
      timeformat = loadLocalValue( context.getString( R.string.key_timeformat_local ),
                                   context.getString( R.string.key_timeformat_sync_with_rtm ),
                                   timeformat );
      defaultListId = loadLocalValue( context.getString( R.string.key_def_list_local ),
                                      context.getString( R.string.key_def_list_sync_with_rtm ),
                                      defaultListId );
      locale = getLocaleFromLanguage( loadLocalValue( context.getString( R.string.key_lang_local ),
                                                      context.getString( R.string.key_lang_sync_with_rtm ),
                                                      locale.getLanguage() ),
                                      null );
      startupView = loadLocalValue( context.getString( R.string.key_startup_view ),
                                    null,
                                    startupView );
      taskSort = loadLocalValue( context.getString( R.string.key_task_sort ),
                                 null,
                                 taskSort );
   }
   


   private int loadRtmSettings( HashMap< Integer, Object > oldValues )
   {
      int settingsChanged = 0;
      
      final ContentProviderClient client = context.getContentResolver()
                                                  .acquireContentProviderClient( dev.drsoran.provider.Rtm.Settings.CONTENT_URI );
      
      if ( client != null )
      {
         rtmSettings = RtmSettingsProviderPart.getSettings( client );
         
         client.release();
         
         settingsChanged |= addSettingIfChanged( oldValues,
                                                 setTimezone(),
                                                 SETTINGS_RTM_TIMEZONE );
         settingsChanged |= addSettingIfChanged( oldValues,
                                                 setDateformat(),
                                                 SETTINGS_RTM_DATEFORMAT );
         settingsChanged |= addSettingIfChanged( oldValues,
                                                 setTimeformat(),
                                                 SETTINGS_RTM_TIMEFORMAT );
         settingsChanged |= addSettingIfChanged( oldValues,
                                                 setDefaultListId(),
                                                 SETTINGS_RTM_DEFAULTLIST );
         settingsChanged |= addSettingIfChanged( oldValues,
                                                 setLocale(),
                                                 SETTINGS_RTM_LANGUAGE );
      }
      
      return settingsChanged;
   }
   


   private final static int addSettingIfChanged( HashMap< Integer, Object > oldValues,
                                                 Object oldValue,
                                                 int setting )
   {
      if ( oldValue != null )
      {
         if ( oldValues != null )
            oldValues.put( setting, oldValue );
         return setting;
      }
      else
         return 0;
   }
   


   private void notifyListeners( int setting, Object oldValue )
   {
      if ( setting > 0 && oldValue != null )
      {
         for ( Iterator< ListenerEntry > i = listeners.iterator(); i.hasNext(); )
         {
            ListenerEntry entry = i.next();
            
            // Check if we have a dead entry
            if ( entry.isDead() )
               i.remove();
            else if ( entry.matches( setting ) )
            {
               final HashMap< Integer, Object > oldValues = new HashMap< Integer, Object >( 1 );
               oldValues.put( setting, oldValue );
               entry.notify( setting, oldValues );
            }
         }
      }
   }
   


   private void notifyListeners( int mask, HashMap< Integer, Object > oldValues )
   {
      if ( mask > 0 && oldValues != null && oldValues.size() > 0 )
      {
         for ( Iterator< ListenerEntry > i = listeners.iterator(); i.hasNext(); )
         {
            ListenerEntry entry = i.next();
            
            // Check if we have a dead entry
            if ( !entry.notifyIfMatches( mask, oldValues ) )
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
   


   private static Locale getLocaleFromLanguage( String language, Locale defValue )
   {
      Locale locale = null;
      
      final Locale[] locales = Locale.getAvailableLocales();
      
      for ( int i = 0; i < locales.length && locale == null; i++ )
      {
         if ( locales[ i ].getLanguage().equals( language ) )
         {
            locale = locales[ i ];
         }
      }
      
      if ( locale == null )
         locale = defValue;
      
      return locale;
   }
}
