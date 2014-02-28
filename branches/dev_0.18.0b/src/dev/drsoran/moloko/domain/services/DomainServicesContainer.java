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

package dev.drsoran.moloko.domain.services;

import java.util.Locale;

import android.content.ContentResolver;
import android.content.Context;
import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.content.db.DbRtmSmartFilterEvaluator;
import dev.drsoran.moloko.domain.content.IContentValuesFactory;
import dev.drsoran.moloko.domain.content.IModelElementFactory;
import dev.drsoran.moloko.domain.content.IModificationsApplier;
import dev.drsoran.moloko.domain.content.MolokoContentValuesFactory;
import dev.drsoran.moloko.domain.content.MolokoModelElementFactory;
import dev.drsoran.moloko.domain.content.MolokoModificationsApplier;
import dev.drsoran.moloko.domain.content.SyncTimesContentEditHandler;
import dev.drsoran.moloko.domain.content.TaskContentEditHandler;
import dev.drsoran.moloko.domain.content.TaskNoteContentEditHandler;
import dev.drsoran.moloko.domain.content.TaskParticipantContentEditHandler;
import dev.drsoran.moloko.domain.content.TasksListContentEditHandler;
import dev.drsoran.moloko.domain.parsing.IRecurrenceParsing;
import dev.drsoran.moloko.domain.parsing.RecurrenceParsing;
import dev.drsoran.rtm.parsing.DefaultRtmCalenderProvider;
import dev.drsoran.rtm.parsing.IDateFormatter;
import dev.drsoran.rtm.parsing.IRtmDateTimeParsing;
import dev.drsoran.rtm.parsing.IRtmSmartFilterParsing;
import dev.drsoran.rtm.parsing.RtmDateTimeParsing;
import dev.drsoran.rtm.parsing.RtmRecurrenceParsing;
import dev.drsoran.rtm.parsing.RtmSmartFilterParsing;
import dev.drsoran.rtm.parsing.datetime.AntlrDateTimeParserFactory;
import dev.drsoran.rtm.parsing.lang.DateLanguageRepository;
import dev.drsoran.rtm.parsing.lang.IDateLanguageRepository;
import dev.drsoran.rtm.parsing.lang.IRecurrenceSentenceLanguage;
import dev.drsoran.rtm.parsing.recurrence.AntlrRecurrenceParserFactory;
import dev.drsoran.rtm.parsing.rtmsmart.IRtmSmartFilterEvaluator;


public class DomainServicesContainer implements IDomainServices
{
   private final ParsingService parsingService;
   
   private final ContentRepository contentRepository;
   
   private final IContentEditService contentEditService;
   
   private final IDateLanguageRepository dateLanguageRepository;
   
   private final IDateFormatter dateFormatter;
   
   private IRecurrenceSentenceLanguage currentRecurrenceSentenceLanguage;
   
   
   
   public DomainServicesContainer( Context context, ILog log,
      IDateFormatter dateFormatter,
      IRecurrenceSentenceLanguage recurrenceSentenceLanguage )
   {
      this.dateFormatter = dateFormatter;
      this.dateLanguageRepository = new DateLanguageRepository();
      this.currentRecurrenceSentenceLanguage = recurrenceSentenceLanguage;
      
      final IRtmDateTimeParsing dateTimeParsing = new RtmDateTimeParsing( new AntlrDateTimeParserFactory(),
                                                                          dateFormatter,
                                                                          dateLanguageRepository,
                                                                          new DefaultRtmCalenderProvider() );
      
      final IRecurrenceParsing recurrenceParsing = createRecurrenceParsing( recurrenceSentenceLanguage,
                                                                            dateTimeParsing );
      
      final IRtmSmartFilterParsing rtmSmartFilterParsing = new RtmSmartFilterParsing( log );
      
      parsingService = new ParsingService( dateTimeParsing,
                                           recurrenceParsing,
                                           rtmSmartFilterParsing );
      
      final IRtmSmartFilterEvaluator dbSmartFilterEvaluator = new DbRtmSmartFilterEvaluator( dateTimeParsing );
      final ContentResolver contentResolver = context.getContentResolver();
      final IModelElementFactory modelElementFactory = new MolokoModelElementFactory();
      
      contentRepository = new ContentRepository( contentResolver,
                                                 modelElementFactory,
                                                 dateTimeParsing,
                                                 rtmSmartFilterParsing,
                                                 dbSmartFilterEvaluator );
      
      final IContentValuesFactory contentValuesFactory = new MolokoContentValuesFactory();
      final IModificationsApplier modificationsApplier = new MolokoModificationsApplier( contentResolver,
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
                                                   new SyncTimesContentEditHandler( contentResolver,
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
      final IRecurrenceParsing recurrenceParsing = createRecurrenceParsing( recurrenceSentenceLanguage,
                                                                            parsingService.getDateTimeParsing() );
      
      parsingService.setRecurrenceParsing( recurrenceParsing );
      currentRecurrenceSentenceLanguage = recurrenceSentenceLanguage;
   }
   
   
   
   public boolean needsParserLanguageUpdate( Locale currentLocale )
   {
      return !currentLocale.equals( currentRecurrenceSentenceLanguage.getLocale() );
   }
   
   
   
   private IRecurrenceParsing createRecurrenceParsing( IRecurrenceSentenceLanguage recurrenceSentenceLanguage,
                                                       IRtmDateTimeParsing dateTimeParsing )
   {
      final IRecurrenceParsing recurrenceParsing = new RecurrenceParsing( new RtmRecurrenceParsing( new AntlrRecurrenceParserFactory(),
                                                                                                    recurrenceSentenceLanguage,
                                                                                                    dateFormatter,
                                                                                                    dateTimeParsing,
                                                                                                    dateLanguageRepository ) );
      return recurrenceParsing;
   }
}
