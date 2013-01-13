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

package dev.drsoran.moloko;

import java.util.regex.Pattern;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.text.util.Linkify.MatchFilter;
import android.widget.TextView;


public class MolokoLinkifier
{
   private final static String EVERNOTE_NOTE_GUID_PATTERN = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";
   
   private final static Pattern EVERNOTE_PATTERN = Pattern.compile( "evernote:///view/\\d+/s\\d+"
      + "/(" + EVERNOTE_NOTE_GUID_PATTERN + "/){2,3}" );
   
   
   
   public static void linkify( TextView textView )
   {
      if ( Linkify.addLinks( SpannableString.valueOf( textView.getText() ),
                             EVERNOTE_PATTERN,
                             "evernote:///",
                             new EvernoteMatchFilter( textView.getContext() ),
                             null ) )
      {
         textView.setMovementMethod( LinkMovementMethod.getInstance() );
      }
   }
   
   
   private final static class EvernoteMatchFilter implements MatchFilter
   {
      private final Context context;
      
      
      
      public EvernoteMatchFilter( Context context )
      {
         this.context = context;
      }
      
      
      
      @Override
      public boolean acceptMatch( CharSequence s, int start, int end )
      {
         return isEvernoteInstalled();
      }
      
      
      
      private boolean isEvernoteInstalled()
      {
         return context.getPackageManager()
                       .resolveActivity( new Intent( "com.evernote.action.VIEW_NOTE" ),
                                         PackageManager.MATCH_DEFAULT_ONLY ) != null;
      }
   };
}
