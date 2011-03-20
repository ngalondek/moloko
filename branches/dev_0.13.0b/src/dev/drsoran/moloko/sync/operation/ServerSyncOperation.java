/* 
 *	Copyright (c) 2011 Ronny Röhricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.sync.operation;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.ContentProviderOperation;
import android.util.Log;

import com.mdt.rtm.Service;
import com.mdt.rtm.ServiceException;
import com.mdt.rtm.TimeLineMethod;
import com.mdt.rtm.TimeLineResult;

import dev.drsoran.moloko.content.Modification;
import dev.drsoran.moloko.content.ModificationsProviderPart;
import dev.drsoran.moloko.content.RtmProvider;
import dev.drsoran.moloko.content.TransactionalAccess;
import dev.drsoran.moloko.service.RtmServiceConstants;
import dev.drsoran.moloko.util.Strings;


public class ServerSyncOperation< T > implements IServerSyncOperation< T >
{
   private final static String TAG = "Moloko."
      + ServerSyncOperation.class.getSimpleName();
   
   
   public static class Builder< T >
   {
      private final Op operationType;
      
      private final Map< TimeLineMethod< T >, List< Modification > > methods = new HashMap< TimeLineMethod< T >, List< Modification > >();
      
      

      public Builder( Op operationType )
      {
         this.operationType = operationType;
      }
      


      public Builder( Builder< T > other )
      {
         this.operationType = other.operationType;
         this.methods.putAll( other.methods );
      }
      


      public Builder( Op operationType, TimeLineMethod< T > method,
         Modification modification )
      {
         this( operationType );
         add( method, modification );
      }
      


      public Builder< T > add( TimeLineMethod< T > method,
                               Modification modification )
      {
         if ( method == null )
            throw new NullPointerException( "method is null" );
         
         methods.put( method, Collections.singletonList( modification ) );
         return this;
      }
      


      public Builder< T > add( TimeLineMethod< T > method,
                               List< Modification > modifications )
      {
         if ( method == null )
            throw new NullPointerException( "method is null" );
         
         methods.put( method, modifications );
         return this;
      }
      


      public Builder< T > add( IServerSyncOperation< T > operation )
      {
         if ( !( operation instanceof INoopSyncOperation ) )
         {
            final Map< TimeLineMethod< T >, List< Modification > > methods = operation.getMethods();
            for ( TimeLineMethod< T > method : methods.keySet() )
               add( method, methods.get( method ) );
         }
         
         return this;
      }
      


      public < O extends IServerSyncOperation< T >> IServerSyncOperation< T > build( Class< O > opType )
      {
         if ( methods.size() == 0 )
            return NoopServerSyncOperation.< T > newInstance();
         else
            try
            {
               return opType.getConstructor( Builder.class ).newInstance( this );
            }
            catch ( IllegalArgumentException e )
            {
               Log.e( TAG, Strings.EMPTY_STRING, e );
               return null;
            }
            catch ( SecurityException e )
            {
               Log.e( TAG, Strings.EMPTY_STRING, e );
               return null;
            }
            catch ( InstantiationException e )
            {
               Log.e( TAG, Strings.EMPTY_STRING, e );
               return null;
            }
            catch ( IllegalAccessException e )
            {
               Log.e( TAG, Strings.EMPTY_STRING, e );
               return null;
            }
            catch ( InvocationTargetException e )
            {
               Log.e( TAG, Strings.EMPTY_STRING, e );
               return null;
            }
            catch ( NoSuchMethodException e )
            {
               Log.e( TAG, Strings.EMPTY_STRING, e );
               return null;
            }
      }
   }
   
   private final Op operationType;
   
   private final Map< TimeLineMethod< T >, List< Modification > > serviceMethods;
   
   private T resultElement;
   
   private List< TimeLineResult.Transaction > transactions;
   
   

   protected ServerSyncOperation( Builder< T > builder )
   {
      this.operationType = builder.operationType;
      this.serviceMethods = new HashMap< TimeLineMethod< T >, List< Modification > >( builder.methods );
   }
   


   public T execute( RtmProvider rtmProvider ) throws ServiceException
   {
      transactions = new LinkedList< TimeLineResult.Transaction >();
      
      final Set< TimeLineMethod< T > > methodSet = serviceMethods.keySet();
      final ArrayList< ContentProviderOperation > removeModOps = new ArrayList< ContentProviderOperation >( methodSet.size() );
      
      for ( TimeLineMethod< T > method : methodSet )
      {
         try
         {
            final TimeLineResult< T > res = method.call();
            resultElement = handleResultElement( res.element );
            transactions.add( res.transaction );
         }
         catch ( Throwable e )
         {
            Log.e( TAG, "Error during server method execution", e );
            
            // If this was a service exception
            if ( e instanceof ServiceException )
            {
               final ServiceException se = (ServiceException) e;
               if ( !RtmServiceConstants.RtmErrorCodes.isElementError( se.responseCode ) )
                  throw se;
               else
                  Log.e( TAG,
                         "Ignoring failed server operation due to Element error "
                            + se.responseCode );
            }
            else
               throw new ServiceException( -1,
                                           "Error during server method execution",
                                           e );
         }
         
         // Remove the modification which led to the server change
         final List< Modification > modifications = serviceMethods.get( method );
         
         if ( modifications != null )
            for ( Modification modification : modifications )
               if ( modification != null )
                  removeModOps.add( ModificationsProviderPart.getRemoveModificationOps( modification.getEntityUri() ) );
      }
      
      if ( removeModOps.size() > 0 )
      {
         Log.i( TAG, "Removing " + removeModOps.size() + " modifications" );
         
         final TransactionalAccess transactionalAccess = rtmProvider.newTransactionalAccess();
         
         if ( transactionalAccess == null )
            throw new IllegalStateException( "No transactional access to remove modifications" );
         
         try
         {
            transactionalAccess.beginTransaction();
            
            rtmProvider.applyBatch( removeModOps );
            
            transactionalAccess.setTransactionSuccessful();
         }
         catch ( Throwable e )
         {
            Log.e( TAG, "Removing modifications failed", e );
         }
         finally
         {
            transactionalAccess.endTransaction();
         }
      }
      
      return resultElement;
   }
   


   public List< TimeLineResult.Transaction > revert( Service service )
   {
      if ( transactions == null )
         throw new IllegalStateException( "Service method not yet executed" );
      
      for ( Iterator< TimeLineResult.Transaction > i = transactions.iterator(); i.hasNext(); )
      {
         final TimeLineResult.Transaction transaction = i.next();
         
         try
         {
            if ( transaction.undoable )
               service.transactions_undo( transaction.timelineId,
                                          transaction.transactionId );
            
            // if the transaction could be reverted, we remove it from the list.
            i.remove();
         }
         catch ( ServiceException e )
         {
            Log.e( TAG, "Error reverting transaction", e );
         }
      }
      
      // Return a list of remaining transactions which could not be reverted.
      return Collections.unmodifiableList( transactions );
   }
   


   public Op getOperationType()
   {
      return operationType;
   }
   


   public Map< TimeLineMethod< T >, List< Modification > > getMethods()
   {
      return this.serviceMethods;
   }
   


   protected T handleResultElement( T resultElement )
   {
      return resultElement;
   }
   


   public final static < T > Builder< T > fromType( ISyncOperation.Op type )
   {
      switch ( type )
      {
         case INSERT:
            return newInsert();
         case UPDATE:
            return newUpdate();
         case DELETE:
            return newDelete();
         default :
            return null;
      }
   }
   


   public final static < T > Builder< T > newInsert()
   {
      return new Builder< T >( Op.INSERT );
   }
   


   public final static < T > Builder< T > newInsert( IServerSyncOperation< T > operation )
   {
      return new Builder< T >( Op.INSERT ).add( operation );
   }
   


   public final static < T > Builder< T > newInsert( TimeLineMethod< T > method,
                                                     Modification modification )
   {
      return new Builder< T >( Op.INSERT, method, modification );
   }
   


   public final static < T > Builder< T > newUpdate()
   {
      return new Builder< T >( Op.UPDATE );
   }
   


   public final static < T > Builder< T > newUpdate( IServerSyncOperation< T > operation )
   {
      return new Builder< T >( Op.UPDATE ).add( operation );
   }
   


   public final static < T > Builder< T > newUpdate( TimeLineMethod< T > method,
                                                     Modification modification )
   {
      return new Builder< T >( Op.UPDATE, method, modification );
   }
   


   public final static < T > Builder< T > newDelete()
   {
      return new Builder< T >( Op.DELETE );
   }
   


   public final static < T > Builder< T > newDelete( IServerSyncOperation< T > operation )
   {
      return new Builder< T >( Op.DELETE ).add( operation );
   }
   


   public final static < T > Builder< T > newDelete( TimeLineMethod< T > method,
                                                     Modification modification )
   {
      return new Builder< T >( Op.DELETE, method, modification );
   }
}
