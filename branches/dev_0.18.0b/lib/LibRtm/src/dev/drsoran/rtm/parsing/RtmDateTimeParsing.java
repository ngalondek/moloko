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

package dev.drsoran.rtm.parsing;

import java.util.Locale;

import org.antlr.v4.runtime.ANTLRErrorStrategy;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;

import dev.drsoran.rtm.Lambda.Func1;
import dev.drsoran.rtm.RtmCalendar;
import dev.drsoran.rtm.parsing.datetime.DefaultDateTimeEvaluator;
import dev.drsoran.rtm.parsing.datetime.IRtmDateTimeParserFactory;
import dev.drsoran.rtm.parsing.datetime.ParseDateWithinReturn;
import dev.drsoran.rtm.parsing.datetime.RtmLikeDateTimeEvaluator;
import dev.drsoran.rtm.parsing.datetime.TimeEstimateEvaluator;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParser;
import dev.drsoran.rtm.parsing.lang.IDateLanguageRepository;
import dev.drsoran.rtm.parsing.util.ParserLanguageDetector;


public class RtmDateTimeParsing implements IRtmDateTimeParsing
{
   private final ANTLRErrorStrategy parseErrorHandler = new BailErrorStrategy();
   
   private final IRtmDateTimeParserFactory parserFactory;
   
   private final IDateFormatter dateFormatter;
   
   private final IDateLanguageRepository dateLanguageRepository;
   
   private final IRtmCalendarProvider calenderProvider;
   
   private final ParserLanguageDetector< DateTimeParser > dateTimeParserLanguageDetector;
   
   
   
   public RtmDateTimeParsing( IRtmDateTimeParserFactory parserFactory,
      IDateFormatter dateFormatter,
      IDateLanguageRepository dateLanguageRepository,
      IRtmCalendarProvider calenderProvider )
   {
      this.parserFactory = parserFactory;
      this.dateFormatter = dateFormatter;
      this.dateLanguageRepository = dateLanguageRepository;
      this.calenderProvider = calenderProvider;
      
      final Iterable< Locale > availableLocales = parserFactory.getAvailableParserLocales();
      this.dateTimeParserLanguageDetector = new ParserLanguageDetector< DateTimeParser >( availableLocales );
   }
   
   
   
   @Override
   public RtmCalendar parseTime( String time ) throws GrammarException
   {
      RtmCalendar cal = calenderProvider.getNow();
      cal = parseTime( cal, time );
      
      return cal;
   }
   
   
   
   @Override
   public RtmCalendar parseDateTime( String dateTime ) throws GrammarException
   {
      RtmCalendar cal = calenderProvider.getNow();
      cal = parseDateTime( cal, dateTime );
      
      return cal;
   }
   
   
   
   @Override
   public long parseEstimated( String estimated ) throws GrammarException
   {
      return parseTimeEstimate( estimated );
   }
   
   
   
   @Override
   public ParseDateWithinReturn parseDateWithin( String range ) throws GrammarException
   {
      return parseDateWithinImpl( range );
   }
   
   
   
   @Override
   public boolean existsParserWithMatchingLocale( Locale locale )
   {
      for ( Locale availableLocale : parserFactory.getAvailableParserLocales() )
      {
         if ( availableLocale.equals( locale ) )
         {
            return true;
         }
      }
      
      return false;
   }
   
   
   
   private RtmCalendar parseTime( final RtmCalendar cal, final String time ) throws GrammarException
   {
      return detectLanguageAndParseDateTime( new Func1< Locale, RtmCalendar >()
      {
         @Override
         public RtmCalendar call( Locale locale )
         {
            final DateTimeParser dateTimeParser = createDateTimeParser( locale,
                                                                        time );
            final ParseTree tree = dateTimeParser.parseTime();
            
            final RtmLikeDateTimeEvaluator evaluator = createDateTimeEvaluator( locale );
            evaluator.visit( tree );
            
            final RtmCalendar cal = evaluator.getCalendar();
            
            return cal;
         }
      } );
   }
   
   
   
   private long parseTimeEstimate( final String timeEstimate ) throws GrammarException
   {
      return detectLanguageAndParseDateTime( new Func1< Locale, Long >()
      {
         @Override
         public Long call( Locale locale )
         {
            final DateTimeParser timeParser = createDateTimeParser( locale,
                                                                    timeEstimate );
            final ParseTree tree = timeParser.parseTimeEstimate();
            
            final TimeEstimateEvaluator timeEstimateEvaluator = new TimeEstimateEvaluator();
            timeEstimateEvaluator.visit( tree );
            
            return timeEstimateEvaluator.getEstimateMillis();
         }
      } );
   }
   
   
   
   private RtmCalendar parseDateTime( final RtmCalendar cal,
                                      final String dateTimeToParse ) throws GrammarException
   {
      return detectLanguageAndParseDateTime( new Func1< Locale, RtmCalendar >()
      {
         @Override
         public RtmCalendar call( Locale locale )
         {
            final DateTimeParser dateTimeParser = createDateTimeParser( locale,
                                                                        dateTimeToParse );
            final ParseTree tree = dateTimeParser.parseDateTime();
            
            final RtmLikeDateTimeEvaluator dateEvaluator = createDateTimeEvaluator( locale );
            dateEvaluator.visit( tree );
            
            final RtmCalendar cal = dateEvaluator.getCalendar();
            
            return cal;
         }
      } );
   }
   
   
   
   private ParseDateWithinReturn parseDateWithinImpl( final String range ) throws GrammarException
   {
      return detectLanguageAndParseDateTime( new Func1< Locale, ParseDateWithinReturn >()
      {
         @Override
         public ParseDateWithinReturn call( Locale locale )
         {
            final DateTimeParser dateTimeParser = createDateTimeParser( locale,
                                                                        range );
            final ParseTree tree = dateTimeParser.parseDateWithin();
            
            final RtmLikeDateTimeEvaluator dateEvaluator = createDateTimeEvaluator( locale );
            dateEvaluator.visit( tree );
            
            return new ParseDateWithinReturn( dateEvaluator.getEpochStart(),
                                              dateEvaluator.getEpochEnd() );
         }
      } );
   }
   
   
   
   private < T > T detectLanguageAndParseDateTime( Func1< Locale, T > parserFunc ) throws GrammarException
   {
      try
      {
         final T detectResult = dateTimeParserLanguageDetector.detectLanguageAndParse( parserFunc );
         
         if ( detectResult == null )
         {
            throw new GrammarException();
         }
         
         return detectResult;
      }
      catch ( ParseCancellationException e )
      {
         throw new GrammarException( e );
      }
   }
   
   
   
   private DateTimeParser createDateTimeParser( Locale locale, String toParse )
   {
      final DateTimeParser timeParser = parserFactory.createDateTimeParser( locale,
                                                                            toParse );
      timeParser.setErrorHandler( parseErrorHandler );
      
      return timeParser;
   }
   
   
   
   private RtmLikeDateTimeEvaluator createDateTimeEvaluator( Locale locale )
   {
      final RtmLikeDateTimeEvaluator dateEvaluator = new RtmLikeDateTimeEvaluator( new DefaultDateTimeEvaluator( dateLanguageRepository.getLanguage( locale ),
                                                                                                                 dateFormatter,
                                                                                                                 calenderProvider ),
                                                                                   calenderProvider );
      return dateEvaluator;
   }
}
