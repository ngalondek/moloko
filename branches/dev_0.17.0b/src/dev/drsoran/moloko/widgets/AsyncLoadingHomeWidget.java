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

package dev.drsoran.moloko.widgets;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import dev.drsoran.moloko.R;


abstract class AsyncLoadingHomeWidget extends LinearLayout implements
         IMolokoHomeWidget
{
   private AsyncTask< Void, Void, Integer > query;
   
   
   
   public AsyncLoadingHomeWidget( Context context, AttributeSet attrs,
      int defStyle )
   {
      super( context, attrs, defStyle );
   }
   
   
   
   public AsyncLoadingHomeWidget( Context context, AttributeSet attrs )
   {
      super( context, attrs );
   }
   
   
   
   public AsyncLoadingHomeWidget( Context context )
   {
      super( context );
   }
   
   
   
   @Override
   public void stop()
   {
      if ( query != null )
         query.cancel( true );
      
      query = null;
   }
   
   
   
   protected void asyncReload()
   {
      if ( query != null )
         query.cancel( true );
      
      final View loadingView = findViewById( R.id.loading );
      loadingView.setVisibility( View.VISIBLE );
      
      final View contentView = findViewById( R.id.content );
      contentView.setVisibility( View.GONE );
      
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
            handleAsyncResult( contentView, result );
            
            loadingView.setVisibility( View.GONE );
            contentView.setVisibility( View.VISIBLE );
            
            query = null;
         }
      }.execute();
   }
   
   
   
   protected void asyncReloadWithoutSpinner()
   {
      if ( query != null )
         query.cancel( true );
      
      final View loadingView = findViewById( R.id.loading );
      loadingView.setVisibility( View.GONE );
      
      final View contentView = findViewById( R.id.content );
      contentView.setVisibility( View.VISIBLE );
      
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
            handleAsyncResult( contentView, result );
            
            query = null;
         }
      }.execute();
   }
   
   
   
   protected abstract Integer doBackgroundQuery();
   
   
   
   protected void handleAsyncResult( View v, Integer c )
   {
      if ( c != null )
         ( (TextView) v ).setText( String.valueOf( c ) );
      else
         ( (TextView) v ).setText( "?" );
   }
}
