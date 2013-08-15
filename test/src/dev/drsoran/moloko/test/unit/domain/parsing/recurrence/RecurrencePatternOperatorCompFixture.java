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

package dev.drsoran.moloko.test.unit.domain.parsing.recurrence;

import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.OP_BYDAY;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.OP_BYMONTH;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.OP_BYMONTHDAY;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.OP_COUNT;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.OP_FREQ;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.OP_INTERVAL;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.OP_UNTIL;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternOperatorComp;
import dev.drsoran.moloko.test.MolokoTestCase;


@RunWith( Theories.class )
public class RecurrencePatternOperatorCompFixture extends MolokoTestCase
{
   private RecurrencePatternOperatorComp comp;
   
   @DataPoints
   public static String[] operators = new String[]
   { OP_FREQ, OP_INTERVAL, OP_BYDAY, OP_BYMONTHDAY, OP_BYMONTH, OP_UNTIL,
    OP_COUNT };
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      comp = new RecurrencePatternOperatorComp();
   }
   
   
   
   @Theory
   public void testCompare( String op1, String op2 )
   {
      final int res = comp.compare( op1, op2 );
      
      final int idxOp1 = getIndex( operators, op1 );
      final int idxOp2 = getIndex( operators, op2 );
      
      assertThat( idxOp1 - idxOp2, is( res ) );
   }
   
   
   
   private int getIndex( String[] array, String op )
   {
      for ( int i = 0; i < array.length; i++ )
      {
         if ( array[ i ].equals( op ) )
         {
            return i + 1;
         }
      }
      
      throw new IllegalArgumentException( "op" );
   }
}
