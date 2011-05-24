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
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import dev.drsoran.moloko.R;


abstract class AsyncLoadingHomeWidget extends LinearLayout implements
         IMolokoHomeWidget
{
   private AsyncTask< Void, Void, Cursor > query;
   
   

   public AsyncLoadingHomeWidget( Context context, AttributeSet attrs )
   {
      super( context, attrs );
   }
   


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
      
      query = new AsyncTask< Void, Void, Cursor >()
      {
         @Override
         protected Cursor doInBackground( Void... params )
         {
            return doBackgroundQuery();
         }
         


         @Override
         protected void onPostExecute( Cursor result )
         {
            handleAsyncResult( contentView, result );
            
            if ( result != null )
               result.close();
            
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
      
      query = new AsyncTask< Void, Void, Cursor >()
      {
         @Override
         protected Cursor doInBackground( Void... params )
         {
            return doBackgroundQuery();
         }
         


         @Override
         protected void onPostExecute( Cursor result )
         {
            handleAsyncResult( contentView, result );
            
            if ( result != null )
               result.close();
            
            query = null;
         }
      }.execute();
   }
   


   protected abstract Cursor doBackgroundQuery();
   


   protected void handleAsyncResult( View v, Cursor c )
   {
      if ( c != null )
         ( (TextView) v ).setText( String.valueOf( c.getCount() ) );
      else
         ( (TextView) v ).setText( "?" );
   }
}
