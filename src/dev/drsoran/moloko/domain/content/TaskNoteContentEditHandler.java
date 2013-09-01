/*
 * Copyright (c) 2010 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.domain.content;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import android.content.ContentResolver;
import dev.drsoran.moloko.content.Columns.NoteColumns;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.model.Modification;
import dev.drsoran.moloko.domain.model.Note;


public class TaskNoteContentEditHandler extends
         AbstractContentEditHandler< Note >
{
   public TaskNoteContentEditHandler( ContentResolver contentResolver,
      IContentValuesFactory contentValuesFactory,
      IModificationsApplier modificationsApplier )
   {
      super( contentResolver, contentValuesFactory, modificationsApplier );
   }
   
   
   
   @Override
   protected Collection< Modification > collectAggregatedUpdateModifications( long rootId,
                                                                              Note existingNote,
                                                                              Note updateNote )
   {
      final Collection< Modification > modifications = new ArrayList< Modification >();
      
      final String entityUri = ContentUris.bindAggregatedElementIdToUri( ContentUris.TASK_NOTES_CONTENT_URI_ID,
                                                                         rootId,
                                                                         existingNote.getId() )
                                          .toString();
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                entityUri,
                                                NoteColumns.NOTE_CREATED_DATE,
                                                existingNote.getCreatedMillisUtc(),
                                                updateNote.getCreatedMillisUtc() );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                entityUri,
                                                NoteColumns.NOTE_MODIFIED_DATE,
                                                existingNote.getModifiedMillisUtc(),
                                                updateNote.getModifiedMillisUtc() );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                entityUri,
                                                NoteColumns.NOTE_DELETED_DATE,
                                                existingNote.getDeletedMillisUtc(),
                                                updateNote.getDeletedMillisUtc() );
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   NoteColumns.NOTE_TITLE,
                                   existingNote.getTitle(),
                                   updateNote.getTitle() );
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   NoteColumns.NOTE_TEXT,
                                   existingNote.getText(),
                                   updateNote.getText() );
      
      return modifications;
   }
   
   
   
   @Override
   protected Collection< Modification > collectAggregatedDeleteModifications( long rootId,
                                                                              long elementId )
   {
      final String entityUri = ContentUris.bindAggregatedElementIdToUri( ContentUris.TASK_NOTES_CONTENT_URI_ID,
                                                                         rootId,
                                                                         elementId )
                                          .toString();
      
      final Modification modification = Modification.newNonPersistentModification( entityUri,
                                                                                   NoteColumns.NOTE_DELETED_DATE,
                                                                                   System.currentTimeMillis() );
      
      return Collections.singletonList( modification );
   }
}
