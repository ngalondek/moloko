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

package dev.drsoran.rtm;

public class RtmResponse< T >
{
   private final RtmTransaction transaction;
   
   private final T element;
   
   
   
   public RtmResponse( T element )
   {
      this( null, element );
   }
   
   
   
   public RtmResponse( RtmTransaction transaction, T element )
   {
      this.transaction = transaction;
      this.element = element;
   }
   
   
   
   public RtmTransaction getTransaction()
   {
      return transaction;
   }
   
   
   
   public boolean isTransactional()
   {
      return transaction != null;
   }
   
   
   
   public boolean isUndoable()
   {
      return isTransactional() && transaction.isUndoable();
   }
   
   
   
   public T getElement()
   {
      return element;
   }
   
   
   
   @Override
   public String toString()
   {
      return "TimeLineResult [transaction=" + transaction + "]";
   }
}
