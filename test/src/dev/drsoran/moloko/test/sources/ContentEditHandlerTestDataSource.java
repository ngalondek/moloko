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

package dev.drsoran.moloko.test.sources;

import java.util.Collection;
import java.util.Collections;

import dev.drsoran.rtm.sync.model.Modification;


abstract public class ContentEditHandlerTestDataSource< T >
{
   public abstract Collection< TestData< T >> getUpdateTestData();
   
   
   public static class TestData< T >
   {
      public final T existingElement;
      
      public final T updateElement;
      
      public final Collection< Modification > expectedModifications;
      
      
      
      public TestData( T existingElement, T updateElement )
      {
         this( existingElement,
               updateElement,
               Collections.< Modification > emptyList() );
      }
      
      
      
      public TestData( T existingElement, T updateElement,
         Modification expectedModification )
      {
         this( existingElement,
               updateElement,
               Collections.singletonList( expectedModification ) );
      }
      
      
      
      public TestData( T existingElement, T updateElement,
         Collection< Modification > expectedModifications )
      {
         this.existingElement = existingElement;
         this.updateElement = updateElement;
         this.expectedModifications = expectedModifications;
      }
   }
}
