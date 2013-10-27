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

package dev.drsoran.moloko.ui.services;

import android.content.Context;
import dev.drsoran.moloko.domain.DomainContext;


public class UiServicesContainer implements IUiServices
{
   private final Context context;
   
   private final MolokoDateFormatterService dataFormatterService;
   
   
   
   public UiServicesContainer( Context context )
   {
      this.context = context;
      this.dataFormatterService = new MolokoDateFormatterService( context );
   }
   
   
   
   public void set24hFormat( boolean is24hFormat )
   {
      dataFormatterService.setIs24hFormat( is24hFormat );
   }
   
   
   
   @Override
   public IDateFormatterService getDateFormatter()
   {
      return dataFormatterService;
   }
   
   
   
   @Override
   public ISmartAddService getSmartAddService()
   {
      final DomainContext domainContext = DomainContext.get( context );
      
      // Create always new because this is about to be used rarely.
      return new SmartAddService( context,
                                  domainContext.getContentRepository(),
                                  domainContext.getParsingService()
                                               .getDateTimeParsing(),
                                  domainContext.getParsingService()
                                               .getRecurrenceParsing(),
                                  dataFormatterService );
   }
   
}
