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

package dev.drsoran.moloko.ui.format;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.TextView;
import dev.drsoran.moloko.domain.model.Due;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.util.MolokoDateUtils;


public final class RtmStyleTaskDescTextViewFormatter
{
   public final static void setTaskDescription( TextView view,
                                                Task task,
                                                long nowMillisUtc )
   {
      view.setText( task.getName() );
      
      boolean setTypeFace = false;
      
      final Due taskDue = task.getDue();
      
      // description
      if ( taskDue != null )
      {
         final long dueDateMillis = taskDue.getMillisUtc();
         
         // Make bold if the task is today
         if ( MolokoDateUtils.isToday( nowMillisUtc, dueDateMillis ) )
         {
            view.setTypeface( Typeface.DEFAULT_BOLD );
            view.setText( task.getName() );
            setTypeFace = true;
         }
         
         // Make underline and bold if overdue
         else
         {
            if ( MolokoDateUtils.isBefore( dueDateMillis, nowMillisUtc ) )
            {
               final SpannableString content = new SpannableString( task.getName() );
               
               content.setSpan( new UnderlineSpan(), 0, content.length(), 0 );
               view.setTypeface( Typeface.DEFAULT_BOLD );
               view.setText( content );
               setTypeFace = true;
            }
         }
      }
      
      if ( !setTypeFace )
      {
         view.setTypeface( Typeface.DEFAULT );
      }
   }
}
