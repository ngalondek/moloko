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
   


   public final static PendingIntent createNotificationIntent( Context context,
                                                               Intent onClickIntent )
   {
      onClickIntent.setFlags( onClickIntent.getFlags()
         | Intent.FLAG_ACTIVITY_CLEAR_TOP );
      return PendingIntent.getActivity( context,
                                        0,
                                        onClickIntent,
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
                       R.drawable.ic_title_list );
      
      String filterString = Strings.EMPTY_STRING;
      
      // If we open a non-smart list, we do not make the list names clickable.
      if ( !list.hasSmartFilter() )
      {
         filterString = RtmSmartFilterLexer.OP_LIST_LIT
            + RtmSmartFilterLexer.quotify( list.getName() );
      }
      
      // if we open a smart list
      else
      {
         filterString = list.getSmartFilter().getFilterString();
      }
      
      if ( filter != null )
      {
         if ( filterString.length() > 0 )
            filterString += ( " " + RtmSmartFilterLexer.AND_LIT + " " + filter );
         else
            filterString = filter;
      }
      
      intent.putExtra( AbstractTasksListActivity.FILTER,
                       new RtmSmartFilter( filterString ) );
      
      return intent;
   }
   


   public final static Intent createOpenTagIntent( Context context,
                                                   String tagText )
   {
      final Intent intent = new Intent( Intent.ACTION_VIEW, Tasks.CONTENT_URI );
      intent.putExtra( AbstractTasksListActivity.FILTER,
                       new RtmSmartFilter( RtmSmartFilterLexer.OP_TAG_LIT
                          + RtmSmartFilterLexer.quotify( tagText ) ) );
      intent.putExtra( AbstractTasksListActivity.TITLE,
                       context.getString( R.string.taskslist_titlebar, tagText ) );
      intent.putExtra( AbstractTasksListActivity.TITLE_ICON,
                       R.drawable.ic_title_tag );
      
      return intent;
   }
   


   public final static Intent createOpenLocationIntentByName( Context context,
                                                              String name )
   {
      final Intent intent = new Intent( Intent.ACTION_VIEW, Tasks.CONTENT_URI );
      
      intent.putExtra( AbstractTasksListActivity.FILTER,
                       new RtmSmartFilter( RtmSmartFilterLexer.OP_LOCATION_LIT
                          + RtmSmartFilterLexer.quotify( name ) ) );
      intent.putExtra( AbstractTasksListActivity.TITLE,
                       context.getString( R.string.taskslist_titlebar, name ) );
      intent.putExtra( AbstractTasksListActivity.TITLE_ICON,
                       R.drawable.ic_title_tag ); // TODO: Make new icon for locations
      
      return intent;
   }
   


   public final static Intent createOpenContactIntent( Context context,
                                                       String fullname,
                                                       String username )
   {
      final Intent intent = new Intent( Intent.ACTION_VIEW, Tasks.CONTENT_URI );
      
      // Here we take the username cause the fullname can be ambiguous.
      intent.putExtra( AbstractTasksListActivity.FILTER,
                       new RtmSmartFilter( RtmSmartFilterLexer.OP_SHARED_WITH_LIT
                          + RtmSmartFilterLexer.quotify( username ) ) );
      intent.putExtra( AbstractTasksListActivity.TITLE,
                       context.getString( R.string.taskslist_titlebar, fullname ) );
      intent.putExtra( AbstractTasksListActivity.TITLE_ICON,
                       R.drawable.ic_title_user );
      
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
      
      intent.putExtra( AbstractTasksListActivity.FILTER, filter );
      
      return intent;
   }
   


   public final static Intent createOpenTaskIntent( Context context,
                                                    String taskId )
   {
      return new Intent( Intent.ACTION_VIEW,
                         Queries.contentUriWithId( Tasks.CONTENT_URI, taskId ) );
   }
}
