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

package dev.drsoran.moloko.ui.services;

import android.text.format.DateUtils;
import dev.drsoran.rtm.parsing.IDateFormatter;


public interface IDateFormatterService extends IDateFormatter
{
   public final static int NO_FLAGS = 0;
   
   public final static int FORMAT_WITH_YEAR = DateUtils.FORMAT_SHOW_YEAR;
   
   public final static int FORMAT_NUMERIC = DateUtils.FORMAT_NUMERIC_DATE;
   
   public final static int FORMAT_SHOW_WEEKDAY = DateUtils.FORMAT_SHOW_WEEKDAY;
   
   public final static int FORMAT_ABR_WEEKDAY = DateUtils.FORMAT_ABBREV_WEEKDAY;
   
   public final static int FORMAT_ABR_MONTH = DateUtils.FORMAT_ABBREV_MONTH;
   
   public final static int FORMAT_ABR_ALL = DateUtils.FORMAT_ABBREV_ALL;
   
   public final static int FORMAT_PARSER = FORMAT_NUMERIC;
   
   
   
   @Override
   String getNumericDateFormatPattern( boolean withYear );
   
   
   
   char[] getDateFormatOrder();
   
   
   
   String formatDate( long millisUtc, int dateStyle );
   
   
   
   @Override
   String formatDateNumeric( String part1, String part2 );
   
   
   
   @Override
   String formatDateNumeric( String part1, String part2, String part3 );
   
   
   
   String formatDateTime( long millisUtc, int dateStyle );
   
   
   
   String formatTime( long millisUtc );
   
   
   
   String formatEstimated( long millisUtc );
}
