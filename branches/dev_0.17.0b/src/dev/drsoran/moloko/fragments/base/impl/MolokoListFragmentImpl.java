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

package dev.drsoran.moloko.fragments.base.impl;

import android.support.v4.app.ListFragment;
import android.view.View;


public class MolokoListFragmentImpl extends ConfigurableFragmentImpl
{
   
   public MolokoListFragmentImpl( ListFragment fragment, int settingsMask )
   {
      super( fragment, settingsMask );
   }
   
   
   
   public View getEmptyView()
   {
      View emptyView = null;
      
      final ListFragment fragment = (ListFragment) getFragment();
      if ( fragment.getView() != null && fragment.getListView() != null )
         emptyView = fragment.getListView().getEmptyView();
      
      return emptyView;
   }
   
   
   
   public void showEmptyView( boolean show )
   {
      final View emptyView = getEmptyView();
      if ( emptyView != null )
         emptyView.setVisibility( show ? View.VISIBLE : View.GONE );
   }
   
   
   
   public void showListView( boolean show )
   {
      final ListFragment fragment = (ListFragment) getFragment();
      final View listView = fragment.getListView();
      if ( listView != null )
         listView.setVisibility( show ? View.VISIBLE : View.GONE );
   }
}
