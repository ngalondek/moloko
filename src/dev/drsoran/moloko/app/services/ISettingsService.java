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

package dev.drsoran.moloko.app.services;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import android.net.Uri;
import dev.drsoran.moloko.app.settings.PermanentNotificationType;
import dev.drsoran.rtm.RtmSettings;


public interface ISettingsService
{
   
   String getDateformat();
   
   
   
   boolean is24hTimeformat();
   
   
   
   String getDefaultListId();
   
   
   
   void setDefaultListIdSyncWithRtm( boolean sync );
   
   
   
   boolean isDefaultListIdInSyncWithRtm();
   
   
   
   void setDefaultListId( String id );
   
   
   
   Locale getLocale();
   
   
   
   RtmSettings getRtmSettings();
   
   
   
   int getStartupView();
   
   
   
   void setStartupView( int value );
   
   
   
   int getTaskSort();
   
   
   
   void setTaskSort( int taskSort );
   
   
   
   boolean isSyncAtStartup();
   
   
   
   void setSyncAtStartUp( boolean value );
   
   
   
   long getSyncInterval();
   
   
   
   boolean isManualSyncOnly();
   
   
   
   boolean isNotifyingDueTasks();
   
   
   
   long getNotifyingDueTasksBeforeMs();
   
   
   
   Uri getNotifyingDueTasksRingtoneUri();
   
   
   
   boolean isNotifyingDueTasksVibration();
   
   
   
   boolean isNotifyingDueTasksLed();
   
   
   
   /**
    * Value: Collection of list IDs to notify tasks for, or the constant {@link Settings.ALL_LISTS}.
    */
   Map< PermanentNotificationType, Collection< String > > getNotifyingPermanentTaskLists();
   
   
   
   boolean isNotifyingPermanentTasks();
   
   
   
   boolean isUsingHttps();
   
   
   
   boolean hasNotifiedTaskListMultiSelectionHint();
   
   
   
   void setTaskListMultiSelectionHintNotified();
}
