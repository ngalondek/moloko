/* 
 *	Copyright (c) 2013 Ronny Röhricht
 *
 *	This file is part of MolokoTest.
 *
 *	MolokoTest is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	MolokoTest is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with MolokoTest.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.test;

import java.util.Comparator;

import dev.drsoran.moloko.util.Lambda.Func2;
import dev.drsoran.rtm.sync.model.Modification;


public class ModificationComparator implements Comparator< Modification >
{
   private final Func2< String, String, Integer > newValCmp;
   
   
   
   public ModificationComparator()
   {
      this( null );
   }
   
   
   
   public ModificationComparator( Func2< String, String, Integer > newValCmp )
   {
      this.newValCmp = newValCmp;
   }
   
   
   
   @Override
   public int compare( Modification m1, Modification m2 )
   {
      int res = m1.getEntityUri().compareTo( m2.getEntityUri() );
      if ( res == 0 )
      {
         res = m1.getColName().compareTo( m2.getColName() );
      }
      
      if ( res == 0 )
      {
         if ( newValCmp == null )
         {
            res = compareValue( m1.getNewValue(), m2.getNewValue() );
         }
         else
         {
            res = newValCmp.call( m1.getNewValue(), m2.getNewValue() );
         }
      }
      
      if ( res == 0 )
      {
         res = compareValue( m1.getSyncedValue(), m2.getSyncedValue() );
      }
      
      if ( res == 0 )
      {
         res = Boolean.compare( m1.isPersistent(), m2.isPersistent() );
      }
      
      return res;
   }
   
   
   
   private int compareValue( String val1, String val2 )
   {
      if ( val1 == val2 )
      {
         return 0;
      }
      else if ( val1 != null && val2 == null )
      {
         return 1;
      }
      else if ( val1 == null && val2 != null )
      {
         return -1;
      }
      
      return val1.compareTo( val2 );
   }
}
