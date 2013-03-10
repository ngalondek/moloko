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

package dev.drsoran.moloko.app.content;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import dev.drsoran.moloko.content.ContentException;
import dev.drsoran.moloko.content.RtmProvider;
import dev.drsoran.moloko.content.TransactionalAccess;


public class ContentProviderActionItemList implements
         List< IContentProviderActionItem >
{
   public final static ContentProviderActionItemList EMPTY = new ContentProviderActionItemList( 0 ).asReadOnly();
   
   private List< IContentProviderActionItem > impl;
   
   
   
   public ContentProviderActionItemList()
   {
      this( 5 );
   }
   
   
   
   public ContentProviderActionItemList( int capacity )
   {
      impl = new ArrayList< IContentProviderActionItem >( capacity );
   }
   
   
   
   public ContentProviderActionItemList asReadOnly()
   {
      impl = Collections.unmodifiableList( impl );
      return this;
   }
   
   
   
   public boolean add( ContentProviderAction.Type type,
                       ContentProviderOperation contentProviderOperation )
   {
      if ( contentProviderOperation == null )
      {
         throw new IllegalArgumentException( "contentProviderOperation" );
      }
      
      return impl.add( ContentProviderAction.fromType( type )
                                            .add( contentProviderOperation )
                                            .build() );
   }
   
   
   
   public void add( int location,
                    ContentProviderAction.Type type,
                    ContentProviderOperation contentProviderOperation )
   {
      if ( contentProviderOperation == null )
      {
         throw new IllegalArgumentException( "contentProviderOperation" );
      }
      
      impl.add( location,
                ContentProviderAction.fromType( type )
                                     .add( contentProviderOperation )
                                     .build() );
   }
   
   
   
   @Override
   public boolean add( IContentProviderActionItem object )
   {
      return impl.add( object );
   }
   
   
   
   @Override
   public void add( int location, IContentProviderActionItem object )
   {
      impl.add( location, object );
   }
   
   
   
   @Override
   public boolean addAll( Collection< ? extends IContentProviderActionItem > arg0 )
   {
      return impl.addAll( arg0 );
   }
   
   
   
   @Override
   public boolean addAll( int arg0,
                          Collection< ? extends IContentProviderActionItem > arg1 )
   {
      return impl.addAll( arg0, arg1 );
   }
   
   
   
   public boolean addAll( ContentProviderAction.Type type,
                          Collection< ? extends ContentProviderOperation > arg0 )
   {
      boolean anyAdded = false;
      
      for ( ContentProviderOperation contentProviderOperation : arg0 )
      {
         anyAdded |= add( type, contentProviderOperation );
      }
      
      return anyAdded;
   }
   
   
   
   @Override
   public void clear()
   {
      impl.clear();
   }
   
   
   
   @Override
   public boolean contains( Object object )
   {
      return impl.contains( object );
   }
   
   
   
   @Override
   public boolean containsAll( Collection< ? > arg0 )
   {
      return impl.containsAll( arg0 );
   }
   
   
   
   @Override
   public boolean equals( Object object )
   {
      return impl.equals( object );
   }
   
   
   
   @Override
   public IContentProviderActionItem get( int location )
   {
      return impl.get( location );
   }
   
   
   
   @Override
   public int hashCode()
   {
      return impl.hashCode();
   }
   
   
   
   @Override
   public int indexOf( Object object )
   {
      return impl.indexOf( object );
   }
   
   
   
   @Override
   public boolean isEmpty()
   {
      return impl.isEmpty();
   }
   
   
   
   @Override
   public Iterator< IContentProviderActionItem > iterator()
   {
      return impl.iterator();
   }
   
   
   
   @Override
   public int lastIndexOf( Object object )
   {
      return impl.lastIndexOf( object );
   }
   
   
   
   @Override
   public ListIterator< IContentProviderActionItem > listIterator()
   {
      return impl.listIterator();
   }
   
   
   
   @Override
   public ListIterator< IContentProviderActionItem > listIterator( int location )
   {
      return impl.listIterator( location );
   }
   
   
   
   @Override
   public IContentProviderActionItem remove( int location )
   {
      return impl.remove( location );
   }
   
   
   
   @Override
   public boolean remove( Object object )
   {
      return impl.remove( object );
   }
   
   
   
   @Override
   public boolean removeAll( Collection< ? > arg0 )
   {
      return impl.removeAll( arg0 );
   }
   
   
   
   @Override
   public boolean retainAll( Collection< ? > arg0 )
   {
      return impl.retainAll( arg0 );
   }
   
   
   
   @Override
   public IContentProviderActionItem set( int location,
                                          IContentProviderActionItem object )
   {
      return impl.set( location, object );
   }
   
   
   
   @Override
   public int size()
   {
      return impl.size();
   }
   
   
   
   @Override
   public List< IContentProviderActionItem > subList( int start, int end )
   {
      return impl.subList( start, end );
   }
   
   
   
   @Override
   public Object[] toArray()
   {
      return impl.toArray();
   }
   
   
   
   @Override
   public < T > T[] toArray( T[] array )
   {
      return impl.toArray( array );
   }
   
   
   
   public void applyTransactional( RtmProvider rtmProvider ) throws ContentException
   {
      final TransactionalAccess transactionalAccess = rtmProvider.newTransactionalAccess();
      
      try
      {
         final ContentResolver contentResolver = rtmProvider.getContext()
                                                            .getContentResolver();
         transactionalAccess.beginTransaction();
         
         for ( IContentProviderActionItem item : this )
         {
            item.apply( contentResolver );
         }
         
         transactionalAccess.setTransactionSuccessful();
      }
      catch ( Throwable e )
      {
         if ( e instanceof ContentException )
         {
            throw (ContentException) e;
         }
         
         throw new ContentException( e );
      }
      finally
      {
         transactionalAccess.endTransaction();
      }
   }
}
