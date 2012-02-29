/* 
 *	Copyright (c) 2012 Ronny Röhricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.util.Bundles;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.moloko.util.Strings;


public class AnnotatedConfigurationSupport
{
   private HashMap< Object, List< Pair< Field, InstanceState >>> registeredInstances;
   
   
   
   public < T > void registerInstance( T instance, Class< T > clazz )
   {
      registerInstance( instance, clazz, null );
   }
   
   
   
   public < T > void registerInstance( T instance,
                                       Class< T > clazz,
                                       Bundle instanceState )
   {
      createAnnotatedInstanceStateFor( instance, clazz );
      
      if ( instanceState != null )
      {
         setInstanceState( instance, instanceState );
      }
      else
      {
         setDefaultInstanceState( instance );
      }
   }
   
   
   
   public void onSaveInstanceStates( Bundle outState )
   {
      if ( registeredInstances != null )
      {
         for ( Object instance : registeredInstances.keySet() )
         {
            onSaveInstanceState( instance, outState );
         }
      }
   }
   
   
   
   public void onSaveInstanceState( Object instance, Bundle outState )
   {
      final Bundle instanceState = getInstanceState( instance );
      outState.putAll( instanceState );
   }
   
   
   
   public void onRestoreInstanceStates( Bundle state )
   {
      if ( registeredInstances != null )
      {
         for ( Object instance : registeredInstances.keySet() )
         {
            onRestoreInstanceState( instance, state );
         }
      }
   }
   
   
   
   public void onRestoreInstanceState( Object instance, Bundle state )
   {
      setInstanceState( instance, state );
   }
   
   
   
   public Bundle getInstanceStates()
   {
      final Bundle mergedState = new Bundle();
      if ( registeredInstances != null )
      {
         for ( Object instance : registeredInstances.keySet() )
         {
            mergedState.putAll( getInstanceState( instance ) );
         }
      }
      
      return mergedState;
   }
   
   
   
   public Bundle getInstanceState( Object instance )
   {
      final Bundle instanceState = new Bundle();
      
      if ( registeredInstances != null )
      {
         final List< Pair< Field, InstanceState >> annotatedFields = getAnnotatedInstanceStateFor( instance );
         for ( Pair< Field, InstanceState > annotatedField : annotatedFields )
         {
            getValue( instance,
                      instanceState,
                      annotatedField.first,
                      annotatedField.second.key() );
         }
      }
      
      return instanceState;
   }
   
   
   
   public void setInstanceStates( Bundle instanceState )
   {
      if ( registeredInstances != null )
      {
         for ( Object instance : registeredInstances.keySet() )
         {
            setInstanceState( instance, instanceState );
         }
      }
   }
   
   
   
   public void setInstanceState( Object instance, Bundle instanceState )
   {
      if ( registeredInstances != null )
      {
         final List< Pair< Field, InstanceState >> annotatedFields = getAnnotatedInstanceStateFor( instance );
         for ( Pair< Field, InstanceState > annotatedField : annotatedFields )
         {
            setValue( instance,
                      instanceState,
                      annotatedField.first,
                      annotatedField.second.key() );
         }
      }
   }
   
   
   
   public void setDefaultInstanceStates()
   {
      if ( registeredInstances != null )
      {
         for ( Object instance : registeredInstances.keySet() )
         {
            setDefaultInstanceState( instance );
         }
      }
   }
   
   
   
   public void setDefaultInstanceState( Object instance )
   {
      if ( registeredInstances != null )
      {
         final List< Pair< Field, InstanceState >> annotatedFields = getAnnotatedInstanceStateFor( instance );
         
         for ( Pair< Field, InstanceState > annotatedField : annotatedFields )
         {
            final InstanceState annotation = annotatedField.second;
            
            if ( !annotation.defaultValue()
                            .equalsIgnoreCase( InstanceState.NO_DEFAULT ) )
            {
               final Field fieldToSet = annotatedField.first;
               final Object defaultValue = getDefaultValueInstance( fieldToSet,
                                                                    annotation );
               
               setInstaceFieldValue( instance, fieldToSet, defaultValue );
            }
         }
      }
   }
   
   
   
   private static Object getDefaultValueInstance( Field field,
                                                  InstanceState annotation )
   {
      final String defaultValue = annotation.defaultValue();
      final Class< ? > defaultValueType = field.getType();
      
      if ( defaultValue.equalsIgnoreCase( InstanceState.NULL ) )
      {
         return null;
      }
      
      else if ( defaultValue.equalsIgnoreCase( InstanceState.NEW ) )
      {
         try
         {
            return defaultValueType.newInstance();
         }
         catch ( InstantiationException e )
         {
            throw new RuntimeException( String.format( "$s could not be instantiated.",
                                                       defaultValueType.getName() ),
                                        e );
         }
         catch ( IllegalAccessException e )
         {
            throw new RuntimeException( String.format( "$s could not be instantiated.",
                                                       defaultValueType.getName() ),
                                        e );
         }
      }
      else
      {
         return Strings.convertTo( defaultValue, defaultValueType );
      }
   }
   
   
   
   private List< Pair< Field, InstanceState >> getAnnotatedInstanceStateFor( Object instance )
   {
      return registeredInstances.get( instance );
   }
   
   
   
   private < T > List< Pair< Field, InstanceState >> createAnnotatedInstanceStateFor( T instance,
                                                                                      Class< T > clazz )
   {
      if ( registeredInstances == null )
      {
         registeredInstances = new HashMap< Object, List< Pair< Field, InstanceState >> >();
      }
      
      final List< Pair< Field, InstanceState >> annotatedInstanceState = collectAnnotatedFields( instance,
                                                                                                 clazz );
      final List< Pair< Field, InstanceState >> baseClassInstanceState = registeredInstances.get( instance );
      
      if ( baseClassInstanceState != null )
      {
         baseClassInstanceState.addAll( annotatedInstanceState );
      }
      else
      {
         registeredInstances.put( instance, annotatedInstanceState );
      }
      
      return annotatedInstanceState;
   }
   
   
   
   private void getValue( Object instance,
                          Bundle instanceState,
                          Field field,
                          String key )
   {
      final Object currentValue = getInstanceFieldValue( instance, field );
      Bundles.put( instanceState, key, currentValue );
   }
   
   
   
   private void setValue( Object instance,
                          Bundle instanceState,
                          Field field,
                          String key )
   {
      if ( instanceState.containsKey( key ) )
      {
         final Object savedValue = instanceState.get( key );
         setInstaceFieldValue( instance, field, savedValue );
      }
   }
   
   
   
   private Object getInstanceFieldValue( Object instance, Field field )
   {
      final boolean access = field.isAccessible();
      try
      {
         field.setAccessible( true );
         final Object value = field.get( instance );
         return value;
      }
      catch ( IllegalArgumentException e )
      {
         Log.e( LogUtils.toTag( AnnotatedConfigurationSupport.class ),
                Strings.EMPTY_STRING,
                e );
         throw e;
      }
      catch ( IllegalAccessException e )
      {
         Log.e( LogUtils.toTag( AnnotatedConfigurationSupport.class ),
                Strings.EMPTY_STRING,
                e );
         throw new RuntimeException( e );
      }
      finally
      {
         field.setAccessible( access );
      }
   }
   
   
   
   private void setInstaceFieldValue( Object instance, Field field, Object value )
   {
      final boolean access = field.isAccessible();
      try
      {
         field.setAccessible( true );
         field.set( instance, value );
      }
      catch ( IllegalArgumentException e )
      {
         Log.e( LogUtils.toTag( AnnotatedConfigurationSupport.class ),
                Strings.EMPTY_STRING,
                e );
         throw e;
      }
      catch ( IllegalAccessException e )
      {
         Log.e( LogUtils.toTag( AnnotatedConfigurationSupport.class ),
                Strings.EMPTY_STRING,
                e );
         throw new RuntimeException( e );
      }
      finally
      {
         field.setAccessible( access );
      }
   }
   
   
   
   private < T > List< Pair< Field, InstanceState >> collectAnnotatedFields( T instance,
                                                                             Class< T > clazz )
   {
      final List< Pair< Field, InstanceState >> annotatedFields = new ArrayList< Pair< Field, InstanceState > >();
      
      for ( Field field : clazz.getDeclaredFields() )
      {
         final InstanceState instanceStateAnnotation = field.getAnnotation( InstanceState.class );
         
         if ( instanceStateAnnotation != null )
         {
            annotatedFields.add( Pair.create( field, instanceStateAnnotation ) );
         }
      }
      
      return annotatedFields;
   }
}
