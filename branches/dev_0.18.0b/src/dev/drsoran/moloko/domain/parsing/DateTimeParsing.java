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

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.antlr.v4.runtime.ANTLRErrorStrategy;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.domain.parsing.datetime.DateEvaluator;
import dev.drsoran.moloko.domain.parsing.datetime.IDateTimeParserFactory;
import dev.drsoran.moloko.domain.parsing.datetime.ParseDateWithinReturn;
import dev.drsoran.moloko.domain.parsing.datetime.ParseReturn;
import dev.drsoran.moloko.domain.parsing.datetime.RtmLikeDateEvaluator;
import dev.drsoran.moloko.domain.parsing.datetime.TimeEstimateEvaluator;
import dev.drsoran.moloko.domain.parsing.datetime.TimeEvaluator;
import dev.drsoran.moloko.domain.parsing.lang.IDateLanguageRepository;
import dev.drsoran.moloko.domain.parsing.util.ParserLanguageDetector;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser;
import dev.drsoran.moloko.grammar.antlr.datetime.TimeParser;
import dev.drsoran.moloko.util.Lambda.Func1;
import dev.drsoran.moloko.util.Strings;


public class DateTimeParsing implements IDateTimeParsing
{
   private final static Pattern TIME_PREFIX_PATTERN = Pattern.compile( "^\\s*\\d+(:\\d){0,2}.*," );
   
   private final ANTLRErrorStrategy parseErrorHandler = new BailErrorStrategy();
   
   private final IDateTimeParserFactory parserFactory;
   
   private final IDateFormatter dateFormatter;
   
   private final IDateLanguageRepository dateLanguageRepository;
   
   private final MolokoCalenderProvider calenderProvider;
   
   private final ParserLanguageDetector< TimeParser > timeParserLanguageDetector;
   
   private final ParserLanguageDetector< DateParser > dateParserLanguageDetector;
   
   
   
   public DateTimeParsing( IDateTimeParserFactory parserFactory,
      IDateFormatter dateFormatter,
      IDateLanguageRepository dateLanguageRepository,
      MolokoCalenderProvider calenderProvider )
   {
      this.parserFactory = parserFactory;
      this.dateFormatter = dateFormatter;
      this.dateLanguageRepository = dateLanguageRepository;
      this.calenderProvider = calenderProvider;
      
      final Iterable< Locale > availableLocales = parserFactory.getAvailableParserLocales();
      this.timeParserLanguageDetector = new ParserLanguageDetector< TimeParser >( availableLocales );
      this.dateParserLanguageDetector = new ParserLanguageDetector< DateParser >( availableLocales );
   }
   
   
   
   @Override
   public MolokoCalendar parseTime( String time ) throws GrammarException
   {
      final MolokoCalendar cal = MolokoCalendar.getInstance();
      parseTime( cal, time, false );
      
      return cal;
   }
   
   
   
   @Override
   public MolokoCalendar parseDateTime( String dateTime ) throws GrammarException
   {
      if ( Strings.isNullOrEmpty( dateTime ) )
      {
         throw new GrammarException( "An empty spec is not parsable." );
      }
      
      final MolokoCalendar cal = MolokoCalendar.getInstance();
      final int specLength = dateTime.length();
      
      int startOfDatePos = 0;
      GrammarException lastGrammarException = null;
      
      // first try to parse time spec
      try
      {
         final Matcher timePrefixMatcher = TIME_PREFIX_PATTERN.matcher( dateTime );
         
         if ( timePrefixMatcher.find() )
         {
            // The parser can adjust the day of week
            // for times in the past.
            parseTime( cal, timePrefixMatcher.group(), !cal.hasDate() );
            startOfDatePos = timePrefixMatcher.end();
         }
      }
      catch ( GrammarException e )
      {
         lastGrammarException = e;
      }
      
      final String datePart = dateTime.substring( startOfDatePos, specLength );
      boolean hasMoreToParse = datePart.length() > 0;
      
      // If we only had a time, then check if at least the time parsing succeeded.
      if ( !hasMoreToParse )
      {
         if ( lastGrammarException != null )
         {
            throw lastGrammarException;
         }
         else
         {
            throw new GrammarException();
         }
      }
      
      // Try parsing the date part as Date
      lastGrammarException = null;
      int endOfDatePos;
      try
      {
         final ParseReturn ret = parseDate( cal, datePart, !cal.hasTime() );
         hasMoreToParse = !ret.isEof;
         endOfDatePos = ret.numParsedChars;
      }
      catch ( GrammarException e )
      {
         lastGrammarException = e;
         endOfDatePos = 0;
      }
      
      // Check if there is a time trailing or we only have a time.
      // The parser can NOT adjust the day of week for times in the past if we have already
      // parsed a date.
      if ( hasMoreToParse && !cal.hasTime() )
      {
         lastGrammarException = null;
         
         final String potentialTimePart = dateTime.substring( endOfDatePos,
                                                              specLength );
         hasMoreToParse = potentialTimePart.length() > 0;
         
         if ( hasMoreToParse )
         {
            try
            {
               hasMoreToParse = !parseTime( cal,
                                            potentialTimePart,
                                            !cal.hasDate() ).isEof;
            }
            catch ( GrammarException e )
            {
               lastGrammarException = e;
            }
         }
      }
      
      if ( lastGrammarException != null )
      {
         throw lastGrammarException;
      }
      
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
         if ( availableLocale == locale )
         {
            return true;
         }
      }
      
      return false;
   }
   
   
   
   private ParseReturn parseTime( final MolokoCalendar cal,
                                  final String time,
                                  final boolean adjustDay ) throws GrammarException
   {
      return detectLanguageAndParseTime( new Func1< Locale, ParseReturn >()
      {
         @Override
         public ParseReturn call( Locale locale )
         {
            final TimeParser timeParser = createTimeParser( locale, time );
            final ParseTree tree = timeParser.parseTime();
            
            final TimeEvaluator timeEvaluator = new TimeEvaluator( cal );
            timeEvaluator.visit( tree );
            
            final MolokoCalendar cal = timeEvaluator.getCalendar();
            
            return createParserReturnResult( cal, timeParser.getTokenStream() );
         }
      } );
   }
   
   
   
   private long parseTimeEstimate( final String timeEstimate ) throws GrammarException
   {
      return detectLanguageAndParseTime( new Func1< Locale, Long >()
      {
         @Override
         public Long call( Locale locale )
         {
            final TimeParser timeParser = createTimeParser( locale,
                                                            timeEstimate );
            final ParseTree tree = timeParser.parseTime();
            
            final TimeEstimateEvaluator timeEstimateEvaluator = new TimeEstimateEvaluator();
            timeEstimateEvaluator.visit( tree );
            
            return timeEstimateEvaluator.getEstimateMillis();
         }
      } );
   }
   
   
   
   private ParseReturn parseDate( final MolokoCalendar cal,
                                  final String date,
                                  final boolean clearTime ) throws GrammarException
   {
      return detectLanguageAndParseDate( new Func1< Locale, ParseReturn >()
      {
         @Override
         public ParseReturn call( Locale locale )
         {
            final DateParser dateParser = createDateParser( locale, date );
            final ParseTree tree = dateParser.parseDate();
            
            final RtmLikeDateEvaluator dateEvaluator = new RtmLikeDateEvaluator( new DateEvaluator( dateLanguageRepository.getLanguage( locale ),
                                                                                                    dateFormatter,
                                                                                                    calenderProvider ),
                                                                                 calenderProvider );
            dateEvaluator.visit( tree );
            
            final MolokoCalendar cal = dateEvaluator.getCalendar();
            
            return createParserReturnResult( cal, dateParser.getTokenStream() );
         }
      } );
   }
   
   
   
   private ParseDateWithinReturn parseDateWithinImpl( final String range ) throws GrammarException
   {
      return detectLanguageAndParseDate( new Func1< Locale, ParseDateWithinReturn >()
      {
         @Override
         public ParseDateWithinReturn call( Locale locale )
         {
            final DateParser dateParser = createDateParser( locale, range );
            final ParseTree tree = dateParser.parseDateWithin();
            
            final RtmLikeDateEvaluator dateEvaluator = new RtmLikeDateEvaluator( new DateEvaluator( dateLanguageRepository.getLanguage( locale ),
                                                                                                    dateFormatter,
                                                                                                    calenderProvider ),
                                                                                 calenderProvider );
            dateEvaluator.visit( tree );
            
            return new ParseDateWithinReturn( dateEvaluator.getEpochStart(),
                                              dateEvaluator.getEpochEnd() );
         }
      } );
   }
   
   
   
   private < T > T detectLanguageAndParseDate( Func1< Locale, T > parserFunc ) throws GrammarException
   {
      try
      {
         final T detectResult = dateParserLanguageDetector.detectLanguageAndParse( parserFunc );
         
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
   
   
   
   private < T > T detectLanguageAndParseTime( Func1< Locale, T > parserFunc ) throws GrammarException
   {
      try
      {
         final T detectResult = timeParserLanguageDetector.detectLanguageAndParse( parserFunc );
         
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
   
   
   
   private TimeParser createTimeParser( Locale locale, String timeToParse )
   {
      final TimeParser timeParser = parserFactory.createTimeParser( locale,
                                                                    timeToParse );
      timeParser.setErrorHandler( parseErrorHandler );
      
      return timeParser;
   }
   
   
   
   private DateParser createDateParser( Locale locale, String dateToParse )
   {
      final DateParser dateParser = parserFactory.createDateParser( locale,
                                                                    dateToParse );
      dateParser.setErrorHandler( parseErrorHandler );
      
      return dateParser;
   }
   
   
   
   private ParseReturn createParserReturnResult( MolokoCalendar cal,
                                                 TokenStream tokenStream )
   {
      final Token lastToken = tokenStream.LT( 1 );
      
      final boolean isEof = lastToken.getType() == Token.EOF;
      final int numParsedChars = lastToken.getCharPositionInLine();
      
      return new ParseReturn( numParsedChars, isEof, cal );
   }
}
