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

package dev.drsoran.moloko.fragments.factories;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import dev.drsoran.moloko.fragments.AbstractTaskListFragment;
import dev.drsoran.moloko.fragments.FullDetailedTasksListFragment;
import dev.drsoran.moloko.fragments.MinDetailedTasksListFragment;
import dev.drsoran.moloko.fragments.SelectableTasksListsFragment;


public final class TasksListFragmentFactory
{
   private final static String TAG = "Moloko."
      + TasksListFragmentFactory.class.getSimpleName();
   
   private final static List< Class< ? extends AbstractTaskListFragment > > FRAGMENT_CLASSES = new ArrayList< Class< ? extends AbstractTaskListFragment > >();
   
   static
   {
      FRAGMENT_CLASSES.add( FullDetailedTasksListFragment.class );
      FRAGMENT_CLASSES.add( MinDetailedTasksListFragment.class );
      FRAGMENT_CLASSES.add( SelectableTasksListsFragment.class );
   }
   
   
   
   public final static Fragment newFragment( Context context, Intent intent )
   {
      Fragment fragment = null;
      
      try
      {
         for ( Iterator< Class< ? extends AbstractTaskListFragment > > i = FRAGMENT_CLASSES.iterator(); i.hasNext()
            && fragment == null; )
         {
            final Class< ? extends AbstractTaskListFragment > entry = i.next();
            
            final IntentFilter filter = (IntentFilter) entry.getMethod( "getIntentFilter" )
                                                            .invoke( null );
            
            if ( filter.matchAction( intent.getAction() )
               && filter.matchData( intent.resolveType( context ),
                                    intent.getScheme(),
                                    intent.getData() ) > 0 )
            {
               fragment = (Fragment) entry.getMethod( "newInstance",
                                                      Bundle.class )
                                          .invoke( null, intent.getExtras() );
            }
         }
      }
      catch ( Throwable e )
      {
         Log.e( TAG,
                "Unable to instantiate new fragment by Intent " + intent,
                e );
      }
      
      return fragment;
   }
}
