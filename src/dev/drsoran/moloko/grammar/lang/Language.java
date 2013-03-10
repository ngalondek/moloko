/* 
 *	Copyright (c) 2010 Ronny Röhricht
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

package dev.drsoran.moloko.grammar.lang;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;


public abstract class Language
{
   private final Locale locale;
   
   protected final HashMap< String, String > dictionary;
   
   
   
   protected Language( Locale locale )
   {
      this.locale = locale;
      dictionary = new HashMap< String, String >();
   }
   
   
   
   public Locale getLocale()
   {
      return locale;
   }
   
   
   
   public String getString( String key )
   {
      return dictionary.get( key );
   }
   
   
   
   public String getPluralString( String key, String unit, int qty )
   {
      return getPluralString( key, unit, String.valueOf( qty ) );
   }
   
   
   
   public String getPluralString( String key, String unit, String qty )
   {
      key = key + "_" + unit + "_";
      
      String res = dictionary.get( key + qty );
      
      if ( res == null )
         res = dictionary.get( key + "n" );
      
      return res;
   }
   
   
   
   public List< String > getStrings( String key )
   {
      final String entry = getString( key );
      
      if ( entry != null )
      {
         final String[] values = entry.split( "," );
         return Arrays.asList( values );
      }
      else
         return Collections.emptyList();
   }
   
   
   
   public List< String > getPluralStrings( String key, String unit, int qty )
   {
      return getPluralStrings( key, unit, String.valueOf( qty ) );
   }
   
   
   
   public List< String > getPluralStrings( String key, String unit, String qty )
   {
      final String entry = getPluralString( key, unit, qty );
      
      if ( entry != null )
      {
         final String[] values = entry.split( "," );
         return Arrays.asList( values );
      }
      else
         return Collections.emptyList();
   }
   
   
   
   public void fromResources( Resources resources, int resId ) throws ParseException
   {
      final XmlResourceParser xmlParser = resources.getXml( resId );
      
      if ( xmlParser == null )
         throw new ParseException( "Missing resource for resource ID " + resId,
                                   -1 );
      try
      {
         int eventType = xmlParser.getEventType();
         
         while ( eventType != XmlPullParser.END_DOCUMENT )
         {
            switch ( eventType )
            {
               case XmlPullParser.START_TAG:
                  final String name = xmlParser.getName();
                  
                  if ( name.equalsIgnoreCase( "entry" ) )
                     readSimpleEntry( xmlParser );
                  
                  else if ( name.equalsIgnoreCase( "entryPl" ) )
                     readPluralEntry( xmlParser );
                  
                  break;
               
               default :
                  break;
            }
            
            eventType = xmlParser.next();
         }
      }
      catch ( XmlPullParserException e )
      {
         throw new ParseException( e.getMessage(), e.getLineNumber() );
      }
      catch ( IOException e )
      {
         throw new ParseException( e.getMessage(), xmlParser.getLineNumber() );
      }
      
      xmlParser.close();
   }
   
   
   
   protected void readSimpleEntry( XmlResourceParser xmlParser ) throws ParseException
   {
      final int attribCount = xmlParser.getAttributeCount();
      
      if ( attribCount < 2 )
         throw new ParseException( "Unexpected number of attributes in 'entry' tag. "
                                      + attribCount,
                                   xmlParser.getLineNumber() );
      
      String key = null;
      String value = null;
      
      for ( int i = 0; i < attribCount; ++i )
      {
         final String attribName = xmlParser.getAttributeName( i );
         
         if ( attribName.equalsIgnoreCase( "key" ) )
         {
            key = xmlParser.getAttributeValue( i );
         }
         else if ( attribName.equalsIgnoreCase( "value" ) )
         {
            value = xmlParser.getAttributeValue( i );
         }
         else
            throw new ParseException( "Unknown attribute " + attribName
                                         + " @line "
                                         + xmlParser.getLineNumber(),
                                      xmlParser.getLineNumber() );
      }
      
      if ( key != null && value != null )
         addToDictionary( xmlParser, key, value );
      else
         throw new ParseException( "Missing attribute @line "
            + xmlParser.getLineNumber(), xmlParser.getLineNumber() );
   }
   
   
   
   protected void readPluralEntry( XmlResourceParser xmlParser ) throws ParseException,
                                                                XmlPullParserException,
                                                                IOException
   
   {
      final int attribCount = xmlParser.getAttributeCount();
      
      if ( attribCount < 2 )
      {
         throw new ParseException( "Unexpected number of attributes in 'entryPl' tag. "
                                      + attribCount,
                                   xmlParser.getLineNumber() );
      }
      
      final HashMap< String, String > attributes = new HashMap< String, String >( attribCount );
      
      String key = null;
      for ( int i = 0; i < attribCount; ++i )
      {
         final String attribName = xmlParser.getAttributeName( i );
         if ( attribName.equalsIgnoreCase( "key" ) )
         {
            key = xmlParser.getAttributeValue( i );
         }
         else
         {
            final String attribValue = xmlParser.getAttributeValue( i );
            attributes.put( attribName, attribValue );
         }
      }
      
      if ( key == null )
      {
         throw new ParseException( "Expected a 'key' attribute in 'entryPl' tag. ",
                                   xmlParser.getLineNumber() );
      }
      
      for ( String attributeName : attributes.keySet() )
      {
         addToDictionary( xmlParser,
                          key + "_" + attributeName,
                          attributes.get( attributeName ) );
      }
   }
   
   
   
   protected void validate( XmlPullParser xmlParser, String key, String value ) throws ParseException
   {
   }
   
   
   
   private void addToDictionary( XmlResourceParser xmlParser,
                                 String key,
                                 String value ) throws ParseException
   {
      validate( xmlParser, key, value );
      
      if ( dictionary.put( key, value ) != null )
         throw new ParseException( "Ambigious entry for " + key + " @line "
            + xmlParser.getLineNumber(), xmlParser.getLineNumber() );
   }
}
