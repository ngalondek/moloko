package dev.drsoran.moloko.prefs;

import android.content.ContentProviderClient;
import android.content.Context;
import android.database.Cursor;
import android.os.RemoteException;
import android.util.AttributeSet;
import dev.drsoran.moloko.R;
import dev.drsoran.provider.Rtm.Lists;


public class DefaultListPreference extends SyncableListPreference
{
   
   public DefaultListPreference( Context context, AttributeSet attrs )
   {
      // TODO: Connect the preference to changes of list column
      
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
               CharSequence[] entries = new CharSequence[ c.getCount() ];
               CharSequence[] entryValues = new CharSequence[ c.getCount() ];
               
               setEnabled( c.getCount() > 0 );
               
               if ( c.getCount() > 0 )
               {
                  for ( ok = c.moveToFirst(); ok && !c.isAfterLast(); c.moveToNext() )
                  {
                     entryValues[ c.getPosition() ] = c.getString( 0 );
                     entries[ c.getPosition() ] = c.getString( 1 );
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
         }
         catch ( RemoteException e )
         {
            // TODO: Show error
         }
      }
   }
   


   @Override
   public CharSequence getSummary()
   {
      final CharSequence summary = super.getSummary();
      final CharSequence entry = getEntry();
      
      if ( summary == null || entry == null )
      {
         return String.format( summary.toString(),
                               getContext().getString( R.string.phr_none_f ) );
      }
      else
      {
         return String.format( summary.toString(), entry.toString() );
      }
   }
   
}
