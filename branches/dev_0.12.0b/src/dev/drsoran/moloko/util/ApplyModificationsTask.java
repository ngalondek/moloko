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

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.content.ModificationsProviderPart;


public class ApplyModificationsTask extends
         AsyncTask< ModificationSet, Void, Boolean >
{
   private final Context context;
   
   private ProgressDialog dialog;
   
   

   public ApplyModificationsTask( Context context )
   {
      if ( context == null )
         throw new NullPointerException( "context is null" );
      
      this.context = context;
   }
   


   @Override
   protected void onPreExecute()
   {
      dialog = new ProgressDialog( context );
      dialog.setMessage( context.getString( R.string.dlg_save_task ) );
      dialog.setCancelable( false );
      dialog.show();
   }
   


   @Override
   protected Boolean doInBackground( ModificationSet... params )
   {
      if ( params == null )
         return Boolean.FALSE;
      
      if ( params.length == 0 || params[ 0 ].size() == 0 )
         return Boolean.TRUE;
      
      return Boolean.valueOf( ModificationsProviderPart.applyModifications( context.getContentResolver(),
                                                                            params[ 0 ] ) );
   }
   


   @Override
   protected void onPostExecute( Boolean result )
   {
      if ( dialog != null )
         dialog.dismiss();
   }
}
