/*
 * Copyright (c) 2012 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.rtm.model;

public class RtmSettings
{
   private final long syncTimeStampMillis;
   
   private final String timezone;
   
   private final int dateFormat;
   
   private final int timeFormat;
   
   private final String defaultListId;
   
   private final String language;
   
   
   
   public RtmSettings( long syncTimeStampMillis, String timezone,
      int dateFormat, int timeFormat, String defaultListId, String language )
   {
      this.syncTimeStampMillis = syncTimeStampMillis;
      this.timezone = timezone;
      this.dateFormat = dateFormat;
      this.timeFormat = timeFormat;
      this.defaultListId = defaultListId;
      this.language = language;
   }
   
   
   
   public long getSyncTimeStampMillis()
   {
      return syncTimeStampMillis;
   }
   
   
   
   public String getTimezone()
   {
      return timezone;
   }
   
   
   
   public int getDateFormat()
   {
      return dateFormat;
   }
   
   
   
   public int getTimeFormat()
   {
      return timeFormat;
   }
   
   
   
   public String getDefaultListId()
   {
      return defaultListId;
   }
   
   
   
   public String getLanguage()
   {
      return language;
   }
   
   
   
   @Override
   public String toString()
   {
      return String.format( "RtmSettings [sync=%s, timezone=%s, dateFormat=%s, timeFormat=%s, defaultListId=%s, language=%s]",
                            syncTimeStampMillis,
                            timezone,
                            dateFormat,
                            timeFormat,
                            defaultListId,
                            language );
   }
   
}
