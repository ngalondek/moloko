package dev.drsoran.moloko.service.sync.lists;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import dev.drsoran.moloko.service.sync.operation.ISyncOperation;
import dev.drsoran.moloko.service.sync.util.PropertyAdapter;


public abstract class SyncableList< T >
{
   private static final long serialVersionUID = 5137594788305624736L;
   
   private final ArrayList< T > impl;
   
   private final Comparator< T > comparator;
   
   private final boolean[] touchedElements;
   
   

   protected SyncableList( Collection< T > elements )
      throws NullPointerException
   {
      if ( elements == null )
         throw new NullPointerException();
      
      this.impl = new ArrayList< T >( elements.size() );
      this.impl.addAll( elements );
      this.comparator = null;
      this.touchedElements = new boolean[ elements.size() ];
   }
   


   protected SyncableList( Collection< T > elements, Comparator< T > comparator )
      throws NullPointerException
   {
      if ( elements == null )
         throw new NullPointerException();
      
      this.impl = new ArrayList< T >( elements.size() );
      this.impl.addAll( elements );
      this.comparator = comparator;
      this.touchedElements = new boolean[ elements.size() ];
      
      if ( comparator != null )
         Collections.sort( this.impl, this.comparator );
   }
   


   public void clearSyncState()
   {
      for ( int i = 0; i < touchedElements.length; i++ )
      {
         touchedElements[ i ] = false;
      }
   }
   


   public ArrayList< T > getUntouchedElements()
   {
      ArrayList< T > result = new ArrayList< T >();
      
      for ( int i = 0; i < touchedElements.length; i++ )
      {
         if ( !touchedElements[ i ] )
         {
            result.add( impl.get( i ) );
         }
      }
      
      return result;
   }
   


   public int find( T element ) throws NullPointerException
   {
      if ( element == null )
         throw new NullPointerException();
      
      int pos = -1;
      
      if ( comparator != null )
      {
         pos = Collections.binarySearch( impl, element, comparator );
         
         // We only want found or not and not the insert position.
         if ( pos < 0 )
            pos = -1;
      }
      else
      {
         for ( int i = 0; i < impl.size() && pos == -1; i++ )
         {
            if ( impl.get( i ).equals( element ) )
               pos = i;
         }
      }
      
      return pos;
   }
   


   public < V > int find( V other, PropertyAdapter< T, V > adapter ) throws NullPointerException,
                                                                    InvocationTargetException
   {
      if ( adapter == null )
         throw new NullPointerException();
      
      int pos = -1;
      
      for ( int i = 0; i < impl.size() && pos == -1; i++ )
      {
         if ( adapter.equals( impl.get( i ), other ) )
            pos = i;
      }
      
      return pos;
   }
   


   public T get( int pos )
   {
      return impl.get( pos );
   }
   


   public boolean isEmpty()
   {
      return impl.isEmpty();
   }
   


   public Iterator< T > iterator()
   {
      return impl.iterator();
   }
   


   public int size()
   {
      return impl.size();
   }
   


   public Object[] toArray()
   {
      return impl.toArray();
   }
   


   public abstract ISyncOperation computeInsertOperation( T newElement );
   


   public ISyncOperation computeUpdateOperation( int pos, T updateElement )
   {
      touchedElements[ pos ] = true;
      
      return internalComputeUpdateOperation( impl.get( pos ), updateElement );
   }
   


   public abstract ISyncOperation computeDeleteOperation( T elementToDelete );
   


   protected abstract ISyncOperation internalComputeUpdateOperation( T old,
                                                                    T updateElement );
}
