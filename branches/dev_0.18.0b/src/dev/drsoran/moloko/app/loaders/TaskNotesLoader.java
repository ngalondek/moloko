/* 
 *	Copyright (c) 2012 Ronny Röhricht
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

package dev.drsoran.moloko.app.loaders;

import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.DomainContext;
import dev.drsoran.moloko.domain.model.Note;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.services.IContentRepository;
import dev.drsoran.moloko.domain.services.TaskContentOptions;


public class TaskNotesLoader extends AbstractLoader< List< Note > >
{
   public final static int ID = R.id.loader_notes;
   
   private final long taskId;
   
   
   
   public TaskNotesLoader( DomainContext context, long taskId )
   {
      super( context );
      this.taskId = taskId;
   }
   
   
   
   @Override
   public Uri getContentUri()
   {
      return ContentUris.bindAggregationIdToUri( ContentUris.TASKS_CONTENT_URI_ID,
                                                 taskId );
   }
   
   
   
   @Override
   protected List< Note > queryResultInBackground( IContentRepository contentRepository )
   {
      final Task task = contentRepository.getTask( taskId,
                                                   TaskContentOptions.WithNotes );
      
      final List< Note > notes = new ArrayList< Note >();
      for ( Note note : task.getNotes() )
      {
         notes.add( note );
      }
      
      return notes;
   }
}
