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

package dev.drsoran.moloko.test.shadows;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

import android.content.Context;
import android.text.format.DateFormat;


@Implements( DateFormat.class )
public abstract class DateFormatShadow
{
   @Implementation
   public static final char[] getDateFormatOrder( Context context )
   {
      final Locale currentLocale = Locale.getDefault();
      
      if ( currentLocale.equals( Locale.GERMAN ) )
      {
         return new char[]
         { DateFormat.DATE, DateFormat.MONTH, DateFormat.YEAR };
      }
      
      if ( currentLocale.equals( Locale.US ) )
      {
         return new char[]
         { DateFormat.MONTH, DateFormat.DATE, DateFormat.YEAR };
      }
      
      return null;
   }
   
   
   
   @Implementation
   public static CharSequence format( CharSequence inFormat, long inTimeInMillis )
   {
      final SimpleDateFormat sdf = new SimpleDateFormat( inFormat.toString(),
                                                         Locale.getDefault() );
      final String formatteDate = sdf.format( new Date( inTimeInMillis ) );
      
      return formatteDate;
   }
}
