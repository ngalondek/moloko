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

package dev.drsoran.rtm.test.testdatasources;

import java.util.Collection;
import java.util.LinkedList;

import dev.drsoran.rtm.test.TestConstants;


public class TimeParserTestDataSource
         extends
         MultiTheoriesTestDataSource< TimeParserTestDataSource.ParseTimeTestData, TimeParserTestDataSource.ParseTimeEstimateTestData >
{
   private final ITimeParserTestLanguage testLanguage;
   
   private final Config config;
   
   
   
   public TimeParserTestDataSource( ITimeParserTestLanguage testLanguage )
   {
      this( testLanguage, Config.Full );
   }
   
   
   
   public TimeParserTestDataSource( ITimeParserTestLanguage testLanguage,
      Config config )
   {
      this.testLanguage = testLanguage;
      this.config = config;
   }
   
   
   
   @Override
   public Collection< Object[] > getTestData()
   {
      final Collection< Object[] > testData = new LinkedList< Object[] >();
      
      addParseTime_literal( testData );
      
      if ( config != Config.Minimal )
      {
         addParseTime_separatedTimeHM( testData );
      }
      
      addParseTime_separatedTimeHMS( testData );
      
      if ( config != Config.Minimal )
      {
         addParseTime_unseparatedH2( testData );
         addParseTime_unseparatedHM3( testData );
      }
      
      addParseTime_unseparatedHM4( testData );
      addParseTime_hmsSeparated_H( testData );
      
      if ( config != Config.Minimal )
      {
         addParseTime_hmsSeparated_M( testData );
         addParseTime_hmsSeparated_S( testData );
         addParseTime_hmsSeparated_DecH( testData );
         addParseTime_hmsSeparated_Mixed( testData );
      }
      
      return testData;
   }
   
   
   
   @Override
   public Class< ParseTimeTestData > getTestDataClass()
   {
      return ParseTimeTestData.class;
   }
   
   
   
   public Collection< Object[] > getParseTimeEstimateTestData()
   {
      final Collection< Object[] > testData = new LinkedList< Object[] >();
      
      addParseTimeEstimate_Mixed( testData );
      
      return testData;
   }
   
   
   
   @Override
   public Collection< Object[] > getSecondTestData()
   {
      return getParseTimeEstimateTestData();
   }
   
   
   
   @Override
   public Class< ParseTimeEstimateTestData > getSecondTestDataClass()
   {
      return ParseTimeEstimateTestData.class;
   }
   
   
   
   private void addParseTime_literal( Collection< Object[] > testData )
   {
      for ( String at : testLanguage.getAts() )
      {
         for ( String string : testLanguage.getMidday() )
         {
            addTestData( testData,
                         new ParseTimeTestData( at + string, 12, 0, 0 ) );
         }
         
         for ( String string : testLanguage.getMidnight() )
         {
            addTestData( testData, new ParseTimeTestData( at + string,
                                                          23,
                                                          59,
                                                          59 ) );
         }
         
         if ( config != Config.Minimal )
         {
            for ( String string : testLanguage.getNever() )
            {
               addTestData( testData, new ParseTimeTestData( at + string,
                                                             0,
                                                             0,
                                                             0,
                                                             false ) );
            }
         }
      }
   }
   
   
   
   private void addParseTime_separatedTimeHM( Collection< Object[] > testData )
   {
      for ( String at : testLanguage.getAts() )
      {
         for ( String timeSep : testLanguage.getTimeSeparators() )
         {
            String testString = at + "12" + timeSep + "13";
            
            if ( config != Config.DateTimeParserTimeTests )
            {
               addTestData( testData, new ParseTimeTestData( testString,
                                                             12,
                                                             13,
                                                             0 ) );
            }
            
            for ( String am : testLanguage.getAm() )
            {
               testString = at + "12" + timeSep + "13" + am;
               
               addTestData( testData, new ParseTimeTestData( testString,
                                                             0,
                                                             13,
                                                             0 ) );
            }
            
            for ( String pm : testLanguage.getPm() )
            {
               testString = at + "12" + timeSep + "13" + pm;
               
               addTestData( testData, new ParseTimeTestData( testString,
                                                             12,
                                                             13,
                                                             0 ) );
            }
         }
      }
   }
   
   
   
   private void addParseTime_separatedTimeHMS( Collection< Object[] > testData )
   {
      for ( String at : testLanguage.getAts() )
      {
         for ( String hmSep : testLanguage.getTimeSeparators() )
         {
            for ( String msSep : testLanguage.getTimeSeparators() )
            {
               String testString = at + "12" + hmSep + "13" + msSep + "25";
               
               if ( config != Config.DateTimeParserTimeTests )
               {
                  addTestData( testData, new ParseTimeTestData( testString,
                                                                12,
                                                                13,
                                                                25 ) );
               }
               
               for ( String am : testLanguage.getAm() )
               {
                  testString = at + "12" + hmSep + "13" + msSep + "25" + am;
                  
                  addTestData( testData, new ParseTimeTestData( testString,
                                                                0,
                                                                13,
                                                                25 ) );
               }
               
               for ( String pm : testLanguage.getPm() )
               {
                  testString = at + "12" + hmSep + "13" + msSep + "25" + pm;
                  
                  addTestData( testData, new ParseTimeTestData( testString,
                                                                12,
                                                                13,
                                                                25 ) );
               }
            }
         }
      }
   }
   
   
   
   private void addParseTime_unseparatedH2( Collection< Object[] > testData )
   {
      for ( String at : testLanguage.getAts() )
      {
         String testString = at + "13";
         
         if ( config != Config.DateTimeParserTimeTests )
         {
            addTestData( testData, new ParseTimeTestData( testString, 13, 0, 0 ) );
         }
         
         for ( String am : testLanguage.getAm() )
         {
            testString = at + "13" + am;
            
            addTestData( testData, new ParseTimeTestData( testString, 1, 0, 0 ) );
         }
         
         for ( String pm : testLanguage.getPm() )
         {
            testString = at + "13" + pm;
            
            addTestData( testData, new ParseTimeTestData( testString, 13, 0, 0 ) );
         }
      }
   }
   
   
   
   private void addParseTime_unseparatedHM3( Collection< Object[] > testData )
   {
      for ( String at : testLanguage.getAts() )
      {
         String testString = at + "110";
         
         if ( config != Config.DateTimeParserTimeTests )
         {
            addTestData( testData, new ParseTimeTestData( testString, 1, 10, 0 ) );
         }
         
         for ( String am : testLanguage.getAm() )
         {
            testString = at + "110" + am;
            
            addTestData( testData, new ParseTimeTestData( testString, 1, 10, 0 ) );
         }
         
         for ( String pm : testLanguage.getPm() )
         {
            testString = at + "110" + pm;
            
            addTestData( testData,
                         new ParseTimeTestData( testString, 13, 10, 0 ) );
         }
      }
   }
   
   
   
   private void addParseTime_unseparatedHM4( Collection< Object[] > testData )
   {
      for ( String at : testLanguage.getAts() )
      {
         String testString = at + "1210";
         
         if ( config != Config.DateTimeParserTimeTests )
         {
            addTestData( testData,
                         new ParseTimeTestData( testString, 12, 10, 0 ) );
         }
         
         for ( String am : testLanguage.getAm() )
         {
            testString = at + "1210" + am;
            
            addTestData( testData, new ParseTimeTestData( testString, 0, 10, 0 ) );
         }
         
         for ( String pm : testLanguage.getPm() )
         {
            testString = at + "1210" + pm;
            
            addTestData( testData,
                         new ParseTimeTestData( testString, 12, 10, 0 ) );
         }
      }
   }
   
   
   
   private void addParseTime_hmsSeparated_H( Collection< Object[] > testData )
   {
      for ( String at : testLanguage.getAts() )
      {
         for ( String hour : testLanguage.getHoursLiteral() )
         {
            final String testString = at + "1 " + hour;
            addTestData( testData, new ParseTimeTestData( testString, 1, 0, 0 ) );
         }
      }
   }
   
   
   
   private void addParseTime_hmsSeparated_M( Collection< Object[] > testData )
   {
      for ( String at : testLanguage.getAts() )
      {
         for ( String minute : testLanguage.getMinutesLiteral() )
         {
            String testString = at + "10 " + minute;
            addTestData( testData, new ParseTimeTestData( testString, 0, 10, 0 ) );
            
            testString = at + "61 " + minute;
            addTestData( testData, new ParseTimeTestData( testString, 1, 1, 0 ) );
         }
      }
   }
   
   
   
   private void addParseTime_hmsSeparated_S( Collection< Object[] > testData )
   {
      for ( String at : testLanguage.getAts() )
      {
         for ( String second : testLanguage.getSecondsLiteral() )
         {
            String testString = at + "50 " + second;
            addTestData( testData, new ParseTimeTestData( testString, 0, 0, 50 ) );
            
            testString = at + "61 " + second;
            addTestData( testData, new ParseTimeTestData( testString, 0, 1, 1 ) );
         }
      }
   }
   
   
   
   private void addParseTime_hmsSeparated_DecH( Collection< Object[] > testData )
   {
      for ( String at : testLanguage.getAts() )
      {
         for ( String dec : testLanguage.getDecimalSigns() )
         {
            for ( String hour : testLanguage.getHoursLiteral() )
            {
               final String testString = at + "1" + dec + "5" + hour;
               addTestData( testData, new ParseTimeTestData( testString,
                                                             1,
                                                             30,
                                                             0 ) );
            }
         }
      }
   }
   
   
   
   private void addParseTime_hmsSeparated_Mixed( Collection< Object[] > testData )
   {
      for ( String at : testLanguage.getAts() )
      {
         final String h = testLanguage.getHoursLiteral().iterator().next();
         final String m = testLanguage.getMinutesLiteral().iterator().next();
         final String s = testLanguage.getSecondsLiteral().iterator().next();
         final String dec = testLanguage.getDecimalSigns().iterator().next();
         
         final String testString = at + "1 " + h + " 10 " + m + " 50" + s
            + " 60" + m + " 2" + dec + "1" + h;
         
         addTestData( testData, new ParseTimeTestData( testString, 4, 16, 50 ) );
      }
   }
   
   
   
   private void addParseTimeEstimate_Mixed( Collection< Object[] > testData )
   {
      final String h = testLanguage.getHoursLiteral().iterator().next();
      final String m = testLanguage.getMinutesLiteral().iterator().next();
      final String s = testLanguage.getSecondsLiteral().iterator().next();
      final String dec = testLanguage.getDecimalSigns().iterator().next();
      
      for ( String day : testLanguage.getDaysLiteral() )
      {
         for ( String concat : testLanguage.getTimeConcatenators() )
         {
            final String testString = "2" + day + concat + "1 " + h + " 10 "
               + m + concat + " 50" + s + " 60" + m + concat + " 2" + dec + "1"
               + h;
            
            addTestData( testData, new ParseTimeEstimateTestData( testString, 2
               * TestConstants.DAY_IN_MILLIS + 4 * TestConstants.HOUR_IN_MILLIS
               + 16 * TestConstants.MINUTE_IN_MILLIS + 50
               * TestConstants.SECOND_IN_MILLIS ) );
         }
      }
   }
   
   
   
   private void addTestData( Collection< Object[] > coll, Object testData )
   {
      coll.add( new Object[]
      { testData } );
   }
   
   
   public final static class ParseTimeTestData
   {
      public final String testString;
      
      public final int expectedHour;
      
      public final int expectedMinute;
      
      public final int expectedSecond;
      
      public final boolean expectedHasTime;
      
      
      
      public ParseTimeTestData( String testString, int expectedHour,
         int expectedMinute, int expectedSecond )
      {
         this( testString, expectedHour, expectedMinute, expectedSecond, true );
      }
      
      
      
      public ParseTimeTestData( String testString, int expectedHour,
         int expectedMinute, int expectedSecond, boolean expectedHasTime )
      {
         this.testString = testString;
         this.expectedHour = expectedHour;
         this.expectedMinute = expectedMinute;
         this.expectedSecond = expectedSecond;
         this.expectedHasTime = expectedHasTime;
      }
      
      
      
      @Override
      public String toString()
      {
         return testString;
      }
   }
   
   
   public enum Config
   {
      Full, DateTimeParserTimeTests, Minimal
   }
   
   
   public final static class ParseTimeEstimateTestData
   {
      public final String testString;
      
      public final long expectedMillis;
      
      
      
      public ParseTimeEstimateTestData( String testString, long expectedMillis )
      {
         this.testString = testString;
         this.expectedMillis = expectedMillis;
      }
      
      
      
      @Override
      public String toString()
      {
         return testString;
      }
   }
}
