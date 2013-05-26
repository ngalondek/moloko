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

package dev.drsoran.moloko.app.taskslist.common;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.util.Pair;
import android.widget.ListAdapter;
import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.content.db.TableColumns.Tasks;
import dev.drsoran.moloko.domain.services.IParsingService;
import dev.drsoran.moloko.grammar.rtmsmart.RtmSmartFilterToken;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.widgets.RtmSmartAddTextView;
import dev.drsoran.moloko.ui.widgets.RtmSmartAddTokenizer;
import dev.drsoran.moloko.ui.widgets.Token;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.rtm.RtmSmartFilter;


class QuickAddTaskActionModeInputHandler
{
   private final AppContext context;
   
   private final IParsingService parsingService;
   
   private final RtmSmartAddTokenizer smartAddTokenizer;
   
   private final RtmSmartAddTextView addTaskEdit;
   
   
   
   public QuickAddTaskActionModeInputHandler( AppContext context,
      RtmSmartAddTextView inputView )
   {
      this.context = context;
      this.parsingService = context.getParsingService();
      
      smartAddTokenizer = new RtmSmartAddTokenizer( parsingService.getDateTimeParsing() );
      
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
      
      addTaskEdit.requestFocus();
   }
   
   
   
   public void clearEditText()
   {
      addTaskEdit.getEditableText().clear();
   }
   
   
   
   private final int preselectByFilter( RtmSmartFilter filter )
   {
      int numPreselected = 0;
      
      // Iterate over all tokens and suggest all non-null tokens
      for ( RtmSmartFilterToken rtmSmartFilterToken : parsingService.getRtmSmartFilterParsing()
                                                                    .removeAmbiguousTokens( filter.getTokens() ) )
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
      final CharSequence input = UiUtils.getTrimmedSequence( addTaskEdit );
      
      context.Log().d( getClass(), "Creating tokens for '" + input + "'" );
      
      final List< Token > tokens = new LinkedList< Token >();
      smartAddTokenizer.getTokens( input, tokens );
      
      context.Log().d( getClass(), "Tokens: " + tokens );
      
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
                  && !TextUtils.isEmpty( token.text.replaceAll( " ",
                                                                Strings.EMPTY_STRING ) ) )
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
                           final MolokoCalendar cal = context.getParsingService()
                                                             .getDateTimeParsing()
                                                             .parseDateTimeSpec( token.text );
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
                           recurr = context.getParsingService()
                                           .getRecurrenceParsing()
                                           .parseRecurrence( token.text );
                        
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
                           final long estimated = context.getParsingService()
                                                         .getDateTimeParsing()
                                                         .getTimeStruct( token.text );
                           if ( estimated != -1 )
                           {
                              config.putString( Tasks.ESTIMATE,
                                                context.getDateFormatter()
                                                       .formatEstimated( estimated ) );
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
