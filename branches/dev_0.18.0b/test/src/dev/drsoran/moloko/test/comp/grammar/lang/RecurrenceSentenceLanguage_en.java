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

package dev.drsoran.moloko.test.comp.grammar.lang;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Locale;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.grammar.lang.Language;
import dev.drsoran.moloko.grammar.lang.RecurrenceSentenceLanguage;
import dev.drsoran.moloko.grammar.lang.XmlLanguageReader;
import dev.drsoran.moloko.test.MolokoRoboTestCase;


@Config( qualifiers = "en" )
public class RecurrenceSentenceLanguage_en extends MolokoRoboTestCase
{
   private Language language;
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      
      XmlLanguageReader reader = null;
      try
      {
         language = new RecurrenceSentenceLanguage( Locale.ENGLISH,
                                                    EasyMock.createNiceMock( ILog.class ) );
         
         final Resources res = Robolectric.application.getResources();
         final XmlResourceParser xmlParser = res.getXml( R.xml.parser_lang_reccur_pattern );
         
         reader = new XmlLanguageReader( language, xmlParser );
      }
      finally
      {
         if ( reader != null )
         {
            reader.close();
         }
      }
   }
   
   
   
   @Test
   public void testEvery()
   {
      fail( "TODO!!" );
   }
   
}
