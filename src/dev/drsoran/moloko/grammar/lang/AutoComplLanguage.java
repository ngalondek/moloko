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

package dev.drsoran.moloko.grammar.lang;

import java.text.ParseException;
import java.util.List;

import android.content.res.Resources;


public class AutoComplLanguage extends Language
{
   public AutoComplLanguage( Resources resources, int langResId )
      throws ParseException
   {
      fromResources( resources, langResId );
   }
   


   public List< String > getSuggestions( int state )
   {
      return super.getStings( String.valueOf( state ) );
   }
   


   public List< String > getSuggestions( int state, String unit, int quantity )
   {
      return super.getPluralStrings( String.valueOf( state ),
                                    unit,
                                    String.valueOf( quantity ) );
   }
}
