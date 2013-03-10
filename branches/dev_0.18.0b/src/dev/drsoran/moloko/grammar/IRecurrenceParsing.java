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

import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.util.Pair;
import dev.drsoran.moloko.grammar.recurrence.IRecurrenceSentenceLanguage;


public interface IRecurrenceParsing
{
   void setRecurrenceSentenceLanguage( IRecurrenceSentenceLanguage recurrenceSentenceLanguage );
   
   
   
   IRecurrenceSentenceLanguage getRecurrenceSentenceLanguage();
   
   
   
   /**
    * @return The human readable pattern sentence, ready to be displayed in the system language.
    */
   String parseRecurrencePatternToSentence( String pattern, boolean isEvery );
   
   
   
   /**
    * @return Map< Token type, values >
    */
   Map< Integer, List< Object >> parseRecurrencePattern( String pattern );
   
   
   
   /**
    * @return The given pattern with the correct operator order.
    */
   String ensureRecurrencePatternOrder( String pattern );
   
   
   
   /**
    * @return Pair, where the first is the RTM style recurrence pattern and the second indicates 'every' recurrence.
    */
   Pair< String, Boolean > parseRecurrence( String sentence );
   
   
   
   boolean existsParserWithMatchingLocale( Locale locale );
}
