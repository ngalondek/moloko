/* 
 *	Copyright (c) 2011 Ronny Röhricht
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

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.content.ModificationsProviderPart;


public class ApplyModificationsTask extends
         AsyncTask< ModificationSet, Void, Boolean >
{
   private final FragmentActivity activity;
   
   @SuppressWarnings( "unused" )
   private final int progressMsgId;
   
   

   public ApplyModificationsTask( FragmentActivity activity, int progressMsgId )
   {
      if ( activity == null )
         throw new NullPointerException( "activity is null" );
      
      this.activity = activity;
      this.progressMsgId = progressMsgId;
   }
   


   @Override
   protected Boolean doInBackground( ModificationSet... params )
   {
      if ( params == null )
         return Boolean.FALSE;
      
      if ( params.length == 0 || params[ 0 ].size() == 0 )
         return Boolean.TRUE;
      
      if ( AccountUtils.isReadOnlyAccess( activity ) )
         throw new SecurityException( LogUtils.DB_READ_ONLY_ERROR );
      
      return Boolean.valueOf( ModificationsProviderPart.applyModifications( activity.getContentResolver(),
                                                                            params[ 0 ] ) );
   }
}
