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
import android.util.Log;


public class RecurrPatternLanguage extends Language
{
   private final static String TAG = "Moloko."
      + RecurrPatternLanguage.class.getSimpleName();
   
   

   public RecurrPatternLanguage( Resources resources, int langResId )
      throws ParseException
   {
      fromResources( resources, langResId );
   }
   


   public void add( StringBuilder sb, String key )
   {
      final String res = dictionary.get( key );
      
      if ( res != null )
         sb.append( res );
      else
         Log.e( TAG, "No dict entry for " + key );
   }
   


   public void addEvery( StringBuilder sb, String unit, String quantity )
   {
      addPlural( sb, "every", unit, quantity );
   }
   


   public void addAfter( StringBuilder sb, String unit, String quantity )
   {
      addPlural( sb, "after", unit, quantity );
   }
   


   public void addStToX( StringBuilder sb, int x )
   {
      final String xStr = String.valueOf( x );
      
      sb.append( xStr );
      
      final String xst = dictionary.get( "xst" );
      
      if ( xst != null )
         sb.append( xst );
      else
      {
         if ( x > 3 && x < 20 )
         {
            sb.append( "th" );
         }
         else
         {
            final char lastNum = xStr.charAt( xStr.length() - 1 );
            
            switch ( lastNum )
            {
               case '1':
                  sb.append( "st" );
                  break;
               case '2':
                  sb.append( "nd" );
                  break;
               case '3':
                  sb.append( "rd" );
                  break;
               default :
                  sb.append( "th" );
                  break;
            }
         }
      }
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
   


   private void readPluralEntry( XmlResourceParser xmlParser ) throws ParseException,
                                                              XmlPullParserException,
                                                              IOException

   {
      final int attribCount = xmlParser.getAttributeCount();
      
      if ( attribCount < 2 )
         throw new ParseException( "Unexpected number of attributes in 'entryPl' tag. "
                                      + attribCount,
                                   xmlParser.getLineNumber() );
      
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
            final String value = xmlParser.getAttributeValue( i );
            
            dictionary.put( key + "_" + attribName, value );
         }
      }
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
         dictionary.put( key, value );
      else
         throw new ParseException( "Missing attribute",
                                   xmlParser.getLineNumber() );
   }
   


   private void addPlural( StringBuilder sb,
                           String prefix,
                           String unit,
                           String quantity )
   {
      String res = null;
      
      final String key = prefix + "_" + unit + "_";
      
      res = dictionary.get( key + quantity );
      
      if ( res == null )
         res = dictionary.get( key + "n" );
      
      if ( res != null )
      {
         sb.append( String.format( res, quantity ) );
      }
      else
         Log.e( TAG, "No dict entry for " + prefix + "_" + unit );
   }
}
