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

package dev.drsoran.moloko.ui.widgets;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.ui.UiContext;


abstract class AsyncLoadingCounterBubbleHomeWidget extends LinearLayout
         implements IMolokoHomeWidget
{
   private final UiContext uiContext;
   
   private AsyncTask< Void, Void, Integer > query;
   
   
   
   protected AsyncLoadingCounterBubbleHomeWidget( Context context,
      AttributeSet attrs, int defStyle )
   {
      super( context, attrs, defStyle );
      uiContext = UiContext.get( context );
   }
   
   
   
   public AsyncLoadingCounterBubbleHomeWidget( Context context,
      AttributeSet attrs )
   {
      this( context, attrs, 0 );
   }
   
   
   
   public AsyncLoadingCounterBubbleHomeWidget( Context context )
   {
      this( context, null );
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
      {
         query.cancel( true );
      }
      
      final View loadingView = findViewById( R.id.loading );
      loadingView.setVisibility( View.VISIBLE );
      
      final TextView counterBubbleView = (TextView) findViewById( R.id.content );
      counterBubbleView.setVisibility( View.GONE );
      
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
            setCounterBubbleValue( counterBubbleView, result );
            
            query = null;
         }
      };
      
      uiContext.asSystemContext().getExecutorService().execute( query );
   }
   
   
   
   protected void asyncReloadWithoutSpinner()
   {
      if ( query != null )
      {
         query.cancel( true );
      }
      
      final View loadingView = findViewById( R.id.loading );
      loadingView.setVisibility( View.GONE );
      
      final TextView counterBubbleView = (TextView) findViewById( R.id.content );
      counterBubbleView.setVisibility( View.VISIBLE );
      
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
            setCounterBubbleValue( counterBubbleView, result );
            query = null;
         }
      };
      
      uiContext.asSystemContext().getExecutorService().execute( query );
   }
   
   
   
   public UiContext getUiContext()
   {
      return uiContext;
   }
   
   
   
   protected abstract Integer doBackgroundQuery();
   
   
   
   private void setCounterBubbleValue( TextView counterBubbleView,
                                       Integer tasksCount )
   {
      if ( tasksCount != null )
      {
         counterBubbleView.setText( String.valueOf( tasksCount ) );
         counterBubbleView.setVisibility( tasksCount > 0 ? View.VISIBLE
                                                        : View.GONE );
      }
      else
      {
         counterBubbleView.setText( "?" );
         counterBubbleView.setVisibility( View.GONE );
      }
   }
}
