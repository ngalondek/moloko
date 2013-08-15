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

public final class RecurrencePatternSyntax
{
   private RecurrencePatternSyntax()
   {
      throw new AssertionError();
   }
   
   public final static String OP_BYDAY = "BYDAY";
   
   public final static String OP_BYMONTH = "BYMONTH";
   
   public final static String OP_BYMONTHDAY = "BYMONTHDAY";
   
   public final static String OP_INTERVAL = "INTERVAL";
   
   public final static String OP_FREQ = "FREQ";
   
   public final static String OP_UNTIL = "UNTIL";
   
   public final static String OP_COUNT = "COUNT";
   
   public final static String VAL_DAILY = "DAILY";
   
   public final static String VAL_WEEKLY = "WEEKLY";
   
   public final static String VAL_MONTHLY = "MONTHLY";
   
   public final static String VAL_YEARLY = "YEARLY";
   
   public final static String IS_EVERY = "EVERY";
   
   public final static String OPERATOR_SEP = ";";
   
   public final static String OPERATOR_VALUE_SEP = "=";
   
   public final static String BYDAY_MON = "MO";
   
   public final static String BYDAY_TUE = "TU";
   
   public final static String BYDAY_WED = "WE";
   
   public final static String BYDAY_THU = "TH";
   
   public final static String BYDAY_FRI = "FR";
   
   public final static String BYDAY_SAT = "SA";
   
   public final static String BYDAY_SUN = "SU";
   
   public final static String DATE_PATTERN = "yyyyMMdd'T'HHmmss";
}
