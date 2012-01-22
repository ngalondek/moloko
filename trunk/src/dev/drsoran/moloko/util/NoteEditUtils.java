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

import android.support.v4.app.FragmentActivity;
import android.util.Pair;

import com.mdt.rtm.data.RtmTaskNote;

import dev.drsoran.moloko.ApplyChangesInfo;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.ContentProviderAction;
import dev.drsoran.moloko.content.ContentProviderActionItemList;
import dev.drsoran.moloko.content.CreationsProviderPart;
import dev.drsoran.moloko.content.Modification;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.content.RtmNotesProviderPart;
import dev.drsoran.provider.Rtm.Notes;


public final class NoteEditUtils
{
   private NoteEditUtils()
   {
      throw new AssertionError();
   }
   
   
   
   public final static Pair< ContentProviderActionItemList, ApplyChangesInfo > setNoteTitleAndText( FragmentActivity activity,
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
      
      return Pair.create( modifications.toContentProviderActionItemList(),
                          new ApplyChangesInfo( activity.getString( R.string.toast_save_note ),
                                                activity.getString( R.string.toast_save_note_ok ),
                                                activity.getString( R.string.toast_save_note_failed ) ) );
   }
   
   
   
   public final static Pair< ContentProviderActionItemList, ApplyChangesInfo > insertNote( FragmentActivity activity,
                                                                                           RtmTaskNote note )
   {
      ContentProviderActionItemList actionItemList = new ContentProviderActionItemList();
      
      boolean ok = actionItemList.add( ContentProviderAction.Type.INSERT,
                                       RtmNotesProviderPart.insertLocalCreatedNote( note ) );
      ok = ok
         && actionItemList.add( ContentProviderAction.Type.INSERT,
                                CreationsProviderPart.newCreation( Queries.contentUriWithId( Notes.CONTENT_URI,
                                                                                             note.getId() ),
                                                                   note.getCreatedDate()
                                                                       .getTime() ) );
      
      if ( !ok )
         actionItemList = null;
      
      return Pair.create( actionItemList,
                          new ApplyChangesInfo( activity.getString( R.string.toast_insert_note ),
                                                activity.getString( R.string.toast_insert_note_ok ),
                                                activity.getString( R.string.toast_insert_note_fail ) ) );
   }
   
   
   
   public final static Pair< ContentProviderActionItemList, ApplyChangesInfo > deleteNote( FragmentActivity activity,
                                                                                           String noteId )
   {
      boolean ok = true;
      ContentProviderActionItemList actionItemList = new ContentProviderActionItemList();
      
      final ModificationSet modifications = new ModificationSet();
      
      modifications.add( Modification.newNonPersistentModification( Queries.contentUriWithId( Notes.CONTENT_URI,
                                                                                              noteId ),
                                                                    Notes.NOTE_DELETED,
                                                                    System.currentTimeMillis() ) );
      modifications.add( Modification.newNoteModified( noteId ) );
      
      ok = actionItemList.add( ContentProviderAction.Type.DELETE,
                               CreationsProviderPart.deleteCreation( Queries.contentUriWithId( Notes.CONTENT_URI,
                                                                                               noteId ) ) );
      actionItemList.add( 0, modifications );
      
      if ( !ok )
         actionItemList = null;
      
      return Pair.create( actionItemList,
                          new ApplyChangesInfo( activity.getString( R.string.toast_delete_note ),
                                                activity.getString( R.string.toast_delete_note_ok ),
                                                activity.getString( R.string.toast_delete_note_failed ) ) );
   }
}
