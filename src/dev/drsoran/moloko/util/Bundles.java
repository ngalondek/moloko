package dev.drsoran.moloko.util;

import android.os.Bundle;
import android.os.Parcelable;


public final class Bundles
{
   private Bundles()
   {
      throw new AssertionError();
   }
   


   public final static < V > void put( Bundle bundle,
                                       String key,
                                       V value,
                                       Class< V > type )
   {
      if ( type == Boolean.TYPE )
      {
         bundle.putBoolean( key, Boolean.TYPE.cast( value ) );
         return;
      }
      
      else if ( type == Bundle.class )
      {
         bundle.putBundle( key, Bundle.class.cast( value ) );
         return;
      }
      
      else if ( type == Integer.TYPE )
      {
         bundle.putInt( key, Integer.TYPE.cast( value ) );
         return;
      }
      
      else if ( type == Float.TYPE )
      {
         bundle.putFloat( key, Float.TYPE.cast( value ) );
         return;
      }
      
      else if ( type == Double.TYPE )
      {
         bundle.putDouble( key, Double.TYPE.cast( value ) );
         return;
      }
      
      else if ( type == String.class )
      {
         bundle.putString( key, String.class.cast( value ) );
         return;
      }
      
      else
      {
         final Class< ? >[] interfaces = type.getInterfaces();
         if ( interfaces != null )
         {
            for ( int i = 0; i < interfaces.length; i++ )
            {
               if ( interfaces[ i ] == Parcelable.class )
               {
                  bundle.putParcelable( key, Parcelable.class.cast( value ) );
                  return;
               }
            }
         }
      }
      
      throw new IllegalArgumentException( "The type " + type.getName()
         + " is not supported" );
   }
}
