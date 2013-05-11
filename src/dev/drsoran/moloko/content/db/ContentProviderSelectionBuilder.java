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

package dev.drsoran.moloko.content.db;

import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import dev.drsoran.moloko.content.ContentUris;


class ContentProviderSelectionBuilder
{
   private final StringBuilder selectionBuilder = new StringBuilder();
   
   private final Uri contentUri;
   
   private final String tableName;
   
   
   
   public ContentProviderSelectionBuilder( Uri contentUri, String tableName )
   {
      this.contentUri = contentUri;
      this.tableName = tableName;
   }
   
   
   
   public ContentProviderSelectionBuilder selectElement( long id )
   {
      appendAndIfNeeded();
      selectionBuilder.append( "(" )
                      .append( BaseColumns._ID )
                      .append( "=" )
                      .append( id )
                      .append( ")" );
      
      return this;
   }
   
   
   
   public ContentProviderSelectionBuilder selectAllForTaskSeries( ITaskSeriesIdProvider taskSeriesIdProvider,
                                                                  String taskSeriesIdColumn )
   {
      final long taskId = ContentUris.getTaskIdFromUri( contentUri );
      
      appendAndIfNeeded();
      selectionBuilder.append( "(" )
                      .append( tableName )
                      .append( "." )
                      .append( taskSeriesIdColumn )
                      .append( "=" )
                      .append( taskSeriesIdProvider.getTaskSeriesIdOfTask( taskId ) )
                      .append( ")" );
      
      return this;
   }
   
   
   
   public ContentProviderSelectionBuilder andSelect( String selection )
   {
      if ( !TextUtils.isEmpty( selection ) )
      {
         appendAndIfNeeded();
         selectionBuilder.append( "(" ).append( selection ).append( ")" );
      }
      
      return this;
   }
   
   
   
   public String build()
   {
      return selectionBuilder.toString();
   }
   
   
   
   private ContentProviderSelectionBuilder appendAndIfNeeded()
   {
      if ( selectionBuilder.length() > 0 )
      {
         selectionBuilder.append( " AND " );
      }
      
      return this;
   }
}
