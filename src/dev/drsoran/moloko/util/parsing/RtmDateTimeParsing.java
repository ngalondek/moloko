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
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.grammar.DateParser;
import dev.drsoran.moloko.grammar.DateTimeLexer;
import dev.drsoran.moloko.grammar.TimeAutoCompl;
import dev.drsoran.moloko.grammar.TimeParser;
import dev.drsoran.moloko.grammar.lang.AutoComplLanguage;
import dev.drsoran.moloko.grammar.lang.NumberLookupLanguage;
import dev.drsoran.moloko.util.ANTLRNoCaseStringStream;


public final class RtmDateTimeParsing
{
   private final static String TAG = "Moloko."
      + RtmDateTimeParsing.class.getSimpleName();
   
   private final static DateTimeLexer dateTimeLexer = new DateTimeLexer();
   
   private final static TimeParser timeParser = new TimeParser();
   
   private final static DateParser dateParser = new DateParser();
   
   private static NumberLookupLanguage numberLookUp;
   
   private static TimeAutoCompl timeAutoCompl;
   
   private static AutoComplLanguage timeAutoComplLang;
   
   

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
   
   
   public final static class DateWithinReturn
   {
      public final Calendar startEpoch;
      
      public final Calendar endEpoch;
      
      

      public DateWithinReturn( Calendar startEpoch, Calendar endEpoch )
      {
         this.startEpoch = startEpoch;
         this.endEpoch = endEpoch;
      }
   }
   
   

   public synchronized final static Calendar parseDateTimeSpec( String spec )
   {
      final ANTLRNoCaseStringStream stream = new ANTLRNoCaseStringStream( spec );
      dateTimeLexer.setCharStream( stream );
      
      final CommonTokenStream antlrTokens = new CommonTokenStream( dateTimeLexer );
      final Calendar cal = TimeParser.getCalendar();
      
      boolean eof = false;
      boolean hasTime = false;
      boolean hasDate = false;
      boolean error = false;
      
      timeParser.setTokenStream( antlrTokens );
      
      // first try to parse time spec
      try
      {
         // The parser can adjust the day of week
         // for times in the past.
         eof = timeParser.parseTimeSpec( cal, !hasDate );
         hasTime = !eof;
      }
      catch ( RecognitionException e )
      {
      }
      
      if ( !eof )
      {
         if ( !hasTime )
            antlrTokens.reset();
         
         dateParser.setNumberLookUp( numberLookUp );
         dateParser.setTokenStream( antlrTokens );
         
         try
         {
            eof = dateParser.parseDate( cal, !hasTime );
            hasDate = !eof;
         }
         catch ( RecognitionException e )
         {
            error = true;
         }
      }
      
      // Check if there is a time trailing.
      // The parser can NOT adjust the day of week
      // for times in the past.
      if ( !error && !eof && hasDate && !hasTime )
      {
         final int streamPos = antlrTokens.mark();
         
         try
         {
            eof = timeParser.parseTime( cal, !hasDate );
            hasTime = !eof;
         }
         catch ( RecognitionException re2 )
         {
         }
         
         if ( !eof && !hasTime )
         {
            antlrTokens.rewind( streamPos );
            
            try
            {
               eof = timeParser.parseTimeSpec( cal, !hasDate );
               hasTime = !eof;
            }
            catch ( RecognitionException re3 )
            {
               error = true;
            }
         }
      }
      
      return ( !error ) ? cal : null;
   }
   


   public synchronized final static long parseEstimated( String estimated )
   {
      long estimatedLong = -1;
      
      final ANTLRNoCaseStringStream stream = new ANTLRNoCaseStringStream( estimated );
      dateTimeLexer.setCharStream( stream );
      
      final CommonTokenStream antlrTokens = new CommonTokenStream( dateTimeLexer );
      boolean error = false;
      
      timeParser.setTokenStream( antlrTokens );
      
      try
      {
         estimatedLong = timeParser.parseTimeEstimate();
      }
      catch ( RecognitionException e )
      {
         error = true;
      }
      
      return ( !error ) ? estimatedLong : -1;
   }
   


   public synchronized final static DateWithinReturn parseDateWithin( String range,
                                                                      boolean past )
   {
      final ANTLRNoCaseStringStream stream = new ANTLRNoCaseStringStream( range );
      dateTimeLexer.setCharStream( stream );
      
      final CommonTokenStream antlrTokens = new CommonTokenStream( dateTimeLexer );
      dateParser.setTokenStream( antlrTokens );
      
      try
      {
         final DateParser.parseDateWithin_return res = dateParser.parseDateWithin( past );
         
         return new DateWithinReturn( res.epochStart, res.epochEnd );
      }
      catch ( RecognitionException e )
      {
         return null;
      }
   }
   


   public synchronized final static List< String > getTimeSuggestions( Context context,
                                                                       String text )
   {
      return getTimeSuggestionsImpl( context, text, false );
   }
   


   public synchronized final static List< String > getEstimatedSuggestions( Context context,
                                                                            String text )
   {
      return getTimeSuggestionsImpl( context, text, true );
   }
   


   private static final List< String > getTimeSuggestionsImpl( Context context,
                                                               String text,
                                                               boolean estimate )
   {
      if ( timeAutoComplLang == null )
         try
         {
            timeAutoComplLang = new AutoComplLanguage( context.getResources(),
                                                       R.xml.auto_compl_time );
         }
         catch ( ParseException e )
         {
            Log.e( TAG, "Error initialize AutoComplLanguage", e );
            return Collections.emptyList();
         }
      
      if ( timeAutoCompl == null )
      {
         timeAutoCompl = new TimeAutoCompl();
         timeAutoCompl.setLanguage( timeAutoComplLang );
      }
      
      final ANTLRNoCaseStringStream stream = new ANTLRNoCaseStringStream( text );
      dateTimeLexer.setCharStream( stream );
      
      final CommonTokenStream antlrTokens = new CommonTokenStream( dateTimeLexer );
      timeAutoCompl.setTokenStream( antlrTokens );
      
      try
      {
         return estimate ? timeAutoCompl.suggTimeEstimate()
                        : timeAutoCompl.suggestTime();
      }
      catch ( RecognitionException e )
      {
         return Collections.emptyList();
      }
   }
}
