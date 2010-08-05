package dev.drsoran.moloko.auth;

import java.lang.ref.WeakReference;

import android.os.AsyncTask;

import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceImpl;
import com.mdt.rtm.data.RtmAuth;
import com.mdt.rtm.data.RtmAuth.Perms;

import dev.drsoran.moloko.service.RtmServiceException;


public class AsyncRtmAuthenticator
{
   private final AuthenticatorActivity activity;
   
   private final ServiceImpl rtmServiceImpl;
   
   @SuppressWarnings( "unchecked" )
   private AsyncTask runningTask;
   
   
   private abstract class RtmAsyncAuthTask< Param, Result > extends
            AsyncTask< Param, Void, Result >
   {
      protected final WeakReference< AuthenticatorActivity > activity;
      
      protected final WeakReference< ServiceImpl > service;
      
      protected ServiceException exception;
      
      

      public RtmAsyncAuthTask( AuthenticatorActivity activity,
         ServiceImpl service )
      {
         this.activity = new WeakReference< AuthenticatorActivity >( activity );
         this.service = new WeakReference< ServiceImpl >( service );
      }
      


      @Override
      protected void onPostExecute( Result result )
      {
         AsyncRtmAuthenticator.this.runningTask = null;
         super.onPostExecute( result );
      }
   }
   

   private final class BeginAuthTask extends RtmAsyncAuthTask< Perms, String >
   {
      public BeginAuthTask( AuthenticatorActivity activity, ServiceImpl service )
      {
         super( activity, service );
      }
      


      @Override
      protected void onPreExecute()
      {
         activity.get().onPreBeginAuthentication();
         super.onPreExecute();
      }
      


      @Override
      protected String doInBackground( Perms... params )
      {
         String result = null;
         
         if ( service != null && params.length > 0 )
         {
            try
            {
               result = service.get().beginAuthorization( params[ 0 ] );
            }
            catch ( ServiceException e )
            {
               exception = e;
            }
         }
         
         return result;
      }
      


      @Override
      protected void onPostExecute( String result )
      {
         if ( activity.get() != null )
            activity.get().onPostBeginAuthentication( result, exception );
         
         super.onPostExecute( result );
      }
   }
   

   private final class CompleteAuthTask extends RtmAsyncAuthTask< Void, String >
   {
      
      public CompleteAuthTask( AuthenticatorActivity activity,
         ServiceImpl service )
      {
         super( activity, service );
      }
      


      @Override
      protected String doInBackground( Void... params )
      {
         String result = null;
         
         if ( service != null )
         {
            try
            {
               result = service.get().completeAuthorization();
            }
            catch ( ServiceException e )
            {
               exception = e;
            }
         }
         
         return result;
      }
      


      @Override
      protected void onPostExecute( String result )
      {
         if ( activity.get() != null )
            activity.get().onPostCompleteAuthentication( result, exception );
         
         super.onPostExecute( result );
      }
   }
   

   private final class CheckAuthTokenTask extends
            RtmAsyncAuthTask< String, RtmAuth >
   {
      
      public CheckAuthTokenTask( AuthenticatorActivity activity,
         ServiceImpl service )
      {
         super( activity, service );
      }
      


      @Override
      protected RtmAuth doInBackground( String... params )
      {
         RtmAuth result = null;
         
         if ( service != null && params.length > 0 )
         {
            try
            {
               result = service.get().auth_checkToken( params[ 0 ] );
            }
            catch ( ServiceException e )
            {
               exception = e;
            }
         }
         
         return result;
      }
      


      @Override
      protected void onPostExecute( RtmAuth result )
      {
         if ( activity.get() != null )
            activity.get().onPostCheckAuthToken( result, exception );
         
         super.onPostExecute( result );
      }
   }
   
   

   public AsyncRtmAuthenticator( AuthenticatorActivity activity,
      ServiceImpl service )
   {
      this.activity = activity;
      this.rtmServiceImpl = service;
   }
   


   public boolean cancelExecution()
   {
      boolean ok = false;
      
      if ( runningTask != null )
         ok = runningTask.cancel( true );
      
      runningTask = null;
      
      return ok;
   }
   


   public void beginAuthentication( Perms permission )
   {
      if ( runningTask != null && !( runningTask instanceof BeginAuthTask ) )
      {
         cancelExecution();
      }
      
      runningTask = new BeginAuthTask( activity, rtmServiceImpl ).execute( permission );
   }
   


   public void completeAuthentication()
   {
      if ( runningTask != null && !( runningTask instanceof CompleteAuthTask ) )
      {
         cancelExecution();
      }
      
      runningTask = new CompleteAuthTask( activity, rtmServiceImpl ).execute();
   }
   


   public void checkAuthToken( String authToken )
   {
      if ( runningTask != null && !( runningTask instanceof CheckAuthTokenTask ) )
      {
         cancelExecution();
      }
      
      runningTask = new CheckAuthTokenTask( activity, rtmServiceImpl ).execute( authToken );
   }
   


   public static String getExceptionCause( final Exception e )
   {
      if ( e instanceof RtmServiceException )
      {
         return ( (RtmServiceException) e ).rtmCause;
      }
      else
      {
         return e.getMessage();
      }
   }
   


   public static int getExceptionCode( final Exception e )
   {
      if ( e instanceof RtmServiceException )
      {
         return ( (RtmServiceException) e ).errorCode;
      }
      else
      {
         return 0;
      }
   }
}
