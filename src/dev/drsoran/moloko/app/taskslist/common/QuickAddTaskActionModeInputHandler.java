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

import static dev.drsoran.moloko.content.Columns.TaskColumns.DUE_DATE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.ESTIMATE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.ESTIMATE_MILLIS;
import static dev.drsoran.moloko.content.Columns.TaskColumns.HAS_DUE_TIME;
import static dev.drsoran.moloko.content.Columns.TaskColumns.LIST_ID;
import static dev.drsoran.moloko.content.Columns.TaskColumns.LOCATION_ID;
import static dev.drsoran.moloko.content.Columns.TaskColumns.LOCATION_NAME;
import static dev.drsoran.moloko.content.Columns.TaskColumns.PRIORITY;
import static dev.drsoran.moloko.content.Columns.TaskColumns.RECURRENCE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.RECURRENCE_EVERY;
import static dev.drsoran.moloko.content.Columns.TaskColumns.TAGS;
import static dev.drsoran.moloko.content.Columns.TaskColumns.TAGS_SEPARATOR;
import static dev.drsoran.moloko.content.Columns.TaskColumns.TASK_NAME;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.DUE_DATE_TYPE;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.ESTIMATE_TYPE;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.LIST_TAGS_TYPE;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.LOCATION_TYPE;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.PRIORITY_TYPE;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.REPEAT_TYPE;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.TASK_NAME_TYPE;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.domain.model.Recurrence;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.domain.parsing.GrammarException;
import dev.drsoran.moloko.domain.parsing.rtmsmart.RtmSmartFilterToken;
import dev.drsoran.moloko.domain.services.IParsingService;
import dev.drsoran.moloko.ui.UiContext;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken;
import dev.drsoran.moloko.ui.services.ISmartAddService;
import dev.drsoran.moloko.ui.widgets.RtmSmartAddTextView;


class QuickAddTaskActionModeInputHandler
{
   private final UiContext context;
   
   private final IParsingService parsingService;
   
   private final ISmartAddService smartAddService;
   
   private final RtmSmartAddTextView addTaskEdit;
   
   
   
   public QuickAddTaskActionModeInputHandler( UiContext context,
      RtmSmartAddTextView inputView )
   {
      this.context = context;
      this.parsingService = context.getParsingService();
      this.smartAddService = context.getSmartAddService();
      this.addTaskEdit = inputView;
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
   
   
   
   public final Bundle getNewTaskConfig()
   {
      final Collection< RtmSmartAddToken > tokens = tokenizeInput();
      
      final Bundle config = new Bundle();
      
      if ( tokens.size() > 0 )
      {
         final RtmSmartAddAdapter rtmSmartAddAdapter = (RtmSmartAddAdapter) addTaskEdit.getAdapter();
         Set< String > tags = new TreeSet< String >();
         
         for ( RtmSmartAddToken token : tokens )
         {
            // Check that the task name is not only a space character sequence
            if ( token.getType() == TASK_NAME_TYPE
               && !token.textContainsOnlySpaces() )
            {
               config.putString( TASK_NAME, token.getText() );
            }
            else
            {
               // Check if the token value comes from a taken suggestion
               final Object suggestionValue = rtmSmartAddAdapter.getSuggestionValue( token.getType(),
                                                                                     token.getText() );
               
               switch ( token.getType() )
               {
                  case DUE_DATE_TYPE:
                     if ( suggestionValue != null )
                     {
                        config.putLong( DUE_DATE, (Long) suggestionValue );
                        config.putBoolean( HAS_DUE_TIME, Boolean.FALSE );
                     }
                     else
                     {
                        try
                        {
                           final MolokoCalendar cal = parsingService.getDateTimeParsing()
                                                                    .parseDateTime( token.getText() );
                           config.putLong( DUE_DATE, cal.getTimeInMillis() );
                           config.putBoolean( HAS_DUE_TIME, cal.hasTime() );
                        }
                        catch ( GrammarException e )
                        {
                           // Not parsable user input
                        }
                     }
                     break;
                  
                  case PRIORITY_TYPE:
                     config.putString( PRIORITY, token.getText() );
                     break;
                  
                  case LIST_TAGS_TYPE:
                     if ( suggestionValue != null )
                     {
                        config.putLong( LIST_ID, (Long) suggestionValue );
                     }
                     else
                     {
                        tags.add( token.getText() );
                     }
                     break;
                  
                  case LOCATION_TYPE:
                     if ( suggestionValue != null )
                     {
                        config.putLong( LOCATION_ID, (Long) suggestionValue );
                     }
                     else
                     {
                        config.putString( LOCATION_NAME, token.getText() );
                     }
                     break;
                  
                  case REPEAT_TYPE:
                     Recurrence recurrence;
                     
                     if ( suggestionValue != null )
                     {
                        recurrence = (Recurrence) suggestionValue;
                     }
                     else
                     {
                        try
                        {
                           recurrence = parsingService.getRecurrenceParsing()
                                                      .parseRecurrence( token.getText() );
                        }
                        catch ( GrammarException e )
                        {
                           recurrence = null;
                        }
                     }
                     
                     if ( recurrence != null )
                     {
                        config.putString( RECURRENCE, recurrence.getPattern() );
                        config.putBoolean( RECURRENCE_EVERY,
                                           recurrence.isEveryRecurrence() );
                     }
                     
                     break;
                  
                  case ESTIMATE_TYPE:
                     if ( suggestionValue != null )
                     {
                        config.putString( ESTIMATE, token.getText() );
                        config.putLong( ESTIMATE_MILLIS, (Long) suggestionValue );
                     }
                     else
                     {
                        try
                        {
                           final long estimated = parsingService.getDateTimeParsing()
                                                                .parseEstimated( token.getText() );
                           config.putString( ESTIMATE,
                                             context.getDateFormatter()
                                                    .formatEstimated( estimated ) );
                           config.putLong( ESTIMATE_MILLIS,
                                           Long.valueOf( estimated ) );
                        }
                        catch ( GrammarException e )
                        {
                           // Not parsable user input
                        }
                     }
                     break;
                  
                  default :
                     break;
               }
            }
         }
         
         if ( !tags.isEmpty() )
         {
            config.putString( TAGS, TextUtils.join( TAGS_SEPARATOR, tags ) );
         }
      }
      
      return config;
   }
   
   
   
   private int preselectByFilter( RtmSmartFilter filter )
   {
      int numPreselected = 0;
      
      Collection< RtmSmartFilterToken > rtmSmartFilterTokens;
      try
      {
         rtmSmartFilterTokens = parsingService.getRtmSmartFilterParsing()
                                              .getSmartFilterTokens( filter.getFilterString() )
                                              .getUniqueTokens();
      }
      catch ( GrammarException e )
      {
         rtmSmartFilterTokens = Collections.emptyList();
      }
      
      // Iterate over all tokens and suggest all non-null tokens
      for ( RtmSmartFilterToken rtmSmartFilterToken : rtmSmartFilterTokens )
      {
         final Character operator = RtmSmartAddToken.getOperatorFromRtmSmartFilterTokenType( rtmSmartFilterToken.operatorType );
         
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
   
   
   
   private Editable insertOperatorAtPosition( char operator, int pos )
   {
      final Editable text = addTaskEdit.getEditableText();
      
      pos = ensureSpace( pos, text );
      
      text.insert( pos, String.valueOf( operator ) );
      
      return text;
   }
   
   
   
   private Editable insertOperatorAndValue( char operator, String value, int pos )
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
   
   
   
   private Collection< RtmSmartAddToken > tokenizeInput()
   {
      final CharSequence input = UiUtils.getTrimmedSequence( addTaskEdit );
      
      context.Log().d( getClass(),
                       MessageFormat.format( "Creating tokens for ''{0}''",
                                             input ) );
      
      final Collection< RtmSmartAddToken > tokens = smartAddService.tokenize( input );
      
      context.Log()
             .d( getClass(), MessageFormat.format( "Tokens: {0}", tokens ) );
      
      return tokens;
   }
}
