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

package dev.drsoran.moloko.test.unit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;

import org.junit.Test;

import dev.drsoran.moloko.util.Reflection;
import dev.drsoran.rtm.test.PrivateCtorCaller;


public class ReflectionFixture
{
   @Test( expected = AssertionError.class )
   public void testPrivateCtor() throws Throwable
   {
      PrivateCtorCaller.callPrivateCtor( Reflection.class );
   }
   
   
   
   @Test
   public void testFindMethod() throws NoSuchMethodException
   {
      final Method method = Reflection.findMethod( String.class, "toString" );
      
      assertNotNull( method );
      assertEquals( method.getName(), "toString" );
   }
   
   
   
   @Test( expected = NoSuchMethodException.class )
   public void testFindMethodNotFound() throws NoSuchMethodException
   {
      Reflection.findMethod( String.class, "notExisting" );
   }
}
