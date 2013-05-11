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
import dev.drsoran.moloko.domain.model.IContact;
import dev.drsoran.moloko.domain.services.IContentRepository;


public class ContactsLoader extends AbstractLoader< List< IContact > >
{
   public final static int ID = R.id.loader_contacts;
   
   
   
   public ContactsLoader( DomainContext context )
   {
      super( context );
   }
   
   
   
   @Override
   public Uri getContentUri()
   {
      return ContentUris.CONTACTS_CONTENT_URI;
   }
   
   
   
   @Override
   protected List< IContact > queryResultInBackground( IContentRepository contentRepository )
   {
      final List< IContact > contacts = new ArrayList< IContact >();
      for ( IContact contact : contentRepository.getAllContacts() )
      {
         contacts.add( contact );
      }
      
      return contacts;
   }
}
