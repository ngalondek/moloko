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

package dev.drsoran.moloko;

import android.content.Context;
import android.util.Log;


public abstract class MolokoLog
{
   private int logLevel;
   
   
   
   protected MolokoLog( Context context )
   {
      setLogLevel( context );
   }
   
   
   
   public void v( Class< ? > clazz, String msg )
   {
      if ( isLogLevelSet( Log.VERBOSE ) )
      {
         Log.v( makeTag( clazz ), msg );
      }
   }
   
   
   
   public void v( Class< ? > clazz, String msg, Throwable tr )
   {
      if ( isLogLevelSet( Log.VERBOSE ) )
      {
         Log.v( makeTag( clazz ), msg, tr );
      }
   }
   
   
   
   public void d( Class< ? > clazz, String msg )
   {
      if ( isLogLevelSet( Log.DEBUG ) )
      {
         Log.d( makeTag( clazz ), msg );
      }
   }
   
   
   
   public void d( Class< ? > clazz, String msg, Throwable tr )
   {
      if ( isLogLevelSet( Log.DEBUG ) )
      {
         Log.d( makeTag( clazz ), msg, tr );
      }
   }
   
   
   
   public void i( Class< ? > clazz, String msg )
   {
      if ( isLogLevelSet( Log.INFO ) )
      {
         Log.i( makeTag( clazz ), msg );
      }
   }
   
   
   
   public void i( Class< ? > clazz, String msg, Throwable tr )
   {
      if ( isLogLevelSet( Log.INFO ) )
      {
         Log.i( makeTag( clazz ), msg, tr );
      }
   }
   
   
   
   public void w( Class< ? > clazz, String msg )
   {
      if ( isLogLevelSet( Log.WARN ) )
      {
         Log.w( makeTag( clazz ), msg );
      }
   }
   
   
   
   public void w( Class< ? > clazz, String msg, Throwable tr )
   {
      if ( isLogLevelSet( Log.WARN ) )
      {
         Log.w( makeTag( clazz ), msg, tr );
      }
   }
   
   
   
   public void e( Class< ? > clazz, String msg )
   {
      if ( isLogLevelSet( Log.ERROR ) )
      {
         Log.e( makeTag( clazz ), msg );
      }
   }
   
   
   
   public void e( Class< ? > clazz, String msg, Throwable tr )
   {
      if ( isLogLevelSet( Log.ERROR ) )
      {
         Log.e( makeTag( clazz ), msg );
      }
   }
   
   
   
   private boolean isLogLevelSet( int level )
   {
      return level >= logLevel;
   }
   
   
   
   private String makeTag( Class< ? > clazz )
   {
      return "Moloko." + clazz.getSimpleName();
   }
   
   
   
   private void setLogLevel( Context context )
   {
      final String logLevelString = context.getString( R.string.env_log_level );
      
      if ( "verbose".equalsIgnoreCase( logLevelString ) )
      {
         logLevel = Log.VERBOSE;
      }
      else if ( "debug".equalsIgnoreCase( logLevelString ) )
      {
         logLevel = Log.DEBUG;
      }
      else if ( "info".equalsIgnoreCase( logLevelString ) )
      {
         logLevel = Log.INFO;
      }
      else if ( "error".equalsIgnoreCase( logLevelString ) )
      {
         logLevel = Log.ERROR;
      }
      else if ( "warn".equalsIgnoreCase( logLevelString ) )
      {
         logLevel = Log.WARN;
      }
      else
      {
         throw new IllegalArgumentException( String.format( "%s is no supported log level.",
                                                            logLevelString ) );
      }
   }
   
}
