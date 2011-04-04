/* 
 *	Copyright (c) 2011 Ronny R�hricht
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
 * Ronny R�hricht - implementation
 */

package dev.drsoran.moloko.grammar;

import java.util.List;

import android.text.TextUtils;
import android.widget.MultiAutoCompleteTextView.Tokenizer;


public class RtmSmartAddTokenizer implements Tokenizer
{
   public static class Token
   {
      public final int type;
      
      public int start = -1;
      
      public int end = -1;
      
      public int startTxt = -1;
      
      public int endTxt = -1;
      
      

      public Token( int type )
      {
         this.type = type;
      }
      


      public String getText( CharSequence chars )
      {
         if ( startTxt == -1 || endTxt == -1 )
            return "";
         else
            return TextUtils.substring( chars,
                                        startTxt,
                                        ( endTxt - startTxt ) + 1 );
      }
      


      @Override
      public String toString()
      {
         return type + ":" + start + "-" + startTxt + "," + end + "-" + endTxt;
      }
      
   }
   
   public final static char OP_DUE_DATE = '^';
   
   public final static char OP_PRIORITY = '!';
   
   public final static char OP_LIST_TAGS = '#';
   
   public final static char OP_LOCATION = '@';
   
   public final static char OP_REPEAT = '*';
   
   public final static char OP_ESTIMATE = '=';
   
   public final static int DUE_DATE_TYPE = 0;
   
   public final static int PRIORITY_TYPE = 1;
   
   public final static int LIST_TAGS_TYPE = 2;
   
   public final static int LOCATION_TYPE = 3;
   
   public final static int REPEAT_TYPE = 4;
   
   public final static int ESTIMATE_TYPE = 5;
   
   public final static int TASK_NAME_TYPE = 6;
   
   

   public RtmSmartAddTokenizer()
   {
   }
   


   public final void getTokens( CharSequence input, List< Token > tokens )
   {
      for ( int i = 0, cnt = input.length(); i < cnt; ++i )
      {
         final Token t;
         
         switch ( input.charAt( i ) )
         {
            case OP_DUE_DATE:
               t = new Token( DUE_DATE_TYPE );
               t.start = i;
               t.end = getNextOperatorPos( input, i, OP_DUE_DATE ) - 1;
               setTextPositions( t, input, t.start + 1, t.end );
               break;
            
            case OP_PRIORITY:
               t = new Token( PRIORITY_TYPE );
               t.start = i;
               t.end = getNextOperatorPos( input, i, OP_PRIORITY ) - 1;
               setTextPositions( t, input, t.start + 1, t.end );
               break;
            
            case OP_LIST_TAGS:
               t = new Token( LIST_TAGS_TYPE );
               t.start = i;
               t.end = getNextOperatorPos( input, i, OP_LIST_TAGS ) - 1;
               setTextPositions( t, input, t.start + 1, t.end );
               break;
            
            case OP_LOCATION:
               t = new Token( LOCATION_TYPE );
               t.start = i;
               t.end = getNextOperatorPos( input, i, OP_LOCATION ) - 1;
               setTextPositions( t, input, t.start + 1, t.end );
               break;
            
            case OP_REPEAT:
               t = new Token( REPEAT_TYPE );
               t.start = i;
               t.end = getNextOperatorPos( input, i, OP_REPEAT ) - 1;
               setTextPositions( t, input, t.start + 1, t.end );
               break;
            
            case OP_ESTIMATE:
               t = new Token( ESTIMATE_TYPE );
               t.start = i;
               t.end = getNextOperatorPos( input, i, OP_ESTIMATE ) - 1;
               setTextPositions( t, input, t.start + 1, t.end );
               break;
            
            default :
               t = new Token( TASK_NAME_TYPE );
               t.start = i;
               t.end = getNextOperatorPos( input, i, null ) - 1;
               setTextPositions( t, input, t.start, t.end );
               break;
         }
         
         tokens.add( t );
         
         i = t.end;
      }
   }
   


   public int findTokenEnd( CharSequence text, int cursor )
   {
      final int end = getNextOperatorPos( text, cursor, null );
      return revFindFirstNotSpace( text, cursor, end );
   }
   


   public int findTokenStart( CharSequence text, int cursor )
   {
      final int start = getPrevOperatorPos( text, cursor, null );
      return findFirstNotSpace( text, start, cursor - 1 );
   }
   


   public CharSequence terminateToken( CharSequence text )
   {
      return " ";
   }
   


   public boolean isOperator( char character, Character ownOp )
   {
      return ( ownOp == null || character != ownOp.charValue() )
         && ( character == OP_DUE_DATE || character == OP_PRIORITY
            || character == OP_LIST_TAGS || character == OP_LOCATION
            || character == OP_REPEAT || character == OP_ESTIMATE );
   }
   


   private int getNextOperatorPos( CharSequence chars,
                                   int startIdx,
                                   Character ownOp )
   {
      for ( int i = startIdx + 1, cnt = chars.length(); i < cnt; ++i )
      {
         final char charI = chars.charAt( i );
         if ( isOperator( charI, ownOp ) )
            return i;
      }
      
      return chars.length();
   }
   


   private int getPrevOperatorPos( CharSequence chars,
                                   int startIdx,
                                   Character ownOp )
   {
      for ( int i = startIdx - 1; i > -1; --i )
      {
         final char charI = chars.charAt( i );
         if ( isOperator( charI, ownOp ) )
            return i;
      }
      
      return 0;
   }
   


   private void setTextPositions( Token token,
                                  CharSequence chars,
                                  int startIdx,
                                  int endIdx )
   {
      // Find first character not space
      token.startTxt = token.endTxt = findFirstNotSpace( chars,
                                                         startIdx,
                                                         endIdx );
      
      if ( token.start < endIdx )
      {
         // Find last character not space
         token.endTxt = revFindFirstNotSpace( chars, startIdx, endIdx );
      }
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
      
      return 0;
   }
}
