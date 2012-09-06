/* 
 *	Copyright (c) 2012 Ronny Röhricht
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

import android.content.ContentProviderClient;
import android.content.Context;
import android.database.Cursor;
import android.os.RemoteException;
import android.text.TextUtils;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.grammar.datetime.DateParser;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.RtmSmartFilter;


class LoadPermanentTasksAsyncTask extends AbstractNotificationTasksLoader
{
   private final boolean includeOverDueTasks;
   
   private final int type;
   
   
   
   public LoadPermanentTasksAsyncTask( Context context,
      ITasksLoadedHandler handler, int type, boolean includeOverdueTasks )
   {
      super( context, handler );
      
      this.type = type;
      this.includeOverDueTasks = includeOverdueTasks;
   }
   
   
   
   public String getFilterString()
   {
      String filterString;
      
      switch ( type )
      {
         case PermanentNotificationType.TODAY:
            filterString = RtmSmartFilterLexer.OP_DUE_LIT
               + DateParser.tokenNames[ DateParser.TODAY ];
            break;
         
         case PermanentNotificationType.TOMORROW:
            filterString = RtmSmartFilterLexer.OP_DUE_LIT
               + DateParser.tokenNames[ DateParser.TOMORROW ];
            break;
         
         case PermanentNotificationType.TODAY_AND_TOMORROW:
            filterString = RtmSmartFilterLexer.OP_DUE_WITHIN_LIT
               + RtmSmartFilterLexer.quotify( "2 of "
                  + DateParser.tokenNames[ DateParser.TODAY ] );
            break;
         
         default :
            filterString = Strings.EMPTY_STRING;
            break;
      }
      
      if ( includeOverDueTasks )
         filterString = includeOverdueTasks( filterString );
      
      return filterString;
   }
   
   
   
   @Override
   protected Cursor doInBackground( Void... params )
   {
      Cursor result = null;
      
      final ContentProviderClient client = context.getContentResolver()
                                                  .acquireContentProviderClient( Tasks.CONTENT_URI );
      
      if ( client != null )
      {
         final String filterString = getFilterString();
         final String evalFilter = RtmSmartFilter.evaluate( filterString, true );
         
         if ( !TextUtils.isEmpty( evalFilter ) )
         {
            try
            {
               result = client.query( Tasks.CONTENT_URI,
                                      TasksProviderPart.PROJECTION,
                                      evalFilter,
                                      null,
                                      null );
            }
            catch ( RemoteException e )
            {
               result = null;
            }
         }
         
         client.release();
      }
      
      return result;
   }
   
   
   
   private String includeOverdueTasks( String filterSting )
   {
      if ( TextUtils.isEmpty( filterSting ) )
         return getOverDueTasksFilter();
      else
         return appendOverDueTasksFilter( filterSting );
   }
   
   
   
   private String getOverDueTasksFilter()
   {
      return RtmSmartFilterLexer.OP_DUE_BEFORE_LIT
         + DateParser.tokenNames[ DateParser.NOW ];
   }
   
   
   
   private String appendOverDueTasksFilter( String filterSting )
   {
      final StringBuilder stringBuilder = new StringBuilder( filterSting );
      
      stringBuilder.append( " " )
                   .append( RtmSmartFilterLexer.OR_LIT )
                   .append( " " )
                   .append( getOverDueTasksFilter() );
      
      return stringBuilder.toString();
   }
}
