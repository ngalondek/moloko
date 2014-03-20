/* 
 *	Copyright (c) 2013 Ronny R�hricht
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

package dev.drsoran.moloko.domain.services;

import java.util.concurrent.atomic.AtomicReference;

import dev.drsoran.moloko.domain.parsing.IRecurrenceParsing;
import dev.drsoran.rtm.parsing.IRtmDateTimeParsing;
import dev.drsoran.rtm.parsing.IRtmSmartFilterParsing;


class ParsingService implements IParsingService
{
   private final IRtmDateTimeParsing dateTimeParsing;
   
   private final IRtmSmartFilterParsing rtmSmartFilterParsing;
   
   private final AtomicReference< IRecurrenceParsing > recurrenceParsing;
   
   
   
   public ParsingService( IRtmDateTimeParsing dateTimeParsing,
      IRecurrenceParsing recurrenceParsing,
      IRtmSmartFilterParsing rtmSmartFilterParsing )
   {
      this.dateTimeParsing = dateTimeParsing;
      this.recurrenceParsing = new AtomicReference< IRecurrenceParsing >( recurrenceParsing );
      this.rtmSmartFilterParsing = rtmSmartFilterParsing;
   }
   
   
   
   @Override
   public IRtmDateTimeParsing getDateTimeParsing()
   {
      return dateTimeParsing;
   }
   
   
   
   @Override
   public IRecurrenceParsing getRecurrenceParsing()
   {
      return recurrenceParsing.get();
   }
   
   
   
   public void setRecurrenceParsing( IRecurrenceParsing recurrenceParsing )
   {
      this.recurrenceParsing.set( recurrenceParsing );
   }
   
   
   
   @Override
   public IRtmSmartFilterParsing getSmartFilterParsing()
   {
      return rtmSmartFilterParsing;
   }
}