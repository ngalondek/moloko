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

package dev.drsoran.moloko.test.unit.domain.parsing.recurrence;

import static dev.drsoran.moloko.test.IterableAsserts.assertEqualSet;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternCollector;
import dev.drsoran.moloko.test.sources.RecurrencePatternCollectorTestDataSource;


@RunWith( Parameterized.class )
public class RecurrencePatternCollectorFixture
{
   private final RecurrencePatternCollectorTestDataSource.TestData testData;
   
   
   
   public RecurrencePatternCollectorFixture(
      RecurrencePatternCollectorTestDataSource.TestData testData )
   {
      this.testData = testData;
   }
   
   
   
   @Parameters( name = "{0}" )
   public static Collection< Object[] > getRecurrencePatterns()
   {
      return new RecurrencePatternCollectorTestDataSource().getTestData();
   }
   
   
   
   @Test
   public void testCollectTokens()
   {
      final Map< Integer, List< Object >> tokens = RecurrencePatternCollector.collectTokens( testData.pattern );
      
      assertThat( tokens, notNullValue() );
      assertThat( "Wrong tokens count",
                  tokens.size(),
                  is( testData.expectedTokens.size() ) );
      
      assertSameTokens( tokens, testData.expectedTokens );
   }
   
   
   
   private void assertSameTokens( Map< Integer, List< Object > > tokens,
                                  Map< Integer, List< Object > > expected )
   {
      Iterator< Integer > tokensIter = tokens.keySet().iterator();
      Iterator< Integer > expectedIter = expected.keySet().iterator();
      
      while ( tokensIter.hasNext() )
      {
         final Integer token = tokensIter.next();
         final Integer expectedToken = expectedIter.next();
         
         assertThat( "Wrong token type", token, is( expectedToken ) );
         
         final List< Object > tokenValues = tokens.get( token );
         final List< Object > expectedTokenValues = expected.get( token );
         
         assertEqualSet( expectedTokenValues, tokenValues );
      }
   }
}
