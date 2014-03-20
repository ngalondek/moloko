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
import dev.drsoran.moloko.content.Columns.TaskColumns;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.model.Note;
import dev.drsoran.rtm.parsing.IRtmCalendarProvider;


public class TaskNoteContentEditHandler extends
         AbstractContentEditHandler< Note >
{
   private final IRtmCalendarProvider calendarProvider;
   
   
   
   public TaskNoteContentEditHandler( ContentResolver contentResolver,
      IContentValuesFactory contentValuesFactory,
      IModificationsApplier modificationsApplier,
      IRtmCalendarProvider calendarProvider )
   {
      super( contentResolver, contentValuesFactory, modificationsApplier );
      this.calendarProvider = calendarProvider;
   }
   
   
   
   @Override
   protected Collection< Modification > collectAggregatedUpdateModifications( long rootId,
                                                                              Note existingNote,
                                                                              Note updateNote )
   {
      final Collection< Modification > modifications = new ArrayList< Modification >();
      
      final String noteEntityUri = ContentUris.bindAggregatedElementIdToUri( ContentUris.TASK_NOTES_CONTENT_URI_ID,
                                                                             rootId,
                                                                             existingNote.getId() )
                                              .toString();
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                noteEntityUri,
                                                NoteColumns.NOTE_CREATED_DATE,
                                                existingNote.getCreatedMillisUtc(),
                                                updateNote.getCreatedMillisUtc() );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                noteEntityUri,
                                                NoteColumns.NOTE_MODIFIED_DATE,
                                                existingNote.getModifiedMillisUtc(),
                                                updateNote.getModifiedMillisUtc() );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                noteEntityUri,
                                                NoteColumns.NOTE_DELETED_DATE,
                                                existingNote.getDeletedMillisUtc(),
                                                updateNote.getDeletedMillisUtc() );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                noteEntityUri,
                                                NoteColumns.NOTE_TITLE,
                                                existingNote.getTitle(),
                                                updateNote.getTitle() );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                noteEntityUri,
                                                NoteColumns.NOTE_TEXT,
                                                existingNote.getText(),
                                                updateNote.getText() );
      
      if ( !modifications.isEmpty() )
      {
         final String taskEntityUri = ContentUris.bindElementId( ContentUris.TASKS_CONTENT_URI_ID,
                                                                 rootId )
                                                 .toString();
         
         final Modification taskModifiedModification = Modification.newNonPersistentModification( taskEntityUri,
                                                                                                  TaskColumns.TASK_MODIFIED_DATE,
                                                                                                  updateNote.getModifiedMillisUtc() );
         modifications.add( taskModifiedModification );
      }
      
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
                                                                                   calendarProvider.getNowMillisUtc() );
      
      return Collections.singletonList( modification );
   }
}
