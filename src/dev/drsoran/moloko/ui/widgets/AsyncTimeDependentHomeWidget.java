/* 
 *	Copyright (c) 2012 Ronny Röhricht
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

package dev.drsoran.moloko.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import dev.drsoran.moloko.app.IOnTimeChangedListener;
import dev.drsoran.moloko.app.MolokoApp;


public abstract class AsyncTimeDependentHomeWidget extends
         AsyncLoadingCounterBubbleHomeWidget implements IOnTimeChangedListener
{
   
   public AsyncTimeDependentHomeWidget( Context context, AttributeSet attrs )
   {
      super( context, attrs );
   }
   
   
   
   public void start()
   {
      asyncReload();
      
      MolokoApp.getNotifierContext( getContext() )
               .registerOnTimeChangedListener( IOnTimeChangedListener.ALL, this );
   }
   
   
   
   @Override
   public void stop()
   {
      MolokoApp.getNotifierContext( getContext() )
               .unregisterOnTimeChangedListener( this );
   }
   
   
   
   public void onTimeChanged( int which )
   {
      switch ( which )
      {
         case IOnTimeChangedListener.MINUTE_TICK:
            onMinuteTick();
            break;
         case IOnTimeChangedListener.SYSTEM_TIME:
            onSystemTimeChanged();
            break;
         case IOnTimeChangedListener.MIDNIGHT:
            onMidnight();
            break;
         default :
            break;
      }
   }
   
   
   
   protected void onMinuteTick()
   {
   }
   
   
   
   protected void onSystemTimeChanged()
   {
   }
   
   
   
   protected void onMidnight()
   {
   }
}
