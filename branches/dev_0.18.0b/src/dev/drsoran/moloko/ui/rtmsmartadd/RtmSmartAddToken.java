/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

import dev.drsoran.Strings;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer;


public class RtmSmartAddToken
{
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
   
   private final int type;
   
   private int start = -1;
   
   private int end = -1;
   
   private String text;
   
   
   
   public RtmSmartAddToken( int type )
   {
      this.type = type;
   }
   
   
   
   public int getType()
   {
      return type;
   }
   
   
   
   public int getStart()
   {
      return start;
   }
   
   
   
   public void setStart( int start )
   {
      this.start = start;
   }
   
   
   
   public int getEnd()
   {
      return end;
   }
   
   
   
   public void setEnd( int end )
   {
      this.end = end;
   }
   
   
   
   public String getText()
   {
      return text;
   }
   
   
   
   public boolean textContainsOnlySpaces()
   {
      return text.replaceAll( " ", Strings.EMPTY_STRING ).length() == 0;
   }
   
   
   
   public void setText( String text )
   {
      this.text = text;
   }
   
   
   
   @Override
   public String toString()
   {
      return type + ":" + start + "," + end + "," + text;
   }
   
   
   
   public static boolean isOperator( char character )
   {
      return ( character == OP_DUE_DATE || character == OP_PRIORITY
         || character == OP_LIST_TAGS || character == OP_LOCATION
         || character == OP_REPEAT || character == OP_ESTIMATE );
   }
   
   
   
   public static Character getOperatorFromRtmSmartFilterTokenType( int rtmSmartFilterTokenType )
   {
      switch ( rtmSmartFilterTokenType )
      {
         case RtmSmartFilterLexer.OP_DUE:
            return Character.valueOf( OP_DUE_DATE );
            
         case RtmSmartFilterLexer.OP_PRIORITY:
            return Character.valueOf( OP_PRIORITY );
            
         case RtmSmartFilterLexer.OP_LIST:
         case RtmSmartFilterLexer.OP_TAG:
            return Character.valueOf( OP_LIST_TAGS );
            
         case RtmSmartFilterLexer.OP_LOCATION:
            return Character.valueOf( OP_LOCATION );
            
         case RtmSmartFilterLexer.OP_TIME_ESTIMATE:
            return Character.valueOf( OP_ESTIMATE );
            
         default :
            return null;
      }
   }
}
