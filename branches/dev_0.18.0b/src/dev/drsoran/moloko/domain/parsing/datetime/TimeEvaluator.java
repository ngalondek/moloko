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

package dev.drsoran.moloko.domain.parsing.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.grammar.antlr.datetime.TimeParser.AMContext;
import dev.drsoran.moloko.grammar.antlr.datetime.TimeParser.MiddayContext;
import dev.drsoran.moloko.grammar.antlr.datetime.TimeParser.MidnightContext;
import dev.drsoran.moloko.grammar.antlr.datetime.TimeParser.NeverContext;
import dev.drsoran.moloko.grammar.antlr.datetime.TimeParser.PMContext;
import dev.drsoran.moloko.grammar.antlr.datetime.TimeParser.SeparatedTimeHMContext;
import dev.drsoran.moloko.grammar.antlr.datetime.TimeParser.SeparatedTimeHMSContext;
import dev.drsoran.moloko.grammar.antlr.datetime.TimeParser.TimeNaturalSpecFloatHoursContext;
import dev.drsoran.moloko.grammar.antlr.datetime.TimeParser.TimeNaturalSpecUnitHoursContext;
import dev.drsoran.moloko.grammar.antlr.datetime.TimeParser.TimeNaturalSpecUnitMinutesContext;
import dev.drsoran.moloko.grammar.antlr.datetime.TimeParser.TimeNaturalSpecUnitSecondsContext;
import dev.drsoran.moloko.grammar.antlr.datetime.TimeParser.UnSeparatedTimeContext;
import dev.drsoran.moloko.grammar.antlr.datetime.TimeParserBaseVisitor;


public class TimeEvaluator extends TimeParserBaseVisitor< Void >
{
   final MolokoCalendar cal;
   
   boolean visitedNever;
   
   
   
   public TimeEvaluator()
   {
      this( MolokoCalendar.getDatelessAndTimelessInstance() );
   }
   
   
   
   public TimeEvaluator( MolokoCalendar cal )
   {
      this.cal = cal;
   }
   
   
   
   public MolokoCalendar getCalendar()
   {
      return cal;
   }
   
   
   
   @Override
   public Void visit( @NotNull ParseTree tree )
   {
      super.visit( tree );
      
      if ( !visitedNever )
      {
         cal.setHasTime( true );
      }
      
      return null;
   }
   
   
   
   @Override
   public Void visitNever( NeverContext ctx )
   {
      cal.setHasDate( false );
      cal.setHasTime( false );
      
      visitedNever = true;
      
      return null;
   }
   
   
   
   @Override
   public Void visitMidnight( MidnightContext ctx )
   {
      cal.set( Calendar.HOUR_OF_DAY, 23 );
      cal.set( Calendar.MINUTE, 59 );
      cal.set( Calendar.SECOND, 59 );
      
      return null;
   }
   
   
   
   @Override
   public Void visitMidday( MiddayContext ctx )
   {
      cal.set( Calendar.HOUR_OF_DAY, 12 );
      cal.set( Calendar.MINUTE, 0 );
      cal.set( Calendar.SECOND, 0 );
      
      return null;
   }
   
   
   
   @Override
   public Void visitSeparatedTimeHM( SeparatedTimeHMContext ctx )
   {
      cal.set( Calendar.HOUR_OF_DAY, Integer.parseInt( ctx.h.getText() ) );
      cal.set( Calendar.MINUTE, Integer.parseInt( ctx.m.getText() ) );
      cal.set( Calendar.SECOND, 0 );
      
      return null;
   }
   
   
   
   @Override
   public Void visitSeparatedTimeHMS( SeparatedTimeHMSContext ctx )
   {
      cal.set( Calendar.HOUR_OF_DAY, Integer.parseInt( ctx.h.getText() ) );
      cal.set( Calendar.MINUTE, Integer.parseInt( ctx.m.getText() ) );
      cal.set( Calendar.SECOND, Integer.parseInt( ctx.s.getText() ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitUnSeparatedTime( UnSeparatedTimeContext ctx )
   {
      final String pointInTime = ctx.INT().getText();
      
      final int len = pointInTime.length();
      
      SimpleDateFormat sdf = null;
      
      try
      {
         if ( len < 3 )
         {
            sdf = new SimpleDateFormat( "HH" );
         }
         else if ( len > 3 )
         {
            sdf = new SimpleDateFormat( "HHmm" );
         }
         else
         {
            sdf = new SimpleDateFormat( "Hmm" );
         }
         
         sdf.setTimeZone( cal.getTimeZone() );
         sdf.parse( pointInTime );
         
         final Calendar sdfCal = sdf.getCalendar();
         cal.set( Calendar.HOUR_OF_DAY, sdfCal.get( Calendar.HOUR_OF_DAY ) );
         cal.set( Calendar.MINUTE, sdfCal.get( Calendar.MINUTE ) );
         cal.set( Calendar.SECOND, 0 );
         
         // This additional get is necessary to have the calendar apply the sdfCal HOUR.
         cal.get( Calendar.HOUR_OF_DAY );
      }
      catch ( ParseException e )
      {
         throw new RecognitionException( null, null, null );
      }
      
      return null;
   }
   
   
   
   @Override
   public Void visitAM( AMContext ctx )
   {
      // This get is needed to prevent stale calendar values
      cal.get( Calendar.AM_PM );
      cal.set( Calendar.AM_PM, Calendar.AM );
      
      return null;
   }
   
   
   
   @Override
   public Void visitPM( PMContext ctx )
   {
      // This get is needed to prevent stale calendar values
      cal.get( Calendar.AM_PM );
      cal.set( Calendar.AM_PM, Calendar.PM );
      
      return null;
   }
   
   
   
   @Override
   public Void visitTimeNaturalSpecFloatHours( TimeNaturalSpecFloatHoursContext ctx )
   {
      final int hours = Integer.parseInt( ctx.INT( 0 ).getText() );
      cal.add( Calendar.HOUR, hours );
      
      final int deciHours = Integer.parseInt( ctx.INT( 1 ).getText() );
      cal.add( Calendar.MINUTE, (int) ( 60.0f * ( deciHours / 10.0f ) ) );
      
      return null;
   }
   
   
   
   @Override
   public Void visitTimeNaturalSpecUnitHours( TimeNaturalSpecUnitHoursContext ctx )
   {
      final int hours = Integer.parseInt( ctx.INT().getText() );
      cal.add( Calendar.HOUR, hours );
      
      return null;
   }
   
   
   
   @Override
   public Void visitTimeNaturalSpecUnitMinutes( TimeNaturalSpecUnitMinutesContext ctx )
   {
      final int minutes = Integer.parseInt( ctx.INT().getText() );
      cal.add( Calendar.MINUTE, minutes );
      
      return null;
   }
   
   
   
   @Override
   public Void visitTimeNaturalSpecUnitSeconds( TimeNaturalSpecUnitSecondsContext ctx )
   {
      final int seconds = Integer.parseInt( ctx.INT().getText() );
      cal.add( Calendar.SECOND, seconds );
      
      return null;
   }
}
