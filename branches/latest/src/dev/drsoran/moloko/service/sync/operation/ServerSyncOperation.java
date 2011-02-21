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
import com.mdt.rtm.Service.MethodCallType;
import com.mdt.rtm.ServiceException;
import com.mdt.rtm.TimeLineMethod;

import dev.drsoran.moloko.content.ModificationList;
import dev.drsoran.moloko.service.sync.syncable.IContentProviderSyncable;
import dev.drsoran.moloko.service.sync.syncable.IServerSyncable;


public class ServerSyncOperation< T extends IServerSyncable< T > > implements
         IServerSyncOperation< T >
{
   private final static String TAG = "Moloko."
      + ServerSyncOperation.class.getSimpleName();
   
   
   public static class Builder< T extends IServerSyncable< T >>
   {
      private final Op operationType;
      
      private final List< TimeLineMethod< T > > methods = new ArrayList< TimeLineMethod< T > >();
      
      private final T sourceElement;
      
      

      public Builder( Op operationType, T sourceElement )
      {
         if ( sourceElement == null )
            throw new NullPointerException( "sourceElement is null" );
         
         this.operationType = operationType;
         this.sourceElement = sourceElement;
      }
      


      public Builder( Builder< T > other )
      {
         this.operationType = other.operationType;
         this.methods.addAll( other.methods );
         this.sourceElement = other.sourceElement;
      }
      


      public Builder( Op operationType, T sourceElement,
         TimeLineMethod< T > method )
      {
         this( operationType, sourceElement );
         add( method );
      }
      


      public Builder( Op operationType, T sourceElement,
         Collection< TimeLineMethod< T > > methods )
      {
         this( operationType, sourceElement );
         add( methods );
      }
      


      public Builder< T > add( TimeLineMethod< T > method )
      {
         if ( method == null )
            throw new NullPointerException( "method is null" );
         
         methods.add( method );
         return this;
      }
      


      public Builder< T > add( Collection< ? extends TimeLineMethod< T > > methods )
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
            return new ServerSyncOperation< T >( this );
      }
   }
   
   private final Op operationType;
   
   private final List< TimeLineMethod< T > > serviceMethods;
   
   private T sourceElement;
   
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
      
      if ( sourceElement instanceof IContentProviderSyncable
         && resultElement instanceof IContentProviderSyncable )
      {
         @SuppressWarnings( "rawtypes" )
         final IContentProviderSyncable sourceSyncable = (IContentProviderSyncable) sourceElement;
         @SuppressWarnings( "rawtypes" )
         final IContentProviderSyncable resultSyncable = (IContentProviderSyncable) resultElement;
         
         @SuppressWarnings( "unchecked" )
         final IContentProviderSyncOperation computeContentProviderUpdateOperation = sourceSyncable.computeContentProviderUpdateOperation( resultSyncable );
         
         return computeContentProviderUpdateOperation;
      }
      else
         return NoopContentProviderSyncOperation.INSTANCE;
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
   


   public final static < T extends IServerSyncable< T > > Builder< T > newInsert( T sourceElement )
   {
      return new Builder< T >( Op.INSERT, sourceElement );
   }
   


   public final static < T extends IServerSyncable< T > > Builder< T > newInsert( T sourceElement,
                                                                                  IServerSyncOperation< T > operation )
   {
      return new Builder< T >( Op.INSERT, sourceElement ).add( operation );
   }
   


   public final static < T extends IServerSyncable< T > > Builder< T > newInsert( T sourceElement,
                                                                                  TimeLineMethod< T > method )
   {
      return new Builder< T >( Op.INSERT, sourceElement, method );
   }
   


   public final static < T extends IServerSyncable< T > > Builder< T > newInsert( T sourceElement,
                                                                                  Collection< TimeLineMethod< T > > methods )
   {
      return new Builder< T >( Op.INSERT, sourceElement, methods );
   }
   


   public final static < T extends IServerSyncable< T > > Builder< T > newUpdate( T sourceElement )
   {
      return new Builder< T >( Op.UPDATE, sourceElement );
   }
   


   public final static < T extends IServerSyncable< T > > Builder< T > newUpdate( T sourceElement,
                                                                                  IServerSyncOperation< T > operation )
   {
      return new Builder< T >( Op.UPDATE, sourceElement ).add( operation );
   }
   


   public final static < T extends IServerSyncable< T > > Builder< T > newUpdate( T sourceElement,
                                                                                  TimeLineMethod< T > method )
   {
      return new Builder< T >( Op.UPDATE, sourceElement, method );
   }
   


   public final static < T extends IServerSyncable< T > > Builder< T > newUpdate( T sourceElement,
                                                                                  Collection< TimeLineMethod< T > > methods )
   {
      return new Builder< T >( Op.UPDATE, sourceElement, methods );
   }
   


   public final static < T extends IServerSyncable< T > > Builder< T > newDelete( T sourceElement )
   {
      return new Builder< T >( Op.DELETE, sourceElement );
   }
   


   public final static < T extends IServerSyncable< T > > Builder< T > newDelete( T sourceElement,
                                                                                  IServerSyncOperation< T > operation )
   {
      return new Builder< T >( Op.DELETE, sourceElement ).add( operation );
   }
   


   public final static < T extends IServerSyncable< T > > Builder< T > newDelete( T sourceElement,
                                                                                  TimeLineMethod< T > method )
   {
      return new Builder< T >( Op.DELETE, sourceElement, method );
   }
   


   public final static < T extends IServerSyncable< T > > Builder< T > newDelete( T sourceElement,
                                                                                  Collection< TimeLineMethod< T > > methods )
   {
      return new Builder< T >( Op.DELETE, sourceElement, methods );
   }
}
