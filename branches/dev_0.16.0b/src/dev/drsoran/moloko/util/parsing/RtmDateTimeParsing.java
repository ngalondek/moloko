/* 
 *	Copyright (c) 2010 Ronny R�hricht
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
 * Ronny R�hricht - implementation
 */

package dev.drsoran.moloko.util.parsing;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

import org.antlr.runtime.RecognitionException;

import dev.drsoran.moloko.grammar.datetime.DateParserFactory;
import dev.drsoran.moloko.grammar.datetime.IDateParser;
import dev.drsoran.moloko.grammar.datetime.ITimeParser;
import dev.drsoran.moloko.grammar.datetime.TimeParserFactory;
import dev.drsoran.moloko.grammar.datetime.IDateParser.ParseDateReturn;
import dev.drsoran.moloko.grammar.datetime.IDateParser.ParseDateWithinReturn;
import dev.drsoran.moloko.grammar.datetime.ITimeParser.ParseTimeReturn;
import dev.drsoran.moloko.util.MolokoCalendar;


public final class RtmDateTimeParsing
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + RtmDateTimeParsing.class.getSimpleName();
   
   private static IDateParser dateParser;
   
   private static ITimeParser timeParser;
   
   

   public synchronized final static MolokoCalendar parseTimeOrTimeSpec( String spec )
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
   


   public synchronized final static MolokoCalendar parseDateTimeSpec( String spec )
   {
      final MolokoCalendar cal = MolokoCalendar.getDatelessAndTimelessInstance();
      
      boolean eof = false;
      boolean hasTime = false;
      boolean hasDate = false;
      boolean error = false;
      
      int startOfDatePos = 0;
      
      // first try to parse time spec
      try
      {
         // The parser can adjust the day of week
         // for times in the past.
         final ParseTimeReturn ret = parseTimeSpec( spec, cal, !hasDate );
         eof = ret.isEof;
         startOfDatePos = ret.lastParsedCharPos;
         hasTime = cal.hasTime();
      }
      catch ( RecognitionException e )
      {
      }
      
      if ( !eof )
      {
         int endOfDatePos = spec.length();
         
         try
         {
            final String datePart = spec.substring( startOfDatePos,
                                                    spec.length() );
            
            final ParseDateReturn ret = parseDate( datePart, cal, !hasTime );
            eof = ret.isEof;
            endOfDatePos = ret.lastParsedCharPos;
            hasDate = cal.hasDate();
         }
         catch ( RecognitionException e )
         {
            error = true;
         }
         
         // Check if there is a time trailing.
         // The parser can NOT adjust the day of week
         // for times in the past.
         if ( !error && !eof && hasDate && !hasTime )
         {
            final String potentialTimePart = spec.substring( endOfDatePos,
                                                             spec.length() );
            try
            {
               eof = parseTime( potentialTimePart, cal, !hasDate ).isEof;
               hasTime = cal.hasTime();
            }
            catch ( RecognitionException re2 )
            {
            }
            
            if ( !eof && !hasTime )
            {
               try
               {
                  eof = parseTimeSpec( potentialTimePart, cal, !hasDate ).isEof;
                  hasTime = cal.hasTime();
               }
               catch ( RecognitionException re3 )
               {
                  error = true;
               }
            }
         }
      }
      
      return ( !error ) ? cal : null;
   }
   


   public synchronized final static long parseEstimated( String estimated )
   {
      try
      {
         return parseTimeEstimate( estimated );
      }
      catch ( RecognitionException e )
      {
         return -1;
      }
   }
   


   public synchronized final static ParseDateWithinReturn parseDateWithin( String range,
                                                                           boolean past )
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
   


   private synchronized final static ParseTimeReturn parseTime( final String time,
                                                                final MolokoCalendar cal,
                                                                final boolean adjustDay ) throws RecognitionException
   {
      return detectLanguageAndParseTime( time,
                                         new Callable< ParseTimeReturn >()
                                         {
                                            public ParseTimeReturn call() throws RecognitionException
                                            {
                                               return timeParser.parseTime( time,
                                                                            cal,
                                                                            adjustDay );
                                            }
                                         } );
   }
   


   private synchronized final static ParseTimeReturn parseTimeSpec( final String timeSpec,
                                                                    final MolokoCalendar cal,
                                                                    final boolean adjustDay ) throws RecognitionException
   {
      return detectLanguageAndParseTime( timeSpec,
                                         new Callable< ParseTimeReturn >()
                                         {
                                            public ParseTimeReturn call() throws RecognitionException
                                            {
                                               return timeParser.parseTimeSpec( timeSpec,
                                                                                cal,
                                                                                adjustDay );
                                            }
                                         } );
   }
   


   private synchronized final static long parseTimeEstimate( final String timeEstimate ) throws RecognitionException
   {
      return detectLanguageAndParseTime( timeEstimate, new Callable< Long >()
      {
         public Long call() throws RecognitionException
         {
            final Long res = timeParser.parseTimeEstimate( timeEstimate );
            return res != null ? res.longValue() : -1;
         }
      } );
   }
   


   private synchronized final static ParseDateReturn parseDate( final String date,
                                                                final MolokoCalendar cal,
                                                                final boolean clearTime ) throws RecognitionException
   {
      return detectLanguageAndParseDate( date,
                                         new Callable< ParseDateReturn >()
                                         {
                                            public ParseDateReturn call() throws RecognitionException
                                            {
                                               return dateParser.parseDate( date,
                                                                            cal,
                                                                            clearTime );
                                            }
                                         } );
   }
   


   private synchronized final static ParseDateWithinReturn parseDateWithinImpl( final String range,
                                                                                final boolean past ) throws RecognitionException
   {
      return detectLanguageAndParseDate( range,
                                         new Callable< ParseDateWithinReturn >()
                                         {
                                            public ParseDateWithinReturn call() throws RecognitionException
                                            {
                                               return dateParser.parseDateWithin( range,
                                                                                  past );
                                            }
                                         } );
   }
   


   private synchronized final static < T > T detectLanguageAndParseTime( String time,
                                                                         Callable< T > parserFunc ) throws RecognitionException
   {
      T result = null;
      
      try
      {
         // If we have a parser, try the current parser first
         if ( timeParser != null )
            result = parserFunc.call();
         
         if ( result == null )
         {
            Locale currentParserLocale = null;
            if ( timeParser != null )
               currentParserLocale = timeParser.getLocale();
            
            final List< ITimeParser > parsers = TimeParserFactory.getAvailableTimeParsers();
            
            for ( ITimeParser parser : parsers )
            {
               // Parsing with the current parser failed, so we can spare trying the parser again.
               if ( currentParserLocale == null
                  || parser.getLocale().hashCode() != currentParserLocale.hashCode() )
               {
                  timeParser = parser;
                  result = parserFunc.call();
               }
               
               if ( result != null )
                  break;
               else
                  timeParser = null;
            }
         }
      }
      catch ( Exception e )
      {
         throw new RecognitionException();
      }
      
      return result;
   }
   


   private synchronized final static < T > T detectLanguageAndParseDate( String date,
                                                                         Callable< T > parserFunc ) throws RecognitionException
   {
      T result = null;
      
      try
      {
         // If we have a parser, try the current parser first
         if ( dateParser != null )
            result = parserFunc.call();
         
         if ( result == null )
         {
            Locale currentParserLocale = null;
            if ( dateParser != null )
               currentParserLocale = dateParser.getLocale();
            
            final List< IDateParser > parsers = DateParserFactory.getAvailableDateParsers();
            
            for ( IDateParser parser : parsers )
            {
               // Parsing with the current parser failed, so we can spare trying the parser again.
               if ( currentParserLocale == null
                  || parser.getLocale().hashCode() != currentParserLocale.hashCode() )
               {
                  dateParser = parser;
                  result = parserFunc.call();
               }
               
               if ( result != null )
                  break;
               else
                  dateParser = null;
            }
         }
      }
      catch ( Exception e )
      {
         throw new RecognitionException();
      }
      
      return result;
   }
}
