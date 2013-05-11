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
   private final String[] columns;
   
   private List< Object[] > list;
   
   private int position = -1;
   
   
   
   public ListCursor( List< Object[] > list, String[] columns )
   {
      if ( list == null )
      {
         throw new IllegalArgumentException( "list" );
      }
      
      if ( columns == null )
      {
         throw new IllegalArgumentException( "columns" );
      }
      
      this.columns = columns;
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
      int newPosition = position + offset;
      
      if ( newPosition < 0 )
      {
         newPosition = -1;
         return false;
      }
      else if ( newPosition >= list.size() )
      {
         newPosition = list.size();
         return false;
      }
      
      position = newPosition;
      return true;
   }
   
   
   
   @Override
   public boolean moveToPosition( int position )
   {
      if ( position > 0 && position < list.size() )
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
      return position == list.size() - 1;
   }
   
   
   
   @Override
   public boolean isBeforeFirst()
   {
      return position < 0;
   }
   
   
   
   @Override
   public boolean isAfterLast()
   {
      return position >= list.size();
   }
   
   
   
   @Override
   public int getColumnIndex( String columnName )
   {
      for ( int i = 0; i < columns.length; i++ )
      {
         if ( columnName.equals( columns[ i ] ) )
         {
            return i;
         }
      }
      
      return -1;
   }
   
   
   
   @Override
   public int getColumnIndexOrThrow( String columnName ) throws IllegalArgumentException
   {
      final int index = getColumnIndexOrThrow( columnName );
      if ( index < 0 )
      {
         throw new IllegalArgumentException( "columnName" );
      }
      
      return index;
   }
   
   
   
   @Override
   public String getColumnName( int columnIndex )
   {
      return columns[ columnIndex ];
   }
   
   
   
   @Override
   public String[] getColumnNames()
   {
      return columns;
   }
   
   
   
   @Override
   public int getColumnCount()
   {
      return columns.length;
   }
   
   
   
   @Override
   public byte[] getBlob( int columnIndex )
   {
      return (byte[]) get( columnIndex );
   }
   
   
   
   @Override
   public String getString( int columnIndex )
   {
      return String.valueOf( get( columnIndex ) );
   }
   
   
   
   @Override
   public void copyStringToBuffer( int columnIndex, CharArrayBuffer buffer )
   {
      final String value = getString( columnIndex );
      buffer.data = value.toCharArray();
      buffer.sizeCopied = value.length();
   }
   
   
   
   @Override
   public short getShort( int columnIndex )
   {
      return Short.parseShort( getString( columnIndex ) );
   }
   
   
   
   @Override
   public int getInt( int columnIndex )
   {
      return Integer.parseInt( getString( columnIndex ) );
   }
   
   
   
   @Override
   public long getLong( int columnIndex )
   {
      return Long.parseLong( getString( columnIndex ) );
   }
   
   
   
   @Override
   public float getFloat( int columnIndex )
   {
      return Float.parseFloat( getString( columnIndex ) );
   }
   
   
   
   @Override
   public double getDouble( int columnIndex )
   {
      return Double.parseDouble( getString( columnIndex ) );
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
