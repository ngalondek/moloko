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

package dev.drsoran.moloko.ui.layouts;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.ui.widgets.IMolokoHomeWidget;


public class SimpleHomeWidgetLayout extends LinearLayout implements
         IMolokoHomeWidget
{
   private final Intent intent;
   
   
   
   public SimpleHomeWidgetLayout( Context context )
   {
      this( context, null, 0 );
   }
   
   
   
   public SimpleHomeWidgetLayout( Context context, AttributeSet attrs )
   {
      this( context, attrs, 0 );
   }
   
   
   
   public SimpleHomeWidgetLayout( Context context, AttributeSet attrs,
      int defStyle )
   {
      this( context, attrs, 0, 0, null );
   }
   
   
   
   public SimpleHomeWidgetLayout( Context context, AttributeSet attrs,
      int labelId, int imgId, Intent intent )
   {
      super( context, attrs );
      
      initUi( context, labelId, imgId );
      
      this.intent = intent;
   }
   
   
   
   @Override
   public Intent getIntent()
   {
      return intent;
   }
   
   
   
   @Override
   public void start()
   {
   }
   
   
   
   @Override
   public void stop()
   {
   }
   
   
   
   private void initUi( Context context, int labelId, int imgId )
   {
      setOrientation( LinearLayout.VERTICAL );
      
      LayoutInflater.from( context )
                    .inflate( R.layout.home_activity_simple_widget, this, true );
      
      ( (ImageView) findViewById( R.id.widget_simple_image ) ).setImageResource( imgId );
      ( (TextView) findViewById( R.id.text ) ).setText( labelId );
   }
   
}
