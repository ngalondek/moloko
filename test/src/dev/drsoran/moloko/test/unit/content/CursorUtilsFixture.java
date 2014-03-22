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
import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.junit.Test;

import android.database.Cursor;
import dev.drsoran.moloko.content.CursorUtils;
import dev.drsoran.rtm.test.PrivateCtorCaller;


public class CursorUtilsFixture
{
   private final static int INDEX = 0;
   
   
   
   @Test( expected = AssertionError.class )
   public void testPrivateCtor() throws Throwable
   {
      PrivateCtorCaller.callPrivateCtor( CursorUtils.class );
   }
   
   
   
   @Test
   public void testGetOptStringCursorInt()
   {
      Cursor c = EasyMock.createStrictMock( Cursor.class );
      EasyMock.expect( c.isNull( INDEX ) ).andReturn( false );
      EasyMock.expect( c.getString( INDEX ) ).andReturn( "test" );
      EasyMock.replay( c );
      
      assertThat( CursorUtils.getOptString( c, INDEX ), is( "test" ) );
      
      EasyMock.verify( c );
      
      c = EasyMock.createStrictMock( Cursor.class );
      EasyMock.expect( c.isNull( INDEX ) ).andReturn( true );
      EasyMock.replay( c );
      
      assertThat( CursorUtils.getOptString( c, INDEX ), is( (String) null ) );
      
      EasyMock.verify( c );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testGetOptStringCursorIntNullCursor()
   {
      CursorUtils.getOptString( null, INDEX );
   }
   
   
   
   @Test
   public void testGetOptStringCursorIntString()
   {
      Cursor c = EasyMock.createStrictMock( Cursor.class );
      EasyMock.expect( c.isNull( INDEX ) ).andReturn( false );
      EasyMock.expect( c.getString( INDEX ) ).andReturn( "test" );
      EasyMock.replay( c );
      
      assertThat( CursorUtils.getOptString( c, INDEX, "default" ), is( "test" ) );
      
      EasyMock.verify( c );
      
      c = EasyMock.createStrictMock( Cursor.class );
      EasyMock.expect( c.isNull( INDEX ) ).andReturn( true ).anyTimes();
      EasyMock.replay( c );
      
      assertThat( CursorUtils.getOptString( c, INDEX, "default" ),
                  is( "default" ) );
      
      assertThat( CursorUtils.getOptString( c, INDEX, null ),
                  is( (String) null ) );
      
      EasyMock.verify( c );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testGetOptStringCursorIntStringNullCursor()
   {
      CursorUtils.getOptString( null, INDEX, null );
   }
   
   
   
   @Test
   public void testGetOptFloat()
   {
      Cursor c = EasyMock.createStrictMock( Cursor.class );
      EasyMock.expect( c.isNull( INDEX ) ).andReturn( false );
      EasyMock.expect( c.getFloat( INDEX ) ).andReturn( 1000.0f );
      EasyMock.replay( c );
      
      assertThat( CursorUtils.getOptFloat( c, INDEX, 1.0f ), is( 1000.0f ) );
      
      EasyMock.verify( c );
      
      c = EasyMock.createStrictMock( Cursor.class );
      EasyMock.expect( c.isNull( INDEX ) ).andReturn( true );
      EasyMock.replay( c );
      
      assertThat( CursorUtils.getOptFloat( c, INDEX, 1.0f ), is( 1.0f ) );
      
      EasyMock.verify( c );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testGetOptFloatNullCursor()
   {
      CursorUtils.getOptFloat( null, INDEX, 1.0f );
   }
   
   
   
   @Test
   public void testGetOptInt()
   {
      Cursor c = EasyMock.createStrictMock( Cursor.class );
      EasyMock.expect( c.isNull( INDEX ) ).andReturn( false );
      EasyMock.expect( c.getInt( INDEX ) ).andReturn( 1000 );
      EasyMock.replay( c );
      
      assertThat( CursorUtils.getOptInt( c, INDEX, 1 ), is( 1000 ) );
      
      EasyMock.verify( c );
      
      c = EasyMock.createStrictMock( Cursor.class );
      EasyMock.expect( c.isNull( INDEX ) ).andReturn( true );
      EasyMock.replay( c );
      
      assertThat( CursorUtils.getOptInt( c, INDEX, 1 ), is( 1 ) );
      
      EasyMock.verify( c );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testGetOptIntNullCursor()
   {
      CursorUtils.getOptInt( null, INDEX, 1 );
   }
   
   
   
   @Test
   public void testGetOptLongCursorIntLong()
   {
      Cursor c = EasyMock.createStrictMock( Cursor.class );
      EasyMock.expect( c.isNull( INDEX ) ).andReturn( false );
      EasyMock.expect( c.getLong( INDEX ) ).andReturn( 1000L );
      EasyMock.replay( c );
      
      assertThat( CursorUtils.getOptLong( c, INDEX, 1L ), is( 1000L ) );
      
      EasyMock.verify( c );
      
      c = EasyMock.createStrictMock( Cursor.class );
      EasyMock.expect( c.isNull( INDEX ) ).andReturn( true );
      EasyMock.replay( c );
      
      assertThat( CursorUtils.getOptLong( c, INDEX, 1L ), is( 1L ) );
      
      EasyMock.verify( c );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testGetOptLongCursorIntLongNullCursor()
   {
      CursorUtils.getOptLong( null, INDEX, 1 );
   }
   
   
   
   @Test
   public void testGetOptLongCursorInt()
   {
      Cursor c = EasyMock.createStrictMock( Cursor.class );
      EasyMock.expect( c.isNull( INDEX ) ).andReturn( false );
      EasyMock.expect( c.getLong( INDEX ) ).andReturn( 1000L );
      EasyMock.replay( c );
      
      assertThat( CursorUtils.getOptLong( c, INDEX ), is( 1000L ) );
      
      EasyMock.verify( c );
      
      c = EasyMock.createStrictMock( Cursor.class );
      EasyMock.expect( c.isNull( INDEX ) ).andReturn( true );
      EasyMock.replay( c );
      
      assertThat( CursorUtils.getOptLong( c, INDEX ), is( (Long) null ) );
      
      EasyMock.verify( c );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testGetOptLongCursorIntNullCursor()
   {
      CursorUtils.getOptLong( null, INDEX );
   }
   
   
   
   @Test
   public void testGetOptBool()
   {
      Cursor c = EasyMock.createStrictMock( Cursor.class );
      EasyMock.expect( c.isNull( INDEX ) ).andReturn( false );
      EasyMock.expect( c.getInt( INDEX ) ).andReturn( 0 );
      EasyMock.replay( c );
      
      assertThat( CursorUtils.getOptBool( c, INDEX, true ), is( false ) );
      
      EasyMock.verify( c );
      
      c = EasyMock.createStrictMock( Cursor.class );
      EasyMock.expect( c.isNull( INDEX ) ).andReturn( false );
      EasyMock.expect( c.getInt( INDEX ) ).andReturn( 1 );
      EasyMock.replay( c );
      
      assertThat( CursorUtils.getOptBool( c, INDEX, false ), is( true ) );
      
      EasyMock.verify( c );
      
      c = EasyMock.createStrictMock( Cursor.class );
      EasyMock.expect( c.isNull( INDEX ) ).andReturn( true );
      EasyMock.replay( c );
      
      assertThat( CursorUtils.getOptBool( c, INDEX, true ), is( true ) );
      
      EasyMock.verify( c );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testGetOptBoolNullCursor()
   {
      CursorUtils.getOptBool( null, INDEX, false );
   }
}
