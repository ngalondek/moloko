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

package dev.drsoran.moloko.service.sync.operation;

public interface IServerSyncOperation extends ISyncOperation
{
   public final static class Op extends ISyncOperation.Op
   {
      public final static int INSERT = 1;
      
      public final static int UPDATE = 2;
      
      public final static int DELETE = 3;
      
      

      public final static String toString( int op )
      {
         switch ( op )
         {
            case NOOP:
               return "NOOP";
            case INSERT:
               return "INSERT";
            case UPDATE:
               return "UPDATE";
            case DELETE:
               return "DELETE";
            default :
               return "UNKNOWN";
         }
      }
      
   }
}
