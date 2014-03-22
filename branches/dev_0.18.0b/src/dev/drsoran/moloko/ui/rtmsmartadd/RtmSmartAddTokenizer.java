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

import dev.drsoran.rtm.Strings;
import dev.drsoran.rtm.parsing.GrammarException;
import dev.drsoran.rtm.parsing.IRtmDateTimeParsing;


public class RtmSmartAddTokenizer
{
   private final IRtmDateTimeParsing dateTimeParsing;
   
   
   
   public RtmSmartAddTokenizer( IRtmDateTimeParsing dateTimeParsing )
   {
      if ( dateTimeParsing == null )
      {
         throw new IllegalArgumentException( "dateTimeParsing" );
      }
      
      this.dateTimeParsing = dateTimeParsing;
   }
   
   
   
   public Collection< RtmSmartAddToken > getTokens( CharSequence input )
   {
      final Collection< RtmSmartAddToken > tokens = new ArrayList< RtmSmartAddToken >();
      
      for ( int i = 0, cnt = input.length(); i < cnt; )
      {
         int textStartPos = i + 1;
         Character ownOp = null;
         
         final RtmSmartAddToken t;
         
         switch ( input.charAt( i ) )
         {
            case OP_DUE_DATE:
               t = new RtmSmartAddToken( DUE_DATE_TYPE );
               break;
            
            case OP_PRIORITY:
               t = new RtmSmartAddToken( PRIORITY_TYPE );
               break;
            
            case OP_LIST_TAGS:
               t = new RtmSmartAddToken( LIST_TAGS_TYPE );
               break;
            
            case OP_LOCATION:
               t = new RtmSmartAddToken( LOCATION_TYPE );
               break;
            
            case OP_REPEAT:
               t = new RtmSmartAddToken( REPEAT_TYPE );
               break;
            
            case OP_ESTIMATE:
               t = new RtmSmartAddToken( ESTIMATE_TYPE );
               break;
            
            default :
               t = new RtmSmartAddToken( TASK_NAME_TYPE );
               textStartPos = i;
               break;
         }
         
         initToken( input, i, textStartPos, t, ownOp );
         
         tokens.add( t );
         
         i = t.getEnd() + 1;
      }
      
      return tokens;
   }
   
   
   
   private void initToken( CharSequence input,
                           int opPos,
                           int textPos,
                           RtmSmartAddToken t,
                           Character ownOp )
   {
      t.setStart( opPos );
      
      final int endPos = getNextOperatorPos( input, textPos, ownOp ) - 1;
      
      t.setEnd( endPos );
      
      if ( endPos >= textPos )
      {
         setText( t, input, textPos, endPos );
      }
      else
      {
         t.setText( Strings.EMPTY_STRING );
      }
   }
   
   
   
   private int getNextOperatorPos( CharSequence chars,
                                   int startIdx,
                                   Character ownOp )
   {
      final int cnt = chars.length();
      
      for ( int i = startIdx; i < cnt; ++i )
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
                  final String dateToParse = chars.subSequence( i, nextOpPos )
                                                  .toString();
                  dateTimeParsing.parseTime( dateToParse );
                  i = nextOpPos;
               }
               catch ( GrammarException e )
               {
                  // Empty, it is a try parse
                  continue;
               }
            }
            
            return i;
         }
      }
      
      return cnt;
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
      
      return chars.subSequence( startTxt, endTxt + 1 ).toString();
   }
   
   
   
   private int findFirstNotSpace( CharSequence chars, int startIdx, int endIdx )
   {
      for ( int i = startIdx; i < endIdx + 1; ++i )
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
