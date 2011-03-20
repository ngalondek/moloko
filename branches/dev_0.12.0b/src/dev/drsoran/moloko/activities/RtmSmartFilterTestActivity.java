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

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.parsing.RtmSmartFilterParsing;


public class RtmSmartFilterTestActivity extends Activity
{
   private static final String TAG = "Moloko."
      + RtmSmartFilterTestActivity.class.getSimpleName();
   
   private EditText filterInput;
   
   private MultiAutoCompleteTextView recurrenceInput;
   
   

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.rtmsmartfiltertest_activity );
      
      filterInput = (EditText) findViewById( R.id.rtmsmartfilter_edit );
      
      recurrenceInput = (MultiAutoCompleteTextView) findViewById( R.id.rtmsmartfilter_recurrence_edit );
      recurrenceInput.setTokenizer( new MultiAutoCompleteTextView.CommaTokenizer() );
   }
   


   public void onFilter( View view )
   {
      final RtmSmartFilterParsing.RtmSmartFilterReturn ret = RtmSmartFilterParsing.evaluateRtmSmartFilter( filterInput.getText()
                                                                                                                      .toString(),
                                                                                                           null );
      
      if ( ret != null )
         Log.d( TAG, "SQL: " + ret.result );
      else
         Log.e( TAG, "Parsing failed." );
   }
   


   public void onRecurrence( View view )
   {
      
   }
}
