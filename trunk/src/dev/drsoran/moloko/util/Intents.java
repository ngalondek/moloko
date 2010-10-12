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

package dev.drsoran.moloko.util;

import android.app.PendingIntent;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.activities.AbstractTasksListActivity;
import dev.drsoran.moloko.content.ListOverviewsProviderPart;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.receivers.SyncAlarmReceiver;
import dev.drsoran.provider.Rtm.ListOverviews;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.RtmListWithTaskCount;
import dev.drsoran.rtm.RtmSmartFilter;


public final class Intents
{
   public final static PendingIntent createSyncAlarmIntent( Context context )
   {
      final Intent intent = new Intent( context, SyncAlarmReceiver.class );
      return PendingIntent.getBroadcast( context,
                                         0,
                                         intent,
                                         PendingIntent.FLAG_UPDATE_CURRENT );
   }
   


   public final static Intent createOpenListIntent( Context context,
                                                    String id,
                                                    String filter )
   {
      Intent intent = null;
      
      final ContentProviderClient client = context.getContentResolver()
                                                  .acquireContentProviderClient( ListOverviews.CONTENT_URI );
      
      if ( client != null )
      {
         final RtmListWithTaskCount list = ListOverviewsProviderPart.getListOverview( client,
                                                                                      ListOverviews._ID
                                                                                         + " = "
                                                                                         + id );
         
         if ( list != null )
            intent = createOpenListIntent( context, list, filter );
         
         client.release();
      }
      
      return intent;
   }
   


   public final static Intent createOpenListIntentByName( Context context,
                                                          String name,
                                                          String filter )
   {
      Intent intent = null;
      
      final ContentProviderClient client = context.getContentResolver()
                                                  .acquireContentProviderClient( ListOverviews.CONTENT_URI );
      
      if ( client != null )
      {
         final RtmListWithTaskCount list = ListOverviewsProviderPart.getListOverview( client,
                                                                                      ListOverviews.LIST_NAME
                                                                                         + " = '"
                                                                                         + name
                                                                                         + "'" );
         
         if ( list != null )
            intent = createOpenListIntent( context, list, filter );
         
         client.release();
      }
      
      return intent;
   }
   


   public final static Intent createOpenListIntent( Context context,
                                                    RtmListWithTaskCount list,
                                                    String filter )
   {
      final Intent intent = new Intent( Intent.ACTION_VIEW, Tasks.CONTENT_URI );
      
      intent.putExtra( AbstractTasksListActivity.TITLE,
                       context.getString( R.string.taskslist_titlebar,
                                          list.getName() ) );
      intent.putExtra( AbstractTasksListActivity.TITLE_ICON,
                       R.drawable.icon_list_white );
      
      String filterString = Strings.EMPTY_STRING;
      
      // If we open a non-smart list, we do not make the list names clickable.
      if ( !list.hasSmartFilter() )
      {
         filterString = RtmSmartFilterLexer.OP_LIST_LIT
            + RtmSmartFilterLexer.quotify( list.getName() );
         
         final Bundle config = new Bundle();
         config.putBoolean( AbstractTasksListActivity.DISABLE_LIST_NAME, true );
         
         intent.putExtra( AbstractTasksListActivity.ADAPTER_CONFIG, config );
      }
      
      // if we open a smart list
      else
      {
         filterString = list.getSmartFilter().getFilterString();
      }
      
      if ( filter != null )
      {
         filterString += ( " " + RtmSmartFilterLexer.AND_LIT + " " + filter );
      }
      
      intent.putExtra( AbstractTasksListActivity.FILTER, filterString );
      
      return intent;
   }
   


   public final static Intent createOpenTagIntent( Context context,
                                                   String tagText )
   {
      final Intent intent = new Intent( Intent.ACTION_VIEW, Tasks.CONTENT_URI );
      intent.putExtra( AbstractTasksListActivity.FILTER,
                       RtmSmartFilterLexer.OP_TAG_LIT + tagText );
      intent.putExtra( AbstractTasksListActivity.TITLE,
                       context.getString( R.string.taskslist_titlebar, tagText ) );
      intent.putExtra( AbstractTasksListActivity.TITLE_ICON,
                       R.drawable.icon_tag_white );
      
      final Bundle config = new Bundle();
      config.putString( UIUtils.DISABLE_TAG_EQUALS, tagText );
      
      intent.putExtra( AbstractTasksListActivity.ADAPTER_CONFIG, config );
      
      return intent;
   }
   


   public final static Intent createSmartFilterIntent( Context context,
                                                       RtmSmartFilter filter,
                                                       String title,
                                                       int iconId )
   {
      final Intent intent = new Intent( Intent.ACTION_VIEW, Tasks.CONTENT_URI );
      
      intent.putExtra( AbstractTasksListActivity.TITLE,
                       context.getString( R.string.taskslist_titlebar,
                                          ( title != null )
                                                           ? title
                                                           : filter.getFilterString() ) );
      if ( iconId != -1 )
         intent.putExtra( AbstractTasksListActivity.TITLE_ICON, iconId );
      
      if ( filter.isEvaluated() )
         intent.putExtra( AbstractTasksListActivity.FILTER_EVALUATED,
                          filter.getEvaluatedFilterString() );
      else
         intent.putExtra( AbstractTasksListActivity.FILTER,
                          filter.getFilterString() );
      
      return intent;
   }
}
