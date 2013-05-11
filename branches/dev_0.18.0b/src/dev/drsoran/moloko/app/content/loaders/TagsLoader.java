/* 
 *	Copyright (c) 2012 Ronny Röhricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.app.content.loaders;

import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.DomainContext;
import dev.drsoran.moloko.domain.services.IContentRepository;


public class TagsLoader extends AbstractLoader< List< String > >
{
   public final static int ID = R.id.loader_tags;
   
   
   
   public TagsLoader( DomainContext context )
   {
      super( context );
   }
   
   
   
   @Override
   public Uri getContentUri()
   {
      return ContentUris.TAG_CLOUD_CONTENT_URI;
   }
   
   
   
   @Override
   protected List< String > queryResultInBackground( IContentRepository contentRepository )
   {
      final List< String > tags = new ArrayList< String >();
      
      for ( String tag : contentRepository.getAllTags() )
      {
         tags.add( tag );
      }
      
      return tags;
   }
}
