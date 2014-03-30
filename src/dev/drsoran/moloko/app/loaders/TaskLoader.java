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

import android.net.Uri;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.DomainContext;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.services.ContentException;
import dev.drsoran.moloko.domain.services.IContentRepository;
import dev.drsoran.moloko.domain.services.TaskContentOptions;


public class TaskLoader extends AbstractLoader< Task >
{
   public final static int ID = R.id.loader_task;
   
   private final long taskId;
   
   private final TaskContentOptions taskContentOptions;
   
   
   
   public TaskLoader( DomainContext context, long taskId,
      TaskContentOptions taskContentOptions )
   {
      super( context );
      
      this.taskId = taskId;
      this.taskContentOptions = taskContentOptions;
   }
   
   
   
   @Override
   public Uri getContentUri()
   {
      return ContentUris.bindElementId( ContentUris.TASKS_CONTENT_URI_ID,
                                        taskId );
   }
   
   
   
   @Override
   public Task queryResultInBackground( IContentRepository contentRepository ) throws ContentException
   {
      return contentRepository.getTask( taskId, taskContentOptions );
   }
}
