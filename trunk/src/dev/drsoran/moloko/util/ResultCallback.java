package dev.drsoran.moloko.util;

import android.os.Bundle;


public abstract class ResultCallback< V > implements Runnable
{
   public V result = null;
   
   public Bundle extraData = null;
   
   public Exception exception = null;
   
   

   public ResultCallback()
   {
      
   }
   


   public ResultCallback( final Bundle extraData )
   {
      this.extraData = extraData;
   }
   


   public ResultCallback< V > setResult( V result )
   {
      this.result = result;
      this.exception = null;
      return this;
   }
   


   public ResultCallback< V > setResult( V result, Exception exception )
   {
      this.result = result;
      this.exception = exception;
      return this;
   }
}
