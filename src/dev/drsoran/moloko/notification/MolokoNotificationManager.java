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
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.ContentObserver;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import com.mdt.rtm.data.RtmTask;

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
         OnSharedPreferenceChangeListener
{
   private final static String TAG = MolokoNotificationManager.class.getName();
   
   private final static int NOTIFICATION_TYPE_PERMANENT = 1;
   
   private final static int NOTIFICATION_PERM_OFF = 0;
   
   private final static int NOTIFICATION_PERM_TODAY = 1;
   
   private final static int NOTIFICATION_PERM_TOMORROW = 2;
   
   private final ExecutorService executorService = Executors.newSingleThreadExecutor();
   
   private final Runnable refreshNotificationsRunnable = new Runnable()
   {
      public void run()
      {
         reEvaluatePermanentNotifications();
      }
   };
   
   private final Context context;
   
   private final ContentObserver dbObserver;
   
   private final PermanentNotification permanentNotification;
   
   

   public MolokoNotificationManager( Context context, final Handler handler )
   {
      this.context = context;
      
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
      final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( context );
      
      if ( prefs != null )
      {
         prefs.registerOnSharedPreferenceChangeListener( this );
      }
      
      TasksProviderPart.registerContentObserver( context, dbObserver );
      
      reEvaluatePermanentNotifications();
   }
   


   public void shutdown()
   {
      permanentNotification.cancel();
      
      TasksProviderPart.unregisterContentObserver( context, dbObserver );
      
      final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( context );
      
      if ( prefs != null )
      {
         prefs.unregisterOnSharedPreferenceChangeListener( this );
      }
      
      shutdownExecutorService();
   }
   


   public void onSharedPreferenceChanged( final SharedPreferences sharedPreferences,
                                          final String key )
   {
      if ( sharedPreferences != null && key != null
         && ( key.equals( context.getString( R.string.key_notify_permanent ) ) ) )
      {
         reEvaluatePermanentNotifications();
      }
   }
   


   public void reEvaluatePermanentNotifications()
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
   


   private Pair< String, Integer > buildPermanentNotificationRowText( ContentProviderClient client,
                                                                      RtmSmartFilter filter )
   {
      int count = 0;
      String result = null;
      
      final String evalFilter = filter.getEvaluatedFilterString();
      
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
         Log.e( TAG, "Error evaluating RtmSmartFilter." );
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
