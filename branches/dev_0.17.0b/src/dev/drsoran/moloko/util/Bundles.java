package dev.drsoran.moloko.util;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Parcelable;


public final class Bundles
{
   private Bundles()
   {
      throw new AssertionError();
   }
   
   
   
   public final static void put( Bundle bundle, String key, Object value )
   {
      final Class< ? > valueClass = value.getClass();
      
      if ( valueClass == String.class )
      {
         bundle.putString( key, String.class.cast( value ) );
         return;
      }
      
      else if ( valueClass == Long.class )
      {
         bundle.putLong( key, Long.class.cast( value ) );
         return;
      }
      
      else if ( valueClass == Integer.class )
      {
         bundle.putInt( key, Integer.class.cast( value ) );
         return;
      }
      
      else if ( valueClass == Boolean.class )
      {
         bundle.putBoolean( key, Boolean.class.cast( value ) );
         return;
      }
      
      else if ( valueClass == Bundle.class )
      {
         bundle.putBundle( key, Bundle.class.cast( value ) );
         return;
      }
      
      else if ( valueClass == Float.class )
      {
         bundle.putFloat( key, Float.class.cast( value ) );
         return;
      }
      
      else if ( valueClass == Double.class )
      {
         bundle.putDouble( key, Double.class.cast( value ) );
         return;
      }
      
      else if ( valueClass == ArrayList.class )
      {
         @SuppressWarnings( "unchecked" )
         ArrayList< String > cast = ArrayList.class.cast( value );
         bundle.putStringArrayList( key, cast );
         return;
      }
      
      else if ( valueClass == boolean[].class )
      {
         bundle.putBooleanArray( key, boolean[].class.cast( value ) );
         return;
      }
      
      else
      {
         final Class< ? >[] interfaces = valueClass.getInterfaces();
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
      
      throw new IllegalArgumentException( "The type " + valueClass.getName()
         + " is not supported" );
   }
   
   
   
   public final static < V > void put( Bundle bundle,
                                       String key,
                                       V value,
                                       Class< V > type )
   {
      put( bundle, key, value );
   }
}
