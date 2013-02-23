/*
 * Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.moloko.app.account;

import java.lang.ref.WeakReference;

import android.os.AsyncTask;

import com.mdt.rtm.ApplicationInfo;
import com.mdt.rtm.Service;
import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceImpl;
import com.mdt.rtm.ServiceInternalException;
import com.mdt.rtm.data.RtmAuth;
import com.mdt.rtm.data.RtmAuth.Perms;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.IExecutorService;
import dev.drsoran.moloko.app.MolokoApp;


class AsyncRtmAuthenticator
{
   private final Service rtmService;
   
   private RtmAsyncAuthTask< ?, ? > runningTask;
   
   private final IExecutorService executorService;
   
   
   
   public AsyncRtmAuthenticator( AuthenticatorActivity activity,
      IExecutorService executor ) throws ServiceInternalException
   {
      this.rtmService = createService( activity );
      this.executorService = executor;
   }
   
   
   
   public void onAttach( IAuthSequenceListener authSequenceListener )
   {
      if ( runningTask != null )
      {
         runningTask.onAttach( authSequenceListener );
      }
   }
   
   
   
   public void onDetach()
   {
      if ( runningTask != null )
      {
         runningTask.onDetach();
      }
   }
   
   
   
   public boolean cancelExecution()
   {
      boolean ok = false;
      
      if ( runningTask != null )
      {
         ok = runningTask.cancel( true );
      }
      
      runningTask = null;
      
      return ok;
   }
   
   
   
   public void shutdown()
   {
      cancelExecution();
      shutdownService();
   }
   
   
   
   public void beginAuthentication( IAuthSequenceListener authSequenceListener,
                                    Perms permission )
   {
      if ( runningTask != null && !( runningTask instanceof BeginAuthTask ) )
      {
         cancelExecution();
      }
      
      final BeginAuthTask task = new BeginAuthTask( rtmService );
      task.onAttach( authSequenceListener );
      
      runningTask = (RtmAsyncAuthTask< ?, ? >) executorService.execute( task,
                                                                        permission );
   }
   
   
   
   public void completeAuthentication( IAuthSequenceListener authSequenceListener )
   {
      if ( runningTask != null && !( runningTask instanceof CompleteAuthTask ) )
      {
         cancelExecution();
      }
      
      final CompleteAuthTask task = new CompleteAuthTask( rtmService );
      task.onAttach( authSequenceListener );
      
      runningTask = (RtmAsyncAuthTask< ?, ? >) executorService.execute( task );
   }
   
   
   
   public void checkAuthToken( IAuthSequenceListener authSequenceListener,
                               String authToken )
   {
      if ( runningTask != null && !( runningTask instanceof CheckAuthTokenTask ) )
      {
         cancelExecution();
      }
      
      final CheckAuthTokenTask task = new CheckAuthTokenTask( rtmService );
      task.onAttach( authSequenceListener );
      
      runningTask = (RtmAsyncAuthTask< ?, ? >) executorService.execute( task,
                                                                        authToken );
   }
   
   
   
   public static String getExceptionCause( final Exception e )
   {
      if ( e instanceof ServiceException )
      {
         return ( (ServiceException) e ).getResponseMessage();
      }
      else
      {
         return e.getMessage();
      }
   }
   
   
   
   private Service createService( AuthenticatorActivity activity ) throws ServiceInternalException
   {
      final ApplicationInfo applicationInfo = new ApplicationInfo( MolokoApp.getRtmApiKey( activity ),
                                                                   MolokoApp.getRtmSharedSecret( activity ),
                                                                   activity.getString( R.string.app_name ),
                                                                   null );
      
      return ServiceImpl.getInstance( MolokoApp.getSettings( activity )
                                               .isUsingHttps(), applicationInfo );
   }
   
   
   
   private void shutdownService()
   {
      if ( rtmService != null )
      {
         rtmService.shutdown();
      }
   }
   
   
   private abstract class RtmAsyncAuthTask< Param, Result > extends
            AsyncTask< Param, Void, Result >
   {
      private boolean notifyPreExecute;
      
      private boolean notifyPostExecute;
      
      private Result notifyResult;
      
      protected final Service service;
      
      protected WeakReference< IAuthSequenceListener > authSequenceRef;
      
      protected volatile ServiceException exception;
      
      
      
      public RtmAsyncAuthTask( Service service )
      {
         this.service = service;
      }
      
      
      
      public void onAttach( IAuthSequenceListener authSequence )
      {
         authSequenceRef = new WeakReference< IAuthSequenceListener >( authSequence );
         
         if ( notifyPreExecute )
         {
            authSequence.post( new Runnable()
            {
               @Override
               public void run()
               {
                  preExecuteImpl();
               }
            } );
            
         }
         
         if ( notifyPostExecute )
         {
            authSequence.post( new Runnable()
            {
               @Override
               public void run()
               {
                  postExecuteImpl( notifyResult );
               }
            } );
            
            notifyResult = null;
         }
      }
      
      
      
      public void onDetach()
      {
         authSequenceRef.clear();
      }
      
      
      
      @Override
      protected final void onPreExecute()
      {
         super.onPreExecute();
         
         if ( authSequenceRef.get() != null )
         {
            preExecuteImpl();
         }
         else
         {
            notifyPreExecute = true;
         }
      }
      
      
      
      private void preExecuteImpl()
      {
         runPreExecute();
         notifyPreExecute = false;
      }
      
      
      
      @Override
      protected final void onPostExecute( Result result )
      {
         if ( authSequenceRef.get() != null )
         {
            postExecuteImpl( result );
         }
         else
         {
            notifyResult = result;
            notifyPostExecute = true;
         }
         
         super.onPostExecute( result );
      }
      
      
      
      private void postExecuteImpl( Result result )
      {
         runPostExecute( result );
         
         notifyPostExecute = false;
         AsyncRtmAuthenticator.this.runningTask = null;
      }
      
      
      
      protected void runPreExecute()
      {
      }
      
      
      
      protected void runPostExecute( Result result )
      {
      }
   }
   
   
   private final class BeginAuthTask extends RtmAsyncAuthTask< Perms, String >
   {
      public BeginAuthTask( Service service )
      {
         super( service );
      }
      
      
      
      @Override
      protected void runPreExecute()
      {
         authSequenceRef.get().onPreBeginAuthentication();
      }
      
      
      
      @Override
      protected String doInBackground( Perms... params )
      {
         String result = null;
         
         if ( params.length > 0 )
         {
            try
            {
               result = service.beginAuthorization( params[ 0 ] );
            }
            catch ( ServiceException e )
            {
               exception = e;
            }
         }
         
         return result;
      }
      
      
      
      @Override
      protected void runPostExecute( String result )
      {
         authSequenceRef.get().onPostBeginAuthentication( result, exception );
      }
   }
   
   
   private final class CompleteAuthTask extends RtmAsyncAuthTask< Void, String >
   {
      
      public CompleteAuthTask( Service service )
      {
         super( service );
      }
      
      
      
      @Override
      protected String doInBackground( Void... params )
      {
         String result = null;
         
         try
         {
            result = service.completeAuthorization();
         }
         catch ( ServiceException e )
         {
            exception = e;
         }
         
         return result;
      }
      
      
      
      @Override
      protected void runPostExecute( String result )
      {
         authSequenceRef.get().onAuthenticationCompleted( result, exception );
      }
   }
   
   
   private final class CheckAuthTokenTask extends
            RtmAsyncAuthTask< String, RtmAuth >
   {
      
      public CheckAuthTokenTask( Service service )
      {
         super( service );
      }
      
      
      
      @Override
      protected RtmAuth doInBackground( String... params )
      {
         RtmAuth result = null;
         
         if ( params.length > 0 )
         {
            try
            {
               result = service.auth_checkToken( params[ 0 ] );
            }
            catch ( ServiceException e )
            {
               exception = e;
            }
         }
         
         return result;
      }
      
      
      
      @Override
      protected void runPostExecute( RtmAuth result )
      {
         authSequenceRef.get().onAuthTokenChecked( result, exception );
      }
   }
}
