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

package dev.drsoran.moloko.fragments.base;

import java.util.List;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.MenuInflater;

import dev.drsoran.moloko.widgets.MolokoListView;
import dev.drsoran.moloko.widgets.MolokoListView.IActionModeSupport;
import dev.drsoran.moloko.widgets.MolokoListView.IMolokoMultiChoiceModeListener;


public abstract class MolokoMultiChoiceModalListFragment< D > extends
         MolokoListFragment< D >
{
   private MolokoListView molokoListView;
   
   
   
   @Override
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      final ListView listView = (ListView) view.findViewById( android.R.id.list );
      
      final int choiceMode = getChoiceMode();
      listView.setChoiceMode( choiceMode );
      
      if ( listView instanceof MolokoListView )
      {
         molokoListView = (MolokoListView) listView;
         
         if ( choiceMode == MolokoListView.CHOICE_MODE_MULTIPLE_MODAL )
         {
            molokoListView.setActionModeSupport( new IActionModeSupport()
            {
               
               @Override
               public ActionMode startActionMode( IMolokoMultiChoiceModeListener callback )
               {
                  return getSherlockActivity().startActionMode( callback );
               }
               
               
               
               @Override
               public MenuInflater getSupportMenuInflater()
               {
                  return getSherlockActivity().getSupportMenuInflater();
               }
            } );
            
            molokoListView.setMolokoMultiChoiceModeListener( createMultiCoiceModalModeListener() );
         }
      }
      
      // Note: This has to be called after acquiring the list instance since list adapters
      // may access the list view in the base class.
      super.onViewCreated( view, savedInstanceState );
   }
   
   
   
   public boolean isMultiChoiceModalMode()
   {
      return molokoListView != null
         && molokoListView.getChoiceMode() == MolokoListView.CHOICE_MODE_MULTIPLE_MODAL;
   }
   
   
   
   public MolokoListView getMolokoListView()
   {
      return molokoListView;
   }
   
   
   
   public int getChoiceMode()
   {
      return MolokoListView.CHOICE_MODE_MULTIPLE_MODAL;
   }
   
   
   
   @Override
   public void onLoaderReset( Loader< List< D >> loader )
   {
      super.onLoaderReset( loader );
   }
   
   
   
   public abstract IMolokoMultiChoiceModeListener createMultiCoiceModalModeListener();
}
