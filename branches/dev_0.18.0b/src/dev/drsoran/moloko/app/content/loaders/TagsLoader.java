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

import java.util.Comparator;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.TagsProviderPart;
import dev.drsoran.provider.Rtm.Tags;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.Tag;


public class TagsLoader extends AbstractLoader< List< Tag > >
{
   public final static int ID = R.id.loader_tags;
   
   
   public final static class Config
   {
      public final static String TASKSERIES_ID = Tasks.TASKSERIES_ID;
      
      public final static String COMPARATOR = "comparator";
      
      public final static String DISTINCT = "distinct";
   }
   
   private final String taskSeriesId;
   
   private final Comparator< Tag > comparator;
   
   private final boolean distinct;
   
   
   
   public TagsLoader( Context context )
   {
      this( context, null, null, true );
   }
   
   
   
   public TagsLoader( Context context, String taskSeriesId,
      Comparator< Tag > comparator, boolean distinct )
   {
      super( context );
      this.taskSeriesId = taskSeriesId;
      this.comparator = comparator;
      this.distinct = distinct;
   }
   
   
   
   @Override
   protected List< Tag > queryResultInBackground( ContentProviderClient client )
   {
      return TagsProviderPart.getAllTags( client,
                                          taskSeriesId,
                                          comparator,
                                          distinct );
   }
   
   
   
   @Override
   protected Uri getContentUri()
   {
      return Tags.CONTENT_URI;
   }
   
   
   
   @Override
   protected void registerContentObserver( ContentObserver observer )
   {
      TagsProviderPart.registerContentObserver( getContext(), observer );
   }
   
   
   
   @Override
   protected void unregisterContentObserver( ContentObserver observer )
   {
      TagsProviderPart.unregisterContentObserver( getContext(), observer );
   }
}
