package dev.drsoran.moloko.service.sync.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class PropertyAdapter< T1, T2 >
{
   private final Method t1PropertyGetter;
   
   private final Method t2PropertyGetter;
   
   

   public PropertyAdapter( Method t1Get, Method t2Get )
   {
      this.t1PropertyGetter = t1Get;
      this.t2PropertyGetter = t2Get;
   }
   


   public boolean equals( T1 instance1, T2 instance2 ) throws InvocationTargetException
   {
      try
      {
         return t1PropertyGetter.invoke( instance1, (Object[]) null )
                                .equals( t2PropertyGetter.invoke( instance2,
                                                                  (Object[]) null ) );
      }
      catch ( IllegalArgumentException e )
      {
         throw new InvocationTargetException( e );
      }
      catch ( IllegalAccessException e )
      {
         throw new InvocationTargetException( e );
      }
   }
}
