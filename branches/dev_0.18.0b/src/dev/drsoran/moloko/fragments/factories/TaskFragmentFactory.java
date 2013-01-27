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

package dev.drsoran.moloko.fragments.factories;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import dev.drsoran.moloko.fragments.TaskAddFragment;
import dev.drsoran.moloko.fragments.TaskEditFragment;
import dev.drsoran.moloko.fragments.TaskFragment;


public final class TaskFragmentFactory extends AbstractIntentFragmentFactory
{
   private final static List< Class< ? extends Fragment > > FRAGMENT_CLASSES = new ArrayList< Class< ? extends Fragment > >();
   
   static
   {
      FRAGMENT_CLASSES.add( TaskFragment.class );
      FRAGMENT_CLASSES.add( TaskEditFragment.class );
      FRAGMENT_CLASSES.add( TaskAddFragment.class );
   }
   
   
   
   public final static Fragment newFragment( Context context,
                                             Intent intent,
                                             Bundle config )
   {
      if ( config != null )
      {
         intent.putExtras( config );
      }
      
      return resolveIntentToFragment( context, intent, FRAGMENT_CLASSES );
   }
}
