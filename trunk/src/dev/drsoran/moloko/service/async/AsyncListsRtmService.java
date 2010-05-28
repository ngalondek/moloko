package dev.drsoran.moloko.service.async;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import android.os.RemoteException;

import com.mdt.rtm.data.RtmLists;

import dev.drsoran.moloko.service.async.AsyncRtmService.Lists;
import dev.drsoran.moloko.util.ResultCallback;


public class AsyncListsRtmService extends AbstractAsyncServicePart implements
         Lists
{
   public Future< ? > getList( final ResultCallback< RtmLists > callback )
   {
      return executor.submit( new Callable< Void >()
      {
         public Void call()
         {
            RtmLists res = null;
            
            try
            {
               res = syncService.lists_getList();
            }
            catch ( RemoteException e )
            {
               handler.post( callback.setResult( res, e ) );
            }
            
            handler.post( callback.setResult( res ) );
            
            return null;
         }
      } );
   }
}
