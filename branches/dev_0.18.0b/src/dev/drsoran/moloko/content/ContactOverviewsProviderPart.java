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
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.SystemContext;
import dev.drsoran.provider.Rtm.ContactOverviews;
import dev.drsoran.provider.Rtm.Contacts;
import dev.drsoran.provider.Rtm.Participants;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.rtm.RtmContact;
import dev.drsoran.rtm.RtmContactWithTaskCount;


public class ContactOverviewsProviderPart extends AbstractProviderPart
{
   private static final Class< ContactOverviewsProviderPart > TAG = ContactOverviewsProviderPart.class;
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { ContactOverviews._ID, ContactOverviews.FULLNAME,
    ContactOverviews.USERNAME, ContactOverviews.TASKS_COUNT };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   private final static String QUERY;
   
   private final static String SUB_QUERY;
   
   static
   {
      AbstractProviderPart.initProjectionDependent( PROJECTION,
                                                    PROJECTION_MAP,
                                                    COL_INDICES );
      
      SUB_QUERY = SQLiteQueryBuilder.buildQueryString( // not distinct
      false,
                                                       
                                                       // tables
                                                       Participants.PATH + ","
                                                          + RawTasks.PATH,
                                                       
                                                       // columns
                                                       new String[]
                                                       { Participants.CONTACT_ID },
                                                       
                                                       // where
                                                       Participants.PATH
                                                          + "."
                                                          + Participants.TASKSERIES_ID
                                                          + "="
                                                          + RawTasks.PATH
                                                          + "."
                                                          + RawTasks.TASKSERIES_ID
                                                          + " AND "
                                                          + RawTasks.COMPLETED_DATE
                                                          + " IS NULL",
                                                       null,
                                                       null,
                                                       null,
                                                       null );
      
      QUERY = new StringBuilder( "SELECT " ).append( Contacts.PATH )
                                            .append( ".*, count( subQuery." )
                                            .append( Participants.CONTACT_ID )
                                            .append( " ) AS " )
                                            .append( ContactOverviews.TASKS_COUNT )
                                            .append( " FROM " )
                                            .append( Contacts.PATH )
                                            .append( " LEFT OUTER JOIN (" )
                                            .append( SUB_QUERY )
                                            .append( ") AS subQuery ON subQuery." )
                                            .append( Participants.CONTACT_ID )
                                            .append( " = " )
                                            .append( Contacts.PATH )
                                            .append( "." )
                                            .append( Contacts._ID )
                                            .append( " GROUP BY " )
                                            .append( Contacts.PATH )
                                            .append( "." )
                                            .append( Contacts._ID )
                                            .toString();
   }
   
   
   
   public final static void registerContentObserver( Context context,
                                                     ContentObserver observer )
   {
      context.getContentResolver()
             .registerContentObserver( Contacts.CONTENT_URI, true, observer );
      context.getContentResolver()
             .registerContentObserver( Participants.CONTENT_URI, true, observer );
   }
   
   
   
   public final static void unregisterContentObserver( Context context,
                                                       ContentObserver observer )
   {
      context.getContentResolver().unregisterContentObserver( observer );
   }
   
   
   
   public final static RtmContactWithTaskCount getContactOverview( ContentProviderClient client,
                                                                   String selection )
   {
      RtmContactWithTaskCount contact = null;
      
      Cursor c = null;
      
      try
      {
         c = client.query( ContactOverviews.CONTENT_URI,
                           PROJECTION,
                           selection,
                           null,
                           null );
         
         if ( c != null && c.getCount() > 0 && c.moveToFirst() )
         {
            contact = createContactOverview( c );
         }
      }
      catch ( RemoteException e )
      {
         MolokoApp.Log().e( TAG, "Query contact overview failed. ", e );
         contact = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return contact;
   }
   
   
   
   public final static List< RtmContactWithTaskCount > getContactOverviews( ContentProviderClient client,
                                                                            String selection )
   {
      List< RtmContactWithTaskCount > contacts = null;
      Cursor c = null;
      
      try
      {
         c = client.query( ContactOverviews.CONTENT_URI,
                           PROJECTION,
                           selection,
                           null,
                           ContactOverviews.DEFAULT_SORT_ORDER );
         
         boolean ok = c != null;
         
         if ( ok )
         {
            contacts = new ArrayList< RtmContactWithTaskCount >( c.getCount() );
            
            if ( c.getCount() > 0 )
            {
               for ( ok = c.moveToFirst(); ok && !c.isAfterLast(); c.moveToNext() )
               {
                  final RtmContactWithTaskCount contact = createContactOverview( c );
                  ok = contact != null;
                  
                  if ( ok )
                     contacts.add( contact );
               }
            }
         }
         
         if ( !ok )
            contacts = null;
      }
      catch ( RemoteException e )
      {
         MolokoApp.Log().e( TAG, "Query contact overviews failed. ", e );
         contacts = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return contacts;
   }
   
   
   
   public ContactOverviewsProviderPart( SystemContext context,
      SQLiteOpenHelper dbAccess )
   {
      super( context, dbAccess, ContactOverviews.PATH );
   }
   
   
   
   @Override
   public Cursor query( String id,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder )
   {
      final StringBuilder stringBuilder = new StringBuilder( "SELECT " ).append( DbUtils.toCommaList( projection ) )
                                                                        .append( " FROM (" )
                                                                        .append( QUERY )
                                                                        .append( ")" );
      
      if ( !TextUtils.isEmpty( selection ) )
      {
         stringBuilder.append( " WHERE ( " )
                      .append( selectionArgs != null
                                                    ? DbUtils.bindAll( selection,
                                                                       selectionArgs )
                                                    : selection )
                      .append( " )" );
      }
      
      if ( !TextUtils.isEmpty( sortOrder ) )
      {
         stringBuilder.append( " ORDER BY " ).append( sortOrder );
      }
      
      final String finalQuery = stringBuilder.toString();
      
      // Get the database and run the QUERY
      final SQLiteDatabase db = dbAccess.getReadableDatabase();
      final Cursor cursor = db.rawQuery( finalQuery, null );
      
      return cursor;
   }
   
   
   
   @Override
   protected String getContentItemType()
   {
      return null;
   }
   
   
   
   @Override
   protected String getContentType()
   {
      return ContactOverviews.CONTENT_TYPE;
   }
   
   
   
   @Override
   public Uri getContentUri()
   {
      return ContactOverviews.CONTENT_URI;
   }
   
   
   
   @Override
   protected String getDefaultSortOrder()
   {
      return ContactOverviews.DEFAULT_SORT_ORDER;
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
   
   
   
   @Override
   public HashMap< String, String > getProjectionMap()
   {
      return PROJECTION_MAP;
   }
   
   
   
   public final static RtmContactWithTaskCount createContactOverview( Cursor c )
   {
      final RtmContact contact = RtmContactsTable.createContact( c );
      
      if ( contact != null )
         return new RtmContactWithTaskCount( contact,
                                             c.getInt( COL_INDICES.get( ContactOverviews.TASKS_COUNT ) ) );
      
      return null;
   }
}
