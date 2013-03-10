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

package dev.drsoran.moloko.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.RemoteException;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.SystemContext;
import dev.drsoran.moloko.content.db.AbstractRtmProviderPart;
import dev.drsoran.moloko.content.db.DbHelper;
import dev.drsoran.provider.Rtm.Contacts;
import dev.drsoran.provider.Rtm.Participants;
import dev.drsoran.rtm.RtmContact;


public class RtmContactsProviderPart extends AbstractRtmProviderPart
{
   private static final Class< RtmContactsProviderPart > TAG = RtmContactsProviderPart.class;
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { Contacts._ID, Contacts.FULLNAME, Contacts.USERNAME };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      AbstractRtmProviderPart.initProjectionDependent( PROJECTION,
                                                       PROJECTION_MAP,
                                                       COL_INDICES );
   }
   
   
   
   public final static ContentValues getContentValues( RtmContact contact,
                                                       boolean withId )
   {
      ContentValues values = null;
      
      if ( contact != null )
      {
         values = new ContentValues();
         
         if ( withId )
            values.put( Contacts._ID, contact.getId() );
         
         values.put( Contacts.FULLNAME, contact.getFullname() );
         values.put( Contacts.USERNAME, contact.getUsername() );
      }
      
      return values;
   }
   
   
   
   public final static RtmContact getContact( ContentProviderClient client,
                                              String id )
   {
      RtmContact contact = null;
      Cursor c = null;
      
      try
      {
         c = DbHelper.getItem( client, PROJECTION, Contacts.CONTENT_URI, id );
         
         boolean ok = c != null && c.moveToFirst();
         
         if ( ok )
         {
            contact = createContact( c );
         }
      }
      catch ( RemoteException e )
      {
         MolokoApp.Log().e( TAG, "Query contact failed. ", e );
         contact = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return contact;
   }
   
   
   
   public final static List< RtmContact > getAllContacts( ContentProviderClient client,
                                                          String selection )
   {
      List< RtmContact > contacts = null;
      Cursor c = null;
      
      try
      {
         c = client.query( Contacts.CONTENT_URI,
                           PROJECTION,
                           selection,
                           null,
                           null );
         
         boolean ok = c != null;
         
         if ( ok )
         {
            contacts = new ArrayList< RtmContact >( c.getCount() );
            
            if ( c.getCount() > 0 )
            {
               for ( ok = c.moveToFirst(); ok && !c.isAfterLast(); c.moveToNext() )
               {
                  final RtmContact contact = createContact( c );
                  ok = contact != null;
                  
                  if ( ok )
                     contacts.add( contact );
               }
            }
            
            if ( !ok )
               contacts = null;
         }
      }
      catch ( RemoteException e )
      {
         MolokoApp.Log().e( TAG, "Query locations failed. ", e );
         contacts = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return contacts;
   }
   
   
   
   public final static void registerContentObserver( Context context,
                                                     ContentObserver observer )
   {
      context.getContentResolver()
             .registerContentObserver( Contacts.CONTENT_URI, true, observer );
   }
   
   
   
   public final static void unregisterContentObserver( Context context,
                                                       ContentObserver observer )
   {
      context.getContentResolver().unregisterContentObserver( observer );
   }
   
   
   
   public static RtmContact createContact( Cursor c )
   {
      return new RtmContact( c.getString( COL_INDICES.get( Contacts._ID ) ),
                             c.getString( COL_INDICES.get( Contacts.FULLNAME ) ),
                             c.getString( COL_INDICES.get( Contacts.USERNAME ) ) );
   }
   
   
   
   public RtmContactsProviderPart( SystemContext context,
      SQLiteOpenHelper dbAccess )
   {
      super( context, dbAccess, Contacts.PATH );
   }
   
   
   
   @Override
   public Object getElement( Uri uri )
   {
      if ( matchUri( uri ) == MATCH_ITEM_TYPE )
         return getContact( aquireContentProviderClient( uri ),
                            uri.getLastPathSegment() );
      return null;
   }
   
   
   
   @Override
   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE " + path + " ( " + Contacts._ID
         + " TEXT NOT NULL, " + Contacts.FULLNAME + " TEXT NOT NULL, "
         + Contacts.USERNAME + " TEXT NOT NULL, "
         + "CONSTRAINT PK_CONTACTS PRIMARY KEY ( \"" + Contacts._ID + "\" )"
         + " );" );
      
      // Trigger: If a contact gets deleted, we also delete:
      // - all referenced participants
      db.execSQL( "CREATE TRIGGER " + path + "_delete_contact AFTER DELETE ON "
         + path + " FOR EACH ROW BEGIN DELETE FROM " + Participants.PATH
         + " WHERE " + Participants.CONTACT_ID + " = old." + Contacts._ID
         + "; END;" );
   }
   
   
   
   @Override
   protected String getContentItemType()
   {
      return Contacts.CONTENT_ITEM_TYPE;
   }
   
   
   
   @Override
   protected String getContentType()
   {
      return Contacts.CONTENT_TYPE;
   }
   
   
   
   @Override
   public Uri getContentUri()
   {
      return Contacts.CONTENT_URI;
   }
   
   
   
   @Override
   protected String getDefaultSortOrder()
   {
      return Contacts.DEFAULT_SORT_ORDER;
   }
   
   
   
   @Override
   public HashMap< String, String > getProjectionMap()
   {
      return PROJECTION_MAP;
   }
   
   
   
   @Override
   public HashMap< String, Integer > getColumnIndices()
   {
      return COL_INDICES;
   }
   
   
   
   @Override
   public String[] getProjection()
   {
      return PROJECTION;
   }
}
