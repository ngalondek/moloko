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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.net.Uri;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.Modifications;


public class ModificationSet implements Set< Modification >,
         IContentProviderActionItem
{
   private final Set< Modification > impl;
   
   public final static ModificationSet EMPTY_MODIFICATION_SET = new ModificationSet();
   
   
   
   public ModificationSet()
   {
      impl = new TreeSet< Modification >();
   }
   
   
   
   public ModificationSet( Collection< ? extends Modification > collection )
   {
      impl = new TreeSet< Modification >( collection );
   }
   
   
   
   @Override
   public boolean add( Modification object )
   {
      return impl.add( object );
   }
   
   
   
   @Override
   public boolean addAll( Collection< ? extends Modification > arg0 )
   {
      return impl.addAll( arg0 );
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
   public boolean isEmpty()
   {
      return impl.isEmpty();
   }
   
   
   
   @Override
   public Iterator< Modification > iterator()
   {
      return impl.iterator();
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
   public int size()
   {
      return impl.size();
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
   
   
   
   /**
    * Checks if the new and the synced value of the {@link Modification} are equal. If they are equal, the new
    * {@link Modification} will not be added and any existing {@link Modification} objects which are refer the same
    * {@link Modification} will be removed. This can happen if the user decided to revert an previously made change by
    * setting it to the same value like before.
    * 
    * @param modification
    */
   public void addOrRevert( Modification modification )
   {
      if ( !SyncUtils.hasChanged( modification.getSyncedValue(),
                                  modification.getNewValue() ) )
         remove( modification );
      else
         add( modification );
   }
   
   
   
   public boolean remove( Uri entityUri, String columnName )
   {
      boolean found = false;
      
      for ( Iterator< Modification > i = impl.iterator(); !found && i.hasNext(); )
      {
         final Modification modification = i.next();
         if ( modification.getEntityUri().equals( entityUri )
            && modification.getColName().equals( columnName ) )
         {
            i.remove();
            found = true;
         }
      }
      
      return found;
   }
   
   
   
   public boolean removeAll( Uri entityUri )
   {
      boolean found = false;
      
      for ( Iterator< Modification > i = impl.iterator(); i.hasNext(); )
      {
         final Modification modification = i.next();
         if ( modification.getEntityUri().equals( entityUri ) )
         {
            i.remove();
            found = true;
         }
      }
      
      return found;
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
   
   
   
   public boolean hasModifications( Uri uri )
   {
      for ( Modification modification : impl )
      {
         if ( modification.getEntityUri()
                          .toString()
                          .startsWith( uri.toString() ) )
            return true;
      }
      
      return false;
   }
   
   
   
   public boolean hasModification( Uri entityUri )
   {
      for ( Modification modification : impl )
      {
         if ( modification.getEntityUri().equals( entityUri ) )
            return true;
      }
      
      return false;
   }
   
   
   
   public boolean hasModification( Uri entityUri, String columnName )
   {
      for ( Modification modification : impl )
      {
         if ( modification.getEntityUri().equals( entityUri )
            && modification.getColName().equals( columnName ) )
            return true;
      }
      
      return false;
   }
   
   
   
   public List< Modification > getModifications( Uri entityUri )
   {
      List< Modification > result = new LinkedList< Modification >();
      
      for ( Modification modification : impl )
      {
         if ( modification.getEntityUri().equals( entityUri ) )
            result.add( modification );
      }
      
      return Collections.unmodifiableList( result );
   }
   
   
   
   public List< ContentProviderOperation > getRevertAllOperations( Uri entityUri )
   {
      final List< Modification > mods = getModifications( entityUri );
      List< ContentProviderOperation > operations = new ArrayList< ContentProviderOperation >( mods.size() );
      
      for ( Modification modification : mods )
      {
         if ( modification.isSyncedValueSet() )
         {
            operations.add( ContentProviderOperation.newUpdate( modification.getEntityUri() )
                                                    .withValue( modification.getColName(),
                                                                modification.getSyncedValue() )
                                                    .build() );
            operations.add( ContentProviderOperation.newDelete( Queries.contentUriWithId( Modifications.CONTENT_URI,
                                                                                          modification.getId() ) )
                                                    .build() );
         }
      }
      
      return operations;
   }
   
   
   
   @Override
   public boolean applyTransactional( RtmProvider rtmProvider )
   {
      return ModificationsProviderPart.applyModificationsTransactional( rtmProvider,
                                                                        this );
   }
   
   
   
   @Override
   public boolean apply( ContentResolver contentResolver )
   {
      return ModificationsProviderPart.applyModifications( contentResolver,
                                                           this );
   }
   
   
   
   public ContentProviderActionItemList toContentProviderActionItemList()
   {
      final ContentProviderActionItemList list = new ContentProviderActionItemList( 1 );
      
      list.add( this );
      
      return list;
   }
}
