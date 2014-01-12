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

import java.util.List;
import java.util.Locale;
import java.util.Map;

import dev.drsoran.rtm.parsing.recurrence.RtmRecurrence;


public interface IRtmRecurrenceParsing
{
   /**
    * @return The human readable pattern sentence, ready to be displayed in the system language.
    */
   String parseRecurrencePatternToSentence( String pattern, boolean isEvery ) throws GrammarException;
   
   
   
   /**
    * @return Map< Token type, values >
    */
   Map< Integer, List< Object >> tokenizeRecurrencePattern( String pattern ) throws GrammarException;
   
   
   
   RtmRecurrence parseRecurrence( String sentence ) throws GrammarException;
   
   
   
   /**
    * @return The given pattern with the correct operator order.
    */
   String ensureRecurrencePatternOrder( String pattern );
   
   
   
   boolean existsParserWithMatchingLocale( Locale locale );
}
