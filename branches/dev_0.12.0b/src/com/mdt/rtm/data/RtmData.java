/*
 * Copyright 2007, MetaDimensional Technologies Inc.
 * 
 * 
 * This file is part of the RememberTheMilk Java API.
 * 
 * The RememberTheMilk Java API is free software; you can redistribute it
 * and/or modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 * 
 * The RememberTheMilk Java API is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.mdt.rtm.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.os.Parcelable;
import android.text.TextUtils;
import android.text.format.Time;
import dev.drsoran.rtm.ParcelableDate;


/**
 * 
 * @author Will Ross Jun 21, 2007
 */
public abstract class RtmData implements Parcelable
{
   
   private static final DateFormat DATE_FORMAT = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss" );
   
   static
   {
      DATE_FORMAT.setTimeZone( TimeZone.getTimeZone( Time.TIMEZONE_UTC ) );
   }
   
   

   public RtmData()
   {
   }
   


   /**
    * The method is not optimized at most, but circumvents a bug in Android runtime.
    */
   public static Element child( Element elt, String nodeName )
   {
      NodeList childNodes = elt.getChildNodes();
      for ( int index = 0, cnt = childNodes.getLength(); index < cnt; index++ )
      {
         Node child = childNodes.item( index );
         if ( child.getNodeType() == Node.ELEMENT_NODE
            && child.getNodeName().equals( nodeName ) )
         {
            return (Element) child;
         }
      }
      return null;
   }
   


   /**
    * The method is not optimized at most, but circumvents a bug in Android runtime.
    */
   public static List< Element > children( Element elt, String nodeName )
   {
      final List< Element > result = new LinkedList< Element >();
      NodeList childNodes = elt.getChildNodes();
      for ( int index = 0, cnt = childNodes.getLength(); index < cnt; index++ )
      {
         Node child = childNodes.item( index );
         if ( child.getNodeType() == Node.ELEMENT_NODE
            && child.getNodeName().equals( nodeName ) )
         {
            result.add( (Element) child );
         }
      }
      return result;
   }
   


   public static String text( Element elt )
   {
      StringBuilder result = new StringBuilder();
      Node child = elt.getFirstChild();
      while ( child != null )
      {
         switch ( child.getNodeType() )
         {
            case Node.TEXT_NODE:
            case Node.CDATA_SECTION_NODE:
               result.append( child.getNodeValue() );
               break;
            default :
               break;
         }
         child = child.getNextSibling();
      }
      return result.toString();
   }
   


   public static String textNullIfEmpty( Element elt )
   {
      final String text = text( elt );
      return ( TextUtils.isEmpty( text ) ? null : text );
   }
   


   public static String textNullIfEmpty( Element elt, String attribute )
   {
      final String text = elt.getAttribute( attribute );
      return ( TextUtils.isEmpty( text ) ? null : text );
   }
   


   public static ParcelableDate parseParcableDate( String s )
   {
      if ( !TextUtils.isEmpty( s ) )
      {
         try
         {
            return new ParcelableDate( DATE_FORMAT.parse( s ) );
         }
         catch ( ParseException e )
         {
            throw new RuntimeException( e );
         }
      }
      
      return null;
   }
   


   public static Date parseDate( String s )
   {
      if ( !TextUtils.isEmpty( s ) )
      {
         try
         {
            return DATE_FORMAT.parse( s );
         }
         catch ( ParseException e )
         {
            throw new RuntimeException( e );
         }
      }
      
      return null;
   }
   


   public static String formatDate( Date d )
   {
      return DATE_FORMAT.format( d ) + "Z";
   }
}
