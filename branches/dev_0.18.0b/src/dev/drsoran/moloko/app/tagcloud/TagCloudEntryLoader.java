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

package dev.drsoran.moloko.app.tagcloud;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import android.database.ContentObserver;
import android.net.Uri;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.content.loaders.AbstractLoader;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.domain.DomainContext;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.services.IContentRepository;
import dev.drsoran.moloko.domain.services.TaskContentOptions;


class TagCloudEntryLoader extends
         AbstractLoader< List< PresentableTagCloudEntry > >
{
   public final static int ID = R.id.loader_tag_cloud_entry;
   
   
   
   public TagCloudEntryLoader( DomainContext context )
   {
      super( context );
   }
   
   
   
   @Override
   public Uri getContentUri()
   {
      // TODO Auto-generated method stub
      return null;
   }
   
   
   
   @Override
   protected List< PresentableTagCloudEntry > queryResultInBackground( IContentRepository contentRepository )
   {
      final SortedMap< PresentableTagCloudEntry, PresentableTagCloudEntry > tagCloudEntries = new TreeMap< PresentableTagCloudEntry, PresentableTagCloudEntry >();
      
      final Iterable< Task > tasks = contentRepository.getAllTasks( TaskContentOptions.Minimal );
      for ( Task task : tasks )
      {
         addOrIncrement( tagCloudEntries,
                         new TasksListTagCloudEntry( task.getListId(),
                                                     task.getListName() ) );
         
         final long locationId = task.getLocationId();
         if ( locationId != Constants.NO_ID )
         {
            addOrIncrement( tagCloudEntries,
                            new LocationTagCloudEntry( location ) );
         }
         
         for ( String tag : task.getTags() )
         {
            addOrIncrement( tagCloudEntries, new TagTagCloudEntry( tag ) );
         }
      }
      
      final List< PresentableTagCloudEntry > cloudEntriesList = new ArrayList< PresentableTagCloudEntry >( tagCloudEntries.keySet() );
      
      return cloudEntriesList;
   }
   
   
   
   private void addOrIncrement( SortedMap< PresentableTagCloudEntry, PresentableTagCloudEntry > tagCloudEntries,
                                PresentableTagCloudEntry tagCloudEntry )
   {
      final PresentableTagCloudEntry existingEntry = tagCloudEntries.get( tagCloudEntry );
      if ( existingEntry != null )
      {
         existingEntry.setCount( existingEntry.getCount() + 1 );
      }
      else
      {
         tagCloudEntries.put( tagCloudEntry, tagCloudEntry );
      }
   }
   
   
   
   @Override
   protected void registerContentObserver( ContentObserver observer )
   {
      ListOverviewsProviderPart.put( getContext(), observer );
      TagsProviderPart.put( getContext(), observer );
      LocationOverviewsProviderPart.put( getContext(), observer );
   }
   
   
   
   @Override
   protected void unregisterContentObserver( ContentObserver observer )
   {
      ListOverviewsProviderPart.unregisterContentObserver( getContext(),
                                                           observer );
      TagsProviderPart.unregisterContentObserver( getContext(), observer );
      LocationOverviewsProviderPart.unregisterContentObserver( getContext(),
                                                               observer );
   }
}
