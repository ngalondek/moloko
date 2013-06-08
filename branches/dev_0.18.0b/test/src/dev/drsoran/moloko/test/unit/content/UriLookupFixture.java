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

import java.util.NoSuchElementException;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.annotation.Config;

import android.content.UriMatcher;
import android.net.Uri;
import dev.drsoran.moloko.content.UriLookup;
import dev.drsoran.moloko.test.MolokoRoboTestCase;


@Config( manifest = Config.NONE )
public class UriLookupFixture extends MolokoRoboTestCase
{
   private final static int MATCH_TYPE_URI_1 = 1;
   
   private final static int MATCH_TYPE_URI_2 = 2;
   
   private Uri testUri1;
   
   private Uri testUri2;
   
   private Uri wrongUri;
   
   private UriMatcher testUriMatcher;
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      
      testUri1 = Uri.parse( "www.google.de" );
      testUri2 = Uri.parse( "www.google.de/path" );
      wrongUri = Uri.parse( "www.abc.de" );
      
      testUriMatcher = EasyMock.createNiceMock( UriMatcher.class );
      EasyMock.expect( testUriMatcher.match( testUri1 ) )
              .andReturn( MATCH_TYPE_URI_1 )
              .anyTimes();
      EasyMock.expect( testUriMatcher.match( testUri2 ) )
              .andReturn( MATCH_TYPE_URI_2 )
              .anyTimes();
      EasyMock.expect( testUriMatcher.match( wrongUri ) )
              .andReturn( UriMatcher.NO_MATCH )
              .anyTimes();
      EasyMock.replay( testUriMatcher );
   }
   
   
   
   @Test
   public void testUriLookup()
   {
      new UriLookup< String >( testUriMatcher );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testUriLookupNullMatcher()
   {
      new UriLookup< String >( null );
   }
   
   
   
   @Test
   public void testPut()
   {
      UriLookup< String > lookup = new UriLookup< String >( testUriMatcher );
      lookup.put( "uri1", MATCH_TYPE_URI_1 );
      lookup.put( "uri2", MATCH_TYPE_URI_2 );
      
      assertThat( lookup.size(), is( 2 ) );
   }
   
   
   
   @Test
   public void testPutSameTwice()
   {
      UriLookup< String > lookup = new UriLookup< String >( testUriMatcher );
      lookup.put( "uri1", MATCH_TYPE_URI_1 );
      lookup.put( "uri1", MATCH_TYPE_URI_1 );
      
      assertThat( lookup.size(), is( 1 ) );
   }
   
   
   
   @Test
   public void testPutSameObjectDifferentMatchType()
   {
      final String uri1Str = "uri1";
      
      UriLookup< String > lookup = new UriLookup< String >( testUriMatcher );
      lookup.put( uri1Str, MATCH_TYPE_URI_1 );
      lookup.put( uri1Str, MATCH_TYPE_URI_2 );
      
      assertThat( lookup.size(), is( 2 ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testPutUnmatched()
   {
      UriLookup< Integer > lookup = new UriLookup< Integer >( testUriMatcher );
      lookup.put( 1, UriMatcher.NO_MATCH );
   }
   
   
   
   public void testGet()
   {
      UriLookup< String > lookup = new UriLookup< String >( testUriMatcher );
      lookup.put( "uri1", MATCH_TYPE_URI_1 );
      lookup.put( "uri2", MATCH_TYPE_URI_2 );
      
      assertThat( lookup.get( testUri1 ), is( "uri1" ) );
      assertThat( lookup.get( testUri2 ), is( "uri2" ) );
   }
   
   
   
   @Test
   public void testGetSameObjectDifferentMatchType()
   {
      final String uri1Str = "uri1";
      
      UriLookup< String > lookup = new UriLookup< String >( testUriMatcher );
      lookup.put( uri1Str, MATCH_TYPE_URI_1 );
      lookup.put( uri1Str, MATCH_TYPE_URI_2 );
      
      assertThat( lookup.get( testUri1 ), is( uri1Str ) );
      assertThat( lookup.get( testUri2 ), is( uri1Str ) );
   }
   
   
   
   @Test( expected = NoSuchElementException.class )
   public void testGetUnmatched()
   {
      UriLookup< Integer > lookup = new UriLookup< Integer >( testUriMatcher );
      lookup.get( wrongUri );
   }
   
   
   
   @Test( expected = NoSuchElementException.class )
   public void testGetNotFound()
   {
      UriLookup< Integer > lookup = new UriLookup< Integer >( testUriMatcher );
      lookup.get( testUri1 );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testGetNullUri()
   {
      UriLookup< Integer > lookup = new UriLookup< Integer >( testUriMatcher );
      lookup.get( null );
   }
   
   
   
   @Test
   public void testCanLookUp()
   {
      UriLookup< String > lookup = new UriLookup< String >( testUriMatcher );
      lookup.put( "uri1", MATCH_TYPE_URI_1 );
      lookup.put( "uri2", MATCH_TYPE_URI_2 );
      
      assertThat( lookup.canLookUp( testUri1 ), is( true ) );
      assertThat( lookup.canLookUp( testUri2 ), is( true ) );
      assertThat( lookup.canLookUp( wrongUri ), is( false ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testCanLookUpNullUri()
   {
      UriLookup< String > lookup = new UriLookup< String >( testUriMatcher );
      lookup.canLookUp( null );
   }
   
   
   
   @Test
   public void testContains()
   {
      UriLookup< String > lookup = new UriLookup< String >( testUriMatcher );
      lookup.put( "uri1", MATCH_TYPE_URI_1 );
      lookup.put( "uri2", MATCH_TYPE_URI_2 );
      
      assertThat( lookup.contains( "uri1" ), is( true ) );
      assertThat( lookup.contains( "uri2" ), is( true ) );
      assertThat( lookup.contains( "uri3" ), is( false ) );
   }
   
   
   
   @Test
   public void testRemove()
   {
      UriLookup< String > lookup = new UriLookup< String >( testUriMatcher );
      lookup.put( "uri1", MATCH_TYPE_URI_1 );
      lookup.put( "uri2", MATCH_TYPE_URI_2 );
      
      lookup.remove( testUri1 );
      assertThat( lookup.size(), is( 1 ) );
      
      lookup.remove( testUri1 );
      assertThat( lookup.size(), is( 1 ) );
      
      lookup.remove( testUri2 );
      assertThat( lookup.size(), is( 0 ) );
   }
   
   
   
   @Test( expected = NoSuchElementException.class )
   public void testRemoveUnmatched()
   {
      UriLookup< Integer > lookup = new UriLookup< Integer >( testUriMatcher );
      lookup.remove( wrongUri );
   }
   
   
   
   @Test
   public void testRemoveNotFound()
   {
      UriLookup< Integer > lookup = new UriLookup< Integer >( testUriMatcher );
      lookup.remove( testUri1 );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRemoveNullUri()
   {
      UriLookup< Integer > lookup = new UriLookup< Integer >( testUriMatcher );
      lookup.remove( null );
   }
   
   
   
   @Test
   public void testRemoveAll()
   {
      UriLookup< String > lookup = new UriLookup< String >( testUriMatcher );
      lookup.put( "uri1", MATCH_TYPE_URI_1 );
      lookup.put( "uri1", MATCH_TYPE_URI_2 );
      
      lookup.removeAll();
      assertThat( lookup.size(), is( 0 ) );
   }
   
   
   
   @Test
   public void testSize()
   {
      UriLookup< Integer > lookup = new UriLookup< Integer >( testUriMatcher );
      assertThat( lookup.size(), is( 0 ) );
   }
}
