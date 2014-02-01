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



public class RtmNote
{
   private final String id;
   
   private final long createdMillisUtc;
   
   private final long modifiedMillisUtc;
   
   private final String title;
   
   private final String text;
   
   
   
   public RtmNote( String id, long createdMillisUtc, long modifiedMillisUtc,
      String title, String text )
   {
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
      this.createdMillisUtc = createdMillisUtc;
      this.modifiedMillisUtc = modifiedMillisUtc;
      this.title = title;
      this.text = text;
   }
   
   
   
   public String getId()
   {
      return id;
   }
   
   
   
   public long getCreatedMillisUtc()
   {
      return createdMillisUtc;
   }
   
   
   
   public long getModifiedMillisUtc()
   {
      return modifiedMillisUtc;
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
