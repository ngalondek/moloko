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

package dev.drsoran.rtm.rest;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import dev.drsoran.rtm.Strings;
import dev.drsoran.rtm.model.RtmConstants;


public final class XmlAttr
{
   private final static DateFormat RTM_DATE_FORMAT = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss",
                                                                           Locale.US );
   
   static
   {
      RTM_DATE_FORMAT.setTimeZone( TimeZone.getTimeZone( "GMT+0" ) );
   }
   
   
   
   private XmlAttr()
   {
      throw new AssertionError();
   }
   
   
   
   public static long getOptMillisUtc( Attributes attrs, String attrName ) throws SAXException
   {
      final String value = attrs.getValue( attrName );
      if ( Strings.isNullOrEmpty( value ) )
      {
         return RtmConstants.NO_TIME;
      }
      
      try
      {
         final long millisUtc = RTM_DATE_FORMAT.parse( value ).getTime();
         return millisUtc;
      }
      catch ( ParseException e )
      {
         throw new SAXException( e );
      }
   }
   
   
   
   public static String getStringNotNull( Attributes attrs, String attrName ) throws SAXException
   {
      final String value = attrs.getValue( attrName );
      if ( value == null )
      {
         throw new SAXException( MessageFormat.format( "Expected attribute {0} to have a value.",
                                                       attrName ) );
      }
      
      return value;
   }
   
   
   
   public static String getOptString( Attributes attrs,
                                      String attrName,
                                      String defValue )
   {
      String value = attrs.getValue( attrName );
      if ( Strings.isNullOrEmpty( value ) )
      {
         value = defValue;
      }
      
      return value;
   }
   
   
   
   public static int getInt( Attributes attrs, String attrName ) throws SAXException
   {
      String value = null;
      try
      {
         value = attrs.getValue( attrName );
         return Integer.parseInt( value );
      }
      catch ( NumberFormatException e )
      {
         throw new SAXException( MessageFormat.format( "{0} is not a parsable Integer",
                                                       value ),
                                 e );
      }
   }
   
   
   
   public static float getFloat( Attributes attrs, String attrName ) throws SAXException
   {
      String value = null;
      try
      {
         value = attrs.getValue( attrName );
         return Float.parseFloat( value );
      }
      catch ( NumberFormatException e )
      {
         throw new SAXException( MessageFormat.format( "{0} is not a parsable Float",
                                                       value ),
                                 e );
      }
   }
   
   
   
   public static boolean getBoolean( Attributes attrs, String attrName ) throws SAXException
   {
      return getInt( attrs, attrName ) != 0;
   }
   
   
   
   public static Attributes copy( Attributes source )
   {
      return new AttributesImpl( source );
   }
}
