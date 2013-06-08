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

package dev.drsoran.moloko.test.comp.content;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import dev.drsoran.moloko.content.ContentMimeTypes;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.test.MolokoRoboTestCase;
import dev.drsoran.moloko.test.PrivateCtorCaller;


public class ContentMimeTypesTest extends MolokoRoboTestCase
{
   
   @Test( expected = AssertionError.class )
   public void testPrivateCtor() throws Throwable
   {
      PrivateCtorCaller.callPrivateCtor( ContentMimeTypes.class );
   }
   
   
   
   @Test
   public void testContentMimeTypesLookup()
   {
      assertThat( ContentMimeTypes.CONTENT_URI_MIME_TYPE_LOOKUP.get( ContentUris.TASKS_LISTS_CONTENT_URI ),
                  is( ContentMimeTypes.TASKS_LIST_CONTENT_TYPE ) );
      
      assertThat( ContentMimeTypes.CONTENT_URI_MIME_TYPE_LOOKUP.get( ContentUris.bindElementId( ContentUris.TASKS_LISTS_CONTENT_URI_ID,
                                                                                                1L ) ),
                  is( ContentMimeTypes.TASKS_LIST_CONTENT_ITEM_TYPE ) );
      
      assertThat( ContentMimeTypes.CONTENT_URI_MIME_TYPE_LOOKUP.get( ContentUris.TASKS_CONTENT_URI ),
                  is( ContentMimeTypes.TASK_CONTENT_TYPE ) );
      
      assertThat( ContentMimeTypes.CONTENT_URI_MIME_TYPE_LOOKUP.get( ContentUris.bindElementId( ContentUris.TASKS_CONTENT_URI_ID,
                                                                                                1L ) ),
                  is( ContentMimeTypes.TASK_CONTENT_ITEM_TYPE ) );
      
      assertThat( ContentMimeTypes.CONTENT_URI_MIME_TYPE_LOOKUP.get( ContentUris.bindAggregationIdToUri( ContentUris.TASK_NOTES_CONTENT_URI,
                                                                                                         1L ) ),
                  is( ContentMimeTypes.TASK_NOTE_CONTENT_TYPE ) );
      
      assertThat( ContentMimeTypes.CONTENT_URI_MIME_TYPE_LOOKUP.get( ContentUris.bindAggregatedElementIdToUri( ContentUris.TASK_NOTES_CONTENT_URI_ID,
                                                                                                               1L,
                                                                                                               2L ) ),
                  is( ContentMimeTypes.TASK_NOTE_CONTENT_ITEM_TYPE ) );
      
      assertThat( ContentMimeTypes.CONTENT_URI_MIME_TYPE_LOOKUP.get( ContentUris.bindAggregationIdToUri( ContentUris.TASK_PARTICIPANTS_CONTENT_URI,
                                                                                                         1L ) ),
                  is( ContentMimeTypes.TASK_PARTICIPANT_CONTENT_TYPE ) );
      
      assertThat( ContentMimeTypes.CONTENT_URI_MIME_TYPE_LOOKUP.get( ContentUris.bindAggregatedElementIdToUri( ContentUris.TASK_PARTICIPANTS_CONTENT_URI_ID,
                                                                                                               1L,
                                                                                                               2L ) ),
                  is( ContentMimeTypes.TASK_PARTICIPANT_CONTENT_ITEM_TYPE ) );
      
      assertThat( ContentMimeTypes.CONTENT_URI_MIME_TYPE_LOOKUP.get( ContentUris.LOCATIONS_CONTENT_URI ),
                  is( ContentMimeTypes.LOCATION_CONTENT_TYPE ) );
      
      assertThat( ContentMimeTypes.CONTENT_URI_MIME_TYPE_LOOKUP.get( ContentUris.bindElementId( ContentUris.LOCATIONS_CONTENT_URI_ID,
                                                                                                1L ) ),
                  is( ContentMimeTypes.LOCATION_CONTENT_ITEM_TYPE ) );
      
      assertThat( ContentMimeTypes.CONTENT_URI_MIME_TYPE_LOOKUP.get( ContentUris.CONTACTS_CONTENT_URI ),
                  is( ContentMimeTypes.CONTACT_CONTENT_TYPE ) );
      
      assertThat( ContentMimeTypes.CONTENT_URI_MIME_TYPE_LOOKUP.get( ContentUris.bindElementId( ContentUris.CONTACTS_CONTENT_URI_ID,
                                                                                                1L ) ),
                  is( ContentMimeTypes.CONTACT_CONTENT_ITEM_TYPE ) );
      
      assertThat( ContentMimeTypes.CONTENT_URI_MIME_TYPE_LOOKUP.get( ContentUris.TAGS_CONTENT_URI ),
                  is( ContentMimeTypes.TAGS_CONTENT_TYPE ) );
   }
}
