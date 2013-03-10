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

package dev.drsoran.moloko.grammar;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.antlr.runtime.RecognitionException;

import android.util.Pair;
import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.grammar.datetime.IDateParser;
import dev.drsoran.moloko.grammar.datetime.IDateTimeParserRepository;
import dev.drsoran.moloko.grammar.datetime.ITimeParser;
import dev.drsoran.moloko.grammar.datetime.ParseDateReturn;
import dev.drsoran.moloko.grammar.datetime.ParseDateWithinReturn;
import dev.drsoran.moloko.grammar.datetime.ParseTimeReturn;


public class DateTimeParsing implements IDateTimeParsing
{
   private final static Pattern TIME_PREFIX_PATTERN = Pattern.compile( "^\\s*\\d+(:\\d){0,2}.*," );
   
   private final IDateTimeParserRepository parserRepository;
   
   private IDateParser dateParser;
   
   private ITimeParser timeParser;
   
   
   
   public DateTimeParsing( IDateTimeParserRepository parserRepository )
   {
      this.parserRepository = parserRepository;
   }
   
   
   
   @Override
   public MolokoCalendar parseTimeOrTimeSpec( String spec )
   {
      final MolokoCalendar cal = MolokoCalendar.getInstance();
      
      boolean error = false;
      
      try
      {
         parseTimeSpec( spec, cal, false );
      }
      catch ( RecognitionException e )
      {
         try
         {
            parseTime( spec, cal, false );
         }
         catch ( RecognitionException e1 )
         {
            error = true;
         }
      }
      
      return ( !error ) ? cal : null;
   }
   
   
   
   @Override
   public MolokoCalendar parseDateTimeSpec( String spec )
   {
      final MolokoCalendar cal = MolokoCalendar.getInstance();
      
      boolean parsingFailed = true;
      boolean hasTime = false;
      boolean hasDate = false;
      
      int startOfDatePos = 0;
      
      // first try to parse time spec
      try
      {
         parsingFailed = false;
         final Matcher timePrefixMatcher = TIME_PREFIX_PATTERN.matcher( spec );
         
         if ( timePrefixMatcher.find() )
         {
            // The parser can adjust the day of week
            // for times in the past.
            parseTimeSpec( timePrefixMatcher.group(), cal, !hasDate );
            
            startOfDatePos = timePrefixMatcher.end();
            hasTime = cal.hasTime();
         }
      }
      catch ( RecognitionException e )
      {
         parsingFailed = true;
      }
      
      final String datePart = spec.substring( startOfDatePos, spec.length() );
      boolean eof = datePart.length() == 0;
      
      int endOfDatePos = spec.length();
      if ( !eof )
      {
         parsingFailed = false;
         
         try
         {
            final ParseDateReturn ret = parseDate( datePart, cal, !hasTime );
            eof = ret.isEof;
            endOfDatePos = ret.lastParsedCharPos;
            hasDate = cal.hasDate();
         }
         catch ( RecognitionException e )
         {
            endOfDatePos = 0;
            parsingFailed = true;
         }
         
         // Check if there is a time trailing or we only have a time.
         // The parser can NOT adjust the day of week for times in the past if we have already
         // parsed a date.
         if ( !eof && !hasTime )
         {
            final String potentialTimePart = spec.substring( endOfDatePos,
                                                             spec.length() );
            
            eof = potentialTimePart.length() == 0;
            if ( !eof )
            {
               parsingFailed = false;
               
               try
               {
                  eof = parseTime( potentialTimePart, cal, !hasDate ).isEof;
                  hasTime = cal.hasTime();
               }
               catch ( RecognitionException re2 )
               {
                  parsingFailed = true;
               }
               
               if ( !eof && !hasTime )
               {
                  parsingFailed = false;
                  
                  try
                  {
                     eof = parseTimeSpec( potentialTimePart, cal, !hasDate ).isEof;
                     hasTime = cal.hasTime();
                  }
                  catch ( RecognitionException re3 )
                  {
                     parsingFailed = true;
                  }
               }
            }
         }
      }
      
      return !parsingFailed ? cal : null;
   }
   
   
   
   @Override
   public long parseEstimated( String estimated )
   {
      try
      {
         return parseTimeEstimate( estimated );
      }
      catch ( RecognitionException e )
      {
         return -1L;
      }
   }
   
   
   
   @Override
   public ParseDateWithinReturn parseDateWithin( String range, boolean past )
   {
      try
      {
         return parseDateWithinImpl( range, past );
      }
      catch ( RecognitionException e )
      {
         return null;
      }
   }
   
   
   
   @Override
   public boolean existsParserWithMatchingLocale( Locale locale )
   {
      return parserRepository.existsDateParserForLocale( locale );
   }
   
   
   
   private ParseTimeReturn parseTime( final String time,
                                      final MolokoCalendar cal,
                                      final boolean adjustDay ) throws RecognitionException
   {
      return detectLanguageAndParseTime( new IParserFunc< ITimeParser, ParseTimeReturn >()
      {
         @Override
         public ParseTimeReturn call( ITimeParser timeParser ) throws RecognitionException
         {
            return timeParser.parseTime( time, cal, adjustDay );
         }
      } );
   }
   
   
   
   private ParseTimeReturn parseTimeSpec( final String timeSpec,
                                          final MolokoCalendar cal,
                                          final boolean adjustDay ) throws RecognitionException
   {
      return detectLanguageAndParseTime( new IParserFunc< ITimeParser, ParseTimeReturn >()
      {
         @Override
         public ParseTimeReturn call( ITimeParser timeParser ) throws RecognitionException
         {
            return timeParser.parseTimeSpec( timeSpec, cal, adjustDay );
         }
      } );
   }
   
   
   
   private long parseTimeEstimate( final String timeEstimate ) throws RecognitionException
   {
      return detectLanguageAndParseTime( new IParserFunc< ITimeParser, Long >()
      {
         @Override
         public Long call( ITimeParser timeParser ) throws RecognitionException
         {
            final Long res = timeParser.parseTimeEstimate( timeEstimate );
            return res != null ? res.longValue() : -1;
         }
      } );
   }
   
   
   
   private ParseDateReturn parseDate( final String date,
                                      final MolokoCalendar cal,
                                      final boolean clearTime ) throws RecognitionException
   {
      return detectLanguageAndParseDate( new IParserFunc< IDateParser, ParseDateReturn >()
      {
         @Override
         public ParseDateReturn call( IDateParser dateParser ) throws RecognitionException
         {
            return dateParser.parseDate( date, cal, clearTime );
         }
      } );
   }
   
   
   
   private ParseDateWithinReturn parseDateWithinImpl( final String range,
                                                      final boolean past ) throws RecognitionException
   {
      return detectLanguageAndParseDate( new IParserFunc< IDateParser, ParseDateWithinReturn >()
      {
         @Override
         public ParseDateWithinReturn call( IDateParser dateParser ) throws RecognitionException
         {
            return dateParser.parseDateWithin( range, past );
         }
      } );
   }
   
   
   
   private < T > T detectLanguageAndParseDate( IParserFunc< IDateParser, T > parserFunc ) throws RecognitionException
   {
      final Pair< IDateParser, T > detectResult = ParserLanguageDetector.detectLanguageAndParse( dateParser,
                                                                                                 parserRepository.getDateParsers(),
                                                                                                 parserFunc );
      dateParser = detectResult.first;
      
      return detectResult.second;
   }
   
   
   
   private < T > T detectLanguageAndParseTime( IParserFunc< ITimeParser, T > parserFunc ) throws RecognitionException
   {
      final Pair< ITimeParser, T > detectResult = ParserLanguageDetector.detectLanguageAndParse( timeParser,
                                                                                                 parserRepository.getTimeParsers(),
                                                                                                 parserFunc );
      timeParser = detectResult.first;
      
      return detectResult.second;
   }
}
