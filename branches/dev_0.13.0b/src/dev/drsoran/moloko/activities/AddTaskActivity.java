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

package dev.drsoran.moloko.activities;

import android.content.Intent;
import android.widget.TextView;
import dev.drsoran.moloko.content.ModificationSet;


public class AddTaskActivity extends AbstractTaskEditActivity
{
   @Override
   protected InitialValues onCreateImpl( Intent intent )
   {
      // TODO Auto-generated method stub
      return null;
   }
   


   @Override
   protected boolean shouldHandleIntentAction( String action )
   {
      return action.equals( Intent.ACTION_INSERT );
   }
   


   @Override
   protected void refreshHeadSection( TextView addedDate,
                                      TextView completedDate,
                                      TextView postponed,
                                      TextView source )
   {
      // TODO Auto-generated method stub
      
   }
   


   @Override
   protected ModificationSet getModifications()
   {
      // TODO Auto-generated method stub
      return null;
   }
}
