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

import java.util.Collection;

import android.text.TextUtils;
import android.widget.MultiAutoCompleteTextView.Tokenizer;
import dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken;
import dev.drsoran.moloko.ui.services.ISmartAddService;


public class RtmSmartAddTokenizerAdapter implements Tokenizer
{
   private final ISmartAddService smartAddService;
   
   private CharSequence lastText;
   
   private Collection< RtmSmartAddToken > lastTokens;
   
   
   
   public RtmSmartAddTokenizerAdapter( ISmartAddService smartAddService )
   {
      this.smartAddService = smartAddService;
   }
   
   
   
   @Override
   public int findTokenEnd( CharSequence text, int cursor )
   {
      tokenize( text );
      final RtmSmartAddToken tokenUnderCursor = getTokenUnderCursor( cursor );
      return tokenUnderCursor.getEnd();
   }
   
   
   
   @Override
   public int findTokenStart( CharSequence text, int cursor )
   {
      tokenize( text );
      final RtmSmartAddToken tokenUnderCursor = getTokenUnderCursor( cursor );
      return tokenUnderCursor.getStart();
   }
   
   
   
   @Override
   public CharSequence terminateToken( CharSequence text )
   {
      return text + " ";
   }
   
   
   
   private void tokenize( CharSequence text )
   {
      if ( !TextUtils.equals( text, lastText ) )
      {
         lastText = text;
         lastTokens = smartAddService.tokenize( text );
      }
   }
   
   
   
   private RtmSmartAddToken getTokenUnderCursor( int cursorPos )
   {
      for ( RtmSmartAddToken token : lastTokens )
      {
         if ( token.getStart() <= cursorPos && token.getEnd() >= cursorPos )
         {
            return token;
         }
      }
      
      return null;
   }
}
