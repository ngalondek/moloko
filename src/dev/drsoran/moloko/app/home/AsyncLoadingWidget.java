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

package dev.drsoran.moloko.app.home;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.SystemContext;


abstract class AsyncLoadingWidget< T > implements INavWidget
{
   private final Context context;
   
   private final int layoutResId;
   
   private final int switchResId;
   
   private View loadingView;
   
   private View switchView;
   
   private AsyncTask< Void, Void, T > query;
   
   private T loadingData;
   
   private boolean needsReload = true;
   
   
   
   protected AsyncLoadingWidget( Context context, int layoutResId,
      int switchResId )
   {
      this.context = context;
      this.layoutResId = layoutResId;
      this.switchResId = switchResId;
   }
   
   
   
   public Context getContext()
   {
      return context;
   }
   
   
   
   @Override
   public void setDirty()
   {
      needsReload = true;
   }
   
   
   
   @Override
   public void stop()
   {
      if ( query != null )
      {
         query.cancel( true );
      }
      
      query = null;
   }
   
   
   
   @Override
   public final View getView( View convertView )
   {
      convertView = LayoutInflater.from( context ).inflate( layoutResId, null );
      
      loadingView = convertView.findViewById( R.id.loading_spinner );
      switchView = convertView.findViewById( switchResId );
      
      initializeNonLoadables( convertView );
      
      if ( needsReload )
      {
         asyncReload();
      }
      else
      {
         initializeLoadables();
      }
      
      return convertView;
   }
   
   
   
   private void asyncReload()
   {
      stop();
      
      if ( loadingView != null )
      {
         loadingView.setVisibility( View.VISIBLE );
      }
      
      if ( switchView != null )
      {
         switchView.setVisibility( View.INVISIBLE );
      }
      
      query = new AsyncTask< Void, Void, T >()
      {
         @Override
         protected T doInBackground( Void... params )
         {
            return doBackgroundQuery();
         }
         
         
         
         @Override
         protected void onPostExecute( T result )
         {
            loadingData = result;
            initializeLoadables();
         }
         
      };
      
      SystemContext.get( context ).getExecutorService().execute( query );
   }
   
   
   
   private void initializeLoadables()
   {
      if ( loadingView != null )
      {
         loadingView.setVisibility( View.GONE );
      }
      
      query = null;
      needsReload = false;
      
      if ( switchView != null )
      {
         initializeSwitchView( switchView, loadingData );
      }
   }
   
   
   
   protected abstract void initializeNonLoadables( View view );
   
   
   
   protected abstract T doBackgroundQuery();
   
   
   
   protected abstract void initializeSwitchView( View switchView, T data );
}
