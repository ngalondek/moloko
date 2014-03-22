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

package dev.drsoran.moloko.test.unit.domain.content;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.NoSuchElementException;

import org.easymock.EasyMock;
import org.junit.Test;
import org.robolectric.annotation.Config;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import dev.drsoran.moloko.domain.content.ContentQueryHandler;
import dev.drsoran.moloko.domain.content.IModelElementFactory;
import dev.drsoran.moloko.domain.services.ContentException;
import dev.drsoran.moloko.test.MolokoRoboTestCase;
import dev.drsoran.rtm.Lambda.Func1;


@Config( manifest = Config.NONE )
public class ContentQueryHandlerFixture extends MolokoRoboTestCase
{
   private final static String[] PROJECTION = new String[]
   { "value" };
   
   private final static Uri ELEMENT_URI = Uri.parse( "content://dev.drsoran.moloko.test/element/#" );
   
   private final static Uri AGG_ELEMENT_URI = Uri.parse( "content://dev.drsoran.moloko.test/elements/#/element/#" );
   
   private final static Uri ALL_URI = Uri.parse( "content://dev.drsoran.moloko.test/element" );
   
   private final static Uri ALL_AGG_URI = Uri.parse( "content://dev.drsoran.moloko.test/elements/#/element" );
   
   
   
   @Test
   public void testGetElement()
   {
      final Uri queryUri = ELEMENT_URI.buildUpon()
                                      .appendPath( String.valueOf( 1L ) )
                                      .build();
      
      testGetSingleElement( new Func1< ContentQueryHandler< Integer >, Integer >()
                            {
                               @Override
                               public Integer call( ContentQueryHandler< Integer > queryHandler )
                               {
                                  return queryHandler.getElement( ELEMENT_URI,
                                                                  1L );
                               }
                            },
                            queryUri );
   }
   
   
   
   @Test( expected = NoSuchElementException.class )
   public void testGetElement_Notfound()
   {
      testNotfound( new Func1< ContentQueryHandler< Integer >, Integer >()
      {
         @Override
         public Integer call( ContentQueryHandler< Integer > queryHandler )
         {
            return queryHandler.getElement( ELEMENT_URI, 1L );
         }
      } );
   }
   
   
   
   @Test( expected = ContentException.class )
   public void testGetElement_ContentResolverException()
   {
      testContentResolverException( new Func1< ContentQueryHandler< Integer >, Integer >()
      {
         @Override
         public Integer call( ContentQueryHandler< Integer > queryHandler )
         {
            return queryHandler.getElement( ELEMENT_URI, 1L );
         }
      } );
   }
   
   
   
   @Test( expected = ContentException.class )
   public void testGetElement_ModelFactoryException()
   {
      testModelFactoryException( new Func1< ContentQueryHandler< Integer >, Integer >()
      {
         @Override
         public Integer call( ContentQueryHandler< Integer > queryHandler )
         {
            return queryHandler.getElement( ELEMENT_URI, 1L );
         }
      } );
   }
   
   
   
   @Test
   public void testGetAggregatedElement()
   {
      final Uri queryUri = Uri.parse( AGG_ELEMENT_URI.toString()
                                                     .replaceFirst( "#", "100" )
                                                     .replaceFirst( "#", "1" ) );
      
      testGetSingleElement( new Func1< ContentQueryHandler< Integer >, Integer >()
                            {
                               @Override
                               public Integer call( ContentQueryHandler< Integer > queryHandler )
                               {
                                  return queryHandler.getAggregatedElement( AGG_ELEMENT_URI,
                                                                            100L,
                                                                            1L );
                               }
                            },
                            queryUri );
   }
   
   
   
   @Test( expected = NoSuchElementException.class )
   public void testGetAggregatedElement_Notfound()
   {
      testNotfound( new Func1< ContentQueryHandler< Integer >, Integer >()
      {
         @Override
         public Integer call( ContentQueryHandler< Integer > queryHandler )
         {
            return queryHandler.getAggregatedElement( AGG_ELEMENT_URI, 100L, 1L );
         }
      } );
   }
   
   
   
   @Test( expected = ContentException.class )
   public void testGetAggregatedElement_ContentResolverException()
   {
      testContentResolverException( new Func1< ContentQueryHandler< Integer >, Integer >()
      {
         @Override
         public Integer call( ContentQueryHandler< Integer > queryHandler )
         {
            return queryHandler.getAggregatedElement( AGG_ELEMENT_URI, 100L, 1L );
         }
      } );
   }
   
   
   
   @Test( expected = ContentException.class )
   public void testGetAggregatedElement_ModelFactoryException()
   {
      testModelFactoryException( new Func1< ContentQueryHandler< Integer >, Integer >()
      {
         @Override
         public Integer call( ContentQueryHandler< Integer > queryHandler )
         {
            return queryHandler.getAggregatedElement( AGG_ELEMENT_URI, 100L, 1L );
         }
      } );
   }
   
   
   
   @Test
   public void testGetAll()
   {
      final String selection = "value > 90";
      testGetMultipleElements( new Func1< ContentQueryHandler< Integer >, Iterable< Integer > >()
                               {
                                  @Override
                                  public Iterable< Integer > call( ContentQueryHandler< Integer > queryHandler )
                                  {
                                     return queryHandler.getAll( ALL_URI,
                                                                 selection,
                                                                 null );
                                  }
                               },
                               ALL_URI,
                               selection );
   }
   
   
   
   @Test
   public void testGetAll_WithSort()
   {
      final String selection = "value > 90";
      final String sort = "value ASC";
      testGetMultipleElements( new Func1< ContentQueryHandler< Integer >, Iterable< Integer > >()
                               {
                                  @Override
                                  public Iterable< Integer > call( ContentQueryHandler< Integer > queryHandler )
                                  {
                                     return queryHandler.getAll( ALL_URI,
                                                                 selection,
                                                                 sort );
                                  }
                               },
                               ALL_URI,
                               selection,
                               sort );
   }
   
   
   
   @Test( expected = ContentException.class )
   public void testGetAll_ContentResolverException()
   {
      testContentResolverException( new Func1< ContentQueryHandler< Integer >, Iterable< Integer > >()
      {
         @Override
         public Iterable< Integer > call( ContentQueryHandler< Integer > queryHandler )
         {
            return queryHandler.getAll( ALL_URI, "value > 90", null );
         }
      } );
   }
   
   
   
   @Test( expected = ContentException.class )
   public void testGetAll_ModelFactoryException()
   {
      testModelFactoryException( new Func1< ContentQueryHandler< Integer >, Iterable< Integer > >()
      {
         @Override
         public Iterable< Integer > call( ContentQueryHandler< Integer > queryHandler )
         {
            return queryHandler.getAll( ALL_URI, "value > 90", null );
         }
      } );
   }
   
   
   
   @Test
   public void testGetAllForAggregation()
   {
      final Uri queryUri = Uri.parse( ALL_AGG_URI.toString()
                                                 .replaceFirst( "#", "100" ) );
      final String selection = "value > 90";
      
      testGetMultipleElements( new Func1< ContentQueryHandler< Integer >, Iterable< Integer > >()
                               {
                                  @Override
                                  public Iterable< Integer > call( ContentQueryHandler< Integer > queryHandler )
                                  {
                                     return queryHandler.getAllForAggregation( ALL_AGG_URI,
                                                                               100L,
                                                                               selection,
                                                                               null );
                                  }
                               },
                               queryUri,
                               selection );
   }
   
   
   
   @Test
   public void testGetAllForAggregation_WithSort()
   {
      final Uri queryUri = Uri.parse( ALL_AGG_URI.toString()
                                                 .replaceFirst( "#", "100" ) );
      final String selection = "value > 90";
      final String sort = "value ASC";
      
      testGetMultipleElements( new Func1< ContentQueryHandler< Integer >, Iterable< Integer > >()
                               {
                                  @Override
                                  public Iterable< Integer > call( ContentQueryHandler< Integer > queryHandler )
                                  {
                                     return queryHandler.getAllForAggregation( ALL_AGG_URI,
                                                                               100L,
                                                                               selection,
                                                                               sort );
                                  }
                               },
                               queryUri,
                               selection,
                               sort );
   }
   
   
   
   @Test( expected = ContentException.class )
   public void testGetAllForAggregation_ContentResolverException()
   {
      testContentResolverException( new Func1< ContentQueryHandler< Integer >, Iterable< Integer > >()
      {
         @Override
         public Iterable< Integer > call( ContentQueryHandler< Integer > queryHandler )
         {
            return queryHandler.getAllForAggregation( ALL_AGG_URI,
                                                      100L,
                                                      "value > 90",
                                                      null );
         }
      } );
   }
   
   
   
   @Test( expected = ContentException.class )
   public void testGetAllForAggregation_ModelFactoryException()
   {
      testModelFactoryException( new Func1< ContentQueryHandler< Integer >, Iterable< Integer > >()
      {
         @Override
         public Iterable< Integer > call( ContentQueryHandler< Integer > queryHandler )
         {
            return queryHandler.getAllForAggregation( ALL_AGG_URI,
                                                      100L,
                                                      "value > 90",
                                                      null );
         }
      } );
   }
   
   
   
   private void testGetSingleElement( Func1< ContentQueryHandler< Integer >, Integer > methodToCall,
                                      Uri queryUri )
   {
      final Cursor c = createCursor( 1 );
      
      final ContentResolver contentResolver = EasyMock.createStrictMock( ContentResolver.class );
      EasyMock.expect( contentResolver.query( queryUri,
                                              PROJECTION,
                                              null,
                                              null,
                                              null ) ).andReturn( c );
      EasyMock.replay( contentResolver );
      
      final IModelElementFactory modelElementFactory = createModelElementFactory( Arrays.asList( 99 ) );
      
      final ContentQueryHandler< Integer > queryHandler = new ContentQueryHandler< Integer >( contentResolver,
                                                                                              PROJECTION,
                                                                                              modelElementFactory,
                                                                                              Integer.class );
      final Integer res = methodToCall.call( queryHandler );
      
      assertThat( res, notNullValue() );
      assertThat( res, is( 99 ) );
      
      EasyMock.verify( c );
      EasyMock.verify( contentResolver );
      EasyMock.verify( modelElementFactory );
   }
   
   
   
   private void testGetMultipleElements( Func1< ContentQueryHandler< Integer >, Iterable< Integer > > methodToCall,
                                         Uri queryUri,
                                         String selection )
   {
      testGetMultipleElements( methodToCall, queryUri, selection, null );
   }
   
   
   
   private void testGetMultipleElements( Func1< ContentQueryHandler< Integer >, Iterable< Integer > > methodToCall,
                                         Uri queryUri,
                                         String selection,
                                         String sort )
   {
      final Cursor c = createCursor( 2 );
      
      final ContentResolver contentResolver = EasyMock.createStrictMock( ContentResolver.class );
      EasyMock.expect( contentResolver.query( queryUri,
                                              PROJECTION,
                                              selection,
                                              null,
                                              sort ) ).andReturn( c );
      EasyMock.replay( contentResolver );
      
      final IModelElementFactory modelElementFactory = createModelElementFactory( Arrays.asList( 99,
                                                                                                 100 ) );
      
      final ContentQueryHandler< Integer > queryHandler = new ContentQueryHandler< Integer >( contentResolver,
                                                                                              PROJECTION,
                                                                                              modelElementFactory,
                                                                                              Integer.class );
      final Iterable< Integer > res = methodToCall.call( queryHandler );
      
      assertThat( res, notNullValue() );
      assertThat( res, hasItems( 99, 100 ) );
      
      EasyMock.verify( c );
      EasyMock.verify( contentResolver );
      EasyMock.verify( modelElementFactory );
   }
   
   
   
   private void testNotfound( Func1< ContentQueryHandler< Integer >, Integer > methodToCall )
   {
      final Cursor c = createCursor( 0 );
      
      final ContentResolver contentResolver = EasyMock.createStrictMock( ContentResolver.class );
      EasyMock.expect( contentResolver.query( EasyMock.anyObject( Uri.class ),
                                              EasyMock.anyObject( String[].class ),
                                              EasyMock.anyObject( String.class ),
                                              EasyMock.anyObject( String[].class ),
                                              EasyMock.anyObject( String.class ) ) )
              .andReturn( c );
      EasyMock.replay( contentResolver );
      
      final IModelElementFactory modelElementFactory = EasyMock.createStrictMock( IModelElementFactory.class );
      EasyMock.replay( modelElementFactory );
      
      final ContentQueryHandler< Integer > queryHandler = new ContentQueryHandler< Integer >( contentResolver,
                                                                                              PROJECTION,
                                                                                              modelElementFactory,
                                                                                              Integer.class );
      try
      {
         methodToCall.call( queryHandler );
      }
      finally
      {
         EasyMock.verify( c );
         EasyMock.verify( contentResolver );
         EasyMock.verify( modelElementFactory );
      }
   }
   
   
   
   private < T > void testContentResolverException( Func1< ContentQueryHandler< Integer >, T > methodToCall )
   {
      final ContentResolver contentResolver = EasyMock.createStrictMock( ContentResolver.class );
      EasyMock.expect( contentResolver.query( EasyMock.anyObject( Uri.class ),
                                              EasyMock.anyObject( String[].class ),
                                              EasyMock.anyObject( String.class ),
                                              EasyMock.anyObject( String[].class ),
                                              EasyMock.anyObject( String.class ) ) )
              .andThrow( new RuntimeException() );
      EasyMock.replay( contentResolver );
      
      final IModelElementFactory modelElementFactory = EasyMock.createStrictMock( IModelElementFactory.class );
      EasyMock.replay( modelElementFactory );
      
      final ContentQueryHandler< Integer > queryHandler = new ContentQueryHandler< Integer >( contentResolver,
                                                                                              PROJECTION,
                                                                                              modelElementFactory,
                                                                                              Integer.class );
      
      try
      {
         methodToCall.call( queryHandler );
      }
      finally
      {
         EasyMock.verify( contentResolver );
         EasyMock.verify( modelElementFactory );
      }
   }
   
   
   
   private < T > void testModelFactoryException( Func1< ContentQueryHandler< Integer >, T > methodToCall )
   {
      final Cursor c = createCursorInfiniteCursor();
      
      final ContentResolver contentResolver = EasyMock.createStrictMock( ContentResolver.class );
      EasyMock.expect( contentResolver.query( EasyMock.anyObject( Uri.class ),
                                              EasyMock.anyObject( String[].class ),
                                              EasyMock.anyObject( String.class ),
                                              EasyMock.anyObject( String[].class ),
                                              EasyMock.anyObject( String.class ) ) )
              .andReturn( c );
      EasyMock.replay( contentResolver );
      
      final IModelElementFactory modelElementFactory = EasyMock.createStrictMock( IModelElementFactory.class );
      EasyMock.expect( modelElementFactory.createElementFromCursor( c,
                                                                    Integer.class ) )
              .andThrow( new IllegalArgumentException() );
      EasyMock.replay( modelElementFactory );
      
      final ContentQueryHandler< Integer > queryHandler = new ContentQueryHandler< Integer >( contentResolver,
                                                                                              PROJECTION,
                                                                                              modelElementFactory,
                                                                                              Integer.class );
      
      try
      {
         methodToCall.call( queryHandler );
      }
      finally
      {
         EasyMock.verify( c );
         EasyMock.verify( contentResolver );
         EasyMock.verify( modelElementFactory );
      }
   }
   
   
   
   private Cursor createCursor( int numElements )
   {
      final Cursor c = EasyMock.createStrictMock( Cursor.class );
      
      for ( int i = 0; i < numElements; i++ )
      {
         EasyMock.expect( c.moveToNext() ).andReturn( true );
      }
      
      EasyMock.expect( c.moveToNext() ).andReturn( false );
      
      c.close();
      
      EasyMock.replay( c );
      
      return c;
   }
   
   
   
   private Cursor createCursorInfiniteCursor()
   {
      final Cursor c = EasyMock.createStrictMock( Cursor.class );
      
      EasyMock.expect( c.moveToNext() ).andReturn( true ).anyTimes();
      c.close();
      
      EasyMock.replay( c );
      
      return c;
   }
   
   
   
   private IModelElementFactory createModelElementFactory( Iterable< Integer > values )
   {
      final IModelElementFactory modelElementFactory = EasyMock.createStrictMock( IModelElementFactory.class );
      
      for ( Integer value : values )
      {
         EasyMock.expect( modelElementFactory.createElementFromCursor( EasyMock.anyObject( Cursor.class ),
                                                                       EasyMock.eq( Integer.class ) ) )
                 .andReturn( value );
      }
      
      EasyMock.replay( modelElementFactory );
      
      return modelElementFactory;
   }
}
