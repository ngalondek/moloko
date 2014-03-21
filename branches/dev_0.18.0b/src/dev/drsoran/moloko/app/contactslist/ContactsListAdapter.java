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

package dev.drsoran.moloko.app.contactslist;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.ui.adapters.SwappableArrayAdapter;


class ContactsListAdapter extends SwappableArrayAdapter< LinkedContact >
{
   private final ContactsListFragment context;
   
   private final LayoutInflater inflater;
   
   
   
   public ContactsListAdapter( ContactsListFragment context )
   {
      super( context.getActivity() );
      
      this.context = context;
      inflater = context.getActivity().getLayoutInflater();
   }
   
   
   
   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      if ( convertView == null )
      {
         convertView = inflater.inflate( R.layout.contactslist_fragment_listitem,
                                         parent,
                                         false );
      }
      
      final ImageView picture = (ImageView) convertView.findViewById( R.id.contactslist_listitem_contact_pic );
      final TextView fullname = (TextView) convertView.findViewById( R.id.contactslist_listitem_fullname );
      final TextView username = (TextView) convertView.findViewById( R.id.contactslist_listitem_username );
      final TextView numSharedTasks = (TextView) convertView.findViewById( R.id.contactslist_listitem_num_shared );
      final View callButton = convertView.findViewById( R.id.contactslist_listitem_btn_call );
      
      final LinkedContact contact = getItem( position );
      final Bitmap photo = contact.getPhoto();
      
      if ( photo != null )
         picture.setImageBitmap( photo );
      else
         picture.setImageResource( R.drawable.ic_list_contactslist_user );
      
      fullname.setText( contact.getFullName() );
      username.setText( contact.getUserName() );
      
      final int numShared = contact.getNumTasksParticipating();
      
      numSharedTasks.setText( context.getString( R.string.contactslist_listitem_num_tasks,
                                                 numShared,
                                                 context.getResources()
                                                        .getQuantityText( R.plurals.g_task,
                                                                          numShared ) ) );
      
      callButton.setVisibility( contact.getLookUpKey() != null
                                                              ? setCallButton( callButton,
                                                                               contact )
                                                              : View.GONE );
      return convertView;
   }
   
   
   
   @Override
   public long getItemId( int position )
   {
      return getItem( position ).getId();
   }
   
   
   
   private int setCallButton( final View view, final LinkedContact contact )
   {
      view.setOnClickListener( new OnClickListener()
      {
         @Override
         public void onClick( final View v )
         {
            context.onCallButtonClicked( contact );
         }
      } );
      
      return View.VISIBLE;
   }
}
