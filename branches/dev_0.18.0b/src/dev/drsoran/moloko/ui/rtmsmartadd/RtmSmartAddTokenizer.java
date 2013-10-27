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

package dev.drsoran.moloko.ui.rtmsmartadd;

import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.DUE_DATE_TYPE;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.ESTIMATE_TYPE;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.LIST_TAGS_TYPE;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.LOCATION_TYPE;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.OP_DUE_DATE;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.OP_ESTIMATE;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.OP_LIST_TAGS;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.OP_LOCATION;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.OP_PRIORITY;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.OP_REPEAT;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.PRIORITY_TYPE;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.REPEAT_TYPE;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.TASK_NAME_TYPE;

import java.util.ArrayList;
import java.util.Collection;

import android.text.TextUtils;
import dev.drsoran.moloko.domain.parsing.GrammarException;
import dev.drsoran.moloko.domain.parsing.IDateTimeParsing;


public class RtmSmartAddTokenizer
{
   private final IDateTimeParsing dateTimeParsing;
   
   
   
   public RtmSmartAddTokenizer( IDateTimeParsing dateTimeParsing )
   {
      this.dateTimeParsing = dateTimeParsing;
   }
   
   
   
   public Collection< RtmSmartAddToken > getTokens( CharSequence input )
   {
      final Collection< RtmSmartAddToken > tokens = new ArrayList< RtmSmartAddToken >();
      
      for ( int i = 0, cnt = input.length(); i < cnt; ++i )
      {
         final RtmSmartAddToken t;
         
         switch ( input.charAt( i ) )
         {
            case OP_DUE_DATE:
               t = new RtmSmartAddToken( DUE_DATE_TYPE );
               t.setStart( i );
               t.setEnd( getNextOperatorPos( input, i + 1, null ) - 1 );
               setText( t, input, t.getStart() + 1, t.getEnd() );
               break;
            
            case OP_PRIORITY:
               t = new RtmSmartAddToken( PRIORITY_TYPE );
               t.setStart( i );
               t.setEnd( getNextOperatorPos( input, i + 1, null ) - 1 );
               setText( t, input, t.getStart() + 1, t.getEnd() );
               break;
            
            case OP_LIST_TAGS:
               t = new RtmSmartAddToken( LIST_TAGS_TYPE );
               t.setStart( i );
               t.setEnd( getNextOperatorPos( input, i + 1, null ) - 1 );
               setText( t, input, t.getStart() + 1, t.getEnd() );
               break;
            
            case OP_LOCATION:
               t = new RtmSmartAddToken( LOCATION_TYPE );
               t.setStart( i );
               t.setEnd( getNextOperatorPos( input, i + 1, OP_LOCATION ) - 1 );
               setText( t, input, t.getStart() + 1, t.getEnd() );
               break;
            
            case OP_REPEAT:
               t = new RtmSmartAddToken( REPEAT_TYPE );
               t.setStart( i );
               t.setEnd( getNextOperatorPos( input, i + 1, null ) - 1 );
               setText( t, input, t.getStart() + 1, t.getEnd() );
               break;
            
            case OP_ESTIMATE:
               t = new RtmSmartAddToken( ESTIMATE_TYPE );
               t.setStart( i );
               t.setEnd( getNextOperatorPos( input, i + 1, null ) - 1 );
               setText( t, input, t.getStart() + 1, t.getEnd() );
               break;
            
            default :
               t = new RtmSmartAddToken( TASK_NAME_TYPE );
               t.setStart( i );
               t.setEnd( getNextOperatorPos( input, i, null ) - 1 );
               setText( t, input, t.getStart(), t.getEnd() );
               break;
         }
         
         tokens.add( t );
         
         i = t.getEnd();
      }
      
      return tokens;
   }
   
   
   
   private int getNextOperatorPos( CharSequence chars,
                                   int startIdx,
                                   Character ownOp )
   {
      for ( int i = startIdx, cnt = chars.length(); i < cnt; ++i )
      {
         final char charI = chars.charAt( i );
         if ( isOperator( charI, ownOp ) )
         {
            // SPECIAL CASE @: In case of a due date, the @ is also
            // used as date and time separator. So we have to check
            // if this is not a time and also belongs to the data.
            if ( charI == '@' )
            {
               // Find the next operator start
               final int nextOpPos = getNextOperatorPos( chars, i + 1, ownOp );
               
               // try to parse as time
               try
               {
                  dateTimeParsing.parseTime( TextUtils.substring( chars,
                                                                  i,
                                                                  nextOpPos ) );
                  i = nextOpPos;
               }
               catch ( GrammarException e )
               {
                  // Empty, it is a try parse
               }
            }
            
            return i;
         }
      }
      
      return chars.length();
   }
   
   
   
   private static boolean isOperator( char character, Character ownOp )
   {
      return ( ownOp == null || character != ownOp.charValue() )
         && RtmSmartAddToken.isOperator( character );
   }
   
   
   
   private void setText( RtmSmartAddToken token,
                         CharSequence chars,
                         int startIdx,
                         int endIdx )
   {
      token.setText( getText( chars, startIdx, endIdx ) );
   }
   
   
   
   private String getText( CharSequence chars, int startIdx, int endIdx )
   {
      // Find first character not space
      int endTxt = 0;
      int startTxt = endTxt = findFirstNotSpace( chars, startIdx, endIdx );
      
      if ( startTxt < endIdx )
      {
         // Find last character not space
         endTxt = revFindFirstNotSpace( chars, startTxt, endIdx );
      }
      
      return TextUtils.substring( chars, startTxt, endTxt + 1 );
   }
   
   
   
   private int findFirstNotSpace( CharSequence chars, int startIdx, int endIdx )
   {
      for ( int i = startIdx; i < endIdx; ++i )
      {
         if ( chars.charAt( i ) != ' ' )
         {
            return i;
         }
      }
      
      return endIdx;
   }
   
   
   
   private int revFindFirstNotSpace( CharSequence chars,
                                     int startIdx,
                                     int endIdx )
   {
      for ( int i = endIdx; i > startIdx; --i )
      {
         if ( chars.charAt( i ) != ' ' )
         {
            return i;
         }
      }
      
      return startIdx;
   }
}
