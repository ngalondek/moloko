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

package dev.drsoran.moloko;

import java.util.HashMap;


public interface IOnSettingsChangedListener
{
   public final static int RTM_TIMEZONE = 1 << 0;
   
   public final static int RTM_DATEFORMAT = 1 << 1;
   
   public final static int RTM_TIMEFORMAT = 1 << 2;
   
   public final static int RTM_DEFAULTLIST = 1 << 3;
   
   public final static int RTM_LANGUAGE = 1 << 4;
   
   public final static int TASK_SORT = 1 << 5;
   
   public final static int DATE_TIME_RELATED = RTM_TIMEZONE | RTM_DATEFORMAT
      | RTM_TIMEFORMAT;
   
   public final static int ALL = Integer.MAX_VALUE;
   
   
   
   public void onSettingsChanged( int which,
                                  HashMap< Integer, Object > oldValues );
}
