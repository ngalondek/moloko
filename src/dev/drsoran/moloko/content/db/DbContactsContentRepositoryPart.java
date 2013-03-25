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

package dev.drsoran.moloko.content.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import android.database.Cursor;
import dev.drsoran.moloko.content.db.Columns.RtmContactsColumns;
import dev.drsoran.moloko.domain.model.Contact;
import dev.drsoran.moloko.domain.model.IContact;
import dev.drsoran.moloko.domain.services.ContentException;


class DbContactsContentRepositoryPart
{
   private final RtmContactsQuery contactsQuery;
   
   private final ParticipantsQuery participantsQuery;
   
   
   
   public DbContactsContentRepositoryPart( RtmDatabase database )
   {
      this.contactsQuery = database.getQuery( RtmContactsQuery.class );
      this.participantsQuery = database.getQuery( ParticipantsQuery.class );
   }
   
   
   
   public IContact getById( long id ) throws NoSuchElementException,
                                     ContentException
   {
      final Iterator< IContact > contactsIterator = getContactsFromDb( RtmContactsColumns._ID
         + "=" + id ).iterator();
      
      if ( contactsIterator.hasNext() )
      {
         return contactsIterator.next();
      }
      else
      {
         throw new NoSuchElementException( "No Contact with ID '" + id + "'" );
      }
   }
   
   
   
   public Iterable< IContact > getAll() throws ContentException
   {
      return getContactsFromDb( null );
   }
   
   
   
   public int getNumTasksContactIsParticipating( long contactId,
                                                 String selection )
   {
      return participantsQuery.getNumTasksContactIsParticipating( contactId,
                                                                  selection );
   }
   
   
   
   private Iterable< IContact > getContactsFromDb( String selection ) throws ContentException
   {
      final Collection< IContact > contacts = new ArrayList< IContact >();
      
      Cursor c = null;
      
      try
      {
         c = contactsQuery.getAllContacts( selection );
         
         while ( c.moveToNext() )
         {
            final Contact contact = createContactFromCursor( c );
            contacts.add( contact );
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
      
      return contacts;
   }
   
   
   
   private static Contact createContactFromCursor( Cursor c )
   {
      return new Contact( c.getLong( Columns.ID_IDX ),
                          c.getString( RtmContactsColumns.USERNAME_IDX ),
                          c.getString( RtmContactsColumns.FULLNAME_IDX ) );
   }
}
