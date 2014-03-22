/* 
 *	Copyright (c) 2014 Ronny Röhricht
 *
 *	This file is part of MolokoTest.
 *
 *	MolokoTest is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	MolokoTest is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with MolokoTest.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.test;

import org.easymock.EasyMock;
import org.junit.Before;

import dev.drsoran.moloko.content.db.sync.DbRtmSyncPartnerFactory;
import dev.drsoran.moloko.content.db.sync.RtmContentValuesFactory;
import dev.drsoran.moloko.content.db.sync.RtmModelElementFactory;
import dev.drsoran.moloko.domain.content.MolokoContentValuesFactory;
import dev.drsoran.rtm.parsing.IRtmDateTimeParsing;
import dev.drsoran.rtm.sync.IRtmSyncPartner;
import dev.drsoran.rtm.test.TestCalendarProvider;
import dev.drsoran.rtm.test.TestConstants;


public abstract class MolokoSyncTestCase extends MolokoDbTestCase
{
   private IRtmSyncPartner syncPartner;
   
   private final long syncTime = TestConstants.NOW;
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      
      openDb();
      
      syncPartner = new DbRtmSyncPartnerFactory( getDb(),
                                                 new RtmModelElementFactory(),
                                                 new RtmContentValuesFactory(),
                                                 new MolokoContentValuesFactory(),
                                                 EasyMock.createNiceMock( IRtmDateTimeParsing.class ),
                                                 TestCalendarProvider.get(),
                                                 createLog() ).createRtmSyncPartner();
   }
   
   
   
   public IRtmSyncPartner getSyncPartner()
   {
      return syncPartner;
   }
   
   
   
   public long getSyncTime()
   {
      return syncTime;
   }
}
