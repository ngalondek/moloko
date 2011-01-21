/* 
 *	Copyright (c) 2011 Ronny Röhricht
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

package dev.drsoran.moloko.content;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;


public class ModificationSet implements Set< Modification< ? > >
{
   
   private final SortedSet< Modification< ? > > modifications = new TreeSet< Modification< ? > >();
   
   

   public ModificationSet()
   {
      
   }
   


   public boolean add( Modification< ? > object )
   {
      return modifications.add( object );
   }
   


   public boolean addAll( Collection< ? extends Modification< ? >> arg0 )
   {
      return modifications.addAll( arg0 );
   }
   


   public void clear()
   {
      modifications.clear();
   }
   


   public Comparator< ? super Modification< ? >> comparator()
   {
      return modifications.comparator();
   }
   


   public boolean contains( Object object )
   {
      return modifications.contains( object );
   }
   


   public boolean containsAll( Collection< ? > arg0 )
   {
      return modifications.containsAll( arg0 );
   }
   


   @Override
   public boolean equals( Object object )
   {
      return modifications.equals( object );
   }
   


   public Modification< ? > first()
   {
      return modifications.first();
   }
   


   @Override
   public int hashCode()
   {
      return modifications.hashCode();
   }
   


   public SortedSet< Modification< ? >> headSet( Modification< ? > end )
   {
      return modifications.headSet( end );
   }
   


   public boolean isEmpty()
   {
      return modifications.isEmpty();
   }
   


   public Iterator< Modification< ? >> iterator()
   {
      return modifications.iterator();
   }
   


   public Modification< ? > last()
   {
      return modifications.last();
   }
   


   public boolean remove( Object object )
   {
      return modifications.remove( object );
   }
   


   public boolean removeAll( Collection< ? > arg0 )
   {
      return modifications.removeAll( arg0 );
   }
   


   public boolean retainAll( Collection< ? > arg0 )
   {
      return modifications.retainAll( arg0 );
   }
   


   public int size()
   {
      return modifications.size();
   }
   


   public SortedSet< Modification< ? >> subSet( Modification< ? > start,
                                                Modification< ? > end )
   {
      return modifications.subSet( start, end );
   }
   


   public SortedSet< Modification< ? >> tailSet( Modification< ? > start )
   {
      return modifications.tailSet( start );
   }
   


   public Object[] toArray()
   {
      return modifications.toArray();
   }
   


   public < T > T[] toArray( T[] array )
   {
      return modifications.toArray( array );
   }
   
}
