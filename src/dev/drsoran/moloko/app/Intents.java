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

package dev.drsoran.moloko.app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import android.app.PendingIntent;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.TextUtils;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.home.HomeActivity;
import dev.drsoran.moloko.app.prefs.activities.MainPreferencesActivity;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.content.db.TableColumns;
import dev.drsoran.moloko.domain.model.Location;
import dev.drsoran.moloko.domain.model.Note;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.moloko.util.Bundles;
import dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterBuilder;


public final class Intents
{
   private Intents()
   {
      throw new AssertionError( "This class should not be instantiated." );
   }
   
   
   public final static class Action
   {
      public final static String SYNC_STATUS_UPDATE = "dev.drsoran.moloko.util.Intents.Action.SYNC_STATUS_UPDATE";
      
      public final static String SETTINGS_CHANGED = "dev.drsoran.moloko.util.Intents.Action.SETTINGS_CHANGED";
      
      public final static String NOTIFICATION_SERVICE_START = "dev.drsoran.moloko.util.Intents.Action.NOTIFICATION_SERVICE_START";
      
      public final static String NOTIFICATION_SERVICE_NOTIFICATION_CLICKED = "dev.drsoran.moloko.util.Intents.Action.NOTIFICATION_SERVICE_NOTIFICATION_CLICKED";
      
      public final static String NOTIFICATION_SERVICE_NOTIFICATON_CLEARED = "dev.drsoran.moloko.util.Intents.Action.NOTIFICATION_SERVICE_NOTIFICATON_CLEARED";
      
      public final static String TASK_POSTPONED_FROM_NOTIFICATION = "dev.drsoran.moloko.util.Intents.Action.TASK_POSTPONED_FROM_NOTIFICATION";
      
      public final static String TASK_COMPLETED_FROM_NOTIFICATION = "dev.drsoran.moloko.util.Intents.Action.TASK_COMPLETED_FROM_NOTIFICATION";
   }
   
   
   public final static class HomeAction
   {
      public final static String NONE = "none";
      
      public final static String BACK = "back";
      
      public final static String HOME = "home";
      
      public final static String ACTIVITY = "activity";
   }
   
   
   public final static class Extras
   {
      public final static String HOME_ACTION = "home_action";
      
      public final static String HOME_AS_UP_ACTIVITY = "home_as_up_activity";
      
      public final static String KEY_ACTIVITY_TITLE = "activity_title";
      
      public final static String KEY_ACTIVITY_SUB_TITLE = "activity_sub_title";
      
      public final static String KEY_TASK = "task";
      
      public final static String KEY_TASK_ID = "task_id";
      
      public final static String KEY_TASKS = Bundles.KEY_QUALIFIER_SERIALIZABLE_ARRAY_LIST
         + "tasks";
      
      public final static String KEY_LIST = "list";
      
      public final static String KEY_LIST_NAME = "list_name";
      
      public final static String KEY_LIST_ID = "list_id";
      
      public final static String KEY_NOTE = "note";
      
      public final static String KEY_NOTE_ID = "note_id";
      
      public final static String KEY_NOTE_TITLE = "note_title";
      
      public final static String KEY_NOTE_TEXT = "note_text";
      
      public final static String KEY_FILTER = "filter";
      
      public final static String KEY_TAGS = "tags";
      
      public final static String KEY_TASK_SORT_ORDER = "tasks_sort_order";
      
      public final static String KEY_SYNC_STATUS = "sync_status";
      
      public final static String KEY_NOTIFICATION_ID = "notification_id";
      
      public final static String KEY_FROM_NOTIFICATION = "from_notification";
      
      public static final String AUTH_TOKEN_EXPIRED = "authTokenExpired";
      
      public static final String AUTH_MISSINGCREDENTIALS = "missingCredentials";
      
      public static final String AUTH_CONFIRMCREDENTIALS = "confirmCredentials";
      
      public static final String AUTH_UPDATECREDENTIALS = "updateCredentials";
      
      
      
      public final static Bundle createSyncStatusExtras( int status )
      {
         final Bundle bundle = new Bundle( 1 );
         
         bundle.putInt( KEY_SYNC_STATUS, status );
         
         return bundle;
      }
      
      
      
      public final static Bundle createEditTaskExtras( Task task )
      {
         final Bundle bundle = new Bundle( 1 );
         
         bundle.putSerializable( Extras.KEY_TASK, task );
         
         return bundle;
      }
      
      
      
      public final static Bundle createEditNoteExtras( Task task, long noteId )
      {
         final Bundle bundle = new Bundle( 2 );
         
         bundle.putSerializable( Extras.KEY_TASK, task );
         bundle.putLong( Extras.KEY_NOTE_ID, noteId );
         
         return bundle;
      }
      
      
      
      public final static Bundle createAddNoteExtras( Task task,
                                                      String title,
                                                      String text )
      {
         final Bundle bundle = new Bundle( 3 );
         
         bundle.putSerializable( Extras.KEY_TASK, task );
         
         if ( !TextUtils.isEmpty( title ) )
            bundle.putString( Extras.KEY_NOTE_TITLE, title );
         
         if ( !TextUtils.isEmpty( text ) )
            bundle.putString( Extras.KEY_NOTE_TEXT, text );
         
         return bundle;
      }
      
      
      
      public final static Bundle createChooseTagsExtras( Collection< String > preselectedTags )
      {
         final Bundle bundle = new Bundle( 1 );
         
         bundle.putStringArrayList( Extras.KEY_TAGS,
                                    new ArrayList< String >( preselectedTags ) );
         
         return bundle;
      }
      
      
      
      public final static Bundle createOpenListExtras( Context context,
                                                       TasksList list,
                                                       String additionalSmartFilter )
      {
         final RtmSmartFilterBuilder smartFilterBuilder = new RtmSmartFilterBuilder();
         
         // If we open a non-smart list
         if ( !list.isSmartList() )
         {
            smartFilterBuilder.list( list.getName() );
         }
         
         // if we open a smart list
         else
         {
            smartFilterBuilder.filter( list.getSmartFilter() );
         }
         
         if ( additionalSmartFilter != null )
         {
            smartFilterBuilder.and()
                              .lParenth()
                              .filterString( additionalSmartFilter )
                              .rParenth();
         }
         
         final Bundle extras = createSmartFilterExtras( context,
                                                        smartFilterBuilder.toSmartFilter(),
                                                        context.getString( R.string.taskslist_actionbar,
                                                                           list.getName() ) );
         extras.putString( Intents.Extras.KEY_LIST_NAME, list.getName() );
         extras.putLong( Intents.Extras.KEY_LIST_ID, list.getId() );
         
         return extras;
      }
      
      
      
      public final static Bundle createOpenLocationExtras( Context context,
                                                           String locationName )
      {
         return createSmartFilterExtras( context,
                                         new RtmSmartFilterBuilder().location( locationName )
                                                                    .toSmartFilter(),
                                         context.getString( R.string.taskslist_actionbar,
                                                            locationName ) );
      }
      
      
      
      public final static Bundle createOpenContactExtras( Context context,
                                                          String fullname,
                                                          String username )
      {
         // Here we take the username cause the fullname can be ambiguous.
         return createSmartFilterExtras( context,
                                         new RtmSmartFilterBuilder().sharedWith( username )
                                                                    .toSmartFilter(),
                                         context.getString( R.string.taskslist_actionbar,
                                                            fullname ) );
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
         final int tagsSize = tags.size();
         
         if ( tagsSize == 0 )
         {
            throw new IllegalArgumentException( "tags must not be empty" );
         }
         
         if ( tagsSize > 1 && logicalOperator == null )
         {
            throw new IllegalArgumentException( "logicalOperator must not be null with multiple tags" );
         }
         
         final RtmSmartFilterBuilder smartFilterBuilder = new RtmSmartFilterBuilder();
         
         for ( int i = 0; i < tagsSize; ++i )
         {
            final String tag = tags.get( i );
            smartFilterBuilder.tag( tag );
            
            // not last element
            if ( i < tagsSize - 1 )
            {
               smartFilterBuilder.filterString( logicalOperator );
            }
         }
         
         return createSmartFilterExtras( context,
                                         smartFilterBuilder.toSmartFilter(),
                                         context.getString( R.string.taskslist_actionbar,
                                                            TextUtils.join( ", ",
                                                                            tags ) ) );
      }
      
      
      
      public final static Bundle createSqlSelectionFilterExtras( Context context,
                                                                 SqlSelectionFilter filter,
                                                                 String title )
      {
         final Bundle extras = new Bundle();
         
         extras.putString( Extras.KEY_ACTIVITY_TITLE,
                           context.getString( R.string.taskslist_actionbar,
                                              ( title != null )
                                                               ? title
                                                               : context.getString( R.string.app_name ) ) );
         
         extras.putParcelable( Extras.KEY_FILTER, filter );
         
         return extras;
      }
      
      
      
      public final static Bundle createSmartFilterExtras( Context context,
                                                          RtmSmartFilter filter,
                                                          String title )
      {
         final Bundle extras = new Bundle();
         
         extras.putString( Extras.KEY_ACTIVITY_TITLE,
                           context.getString( R.string.taskslist_actionbar,
                                              ( title != null )
                                                               ? title
                                                               : filter.getFilterString() ) );
         extras.putSerializable( Extras.KEY_FILTER, filter );
         
         return extras;
      }
   }
   
   
   
   /** INTENTS **/
   
   public final static Intent createStartNotificationServiceIntent( MolokoApp molokoApp )
   {
      final Intent intent = new Intent( molokoApp,
                                        MolokoNotificationService.class );
      intent.setAction( Action.NOTIFICATION_SERVICE_START );
      
      return intent;
   }
   
   
   
   public final static PendingIntent createSyncAlarmIntent( Context context )
   {
      final Intent intent = new Intent( context, SyncAlarmReceiver.class );
      return PendingIntent.getBroadcast( context,
                                         0,
                                         intent,
                                         PendingIntent.FLAG_UPDATE_CURRENT );
   }
   
   
   
   public final static Intent createSyncStartedIntent()
   {
      final Intent intent = new Intent( Intents.Action.SYNC_STATUS_UPDATE );
      intent.putExtras( Extras.createSyncStatusExtras( Constants.SYNC_STATUS_STARTED ) );
      
      return intent;
   }
   
   
   
   public final static Intent createSyncFinishedIntent()
   {
      final Intent intent = new Intent( Intents.Action.SYNC_STATUS_UPDATE );
      intent.putExtras( Extras.createSyncStatusExtras( Constants.SYNC_STATUS_FINISHED ) );
      
      return intent;
   }
   
   
   
   public final static PendingIntent createPermanentNotificationIntent( Context context,
                                                                        int notificationId )
   {
      final Intent onClickIntent = new Intent( context,
                                               MolokoNotificationService.class );
      
      onClickIntent.setAction( Action.NOTIFICATION_SERVICE_NOTIFICATION_CLICKED );
      onClickIntent.putExtra( Extras.KEY_NOTIFICATION_ID, notificationId );
      
      return PendingIntent.getService( context,
                                       0,
                                       onClickIntent,
                                       PendingIntent.FLAG_CANCEL_CURRENT );
   }
   
   
   
   public final static PendingIntent createDueTasksNotificationIntent( Context context,
                                                                       int notificationId )
   {
      final Intent onClickIntent = new Intent( context,
                                               MolokoNotificationService.class );
      
      onClickIntent.setAction( Action.NOTIFICATION_SERVICE_NOTIFICATION_CLICKED );
      onClickIntent.putExtra( Extras.KEY_NOTIFICATION_ID, notificationId );
      
      return PendingIntent.getService( context,
                                       1,
                                       onClickIntent,
                                       PendingIntent.FLAG_CANCEL_CURRENT
                                          | PendingIntent.FLAG_ONE_SHOT );
   }
   
   
   
   public final static PendingIntent createNotificationClearedIntent( Context context,
                                                                      int notificationId )
   {
      final Intent onClickIntent = new Intent( context,
                                               MolokoNotificationService.class );
      
      onClickIntent.setAction( Action.NOTIFICATION_SERVICE_NOTIFICATON_CLEARED );
      onClickIntent.putExtra( Extras.KEY_NOTIFICATION_ID, notificationId );
      
      return PendingIntent.getService( context,
                                       2,
                                       onClickIntent,
                                       PendingIntent.FLAG_CANCEL_CURRENT );
   }
   
   
   
   public final static PendingIntent createTaskPostponedFromNotificationIntent( Context context,
                                                                                Task task )
   {
      final Intent broadcastIntent = new Intent( Action.TASK_POSTPONED_FROM_NOTIFICATION );
      
      broadcastIntent.putExtra( Extras.KEY_TASK, task );
      
      return PendingIntent.getBroadcast( context,
                                         0,
                                         broadcastIntent,
                                         PendingIntent.FLAG_CANCEL_CURRENT );
   }
   
   
   
   public final static PendingIntent createTaskCompletedFromNotificationIntent( Context context,
                                                                                Task task )
   {
      final Intent broadcastIntent = new Intent( Action.TASK_COMPLETED_FROM_NOTIFICATION );
      
      broadcastIntent.putExtra( Extras.KEY_TASK, task );
      
      return PendingIntent.getBroadcast( context,
                                         0,
                                         broadcastIntent,
                                         PendingIntent.FLAG_CANCEL_CURRENT );
   }
   
   
   
   public final static Intent createHomeIntent( Context context )
   {
      return createHomeAsUpIntent( context, HomeActivity.class );
   }
   
   
   
   public final static Intent createHomeAsUpIntent( Context context,
                                                    Class< ? > homeAsUpTarget )
   {
      return new Intent( context, homeAsUpTarget ).addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
   }
   
   
   
   public final static Intent createOpenPreferencesIntent( Context context )
   {
      return new Intent( context, MainPreferencesActivity.class );
   }
   
   
   
   public final static Intent createOpenSystemAccountSettingsIntent()
   {
      final Intent intent = new Intent( Settings.ACTION_SYNC_SETTINGS );
      
      intent.putExtra( Settings.EXTRA_AUTHORITIES, TableColumns.AUTHORITY );
      
      return intent;
   }
   
   
   
   public final static Intent createOpenListOverviewsIntent()
   {
      return new Intent( Intent.ACTION_VIEW, ListOverviews.CONTENT_URI );
   }
   
   
   
   public final static Intent createNewAccountIntent()
   {
      return new Intent( Settings.ACTION_ADD_ACCOUNT );
   }
   
   
   
   public final static Intent createOpenListIntentById( Context context,
                                                        long id,
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
         {
            intent = createOpenListIntent( context, list, filter );
         }
         
         client.release();
      }
      
      return intent;
   }
   
   
   
   public final static Intent createOpenListIntent( Context context,
                                                    TasksList list,
                                                    String additionalSmartFilter )
   {
      return new Intent( Intent.ACTION_VIEW, ContentUris.TASKS_CONTENT_URI ).putExtras( Extras.createOpenListExtras( context,
                                                                                                                     list,
                                                                                                                     additionalSmartFilter ) );
   }
   
   
   
   public final static Intent createOpenTagsIntent( Context context,
                                                    List< String > tags,
                                                    String logicalOperator )
   {
      return new Intent( Intent.ACTION_VIEW, ContentUris.TASKS_CONTENT_URI ).putExtras( Extras.createOpenTagsExtras( context,
                                                                                                                     tags,
                                                                                                                     logicalOperator ) );
   }
   
   
   
   public final static Intent createOpenLocationIntentByName( Context context,
                                                              String name )
   {
      return new Intent( Intent.ACTION_VIEW, ContentUris.TASKS_CONTENT_URI ).putExtras( Extras.createOpenLocationExtras( context,
                                                                                                                         name ) );
   }
   
   
   
   public final static Intent createOpenLocationWithOtherAppIntent( float lon,
                                                                    float lat,
                                                                    int zoom )
   {
      return new Intent( Intent.ACTION_VIEW, Uri.parse( "geo:" + lat + ","
         + lon + "?z=" + zoom ) );
   }
   
   
   
   public final static Intent createOpenLocationWithOtherAppIntent( String address )
   {
      return new Intent( Intent.ACTION_VIEW, Uri.parse( "geo:0,0?q=" + address ) );
   }
   
   
   
   public final static Intent createOpenLocationWithOtherAppChooser( float lon,
                                                                     float lat,
                                                                     int zoom )
   {
      return Intent.createChooser( createOpenLocationWithOtherAppIntent( lon,
                                                                         lat,
                                                                         zoom ),
                                   null );
   }
   
   
   
   public final static Intent createOpenLocationWithOtherAppChooser( Location location )
   {
      // Determine the type of the location. If we have coordinates
      // we use these cause they are more precise than the
      // address.
      if ( location.getLongitude() != 0.0f || location.getLatitude() != 0.0f )
      {
         return Intent.createChooser( createOpenLocationWithOtherAppIntent( location.getLongitude(),
                                                                            location.getLatitude(),
                                                                            location.getZoom() ),
                                      null );
      }
      else if ( !TextUtils.isEmpty( location.getAddress() ) )
      {
         return Intent.createChooser( createOpenLocationWithOtherAppIntent( location.getAddress() ),
                                      null );
      }
      
      else
      {
         return null;
      }
   }
   
   
   
   public final static Intent createOpenContactIntent( Context context,
                                                       String fullname,
                                                       String username )
   {
      return new Intent( Intent.ACTION_VIEW, ContentUris.TASKS_CONTENT_URI ).putExtras( Extras.createOpenContactExtras( context,
                                                                                                                        fullname,
                                                                                                                        username ) );
   }
   
   
   
   public final static Intent createShowPhonebookContactIntent( String lookUpKey )
   {
      return new Intent( Intent.ACTION_VIEW,
                         Uri.withAppendedPath( ContactsContract.Contacts.CONTENT_LOOKUP_URI,
                                               lookUpKey ) );
   }
   
   
   
   public final static Intent createOpenTaskIntent( Context context, long taskId )
   {
      return new Intent( Intent.ACTION_VIEW,
                         ContentUris.bindElementId( ContentUris.TASKS_CONTENT_URI_ID,
                                                    taskId ) );
   }
   
   
   
   public final static Intent createOpenTaskIntentFromNotification( Context context,
                                                                    long taskId )
   {
      return createOpenTaskIntent( context, taskId ).putExtra( Extras.KEY_FROM_NOTIFICATION,
                                                               true );
   }
   
   
   
   public final static Intent createEditTaskIntent( Context context, Task task )
   {
      return new Intent( Intent.ACTION_EDIT,
                         ContentUris.bindElementId( ContentUris.TASKS_CONTENT_URI_ID,
                                                    task.getId() ) ).putExtras( Extras.createEditTaskExtras( task ) );
   }
   
   
   
   public final static Intent createEditMultipleTasksIntent( Context context,
                                                             Collection< ? extends Task > tasks )
   {
      final Intent intent = new Intent( Intent.ACTION_EDIT,
                                        ContentUris.TASKS_CONTENT_URI );
      intent.putExtra( Extras.KEY_TASKS, new ArrayList< Task >( tasks ) );
      
      return intent;
   }
   
   
   
   public final static Intent createAddTaskIntent( Context context,
                                                   Bundle initialValues )
   {
      final Intent intent = new Intent( Intent.ACTION_INSERT,
                                        ContentUris.TASKS_CONTENT_URI ).putExtras( initialValues != null
                                                                                                        ? initialValues
                                                                                                        : Bundle.EMPTY );
      
      return intent;
   }
   
   
   
   public final static Intent createEditNoteIntent( Context context,
                                                    Task task,
                                                    Note note )
   {
      final Intent intent = new Intent( Intent.ACTION_EDIT,
                                        ContentUris.bindAggregatedElementIdToUri( ContentUris.TASK_NOTES_CONTENT_URI_ID,
                                                                                  task.getId(),
                                                                                  note.getId() ) );
      
      intent.putExtras( Extras.createEditNoteExtras( task, note.getId() ) );
      
      return intent;
   }
   
   
   
   public final static Intent createAddNoteIntent( Context context,
                                                   Task task,
                                                   String title,
                                                   String text )
   {
      final Intent intent = new Intent( Intent.ACTION_INSERT,
                                        ContentUris.TASK_NOTES_CONTENT_URI );
      
      intent.putExtras( Extras.createAddNoteExtras( task, title, text ) );
      
      return intent;
   }
   
   
   
   public final static Intent createChooseTagsIntent( Collection< String > preselectedTags )
   {
      final Intent intent = new Intent( Intent.ACTION_PICK,
                                        ContentUris.TAGS_CONTENT_URI );
      
      intent.putExtras( Extras.createChooseTagsExtras( preselectedTags ) );
      
      return intent;
   }
   
   
   
   public final static Intent createSmartFilterIntent( Context context,
                                                       RtmSmartFilter filter,
                                                       String title )
   {
      final Intent intent = new Intent( Intent.ACTION_VIEW,
                                        ContentUris.TASKS_CONTENT_URI );
      
      intent.putExtras( Extras.createSmartFilterExtras( context, filter, title ) );
      
      return intent;
   }
   
   
   
   public static Intent getFirstResolvable( Context context, Intent[] intents )
   {
      final PackageManager pm = context.getPackageManager();
      
      Intent firstResolvedIntent = null;
      for ( int i = 0; i < intents.length && firstResolvedIntent == null; i++ )
      {
         final Intent intent = intents[ i ];
         
         if ( intent != null )
         {
            final List< ResolveInfo > resolveInfos = pm.queryIntentActivities( intent,
                                                                               PackageManager.MATCH_DEFAULT_ONLY );
            if ( resolveInfos != null && resolveInfos.size() > 0 )
            {
               firstResolvedIntent = intent;
            }
         }
      }
      
      return firstResolvedIntent;
   }
}
