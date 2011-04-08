/* 
 *	Copyright (c) 2011 Ronny Röhricht
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

package dev.drsoran.moloko.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ProgressDialog;
import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.RtmProvider;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.content.TransactionalAccess;
import dev.drsoran.provider.Rtm;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.Task;


public class AsyncInsertTask extends AsyncTask< Task, Void, Uri >
{
   private final Context context;
   
   private ProgressDialog dialog;
   
   

   public AsyncInsertTask( Context context )
   {
      if ( context == null )
         throw new NullPointerException( "context is null" );
      
      this.context = context;
   }
   


   @Override
   protected void onPreExecute()
   {
      dialog = new ProgressDialog( context );
      dialog.setMessage( context.getString( R.string.dlg_insert_task ) );
      dialog.setCancelable( false );
      dialog.show();
   }
   


   @Override
   protected Uri doInBackground( Task... task )
   {
      if ( task == null || task.length < 1 )
         return null;
      
      final ContentProvider provider = context.getContentResolver()
                                              .acquireContentProviderClient( Rtm.AUTHORITY )
                                              .getLocalContentProvider();
      
      if ( !( provider instanceof RtmProvider ) )
         return null;
      
      final List< ContentProviderOperation > operations = TasksProviderPart.insertTask( context.getContentResolver(),
                                                                                        task[ 0 ] );
      
      if ( operations == null )
         return null;
      
      final RtmProvider rtmProvider = (RtmProvider) provider;
      final TransactionalAccess transactionalAccess = rtmProvider.newTransactionalAccess();
      
      String newTaskId = null;
      
      try
      {
         transactionalAccess.beginTransaction();
         
         final ContentProviderResult[] res = provider.applyBatch( new ArrayList< ContentProviderOperation >( operations ) );
         
         for ( int i = 0, cnt = res.length; newTaskId == null && i < cnt; ++i )
         {
            final Pattern pattern = Pattern.compile( "/" + RawTasks.PATH
               + "/(.*)" );
            
            final ContentProviderResult contentProviderResult = res[ i ];
            
            if ( contentProviderResult != null
               && contentProviderResult.uri != null )
            {
               final Uri uri = contentProviderResult.uri;
               final String path = uri.getPath();
               
               if ( path != null )
               {
                  final Matcher matcher = pattern.matcher( path );
                  if ( matcher.matches() && matcher.groupCount() == 1 )
                     newTaskId = matcher.group( 1 );
               }
            }
         }
         
         transactionalAccess.setTransactionSuccessful();
      }
      catch ( Throwable e )
      {
         Log.e( LogUtils.toTag( AsyncInsertTask.class ),
                "Inserting new task failed",
                e );
         return null;
      }
      finally
      {
         transactionalAccess.endTransaction();
      }
      
      return Queries.contentUriWithId( Tasks.CONTENT_URI, newTaskId );
   }
   


   @Override
   protected void onPostExecute( Uri result )
   {
      if ( dialog != null )
         dialog.dismiss();
   }
}
