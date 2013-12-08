/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.moloko.test;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;


public class XmlFileResource extends FileResource
{
   public XmlFileResource( Class< ? > clazz, String filename )
   {
      super( clazz, filename );
   }
   
   
   
   public XMLReader getXmlReader()
   {
      try
      {
         return SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      }
      catch ( ParserConfigurationException e )
      {
         throw new RuntimeException( e );
      }
      catch ( SAXException e )
      {
         throw new RuntimeException( e );
      }
   }
   
   
   
   public Document getDocument()
   {
      try
      {
         return DocumentBuilderFactory.newInstance()
                                      .newDocumentBuilder()
                                      .parse( getInputStream() );
      }
      catch ( SAXException e )
      {
         throw new RuntimeException( e );
      }
      catch ( IOException e )
      {
         throw new RuntimeException( e );
      }
      catch ( ParserConfigurationException e )
      {
         throw new RuntimeException( e );
      }
   }
}
