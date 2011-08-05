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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import dev.drsoran.moloko.content.RtmProvider;
import dev.drsoran.moloko.content.TransactionalAccess;
import dev.drsoran.provider.Rtm;


public abstract class AsyncInsertEntity< T > extends AsyncTask< T, Void, Uri >
{
   private final Activity activity;
   
   private ProgressDialog dialog;
   
   

   public AsyncInsertEntity( Activity activity )
   {
      if ( activity == null )
         throw new NullPointerException( "activity is null" );
      
      this.activity = activity;
   }
   


   protected abstract int getProgressMessageId();
   


   protected abstract List< ContentProviderOperation > getInsertOperations( ContentResolver contentResolver,
                                                                            T entity );
   


   protected abstract Uri getContentUri();
   


   protected abstract String getPath();
   


   @Override
   protected void onPreExecute()
   {
      dialog = new ProgressDialog( activity );
      dialog.setOwnerActivity( activity );
      dialog.setMessage( activity.getString( getProgressMessageId() ) );
      dialog.setCancelable( false );
      dialog.show();
   }
   


   @Override
   protected Uri doInBackground( T... entity )
   {
      if ( entity == null || entity.length < 1 )
         return null;
      
      final ContentProvider provider = activity.getContentResolver()
                                               .acquireContentProviderClient( Rtm.AUTHORITY )
                                               .getLocalContentProvider();
      
      if ( !( provider instanceof RtmProvider ) )
         return null;
      
      final List< ContentProviderOperation > operations = getInsertOperations( activity.getContentResolver(),
                                                                               entity[ 0 ] );
      
      if ( operations == null )
         return null;
      
      final RtmProvider rtmProvider = (RtmProvider) provider;
      final TransactionalAccess transactionalAccess = rtmProvider.newTransactionalAccess();
      
      String newEntityId = null;
      
      try
      {
         transactionalAccess.beginTransaction();
         
         final ContentProviderResult[] res = provider.applyBatch( new ArrayList< ContentProviderOperation >( operations ) );
         final String path = getPath();
         
         for ( int i = 0, cnt = res.length; newEntityId == null && i < cnt; ++i )
         {
            final Pattern pattern = Pattern.compile( "/" + path + "/(.*)" );
            
            final ContentProviderResult contentProviderResult = res[ i ];
            
            if ( contentProviderResult != null
               && contentProviderResult.uri != null )
            {
               final Uri uri = contentProviderResult.uri;
               final String uriPath = uri.getPath();
               
               if ( uriPath != null )
               {
                  final Matcher matcher = pattern.matcher( uriPath );
                  if ( matcher.matches() && matcher.groupCount() == 1 )
                     newEntityId = matcher.group( 1 );
               }
            }
         }
         
         transactionalAccess.setTransactionSuccessful();
      }
      catch ( Throwable e )
      {
         Log.e( LogUtils.toTag( AsyncInsertEntity.class ),
                "Inserting new entity failed",
                e );
         return null;
      }
      finally
      {
         transactionalAccess.endTransaction();
      }
      
      return Queries.contentUriWithId( getContentUri(), newEntityId );
   }
   


   @Override
   protected void onPostExecute( Uri result )
   {
      if ( dialog != null )
         dialog.dismiss();
   }
}
