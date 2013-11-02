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

package dev.drsoran.moloko.app.prefs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.AttributeSet;
import android.widget.Toast;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.db.RtmDatabase;


class SendDbPreference extends InfoTextPreference
{
   private final Context context;
   
   private File tempRtmDbFile;
   
   
   
   public SendDbPreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      this.context = context;
   }
   
   
   
   @Override
   public void checkEnabled()
   {
      super.checkEnabled();
      
      final boolean enabled = checkForApiVersionGreater8()
         && checkForExternalStorage();
      
      setEnabled( enabled );
   }
   
   
   
   @Override
   public void cleanUp()
   {
      super.cleanUp();
      deleteExternalStoragePrivateFile();
   }
   
   
   
   @Override
   protected void onClick()
   {
      final File rtmDb = context.getDatabasePath( RtmDatabase.DATABASE_NAME );
      
      if ( rtmDb != null && rtmDb.canRead() )
      {
         tempRtmDbFile = null;
         
         try
         {
            tempRtmDbFile = copyDbToExternalStoragePrivateFile( rtmDb );
            final Intent sendDbIntent = new Intent( Intent.ACTION_SEND );
            
            sendDbIntent.putExtra( Intent.EXTRA_EMAIL, new String[]
            { context.getString( R.string.send_log_address ) } );
            sendDbIntent.putExtra( Intent.EXTRA_SUBJECT, "Moloko database" );
            sendDbIntent.putExtra( Intent.EXTRA_TEXT,
                                   "Please find my Moloko database attached." );
            sendDbIntent.putExtra( Intent.EXTRA_STREAM,
                                   Uri.fromFile( tempRtmDbFile ) );
            sendDbIntent.setType( "application/db" );
            
            context.startActivity( Intent.createChooser( sendDbIntent,
                                                         context.getString( R.string.moloko_prefs_send_db_text ) ) );
         }
         catch ( Throwable e )
         {
            Log().e( getClass(), "Sending DB failed", e );
            
            deleteExternalStoragePrivateFile();
            
            Toast.makeText( context,
                            R.string.moloko_prefs_send_db_toast_failed,
                            Toast.LENGTH_LONG ).show();
         }
      }
      else
      {
         Toast.makeText( context,
                         R.string.moloko_prefs_send_db_toast_failed,
                         Toast.LENGTH_LONG ).show();
         
         Log().e( getClass(), "Sending DB failed, no access" );
      }
   }
   
   
   
   private File copyDbToExternalStoragePrivateFile( File rtmDb ) throws IOException
   {
      final File tempRtmDbFile = new File( context.getExternalFilesDir( null ),
                                           RtmDatabase.DATABASE_NAME );
      InputStream is = null;
      OutputStream os = null;
      
      try
      {
         is = new FileInputStream( rtmDb );
         os = new FileOutputStream( tempRtmDbFile );
         
         // TODO: Do we need an array here?
         final byte[] data = new byte[ is.available() ];
         is.read( data );
         os.write( data );
      }
      finally
      {
         is.close();
         os.close();
      }
      
      return tempRtmDbFile;
   }
   
   
   
   private void deleteExternalStoragePrivateFile()
   {
      if ( tempRtmDbFile != null )
      {
         tempRtmDbFile.delete();
         tempRtmDbFile = null;
      }
   }
   
   
   
   private boolean checkForExternalStorage()
   {
      return Environment.getExternalStorageState()
                        .equals( Environment.MEDIA_MOUNTED );
   }
   
   
   
   private boolean checkForApiVersionGreater8()
   {
      return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
   }
}
