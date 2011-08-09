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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.PendingIntent;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.SqlSelectionFilter;
import dev.drsoran.moloko.activities.HomeActivity;
import dev.drsoran.moloko.activities.MolokoPreferencesActivity;
import dev.drsoran.moloko.activities.StartUpActivity;
import dev.drsoran.moloko.activities.TaskEditMultipleActivity;
import dev.drsoran.moloko.activities.TasksListActivity;
import dev.drsoran.moloko.content.ListOverviewsProviderPart;
import dev.drsoran.moloko.fragments.AbstractTasksListFragment;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.receivers.SyncAlarmReceiver;
import dev.drsoran.provider.Rtm.ListOverviews;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.RtmListWithTaskCount;
import dev.drsoran.rtm.RtmSmartFilter;
import dev.drsoran.rtm.Task;


public final class Intents
{
   private Intents()
   {
      throw new AssertionError( "This class should not be instantiated." );
   }
   
   
   public final static class Action
   {
      public final static String TASKS_LISTS_MIN_DETAILED = "dev.drsoran.moloko.util.Intents.Action.TASKS_LISTS_MIN_DETAILED";
   }
   

   public final static class Extras
   {
      
      public final static Bundle createOpenListExtrasById( Context context,
                                                           String id,
                                                           String additionalFilter )
      {
         Bundle extras = null;
         
         final ContentProviderClient client = context.getContentResolver()
                                                     .acquireContentProviderClient( ListOverviews.CONTENT_URI );
         
         if ( client != null )
         {
            final RtmListWithTaskCount list = ListOverviewsProviderPart.getListOverview( client,
                                                                                         ListOverviews._ID
                                                                                            + "="
                                                                                            + id );
            
            if ( list != null )
               extras = createOpenListExtras( context, list, additionalFilter );
            
            client.release();
         }
         
         return extras;
      }
      


      public final static Bundle createOpenListExtrasByName( Context context,
                                                             String name,
                                                             String additionalFilter )
      {
         Bundle extras = null;
         
         final ContentProviderClient client = context.getContentResolver()
                                                     .acquireContentProviderClient( ListOverviews.CONTENT_URI );
         
         if ( client != null )
         {
            final RtmListWithTaskCount list = ListOverviewsProviderPart.getListOverview( client,
                                                                                         ListOverviews.LIST_NAME
                                                                                            + "='"
                                                                                            + name
                                                                                            + "'" );
            
            if ( list != null )
               extras = createOpenListExtras( context, list, additionalFilter );
            
            client.release();
         }
         
         return extras;
      }
      


      public final static Bundle createOpenListExtras( Context context,
                                                       RtmListWithTaskCount list,
                                                       String additionalFilter )
      {
         String filterString = Strings.EMPTY_STRING;
         
         // If we open a non-smart list
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
         
         if ( additionalFilter != null )
         {
            if ( filterString.length() > 0 )
               filterString += ( " " + RtmSmartFilterLexer.AND_LIT + " ("
                  + additionalFilter + ")" );
            else
               filterString = additionalFilter;
         }
         
         final Bundle extras = createSmartFilterExtras( context,
                                                        new RtmSmartFilter( filterString ),
                                                        context.getString( R.string.taskslist_titlebar,
                                                                           list.getName() ),
                                                        R.drawable.ic_title_list );
         extras.putString( Lists.LIST_NAME, list.getName() );
         return extras;
      }
      


      public final static Bundle createOpenLocationExtras( Context context,
                                                           String name )
      {
         return createSmartFilterExtras( context,
                                         new RtmSmartFilter( RtmSmartFilterLexer.OP_LOCATION_LIT
                                            + RtmSmartFilterLexer.quotify( name ) ),
                                         context.getString( R.string.taskslist_titlebar,
                                                            name ),
                                         R.drawable.ic_title_location );
      }
      


      public final static Bundle createOpenContactExtras( Context context,
                                                          String fullname,
                                                          String username )
      {
         // Here we take the username cause the fullname can be ambiguous.
         return createSmartFilterExtras( context,
                                         new RtmSmartFilter( RtmSmartFilterLexer.OP_SHARED_WITH_LIT
                                            + RtmSmartFilterLexer.quotify( username ) ),
                                         context.getString( R.string.taskslist_titlebar,
                                                            fullname ),
                                         R.drawable.ic_title_user );
      }
      


      public final static Bundle createOpenTagExtras( Context context,
                                                      String tag )
      {
         return createOpenTagsExtras( context,
                                      Collections.singletonList( tag ),
                                      null );
      }
      


      public final static Bundle createOpenTagsExtras( Context context,
                                                       List< String > tags,
                                                       String logicalOperator )
      {
         if ( tags.size() > 1 && logicalOperator == null )
            throw new IllegalArgumentException( "logicalOperator must not be null with multiple tags" );
         
         final StringBuilder filterString = new StringBuilder();
         
         for ( int i = 0, cnt = tags.size(); i < cnt; ++i )
         {
            final String tag = tags.get( i );
            
            filterString.append( RtmSmartFilterLexer.OP_TAG_LIT ).append( tag );
            
            // not last element
            if ( i < cnt - 1 )
               filterString.append( " " )
                           .append( logicalOperator )
                           .append( " " );
         }
         
         return createSmartFilterExtras( context,
                                         new RtmSmartFilter( filterString.toString() ),
                                         context.getString( R.string.taskslist_titlebar,
                                                            TextUtils.join( ", ",
                                                                            tags ) ),
                                         R.drawable.ic_title_tag );
      }
      


      public final static Bundle createSqlSelectionFilterExtras( Context context,
                                                                 SqlSelectionFilter filter,
                                                                 String title,
                                                                 int iconId )
      {
         final Bundle extras = new Bundle();
         
         extras.putString( TasksListActivity.Config.TITLE,
                           context.getString( R.string.taskslist_titlebar,
                                              ( title != null )
                                                               ? title
                                                               : context.getString( R.string.app_name ) ) );
         if ( iconId != -1 )
            extras.putInt( TasksListActivity.Config.TITLE_ICON, iconId );
         
         extras.putParcelable( AbstractTasksListFragment.Config.FILTER, filter );
         
         return extras;
      }
      


      public final static Bundle createSmartFilterExtras( Context context,
                                                          RtmSmartFilter filter,
                                                          String title,
                                                          int iconId )
      {
         final Bundle extras = new Bundle();
         
         extras.putString( TasksListActivity.Config.TITLE,
                           context.getString( R.string.taskslist_titlebar,
                                              ( title != null )
                                                               ? title
                                                               : filter.getFilterString() ) );
         if ( iconId != -1 )
            extras.putInt( TasksListActivity.Config.TITLE_ICON, iconId );
         
         extras.putParcelable( AbstractTasksListFragment.Config.FILTER, filter );
         
         return extras;
      }
   }
   
   

   /** INTENTS **/
   
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
   


   public final static Intent createOpenHomeIntent( Context context )
   {
      return new Intent( context, HomeActivity.class ).addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
   }
   


   public final static Intent createOpenPreferencesIntent( Context context )
   {
      return new Intent( context, MolokoPreferencesActivity.class );
   }
   


   public final static Intent createOpenListOverviewsIntent()
   {
      return new Intent( Intent.ACTION_VIEW, ListOverviews.CONTENT_URI );
   }
   


   public final static Intent createNewAccountIntent( Context context )
   {
      return new Intent( context, StartUpActivity.class ).putExtra( StartUpActivity.ONLY_CHECK_ACCOUNT,
                                                                    Boolean.TRUE );
   }
   


   public final static Intent createOpenListIntentById( Context context,
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
                                                                                         + "="
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
                                                                                         + "='"
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
      return new Intent( Intent.ACTION_VIEW, Tasks.CONTENT_URI ).putExtras( Extras.createOpenListExtras( context,
                                                                                                         list,
                                                                                                         filter ) );
   }
   


   public final static Intent createOpenTagsIntent( Context context,
                                                    List< String > tags,
                                                    String logicalOperator )
   {
      return new Intent( Intent.ACTION_VIEW, Tasks.CONTENT_URI ).putExtras( Extras.createOpenTagsExtras( context,
                                                                                                         tags,
                                                                                                         logicalOperator ) );
   }
   


   public final static Intent createOpenLocationIntentByName( Context context,
                                                              String name )
   {
      return new Intent( Intent.ACTION_VIEW, Tasks.CONTENT_URI ).putExtras( Extras.createOpenLocationExtras( context,
                                                                                                             name ) );
   }
   


   public final static Intent createOpenContactIntent( Context context,
                                                       String fullname,
                                                       String username )
   {
      return new Intent( Intent.ACTION_VIEW, Tasks.CONTENT_URI ).putExtras( Extras.createOpenContactExtras( context,
                                                                                                            fullname,
                                                                                                            username ) );
   }
   


   public final static Intent createSqlSelectionFilterIntent( Context context,
                                                              SqlSelectionFilter filter,
                                                              String title,
                                                              int iconId )
   {
      return new Intent( Intent.ACTION_VIEW, Tasks.CONTENT_URI ).putExtras( Extras.createSqlSelectionFilterExtras( context,
                                                                                                                   filter,
                                                                                                                   title,
                                                                                                                   iconId ) );
   }
   


   public final static Intent createOpenTaskIntent( Context context,
                                                    String taskId )
   {
      return new Intent( Intent.ACTION_VIEW,
                         Queries.contentUriWithId( Tasks.CONTENT_URI, taskId ) );
   }
   


   public final static Intent createEditTaskIntent( Context context,
                                                    String taskId )
   {
      return new Intent( Intent.ACTION_EDIT,
                         Queries.contentUriWithId( Tasks.CONTENT_URI, taskId ) );
   }
   


   public final static Intent createEditMultipleTasksIntent( Context context,
                                                             List< ? extends Task > tasks )
   {
      final Intent intent = new Intent( Intent.ACTION_EDIT, Tasks.CONTENT_URI );
      intent.putParcelableArrayListExtra( TaskEditMultipleActivity.Config.TASKS,
                                          new ArrayList< Task >( tasks ) );
      
      return intent;
   }
   


   public final static Intent createSelectMultipleTasksIntent( Context context,
                                                               IFilter filter,
                                                               int sortOrder )
   {
      final Intent intent;
      
      if ( filter instanceof SqlSelectionFilter )
         intent = createSqlSelectionFilterIntent( context,
                                                  (SqlSelectionFilter) filter,
                                                  context.getString( R.string.select_multiple_tasks_titlebar ),
                                                  -1 );
      else
         intent = createSmartFilterIntent( context,
                                           (RtmSmartFilter) filter,
                                           context.getString( R.string.select_multiple_tasks_titlebar ),
                                           -1 );
      
      intent.setAction( Intent.ACTION_PICK );
      
      if ( sortOrder != -1 )
         intent.putExtra( AbstractTasksListFragment.Config.TASK_SORT_ORDER,
                          sortOrder );
      
      return intent;
   }
   


   public final static Intent createAddTaskIntent( Context context,
                                                   Bundle initialValues )
   {
      final Intent intent = new Intent( Intent.ACTION_INSERT, Tasks.CONTENT_URI );
      
      if ( initialValues != null )
         intent.putExtras( initialValues );
      
      return intent;
   }
   


   public final static Intent createSmartFilterIntent( Context context,
                                                       RtmSmartFilter filter,
                                                       String title,
                                                       int iconId )
   {
      final Intent intent = new Intent( Intent.ACTION_VIEW, Tasks.CONTENT_URI );
      
      intent.putExtras( Extras.createSmartFilterExtras( context,
                                                        filter,
                                                        title,
                                                        iconId ) );
      
      return intent;
   }
}
