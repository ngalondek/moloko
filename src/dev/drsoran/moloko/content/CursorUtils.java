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

import android.database.Cursor;


public final class CursorUtils
{
   
   private CursorUtils()
   {
      throw new AssertionError();
   }
   
   
   
   public final static String getOptString( Cursor c, int index )
   {
      if ( c == null )
      {
         throw new IllegalArgumentException( "c" );
      }
      
      return c.isNull( index ) ? null : c.getString( index );
   }
   
   
   
   public final static String getOptString( Cursor c, int index, String defValue )
   {
      if ( c == null )
      {
         throw new IllegalArgumentException( "c" );
      }
      
      return c.isNull( index ) ? defValue : c.getString( index );
   }
   
   
   
   public final static float getOptFloat( Cursor c, int index, float defValue )
   {
      if ( c == null )
      {
         throw new IllegalArgumentException( "c" );
      }
      
      return c.isNull( index ) ? defValue : c.getFloat( index );
   }
   
   
   
   public final static int getOptInt( Cursor c, int index, int defValue )
   {
      if ( c == null )
      {
         throw new IllegalArgumentException( "c" );
      }
      
      return c.isNull( index ) ? defValue : c.getInt( index );
   }
   
   
   
   public final static long getOptLong( Cursor c, int index, long defValue )
   {
      if ( c == null )
      {
         throw new IllegalArgumentException( "c" );
      }
      
      return c.isNull( index ) ? defValue : c.getLong( index );
   }
   
   
   
   public final static Long getOptLong( Cursor c, int index )
   {
      if ( c == null )
      {
         throw new IllegalArgumentException( "c" );
      }
      
      return c.isNull( index ) ? null : c.getLong( index );
   }
   
   
   
   public final static boolean getOptBool( Cursor c, int index, boolean defValue )
   {
      if ( c == null )
      {
         throw new IllegalArgumentException( "c" );
      }
      
      return c.isNull( index ) ? defValue : c.getInt( index ) != 0;
   }
}
