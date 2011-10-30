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
      if ( type == String.class )
      {
         bundle.putString( key, String.class.cast( value ) );
         return;
      }
      
      else if ( type == Long.class )
      {
         bundle.putLong( key, Long.class.cast( value ) );
         return;
      }
      
      else if ( type == Integer.class )
      {
         bundle.putInt( key, Integer.class.cast( value ) );
         return;
      }
      
      else if ( type == Boolean.class )
      {
         bundle.putBoolean( key, Boolean.class.cast( value ) );
         return;
      }
      
      else if ( type == Bundle.class )
      {
         bundle.putBundle( key, Bundle.class.cast( value ) );
         return;
      }
      
      else if ( type == Float.class )
      {
         bundle.putFloat( key, Float.class.cast( value ) );
         return;
      }
      
      else if ( type == Double.class )
      {
         bundle.putDouble( key, Double.class.cast( value ) );
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
