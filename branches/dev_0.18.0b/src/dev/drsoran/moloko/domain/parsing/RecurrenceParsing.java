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

import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;

import dev.drsoran.moloko.domain.parsing.lang.IDateLanguageRepository;
import dev.drsoran.moloko.domain.parsing.lang.IRecurrenceSentenceLanguage;
import dev.drsoran.moloko.domain.parsing.recurrence.IRecurrenceParserFactory;
import dev.drsoran.moloko.domain.parsing.recurrence.RecurrenceEvaluator;
import dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternCollector;
import dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternOperatorComp;
import dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax;
import dev.drsoran.moloko.domain.parsing.recurrence.RecurrenceSentenceEvaluator;
import dev.drsoran.moloko.domain.parsing.util.ParserLanguageDetector;
import dev.drsoran.moloko.grammar.antlr.recurrence.RecurrenceParser;
import dev.drsoran.moloko.grammar.antlr.recurrence.RecurrencePatternParser;
import dev.drsoran.moloko.util.Lambda.Func1;
import dev.drsoran.moloko.util.Pair;
import dev.drsoran.moloko.util.Strings;


public class RecurrenceParsing implements IRecurrenceParsing
{
   private final IRecurrenceParserFactory recurrenceParserFactory;
   
   private final IDateFormatter dateFormatter;
   
   private final IDateTimeParsing dateTimeParsing;
   
   private final IRecurrenceSentenceLanguage recurrenceLanguage;
   
   private final IDateLanguageRepository dateLanguageRepository;
   
   private final ParserLanguageDetector< RecurrenceParser > recurrenceParserLanguageDetector;
   
   
   
   public RecurrenceParsing( IRecurrenceParserFactory recurrenceParserFactory,
      IRecurrenceSentenceLanguage recurrenceLanguage,
      IDateFormatter dateFormatter, IDateTimeParsing dateTimeParsing,
      IDateLanguageRepository dateLanguageRepository )
   {
      this.recurrenceParserFactory = recurrenceParserFactory;
      this.recurrenceLanguage = recurrenceLanguage;
      this.dateFormatter = dateFormatter;
      this.dateTimeParsing = dateTimeParsing;
      this.dateLanguageRepository = dateLanguageRepository;
      this.recurrenceParserLanguageDetector = new ParserLanguageDetector< RecurrenceParser >( recurrenceParserFactory.getAvailableParserLocales() );
   }
   
   
   
   @Override
   public String parseRecurrencePatternToSentence( String pattern,
                                                   boolean isEvery ) throws GrammarException
   
   {
      try
      {
         final RecurrencePatternParser patternParser = recurrenceParserFactory.createRecurrencePatternParser( pattern );
         patternParser.setErrorHandler( new BailErrorStrategy() );
         
         final ParseTree tree = patternParser.parseRecurrencePattern();
         final RecurrenceSentenceEvaluator evaluator = new RecurrenceSentenceEvaluator( dateFormatter,
                                                                                        recurrenceLanguage,
                                                                                        isEvery );
         evaluator.visit( tree );
         
         return evaluator.getSentence();
      }
      catch ( ParseCancellationException e )
      {
         throw new GrammarException( e );
      }
   }
   
   
   
   @Override
   public Map< Integer, List< Object >> tokenizeRecurrencePattern( String pattern ) throws GrammarException
   {
      try
      {
         return RecurrencePatternCollector.collectTokens( pattern );
      }
      catch ( ParseCancellationException e )
      {
         throw new GrammarException( e );
      }
   }
   
   
   
   @Override
   public Pair< String, Boolean > parseRecurrence( String recurrence ) throws GrammarException
   {
      final Map< String, Object > result = detectLanguageAndParseRecurrence( recurrence );
      final Boolean isEvery = (Boolean) result.remove( RecurrencePatternSyntax.IS_EVERY );
      
      final String pattern = buildRecurrencePattern( result );
      
      return Pair.create( pattern, isEvery );
   }
   
   
   
   @Override
   public String ensureRecurrencePatternOrder( String recurrencePattern )
   {
      final String[] operators = recurrencePattern.split( RecurrencePatternSyntax.OPERATOR_SEP );
      Arrays.sort( operators, new RecurrencePatternOperatorComp() );
      
      return Strings.join( RecurrencePatternSyntax.OPERATOR_SEP, operators );
   }
   
   
   
   @Override
   public boolean existsParserWithMatchingLocale( Locale locale )
   {
      for ( Locale availLocale : recurrenceParserFactory.getAvailableParserLocales() )
      {
         if ( availLocale.equals( locale ) )
         {
            return true;
         }
      }
      
      return false;
   }
   
   
   
   private Map< String, Object > detectLanguageAndParseRecurrence( final String recurrenceSentence ) throws GrammarException
   {
      final Map< String, Object > detectResult = recurrenceParserLanguageDetector.detectLanguageAndParse( new Func1< Locale, Map< String, Object > >()
      {
         @Override
         public Map< String, Object > call( Locale locale )
         {
            final RecurrenceParser parser = recurrenceParserFactory.createRecurrenceParser( locale,
                                                                                            recurrenceSentence );
            parser.setErrorHandler( new BailErrorStrategy() );
            
            final ParseTree tree = parser.parseRecurrenceSentence();
            final RecurrenceEvaluator evaluator = new RecurrenceEvaluator( dateTimeParsing,
                                                                           dateLanguageRepository.getLanguage( locale ) );
            evaluator.visit( tree );
            
            return evaluator.getRecurrencePattern();
         }
      } );
      
      if ( detectResult == null )
      {
         throw new GrammarException();
      }
      
      return detectResult;
   }
   
   
   
   private final static String buildRecurrencePattern( Map< String, Object > patternParts )
   {
      final StringBuilder sb = new StringBuilder();
      final Set< String > keys = patternParts.keySet();
      
      for ( Iterator< String > i = keys.iterator(); i.hasNext(); )
      {
         final String key = i.next();
         sb.append( key )
           .append( RecurrencePatternSyntax.OPERATOR_VALUE_SEP )
           .append( patternParts.get( key ) );
         
         if ( i.hasNext() )
         {
            sb.append( RecurrencePatternSyntax.OPERATOR_SEP );
         }
      }
      
      return sb.toString();
   }
}
