/*
 * Copyright 2007, MetaDimensional Technologies Inc.
 * 
 * 
 * This file is part of the RememberTheMilk Java API.
 * 
 * The RememberTheMilk Java API is free software; you can redistribute it
 * and/or modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 * 
 * The RememberTheMilk Java API is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package dev.drsoran.rtm;

import java.text.MessageFormat;


/**
 * 
 * @author Will Ross Jun 21, 2007
 */
public class RtmServiceException extends Exception
{
   private static final long serialVersionUID = -6711156026040643361L;
   
   private final int responseCode;
   
   private final String responseMessage;
   
   
   
   public RtmServiceException( Throwable e )
   {
      super( e );
      
      this.responseCode = -1;
      this.responseMessage = null;
   }
   
   
   
   public RtmServiceException( String message )
   {
      super( message );
      
      this.responseCode = -1;
      this.responseMessage = null;
   }
   
   
   
   public RtmServiceException( String message, Throwable e )
   {
      super( message, e );
      
      this.responseCode = -1;
      this.responseMessage = null;
   }
   
   
   
   public RtmServiceException( int responseCode, String responseMessage )
   {
      super( MessageFormat.format( "Service invocation failed. Code: {0}; message: {1}",
                                   responseCode,
                                   responseMessage ) );
      
      this.responseCode = responseCode;
      this.responseMessage = responseMessage;
   }
   
   
   
   public RtmServiceException( int responseCode, String responseMessage,
      Throwable e )
   {
      super( MessageFormat.format( "Service invocation failed. Code: {0}; message: {1}",
                                   responseCode,
                                   responseMessage ),
             e );
      
      this.responseCode = responseCode;
      this.responseMessage = responseMessage;
   }
   
   
   
   public int getResponseCode()
   {
      return responseCode;
   }
   
   
   
   public String getResponseMessage()
   {
      return responseMessage;
   }
   
   
   
   @Override
   public String getLocalizedMessage()
   {
      if ( Strings.isNullOrEmpty( responseMessage ) )
      {
         if ( getCause() != null )
         {
            return getCause().getLocalizedMessage();
         }
         else
         {
            return null;
         }
      }
      else
      {
         return responseMessage;
      }
   }
}
