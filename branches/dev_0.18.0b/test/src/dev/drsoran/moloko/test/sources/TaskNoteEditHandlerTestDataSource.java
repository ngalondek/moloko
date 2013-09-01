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

import static dev.drsoran.moloko.content.Columns.NoteColumns.NOTE_CREATED_DATE;
import static dev.drsoran.moloko.content.Columns.NoteColumns.NOTE_DELETED_DATE;
import static dev.drsoran.moloko.content.Columns.NoteColumns.NOTE_MODIFIED_DATE;
import static dev.drsoran.moloko.content.Columns.NoteColumns.NOTE_TEXT;
import static dev.drsoran.moloko.content.Columns.NoteColumns.NOTE_TITLE;
import static dev.drsoran.moloko.content.ContentUris.TASK_NOTES_CONTENT_URI_ID;
import static dev.drsoran.moloko.test.TestConstants.LATER;
import static dev.drsoran.moloko.test.TestConstants.NOW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.model.Modification;
import dev.drsoran.moloko.domain.model.Note;


public class TaskNoteEditHandlerTestDataSource extends
         ContentEditHandlerTestDataSource< Note >
{
   private final long rootId;
   
   private final long elementId;
   
   
   
   public TaskNoteEditHandlerTestDataSource( long rootId, long elementId )
   {
      this.rootId = rootId;
      this.elementId = elementId;
   }
   
   
   
   @Override
   public Collection< TestData< Note >> getUpdateTestData()
   {
      final Collection< TestData< Note >> testData = new ArrayList< TestData< Note > >();
      
      addUpdateLifeTimeManaged( testData );
      addUpdateTitle( testData );
      addUpdateText( testData );
      addUpdateMultiple( testData );
      
      return testData;
   }
   
   
   
   private void addUpdateLifeTimeManaged( Collection< TestData< Note >> testData )
   {
      Note existing = new Note( 1L, NOW );
      Note update = new Note( 1L, LATER );
      
      Modification mod = Modification.newNonPersistentModification( getEntityUri(),
                                                                    NOTE_CREATED_DATE,
                                                                    LATER );
      
      testData.add( new TestData< Note >( existing, update, mod ) );
      
      existing = new Note( 1L, NOW );
      existing.setModifiedMillisUtc( NOW );
      
      update = new Note( 1L, NOW );
      update.setModifiedMillisUtc( LATER );
      
      mod = Modification.newNonPersistentModification( getEntityUri(),
                                                       NOTE_MODIFIED_DATE,
                                                       LATER );
      
      testData.add( new TestData< Note >( existing, update, mod ) );
      
      existing = new Note( 1L, NOW );
      existing.setDeletedMillisUtc( NOW );
      
      update = new Note( 1L, NOW );
      update.setDeletedMillisUtc( LATER );
      
      mod = Modification.newNonPersistentModification( getEntityUri(),
                                                       NOTE_DELETED_DATE,
                                                       LATER );
      
      testData.add( new TestData< Note >( existing, update, mod ) );
   }
   
   
   
   private void addUpdateTitle( Collection< TestData< Note >> testData )
   {
      Note existing = new Note( 1L, NOW );
      existing.setTitle( "title" );
      
      Note update = new Note( 1L, NOW );
      update.setTitle( "t" );
      
      Modification mod = Modification.newModification( getEntityUri(),
                                                       NOTE_TITLE,
                                                       "t",
                                                       "title" );
      
      testData.add( new TestData< Note >( existing, update, mod ) );
   }
   
   
   
   private void addUpdateText( Collection< TestData< Note >> testData )
   {
      Note existing = new Note( 1L, NOW );
      existing.setText( "text" );
      
      Note update = new Note( 1L, NOW );
      update.setText( "t" );
      
      Modification mod = Modification.newModification( getEntityUri(),
                                                       NOTE_TEXT,
                                                       "t",
                                                       "text" );
      
      testData.add( new TestData< Note >( existing, update, mod ) );
   }
   
   
   
   private void addUpdateMultiple( Collection< TestData< Note >> testData )
   {
      Note existing = new Note( 1L, NOW );
      existing.setText( "text" );
      
      Note update = new Note( 1L, LATER );
      update.setText( "t" );
      
      Modification mod1 = Modification.newNonPersistentModification( getEntityUri(),
                                                                     NOTE_CREATED_DATE,
                                                                     LATER );
      
      Modification mod2 = Modification.newModification( getEntityUri(),
                                                        NOTE_TEXT,
                                                        "t",
                                                        "text" );
      
      testData.add( new TestData< Note >( existing,
                                          update,
                                          Arrays.asList( mod1, mod2 ) ) );
   }
   
   
   
   private String getEntityUri()
   {
      return ContentUris.bindAggregatedElementIdToUri( TASK_NOTES_CONTENT_URI_ID,
                                                       rootId,
                                                       elementId )
                        .toString();
   }
}
