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

import android.content.Context;
import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.content.db.DbContentEditService;
import dev.drsoran.moloko.content.db.DbContentRepository;
import dev.drsoran.moloko.content.db.RtmDatabase;
import dev.drsoran.moloko.domain.services.IContentEditService;
import dev.drsoran.moloko.domain.services.IContentRepository;
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
   
   private final DbContentRepository contentRepository;
   
   private final IContentEditService contentEditService;
   
   
   
   public DomainServicesContainer( Context context, ILog log,
      IDateFormatter dateFormatContext,
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
      
      final IRtmSmartFilterParsing rtmSmartFilterParsing = new RtmSmartFilterParsing( log );
      
      this.parsingService = new ParsingService( dateTimeParsing,
                                                recurrenceParsing,
                                                rtmSmartFilterParsing );
      
      final RtmDatabase database = new RtmDatabase( context, log );
      this.contentRepository = new DbContentRepository( database,
                                                        dateTimeParsing,
                                                        rtmSmartFilterParsing );
      
      this.contentEditService = new DbContentEditService( database,
                                                          contentRepository );
   }
   
   
   
   public void shutdown()
   {
      contentRepository.shutdown();
   }
   
   
   
   @Override
   public IParsingService getParsingService()
   {
      return parsingService;
   }
   
   
   
   @Override
   public IContentRepository getContentRepository()
   {
      return contentRepository;
   }
   
   
   
   @Override
   public IContentEditService getContentEditService()
   {
      return contentEditService;
   }
   
   
   
   public void updateParserLanguages( IRecurrenceSentenceLanguage recurrenceSentenceLanguage )
   {
      parsingService.getRecurrenceParsing()
                    .setRecurrenceSentenceLanguage( recurrenceSentenceLanguage );
   }
   
}
