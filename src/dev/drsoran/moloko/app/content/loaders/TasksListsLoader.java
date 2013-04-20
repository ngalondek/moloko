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

package dev.drsoran.moloko.app.content.loaders;

import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.DomainContext;
import dev.drsoran.moloko.domain.model.ExtendedTaskCount;
import dev.drsoran.moloko.domain.model.ITasksList;
import dev.drsoran.moloko.domain.services.ContentException;
import dev.drsoran.moloko.domain.services.IContentRepository;
import dev.drsoran.moloko.grammar.GrammarException;


public class TasksListsLoader extends AbstractLoader< List< ITasksList > >
{
   public final static int ID = R.id.tasks_lists_loader;
   
   private final boolean includeTasksCount;
   
   
   
   public TasksListsLoader( DomainContext context, boolean includeTasksCount )
   {
      super( context );
      this.includeTasksCount = includeTasksCount;
   }
   
   
   
   @Override
   public Uri getContentUri()
   {
      return ContentUris.TASKS_LISTS_CONTENT_URI;
   }
   
   
   
   @Override
   protected List< ITasksList > queryResultInBackground( IContentRepository contentRepository ) throws ContentException
   {
      final ArrayList< ITasksList > list = new ArrayList< ITasksList >();
      
      for ( ITasksList tasksList : contentRepository.getAllTasksLists() )
      {
         if ( includeTasksCount )
         {
            try
            {
               final ExtendedTaskCount taskCount = contentRepository.getTaskCountOfTasksList( tasksList );
               tasksList.setTasksCount( taskCount );
            }
            catch ( GrammarException e )
            {
            }
         }
         
         list.add( tasksList );
      }
      
      return list;
   }
}
