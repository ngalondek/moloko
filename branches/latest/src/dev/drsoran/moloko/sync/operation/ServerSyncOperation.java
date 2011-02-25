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
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import android.util.Log;

import com.mdt.rtm.Service;
import com.mdt.rtm.Service.MethodCallType;
import com.mdt.rtm.ServiceException;
import com.mdt.rtm.TimeLineMethod;
import com.mdt.rtm.TimeLineResult;

import dev.drsoran.moloko.content.IProviderPart;
import dev.drsoran.moloko.content.ModificationList;
import dev.drsoran.moloko.content.RtmProvider;
import dev.drsoran.moloko.sync.syncable.IContentProviderSyncable;
import dev.drsoran.moloko.sync.syncable.IServerSyncable;


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
         this( operationType );
         add( method );
      }
      


      public Builder( Op operationType,
         Collection< TimeLineMethod< T > > methods )
      {
         this( operationType );
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
   
   private T resultElement;
   
   private List< TimeLineResult.Transaction > transactions;
   
   

   private ServerSyncOperation( Builder< T > builder )
   {
      this.operationType = builder.operationType;
      this.serviceMethods = Collections.unmodifiableList( new ArrayList< TimeLineMethod< T > >( builder.methods ) );
   }
   


   @SuppressWarnings( "unchecked" )
   public List< IContentProviderSyncOperation > execute( RtmProvider provider ) throws ServiceException
   {
      transactions = new LinkedList< TimeLineResult.Transaction >();
      
      for ( Iterator< TimeLineMethod< T > > i = serviceMethods.iterator(); i.hasNext(); )
      {
         final TimeLineMethod< T > method = i.next();
         
         try
         {
            if ( i.hasNext() )
               // We retrieve the result only for the last element.
               transactions.add( method.call( MethodCallType.NO_RESULT ).transaction );
            else
            {
               final TimeLineResult< T > res = method.call( MethodCallType.WITH_RESULT );
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
      
      // If the result element is IContentProviderSyncable we update our local element
      // with the received server element. This will merge all changes we applied to the
      // server and the server had.
      if ( resultElement instanceof IContentProviderSyncable )
      {
         List< IContentProviderSyncOperation > operations = null;
         final IContentProviderSyncable< ? > cpSyncableResultElement = (IContentProviderSyncable< ? >) resultElement;
         
         // Retrieve the Provider part that can handle the element.
         final IProviderPart part = provider.getPart( cpSyncableResultElement.getContentUriWithId() );
         
         if ( part != null )
         {
            // Retrieve the local element from the provider part.
            final Object localElement = part.getElement( cpSyncableResultElement.getContentUriWithId() );
            
            if ( localElement != null )
            {
               if ( localElement.getClass() == resultElement.getClass() )
               {
                  @SuppressWarnings( "rawtypes" )
                  final IContentProviderSyncable cpSyncableLocalElement = (IContentProviderSyncable) localElement;
                  operations = cpSyncableLocalElement.computeContentProviderUpdateOperations( new Date(),
                                                                                              cpSyncableResultElement );
               }
               else
               {
                  Log.e( TAG, "Local element "
                     + localElement.getClass().getSimpleName()
                     + " is not of class "
                     + resultElement.getClass().getSimpleName() );
               }
            }
            else
            {
               Log.e( TAG, "No local element retrieved for URI "
                  + cpSyncableResultElement.getContentUriWithId() );
            }
         }
         else
         {
            Log.e( TAG, "Found no IProviderPart for URI "
               + cpSyncableResultElement.getContentUriWithId() );
         }
         
         if ( operations != null )
            return Collections.unmodifiableList( operations );
         else
            return Collections.emptyList();
      }
      else
      {
         return Collections.emptyList();
      }
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
   


   public IContentProviderSyncOperation removeModification( ModificationList modifications )
   {
      if ( resultElement != null )
         return resultElement.computeRemoveModificationsOperation( modifications );
      else
         return NoopContentProviderSyncOperation.INSTANCE;
   }
   


   public Op getOperationType()
   {
      return operationType;
   }
   


   public List< TimeLineMethod< T >> getMethods()
   {
      return this.serviceMethods;
   }
   


   public final static < T extends IServerSyncable< T > > Builder< T > fromType( ISyncOperation.Op type )
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
