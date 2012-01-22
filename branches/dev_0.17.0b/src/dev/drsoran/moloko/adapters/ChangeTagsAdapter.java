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

package dev.drsoran.moloko.adapters;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.dialogs.ChangeTagsDialogFragment;


public class ChangeTagsAdapter extends
         ArrayAdapter< ChangeTagsDialogFragment.ChangeTag >
{
   private final static String TAG = "Moloko."
      + ChangeTagsAdapter.class.getName();
   
   private final int resourceId;
   
   private final LayoutInflater inflater;
   
   

   public ChangeTagsAdapter( Context context, int resourceId,
      List< ChangeTagsDialogFragment.ChangeTag > tags )
   {
      super( context, 0, tags );
      
      this.resourceId = resourceId;
      this.inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
   }
   


   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      if ( convertView == null )
         convertView = inflater.inflate( resourceId, parent, false );
      
      ImageView icon;
      TextView tagText;
      
      try
      {
         icon = (ImageView) convertView.findViewById( R.id.change_tags_activity_listitem_icon );
         tagText = (TextView) convertView.findViewById( R.id.change_tags_activity_listitem_tag );
      }
      catch ( ClassCastException e )
      {
         Log.e( TAG, "Invalid layout spec.", e );
         throw e;
      }
      
      final ChangeTagsDialogFragment.ChangeTag tag = getItem( position );
      
      icon.setImageResource( tag.isAvailable
                                            ? R.drawable.ic_list_change_tags_tag_add
                                            : R.drawable.ic_list_change_tags_tag_remove );
      tagText.setText( tag.tag );
      
      return convertView;
   }
}
