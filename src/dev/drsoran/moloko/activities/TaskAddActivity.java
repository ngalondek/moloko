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

package dev.drsoran.moloko.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.TaskAddFragment;


public class TaskAddActivity extends AbstractTaskEditActivity
{
   @Override
   protected int getContentViewResourceId()
   {
      return R.layout.task_edit_activity;
   }
   
   
   
   @Override
   protected Fragment createTaskEditFragment()
   {
      final Fragment fragment = TaskAddFragment.newInstance( createTaskAddFragmentConfig() );
      return fragment;
   }
   
   
   
   private Bundle createTaskAddFragmentConfig()
   {
      final Bundle config = new Bundle();
      
      config.putAll( getIntent().getExtras() );
      
      return config;
   }
}
