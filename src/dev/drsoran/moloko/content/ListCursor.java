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

import java.util.List;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;


public class ListCursor implements Cursor
{
   private final String[] projection;
   
   private List< Object[] > list;
   
   private int position = -1;
   
   
   
   public ListCursor( List< Object[] > list, String[] projection )
   {
      if ( list == null )
      {
         throw new IllegalArgumentException( "list" );
      }
      
      if ( projection == null )
      {
         throw new IllegalArgumentException( "columns" );
      }
      
      this.projection = projection;
      this.list = list;
   }
   
   
   
   @Override
   public int getCount()
   {
      return list.size();
   }
   
   
   
   @Override
   public int getPosition()
   {
      return position;
   }
   
   
   
   @Override
   public boolean move( int offset )
   {
      if ( list.isEmpty() )
      {
         position = -1;
         return false;
      }
      
      int newPosition = position + offset;
      
      boolean ok = true;
      if ( newPosition < 0 )
      {
         newPosition = -1;
         ok = false;
      }
      else if ( newPosition >= list.size() )
      {
         newPosition = list.size();
         ok = false;
      }
      
      position = newPosition;
      return ok;
   }
   
   
   
   @Override
   public boolean moveToPosition( int position )
   {
      if ( position > -1 && position < list.size() )
      {
         this.position = position;
         return true;
      }
      
      return false;
   }
   
   
   
   @Override
   public boolean moveToFirst()
   {
      if ( !list.isEmpty() )
      {
         position = 0;
         return true;
      }
      return false;
   }
   
   
   
   @Override
   public boolean moveToLast()
   {
      if ( !list.isEmpty() )
      {
         position = list.size() - 1;
         return true;
      }
      
      return false;
   }
   
   
   
   @Override
   public boolean moveToNext()
   {
      return move( 1 );
   }
   
   
   
   @Override
   public boolean moveToPrevious()
   {
      return move( -1 );
   }
   
   
   
   @Override
   public boolean isFirst()
   {
      return position == 0;
   }
   
   
   
   @Override
   public boolean isLast()
   {
      final int listSize = list.size();
      return listSize > 0 && position == listSize - 1;
   }
   
   
   
   @Override
   public boolean isBeforeFirst()
   {
      return !list.isEmpty() && position < 0;
   }
   
   
   
   @Override
   public boolean isAfterLast()
   {
      final int listSize = list.size();
      return listSize > 0 && position >= listSize;
   }
   
   
   
   @Override
   public int getColumnIndex( String columnName )
   {
      for ( int i = 0; i < projection.length; i++ )
      {
         if ( columnName.equals( projection[ i ] ) )
         {
            return i;
         }
      }
      
      return -1;
   }
   
   
   
   @Override
   public int getColumnIndexOrThrow( String columnName ) throws IllegalArgumentException
   {
      final int index = getColumnIndex( columnName );
      if ( index < 0 )
      {
         throw new IllegalArgumentException( "columnName" );
      }
      
      return index;
   }
   
   
   
   @Override
   public String getColumnName( int columnIndex )
   {
      return projection[ columnIndex ];
   }
   
   
   
   @Override
   public String[] getColumnNames()
   {
      return projection;
   }
   
   
   
   @Override
   public int getColumnCount()
   {
      return projection.length;
   }
   
   
   
   @Override
   public byte[] getBlob( int columnIndex )
   {
      return (byte[]) get( columnIndex );
   }
   
   
   
   @Override
   public String getString( int columnIndex )
   {
      final Object o = get( columnIndex );
      return o == null ? null : String.valueOf( o );
   }
   
   
   
   @Override
   public void copyStringToBuffer( int columnIndex, CharArrayBuffer buffer )
   {
      final String value = getString( columnIndex );
      if ( value != null )
      {
         buffer.data = value.toCharArray();
         buffer.sizeCopied = value.length();
      }
      else
      {
         buffer.data = null;
         buffer.sizeCopied = 0;
      }
   }
   
   
   
   @Override
   public short getShort( int columnIndex )
   {
      final String str = getString( columnIndex );
      return str == null ? 0 : Short.parseShort( str );
   }
   
   
   
   @Override
   public int getInt( int columnIndex )
   {
      final String str = getString( columnIndex );
      return str == null ? 0 : Integer.parseInt( str );
   }
   
   
   
   @Override
   public long getLong( int columnIndex )
   {
      final String str = getString( columnIndex );
      return str == null ? 0L : Long.parseLong( str );
   }
   
   
   
   @Override
   public float getFloat( int columnIndex )
   {
      final String str = getString( columnIndex );
      return str == null ? 0.0f : Float.parseFloat( str );
   }
   
   
   
   @Override
   public double getDouble( int columnIndex )
   {
      final String str = getString( columnIndex );
      return str == null ? 0.0 : Double.parseDouble( str );
   }
   
   
   
   @Override
   public int getType( int columnIndex )
   {
      throw new UnsupportedOperationException();
   }
   
   
   
   @Override
   public boolean isNull( int columnIndex )
   {
      return get( columnIndex ) == null;
   }
   
   
   
   @Override
   public void deactivate()
   {
   }
   
   
   
   @Override
   @Deprecated
   public boolean requery()
   {
      return true;
   }
   
   
   
   @Override
   public void close()
   {
      list = null;
      position = -1;
   }
   
   
   
   @Override
   public boolean isClosed()
   {
      return list == null;
   }
   
   
   
   @Override
   public void registerContentObserver( ContentObserver observer )
   {
      throw new UnsupportedOperationException();
   }
   
   
   
   @Override
   public void unregisterContentObserver( ContentObserver observer )
   {
      throw new UnsupportedOperationException();
   }
   
   
   
   @Override
   public void registerDataSetObserver( DataSetObserver observer )
   {
      throw new UnsupportedOperationException();
   }
   
   
   
   @Override
   public void unregisterDataSetObserver( DataSetObserver observer )
   {
      throw new UnsupportedOperationException();
   }
   
   
   
   @Override
   public void setNotificationUri( ContentResolver cr, Uri uri )
   {
      throw new UnsupportedOperationException();
   }
   
   
   
   @Override
   public boolean getWantsAllOnMoveCalls()
   {
      return false;
   }
   
   
   
   @Override
   public Bundle getExtras()
   {
      return Bundle.EMPTY;
   }
   
   
   
   @Override
   public Bundle respond( Bundle extras )
   {
      return null;
   }
   
   
   
   private Object get( int column )
   {
      return list.get( position )[ column ];
   }
}
