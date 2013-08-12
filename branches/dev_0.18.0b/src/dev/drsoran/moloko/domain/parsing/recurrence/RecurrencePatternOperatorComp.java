/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.moloko.domain.parsing.recurrence;

import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.OP_BYDAY;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.OP_BYMONTHDAY;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.OP_BYMONTH;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.OP_COUNT;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.OP_FREQ;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.OP_INTERVAL;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.OP_UNTIL;

import java.util.Comparator;


public class RecurrencePatternOperatorComp implements Comparator< String >
{
   @Override
   public int compare( String op1, String op2 )
   {
      return operatorToInt( op1 ) - operatorToInt( op2 );
   }
   
   
   
   private static int operatorToInt( String operator )
   {
      if ( operator.startsWith( OP_FREQ ) )
         return 1;
      else if ( operator.startsWith( OP_INTERVAL ) )
         return 2;
      else if ( operator.startsWith( OP_BYDAY ) )
         return 3;
      else if ( operator.startsWith( OP_BYMONTHDAY ) )
         return 3;
      else if ( operator.startsWith( OP_BYMONTH ) )
         return 4;
      else if ( operator.startsWith( OP_UNTIL ) )
         return 5;
      else if ( operator.startsWith( OP_COUNT ) )
         return 6;
      else
         return Integer.MAX_VALUE;
   }
}
