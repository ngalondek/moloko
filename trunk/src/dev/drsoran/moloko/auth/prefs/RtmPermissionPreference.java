package dev.drsoran.moloko.auth.prefs;

import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.preference.ListPreference;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.View;
import dev.drsoran.moloko.R;


public class RtmPermissionPreference extends ListPreference implements
         OnSharedPreferenceChangeListener
{
   public final static String TAG = RtmPermissionPreference.class.getSimpleName();
   
   private boolean hasAuthToken = false;
   
   

   public RtmPermissionPreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );
   }
   


   public boolean hasAuthToken()
   {
      return hasAuthToken;
   }
   


   @Override
   protected void onBindView( View view )
   {
      final Resources resources = getContext().getResources();
      
      final SharedPreferences prefs = getPreferenceManager().getSharedPreferences();
      
      hasAuthToken = prefs != null;
      
      if ( hasAuthToken )
      {
         final String authToken = prefs.getString( resources.getString( R.string.key_authToken ),
                                                   null );
         
         hasAuthToken = authToken != null;
         
         if ( hasAuthToken )
         {
            final String permission = getPermissionEntry( resources,
                                                          prefs.getString( resources.getString( R.string.auth_pref_permission_key ),
                                                                           null ) );
            
            final long sinceDate = prefs.getLong( resources.getString( R.string.key_authToken_date ),
                                                  -1 );
            
            if ( sinceDate > -1 )
            {
               final java.text.DateFormat dateFormat = DateFormat.getDateFormat( getContext() );
               
               setSummary( resources.getString( R.string.auth_pref_perm_has_since,
                                                permission,
                                                dateFormat.format( new Date( sinceDate ) ) ) );
            }
            else
            {
               setSummary( resources.getString( R.string.auth_pref_perm_has,
                                                permission ) );
            }
         }
         
         // We tag the view if we have a auth token. So we can show a
         // context menu on long press in preferences list view.
         view.setTag( TAG );
      }
      
      if ( !hasAuthToken )
      {
         setSummary( R.string.auth_pref_perm_has_not );
      }
      
      prefs.registerOnSharedPreferenceChangeListener( this );
      
      super.onBindView( view );
   }
   


   public static final String getPermissionEntry( final Resources resources,
                                                  final String permValue )
   {
      String entry = null;
      
      final String[] permValues = resources.getStringArray( R.array.rtm_permissions_values );
      
      boolean found = false;
      
      int i = 0;
      
      for ( ; i < permValues.length && !found; )
      {
         found = permValue.equals( permValues[ i ] );
         
         if ( !found )
            ++i;
      }
      
      if ( found )
      {
         entry = resources.getStringArray( R.array.rtm_permissions )[ i ].toString();
      }
      
      return ( entry );
   }
   


   public void onSharedPreferenceChanged( SharedPreferences sharedPreferences,
                                          String key )
   {
      final Resources res = getContext().getResources();
      
      if ( key.equals( res.getString( R.string.key_authToken ) )
         || key.equals( res.getString( R.string.key_authToken_date ) )
         || key.equals( res.getString( R.string.auth_pref_permission_key ) ) )
      {
         notifyChanged();
      }
   }
}
