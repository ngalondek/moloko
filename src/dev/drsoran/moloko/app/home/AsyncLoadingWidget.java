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


abstract class AsyncLoadingWidget< T > implements IMolokoHomeWidget
{
   private final Context context;
   
   private View view;
   
   private AsyncTask< Void, Void, T > query;
   
   private boolean needsReload = true;
   
   private final int layoutResId;
   
   private final int switchResId;
   
   
   
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
      if ( convertView == null )
      {
         convertView = LayoutInflater.from( context ).inflate( layoutResId,
                                                               null );
      }
      view = convertView;
      
      if ( needsReload )
      {
         asyncReload();
      }
      
      initializeNonLoadables( view );
      
      return view;
   }
   
   
   
   private void asyncReload()
   {
      stop();
      
      final View loadingView = view.findViewById( R.id.loading_spinner );
      loadingView.setVisibility( View.VISIBLE );
      
      final View switchView;
      if ( switchResId != -1 )
      {
         switchView = view.findViewById( switchResId );
         switchView.setVisibility( View.INVISIBLE );
      }
      else
      {
         switchView = null;
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
            loadingView.setVisibility( View.GONE );
            
            query = null;
            needsReload = false;
            
            if ( switchView != null )
            {
               setSwitchViewData( switchView, result );
            }
         }
      };
      
      SystemContext.get( context ).getExecutorService().execute( query );
   }
   
   
   
   protected abstract void initializeNonLoadables( View view );
   
   
   
   protected abstract T doBackgroundQuery();
   
   
   
   protected abstract void setSwitchViewData( View switchView, T data );
}
