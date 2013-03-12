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

package dev.drsoran.moloko.util;

import java.util.Collection;
import java.util.Iterator;

import android.content.Context;

import com.mdt.rtm.data.RtmTaskNote;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.content.ApplyChangesInfo;
import dev.drsoran.moloko.app.content.ContentProviderAction;
import dev.drsoran.moloko.app.content.ContentProviderActionItemList;
import dev.drsoran.moloko.content.Modification;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.content.db.CreationsProviderPart;
import dev.drsoran.moloko.content.db.DbHelper;
import dev.drsoran.moloko.content.db.RtmNotesTable;
import dev.drsoran.provider.Rtm.Notes;


public final class NoteEditUtils
{
   private NoteEditUtils()
   {
      throw new AssertionError();
   }
   
   
   
   public final static ApplyChangesInfo setNoteTitleAndText( Context context,
                                                             String noteId,
                                                             String title,
                                                             String text )
   {
      final ModificationSet modifications = new ModificationSet();
      
      modifications.add( Modification.newModification( DbHelper.contentUriWithId( Notes.CONTENT_URI,
                                                                                 noteId ),
                                                       Notes.NOTE_TITLE,
                                                       title ) );
      modifications.add( Modification.newModification( DbHelper.contentUriWithId( Notes.CONTENT_URI,
                                                                                 noteId ),
                                                       Notes.NOTE_TEXT,
                                                       text ) );
      modifications.add( Modification.newNoteModified( noteId ) );
      
      return new ApplyChangesInfo( modifications.toContentProviderActionItemList(),
                                   context.getString( R.string.toast_save_note ),
                                   context.getString( R.string.toast_save_note_ok ),
                                   context.getString( R.string.toast_save_note_failed ) );
   }
   
   
   
   public final static ApplyChangesInfo insertNote( Context context,
                                                    RtmTaskNote note )
   {
      ContentProviderActionItemList actionItemList = new ContentProviderActionItemList();
      
      boolean ok = actionItemList.add( ContentProviderAction.Type.INSERT,
                                       RtmNotesTable.insertLocalCreatedNote( note ) );
      ok = ok
         && actionItemList.add( ContentProviderAction.Type.INSERT,
                                CreationsProviderPart.newCreation( DbHelper.contentUriWithId( Notes.CONTENT_URI,
                                                                                             note.getId() ),
                                                                   note.getCreatedDate()
                                                                       .getTime() ) );
      
      if ( !ok )
         actionItemList = null;
      
      return new ApplyChangesInfo( actionItemList,
                                   context.getString( R.string.toast_insert_note ),
                                   context.getString( R.string.toast_insert_note_ok ),
                                   context.getString( R.string.toast_insert_note_fail ) );
   }
   
   
   
   public final static ApplyChangesInfo deleteNotes( Context context,
                                                     Collection< RtmTaskNote > notes )
   {
      boolean ok = true;
      ContentProviderActionItemList actionItemList = new ContentProviderActionItemList();
      
      for ( Iterator< RtmTaskNote > i = notes.iterator(); ok && i.hasNext(); )
      {
         final String noteId = i.next().getId();
         final ModificationSet modifications = new ModificationSet();
         
         modifications.add( Modification.newNonPersistentModification( DbHelper.contentUriWithId( Notes.CONTENT_URI,
                                                                                                 noteId ),
                                                                       Notes.NOTE_DELETED,
                                                                       System.currentTimeMillis() ) );
         modifications.add( Modification.newNoteModified( noteId ) );
         
         ok = actionItemList.add( ContentProviderAction.Type.DELETE,
                                  CreationsProviderPart.deleteCreation( DbHelper.contentUriWithId( Notes.CONTENT_URI,
                                                                                                  noteId ) ) );
         actionItemList.add( modifications );
      }
      
      if ( !ok )
         actionItemList = null;
      
      return new ApplyChangesInfo( actionItemList,
                                   context.getString( R.string.toast_delete_note ),
                                   context.getString( R.string.toast_delete_note_ok ),
                                   context.getString( R.string.toast_delete_note_failed ) );
   }
}
