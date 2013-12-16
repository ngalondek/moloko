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

package dev.drsoran.moloko.app.loaders;

import java.util.List;

import android.net.Uri;
import dev.drsoran.Iterables;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.DomainContext;
import dev.drsoran.moloko.domain.model.CloudEntry;
import dev.drsoran.moloko.domain.services.IContentRepository;


public class CloudEntryLoader extends AbstractLoader< List< CloudEntry > >
{
   public final static int ID = R.id.loader_cloud_entry;
   
   
   
   public CloudEntryLoader( DomainContext context )
   {
      super( context );
   }
   
   
   
   @Override
   public Uri getContentUri()
   {
      return ContentUris.CLOUD_ENTRIES_CONTENT_URI;
   }
   
   
   
   @Override
   protected List< CloudEntry > queryResultInBackground( IContentRepository contentRepository )
   {
      return Iterables.asList( contentRepository.getCloudEntries() );
   }
}
