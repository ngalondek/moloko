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
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.ui.UiContext;


abstract class AsyncLoadingCounterHomeWidget implements IMolokoHomeWidget
{
   private final UiContext uiContext;
   
   private final int resId;
   
   private View view;
   
   private AsyncTask< Void, Void, Integer > query;
   
   private boolean needsReload = true;
   
   
   
   public AsyncLoadingCounterHomeWidget( Context context, int resId )
   {
      this.uiContext = UiContext.get( context );
      this.resId = resId;
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
         convertView = LayoutInflater.from( uiContext.getBaseContext() )
                                     .inflate( resId, null );
      }
      view = convertView;
      
      if ( needsReload )
      {
         asyncReload();
      }
      
      initializeNonLoadables( view );
      
      return view;
   }
   
   
   
   protected void asyncReload()
   {
      stop();
      
      final View loadingView = view.findViewById( R.id.loading );
      loadingView.setVisibility( View.VISIBLE );
      
      final TextView counterView = (TextView) view.findViewById( R.id.numTasks );
      counterView.setVisibility( View.GONE );
      
      query = new AsyncTask< Void, Void, Integer >()
      {
         @Override
         protected Integer doInBackground( Void... params )
         {
            return doBackgroundQuery();
         }
         
         
         
         @Override
         protected void onPostExecute( Integer result )
         {
            loadingView.setVisibility( View.GONE );
            setTasksCount( counterView, result );
            
            query = null;
            needsReload = false;
         }
      };
      
      uiContext.asSystemContext().getExecutorService().execute( query );
   }
   
   
   
   protected void asyncReloadWithoutSpinner()
   {
      stop();
      
      final View loadingView = view.findViewById( R.id.loading );
      loadingView.setVisibility( View.GONE );
      
      final TextView counterView = (TextView) view.findViewById( R.id.numTasks );
      counterView.setVisibility( View.VISIBLE );
      
      query = new AsyncTask< Void, Void, Integer >()
      {
         @Override
         protected Integer doInBackground( Void... params )
         {
            return doBackgroundQuery();
         }
         
         
         
         @Override
         protected void onPostExecute( Integer result )
         {
            setTasksCount( counterView, result );
            
            query = null;
            needsReload = false;
         }
      };
      
      uiContext.asSystemContext().getExecutorService().execute( query );
   }
   
   
   
   public UiContext getUiContext()
   {
      return uiContext;
   }
   
   
   
   protected abstract void initializeNonLoadables( View view );
   
   
   
   protected abstract Integer doBackgroundQuery();
   
   
   
   private void setTasksCount( TextView counterView, Integer tasksCount )
   {
      if ( tasksCount != null )
      {
         counterView.setText( String.valueOf( tasksCount ) );
         counterView.setVisibility( tasksCount > 0 ? View.VISIBLE : View.GONE );
      }
      else
      {
         counterView.setText( "?" );
         counterView.setVisibility( View.GONE );
      }
   }
}
