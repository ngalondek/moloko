/* 
 *	Copyright (c) 2013 Ronny Röhricht
 *
 *	This file is part of MolokoTest.
 *
 *	MolokoTest is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	MolokoTest is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with MolokoTest.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.test.unit.content;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.robolectric.annotation.Config;

import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.os.Bundle;
import dev.drsoran.moloko.content.ListCursor;
import dev.drsoran.moloko.test.MolokoRoboTestCase;


@SuppressWarnings( "resource" )
@Config( manifest = Config.NONE )
public class ListCursorFixture extends MolokoRoboTestCase
{
   private final static String[] COLUMNS = new String[]
   { "id", "value" };
   
   private final static List< Object[] > EMPTY_LIST = new ArrayList< Object[] >( 0 );
   
   private final static List< Object[] > TEST_LIST_STRING;
   
   static
   {
      TEST_LIST_STRING = makeList( "testValue1", "testValue2", null );
   }
   
   
   
   @Test
   public void testListCursor()
   {
      new ListCursor( EMPTY_LIST, COLUMNS );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testListCursorNullList()
   {
      new ListCursor( null, COLUMNS );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testListCursorNullColumns()
   {
      new ListCursor( EMPTY_LIST, null );
   }
   
   
   
   @Test
   public void testGetCount()
   {
      assertThat( new ListCursor( EMPTY_LIST, COLUMNS ).getCount(), is( 0 ) );
      assertThat( new ListCursor( TEST_LIST_STRING, COLUMNS ).getCount(),
                  is( TEST_LIST_STRING.size() ) );
   }
   
   
   
   @Test
   public void testGetPosition()
   {
      final Cursor listCursor = new ListCursor( TEST_LIST_STRING, COLUMNS );
      assertThat( listCursor.getPosition(), is( -1 ) );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.getPosition(), is( 0 ) );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.getPosition(), is( 1 ) );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.getPosition(), is( 2 ) );
      
      assertThat( listCursor.moveToNext(), is( false ) );
      assertThat( listCursor.getPosition(), is( 3 ) );
   }
   
   
   
   @Test
   public void testMove()
   {
      Cursor listCursor = new ListCursor( TEST_LIST_STRING, COLUMNS );
      assertThat( listCursor.move( -1 ), is( false ) );
      assertThat( listCursor.getPosition(), is( -1 ) );
      
      assertThat( listCursor.move( 0 ), is( false ) );
      assertThat( listCursor.getPosition(), is( -1 ) );
      
      assertThat( listCursor.move( 1 ), is( true ) );
      assertThat( listCursor.getPosition(), is( 0 ) );
      
      assertThat( listCursor.move( 1 ), is( true ) );
      assertThat( listCursor.getPosition(), is( 1 ) );
      
      assertThat( listCursor.move( 1 ), is( true ) );
      assertThat( listCursor.getPosition(), is( 2 ) );
      
      assertThat( listCursor.move( 0 ), is( true ) );
      assertThat( listCursor.getPosition(), is( 2 ) );
      
      assertThat( listCursor.move( 1 ), is( false ) );
      assertThat( listCursor.getPosition(), is( 3 ) );
      
      assertThat( listCursor.move( 1 ), is( false ) );
      assertThat( listCursor.getPosition(), is( 3 ) );
      
      assertThat( listCursor.move( 0 ), is( false ) );
      assertThat( listCursor.getPosition(), is( 3 ) );
      
      assertThat( listCursor.move( -1 ), is( true ) );
      assertThat( listCursor.getPosition(), is( 2 ) );
      
      assertThat( listCursor.move( -1 ), is( true ) );
      assertThat( listCursor.getPosition(), is( 1 ) );
      
      assertThat( listCursor.move( -1 ), is( true ) );
      assertThat( listCursor.getPosition(), is( 0 ) );
      
      assertThat( listCursor.move( -1 ), is( false ) );
      assertThat( listCursor.getPosition(), is( -1 ) );
      
      assertThat( listCursor.move( -1 ), is( false ) );
      assertThat( listCursor.getPosition(), is( -1 ) );
      
      listCursor = new ListCursor( TEST_LIST_STRING, COLUMNS );
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.getPosition(), is( 1 ) );
      
      assertThat( listCursor.move( 100 ), is( false ) );
      assertThat( listCursor.getPosition(), is( 3 ) );
      
      listCursor = new ListCursor( TEST_LIST_STRING, COLUMNS );
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.getPosition(), is( 1 ) );
      
      assertThat( listCursor.move( -100 ), is( false ) );
      assertThat( listCursor.getPosition(), is( -1 ) );
   }
   
   
   
   @Test
   public void testMoveEmptyList()
   {
      final Cursor listCursor = new ListCursor( EMPTY_LIST, COLUMNS );
      assertThat( listCursor.move( -1 ), is( false ) );
      assertThat( listCursor.getPosition(), is( -1 ) );
      
      assertThat( listCursor.move( 0 ), is( false ) );
      assertThat( listCursor.getPosition(), is( -1 ) );
      
      assertThat( listCursor.move( 1 ), is( false ) );
      assertThat( listCursor.getPosition(), is( -1 ) );
   }
   
   
   
   @Test
   public void testMoveToPosition()
   {
      Cursor listCursor = new ListCursor( TEST_LIST_STRING, COLUMNS );
      assertThat( listCursor.moveToPosition( -1 ), is( false ) );
      assertThat( listCursor.getPosition(), is( -1 ) );
      assertThat( listCursor.moveToPosition( -10 ), is( false ) );
      assertThat( listCursor.getPosition(), is( -1 ) );
      assertThat( listCursor.moveToPosition( 3 ), is( false ) );
      assertThat( listCursor.getPosition(), is( -1 ) );
      assertThat( listCursor.moveToPosition( 30 ), is( false ) );
      assertThat( listCursor.getPosition(), is( -1 ) );
      
      assertThat( listCursor.moveToPosition( 0 ), is( true ) );
      assertThat( listCursor.moveToPosition( 1 ), is( true ) );
      assertThat( listCursor.moveToPosition( 2 ), is( true ) );
      assertThat( listCursor.moveToPosition( 3 ), is( false ) );
      assertThat( listCursor.getPosition(), is( 2 ) );
   }
   
   
   
   @Test
   public void testMoveToFirst()
   {
      Cursor listCursor = new ListCursor( TEST_LIST_STRING, COLUMNS );
      assertThat( listCursor.moveToFirst(), is( true ) );
      assertThat( listCursor.getPosition(), is( 0 ) );
      
      listCursor = new ListCursor( EMPTY_LIST, COLUMNS );
      assertThat( listCursor.moveToFirst(), is( false ) );
      assertThat( listCursor.getPosition(), is( -1 ) );
   }
   
   
   
   @Test
   public void testMoveToLast()
   {
      Cursor listCursor = new ListCursor( TEST_LIST_STRING, COLUMNS );
      assertThat( listCursor.moveToLast(), is( true ) );
      assertThat( listCursor.getPosition(), is( 2 ) );
      
      listCursor = new ListCursor( EMPTY_LIST, COLUMNS );
      assertThat( listCursor.moveToLast(), is( false ) );
      assertThat( listCursor.getPosition(), is( -1 ) );
   }
   
   
   
   @Test
   public void testMoveToNext()
   {
      Cursor listCursor = new ListCursor( TEST_LIST_STRING, COLUMNS );
      assertThat( listCursor.getPosition(), is( -1 ) );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.getPosition(), is( 0 ) );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.getPosition(), is( 1 ) );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.getPosition(), is( 2 ) );
      
      assertThat( listCursor.moveToNext(), is( false ) );
      assertThat( listCursor.getPosition(), is( 3 ) );
      
      assertThat( listCursor.moveToNext(), is( false ) );
      assertThat( listCursor.getPosition(), is( 3 ) );
   }
   
   
   
   @Test
   public void testMoveToNextEmptyList()
   {
      Cursor listCursor = new ListCursor( EMPTY_LIST, COLUMNS );
      assertThat( listCursor.getPosition(), is( -1 ) );
      assertThat( listCursor.moveToNext(), is( false ) );
      assertThat( listCursor.getPosition(), is( -1 ) );
   }
   
   
   
   @Test
   public void testMoveToPrevious()
   {
      Cursor listCursor = new ListCursor( TEST_LIST_STRING, COLUMNS );
      assertThat( listCursor.getPosition(), is( -1 ) );
      
      assertThat( listCursor.moveToPrevious(), is( false ) );
      assertThat( listCursor.getPosition(), is( -1 ) );
      
      assertThat( listCursor.moveToPosition( 2 ), is( true ) );
      assertThat( listCursor.getPosition(), is( 2 ) );
      
      assertThat( listCursor.moveToPrevious(), is( true ) );
      assertThat( listCursor.getPosition(), is( 1 ) );
      
      assertThat( listCursor.moveToPrevious(), is( true ) );
      assertThat( listCursor.getPosition(), is( 0 ) );
      
      assertThat( listCursor.moveToPrevious(), is( false ) );
      assertThat( listCursor.getPosition(), is( -1 ) );
      
      assertThat( listCursor.moveToPrevious(), is( false ) );
      assertThat( listCursor.getPosition(), is( -1 ) );
   }
   
   
   
   @Test
   public void testMoveToPreviousEmptyList()
   {
      Cursor listCursor = new ListCursor( EMPTY_LIST, COLUMNS );
      assertThat( listCursor.getPosition(), is( -1 ) );
      assertThat( listCursor.moveToPrevious(), is( false ) );
      assertThat( listCursor.getPosition(), is( -1 ) );
   }
   
   
   
   @Test
   public void testIsFirst()
   {
      Cursor listCursor = new ListCursor( TEST_LIST_STRING, COLUMNS );
      assertThat( listCursor.getPosition(), is( -1 ) );
      assertThat( listCursor.isFirst(), is( false ) );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.isFirst(), is( true ) );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.isFirst(), is( false ) );
      
      assertThat( listCursor.moveToPrevious(), is( true ) );
      assertThat( listCursor.isFirst(), is( true ) );
   }
   
   
   
   @Test
   public void testIsFirstEmptyList()
   {
      Cursor listCursor = new ListCursor( EMPTY_LIST, COLUMNS );
      assertThat( listCursor.moveToNext(), is( false ) );
      assertThat( listCursor.isFirst(), is( false ) );
   }
   
   
   
   @Test
   public void testIsLast()
   {
      Cursor listCursor = new ListCursor( TEST_LIST_STRING, COLUMNS );
      assertThat( listCursor.getPosition(), is( -1 ) );
      assertThat( listCursor.isLast(), is( false ) );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.isLast(), is( false ) );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.isLast(), is( false ) );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.isLast(), is( true ) );
      
      assertThat( listCursor.moveToNext(), is( false ) );
      assertThat( listCursor.isLast(), is( false ) );
      
      assertThat( listCursor.moveToPrevious(), is( true ) );
      assertThat( listCursor.isLast(), is( true ) );
   }
   
   
   
   @Test
   public void testIsLastEmptyList()
   {
      Cursor listCursor = new ListCursor( EMPTY_LIST, COLUMNS );
      assertThat( listCursor.moveToNext(), is( false ) );
      assertThat( listCursor.isLast(), is( false ) );
   }
   
   
   
   @Test
   public void testIsBeforeFirst()
   {
      Cursor listCursor = new ListCursor( TEST_LIST_STRING, COLUMNS );
      assertThat( listCursor.getPosition(), is( -1 ) );
      assertThat( listCursor.isBeforeFirst(), is( true ) );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.isBeforeFirst(), is( false ) );
      
      assertThat( listCursor.moveToPrevious(), is( false ) );
      assertThat( listCursor.isBeforeFirst(), is( true ) );
   }
   
   
   
   @Test
   public void testIsBeforeFirstEmptyList()
   {
      Cursor listCursor = new ListCursor( EMPTY_LIST, COLUMNS );
      assertThat( listCursor.isBeforeFirst(), is( false ) );
   }
   
   
   
   @Test
   public void testIsAfterLast()
   {
      Cursor listCursor = new ListCursor( TEST_LIST_STRING, COLUMNS );
      assertThat( listCursor.getPosition(), is( -1 ) );
      assertThat( listCursor.isAfterLast(), is( false ) );
      
      assertThat( listCursor.moveToPosition( listCursor.getCount() - 1 ),
                  is( true ) );
      assertThat( listCursor.isLast(), is( true ) );
      assertThat( listCursor.isAfterLast(), is( false ) );
      
      assertThat( listCursor.moveToNext(), is( false ) );
      assertThat( listCursor.isAfterLast(), is( true ) );
      
      assertThat( listCursor.moveToPrevious(), is( true ) );
      assertThat( listCursor.isAfterLast(), is( false ) );
   }
   
   
   
   @Test
   public void testIsAfterLastEmptyList()
   {
      Cursor listCursor = new ListCursor( EMPTY_LIST, COLUMNS );
      assertThat( listCursor.isAfterLast(), is( false ) );
   }
   
   
   
   @Test
   public void testGetColumnIndex()
   {
      Cursor listCursor = new ListCursor( TEST_LIST_STRING, COLUMNS );
      assertThat( listCursor.getColumnIndex( COLUMNS[ 0 ] ), is( 0 ) );
      assertThat( listCursor.getColumnIndex( COLUMNS[ 1 ] ), is( 1 ) );
      assertThat( listCursor.getColumnIndex( "??" ), is( -1 ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testGetColumnIndexOrThrow()
   {
      Cursor listCursor = new ListCursor( TEST_LIST_STRING, COLUMNS );
      assertThat( listCursor.getColumnIndexOrThrow( COLUMNS[ 0 ] ), is( 0 ) );
      assertThat( listCursor.getColumnIndexOrThrow( COLUMNS[ 1 ] ), is( 1 ) );
      listCursor.getColumnIndexOrThrow( "??" );
   }
   
   
   
   @Test
   public void testGetColumnName()
   {
      Cursor listCursor = new ListCursor( TEST_LIST_STRING, COLUMNS );
      assertThat( listCursor.getColumnName( 0 ), is( COLUMNS[ 0 ] ) );
      assertThat( listCursor.getColumnName( 1 ), is( COLUMNS[ 1 ] ) );
   }
   
   
   
   @Test( expected = IndexOutOfBoundsException.class )
   public void testGetColumnNameWrongIndex()
   {
      Cursor listCursor = new ListCursor( TEST_LIST_STRING, COLUMNS );
      listCursor.getColumnName( 10 );
   }
   
   
   
   @Test
   public void testGetColumnNames()
   {
      Cursor listCursor = new ListCursor( TEST_LIST_STRING, COLUMNS );
      assertArrayEquals( COLUMNS, listCursor.getColumnNames() );
   }
   
   
   
   @Test
   public void testGetColumnCount()
   {
      Cursor listCursor = new ListCursor( TEST_LIST_STRING, COLUMNS );
      assertThat( listCursor.getColumnCount(), is( 2 ) );
   }
   
   
   
   @Test
   public void testGetBlob()
   {
      List< Object[] > blobList = makeList( new byte[]
      { 0x01 }, new byte[]
      { 0x02 }, null );
      
      Cursor listCursor = new ListCursor( blobList, COLUMNS );
      assertThat( listCursor.moveToNext(), is( true ) );
      assertArrayEquals( listCursor.getBlob( 1 ), new byte[]
      { 0x01 } );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      assertArrayEquals( listCursor.getBlob( 1 ), new byte[]
      { 0x02 } );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.getBlob( 1 ), is( (byte[]) null ) );
   }
   
   
   
   @Test
   public void testGetString()
   {
      Cursor listCursor = new ListCursor( TEST_LIST_STRING, COLUMNS );
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.getString( 1 ), is( "testValue1" ) );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.getString( 1 ), is( "testValue2" ) );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.getString( 1 ), is( (String) null ) );
   }
   
   
   
   @Test
   public void testCopyStringToBuffer()
   {
      Cursor listCursor = new ListCursor( TEST_LIST_STRING, COLUMNS );
      CharArrayBuffer buffer = new CharArrayBuffer( 0 );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      listCursor.copyStringToBuffer( 1, buffer );
      assertArrayEquals( buffer.data, "testValue1".toCharArray() );
      assertThat( buffer.sizeCopied, is( "testValue1".length() ) );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      listCursor.copyStringToBuffer( 1, buffer );
      assertArrayEquals( buffer.data, "testValue2".toCharArray() );
      assertThat( buffer.sizeCopied, is( "testValue2".length() ) );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      listCursor.copyStringToBuffer( 1, buffer );
      assertNull( buffer.data );
      assertThat( buffer.sizeCopied, is( 0 ) );
   }
   
   
   
   @Test
   public void testGetShort()
   {
      List< Object[] > shortList = makeList( (short) 1, (short) 2, (Short) null );
      
      Cursor listCursor = new ListCursor( shortList, COLUMNS );
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.getShort( 1 ), is( (short) 1 ) );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.getShort( 1 ), is( (short) 2 ) );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.getShort( 1 ), is( (short) 0 ) );
   }
   
   
   
   @Test
   public void testGetInt()
   {
      List< Object[] > intList = makeList( 1, 2, null );
      
      Cursor listCursor = new ListCursor( intList, COLUMNS );
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.getInt( 1 ), is( 1 ) );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.getInt( 1 ), is( 2 ) );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.getInt( 1 ), is( 0 ) );
   }
   
   
   
   @Test
   public void testGetLong()
   {
      List< Object[] > longList = makeList( 1L, 2L, null );
      
      Cursor listCursor = new ListCursor( longList, COLUMNS );
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.getLong( 1 ), is( 1L ) );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.getLong( 1 ), is( 2L ) );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.getLong( 1 ), is( 0L ) );
   }
   
   
   
   @Test
   public void testGetFloat()
   {
      List< Object[] > floatList = makeList( 1.0f, 2.0f, null );
      
      Cursor listCursor = new ListCursor( floatList, COLUMNS );
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.getFloat( 1 ), is( 1.0f ) );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.getFloat( 1 ), is( 2.0f ) );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.getFloat( 1 ), is( 0.0f ) );
   }
   
   
   
   @Test
   public void testGetDouble()
   {
      List< Object[] > doubleList = makeList( 1.0, 2.0, null );
      
      Cursor listCursor = new ListCursor( doubleList, COLUMNS );
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.getDouble( 1 ), is( 1.0 ) );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.getDouble( 1 ), is( 2.0 ) );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.getDouble( 1 ), is( 0.0 ) );
   }
   
   
   
   @Test( expected = UnsupportedOperationException.class )
   public void testGetType()
   {
      new ListCursor( EMPTY_LIST, COLUMNS ).getType( 0 );
   }
   
   
   
   @Test
   public void testIsNull()
   {
      Cursor listCursor = new ListCursor( TEST_LIST_STRING, COLUMNS );
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.isNull( 0 ), is( false ) );
      assertThat( listCursor.isNull( 1 ), is( false ) );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.isNull( 0 ), is( false ) );
      assertThat( listCursor.isNull( 1 ), is( false ) );
      
      assertThat( listCursor.moveToNext(), is( true ) );
      assertThat( listCursor.isNull( 0 ), is( false ) );
      assertThat( listCursor.isNull( 1 ), is( true ) );
   }
   
   
   
   @Test( expected = IndexOutOfBoundsException.class )
   public void testIsNullInvalidIndex()
   {
      Cursor listCursor = new ListCursor( TEST_LIST_STRING, COLUMNS );
      listCursor.isNull( 100 );
   }
   
   
   
   @Test( expected = IndexOutOfBoundsException.class )
   public void testIsNullEmptyList()
   {
      Cursor listCursor = new ListCursor( EMPTY_LIST, COLUMNS );
      listCursor.isNull( 0 );
   }
   
   
   
   @Test
   public void testDeactivate()
   {
      new ListCursor( EMPTY_LIST, COLUMNS ).deactivate();
   }
   
   
   
   @SuppressWarnings( "deprecation" )
   @Test
   public void testRequery()
   {
      assertThat( new ListCursor( EMPTY_LIST, COLUMNS ).requery(), is( true ) );
   }
   
   
   
   @Test
   public void testClose()
   {
      Cursor listCursor = new ListCursor( TEST_LIST_STRING, COLUMNS );
      listCursor.close();
      listCursor.close();
   }
   
   
   
   @Test
   public void testIsClosed()
   {
      Cursor listCursor = new ListCursor( TEST_LIST_STRING, COLUMNS );
      assertThat( listCursor.isClosed(), is( false ) );
      listCursor.close();
      assertThat( listCursor.isClosed(), is( true ) );
      listCursor.close();
      assertThat( listCursor.isClosed(), is( true ) );
   }
   
   
   
   @Test( expected = UnsupportedOperationException.class )
   public void testRegisterContentObserver()
   {
      new ListCursor( EMPTY_LIST, COLUMNS ).registerContentObserver( null );
   }
   
   
   
   @Test( expected = UnsupportedOperationException.class )
   public void testUnregisterContentObserver()
   {
      new ListCursor( EMPTY_LIST, COLUMNS ).unregisterContentObserver( null );
   }
   
   
   
   @Test( expected = UnsupportedOperationException.class )
   public void testRegisterDataSetObserver()
   {
      new ListCursor( EMPTY_LIST, COLUMNS ).registerDataSetObserver( null );
   }
   
   
   
   @Test( expected = UnsupportedOperationException.class )
   public void testUnregisterDataSetObserver()
   {
      new ListCursor( EMPTY_LIST, COLUMNS ).unregisterDataSetObserver( null );
   }
   
   
   
   @Test( expected = UnsupportedOperationException.class )
   public void testSetNotificationUri()
   {
      new ListCursor( EMPTY_LIST, COLUMNS ).setNotificationUri( null, null );
   }
   
   
   
   @Test
   public void testGetWantsAllOnMoveCalls()
   {
      assertThat( new ListCursor( EMPTY_LIST, COLUMNS ).getWantsAllOnMoveCalls(),
                  is( false ) );
   }
   
   
   
   @Test
   public void testGetExtras()
   {
      assertThat( new ListCursor( EMPTY_LIST, COLUMNS ).getExtras(),
                  is( Bundle.EMPTY ) );
   }
   
   
   
   @Test
   public void testRespond()
   {
      assertNull( new ListCursor( EMPTY_LIST, COLUMNS ).respond( null ) );
   }
   
   
   
   private static < T > List< Object[] > makeList( T... elements )
   {
      final List< Object[] > list = new ArrayList< Object[] >();
      for ( int i = 0; i < elements.length; i++ )
      {
         T element = elements[ i ];
         list.add( new Object[]
         { i, element } );
      }
      
      return list;
   }
}
