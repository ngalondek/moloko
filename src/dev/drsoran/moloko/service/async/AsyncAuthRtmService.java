package dev.drsoran.moloko.service.async;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import android.os.RemoteException;

import com.mdt.rtm.data.RtmAuth;
import com.mdt.rtm.data.RtmAuth.Perms;

import dev.drsoran.moloko.service.async.AsyncRtmService.Auth;
import dev.drsoran.moloko.util.ResultCallback;


public class AsyncAuthRtmService extends AbstractAsyncServicePart implements
         Auth
{
   
   public Future< ? > beginAuthorization( final Perms permission,
                                          final ResultCallback< String > callback )
   {
      return executor.submit( new Callable< Void >()
      {
         public Void call()
         {
            String res = null;
            
            try
            {
               res = syncService.beginAuthorization( permission.ordinal() );
               handler.post( callback.setResult( res ) );
            }
            catch ( RemoteException e )
            {
               handler.post( callback.setResult( res, e ) );
            }
            
            return null;
         }
      } );
   }
   


   public Future< ? > checkAuthToken( final String authToken,
                                      final ResultCallback< RtmAuth > callback )
   {
      return executor.submit( new Callable< Void >()
      {
         public Void call()
         {
            RtmAuth res = null;
            
            try
            {
               res = syncService.checkAuthToken( authToken );
               handler.post( callback.setResult( res ) );
            }
            catch ( RemoteException e )
            {
               handler.post( callback.setResult( res, e ) );
            }
            
            return null;
         }
      } );
   }
   


   public Future< ? > completeAuthorization( final ResultCallback< String > callback )
   {
      return executor.submit( new Callable< Void >()
      {
         public Void call()
         {
            String res = null;
            
            try
            {
               res = syncService.completeAuthorization();
               handler.post( callback.setResult( res ) );
            }
            catch ( RemoteException e )
            {
               handler.post( callback.setResult( res, e ) );
            }
            
            return null;
         }
      } );
   }
}
