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


abstract class MultiTheoriesTestDataSource< T1, T2 > extends
         TheoriesTestDataSource< T1 >
{
   
   public T2[] getSecondTheoryTestData()
   {
      return toTheoryTestData( getSecondTestData(), getSecondTestDataClass() );
   }
   
   
   
   public abstract Collection< Object[] > getSecondTestData();
   
   
   
   public abstract Class< T2 > getSecondTestDataClass();
}
