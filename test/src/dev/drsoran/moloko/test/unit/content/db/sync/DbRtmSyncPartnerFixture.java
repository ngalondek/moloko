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

package dev.drsoran.moloko.test.unit.content.db.sync;

import org.easymock.EasyMock;
import org.junit.Test;

import dev.drsoran.moloko.content.db.RtmDatabase;
import dev.drsoran.moloko.content.db.sync.DbRtmSyncPartner;
import dev.drsoran.moloko.content.db.sync.IDbElementSyncHandler;
import dev.drsoran.moloko.content.db.sync.IModificationsProvider;
import dev.drsoran.moloko.content.db.sync.ITaskSeriesIdProvider;
import dev.drsoran.moloko.domain.content.IModelElementFactory;


public class DbRtmSyncPartnerFixture
{
   @SuppressWarnings( "unchecked" )
   @Test
   public void testDbRtmSyncPartner()
   {
      new DbRtmSyncPartner( EasyMock.createNiceMock( RtmDatabase.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IModificationsProvider.class ),
                            EasyMock.createNiceMock( IModelElementFactory.class ),
                            EasyMock.createNiceMock( ITaskSeriesIdProvider.class ) );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test( expected = IllegalArgumentException.class )
   public void testDbRtmSyncPartner_NullDb()
   {
      new DbRtmSyncPartner( null,
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IModificationsProvider.class ),
                            EasyMock.createNiceMock( IModelElementFactory.class ),
                            EasyMock.createNiceMock( ITaskSeriesIdProvider.class ) );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test( expected = IllegalArgumentException.class )
   public void testDbRtmSyncPartner_NullTasksListSyncHandler()
   {
      new DbRtmSyncPartner( EasyMock.createNiceMock( RtmDatabase.class ),
                            null,
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IModificationsProvider.class ),
                            EasyMock.createNiceMock( IModelElementFactory.class ),
                            EasyMock.createNiceMock( ITaskSeriesIdProvider.class ) );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test( expected = IllegalArgumentException.class )
   public void testDbRtmSyncPartner_NullTaskSyncHandler()
   {
      new DbRtmSyncPartner( EasyMock.createNiceMock( RtmDatabase.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            null,
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IModificationsProvider.class ),
                            EasyMock.createNiceMock( IModelElementFactory.class ),
                            EasyMock.createNiceMock( ITaskSeriesIdProvider.class ) );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test( expected = IllegalArgumentException.class )
   public void testDbRtmSyncPartner_NullLocationsSyncHandler()
   {
      new DbRtmSyncPartner( EasyMock.createNiceMock( RtmDatabase.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            null,
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IModificationsProvider.class ),
                            EasyMock.createNiceMock( IModelElementFactory.class ),
                            EasyMock.createNiceMock( ITaskSeriesIdProvider.class ) );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test( expected = IllegalArgumentException.class )
   public void testDbRtmSyncPartner_NullContactsSyncHandler()
   {
      new DbRtmSyncPartner( EasyMock.createNiceMock( RtmDatabase.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            null,
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IModificationsProvider.class ),
                            EasyMock.createNiceMock( IModelElementFactory.class ),
                            EasyMock.createNiceMock( ITaskSeriesIdProvider.class ) );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test( expected = IllegalArgumentException.class )
   public void testDbRtmSyncPartner_NullSettingsSyncHandler()
   {
      new DbRtmSyncPartner( EasyMock.createNiceMock( RtmDatabase.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            null,
                            EasyMock.createNiceMock( IModificationsProvider.class ),
                            EasyMock.createNiceMock( IModelElementFactory.class ),
                            EasyMock.createNiceMock( ITaskSeriesIdProvider.class ) );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test( expected = IllegalArgumentException.class )
   public void testDbRtmSyncPartner_NullModificationsProvider()
   {
      new DbRtmSyncPartner( EasyMock.createNiceMock( RtmDatabase.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            null,
                            EasyMock.createNiceMock( IModelElementFactory.class ),
                            EasyMock.createNiceMock( ITaskSeriesIdProvider.class ) );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test( expected = IllegalArgumentException.class )
   public void testDbRtmSyncPartner_NullModelElementFatcory()
   {
      new DbRtmSyncPartner( EasyMock.createNiceMock( RtmDatabase.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IModificationsProvider.class ),
                            null,
                            EasyMock.createNiceMock( ITaskSeriesIdProvider.class ) );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test( expected = IllegalArgumentException.class )
   public void testDbRtmSyncPartner_NullTaskSeriesIdProvider()
   {
      new DbRtmSyncPartner( EasyMock.createNiceMock( RtmDatabase.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                            EasyMock.createNiceMock( IModificationsProvider.class ),
                            EasyMock.createNiceMock( IModelElementFactory.class ),
                            null );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testOnSyncStarted()
   {
      final IModificationsProvider modificationsProvider = EasyMock.createMock( IModificationsProvider.class );
      EasyMock.replay( modificationsProvider );
      
      final RtmDatabase rtmDatabase = EasyMock.createStrictMock( RtmDatabase.class );
      rtmDatabase.beginTransaction();
      EasyMock.replay( rtmDatabase );
      
      final DbRtmSyncPartner dbRtmSyncPartner = new DbRtmSyncPartner( rtmDatabase,
                                                                      EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                                                                      EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                                                                      EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                                                                      EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                                                                      EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                                                                      modificationsProvider,
                                                                      EasyMock.createNiceMock( IModelElementFactory.class ),
                                                                      EasyMock.createNiceMock( ITaskSeriesIdProvider.class ) );
      
      dbRtmSyncPartner.onSyncStarted();
      
      EasyMock.verify( modificationsProvider );
      EasyMock.verify( rtmDatabase );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testOnSyncSuccessful()
   {
      final IModificationsProvider modificationsProvider = EasyMock.createMock( IModificationsProvider.class );
      modificationsProvider.clearModifications();
      EasyMock.replay( modificationsProvider );
      
      final RtmDatabase rtmDatabase = EasyMock.createStrictMock( RtmDatabase.class );
      rtmDatabase.setTransactionSuccessful();
      rtmDatabase.endTransaction();
      EasyMock.replay( rtmDatabase );
      
      final DbRtmSyncPartner dbRtmSyncPartner = new DbRtmSyncPartner( rtmDatabase,
                                                                      EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                                                                      EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                                                                      EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                                                                      EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                                                                      EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                                                                      modificationsProvider,
                                                                      EasyMock.createNiceMock( IModelElementFactory.class ),
                                                                      EasyMock.createNiceMock( ITaskSeriesIdProvider.class ) );
      
      dbRtmSyncPartner.onSyncSuccessful();
      
      EasyMock.verify( rtmDatabase );
      EasyMock.verify( modificationsProvider );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testOnSyncFailed()
   {
      final IModificationsProvider modificationsProvider = EasyMock.createMock( IModificationsProvider.class );
      EasyMock.replay( modificationsProvider );
      
      final RtmDatabase rtmDatabase = EasyMock.createStrictMock( RtmDatabase.class );
      rtmDatabase.endTransaction();
      EasyMock.replay( rtmDatabase );
      
      final DbRtmSyncPartner dbRtmSyncPartner = new DbRtmSyncPartner( rtmDatabase,
                                                                      EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                                                                      EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                                                                      EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                                                                      EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                                                                      EasyMock.createNiceMock( IDbElementSyncHandler.class ),
                                                                      modificationsProvider,
                                                                      EasyMock.createNiceMock( IModelElementFactory.class ),
                                                                      EasyMock.createNiceMock( ITaskSeriesIdProvider.class ) );
      
      dbRtmSyncPartner.onSyncFailed();
      
      EasyMock.verify( rtmDatabase );
      EasyMock.verify( modificationsProvider );
   }
   
}
