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

package dev.drsoran.moloko.domain.model;

import dev.drsoran.moloko.util.Strings;


public class Note extends LifeTimeManaged
{
   private final long id;
   
   private String title = Strings.EMPTY_STRING;
   
   private String text = Strings.EMPTY_STRING;
   
   
   
   public Note( long id, long createdMillisUtc )
   {
      super( createdMillisUtc );
      this.id = id;
   }
   
   
   
   public long getId()
   {
      return id;
   }
   
   
   
   public String getTitle()
   {
      return title;
   }
   
   
   
   public void setTitle( String title )
   {
      if ( title == null )
      {
         throw new IllegalArgumentException( "title" );
      }
      
      this.title = title;
   }
   
   
   
   public String getText()
   {
      return text;
   }
   
   
   
   public void setText( String text )
   {
      if ( text == null )
      {
         throw new IllegalArgumentException( "text" );
      }
      
      this.text = text;
   }
   
   
   
   @Override
   public String toString()
   {
      return String.format( "Note [id=%s, %s, title=%s, text=%s]",
                            id,
                            super.toString(),
                            title.substring( 0, Math.min( title.length(), 100 ) ),
                            text.substring( 0, Math.min( text.length(), 100 ) ) );
   }
}
