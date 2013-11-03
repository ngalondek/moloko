/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dev.drsoran.moloko.domain.model.Location;
import dev.drsoran.moloko.domain.model.TasksList;


class TaskEditData
{
   public final Collection< TasksList > lists;
   
   public final Collection< Location > locations;
   
   
   
   public TaskEditData( Collection< TasksList > lists,
      Collection< Location > locations )
   {
      this.lists = lists;
      this.locations = locations;
   }
   
   
   
   public Map< Long, String > getListIdsWithListNames()
   {
      final Map< Long, String > listIdToListName = new LinkedHashMap< Long, String >( lists.size() );
      for ( TasksList list : lists )
      {
         listIdToListName.put( list.getId(), list.getName() );
      }
      
      return listIdToListName;
   }
   
   
   
   public List< String > getListIds()
   {
      final List< String > ids = new ArrayList< String >( lists.size() );
      for ( TasksList list : lists )
      {
         ids.add( String.valueOf( list.getId() ) );
      }
      
      return ids;
   }
   
   
   
   public List< String > getListNames()
   {
      final List< String > names = new ArrayList< String >( lists.size() );
      for ( TasksList list : lists )
      {
         names.add( list.getName() );
      }
      
      return names;
   }
   
   
   
   public Map< Long, String > getLocationIdsWithLocationNames()
   {
      final Map< Long, String > locationIdToLocationName = new LinkedHashMap< Long, String >( locations.size() );
      for ( Location location : locations )
      {
         locationIdToLocationName.put( location.getId(), location.getName() );
      }
      
      return locationIdToLocationName;
   }
   
   
   
   public List< String > getLocationIds()
   {
      final List< String > ids = new ArrayList< String >( locations.size() );
      for ( Location location : locations )
      {
         ids.add( String.valueOf( location.getId() ) );
      }
      
      return ids;
   }
   
   
   
   public List< String > getLocationNames()
   {
      final List< String > names = new ArrayList< String >( locations.size() );
      for ( Location location : locations )
      {
         names.add( location.getName() );
      }
      
      return names;
   }
}
