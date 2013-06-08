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

package dev.drsoran.moloko.test.unit.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.easymock.EasyMock;
import org.junit.Test;
import org.robolectric.annotation.Config;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import dev.drsoran.moloko.test.MolokoRoboTestCase;
import dev.drsoran.moloko.test.PrivateCtorCaller;
import dev.drsoran.moloko.util.Bundles;


@Config( manifest = Config.NONE )
public class BundlesFixture extends MolokoRoboTestCase
{
   @Test( expected = AssertionError.class )
   public void testPrivateCtor() throws Throwable
   {
      PrivateCtorCaller.callPrivateCtor( Bundles.class );
   }
   
   
   
   @Test
   public void testPutBundleStringObjectClassOfQ()
   {
      final String key = "key";
      final Bundle bundle = new Bundle();
      
      Bundles.put( bundle, key, "string", String.class );
      assertThat( bundle.getString( key ), is( "string" ) );
      
      Bundles.put( bundle, key, 1, Integer.class );
      assertThat( bundle.getInt( key ), is( 1 ) );
      Bundles.put( bundle, key, 2, int.class );
      assertThat( bundle.getInt( key ), is( 2 ) );
      
      Bundles.put( bundle, key, true, Boolean.class );
      assertThat( bundle.getBoolean( key ), is( true ) );
      Bundles.put( bundle, key, false, boolean.class );
      assertThat( bundle.getBoolean( key ), is( false ) );
      
      final Bundle otherBundle = new Bundle();
      Bundles.put( bundle, key, otherBundle, Bundle.class );
      assertThat( bundle.getBundle( key ), is( otherBundle ) );
      
      final ArrayList< String > stringArrayList = new ArrayList< String >();
      Bundles.put( bundle, key, stringArrayList, ArrayList.class );
      assertThat( bundle.getStringArrayList( key ), is( stringArrayList ) );
      
      final ArrayList< Parcelable > parcArrayList = new ArrayList< Parcelable >();
      Bundles.put( bundle,
                   Bundles.KEY_QUALIFIER_PARCABLE_ARRAY_LIST + key,
                   parcArrayList,
                   ArrayList.class );
      assertThat( bundle.getParcelableArrayList( Bundles.KEY_QUALIFIER_PARCABLE_ARRAY_LIST
                     + key ),
                  is( parcArrayList ) );
      
      final SparseArray< Parcelable > parcableSparseArray = new SparseArray< Parcelable >();
      Bundles.put( bundle, key, parcableSparseArray, SparseArray.class );
      assertThat( bundle.getSparseParcelableArray( key ),
                  is( parcableSparseArray ) );
      
      Bundles.put( bundle, key, 1L, Long.class );
      assertThat( bundle.getLong( key ), is( 1L ) );
      Bundles.put( bundle, key, 2L, long.class );
      assertThat( bundle.getLong( key ), is( 2L ) );
      
      Bundles.put( bundle, key, 1.0f, Float.class );
      assertThat( bundle.getFloat( key ), is( 1.0f ) );
      Bundles.put( bundle, key, 2.0f, float.class );
      assertThat( bundle.getFloat( key ), is( 2.0f ) );
      
      final String[] strArray = new String[] {};
      Bundles.put( bundle, key, strArray, String[].class );
      assertThat( bundle.getStringArray( key ), is( strArray ) );
      
      final boolean[] boolArary = new boolean[] {};
      Bundles.put( bundle, key, boolArary, boolean[].class );
      assertThat( bundle.getBooleanArray( key ), is( boolArary ) );
      
      final Parcelable parcelable = EasyMock.createNiceMock( Parcelable.class );
      Bundles.put( bundle, key, parcelable, Parcelable.class );
      assertThat( bundle.getParcelable( key ), is( parcelable ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testPutBundleStringObjectClassOfQ_Unsupported()
   {
      final Bundle bundle = new Bundle();
      Bundles.put( bundle, "key", "0", BigDecimal.class );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testPutBundleStringObjectClassOfQ_NullBundle()
   {
      Bundles.put( null, "key", "value", String.class );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testPutBundleStringObjectClassOfQ_NullKey()
   {
      Bundles.put( Bundle.EMPTY, null, "value", String.class );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testPutBundleStringObjectClassOfQ_EmptyKey()
   {
      Bundles.put( Bundle.EMPTY, "", "value", String.class );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testPutBundleStringObjectClassOfQ_NullClass()
   {
      Bundles.put( Bundle.EMPTY, "key", "value", null );
   }
   
   
   
   @Test
   public void testPutBundleStringObject()
   {
      final String key = "key";
      final Bundle bundle = new Bundle();
      
      Bundles.put( bundle, key, "string" );
      assertThat( bundle.getString( key ), is( "string" ) );
      
      Bundles.put( bundle, key, 1 );
      assertThat( bundle.getInt( key ), is( 1 ) );
      
      Bundles.put( bundle, key, true );
      assertThat( bundle.getBoolean( key ), is( true ) );
      
      final Bundle otherBundle = new Bundle();
      Bundles.put( bundle, key, otherBundle );
      assertThat( bundle.getBundle( key ), is( otherBundle ) );
      
      final ArrayList< String > stringArrayList = new ArrayList< String >();
      Bundles.put( bundle, key, stringArrayList );
      assertThat( bundle.getStringArrayList( key ), is( stringArrayList ) );
      
      final ArrayList< Parcelable > parcArrayList = new ArrayList< Parcelable >();
      Bundles.put( bundle,
                   Bundles.KEY_QUALIFIER_PARCABLE_ARRAY_LIST + key,
                   parcArrayList );
      assertThat( bundle.getParcelableArrayList( Bundles.KEY_QUALIFIER_PARCABLE_ARRAY_LIST
                     + key ),
                  is( parcArrayList ) );
      
      final SparseArray< Parcelable > parcableSparseArray = new SparseArray< Parcelable >();
      Bundles.put( bundle, key, parcableSparseArray );
      assertThat( bundle.getSparseParcelableArray( key ),
                  is( parcableSparseArray ) );
      
      Bundles.put( bundle, key, 1L );
      assertThat( bundle.getLong( key ), is( 1L ) );
      
      Bundles.put( bundle, key, 1.0f );
      assertThat( bundle.getFloat( key ), is( 1.0f ) );
      
      final String[] strArray = new String[] {};
      Bundles.put( bundle, key, strArray );
      assertThat( bundle.getStringArray( key ), is( strArray ) );
      
      final boolean[] boolArary = new boolean[] {};
      Bundles.put( bundle, key, boolArary );
      assertThat( bundle.getBooleanArray( key ), is( boolArary ) );
      
      final Parcelable parcelable = EasyMock.createNiceMock( Parcelable.class );
      Bundles.put( bundle, key, parcelable );
      assertThat( bundle.getParcelable( key ), is( parcelable ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testPutBundleStringObject_Unsupported()
   {
      final Bundle bundle = new Bundle();
      Bundles.put( bundle, "key", new BigDecimal( 0 ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testPutBundleStringObject_NullBundle()
   {
      Bundles.put( null, "key", "value" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testPutBundleStringObject_NullKey()
   {
      Bundles.put( Bundle.EMPTY, null, "value" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testPutBundleStringObject_EmptyKey()
   {
      Bundles.put( Bundle.EMPTY, "", "value" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testPutBundleStringObject_NullValue()
   {
      Bundles.put( Bundle.EMPTY, "key", null );
   }
}
