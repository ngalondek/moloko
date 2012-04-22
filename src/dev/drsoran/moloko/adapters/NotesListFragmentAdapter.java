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

import java.util.Collections;
import java.util.List;

import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mdt.rtm.data.RtmTaskNote;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.base.MolokoListFragment;
import dev.drsoran.moloko.util.MolokoDateUtils;


public class NotesListFragmentAdapter extends ArrayAdapter< RtmTaskNote >
{
   private final MolokoListFragment< List< RtmTaskNote > > fragment;
   
   private final int resourceId;
   
   
   
   public NotesListFragmentAdapter(
      MolokoListFragment< List< RtmTaskNote > > fragment, int resourceId )
   {
      this( fragment, resourceId, Collections.< RtmTaskNote > emptyList() );
   }
   
   
   
   public NotesListFragmentAdapter(
      MolokoListFragment< List< RtmTaskNote > > fragment, int resourceId,
      List< RtmTaskNote > notes )
   {
      super( fragment.getSherlockActivity(), View.NO_ID, notes );
      
      this.fragment = fragment;
      this.resourceId = resourceId;
   }
   
   
   
   public int getLayoutRessource()
   {
      return resourceId;
   }
   
   
   
   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      if ( convertView == null )
         convertView = fragment.getLayoutInflater( null ).inflate( resourceId,
                                                                   parent,
                                                                   false );
      
      final RtmTaskNote note = getItem( position );
      initNoteListItem( note, (ViewGroup) convertView );
      
      return convertView;
   }
   
   
   
   private void initNoteListItem( RtmTaskNote note, ViewGroup listItemView )
   {
      final TextView createdDateView = (TextView) listItemView.findViewById( R.id.note_created_date );
      createdDateView.setText( MolokoDateUtils.formatDate( fragment.getSherlockActivity(),
                                                           note.getCreatedDate()
                                                               .getTime(),
                                                           MolokoDateUtils.FORMAT_ABR_MONTH
                                                              | DateUtils.FORMAT_SHOW_YEAR ) );
      
      final View checkBox = listItemView.findViewById( android.R.id.checkbox );
      checkBox.setVisibility( View.GONE );
      
      final TextView titleView = (TextView) listItemView.findViewById( R.id.note_title );
      if ( !TextUtils.isEmpty( note.getTitle() ) )
      {
         titleView.setVisibility( View.VISIBLE );
         titleView.setText( note.getTitle() );
      }
      else
      {
         titleView.setVisibility( View.GONE );
      }
      
      final TextView noteTextView = (TextView) listItemView.findViewById( R.id.note_text );
      if ( !TextUtils.isEmpty( note.getText() ) )
      {
         noteTextView.setVisibility( View.VISIBLE );
         noteTextView.setText( note.getText() );
      }
      else
      {
         noteTextView.setVisibility( View.GONE );
      }
   }
}
