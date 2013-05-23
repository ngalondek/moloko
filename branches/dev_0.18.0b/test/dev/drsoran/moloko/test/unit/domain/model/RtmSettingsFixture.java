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

package dev.drsoran.moloko.test.unit.domain.model;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.domain.model.RtmSettings;


@RunWith( Theories.class )
public class RtmSettingsFixture
{
   @DataPoint
   public final static long ID = 1L;
   
   @DataPoint
   public final static long NO_ID = Constants.NO_ID;
   
   @DataPoint
   public final static long NOW = System.currentTimeMillis();
   
   @DataPoint
   public final static long NEVER = Constants.NO_TIME;
   
   @DataPoint
   public final static String TEST_STRING = "testString";
   
   @DataPoint
   public final static String NULL_STRING = null;
   
   @DataPoint
   public final static String EMPTY_STRING = "";
   
   @DataPoint
   public final static int INTEGER_MIN_1 = -1;
   
   @DataPoint
   public final static int INTEGER_1 = 1;
   
   
   
   @Theory
   public void testRtmSettings( long sync,
                                String tz,
                                int df,
                                int tf,
                                long listId,
                                String lang )
   {
      new RtmSettings( sync, tz, df, tf, listId, lang );
   }
   
   
   
   @Theory
   public void testGetSyncTimeStampMillis( long sync,
                                           String tz,
                                           int df,
                                           int tf,
                                           long listId,
                                           String lang )
   {
      assertThat( new RtmSettings( sync, tz, df, tf, listId, lang ).getSyncTimeStampMillis(),
                  is( sync ) );
   }
   
   
   
   @Theory
   public void testGetTimezone( long sync,
                                String tz,
                                int df,
                                int tf,
                                long listId,
                                String lang )
   {
      assertThat( new RtmSettings( sync, tz, df, tf, listId, lang ).getTimezone(),
                  is( tz ) );
   }
   
   
   
   @Theory
   public void testGetDateFormat( long sync,
                                  String tz,
                                  int df,
                                  int tf,
                                  long listId,
                                  String lang )
   {
      assertThat( new RtmSettings( sync, tz, df, tf, listId, lang ).getDateFormat(),
                  is( df ) );
   }
   
   
   
   @Theory
   public void testGetTimeFormat( long sync,
                                  String tz,
                                  int df,
                                  int tf,
                                  long listId,
                                  String lang )
   {
      assertThat( new RtmSettings( sync, tz, df, tf, listId, lang ).getTimeFormat(),
                  is( tf ) );
   }
   
   
   
   @Theory
   public void testGetDefaultListId( long sync,
                                     String tz,
                                     int df,
                                     int tf,
                                     long listId,
                                     String lang )
   {
      assertThat( new RtmSettings( sync, tz, df, tf, listId, lang ).getDefaultListId(),
                  is( listId ) );
   }
   
   
   
   @Theory
   public void testGetLanguage( long sync,
                                String tz,
                                int df,
                                int tf,
                                long listId,
                                String lang )
   {
      assertThat( new RtmSettings( sync, tz, df, tf, listId, lang ).getLanguage(),
                  is( lang ) );
   }
   
   
   
   @Theory
   public void testToString( long sync,
                             String tz,
                             int df,
                             int tf,
                             long listId,
                             String lang )
   {
      new RtmSettings( sync, tz, df, tf, listId, lang ).toString();
   }
}
