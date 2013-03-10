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

package dev.drsoran.moloko.ui.widgets;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import dev.drsoran.moloko.IHandlerToken;
import dev.drsoran.moloko.ui.UiUtils;


final class EditTextFocusHandler implements OnFocusChangeListener
{
   private final IHandlerToken handlerToken;
   
   private final EditText client;
   
   private Runnable showSoftInputRunnable;
   
   
   
   public EditTextFocusHandler( EditText client, IHandlerToken handlerToken )
   {
      this.handlerToken = handlerToken;
      this.client = client;
      this.client.setOnFocusChangeListener( this );
   }
   
   
   
   @Override
   public void onFocusChange( View v, boolean hasFocus )
   {
      if ( !handlerToken.isReleased() && hasFocus && v == client )
      {
         handlerToken.post( getShowSoftInputRunnable() );
      }
   }
   
   
   
   public void shutdown()
   {
      handlerToken.release();
   }
   
   
   
   private Runnable getShowSoftInputRunnable()
   {
      if ( showSoftInputRunnable == null )
      {
         showSoftInputRunnable = new Runnable()
         {
            @Override
            public void run()
            {
               UiUtils.showSoftInput( client );
            }
         };
      }
      
      return showSoftInputRunnable;
   }
}
