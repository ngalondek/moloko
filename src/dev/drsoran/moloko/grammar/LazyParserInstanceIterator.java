/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.moloko.grammar;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;


public final class LazyParserInstanceIterator< T > implements Iterable< T >,
         Iterator< T >
{
   private final List< Class< ? extends T > > classes;
   
   private final List< T > instances;
   
   private final Object[] ctorArgs;
   
   private int classesIndex;
   
   
   
   public LazyParserInstanceIterator( List< Class< ? extends T > > classes,
      List< T > instances )
   {
      this( classes, instances, (Object[]) null );
   }
   
   
   
   public LazyParserInstanceIterator( List< Class< ? extends T > > classes,
      List< T > instances, Object... ctorArgs )
   {
      this.classes = classes;
      this.instances = instances;
      this.ctorArgs = ctorArgs;
   }
   
   
   
   @Override
   public Iterator< T > iterator()
   {
      classesIndex = 0;
      return this;
   }
   
   
   
   @Override
   public boolean hasNext()
   {
      return hasNextImpl();
   }
   
   
   
   @Override
   public T next()
   {
      if ( !hasNextImpl() )
      {
         throw new UnsupportedOperationException( "No next element." );
      }
      
      if ( instances.size() - 1 < classesIndex )
      {
         addInstance();
      }
      
      return instances.get( classesIndex++ );
   }
   
   
   
   @Override
   public void remove()
   {
      throw new UnsupportedOperationException( "Modification not allowed" );
   }
   
   
   
   private boolean hasNextImpl()
   {
      final boolean hasNext = classes.size() > classesIndex;
      return hasNext;
   }
   
   
   
   private void addInstance()
   {
      final Class< ? extends T > clazz = classes.get( classesIndex );
      T instance;
      
      if ( ctorArgs != null && ctorArgs.length > 0 )
      {
         instance = createInstanceWithParams( clazz );
      }
      else
      {
         instance = createParamLessInstance( clazz );
      }
      
      instances.add( instance );
   }
   
   
   
   private T createParamLessInstance( Class< ? extends T > clazz )
   {
      try
      {
         return clazz.newInstance();
      }
      catch ( InstantiationException e )
      {
         throw new RuntimeException( e );
      }
      catch ( IllegalAccessException e )
      {
         throw new RuntimeException( e );
      }
   }
   
   
   
   private T createInstanceWithParams( Class< ? extends T > clazz )
   {
      try
      {
         final Class< ? >[] ctorTypes = new Class< ? >[ ctorArgs.length ];
         for ( int i = 0; i < ctorArgs.length; ++i )
         {
            ctorTypes[ i ] = ctorArgs[ i ].getClass();
         }
         
         final Constructor< ? extends T > ctor = clazz.getConstructor( ctorTypes );
         return ctor.newInstance( ctorArgs );
      }
      catch ( NoSuchMethodException e )
      {
         throw new RuntimeException( e );
      }
      catch ( IllegalArgumentException e )
      {
         throw new RuntimeException( e );
      }
      catch ( InstantiationException e )
      {
         throw new RuntimeException( e );
      }
      catch ( IllegalAccessException e )
      {
         throw new RuntimeException( e );
      }
      catch ( InvocationTargetException e )
      {
         throw new RuntimeException( e );
      }
   }
}
