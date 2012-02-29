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
   
   public final static String KEY_QUALIFIER_PARCABLE_ARRAY_LIST = "PAL_";
   
   
   
   public final static < V > void put( Bundle bundle,
                                       String key,
                                       V value,
                                       Class< V > type )
   {
      put( bundle, key, value );
   }
   
   
   
   public final static void put( Bundle bundle, String key, Object value )
   {
      if ( value != null )
      {
         final Class< ? > valueClass = value.getClass();
         
         if ( valueClass == String.class )
         {
            bundle.putString( key, String.class.cast( value ) );
         }
         
         else if ( valueClass == Long.class )
         {
            bundle.putLong( key, Long.class.cast( value ) );
         }
         
         else if ( valueClass == Integer.class )
         {
            bundle.putInt( key, Integer.class.cast( value ) );
         }
         
         else if ( valueClass == Boolean.class )
         {
            bundle.putBoolean( key, Boolean.class.cast( value ) );
         }
         
         else if ( valueClass == Bundle.class )
         {
            bundle.putBundle( key, Bundle.class.cast( value ) );
         }
         
         else if ( valueClass == Float.class )
         {
            bundle.putFloat( key, Float.class.cast( value ) );
         }
         
         else if ( valueClass == Double.class )
         {
            bundle.putDouble( key, Double.class.cast( value ) );
         }
         
         else if ( valueClass == ArrayList.class )
         {
            if ( key.startsWith( KEY_QUALIFIER_PARCABLE_ARRAY_LIST ) )
            {
               @SuppressWarnings( "unchecked" )
               final ArrayList< Parcelable > cast = ArrayList.class.cast( value );
               bundle.putParcelableArrayList( key, cast );
            }
            else
            {
               @SuppressWarnings( "unchecked" )
               final ArrayList< String > cast = ArrayList.class.cast( value );
               bundle.putStringArrayList( key, cast );
            }
         }
         
         else if ( valueClass == String[].class )
         {
            bundle.putStringArray( key, String[].class.cast( value ) );
         }
         
         else if ( valueClass == boolean[].class )
         {
            bundle.putBooleanArray( key, boolean[].class.cast( value ) );
         }
         
         else
         {
            try
            {
               final Parcelable parcelable = Parcelable.class.cast( value );
               bundle.putParcelable( key, parcelable );
            }
            catch ( ClassCastException e )
            {
               throw new IllegalArgumentException( "The type "
                  + valueClass.getName() + " is not supported" );
            }
         }
      }
   }
}
