/* 
 *	Copyright (c) 2012 Ronny Röhricht
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

import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.util.Log;
import dev.drsoran.moloko.util.LogUtils;


abstract class AbstractIntentFragmentFactory
{
   protected AbstractIntentFragmentFactory()
   {
      throw new AssertionError();
   }
   
   
   
   protected final static Fragment resolveIntentToFragment( Context context,
                                                            Intent intent,
                                                            List< Class< ? extends Fragment > > fragmentClasses )
   {
      Fragment fragment = null;
      
      try
      {
         for ( Iterator< Class< ? extends Fragment > > i = fragmentClasses.iterator(); i.hasNext()
            && fragment == null; )
         {
            final Class< ? extends Fragment > entry = i.next();
            
            final IntentFilter filter = (IntentFilter) entry.getMethod( "getIntentFilter" )
                                                            .invoke( null );
            
            if ( filter.matchAction( intent.getAction() )
               && filter.matchData( intent.resolveType( context ),
                                    intent.getScheme(),
                                    intent.getData() ) > 0 )
            {
               fragment = DefaultFragmentFactory.create( context,
                                                         entry,
                                                         intent.getExtras() );
            }
         }
      }
      catch ( Throwable e )
      {
         Log.e( LogUtils.toTag( AbstractIntentFragmentFactory.class ),
                "Unable to instantiate new fragment by Intent " + intent,
                e );
      }
      
      return fragment;
   }
}
