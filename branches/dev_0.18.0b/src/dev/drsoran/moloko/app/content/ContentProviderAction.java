/*
 * Copyright (c) 2011 Ronny Röhricht
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

package dev.drsoran.moloko.app.content;

import java.util.ArrayList;
import java.util.Collection;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import dev.drsoran.moloko.content.ContentRepository;
import dev.drsoran.moloko.content.TransactionalAccess;
import dev.drsoran.moloko.domain.services.ContentException;
import dev.drsoran.provider.Rtm;


public class ContentProviderAction implements IContentProviderActionItem

{
   public enum Type
   {
      INSERT, UPDATE, DELETE
   }
   
   
   public static class Builder
   {
      private final Type operationType;
      
      private final ArrayList< ContentProviderOperation > operations = new ArrayList< ContentProviderOperation >();
      
      
      
      public Builder( Type operationType )
      {
         this.operationType = operationType;
      }
      
      
      
      public Builder( Builder other )
      {
         this.operationType = other.operationType;
         this.operations.addAll( other.operations );
      }
      
      
      
      public Builder( Type operationType, ContentProviderOperation operation )
      {
         if ( operation == null )
            throw new NullPointerException();
         
         this.operationType = operationType;
         operations.add( operation );
      }
      
      
      
      public Builder( Type operationType,
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
      
      
      
      public ContentProviderAction build()
      {
         return new ContentProviderAction( this );
      }
   }
   
   private final ArrayList< ContentProviderOperation > operations;
   
   private final Type operationType;
   
   
   
   private ContentProviderAction( Builder builder )
   {
      this.operationType = builder.operationType;
      this.operations = new ArrayList< ContentProviderOperation >( builder.operations );
   }
   
   
   
   @Override
   public Iterable< ContentProviderOperation > getOperations()
   {
      return operations;
   }
   
   
   
   @Override
   public int addOperationsToBatch( Collection< ? super ContentProviderOperation > batch )
   {
      for ( ContentProviderOperation operation : operations )
      {
         batch.add( operation );
      }
      
      return operations.size();
   }
   
   
   
   @Override
   public Type getActionType()
   {
      return operationType;
   }
   
   
   
   @Override
   public int getOperationsCount()
   {
      return operations.size();
   }
   
   
   
   @Override
   public void apply( ContentResolver contentResolver ) throws ContentException
   {
      ContentProviderClient client = null;
      
      try
      {
         client = contentResolver.acquireContentProviderClient( Rtm.AUTHORITY );
         client.applyBatch( operations );
      }
      catch ( Throwable e )
      {
         throw new ContentException( e );
      }
      finally
      {
         if ( client != null )
         {
            client.release();
         }
      }
   }
   
   
   
   @Override
   public void applyTransactional( ContentRepository rtmProvider ) throws ContentException
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
         throw new ContentException( e );
      }
      finally
      {
         transactionalAccess.endTransaction();
      }
   }
   
   
   
   public final static Builder fromType( Type type )
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
      return new Builder( Type.INSERT );
   }
   
   
   
   public final static Builder newInsert( ContentProviderOperation operation )
   {
      return new Builder( Type.INSERT, operation );
   }
   
   
   
   public final static Builder newInsert( Collection< ContentProviderOperation > operations )
   {
      return new Builder( Type.INSERT, operations );
   }
   
   
   
   public final static Builder newUpdate()
   {
      return new Builder( Type.UPDATE );
   }
   
   
   
   public final static Builder newUpdate( ContentProviderOperation operation )
   {
      return new Builder( Type.UPDATE, operation );
   }
   
   
   
   public final static Builder newUpdate( Collection< ContentProviderOperation > operations )
   {
      return new Builder( Type.UPDATE, operations );
   }
   
   
   
   public final static Builder newDelete()
   {
      return new Builder( Type.DELETE );
   }
   
   
   
   public final static Builder newDelete( ContentProviderOperation operation )
   {
      return new Builder( Type.DELETE, operation );
   }
   
   
   
   public final static Builder newDelete( Collection< ContentProviderOperation > operations )
   {
      return new Builder( Type.DELETE, operations );
   }
}
