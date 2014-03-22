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

package dev.drsoran.moloko.app.services;

import dev.drsoran.rtm.Strings;


public final class AppContentEditInfo
{
   public final static AppContentEditInfo EMPTY = new AppContentEditInfo( Strings.EMPTY_STRING,
                                                                          Strings.EMPTY_STRING,
                                                                          Strings.EMPTY_STRING );
   
   private final String progressMessage;
   
   private final String successMessage;
   
   private final String failedMessage;
   
   
   
   public AppContentEditInfo( String progressMessage,
      String applySuccessMessage, String applyFailedMessage )
   {
      this.progressMessage = progressMessage;
      this.successMessage = applySuccessMessage;
      this.failedMessage = applyFailedMessage;
   }
   
   
   
   public String getProgressMessage()
   {
      return progressMessage;
   }
   
   
   
   public boolean showProgress()
   {
      return !Strings.isNullOrEmpty( progressMessage );
   }
   
   
   
   public String getSuccessMessage()
   {
      return successMessage;
   }
   
   
   
   public boolean showSuccessMessage()
   {
      return !Strings.isNullOrEmpty( successMessage );
   }
   
   
   
   public String getFailedMessage()
   {
      return failedMessage;
   }
   
   
   
   public boolean showFailedMessage()
   {
      return !Strings.isNullOrEmpty( failedMessage );
   }
}
