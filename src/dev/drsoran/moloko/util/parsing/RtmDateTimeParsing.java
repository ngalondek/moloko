/* 
 *	Copyright (c) 2010 Ronny Röhricht
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

package dev.drsoran.moloko.util.parsing;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;

import android.content.res.Resources;
import android.util.Log;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.grammar.DateParser;
import dev.drsoran.moloko.grammar.DateTimeLexer;
import dev.drsoran.moloko.grammar.datetime.ITimeParser;
import dev.drsoran.moloko.grammar.datetime.TimeParserFactory;
import dev.drsoran.moloko.grammar.datetime.IDateParser.ParseDateWithinReturn;
import dev.drsoran.moloko.grammar.datetime.ITimeParser.ParseTimeReturn;
import dev.drsoran.moloko.grammar.lang.NumberLookupLanguage;
import dev.drsoran.moloko.util.ANTLRNoCaseStringStream;
import dev.drsoran.moloko.util.MolokoCalendar;


public final class RtmDateTimeParsing
{
   private final static String TAG = "Moloko."
      + RtmDateTimeParsing.class.getSimpleName();
   
   private final static DateTimeLexer dateTimeLexer = new DateTimeLexer();
   
   private final static DateParser dateParser = new DateParser();
   
   private static ITimeParser timeParser;
   
   private static NumberLookupLanguage numberLookUp;
   
   

   public final static void initLookupLanguage( Resources resources )
   {
      try
      {
         numberLookUp = new NumberLookupLanguage( resources,
                                                  R.xml.parser_lang_number_lookup );
      }
      catch ( ParseException e )
      {
         numberLookUp = null;
         Log.e( TAG, "Unable to initialize number lookup language.", e );
      }
   }
   


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
      final MolokoCalendar cal = MolokoCalendar.getInstance();
      
      boolean eof = false;
      boolean hasTime = false;
      boolean hasDate = false;
      boolean error = false;
      
      // first try to parse time spec
      try
      {
         // The parser can adjust the day of week
         // for times in the past.
         eof = parseTimeSpec( spec, cal, !hasDate ).isEof;
         hasTime = !eof;
      }
      catch ( RecognitionException e )
      {
      }
      
      if ( !eof )
      {
         final ANTLRNoCaseStringStream stream = new ANTLRNoCaseStringStream( spec );
         dateTimeLexer.setCharStream( stream );
         
         final CommonTokenStream antlrTokens = new CommonTokenStream( dateTimeLexer );
         
         dateParser.setNumberLookUp( numberLookUp );
         dateParser.setTokenStream( antlrTokens );
         
         try
         {
            eof = dateParser.parseDate( cal, !hasTime );
            hasDate = !eof;
            eof = antlrTokens.LA( 1 ) == Token.EOF;
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
            final int streamPos = antlrTokens.mark();
            
            final String potentialTimePart = spec.substring( streamPos,
                                                             spec.length() );
            try
            {
               eof = parseTime( potentialTimePart, cal, !hasDate ).isEof;
               hasTime = !eof;
            }
            catch ( RecognitionException re2 )
            {
            }
            
            if ( !eof && !hasTime )
            {
               try
               {
                  eof = parseTimeSpec( potentialTimePart, cal, !hasDate ).isEof;
                  hasTime = !eof;
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
      final ANTLRNoCaseStringStream stream = new ANTLRNoCaseStringStream( range );
      dateTimeLexer.setCharStream( stream );
      
      final CommonTokenStream antlrTokens = new CommonTokenStream( dateTimeLexer );
      dateParser.setTokenStream( antlrTokens );
      
      try
      {
         final DateParser.parseDateWithin_return res = dateParser.parseDateWithin( past );
         
         return new ParseDateWithinReturn( res.epochStart, res.epochEnd );
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
      return detectLanguageAndParse( time, new Callable< ParseTimeReturn >()
      {
         public ParseTimeReturn call() throws RecognitionException
         {
            return timeParser.parseTime( time, cal, adjustDay );
         }
      } );
   }
   


   private synchronized final static ParseTimeReturn parseTimeSpec( final String timeSpec,
                                                                    final MolokoCalendar cal,
                                                                    final boolean adjustDay ) throws RecognitionException
   {
      return detectLanguageAndParse( timeSpec,
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
      return detectLanguageAndParse( timeEstimate, new Callable< Long >()
      {
         public Long call() throws RecognitionException
         {
            final Long res = timeParser.parseTimeEstimate( timeEstimate );
            return res != null ? res.longValue() : -1;
         }
      } );
   }
   


   private synchronized final static < T > T detectLanguageAndParse( String time,
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
                  result = parserFunc.call();
               
               if ( result != null )
                  break;
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
