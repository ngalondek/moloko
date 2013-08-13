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

package dev.drsoran.moloko.domain.parsing;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import android.text.TextUtils;
import android.util.Pair;
import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.domain.parsing.lang.IRecurrenceSentenceLanguage;
import dev.drsoran.moloko.domain.parsing.util.ParserLanguageDetector;
import dev.drsoran.moloko.grammar.antlr.recurrence.RecurrencePatternParser;


public class RecurrenceParsing implements IRecurrenceParsing
{
   private final ILog log;
   
   private final IRecurrenceParserRepository parserRepository;
   
   private IRecurrenceParser recurrenceParser;
   
   
   
   public RecurrenceParsing( ILog log,
      IRecurrenceParserRepository parserRepository )
   {
      this.log = log;
      this.parserRepository = parserRepository;
   }
   
   
   
   @Override
   public void setRecurrenceSentenceLanguage( IRecurrenceSentenceLanguage recurrenceSentenceLanguage )
   {
      parserRepository.getRecurrencePatternParser()
                      .setRecurrenceSentenceLanguage( recurrenceSentenceLanguage );
   }
   
   
   
   @Override
   public IRecurrenceSentenceLanguage getRecurrenceSentenceLanguage()
   {
      return parserRepository.getRecurrencePatternParser()
                             .getRecurrenceSentenceLanguage();
   }
   
   
   
   @Override
   public String parseRecurrencePatternToSentence( String pattern,
                                                   boolean isEvery )
   {
      return parserRepository.getRecurrencePatternParser()
                             .parseRecurrencePatternToSentence( pattern,
                                                                isEvery );
   }
   
   
   
   @Override
   public Map< Integer, List< Object >> parseRecurrencePattern( String pattern )
   {
      return parserRepository.getRecurrencePatternParser()
                             .parseRecurrencePattern( pattern );
   }
   
   
   
   @Override
   public String ensureRecurrencePatternOrder( String recurrencePattern )
   {
      final String[] operators = recurrencePattern.split( RecurrencePatternParser.OPERATOR_SEP );
      Arrays.sort( operators, RecurrencePatternParser.CMP_OPERATORS );
      
      return TextUtils.join( RecurrencePatternParser.OPERATOR_SEP, operators );
   }
   
   
   
   @Override
   public Pair< String, Boolean > parseRecurrence( final String recurrence )
   {
      final Pair< String, Boolean > result = detectLanguageAndParseRecurrence( new IParserFunc< IRecurrenceParser, Pair< String, Boolean > >()
      {
         @Override
         public Pair< String, Boolean > call( IRecurrenceParser recurrenceParser ) throws GrammarException
         {
            return parseRecurrence( recurrenceParser, recurrence );
         }
      } );
      
      if ( result == null )
      {
         log.w( getClass(), "Failed to parse recurrence " + recurrence );
      }
      
      return result;
   }
   
   
   
   @Override
   public boolean existsParserWithMatchingLocale( Locale locale )
   {
      return parserRepository.existsRecurrenceParser( locale );
   }
   
   
   
   private Pair< String, Boolean > parseRecurrence( IRecurrenceParser recurrenceParser,
                                                    String recurrence ) throws GrammarException
   {
      final Map< String, Object > patternObjects = recurrenceParser.parseRecurrence( recurrence );
      final Boolean isEvery = (Boolean) patternObjects.remove( RecurrencePatternParser.IS_EVERY );
      
      return Pair.create( joinRecurrencePattern( patternObjects ), isEvery );
   }
   
   
   
   private < T > T detectLanguageAndParseRecurrence( IParserFunc< IRecurrenceParser, T > parserFunc )
   {
      final Pair< IRecurrenceParser, T > detectResult = ParserLanguageDetector.detectLanguageAndParse( recurrenceParser,
                                                                                                       parserRepository.getRecurrenceParsers(),
                                                                                                       parserFunc );
      recurrenceParser = detectResult.first;
      return detectResult.second;
   }
   
   
   
   private final static String joinRecurrencePattern( Map< String, Object > parts )
   {
      final StringBuilder sb = new StringBuilder();
      final Set< String > keys = parts.keySet();
      
      for ( Iterator< String > i = keys.iterator(); i.hasNext(); )
      {
         final String key = i.next();
         sb.append( key ).append( "=" ).append( parts.get( key ) );
         
         if ( i.hasNext() )
         {
            sb.append( RecurrencePatternParser.OPERATOR_SEP );
         }
      }
      
      return sb.toString();
   }
}
