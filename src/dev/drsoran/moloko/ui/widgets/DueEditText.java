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

package dev.drsoran.moloko.ui.widgets;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.domain.parsing.GrammarException;
import dev.drsoran.moloko.ui.IChangesTarget;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.ValidationResult;
import dev.drsoran.moloko.ui.services.IDateFormatterService;


public class DueEditText extends ClearableEditText
{
   private final static int FORMAT = IDateFormatterService.FORMAT_NUMERIC
      | IDateFormatterService.FORMAT_WITH_YEAR;
   
   public final static String EDIT_DUE_TEXT = "edit_due_text";
   
   private MolokoCalendar dueCalendar = getDatelessAndTimelessInstance();
   
   private IChangesTarget changes;
   
   
   
   public DueEditText( Context context )
   {
      this( context, null, android.R.attr.editTextStyle );
   }
   
   
   
   public DueEditText( Context context, AttributeSet attrs )
   {
      this( context, attrs, android.R.attr.editTextStyle );
   }
   
   
   
   public DueEditText( Context context, AttributeSet attrs, int defStyle )
   {
      super( context, attrs, defStyle );
   }
   
   
   
   public void setDue( long millis, boolean hasDueTime )
   {
      setDueCalendarByMillis( millis, hasDueTime );
      updateEditDueText();
   }
   
   
   
   public void setDue( String dueString )
   {
      commitInput( dueString );
   }
   
   
   
   public void putInitialValue( Bundle initialValues )
   {
      initialValues.putString( EDIT_DUE_TEXT, getTextTrimmed() );
   }
   
   
   
   public void setChangesTarget( IChangesTarget changes )
   {
      this.changes = changes;
   }
   
   
   
   public boolean hasDate()
   {
      return isDueCalendarValid() && dueCalendar.hasDate();
   }
   
   
   
   public ValidationResult validate()
   {
      if ( isEnabled() )
      {
         final MolokoCalendar cal = parseDue( getTextTrimmed() );
         return validateCalendar( cal );
      }
      else
      {
         return ValidationResult.OK;
      }
   }
   
   
   
   public MolokoCalendar getDueCalendar()
   {
      commitInput( getTextTrimmed() );
      return dueCalendar;
   }
   
   
   
   @Override
   public void onEditorAction( int actionCode )
   {
      boolean stayInEditText = false;
      
      if ( UiUtils.hasInputCommitted( actionCode ) )
      {
         setDueCalendarByParseString( getTextTrimmed() );
         
         final boolean inputValid = validateCalendar( dueCalendar ).isOk();
         stayInEditText = !inputValid;
         
         if ( inputValid )
            updateEditDueText();
      }
      
      if ( !stayInEditText )
         super.onEditorAction( actionCode );
   }
   
   
   
   @Override
   protected void onTextChanged( CharSequence text,
                                 int start,
                                 int before,
                                 int after )
   {
      super.onTextChanged( text, start, before, after );
      
      putTextChange();
      
      if ( TextUtils.isEmpty( text ) )
      {
         handleEmptyInputString();
      }
   }
   
   
   
   private void updateEditDueText()
   {
      if ( isDueCalendarValid() )
      {
         if ( !dueCalendar.hasDate() )
         {
            setText( null );
         }
         else if ( dueCalendar.hasTime() )
         {
            setText( getUiContext().getDateFormatter()
                                   .formatDateTime( dueCalendar.getTimeInMillis(),
                                                    FORMAT ) );
         }
         else
         {
            setText( getUiContext().getDateFormatter()
                                   .formatDate( dueCalendar.getTimeInMillis(),
                                                FORMAT ) );
         }
      }
   }
   
   
   
   private MolokoCalendar parseDue( String dueStr )
   {
      if ( TextUtils.isEmpty( dueStr ) )
      {
         return handleEmptyInputString();
      }
      else
      {
         try
         {
            return getUiContext().getParsingService()
                                 .getDateTimeParsing()
                                 .parseDateTimeSpec( dueStr );
         }
         catch ( GrammarException e )
         {
            return null;
         }
      }
   }
   
   
   
   private MolokoCalendar handleEmptyInputString()
   {
      final MolokoCalendar cal;
      
      if ( isDueCalendarValid() )
      {
         cal = dueCalendar;
         cal.setHasDate( false );
         cal.setHasTime( false );
      }
      else
      {
         cal = getDatelessAndTimelessInstance();
      }
      
      return cal;
   }
   
   
   
   private void setDueCalendarByMillis( long millis, boolean hasDueTime )
   {
      if ( !isDueCalendarValid() )
         dueCalendar = MolokoCalendar.getInstance();
      
      if ( millis != -1 )
      {
         dueCalendar.setTimeInMillis( millis );
         dueCalendar.setHasDate( true );
         dueCalendar.setHasTime( hasDueTime );
      }
   }
   
   
   
   private void setDueCalendarByParseString( String dueString )
   {
      dueCalendar = parseDue( dueString );
   }
   
   
   
   private boolean isDueCalendarValid()
   {
      return dueCalendar != null;
   }
   
   
   
   private ValidationResult validateCalendar( MolokoCalendar cal )
   {
      final boolean valid = cal != null;
      if ( !valid )
      {
         return new ValidationResult( getContext().getString( R.string.task_edit_validate_due,
                                                              getTextTrimmed() ),
                                      this );
      }
      
      return ValidationResult.OK;
   }
   
   
   
   private void putTextChange()
   {
      if ( changes != null )
      {
         changes.putChange( EDIT_DUE_TEXT, getTextTrimmed(), String.class );
      }
   }
   
   
   
   private void commitInput( String input )
   {
      if ( isEnabled() )
      {
         setDueCalendarByParseString( input );
         updateEditDueText();
      }
   }
   
   
   
   private MolokoCalendar getDatelessAndTimelessInstance()
   {
      if ( !isInEditMode() )
         return MolokoCalendar.getDatelessAndTimelessInstance();
      else
         return null;
   }
}
