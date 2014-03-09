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

import org.junit.runners.model.InitializationError;
import org.robolectric.bytecode.ShadowMap;
import org.robolectric.util.DatabaseConfig.UsingDatabaseMap;
import org.robolectric.util.SQLiteMap;

import android.database.sqlite.SQLiteQuery;
import dev.drsoran.moloko.test.shadows.SQLiteQueryShadow;


@UsingDatabaseMap( SQLiteMap.class )
public class SQLiteTestRunner extends MolokoTestAppRunner_en
{
   public SQLiteTestRunner( Class< ? > testClass ) throws InitializationError
   {
      super( testClass );
   }
   
   
   
   @Override
   protected ShadowMap createShadowMap()
   {
      final ShadowMap map = new ShadowMap.Builder( super.createShadowMap() ).addShadowClass( SQLiteQuery.class,
                                                                                             SQLiteQueryShadow.class,
                                                                                             true,
                                                                                             true,
                                                                                             false )
                                                                            .build();
      
      return map;
   }
}
