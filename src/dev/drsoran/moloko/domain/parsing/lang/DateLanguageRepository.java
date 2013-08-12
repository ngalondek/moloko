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

package dev.drsoran.moloko.domain.parsing.lang;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.NoSuchElementException;


public class DateLanguageRepository implements IDateLanguageRepository
{
   private ILanguage dateLanguageEn;
   
   private ILanguage dateLanguageDe;
   
   
   
   @Override
   public ILanguage getLanguage( Locale locale ) throws NoSuchElementException
   {
      if ( locale == Locale.ENGLISH )
      {
         return getDateLanguageEn();
      }
      
      if ( locale == Locale.GERMAN )
      {
         return getDateLanguageDe();
      }
      
      throw new NoSuchElementException( MessageFormat.format( "No langauge for locale {0}",
                                                              locale.toString() ) );
   }
   
   
   
   private ILanguage getDateLanguageEn()
   {
      if ( dateLanguageEn == null )
      {
         dateLanguageEn = new DateLanguage();
      }
      
      return dateLanguageEn;
   }
   
   
   
   private ILanguage getDateLanguageDe()
   {
      if ( dateLanguageDe == null )
      {
         dateLanguageDe = new DateLanguage();
      }
      
      return dateLanguageDe;
   }
}
