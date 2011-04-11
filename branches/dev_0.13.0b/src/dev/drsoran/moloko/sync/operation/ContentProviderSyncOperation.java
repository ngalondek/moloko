/*
 * Copyright (c) 2010 Ronny Röhricht
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

package dev.drsoran.moloko.sync.operation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import android.content.ContentProviderOperation;
import android.content.SyncResult;
import android.util.Log;
import dev.drsoran.moloko.content.RtmProvider;
import dev.drsoran.moloko.content.TransactionalAccess;
import dev.drsoran.moloko.util.LogUtils;


public class ContentProviderSyncOperation implements
         IContentProviderSyncOperation

{
   public static class Builder
   {
      private final Op operationType;
      
      private final List< ContentProviderOperation > operations = new ArrayList< ContentProviderOperation >();
      
      

      public Builder( Op operationType )
      {
         this.operationType = operationType;
      }
      


      public Builder( Builder other )
      {
         this.operationType = other.operationType;
         this.operations.addAll( other.operations );
      }
      


      public Builder( Op operationType, ContentProviderOperation operation )
      {
         if ( operation == null )
            throw new NullPointerException();
         
         this.operationType = operationType;
         operations.add( operation );
      }
      


      public Builder( Op operationType,
         Collection< ContentProviderOperation > operations )
      {
         if ( operations == null )
            throw new NullPointerException();
         
         this.operationType = operationType;
         this.operations.addAll( operations );
      }
      


      public Builder add( ContentProviderOperation operation )
      {
         if ( operation == null )
            throw new NullPointerException( "operation is null" );
         
         operations.add( operation );
         return this;
      }
      


      public Builder addAll( Collection< ? extends ContentProviderOperation > operations )
      {
         if ( operations == null )
            throw new NullPointerException( "operations are null" );
         
         for ( ContentProviderOperation contentProviderOperation : operations )
         {
            add( contentProviderOperation );
         }
         
         return this;
      }
      


      public Builder add( IContentProviderSyncOperation operation )
      {
         if ( !( operation instanceof INoopSyncOperation ) )
         {
            final List< ContentProviderOperation > batch = new ArrayList< ContentProviderOperation >( operation.size() );
            operation.getBatch( batch );
            
            for ( ContentProviderOperation contentProviderOperation : batch )
            {
               add( contentProviderOperation );
            }
         }
         
         return this;
      }
      


      public Builder add( Collection< ? extends IContentProviderSyncOperation > operations )
      {
         for ( IContentProviderSyncOperation operation : operations )
         {
            if ( !( operation instanceof INoopSyncOperation ) )
               add( operation );
         }
         
         return this;
      }
      


      public IContentProviderSyncOperation build()
      {
         if ( operations.size() == 0 )
            return NoopContentProviderSyncOperation.INSTANCE;
         else
            return new ContentProviderSyncOperation( this );
      }
      


      public List< IContentProviderSyncOperation > asList()
      {
         if ( operations.size() == 0 )
            return Collections.emptyList();
         else
            return Collections.singletonList( build() );
      }
   }
   
   private final List< ContentProviderOperation > operations;
   
   private final Op operationType;
   
   

   private ContentProviderSyncOperation( Builder builder )
   {
      this.operationType = builder.operationType;
      this.operations = Collections.unmodifiableList( new ArrayList< ContentProviderOperation >( builder.operations ) );
   }
   


   public int getBatch( List< ContentProviderOperation > batch )
   {
      batch.addAll( operations );
      return operations.size();
   }
   


   public Op getOperationType()
   {
      return operationType;
   }
   


   public int size()
   {
      return operations.size();
   }
   


   public boolean applyTransactional( RtmProvider rtmProvider )
   {
      final TransactionalAccess transactionalAccess = rtmProvider.newTransactionalAccess();
      
      try
      {
         transactionalAccess.beginTransaction();
         
         rtmProvider.applyBatch( new ArrayList< ContentProviderOperation >( operations ) );
         
         transactionalAccess.setTransactionSuccessful();
      }
      catch ( Throwable e )
      {
         Log.e( LogUtils.toTag( ContentProviderSyncOperation.class ),
                LogUtils.GENERIC_DB_ERROR,
                e );
         return false;
      }
      finally
      {
         transactionalAccess.endTransaction();
      }
      
      return true;
   }
   


   public static void updateSyncResult( SyncResult syncResult,
                                        Op operationType,
                                        int count )
   {
      switch ( operationType )
      {
         case INSERT:
            syncResult.stats.numInserts += count;
            break;
         case UPDATE:
            syncResult.stats.numUpdates += count;
            break;
         case DELETE:
            syncResult.stats.numDeletes += count;
            break;
         default :
            break;
      }
   }
   


   public final static Builder fromType( ISyncOperation.Op type )
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
   


   public final static Builder newInsert()
   {
      return new Builder( Op.INSERT );
   }
   


   public final static Builder newInsert( ContentProviderOperation operation )
   {
      return new Builder( Op.INSERT, operation );
   }
   


   public final static Builder newInsert( Collection< ContentProviderOperation > operations )
   {
      return new Builder( Op.INSERT, operations );
   }
   


   public final static Builder newInsert( IContentProviderSyncOperation operation )
   {
      return new Builder( Op.INSERT ).add( operation );
   }
   


   public final static Builder newUpdate()
   {
      return new Builder( Op.UPDATE );
   }
   


   public final static Builder newUpdate( ContentProviderOperation operation )
   {
      return new Builder( Op.UPDATE, operation );
   }
   


   public final static Builder newUpdate( Collection< ContentProviderOperation > operations )
   {
      return new Builder( Op.UPDATE, operations );
   }
   


   public final static Builder newUpdate( IContentProviderSyncOperation operation )
   {
      return new Builder( Op.UPDATE ).add( operation );
   }
   


   public final static Builder newDelete()
   {
      return new Builder( Op.DELETE );
   }
   


   public final static Builder newDelete( ContentProviderOperation operation )
   {
      return new Builder( Op.DELETE, operation );
   }
   


   public final static Builder newDelete( Collection< ContentProviderOperation > operations )
   {
      return new Builder( Op.DELETE, operations );
   }
   


   public final static Builder newDelete( IContentProviderSyncOperation operation )
   {
      return new Builder( Op.DELETE ).add( operation );
   }
   
}
