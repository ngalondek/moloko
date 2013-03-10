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

package dev.drsoran.moloko.app.task;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdt.rtm.data.RtmTaskNote;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.ui.MolokoLinkifier;
import dev.drsoran.moloko.ui.adapters.MultiChoiceModalArrayAdapter;
import dev.drsoran.moloko.ui.services.IDateFormatterService;


class NotesListFragmentAdapter extends
         MultiChoiceModalArrayAdapter< RtmTaskNote >
{
   public NotesListFragmentAdapter( NotesListFragment fragment )
   {
      super( fragment.getMolokoListView(), R.layout.noteslist_listitem );
   }
   
   
   
   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      convertView = super.getView( position, convertView, parent );
      
      final RtmTaskNote note = getItem( position );
      initNoteListItem( note, (ViewGroup) convertView );
      
      return convertView;
   }
   
   
   
   @Override
   public long getItemId( int position )
   {
      // Note: We handle the case for queries out of bounds because the
      // Android runtime asks us for invalid positions if the last element
      // is removed and we get a changed data set.
      if ( position < getCount() )
      {
         return Long.parseLong( getItem( position ).getId() );
      }
      else
      {
         return -1;
      }
   }
   
   
   
   private void initNoteListItem( RtmTaskNote note, ViewGroup listItemView )
   {
      final TextView createdDateView = (TextView) listItemView.findViewById( R.id.note_created_date );
      createdDateView.setText( getUiContext().getDateFormatter()
                                             .formatDate( note.getCreatedDate()
                                                              .getTime(),
                                                          IDateFormatterService.FORMAT_ABR_MONTH
                                                             | IDateFormatterService.FORMAT_WITH_YEAR ) );
      
      final TextView titleView = (TextView) listItemView.findViewById( R.id.note_title );
      if ( !TextUtils.isEmpty( note.getTitle() ) )
      {
         titleView.setVisibility( View.VISIBLE );
         titleView.setText( note.getTitle() );
         MolokoLinkifier.linkify( titleView );
      }
      else
      {
         titleView.setVisibility( View.GONE );
      }
      
      final TextView noteTextView = (TextView) listItemView.findViewById( R.id.note_text );
      final View simpleLine = listItemView.findViewById( R.id.line );
      if ( !TextUtils.isEmpty( note.getText() ) )
      {
         noteTextView.setVisibility( View.VISIBLE );
         noteTextView.setText( note.getText() );
         MolokoLinkifier.linkify( noteTextView );
         
         simpleLine.setVisibility( View.VISIBLE );
      }
      else
      {
         noteTextView.setVisibility( View.GONE );
         simpleLine.setVisibility( View.GONE );
      }
   }
}
