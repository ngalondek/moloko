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

package dev.drsoran.moloko.widgets;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import dev.drsoran.moloko.R;


public class SimpleHomeWidgetLayout extends LinearLayout implements
         IMolokoHomeWidget
{
   private final Intent intent;
   
   

   public SimpleHomeWidgetLayout( Context context, AttributeSet attrs,
      int labelId, int imgId, Intent intent )
   {
      super( context, attrs );
      
      setOrientation( LinearLayout.VERTICAL );
      
      LayoutInflater.from( context )
                    .inflate( R.layout.home_activity_simple_widget, this, true );
      
      ( (ImageView) findViewById( R.id.widget_simple_image ) ).setImageResource( imgId );
      ( (TextView) findViewById( R.id.text ) ).setText( labelId );
      
      this.intent = intent;
   }
   


   public void onClick( View v )
   {
      if ( intent != null )
         getContext().startActivity( intent );
   }
   


   public Intent getIntent()
   {
      return intent;
   }
   


   public void refresh()
   {
   }
}
