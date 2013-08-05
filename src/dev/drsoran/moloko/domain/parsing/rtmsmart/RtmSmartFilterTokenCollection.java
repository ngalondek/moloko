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

package dev.drsoran.moloko.domain.parsing.rtmsmart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import dev.drsoran.moloko.grammar.antlr.rtmsmart.RtmSmartFilterLexer;
import dev.drsoran.moloko.grammar.rtmsmart.RtmSmartFilterSyntax;


public class RtmSmartFilterTokenCollection implements
         Collection< RtmSmartFilterToken >
{
   private final Collection< RtmSmartFilterToken > impl;
   
   private Collection< RtmSmartFilterToken > uniqueTokens;
   
   
   
   public RtmSmartFilterTokenCollection(
      Collection< RtmSmartFilterToken > tokens )
   {
      if ( tokens == null )
      {
         throw new IllegalArgumentException( "tokens" );
      }
      
      impl = Collections.unmodifiableCollection( new ArrayList< RtmSmartFilterToken >( tokens ) );
   }
   
   
   
   public Collection< RtmSmartFilterToken > getUniqueTokens()
   {
      if ( uniqueTokens == null )
      {
         uniqueTokens = collectUniqueTokens();
      }
      
      return uniqueTokens;
   }
   
   
   
   public boolean hasUniqueOperator( int operator, boolean negated )
   {
      for ( RtmSmartFilterToken token : getUniqueTokens() )
      {
         if ( token.operatorType == operator && token.isNegated == negated )
         {
            return true;
         }
      }
      
      return false;
   }
   
   
   
   public boolean hasUniqueOperatorWithValue( int operator,
                                              String value,
                                              boolean negated )
   {
      for ( RtmSmartFilterToken token : getUniqueTokens() )
      {
         if ( token.operatorType == operator
            && token.value.equalsIgnoreCase( value )
            && negated == token.isNegated )
         {
            return true;
         }
      }
      
      return false;
   }
   
   
   
   public boolean hasCompletedOperator()
   {
      for ( RtmSmartFilterToken token : impl )
      {
         final int tokenType = token.operatorType;
         if ( ( tokenType == RtmSmartFilterLexer.OP_STATUS && token.value.equalsIgnoreCase( RtmSmartFilterSyntax.COMPLETED ) )
            || tokenType == RtmSmartFilterLexer.OP_COMPLETED
            || tokenType == RtmSmartFilterLexer.OP_COMPLETED_AFTER
            || tokenType == RtmSmartFilterLexer.OP_COMPLETED_BEFORE
            || tokenType == RtmSmartFilterLexer.OP_COMPLETED_WITHIN )
         {
            return true;
         }
      }
      
      return false;
   }
   
   
   
   @Override
   public boolean add( RtmSmartFilterToken object ) throws UnsupportedOperationException
   {
      throw new UnsupportedOperationException();
   }
   
   
   
   @Override
   public boolean addAll( Collection< ? extends RtmSmartFilterToken > collection ) throws UnsupportedOperationException
   {
      throw new UnsupportedOperationException();
   }
   
   
   
   @Override
   public void clear() throws UnsupportedOperationException
   {
      throw new UnsupportedOperationException();
   }
   
   
   
   @Override
   public boolean contains( Object object )
   {
      return impl.contains( object );
   }
   
   
   
   @Override
   public boolean containsAll( Collection< ? > collection )
   {
      return impl.containsAll( collection );
   }
   
   
   
   @Override
   public boolean isEmpty()
   {
      return impl.isEmpty();
   }
   
   
   
   @Override
   public Iterator< RtmSmartFilterToken > iterator()
   {
      return impl.iterator();
   }
   
   
   
   @Override
   public boolean remove( Object object )
   {
      throw new UnsupportedOperationException();
   }
   
   
   
   @Override
   public boolean removeAll( Collection< ? > collection ) throws UnsupportedOperationException
   {
      throw new UnsupportedOperationException();
   }
   
   
   
   @Override
   public boolean retainAll( Collection< ? > collection ) throws UnsupportedOperationException
   {
      throw new UnsupportedOperationException();
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
    * Ambiguous is determined by the number a token exists in the list of given tokens. This is the simplest approach to
    * avoid situation with complex logical expressions where a token is entered negated and not negated.
    * 
    * TODO: Use more sophisticated approach like BDDs
    */
   private Collection< RtmSmartFilterToken > collectUniqueTokens()
   {
      final List< RtmSmartFilterToken > filterTokens = new LinkedList< RtmSmartFilterToken >( impl );
      
      Collections.sort( filterTokens, new Comparator< RtmSmartFilterToken >()
      {
         @Override
         public int compare( RtmSmartFilterToken object1,
                             RtmSmartFilterToken object2 )
         {
            return object1.operatorType - object2.operatorType;
         }
      } );
      
      for ( int i = 0, cnt = filterTokens.size(); i < cnt; )
      {
         boolean ambiguous = false;
         
         RtmSmartFilterToken filterToken = filterTokens.get( i );
         
         // next token
         int startI = i++;
         
         // Look ahead if we have a row of equal operators
         for ( ; i < cnt
            && filterTokens.get( i ).operatorType == filterToken.operatorType; ++i )
         {
            ambiguous = true;
            
            // remove equal token in row
            filterTokens.set( i, null );
         }
         
         if ( ambiguous )
         {
            // Remove first token of the row
            filterTokens.set( startI, null );
         }
      }
      
      for ( Iterator< RtmSmartFilterToken > i = filterTokens.iterator(); i.hasNext(); )
      {
         if ( i.next() == null )
         {
            i.remove();
         }
      }
      
      return Collections.unmodifiableCollection( filterTokens );
   }
}
