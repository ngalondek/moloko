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

package dev.drsoran.moloko.loaders;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.ListOverviewsProviderPart;
import dev.drsoran.moloko.content.LocationOverviewsProviderPart;
import dev.drsoran.moloko.content.TagsProviderPart;
import dev.drsoran.moloko.fragments.TagCloudFragment.ListTagCloudEntry;
import dev.drsoran.moloko.fragments.TagCloudFragment.LocationTagCloudEntry;
import dev.drsoran.moloko.fragments.TagCloudFragment.TagCloudEntry;
import dev.drsoran.moloko.fragments.TagCloudFragment.TagTagCloudEntry;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.provider.Rtm;
import dev.drsoran.provider.Rtm.ListOverviews;
import dev.drsoran.rtm.LocationWithTaskCount;
import dev.drsoran.rtm.RtmListWithTaskCount;
import dev.drsoran.rtm.TagWithTaskCount;


public class TagCloudEntryLoader extends AbstractLoader< List< TagCloudEntry > >
{
   public final static int ID = R.id.loader_tag_cloud_entry;
   
   
   
   public TagCloudEntryLoader( Context context )
   {
      super( context );
   }
   
   
   
   @Override
   protected List< TagCloudEntry > queryResultInBackground( ContentProviderClient client )
   {
      // Fetch all lists and their task count
      List< RtmListWithTaskCount > lists = null;
      {
         // get all non smart lists
         lists = ListOverviewsProviderPart.getListsOverview( client,
                                                             ListOverviews.IS_SMART_LIST
                                                                + " = 0" );
         if ( lists == null )
         {
            LogUtils.logDBError( getContext(), getClass(), "Lists" );
         }
      }
      
      // Fetch all Tags and their task count
      List< TagWithTaskCount > tags = null;
      {
         tags = TagsProviderPart.getAllTagsWithTaskCount( client );
         
         if ( tags == null )
         {
            LogUtils.logDBError( getContext(), getClass(), "Tags" );
         }
      }
      
      // Fetch all Locations and their task count
      List< LocationWithTaskCount > locations = null;
      {
         locations = LocationOverviewsProviderPart.getLocationsOverview( client,
                                                                         null );
         if ( locations == null )
         {
            LogUtils.logDBError( getContext(), getClass(), "Locations" );
         }
      }
      
      List< TagCloudEntry > cloudEntries = null;
      
      if ( lists != null && tags != null && locations != null )
      {
         final int count = lists.size() + tags.size() + locations.size();
         cloudEntries = new ArrayList< TagCloudEntry >( count );
         
         for ( RtmListWithTaskCount list : lists )
         {
            if ( list.getTaskCount() > 0 )
            {
               cloudEntries.add( new ListTagCloudEntry( list ) );
            }
         }
         
         for ( TagWithTaskCount tag : tags )
         {
            cloudEntries.add( new TagTagCloudEntry( tag ) );
         }
         
         for ( LocationWithTaskCount location : locations )
         {
            if ( location.getIncompleteTaskCount() > 0 )
            {
               cloudEntries.add( new LocationTagCloudEntry( location ) );
            }
         }
      }
      
      return cloudEntries;
   }
   
   
   
   @Override
   protected Uri getContentUri()
   {
      return Uri.EMPTY;
   }
   
   
   
   @Override
   protected ContentProviderClient getContentProviderClient()
   {
      return getContext().getContentResolver()
                         .acquireContentProviderClient( Rtm.AUTHORITY );
   }
   
   
   
   @Override
   protected void registerContentObserver( ContentObserver observer )
   {
      ListOverviewsProviderPart.registerContentObserver( getContext(), observer );
      TagsProviderPart.registerContentObserver( getContext(), observer );
      LocationOverviewsProviderPart.registerContentObserver( getContext(),
                                                             observer );
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
