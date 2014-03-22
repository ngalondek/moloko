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

package dev.drsoran.moloko.auth;

import java.lang.ref.WeakReference;

import android.os.AsyncTask;
import dev.drsoran.moloko.IExecutorService;
import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.service.IRtmAuthenticationService;
import dev.drsoran.rtm.service.RtmAuth;
import dev.drsoran.rtm.service.RtmAuthHandle;
import dev.drsoran.rtm.service.RtmFrob;
import dev.drsoran.rtm.service.RtmServicePermission;


public class AsyncRtmAuthenticator
{
   private final IRtmAuthenticationService rtmAuthService;
   
   private RtmAsyncAuthTask< ?, ? > runningTask;
   
   private final IExecutorService executorService;
   
   
   
   public AsyncRtmAuthenticator( IRtmAuthenticationService rtmAuthService,
      IExecutorService executorService )
   {
      this.rtmAuthService = rtmAuthService;
      this.executorService = executorService;
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
   }
   
   
   
   public void beginAuthentication( IAuthSequenceListener authSequenceListener,
                                    RtmServicePermission permission )
   {
      if ( runningTask != null && !( runningTask instanceof BeginAuthTask ) )
      {
         cancelExecution();
      }
      
      final BeginAuthTask task = new BeginAuthTask( rtmAuthService );
      task.onAttach( authSequenceListener );
      
      runningTask = (RtmAsyncAuthTask< ?, ? >) executorService.execute( task,
                                                                        permission );
   }
   
   
   
   public void completeAuthentication( IAuthSequenceListener authSequenceListener,
                                       RtmFrob frob )
   {
      if ( runningTask != null && !( runningTask instanceof CompleteAuthTask ) )
      {
         cancelExecution();
      }
      
      final CompleteAuthTask task = new CompleteAuthTask( rtmAuthService );
      task.onAttach( authSequenceListener );
      
      runningTask = (RtmAsyncAuthTask< ?, ? >) executorService.execute( task,
                                                                        frob );
   }
   
   
   private abstract class RtmAsyncAuthTask< Param, Result > extends
            AsyncTask< Param, Void, Result >
   {
      private boolean notifyPreExecute;
      
      private boolean notifyPostExecute;
      
      private Result notifyResult;
      
      protected final IRtmAuthenticationService rtmAuthService;
      
      protected WeakReference< IAuthSequenceListener > authSequenceRef;
      
      protected volatile RtmServiceException exception;
      
      
      
      public RtmAsyncAuthTask( IRtmAuthenticationService rtmAuthService )
      {
         this.rtmAuthService = rtmAuthService;
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
   
   
   private final class BeginAuthTask extends
            RtmAsyncAuthTask< RtmServicePermission, RtmAuthHandle >
   {
      public BeginAuthTask( IRtmAuthenticationService rtmAuthService )
      {
         super( rtmAuthService );
      }
      
      
      
      @Override
      protected void runPreExecute()
      {
         authSequenceRef.get().onPreBeginAuthentication();
      }
      
      
      
      @Override
      protected RtmAuthHandle doInBackground( RtmServicePermission... params )
      {
         RtmAuthHandle authHandle = null;
         
         if ( params.length > 0 )
         {
            try
            {
               authHandle = rtmAuthService.beginAuthorization( params[ 0 ] );
            }
            catch ( RtmServiceException e )
            {
               exception = e;
            }
         }
         
         return authHandle;
      }
      
      
      
      @Override
      protected void runPostExecute( RtmAuthHandle authHandle )
      {
         authSequenceRef.get()
                        .onPostBeginAuthentication( authHandle, exception );
      }
   }
   
   
   private final class CompleteAuthTask extends
            RtmAsyncAuthTask< RtmFrob, RtmAuth >
   {
      public CompleteAuthTask( IRtmAuthenticationService rtmAuthService )
      {
         super( rtmAuthService );
      }
      
      
      
      @Override
      protected RtmAuth doInBackground( RtmFrob... params )
      {
         RtmAuth rtmAuth = null;
         
         try
         {
            rtmAuth = rtmAuthService.completeAuthorization( params[ 0 ] );
         }
         catch ( RtmServiceException e )
         {
            exception = e;
         }
         
         return rtmAuth;
      }
      
      
      
      @Override
      protected void runPostExecute( RtmAuth rtmauth )
      {
         authSequenceRef.get().onAuthenticationCompleted( rtmauth, exception );
      }
   }
}
