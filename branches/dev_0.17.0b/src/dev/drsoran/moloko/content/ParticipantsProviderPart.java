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
import android.content.ContentProviderOperation;
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
import dev.drsoran.provider.Rtm.Contacts;
import dev.drsoran.provider.Rtm.Participants;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.rtm.Participant;
import dev.drsoran.rtm.ParticipantList;
import dev.drsoran.rtm.RtmContact;


public class ParticipantsProviderPart extends AbstractRtmProviderPart
{
   private static final Class< ParticipantsProviderPart > TAG = ParticipantsProviderPart.class;
   
   public final static HashMap< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   public final static String[] PROJECTION =
   { Participants._ID, Participants.CONTACT_ID, Participants.TASKSERIES_ID,
    Participants.FULLNAME, Participants.USERNAME };
   
   public final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      AbstractRtmProviderPart.initProjectionDependent( PROJECTION,
                                                       PROJECTION_MAP,
                                                       COL_INDICES );
   }
   
   
   
   public final static ContentValues getContentValues( Participant participant,
                                                       boolean withId )
   {
      if ( participant == null )
         throw new NullPointerException( "participant is null" );
      
      final ContentValues values = new ContentValues();
      
      if ( withId )
         values.put( Participants._ID, participant.getId() );
      
      values.put( Participants.TASKSERIES_ID, participant.getTaskSeriesId() );
      values.put( Participants.CONTACT_ID, participant.getContactId() );
      values.put( Participants.FULLNAME, participant.getFullname() );
      values.put( Participants.USERNAME, participant.getUsername() );
      
      return values;
   }
   
   
   
   public final static ParticipantList getParticipants( ContentProviderClient client,
                                                        String taskSeriesId )
   {
      ParticipantList participantsList = null;
      
      Cursor c = null;
      
      try
      {
         c = client.query( Participants.CONTENT_URI,
                           PROJECTION,
                           Participants.TASKSERIES_ID + " = " + taskSeriesId,
                           null,
                           null );
         
         boolean ok = c != null;
         
         if ( ok )
         {
            final ArrayList< Participant > participants = new ArrayList< Participant >( c.getCount() );
            
            if ( c.getCount() > 0 )
            {
               for ( ok = c.moveToFirst(); ok && !c.isAfterLast(); c.moveToNext() )
               {
                  final Participant participant = createParticipant( c );
                  ok = participant != null;
                  
                  if ( ok )
                     participants.add( participant );
               }
            }
            
            if ( ok )
               participantsList = new ParticipantList( taskSeriesId,
                                                       participants );
         }
      }
      catch ( final RemoteException e )
      {
         MolokoApp.Log.e( TAG, "Query participants failed. ", e );
         participantsList = null;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return participantsList;
   }
   
   
   
   public final static List< RtmContact > getParticipatingContacts( ContentProviderClient client,
                                                                    String taskSeriesId )
   {
      List< RtmContact > contacts = null;
      Cursor participantsCursor = null;
      
      try
      {
         participantsCursor = client.query( Participants.CONTENT_URI,
                                            PROJECTION,
                                            Participants.TASKSERIES_ID + "="
                                               + taskSeriesId,
                                            null,
                                            null );
         
         boolean ok = participantsCursor != null;
         
         if ( ok )
         {
            contacts = new ArrayList< RtmContact >( participantsCursor.getCount() );
            
            if ( participantsCursor.getCount() > 0 )
            {
               // Only select the contacts which participate in the given taskseries
               final StringBuilder contactsSelection = new StringBuilder();
               
               for ( ok = participantsCursor.moveToFirst(); ok
                  && !participantsCursor.isAfterLast(); participantsCursor.moveToNext() )
               {
                  contactsSelection.append( Contacts._ID )
                                   .append( " = " )
                                   .append( participantsCursor.getString( COL_INDICES.get( Participants.CONTACT_ID ) ) );
                  
                  if ( !participantsCursor.isLast() )
                     contactsSelection.append( " OR " );
               }
               
               if ( ok )
               {
                  contacts = RtmContactsProviderPart.getAllContacts( client,
                                                                     contactsSelection.toString() );
               }
            }
         }
         
         if ( !ok )
            contacts = null;
      }
      catch ( RemoteException e )
      {
         MolokoApp.Log.e( TAG, "Query participating contacts failed. ", e );
         contacts = null;
      }
      finally
      {
         if ( participantsCursor != null )
            participantsCursor.close();
      }
      
      return contacts;
   }
   
   
   
   public final static int getNumTasksParticipating( ContentProviderClient client,
                                                     String contactId )
   {
      int num = -1;
      Cursor c = null;
      
      try
      {
         c = client.query( Participants.CONTENT_URI, new String[]
         { Participants._ID, Participants.CONTACT_ID }, Participants.CONTACT_ID
            + "=" + contactId, null, null );
         
         final boolean ok = c != null;
         
         if ( ok )
         {
            num = c.getCount();
         }
      }
      catch ( final RemoteException e )
      {
         MolokoApp.Log.e( TAG, "Query num tasks participating failed. ", e );
         num = -1;
      }
      finally
      {
         if ( c != null )
            c.close();
      }
      
      return num;
   }
   
   
   
   public final static List< ContentProviderOperation > insertParticipants( ParticipantList list )
   {
      final ArrayList< ContentProviderOperation > operations = new ArrayList< ContentProviderOperation >();
      final List< Participant > participants = list.getParticipants();
      
      for ( final Participant participant : participants )
      {
         operations.add( ContentProviderOperation.newInsert( Participants.CONTENT_URI )
                                                 .withValues( ParticipantsProviderPart.getContentValues( participant,
                                                                                                         true ) )
                                                 .build() );
      }
      
      return operations;
   }
   
   
   
   public final static Participant createParticipant( Cursor c )
   {
      return new Participant( c.getString( COL_INDICES.get( Participants._ID ) ),
                              c.getString( COL_INDICES.get( Participants.TASKSERIES_ID ) ),
                              c.getString( COL_INDICES.get( Participants.CONTACT_ID ) ),
                              c.getString( COL_INDICES.get( Participants.FULLNAME ) ),
                              c.getString( COL_INDICES.get( Participants.USERNAME ) ) );
   }
   
   
   
   public final static void registerContentObserver( Context context,
                                                     ContentObserver observer )
   {
      context.getContentResolver()
             .registerContentObserver( Participants.CONTENT_URI, true, observer );
   }
   
   
   
   public final static void unregisterContentObserver( Context context,
                                                       ContentObserver observer )
   {
      context.getContentResolver().unregisterContentObserver( observer );
   }
   
   
   
   public ParticipantsProviderPart( Context context, SQLiteOpenHelper dbAccess )
   {
      super( context, dbAccess, Participants.PATH );
   }
   
   
   
   @Override
   public void create( SQLiteDatabase db ) throws SQLException
   {
      db.execSQL( "CREATE TABLE "
         + path
         + " ( "
         + Participants._ID
         + " INTEGER NOT NULL CONSTRAINT PK_PARTICIPANTS PRIMARY KEY AUTOINCREMENT, "
         + Participants.CONTACT_ID + " TEXT NOT NULL, "
         + Participants.TASKSERIES_ID + " TEXT NOT NULL, "
         + Participants.FULLNAME + " TEXT NOT NULL, " + Participants.USERNAME
         + " TEXT NOT NULL, " + "CONSTRAINT participant FOREIGN KEY ( "
         + Participants.TASKSERIES_ID + " ) REFERENCES " + TaskSeries.PATH
         + " (\"" + TaskSeries._ID + "\"), "
         + "CONSTRAINT participates FOREIGN KEY ( " + Participants.CONTACT_ID
         + " ) REFERENCES " + Contacts.PATH + " (\"" + Contacts._ID + "\") "
         + " );" );
   }
   
   
   
   @Override
   protected String getContentItemType()
   {
      return Participants.CONTENT_ITEM_TYPE;
   }
   
   
   
   @Override
   protected String getContentType()
   {
      return Participants.CONTENT_TYPE;
   }
   
   
   
   @Override
   public Uri getContentUri()
   {
      return Participants.CONTENT_URI;
   }
   
   
   
   @Override
   protected String getDefaultSortOrder()
   {
      return null;
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
