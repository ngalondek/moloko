package dev.drsoran.moloko.login;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;

import com.mdt.rtm.ApplicationInfo;
import com.mdt.rtm.data.RtmAuth;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.service.IRtmService;
import dev.drsoran.moloko.service.IRtmServiceCallback;
import dev.drsoran.moloko.service.RtmServiceConstants;
import dev.drsoran.moloko.service.parcel.ParcelableApplicationInfo;


public class Login extends Activity
{
   private final static String TAG = Login.class.getSimpleName();
   
   
   public final class ReqType
   {
      public static final int AUTHENTICATE = 0;
   }
   

   public final class ReturnCode
   {
      public static final int SUCCESS = 0;
      
      public static final int CANCELED = 1;
      
      public static final int ERROR = 2;
   }
   
   

   public final static int toResourceId( int code )
   {
      switch ( code )
      {
         case ReturnCode.SUCCESS:
            return R.string.login_ret_success;
            
         case ReturnCode.CANCELED:
            return R.string.login_ret_canceled;
            
         case ReturnCode.ERROR:
            return R.string.login_ret_error;
            
         default :
            return -1;
      }
   }
   
   
   private final class MsgType
   {
      public static final int SVC_STATUS_UPDATE = 0;
      
      public static final int FINISH_AUTHENTICATE = 1;
      
      public static final int CANCEL = 2;
      
      public static final int ERROR_RTM_WEB_ACCESS = 3;
      
      public static final int SERVICE_DISCONNECTED = 4;
   }
   
   private String authToken = null;
   
   private String lastError = null;
   
   private int serviceStatus = RtmServiceConstants.ServiceState.DISCONNECTED;
   
   private int loginStatus = ReturnCode.SUCCESS;
   
   private IRtmService rtmService = null;
   
   private boolean runningRtmWebAccess = false;
   
   private IRtmServiceCallback.Stub rtmServiceCallback =
            new IRtmServiceCallback.Stub()
            {
               
               public void onStatusUpdate( int status ) throws RemoteException
               {
                  if ( ( status & ( RtmServiceConstants.CLASS_AUTH | RtmServiceConstants.CLASS_SERVICE ) ) != 0 )
                  {
                     updateServiceStatus( status );
                  }
               }
            };
   
   private ServiceConnection serviceConnection = new ServiceConnection()
   {
      public void onServiceConnected( ComponentName name, IBinder service )
      {
         rtmService = IRtmService.Stub.asInterface( service );
         updateServiceStatus( RtmServiceConstants.ServiceState.CONNECTED );
         
         try
         {
            rtmService.registerCallback( rtmServiceCallback );
         }
         catch ( RemoteException e )
         {
            // In this case the service has crashed before we could even
            // do anything with it; we can count on soon being
            // disconnected (and then reconnected if it can be restarted)
            // so there is no need to do anything here.
         }
      }
      


      public void onServiceDisconnected( ComponentName name )
      {
         // This is called when the connection with the service has been
         // unexpectedly disconnected -- that is, its process crashed.
         rtmService = null;
         updateServiceStatus( RtmServiceConstants.ServiceState.DISCONNECTED );
      }
   };
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      // Establish a couple connections with the service, binding
      // by interface names. This allows other applications to be
      // installed that replace the remote service by implementing
      // the same interface.
      bindService( new Intent( IRtmService.class.getName() ),
                   serviceConnection,
                   Context.BIND_AUTO_CREATE );
      
      super.onCreate( savedInstanceState );
   }
   


   @Override
   public boolean onKeyDown( int keyCode, KeyEvent event )
   {
      if ( ( keyCode == KeyEvent.KEYCODE_BACK ) )
      {
         cancelLoginAsync();
         return true;
      }
      else
      {
         return super.onKeyDown( keyCode, event );
      }
   }
   


   @Override
   protected void onDestroy()
   {
      super.onDestroy();
      
      if ( runningRtmWebAccess )
      {
         finishActivity( RtmWebAccess.ReqType.OPEN_URL );
      }
      
      if ( loginStatus == ReturnCode.SUCCESS )
         disconnectService();
   }
   


   private void disconnectService()
   {
      if ( rtmService != null && serviceStatus > RtmServiceConstants.ServiceState.CONNECTED )
      {
         try
         {
            rtmService.unregisterCallback( rtmServiceCallback );
         }
         catch ( RemoteException e )
         {
            // There is nothing special we need to do if the service
            // has crashed.
         }
         
         unbindService( serviceConnection );
         serviceConnection = null;
         rtmService = null;
         
         evtHandler.sendEmptyMessage( MsgType.SERVICE_DISCONNECTED );
      }
   }
   


   private void cancelLoginAsync()
   {
      evtHandler.sendEmptyMessage( MsgType.CANCEL );
   }
   


   private void cancelLogin()
   {
      Log.d( TAG, "Login canceled." );
      disconnectService();
   }
   


   private void updateServiceStatus( int status )
   {
      evtHandler.sendMessage( evtHandler
         .obtainMessage( MsgType.SVC_STATUS_UPDATE, status, 0 ) );
   }
   


   @Override
   protected void onActivityResult( int requestCode, int resultCode, Intent data )
   {
      switch ( requestCode )
      {
         case RtmWebAccess.ReqType.OPEN_URL:
            runningRtmWebAccess = false;
            
            switch ( resultCode )
            {
               case RtmWebAccess.ReturnCode.SUCCESS:
                  evtHandler.sendEmptyMessage( MsgType.FINISH_AUTHENTICATE );
                  break;
               default :
                  evtHandler.sendEmptyMessage( MsgType.ERROR_RTM_WEB_ACCESS );
                  break;
            }
            break;
         
         default :
            super.onActivityResult( requestCode, resultCode, data );
            break;
      }
   }
   


   private void returnWithCode( int code )
   {
      if ( lastError != null )
      {
         setResult( code, getIntent()
            .putExtra( getString( R.string.key_lastError ), lastError ) );
      }
      else
      {
         setResult( code );
      }
      
      finish();
   }
   


   private void returnWithAuthKey()
   {
      setResult( ReturnCode.SUCCESS, getIntent()
         .putExtra( getString( R.string.key_authToken ), authToken ) );
      finish();
   }
   


   private ApplicationInfo getRtmApplicationInfo()
   {
      ApplicationInfo applicationInfo = null;
      
      final SharedPreferences prefs =
               PreferenceManager.getDefaultSharedPreferences( this );
      
      if ( prefs != null )
      {
         applicationInfo =
                  new ApplicationInfo( prefs
                                          .getString( getString( R.string.pref_api_key_key ),
                                                      null ),
                                       prefs
                                          .getString( getString( R.string.pref_api_shared_secret_key ),
                                                      null ),
                                       prefs
                                          .getString( getString( R.string.pref_login_username_key ),
                                                      null ),
                                       prefs
                                          .getString( getString( R.string.pref_login_pass_key ),
                                                      null ) );
      }
      
      return applicationInfo;
   }
   


   private String getLastServiceError()
   {
      StringBuffer error = new StringBuffer();
      
      if ( rtmService != null )
      {
         try
         {
            error
               .append( rtmService.getLastErrorDescription() )
                  .append( "; Code: " )
                  .append( rtmService.getLastErrorCode() );
         }
         catch ( RemoteException e )
         {
         }
      }
      
      return error.toString();
   }
   
   private Handler evtHandler = new Handler()
   {
      @Override
      public void handleMessage( Message msg )
      {
         try
         {
            switch ( msg.what )
            {
               case MsgType.SVC_STATUS_UPDATE:
                  switch ( msg.arg1 )
                  {
                     case RtmServiceConstants.ServiceState.CONNECTED:
                        final String permMode =
                                 getIntent()
                                    .getStringExtra( getString( R.string.pref_permission_key ) );
                        
                        if ( permMode == null )
                        {
                           Log
                              .e( TAG,
                                  "Missing Intent extra parameter 'R.string.pref_permission_key'" );
                        }
                        else
                        {
                           rtmService
                              .beginAuthorization( new ParcelableApplicationInfo( getRtmApplicationInfo(),
                                                                                  RtmAuth.Perms
                                                                                     .valueOf( permMode ) ) );
                        }
                        
                        break;
                     
                     case RtmServiceConstants.AuthState.GET_LOGIN_URL_FIN:
                        final Intent intent =
                                 new Intent( android.content.Intent.ACTION_VIEW,
                                             Uri.parse( rtmService
                                                .getLoginUrl() ),
                                             Login.this,
                                             RtmWebAccess.class );
                        startActivityForResult( intent,
                                                RtmWebAccess.ReqType.OPEN_URL );
                        
                        runningRtmWebAccess = true;
                        break;
                     
                     case RtmServiceConstants.AuthState.GET_TOKEN_FIN:
                        authToken = rtmService.getAuthToken();
                        
                        if ( authToken == null )
                        {
                           lastError =
                                    getString( R.string.login_err_invalid_auth_token );
                           loginStatus = ReturnCode.ERROR;
                        }
                        else
                        {
                           loginStatus = ReturnCode.SUCCESS;
                        }
                        disconnectService();
                        break;
                     
                     case RtmServiceConstants.AuthState.FAILED:
                     case RtmServiceConstants.ServiceState.DISCONNECTED:
                     case RtmServiceConstants.ServiceState.INTERNAL_ERROR:
                        final String serviceError = getLastServiceError();
                        
                        if ( serviceError != null )
                        {
                           lastError =
                                    new StringBuffer()
                                       .append( "RTM: " )
                                          .append( serviceError )
                                          .toString();
                           Log.e( TAG, lastError );
                        }
                        else
                        {
                           lastError = null;
                           Log.e( TAG, "Unknown error." );
                        }
                        
                        loginStatus = ReturnCode.ERROR;
                        disconnectService();
                        break;
                     
                     default :
                        break;
                  }
                  
                  serviceStatus = msg.arg1;
                  break;
               
               case MsgType.FINISH_AUTHENTICATE:
                  rtmService.completeAuthorization();
                  break;
               
               case MsgType.CANCEL:
                  if ( runningRtmWebAccess )
                  {
                     finishActivity( RtmWebAccess.ReqType.OPEN_URL );
                  }
                  
                  cancelLogin();
                  loginStatus = ReturnCode.CANCELED;
                  break;
               
               case MsgType.ERROR_RTM_WEB_ACCESS:
                  lastError = getString( R.string.login_err_rtmWebAccess );
                  disconnectService();
                  loginStatus = ReturnCode.ERROR;
                  break;
               
               case MsgType.SERVICE_DISCONNECTED:
                  if ( loginStatus == ReturnCode.SUCCESS )
                  {
                     returnWithAuthKey();
                  }
                  else
                  {
                     returnWithCode( loginStatus );
                  }
                  
               default :
                  super.handleMessage( msg );
            }
         }
         catch ( RemoteException e )
         {
            lastError = getString( R.string.login_err_conn_lost );
            returnWithCode( ReturnCode.ERROR );
         }
      }
   };
}
