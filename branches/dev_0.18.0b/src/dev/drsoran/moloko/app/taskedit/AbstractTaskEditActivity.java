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

package dev.drsoran.moloko.app.taskedit;

import android.app.Dialog;
import android.view.Menu;
import android.view.MenuItem;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.baseactivities.MolokoEditActivity;
import dev.drsoran.moloko.ui.fragments.IEditFragment;


abstract class AbstractTaskEditActivity extends MolokoEditActivity
{
   @Override
   public boolean onActivityCreateOptionsMenu( Menu menu )
   {
      getMenuInflater().inflate( R.menu.edit_activity, menu );
      super.onActivityCreateOptionsMenu( menu );
      
      return true;
   }
   
   
   
   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case R.id.menu_save:
            saveChangesAndFinish();
            return true;
            
         case R.id.menu_abort_edit:
            cancelEditingAndFinish();
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   
   
   
   @Override
   protected boolean onFinishActivityByHome()
   {
      return cancelFragmentEditing( getTaskEditFragment() );
   }
   
   
   
   @Override
   public void onBackPressed()
   {
      if ( cancelFragmentEditing( getTaskEditFragment() ) )
      {
         super.onBackPressed();
      }
   }
   
   
   
   public IEditFragment getTaskEditFragment()
   {
      final IEditFragment taskEditFragment = (IEditFragment) findAddedFragmentById( R.id.frag_task_edit );
      return taskEditFragment;
   }
   
   
   
   @Override
   protected void handleCancelWithChangesDialogClick( String tag, int which )
   {
      if ( which == Dialog.BUTTON_POSITIVE )
      {
         setResult( RESULT_CANCELED );
         finish();
      }
   }
   
   
   
   @Override
   protected int[] getFragmentIds()
   {
      return new int[]
      { R.id.frag_task_edit };
   }
   
   
   
   private void saveChangesAndFinish()
   {
      if ( finishFragmentEditing( getTaskEditFragment() ) )
      {
         setResult( RESULT_OK );
         finish();
      }
   }
   
   
   
   private void cancelEditingAndFinish()
   {
      if ( cancelFragmentEditing( getTaskEditFragment() ) )
      {
         setResult( RESULT_CANCELED );
         finish();
      }
   }
}
