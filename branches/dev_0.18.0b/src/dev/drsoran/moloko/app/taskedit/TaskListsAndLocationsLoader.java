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

package dev.drsoran.moloko.app.taskedit;

import java.util.List;

import android.net.Uri;
import dev.drsoran.Iterables;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.loaders.AbstractLoader;
import dev.drsoran.moloko.domain.DomainContext;
import dev.drsoran.moloko.domain.model.Location;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.moloko.domain.services.ContentException;
import dev.drsoran.moloko.domain.services.IContentRepository;


class TaskListsAndLocationsLoader extends AbstractLoader< TaskEditData >
{
   public final static int ID = R.id.loader_taskedit_data;
   
   
   
   public TaskListsAndLocationsLoader( DomainContext context )
   {
      super( context );
   }
   
   
   
   @Override
   public Uri getContentUri()
   {
      return Uri.EMPTY;
   }
   
   
   
   @Override
   protected TaskEditData queryResultInBackground( IContentRepository respository ) throws ContentException
   {
      final List< TasksList > lists = Iterables.asList( respository.getPhysicalTasksLists() );
      final List< Location > locations = Iterables.asList( respository.getAllLocations() );
      
      return new TaskEditData( lists, locations );
   }
}
