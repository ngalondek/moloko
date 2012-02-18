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

package dev.drsoran.moloko.fragments.base.impl;

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
   
   
   
   public void registerInstance( Object instance, Bundle instanceState )
   {
      createAnnotatedInstanceStateFor( instance );
      
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
      for ( Object instance : registeredInstances.keySet() )
      {
         onSaveInstanceState( instance, outState );
      }
   }
   
   
   
   public void onSaveInstanceState( Object instance, Bundle outState )
   {
      final Bundle instanceState = getInstanceState( instance );
      outState.putAll( instanceState );
   }
   
   
   
   public void onRestoreInstanceStates( Bundle state )
   {
      for ( Object instance : registeredInstances.keySet() )
      {
         onRestoreInstanceState( instance, state );
      }
   }
   
   
   
   public void onRestoreInstanceState( Object instance, Bundle state )
   {
      setInstanceState( instance, state );
   }
   
   
   
   public Bundle getInstanceStates()
   {
      final Bundle mergedState = new Bundle();
      for ( Object instance : registeredInstances.keySet() )
      {
         mergedState.putAll( getInstanceState( instance ) );
      }
      
      return mergedState;
   }
   
   
   
   public Bundle getInstanceState( Object instance )
   {
      final List< Pair< Field, InstanceState >> annotatedFields = getAnnotatedInstanceStateFor( instance );
      final Bundle instanceState = new Bundle( annotatedFields.size() );
      
      for ( Pair< Field, InstanceState > annotatedField : annotatedFields )
      {
         getValue( instance,
                   instanceState,
                   annotatedField.first,
                   annotatedField.second.key() );
      }
      
      return instanceState;
   }
   
   
   
   public void setInstanceStates( Bundle instanceState )
   {
      for ( Object instance : registeredInstances.keySet() )
      {
         setInstanceState( instance, instanceState );
      }
   }
   
   
   
   public void setInstanceState( Object instance, Bundle instanceState )
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
   
   
   
   public void setDefaultInstanceStates()
   {
      for ( Object instance : registeredInstances.keySet() )
      {
         setDefaultInstanceState( instance );
      }
   }
   
   
   
   public void setDefaultInstanceState( Object instance )
   {
      final List< Pair< Field, InstanceState >> annotatedFields = getAnnotatedInstanceStateFor( instance );
      
      for ( Pair< Field, InstanceState > annotatedField : annotatedFields )
      {
         final Field fieldToSet = annotatedField.first;
         
         setInstaceFieldValue( instance,
                               fieldToSet,
                               Strings.convertTo( annotatedField.second.defaultValue(),
                                                  fieldToSet.getType() ) );
      }
   }
   
   
   
   private List< Pair< Field, InstanceState >> getAnnotatedInstanceStateFor( Object instance )
   {
      return registeredInstances.get( instance );
   }
   
   
   
   private List< Pair< Field, InstanceState >> createAnnotatedInstanceStateFor( Object instance )
   {
      if ( registeredInstances == null )
      {
         registeredInstances = new HashMap< Object, List< Pair< Field, InstanceState >> >();
      }
      
      final List< Pair< Field, InstanceState >> annotatedInstanceState = collectAnnotatedFields( instance );
      registeredInstances.put( instance, annotatedInstanceState );
      
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
      try
      {
         return field.get( instance );
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
   }
   
   
   
   private void setInstaceFieldValue( Object instance, Field field, Object value )
   {
      try
      {
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
   }
   
   
   
   private List< Pair< Field, InstanceState >> collectAnnotatedFields( Object instance )
   {
      final List< Pair< Field, InstanceState >> annotatedFields = new ArrayList< Pair< Field, InstanceState > >();
      
      for ( Field field : instance.getClass().getFields() )
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
