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

package dev.drsoran.moloko.prefs;

import android.content.ContentProviderClient;
import android.content.Context;
import android.database.Cursor;
import android.os.RemoteException;
import android.util.AttributeSet;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.Settings;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.provider.Rtm.Lists;


public class DefaultListPreference extends SyncableListPreference
{
   public final static class EntriesAndValues
   {
      public final static int NONE_IDX = 0;
      
      public CharSequence[] entries;
      
      public CharSequence[] values;
   }
   
   

   public DefaultListPreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      
      final EntriesAndValues entriesAndValues = createEntriesAndValues( context );
      
      if ( entriesAndValues != null )
      {
         setEntries( entriesAndValues.entries );
         setEntryValues( entriesAndValues.values );
      }
   }
   


   public final static EntriesAndValues createEntriesAndValues( Context context )
   {
      EntriesAndValues entriesAndValues = null;
      
      // get all lists
      final ContentProviderClient client = context.getContentResolver()
                                                  .acquireContentProviderClient( Lists.CONTENT_URI );
      
      if ( client != null )
      {
         Cursor c = null;
         
         try
         {
            c = client.query( Lists.CONTENT_URI, new String[]
            { Lists._ID, Lists.LIST_NAME }, null, null, Lists.LIST_NAME );
            
            boolean ok = c != null;
            
            if ( ok )
            {
               CharSequence[] entries = new CharSequence[ c.getCount() + 1 ]; // +1
               // cause
               // of
               // "none"
               CharSequence[] entryValues = new CharSequence[ c.getCount() + 1 ]; // +1
               // cause
               // of
               // "none"
               
               entries[ EntriesAndValues.NONE_IDX ] = context.getResources()
                                                             .getString( R.string.phr_none_f );
               entryValues[ EntriesAndValues.NONE_IDX ] = Settings.NO_DEFAULT_LIST_ID;
               
               if ( c.getCount() > 0 )
               {
                  for ( ok = c.moveToFirst(); ok && !c.isAfterLast(); c.moveToNext() )
                  {
                     entryValues[ c.getPosition() + 1 ] = c.getString( 0 );
                     entries[ c.getPosition() + 1 ] = c.getString( 1 );
                  }
               }
               
               if ( ok )
               {
                  entriesAndValues = new EntriesAndValues();
                  entriesAndValues.entries = entries;
                  entriesAndValues.values = entryValues;
               }
            }
            
            if ( c != null )
               c.close();
            
            client.release();
         }
         catch ( RemoteException e )
         {
            LogUtils.logDBError( context,
                                 LogUtils.toTag( DefaultListPreference.class ),
                                 "Lists",
                                 e );
         }
         finally
         {
            if ( c != null )
               c.close();
         }
      }
      else
      {
         LogUtils.logDBError( context,
                              LogUtils.toTag( DefaultListPreference.class ),
                              "Lists" );
      }
      
      return entriesAndValues;
   }
}
