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

package dev.drsoran.moloko.service.sync.lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import android.net.Uri;
import dev.drsoran.moloko.content.Modification;


public class ModificationList implements List< Modification >
{
   private final List< Modification > impl;
   
   

   public ModificationList()
   {
      impl = Collections.emptyList();
   }
   


   public ModificationList( Collection< ? extends Modification > collection )
   {
      impl = Collections.unmodifiableList( new ArrayList< Modification >( collection ) );
   }
   


   public void add( int location, Modification object )
   {
      impl.add( location, object );
   }
   


   public boolean add( Modification object )
   {
      return impl.add( object );
   }
   


   public boolean addAll( Collection< ? extends Modification > arg0 )
   {
      return impl.addAll( arg0 );
   }
   


   public boolean addAll( int arg0, Collection< ? extends Modification > arg1 )
   {
      return impl.addAll( arg0, arg1 );
   }
   


   public void clear()
   {
      impl.clear();
   }
   


   public boolean contains( Object object )
   {
      return impl.contains( object );
   }
   


   public boolean containsAll( Collection< ? > arg0 )
   {
      return impl.containsAll( arg0 );
   }
   


   @Override
   public boolean equals( Object object )
   {
      return impl.equals( object );
   }
   


   public Modification get( int location )
   {
      return impl.get( location );
   }
   


   @Override
   public int hashCode()
   {
      return impl.hashCode();
   }
   


   public int indexOf( Object object )
   {
      return impl.indexOf( object );
   }
   


   public boolean isEmpty()
   {
      return impl.isEmpty();
   }
   


   public Iterator< Modification > iterator()
   {
      return impl.iterator();
   }
   


   public int lastIndexOf( Object object )
   {
      return impl.lastIndexOf( object );
   }
   


   public ListIterator< Modification > listIterator()
   {
      return impl.listIterator();
   }
   


   public ListIterator< Modification > listIterator( int location )
   {
      return impl.listIterator( location );
   }
   


   public Modification remove( int location )
   {
      return impl.remove( location );
   }
   


   public boolean remove( Object object )
   {
      return impl.remove( object );
   }
   


   public boolean removeAll( Collection< ? > arg0 )
   {
      return impl.removeAll( arg0 );
   }
   


   public boolean retainAll( Collection< ? > arg0 )
   {
      return impl.retainAll( arg0 );
   }
   


   public Modification set( int location, Modification object )
   {
      return impl.set( location, object );
   }
   


   public int size()
   {
      return impl.size();
   }
   


   public List< Modification > subList( int start, int end )
   {
      return impl.subList( start, end );
   }
   


   public Object[] toArray()
   {
      return impl.toArray();
   }
   


   public < T > T[] toArray( T[] array )
   {
      return impl.toArray( array );
   }
   


   public Modification find( Uri entityUri, String columnName )
   {
      for ( Modification modification : impl )
      {
         if ( modification.getEntityUri().equals( entityUri )
            && modification.getColName().equals( columnName ) )
            return modification;
      }
      
      return null;
   }
}
