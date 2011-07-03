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

package dev.drsoran.moloko.fragments;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.os.Bundle;
import dev.drsoran.provider.Rtm.Tasks;


public class SelectableTasksListsFragment extends MinDetailedTasksListFragment
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + SelectableTasksListsFragment.class.getSimpleName();
   
   private final static IntentFilter INTENT_FILTER;
   
   static
   {
      try
      {
         INTENT_FILTER = new IntentFilter( Intent.ACTION_PICK,
                                           "vnd.android.cursor.dir/vnd.rtm.task" );
         INTENT_FILTER.addCategory( Intent.CATEGORY_DEFAULT );
      }
      catch ( MalformedMimeTypeException e )
      {
         throw new RuntimeException( e );
      }
   }
   
   
   
   public static SelectableTasksListsFragment newInstance( Bundle configuration )
   {
      final SelectableTasksListsFragment fragment = new SelectableTasksListsFragment();
      
      fragment.setArguments( configuration );
      
      return fragment;
   }
   
   
   
   public static IntentFilter getIntentFilter()
   {
      return INTENT_FILTER;
   }
   
   
   
   @Override
   public Intent newDefaultIntent()
   {
      return new Intent( INTENT_FILTER.getAction( 0 ), Tasks.CONTENT_URI );
   }
   
}
