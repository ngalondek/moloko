package dev.drsoran.moloko.prefs;

import android.content.ContentProviderClient;
import android.content.Context;
import android.database.Cursor;
import android.os.RemoteException;
import android.util.AttributeSet;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.Settings;
import dev.drsoran.provider.Rtm.Lists;


public class DefaultListPreference extends SyncableListPreference
{
   public DefaultListPreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      
      // get all lists
      final ContentProviderClient client = getContext().getContentResolver()
                                                       .acquireContentProviderClient( Lists.CONTENT_URI );
      
      if ( client != null )
      {
         try
         {
            final Cursor c = client.query( Lists.CONTENT_URI, new String[]
            { Lists._ID, Lists.LIST_NAME }, null, null, Lists.LIST_NAME );
            
            boolean ok = c != null;
            
            if ( ok )
            {
               CharSequence[] entries = new CharSequence[ c.getCount() + 1 ];
               CharSequence[] entryValues = new CharSequence[ c.getCount() + 1 ];
               
               entries[ 0 ] = getContext().getResources()
                                          .getString( R.string.phr_none_f );
               entryValues[ 0 ] = Settings.NO_DEFAULT_LIST_ID;
               
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
                  setEntries( entries );
                  setEntryValues( entryValues );
               }
            }
            
            if ( c != null )
               c.close();
            
            client.release();
         }
         catch ( RemoteException e )
         {
            // TODO: Show error
         }
      }
   }   
}
