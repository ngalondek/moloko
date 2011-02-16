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

package dev.drsoran.moloko.service.sync.operation;

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
import com.mdt.rtm.Service.MethodCallType;

import dev.drsoran.moloko.content.ModificationList;
import dev.drsoran.moloko.service.sync.syncable.IServerSyncable;
import dev.drsoran.moloko.service.sync.syncable.IServerSyncable.MergeDirection;


public class ServerSyncOperation< T extends IServerSyncable< T > > implements
         IServerSyncOperation< T >
{
   private final static String TAG = "Moloko."
      + ServerSyncOperation.class.getSimpleName();
   
   
   public static class Builder< T extends IServerSyncable< T >>
   {
      private final Op operationType;
      
      private final List< TimeLineMethod< T > > methods = new ArrayList< TimeLineMethod< T > >();
      
      

      public Builder( Op operationType )
      {
         this.operationType = operationType;
      }
      


      public Builder( Builder< T > other )
      {
         this.operationType = other.operationType;
         this.methods.addAll( other.methods );
      }
      


      public Builder( Op operationType, TimeLineMethod< T > method )
      {
         add( method );
         this.operationType = operationType;
      }
      


      public Builder( Op operationType,
         Collection< TimeLineMethod< T > > methods )
      {
         add( methods );
         this.operationType = operationType;
      }
      


      public Builder< T > add( TimeLineMethod< T > method )
      {
         if ( method == null )
            throw new NullPointerException( "method is null" );
         
         methods.add( method );
         return this;
      }
      


      public Builder< T > add( Collection< TimeLineMethod< T > > methods )
      {
         if ( methods == null )
            throw new NullPointerException( "methods are null" );
         
         for ( TimeLineMethod< T > timeLineMethod : methods )
         {
            add( timeLineMethod );
         }
         
         return this;
      }
      


      public Builder< T > add( IServerSyncOperation< T > operation )
      {
         if ( !( operation instanceof INoopSyncOperation ) )
         {
            if ( operation.getOperationType() != operationType )
               throw new IllegalArgumentException( "This operation type "
                  + operationType + " differs from argument operation type "
                  + operation.getOperationType() );
            
            for ( TimeLineMethod< T > method : operation.getMethods() )
            {
               add( method );
            }
         }
         
         return this;
      }
      


      @SuppressWarnings( "unchecked" )
      public IServerSyncOperation< T > build()
      {
         if ( methods.size() == 0 )
            return NoopServerSyncOperation.INSTANCE;
         else
            return new ServerSyncOperation( this );
      }
   }
   
   private final Op operationType;
   
   private final List< TimeLineMethod< T > > serviceMethods;
   
   private T resultElement;
   
   private List< TimeLineMethod.Transaction > transactions;
   
   

   private ServerSyncOperation( Builder< T > builder )
   {
      this.operationType = builder.operationType;
      this.serviceMethods = Collections.unmodifiableList( new ArrayList< TimeLineMethod< T > >( builder.methods ) );
   }
   


   public IContentProviderSyncOperation execute() throws ServiceException
   {
      transactions = new LinkedList< TimeLineMethod.Transaction >();
      
      for ( Iterator< TimeLineMethod< T > > i = serviceMethods.iterator(); i.hasNext(); )
      {
         final TimeLineMethod< T > method = i.next();
         
         if ( i.hasNext() )
            // We retrieve the result only for the last element.
            transactions.add( method.call( MethodCallType.NO_RESULT ).transaction );
         else
         {
            final TimeLineMethod.Result< T > res = method.call( MethodCallType.WITH_RESULT );
            resultElement = res.element;
            transactions.add( res.transaction );
         }
      }
      
      if ( resultElement == null )
         throw new IllegalStateException( "Service method did not produced a result element" );
      
      return resultElement.computeMergeOperations( null,
                                                   null,
                                                   resultElement,
                                                   MergeDirection.LOCAL_ONLY )
                          .getLocalOperation();
   }
   


   public List< TimeLineMethod.Transaction > revert( Service service )
   {
      if ( transactions == null )
         throw new IllegalStateException( "Service method not yet executed" );
      
      for ( Iterator< TimeLineMethod.Transaction > i = transactions.iterator(); i.hasNext(); )
      {
         final TimeLineMethod.Transaction transaction = i.next();
         
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
   


   public IContentProviderSyncOperation removeModification( ModificationList modifications )
   {
      if ( resultElement == null )
         throw new IllegalStateException( "Service method not yet executed" );
      
      return resultElement.computeRemoveModificationsOperation( modifications );
   }
   


   public Op getOperationType()
   {
      return operationType;
   }
   


   public List< TimeLineMethod< T >> getMethods()
   {
      return this.serviceMethods;
   }
   


   public T getResultElement()
   {
      return resultElement;
   }
   


   public final static < T extends IServerSyncable< T > > Builder< T > newInsert()
   {
      return new Builder< T >( Op.INSERT );
   }
   


   public final static < T extends IServerSyncable< T > > Builder< T > newInsert( IServerSyncOperation< T > operation )
   {
      return new Builder< T >( Op.INSERT ).add( operation );
   }
   


   public final static < T extends IServerSyncable< T > > Builder< T > newInsert( TimeLineMethod< T > method )
   {
      return new Builder< T >( Op.INSERT, method );
   }
   


   public final static < T extends IServerSyncable< T > > Builder< T > newInsert( Collection< TimeLineMethod< T > > methods )
   {
      return new Builder< T >( Op.INSERT, methods );
   }
   


   public final static < T extends IServerSyncable< T > > Builder< T > newUpdate()
   {
      return new Builder< T >( Op.UPDATE );
   }
   


   public final static < T extends IServerSyncable< T > > Builder< T > newUpdate( IServerSyncOperation< T > operation )
   {
      return new Builder< T >( Op.UPDATE ).add( operation );
   }
   


   public final static < T extends IServerSyncable< T > > Builder< T > newUpdate( TimeLineMethod< T > method )
   {
      return new Builder< T >( Op.UPDATE, method );
   }
   


   public final static < T extends IServerSyncable< T > > Builder< T > newUpdate( Collection< TimeLineMethod< T > > methods )
   {
      return new Builder< T >( Op.UPDATE, methods );
   }
   


   public final static < T extends IServerSyncable< T > > Builder< T > newDelete()
   {
      return new Builder< T >( Op.DELETE );
   }
   


   public final static < T extends IServerSyncable< T > > Builder< T > newDelete( IServerSyncOperation< T > operation )
   {
      return new Builder< T >( Op.DELETE ).add( operation );
   }
   


   public final static < T extends IServerSyncable< T > > Builder< T > newDelete( TimeLineMethod< T > method )
   {
      return new Builder< T >( Op.DELETE, method );
   }
   


   public final static < T extends IServerSyncable< T > > Builder< T > newDelete( Collection< TimeLineMethod< T > > methods )
   {
      return new Builder< T >( Op.DELETE, methods );
   }
}
