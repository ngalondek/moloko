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

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.Context;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.CreationsProviderPart;
import dev.drsoran.moloko.content.Modification;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.provider.Rtm.Notes;


public final class NoteEditUtils
{
   @SuppressWarnings( "unused" )
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
      
      return UIUtils.reportStatus( context,
                                   R.string.toast_save_note_ok,
                                   R.string.toast_save_note_failed,
                                   Queries.applyModifications( context,
                                                               modifications,
                                                               R.string.toast_save_note ) );
   }
   


   public final static boolean deleteNote( Context context, String noteId )
   {
      final ModificationSet modifications = new ModificationSet();
      
      modifications.add( Modification.newNonPersistentModification( Queries.contentUriWithId( Notes.CONTENT_URI,
                                                                                              noteId ),
                                                                    Notes.NOTE_DELETED,
                                                                    System.currentTimeMillis() ) );
      modifications.add( Modification.newNoteModified( noteId ) );
      
      final ArrayList< ContentProviderOperation > removeCreatedOperations = new ArrayList< ContentProviderOperation >( 1 );
      removeCreatedOperations.add( CreationsProviderPart.deleteCreation( Queries.contentUriWithId( Notes.CONTENT_URI,
                                                                                                   noteId ) ) );
      
      return UIUtils.reportStatus( context,
                                   R.string.toast_delete_note_ok,
                                   R.string.toast_delete_note_failed,
                                   Queries.applyModifications( context,
                                                               modifications,
                                                               R.string.toast_delete_note )
                                      && Queries.transactionalApplyOperations( context,
                                                                               removeCreatedOperations,
                                                                               R.string.toast_delete_note ) );
   }
}
