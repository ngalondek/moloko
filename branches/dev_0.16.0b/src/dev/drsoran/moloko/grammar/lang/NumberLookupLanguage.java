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

import java.text.ParseException;

import org.xmlpull.v1.XmlPullParser;

import android.content.res.Resources;


public class NumberLookupLanguage extends Language
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + NumberLookupLanguage.class.getSimpleName();
   
   

   public NumberLookupLanguage( Resources resources, int langResId )
      throws ParseException
   {
      fromResources( resources, langResId );
   }
   


   public Integer get( String key )
   {
      Integer res = null;
      
      final String value = super.getString( key );
      
      if ( value != null )
      {
         res = Integer.parseInt( value );
      }
      
      return res;
   }
   


   @Override
   protected void validate( XmlPullParser xmlParser, String key, String value ) throws ParseException
   {
      try
      {
         Integer.parseInt( value );
      }
      catch ( NumberFormatException e )
      {
         throw new ParseException( "Expected Integer type attribute @line "
            + xmlParser.getLineNumber(), xmlParser.getLineNumber() );
      }
   }
}
