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

package dev.drsoran.moloko.prefs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;

import com.xtralogic.android.logcollector.SendLogActivity;

import dev.drsoran.moloko.R;


class SendLogPreference extends InfoTextPreference
{
   private final static String LOG_PATTERN = "E/AndroidRuntime|[VDIWEFS]/Moloko";
   
   
   
   public SendLogPreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );
   }
   
   
   
   @Override
   protected void onClick()
   {
      final Context context = getContext();
      
      final Intent intent = new Intent( SendLogActivity.ACTION_SEND_LOG );
      intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
      
      intent.putExtra( SendLogActivity.EXTRA_SEND_INTENT_ACTION,
                       Intent.ACTION_SENDTO );
      
      intent.putExtra( SendLogActivity.EXTRA_DATA,
                       Uri.parse( "mailto:"
                          + context.getString( R.string.send_log_address ) ) );
      
      intent.putExtra( Intent.EXTRA_SUBJECT,
                       context.getString( R.string.send_log_subject ) );
      
      intent.putExtra( SendLogActivity.EXTRA_FORMAT,
                       context.getString( R.string.send_log_format ) );
      
      intent.putExtra( SendLogActivity.EXTRA_SHOW_UI, true );
      
      intent.putExtra( SendLogActivity.EXTRA_FILTER_SPECS, new String[]
      { "*:V" } );
      
      intent.putExtra( SendLogActivity.EXTRA_REGEX, LOG_PATTERN );
      
      intent.putExtra( SendLogActivity.EXTRA_DEFAULT_ADDITIONAL_INFO, true );
      
      context.startActivity( intent );
   }
}
