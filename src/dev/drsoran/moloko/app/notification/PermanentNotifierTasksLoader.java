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

package dev.drsoran.moloko.app.notification;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import android.content.ContentProviderClient;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.settings.PermanentNotificationType;
import dev.drsoran.moloko.app.settings.Settings;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.content.db.RtmListsTable;
import dev.drsoran.moloko.grammar.datetime.DateParser;
import dev.drsoran.moloko.grammar.rtmsmart.RtmSmartFilterLexer;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.RtmSmartFilter;


class PermanentNotifierTasksLoader extends
         AbstractFilterBasedNotificationTasksLoader
{
   private final Map< PermanentNotificationType, Collection< String >> listIdsOfTasksToNotify;
   
   private volatile String filterString;
   
   public final static int ID = R.id.loader_permanent_notifier_tasks;
   
   
   
   public PermanentNotifierTasksLoader(
      Context context,
      Map< PermanentNotificationType, Collection< String >> listIdsOfTasksToNotify )
   {
      super( context );
      this.listIdsOfTasksToNotify = listIdsOfTasksToNotify;
   }
   
   
   
   @Override
   public String getFilterString()
   {
      return filterString;
   }
   
   
   
   @Override
   protected Cursor queryResultInBackground( ContentProviderClient client )
   {
      Cursor result = null;
      
      try
      {
         buildFilterString();
         final String evalFilter = RtmSmartFilter.evaluate( filterString, true );
         
         if ( !TextUtils.isEmpty( evalFilter ) )
         {
            result = client.query( getContentUri(),
                                   TasksProviderPart.PROJECTION,
                                   evalFilter,
                                   null,
                                   null );
         }
      }
      catch ( RemoteException e )
      {
         result = null;
      }
      
      return result;
   }
   
   
   
   @Override
   protected Uri getContentUri()
   {
      return Tasks.CONTENT_URI;
   }
   
   
   
   @Override
   protected void registerContentObserver( ContentObserver observer )
   {
      TasksProviderPart.registerContentObserver( getContext(), observer );
   }
   
   
   
   @Override
   protected void unregisterContentObserver( ContentObserver observer )
   {
      TasksProviderPart.unregisterContentObserver( getContext(), observer );
   }
   
   
   
   /**
    * This is called from the background thread
    */
   private void buildFilterString()
   {
      final StringBuilder filterStringBuilder = new StringBuilder();
      
      boolean isFirst = true;
      for ( Iterator< PermanentNotificationType > i = listIdsOfTasksToNotify.keySet()
                                                                            .iterator(); i.hasNext(); )
      {
         final PermanentNotificationType notificationType = i.next();
         final Collection< String > taskListIds = listIdsOfTasksToNotify.get( notificationType );
         
         if ( !taskListIds.isEmpty() )
         {
            final int stringBuilderPosition = filterStringBuilder.length();
            
            if ( appendFilterString( notificationType,
                                     taskListIds,
                                     filterStringBuilder ) )
            {
               if ( !isFirst )
               {
                  filterStringBuilder.insert( stringBuilderPosition,
                                              RtmSmartFilterLexer.OR_LIT + " (" );
               }
               else
               {
                  filterStringBuilder.insert( stringBuilderPosition, " (" );
               }
               
               filterStringBuilder.append( ") " );
               
               isFirst = false;
            }
         }
      }
      
      filterString = filterStringBuilder.toString();
   }
   
   
   
   private boolean appendFilterString( PermanentNotificationType notificationType,
                                       Collection< String > taskListIds,
                                       StringBuilder filterStringBuilder )
   {
      boolean hasAppended = false;
      
      if ( !taskListIds.contains( Settings.ALL_LISTS ) )
      {
         final Collection< String > resolvedListNames = resolveListIdsToListNames( taskListIds );
         
         // It may happen that we cannot resolve a list if the list has been deleted by
         // a background sync but the setting is not up to date. In this case we do not show
         // anything since the setting has now the meaning of "Off".
         if ( !resolvedListNames.isEmpty() )
         {
            appendDueFilter( notificationType, filterStringBuilder );
            appendListNamesFilter( resolvedListNames, filterStringBuilder );
            hasAppended = true;
         }
      }
      
      // If we have "All" lists to show, then we do not add them to the filter.
      else
      {
         appendDueFilter( notificationType, filterStringBuilder );
         hasAppended = true;
      }
      
      return hasAppended;
   }
   
   
   
   private void appendDueFilter( PermanentNotificationType notificationType,
                                 StringBuilder filterStringBuilder )
   {
      switch ( notificationType )
      {
         case TODAY:
            filterStringBuilder.append( RtmSmartFilterLexer.OP_DUE_LIT )
                               .append( DateParser.tokenNames[ DateParser.TODAY ] );
            break;
         
         case TOMORROW:
            filterStringBuilder.append( RtmSmartFilterLexer.OP_DUE_LIT )
                               .append( DateParser.tokenNames[ DateParser.TOMORROW ] );
            break;
         
         case OVERDUE:
            filterStringBuilder.append( RtmSmartFilterLexer.OP_DUE_BEFORE_LIT )
                               .append( DateParser.tokenNames[ DateParser.NOW ] );
            break;
         
         default :
            throw new IllegalArgumentException( "Unexpected permanent notification type "
               + notificationType.toString() );
      }
   }
   
   
   
   private void appendListNamesFilter( Collection< String > listNames,
                                       StringBuilder filterStringBuilder )
   {
      filterStringBuilder.append( " " )
                         .append( RtmSmartFilterLexer.AND_LIT )
                         .append( " (" );
      
      boolean isFirst = true;
      for ( String listName : listNames )
      {
         if ( !isFirst )
         {
            filterStringBuilder.append( " " )
                               .append( RtmSmartFilterLexer.OR_LIT )
                               .append( " " );
         }
         
         filterStringBuilder.append( RtmSmartFilterLexer.OP_LIST_LIT )
                            .append( "\"" )
                            .append( listName )
                            .append( "\"" );
         
         isFirst = false;
      }
      
      filterStringBuilder.append( ")" );
   }
   
   
   
   private Collection< String > resolveListIdsToListNames( Collection< String > taskListIds )
   {
      ContentProviderClient client = null;
      
      try
      {
         client = getContext().getContentResolver()
                              .acquireContentProviderClient( Lists.CONTENT_URI );
         return RtmListsTable.resolveListIdsToListNames( client,
                                                                taskListIds );
      }
      finally
      {
         if ( client != null )
         {
            client.release();
         }
      }
   }
}
