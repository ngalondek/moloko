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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;


public final class LazyParserInstanceIterator< T > implements Iterable< T >,
         Iterator< T >
{
   private final List< ? > factories;
   
   private final Method factoryMethod;
   
   private final List< T > instances;
   
   private final Object[] factoryMethodArgs;
   
   private int factoryIndex;
   
   
   
   public LazyParserInstanceIterator( List< ? > factories,
      Method factoryMethod, List< T > instances )
   {
      this( factories, factoryMethod, instances, (Object[]) null );
   }
   
   
   
   public LazyParserInstanceIterator( List< ? > factories,
      Method factoryMethod, List< T > instances, Object... factoryMethodArgs )
   {
      this.factories = factories;
      this.factoryMethod = factoryMethod;
      this.instances = instances;
      this.factoryMethodArgs = factoryMethodArgs;
   }
   
   
   
   @Override
   public Iterator< T > iterator()
   {
      factoryIndex = 0;
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
      
      if ( instances.size() - 1 < factoryIndex )
      {
         addInstance();
      }
      
      return instances.get( factoryIndex++ );
   }
   
   
   
   @Override
   public void remove()
   {
      throw new UnsupportedOperationException( "Modification not allowed" );
   }
   
   
   
   private boolean hasNextImpl()
   {
      final boolean hasNext = factories.size() > factoryIndex;
      return hasNext;
   }
   
   
   
   private void addInstance()
   {
      final Object factory = factories.get( factoryIndex );
      final T instance = callFactoryMethod( factory );
      
      instances.add( instance );
   }
   
   
   
   private T callFactoryMethod( Object factory )
   {
      try
      {
         @SuppressWarnings( "unchecked" )
         final T instance = (T) factoryMethod.invoke( factory,
                                                      factoryMethodArgs );
         
         return instance;
      }
      catch ( IllegalAccessException e )
      {
         throw new RuntimeException( e );
      }
      catch ( IllegalArgumentException e )
      {
         throw new RuntimeException( e );
      }
      catch ( InvocationTargetException e )
      {
         throw new RuntimeException( e );
      }
   }
}
