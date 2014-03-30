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

package dev.drsoran.moloko.app.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import dev.drsoran.moloko.R;


public class WidgetWithIcon implements INavWidget
{
   private final Context context;
   
   private final int iconResId;
   
   private final int textResId;
   
   private final Intent onClickIntent;
   
   
   
   public WidgetWithIcon( Context context, int iconResId, int textResId,
      Intent onClickIntent )
   {
      this.context = context;
      this.iconResId = iconResId;
      this.textResId = textResId;
      this.onClickIntent = onClickIntent;
   }
   
   
   
   public int getIconResId()
   {
      return iconResId;
   }
   
   
   
   public int getTextResId()
   {
      return textResId;
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
   public View getView( View convertView )
   {
      convertView = LayoutInflater.from( context )
                                  .inflate( R.layout.home_activity_drawer_item_with_widget,
                                            null );
      
      setText( convertView );
      setIcon( convertView );
      
      return convertView;
   }
   
   
   
   private void setText( View view )
   {
      ( (TextView) view.findViewById( android.R.id.text1 ) ).setText( textResId );
      view.findViewById( R.id.numTasks ).setVisibility( View.GONE );
      view.findViewById( R.id.loading_spinner ).setVisibility( View.GONE );
   }
   
   
   
   private void setIcon( View view )
   {
      final ViewGroup widgetFrame = (ViewGroup) view.findViewById( android.R.id.widget_frame );
      ImageView icon = (ImageView) widgetFrame.findViewById( android.R.id.icon );
      
      if ( icon == null )
      {
         final View iconWidget = LayoutInflater.from( context )
                                               .inflate( R.layout.home_activity_drawer_icon_widget,
                                                         widgetFrame,
                                                         true );
         
         icon = (ImageView) iconWidget.findViewById( android.R.id.icon );
      }
      
      icon.setImageDrawable( context.getResources().getDrawable( iconResId ) );
   }
   
   
   
   @Override
   public Intent getIntent()
   {
      return onClickIntent;
   }
}
