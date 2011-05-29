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

package dev.drsoran.moloko.activities;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.widgets.CalendarHomeWidget;
import dev.drsoran.moloko.widgets.IMolokoHomeWidget;
import dev.drsoran.moloko.widgets.OverDueTasksHomeWidget;
import dev.drsoran.moloko.widgets.SimpleHomeWidgetLayout;
import dev.drsoran.provider.Rtm.ListOverviews;


public class HomeAdapter extends BaseAdapter
{
   private final IMolokoHomeWidget[] WIDGETS;
   
   

   public HomeAdapter( final Context context )
   {
      WIDGETS = new IMolokoHomeWidget[]
      {
       new CalendarHomeWidget( context,
                               null,
                               R.string.home_label_today,
                               CalendarHomeWidget.TODAY ),
       
       new CalendarHomeWidget( context,
                               null,
                               R.string.home_label_tomorrow,
                               CalendarHomeWidget.TOMORROW ),
       
       new OverDueTasksHomeWidget( context, null, R.string.home_label_overdue ),
       
       new SimpleHomeWidgetLayout( context,
                                   null,
                                   R.string.app_tasklists,
                                   R.drawable.ic_home_list_detailed,
                                   new Intent( Intent.ACTION_VIEW,
                                               ListOverviews.CONTENT_URI ) ),
       
       new SimpleHomeWidgetLayout( context,
                                   null,
                                   R.string.app_tagcloud,
                                   R.drawable.ic_home_tag,
                                   new Intent( context, TagCloudActivity.class ) ),
       
       new SimpleHomeWidgetLayout( context,
                                   null,
                                   R.string.app_contacts,
                                   R.drawable.ic_home_user,
                                   new Intent( context,
                                               ContactsListActivity.class ) ),
       new SimpleHomeWidgetLayout( context,
                                   null,
                                   R.string.app_task_add,
                                   R.drawable.ic_home_add_task,
                                   Intents.createAddTaskIntent( context, null ) ),
       
       new SimpleHomeWidgetLayout( context,
                                   null,
                                   R.string.home_label_sync,
                                   R.drawable.ic_home_refresh,
                                   new Runnable()
                                   {
                                      public void run()
                                      {
                                         SyncUtils.requestManualSync( context );
                                      }
                                   } ),
       
       new SimpleHomeWidgetLayout( context,
                                   null,
                                   R.string.app_preferences,
                                   R.drawable.ic_home_settings,
                                   new Intent( context,
                                               MolokoPreferencesActivity.class ) )

      // new SimpleHomeWidgetLayout( context,
      // null,
      // android.R.string.untitled,
      // R.drawable.ic_home_settings,
      // new Intent( context,
      // RtmSmartFilterTestActivity.class ) )
      };
   }
   


   public int getCount()
   {
      return WIDGETS.length;
   }
   


   public Object getItem( int position )
   {
      return null;
   }
   


   public long getItemId( int position )
   {
      return 0;
   }
   


   public View getView( int position, View convertView, ViewGroup parent )
   {
      return (View) WIDGETS[ position ];
   }
   


   public Intent getIntentForWidget( int position )
   {
      if ( position < WIDGETS.length )
         return ( WIDGETS[ position ] ).getIntent();
      else
         return null;
   }
   


   public Runnable getRunnableForWidget( int position )
   {
      if ( position < WIDGETS.length )
         return ( WIDGETS[ position ] ).getRunnable();
      else
         return null;
   }
   


   public void startWidgets()
   {
      for ( int i = 0; i < WIDGETS.length; i++ )
      {
         WIDGETS[ i ].start();
      }
   }
   


   public void stopWidgets()
   {
      for ( int i = 0; i < WIDGETS.length; i++ )
      {
         WIDGETS[ i ].stop();
      }
   }
}
