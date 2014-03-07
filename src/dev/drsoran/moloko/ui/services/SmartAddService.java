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

package dev.drsoran.moloko.ui.services;

import java.util.Collection;

import android.content.Context;
import dev.drsoran.moloko.domain.parsing.IRecurrenceParsing;
import dev.drsoran.moloko.domain.services.IContentRepository;
import dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddSuggestion;
import dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddSuggestor;
import dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken;
import dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddTokenizer;
import dev.drsoran.rtm.parsing.IRtmCalendarProvider;
import dev.drsoran.rtm.parsing.IRtmDateTimeParsing;


class SmartAddService implements ISmartAddService
{
   private final RtmSmartAddTokenizer rtmSmartAddTokenizer;
   
   private final RtmSmartAddSuggestor rtmSmartAddSuggestor;
   
   
   
   public SmartAddService( Context context,
      IContentRepository contentRepository,
      IRtmDateTimeParsing dateTimeParsing,
      IRecurrenceParsing recurrenceParsing,
      IDateFormatterService dateFormatter, IRtmCalendarProvider calendarProvider )
   {
      this.rtmSmartAddTokenizer = new RtmSmartAddTokenizer( dateTimeParsing );
      this.rtmSmartAddSuggestor = new RtmSmartAddSuggestor( context,
                                                            contentRepository,
                                                            recurrenceParsing,
                                                            dateFormatter,
                                                            calendarProvider );
   }
   
   
   
   @Override
   public Collection< RtmSmartAddToken > tokenize( CharSequence input )
   {
      return rtmSmartAddTokenizer.getTokens( input );
   }
   
   
   
   @Override
   public Collection< RtmSmartAddSuggestion > getSuggestions( int rtmSmartAddTokenType ) throws IllegalArgumentException
   {
      return rtmSmartAddSuggestor.getSuggestions( rtmSmartAddTokenType );
   }
}
