/* 
 *	Copyright (c) 2014 Ronny Röhricht
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

package dev.drsoran.moloko.content.db.sync;

import dev.drsoran.moloko.app.services.IRtmSyncPartnerFactory;
import dev.drsoran.moloko.content.db.RtmDatabase;
import dev.drsoran.moloko.domain.content.IContentValuesFactory;
import dev.drsoran.moloko.domain.content.IModelElementFactory;
import dev.drsoran.rtm.ILog;
import dev.drsoran.rtm.parsing.IRtmCalendarProvider;
import dev.drsoran.rtm.parsing.IRtmDateTimeParsing;
import dev.drsoran.rtm.sync.IRtmSyncPartner;


public class DbRtmSyncPartnerFactory implements IRtmSyncPartnerFactory
{
   private final RtmDatabase database;
   
   private final IModelElementFactory rtmModelElementFactory;
   
   private final IContentValuesFactory rtmContentValuesFactory;
   
   private final IContentValuesFactory molokoContentValuesFactory;
   
   private final IRtmDateTimeParsing dateTimeParsing;
   
   private final IRtmCalendarProvider calendarProvider;
   
   private final ILog log;
   
   
   
   public DbRtmSyncPartnerFactory( RtmDatabase database,
      IModelElementFactory rtmModelElementFactory,
      IContentValuesFactory rtmContentValuesFactory,
      IContentValuesFactory molokoContentValuesFactory,
      IRtmDateTimeParsing dateTimeParsing,
      IRtmCalendarProvider calendarProvider, ILog log )
   {
      this.database = database;
      this.rtmModelElementFactory = rtmModelElementFactory;
      this.rtmContentValuesFactory = rtmContentValuesFactory;
      this.molokoContentValuesFactory = molokoContentValuesFactory;
      this.dateTimeParsing = dateTimeParsing;
      this.calendarProvider = calendarProvider;
      this.log = log;
   }
   
   
   
   @Override
   public IRtmSyncPartner createRtmSyncPartner()
   {
      final ITaskSeriesIdProvider taskSeriesIdProvider = new CachingTaskSeriesIdProvider( database );
      final long timeOfSync = calendarProvider.getNowMillisUtc();
      
      return new DbRtmSyncPartner( database,
                                   new RtmTasksListElementSyncHandler( database,
                                                                       rtmModelElementFactory,
                                                                       rtmContentValuesFactory,
                                                                       timeOfSync ),
                                   new RtmTaskElementSyncHandler( database,
                                                                  rtmModelElementFactory,
                                                                  rtmContentValuesFactory,
                                                                  molokoContentValuesFactory,
                                                                  taskSeriesIdProvider,
                                                                  dateTimeParsing,
                                                                  log ),
                                   new RtmLocationElementSyncHandler( database,
                                                                      rtmModelElementFactory,
                                                                      rtmContentValuesFactory ),
                                   new RtmContactElementSyncHandler( database,
                                                                     rtmModelElementFactory,
                                                                     rtmContentValuesFactory ),
                                   new RtmSettingsElementSyncHandler( database,
                                                                      rtmModelElementFactory,
                                                                      rtmContentValuesFactory,
                                                                      timeOfSync ),
                                   new DbModificationsProvider( database ),
                                   rtmModelElementFactory,
                                   taskSeriesIdProvider );
   }
}
