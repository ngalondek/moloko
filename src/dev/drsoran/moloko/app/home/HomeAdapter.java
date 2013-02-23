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

package dev.drsoran.moloko.app.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.contactslist.ContactsListActivity;
import dev.drsoran.moloko.app.tagcloud.TagCloudActivity;
import dev.drsoran.moloko.ui.layouts.SimpleHomeWidgetLayout;
import dev.drsoran.moloko.ui.widgets.CalendarHomeWidget;
import dev.drsoran.moloko.ui.widgets.IMolokoHomeWidget;
import dev.drsoran.moloko.ui.widgets.OverDueTasksHomeWidget;
import dev.drsoran.provider.Rtm.ListOverviews;


class HomeAdapter extends BaseAdapter
{
   private final List< IMolokoHomeWidget > widgets = new ArrayList< IMolokoHomeWidget >( 7 );
   
   
   
   public HomeAdapter( Context context )
   {
      widgets.add( new CalendarHomeWidget( context,
                                           null,
                                           R.string.home_label_today,
                                           CalendarHomeWidget.TODAY ) );
      
      widgets.add( new CalendarHomeWidget( context,
                                           null,
                                           R.string.home_label_tomorrow,
                                           CalendarHomeWidget.TOMORROW ) );
      
      widgets.add( new OverDueTasksHomeWidget( context,
                                               null,
                                               R.string.home_label_overdue ) );
      
      widgets.add( new SimpleHomeWidgetLayout( context,
                                               null,
                                               R.string.app_tasklists,
                                               R.drawable.ic_home_list_detailed,
                                               new Intent( Intent.ACTION_VIEW,
                                                           ListOverviews.CONTENT_URI ) ) );
      
      widgets.add( new SimpleHomeWidgetLayout( context,
                                               null,
                                               R.string.app_tagcloud,
                                               R.drawable.ic_home_tag,
                                               new Intent( context,
                                                           TagCloudActivity.class ) ) );
      
      widgets.add( new SimpleHomeWidgetLayout( context,
                                               null,
                                               R.string.app_contacts,
                                               R.drawable.ic_home_user,
                                               new Intent( context,
                                                           ContactsListActivity.class ) ) );
      
      widgets.add( new SimpleHomeWidgetLayout( context,
                                               null,
                                               R.string.app_preferences,
                                               R.drawable.ic_home_settings,
                                               Intents.createOpenPreferencesIntent( context ) ) );
   }
   
   
   
   @Override
   public int getCount()
   {
      return widgets.size();
   }
   
   
   
   @Override
   public Object getItem( int position )
   {
      return null;
   }
   
   
   
   @Override
   public long getItemId( int position )
   {
      return 0;
   }
   
   
   
   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      return (View) widgets.get( position );
   }
   
   
   
   public Intent getIntentForWidget( int position )
   {
      if ( position < widgets.size() )
         return ( widgets.get( position ) ).getIntent();
      else
         return null;
   }
   
   
   
   public void startWidgets()
   {
      for ( IMolokoHomeWidget widget : widgets )
         widget.start();
   }
   
   
   
   public void addWidget( IMolokoHomeWidget widget )
   {
      widgets.add( widget );
      widget.start();
      
      notifyDataSetChanged();
   }
   
   
   
   public void removeWidget( IMolokoHomeWidget widget )
   {
      widget.stop();
      widgets.remove( widget );
      
      notifyDataSetChanged();
   }
   
   
   
   public void stopWidgets()
   {
      for ( IMolokoHomeWidget widget : widgets )
         widget.stop();
   }
}
