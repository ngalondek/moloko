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

package dev.drsoran.moloko.app.content;

import dev.drsoran.moloko.util.Strings;


public final class ApplyChangesInfo
{
   public final static ApplyChangesInfo EMPTY = new ApplyChangesInfo( ContentProviderActionItemList.EMPTY,
                                                                      Strings.EMPTY_STRING,
                                                                      Strings.EMPTY_STRING,
                                                                      Strings.EMPTY_STRING );
   
   private final ContentProviderActionItemList actionItems;
   
   private final String progressMessage;
   
   private final String applySuccessMessage;
   
   private final String applyFailedMessage;
   
   private boolean failed;
   
   
   
   public ApplyChangesInfo( ContentProviderActionItemList actionItems,
      String progressMessage, String applySuccessMessage,
      String applyFailedMessage )
   {
      this.actionItems = actionItems;
      this.progressMessage = progressMessage;
      this.applySuccessMessage = applySuccessMessage;
      this.applyFailedMessage = applyFailedMessage;
   }
   
   
   
   public boolean isFailed()
   {
      return failed;
   }
   
   
   
   public String getProgressMessage()
   {
      return progressMessage;
   }
   
   
   
   public String getApplySuccessMessage()
   {
      return applySuccessMessage;
   }
   
   
   
   public String getApplyFailedMessage()
   {
      return applyFailedMessage;
   }
   
   
   
   public ContentProviderActionItemList getActionItems()
   {
      return actionItems;
   }
   
   
   
   public boolean hasChanges()
   {
      return actionItems != null && actionItems.size() > 0;
   }
   
   
   
   public static ApplyChangesInfo failed( String applyFailedMessage )
   {
      final ApplyChangesInfo applyChangesInfo = new ApplyChangesInfo( ContentProviderActionItemList.EMPTY,
                                                                      Strings.EMPTY_STRING,
                                                                      Strings.EMPTY_STRING,
                                                                      applyFailedMessage );
      applyChangesInfo.failed = true;
      
      return applyChangesInfo;
   }
}
