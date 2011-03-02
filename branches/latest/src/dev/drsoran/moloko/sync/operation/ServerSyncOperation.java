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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import android.util.Log;

import com.mdt.rtm.Service;
import com.mdt.rtm.ServiceException;
import com.mdt.rtm.TimeLineMethod;
import com.mdt.rtm.TimeLineResult;
import com.mdt.rtm.Service.MethodCallType;

import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.sync.syncable.IServerSyncable;


public class ServerSyncOperation< T extends IServerSyncable< T, V >, V >
         implements IServerSyncOperation< V >
{
   private final static String TAG = "Moloko."
      + ServerSyncOperation.class.getSimpleName();
   
   
   public static class Builder< T extends IServerSyncable< T, V >, V >
   {
      private final Op operationType;
      
      private final List< TimeLineMethod< V > > methods = new ArrayList< TimeLineMethod< V > >();
      
      private final T sourceElement;
      
      

      public Builder( Op operationType, T sourceElement )
      {
         if ( sourceElement == null )
            throw new NullPointerException( "sourceElement is null" );
         
         this.operationType = operationType;
         this.sourceElement = sourceElement;
      }
      


      public Builder( Builder< T, V > other )
      {
         if ( other.sourceElement == null )
            throw new NullPointerException( "sourceElement is null" );
         
         this.operationType = other.operationType;
         this.methods.addAll( other.methods );
         this.sourceElement = other.sourceElement;
      }
      


      public Builder( Op operationType, TimeLineMethod< V > method,
         T sourceElement )
      {
         this( operationType, sourceElement );
         add( method );
      }
      


      public Builder( Op operationType,
         Collection< TimeLineMethod< V > > methods, T sourceElement )
      {
         this( operationType, sourceElement );
         add( methods );
      }
      


      public Builder< T, V > add( TimeLineMethod< V > method )
      {
         if ( method == null )
            throw new NullPointerException( "method is null" );
         
         methods.add( method );
         return this;
      }
      


      public Builder< T, V > add( Collection< ? extends TimeLineMethod< V > > methods )
      {
         if ( methods == null )
            throw new NullPointerException( "methods are null" );
         
         for ( TimeLineMethod< V > timeLineMethod : methods )
         {
            add( timeLineMethod );
         }
         
         return this;
      }
      


      public Builder< T, V > add( IServerSyncOperation< V > operation )
      {
         if ( !( operation instanceof INoopSyncOperation ) )
            for ( TimeLineMethod< V > method : operation.getMethods() )
               add( method );
         
         return this;
      }
      


      public IServerSyncOperation< V > build()
      {
         if ( methods.size() == 0 )
            return NoopServerSyncOperation.< V > newInstance();
         else
            return new ServerSyncOperation< T, V >( this );
      }
   }
   
   private final Op operationType;
   
   private final List< TimeLineMethod< V > > serviceMethods;
   
   private final T sourceElement;
   
   private V resultElement;
   
   private List< TimeLineResult.Transaction > transactions;
   
   

   private ServerSyncOperation( Builder< T, V > builder )
   {
      this.operationType = builder.operationType;
      this.serviceMethods = new ArrayList< TimeLineMethod< V > >( builder.methods );
      this.sourceElement = builder.sourceElement;
   }
   


   public V execute() throws ServiceException
   {
      transactions = new LinkedList< TimeLineResult.Transaction >();
      
      for ( Iterator< TimeLineMethod< V > > i = serviceMethods.iterator(); i.hasNext(); )
      {
         final TimeLineMethod< V > method = i.next();
         
         try
         {
            if ( i.hasNext() )
               // We retrieve the result only for the last element.
               transactions.add( method.call( MethodCallType.NO_RESULT ).transaction );
            else
            {
               final TimeLineResult< V > res = method.call( MethodCallType.WITH_RESULT );
               resultElement = res.element;
               transactions.add( res.transaction );
            }
         }
         catch ( Throwable e )
         {
            Log.e( TAG, "Error during server method execution", e );
            
            // If this was a service exception, re-throw it
            if ( e instanceof ServiceException )
               throw (ServiceException) e;
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
   


   public IContentProviderSyncOperation removeModifications( ModificationSet modifications,
                                                             boolean revert )
   {
      if ( sourceElement != null )
         return sourceElement.removeModifications( modifications, revert );
      else
         return NoopContentProviderSyncOperation.INSTANCE;
   }
   


   public Op getOperationType()
   {
      return operationType;
   }
   


   public List< TimeLineMethod< V >> getMethods()
   {
      return this.serviceMethods;
   }
   


   public void addMethod( TimeLineMethod< V > method )
   {
      if ( method == null )
         throw new NullPointerException( "method is null" );
      
      serviceMethods.add( method );
   }
   


   public final static < T extends IServerSyncable< T, V >, V > Builder< T, V > fromType( T sourceElement,
                                                                                          ISyncOperation.Op type )
   {
      switch ( type )
      {
         case INSERT:
            return newInsert( sourceElement );
         case UPDATE:
            return newUpdate( sourceElement );
         case DELETE:
            return newDelete( sourceElement );
         default :
            return null;
      }
   }
   


   public final static < T extends IServerSyncable< T, V >, V > Builder< T, V > newInsert( T sourceElement )
   {
      return new Builder< T, V >( Op.INSERT, sourceElement );
   }
   


   public final static < T extends IServerSyncable< T, V >, V > Builder< T, V > newInsert( T sourceElement,
                                                                                           IServerSyncOperation< V > operation )
   {
      return new Builder< T, V >( Op.INSERT, sourceElement ).add( operation );
   }
   


   public final static < T extends IServerSyncable< T, V >, V > Builder< T, V > newInsert( T sourceElement,
                                                                                           TimeLineMethod< V > method )
   {
      return new Builder< T, V >( Op.INSERT, method, sourceElement );
   }
   


   public final static < T extends IServerSyncable< T, V >, V > Builder< T, V > newInsert( T sourceElement,
                                                                                           Collection< TimeLineMethod< V > > methods )
   {
      return new Builder< T, V >( Op.INSERT, methods, sourceElement );
   }
   


   public final static < T extends IServerSyncable< T, V >, V > Builder< T, V > newUpdate( T sourceElement )
   {
      return new Builder< T, V >( Op.UPDATE, sourceElement );
   }
   


   public final static < T extends IServerSyncable< T, V >, V > Builder< T, V > newUpdate( T sourceElement,
                                                                                           IServerSyncOperation< V > operation )
   {
      return new Builder< T, V >( Op.UPDATE, sourceElement ).add( operation );
   }
   


   public final static < T extends IServerSyncable< T, V >, V > Builder< T, V > newUpdate( T sourceElement,
                                                                                           TimeLineMethod< V > method )
   {
      return new Builder< T, V >( Op.UPDATE, method, sourceElement );
   }
   


   public final static < T extends IServerSyncable< T, V >, V > Builder< T, V > newUpdate( T sourceElement,
                                                                                           Collection< TimeLineMethod< V > > methods )
   {
      return new Builder< T, V >( Op.UPDATE, methods, sourceElement );
   }
   


   public final static < T extends IServerSyncable< T, V >, V > Builder< T, V > newDelete( T sourceElement )
   {
      return new Builder< T, V >( Op.DELETE, sourceElement );
   }
   


   public final static < T extends IServerSyncable< T, V >, V > Builder< T, V > newDelete( T sourceElement,
                                                                                           IServerSyncOperation< V > operation )
   {
      return new Builder< T, V >( Op.DELETE, sourceElement ).add( operation );
   }
   


   public final static < T extends IServerSyncable< T, V >, V > Builder< T, V > newDelete( T sourceElement,
                                                                                           TimeLineMethod< V > method )
   {
      return new Builder< T, V >( Op.DELETE, method, sourceElement );
   }
   


   public final static < T extends IServerSyncable< T, V >, V > Builder< T, V > newDelete( T sourceElement,
                                                                                           Collection< TimeLineMethod< V > > methods )
   {
      return new Builder< T, V >( Op.DELETE, methods, sourceElement );
   }
   
}
