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

import static dev.drsoran.moloko.test.IterableAsserts.assertCount;
import static dev.drsoran.moloko.test.IterableAsserts.assertEmpty;
import static dev.drsoran.moloko.test.TestConstants.NEVER;
import static dev.drsoran.moloko.test.TestConstants.NOW;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.domain.model.Modification;
import dev.drsoran.moloko.test.EqualsHashCodeTestCase;
import dev.drsoran.moloko.util.Strings;


public class ModificationFixture extends EqualsHashCodeTestCase
{
   
   @Test
   public void testGetEntityUri()
   {
      final String testUri = "http://www.google.de";
      assertEquals( Modification.newModification( testUri, "col", 1L )
                                .getEntityUri(), testUri );
   }
   
   
   
   @Test
   public void testGetColName()
   {
      assertThat( Modification.newModification( Strings.EMPTY_STRING, "col", 1L )
                              .getColName(),
                  is( "col" ) );
   }
   
   
   
   @Test
   public void testGetNewValue()
   {
      assertEquals( Modification.newModification( Strings.EMPTY_STRING,
                                                  "col",
                                                  1L ).getNewValue(),
                    "1" );
      assertNull( Modification.newModification( Strings.EMPTY_STRING,
                                                "col",
                                                null ).getNewValue() );
   }
   
   
   
   @Test
   public void testGetNewValueClassOfT()
   {
      assertThat( Modification.newModification( Strings.EMPTY_STRING, "col", 1L )
                              .getNewValue( Long.class ),
                  is( 1L ) );
      assertNull( Modification.newModification( Strings.EMPTY_STRING,
                                                "col",
                                                null ).getNewValue( Long.class ) );
   }
   
   
   
   @Test
   public void testGetSyncedValue()
   {
      assertEquals( Modification.newModification( Strings.EMPTY_STRING,
                                                  "col",
                                                  1L,
                                                  2L ).getSyncedValue(),
                    "2" );
      assertNull( Modification.newModification( Strings.EMPTY_STRING,
                                                "col",
                                                1L,
                                                null ).getSyncedValue() );
   }
   
   
   
   @Test
   public void testGetSyncedValueClassOfT()
   {
      assertThat( Modification.newModification( Strings.EMPTY_STRING,
                                                "col",
                                                1L,
                                                2L )
                              .getSyncedValue( Long.class ),
                  is( 2L ) );
      assertNull( Modification.newModification( Strings.EMPTY_STRING,
                                                "col",
                                                1L,
                                                null )
                              .getSyncedValue( Long.class ) );
   }
   
   
   
   @Test
   public void testIsSetSyncedValue()
   {
      assertTrue( Modification.newModification( Strings.EMPTY_STRING,
                                                "col",
                                                1L,
                                                2L ).isSetSyncedValue() );
      assertTrue( Modification.newModification( Strings.EMPTY_STRING,
                                                "col",
                                                1L,
                                                null ).isSetSyncedValue() );
      assertFalse( Modification.newModification( Strings.EMPTY_STRING,
                                                 "col",
                                                 1L ).isSetSyncedValue() );
      assertTrue( Modification.newModification( Strings.EMPTY_STRING,
                                                "col",
                                                1L,
                                                2L,
                                                NOW ).isPersistent() );
      assertTrue( Modification.newModification( Strings.EMPTY_STRING,
                                                "col",
                                                1L,
                                                null,
                                                NOW ).isPersistent() );
      assertFalse( Modification.newNonPersistentModification( Strings.EMPTY_STRING,
                                                              "col",
                                                              1L )
                               .isSetSyncedValue() );
   }
   
   
   
   @Test
   public void testIsPersistent()
   {
      assertTrue( Modification.newModification( Strings.EMPTY_STRING,
                                                "col",
                                                "1" ).isPersistent() );
      assertTrue( Modification.newModification( Strings.EMPTY_STRING,
                                                "col",
                                                "1",
                                                "2" ).isPersistent() );
      assertTrue( Modification.newModification( Strings.EMPTY_STRING,
                                                "col",
                                                "1",
                                                "2",
                                                NOW ).isPersistent() );
      assertFalse( Modification.newNonPersistentModification( Strings.EMPTY_STRING,
                                                              "col",
                                                              "1" )
                               .isPersistent() );
   }
   
   
   
   @Test
   public void testGetTimestamp()
   {
      assertThat( Modification.newModification( Strings.EMPTY_STRING,
                                                "col",
                                                "1" ).getTimestamp(),
                  not( is( Constants.NO_TIME ) ) );
      assertThat( Modification.newModification( Strings.EMPTY_STRING,
                                                "col",
                                                "1",
                                                null,
                                                NOW ).getTimestamp(),
                  is( NOW ) );
   }
   
   
   
   @Test
   public void testToString()
   {
      Modification.newModification( Strings.EMPTY_STRING, "col", "1" )
                  .toString();
   }
   
   
   
   @Test
   public void testNewModificationUriStringT()
   {
      Modification.newModification( Strings.EMPTY_STRING, "col", 1L );
      Modification.newModification( Strings.EMPTY_STRING, "col", null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNewModificationUriStringTNullUri()
   {
      Modification.newModification( null, "col", 1L );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNewModificationUriStringTNullColumn()
   {
      Modification.newModification( Strings.EMPTY_STRING, null, 1L );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNewModificationUriStringTEmptyColumn()
   {
      Modification.newModification( Strings.EMPTY_STRING, "", 1L );
   }
   
   
   
   @Test
   public void testNewModificationUriStringTT()
   {
      Modification.newModification( Strings.EMPTY_STRING, "col", 1L, 2L );
      Modification.newModification( Strings.EMPTY_STRING, "col", 1L, null );
      Modification.newModification( Strings.EMPTY_STRING, "col", null, 2L );
      Modification.newModification( Strings.EMPTY_STRING, "col", null, null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNewModificationUriStringTTNullUri()
   {
      Modification.newModification( null, "col", 1L, 2L );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNewModificationUriStringTTNullColumn()
   {
      Modification.newModification( Strings.EMPTY_STRING, null, 1L, 2L );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNewModificationUriStringTTEmptyColumn()
   {
      Modification.newModification( Strings.EMPTY_STRING, "", 1L, 2L );
   }
   
   
   
   @Test
   public void testNewModificationUriStringTTLong()
   {
      Modification.newModification( Strings.EMPTY_STRING, "col", 1L, 2L, NOW );
      Modification.newModification( Strings.EMPTY_STRING, "col", null, 2L, NOW );
      Modification.newModification( Strings.EMPTY_STRING, "col", 1L, null, NOW );
      Modification.newModification( Strings.EMPTY_STRING,
                                    "col",
                                    null,
                                    null,
                                    NOW );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNewModificationUriStringTTLongNullUri()
   {
      Modification.newModification( null, "col", 1L, 2L, NOW );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNewModificationUriStringTTLongNullColumn()
   {
      Modification.newModification( Strings.EMPTY_STRING, null, 1L, 2L, NOW );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNewModificationUriStringTTLongEmptyColumn()
   {
      Modification.newModification( Strings.EMPTY_STRING, "", 1L, 2L, NOW );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNewModificationUriStringTTLongNoTime()
   {
      Modification.newModification( Strings.EMPTY_STRING, "col", 1L, 2L, NEVER );
   }
   
   
   
   @Test
   public void testNewNonPersistentModification()
   {
      Modification.newNonPersistentModification( Strings.EMPTY_STRING,
                                                 "col",
                                                 1L );
      Modification.newNonPersistentModification( Strings.EMPTY_STRING,
                                                 "col",
                                                 null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNewNonPersistentModificationNullUri()
   {
      Modification.newNonPersistentModification( null, "col", 1L );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNewNonPersistentModificationNullColumn()
   {
      Modification.newNonPersistentModification( Strings.EMPTY_STRING, null, 1L );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testNewNonPersistentModificationEmptyColumn()
   {
      Modification.newNonPersistentModification( Strings.EMPTY_STRING, "", 1L );
   }
   
   
   
   @Test
   public void testAddIfDifferent()
   {
      final Collection< Modification > modifications = new ArrayList< Modification >();
      Modification.addIfDifferent( modifications,
                                   Strings.EMPTY_STRING,
                                   "col",
                                   1L,
                                   1L );
      assertEmpty( modifications );
      
      Modification.addIfDifferent( modifications,
                                   Strings.EMPTY_STRING,
                                   "col",
                                   null,
                                   null );
      assertEmpty( modifications );
      
      Modification.addIfDifferent( modifications,
                                   Strings.EMPTY_STRING,
                                   "col",
                                   1L,
                                   2L );
      assertCount( modifications, 1 );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testAddIfDifferentNullCollection()
   {
      Modification.addIfDifferent( null, Strings.EMPTY_STRING, "col", 1L, 2L );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testAddIfDifferentNullUri()
   {
      Modification.addIfDifferent( new ArrayList< Modification >(),
                                   null,
                                   "col",
                                   1L,
                                   2L );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testAddIfDifferentNullColumn()
   {
      Modification.addIfDifferent( new ArrayList< Modification >(),
                                   Strings.EMPTY_STRING,
                                   null,
                                   1L,
                                   2L );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testAddIfDifferentEmptyColumn()
   {
      Modification.addIfDifferent( new ArrayList< Modification >(),
                                   Strings.EMPTY_STRING,
                                   "",
                                   1L,
                                   2L );
   }
   
   
   
   @Test
   public void testAddIfDifferentNonPersistent()
   {
      final Collection< Modification > modifications = new ArrayList< Modification >();
      Modification.addIfDifferentNonPersistent( modifications,
                                                Strings.EMPTY_STRING,
                                                "col",
                                                1L,
                                                1L );
      assertEmpty( modifications );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                Strings.EMPTY_STRING,
                                                "col",
                                                null,
                                                null );
      assertEmpty( modifications );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                Strings.EMPTY_STRING,
                                                "col",
                                                1L,
                                                2L );
      assertCount( modifications, 1 );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testAddIfDifferentNonPersistentNullCollection()
   {
      Modification.addIfDifferentNonPersistent( null,
                                                Strings.EMPTY_STRING,
                                                "col",
                                                1L,
                                                2L );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testAddIfDifferentNonPersistentNullUri()
   {
      Modification.addIfDifferentNonPersistent( new ArrayList< Modification >(),
                                                null,
                                                "col",
                                                1L,
                                                2L );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testAddIfDifferentNonPersistentNullColumn()
   {
      Modification.addIfDifferentNonPersistent( new ArrayList< Modification >(),
                                                Strings.EMPTY_STRING,
                                                null,
                                                1L,
                                                2L );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testAddIfDifferentNonPersistentEmptyColumn()
   {
      Modification.addIfDifferentNonPersistent( new ArrayList< Modification >(),
                                                Strings.EMPTY_STRING,
                                                "",
                                                1L,
                                                2L );
   }
   
   
   
   @Override
   protected Object createEqualEqualsHashTestInstance() throws Exception
   {
      return Modification.newModification( Strings.EMPTY_STRING, "col", 1L );
   }
   
   
   
   @Override
   protected Object createNotEqualEqualsHashTestInstance() throws Exception
   {
      return Modification.newModification( Strings.EMPTY_STRING, "col", 2L );
   }
}
