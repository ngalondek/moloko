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

package dev.drsoran.moloko;

public interface IOnSettingsChangedListener
{
   public final static int RTM_SETTINGS_SYNCED = 1 << 0;
   
   public final static int DATEFORMAT = 1 << 1;
   
   public final static int TIMEFORMAT = 1 << 2;
   
   public final static int RTM_DEFAULTLIST = 1 << 3;
   
   public final static int TASK_SORT = 1 << 4;
   
   public final static int STARTUP_VIEW = 1 << 5;
   
   public final static int SYNC_INTERVAL = 1 << 6;
   
   public final static int NOTIFY_DUE_TASKS = 1 << 7;
   
   public final static int NOTIFY_DUE_TASKS_BEFORE_TIME = 1 << 8;
   
   public final static int NOTIFY_DUE_TASKS_FEATURE = 1 << 9;
   
   public final static int NOTIFY_PERMANENT_TASKS = 1 << 10;
   
   public final static int DATE_TIME_RELATED = DATEFORMAT | TIMEFORMAT;
   
   public final static int NOTIFY_DUE_TASKS_RELATED = NOTIFY_DUE_TASKS
      | NOTIFY_DUE_TASKS_BEFORE_TIME | NOTIFY_DUE_TASKS_FEATURE;
   
   public final static int NOTIFY_PERMANENT_RELATED = NOTIFY_PERMANENT_TASKS;
   
   public final static int NOTIFICATION_RELATED = NOTIFY_DUE_TASKS_RELATED
      | NOTIFY_PERMANENT_RELATED;
   
   public final static int ALL = Integer.MAX_VALUE;
   
   
   
   public void onSettingsChanged( int which );
}
