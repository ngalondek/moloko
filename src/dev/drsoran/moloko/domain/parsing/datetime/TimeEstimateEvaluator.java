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

import dev.drsoran.moloko.grammar.antlr.datetime.TimeParser.ParseTimeEstimateContext;
import dev.drsoran.moloko.grammar.antlr.datetime.TimeParser.TimeNaturalSpecFloatHoursContext;
import dev.drsoran.moloko.grammar.antlr.datetime.TimeParser.TimeNaturalSpecUnitHoursContext;
import dev.drsoran.moloko.grammar.antlr.datetime.TimeParser.TimeNaturalSpecUnitMinutesContext;
import dev.drsoran.moloko.grammar.antlr.datetime.TimeParser.TimeNaturalSpecUnitSecondsContext;
import dev.drsoran.moloko.grammar.antlr.datetime.TimeParserBaseVisitor;


public class TimeEstimateEvaluator extends TimeParserBaseVisitor< Void >
{
   private long estimateMillis;
   
   
   
   public long getEstimateMillis()
   {
      return estimateMillis;
   }
   
   
   
   @Override
   public Void visitParseTimeEstimate( ParseTimeEstimateContext ctx )
   {
      if ( ctx.d != null )
      {
         final int days = Integer.parseInt( ctx.d.getText() );
         addDays( days );
      }
      
      return super.visitParseTimeEstimate( ctx );
   }
   
   
   
   @Override
   public Void visitTimeNaturalSpecFloatHours( TimeNaturalSpecFloatHoursContext ctx )
   {
      final int hours = Integer.parseInt( ctx.INT( 0 ).getText() );
      addHours( hours );
      
      final int deciHours = Integer.parseInt( ctx.INT( 1 ).getText() );
      final int minutes = (int) ( 60.0f * ( deciHours / 10.0f ) );
      addMinutes( minutes );
      
      return null;
   }
   
   
   
   @Override
   public Void visitTimeNaturalSpecUnitHours( TimeNaturalSpecUnitHoursContext ctx )
   {
      final int hours = Integer.parseInt( ctx.INT().getText() );
      addHours( hours );
      
      return null;
   }
   
   
   
   @Override
   public Void visitTimeNaturalSpecUnitMinutes( TimeNaturalSpecUnitMinutesContext ctx )
   {
      final int minutes = Integer.parseInt( ctx.INT().getText() );
      addMinutes( minutes );
      
      return null;
   }
   
   
   
   @Override
   public Void visitTimeNaturalSpecUnitSeconds( TimeNaturalSpecUnitSecondsContext ctx )
   {
      final int seconds = Integer.parseInt( ctx.INT().getText() );
      addSeconds( seconds );
      
      return null;
   }
   
   
   
   private void addDays( int days )
   {
      addHours( 24 * days );
   }
   
   
   
   private void addHours( int hours )
   {
      addMinutes( 60 * hours );
   }
   
   
   
   private void addMinutes( int minutes )
   {
      addSeconds( 60 * minutes );
   }
   
   
   
   private void addSeconds( int seconds )
   {
      estimateMillis += seconds * 1000;
   }
}
