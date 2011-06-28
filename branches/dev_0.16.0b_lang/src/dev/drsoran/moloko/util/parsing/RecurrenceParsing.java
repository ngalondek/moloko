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
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.grammar.lang.RecurrPatternLanguage;
import dev.drsoran.moloko.grammar.recurrence.IRecurrenceParser;
import dev.drsoran.moloko.grammar.recurrence.RecurrenceParserFactory;
import dev.drsoran.moloko.grammar.recurrence.RecurrencePatternLexer;
import dev.drsoran.moloko.grammar.recurrence.RecurrencePatternParser;


public final class RecurrenceParsing
{
   
   private final static String TAG = "Moloko."
      + RecurrenceParsing.class.getSimpleName();
   
   private static RecurrencePatternLexer patternLexer;
   
   private static RecurrencePatternParser patternParser;
   
   private static RecurrPatternLanguage lang;
   
   private static IRecurrenceParser recurrenceParser;
   
   

   public final static void initPatternLanguage( Resources resources )
   {
      try
      {
         lang = new RecurrPatternLanguage( resources,
                                           R.xml.parser_lang_reccur_pattern );
      }
      catch ( ParseException e )
      {
         lang = null;
         Log.e( TAG, "Unable to initialize recurrence pattern language.", e );
      }
   }
   


   public synchronized final static RecurrPatternLanguage getPatternLanguage()
   {
      return new RecurrPatternLanguage( lang );
   }
   


   public synchronized final static String parseRecurrencePattern( String pattern,
                                                                   boolean isEvery )
   {
      String result = null;
      
      if ( lang != null )
      {
         if ( patternLexer == null )
            patternLexer = new RecurrencePatternLexer();
         
         if ( patternParser == null )
            patternParser = new RecurrencePatternParser();
         
         patternLexer.setCharStream( new ANTLRStringStream( pattern ) );
         final CommonTokenStream antlrTokens = new CommonTokenStream( patternLexer );
         patternParser.setTokenStream( antlrTokens );
         
         try
         {
            result = patternParser.parseRecurrencePattern( lang, isEvery );
         }
         catch ( RecognitionException e )
         {
            Log.e( TAG, "Failed to parse recurrence pattern " + pattern, e );
            result = null;
         }
      }
      else
         Log.e( TAG,
                "Unable to parse recurrence pattern due to missing language." );
      
      return result;
   }
   


   /**
    * @return Map< Token type, values >
    */
   public synchronized final static Map< Integer, List< Object >> parseRecurrencePattern( String pattern )
   {
      if ( TextUtils.isEmpty( pattern ) )
         return Collections.emptyMap();
      
      if ( patternLexer == null )
         patternLexer = new RecurrencePatternLexer();
      
      if ( patternParser == null )
         patternParser = new RecurrencePatternParser();
      
      patternLexer.setCharStream( new ANTLRStringStream( pattern ) );
      
      final CommonTokenStream antlrTokens = new CommonTokenStream( patternLexer );
      patternParser.setTokenStream( antlrTokens );
      
      try
      {
         return patternParser.parseRecurrencePattern1();
      }
      catch ( RecognitionException e )
      {
         Log.e( TAG, "Failed to parse recurrence pattern " + pattern, e );
         return null;
      }
   }
   


   public final static String ensureRecurrencePatternOrder( String recurrencePattern )
   {
      final String[] operators = recurrencePattern.split( RecurrencePatternParser.OPERATOR_SEP );
      Arrays.sort( operators, RecurrencePatternParser.CMP_OPERATORS );
      
      return TextUtils.join( RecurrencePatternParser.OPERATOR_SEP, operators );
   }
   


   public synchronized final static Pair< String, Boolean > parseRecurrence( String recurrence )
   {
      Pair< String, Boolean > result = null;
      
      // If we have a parser, try the current parser first
      if ( recurrenceParser != null )
         result = parseRecurrence( recurrence, recurrenceParser.getLocale() );
      
      if ( result == null )
      {
         Locale currentParserLocale = null;
         if ( recurrenceParser != null )
            currentParserLocale = recurrenceParser.getLocale();
         
         final List< IRecurrenceParser > parsers = RecurrenceParserFactory.getAvailableRecurrenceParsers();
         
         for ( IRecurrenceParser iRecurrenceParser : parsers )
         {
            // Parsing with the current parser failed, so we can spare trying the parser again.
            if ( currentParserLocale == null
               || iRecurrenceParser.getLocale().hashCode() != currentParserLocale.hashCode() )
               result = parseRecurrence( recurrence,
                                         iRecurrenceParser.getLocale() );
            if ( result != null )
               break;
         }
      }
      
      if ( result == null )
      {
         Log.e( TAG, "Failed to parse recurrence " + recurrence );
      }
      
      return result;
   }
   


   public synchronized final static Pair< String, Boolean > parseRecurrence( String recurrence,
                                                                             Locale locale )
   {
      Pair< String, Boolean > result;
      
      if ( recurrenceParser == null
         || recurrenceParser.getLocale().hashCode() != locale.hashCode() )
         recurrenceParser = RecurrenceParserFactory.createRecurrenceParserForLocale( locale );
      
      try
      {
         final Map< String, Object > patternObjects = recurrenceParser.parseRecurrence( recurrence );
         final Boolean isEvery = (Boolean) patternObjects.remove( RecurrencePatternParser.IS_EVERY );
         result = Pair.create( joinRecurrencePattern( patternObjects ), isEvery );
      }
      catch ( RecognitionException e )
      {
         result = null;
      }
      
      return result;
   }
   


   public final static String joinRecurrencePattern( Map< String, Object > parts )
   {
      final StringBuilder sb = new StringBuilder();
      final Set< String > keys = parts.keySet();
      
      for ( Iterator< String > i = keys.iterator(); i.hasNext(); )
      {
         final String key = i.next();
         sb.append( key ).append( "=" ).append( parts.get( key ) );
         
         if ( i.hasNext() )
            sb.append( RecurrencePatternParser.OPERATOR_SEP );
      }
      
      return sb.toString();
   }
}
