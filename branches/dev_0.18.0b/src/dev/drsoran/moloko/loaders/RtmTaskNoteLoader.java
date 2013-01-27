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

package dev.drsoran.moloko.loaders;

import android.content.ContentProviderClient;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;

import com.mdt.rtm.data.RtmTaskNote;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.RtmNotesProviderPart;
import dev.drsoran.provider.Rtm.Notes;


public class RtmTaskNoteLoader extends AbstractLoader< RtmTaskNote >
{
   public final static int ID = R.id.loader_note;
   
   private final String noteId;
   
   
   
   public RtmTaskNoteLoader( Context context, String noteId )
   {
      super( context );
      this.noteId = noteId;
   }
   
   
   
   @Override
   protected RtmTaskNote queryResultInBackground( ContentProviderClient client )
   {
      RtmTaskNote note = null;
      
      if ( noteId != null )
         note = RtmNotesProviderPart.getNote( client, noteId );
      
      return note;
   }
   
   
   
   @Override
   protected Uri getContentUri()
   {
      return Notes.CONTENT_URI;
   }
   
   
   
   @Override
   protected void registerContentObserver( ContentObserver observer )
   {
      getContext().getContentResolver()
                  .registerContentObserver( getContentUri(), true, observer );
   }
   
   
   
   @Override
   protected void unregisterContentObserver( ContentObserver observer )
   {
      getContext().getContentResolver().unregisterContentObserver( observer );
   }
}
