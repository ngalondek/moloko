/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.moloko.test.sources;

import static dev.drsoran.moloko.content.Columns.ParticipantColumns.CONTACT_ID;
import static dev.drsoran.moloko.content.Columns.ParticipantColumns.FULLNAME;
import static dev.drsoran.moloko.content.Columns.ParticipantColumns.USERNAME;
import static dev.drsoran.moloko.content.ContentUris.TASK_PARTICIPANTS_CONTENT_URI_ID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.model.Participant;
import dev.drsoran.moloko.sync.model.Modification;


public class TaskParticipantEditHandlerTestDataSource extends
         ContentEditHandlerTestDataSource< Participant >
{
   private final long rootId;
   
   private final long elementId;
   
   
   
   public TaskParticipantEditHandlerTestDataSource( long rootId, long elementId )
   {
      this.rootId = rootId;
      this.elementId = elementId;
   }
   
   
   
   @Override
   public Collection< TestData< Participant >> getUpdateTestData()
   {
      final Collection< TestData< Participant >> testData = new ArrayList< TestData< Participant > >();
      
      addUpdateContactId( testData );
      addUpdateFullname( testData );
      addUpdateUsername( testData );
      addUpdateMultiple( testData );
      
      return testData;
   }
   
   
   
   private void addUpdateContactId( Collection< TestData< Participant >> testData )
   {
      final Participant existing = new Participant( elementId,
                                                    100L,
                                                    "fullname",
                                                    "username" );
      final Participant update = new Participant( elementId,
                                                  101L,
                                                  "fullname",
                                                  "username" );
      
      final Modification mod = Modification.newNonPersistentModification( getEntityUri(),
                                                                          CONTACT_ID,
                                                                          101L );
      
      testData.add( new TestData< Participant >( existing, update, mod ) );
   }
   
   
   
   private void addUpdateFullname( Collection< TestData< Participant >> testData )
   {
      final Participant existing = new Participant( elementId,
                                                    100L,
                                                    "fullname",
                                                    "username" );
      final Participant update = new Participant( elementId,
                                                  100L,
                                                  "full",
                                                  "username" );
      
      final Modification mod = Modification.newNonPersistentModification( getEntityUri(),
                                                                          FULLNAME,
                                                                          "full" );
      
      testData.add( new TestData< Participant >( existing, update, mod ) );
   }
   
   
   
   private void addUpdateUsername( Collection< TestData< Participant >> testData )
   {
      final Participant existing = new Participant( elementId,
                                                    100L,
                                                    "fullname",
                                                    "username" );
      final Participant update = new Participant( elementId,
                                                  100L,
                                                  "fullname",
                                                  "user" );
      
      final Modification mod = Modification.newNonPersistentModification( getEntityUri(),
                                                                          USERNAME,
                                                                          "user" );
      
      testData.add( new TestData< Participant >( existing, update, mod ) );
   }
   
   
   
   private void addUpdateMultiple( Collection< TestData< Participant >> testData )
   {
      final Participant existing = new Participant( elementId,
                                                    100L,
                                                    "fullname",
                                                    "username" );
      final Participant update = new Participant( elementId,
                                                  100L,
                                                  "full",
                                                  "user" );
      
      final Modification mod1 = Modification.newNonPersistentModification( getEntityUri(),
                                                                           FULLNAME,
                                                                           "full" );
      
      final Modification mod2 = Modification.newNonPersistentModification( getEntityUri(),
                                                                           USERNAME,
                                                                           "user" );
      
      testData.add( new TestData< Participant >( existing,
                                                 update,
                                                 Arrays.asList( mod1, mod2 ) ) );
   }
   
   
   
   private String getEntityUri()
   {
      return ContentUris.bindAggregatedElementIdToUri( TASK_PARTICIPANTS_CONTENT_URI_ID,
                                                       rootId,
                                                       elementId )
                        .toString();
   }
}
