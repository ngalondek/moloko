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

package dev.drsoran.moloko.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;
import dev.drsoran.moloko.R;


public class ChangeTagsActivity extends ListActivity
{
   public final static int REQ_CHANGE_TAGS = 0;
   
   public final static String INTENT_EXTRA_TAGS = "tags";
   
   private MultiAutoCompleteTextView editView;
   
   

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.change_tags_activity );
      
      editView = (MultiAutoCompleteTextView) findViewById( R.id.change_tags_activity_edit );
      editView.setTokenizer( new MultiAutoCompleteTextView.CommaTokenizer() );
      
      final Intent intent = getIntent();
      
      if ( intent.hasExtra( INTENT_EXTRA_TAGS ) )
      {
         getListView().setAdapter( new ArrayAdapter< String >( this,
                                                               R.layout.change_tags_activity_listitem,
                                                               R.id.change_tags_activity_listitem_tag,
                                                               intent.getStringArrayExtra( INTENT_EXTRA_TAGS ) ) );
      }
   }
}
