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

import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.Modification;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.provider.Rtm.Notes;


public final class NoteEditUtils
{
   private final static String TAG = "Moloko."
      + NoteEditUtils.class.getSimpleName();
   
   

   private NoteEditUtils()
   {
      throw new AssertionError();
   }
   


   public final static boolean setNoteTitleAndText( Context context,
                                                    String noteId,
                                                    String title,
                                                    String text )
   {
      final ModificationSet modifications = new ModificationSet();
      
      modifications.add( Modification.newModification( Queries.contentUriWithId( Notes.CONTENT_URI,
                                                                                 noteId ),
                                                       Notes.NOTE_TITLE,
                                                       title ) );
      modifications.add( Modification.newModification( Queries.contentUriWithId( Notes.CONTENT_URI,
                                                                                 noteId ),
                                                       Notes.NOTE_TEXT,
                                                       text ) );
      modifications.add( Modification.newNoteModified( noteId ) );
      
      return reportStatus( context, applyModifications( context, modifications ) );
   }
   


   private final static boolean reportStatus( Context context, boolean ok )
   {
      Toast.makeText( context,
                      ok ? R.string.note_save_ok : R.string.note_save_error,
                      Toast.LENGTH_SHORT ).show();
      
      return ok;
   }
   


   private final static boolean applyModifications( Context context,
                                                    ModificationSet modifications )
   {
      boolean ok = true;
      
      if ( modifications.size() > 0 )
      {
         try
         {
            ok = new ApplyModificationsTask( context, R.string.dlg_save_note ).execute( modifications )
                                                                              .get();
         }
         catch ( InterruptedException e )
         {
            Log.e( TAG, "Applying note changes failed", e );
            ok = false;
         }
         catch ( ExecutionException e )
         {
            Log.e( TAG, "Applying note changes failed", e );
            ok = false;
         }
      }
      
      return ok;
   }
}
