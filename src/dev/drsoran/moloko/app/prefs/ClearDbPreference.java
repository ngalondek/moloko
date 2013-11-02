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
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.Toast;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.ContentAuthority;
import dev.drsoran.moloko.content.db.DbContentProvider;
import dev.drsoran.moloko.content.db.ITable;


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
                                                       .acquireContentProviderClient( ContentAuthority.RTM );
      if ( client != null
         && ( client.getLocalContentProvider() instanceof DbContentProvider ) )
      {
         final List< ? extends ITable > tables = ( (DbContentProvider) client.getLocalContentProvider() ).getTables();
         
         final CharSequence[] tableNames = new CharSequence[ tables.size() ];
         final boolean[] checked = new boolean[ tables.size() ];
         Arrays.fill( checked, true );
         
         for ( int i = 0; i < tables.size(); ++i )
         {
            tableNames[ i ] = tables.get( i ).getTableName();
         }
         
         new AlertDialog.Builder( getContext() ).setTitle( R.string.moloko_prefs_clear_db_dlg_title )
                                                .setIcon( R.drawable.ic_prefs_delete )
                                                .setMultiChoiceItems( tableNames,
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
                                                                          final List< ITable > clearList = new LinkedList< ITable >();
                                                                          
                                                                          for ( int i = 0; i < checked.length; ++i )
                                                                          {
                                                                             if ( checked[ i ] )
                                                                             {
                                                                                clearList.add( tables.get( i ) );
                                                                             }
                                                                          }
                                                                          
                                                                          if ( clearList.size() > 0 )
                                                                          {
                                                                             clear( clearList );
                                                                          }
                                                                       }
                                                                    } )
                                                .setNegativeButton( android.R.string.no,
                                                                    null )
                                                .setCancelable( true )
                                                .show();
      }
   }
   
   
   
   private void clear( final List< ITable > tablesToClear )
   {
      final AsyncTask< ITable, Void, Void > task = new AsyncTask< ITable, Void, Void >()
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
         protected Void doInBackground( ITable... params )
         {
            for ( ITable table : tablesToClear )
            {
               table.clear();
            }
            
            return null;
         }
         
         
         
         @Override
         protected void onPostExecute( Void result )
         {
            if ( dialog != null )
            {
               dialog.dismiss();
               dialog = null;
            }
            
            Toast.makeText( getContext(),
                            failed.get()
                                        ? R.string.moloko_prefs_clear_db_toast_clearing_failed
                                        : R.string.moloko_prefs_clear_db_toast_cleared,
                            Toast.LENGTH_LONG )
                 .show();
         }
         
      };
      
      final ITable[] tablesArray = new ITable[ tablesToClear.size() ];
      
      getAppContext().getExecutorService()
                     .execute( task, tablesToClear.toArray( tablesArray ) );
   }
}
