/* 
 * Copyright (c) 2012 Ronny Röhricht
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.actionmodes;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.widget.ListAdapter;
import dev.drsoran.moloko.adapters.RtmSmartAddAdapter;
import dev.drsoran.moloko.grammar.RtmSmartAddTokenizer;
import dev.drsoran.moloko.grammar.RtmSmartAddTokenizer.Token;
import dev.drsoran.moloko.util.MolokoCalendar;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.moloko.util.parsing.RecurrenceParsing;
import dev.drsoran.moloko.util.parsing.RtmDateTimeParsing;
import dev.drsoran.moloko.util.parsing.RtmSmartFilterParsing;
import dev.drsoran.moloko.util.parsing.RtmSmartFilterToken;
import dev.drsoran.moloko.widgets.RtmSmartAddTextView;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.RtmSmartFilter;


class QuickAddTaskActionModeInputHandler
{
   private static final String TAG = "Moloko."
      + QuickAddTaskActionModeInputHandler.class.getSimpleName();
   
   private final Context context;
   
   private final RtmSmartAddTokenizer smartAddTokenizer = new RtmSmartAddTokenizer();
   
   private final RtmSmartAddTextView addTaskEdit;
   
   
   
   public QuickAddTaskActionModeInputHandler( Context context,
      RtmSmartAddTextView inputView )
   {
      this.context = context;
      this.addTaskEdit = inputView;
      
      addTaskEdit.setTokenizer( smartAddTokenizer );
      addTaskEdit.setThreshold( 1 );
      addTaskEdit.setAdapter( new RtmSmartAddAdapter( context ) );
      addTaskEdit.requestFocus();
   }
   
   
   
   public void preselect( RtmSmartFilter filter )
   {
      // Depending on the used filter, pre-select certain operators
      // Only do that if the edit field is empty cause this
      // instance is kept even after closing the quick add field.
      if ( filter != null && addTaskEdit.getText().length() == 0
         && preselectByFilter( filter ) > 0 )
      {
         addTaskEdit.setText( " " + addTaskEdit.getText() );
         Selection.setSelection( addTaskEdit.getText(), 0 );
      }
   }
   
   
   
   public void insertOperatorAtCursor( char operator )
   {
      final int pos = Selection.getSelectionStart( addTaskEdit.getText() );
      insertOperatorAtPosition( operator, pos );
   }
   
   
   
   public void clearEditText()
   {
      addTaskEdit.getEditableText().clear();
   }
   
   
   
   private final int preselectByFilter( RtmSmartFilter filter )
   {
      int numPreselected = 0;
      
      // Iterate over all tokens and suggest all non-null tokens
      for ( RtmSmartFilterToken rtmSmartFilterToken : RtmSmartFilterParsing.removeAmbiguousTokens( filter.getTokens() ) )
      {
         final Character operator = RtmSmartAddTokenizer.getOperatorFromRtmSmartFilterTokenType( rtmSmartFilterToken.operatorType );
         
         // Check if the RtmSmartFilterToken can be used as pre-selection
         if ( operator != null )
         {
            insertOperatorAndValue( operator.charValue(),
                                    rtmSmartFilterToken.value,
                                    -1 );
            ++numPreselected;
         }
      }
      
      return numPreselected;
   }
   
   
   
   private final Editable insertOperatorAtPosition( char operator, int pos )
   {
      final Editable text = addTaskEdit.getEditableText();
      
      if ( pos == -1 )
         pos = text.length();
      
      if ( pos > 0 && text.charAt( pos - 1 ) != ' ' )
         text.insert( pos++, " " );
      
      text.insert( pos, String.valueOf( operator ) );
      
      return text;
   }
   
   
   
   private final Editable insertOperatorAndValue( char operator,
                                                  String value,
                                                  int pos )
   {
      final Editable text = addTaskEdit.getEditableText();
      
      if ( pos == -1 )
         pos = text.length();
      
      if ( pos > 0 && text.charAt( pos - 1 ) != ' ' )
         text.insert( pos++, " " );
      
      text.insert( pos, value ).insert( pos, String.valueOf( operator ) );
      
      return text;
   }
   
   
   
   public final Bundle getNewTaskConfig()
   {
      final CharSequence input = UIUtils.getTrimmedSequence( addTaskEdit );
      
      Log.i( TAG, "Creating tokens for '" + input + "'" );
      
      final List< RtmSmartAddTokenizer.Token > tokens = new LinkedList< RtmSmartAddTokenizer.Token >();
      smartAddTokenizer.getTokens( input, tokens );
      
      Log.i( TAG, "Tokens: " + tokens );
      
      final Bundle config = new Bundle();
      
      if ( tokens.size() > 0 )
      {
         final ListAdapter adapter = addTaskEdit.getAdapter();
         if ( adapter instanceof RtmSmartAddAdapter )
         {
            final RtmSmartAddAdapter rtmSmartAddAdapter = (RtmSmartAddAdapter) adapter;
            Set< String > tags = null;
            
            for ( Token token : tokens )
            {
               // Check that the task name is not only a space character sequence
               if ( token.type == RtmSmartAddTokenizer.TASK_NAME_TYPE
                  && !TextUtils.isEmpty( token.text.replaceAll( " ", "" ) ) )
               {
                  config.putString( Tasks.TASKSERIES_NAME, token.text );
               }
               else
               {
                  // Check if the token value comes from a taken suggestion
                  final Object value = rtmSmartAddAdapter.getSuggestionValue( token.type,
                                                                              token.text );
                  
                  switch ( token.type )
                  {
                     case RtmSmartAddTokenizer.DUE_DATE_TYPE:
                        if ( value != null )
                        {
                           config.putLong( Tasks.DUE_DATE, (Long) value );
                           config.putBoolean( Tasks.HAS_DUE_TIME, Boolean.FALSE );
                        }
                        else
                        {
                           final MolokoCalendar cal = RtmDateTimeParsing.parseDateTimeSpec( token.text );
                           if ( cal != null )
                           {
                              config.putLong( Tasks.DUE_DATE,
                                              cal.getTimeInMillis() );
                              config.putBoolean( Tasks.HAS_DUE_TIME,
                                                 cal.hasTime() );
                           }
                        }
                        break;
                     
                     case RtmSmartAddTokenizer.PRIORITY_TYPE:
                        config.putString( Tasks.PRIORITY, token.text );
                        break;
                     
                     case RtmSmartAddTokenizer.LIST_TAGS_TYPE:
                        boolean isTag = true;
                        
                        if ( value != null )
                        {
                           @SuppressWarnings( "unchecked" )
                           final Pair< String, Boolean > list_or_tag = (Pair< String, Boolean >) value;
                           if ( list_or_tag.second )
                           {
                              config.putString( Tasks.LIST_ID,
                                                list_or_tag.first );
                              isTag = false;
                           }
                        }
                        
                        if ( isTag )
                        {
                           if ( tags == null )
                              tags = new TreeSet< String >();
                           tags.add( token.text );
                        }
                        break;
                     
                     case RtmSmartAddTokenizer.LOCATION_TYPE:
                        if ( value != null )
                           config.putString( Tasks.LOCATION_ID, (String) value );
                        else
                           config.putString( Tasks.LOCATION_NAME, token.text );
                        break;
                     
                     case RtmSmartAddTokenizer.REPEAT_TYPE:
                        final Pair< String, Boolean > recurr;
                        
                        if ( value != null )
                        {
                           @SuppressWarnings( "unchecked" )
                           final Pair< String, Boolean > tempValue = (Pair< String, Boolean >) value;
                           recurr = tempValue;
                        }
                        else
                           recurr = RecurrenceParsing.parseRecurrence( token.text );
                        
                        if ( recurr != null )
                        {
                           config.putString( Tasks.RECURRENCE, recurr.first );
                           config.putBoolean( Tasks.RECURRENCE_EVERY,
                                              recurr.second );
                        }
                        
                        break;
                     
                     case RtmSmartAddTokenizer.ESTIMATE_TYPE:
                        if ( value != null )
                        {
                           config.putString( Tasks.ESTIMATE, token.text );
                           config.putLong( Tasks.ESTIMATE_MILLIS, (Long) value );
                        }
                        else
                        {
                           final long estimated = RtmDateTimeParsing.parseEstimated( token.text );
                           if ( estimated != -1 )
                           {
                              config.putString( Tasks.ESTIMATE,
                                                MolokoDateUtils.formatEstimated( context,
                                                                                 estimated ) );
                              config.putLong( Tasks.ESTIMATE_MILLIS,
                                              Long.valueOf( estimated ) );
                           }
                        }
                        break;
                     
                     default :
                        break;
                  }
               }
            }
            
            if ( tags != null && tags.size() > 0 )
            {
               config.putString( Tasks.TAGS,
                                 TextUtils.join( Tasks.TAGS_SEPARATOR, tags ) );
            }
         }
      }
      
      return config;
   }
}
