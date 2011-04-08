/* 
 *	Copyright (c) 2011 Ronny Röhricht
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

package dev.drsoran.moloko.sync;

import android.util.Log;

import com.mdt.rtm.Service;
import com.mdt.rtm.ServiceException;
import com.mdt.rtm.data.RtmTimeline;


public final class TimeLineFactory
{
   private final static String TAG = "Moloko."
      + TimeLineFactory.class.getSimpleName();
   
   private final Service service;
   
   private RtmTimeline timeline;
   
   

   public TimeLineFactory( Service service )
   {
      this.service = service;
   }
   


   public final RtmTimeline createTimeline() throws ServiceException
   {
      if ( timeline == null )
         timeline = createTimelineImpl();
      
      return timeline;
   }
   


   public final RtmTimeline createOneShotTimeline() throws ServiceException
   {
      return createTimelineImpl();
   }
   


   private final RtmTimeline createTimelineImpl() throws ServiceException
   {
      final RtmTimeline newTimeline = service.timelines_create();
      Log.i( TAG, "Created new time line " + timeline );
      return newTimeline;
   }
}
