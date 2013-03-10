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

package dev.drsoran.moloko.domain;

import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.domain.services.IDomainServices;
import dev.drsoran.moloko.domain.services.IParsingService;
import dev.drsoran.moloko.grammar.DateTimeParsing;
import dev.drsoran.moloko.grammar.IDateFormatter;
import dev.drsoran.moloko.grammar.IDateTimeParsing;
import dev.drsoran.moloko.grammar.IRecurrenceParsing;
import dev.drsoran.moloko.grammar.IRtmSmartFilterParsing;
import dev.drsoran.moloko.grammar.RecurrenceParsing;
import dev.drsoran.moloko.grammar.RtmSmartFilterParsing;
import dev.drsoran.moloko.grammar.datetime.DateTimeParserRepository;
import dev.drsoran.moloko.grammar.datetime.IDateTimeParserRepository;
import dev.drsoran.moloko.grammar.recurrence.IRecurrenceParserRepository;
import dev.drsoran.moloko.grammar.recurrence.IRecurrenceSentenceLanguage;
import dev.drsoran.moloko.grammar.recurrence.RecurrenceParserRepository;


public class DomainServicesContainer implements IDomainServices
{
   private final IParsingService parsingService;
   
   
   
   public DomainServicesContainer( ILog log, IDateFormatter dateFormatContext,
      IRecurrenceSentenceLanguage recurrenceSentenceLanguage )
   {
      final IDateTimeParserRepository dateTimeParserRepository = new DateTimeParserRepository();
      final IDateTimeParsing dateTimeParsing = new DateTimeParsing( dateTimeParserRepository );
      
      final IRecurrenceParserRepository recurrenceParserRepository = new RecurrenceParserRepository( log,
                                                                                                     dateFormatContext,
                                                                                                     recurrenceSentenceLanguage,
                                                                                                     dateTimeParsing );
      final IRecurrenceParsing recurrenceParsing = new RecurrenceParsing( log,
                                                                          recurrenceParserRepository );
      
      final IRtmSmartFilterParsing rtmSmartFilterParsing = new RtmSmartFilterParsing( log,
                                                                                      dateTimeParsing );
      
      this.parsingService = new ParsingService( dateTimeParsing,
                                                recurrenceParsing,
                                                rtmSmartFilterParsing );
   }
   
   
   
   @Override
   public IParsingService getParsingService()
   {
      return parsingService;
   }
   
   
   
   public void updateParserLanguages( IRecurrenceSentenceLanguage recurrenceSentenceLanguage )
   {
      parsingService.getRecurrenceParsing()
                    .setRecurrenceSentenceLanguage( recurrenceSentenceLanguage );
   }
}
