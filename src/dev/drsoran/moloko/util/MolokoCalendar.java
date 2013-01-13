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

package dev.drsoran.moloko.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public final class MolokoCalendar
{
   private final Calendar impl = Calendar.getInstance();
   
   private boolean hasDate = true;
   
   private boolean hasTime = true;
   
   
   
   private MolokoCalendar( TimeZone timezone )
   {
      impl.setTimeZone( timezone );
   }
   
   
   
   public void add( int field, int value )
   {
      impl.add( field, value );
   }
   
   
   
   public boolean after( Object calendar )
   {
      return impl.after( calendar );
   }
   
   
   
   public boolean before( Object calendar )
   {
      return impl.before( calendar );
   }
   
   
   
   public final void clear()
   {
      impl.clear();
   }
   
   
   
   public final void clear( int field )
   {
      impl.clear( field );
   }
   
   
   
   @Override
   public Object clone()
   {
      return impl.clone();
   }
   
   
   
   public int compareTo( Calendar anotherCalendar )
   {
      return impl.compareTo( anotherCalendar );
   }
   
   
   
   @Override
   public boolean equals( Object object )
   {
      return impl.equals( object );
   }
   
   
   
   public int get( int field )
   {
      return impl.get( field );
   }
   
   
   
   public int getActualMaximum( int field )
   {
      return impl.getActualMaximum( field );
   }
   
   
   
   public int getActualMinimum( int field )
   {
      return impl.getActualMinimum( field );
   }
   
   
   
   public int getFirstDayOfWeek()
   {
      return impl.getFirstDayOfWeek();
   }
   
   
   
   public int getGreatestMinimum( int field )
   {
      return impl.getGreatestMinimum( field );
   }
   
   
   
   public int getLeastMaximum( int field )
   {
      return impl.getLeastMaximum( field );
   }
   
   
   
   public int getMaximum( int field )
   {
      return impl.getMaximum( field );
   }
   
   
   
   public int getMinimalDaysInFirstWeek()
   {
      return impl.getMinimalDaysInFirstWeek();
   }
   
   
   
   public int getMinimum( int field )
   {
      return impl.getMinimum( field );
   }
   
   
   
   public final Date getTime()
   {
      return impl.getTime();
   }
   
   
   
   public long getTimeInMillis()
   {
      return impl.getTimeInMillis();
   }
   
   
   
   public TimeZone getTimeZone()
   {
      return impl.getTimeZone();
   }
   
   
   
   @Override
   public int hashCode()
   {
      return impl.hashCode();
   }
   
   
   
   public boolean isLenient()
   {
      return impl.isLenient();
   }
   
   
   
   public final boolean isSet( int field )
   {
      return impl.isSet( field );
   }
   
   
   
   public void roll( int field, boolean increment )
   {
      impl.roll( field, increment );
   }
   
   
   
   public void roll( int field, int value )
   {
      impl.roll( field, value );
   }
   
   
   
   public final void set( int year,
                          int month,
                          int day,
                          int hourOfDay,
                          int minute,
                          int second )
   {
      impl.set( year, month, day, hourOfDay, minute, second );
   }
   
   
   
   public final void set( int year,
                          int month,
                          int day,
                          int hourOfDay,
                          int minute )
   {
      impl.set( year, month, day, hourOfDay, minute );
   }
   
   
   
   public final void set( int year, int month, int day )
   {
      impl.set( year, month, day );
   }
   
   
   
   public void set( int field, int value )
   {
      impl.set( field, value );
   }
   
   
   
   public void setFirstDayOfWeek( int value )
   {
      impl.setFirstDayOfWeek( value );
   }
   
   
   
   public void setLenient( boolean value )
   {
      impl.setLenient( value );
   }
   
   
   
   public void setMinimalDaysInFirstWeek( int value )
   {
      impl.setMinimalDaysInFirstWeek( value );
   }
   
   
   
   public final void setTime( Date date )
   {
      impl.setTime( date );
   }
   
   
   
   public void setTimeInMillis( long milliseconds )
   {
      impl.setTimeInMillis( milliseconds );
   }
   
   
   
   public void setTimeZone( TimeZone timezone )
   {
      impl.setTimeZone( timezone );
   }
   
   
   
   @Override
   public String toString()
   {
      if ( hasTime )
      {
         return impl.getTime().toString();
      }
      else
      {
         return new SimpleDateFormat( "EEE, dd.MM.yyyy" ).format( getTime() );
      }
   }
   
   
   
   public boolean hasDate()
   {
      return hasDate;
   }
   
   
   
   public void setHasDate( boolean hasDate )
   {
      this.hasDate = hasDate;
      
      if ( !this.hasDate )
      {
         impl.clear( Calendar.YEAR );
         impl.clear( Calendar.MONTH );
         impl.clear( Calendar.DATE );
         impl.clear( Calendar.DAY_OF_YEAR );
         impl.clear( Calendar.WEEK_OF_YEAR );
      }
   }
   
   
   
   public boolean hasTime()
   {
      return hasTime;
   }
   
   
   
   public void setHasTime( boolean hasTime )
   {
      this.hasTime = hasTime;
      
      if ( !this.hasTime )
      {
         impl.clear( Calendar.HOUR );
         impl.clear( Calendar.HOUR_OF_DAY );
         impl.clear( Calendar.MINUTE );
         impl.clear( Calendar.SECOND );
         impl.clear( Calendar.MILLISECOND );
         impl.clear( Calendar.AM_PM );
      }
   }
   
   
   
   public final static MolokoCalendar getInstance()
   {
      return new MolokoCalendar( TimeZone.getDefault() );
   }
   
   
   
   public final static MolokoCalendar getDatelessAndTimelessInstance()
   {
      final MolokoCalendar cal = getInstance();
      cal.setHasDate( false );
      cal.setHasTime( false );
      
      return cal;
   }
   
   
   
   public final static MolokoCalendar clone( MolokoCalendar other )
   {
      final MolokoCalendar calClone = getInstance();
      
      calClone.setTimeInMillis( other.getTimeInMillis() );
      calClone.setHasDate( other.hasDate );
      calClone.setHasTime( other.hasTime );
      
      return calClone;
   }
   
   
   
   public Calendar toCalendar()
   {
      return impl;
   }
   
   
   
   public final static MolokoCalendar getUTCInstance()
   {
      return new MolokoCalendar( TimeZone.getTimeZone( "UTC" ) );
   }
}
