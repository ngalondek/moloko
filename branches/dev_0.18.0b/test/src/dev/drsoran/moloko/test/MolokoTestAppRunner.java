/* 
 *	Copyright (c) 2012 Ronny Röhricht
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

import org.junit.runners.model.InitializationError;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.TestLifecycle;

import android.app.Application;


public abstract class MolokoTestAppRunner extends RobolectricTestRunner
{
   protected MolokoTestAppRunner( Class< ? > testClass )
      throws InitializationError
   {
      super( testClass );
   }
   
   
   
   public Application getApplication()
   {
      return Robolectric.application;
   }
   
   
   
   @SuppressWarnings( "rawtypes" )
   @Override
   protected Class< ? extends TestLifecycle > getTestLifecycleClass()
   {
      return MolokoTestAppTestLifeCycle.class;
   }
   
   
   
   public abstract String getValuesResQualifiers();
}
