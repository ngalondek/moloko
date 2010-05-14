package dev.drsoran.moloko.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceImpl;
import com.mdt.rtm.ServiceInternalException;

import dev.drsoran.moloko.service.parcel.ParcelableApplicationInfo;


public class RtmService extends Service
{
   private final class MsgType
   {
      public final static int AUTHORIZE_BEGIN = 0;
      
      public final static int AUTHORIZE_COMPLETE = 1;
   }
   

   private final class RtmServiceProxy extends IRtmService.Stub
   {
      public void beginAuthorization( ParcelableApplicationInfo info ) throws RemoteException
      {
         handler.sendMessage( handler.obtainMessage( MsgType.AUTHORIZE_BEGIN,
                                                     info ) );
      }
      


      public void completeAuthorization() throws RemoteException
      {
         handler.sendMessage( handler
            .obtainMessage( MsgType.AUTHORIZE_COMPLETE ) );
      }
      


      public boolean isAuthorized() throws RemoteException
      {
         boolean is = false;
         
         try
         {
            is = serviceImpl != null && serviceImpl.isServiceAuthorized();
         }
         catch ( ServiceException e )
         {
            sendStatusUpdate( RtmServiceConstants.ServiceState.INTERNAL_ERROR,
                              e );
         }
         
         return is;
      }
      


      public void registerCallback( IRtmServiceCallback callback ) throws RemoteException
      {
         if ( callback != null )
         {
            callbacks.register( callback );
         }
      }
      


      public void unregisterCallback( IRtmServiceCallback callback ) throws RemoteException
      {
         if ( callback != null )
         {
            callbacks.unregister( callback );
         }
      }
      


      public String getLastErrorDescription() throws RemoteException
      {
         return lastErrorDesc;
      }
      


      public int getLastErrorCode() throws RemoteException
      {
         return lasErrorCode;
      }
      


      public String getAuthToken() throws RemoteException
      {
         return rtmAuthToken;
      }
      


      public String getLoginUrl() throws RemoteException
      {
         return rtmLoginUrl;
      }
   }
   
   // ////// Implementation //////////////////////////////////////////////////
   
   /**
    * This is a list of callbacks that have been registered with the service.
    * Note that this is package scoped (instead of private) so that it can be
    * accessed more efficiently from inner classes.
    */
   final RemoteCallbackList< IRtmServiceCallback > callbacks =
            new RemoteCallbackList< IRtmServiceCallback >();
   
   private ServiceImpl serviceImpl = null;
   
   private String rtmAuthToken = null;
   
   private String rtmLoginUrl = null;
   
   private String lastErrorDesc = null;
   
   private int lasErrorCode = 0;
   
   private final RtmServiceProxy proxy = new RtmServiceProxy();
   
   

   @Override
   public IBinder onBind( Intent intent )
   {
      IBinder binder = null;
      
      if ( intent != null && intent.getAction().equals( IRtmService.class
              .getName() ) )
      {
         binder = asBinder();
      }
      
      return binder;
   }
   


   public IBinder asBinder()
   {
      return proxy;
   }
   


   @Override
   public void onCreate()
   {
      super.onCreate();
      
      // android.os.Debug.waitForDebugger();
   }
   


   @Override
   public void onDestroy()
   {
      // Unregister all callbacks.
      callbacks.kill();
   }
   


   private void sendStatusUpdate( int status )
   {
      setError( null, 0 );
      broadcastStatusUpdate( status );
   }
   


   private void sendStatusUpdate( int status, Exception e )
   {
      if ( e instanceof ServiceException )
      {
         setError( ( (ServiceException) e ).getResponseMessage(),
                   ( (ServiceException) e ).getResponseCode() );
         broadcastStatusUpdate( RtmServiceConstants.AuthState.FAILED );
      }
      else
      {
         setError( e.getMessage(),
                   RtmServiceConstants.ServiceState.INTERNAL_ERROR );
         broadcastStatusUpdate( RtmServiceConstants.ServiceState.INTERNAL_ERROR );
      }
   }
   


   private void setError( final String errorDesc, int errorCode )
   {
      lastErrorDesc = errorDesc;
      lasErrorCode = errorCode;
   }
   


   private void broadcastStatusUpdate( int status )
   {
      // Broadcast to all clients the new value.
      final int N = callbacks.beginBroadcast();
      
      for ( int i = 0; i < N; i++ )
      {
         try
         {
            callbacks.getBroadcastItem( i ).onStatusUpdate( status );
         }
         catch ( RemoteException e )
         {
            // The RemoteCallbackList will take care of removing
            // the dead object for us.
         }
      }
      
      callbacks.finishBroadcast();
   }
   


   public void beginAuthorizationImpl( ParcelableApplicationInfo info )
   {
      try
      {
         serviceImpl = new ServiceImpl( info.getApplicationInfo() );
         
         try
         {
            sendStatusUpdate( RtmServiceConstants.AuthState.GET_LOGIN_URL_START );
            
            rtmLoginUrl = serviceImpl.beginAuthorization( info.getPermission() );
            
            sendStatusUpdate( RtmServiceConstants.AuthState.GET_LOGIN_URL_FIN );
         }
         catch ( ServiceException e )
         {
            sendStatusUpdate( RtmServiceConstants.AuthState.FAILED, e );
         }
      }
      catch ( ServiceInternalException e )
      {
         sendStatusUpdate( RtmServiceConstants.ServiceState.INTERNAL_ERROR, e );
      }
   }
   


   public void completeAuthorizationImpl()
   {
      if ( serviceImpl == null )
      {
         sendStatusUpdate( RtmServiceConstants.ServiceState.INTERNAL_ERROR,
                           new Exception( "No service" ) );
      }
      
      try
      {
         sendStatusUpdate( RtmServiceConstants.AuthState.GET_TOKEN_START );
         
         rtmAuthToken = serviceImpl.completeAuthorization();
         
         sendStatusUpdate( RtmServiceConstants.AuthState.GET_TOKEN_FIN );
      }
      catch ( ServiceException e )
      {
         sendStatusUpdate( RtmServiceConstants.AuthState.FAILED, e );
      }
   }
   
   // / Thread loop //////////////////////////////////////
   
   private final Handler handler = new Handler()
   {
      @Override
      public void handleMessage( Message msg )
      {
         switch ( msg.what )
         {
            case MsgType.AUTHORIZE_BEGIN:
               beginAuthorizationImpl( (ParcelableApplicationInfo) msg.obj );
               break;
            case MsgType.AUTHORIZE_COMPLETE:
               completeAuthorizationImpl();
               break;            
            default :
               super.handleMessage( msg );
         }
      }
   };
}
