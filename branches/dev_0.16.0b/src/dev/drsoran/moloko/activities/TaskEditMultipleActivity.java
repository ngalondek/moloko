/*
 * Copyright (c) 2010 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.mdt.rtm.data.RtmAuth;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.TaskEditMultipleFragment;
import dev.drsoran.moloko.util.AccountUtils;


public class TaskEditMultipleActivity extends MolokoFragmentActivity
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + TaskEditMultipleActivity.class.getSimpleName();
   
   
   public static class Config extends TaskEditMultipleFragment.Config
   {
   }
   
   public final static int REQ_EDIT_TASKS = 0;
   
   public final static int RESULT_EDIT_TASKS_FAILED = 1 << 0;
   
   public final static int RESULT_EDIT_TASKS_OK = 1 << 8;
   
   public final static int RESULT_EDIT_TASKS_CHANGED = 1 << 9
      | RESULT_EDIT_TASKS_OK;
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.task_edit_multiple_activity );
      
      if ( savedInstanceState == null )
         createTaskEditMultipleFragment( getIntent().getExtras() );
   }
   


   private void createTaskEditMultipleFragment( Bundle fragmentConfig )
   {
      final Fragment fragment = TaskEditMultipleFragment.newInstance( fragmentConfig );
      final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      
      transaction.add( R.id.fragment_container, fragment, null );
      
      transaction.commit();
   }
   


   @Override
   protected void onReEvaluateRtmAccessLevel( RtmAuth.Perms currentAccessLevel )
   {
      super.onReEvaluateRtmAccessLevel( currentAccessLevel );
      
      // TODO: Show message
      if ( AccountUtils.isReadOnlyAccess( currentAccessLevel ) )
         finish();
   }
   


   @Override
   protected int[] getFragmentIds()
   {
      return new int[]
      { R.id.frag_task_edit_multiple };
   }
}
