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

package dev.drsoran.moloko.domain.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class CompositeComparator< T > implements Comparator< T >
{
   private final List< Comparator< T > > comparators;
   
   
   
   public CompositeComparator( Comparator< T > comparator )
   {
      this( Collections.singletonList( comparator ) );
   }
   
   
   
   public CompositeComparator( Collection< Comparator< T > > comparators )
   {
      if ( comparators.size() == 0 )
         throw new IllegalArgumentException( "comparators" );
      
      this.comparators = new ArrayList< Comparator< T > >( comparators );
   }
   
   
   
   public CompositeComparator< T > add( Comparator< T > comparator )
   {
      comparators.add( comparator );
      return this;
   }
   
   
   
   @Override
   public int compare( T lhs, T rhs )
   {
      for ( Comparator< T > comparator : comparators )
      {
         final int value = comparator.compare( lhs, rhs );
         if ( value != 0 )
            return value;
      }
      
      return 0;
   }
}
