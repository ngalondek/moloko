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

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;


public class RemoveWhiteSpaceXmlFilter extends XMLFilterImpl
{
   public RemoveWhiteSpaceXmlFilter( XMLReader parent )
   {
      super( parent );
   }
   
   
   
   @Override
   public void characters( char[] ch, int start, int length ) throws SAXException
   {
      int pos = start;
      int posEnd = start + length;
      while ( pos < posEnd )
      {
         if ( !Character.isWhitespace( ch[ pos ] ) )
         {
            super.characters( ch, start, length );
            return;
         }
         
         ++pos;
      }
   }
   
   
   
   @Override
   public void ignorableWhitespace( char[] ch, int start, int length ) throws SAXException
   {
   }
}
