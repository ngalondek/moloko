/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.moloko.app.prefs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.OperationApplicationException;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.Toast;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.IRtmProviderPart;
import dev.drsoran.moloko.content.RtmProvider;
import dev.drsoran.provider.Rtm;


class ClearDbPreference extends InfoTextPreference
{
   public ClearDbPreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );
   }
   
   
   
   @Override
   protected void onClick()
   {
      final ContentProviderClient client = getContext().getContentResolver()
                                                       .acquireContentProviderClient( Rtm.AUTHORITY );
      if ( client != null
         && ( client.getLocalContentProvider() instanceof RtmProvider ) )
      {
         final List< IRtmProviderPart > parts = ( (RtmProvider) client.getLocalContentProvider() ).getMutableParts();
         final CharSequence[] partNames = new CharSequence[ parts.size() ];
         final boolean[] checked = new boolean[ parts.size() ];
         Arrays.fill( checked, true );
         
         for ( int i = 0; i < partNames.length; ++i )
         {
            partNames[ i ] = parts.get( i ).getTableName();
         }
         
         new AlertDialog.Builder( getContext() ).setTitle( R.string.moloko_prefs_clear_db_dlg_title )
                                                .setIcon( R.drawable.ic_prefs_delete )
                                                .setMultiChoiceItems( partNames,
                                                                      checked,
                                                                      new OnMultiChoiceClickListener()
                                                                      {
                                                                         @Override
                                                                         public void onClick( DialogInterface dialog,
                                                                                              int which,
                                                                                              boolean isChecked )
                                                                         {
                                                                            checked[ which ] = isChecked;
                                                                         }
                                                                      } )
                                                .setPositiveButton( android.R.string.yes,
                                                                    new OnClickListener()
                                                                    {
                                                                       @Override
                                                                       public void onClick( DialogInterface dialog,
                                                                                            int which )
                                                                       {
                                                                          final List< IRtmProviderPart > clearList = new LinkedList< IRtmProviderPart >();
                                                                          
                                                                          for ( int i = 0; i < checked.length; ++i )
                                                                          {
                                                                             if ( checked[ i ] )
                                                                                clearList.add( parts.get( i ) );
                                                                          }
                                                                          if ( clearList.size() > 0 )
                                                                             clear( clearList );
                                                                       }
                                                                    } )
                                                .setNegativeButton( android.R.string.no,
                                                                    null )
                                                .setCancelable( true )
                                                .show();
      }
   }
   
   
   
   private void clear( final List< ? extends IRtmProviderPart > parts )
   {
      final AsyncTask< Void, Void, Void > task = new AsyncTask< Void, Void, Void >()
      {
         private Dialog dialog;
         
         private final AtomicBoolean failed = new AtomicBoolean();
         
         
         
         @Override
         protected void onPreExecute()
         {
            dialog = ProgressDialog.show( getContext(),
                                          null,
                                          getContext().getString( R.string.moloko_prefs_clear_db_dlg_clearing ),
                                          true,
                                          false );
         }
         
         
         
         @Override
         protected Void doInBackground( Void... params )
         {
            final ContentProviderClient client = getContext().getContentResolver()
                                                             .acquireContentProviderClient( Rtm.AUTHORITY );
            
            if ( client != null
               && ( client.getLocalContentProvider() instanceof RtmProvider ) )
            {
               final RtmProvider rtmProvider = (RtmProvider) client.getLocalContentProvider();
               
               try
               {
                  rtmProvider.clear( parts );
               }
               catch ( OperationApplicationException e )
               {
                  failed.set( true );
               }
               
               client.release();
            }
            
            return null;
         }
         
         
         
         @Override
         protected void onPostExecute( Void result )
         {
            if ( dialog != null )
               dialog.dismiss();
            
            dialog = null;
            
            Toast.makeText( getContext(),
                            failed.get()
                                        ? R.string.moloko_prefs_clear_db_toast_clearing_failed
                                        : R.string.moloko_prefs_clear_db_toast_cleared,
                            Toast.LENGTH_LONG )
                 .show();
         }
         
      };
      
      getAppContext().getExecutorService().execute( task );
   }
}
