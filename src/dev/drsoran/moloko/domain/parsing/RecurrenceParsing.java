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

import java.util.List;
import java.util.Locale;
import java.util.Map;

import dev.drsoran.moloko.domain.model.Recurrence;
import dev.drsoran.rtm.parsing.GrammarException;
import dev.drsoran.rtm.parsing.IRtmRecurrenceParsing;


public class RecurrenceParsing implements IRecurrenceParsing
{
   private final IRtmRecurrenceParsing rtmRecurrenceParsing;
   
   
   
   public RecurrenceParsing( IRtmRecurrenceParsing rtmRecurrenceParsing )
   {
      this.rtmRecurrenceParsing = rtmRecurrenceParsing;
   }
   
   
   
   @Override
   public String parseRecurrencePatternToSentence( String pattern,
                                                   boolean isEvery ) throws GrammarException
   
   {
      return rtmRecurrenceParsing.parseRecurrencePatternToSentence( pattern,
                                                                    isEvery );
   }
   
   
   
   @Override
   public Map< Integer, List< Object >> tokenizeRecurrencePattern( String pattern ) throws GrammarException
   {
      return rtmRecurrenceParsing.tokenizeRecurrencePattern( pattern );
   }
   
   
   
   @Override
   public Recurrence parseRecurrence( String recurrence ) throws GrammarException
   {
      return new Recurrence( rtmRecurrenceParsing.parseRecurrence( recurrence ) );
   }
   
   
   
   @Override
   public String ensureRecurrencePatternOrder( String recurrencePattern )
   {
      return rtmRecurrenceParsing.ensureRecurrencePatternOrder( recurrencePattern );
   }
   
   
   
   @Override
   public boolean existsParserWithMatchingLocale( Locale locale )
   {
      return rtmRecurrenceParsing.existsParserWithMatchingLocale( locale );
   }
}
