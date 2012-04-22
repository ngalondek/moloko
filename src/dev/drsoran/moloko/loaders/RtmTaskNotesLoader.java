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

import java.util.List;

import android.content.ContentProviderClient;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.mdt.rtm.data.RtmTaskNote;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.RtmNotesProviderPart;
import dev.drsoran.moloko.content.RtmProvider;
import dev.drsoran.provider.Rtm.Notes;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.TaskSeries;


public class RtmTaskNotesLoader extends AbstractLoader< List< RtmTaskNote > >
{
   public final static int ID = R.id.loader_notes;
   
   private final String taskId;
   
   
   
   public RtmTaskNotesLoader( Context context, String taskId )
   {
      super( context );
      this.taskId = taskId;
   }
   
   
   
   @Override
   protected List< RtmTaskNote > queryResultInBackground( ContentProviderClient client )
   {
      final String query = buildQuery();
      final RtmProvider rtmProvider = (RtmProvider) client.getLocalContentProvider();
      final Cursor notesCursor = rtmProvider.querySql( query );
      
      List< RtmTaskNote > taskNotes = null;
      if ( notesCursor != null )
      {
         try
         {
            taskNotes = RtmNotesProviderPart.fromCursor( notesCursor );
         }
         finally
         {
            notesCursor.close();
         }
      }
      
      return taskNotes;
   }
   
   
   
   private String buildQuery()
   {
      final String subQuery = SQLiteQueryBuilder.buildQueryString( // not distinct
      false,
                                                                   // tables
                                                                   TaskSeries.PATH
                                                                      + ","
                                                                      + RawTasks.PATH,
                                                                   
                                                                   // columns
                                                                   new String[]
                                                                   { TaskSeries.PATH
                                                                      + "."
                                                                      + TaskSeries._ID
                                                                      + " AS series_id" },
                                                                   
                                                                   // where
                                                                   "series_id ="
                                                                      + RawTasks.PATH
                                                                      + "."
                                                                      + RawTasks.TASKSERIES_ID
                                                                      + " AND "
                                                                      + RawTasks.PATH
                                                                      + "."
                                                                      + RawTasks._ID
                                                                      + " = "
                                                                      + taskId,
                                                                   null,
                                                                   null,
                                                                   null,
                                                                   null );
      
      final String query = SQLiteQueryBuilder.buildQueryString( // not distinct
      false,
                                                                // tables
                                                                Notes.PATH
                                                                   + ", ("
                                                                   + subQuery
                                                                   + ") AS subQuery",
                                                                
                                                                // columns
                                                                RtmNotesProviderPart.PROJECTION,
                                                                
                                                                // where
                                                                "subQuery.series_id ="
                                                                   + Notes.PATH
                                                                   + "."
                                                                   + Notes.TASKSERIES_ID,
                                                                null,
                                                                null,
                                                                null,
                                                                null );
      
      return query;
   }
   
   
   
   @Override
   protected Uri getContentUri()
   {
      return Notes.CONTENT_URI;
   }
   
   
   
   @Override
   protected void registerContentObserver( ContentObserver observer )
   {
      getContext().getContentResolver()
                  .registerContentObserver( getContentUri(), true, observer );
   }
   
   
   
   @Override
   protected void unregisterContentObserver( ContentObserver observer )
   {
      getContext().getContentResolver().unregisterContentObserver( observer );
   }
}
