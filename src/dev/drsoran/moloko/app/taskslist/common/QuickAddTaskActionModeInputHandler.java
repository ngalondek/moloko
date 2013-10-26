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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.util.Pair;
import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.domain.parsing.rtmsmart.RtmSmartFilterToken;
import dev.drsoran.moloko.domain.services.IParsingService;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.widgets.RtmSmartAddTextView;
import dev.drsoran.moloko.ui.widgets.RtmSmartAddTokenizer;
import dev.drsoran.moloko.ui.widgets.RtmSmartAddTokenizer.Token;


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
      
      pos = ensureSpace( pos, text );
      
      text.insert( pos, String.valueOf( operator ) );
      
      return text;
   }
   
   
   
   private final Editable insertOperatorAndValue( char operator,
                                                  String value,
                                                  int pos )
   {
      final Editable text = addTaskEdit.getEditableText();
      
      pos = ensureSpace( pos, text );
      
      text.insert( pos, value ).insert( pos, String.valueOf( operator ) );
      
      return text;
   }
   
   
   
   private int ensureSpace( int pos, Editable text )
   {
      if ( pos == -1 )
      {
         pos = text.length();
      }
      
      if ( pos > 0 && text.charAt( pos - 1 ) != ' ' )
      {
         text.insert( pos++, " " );
      }
      
      return pos;
   }
   
   
   
   public final Bundle getNewTaskConfig()
   {
      final Collection< Token > tokens = tokenizeInput();
      
      final Bundle config = new Bundle();
      
      if ( tokens.size() > 0 )
      {
         final RtmSmartAddAdapter rtmSmartAddAdapter = (RtmSmartAddAdapter) addTaskEdit.getAdapter();
         Set< String > tags = null;
         
         for ( Token token : tokens )
         {
            // Check that the task name is not only a space character sequence
            if ( token.getType() == RtmSmartAddTokenizer.TASK_NAME_TYPE
               && !token.textContainsOnlySpaces() )
            {
               config.putString( Tasks.TASKSERIES_NAME, token.getText() );
            }
            else
            {
               // Check if the token value comes from a taken suggestion
               final Object suggestionValue = rtmSmartAddAdapter.getSuggestionValue( token.getType(),
                                                                                     token.getText() );
               
               switch ( token.getType() )
               {
                  case RtmSmartAddTokenizer.DUE_DATE_TYPE:
                     if ( suggestionValue != null )
                     {
                        config.putLong( Tasks.DUE_DATE, (Long) suggestionValue );
                        config.putBoolean( Tasks.HAS_DUE_TIME, Boolean.FALSE );
                     }
                     else
                     {
                        final MolokoCalendar cal = context.getParsingService()
                                                          .getDateTimeParsing()
                                                          .parseDateTimeSpec( token.getText() );
                        if ( cal != null )
                        {
                           config.putLong( Tasks.DUE_DATE,
                                           cal.getTimeInMillis() );
                           config.putBoolean( Tasks.HAS_DUE_TIME, cal.hasTime() );
                        }
                     }
                     break;
                  
                  case RtmSmartAddTokenizer.PRIORITY_TYPE:
                     config.putString( Tasks.PRIORITY, token.getText() );
                     break;
                  
                  case RtmSmartAddTokenizer.LIST_TAGS_TYPE:
                     boolean isTag = true;
                     
                     if ( suggestionValue != null )
                     {
                        @SuppressWarnings( "unchecked" )
                        final Pair< String, Boolean > list_or_tag = (Pair< String, Boolean >) suggestionValue;
                        if ( list_or_tag.second )
                        {
                           config.putString( Tasks.LIST_ID, list_or_tag.first );
                           isTag = false;
                        }
                     }
                     
                     if ( isTag )
                     {
                        if ( tags == null )
                        {
                           tags = new TreeSet< String >();
                        }
                        
                        tags.add( token.getText() );
                     }
                     break;
                  
                  case RtmSmartAddTokenizer.LOCATION_TYPE:
                     if ( suggestionValue != null )
                        config.putString( Tasks.LOCATION_ID,
                                          (String) suggestionValue );
                     else
                        config.putString( Tasks.LOCATION_NAME, token.getText() );
                     break;
                  
                  case RtmSmartAddTokenizer.REPEAT_TYPE:
                     final Pair< String, Boolean > recurr;
                     
                     if ( suggestionValue != null )
                     {
                        @SuppressWarnings( "unchecked" )
                        final Pair< String, Boolean > tempValue = (Pair< String, Boolean >) suggestionValue;
                        recurr = tempValue;
                     }
                     else
                        recurr = context.getParsingService()
                                        .getRecurrenceParsing()
                                        .parseRecurrence( token.getText() );
                     
                     if ( recurr != null )
                     {
                        config.putString( Tasks.RECURRENCE, recurr.first );
                        config.putBoolean( Tasks.RECURRENCE_EVERY,
                                           recurr.second );
                     }
                     
                     break;
                  
                  case RtmSmartAddTokenizer.ESTIMATE_TYPE:
                     if ( suggestionValue != null )
                     {
                        config.putString( Tasks.ESTIMATE, token.getText() );
                        config.putLong( Tasks.ESTIMATE_MILLIS,
                                        (Long) suggestionValue );
                     }
                     else
                     {
                        final long estimated = context.getParsingService()
                                                      .getDateTimeParsing()
                                                      .getTimeStruct( token.getText() );
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
      
      return config;
   }
   
   
   
   private Collection< Token > tokenizeInput()
   {
      final CharSequence input = UiUtils.getTrimmedSequence( addTaskEdit );
      
      context.Log().d( getClass(), "Creating tokens for '" + input + "'" );
      
      final List< Token > tokens = new LinkedList< Token >();
      smartAddTokenizer.getTokens( input, tokens );
      
      context.Log().d( getClass(), "Tokens: " + tokens );
      return tokens;
   }
}
