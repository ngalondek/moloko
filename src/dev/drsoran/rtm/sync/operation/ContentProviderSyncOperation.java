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
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.domain.services.ContentRepository;


public class ContentProviderSyncOperation implements
         IContentProviderSyncOperation

{
   public static class Builder
   {
      private final SyncOperationType operationType;
      
      private final ArrayList< ContentProviderOperation > operations = new ArrayList< ContentProviderOperation >();
      
      
      
      public Builder( SyncOperationType operationType )
      {
         this.operationType = operationType;
      }
      
      
      
      public Builder( Builder other )
      {
         this.operationType = other.operationType;
         this.operations.addAll( other.operations );
      }
      
      
      
      public Builder( SyncOperationType operationType, ContentProviderOperation operation )
      {
         if ( operation == null )
            throw new NullPointerException();
         
         this.operationType = operationType;
         operations.add( operation );
      }
      
      
      
      public Builder( SyncOperationType operationType,
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
   
   private final ArrayList< ContentProviderOperation > operations;
   
   private final SyncOperationType operationType;
   
   
   
   private ContentProviderSyncOperation( Builder builder )
   {
      this.operationType = builder.operationType;
      this.operations = new ArrayList< ContentProviderOperation >( builder.operations );
      
      logOperations();
   }
   
   
   
   @Override
   public int getBatch( List< ContentProviderOperation > batch )
   {
      batch.addAll( operations );
      return operations.size();
   }
   
   
   
   @Override
   public SyncOperationType getOperationType()
   {
      return operationType;
   }
   
   
   
   @Override
   public int size()
   {
      return operations.size();
   }
   
   
   
   @Override
   public boolean applyTransactional( ContentRepository rtmProvider )
   {
      final TransactionalAccess transactionalAccess = rtmProvider.newTransactionalAccess();
      
      try
      {
         transactionalAccess.beginTransaction();
         
         rtmProvider.applyBatch( operations );
         
         transactionalAccess.setTransactionSuccessful();
      }
      catch ( Throwable e )
      {
         MolokoApp.Log.e( getClass(), LogUtils.GENERIC_DB_ERROR, e );
         return false;
      }
      finally
      {
         transactionalAccess.endTransaction();
      }
      
      return true;
   }
   
   
   
   public static void updateSyncResult( SyncResult syncResult,
                                        SyncOperationType operationType,
                                        int count )
   {
      switch ( operationType )
      {
         case Insert:
            syncResult.stats.numInserts += count;
            break;
         case Update:
            syncResult.stats.numUpdates += count;
            break;
         case Delete:
            syncResult.stats.numDeletes += count;
            break;
         default :
            break;
      }
   }
   
   
   
   public final static Builder fromType( SyncOperationType type )
   {
      switch ( type )
      {
         case Insert:
            return newInsert();
         case Update:
            return newUpdate();
         case Delete:
            return newDelete();
         default :
            return null;
      }
   }
   
   
   
   private void logOperations()
   {
      final StringBuilder stringBuilder = new StringBuilder( String.format( "%s: ",
                                                                            operationType ) );
      
      for ( ContentProviderOperation op : operations )
      {
         stringBuilder.append( op.toString() ).append( "; " );
      }
      
      MolokoApp.Log.d( getClass(), stringBuilder.toString() );
   }
   
   
   
   public final static Builder newInsert()
   {
      return new Builder( SyncOperationType.Insert );
   }
   
   
   
   public final static Builder newInsert( ContentProviderOperation operation )
   {
      return new Builder( SyncOperationType.Insert, operation );
   }
   
   
   
   public final static Builder newInsert( Collection< ContentProviderOperation > operations )
   {
      return new Builder( SyncOperationType.Insert, operations );
   }
   
   
   
   public final static Builder newInsert( IContentProviderSyncOperation operation )
   {
      return new Builder( SyncOperationType.Insert ).add( operation );
   }
   
   
   
   public final static Builder newUpdate()
   {
      return new Builder( SyncOperationType.Update );
   }
   
   
   
   public final static Builder newUpdate( ContentProviderOperation operation )
   {
      return new Builder( SyncOperationType.Update, operation );
   }
   
   
   
   public final static Builder newUpdate( Collection< ContentProviderOperation > operations )
   {
      return new Builder( SyncOperationType.Update, operations );
   }
   
   
   
   public final static Builder newUpdate( IContentProviderSyncOperation operation )
   {
      return new Builder( SyncOperationType.Update ).add( operation );
   }
   
   
   
   public final static Builder newDelete()
   {
      return new Builder( SyncOperationType.Delete );
   }
   
   
   
   public final static Builder newDelete( ContentProviderOperation operation )
   {
      return new Builder( SyncOperationType.Delete, operation );
   }
   
   
   
   public final static Builder newDelete( Collection< ContentProviderOperation > operations )
   {
      return new Builder( SyncOperationType.Delete, operations );
   }
   
   
   
   public final static Builder newDelete( IContentProviderSyncOperation operation )
   {
      return new Builder( SyncOperationType.Delete ).add( operation );
   }
   
}
