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

import java.util.Date;

import android.database.Cursor;
import dev.drsoran.moloko.util.Strings;


final class CursorUtils
{
   
   private CursorUtils()
   {
      throw new AssertionError();
   }
   
   
   
   public final static String getOptString( Cursor c, int index )
   {
      return c.isNull( index ) ? Strings.EMPTY_STRING : c.getString( index );
   }
   
   
   
   public final static String getOptString( Cursor c, int index, String defValue )
   {
      return c.isNull( index ) ? defValue : c.getString( index );
   }
   
   
   
   public final static Date getOptDate( Cursor c, int index )
   {
      return c.isNull( index ) ? null : new Date( c.getLong( index ) );
   }
   
   
   
   public final static float getOptFloat( Cursor c, int index, float defValue )
   {
      return c.isNull( index ) ? defValue : c.getFloat( index );
   }
   
   
   
   public final static int getOptInt( Cursor c, int index, int defValue )
   {
      return c.isNull( index ) ? defValue : c.getInt( index );
   }
   
   
   
   public final static long getOptLong( Cursor c, int index, long defValue )
   {
      return c.isNull( index ) ? defValue : c.getLong( index );
   }
   
   
   
   public final static Long getOptLong( Cursor c, int index )
   {
      return c.isNull( index ) ? null : c.getLong( index );
   }
   
   
   
   public final static boolean getOptBool( Cursor c, int index, boolean defValue )
   {
      return c.isNull( index ) ? defValue : c.getInt( index ) != 0;
   }
   
   
   
   public final static String[] cursorAsStringArray( Cursor c, int columnIndex )
   {
      return fillStringArray( c, columnIndex, null, 0 );
   }
   
   
   
   public final static String[] fillStringArray( Cursor c,
                                                 int columnIndex,
                                                 String[] array,
                                                 int startIdx )
   {
      String[] res = null;
      
      if ( array == null )
      {
         res = new String[ c.getCount() ];
      }
      else
      {
         res = array;
      }
      
      final boolean cursorHasElements = c.getCount() > 0;
      
      if ( res.length > 0 && cursorHasElements && c.getCount() <= res.length )
      {
         boolean ok = c.moveToFirst();
         
         for ( int i = startIdx; ok && !c.isAfterLast(); c.moveToNext(), ++i )
         {
            res[ i ] = c.getString( columnIndex );
         }
         
         if ( !ok )
         {
            res = null;
         }
      }
      
      return res;
   }
}
