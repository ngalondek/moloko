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

package dev.drsoran.moloko.loaders;

import java.util.concurrent.atomic.AtomicBoolean;

import android.content.ContentProviderClient;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import dev.drsoran.moloko.util.LogUtils;


public abstract class AbstractLoader< D > extends AsyncTaskLoader< D >
{
   private final ForceLoadContentObserver observer = new ForceLoadContentObserver();
   
   private volatile D result;
   
   private final AtomicBoolean respectContentChanges = new AtomicBoolean( true );
   
   
   
   protected AbstractLoader( Context context )
   {
      super( context );
   }
   
   
   
   public void setRespectContentChanges( boolean respect )
   {
      respectContentChanges.set( respect );
      
      if ( respect )
         registerContentObserver( observer );
      else
         unregisterContentObserver( observer );
   }
   
   
   
   @Override
   public D loadInBackground()
   {
      D result = null;
      
      final ContentProviderClient client = getContentProviderClient();
      
      if ( client != null )
      {
         result = queryResultInBackground( client );
         
         client.release();
         
         if ( result != null && respectContentChanges.get() )
            registerContentObserver( observer );
      }
      else
      {
         LogUtils.logDBError( getContext(),
                              getClass(),
                              LogUtils.GENERIC_DB_ERROR );
      }
      
      return result;
   }
   
   
   
   protected ContentProviderClient getContentProviderClient()
   {
      return getContext().getContentResolver()
                         .acquireContentProviderClient( getContentUri() );
   }
   
   
   
   /**
    * Runs on the UI thread
    */
   @Override
   public void deliverResult( D result )
   {
      if ( isReset() )
      {
         // An async query came in while the loader is stopped
         if ( result != null )
         {
            clearResult( result );
         }
      }
      else
      {
         this.result = result;
         
         if ( isStarted() )
         {
            super.deliverResult( this.result );
         }
      }
   }
   
   
   
   /**
    * Must be called from the UI thread
    */
   @Override
   protected void onStartLoading()
   {
      if ( result != null )
      {
         deliverResult( result );
      }
      
      if ( takeContentChanged() || result == null )
      {
         forceLoad();
      }
   }
   
   
   
   /**
    * Must be called from the UI thread
    */
   @Override
   protected void onStopLoading()
   {
      // Attempt to cancel the current load task if possible.
      cancelLoad();
   }
   
   
   
   @Override
   public void onCanceled( D result )
   {
      if ( result != null )
      {
         clearResult( result );
      }
   }
   
   
   
   @Override
   protected void onReset()
   {
      super.onReset();
      
      // Ensure the loader is stopped
      onStopLoading();
      
      result = null;
   }
   
   
   
   abstract protected D queryResultInBackground( ContentProviderClient client );
   
   
   
   abstract protected Uri getContentUri();
   
   
   
   abstract protected void registerContentObserver( ContentObserver observer );
   
   
   
   abstract protected void unregisterContentObserver( ContentObserver observer );
   
   
   
   protected void clearResult( D result )
   {
   }
}
