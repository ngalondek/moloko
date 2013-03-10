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

package dev.drsoran.moloko.app.content;

import java.util.Collection;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.app.content.ContentProviderAction.Type;
import dev.drsoran.moloko.content.ContentException;
import dev.drsoran.moloko.content.RtmProvider;
import dev.drsoran.moloko.util.LogUtils;


public class LoggingContentProviderAction implements IContentProviderActionItem
{
   private final ILog log;
   
   private final IContentProviderActionItem itemToLog;
   
   
   
   public LoggingContentProviderAction( ILog log,
      IContentProviderActionItem itemToLog )
   {
      this.log = log;
      this.itemToLog = itemToLog;
   }
   
   
   
   @Override
   public Iterable< ContentProviderOperation > getOperations()
   {
      return itemToLog.getOperations();
   }
   
   
   
   @Override
   public int getOperationsCount()
   {
      return itemToLog.getOperationsCount();
   }
   
   
   
   @Override
   public int addOperationsToBatch( Collection< ? super ContentProviderOperation > batch )
   {
      return itemToLog.addOperationsToBatch( batch );
   }
   
   
   
   @Override
   public Type getActionType()
   {
      return itemToLog.getActionType();
   }
   
   
   
   @Override
   public void applyTransactional( RtmProvider rtmProvider ) throws ContentException
   {
      LogOperations();
      
      try
      {
         itemToLog.applyTransactional( rtmProvider );
      }
      catch ( ContentException e )
      {
         log.e( IContentProviderActionItem.class, LogUtils.GENERIC_DB_ERROR, e );
         throw e;
      }
   }
   
   
   
   @Override
   public void apply( ContentResolver contentResolver ) throws ContentException
   {
      LogOperations();
      
      try
      {
         itemToLog.apply( contentResolver );
      }
      catch ( ContentException e )
      {
         log.e( IContentProviderActionItem.class, LogUtils.GENERIC_DB_ERROR, e );
         throw e;
      }
   }
   
   
   
   private void LogOperations()
   {
      ContentProviderAction.Type actionType = itemToLog.getActionType();
      for ( ContentProviderOperation op : itemToLog.getOperations() )
      {
         log.d( getClass(), String.format( "%s %s", actionType, op.toString() ) );
      }
   }
}
