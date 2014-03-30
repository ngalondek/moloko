/* 
 *	Copyright (c) 2014 Ronny Röhricht
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

package dev.drsoran.moloko.app.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import dev.drsoran.moloko.R;


public class SectionWidget implements INavWidget
{
   private final Context context;
   
   private final int captionResId;
   
   
   
   public SectionWidget( Context context, int captionResId )
   {
      this.context = context;
      this.captionResId = captionResId;
   }
   
   
   
   @Override
   public void setDirty()
   {
   }
   
   
   
   @Override
   public void stop()
   {
   }
   
   
   
   @Override
   public Intent getIntent()
   {
      return null;
   }
   
   
   
   @Override
   public View getView( View convertView )
   {
      convertView = LayoutInflater.from( context )
                                  .inflate( R.layout.home_activity_drawer_section_widget,
                                            null );
      
      ( (TextView) convertView.findViewById( android.R.id.text1 ) ).setText( captionResId );
      
      return convertView;
   }
}
