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

import android.text.TextUtils;
import android.util.Pair;
import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.grammar.datetime.IDateParser;
import dev.drsoran.moloko.grammar.datetime.IDateTimeParserRepository;
import dev.drsoran.moloko.grammar.datetime.ITimeParser;
import dev.drsoran.moloko.grammar.datetime.ParseDateWithinReturn;
import dev.drsoran.moloko.grammar.datetime.ParseReturn;


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
   public MolokoCalendar parseTimeOrTimeSpec( String spec ) throws GrammarException
   {
      final MolokoCalendar cal = MolokoCalendar.getInstance();
      
      try
      {
         parseTimeSpec( spec, cal, false );
      }
      catch ( GrammarException e )
      {
         parseTime( spec, cal, false );
      }
      
      return cal;
   }
   
   
   
   @Override
   public MolokoCalendar parseDateTimeSpec( String spec ) throws GrammarException
   {
      if ( TextUtils.isEmpty( spec ) )
      {
         throw new GrammarException( "An empty spec is not parsable." );
      }
      
      final MolokoCalendar cal = MolokoCalendar.getInstance();
      final int specLength = spec.length();
      
      int startOfDatePos = 0;
      GrammarException lastGrammarException = null;
      
      // first try to parse time spec
      try
      {
         final Matcher timePrefixMatcher = TIME_PREFIX_PATTERN.matcher( spec );
         
         if ( timePrefixMatcher.find() )
         {
            // The parser can adjust the day of week
            // for times in the past.
            parseTimeSpec( timePrefixMatcher.group(), cal, !cal.hasDate() );
            startOfDatePos = timePrefixMatcher.end();
         }
      }
      catch ( GrammarException e )
      {
         lastGrammarException = e;
      }
      
      final String datePart = spec.substring( startOfDatePos, specLength );
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
         final ParseReturn ret = parseDate( datePart, cal, !cal.hasTime() );
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
         
         final String potentialTimePart = spec.substring( endOfDatePos,
                                                          specLength );
         hasMoreToParse = potentialTimePart.length() > 0;
         
         if ( hasMoreToParse )
         {
            try
            {
               hasMoreToParse = !parseTime( potentialTimePart,
                                            cal,
                                            !cal.hasDate() ).isEof;
            }
            catch ( GrammarException e )
            {
               lastGrammarException = e;
            }
            
            if ( hasMoreToParse && !cal.hasTime() )
            {
               lastGrammarException = null;
               
               try
               {
                  parseTimeSpec( potentialTimePart, cal, !cal.hasDate() );
               }
               catch ( GrammarException e )
               {
                  lastGrammarException = e;
               }
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
   public ParseDateWithinReturn parseDateWithin( String range, boolean past ) throws GrammarException
   {
      return parseDateWithinImpl( range, past );
   }
   
   
   
   @Override
   public boolean existsParserWithMatchingLocale( Locale locale )
   {
      return parserRepository.existsDateParserForLocale( locale );
   }
   
   
   
   private ParseReturn parseTime( final String time,
                                  final MolokoCalendar cal,
                                  final boolean adjustDay ) throws GrammarException
   {
      return detectLanguageAndParseTime( new IParserFunc< ITimeParser, ParseReturn >()
      {
         @Override
         public ParseReturn call( ITimeParser timeParser ) throws GrammarException
         {
            return timeParser.parseTime( time, cal, adjustDay );
         }
      } );
   }
   
   
   
   private ParseReturn parseTimeSpec( final String timeSpec,
                                      final MolokoCalendar cal,
                                      final boolean adjustDay ) throws GrammarException
   {
      return detectLanguageAndParseTime( new IParserFunc< ITimeParser, ParseReturn >()
      {
         @Override
         public ParseReturn call( ITimeParser timeParser ) throws GrammarException
         {
            return timeParser.parseTimeSpec( timeSpec, cal, adjustDay );
         }
      } );
   }
   
   
   
   private long parseTimeEstimate( final String timeEstimate ) throws GrammarException
   {
      return detectLanguageAndParseTime( new IParserFunc< ITimeParser, Long >()
      {
         @Override
         public Long call( ITimeParser timeParser ) throws GrammarException
         {
            final Long res = timeParser.parseTimeEstimate( timeEstimate );
            return res != null ? res.longValue() : -1;
         }
      } );
   }
   
   
   
   private ParseReturn parseDate( final String date,
                                  final MolokoCalendar cal,
                                  final boolean clearTime ) throws GrammarException
   {
      return detectLanguageAndParseDate( new IParserFunc< IDateParser, ParseReturn >()
      {
         @Override
         public ParseReturn call( IDateParser dateParser ) throws GrammarException
         {
            return dateParser.parseDate( date, cal, clearTime );
         }
      } );
   }
   
   
   
   private ParseDateWithinReturn parseDateWithinImpl( final String range,
                                                      final boolean past ) throws GrammarException
   {
      return detectLanguageAndParseDate( new IParserFunc< IDateParser, ParseDateWithinReturn >()
      {
         @Override
         public ParseDateWithinReturn call( IDateParser dateParser ) throws GrammarException
         {
            return dateParser.parseDateWithin( range, past );
         }
      } );
   }
   
   
   
   private < T > T detectLanguageAndParseDate( IParserFunc< IDateParser, T > parserFunc ) throws GrammarException
   {
      final Pair< IDateParser, T > detectResult = ParserLanguageDetector.detectLanguageAndParse( dateParser,
                                                                                                 parserRepository.getDateParsers(),
                                                                                                 parserFunc );
      
      if ( detectResult.first == null )
      {
         throw new GrammarException();
      }
      
      dateParser = detectResult.first;
      
      return detectResult.second;
   }
   
   
   
   private < T > T detectLanguageAndParseTime( IParserFunc< ITimeParser, T > parserFunc ) throws GrammarException
   {
      final Pair< ITimeParser, T > detectResult = ParserLanguageDetector.detectLanguageAndParse( timeParser,
                                                                                                 parserRepository.getTimeParsers(),
                                                                                                 parserFunc );
      
      if ( detectResult.first == null )
      {
         throw new GrammarException();
      }
      
      timeParser = detectResult.first;
      
      return detectResult.second;
   }
}
