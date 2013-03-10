/* 
 *	Copyright (c) 2012 Ronny Röhricht
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

package dev.drsoran.moloko.grammar.lang;

import java.text.ParseException;
import java.util.Locale;

import android.content.res.Resources;
import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.grammar.recurrence.IRecurrenceSentenceLanguage;


public class RecurrenceSentenceLanguage extends Language implements
         IRecurrenceSentenceLanguage
{
   private final ILog log;
   
   
   
   public RecurrenceSentenceLanguage( Locale locale, ILog log,
      Resources resources, int langResId ) throws ParseException
   {
      super( locale );
      
      this.log = log;
      fromResources( resources, langResId );
   }
   
   
   
   @Override
   public void add( StringBuilder sb, String key )
   {
      final String res = dictionary.get( key );
      
      if ( res != null )
      {
         sb.append( res );
      }
      else
      {
         log.e( getClass(), "No dict entry for " + key );
      }
   }
   
   
   
   @Override
   public void addEvery( StringBuilder sb, String unit, String quantity )
   {
      addPlural( sb, "every", unit, quantity );
   }
   
   
   
   @Override
   public void addAfter( StringBuilder sb, String unit, String quantity )
   {
      addPlural( sb, "after", unit, quantity );
   }
   
   
   
   @Override
   public void addStToX( StringBuilder sb, int x )
   {
      final String xStr = String.valueOf( x );
      
      sb.append( xStr );
      
      final String xst = dictionary.get( "xst" );
      
      if ( xst != null )
         sb.append( xst );
      else
      {
         if ( x > 3 && x < 20 )
         {
            sb.append( "th" );
         }
         else
         {
            final char lastNum = xStr.charAt( xStr.length() - 1 );
            
            switch ( lastNum )
            {
               case '1':
                  sb.append( "st" );
                  break;
               case '2':
                  sb.append( "nd" );
                  break;
               case '3':
                  sb.append( "rd" );
                  break;
               default :
                  sb.append( "th" );
                  break;
            }
         }
      }
   }
   
   
   
   private void addPlural( StringBuilder sb,
                           String prefix,
                           String unit,
                           String quantity )
   {
      final String res = getPluralString( prefix, unit, quantity );
      
      if ( res != null )
      {
         sb.append( String.format( res, quantity ) );
      }
      else
      {
         log.e( getClass(), "No dict entry for " + prefix + "_" + unit );
      }
   }
}
