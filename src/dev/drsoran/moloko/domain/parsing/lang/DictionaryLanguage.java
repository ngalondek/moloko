/* 
 *	Copyright (c) 2010 Ronny Röhricht
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

package dev.drsoran.moloko.domain.parsing.lang;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import dev.drsoran.Strings;


public abstract class DictionaryLanguage< T > implements ILanguage
{
   private final Locale locale;
   
   protected final HashMap< String, T > dictionary = new HashMap< String, T >();
   
   
   
   protected DictionaryLanguage( Locale locale )
   {
      if ( locale == null )
      {
         throw new IllegalArgumentException( "locale" );
      }
      
      this.locale = locale;
   }
   
   
   
   @Override
   public Locale getLocale()
   {
      return locale;
   }
   
   
   
   @Override
   public int getInteger( String key )
   {
      return Strings.convertTo( getString( key ), int.class );
   }
   
   
   
   @Override
   public String getString( String key )
   {
      return Strings.convertFrom( dictionary.get( key ) );
   }
   
   
   
   @Override
   public String getPluralString( String key, String unit, int qty )
   {
      return getPluralString( key, unit, String.valueOf( qty ) );
   }
   
   
   
   @Override
   public String getPluralString( String key, String unit, String qty )
   {
      key = MessageFormat.format( "{0}_{1}_", key, unit );
      
      String res = getString( key + qty );
      
      if ( res == null )
      {
         res = getString( key + "n" );
      }
      
      return res;
   }
   
   
   
   @Override
   public List< String > getStrings( String key )
   {
      final String entry = getString( key );
      
      if ( entry != null )
      {
         final String[] values = entry.split( "," );
         return Arrays.asList( values );
      }
      
      return Collections.emptyList();
   }
   
   
   
   public void add( String key, T value ) throws IllegalArgumentException
   {
      if ( Strings.isNullOrEmpty( key ) )
      {
         throw new IllegalArgumentException( "key" );
      }
      
      if ( value == null )
      {
         throw new IllegalArgumentException( "value" );
      }
      
      if ( dictionary.put( key, value ) != null )
      {
         throw new IllegalArgumentException( MessageFormat.format( "Ambigious entry for key {0}",
                                                                   key ) );
      }
   }
}
