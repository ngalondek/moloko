/*
 * Copyright (c) 2010 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.domain.content;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.services.ContentException;


public class ContentQueryHandler< T >
{
   private final ContentResolver contentResolver;
   
   private final String[] contentProjection;
   
   private final IModelElementFactory modelElementFactory;
   
   private final Class< T > elementType;
   
   
   
   public ContentQueryHandler( ContentResolver contentResolver,
      String[] contentProjection, IModelElementFactory modelElementFactory,
      Class< T > elementType )
   {
      this.contentResolver = contentResolver;
      this.contentProjection = contentProjection;
      this.modelElementFactory = modelElementFactory;
      this.elementType = elementType;
   }
   
   
   
   public T getElement( Uri contentUri, long elementId ) throws NoSuchElementException,
                                                        ContentException
   {
      final List< T > elements = performQuery( ContentUris.bindElementId( contentUri,
                                                                          elementId ),
                                               null );
      
      if ( elements.size() == 0 )
      {
         throw new NoSuchElementException( MessageFormat.format( "No element with ID ''{0}''",
                                                                 elementId ) );
      }
      
      return elements.get( 0 );
   }
   
   
   
   public T getAggregatedElement( Uri contentUri, long rootId, long elementId ) throws NoSuchElementException,
                                                                               ContentException
   {
      final List< T > elements = performQuery( ContentUris.bindAggregatedElementIdToUri( contentUri,
                                                                                         rootId,
                                                                                         elementId ),
                                               null );
      if ( elements.size() == 0 )
      {
         throw new NoSuchElementException( MessageFormat.format( "No aggregated element with root ID ''{0}'' and element ID ''{1}''",
                                                                 rootId,
                                                                 elementId ) );
      }
      
      return elements.get( 0 );
   }
   
   
   
   public Iterable< T > getAll( Uri contentUri, String selection ) throws ContentException
   {
      return performQuery( contentUri, selection );
   }
   
   
   
   public Iterable< T > getAllForAggregation( Uri contentUri,
                                              long rootId,
                                              String selection ) throws ContentException
   {
      contentUri = ContentUris.bindAggregationIdToUri( contentUri, rootId );
      return performQuery( contentUri, selection );
   }
   
   
   
   private List< T > performQuery( Uri contentUri, String selection ) throws ContentException
   {
      final List< T > elements = new ArrayList< T >();
      
      Cursor c = null;
      try
      {
         c = contentResolver.query( contentUri,
                                    contentProjection,
                                    selection,
                                    null,
                                    // TODO: Perhaps the sort order must be a parameter
                                    null );
         
         while ( c.moveToNext() )
         {
            final T element = modelElementFactory.createElementFromCursor( c,
                                                                           elementType );
            elements.add( element );
         }
      }
      catch ( Throwable e )
      {
         throw new ContentException( e );
      }
      finally
      {
         if ( c != null )
         {
            c.close();
         }
      }
      
      return elements;
   }
}
