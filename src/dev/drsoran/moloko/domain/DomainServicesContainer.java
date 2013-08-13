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

import android.content.ContentResolver;
import android.content.Context;
import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.content.db.DbRtmSmartFilterEvaluator;
import dev.drsoran.moloko.domain.parsing.DateTimeParsing;
import dev.drsoran.moloko.domain.parsing.IDateFormatter;
import dev.drsoran.moloko.domain.parsing.IDateTimeParsing;
import dev.drsoran.moloko.domain.parsing.IRecurrenceParsing;
import dev.drsoran.moloko.domain.parsing.IRtmSmartFilterParsing;
import dev.drsoran.moloko.domain.parsing.RecurrenceParsing;
import dev.drsoran.moloko.domain.parsing.RtmSmartFilterParsing;
import dev.drsoran.moloko.domain.parsing.lang.IRecurrenceSentenceLanguage;
import dev.drsoran.moloko.domain.parsing.recurrence.IRecurrenceParserRepository;
import dev.drsoran.moloko.domain.parsing.recurrence.RecurrenceParserRepository;
import dev.drsoran.moloko.domain.parsing.rtmsmart.IRtmSmartFilterEvaluator;
import dev.drsoran.moloko.domain.services.IContentEditService;
import dev.drsoran.moloko.domain.services.IContentRepository;
import dev.drsoran.moloko.domain.services.IDomainServices;
import dev.drsoran.moloko.domain.services.IParsingService;
import dev.drsoran.moloko.grammar.datetime.DateTimeParserRepository;
import dev.drsoran.moloko.grammar.datetime.IDateTimeParserRepository;


public class DomainServicesContainer implements IDomainServices
{
   private final IParsingService parsingService;
   
   private final ContentRepository contentRepository;
   
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
      
      parsingService = new ParsingService( dateTimeParsing,
                                           recurrenceParsing,
                                           rtmSmartFilterParsing );
      
      final IRtmSmartFilterEvaluator dbSmartFilterEvaluator = new DbRtmSmartFilterEvaluator( dateTimeParsing );
      final ContentResolver contentResolver = context.getContentResolver();
      final IModelElementFactory modelElementFactory = new DefaultModelElementFactory();
      
      contentRepository = new ContentRepository( contentResolver,
                                                 modelElementFactory,
                                                 dateTimeParsing,
                                                 rtmSmartFilterParsing,
                                                 dbSmartFilterEvaluator );
      
      final IContentValuesFactory contentValuesFactory = new DefaultContentValuesFactory();
      final IModificationsApplier modificationsApplier = new DefaultModificationsApplier( contentResolver,
                                                                                          contentValuesFactory );
      
      contentEditService = new ContentEditService( new TaskContentEditHandler( contentResolver,
                                                                               contentValuesFactory,
                                                                               modificationsApplier ),
                                                   new TasksListContentEditHandler( contentResolver,
                                                                                    contentValuesFactory,
                                                                                    modificationsApplier ),
                                                   new TaskNoteContentEditHandler( contentResolver,
                                                                                   contentValuesFactory,
                                                                                   modificationsApplier ),
                                                   new TaskParticipantContentEditHandler( contentResolver,
                                                                                          contentValuesFactory,
                                                                                          modificationsApplier ),
                                                   contentRepository );
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
