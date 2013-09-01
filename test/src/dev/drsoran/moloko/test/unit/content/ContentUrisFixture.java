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

import static dev.drsoran.moloko.test.IterableAsserts.assertEmpty;
import static dev.drsoran.moloko.test.IterableAsserts.assertEqualSet;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.robolectric.annotation.Config;

import android.net.Uri;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.test.MolokoRoboTestCase;
import dev.drsoran.moloko.test.PrivateCtorCaller;


@Config( manifest = Config.NONE )
public class ContentUrisFixture extends MolokoRoboTestCase
{
   @Test( expected = AssertionError.class )
   public void testPrivateCtor() throws Throwable
   {
      PrivateCtorCaller.callPrivateCtor( ContentUris.class );
   }
   
   
   
   @Test
   public void testGetIdsFromUri()
   {
      List< Long > ids = ContentUris.getIdsFromUri( Uri.parse( "http://www.google.de" ) );
      assertNotNull( ids );
      assertEmpty( ids );
      
      ids = ContentUris.getIdsFromUri( Uri.parse( "http://www.google.de/129/abc/30/de" ) );
      assertEqualSet( ids, 129L, 30L );
      
      ids = ContentUris.getIdsFromUri( Uri.parse( "http://www.google.de/129/abc/#/de/10" ) );
      assertEqualSet( ids, 129L );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testGetIdsFromUriNullUri()
   {
      ContentUris.getIdsFromUri( null );
   }
   
   
   
   @Test
   public void testGetTaskIdFromUri()
   {
      long id = ContentUris.getTaskIdFromUri( Uri.parse( "http://www.google.de" ) );
      assertThat( id, is( Constants.NO_ID ) );
      
      id = ContentUris.getTaskIdFromUri( Uri.parse( "http://www.google.de/123/" ) );
      assertThat( id, is( 123L ) );
      
      id = ContentUris.getTaskIdFromUri( Uri.parse( "http://www.google.de/123/de/45" ) );
      assertThat( id, is( 123L ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testGetTaskIdFromUriNullUri()
   {
      ContentUris.getTaskIdFromUri( null );
   }
   
   
   
   @Test
   public void testBindAggregationIdToUri()
   {
      Uri uri = ContentUris.bindAggregationIdToUri( Uri.parse( "http://www.google.de/#/de/" ),
                                                    1L );
      assertThat( uri.toString(), is( "http://www.google.de/1/de/" ) );
      
      uri = ContentUris.bindAggregationIdToUri( Uri.parse( "http://www.google.de/#/de/#" ),
                                                10L );
      assertThat( uri.toString(), is( "http://www.google.de/10/de/#" ) );
      
      uri = ContentUris.bindAggregationIdToUri( Uri.parse( "http://www.google.de/" ),
                                                100L );
      assertThat( uri.toString(), is( "http://www.google.de/" ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testBindAggregationIdToUriNullUri()
   {
      ContentUris.bindAggregationIdToUri( null, 1L );
   }
   
   
   
   @Test
   public void testBindAggregatedElementIdToUri()
   {
      Uri uri = ContentUris.bindAggregatedElementIdToUri( Uri.parse( "http://www.google.de/#/de/#" ),
                                                          1L,
                                                          2L );
      assertThat( uri.toString(), is( "http://www.google.de/1/de/2" ) );
      
      uri = ContentUris.bindAggregatedElementIdToUri( Uri.parse( "http://www.google.de/#/de/#" ),
                                                      10L,
                                                      2L );
      assertThat( uri.toString(), is( "http://www.google.de/10/de/2" ) );
      
      uri = ContentUris.bindAggregatedElementIdToUri( Uri.parse( "http://www.google.de/#/de/#" ),
                                                      1L,
                                                      20L );
      assertThat( uri.toString(), is( "http://www.google.de/1/de/20" ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testBindAggregatedElementIdToUriNoRootPlaceholder()
   {
      ContentUris.bindAggregatedElementIdToUri( Uri.parse( "http://www.google.de" ),
                                                1L,
                                                2L );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testBindAggregatedElementIdToUriNElementPlaceholder()
   {
      ContentUris.bindAggregatedElementIdToUri( Uri.parse( "http://www.google.de/#/de" ),
                                                1L,
                                                2L );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testBindAggregatedElementIdToUriNullUri()
   {
      ContentUris.bindAggregatedElementIdToUri( null, 1L, 2L );
   }
   
   
   
   @Test
   public void testGetLastPathIdFromUri()
   {
      long id = ContentUris.getLastPathIdFromUri( Uri.parse( "http://www.google.de/123" ) );
      assertThat( id, is( 123L ) );
      
      id = ContentUris.getLastPathIdFromUri( Uri.parse( "http://www.google.de/" ) );
      assertThat( id, is( Constants.NO_ID ) );
      
      id = ContentUris.getLastPathIdFromUri( Uri.parse( "http://www.google.de/123/de" ) );
      assertThat( id, is( Constants.NO_ID ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testGetLastPathIdFromUriNullUri()
   {
      ContentUris.getLastPathIdFromUri( null );
   }
   
   
   
   @Test
   public void testBindElementId()
   {
      Uri uri = ContentUris.bindElementId( Uri.parse( "http://www.google.de/#" ),
                                           1L );
      assertThat( uri.toString(), is( "http://www.google.de/1" ) );
      
      uri = ContentUris.bindElementId( Uri.parse( "http://www.google.de/#/de/#" ),
                                       10L );
      assertThat( uri.toString(), is( "http://www.google.de/#/de/10" ) );
      
      uri = ContentUris.bindElementId( Uri.parse( "http://www.google.de/de/#" ),
                                       100L );
      assertThat( uri.toString(), is( "http://www.google.de/de/100" ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testBindElementIdNullUriNoElementPlaceholder()
   {
      ContentUris.bindElementId( Uri.parse( "http://www.google.de" ), 1L );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testBindElementIdNullUri()
   {
      ContentUris.bindElementId( null, 1L );
   }
}
