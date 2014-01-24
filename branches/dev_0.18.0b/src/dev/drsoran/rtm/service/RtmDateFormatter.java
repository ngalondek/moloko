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

package dev.drsoran.rtm.service;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.text.format.Time;


public class RtmDateFormatter
{
   private static final DateFormat DATE_FORMAT = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss" );
   
   static
   {
      DATE_FORMAT.setTimeZone( TimeZone.getTimeZone( Time.TIMEZONE_UTC ) );
   }
   
   
   
   private RtmDateFormatter()
   {
      throw new AssertionError();
   }
   
   
   
   public static Date parseDate( String dateString )
   {
      if ( dateString == null )
      {
         throw new IllegalArgumentException( "dateString" );
      }
      
      if ( dateString.length() > 0 )
      {
         try
         {
            return DATE_FORMAT.parse( dateString );
         }
         catch ( ParseException e )
         {
            throw new IllegalArgumentException( e );
         }
      }
      
      return null;
   }
   
   
   
   public static String formatDate( Date d )
   {
      return MessageFormat.format( "{0}Z", DATE_FORMAT.format( d ) );
   }
}
