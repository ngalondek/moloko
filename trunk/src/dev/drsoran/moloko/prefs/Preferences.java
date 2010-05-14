package dev.drsoran.moloko.prefs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.login.Login;


public class Preferences extends PreferenceActivity implements
   Preference.OnPreferenceChangeListener
{
   
   @SuppressWarnings( "unused" )
   private final static String TAG = Preferences.class.getSimpleName();
   
   private String newPermissionRequest = null;
   
   private String loginLastError = null;
   
   
   private final class DlgType
   {
      public static final int GET_AUTH_TOKEN = 0;
      
      public static final int ERROR = 1;
      
      public static final int FINISHED = 3;
   }
   
   private int activeDlgId = -1;
   
   

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      addPreferencesFromResource( R.xml.preferences );
      
      ListPreference permPref = getPermissionList();
      
      if ( permPref != null )
      {
         permPref.setOnPreferenceChangeListener( this );
      }
      
      registerForContextMenu( getListView() );
   }
   


   @Override
   public boolean onContextItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case R.id.prefs_ctxmenu_perm_check_auth_token:
            
            return true;
         default :
            return super.onContextItemSelected( item );
      }
   }
   


   @Override
   public void onCreateContextMenu( ContextMenu menu,
                                    View v,
                                    ContextMenuInfo menuInfo )
   {
      if ( menuInfo instanceof AdapterView.AdapterContextMenuInfo )
      {
         final Object tag =
                  ( (AdapterView.AdapterContextMenuInfo) menuInfo ).targetView
                     .getTag();
         
         if ( tag instanceof String )
         {
            final String tagStr = (String) tag;
            
            // Check if the view has the right tag
            if ( tagStr.equals( RtmPermissionPreference.TAG ) )
            {
               final RtmPermissionPreference permissionPreference =
                        getPermissionList();
               
               // Only if we have a token we can validate it
               if ( permissionPreference.hasAuthToken() )
               {
                  final MenuInflater inflater = getMenuInflater();
                  inflater.inflate( R.menu.prefs_ctxmenu_perm, menu );
                  return;
               }
            }
         }
      }
      
      super.onCreateContextMenu( menu, v, menuInfo );
   }
   


   @Override
   protected Dialog onCreateDialog( int id )
   {
      Dialog dialog = null;
      
      if ( id == DlgType.GET_AUTH_TOKEN )
      {
         ProgressDialog dlg = new ProgressDialog( Preferences.this );
         dlg.setProgressStyle( ProgressDialog.STYLE_SPINNER );
         dlg.setTitle( getString( R.string.app_login ) );
         dlg
            .setMessage( new StringBuffer( getString( R.string.pref_dlg_get_auth_token ) )
               .append( " '" )
                  .append( newPermissionRequest )
                  .append( "'" )
                  .toString() );
         
         dialog = dlg;
      }
      else
      {
         AlertDialog.Builder builder = new AlertDialog.Builder( this );
         builder
            .setTitle( getString( R.string.app_login ) )
               .setMessage( "If you can read this RUN!" );
         
         switch ( id )
         {
            case DlgType.ERROR:
               builder
                  .setMessage( new StringBuffer( getString( R.string.pref_dlg_error ) )
                     .append( " " )
                        .append( loginLastError )
                        .toString() );
               break;
            
            case DlgType.FINISHED:
               builder
                  .setMessage( new StringBuffer( getString( R.string.pref_dlg_finsihed ) )
                     .append( " '" )
                        .append( newPermissionRequest )
                        .append( "'" )
                        .toString() );
               break;
            
            default :
               break;
         }
         
         dialog = builder.create();
      }
      
      if ( dialog != null )
      {
         activeDlgId = id;
      }
      
      return dialog;
   }
   


   private void activateDialog( int id )
   {
      if ( activeDlgId != -1 )
      {
         removeDialog( activeDlgId );
      }
      
      showDialog( id );
   }
   


   @Override
   protected void onActivityResult( int requestCode, int resultCode, Intent data )
   {
      if ( requestCode == Login.ReqType.AUTHENTICATE )
      {
         loginLastError = null;
         
         if ( data != null )
         {
            loginLastError =
                     data.getStringExtra( getString( R.string.key_lastError ) );
         }
         
         if ( loginLastError == null )
         {
            loginLastError = getString( Login.toResourceId( resultCode ) );
         }
         
         switch ( resultCode )
         {
            case Login.ReturnCode.SUCCESS:

               if ( commitNewPermissionLevel( data ) )
               {
                  activateDialog( DlgType.FINISHED );
               }
               else
               {
                  activateDialog( DlgType.ERROR );
               }
               break;
            case Login.ReturnCode.CANCELED:
            case Login.ReturnCode.ERROR:
               clearAuthToken();
               activateDialog( DlgType.ERROR );
               break;
            default :
               break;
         }
      }
   }
   


   private void clearAuthToken()
   {
      final String permissionLevelNone =
               getResources().getStringArray( R.array.rtm_permissions_values )[ 0 ];
      
      // Set new selected permission level
      SharedPreferences.Editor prefsEditor =
               PreferenceManager.getDefaultSharedPreferences( this ).edit();
      
      // set permission to nothing
      prefsEditor.putString( getString( R.string.pref_permission_key ),
                             permissionLevelNone );
      
      // remove auth token
      prefsEditor.remove( getString( R.string.key_authToken ) );
      
      // remove auth token date
      prefsEditor.remove( getString( R.string.key_authToken_date ) );
      
      // store everything
      if ( prefsEditor.commit() )
      {
         getPermissionList().setValue( permissionLevelNone );
      }
   }
   


   private boolean commitNewPermissionLevel( final Intent data )
   {
      boolean ok = data != null;
      
      if ( ok )
      {
         final String authToken =
                  data.getStringExtra( getString( R.string.key_authToken ) );
         
         final String permissionLevel =
                  data
                     .getStringExtra( getString( R.string.pref_permission_key ) );
         
         ok = authToken != null;
         ok = ok && permissionLevel != null;
         
         if ( ok )
         {
            // Set new selected permission level
            SharedPreferences.Editor prefsEditor =
                     PreferenceManager
                        .getDefaultSharedPreferences( this )
                           .edit();
            
            prefsEditor.putString( getString( R.string.pref_permission_key ),
                                   permissionLevel );
            
            // set auth token
            prefsEditor.putString( getString( R.string.key_authToken ),
                                   authToken );
            
            // set auth token date
            prefsEditor.putLong( getString( R.string.key_authToken_date ),
                                 System.currentTimeMillis() );
            
            // store everything
            ok = prefsEditor.commit();
            
            if ( ok )
            {
               getPermissionList().setValue( permissionLevel );
            }
            else
            {
               loginLastError = getString( R.string.err_saving_preferences );
            }
         }
         else
         {
            loginLastError = getString( R.string.login_err_invalid_auth_token );
         }
      }
      else
      {
         loginLastError = getString( R.string.login_err_invalid_auth_token );
      }
      
      return ok;
   }
   


   public boolean onPreferenceChange( Preference preference, Object newValue )
   {
      boolean takeIt = true;
      
      // Here we drop the new value cause we have to retrieve the new
      // auth code from RTM for the permission.
      if ( newValue instanceof String )
      {
         final String newPermissionValue = (String) newValue;
         
         // If the user decided to allow nothing we simply take it. This
         // needs no check.
         if ( newPermissionValue.equals( getResources()
            .getStringArray( R.array.rtm_permissions_values )[ 0 ] ) )
         {
            clearAuthToken();
            takeIt = true;
         }
         else
         {
            takeIt = false;
            
            final ListPreference permissionList = getPermissionList();
            
            if ( permissionList != null )
            {
               final int idx =
                        permissionList.findIndexOfValue( newPermissionValue );
               
               if ( idx > -1 )
               {
                  newPermissionRequest =
                           ( permissionList.getEntries() )[ idx ].toString();
               }
            }
            
            // Check and authenticate the user. The result stores the new
            // value if successful.
            final Intent intent = new Intent( this, Login.class );
            intent.putExtra( getString( R.string.pref_permission_key ),
                             newPermissionValue );
            
            startActivityForResult( intent, Login.ReqType.AUTHENTICATE );
            
            activateDialog( DlgType.GET_AUTH_TOKEN );
         }
      }
      
      return takeIt;
   }
   


   private RtmPermissionPreference getPermissionList()
   {
      RtmPermissionPreference listPreference = null;
      
      Preference permPref =
               findPreference( getString( R.string.pref_permission_key ) );
      
      if ( permPref instanceof ListPreference )
      {
         listPreference = (RtmPermissionPreference) permPref;
      }
      
      return listPreference;
   }
   
}
