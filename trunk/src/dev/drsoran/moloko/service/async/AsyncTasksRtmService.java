package dev.drsoran.moloko.service.async;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import android.os.RemoteException;

import com.mdt.rtm.data.RtmTasks;

import dev.drsoran.moloko.service.async.AsyncRtmService.Tasks;
import dev.drsoran.moloko.service.parcel.ParcelableDate;
import dev.drsoran.moloko.util.ResultCallback;


public class AsyncTasksRtmService extends AbstractAsyncServicePart implements
         Tasks
{
   public Future< ? > getList( final String listId,
                               final String filter,
                               final Date lastSync,
                               final ResultCallback< RtmTasks > callback )
   {
      return executor.submit( new Callable< Void >()
      {
         public Void call()
         {
            RtmTasks res = null;
            
            try
            {
               res = syncService.tasks_getList( listId,
                                                filter,
                                                ( lastSync != null
                                                                  ? new ParcelableDate( lastSync )
                                                                  : null ) );
               
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
