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

package dev.drsoran.moloko.domain.parsing.util;

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
   
   
   
   /**
    * Creates a new {@link LazyParserInstanceIterator} instance.
    * 
    * The instances list may hold already created instances from a previous iteration. The instance index must match the
    * factory index from factories. New instances will be appended to the end of the list.
    * 
    * @param factories
    *           the factories to create new instances
    * @param factoryMethod
    *           the method to call for new instances on each factory
    * @param instances
    *           a list with already created instances
    */
   public LazyParserInstanceIterator( List< ? > factories,
      Method factoryMethod, List< T > instances )
   {
      this( factories, factoryMethod, instances, (Object[]) null );
   }
   
   
   
   /**
    * Creates a new {@link LazyParserInstanceIterator} instance.
    * 
    * The instances list may hold already created instances from a previous iteration. The instance index must match the
    * factory index from factories. New instances will be appended to the end of the list.
    * 
    * @param factories
    *           the factories to create new instances
    * @param factoryMethod
    *           the method to call for new instances on each factory
    * @param instances
    *           a list with already created instances
    * @param factoryMethodArgs
    *           Optional arguments for the factoryMethod
    */
   public LazyParserInstanceIterator( List< ? > factories,
      Method factoryMethod, List< T > instances, Object... factoryMethodArgs )
   {
      if ( factories == null )
      {
         throw new IllegalArgumentException( "factories" );
      }
      if ( factoryMethod == null )
      {
         throw new IllegalArgumentException( "factoryMethod" );
      }
      if ( instances == null )
      {
         throw new IllegalArgumentException( "instances" );
      }
      
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
