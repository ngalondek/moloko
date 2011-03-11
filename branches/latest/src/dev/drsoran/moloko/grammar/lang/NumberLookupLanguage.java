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

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;


public class NumberLookupLanguage extends Language< String, Integer >
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + NumberLookupLanguage.class.getSimpleName();
   
   

   public NumberLookupLanguage( Resources resources, int langResId )
      throws ParseException
   {
      fromResources( resources, langResId );
   }
   


   @Override
   protected void fromResources( Resources resources, int resId ) throws ParseException
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
   


   private void readSimpleEntry( XmlResourceParser xmlParser ) throws ParseException
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
            throw new ParseException( "Unknown attribute " + attribName,
                                      xmlParser.getLineNumber() );
      }
      
      if ( key != null && value != null )
         try
         {
            if ( dictionary.put( key, Integer.valueOf( value ) ) != null )
               throw new ParseException( "Ambigious entry for " + key,
                                         xmlParser.getLineNumber() );
         }
         catch ( NumberFormatException e )
         {
            throw new ParseException( "Expected Integer type attribute",
                                      xmlParser.getLineNumber() );
         }
      else
         throw new ParseException( "Missing attribute",
                                   xmlParser.getLineNumber() );
   }
}
