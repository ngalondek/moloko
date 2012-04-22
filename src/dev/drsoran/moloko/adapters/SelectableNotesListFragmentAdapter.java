/*
 * Copyright (c) 2012 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.adapters;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.mdt.rtm.data.RtmTaskNote;

import dev.drsoran.moloko.fragments.base.MolokoListFragment;


public class SelectableNotesListFragmentAdapter extends
         SelectableArrayAdapter< RtmTaskNote >
{
   private final NotesListFragmentAdapter adapterImpl;
   
   
   
   public SelectableNotesListFragmentAdapter(
      MolokoListFragment< List< RtmTaskNote > > fragment, int resourceId,
      List< RtmTaskNote > notes, List< RtmTaskNote > preSelectedNotes )
   {
      super( fragment.getSherlockActivity(), resourceId, notes );
      
      selectBulk( preSelectedNotes );
      adapterImpl = new NotesListFragmentAdapter( fragment, resourceId, notes );
   }
   
   
   
   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      final View view = adapterImpl.getView( position, convertView, parent );
      initNoteListItem( getItem( position ), (ViewGroup) view );
      
      return view;
   }
   
   
   
   private void initNoteListItem( final RtmTaskNote note, ViewGroup listItemView )
   {
      final CheckBox checkBoxView = (CheckBox) listItemView.findViewById( android.R.id.checkbox );
      checkBoxView.setVisibility( View.VISIBLE );
      
      // Note: Setting the checkChangedListener to null before initializing
      // the checked mark prevents notification from recycled convert views.
      checkBoxView.setOnCheckedChangeListener( null );
      checkBoxView.setChecked( isSelected( note ) );
      checkBoxView.setOnCheckedChangeListener( new OnCheckedChangeListener()
      {
         @Override
         public void onCheckedChanged( CompoundButton buttonView,
                                       boolean isChecked )
         {
            if ( isChecked )
            {
               select( note );
            }
            else
            {
               deselect( note );
            }
         }
      } );
   }
}
