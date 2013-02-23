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

package dev.drsoran.moloko.app.account;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import dev.drsoran.moloko.ui.fragments.MolokoFragment;


abstract class AuthFragment extends MolokoFragment
{
   private Button continueButton;
   
   private Button cancelButton;
   
   
   
   @Override
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      super.onViewCreated( view, savedInstanceState );
      
      continueButton = (Button) view.findViewById( android.R.id.button1 );
      if ( continueButton != null )
      {
         continueButton.setOnClickListener( new OnClickListener()
         {
            @Override
            public void onClick( View v )
            {
               onContinueButtonClicked();
            }
         } );
      }
      
      cancelButton = (Button) view.findViewById( android.R.id.button2 );
      if ( cancelButton != null )
      {
         cancelButton.setOnClickListener( new OnClickListener()
         {
            @Override
            public void onClick( View v )
            {
               onCancelButtonClicked();
            }
         } );
      }
   }
   
   
   
   public void onContinueButtonClicked()
   {
   }
   
   
   
   public void onCancelButtonClicked()
   {
   }
   
   
   
   public Object onRetainNonConfigurationInstance()
   {
      return null;
   }
}
