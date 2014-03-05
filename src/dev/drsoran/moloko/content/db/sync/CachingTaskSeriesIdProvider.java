/* 
 *	Copyright (c) 2014 Ronny Röhricht
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

package dev.drsoran.moloko.content.db.sync;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import dev.drsoran.db.ITable;
import dev.drsoran.moloko.content.db.RtmDatabase;
import dev.drsoran.moloko.content.db.TableColumns.RtmTaskSeriesColumns;
import dev.drsoran.moloko.content.db.TableNames;


public class CachingTaskSeriesIdProvider implements ITaskSeriesIdProvider
{
   private final Map< String, Long > taskSeriesIdCache = new HashMap< String, Long >();
   
   private final RtmDatabase database;
   
   
   
   public CachingTaskSeriesIdProvider( RtmDatabase database )
   {
      if ( database == null )
      {
         throw new IllegalArgumentException( "database" );
      }
      
      this.database = database;
   }
   
   
   
   @Override
   public long getTaskSeriesIdOfRtmTaskSeriesId( String rtmTaskSeriesId )
   {
      Long taskSeriesId = taskSeriesIdCache.get( rtmTaskSeriesId );
      if ( taskSeriesId == null )
      {
         taskSeriesId = queryTaskSeriesId( rtmTaskSeriesId );
         taskSeriesIdCache.put( rtmTaskSeriesId, taskSeriesId );
      }
      
      return taskSeriesId.longValue();
   }
   
   
   
   private Long queryTaskSeriesId( String rtmTaskSeriesId )
   {
      Cursor c = null;
      try
      {
         final ITable taskseriesTable = database.getTable( TableNames.RTM_TASK_SERIES_TABLE );
         c = taskseriesTable.query( new String[]
                                    { RtmTaskSeriesColumns._ID,
                                     RtmTaskSeriesColumns.RTM_TASKSERIES_ID },
                                    RtmTaskSeriesColumns.RTM_TASKSERIES_ID
                                       + "=?",
                                    new String[]
                                    { rtmTaskSeriesId },
                                    null );
         
         if ( !c.moveToFirst() )
         {
            throw new SQLiteException( MessageFormat.format( "Unable to query taskseried ID for RTM taskseries ID {0}",
                                                             rtmTaskSeriesId ) );
         }
         
         return c.getLong( 0 );
      }
      finally
      {
         if ( c != null )
         {
            c.close();
         }
      }
   }
}
