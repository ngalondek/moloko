/* 
 *	Copyright (c) 2010 Ronny R�hricht
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
 * Ronny R�hricht - implementation
 */

package dev.drsoran.moloko.notification;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import android.content.ContentProviderClient;
import android.content.Context;
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
import dev.drsoran.moloko.IOnTimeChangedListener;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.grammar.DateParser;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.util.DelayedRun;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.RtmSmartFilter;
import dev.drsoran.rtm.Task;


public class MolokoNotificationManager implements
         OnSharedPreferenceChangeListener, IOnBootCompletedListener,
         IOnTimeChangedListener
{
   private final static String TAG = "Moloko."
      + MolokoNotificationManager.class.getName();
   
   private final static int NOTIFICATION_TYPE_PERMANENT = 1;
   
   private final static int NOTIFICATION_PERM_OFF = 0;
   
   private final static int NOTIFICATION_PERM_TODAY = 1;
   
   private final static int NOTIFICATION_PERM_TOMORROW = 2;
   
   private final static int NOTIFICATION_DUE_UPD_REMIND_TIME = 0;
   
   private final static int NOTIFICATION_DUE_UPD_MINUTE_TICK = 1;
   
   private final static int NOTIFICATION_DUE_UPD_VIB_TONE_LED = 2;
   
   private final ExecutorService executorService = Executors.newSingleThreadExecutor();
   
   private final Runnable refreshNotificationsRunnable = new Runnable()
   {
      public void run()
      {
         reEvaluatePermanentNotifications();
         reCreateDueTaskNotifications();
      }
   };
   
   private final Context context;
   
   private final ContentObserver dbObserver;
   
   private final PermanentNotification permanentNotification;
   
   private final LinkedList< DueTaskNotification > dueTaskNotifications;
   
   

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
      permanentNotification.cancel();
      cancelAllDueTaskNotifications();
      
      TasksProviderPart.unregisterContentObserver( context, dbObserver );
      
      // IOnBootCompletedListener will be unregistered in onBootCompleted().
      MolokoApp.get( context ).unregisterOnTimeChangedListener( this );
      
      {
         final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( context );
         
         if ( prefs != null )
         {
            prefs.unregisterOnSharedPreferenceChangeListener( this );
         }
      }
      
      shutdownExecutorService();
   }
   


   public void onBootCompleted()
   {
      MolokoApp.get( context ).unregisterOnBootCompletedListener( this );
      reEvaluatePermanentNotifications();
      reCreateDueTaskNotifications();
   }
   


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
   


   public void onSharedPreferenceChanged( final SharedPreferences sharedPreferences,
                                          final String key )
   {
      if ( sharedPreferences != null && key != null )
      {
         if ( key.equals( context.getString( R.string.key_notify_permanent ) ) )
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
                     
                     // today
                     final Calendar cal = getDateOnlyCalendar( 0 );
                     
                     // Collect all tasks which due today + the reminder time span.
                     final StringBuffer buffer = new StringBuffer( Tasks.DUE_DATE );
                     
                     buffer.append( " >= " );
                     buffer.append( cal.getTimeInMillis() );
                     buffer.append( " AND " );
                     
                     // tomorrow
                     cal.roll( Calendar.DAY_OF_YEAR, true );
                     
                     buffer.append( Tasks.DUE_DATE );
                     buffer.append( " < " );
                     // + remind before time
                     buffer.append( cal.getTimeInMillis() + remindBeforeMillis );
                     
                     // only tasks with due time
                     buffer.append( " AND " );
                     buffer.append( Tasks.HAS_DUE_TIME );
                     buffer.append( " != 0" );
                     
                     // only incomplete tasks
                     buffer.append( " AND " );
                     buffer.append( Tasks.COMPLETED_DATE );
                     buffer.append( " IS NULL" );
                     
                     final ArrayList< Task > tasks = TasksProviderPart.getTasks( client,
                                                                                 buffer.toString(),
                                                                                 null );
                     if ( tasks != null )
                     {
                        if ( tasks.size() > 0 )
                        {
                           // first: Tasks which tasks are new notifications.
                           // second: DueTaskNotifications which must be canceled(false) or not.
                           final Pair< ArrayList< Task >, BitSet > diff = diffDueNotifications( tasks );
                           
                           // Cancel no longer active DueTaskNotifications
                           for ( int i = 0; i < dueTaskNotifications.size(); ++i )
                           {
                              if ( !diff.second.get( i ) )
                              {
                                 dueTaskNotifications.remove( i ).cancel();
                              }
                           }
                           
                           // Add new notifications
                           for ( Task task : diff.first )
                           {
                              // TODO: Optimization: Read the config values for ringtone etc outside the loop.
                              final String ringTone = prefs.getString( context.getString( R.string.key_notify_due_tasks_ringtone ),
                                                                       null );
                              
                              Uri ringToneUri = null;
                              
                              if ( !TextUtils.isEmpty( ringTone ) )
                              {
                                 ringToneUri = Uri.parse( ringTone );
                              }
                              
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
                     else
                     {
                        Log.e( TAG, "Error during database query." );
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
         public void run()
         {
            final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( context );
            
            if ( prefs != null )
            {
               int type = Integer.parseInt( prefs.getString( context.getString( R.string.key_notify_permanent ),
                                                             "0" ) );
               final ContentProviderClient client = context.getContentResolver()
                                                           .acquireContentProviderClient( Tasks.CONTENT_URI );
               
               if ( client != null )
               {
                  switch ( type )
                  {
                     case NOTIFICATION_PERM_TODAY:
                        updatePermanentNotification( client,
                                                     context.getString( R.string.phr_today_with_date,
                                                                        MolokoDateUtils.formatDate( getCalendar( 0 ).getTimeInMillis(),
                                                                                                    0 ) ),
                                                     DateParser.TODAY );
                        break;
                     case NOTIFICATION_PERM_TOMORROW:
                        updatePermanentNotification( client,
                                                     context.getString( R.string.phr_tomorrow_with_date,
                                                                        MolokoDateUtils.formatDate( getCalendar( 1 ).getTimeInMillis(),
                                                                                                    0 ) ),
                                                     DateParser.TOMORROW );
                        break;
                     case NOTIFICATION_PERM_OFF:
                        permanentNotification.cancel();
                        break;
                     default :
                        break;
                  }
               }
            }
         }
      } );
   }
   


   private void updatePermanentNotification( ContentProviderClient client,
                                             String title,
                                             int filterType )
   {
      RtmSmartFilter filter = null;
      Pair< String, Integer > text = null;
      
      filter = new RtmSmartFilter( RtmSmartFilterLexer.OP_DUE_LIT
         + DateParser.tokenNames[ filterType ] );
      
      text = buildPermanentNotificationRowText( client, filter );
      
      // Has tasks
      final boolean show = text.second > 0;
      
      if ( show )
         permanentNotification.update( title,
                                       text.first,
                                       text.second,
                                       Intents.createSmartFilterIntent( context,
                                                                        filter,
                                                                        title,
                                                                        -1 ) );
      else
         permanentNotification.cancel();
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
            
            default :
               Log.w( TAG, "Unknown due time notification update type " + what );
               break;
         }
      }
   }
   


   private Pair< String, Integer > buildPermanentNotificationRowText( ContentProviderClient client,
                                                                      RtmSmartFilter filter )
   {
      int count = 0;
      String result = null;
      
      final String evalFilter = filter.getEvaluatedFilterString( false );
      
      if ( !TextUtils.isEmpty( evalFilter ) )
      {
         final ArrayList< Task > tasks = TasksProviderPart.getTasks( client,
                                                                     evalFilter,
                                                                     null );
         if ( tasks != null )
         {
            int numHighPrioTasks = 0;
            
            count = tasks.size();
            
            if ( count > 0 )
            {
               for ( Iterator< Task > i = tasks.iterator(); i.hasNext(); )
               {
                  if ( i.next().getPriority() == RtmTask.Priority.High )
                     ++numHighPrioTasks;
               }
            }
            
            result = context.getString( R.string.notification_permanent,
                                        count,
                                        context.getResources()
                                               .getQuantityString( R.plurals.g_task,
                                                                   count ),
                                        numHighPrioTasks );
         }
         else
         {
            Log.e( TAG, "Error during database query." );
         }
      }
      else
      {
         Log.e( TAG, "Error evaluating RtmSmartFilter "
            + filter.getFilterString() );
      }
      
      return new Pair< String, Integer >( result, count );
   }
   


   private Calendar getCalendar( int dayOffset )
   {
      final Calendar cal = Calendar.getInstance( MolokoApp.getSettings()
                                                          .getTimezone() );
      cal.add( Calendar.DAY_OF_YEAR, dayOffset );
      
      return cal;
   }
   


   private Calendar getDateOnlyCalendar( int dayOffset )
   {
      final Calendar cal = getCalendar( dayOffset );
      
      cal.clear( Calendar.HOUR );
      cal.clear( Calendar.HOUR_OF_DAY );
      cal.clear( Calendar.MINUTE );
      cal.clear( Calendar.SECOND );
      cal.clear( Calendar.MILLISECOND );
      
      return cal;
   }
   


   private Pair< ArrayList< Task >, BitSet > diffDueNotifications( ArrayList< Task > tasks )
   {
      // first: Tasks which tasks are new notifications.
      // second: DueTaskNotifications which have been found or not.
      final Pair< ArrayList< Task >, BitSet > result = new Pair< ArrayList< Task >, BitSet >( new ArrayList< Task >(),
                                                                                              new BitSet( dueTaskNotifications.size() ) );
      
      result.second.clear();
      
      // Here we use O(n�) algorithm cause we do not expect so much notifications.
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
               result.second.set( j );
            }
         }
         
         // If the task has no notification then it is a new notification
         if ( !foundNotification )
            result.first.add( task );
      }
      
      return result;
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