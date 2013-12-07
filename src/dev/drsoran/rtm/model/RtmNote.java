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

package dev.drsoran.rtm.model;

import dev.drsoran.Strings;


public class RtmNote
{
   private final String id;
   
   private final String taskSeriesId;
   
   private final long createdMillisUtc;
   
   private final long modifiedMillisUtc;
   
   private final long deletedMillisUtc;
   
   private final String title;
   
   private final String text;
   
   
   
   public RtmNote( String id, String taskSeriesId, long createdMillisUtc,
      long modifiedMillisUtc, long deletedMillisUtc, String title, String text )
   {
      if ( Strings.isNullOrEmpty( id ) )
      {
         throw new IllegalArgumentException( "id" );
      }
      
      if ( Strings.isNullOrEmpty( taskSeriesId ) )
      {
         throw new IllegalArgumentException( "taskSeriesId" );
      }
      
      if ( createdMillisUtc == RtmConstants.NO_TIME )
      {
         throw new IllegalArgumentException( "createdMillisUtc" );
      }
      
      if ( title == null )
      {
         throw new IllegalArgumentException( "title" );
      }
      
      if ( text == null )
      {
         throw new IllegalArgumentException( "text" );
      }
      
      this.id = id;
      this.taskSeriesId = taskSeriesId;
      this.createdMillisUtc = createdMillisUtc;
      this.modifiedMillisUtc = modifiedMillisUtc;
      this.deletedMillisUtc = deletedMillisUtc;
      this.title = title;
      this.text = text;
   }
   
   
   
   public String getId()
   {
      return id;
   }
   
   
   
   public String getTaskSeriesId()
   {
      return taskSeriesId;
   }
   
   
   
   public long getCreatedMillisUtc()
   {
      return createdMillisUtc;
   }
   
   
   
   public long getModifiedMillisUtc()
   {
      return modifiedMillisUtc;
   }
   
   
   
   public long getDeletedMillisUtc()
   {
      return deletedMillisUtc;
   }
   
   
   
   public String getTitle()
   {
      return title;
   }
   
   
   
   public String getText()
   {
      return text;
   }
   
   
   
   @Override
   public String toString()
   {
      return String.format( "RtmNote [id=%s, title=%s, text=%s]",
                            id,
                            title.substring( 0, Math.min( title.length(), 100 ) ),
                            text.substring( 0, Math.min( text.length(), 100 ) ) );
   }
}
