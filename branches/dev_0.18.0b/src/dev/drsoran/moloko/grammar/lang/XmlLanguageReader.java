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

package dev.drsoran.moloko.grammar.lang;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;
import dev.drsoran.moloko.util.Strings;


public class XmlLanguageReader
{
   private final Language language;
   
   private final XmlResourceParser xmlParser;
   
   
   
   public XmlLanguageReader( Language language, XmlResourceParser xmlParser )
   {
      if ( language == null )
      {
         throw new IllegalArgumentException( "language" );
      }
      
      if ( xmlParser == null )
      {
         throw new IllegalArgumentException( "xmlParser" );
      }
      
      this.language = language;
      this.xmlParser = xmlParser;
   }
   
   
   
   public void read() throws ParseException
   {
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
                     readSimpleEntry();
                  
                  else if ( name.equalsIgnoreCase( "entryPl" ) )
                     readPluralEntry();
                  
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
   }
   
   
   
   public void close()
   {
      xmlParser.close();
   }
   
   
   
   private void readSimpleEntry() throws ParseException
   {
      final int attribCount = xmlParser.getAttributeCount();
      
      if ( attribCount < 2 )
      {
         throw new ParseException( "Unexpected number of attributes in 'entry' tag. "
                                      + attribCount,
                                   xmlParser.getLineNumber() );
      }
      
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
         {
            throw new ParseException( "Unknown attribute " + attribName
                                         + " @line "
                                         + xmlParser.getLineNumber(),
                                      xmlParser.getLineNumber() );
         }
      }
      
      if ( !Strings.isNullOrEmpty( key ) && value != null )
      {
         addToLanguge( key, value );
      }
      else
      {
         throw new ParseException( "Missing attribute @line "
            + xmlParser.getLineNumber(), xmlParser.getLineNumber() );
      }
   }
   
   
   
   private void readPluralEntry() throws ParseException,
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
            
            if ( attribValue == null )
            {
               throw new ParseException( "Expected a non null attribute value.",
                                         xmlParser.getLineNumber() );
            }
            
            attributes.put( attribName, attribValue );
         }
      }
      
      if ( Strings.isNullOrEmpty( key ) )
      {
         throw new ParseException( "Expected a 'key' attribute in 'entryPl' tag. ",
                                   xmlParser.getLineNumber() );
      }
      
      for ( String attributeName : attributes.keySet() )
      {
         addToLanguge( key + "_" + attributeName,
                       attributes.get( attributeName ) );
      }
   }
   
   
   
   private void addToLanguge( String key, String value ) throws ParseException
   {
      try
      {
         language.add( key, value );
      }
      catch ( IllegalArgumentException e )
      {
         throw new ParseException( "Ambigious entry for key " + key + " @line "
            + xmlParser.getLineNumber(), xmlParser.getLineNumber() );
      }
   }
}
