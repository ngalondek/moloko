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

package dev.drsoran.moloko.util;

import java.util.ArrayList;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.Context;

import com.mdt.rtm.data.RtmList;

import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.content.ApplyContentChangesInfo;
import dev.drsoran.moloko.app.content.ContentProviderAction;
import dev.drsoran.moloko.app.content.ContentProviderActionItemList;
import dev.drsoran.moloko.app.settings.Settings;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.content.db.CreationsProviderPart;
import dev.drsoran.moloko.content.db.DbUtils;
import dev.drsoran.moloko.content.db.Modification;
import dev.drsoran.moloko.content.db.RtmListsTable;
import dev.drsoran.moloko.content.db.RtmTaskSeriesTable;
import dev.drsoran.provider.Rtm.Lists;


public final class RtmListEditUtils
{
   private RtmListEditUtils()
   {
      throw new AssertionError();
   }
   
   
   
   public final static ApplyContentChangesInfo setListName( Context context,
                                                     String listId,
                                                     String name )
   {
      final ModificationSet modifications = new ModificationSet();
      
      modifications.add( Modification.newModification( DbUtils.contentUriWithId( Lists.CONTENT_URI,
                                                                                 listId ),
                                                       Lists.LIST_NAME,
                                                       name ) );
      modifications.add( Modification.newListModified( listId ) );
      
      return new ApplyContentChangesInfo( modifications.toContentProviderActionItemList(),
                                   context.getString( R.string.toast_save_list ),
                                   context.getString( R.string.toast_save_list_ok ),
                                   context.getString( R.string.toast_save_list_failed ) );
   }
   
   
   
   public final static ApplyContentChangesInfo deleteListByName( Context context,
                                                          String listName )
   {
      final ContentProviderClient client = context.getContentResolver()
                                                  .acquireContentProviderClient( Lists.CONTENT_URI );
      try
      {
         final RtmList list = RtmListsTable.getListByName( client,
                                                                  listName );
         if ( list != null )
         {
            return deleteList( context, list );
         }
      }
      finally
      {
         client.release();
      }
      
      return ApplyContentChangesInfo.failed( context.getString( R.string.toast_delete_list_failed,
                                                         listName ) );
   }
   
   
   
   public final static ApplyContentChangesInfo insertList( Context context,
                                                    RtmList list )
   {
      ContentProviderActionItemList actionItemList = new ContentProviderActionItemList();
      
      boolean ok = actionItemList.add( ContentProviderAction.Type.INSERT,
                                       RtmListsTable.insertLocalCreatedList( list ) );
      ok = ok
         && actionItemList.add( ContentProviderAction.Type.INSERT,
                                CreationsProviderPart.newCreation( DbUtils.contentUriWithId( Lists.CONTENT_URI,
                                                                                             list.getId() ),
                                                                   list.getCreatedDate()
                                                                       .getTime() ) );
      if ( !ok )
      {
         actionItemList = null;
      }
      
      final String listname = list.getName();
      
      return new ApplyContentChangesInfo( actionItemList,
                                   context.getString( R.string.toast_insert_list,
                                                      listname ),
                                   context.getString( R.string.toast_insert_list_ok,
                                                      listname ),
                                   context.getString( R.string.toast_insert_list_fail,
                                                      listname ) );
   }
   
   
   
   public final static ApplyContentChangesInfo deleteList( Context context,
                                                    RtmList list )
   {
      ContentProviderActionItemList actionItemList = new ContentProviderActionItemList();
      final String listName = list.getName();
      final String errorFeedbackMessage;
      
      if ( list.getLocked() == 0 )
      {
         boolean ok = true;
         
         final String listId = list.getId();
         final ModificationSet modifications = new ModificationSet();
         
         modifications.add( Modification.newNonPersistentModification( DbUtils.contentUriWithId( Lists.CONTENT_URI,
                                                                                                 listId ),
                                                                       Lists.LIST_DELETED,
                                                                       System.currentTimeMillis() ) );
         modifications.add( Modification.newListModified( listId ) );
         
         // Move all contained tasks of the deleted List to the Inbox, this only applies to non-smart lists.
         if ( list.getSmartFilter() == null )
         {
            final ArrayList< ContentProviderOperation > moveTasksToInboxOps = RtmTaskSeriesTable.moveTaskSeriesToInbox( context.getContentResolver(),
                                                                                                                               listId,
                                                                                                                               context.getString( R.string.app_list_name_inbox ) );
            ok = moveTasksToInboxOps != null;
            
            if ( ok && moveTasksToInboxOps.size() > 0 )
            {
               ok = actionItemList.addAll( ContentProviderAction.Type.UPDATE,
                                           moveTasksToInboxOps );
            }
         }
         
         ok = ok
            && actionItemList.add( ContentProviderAction.Type.DELETE,
                                   CreationsProviderPart.deleteCreation( DbUtils.contentUriWithId( Lists.CONTENT_URI,
                                                                                                   listId ) ) );
         
         // Add the modifications to the actionItemList
         // Remove the default list setting, if same list ID
         if ( ok )
         {
            actionItemList.add( 0, modifications );
            
            final String defaultListId = MolokoApp.getSettings( context )
                                                  .getDefaultListId();
            if ( defaultListId.equals( listId ) )
            {
               MolokoApp.getSettings( context )
                        .setDefaultListId( Settings.NO_DEFAULT_LIST_ID );
            }
         }
         else
         {
            actionItemList = null;
         }
         
         errorFeedbackMessage = context.getString( R.string.toast_delete_list_failed,
                                                   listName );
      }
      else
      {
         errorFeedbackMessage = context.getString( R.string.toast_delete_locked_list,
                                                   listName );
      }
      
      final ApplyContentChangesInfo applyChangesInfo = new ApplyContentChangesInfo( actionItemList,
                                                                      context.getString( R.string.toast_delete_list,
                                                                                         listName ),
                                                                      context.getString( R.string.toast_delete_list_ok,
                                                                                         listName ),
                                                                      errorFeedbackMessage );
      return applyChangesInfo;
   }
}
