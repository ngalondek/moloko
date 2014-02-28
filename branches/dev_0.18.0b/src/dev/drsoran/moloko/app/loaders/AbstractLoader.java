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

package dev.drsoran.moloko.app.loaders;

import java.util.concurrent.atomic.AtomicBoolean;

import android.database.ContentObserver;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import dev.drsoran.moloko.domain.DomainContext;
import dev.drsoran.moloko.domain.services.ContentException;
import dev.drsoran.moloko.domain.services.IContentRepository;


public abstract class AbstractLoader< D > extends AsyncTaskLoader< D >
{
   private final ForceLoadContentObserver observer = new ForceLoadContentObserver();
   
   private final AtomicBoolean respectContentChanges = new AtomicBoolean( true );
   
   private final IContentRepository contentRepository;
   
   private volatile D result;
   
   private volatile ContentException contentException;
   
   
   
   protected AbstractLoader( DomainContext context )
   {
      super( context );
      this.contentRepository = context.getContentRepository();
   }
   
   
   
   public boolean hasContentException()
   {
      return contentException != null;
   }
   
   
   
   // TODO: Handle contentException
   public ContentException getContentException()
   {
      return contentException;
   }
   
   
   
   public void throwContentExceptionOnError() throws ContentException
   {
      if ( contentException != null )
      {
         throw contentException;
      }
   }
   
   
   
   public void clearContentException()
   {
      contentException = null;
   }
   
   
   
   public void setRespectContentChanges( boolean respect )
   {
      respectContentChanges.set( respect );
      
      if ( respect )
      {
         registerContentObserver( observer );
      }
      else
      {
         unregisterContentObserver( observer );
      }
   }
   
   
   
   @Override
   public D loadInBackground()
   {
      clearContentException();
      
      D result = null;
      
      try
      {
         result = queryResultInBackground( contentRepository );
         
         if ( result != null && respectContentChanges.get() )
         {
            registerContentObserver( observer );
         }
      }
      catch ( ContentException e )
      {
         contentException = e;
      }
      
      return result;
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
         clearContentException();
      }
   }
   
   
   
   @Override
   protected void onReset()
   {
      // Ensure the loader is stopped
      onStopLoading();
      
      result = null;
      clearContentException();
      
      super.onReset();
   }
   
   
   
   protected void clearResult( D result )
   {
   }
   
   
   
   private void registerContentObserver( ContentObserver observer )
   {
      contentRepository.registerContentObserver( observer, getContentUri() );
   }
   
   
   
   private void unregisterContentObserver( ContentObserver observer )
   {
      contentRepository.unregisterContentObserver( observer );
   }
   
   
   
   abstract public Uri getContentUri();
   
   
   
   abstract protected D queryResultInBackground( IContentRepository repository ) throws ContentException;
}
