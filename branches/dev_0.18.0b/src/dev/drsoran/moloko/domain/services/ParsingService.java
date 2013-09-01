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

package dev.drsoran.moloko.domain.services;

import dev.drsoran.moloko.domain.parsing.IDateTimeParsing;
import dev.drsoran.moloko.domain.parsing.IRecurrenceParsing;
import dev.drsoran.moloko.domain.parsing.IRtmSmartFilterParsing;


class ParsingService implements IParsingService
{
   private final IDateTimeParsing dateTimeParsing;
   
   private final IRtmSmartFilterParsing rtmSmartFilterParsing;
   
   private IRecurrenceParsing recurrenceParsing;
   
   
   
   public ParsingService( IDateTimeParsing dateTimeParsing,
      IRecurrenceParsing recurrenceParsing,
      IRtmSmartFilterParsing rtmSmartFilterParsing )
   {
      this.dateTimeParsing = dateTimeParsing;
      this.recurrenceParsing = recurrenceParsing;
      this.rtmSmartFilterParsing = rtmSmartFilterParsing;
   }
   
   
   
   @Override
   public IDateTimeParsing getDateTimeParsing()
   {
      return dateTimeParsing;
   }
   
   
   
   @Override
   public IRecurrenceParsing getRecurrenceParsing()
   {
      return recurrenceParsing;
   }
   
   
   
   public void setRecurrenceParsing( IRecurrenceParsing recurrenceParsing )
   {
      this.recurrenceParsing = recurrenceParsing;
   }
   
   
   
   @Override
   public IRtmSmartFilterParsing getRtmSmartFilterParsing()
   {
      return rtmSmartFilterParsing;
   }
}
