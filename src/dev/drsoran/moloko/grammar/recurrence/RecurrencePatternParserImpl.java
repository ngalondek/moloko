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

package dev.drsoran.moloko.grammar.recurrence;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import android.text.TextUtils;
import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.domain.parsing.IDateFormatter;


public class RecurrencePatternParserImpl implements IRecurrencePatternParser
{
   private final RecurrencePatternLexer patternLexer = new RecurrencePatternLexer();
   
   private final RecurrencePatternParser patternParser = new RecurrencePatternParser();
   
   private final ILog log;
   
   private IRecurrenceSentenceLanguage sentenceLangauge;
   
   
   
   public RecurrencePatternParserImpl( ILog log, IDateFormatter dateFormatter,
      IRecurrenceSentenceLanguage recurrenceSentenceLanguage )
   {
      this.log = log;
      this.sentenceLangauge = recurrenceSentenceLanguage;
      
      patternParser.setDateFormatter( dateFormatter );
   }
   
   
   
   @Override
   public void setRecurrenceSentenceLanguage( IRecurrenceSentenceLanguage recurrenceSentenceLanguage )
   {
      this.sentenceLangauge = recurrenceSentenceLanguage;
   }
   
   
   
   @Override
   public IRecurrenceSentenceLanguage getRecurrenceSentenceLanguage()
   {
      return sentenceLangauge;
   }
   
   
   
   @Override
   public String parseRecurrencePatternToSentence( String pattern,
                                                   boolean isEvery )
   {
      String result = null;
      
      patternLexer.setCharStream( new ANTLRStringStream( pattern ) );
      
      final CommonTokenStream antlrTokens = new CommonTokenStream( patternLexer );
      patternParser.setTokenStream( antlrTokens );
      
      try
      {
         result = patternParser.parseRecurrencePattern( sentenceLangauge,
                                                        isEvery );
      }
      catch ( RecognitionException e )
      {
         log.w( getClass(), "Failed to parse recurrence pattern " + pattern, e );
         result = null;
      }
      
      return result;
   }
   
   
   
   @Override
   public Map< Integer, List< Object >> parseRecurrencePattern( String pattern )
   {
      if ( TextUtils.isEmpty( pattern ) )
      {
         return Collections.emptyMap();
      }
      
      patternLexer.setCharStream( new ANTLRStringStream( pattern ) );
      
      final CommonTokenStream antlrTokens = new CommonTokenStream( patternLexer );
      patternParser.setTokenStream( antlrTokens );
      
      try
      {
         return patternParser.parseRecurrencePattern1();
      }
      catch ( RecognitionException e )
      {
         log.d( getClass(), "Failed to parse recurrence pattern " + pattern, e );
         return null;
      }
   }
}
