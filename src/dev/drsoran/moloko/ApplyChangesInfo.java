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

package dev.drsoran.moloko;

import dev.drsoran.moloko.content.ContentProviderActionItemList;
import dev.drsoran.moloko.util.Strings;


public final class ApplyChangesInfo
{
   private final ContentProviderActionItemList actionItems;
   
   private final String progressMessage;
   
   private final String applySuccessMessage;
   
   private final String applyFailedMessage;
   
   public final static ApplyChangesInfo EMPTY = new ApplyChangesInfo( new ContentProviderActionItemList( 0 ),
                                                                      Strings.EMPTY_STRING,
                                                                      Strings.EMPTY_STRING,
                                                                      Strings.EMPTY_STRING );
   
   
   
   public ApplyChangesInfo( ContentProviderActionItemList actionItems,
      String progressMessage, String applySuccessMessage,
      String applyFailedMessage )
   {
      this.actionItems = actionItems;
      this.progressMessage = progressMessage;
      this.applySuccessMessage = applySuccessMessage;
      this.applyFailedMessage = applyFailedMessage;
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
   
   
   
   public boolean hasDatabaseError()
   {
      return actionItems == null;
   }
   
   
   
   public boolean hasChanges()
   {
      return actionItems != null && actionItems.size() > 0;
   }
   
   
   
   public static ApplyChangesInfo failed( String applyFailedMessage )
   {
      return new ApplyChangesInfo( null,
                                   Strings.EMPTY_STRING,
                                   Strings.EMPTY_STRING,
                                   applyFailedMessage );
   }
}
