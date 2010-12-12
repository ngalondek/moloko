/*
 * Copyright (c) 2010 Ronny Röhricht
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

package dev.drsoran.moloko.activities;

import java.util.List;

import android.content.ContentProviderClient;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.ParticipantsProviderPart;
import dev.drsoran.provider.Rtm.Participants;
import dev.drsoran.rtm.Contact;
import dev.drsoran.rtm.RtmContact;


public class ContactsListAdapter extends ArrayAdapter< Contact >
{
   private final static String TAG = "Moloko."
      + ContactsListAdapter.class.getName();
   
   private final Context context;
   
   private final int resourceId;
   
   private final LayoutInflater inflater;
   
   

   public ContactsListAdapter( Context context, int resourceId,
      List< Contact > contacts )
   {
      super( context, 0, contacts );
      
      this.context = context;
      this.resourceId = resourceId;
      this.inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
   }
   


   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      final View view = inflater.inflate( resourceId, parent, false );
      
      ImageView picture;
      TextView fullname;
      TextView username;
      TextView numSharedTasks;
      
      try
      {
         picture = (ImageView) view.findViewById( R.id.contactslist_listitem_contact_pic );
         fullname = (TextView) view.findViewById( R.id.contactslist_listitem_fullname );
         username = (TextView) view.findViewById( R.id.contactslist_listitem_username );
         numSharedTasks = (TextView) view.findViewById( R.id.contactslist_listitem_num_shared );
      }
      catch ( ClassCastException e )
      {
         Log.e( TAG, "Invalid layout spec.", e );
         throw e;
      }
      
      final Contact contact = getItem( position );
      
      final Bitmap photo = contact.getPhoto();
      
      if ( photo != null )
         picture.setImageBitmap( photo );
      
      fullname.setText( contact.getFullname() );
      username.setText( contact.getUsername() );
      
      final int numShared = getSharedTasks( contact );
      
      numSharedTasks.setText( context.getString( R.string.contactslist_listitem_num_tasks,
                                                 numShared,
                                                 context.getResources()
                                                        .getQuantityText( R.plurals.g_task,
                                                                          numShared ) ) );
      return view;
   }
   


   private int getSharedTasks( RtmContact contact )
   {
      int num = 0;
      
      final ContentProviderClient client = context.getContentResolver()
                                                  .acquireContentProviderClient( Participants.CONTENT_URI );
      
      if ( client != null )
      {
         num = ParticipantsProviderPart.getNumTasksParticipating( client,
                                                                  contact.getId() );
         
         client.release();
      }
      else
      {
         // TODO: Show error
      }
      
      return num;
   }
   
}
