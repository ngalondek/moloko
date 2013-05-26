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

package dev.drsoran.moloko.test.unit.domain.model;

import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.test.MolokoTestCase;


abstract class ModelTestCase extends MolokoTestCase
{
   public static final long ID = 1;
   
   public static final long NO_ID = Constants.NO_ID;
   
   public static final long NOW = System.currentTimeMillis();
   
   public static final long LATER = NOW + 3600 * 1000;
   
   public static final long NEVER = Constants.NO_TIME;
}
