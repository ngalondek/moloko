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

package dev.drsoran.moloko.content;

public class ContentException extends Exception
{
   
   private static final long serialVersionUID = 8983613853726479118L;
   
   
   
   public ContentException()
   {
      super();
   }
   
   
   
   public ContentException( String detailMessage )
   {
      super( detailMessage );
   }
   
   
   
   public ContentException( Throwable throwable )
   {
      super( throwable );
   }
   
   
   
   public ContentException( String detailMessage, Throwable throwable )
   {
      super( detailMessage, throwable );
   }
}
