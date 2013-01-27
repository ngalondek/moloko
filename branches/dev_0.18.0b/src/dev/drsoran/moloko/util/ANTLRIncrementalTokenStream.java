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

package dev.drsoran.moloko.util;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.Token;
import org.antlr.runtime.TokenSource;
import org.antlr.runtime.TokenStream;


public class ANTLRIncrementalTokenStream implements TokenStream
{
   protected final List< Token > consumedTokens = new LinkedList< Token >();
   
   protected final static int CHANNEL = Token.DEFAULT_CHANNEL;
   
   protected TokenSource tokenSource;
   
   protected int p = -1;
   
   protected int lastMarker = 0;
   
   protected int consume = 0;
   
   
   
   public ANTLRIncrementalTokenStream()
   {
   }
   
   
   
   public ANTLRIncrementalTokenStream( TokenSource tokenSource )
   {
      this.tokenSource = tokenSource;
   }
   
   
   
   public int LA( int i )
   {
      return LT( i ).getType();
   }
   
   
   
   public void consume()
   {
      ++consume;
   }
   
   
   
   public TokenSource getTokenSource()
   {
      return tokenSource;
   }
   
   
   
   /** Reset this token stream by setting its token source. */
   public void setTokenSource( TokenSource tokenSource )
   {
      this.tokenSource = tokenSource;
      consumedTokens.clear();
      p = -1;
      lastMarker = 0;
      consume = 0;
   }
   
   
   
   public String getSourceName()
   {
      return tokenSource.getSourceName();
   }
   
   
   
   public int index()
   {
      return p;
   }
   
   
   
   public int mark()
   {
      return lastMarker = p;
   }
   
   
   
   public void release( int marker )
   {
   }
   
   
   
   public void rewind()
   {
      seek( lastMarker );
   }
   
   
   
   public void rewind( int marker )
   {
      seek( marker );
   }
   
   
   
   public void seek( int pos )
   {
      p = pos;
   }
   
   
   
   public int size()
   {
      return consumedTokens.size();
   }
   
   
   
   /**
    * Get the i-th token from the current position 1..n where k=1 is the first symbol of lookahead.
    */
   public Token LT( int k )
   {
      Token t = null;
      
      if ( k > 0 )
      {
         ensureFirstToken();
         
         int n = lazyConsume();
         t = get( n );
         
         while ( t != null && t.getType() != Token.EOF && --k > 0 )
         {
            n = getNextChannelTokenPosAfter( n );
            t = get( n );
         }
      }
      
      return t;
   }
   
   
   
   public boolean isEof()
   {
      return hasEofToken();
   }
   
   
   
   public Token reverseGetConsumedToken( int i )
   {
      Token t = null;
      
      if ( consumedTokens.size() > i )
      {
         t = consumedTokens.get( consumedTokens.size() - 1 - i );
      }
      
      return t;
   }
   
   
   
   public Token get( int i )
   {
      if ( i > -1 )
      {
         final int lastTokenIndex = size() - 1;
         
         int index = i;
         
         if ( i > lastTokenIndex )
            index = fillBufferUpToTokenIndex( i );
         
         if ( index < i )
            return Token.EOF_TOKEN;
         else
            return consumedTokens.get( i );
      }
      else
         return null;
   }
   
   
   
   @Override
   public String toString()
   {
      return toString( 0, consumedTokens.size() - 1 );
   }
   
   
   
   public String toString( int start, int stop )
   {
      if ( start < 0 || stop < 0 )
      {
         return "";
      }
      
      final StringBuffer buf = new StringBuffer();
      
      for ( int i = start; i <= Math.max( stop, size() - 1 ); i++ )
      {
         final Token t = get( i );
         buf.append( t.getText() );
      }
      
      return buf.toString();
   }
   
   
   
   public String toString( Token start, Token stop )
   {
      if ( start != null && stop != null )
      {
         return toString( start.getTokenIndex(), stop.getTokenIndex() );
      }
      return "";
   }
   
   
   
   protected boolean addToken( Token token )
   {
      boolean added = false;
      
      if ( token != null )
      {
         if ( token.getType() != Token.EOF || !hasEofToken() )
         {
            consumedTokens.add( token );
            added = true;
         }
      }
      
      return added;
   }
   
   
   
   protected int fillBufferUpToTokenIndex( int index )
   {
      boolean stop = false;
      
      while ( index > ( size() - 1 ) && !stop )
      {
         stop = !addToken( tokenSource.nextToken() );
      }
      
      return size() - 1;
   }
   
   
   
   /**
    * Given a starting index, return the index of the first on-channel token.
    */
   protected int getNextChannelTokenPosAfter( int i )
   {
      int pos = i;
      
      Token t = get( ++pos );
      
      while ( t.getType() != Token.EOF && t.getChannel() != CHANNEL )
      {
         t = get( ++pos );
      }
      
      return pos;
   }
   
   
   
   protected int getPreviousChannelTokenPosBefore( int i )
   {
      int pos = i;
      
      Token t = get( --pos );
      
      while ( t != null && t.getType() != Token.EOF
         && t.getChannel() != CHANNEL )
      {
         t = get( --pos );
      }
      
      return pos;
   }
   
   
   
   protected void ensureFirstToken()
   {
      if ( p == -1 )
         p = getNextChannelTokenPosAfter( p );
   }
   
   
   
   protected boolean hasEofToken()
   {
      return consumedTokens.size() > 0
         && consumedTokens.get( consumedTokens.size() - 1 ).getType() == Token.EOF;
   }
   
   
   
   protected int lazyConsume()
   {
      while ( consume > 0 )
      {
         p = getNextChannelTokenPosAfter( p );
         --consume;
      }
      
      return p;
   }
}
