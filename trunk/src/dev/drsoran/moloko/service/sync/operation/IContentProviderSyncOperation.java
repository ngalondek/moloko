package dev.drsoran.moloko.service.sync.operation;

import java.util.ArrayList;

import android.content.ContentProviderOperation;


public interface IContentProviderSyncOperation extends ISyncOperation
{
   public final static class Op
   {
      public final static int NOOP = 0;
      
      public final static int INSERT = 1;
      
      public final static int UPDATE = 2;
      
      public final static int DELETE = 3;
      
      

      public final static String toString( int op )
      {
         switch ( op )
         {
            case NOOP:
               return "NOOP";
            case INSERT:
               return "INSERT";
            case UPDATE:
               return "UPDATE";
            case DELETE:
               return "DELETE";
            default :
               return "UNKNOWN";
         }
      }
   }
   
   

   public int getBatch( ArrayList< ContentProviderOperation > batch );
   


   public int getOperationType();
}
