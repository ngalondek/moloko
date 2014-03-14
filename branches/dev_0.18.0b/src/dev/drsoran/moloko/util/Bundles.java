package dev.drsoran.moloko.util;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import dev.drsoran.Strings;


public final class Bundles
{
   private Bundles()
   {
      throw new AssertionError();
   }
   
   public final static String KEY_QUALIFIER_PARCABLE_ARRAY_LIST = "PAL_";
   
   public final static String KEY_QUALIFIER_SERIALIZABLE_ARRAY_LIST = "SAL_";
   
   
   
   public final static void put( Bundle bundle,
                                 String key,
                                 Object value,
                                 Class< ? > type )
   {
      if ( bundle == null )
      {
         throw new IllegalArgumentException( "bundle" );
      }
      
      if ( Strings.isNullOrEmpty( key ) )
      {
         throw new IllegalArgumentException( "key" );
      }
      
      if ( type == null )
      {
         throw new IllegalArgumentException( "type" );
      }
      
      if ( type == String.class )
      {
         bundle.putString( key, String.class.cast( value ) );
      }
      
      else if ( type == Integer.class || type == int.class )
      {
         bundle.putInt( key, Integer.class.cast( value ) );
      }
      
      else if ( type == Boolean.class || type == boolean.class )
      {
         bundle.putBoolean( key, Boolean.class.cast( value ) );
      }
      
      else if ( type == Bundle.class )
      {
         bundle.putBundle( key, Bundle.class.cast( value ) );
      }
      
      else if ( type == ArrayList.class )
      {
         if ( key.startsWith( KEY_QUALIFIER_PARCABLE_ARRAY_LIST ) )
         {
            @SuppressWarnings( "unchecked" )
            final ArrayList< Parcelable > cast = ArrayList.class.cast( value );
            bundle.putParcelableArrayList( key, cast );
         }
         else if ( key.startsWith( KEY_QUALIFIER_SERIALIZABLE_ARRAY_LIST ) )
         {
            @SuppressWarnings(
            { "rawtypes" } )
            final ArrayList cast = ArrayList.class.cast( value );
            bundle.putSerializable( key, cast );
         }
         else
         {
            @SuppressWarnings( "unchecked" )
            final ArrayList< String > cast = ArrayList.class.cast( value );
            bundle.putStringArrayList( key, cast );
         }
      }
      
      else if ( type == SparseArray.class )
      {
         @SuppressWarnings( "unchecked" )
         final SparseArray< ? extends Parcelable > cast = SparseArray.class.cast( value );
         bundle.putSparseParcelableArray( key, cast );
      }
      
      else if ( type == Long.class || type == long.class )
      {
         bundle.putLong( key, Long.class.cast( value ) );
      }
      
      else if ( type == Float.class || type == float.class )
      {
         bundle.putFloat( key, Float.class.cast( value ) );
      }
      
      else if ( type == String[].class )
      {
         bundle.putStringArray( key, String[].class.cast( value ) );
      }
      
      else if ( type == boolean[].class )
      {
         bundle.putBooleanArray( key, boolean[].class.cast( value ) );
      }
      
      else if ( Serializable.class.isAssignableFrom( type ) )
      {
         bundle.putSerializable( key, (Serializable) value );
      }
      
      else if ( Parcelable.class.isAssignableFrom( type ) )
      {
         bundle.putParcelable( key, (Parcelable) value );
      }
      
      else
      {
         throw new IllegalArgumentException( MessageFormat.format( "The type {0} is not supported to be put into a Bundle",
                                                                   type.getName() ) );
      }
   }
   
   
   
   public final static void put( Bundle bundle, String key, Object value )
   {
      if ( value == null )
      {
         throw new IllegalArgumentException( "value" );
      }
      
      put( bundle, key, value, value.getClass() );
   }
}
