/* 
 *	Copyright (c) 2010 Ronny Röhricht
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

package dev.drsoran.moloko.notification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import com.mdt.rtm.data.RtmTask;

import dev.drsoran.moloko.IOnBootCompletedListener;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.IOnTimeChangedListener;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.grammar.datetime.DateParser;
import dev.drsoran.moloko.util.DelayedRun;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.moloko.util.MolokoCalendar;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.RtmSmartFilter;
import dev.drsoran.rtm.Task;


public class MolokoNotificationManager implements
         OnSharedPreferenceChangeListener, IOnBootCompletedListener,
         IOnTimeChangedListener, IOnSettingsChangedListener
{
   private final static String TAG = "Moloko."
      + MolokoNotificationManager.class.getName();
   
   private final static int NOTIFICATION_TYPE_PERMANENT = 1;
   
   private final static int NOTIFICATION_PERM_OFF = 0;
   
   private final static int NOTIFICATION_PERM_TODAY = 1;
   
   private final static int NOTIFICATION_PERM_TOMORROW = 2;
   
   private final static int NOTIFICATION_PERM_TODAY_AND_TOMORROW = 3;
   
   private final static int NOTIFICATION_DUE_UPD_REMIND_TIME = 0;
   
   private final static int NOTIFICATION_DUE_UPD_MINUTE_TICK = 1;
   
   private final static int NOTIFICATION_DUE_UPD_VIB_TONE_LED = 2;
   
   private final static int NOTIFICATION_DUE_UPD_TIME_FORMAT_CHANGED = 3;
   
   private final static String DUE_TASKS_QUERY_WITH_REMIND_BEFORE = Tasks.DUE_DATE
      + " >= ? AND "
      + Tasks.DUE_DATE
      + " < ? AND "
      + Tasks.HAS_DUE_TIME
      + " != 0 AND "
      + Tasks.COMPLETED_DATE
      + " IS NULL AND "
      + Tasks.DELETED_DATE + " IS NULL";
   
   private final ExecutorService executorService = Executors.newSingleThreadExecutor();
   
   private final Runnable refreshNotificationsRunnable = new Runnable()
   {
      @Override
      public void run()
      {
         reEvaluatePermanentNotifications();
         reCreateDueTaskNotifications();
      }
   };
   
   private final Context context;
   
   private final ContentObserver dbObserver;
   
   private final PermanentNotification permanentNotification;
   
   private final List< DueTaskNotification > dueTaskNotifications;
   
   

   public MolokoNotificationManager( Context context )
   {
      this.context = context;
      
      final Handler handler = MolokoApp.get( context ).getHandler();
      
      dbObserver = new ContentObserver( handler )
      {
         @Override
         public void onChange( boolean selfChange )
         {
            // Aggregate several calls to a single update.
            DelayedRun.run( handler, refreshNotificationsRunnable, 1000 );
         }
      };
      
      permanentNotification = new PermanentNotification( context,
                                                         NOTIFICATION_TYPE_PERMANENT );
      
      dueTaskNotifications = new LinkedList< DueTaskNotification >();
      
      MolokoApp.get( context ).registerOnBootCompletedListener( this );
      MolokoApp.get( context )
               .registerOnTimeChangedListener( IOnTimeChangedListener.ALL, this );
      MolokoApp.get( context )
               .registerOnSettingsChangedListener( IOnSettingsChangedListener.DATE_TIME_RELATED,
                                                   this );
      {
         final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( context );
         
         if ( prefs != null )
         {
            prefs.registerOnSharedPreferenceChangeListener( this );
         }
      }
      
      TasksProviderPart.registerContentObserver( context, dbObserver );
      
      reEvaluatePermanentNotifications();
      reCreateDueTaskNotifications();
   }
   


   public void shutdown()
   {
      cancelAllDueTaskNotifications();
      shutdownExecutorService();
      
      permanentNotification.cancel();
      
      TasksProviderPart.unregisterContentObserver( context, dbObserver );
      
      // IOnBootCompletedListener will be unregistered in onBootCompleted().
      MolokoApp.get( context ).unregisterOnTimeChangedListener( this );
      MolokoApp.get( context ).unregisterOnSettingsChangedListener( this );
      
      {
         final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( context );
         
         if ( prefs != null )
         {
            prefs.unregisterOnSharedPreferenceChangeListener( this );
         }
      }
   }
   


   @Override
   public void onBootCompleted()
   {
      MolokoApp.get( context ).unregisterOnBootCompletedListener( this );
      reEvaluatePermanentNotifications();
      reCreateDueTaskNotifications();
   }
   


   @Override
   public void onTimeChanged( int which )
   {
      switch ( which )
      {
         case IOnTimeChangedListener.MIDNIGHT:
            reEvaluatePermanentNotifications();
            reCreateDueTaskNotifications();
            break;
         
         case IOnTimeChangedListener.SYSTEM_TIME:
            reEvaluatePermanentNotifications();
            reEvaluateDueTaskNotifications( NOTIFICATION_DUE_UPD_MINUTE_TICK );
            break;
         
         case IOnTimeChangedListener.MINUTE_TICK:
            reEvaluateDueTaskNotifications( NOTIFICATION_DUE_UPD_MINUTE_TICK );
            
         default :
            break;
      }
   }
   


   @Override
   public void onSettingsChanged( int which,
                                  HashMap< Integer, Object > oldValues )
   {
      switch ( which )
      {
         case IOnSettingsChangedListener.DATEFORMAT:
            reEvaluatePermanentNotifications();
            break;
         
         case IOnSettingsChangedListener.TIMEFORMAT:
            reEvaluateDueTaskNotifications( NOTIFICATION_DUE_UPD_TIME_FORMAT_CHANGED );
            break;
         
         default :
            break;
      }
      
   }
   


   @Override
   public void onSharedPreferenceChanged( final SharedPreferences sharedPreferences,
                                          final String key )
   {
      if ( sharedPreferences != null && key != null )
      {
         if ( key.equals( context.getString( R.string.key_notify_permanent ) )
            || key.equals( context.getString( R.string.key_notify_permanent_overdue ) ) )
         {
            reEvaluatePermanentNotifications();
         }
         else if ( key.equals( context.getString( R.string.key_notify_due_tasks ) ) )
         {
            reCreateDueTaskNotifications();
         }
         else if ( key.equals( context.getString( R.string.key_notify_due_tasks_ringtone ) )
            || key.equals( context.getString( R.string.key_notify_due_tasks_vibrate ) )
            || key.equals( context.getString( R.string.key_notify_due_tasks_led ) ) )
         {
            reEvaluateDueTaskNotifications( NOTIFICATION_DUE_UPD_VIB_TONE_LED );
         }
         else if ( key.equals( context.getString( R.string.key_notify_due_tasks_before ) ) )
         {
            reEvaluateDueTaskNotifications( NOTIFICATION_DUE_UPD_REMIND_TIME );
         }
      }
   }
   


   public void reCreateDueTaskNotifications()
   {
      executorService.execute( new Runnable()
      {
         @Override
         public void run()
         {
            final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( context );
            
            if ( prefs != null )
            {
               final boolean show = prefs.getBoolean( context.getString( R.string.key_notify_due_tasks ),
                                                      false );
               
               if ( show )
               {
                  final ContentProviderClient client = context.getContentResolver()
                                                              .acquireContentProviderClient( Tasks.CONTENT_URI );
                  
                  if ( client != null )
                  {
                     final long remindBeforeMillis = Integer.parseInt( prefs.getString( context.getString( R.string.key_notify_due_tasks_before ),
                                                                                        context.getString( R.string.moloko_prefs_notification_tasks_w_due_before_default_value ) ) );
                     
                     final List< Task > tasks = TasksProviderPart.getTasks( client,
                                                                            getDueTasksSelection( remindBeforeMillis ),
                                                                            null );
                     client.release();
                     
                     if ( tasks != null )
                     {
                        diffDueNotifications( tasks );
                     }
                     else
                     {
                        Log.e( TAG, LogUtils.GENERIC_DB_ERROR );
                     }
                  }
               }
            }
            else
               cancelAllDueTaskNotifications();
         }
      } );
   }
   


   public void reEvaluateDueTaskNotifications( final int what )
   {
      executorService.execute( new Runnable()
      {
         @Override
         public void run()
         {
            if ( dueTaskNotifications.size() > 0 )
            {
               for ( DueTaskNotification notification : dueTaskNotifications )
               {
                  updateDueTaskNotification( notification, what );
               }
            }
         }
      } );
   }
   


   public void cancelAllDueTaskNotifications()
   {
      executorService.execute( new Runnable()
      {
         @Override
         public void run()
         {
            for ( DueTaskNotification notification : dueTaskNotifications )
            {
               notification.cancel();
            }
            
            dueTaskNotifications.clear();
         }
      } );
   }
   


   private void reEvaluatePermanentNotifications()
   {
      executorService.execute( new Runnable()
      {
         @Override
         public void run()
         {
            final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( context );
            
            if ( prefs != null )
            {
               final int type = Integer.parseInt( prefs.getString( context.getString( R.string.key_notify_permanent ),
                                                                   "0" ) );
               final boolean showOverdue = prefs.getBoolean( context.getString( R.string.key_notify_permanent_overdue ),
                                                             false );
               final ContentProviderClient client = context.getContentResolver()
                                                           .acquireContentProviderClient( Tasks.CONTENT_URI );
               
               if ( client != null )
               {
                  switch ( type )
                  {
                     case NOTIFICATION_PERM_TODAY:
                     {
                        String filterSting = RtmSmartFilterLexer.OP_DUE_LIT
                           + DateParser.tokenNames[ DateParser.TODAY ];
                        
                        if ( showOverdue )
                           filterSting = includeOverdueTasks( filterSting );
                        
                        updatePermanentNotification( client,
                                                     context.getString( R.string.notification_permanent_today_title,
                                                                        MolokoDateUtils.formatDate( context,
                                                                                                    getCalendar( 0 ).getTimeInMillis(),
                                                                                                    0 ) ),
                                                     filterSting );
                     }
                        break;
                     
                     case NOTIFICATION_PERM_TOMORROW:
                     {
                        String filterSting = RtmSmartFilterLexer.OP_DUE_LIT
                           + DateParser.tokenNames[ DateParser.TOMORROW ];
                        
                        if ( showOverdue )
                           filterSting = includeOverdueTasks( filterSting );
                        
                        updatePermanentNotification( client,
                                                     context.getString( R.string.notification_permanent_tomorrow_title,
                                                                        MolokoDateUtils.formatDate( context,
                                                                                                    getCalendar( 1 ).getTimeInMillis(),
                                                                                                    0 ) ),
                                                     filterSting );
                     }
                        break;
                     
                     case NOTIFICATION_PERM_TODAY_AND_TOMORROW:
                     {
                        String filterSting = RtmSmartFilterLexer.OP_DUE_WITHIN_LIT
                           + RtmSmartFilterLexer.quotify( "2 of "
                              + DateParser.tokenNames[ DateParser.TODAY ] );
                        
                        if ( showOverdue )
                           filterSting = includeOverdueTasks( filterSting );
                        
                        updatePermanentNotification( client,
                                                     context.getString( R.string.notification_permanent_today_and_tomorrow_title,
                                                                        MolokoDateUtils.formatDate( context,
                                                                                                    getCalendar( 0 ).getTimeInMillis(),
                                                                                                    MolokoDateUtils.FORMAT_NUMERIC ),
                                                                        MolokoDateUtils.formatDate( context,
                                                                                                    getCalendar( 1 ).getTimeInMillis(),
                                                                                                    MolokoDateUtils.FORMAT_NUMERIC ) ),
                                                     filterSting );
                     }
                        break;
                     
                     case NOTIFICATION_PERM_OFF:
                     {
                        if ( showOverdue )
                        {
                           updatePermanentNotification( client,
                                                        context.getString( R.string.notification_permanent_overdue_title ),
                                                        RtmSmartFilterLexer.OP_DUE_BEFORE_LIT
                                                           + DateParser.tokenNames[ DateParser.NOW ] );
                        }
                        else
                        {
                           permanentNotification.cancel();
                        }
                     }
                        break;
                     
                     default :
                        break;
                  }
               }
            }
         }
         


         private String includeOverdueTasks( String filterSting )
         {
            final StringBuilder stringBuilder = new StringBuilder( filterSting );
            
            stringBuilder.append( " " )
                         .append( RtmSmartFilterLexer.OR_LIT )
                         .append( " " )
                         .append( RtmSmartFilterLexer.OP_DUE_BEFORE_LIT )
                         .append( DateParser.tokenNames[ DateParser.NOW ] );
            
            return stringBuilder.toString();
         }
      } );
   }
   


   private void updatePermanentNotification( ContentProviderClient client,
                                             String title,
                                             String filterString )
   {
      final RtmSmartFilter filter = new RtmSmartFilter( filterString );
      
      // first: The complete notification text to show
      // second: The tasks affected
      final Pair< String, List< Task > > permNotificationInfo = buildPermanentNotificationRowText( client,
                                                                                                   filter );
      // Has tasks
      final boolean show = permNotificationInfo.second.size() > 0;
      
      if ( show )
      {
         final Intent onClickIntent = permNotificationInfo.second.size() == 1
                                                                             ? Intents.createOpenTaskIntent( context,
                                                                                                             permNotificationInfo.second.get( 0 )
                                                                                                                                        .getId() )
                                                                             : Intents.createSmartFilterIntent( context,
                                                                                                                filter,
                                                                                                                title );
         permanentNotification.update( title,
                                       permNotificationInfo.first,
                                       permNotificationInfo.second.size(),
                                       onClickIntent );
         
      }
      else
      {
         permanentNotification.cancel();
      }
   }
   


   private void updateDueTaskNotification( DueTaskNotification notification,
                                           int what )
   {
      final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( context );
      
      if ( prefs != null )
      {
         switch ( what )
         {
            case NOTIFICATION_DUE_UPD_REMIND_TIME:
               final long remindBeforeMillis = Integer.parseInt( prefs.getString( context.getString( R.string.key_notify_due_tasks_before ),
                                                                                  context.getString( R.string.moloko_prefs_notification_tasks_w_due_before_default_value ) ) );
               notification.update( remindBeforeMillis );
               break;
            
            case NOTIFICATION_DUE_UPD_VIB_TONE_LED:
               final String ringTone = prefs.getString( context.getString( R.string.key_notify_due_tasks_ringtone ),
                                                        null );
               
               Uri ringToneUri = null;
               
               if ( !TextUtils.isEmpty( ringTone ) )
               {
                  ringToneUri = Uri.parse( ringTone );
               }
               
               notification.update( prefs.getBoolean( context.getString( R.string.key_notify_due_tasks_vibrate ),
                                                      false ),
                                    prefs.getBoolean( context.getString( R.string.key_notify_due_tasks_led ),
                                                      false ),
                                    ringToneUri );
               break;
            
            case NOTIFICATION_DUE_UPD_MINUTE_TICK:
               notification.updateMinuteTick();
               break;
            
            case NOTIFICATION_DUE_UPD_TIME_FORMAT_CHANGED:
               notification.onTimeFormatChanged();
               break;
            
            default :
               Log.w( TAG, "Unknown due time notification update type " + what );
               break;
         }
      }
   }
   


   private Pair< String, List< Task > > buildPermanentNotificationRowText( ContentProviderClient client,
                                                                           RtmSmartFilter filter )
   {
      List< Task > affectedTasks = null;
      String result = Strings.EMPTY_STRING;
      
      final String evalFilter = filter.getEvaluatedFilterString( false );
      
      if ( !TextUtils.isEmpty( evalFilter ) )
      {
         affectedTasks = TasksProviderPart.getTasks( client, evalFilter, null );
         
         if ( affectedTasks != null )
         {
            int tasksCount = affectedTasks.size();
            
            if ( tasksCount > 0 )
            {
               final Pair< Integer, Integer > numHighPrioAndOverdueTasks = countHighPrioAndOverdueTasks( affectedTasks );
               final int highPrioCnt = numHighPrioAndOverdueTasks.first.intValue();
               final int overdueCnt = numHighPrioAndOverdueTasks.second.intValue();
               final int tasksDueCnt = tasksCount - overdueCnt;
               
               if ( tasksCount == 1 )
               {
                  result = context.getString( R.string.notification_permanent_text_one_task,
                                              affectedTasks.get( 0 ).getName() );
               }
               
               else if ( tasksDueCnt > 0 )
               {
                  if ( overdueCnt == 0 )
                     result = context.getString( R.string.notification_permanent_text_multiple,
                                                 tasksDueCnt,
                                                 context.getResources()
                                                        .getQuantityString( R.plurals.g_task,
                                                                            tasksDueCnt ),
                                                 highPrioCnt );
                  else if ( overdueCnt > 0 )
                     result = context.getString( R.string.notification_permanent_text_multiple_w_overdue,
                                                 tasksDueCnt,
                                                 context.getResources()
                                                        .getQuantityString( R.plurals.g_task,
                                                                            tasksDueCnt ),
                                                 overdueCnt,
                                                 context.getResources()
                                                        .getQuantityString( R.plurals.g_task,
                                                                            overdueCnt ) );
               }
               
               else if ( tasksDueCnt == 0 && overdueCnt > 1 )
               {
                  result = context.getString( R.string.notification_permanent_text_multiple_overdue,
                                              overdueCnt,
                                              context.getResources()
                                                     .getQuantityString( R.plurals.g_task,
                                                                         overdueCnt ) );
               }
               
               else
               {
                  result = String.format( "Unhandled case tasks:%d, due:%d, overdue %d",
                                          tasksCount,
                                          tasksDueCnt,
                                          overdueCnt );
               }
            }
         }
         else
         {
            Log.e( TAG, LogUtils.GENERIC_DB_ERROR );
         }
      }
      else
      {
         Log.e( TAG, "Error evaluating RtmSmartFilter "
            + filter.getFilterString() );
      }
      
      if ( affectedTasks == null )
         return new Pair< String, List< Task > >( result,
                                                  new ArrayList< Task >( 0 ) );
      else
         return new Pair< String, List< Task >>( result, affectedTasks );
   }
   


   private Pair< Integer, Integer > countHighPrioAndOverdueTasks( List< Task > tasks )
   {
      int numOverdueTasks = 0;
      int numHighPrioTasks = 0;
      
      final int tasksCount = tasks.size();
      
      if ( tasksCount > 0 )
      {
         MolokoCalendar nowCal = null;
         
         for ( Iterator< Task > i = tasks.iterator(); i.hasNext(); )
         {
            final Task task = i.next();
            
            if ( task.getPriority() == RtmTask.Priority.High )
            {
               ++numHighPrioTasks;
            }
            
            final Date due = task.getDue();
            
            if ( due != null )
            {
               if ( nowCal == null )
                  nowCal = MolokoCalendar.getInstance();
               
               // If the task has a due time then it can be overdue
               // even today.
               if ( task.hasDueTime()
                  && MolokoDateUtils.isBefore( due.getTime(),
                                               nowCal.getTimeInMillis() ) )
               {
                  ++numOverdueTasks;
               }
               
               // If the task has no due time then it can be overdue
               // only before today.
               else if ( !MolokoDateUtils.isToday( due.getTime() )
                  && !MolokoDateUtils.isAfter( due.getTime(),
                                               nowCal.getTimeInMillis() ) )
               {
                  ++numOverdueTasks;
               }
            }
         }
      }
      
      return Pair.create( Integer.valueOf( numHighPrioTasks ),
                          Integer.valueOf( numOverdueTasks ) );
   }
   


   private String getDueTasksSelection( long remindBeforeMillis )
   {
      final MolokoCalendar cal = getDateOnlyCalendar( 0 );
      final long today = cal.getTimeInMillis();
      
      cal.roll( Calendar.DAY_OF_YEAR, true );
      
      final long tomorrowPlusReminder = cal.getTimeInMillis()
         + remindBeforeMillis;
      
      final String result = Queries.bindAll( DUE_TASKS_QUERY_WITH_REMIND_BEFORE,
                                             new String[]
                                             {
                                              String.valueOf( today ),
                                              String.valueOf( tomorrowPlusReminder ) } );
      
      return result;
   }
   


   private static MolokoCalendar getCalendar( int dayOffset )
   {
      final MolokoCalendar cal = MolokoCalendar.getInstance();
      cal.add( Calendar.DAY_OF_YEAR, dayOffset );
      
      return cal;
   }
   


   private static MolokoCalendar getDateOnlyCalendar( int dayOffset )
   {
      final MolokoCalendar cal = getCalendar( dayOffset );
      cal.setHasTime( false );
      
      return cal;
   }
   


   private void diffDueNotifications( List< Task > tasks )
   {
      // Build a snapshot of the currently scheduled notifications
      final List< DueTaskNotification > unhandledNotifications = new ArrayList< DueTaskNotification >( dueTaskNotifications );
      
      List< Task > newNotifications = null;
      
      // Here we use O(n²) algorithm cause we do not expect so much notifications.
      for ( int i = 0, taskCount = tasks.size(); i < taskCount; ++i )
      {
         final Task task = tasks.get( i );
         
         boolean foundNotification = false;
         
         for ( int j = 0, notifCount = dueTaskNotifications.size(); j < notifCount
            && !foundNotification; ++j )
         {
            final DueTaskNotification notification = dueTaskNotifications.get( j );
            
            // If the task ID and the due time is the same then we leave the
            // notification untouched.
            if ( notification.getTaskId().equals( task.getId() )
               && notification.getDueTime() == task.getDue().getTime() )
            {
               foundNotification = true;
               unhandledNotifications.remove( notification );
            }
         }
         
         // If the task has no notification then it is a new notification
         if ( !foundNotification )
         {
            if ( newNotifications == null )
               newNotifications = new LinkedList< Task >();
            
            newNotifications.add( task );
         }
      }
      
      cancelDueTaskNotifications( unhandledNotifications );
      
      if ( newNotifications != null )
         addNewDueTaskNotifications( newNotifications );
   }
   


   private void addNewDueTaskNotifications( List< Task > tasks )
   {
      final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( context );
      
      if ( prefs != null )
      {
         final String ringTone = prefs.getString( context.getString( R.string.key_notify_due_tasks_ringtone ),
                                                  null );
         
         final long remindBeforeMillis = Integer.parseInt( prefs.getString( context.getString( R.string.key_notify_due_tasks_before ),
                                                                            context.getString( R.string.moloko_prefs_notification_tasks_w_due_before_default_value ) ) );
         
         Uri ringToneUri = null;
         
         if ( !TextUtils.isEmpty( ringTone ) )
            ringToneUri = Uri.parse( ringTone );
         
         for ( Task task : tasks )
         {
            final DueTaskNotification newNotification = new DueTaskNotification( context,
                                                                                 task,
                                                                                 remindBeforeMillis,
                                                                                 prefs.getBoolean( context.getString( R.string.key_notify_due_tasks_vibrate ),
                                                                                                   false ),
                                                                                 prefs.getBoolean( context.getString( R.string.key_notify_due_tasks_led ),
                                                                                                   false ),
                                                                                 ringToneUri );
            dueTaskNotifications.add( newNotification );
            updateDueTaskNotification( newNotification,
                                       NOTIFICATION_DUE_UPD_VIB_TONE_LED );
         }
      }
   }
   


   private void cancelDueTaskNotifications( List< DueTaskNotification > notifications )
   {
      for ( DueTaskNotification dueTaskNotification : notifications )
      {
         final int idx = dueTaskNotifications.indexOf( dueTaskNotification );
         if ( idx != -1 )
         {
            dueTaskNotifications.remove( idx ).cancel();
         }
      }
   }
   


   private void shutdownExecutorService()
   {
      // Disable new tasks from being submitted
      executorService.shutdown();
      
      try
      {
         // Wait a while for existing tasks to terminate
         if ( !executorService.awaitTermination( 2, TimeUnit.SECONDS ) )
         {
            // Cancel currently executing tasks
            executorService.shutdownNow();
            
            // Wait a while for tasks to respond to being canceled
            if ( !executorService.awaitTermination( 2, TimeUnit.SECONDS ) )
               Log.e( TAG, "ExecutorService thread pool did not terminate" );
         }
      }
      catch ( InterruptedException ie )
      {
         // (Re-)Cancel if current thread also interrupted
         executorService.shutdownNow();
         // Preserve interrupt status
         Thread.currentThread().interrupt();
      }
   }
   
}
