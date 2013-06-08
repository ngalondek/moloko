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
import org.robolectric.annotation.Config;

import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.test.MolokoRoboTestCase;


@Config( manifest = Config.NONE )
public class ContentUrisTest extends MolokoRoboTestCase
{
   @Test
   public void testUriMatcher()
   {
      assertThat( ContentUris.MATCHER.match( ContentUris.TASKS_LISTS_CONTENT_URI ),
                  is( ContentUris.MATCH_TASKS_LISTS ) );
      
      assertThat( ContentUris.MATCHER.match( ContentUris.bindElementId( ContentUris.TASKS_LISTS_CONTENT_URI_ID,
                                                                        1L ) ),
                  is( ContentUris.MATCH_TASKS_LISTS_ID ) );
      
      assertThat( ContentUris.MATCHER.match( ContentUris.TASKS_CONTENT_URI ),
                  is( ContentUris.MATCH_TASKS ) );
      
      assertThat( ContentUris.MATCHER.match( ContentUris.bindElementId( ContentUris.TASKS_CONTENT_URI_ID,
                                                                        1L ) ),
                  is( ContentUris.MATCH_TASKS_ID ) );
      
      assertThat( ContentUris.MATCHER.match( ContentUris.TASKS_COUNT_CONTENT_URI ),
                  is( ContentUris.MATCH_TASKS_COUNT ) );
      
      assertThat( ContentUris.MATCHER.match( ContentUris.bindAggregationIdToUri( ContentUris.TASK_NOTES_CONTENT_URI,
                                                                                 1L ) ),
                  is( ContentUris.MATCH_TASK_NOTES ) );
      
      assertThat( ContentUris.MATCHER.match( ContentUris.bindAggregatedElementIdToUri( ContentUris.TASK_NOTES_CONTENT_URI_ID,
                                                                                       1L,
                                                                                       2L ) ),
                  is( ContentUris.MATCH_TASK_NOTES_ID ) );
      
      assertThat( ContentUris.MATCHER.match( ContentUris.bindAggregationIdToUri( ContentUris.TASK_PARTICIPANTS_CONTENT_URI,
                                                                                 1L ) ),
                  is( ContentUris.MATCH_TASK_PARTICIPANTS ) );
      
      assertThat( ContentUris.MATCHER.match( ContentUris.bindAggregatedElementIdToUri( ContentUris.TASK_PARTICIPANTS_CONTENT_URI_ID,
                                                                                       1L,
                                                                                       2L ) ),
                  is( ContentUris.MATCH_TASK_PARTICIPANTS_ID ) );
      
      assertThat( ContentUris.MATCHER.match( ContentUris.LOCATIONS_CONTENT_URI ),
                  is( ContentUris.MATCH_LOCATIONS ) );
      
      assertThat( ContentUris.MATCHER.match( ContentUris.bindElementId( ContentUris.LOCATIONS_CONTENT_URI_ID,
                                                                        1L ) ),
                  is( ContentUris.MATCH_LOCATIONS_ID ) );
      
      assertThat( ContentUris.MATCHER.match( ContentUris.CONTACTS_CONTENT_URI ),
                  is( ContentUris.MATCH_CONTACTS ) );
      
      assertThat( ContentUris.MATCHER.match( ContentUris.bindElementId( ContentUris.CONTACTS_CONTENT_URI_ID,
                                                                        1L ) ),
                  is( ContentUris.MATCH_CONTACTS_ID ) );
      
      assertThat( ContentUris.MATCHER.match( ContentUris.TAGS_CONTENT_URI ),
                  is( ContentUris.MATCH_TAGS ) );
      
      assertThat( ContentUris.MATCHER.match( ContentUris.MODIFICATIONS_CONTENT_URI ),
                  is( ContentUris.MATCH_MODIFICATIONS ) );
      
      assertThat( ContentUris.MATCHER.match( ContentUris.bindElementId( ContentUris.MODIFICATIONS_CONTENT_URI_ID,
                                                                        1L ) ),
                  is( ContentUris.MATCH_MODIFICATIONS_ID ) );
      
      assertThat( ContentUris.MATCHER.match( ContentUris.RTM_SETTINGS_CONTENT_URI ),
                  is( ContentUris.MATCH_RTM_SETTINGS ) );
      
      assertThat( ContentUris.MATCHER.match( ContentUris.bindElementId( ContentUris.RTM_SETTINGS_CONTENT_URI_ID,
                                                                        1L ) ),
                  is( ContentUris.MATCH_RTM_SETTINGS_ID ) );
      
      assertThat( ContentUris.MATCHER.match( ContentUris.SYNC_CONTENT_URI ),
                  is( ContentUris.MATCH_SYNC ) );
      
      assertThat( ContentUris.MATCHER.match( ContentUris.bindElementId( ContentUris.SYNC_CONTENT_URI_ID,
                                                                        1L ) ),
                  is( ContentUris.MATCH_SYNC_ID ) );
   }
}
