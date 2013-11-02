/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.moloko.app.tagcloud;

import android.view.View;
import android.widget.Button;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.domain.model.CloudEntry;
import dev.drsoran.moloko.domain.model.CloudEntryType;


class TagCloudEntry extends PresentableCloudEntry implements
         View.OnClickListener
{
   public TagCloudEntry( CloudEntry tagCloudEntry )
   {
      super( tagCloudEntry );
      
      if ( tagCloudEntry.getType() != CloudEntryType.Tag )
      {
         throw new IllegalArgumentException( "Expected tag cloud entry of type "
            + CloudEntryType.Tag );
      }
   }
   
   
   
   @Override
   public void present( Button button )
   {
      button.setOnClickListener( this );
      button.setBackgroundResource( R.drawable.tagcloud_tag_bgnd );
      button.setTextColor( button.getContext()
                                 .getResources()
                                 .getColor( R.color.tagcloud_tag_text_normal ) );
   }
   
   
   
   @Override
   public void onClick( View v )
   {
      if ( getTagCloudFragmentListener() != null )
      {
         getTagCloudFragmentListener().onOpenTag( getDisplay() );
      }
   }
}
