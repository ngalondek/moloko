/* 
 *	Copyright (c) 2014 Ronny Röhricht
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

import java.util.List;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.loaders.TasksListsLoader;
import dev.drsoran.moloko.domain.DomainContext;
import dev.drsoran.moloko.domain.model.TasksList;


public class ListsWidget implements INavWidget,
         LoaderCallbacks< List< TasksList > >
{
   private final LoaderManager loaderManager;
   
   private boolean needsReload = true;
   
   private final Context context;
   
   
   
   public ListsWidget( Context context, LoaderManager loaderManager )
   {
      this.context = context;
      this.loaderManager = loaderManager;
   }
   
   
   
   @Override
   public void setDirty()
   {
      needsReload = true;
   }
   
   
   
   @Override
   public void stop()
   {
      loaderManager.destroyLoader( TasksListsLoader.ID );
   }
   
   
   
   @Override
   public View getView( View convertView )
   {
      convertView = LayoutInflater.from( context )
                                  .inflate( R.layout.home_activity_drawer_lists_widget,
                                            null );
      
      if ( needsReload )
      {
         loaderManager.initLoader( TasksListsLoader.ID, Bundle.EMPTY, this );
      }
      
      return convertView;
   }
   
   
   
   @Override
   public Intent getIntent()
   {
      return null;
   }
   
   
   
   @Override
   public Loader< List< TasksList >> onCreateLoader( int id, Bundle args )
   {
      return new TasksListsLoader( DomainContext.get( context ), true );
   }
   
   
   
   @Override
   public void onLoadFinished( Loader< List< TasksList >> loader,
                               List< TasksList > data )
   {
      needsReload = data != null;
   }
   
   
   
   @Override
   public void onLoaderReset( Loader< List< TasksList >> loader )
   {
      needsReload = true;
   }
}
